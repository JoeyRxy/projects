#ifndef TEST_PROBLEM_ZDT3
#define	TEST_PROBLEM_ZDT3 1

#include <cmath>

#include "AbstractTestProblem.h"

class ZDT3 : public AbstractTestProblem {
public:

    ZDT3() {
        name = "ZDT3";

        numVar = 30;
        numObj = 2;
        numConstr = 0;
        minVar = new double[numVar];
        maxVar = new double[numVar];
        for (register unsigned int i = 0; i < numVar; i++) {
            minVar[i] = 0.0;
            maxVar[i] = 1.0;
        }
    }

    ~ZDT3() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        register double f1, f2, g, h;
        f1 = var[0];
        g = 0.0;
        for (int i = 1; i < 30; i++) {
            g += var[i];
        }
        g = 9.0 * g / 29.0;
        g += 1.0;
        h = 1.0 - sqrt(f1 / g) - (f1 / g) * sin(10.0 * PI * f1);
        f2 = g*h;
        obj[0] = f1;
        obj[1] = f2;
        return;
    }

};

#endif
