#ifndef CTP6_H
#define	CTP6_H 1

#include "AbstractTestProblem.h"

class CTP6 : public AbstractTestProblem {
public:

    CTP6() {
        name = "CTP6";
        numVar = 2;
        numObj = 2;
        numConstr = 1;
        minVar = new double[numVar];
        maxVar = new double[numVar];
        minVar[0] = 0.0;
        minVar[1] = 0.0;
        maxVar[0] = 1.0;
        maxVar[1] = 5.0;
    }

    ~CTP6() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        register double g;
        register double theta, a, b, c, d, e;
        register double exp1, exp2;
        theta = 0.1 * PI;
        a = 40.0;
        b = 0.5;
        c = 1.0;
        d = 2.0;
        e = -2.0;
        g = 1.0 + var[1] * var[1];
        obj[0] = var[0];
        obj[1] = g * (1.0 - sqrt(obj[0] / g));
        exp1 = (obj[1] - e) * cos(theta) - obj[0] * sin(theta);
        exp2 = (obj[1] - e) * sin(theta) + obj[0] * cos(theta);
        exp2 = b * PI * pow(exp2, c);
        exp2 = fabs(sin(exp2));
        exp2 = a * pow(exp2, d);
        constr[0] = exp1 / exp2 - 1.0;
        return;
    }

};

#endif
