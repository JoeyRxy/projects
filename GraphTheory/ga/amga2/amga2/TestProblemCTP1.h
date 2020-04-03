#ifndef CTP1_H
#define	CTP1_H 1

#include "AbstractTestProblem.h"

class CTP1 : public AbstractTestProblem {
public:

    CTP1() {
        name = "CTP1";
        numVar = 2;
        numObj = 2;
        numConstr = 2;
        minVar = new double[numVar];
        maxVar = new double[numVar];
        minVar[0] = 0.0;
        minVar[1] = 0.0;
        maxVar[0] = 1.0;
        maxVar[1] = 1.0;
    }

    ~CTP1() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        register double g;
        register unsigned int i;
        g = 1.0;
        for (i = 1; i < 2; i++) {
            g += var[i] * var[i];
        }
        obj[0] = var[0];
        obj[1] = g * exp(-obj[0] / g);
        constr[0] = obj[1] / (0.858 * exp(-0.541 * obj[0])) - 1.0;
        constr[1] = obj[1] / (0.728 * exp(-0.295 * obj[0])) - 1.0;
        return;
    }

};

#endif
