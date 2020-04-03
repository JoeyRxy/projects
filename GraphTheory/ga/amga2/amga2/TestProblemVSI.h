#ifndef TEST_PROBLEM_VSI
#define	TEST_PROBLEM_VSI 1

#include <cmath>

#include "AbstractTestProblem.h"

class VSI : public AbstractTestProblem {
public:

    VSI() {

        name = "VSI";

        numVar = 7;
        numObj = 2;
        numConstr = 10;
        minVar = new double[numVar];
        maxVar = new double[numVar];
        for (register unsigned int i = 0; i < numVar; i++) {
            minVar[i] = 0.5;
            maxVar[i] = 1.5;
        }
    }

    ~VSI() {
        delete[](minVar);
        delete[](maxVar);
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const {
        register double x1 = var[0]; //Thickness_B_Pillar_Inner
        register double x2 = var[1]; //Thickness_B_Pillar_Reinforcement
        register double x3 = var[2]; //Thickness_Floor_Side_Inner
        register double x4 = var[3]; //Thickness_Cross_Members
        register double x5 = var[4]; //Thickness_Door_Beam
        register double x6 = var[5]; //Thickness_Door_Belt_Line_Inforcement
        register double x7 = var[6]; //Thickness_Roof_Rail
        register double x8 = 0.345; //Material_B_Pillar_Inner
        register double x9 = 0.192; //varMaterial_Floor_Side_Inner
        register double x10 = 30.0; //Barrier_Height
        register double x11 = 30.0; //Barrier_Hitting_Position
        register double Weight = 1.98 + 4.9 * x1 + 6.67 * x2 + 6.98 * x3 + 4.01 * x4 + 1.78 * x5 + 2.73 * x7;
        register double Velocity_B_Pillar = 10.58 - 0.674 * x1 * x2 - 1.95 * x2 * x8 + 0.02054 * x3 * x10 - 0.0198 * x4 * x10 + 0.028 * x6*x10;
        register double Velocity_Front_Door = 16.45 - 0.489 * x3 * x7 - 0.843 * x5 * x6 + 0.0432 * x9 * x10 - 0.0556 * x9 * x11 - 0.000786 * x11*x11;
        register double Abdomen_Load = 1.16 - 0.3717 * x2 * x4 - 0.00931 * x2 * x10 - 0.484 * x3 * x9 + 0.01343 * x6*x10;
        register double Viscous_Criteria_Upper = 0.261 - 0.0159 * x1 * x2 - 0.188 * x1 * x8 - 0.019 * x2 * x7 + 0.0144 * x3 * x5 + 0.0008757 * x5 * x10 + 0.08045 * x6 * x9 + 0.00139 * x8 * x11 + 0.00001575 * x10*x11;
        register double Viscous_Criteria_Middle = 0.214 + 0.00817 * x5 - 0.131 * x1 * x8 - 0.0704 * x1 * x9 + 0.03099 * x2 * x6 - 0.018 * x2 * x7 + 0.0208 * x3 * x8 + 0.121 * x3 * x9 - 0.00364 * x5 * x6 + 0.0007715 * x5 * x10 - 0.0005354 * x6 * x10 + 0.00121 * x8*x11;
        register double Viscous_Criteria_Lower = 0.74 - 0.61 * x2 - 0.163 * x3 * x8 + 0.001232 * x3 * x10 - 0.166 * x7 * x9 + 0.227 * x2*x2;
        register double Rib_Deflection_Upper = 28.98 + 3.818 * x3 - 4.2 * x1 * x2 + 0.0207 * x5 * x10 + 6.63 * x6 * x9 - 7.7 * x7 * x8 + 0.32 * x9*x10;
        register double Rib_Deflection_Middle = 33.86 + 2.95 * x3 + 0.1792 * x10 - 5.057 * x1 * x2 - 11.0 * x2 * x8 - 0.0215 * x5 * x10 - 9.98 * x7 * x8 + 22.0 * x8*x9;
        register double Rib_Deflection_Lower = 46.36 - 9.9 * x2 - 12.9 * x1 * x8 + 0.1107 * x3*x10;
        register double Pubic_Symphysis_Force = 4.72 - 0.5 * x4 - 0.19 * x2 * x3 - 0.0122 * x4 * x10 + 0.009325 * x6 * x10 + 0.000191 * x11*x11;
        obj[0] = Weight;
        obj[1] = Velocity_Front_Door;
        constr[0] = 1.0 - Abdomen_Load;
        constr[1] = 1.0 - Viscous_Criteria_Lower / 0.32;
        constr[2] = 1.0 - Viscous_Criteria_Middle / 0.32;
        constr[3] = 1.0 - Viscous_Criteria_Upper / 0.32;
        constr[4] = 1.0 - Rib_Deflection_Lower / 32.0;
        constr[5] = 1.0 - Rib_Deflection_Middle / 32.0;
        constr[6] = 1.0 - Rib_Deflection_Upper / 32.0;
        constr[7] = 1.0 - Pubic_Symphysis_Force / 4.0;
        constr[8] = 1.0 - Velocity_B_Pillar / 9.9;
        constr[9] = 1.0 - Velocity_Front_Door / 15.7;
        return;
    }
};

#endif
