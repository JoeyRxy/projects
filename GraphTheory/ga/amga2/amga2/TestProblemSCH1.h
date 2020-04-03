#ifndef TEST_PROBLEM_SCH1
#define	TEST_PROBLEM_SCH1 1

#include <cmath>

#include "AbstractTestProblem.h"

class SCH1 : public AbstractTestProblem {
public:

    SCH1() {
        name = "SCH1";
        numVar = 1;
        numObj = 2;
        numConstr = 0;
        minVar = new double[numVar];
        maxVar = new double[numVar];

        minVar[0] = 0.0;
        maxVar[0] = 10.0;
    }

    ~SCH1() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        obj[0] = var[0] * var[0];
        obj[1] = (var[0] - 2.0)*(var[0] - 2.0);
        return;
    }

};

#endif
