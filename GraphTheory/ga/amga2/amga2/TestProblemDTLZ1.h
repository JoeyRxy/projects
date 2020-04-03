#ifndef DTLZ1_H
#define	DTLZ1_H 1

#include "AbstractTestProblem.h"

class DTLZ1 : public AbstractTestProblem {
public:

    DTLZ1() {
        name = "DTLZ1";
        numVar = 7;
        numObj = 3;
        numConstr = 0;
        minVar = new double[numVar];
        maxVar = new double[numVar];
        for (register unsigned int i = 0; i < numVar; i++) {
            minVar[i] = 0.0;
            maxVar[i] = 1.0;
        }
    }

    ~DTLZ1() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        register double g = 0.0;
        for (register unsigned int i = 2; i < 7; i++) {
            g += pow((var[i] - 0.5), 2.0) - cos(20.0 * PI * (var[i] - 0.5));
        }
        g += 5.0;
        g = g * 100.0;
        obj[0] = 0.5 * var[0] * var[1]*(1.0 + g);
        obj[1] = 0.5 * var[0]*(1.0 - var[1])*(1.0 + g);
        obj[2] = 0.5 * (1.0 - var[0])*(1.0 + g);
        return;
    }

};

#endif
