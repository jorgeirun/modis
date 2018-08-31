#!/bin/bash -f

##############################################################################
#
#                     caltrack_reader library compilation environment
#
############################################################################## 

echo " "
echo "-----------------------------------------------"
echo "-- c_caltrack_reader compilation environment --"
echo "-----------------------------------------------"

#--------------------------------------#
# Compiler flags                       #
#--------------------------------------#

export C="gcc"
# export CFLAGS="-O0 -Wall"
export CFLAGS="-O2"

case $MACHINE_NAME in
    "icarabosse.icare.univ-lille1.fr" ) # force g++ 3.2.x usage to avoid linkage bugs
        export C="gcc4"
        ;;
    "camelot.ipsl.polytechnique.fr" ) # needs to link to the static library under camelot
        export CFLAGS=$CFLAGS" -static-libgcc"
        ;;
esac
echo " C compiler:" $C
echo " C flags:" $CFLAGS

#-------------------------#
# HDF library information #
#-------------------------#

# --- Set a Machine Specific Environment --- #
MACHINE_NAME=`uname -n`
case $MACHINE_NAME in
    "icare-pc12" )
        export HDF_INC="-I/home/pascal/include"
        export HDF_LIBS="-L/home/pascal/lib -lmfhdf -ldf -ljpeg -lz -lm"
        ;;
    "icarabosse.icare.univ-lille1.fr" )
        export HDF_INC="-I/usr/lib/hdf/include"
        export HDF_LIBS="-L/usr/lib/hdf/lib -lmfhdf -ldf -ljpeg -lz -lm"
        ;;
    "icarbure.icare.univ-lille1.fr" )
        export HDF_INC="-I/usr/local/hdf4.2r1/include"
        export HDF_LIBS="-L/usr/local/hdf4.2r1/lib -lmfhdf -ldf -ljpeg -lz -lm"
        ;;
    "camelot.ipsl.polytechnique.fr" )
	export HDF_INC="-I/usr/include/hdf"
	export HDF_LIBS="-L/usr/lib64/hdf -lmfhdf -ldf -ljpeg -lz -lm"
        ;;
     * )
        export HDF_INC="-I/usr/include"
        export HDF_LIBS="-L/usr/lib -lmfhdf -ldf -ljpeg -lz -lm"
        ;;
esac

echo " HDF includes      :" $HDF_INC
echo " HDF libs          :" $HDF_LIBS

echo " "

