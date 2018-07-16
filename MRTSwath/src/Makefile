
CC = gcc
CFLAGS = -O3 -Wall -W -Wno-switch
LDFLAGS = $(HDFLIB) $(GEOLIB) $(TIFFLIB) -lm -s
LDFLAGS_DUMP = $(HDFLIB) -lm -s
MV = mv
CP = cp
RM = rm -f
INCS  = -I../include


#--------------------------
# Define the include files:
#--------------------------
INC	= resamp.h param.h geoloc.h input.h scan.h output.h space.h kernel.h \
	 patches.h myhdf.h mystring.h myerror.h bool.h range.h parser.h \
	 myproj.h myproj_const.h usage.h const.h deg2dms.h GeoS2G.h addmeta.h \
	 geowrpr.h myendian.h

#-----------------------------------------------------------
# Define the source code and object files for the resampler:
#-----------------------------------------------------------
SRC	= param.c geoloc.c input.c scan.c output.c space.c kernel.c \
	  patches.c myhdf.c mystring.c parser.c myerror.c \
	  InitGeoTiff.c deg2dms.c degdms.c convert_corners.c metadata.c \
	  geo_trans.c write_hdr.c write_rb.c addmeta.c logh.c geowrpr.c \
	  filegeo.c myendian.c

OBJ = $(SRC:.c=.o)

#----------------------------------
# Define the resampler executables:
#----------------------------------
EXE = swath2grid dumpmeta hdf2hdr

#-----------------------------
# Define the object libraries:
#-----------------------------
# GEOLIB = $(MODISDIR)/lib/libgeolib.a $(MODISDIR)/lib/libgctp.a
# HDFLIB = $(MODISDIR)/lib/libhdfeos.a $(MODISDIR)/lib/libmfhdf.a $(MODISDIR)/lib/libdf.a $(MODISDIR)/lib/libjpeg.a $(MODISDIR)/lib/libz.a
# TIFFLIB = $(MODISDIR)/lib/libgeotiff.a $(MODISDIR)/lib/libtiff.a
GEOLIB = ../lib/libgctp.a
HDFLIB = ../lib/libhdfeos.a ../lib/lib/libmfhdf.a ../lib/libdf.a ../lib/libjpeg.a ../lib/libz.a ../libsz.a
TIFFLIB = ../lib/libgeotiff.a ../lib/libtiff.a

#-------------------------------------------------------------------
# Targets for each executable.
# Note: stripping executable with -s flag on SGI may cause problems.
#-------------------------------------------------------------------
all: $(EXE)

swath2grid: resamp.o $(OBJ) $(INC)
	$(CC) -o swath2grid resamp.o $(OBJ) $(LDFLAGS) -lm -s

dumpmeta: dumpmeta.o
	$(CC) -o dumpmeta dumpmeta.o $(LDFLAGS_DUMP) -lm -s

hdf2hdr: hdf2hdr.o gui_hdr.o $(OBJ) gui_hdr.h
	$(CC) -o hdf2hdr hdf2hdr.o gui_hdr.o $(OBJ) $(LDFLAGS) -lm -s

install:
	$(MV) swath2grid dumpmeta hdf2hdr ../bin

clean:
	$(RM) *.o swath2grid dumpmeta hdf2hdr *~

copy-makefile:
	@if [ ! -f Makefile.orig ]; then \
	  $(CP) Makefile Makefile.orig; fi
	$(CP) Makefile.$(CPMAKEFILEEXT) Makefile

#-----------------------------------------------------------------------------
# Rules for compiling the object files.
#-----------------------------------------------------------------------------

"$(OBJ)": $(INC)

.c.o:
	$(CC) $(CFLAGS) $(INCS) -c $<


