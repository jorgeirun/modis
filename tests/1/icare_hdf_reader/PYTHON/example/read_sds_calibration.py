#! /usr/bin/env python
# -*- coding: latin-1 -*-

###################################################################################################################################
#
# @file read_sds_calibration.py
# @brief How to read the calibration of a HDF4 SDS
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
#           READ A SDS CALIBRATION
#*********************************************/

print("*** Calibration of SDS %s ***"%sds_name)
cal, cal_err, offset, offset_err, data_type = get_sds_calibration(filename, sds_name)
print("sds %s : cal=%f cal_err=%f offset=%f offset_err=%f data_type=%d"%(sds_name, cal, cal_err, offset, offset_err, data_type))
