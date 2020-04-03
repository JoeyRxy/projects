#ifndef DTLZ8_H
#define	DTLZ8_H 1

#include "AbstractTestProblem.h"

class DTLZ8 : public AbstractTestProblem {
public:

    DTLZ8() {
        name = "DTLZ8";
        numVar = 30;
        numObj = 3;
        numConstr = 3;
        minVar = new double[numVar];
        maxVar = new double[numVar];
        for (register unsigned int i = 0; i < numVar; i++) {
            minVar[i] = 0.0;
            maxVar[i] = 1.0;
        }
    }

    ~DTLZ8() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        register unsigned int nreal = 30;
        register unsigned int nobj = 3;
        register unsigned int j, k, nbyM;
        register double endk;
        register double minf12;
        register double fj;
        nbyM = nreal / nobj;
        for (j = 0; j < nobj; j++) {
            endk = (j < nobj - 1) ? (j + 1) * nbyM : nreal;
            for (k = j * nbyM, fj = 0.0; k < endk; k++) {
                fj += var[k];
            }
            obj[j] = fj / (1.0 * nbyM);
        }
        minf12 = obj[0] + obj[1];
        for (k = 0; k < nobj - 2; k++) {
            for (j = k + 1; j < nobj - 1; j++) {
                if (obj[k] + obj[j] < minf12) {
                    minf12 = obj[k] + obj[j];
                }
            }
        }
        for (j = 0; j < nobj - 1; j++) {
            constr[j] = obj[nobj - 1] + 4.0 * obj[j] - 1.0;
        }
        constr[nobj - 1] = 2.0 * obj[nobj - 1] + minf12 - 1.0;
        return;
    }

};

#endif
