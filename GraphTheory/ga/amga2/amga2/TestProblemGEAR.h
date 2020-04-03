#ifndef TEST_PROBLEM_GEAR
#define	TEST_PROBLEM_GEAR 1

#include <cmath>

#include "AbstractTestProblem.h"

class GEAR : public AbstractTestProblem {
public:

    GEAR() {
        name = "GEAR";
        numVar = 7;
        numObj = 3;
        numConstr = 11;
        minVar = new double[numVar];
        maxVar = new double[numVar];

        minVar[0] = 2.6;
        maxVar[0] = 3.6;

        minVar[1] = 0.7;
        maxVar[1] = 0.8;

        minVar[2] = 17.0;
        maxVar[2] = 28.0;

        minVar[3] = 7.3;
        maxVar[3] = 8.3;

        minVar[4] = 7.3;
        maxVar[4] = 8.3;

        minVar[5] = 2.9;
        maxVar[5] = 3.9;

        minVar[6] = 5.0;
        maxVar[6] = 5.5;
    }

    ~GEAR() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        register double x1, x2, x3, x4, x5, x6, x7;
        register double temp = 0.0;
        x1 = var[0]; //Width of the gear face
        x2 = var[1]; //Teeth module
        x3 = static_cast<int> (var[2] > 0.0 ? var[2] + 0.5 : var[2] - 0.5); //Number of teeth of pinion
        x4 = var[3]; //Shaft 1 length between bearings
        x5 = var[4]; //Shaft 2 length between bearings
        x6 = var[5]; //Diameter of shaft 1
        x7 = var[6]; //Diameter of shaft 2

        obj[0] = 0.7854 * x1 * x2 * x2 * (10.0 * x3 * x3 / 3.0 + 14.9334 * x3 - 43.0934) - 1.5079 * x1 * (x6 * x6 + x7 * x7) + 7.477 * (x6 * x6 * x6 + x7 * x7 * x7) + 0.7854 * (x4 * x6 * x6 + x5 * x7 * x7);

        temp = (745.0 * x4) / (x2 * x3);
        obj[1] = (sqrt(temp * temp + 1.69 * 1.0e7)) / (0.1 * x6 * x6 * x6);

        temp = (745.0 * x5) / (x2 * x3);
        obj[2] = (sqrt(temp * temp + 1.575 * 1.0e8)) / (0.1 * x7 * x7 * x7);

        constr[0] = (x1 * x2 * x2 * x3) / 27.0 - 1.0;
        constr[1] = (x1 * x2 * x2 * x3 * x3) / 397.5 - 1.0;
        constr[2] = (x2 * x3 * x6 * x6 * x6 * x6) / (1.93 * x4 * x4 * x4) - 1.0;
        constr[3] = (x2 * x3 * x7 * x7 * x7 * x7) / (1.93 * x5 * x5 * x5) - 1.0;
        constr[4] = 1.0 - obj[1] / 1100.0;
        constr[5] = 1.0 - obj[2] / 850.0;
        constr[6] = 1.0 - (x2 * x3) / 40.0;
        constr[7] = 1.0 - (5.0 * x2) / x1;
        constr[8] = 1.0 - x1 / (12.0 * x2);
        constr[9] = 1.0 - (1.5 * x6 + 1.9) / x4;
        constr[10] = 1.0 - (1.1 * x7 + 1.9) / x5;
        return;
    }
};

#endif
