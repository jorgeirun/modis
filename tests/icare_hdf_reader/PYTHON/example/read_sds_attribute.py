#! /usr/bin/env python
# -*- coding: latin-1 -*-

###################################################################################################################################
#
# @file read_sds_attribute.py
# @brief describes how to read an HDF4 SDS attribute
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
# sds_name = "P3L1_Q_Stokes_490P"
sds_name = "Cloud_Mask"
# name of a file attribute
sds_attr_name = "valid_range"

#*********************************************/
#          READ A SDS ATTRIBUTE
#*********************************************/

print("*** Read SDS attribute %s ***\n"%sds_attr_name)
# read its value
attr=get_sds_attr(filename, sds_name, sds_attr_name)
# print it out
print(attr)