#ifndef TEST_PROBLEM_KUR
#define	TEST_PROBLEM_KUR 1

#include <cmath>

#include "AbstractTestProblem.h"

class KUR : public AbstractTestProblem {
public:

    KUR() {
        name = "KUR";
        numVar = 3;
        numObj = 2;
        numConstr = 0;
        minVar = new double[numVar];
        maxVar = new double[numVar];
        for (register unsigned int i = 0; i < numVar; i++) {
            minVar[i] = -5.0;
            maxVar[i] = 5.0;
        }
    }

    ~KUR() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        register double res1, res2;
        res1 = -0.2 * sqrt((var[0] * var[0]) + (var[1] * var[1]));
        res2 = -0.2 * sqrt((var[1] * var[1]) + (var[2] * var[2]));
        obj[0] = -10.0 * (exp(res1) + exp(res2));
        obj[1] = 0.0;
        for (int i = 0; i < 3; i++) {
            obj[1] += pow(fabs(var[i]), 0.8) + 5.0 * sin(pow(var[i], 3.0));
        }
        return;
    }

};

#endif
