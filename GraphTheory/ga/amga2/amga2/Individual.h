#ifndef INDIVIDUAL_H
#define INDIVIDUAL_H 1

#include <ostream>

#include "AbstractTestProblem.h"

class Amga2;

class Individual {
    friend class Amga2;

public:
    Individual(const Amga2&, const AbstractTestProblem&);
    Individual(const Individual&);
    const Individual & operator=(const Individual&);
    void evaluate();
    void normalizeValues();
    ~Individual();

private:
    void copy(const Individual&);

private:
    const Amga2& algo;
    const AbstractTestProblem& evaluator;
    double* var;
    double* obj;
    double* constr;
    double* normalVar;
    double* normalObj;
    double* normalConstr;
    double constrViolation;
    unsigned int evalCounter;
    unsigned int rank;
    double diversity;
};

#endif
