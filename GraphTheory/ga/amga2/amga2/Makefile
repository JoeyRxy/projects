# Makefile for building C++ source of AMGA2

MAIN=amga2.exe

CPP=g++

RM=rm -f

OBJS=$(patsubst %.cpp,%.o,$(wildcard *.cpp))

HEADERS=$(wildcard *.h)

all:$(MAIN)

$(MAIN): $(OBJS)
	$(CPP) $(OBJS) -lm -o $@

%.o: %.cpp $(HEADERS)
	$(CPP) -Wall -pedantic -march=nocona -O3 -pipe -fomit-frame-pointer -fprefetch-loop-arrays -funroll-loops -msse3 -ffloat-store -c $<

clean:
	$(RM) $(MAIN) *.o *.txt *~
