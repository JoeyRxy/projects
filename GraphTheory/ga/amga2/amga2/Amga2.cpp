#include <cmath>
#include <iostream>
#include <iomanip>
#include <fstream>
#include <sstream>
#include <algorithm>

#include "Amga2.h"
#include "Display.h"

using namespace std;

const double Amga2::INF = 1.0e14;
const double Amga2::EPS = 1.0e-8;

Amga2::Amga2(const AbstractTestProblem &_evaluator, unsigned int _numVar, unsigned int _numObj, unsigned int _numConstr, const double *_minVar, const double *_maxVar, unsigned int _numEvaluations, unsigned int _eliteSize, double _randomSeed, unsigned int _debugMode) : evaluator(_evaluator) {
    numVar = _numVar;
    minVar = new double[numVar];
    maxVar = new double[numVar];
    for (register unsigned int i = 0; i < numVar; i++) {
        minVar[i] = _minVar[i];
        maxVar[i] = _maxVar[i];
    }
    numObj = _numObj;
    minObj = new double[numObj];
    maxObj = new double[numObj];
    for (register unsigned int i = 0; i < numObj; i++) {
        minObj[i] = maxObj[i] = 0.0;
    }
    numConstr = _numConstr;
    if (numConstr > 0) {
        minConstr = new double[numConstr];
        maxConstr = new double[numConstr];
        for (register unsigned int i = 0; i < numConstr; i++) {
            minConstr[i] = maxConstr[i] = 0.0;
        }
    }
    numEvaluations = _numEvaluations;
    initialSize = _eliteSize;
    populationSize = 4 * numObj;
    archiveSize = _eliteSize;
    eliteSize = _eliteSize;
    probCross = 0.1;
    etaCross = 0.5;
    probMut = 1.0 / numVar;
    etaMut = 15.0;
    randGen.setSeed(_randomSeed);
    debugMode = _debugMode;
    currentArchiveSize = 0;
    evaluationCounter = 0;
    isEvolutionEnded = false;
    archivePopulation.reserve(archiveSize);
    parentPopulation.reserve(populationSize);
    offspringPopulation.reserve(populationSize);
    combinedPopulation.reserve(populationSize + archiveSize);
    for (register unsigned int i = 0; i < archiveSize; i++) {
        archivePopulation.push_back(Individual(*this, evaluator));
        combinedPopulation.push_back(Individual(*this, evaluator));
    }
    for (register unsigned int i = 0; i < populationSize; i++) {
        parentPopulation.push_back(Individual(*this, evaluator));
        offspringPopulation.push_back(Individual(*this, evaluator));
        combinedPopulation.push_back(Individual(*this, evaluator));
    }
}

Amga2::~Amga2() {
    archivePopulation.clear();
    parentPopulation.clear();
    offspringPopulation.clear();
    combinedPopulation.clear();
    delete[](minVar);
    delete[](maxVar);
    delete[](minObj);
    delete[](maxObj);
    if (numConstr > 0) {
        delete[](minConstr);
        delete[](maxConstr);
    }
}

void Amga2::assignDiversityMetric(vector<Individual>& population, list<unsigned int>& elite) {
    if (elite.size() <= 2) {
        for (list<unsigned int>::iterator iter = elite.begin(); iter != elite.end(); ++iter) {
            population[*iter].diversity = INF;
        }
        return;
    }
    for (list<unsigned int>::iterator iter = elite.begin(); iter != elite.end(); ++iter) {
        population[*iter].diversity = 0.0;
    }
    list<unsigned int> distinct;
    extractDistinctIndividuals(population, elite, distinct);
    if (distinct.size() <= 2) {
        for (list<unsigned int>::iterator iter = distinct.begin(); iter != distinct.end(); ++iter) {
            population[*iter].diversity = INF;
        }
    } else {
        register unsigned int size = static_cast<unsigned int> (distinct.size());
        unsigned int *indexArray = new unsigned int[size];
        unsigned int **objArray = new unsigned int*[numObj];
        for (register unsigned int i = 0; i < numObj; i++) {
            objArray[i] = new unsigned int[size];
        }
        register unsigned int counter = 0;
        for (list<unsigned int>::iterator iter = distinct.begin(); iter != distinct.end(); ++iter) {
            indexArray[counter++] = *iter;
        }
        for (register unsigned int i = 0; i < numObj; i++) {
            for (register unsigned int j = 0; j < size; j++) {
                objArray[i][j] = indexArray[j];
            }
            quickSortObj(population, i, objArray[i], 0, size - 1);
        }
        for (register unsigned int j = 0; j < size; j++) {
            population[indexArray[j]].diversity = 0.0;
        }
        register double l, r;
        for (register unsigned int i = 0; i < numObj; i++) {
            for (register unsigned int j = 0; j < size; j++) {
                if (population[objArray[i][size - 1]].normalObj[i] != population[objArray[i][0]].normalObj[i]) {
                    register double val = population[objArray[i][size - 1]].normalObj[i] - population[objArray[i][0]].normalObj[i];
                    if (j == 0) {
                        r = (population[objArray[i][j + 1]].normalObj[i] - population[objArray[i][j]].normalObj[i]) / val;
                        population[objArray[i][j]].diversity += r*r;
                    } else if (j == size - 1) {
                        l = (population[objArray[i][j]].normalObj[i] - population[objArray[i][j - 1]].normalObj[i]) / val;
                        population[objArray[i][j]].diversity += l*l;
                    } else {
                        l = (population[objArray[i][j]].normalObj[i] - population[objArray[i][j - 1]].normalObj[i]) / val;
                        r = (population[objArray[i][j + 1]].normalObj[i] - population[objArray[i][j]].normalObj[i]) / val;
                        population[objArray[i][j]].diversity += l*r;
                    }
                }
            }
        }
        delete[](indexArray);
        for (register unsigned int i = 0; i < numObj; i++) {
            delete[](objArray[i]);
        }
        delete[](objArray);
    }
    distinct.clear();
    return;
}

void Amga2::assignInfiniteDiversity(vector<Individual> &population, list<unsigned int> &elite) {
    if (elite.size() <= 2) {
        for (list<unsigned int>::iterator iter = elite.begin(); iter != elite.end(); ++iter) {
            population[*iter].diversity = INF;
        }
        return;
    }
    register unsigned int size = elite.size();
    unsigned int *indexArray = new unsigned int[size];
    register unsigned int counter = 0;
    for (list<unsigned int>::iterator iter = elite.begin(); iter != elite.end(); ++iter) {
        indexArray[counter++] = *iter;
    }
    register unsigned int index;
    register double minimumObjective;
    for (register unsigned int i = 0; i < numObj; i++) {
        index = indexArray[0];
        minimumObjective = population[index].normalObj[i];
        for (register unsigned int j = 0; j < size; j++) {
            if (minimumObjective > population[indexArray[j]].normalObj[i]) {
                index = indexArray[j];
                minimumObjective = population[index].normalObj[i];
            }
        }
        population[index].diversity = INF;
    }
    delete[](indexArray);
    return;
}

void Amga2::createInitialPopulation() {
    unsigned int *a = new unsigned int[initialSize];
    double *grid = new double[numVar];
    double *val = new double[initialSize];
    register unsigned int temp = 0;
    register unsigned int rand = 0;
    for (register unsigned int j = 0; j < numVar; j++) {
        grid[j] = 1.0 / (double) initialSize;
    }
    for (register unsigned int i = 0; i < initialSize; i++) {
        a[i] = i;
    }
    for (register unsigned int j = 0; j < numVar; j++) {
        for (register unsigned int i = 0; i < initialSize; i++) {
            rand = randGen.fetchRandomInteger(i, initialSize - 1);
            temp = a[rand];
            a[rand] = a[i];
            a[i] = temp;
        }
        for (register unsigned int i = 0; i < initialSize; i++) {
            val[i] = randGen.fetchRandomReal(1.0 + grid[j] * i, 1.0 + grid[j]*(i + 1));
            if (val[i] < 1.0) {
                val[i] = 1.0;
            } else if (val[i] > 2.0) {
                val[i] = 2.0;
            }
            archivePopulation[a[i]].normalVar[j] = val[i];
        }
    }
    delete[](a);
    delete[](grid);
    delete[](val);
    return;
}

void Amga2::createParentPopulation() {
    list<unsigned int> pool;
    list<unsigned int> elite;
    list<unsigned int> selectionPool;
    list<unsigned int> extreme;
    for (register unsigned int i = 0; i < currentArchiveSize; i++) {
        pool.push_back(i);
    }
    register unsigned int rank = 1;
    while (selectionPool.size() < populationSize) {
        extractBestRank(archivePopulation, pool, elite);
        for (list<unsigned int>::iterator iter = elite.begin(); iter != elite.end(); ++iter) {
            archivePopulation[*iter].rank = rank;
            selectionPool.push_back(*iter);
        }
        rank++;
        elite.clear();
    }
    extractExtremeSolutions(archivePopulation, selectionPool, extreme);
    register unsigned int j = 0;
    for (list<unsigned int>::iterator iter = extreme.begin(); iter != extreme.end(); ++iter) {
        parentPopulation[j++] = archivePopulation[*iter];
    }
    fillDiversePopulation(archivePopulation, selectionPool, parentPopulation, j, populationSize - j);
    pool.clear();
    elite.clear();
    selectionPool.clear();
    extreme.clear();
    return;
}

void Amga2::createOffspringPopulation() {
    register unsigned int index, r1, r2, r3;
    for (register unsigned int i = 0; i < populationSize; i++) {
        index = i;
        do {
            r1 = randGen.fetchRandomInteger(0, currentArchiveSize - 1);
        } while (archivePopulation[r1].evalCounter == parentPopulation[index].evalCounter);
        do {
            r2 = randGen.fetchRandomInteger(0, currentArchiveSize - 1);
        } while (archivePopulation[r2].evalCounter == parentPopulation[index].evalCounter || r2 == r1);
        do {
            r3 = randGen.fetchRandomInteger(0, currentArchiveSize - 1);
        } while (archivePopulation[r3].evalCounter == parentPopulation[index].evalCounter || r3 == r1 || r3 == r2);
        crossIndividuals(parentPopulation[index], archivePopulation[r1], archivePopulation[r2], archivePopulation[r3], offspringPopulation[index]);
        offspringPopulation[index].rank = parentPopulation[index].rank; //for rank based mutation
    }
    return;
}

void Amga2::crossIndividuals(const Individual &parent, const Individual& r1, const Individual &r2, const Individual &r3, Individual &offspring) {
    register unsigned int jrand = randGen.fetchRandomInteger(0, numVar - 1);
    for (register unsigned int j = 0; j < numVar; j++) {
        if (randGen.fetchRandomReal(0.0, 1.0) < probCross || j == jrand) {
            register double value = r3.normalVar[j] + etaCross * (r1.normalVar[j] - r2.normalVar[j]);
            if (value < 1.0) {
                value = 1.0;
            } else if (value > 2.0) {
                value = 2.0;
            }
            offspring.normalVar[j] = value;
        } else {
            offspring.normalVar[j] = parent.normalVar[j];
        }
    }
    return;
}

void Amga2::finalizePopulation() {
    list<unsigned int> pool;
    list<unsigned int> elite;
    for (register unsigned int i = 0; i < currentArchiveSize; i++) {
        if (archivePopulation[i].constrViolation >= 0.0) {
            pool.push_back(i);
        }
    }
    if (pool.size() == 0) {
        currentArchiveSize = 0;
    } else {
        extractBestRank(archivePopulation, pool, elite);
        pool.clear();
        if (elite.size() > eliteSize) {
            for (list<unsigned int>::iterator iter = elite.begin(); iter != elite.end(); ++iter) {
                archivePopulation[*iter].diversity = 0.0;
            }
            assignInfiniteDiversity(archivePopulation, elite);
            extractENNSPopulation(archivePopulation, elite, pool, eliteSize);
            elite.clear();
            for (list<unsigned int>::iterator iter = pool.begin(); iter != pool.end(); ++iter) {
                elite.push_back(*iter);
            }
            pool.clear();
        }
        currentArchiveSize = elite.size();
        register unsigned int i = 0;
        for (list<unsigned int>::iterator iter = elite.begin(); iter != elite.end(); ++iter) {
            combinedPopulation[i++] = archivePopulation[*iter];
        }
    }
    pool.clear();
    elite.clear();
    return;
}

void Amga2::evaluatePopulation(vector<Individual>& population, unsigned int size) {
    for (register unsigned int i = 0; i < size; i++) {
        evaluationCounter++;
        population[i].evaluate();
    }
    return;
}

void Amga2::normalizePopulation(vector<Individual>& population, unsigned int size) {
    for (register unsigned int i = 0; i < size; i++) {
        population[i].normalizeValues();
    }
    return;
}

void Amga2::evolvePopulation() {
    Display viewer(numObj);
    createInitialPopulation();
    currentArchiveSize = initialSize;
    evaluatePopulation(archivePopulation, currentArchiveSize);
    updateObjectiveConstraintRange();
    normalizePopulation(archivePopulation, currentArchiveSize);
    register unsigned int numGenerations = (numEvaluations - evaluationCounter) / populationSize;
    cout << "\n Generation:      1     Evaluations: " << setw(6) << evaluationCounter;
    if (debugMode) {
        printObjectives(archivePopulation, currentArchiveSize, "arch.txt");
        viewer.display("arch.txt", evaluationCounter, 6, 1);
    }
    for (register unsigned int i = 0; i < numGenerations; i++) {
        updateObjectiveConstraintRange();
        normalizePopulation(archivePopulation, currentArchiveSize);
        createParentPopulation();
        createOffspringPopulation();
        mutateOffspringPopulation();
        evaluatePopulation(offspringPopulation, populationSize);
        normalizePopulation(offspringPopulation, populationSize);
        updateArchivePopulation();
        register unsigned int rankOneSize = 0;
        for (register unsigned int j = 0; j < currentArchiveSize; j++) {
            if (archivePopulation[j].rank == 1) {
                rankOneSize++;
            }
        }
        cout << "\n Generation: " << setw(6) << i + 2 << "     Evaluations: " << setw(6) << evaluationCounter << "     EliteSize: " << setw(3) << rankOneSize;
        if (debugMode) {
            printObjectives(archivePopulation, currentArchiveSize, "arch.txt");
            viewer.display("arch.txt", evaluationCounter, 6, 1);
            printObjectives(parentPopulation, populationSize, "parent.txt");
            viewer.reDisplay("parent.txt", 4, 2);
        }
    }
    finalizePopulation();
    return;
}

void Amga2::extractBestRank(const vector<Individual>& population, list<unsigned int>& pool, list<unsigned int>& elite) {
    list<unsigned int> remains;
    list<unsigned int>::iterator iter;
    register int flag = 0;
    register int index1 = 0;
    register int index2 = 0;
    bool stop = true;
    elite.push_back(pool.front());
    pool.pop_front();
    while (pool.size() > 0) {
        index1 = pool.front();
        pool.pop_front();
        stop = false;
        flag = -1;
        iter = elite.begin();
        while (stop == false && iter != elite.end()) {
            index2 = *iter;
            flag = checkDomination(population[index1], population[index2]);
            if (flag == 1) {
                remains.push_back(index2);
                iter = elite.erase(iter);
            } else if (flag == -1) {
                stop = true;
                ++iter;
            } else {
                ++iter;
            }
        }
        if (flag == 0 || flag == 1) {
            elite.push_back(index1);
        } else {
            remains.push_back(index1);
        }
    }
    pool.clear();
    for (iter = remains.begin(); iter != remains.end(); ++iter) {
        pool.push_back(*iter);
    }
    remains.clear();
    return;
}

void Amga2::extractDistinctIndividuals(vector<Individual> &population, list<unsigned int> &elite, list<unsigned int> &distinct) {
    register unsigned int size = static_cast<unsigned int> (elite.size());
    unsigned int* indexArray = new unsigned int[size];
    register unsigned int i = 0;
    for (list<unsigned int>::iterator iter = elite.begin(); iter != elite.end(); ++iter) {
        indexArray[i++] = *iter;
    }
    quickSortObj(population, 0, indexArray, 0, size - 1);
    register unsigned int current = indexArray[0];
    distinct.push_back(current);
    for (register unsigned int i = 1; i < size; i++) {
        register bool flag = true;
        for (register unsigned int j = 0; j < numObj; j++) {
            if (population[indexArray[i]].normalObj[j] != population[current].normalObj[j]) {
                flag = false;
            }
        }
        if (!flag) {
            current = indexArray[i];
            distinct.push_back(current);
        }
    }
    delete[](indexArray);
    return;
}

void Amga2::extractENNSPopulation(const vector<Individual> &mixedPopulation, list<unsigned int> &pool, list<unsigned int> &elite, unsigned int desiredEliteSize) {
    register unsigned int poolSize = pool.size();
    register unsigned int mixedSize = mixedPopulation.size();
    register unsigned int numInf = 0;
    for (list<unsigned int>::iterator iter = pool.begin(); iter != pool.end(); ++iter) {
        if (mixedPopulation[*iter].diversity == INF) {
            numInf++;
        }
    }
    if (desiredEliteSize <= numInf) {
        for (list<unsigned int>::iterator iter = pool.begin(); iter != pool.end(); ++iter) {
            if (mixedPopulation[*iter].diversity == INF) {
                elite.push_back(*iter);
                if (elite.size() == desiredEliteSize) {
                    return;
                }
            }
        }
    }
    for (list<unsigned int>::iterator iter = pool.begin(); iter != pool.end(); ++iter) {
        elite.push_back(*iter);
    }
    pool.clear();
    if (desiredEliteSize == elite.size()) {
        return;
    }
    double **distance;
    unsigned int *indexArray;
    int *originalArray;
    distance = new double*[poolSize];
    indexArray = new unsigned int[poolSize];
    originalArray = new int[mixedSize];
    for (register unsigned int i = 0; i < poolSize; i++) {
        distance[i] = new double[poolSize];
        indexArray[i] = 0;
        for (register unsigned int j = 0; j < poolSize; j++) {
            distance[i][j] = 0.0;
        }
    }
    for (register unsigned int i = 0; i < mixedSize; i++) {
        originalArray[i] = -1;
    }
    register unsigned int counter = 0;
    for (list<unsigned int>::iterator iter = elite.begin(); iter != elite.end(); ++iter) {
        indexArray[counter] = *iter;
        originalArray[indexArray[counter]] = counter;
        counter++;
    }
    vector<DistanceMatrix> distArray;
    distArray.reserve(((poolSize - 1) * poolSize) / 2);
    for (register unsigned int i = 0; i < poolSize; i++) {
        distance[i][i] = 0.0;
        for (register unsigned int j = i + 1; j < poolSize; j++) {
            distance[j][i] = distance[i][j] = calcEuclideanDistance(mixedPopulation[indexArray[i]], mixedPopulation[indexArray[j]]);
            distArray.push_back(DistanceMatrix(indexArray[i], indexArray[j], distance[i][j]));
        }
    }
    sort(distArray.begin(), distArray.end());
    vector<DistanceMatrix>::iterator distIterator = distArray.begin();
    while (elite.size() > desiredEliteSize && distIterator != distArray.end()) {
        register int index = -1;
        register int index1 = -1;
        register int index2 = -1;
        register double dist1 = 0.0;
        register double dist2 = 0.0;
        do {
            DistanceMatrix temp = *distIterator;
            ++distIterator;
            index1 = temp.index1;
            index2 = temp.index2;
        } while ((originalArray[index1] == -1 || originalArray[index2] == -1) && distIterator != distArray.end());
        if (distIterator == distArray.end()) {
            break;
        }
        if (mixedPopulation[index1].diversity == INF && mixedPopulation[index2].diversity == INF) {
            // do nothing
        } else if (mixedPopulation[index1].diversity == INF) {
            elite.remove(index2);
            pool.push_back(index2);
            originalArray[index2] = -1;
        } else if (mixedPopulation[index2].diversity == INF) {
            elite.remove(index1);
            pool.push_back(index1);
            originalArray[index1] = -1;
        } else {
            dist1 = INF;
            list<unsigned int>::iterator iter;
            for (iter = elite.begin(); iter != elite.end(); ++iter) {
                index = *iter;
                if (index != index1 && index != index2) {
                    if (dist1 > distance[originalArray[index1]][originalArray[index]]) {
                        dist1 = distance[originalArray[index1]][originalArray[index]];
                    }
                }
            }
            dist2 = INF;
            for (iter = elite.begin(); iter != elite.end(); ++iter) {
                index = *iter;
                if (index != index1 && index != index2) {
                    if (dist2 > distance[originalArray[index2]][originalArray[index]]) {
                        dist2 = distance[originalArray[index2]][originalArray[index]];
                    }
                }
            }
            if (dist1 < dist2) {
                elite.remove(index1);
                pool.push_back(index1);
                originalArray[index1] = -1;
            } else {
                elite.remove(index2);
                pool.push_back(index2);
                originalArray[index2] = -1;
            }
        }
    }
    if (elite.size() > desiredEliteSize) {
        while (elite.size() > desiredEliteSize) {
            pool.push_back(elite.front());
            elite.pop_front();
        }
    }
    delete[](indexArray);
    delete[](originalArray);
    for (register unsigned int i = 0; i < poolSize; i++) {
        delete[](distance[i]);
    }
    delete[](distance);
    return;
}

void Amga2::extractExtremeSolutions(const vector<Individual> &population, list<unsigned int> &pool, list<unsigned int> &extreme) {
    unsigned int *extremeSolution = new unsigned int[numObj];
    vector<list<unsigned int>::iterator> iterVector;
    iterVector.reserve(numObj);
    for (register unsigned int i = 0; i < numObj; i++) {
        iterVector.push_back(pool.begin());
    }
    list<unsigned int>::iterator iter;
    for (register unsigned int i = 0; i < numObj; i++) {
        iter = pool.begin();
        extremeSolution[i] = *iter;
        iterVector[i] = iter;
        ++iter;
        while (iter != pool.end()) {
            if (population[*iter].normalObj[i] > population[extremeSolution[i]].normalObj[i]) {
                extremeSolution[i] = *iter;
                iterVector[i] = iter;
            }
            ++iter;
        }
    }
    for (register unsigned int i = 0; i < numObj; i++) {
        for (register unsigned int j = i + 1; j < numObj; j++) {
            if (extremeSolution[j] != numEvaluations && extremeSolution[i] != numEvaluations) {
                if (extremeSolution[j] == extremeSolution[i]) {
                    extremeSolution[j] = numEvaluations;
                }
            }
        }
    }
    for (register unsigned int i = 0; i < numObj; i++) {
        if (extremeSolution[i] != numEvaluations) {
            pool.erase(iterVector[i]);
            extreme.push_back(extremeSolution[i]);
        }
    }
    iterVector.clear();
    delete[](extremeSolution);
    return;
}

void Amga2::fillBestPopulation(vector<Individual> &mixedPopulation, unsigned int mixedLength, vector<Individual> &population, unsigned int populationLength) {
    list<unsigned int> pool;
    list<unsigned int> elite;
    list<unsigned int> filled;
    register unsigned int rank = 1;
    for (register unsigned int i = 0; i < mixedLength; i++) {
        pool.push_back(i);
    }
    for (list<unsigned int>::iterator iter = pool.begin(); iter != pool.end(); ++iter) {
        mixedPopulation[*iter].diversity = 0.0;
    }
    while (filled.size() < populationLength) {
        extractBestRank(mixedPopulation, pool, elite);
        for (list<unsigned int>::iterator iter = elite.begin(); iter != elite.end(); ++iter) {
            mixedPopulation[*iter].rank = rank;
        }
        if (rank == 1) {
            assignInfiniteDiversity(mixedPopulation, elite);
        }
        rank++;
        if (elite.size() + filled.size() < populationLength) {
            for (list<unsigned int>::iterator iter = elite.begin(); iter != elite.end(); ++iter) {
                filled.push_back(*iter);
            }
            elite.clear();
        } else {
            list<unsigned int> temp;
            extractENNSPopulation(mixedPopulation, elite, temp, populationLength - filled.size());
            for (list<unsigned int>::iterator iter = temp.begin(); iter != temp.end(); ++iter) {
                filled.push_back(*iter);
            }
            temp.clear();
        }
    }
    register unsigned int i = 0;
    for (list<unsigned int>::iterator iter = filled.begin(); iter != filled.end(); ++iter) {
        population[i++] = mixedPopulation[*iter];
    }
    pool.clear();
    elite.clear();
    filled.clear();
    return;
}

void Amga2::fillDiversePopulation(vector<Individual> &mixedPopulation, list<unsigned int> &pool, vector<Individual> &population, unsigned int startLocation, unsigned int desiredSize) {
    assignDiversityMetric(mixedPopulation, pool);
    register unsigned int poolSize = pool.size();
    unsigned int *indexArray = new unsigned int[poolSize];
    register unsigned int j = 0;
    for (list<unsigned int>::iterator iter = pool.begin(); iter != pool.end(); ++iter) {
        indexArray[j++] = *iter;
    }
    quickSortDiversity(mixedPopulation, indexArray, 0, poolSize - 1);
    for (register unsigned int i = 0; i < desiredSize; i++) {
        population[startLocation + i] = mixedPopulation[indexArray[poolSize - 1 - i]];
    }
    delete[](indexArray);
    return;
}

void Amga2::mutateOffspringPopulation() {
    register double rnd = 0.0;
    register double delta1 = 0.0;
    register double delta2 = 0.0;
    register double mut_pow = 0.0;
    register double deltaq = 0.0;
    register double y = 0.0;
    register double yl = 0.0;
    register double yu = 0.0;
    register double val = 0.0;
    register double xy = 0.0;
    for (register unsigned int i = 0; i < populationSize; i++) {
        for (register unsigned int j = 0; j < numVar; j++) {
            double pMut = probMut + (1.0 - probMut)*((double) (offspringPopulation[i].rank - 1) / (currentArchiveSize - 1)); //rank-based variation
            if (randGen.fetchRandomReal(0.0, 1.0) <= pMut) {
                y = offspringPopulation[i].normalVar[j];
                yl = 1.0;
                yu = 2.0;
                delta1 = (y - yl) / (yu - yl);
                delta2 = (yu - y) / (yu - yl);
                rnd = randGen.fetchRandomReal(0.0, 1.0);
                mut_pow = 1.0 / (etaMut + 1.0);
                if (rnd <= 0.5) {
                    xy = 1.0 - delta1;
                    val = 2.0 * rnd + (1.0 - 2.0 * rnd) * (pow(xy, (etaMut + 1.0)));
                    deltaq = pow(val, mut_pow) - 1.0;
                } else {
                    xy = 1.0 - delta2;
                    val = 2.0 * (1.0 - rnd) + 2.0 * (rnd - 0.5) * (pow(xy, (etaMut + 1.0)));
                    deltaq = 1.0 - (pow(val, mut_pow));
                }
                y = y + deltaq * (yu - yl);
                if (y < yl) {
                    y = yl;
                }
                if (y > yu) {
                    y = yu;
                }
                offspringPopulation[i].normalVar[j] = y;
            }
        }
    }
    return;
}

void Amga2::updateArchivePopulation() {
    if (currentArchiveSize + populationSize <= archiveSize) {
        for (register unsigned int j = currentArchiveSize, i = 0; i < populationSize; i++, j++) {
            archivePopulation[j] = offspringPopulation[i];
        }
        currentArchiveSize += populationSize;
    } else {
        for (register unsigned int i = 0; i < currentArchiveSize; i++) {
            combinedPopulation[i] = archivePopulation[i];
        }
        for (register unsigned int i = 0; i < populationSize; i++) {
            combinedPopulation[currentArchiveSize + i] = offspringPopulation[i];
        }
        fillBestPopulation(combinedPopulation, currentArchiveSize + populationSize, archivePopulation, archiveSize);
        currentArchiveSize = archiveSize;
    }
    for (register unsigned int i = 0; i < currentArchiveSize; i++) {
        archivePopulation[i].diversity = 0.0;
    }
    return;
}

double Amga2::calcEuclideanDistance(const Individual &a, const Individual &b) const {
    register double distance = 0.0;
    register double temp;
    for (register unsigned int i = 0; i < numObj; i++) {
        temp = (a.normalObj[i] - b.normalObj[i]);
        distance += temp*temp;
    }
    return (distance / numObj);
}

int Amga2::checkDomination(const Individual& a, const Individual& b) const {
    bool flag1 = false;
    bool flag2 = false;
    if (a.constrViolation < 0.0 && b.constrViolation < 0.0) {
        if (a.constrViolation > b.constrViolation) {
            return (1);
        }
        if (a.constrViolation < b.constrViolation) {
            return (-1);
        } else {
            return (0);
        }
    }
    if (a.constrViolation < 0.0 && b.constrViolation == 0.0) {
        return (-1);
    }
    if (a.constrViolation == 0.0 && b.constrViolation < 0.0) {
        return (1);
    }
    for (register unsigned int i = 0; i < numObj; i++) {
        if (a.normalObj[i] < b.normalObj[i]) {
            flag1 = true;
        } else if (a.normalObj[i] > b.normalObj[i]) {
            flag2 = true;
        }
    }
    if (flag1 == true && flag2 == false) {
        return (1);
    }
    if (flag1 == false && flag2 == true) {
        return (-1);
    }
    return (0);
}

void Amga2::quickSortDiversity(const vector<Individual>& population, unsigned int* diverseArray, int left, int right) const {
    register int index = 0;
    register int temp = 0;
    register int i = 0;
    register int j = 0;
    register double pivot = 0.0;
    if (left < right) {
        pivot = population[diverseArray[right]].diversity;
        i = left - 1;
        for (j = left; j < right; j++) {
            if (population[diverseArray[j]].diversity <= pivot) {
                i += 1;
                temp = diverseArray[j];
                diverseArray[j] = diverseArray[i];
                diverseArray[i] = temp;
            }
        }
        index = i + 1;
        temp = diverseArray[index];
        diverseArray[index] = diverseArray[right];
        diverseArray[right] = temp;
        quickSortDiversity(population, diverseArray, left, index - 1);
        quickSortDiversity(population, diverseArray, index + 1, right);
    }
    return;
}

void Amga2::quickSortObj(const vector<Individual>& population, unsigned int objCount, unsigned int* objArray, int left, int right) const {
    register int index = 0;
    register int temp = 0;
    register int i = 0;
    register int j = 0;
    register double pivot = 0.0;
    if (left < right) {
        pivot = population[objArray[right]].normalObj[objCount];
        i = left - 1;
        for (j = left; j < right; j++) {
            if (population[objArray[j]].normalObj[objCount] <= pivot) {
                i += 1;
                temp = objArray[j];
                objArray[j] = objArray[i];
                objArray[i] = temp;
            }
        }
        index = i + 1;
        temp = objArray[index];
        objArray[index] = objArray[right];
        objArray[right] = temp;
        quickSortObj(population, objCount, objArray, left, index - 1);
        quickSortObj(population, objCount, objArray, index + 1, right);
    }
    return;
}

void Amga2::updateObjectiveConstraintRange() {
    for (register unsigned int i = 0; i < numObj; i++) {
        minObj[i] = archivePopulation[0].obj[i];
        maxObj[i] = archivePopulation[0].obj[i];
        for (register unsigned int j = 1; j < currentArchiveSize; j++) {
            if (archivePopulation[j].obj[i] < minObj[i]) {
                minObj[i] = archivePopulation[j].obj[i];
            }
            if (archivePopulation[j].obj[i] > maxObj[i]) {
                maxObj[i] = archivePopulation[j].obj[i];
            }
        }
    }
    if (numConstr > 0) {
        for (register unsigned int i = 0; i < numConstr; i++) {
            //minConstr[i] = archivePopulation[0].constr[i];
            //maxConstr[i] = archivePopulation[0].constr[i];
            for (register unsigned int j = 0; j < currentArchiveSize; j++) {
                if (archivePopulation[j].constr[i] < minConstr[i]) {
                    minConstr[i] = archivePopulation[j].constr[i];
                }
                if (archivePopulation[j].constr[i] > maxConstr[i]) {
                    maxConstr[i] = archivePopulation[j].constr[i];
                }
            }
        }
    }
    for (register unsigned int i = 0; i < currentArchiveSize; i++) {
        archivePopulation[i].rank = 0;
        archivePopulation[i].diversity = 0.0;
    }
    return;
}

void Amga2::writeParetoFile(const string& paretoFile) const {
    if (currentArchiveSize == 0) {
        cout << "\n NO FEASIBLE SOLUTION FOUND, HENCE NO OUTPUT FILE \n";
    } else {
        ofstream out(paretoFile.c_str(), ofstream::out);
        if (!out) {
            cerr << "\n Could not open output file " << paretoFile << " for writing, no pareto output. \n";
            return;
        }
        out << left << setw(13) << "#Counter";
        for (register unsigned int j = 0; j < numVar; j++) {
            out << "Variable_" << left << setw(9) << (j + 1);
        }
        for (register unsigned int j = 0; j < numObj; j++) {
            out << "Objective_" << left << setw(8) << (j + 1);
        }
        if (numConstr > 0) {
            for (register unsigned int j = 0; j < numConstr; j++) {
                out << "Constraint_" << left << setw(7) << (j + 1);
            }
        }
        out << endl;
        for (register unsigned int i = 0; i < currentArchiveSize; i++) {
            out << left << setw(6) << combinedPopulation[i].evalCounter;
            for (register unsigned int j = 0; j < numVar; j++) {
                out << right << setw(18) << scientific << combinedPopulation[i].var[j];
            }
            for (register unsigned int j = 0; j < numObj; j++) {
                out << right << setw(18) << scientific << combinedPopulation[i].obj[j];
            }
            if (numConstr > 0) {
                out << " ";
                for (register unsigned int j = 0; j < numConstr; j++) {
                    out << right << setw(18) << scientific << combinedPopulation[i].constr[j];
                }
            }
            out << endl;
        }
        out.close();
    }
    return;
}

void Amga2::writeParetoObjectives(const std::string& fileName) const {
    if (currentArchiveSize == 0) {
        cout << "\n NO FEASIBLE SOLUTION FOUND, HENCE NO OUTPUT FILE \n";
    } else {
        ofstream out(fileName.c_str(), ofstream::out);
        if (!out) {
            cerr << "\n Could not open output file " << fileName << " for writing, exiting \n";
            return;
        }
        for (register unsigned int i = 0; i < currentArchiveSize; i++) {
            for (register unsigned int j = 0; j < numObj; j++) {
                out << scientific << combinedPopulation[i].obj[j] << "\t";
            }
            out << endl;
        }
        out.close();

    }
}

void Amga2::printObjectives(const vector<Individual>& population, unsigned int size, const string& fileName) const {
    ofstream out(fileName.c_str(), ofstream::out);
    if (!out) {
        cerr << "\n Could not open output file " << fileName << " for writing, exiting \n";
        return;
    }
    for (register unsigned int i = 0; i < size; i++) {
        for (register unsigned int j = 0; j < numObj; j++) {
            out << scientific << population[i].obj[j] << "\t";
        }
        out << endl;
    }
    out.close();
    return;
}

ostream& operator<<(ostream& out, const Amga2& rhs) {
    out << "\n The simulation parameters are as follows:";
    out << "\n Maximum allowed size for the archive     = " << rhs.archiveSize;
    out << "\n Size of initial population               = " << rhs.initialSize;
    out << "\n Size of working population               = " << rhs.populationSize;
    out << "\n Maximum number of function evaluations   = " << rhs.numEvaluations;
    out << "\n Desired number of elite solutions        = " << rhs.eliteSize;
    out << "\n Probability of crossover (CR)            = " << rhs.probCross;
    out << "\n Probability of mutation (P)              = " << rhs.probMut;
    out << "\n Index for crossover (F)                  = " << rhs.etaCross;
    out << "\n Index for mutation (eta)                 = " << rhs.etaMut;
    out << "\n Random seed for the simulation           = " << rhs.randGen.getSeed();
    out << "\n Debug mode for the simulation            = " << rhs.debugMode;
    out << "\n Number of function evaluations exhausted = " << rhs.evaluationCounter;
    out << "\n";
    return (out);
}
