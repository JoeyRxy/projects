#include <cstdlib>
#include <cstdio>
#include <iostream>

#if defined(WIN32) || defined(_WIN32)
#define popen _popen
#define pclose _pclose
#include <windows.h>
#include <winbase.h>
#define mysleep(x) Sleep(x)
#else
#include <unistd.h>
#define mysleep(x) usleep(1000*x)
#endif

#define DELAY_MILLISEC 10

#include "Display.h"

using namespace std;

Display::Display(unsigned int d) : numDimensions(d) {
    if (numDimensions == 2 || numDimensions == 3) {
        handle = popen(GNUPLOT_COMMAND, "w");
        if (handle == NULL) {
            cerr << "\n Could not open gnuplot for plotting, check the command syntax \n";
        }
    } else {
        cerr << "\n Wrong number of dimensions entered, the plotting will not be available \n";
        handle = NULL;
    }
}

Display::~Display() {
    if (handle) {
        pclose(handle);
    }
}

void Display::display(const string& fileName, unsigned int eval, unsigned int type, unsigned int size) {
    if (handle) {
        mysleep(DELAY_MILLISEC);
        fprintf(handle, "set title 'Evaluation #%d'\n", eval);
        fprintf(handle, "unset key\n");
        fprintf(handle, "set ticslevel 0\n");
        if (numDimensions == 2) {
            fprintf(handle, "plot '%s' w points pointtype %u pointsize %u \n \n", fileName.c_str(), type, size);
        } else if (numDimensions == 3) {
            fprintf(handle, "splot '%s' w points pointtype %u pointsize %u \n \n", fileName.c_str(), type, size);
        }
        fflush(handle);
        mysleep(DELAY_MILLISEC);
    }
    return;
}

void Display::reDisplay(const string& fileName, unsigned int type, unsigned int size) {
    if (handle) {
        mysleep(DELAY_MILLISEC);
        if (numDimensions == 2) {
            fprintf(handle, "replot '%s' w points pointtype %u pointsize %u \n \n", fileName.c_str(), type, size);
        } else if (numDimensions == 3) {
            fprintf(handle, "replot '%s' w points pointtype %u pointsize %u \n \n", fileName.c_str(), type, size);
        } else {

        }
        fflush(handle);
        mysleep(DELAY_MILLISEC);
    }
    return;
}
