#! /usr/bin/env python
# -*- coding: latin-1 -*-

###################################################################################################################################
#
# @file read_hdf_file_structure.py
# @brief How to read the structure of an HDF4 file : retrieve the names of the SDS and the names of the file attributes
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
# filename = "../data/test.hdf";
filename = "MOD35_L2.A2017001.1355.061.2017312070506.hdf"

#*********************************************/
# RETRIEVE HDF STRUCTURE :
# - LIST OF SDS NAMES and
# - LIST OF FILE ATTRIBUTES NAMES
#*********************************************/

# --- read HDF file structure ---
n_sds, v_sds_name, n_file_attr, v_file_attr_name = get_file_info(filename)
# --- print it out ---
print("*** Structure of file %s ***"%filename)
# -> sds
print("--- SDS ---");
for i in range(n_sds) :
    print("\t%s"%v_sds_name[i])
# -> file attributes
print("--- FILE ATTRIBUTES ---")
for i in range(n_file_attr) :
    print("\t%s"%v_file_attr_name[i])
print("\n")