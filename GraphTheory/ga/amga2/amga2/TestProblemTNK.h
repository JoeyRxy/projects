#ifndef TEST_PROBLEM_TNK
#define	TEST_PROBLEM_TNK 1

#include <cmath>

#include "AbstractTestProblem.h"

class TNK : public AbstractTestProblem {
public:

    TNK() {
        name = "TNK";

        numVar = 2;
        numObj = 2;
        numConstr = 2;
        minVar = new double[numVar];
        maxVar = new double[numVar];

        minVar[0] = 0.0;
        maxVar[0] = PI;

        minVar[1] = 0.0;
        maxVar[1] = PI;
    }

    ~TNK() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        obj[0] = var[0];
        obj[1] = var[1];
        if (fabs(var[1]) < 1E-6) {
            constr[0] = var[0] * var[0] - 1.1;
        } else {
            constr[0] = var[0] * var[0] + var[1] * var[1] - 1.0 - 0.1 * cos(16.0 * atan(var[0] / var[1]));
        }
        constr[1] = 1.0 - 2.0 * (pow(var[0] - 0.5, 2.0) + pow(var[1] - 0.5, 2.0));
        return;
    }

};

#endif
