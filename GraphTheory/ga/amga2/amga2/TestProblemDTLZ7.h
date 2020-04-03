#ifndef DTLZ7_H
#define	DTLZ7_H 1

#include "AbstractTestProblem.h"

class DTLZ7 : public AbstractTestProblem {
public:

    DTLZ7() {
        name = "DTLZ7";
        numVar = 22;
        numObj = 3;
        numConstr = 0;
        minVar = new double[numVar];
        maxVar = new double[numVar];
        for (register unsigned int i = 0; i < numVar; i++) {
            minVar[i] = 0.0;
            maxVar[i] = 1.0;
        }
    }

    ~DTLZ7() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        register double g, h;
        obj[0] = var[0];
        obj[1] = var[1];
        g = 0.0;
        for (register unsigned int i = 2; i < 22; i++) {
            g += var[i];
        }
        g = 1.0 + (9.0 / 20.0) * g;
        h = 3.0 - obj[0]*(1.0 + sin(3.0 * PI * obj[0])) / (1.0 + g) - obj[1]*(1.0 + sin(3.0 * PI * obj[1])) / (1.0 + g);
        obj[2] = (1.0 + g) * h;
        return;
    }

};

#endif
