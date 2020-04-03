#ifndef TEST_PROBLEM_FON
#define	TEST_PROBLEM_FON 1

#include <cmath>

#include "AbstractTestProblem.h"

class FON : public AbstractTestProblem {
public:

    FON() {
        name = "FON";
        numVar = 10;
        numObj = 2;
        numConstr = 0;
        minVar = new double[numVar];
        maxVar = new double[numVar];
        for (register unsigned int i = 0; i < numVar; i++) {
            minVar[i] = -4.0;
            maxVar[i] = 4.0;
        }
    }

    ~FON() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        register double exp1 = 0.0;
        register double exp2 = 0.0;
        register double temp = 1.0 / sqrt(10.0);
        for (int i = 0; i < 10; i++) {
            exp1 += pow(var[i] - temp, 2.0);
            exp2 += pow(var[i] + temp, 2.0);
        }
        obj[0] = 1.0 - exp(-exp1);
        obj[1] = 1.0 - exp(-exp2);
        return;
    }

};

#endif
