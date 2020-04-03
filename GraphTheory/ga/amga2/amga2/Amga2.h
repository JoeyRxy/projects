#ifndef AMGA2_H
#define AMGA2_H 1

#include "RandomNumberGenerator.h"
#include "Individual.h"
#include "AbstractTestProblem.h"

#include <vector>
#include <string>
#include <iostream>
#include <list>

std::ostream& operator<<(std::ostream&, const Amga2&);

class Amga2 {
    friend std::ostream& operator<<(std::ostream&, const Amga2&);
    friend class Individual;

public:
    Amga2(const AbstractTestProblem &_evaluator, unsigned int _numVar, unsigned int _numObj, unsigned int _numConstr, const double *_minVar, const double *_maxVar, unsigned int _numEvaluations, unsigned int _eliteSize, double _randomSeed, unsigned int _debugMode);
    void evolvePopulation();
    void writeParetoFile(const std::string&) const;
    void writeParetoObjectives(const std::string&) const;
    ~Amga2();

private:
    void assignDiversityMetric(std::vector<Individual> &population, std::list<unsigned int> &elite);
    void assignInfiniteDiversity(std::vector<Individual> &population, std::list<unsigned int> &elite);
    void createInitialPopulation();
    void createParentPopulation();
    void createOffspringPopulation();
    void crossIndividuals(const Individual &parent, const Individual& r1, const Individual &r2, const Individual &r3, Individual &offspring);
    void finalizePopulation();
    void evaluatePopulation(std::vector<Individual>& population, unsigned int size);
    void normalizePopulation(std::vector<Individual>& population, unsigned int size);
    void extractBestRank(const std::vector<Individual>& population, std::list<unsigned int>& pool, std::list<unsigned int>& elite);
    void extractDistinctIndividuals(std::vector<Individual> &population, std::list<unsigned int> &elite, std::list<unsigned int> &distinct);
    void extractENNSPopulation(const std::vector<Individual> &population, std::list<unsigned int> &pool, std::list<unsigned int> &elite, unsigned int desiredEliteSize);
    void extractExtremeSolutions(const std::vector<Individual> &population, std::list<unsigned int> &pool, std::list<unsigned int> &extreme);
    void fillBestPopulation(std::vector<Individual> &mixedPopulation, unsigned int mixedLength, std::vector<Individual> &population, unsigned int desiredBestSize);
    void fillDiversePopulation(std::vector<Individual> &mixedPopulation, std::list<unsigned int> &pool, std::vector<Individual> &population, unsigned int startLocation, unsigned int desiredSize);
    void mutateOffspringPopulation();
    void updateArchivePopulation();
    void quickSortObj(const std::vector<Individual>& population, unsigned int objCount, unsigned int* objArray, int left, int right) const;
    void quickSortDiversity(const std::vector<Individual>& population, unsigned int* diversityArray, int left, int right) const;
    void updateObjectiveConstraintRange();
    void printObjectives(const std::vector<Individual>& population, unsigned int size, const std::string& fileName) const;
    double calcEuclideanDistance(const Individual &a, const Individual &b) const;
    int checkDomination(const Individual& a, const Individual& b) const;

private:
    unsigned int numVar;
    unsigned int numObj;
    unsigned int numConstr;
    double *minVar;
    double *maxVar;
    double *minObj;
    double *maxObj;
    double *minConstr;
    double *maxConstr;
    unsigned int archiveSize;
    unsigned int initialSize;
    unsigned int populationSize;
    unsigned int eliteSize;
    unsigned int numEvaluations;
    double probCross;
    double probMut;
    double etaCross;
    double etaMut;
    unsigned int debugMode;
    unsigned int evaluationCounter;
    static const double INF;
    static const double EPS;
    const AbstractTestProblem &evaluator;
    RandomNumberGenerator randGen;
    std::vector<Individual> offspringPopulation;
    std::vector<Individual> parentPopulation;
    std::vector<Individual> archivePopulation;
    std::vector<Individual> combinedPopulation;
    unsigned int currentArchiveSize;
    bool isEvolutionEnded;

    class DistanceMatrix {
    public:
        int index1;
        int index2;
        double distance;

        DistanceMatrix() : index1(-1), index2(-1), distance(0.0) {
        }

        DistanceMatrix(int m_index1, int m_index2, double m_distance) : index1(m_index1), index2(m_index2), distance(m_distance) {
        }

        DistanceMatrix(const DistanceMatrix& rhs) : index1(rhs.index1), index2(rhs.index2), distance(rhs.distance) {
        }

        ~DistanceMatrix() {
        }

        const DistanceMatrix & operator=(const DistanceMatrix& rhs) {
            if (this == &rhs) {
                return (*this);
            }
            distance = rhs.distance;
            index1 = rhs.index1;
            index2 = rhs.index2;
            return (*this);
        }

        bool operator<(const DistanceMatrix& o) const {
            if (distance < o.distance) {
                return (true);
            } else if (distance > o.distance) {
                return (false);
            } else if (index1 < o.index1) {
                return (true);
            } else if (index1 > o.index1) {
                return (false);
            } else if (index2 < o.index2) {
                return (true);
            } else if (index2 > o.index2) {
                return (false);
            } else {
                return (false);
            }
        }
    };
};

#endif
