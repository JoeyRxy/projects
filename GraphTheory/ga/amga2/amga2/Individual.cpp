#include <iostream>
#include <cstdlib>
#include <cmath>

#include "Individual.h"
#include "Amga2.h"

using namespace std;

Individual::Individual(const Amga2& _algo, const AbstractTestProblem& _evaluator) : algo(_algo), evaluator(_evaluator) {
    constrViolation = 0.0;
    evalCounter = 0;
    rank = 0;
    diversity = 0.0;
    var = new double[algo.numVar];
    normalVar = new double[algo.numVar];
    obj = new double[algo.numObj];
    normalObj = new double[algo.numObj];
    for (register unsigned int i = 0; i < algo.numVar; i++) {
        var[i] = normalVar[i] = 0.0;
    }
    for (register unsigned int i = 0; i < algo.numObj; i++) {
        obj[i] = normalObj[i] = 0.0;
    }
    if (algo.numConstr > 0) {
        constr = new double[algo.numConstr];
        normalConstr = new double[algo.numConstr];
        for (register unsigned int i = 0; i < algo.numConstr; i++) {
            constr[i] = normalConstr[i] = 0.0;
        }
    }
}

Individual::Individual(const Individual& rhs) : algo(rhs.algo), evaluator(rhs.evaluator) {
    var = new double[algo.numVar];
    normalVar = new double[algo.numVar];
    obj = new double[algo.numObj];
    normalObj = new double[algo.numObj];
    if (algo.numConstr > 0) {
        constr = new double[algo.numConstr];
        normalConstr = new double[algo.numConstr];
    }
    copy(rhs);
}

Individual::~Individual() {
    delete[](var);
    delete[](normalVar);
    delete[](obj);
    delete[](normalObj);
    if (algo.numConstr > 0) {
        delete[](constr);
        delete[](normalConstr);
    }
}

const Individual& Individual::operator=(const Individual& rhs) {
    if (this == &rhs) {
        return (*this);
    }
    copy(rhs);
    return (*this);
}

void Individual::evaluate() {
    for (register unsigned int i = 0; i < algo.numVar; i++) {
        var[i] = (algo.maxVar[i] - algo.minVar[i]) * normalVar[i] + 2.0 * algo.minVar[i] - algo.maxVar[i];
        if (var[i] < algo.minVar[i]) {
            var[i] = algo.minVar[i];
        }
        if (var[i] > algo.maxVar[i]) {
            var[i] = algo.maxVar[i];
        }
    }
    evaluator.evaluate(var, obj, constr);
    for (register unsigned int i = 0; i < algo.numObj; i++) {
        if (obj[i] != obj[i]) {
            cerr << "\n Nan returned for the objective " << i + 1 << ", cannot proceed.";
            cerr << "\n For your reference, the optimization variables are as follows : ";
            for (register unsigned int j = 0; j < algo.numVar; j++) {
                cerr << "\n Variable " << j + 1 << " = " << scientific << var[j];
            }
            cerr << "\n Check the value of your objective for this solution, exiting \n";
            exit(1);
        }
    }
    if (algo.numConstr > 0) {
        for (register unsigned int i = 0; i < algo.numConstr; i++) {
            if (constr[i] != constr[i]) {
                cerr << "\n Nan returned for the constraint " << i + 1 << ", cannot proceed.";
                cerr << "\n For your reference, the optimization variables are as follows : ";
                for (register unsigned int j = 0; j < algo.numVar; j++) {
                    cerr << "\n Variable " << j + 1 << " = " << scientific << var[j];
                }
                cerr << "\n Check the value of your constraint for this solution, exiting \n";
                exit(1);
            }
        }
    }
    rank = 0;
    diversity = 0.0;
    evalCounter = algo.evaluationCounter;
    return;
}

void Individual::normalizeValues() {
    for (register unsigned int i = 0; i < algo.numObj; i++) {
        if (algo.maxObj[i] > algo.minObj[i]) {
            normalObj[i] = (obj[i] + algo.maxObj[i] - 2.0 * algo.minObj[i]) / (algo.maxObj[i] - algo.minObj[i]);
        } else {
            normalObj[i] = 1.0;
            cerr << "\n Warning, there seems to be no change in the value of objective " << i + 1 << ", the performance of the algorithm might suffer \n";
        }
    }
    constrViolation = 0.0;
    if (algo.numConstr > 0) {
        for (register unsigned int i = 0; i < algo.numConstr; i++) {
            if (algo.maxConstr[i] > algo.minConstr[i]) {
                register double d1 = fabs(algo.minConstr[i]);
                register double d2 = fabs(algo.maxConstr[i]);
                register double d = d1 > d2 ? d1 : d2;
                normalConstr[i] = constr[i] / d;
            } else {
                cerr << "\n Warning, there seems to be no change in the value of constraint " << i + 1 << ", the performance of the algorithm might suffer \n";
                if (constr[i] < 0.0) {
                    normalConstr[i] = -1.0;
                } else {
                    normalConstr[i] = 1.0;
                }
            }
        }
        for (register unsigned int i = 0; i < algo.numConstr; i++) {
            if (constr[i] < 0.0) {
                constrViolation += normalConstr[i];
            }
        }
    }
    return;
}

void Individual::copy(const Individual& rhs) {
    for (register unsigned int i = 0; i < algo.numVar; i++) {
        var[i] = rhs.var[i];
        normalVar[i] = rhs.normalVar[i];
    }
    for (register unsigned int i = 0; i < algo.numObj; i++) {
        obj[i] = rhs.obj[i];
        normalObj[i] = rhs.normalObj[i];
    }
    if (algo.numConstr > 0) {
        for (register unsigned int i = 0; i < algo.numConstr; i++) {
            constr[i] = rhs.constr[i];
            normalConstr[i] = rhs.normalConstr[i];
        }
    }
    constrViolation = rhs.constrViolation;
    evalCounter = rhs.evalCounter;
    rank = rhs.rank;
    diversity = rhs.diversity;
    return;
}
