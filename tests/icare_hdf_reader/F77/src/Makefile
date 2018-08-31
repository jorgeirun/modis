HDFDIR = /opt/HDF4
FC     = f77

HDFINC = $(HDFDIR)/include
ICAINC = ../include
ICALIB = ../lib
LIBPTH = $(ICALIB)/libicaf77hdf.a

CFLAGS = -w -I$(ICAINC) -I$(HDFINC)

LIBSRC = ica_f77_util.f ica_f77_hdf_util.f ica_f77_hdf_sds.f 
LIBOBJ = ica_f77_util.o ica_f77_hdf_util.o ica_f77_hdf_sds.o 

all: lib

lib: $(LIBOBJ)
#	$(FC) -c $(CFLAGS) $(LIBSRC)
	mkdir -p $(ICALIB)
	ar -r $(LIBPTH) $(LIBOBJ)

ica_f77_util.o: ica_f77_util.f
	$(FC) -c $(CFLAGS) ica_f77_util.f

ica_f77_hdf_util.o: ica_f77_hdf_util.f $(ICAINC)/icadffunc.inc
	$(FC) -c $(CFLAGS) ica_f77_hdf_util.f

ica_f77_hdf_sds.o: ica_f77_hdf_sds.f $(ICAINC)/icadffunc.inc $(ICAINC)/icahdfsds.inc
	$(FC) -c $(CFLAGS) ica_f77_hdf_sds.f

clean :
	rm -rf $(ICALIB) *.o
