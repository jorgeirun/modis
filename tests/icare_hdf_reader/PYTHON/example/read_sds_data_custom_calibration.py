#! /usr/bin/env python
# -*- coding: latin-1 -*-

###################################################################################################################################
#
# @file read_sds_data_custom_calibration.py
# @brief This example describes how to read the binary data of an HDF4 SDS, read its calibration factors and apply a non standard calibration to it.
# @warning What is called a non-standard calibration is one different than "science_value = scaling_factor ( binary_file_value - offset ) ". If the SDS you are reading uses a calibration of this type you can check the "read_sds_data.c" example, which automatically do it for you.
# @warning Products that are using a non-standard calibration are : CALIPSO IIR L2, CLOUDSAT...
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
#       READ A BINARY SDS DATA
#*********************************************/

print("*** Read Binary data of SDS %s ***\n"%sds_name)

# READ THE WHOLE DATASET : let start, stride and edge parameters of read sds data to default
binary_data = read_sds_binary_data(filename, sds_name)

# set dimensions of the read SDS. HDF SDS dimensions are orderd as [Z,Y,X]
dim_y = binary_data.shape[0]
dim_x = binary_data.shape[1]

# print out first read data
for i_y in range(10):
    for i_x in range(dim_x):
        # buffer allocated by the read sds function are accessed lineary
        print(binary_data[i_y][i_x]),
    print("\n")

#*********************************************/
#           READ A SDS CALIBRATION
#*********************************************/

print("*** Calibration of SDS %s ***\n"%sds_name)
cal, cal_err, offset, offset_err, data_type = get_sds_calibration(filename, sds_name)
print("sds %s : cal=%f cal_err=%f offset=%f offset_err=%f data_type=%d\n"%(sds_name, cal, cal_err, offset, offset_err, data_type))

#*********************************************************/
#          APPLY CUSTOM CALIBRATION                      */
# using for example an equation like :                   */
# science_value = scaling_factor * binary_value + offset */
#*********************************************************/

# apply scaling equation on binary data
data = cal * binary_data + offset

# print out first read data
for i_y in xrange(10):
    for i_x in xrange(dim_x):
        # buffer allocated by the read sds function are accessed lineary
        print("%f\t"%data[i_y][i_x]),
    print("\n")

