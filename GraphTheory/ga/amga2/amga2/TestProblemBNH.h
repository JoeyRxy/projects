#ifndef TEST_PROBLEM_BNH
#define	TEST_PROBLEM_BNH 1

#include <cmath>

#include "AbstractTestProblem.h"

class BNH : public AbstractTestProblem {
public:

    BNH() {
        name = "BNH";
        numVar = 2;
        numObj = 2;
        numConstr = 2;
        minVar = new double[numVar];
        maxVar = new double[numVar];

        minVar[0] = 0.0;
        maxVar[0] = 5.0;

        minVar[1] = 0.0;
        maxVar[1] = 3.0;
    }

    ~BNH() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        obj[0] = 4.0 * (var[0] * var[0] + var[1] * var[1]);
        obj[1] = pow((var[0] - 5.0), 2.0) + pow((var[1] - 5.0), 2.0);
        constr[0] = 1.0 - (pow((var[0] - 5.0), 2.0) + var[1] * var[1]) / 25.0;
        constr[1] = (pow((var[0] - 8.0), 2.0) + pow((var[1] + 3.0), 2.0)) / 7.7 - 1.0;
        return;
    }

};

#endif
