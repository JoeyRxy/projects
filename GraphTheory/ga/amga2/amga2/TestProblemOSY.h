#ifndef TEST_PROBLEM_OSY
#define	TEST_PROBLEM_OSY 1

#include <cmath>

#include "AbstractTestProblem.h"

class OSY : public AbstractTestProblem {
public:

    OSY() {
        name = "OSY";

        numVar = 6;
        numObj = 2;
        numConstr = 6;
        minVar = new double[numVar];
        maxVar = new double[numVar];

        minVar[0] = 0.0;
        maxVar[0] = 10.0;

        minVar[1] = 0.0;
        maxVar[1] = 10.0;

        minVar[2] = 1.0;
        maxVar[2] = 5.0;

        minVar[3] = 0.0;
        maxVar[3] = 6.0;

        minVar[4] = 1.0;
        maxVar[4] = 5.0;

        minVar[5] = 0.0;
        maxVar[5] = 10.0;
    }

    ~OSY() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        obj[0] = -(25.0 * pow(var[0] - 2.0, 2.0) + pow(var[1] - 2.0, 2.0) + pow(var[2] - 1.0, 2.0) + pow(var[3] - 4.0, 2.0) + pow(var[4] - 1.0, 2.0));
        obj[1] = var[0] * var[0] + var[1] * var[1] + var[2] * var[2] + var[3] * var[3] + var[4] * var[4] + var[5] * var[5];
        constr[0] = (var[0] + var[1]) / 2.0 - 1.0;
        constr[1] = 1.0 - (var[0] + var[1]) / 6.0;
        constr[2] = 1.0 + (var[0] - var[1]) / 2.0;
        constr[3] = 1.0 - (var[0] - 3.0 * var[1]) / 2.0;
        constr[4] = 1.0 - (pow(var[2] - 3.0, 2.0) + var[3]) / 4.0;
        constr[5] = (pow(var[4] - 3.0, 2.0) + var[5]) / 4.0 - 1.0;
        return;
    }

};

#endif
