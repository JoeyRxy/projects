#include <iostream>
#include <cstdlib>

#include "AbstractTestProblem.h"
#include "ProblemFactory.h"
#include "Amga2.h"

using namespace std;

int main(int argc, const char* const argv[]) {

    //Check command line arguments
    if (argc != 7) {
        cerr << "\n Usage: " << argv[0] << " problemName numEvaluations, desiredOptimSetSize, randomSeed debugMode paretoFile" << "\n";
        printProblemList(cerr);
        cerr << "\n numEvaluations      --> Number of function evaluations";
        cerr << "\n desiredOptimSetSize --> Number of solutions desired at the end of optimization";
        cerr << "\n randomSeed          --> Random seed for optimization in the range (0, 1)";
        cerr << "\n debugMode           --> 0 or 1, 1 implies real-time optimization plot";
        cerr << "\n paretoFile          --> file in which to store the results of optimization";
        cerr << "\n";
        return 0;
    }
    string name(argv[1]);

    //Fetch the problem with the given name
    const AbstractTestProblem* problem = getTestProblem(name);
    if (problem == NULL) {
        cerr << "\n Exiting, the problem with name " << name << " could not be found.\n";
        printProblemList(cerr);
        return 0;
    }
    cout << *problem;

    unsigned int numEvaluations = static_cast<unsigned int> (atoi(argv[2]));
    unsigned int desiredOptimSetSize = static_cast<unsigned int> (atoi(argv[3]));
    double randomSeed = static_cast<double> (atof(argv[4]));
    unsigned int debugMode = static_cast<unsigned int> (atoi(argv[5]));

    Amga2 algorithm(*problem, problem->getNumVar(), problem->getNumObj(), problem->getNumConstr(), problem->getMinVar(), problem->getMaxVar(), numEvaluations, desiredOptimSetSize, randomSeed, debugMode);
    cout << algorithm;
    algorithm.evolvePopulation();
    algorithm.writeParetoFile(argv[6]);
	//algorithm.writeParetoObjectives(argv[6]);

    delete(problem);

    cout << "\n";
    return 0;
}
