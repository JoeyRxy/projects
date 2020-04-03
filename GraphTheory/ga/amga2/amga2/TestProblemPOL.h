#ifndef TEST_PROBLEM_POL
#define	TEST_PROBLEM_POL 1

#include <cmath>

#include "AbstractTestProblem.h"

class POL : public AbstractTestProblem {
public:

    POL() {
        name = "POL";
        numVar = 2;
        numObj = 2;
        numConstr = 0;
        minVar = new double[numVar];
        maxVar = new double[numVar];

        minVar[0] = -PI;
        maxVar[0] = PI;

        minVar[1] = -PI;
        maxVar[1] = PI;
    }

    ~POL() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        register double a1, a2, b1, b2;
        a1 = 0.5 * sin(1.0) - 2.0 * cos(1.0) + sin(2.0) - 1.5 * cos(2.0);
        a2 = 1.5 * sin(1.0) - cos(1.0) + 2.0 * sin(2.0) - 0.5 * cos(2.0);
        b1 = 0.5 * sin(var[0]) - 2.0 * cos(var[0]) + sin(var[1]) - 1.5 * cos(var[1]);
        b2 = 1.5 * sin(var[0]) - cos(var[0]) + 2.0 * sin(var[1]) - 0.5 * cos(var[1]);
        obj[0] = 1.0 + pow((a1 - b1), 2.0) + pow((a2 - b2), 2.0);
        obj[1] = pow((var[0] + 3.0), 2.0) + pow((var[1] + 1.0), 2.0);
        return;
    }

};

#endif
