#ifndef ABSTRACT_TEST_PROBLEM_H
#define ABSTRACT_TEST_PROBLEM_H 1

#include <string>

#define PI 3.14159
#define EPSILON 1.0e-10

class AbstractTestProblem;
std::ostream& operator<<(std::ostream& out, const AbstractTestProblem& rhs);

class AbstractTestProblem {
    friend std::ostream& operator<<(std::ostream& out, const AbstractTestProblem& rhs);

public:

    AbstractTestProblem() {
    }

    virtual ~AbstractTestProblem() {
    }

    virtual void evaluate(const double *var, double *obj, double *constr) const = 0;

    virtual const std::string& getName() const {
        return name;
    }

    unsigned int getNumVar() const {
        return numVar;
    }

    unsigned int getNumObj() const {
        return numObj;
    }

    unsigned int getNumConstr() const {
        return numConstr;
    }

    const double* getMinVar() const {
        return minVar;
    }

    const double* getMaxVar() const {
        return maxVar;
    }

protected:

    virtual std::ostream& print(std::ostream& out) const {
        out << "\n Name of the problem   = " << name;
        out << "\n Number of variables   = " << numVar;
        out << "\n Number of objectives  = " << numObj;
        out << "\n Number of constraints = " << numConstr;
        for (register unsigned int i = 0; i < numVar; ++i) {
            out << "\n Variable " << +(i + 1) << " bounds     = [" << minVar[i] << ", " << maxVar[i] << "]";
        }
        out << "\n";
        return out;
    }

protected:
    std::string name;
    unsigned int numVar;
    unsigned int numObj;
    unsigned int numConstr;
    double *minVar;
    double *maxVar;
};

inline std::ostream& operator<<(std::ostream& out, const AbstractTestProblem& rhs) {
    return rhs.print(out);
}

#endif
