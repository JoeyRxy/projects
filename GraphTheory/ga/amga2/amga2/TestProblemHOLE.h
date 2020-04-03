#ifndef TEST_PROBLEM_HOLE
#define	TEST_PROBLEM_HOLE 1

#include <cmath>

#include "AbstractTestProblem.h"

class HOLE : public AbstractTestProblem {
public:

    HOLE() {
        name = "HOLE";
        numVar = 2;
        numObj = 2;
        numConstr = 0;
        minVar = new double[numVar];
        maxVar = new double[numVar];

        minVar[0] = -1.0;
        maxVar[0] = 1.0;

        minVar[1] = -1.0;
        maxVar[1] = 1.0;
    }

    ~HOLE() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        register double x = var[0];
        register double y = var[1];
        register double q = 0.2;
        register double p = 2.0;
        register double d0 = 0.02;
        register double h = 2.0;
        register double delta = 1.0 - 1.0 / sqrt(2.0);
        register double xp = x + delta;
        register double yp = y - delta;
        register double alpha = PI / 4.0;
        register double xpp = xp * cos(alpha) + yp * sin(alpha);
        register double ypp = -xp * sin(alpha) + yp * cos(alpha);
        register double xppp = xpp*PI;
        register double yppp = ypp*PI;
        register double u = sin(xppp / 2.0);
        register double v = pow(sin(yppp / 2.0), 2.0);
        register double up;
        if (u >= 0.0) {
            up = pow(u, h);
        } else {
            up = -pow(-u, h);
        }
        register double vp = pow(v, 1.0 / h);
        register double t = up;
        register double a = 2.0 * vp*p;
        register double b = 0.0;
        if (a <= p) {
            b = (p - a) * exp(q);
        }
        register double d = a * q / 2.0 + d0;
        register double c = q / (d * d);
        obj[0] = pow(t + 1.0, 2.0) + a + b * exp(-c * (t - d)*(t - d));
        obj[1] = pow(t - 1.0, 2.0) + a + b * exp(-c * (t + d)*(t + d));
        return;
    }

};

#endif
