HDFDIR = /opt/HDF4
FC     = f77

HDFINC = $(HDFDIR)/include
HDFLIB = $(HDFDIR)/lib
ICAINC = ../include
ICALIB = ../lib

CFLAGS = -w -I$(ICAINC) -I$(HDFINC)
LFLAGS = -L$(ICALIB) -L$(HDFLIB) -licaf77hdf -lmfhdf -ldf -ljpeg -lz -lm

COMSRC = prntresu.f own_dclb.f
COMOBJ = prntresu.o own_dclb.o

all: example1 example2 example3 example4 example5 example6 example7

example1: example1.f $(COMSRC)
	$(FC) example1.f $(COMSRC) -o example1 $(CFLAGS) $(LFLAGS)

example2: example2.f $(COMSRC)
	$(FC) example2.f $(COMSRC) -o example2 $(CFLAGS) $(LFLAGS)

example3: example3.f $(COMSRC)
	$(FC) example3.f $(COMSRC) -o example3 $(CFLAGS) $(LFLAGS)

example4: example4.f $(COMSRC)
	$(FC) example4.f $(COMSRC) -o example4 $(CFLAGS) $(LFLAGS)

example5: example5.f $(COMSRC)
	$(FC) example5.f $(COMSRC) -o example5 $(CFLAGS) $(LFLAGS)

example6: example6.f $(COMSRC)
	$(FC) example6.f $(COMSRC) -o example6 $(CFLAGS) $(LFLAGS)

example7: example7.f $(COMSRC)
	$(FC) example7.f $(COMSRC) -o example7 $(CFLAGS) $(LFLAGS)

clean : 
	rm -f *.o example?
