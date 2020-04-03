#ifndef TEST_PROBLEM_SRN
#define	TEST_PROBLEM_SRN 1

#include <cmath>

#include "AbstractTestProblem.h"

class SRN : public AbstractTestProblem {
public:

    SRN() {
        name = "SRN";
        numVar = 2;
        numObj = 2;
        numConstr = 2;
        minVar = new double[numVar];
        maxVar = new double[numVar];

        minVar[0] = -20.0;
        maxVar[0] = 20.0;

        minVar[1] = -20.0;
        maxVar[1] = 20.0;
    }

    ~SRN() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        obj[0] = 2.0 + pow((var[0] - 2.0), 2.0) + pow((var[1] - 1.0), 2.0);
        obj[1] = 9.0 * var[0] - pow((var[1] - 1.0), 2.0);
        constr[0] = 1.0 - (pow(var[0], 2.0) + pow(var[1], 2.0)) / 225.0;
        constr[1] = 3.0 * var[1] / 10.0 - var[0] / 10.0 - 1.0;
        return;
    }

};

#endif
