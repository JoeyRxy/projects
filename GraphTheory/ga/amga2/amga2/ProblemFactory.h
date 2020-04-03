#ifndef PROBLEM_FACTORY_H
#define	PROBLEM_FACTORY_H 1

#include <string>

#include "AbstractTestProblem.h"
#include "TestProblemBNH.h"
#include "TestProblemCEC09.h"
#include "TestProblemCTP1.h"
#include "TestProblemCTP2.h"
#include "TestProblemCTP3.h"
#include "TestProblemCTP4.h"
#include "TestProblemCTP5.h"
#include "TestProblemCTP6.h"
#include "TestProblemCTP7.h"
#include "TestProblemCTP8.h"
#include "TestProblemDTLZ1.h"
#include "TestProblemDTLZ2.h"
#include "TestProblemDTLZ3.h"
#include "TestProblemDTLZ4.h"
#include "TestProblemDTLZ5.h"
#include "TestProblemDTLZ6.h"
#include "TestProblemDTLZ7.h"
#include "TestProblemDTLZ8.h"
#include "TestProblemDTLZ9.h"
#include "TestProblemFON.h"
#include "TestProblemGEAR.h"
#include "TestProblemHOLE.h"
#include "TestProblemKUR.h"
#include "TestProblemOSY.h"
#include "TestProblemPOL.h"
#include "TestProblemSCH1.h"
#include "TestProblemSCH2.h"
#include "TestProblemSRN.h"
#include "TestProblemTNK.h"
#include "TestProblemTRUSS.h"
#include "TestProblemVNT.h"
#include "TestProblemVSI.h"
#include "TestProblemZDT1.h"
#include "TestProblemZDT2.h"
#include "TestProblemZDT3.h"
#include "TestProblemZDT4.h"
#include "TestProblemZDT6.h"

const AbstractTestProblem* getTestProblem(const std::string& name) {
    if (name.compare("BNH") == 0) {
        return new BNH();
    } else if (CEC09::getProblemNumber(name) != 0) {
        return new CEC09(name);
    } else if (name.compare("CTP1") == 0) {
        return new CTP1();
    } else if (name.compare("CTP2") == 0) {
        return new CTP2();
    } else if (name.compare("CTP3") == 0) {
        return new CTP3();
    } else if (name.compare("CTP4") == 0) {
        return new CTP4();
    } else if (name.compare("CTP5") == 0) {
        return new CTP5();
    } else if (name.compare("CTP6") == 0) {
        return new CTP6();
    } else if (name.compare("CTP7") == 0) {
        return new CTP7();
    } else if (name.compare("CTP8") == 0) {
        return new CTP8();
    } else if (name.compare("DTLZ1") == 0) {
        return new DTLZ1();
    } else if (name.compare("DTLZ2") == 0) {
        return new DTLZ2();
    } else if (name.compare("DTLZ3") == 0) {
        return new DTLZ3();
    } else if (name.compare("DTLZ4") == 0) {
        return new DTLZ4();
    } else if (name.compare("DTLZ5") == 0) {
        return new DTLZ5();
    } else if (name.compare("DTLZ6") == 0) {
        return new DTLZ6();
    } else if (name.compare("DTLZ7") == 0) {
        return new DTLZ7();
    } else if (name.compare("DTLZ8") == 0) {
        return new DTLZ8();
    } else if (name.compare("DTLZ9") == 0) {
        return new DTLZ9();
    } else if (name.compare("FON") == 0) {
        return new FON();
    } else if (name.compare("GEAR") == 0) {
        return new GEAR();
    } else if (name.compare("HOLE") == 0) {
        return new HOLE();
    } else if (name.compare("KUR") == 0) {
        return new KUR();
    } else if (name.compare("OSY") == 0) {
        return new OSY();
    } else if (name.compare("POL") == 0) {
        return new POL();
    } else if (name.compare("SCH1") == 0) {
        return new SCH1();
    } else if (name.compare("SCH2") == 0) {
        return new SCH2();
    } else if (name.compare("SRN") == 0) {
        return new SRN();
    } else if (name.compare("TNK") == 0) {
        return new TNK();
    } else if (name.compare("TRUSS") == 0) {
        return new TRUSS();
    } else if (name.compare("VNT") == 0) {
        return new VNT();
    } else if (name.compare("VSI") == 0) {
        return new VSI();
    } else if (name.compare("ZDT1") == 0) {
        return new ZDT1();
    } else if (name.compare("ZDT2") == 0) {
        return new ZDT2();
    } else if (name.compare("ZDT3") == 0) {
        return new ZDT3();
    } else if (name.compare("ZDT4") == 0) {
        return new ZDT4();
    } else if (name.compare("ZDT6") == 0) {
        return new ZDT6();
    }
    return NULL;

}

void printProblemList(std::ostream& out) {
    out << "\n The list of available problems is as follows.";
    out << "\n 1.  BNH";
    out << "\n 2.  UF1";
    out << "\n 3.  UF2";
    out << "\n 4.  UF3";
    out << "\n 5.  UF4";
    out << "\n 6.  UF5";
    out << "\n 7.  UF6";
    out << "\n 8.  UF7";
    out << "\n 9.  UF8";
    out << "\n 10. UF9";
    out << "\n 11. UF10";
    out << "\n 12. CF1";
    out << "\n 13. CF2";
    out << "\n 14. CF3";
    out << "\n 15. CF4";
    out << "\n 16. CF5";
    out << "\n 17. CF6";
    out << "\n 18. CF7";
    out << "\n 19. CF8";
    out << "\n 20. CF9";
    out << "\n 21. CF10";
    out << "\n 22. R2_DTLZ2_M5";
    out << "\n 23. R3_DTLZ3_M5";
    out << "\n 24. WFG1_M5";
    out << "\n 25. CTP1";
    out << "\n 26. CTP2";
    out << "\n 27. CTP3";
    out << "\n 28. CTP4";
    out << "\n 29. CTP5";
    out << "\n 30. CTP6";
    out << "\n 31. CTP7";
    out << "\n 32. CTP8";
    out << "\n 33. DTLZ1";
    out << "\n 34. DTLZ2";
    out << "\n 35. DTLZ3";
    out << "\n 36. DTLZ4";
    out << "\n 37. DTLZ5";
    out << "\n 38. DTLZ6";
    out << "\n 39. DTLZ7";
    out << "\n 40. DTLZ8";
    out << "\n 41. DTLZ9";
    out << "\n 42. FON";
    out << "\n 43. GEAR";
    out << "\n 44. HOLE";
    out << "\n 45. KUR";
    out << "\n 46. OSY";
    out << "\n 47. POL";
    out << "\n 48. SCH1";
    out << "\n 49. SCH2";
    out << "\n 50. SRN";
    out << "\n 51. TNK";
    out << "\n 52. TRUSS";
    out << "\n 53. VNT";
    out << "\n 54. VSI";
    out << "\n 55. ZDT1";
    out << "\n 56. ZDT2";
    out << "\n 57. ZDT3";
    out << "\n 58. ZDT4";
    out << "\n 59. ZDT6";
    out << "\n";
    return;
}

#endif
