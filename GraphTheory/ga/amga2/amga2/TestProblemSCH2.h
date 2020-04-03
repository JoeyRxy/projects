#ifndef TEST_PROBLEM_SCH2
#define	TEST_PROBLEM_SCH2 1

#include <cmath>

#include "AbstractTestProblem.h"

class SCH2 : public AbstractTestProblem {
public:

    SCH2() {
        name = "SCH2";
        numVar = 1;
        numObj = 2;
        numConstr = 0;
        minVar = new double[numVar];
        maxVar = new double[numVar];

        minVar[0] = -5.0;
        maxVar[0] = 10.0;
    }

    ~SCH2() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        if (var[0] <= 1.0) {
            obj[0] = -var[0];
        } else if (var[0] <= 3.0) {
            obj[0] = var[0] - 2.0;
        } else if (var[0] <= 4.0) {
            obj[0] = 4.0 - var[0];
        } else {
            obj[0] = var[0] - 4.0;
        }
        obj[1] = pow((var[0] - 5.0), 2.0);
        return;
    }

};

#endif
