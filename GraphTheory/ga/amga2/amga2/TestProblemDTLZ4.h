#ifndef DTLZ4_H
#define	DTLZ4_H 1

#include "AbstractTestProblem.h"

class DTLZ4 : public AbstractTestProblem {
public:

    DTLZ4() {
        name = "DTLZ4";
        numVar = 12;
        numObj = 3;
        numConstr = 0;
        minVar = new double[numVar];
        maxVar = new double[numVar];
        for (register unsigned int i = 0; i < numVar; i++) {
            minVar[i] = 0.0;
            maxVar[i] = 1.0;
        }
    }

    ~DTLZ4() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        register double g = 0.0;
        register double alpha = 100.0;
        for (register unsigned int i = 2; i < 12; i++) {
            g += pow((var[i] - 0.5), 2.0);
        }
        obj[0] = (1.0 + g) * cos(pow(var[0], alpha) * PI / 2.0) * cos(pow(var[1], alpha) * PI / 2.0);
        obj[1] = (1.0 + g) * cos(pow(var[0], alpha) * PI / 2.0) * sin(pow(var[1], alpha) * PI / 2.0);
        obj[2] = (1.0 + g) * sin(pow(var[0], alpha) * PI / 2.0);
        return;
    }

};

#endif
