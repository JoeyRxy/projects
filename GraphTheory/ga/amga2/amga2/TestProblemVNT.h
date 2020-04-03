#ifndef TEST_PROBLEM_VNT
#define	TEST_PROBLEM_VNT 1

#include <cmath>

#include "AbstractTestProblem.h"

class VNT : public AbstractTestProblem {
public:

    VNT() {
        name = "VNT";
        numVar = 2;
        numObj = 3;
        numConstr = 0;
        minVar = new double[numVar];
        maxVar = new double[numVar];

        minVar[0] = -3.0;
        maxVar[0] = 3.0;

        minVar[1] = -3.0;
        maxVar[1] = 3.0;
    }

    ~VNT() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        obj[0] = 0.5 * (var[0] * var[0] + var[1] * var[1]) + sin(var[0] * var[0] + var[1] * var[1]);
        obj[1] = (pow((3.0 * var[0] - 2.0 * var[1] + 4.0), 2.0)) / 8.0 + (pow((var[0] - var[1] + 1.0), 2.0)) / 27.0 + 15.0;
        obj[2] = 1.0 / (var[0] * var[0] + var[1] * var[1] + 1.0) - 1.1 * exp(-(var[0] * var[0] + var[1] * var[1]));
        return;
    }

};

#endif
