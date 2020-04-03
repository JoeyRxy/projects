#ifndef CTP5_H
#define	CTP5_H 1

#include "AbstractTestProblem.h"

class CTP5 : public AbstractTestProblem {
public:

    CTP5() {
        name = "CTP5";
        numVar = 2;
        numObj = 2;
        numConstr = 1;
        minVar = new double[numVar];
        maxVar = new double[numVar];
        minVar[0] = 0.0;
        minVar[1] = 0.0;
        maxVar[0] = 1.0;
        maxVar[1] = 1.0;
    }

    ~CTP5() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        register double g;
        register double theta, a, b, c, d, e;
        register double exp1, exp2;
        theta = -0.2 * PI;
        a = 0.1;
        b = 10.0;
        c = 2.0;
        d = 0.5;
        e = 1.0;
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
