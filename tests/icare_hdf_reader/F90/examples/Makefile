HDFDIR = /opt/HDF4
COMP   = g95
#COMP   = lf95
#COMP   = ifort

HDFINC = $(HDFDIR)/include
HDFLIB = $(HDFDIR)/lib
ICAINC = ../include
ICALIB = ../lib

CFLAGS = -w -I$(ICAINC) -I$(HDFINC)
LFLAGS = -L$(ICALIB) -L$(HDFLIB) -licaf90hdf -lmfhdf -ldf -ljpeg -lz -lm

COMSRC = prntutil.f90
COMOBJ = prntutil.o

all: example1 example2 example3 example4

example1: example1.f90
	$(COMP) -c $(CFLAGS) example1.f90 $(COMSRC)
	$(COMP) example1.o $(COMOBJ) -o example1 $(LFLAGS)

example2: example2.f90
	$(COMP) -c $(CFLAGS) example2.f90 $(COMSRC)
	$(COMP) example2.o $(COMOBJ) -o example2 $(CFLAGS) $(LFLAGS)

example3: example3.f90
	$(COMP) -c $(CFLAGS) example3.f90 $(COMSRC)
	$(COMP) example3.o $(COMOBJ) -o example3 $(CFLAGS) $(LFLAGS)

example4: example4.f90
	$(COMP) -c $(CFLAGS) example4.f90 $(COMSRC)
	$(COMP) example4.o $(COMOBJ) -o example4 $(LFLAGS)

clean :
	rm -f *.o example?
