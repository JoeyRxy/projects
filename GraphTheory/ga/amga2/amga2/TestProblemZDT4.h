#ifndef TEST_PROBLEM_ZDT4
#define	TEST_PROBLEM_ZDT4 1

#include <cmath>

#include "AbstractTestProblem.h"

class ZDT4 : public AbstractTestProblem {
public:

    ZDT4() {

        name = "ZDT4";

        numVar = 10;
        numObj = 2;
        numConstr = 0;
        minVar = new double[numVar];
        maxVar = new double[numVar];
        minVar[0] = 0.0;
        maxVar[0] = 1.0;
        for (register unsigned int i = 1; i < numVar; i++) {
            minVar[i] = -5.0;
            maxVar[i] = 5.0;
        }
    }

    ~ZDT4() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        register double f1, f2, g, h;
        f1 = var[0];
        g = 91.0;
        for (int i = 1; i < 10; i++) {
            g += var[i] * var[i] - 10.0 * cos(4.0 * PI * var[i]);
        }
        h = 1.0 - sqrt(f1 / g);
        f2 = g*h;
        obj[0] = f1;
        obj[1] = f2;
        return;
    }

};

#endif
