#ifndef TEST_PROBLEM_ZDT6
#define	TEST_PROBLEM_ZDT6 1

#include <cmath>

#include "AbstractTestProblem.h"

class ZDT6 : public AbstractTestProblem {
public:

    ZDT6() {

        name = "ZDT6";

        numVar = 10;
        numObj = 2;
        numConstr = 0;
        minVar = new double[numVar];
        maxVar = new double[numVar];
        for (register unsigned int i = 0; i < numVar; i++) {
            minVar[i] = 0.0;
            maxVar[i] = 1.0;
        }
    }

    ~ZDT6() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        register double f1, f2, g, h;
        f1 = 1.0 - (exp(-4.0 * var[0])) * pow((sin(6.0 * PI * var[0])), 6.0);
        g = 0.0;
        for (int i = 1; i < 10; i++) {
            g += var[i];
        }
        g = g / 9.0;
        g = pow(g, 0.25);
        g = 1.0 + 9.0 * g;
        h = 1.0 - pow((f1 / g), 2.0);
        f2 = g*h;
        obj[0] = f1;
        obj[1] = f2;
        return;
    }

};

#endif
