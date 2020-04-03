#ifndef DTLZ9_H
#define	DTLZ9_H 1

#include "AbstractTestProblem.h"

class DTLZ9 : public AbstractTestProblem {
public:

    DTLZ9() {
        name = "DTLZ9";
        numVar = 30;
        numObj = 3;
        numConstr = 2;
        minVar = new double[numVar];
        maxVar = new double[numVar];
        for (register unsigned int i = 0; i < numVar; i++) {
            minVar[i] = 0.0;
            maxVar[i] = 1.0;
        }
    }

    ~DTLZ9() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        register int fc;
        register double xkk;
        register unsigned int bg;
        register unsigned int j;
        register unsigned int k;
        register unsigned int ed;
        register unsigned int nobj = 3;
        register unsigned int nreal = 30;
        register unsigned int nconstr = 2;
        fc = nreal / nobj;
        for (j = 0; j < nobj; j++) {
            bg = j*fc;
            ed = (j == nobj - 1) ? nreal : (j + 1) * fc;
            for (k = bg, xkk = 0.0; k < ed; k++) {
                xkk += pow(var[k], 0.1);
            }
            obj[j] = xkk;
        }
        for (j = 0; j < nconstr; j++) {
            constr[j] = obj[nobj - 1] * obj[nobj - 1] + obj[j] * obj[j] - 1.0;
        }
        return;
    }

};

#endif
