#! /usr/bin/env python
# -*- coding: latin-1 -*-

###################################################################################################################################
#
# @file read_sds_data.py
# @brief This example describes how to read the data of an HDF4 SDS that uses a standard calibration
# @warning What is called a standard calibration is one like "science_value = scaling_factor ( binary_file_value - offset )"
# @author Nicolas PASCAL (nicolas.pascal@icare.univ-lille1.fr), (C) Centre de Gestion et de Traitement de Données (CGTD) ICARE 2008
# @version 0.0.0
# @date 2008/04/07
#
# Copyright: See COPYING file that comes with this distribution
#
# History :
#   v0.0.0 : creation
#
###################################################################################################################################

from py_hdf_reader import *

__VERSION__="0.0.0"
__AUTHOR__="CGTD/UDEV Nicolas PASCAL (nicolas.pascal@icare.univ-lille1.fr)"

# path to the file to read
# filename = "../data/test.hdf"
filename = "MOD35_L2.A2017001.1355.061.2017312070506.hdf"
# name of an sds to access
sds_name = "Cloud_Mask"

#*********************************************/
#         READ A SCALED SDS DATA
#*********************************************/

print("*** Read scaled data of SDS %s ***\n"%sds_name)

# READ THE WHOLE DATASET : let start, stride and edge parameters to default None
data = read_sds_data(filename, sds_name);

# set dimensions of the read SDS. HDF SDS dimensions are ordered as [Z,Y,X]
dim_y = data.shape[0]
dim_x = data.shape[1]

# print out first read data
for i_y in xrange(10):
    for i_x in xrange(dim_x):
        # buffer allocated by the read sds function are accessed lineary
        print("%f\t"%data[i_y][i_x]),
    print("\n")
