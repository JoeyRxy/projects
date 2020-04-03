#ifndef DISPLAY_H
#define DISPLAY_H 1

#define GNUPLOT_COMMAND "gnuplot -persist"

#include <iostream>
#include <string>

class Display {
public:
    Display(unsigned int);
    void display(const std::string&, unsigned int, unsigned int, unsigned int);
    void reDisplay(const std::string&, unsigned int, unsigned int);
    ~Display();

private:
    FILE *handle;
    unsigned int numDimensions;
};

#endif
