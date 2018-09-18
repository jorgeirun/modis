#! /usr/bin/env python
# -*- coding: latin-1 -*-

###################################################################################################################################
#
# @file py_hdf_reader.py
# @brief library of functions for common manipulations of HDF4 files
# @author Nicolas PASCAL (nicolas.pascal@icare.univ-lille1.fr), (C) Centre de Gestion et de Traitement de Données (CGTD) ICARE 2008
# @version 0.0.0
# @date 2008/02/29
#
# Copyright: See COPYING file that comes with this distribution
#
# History :
#   v0.0.0 : creation
#
###################################################################################################################################

from pyhdf import SD
import numpy

__VERSION__="0.0.0"
__AUTHOR__="CGTD/UDEV Nicolas PASCAL (nicolas.pascal@icare.univ-lille1.fr)"

class SDS_INFO:
    """
    @class SDS_INFO represents informations about a SDS
    Those informations are :
        - name the name of the SDS
        - data_type the HDF data type code of the SDS
        - rank the number of dimensions
        - dim_size the size of each dimension
        - n_attr the number of SDS attributes
        - v_attr the list of SDS atributes
    """
    def __init__(self,name="",rank=-1,dim_size=[],data_type=-1,n_attr=-1,v_attr=[]):
        """
        @brief constructor
        @param name the name of the SDS
        @param data_type the HDF data type code of the SDS
        @param rank the number of dimensions
        @param dim_size the size of each dimension
        @param n_attr the number of SDS attributes
        @param v_attr the list of SDS atributes
        """

        self.name = name
        self.data_type = data_type
        self.rank = rank
        self.dim_size = dim_size
        self.n_attr = n_attr
        self.v_attr = v_attr

    def __str__(self):
        """
        @brief return a string representation of the object
        @return a string representation of the object
        """
        s=""
        s+="--- SDS "+self.name+" ---\n"
        s+="Data Type : "+str(self.data_type)+"\n"
        s+="Rank : "+str(self.rank)+"\n"
        s+="Dimensions : "+str(self.dim_size)+"\n"
        s+="Number of attributes : "+str(self.n_attr)+"\n"
        for attr in iter(self.v_attr):
            s+=str(attr)
        return s

class ATTR:
    """
    @class ATTR represents the available informations about an attribute (either a file or a SDS one)
    Those informations are :
        - name the name of the attribute
        - data_type the HDF data type code of the attribute
        - n_val the number of values of this attribute
        - value the value of this attribute
    """
    def __init__(self,name="",data_type=-1,n_val=-1,value=None):
        """
        @brief constructor
        @param name the name of the attribute
        @param data_type the HDF data type code of the attribute
        @param n_val the number of values of this attribute
        @param value the value of this attribute
        """
        self.name = name
        self.data_type = data_type
        self.n_val = n_val
        self.value = value

    def __str__(self):
        """
        @brief return a string representation of the object
        @return a string representation of the object
        """
        s=""
        s+="-> ATTRIBUTE "+self.name+" ---\n"
        s+="Data Type : "+str(self.data_type)+"\n"
        s+="Nb of Values : "+str(self.n_val)+"\n"
        s+="Value : "+str(self.value)+"\n"
        return s

def read_sds_binary_data(filename, sds_name, start = None, stride = None, edge = None):
    """
    read the SDS datas array in an HDF file and return it
    """
    # open the hdf file for reading
    hdf=SD.SD(filename)
    # select the sds to read
    sds=hdf.select(sds_name)
    # read the sds data and return it
    return sds.get(start,edge,stride)

def read_sds_data(filename, sds_name, start = None, stride = None, edge = None):
    """
    read the SDS datas array in an HDF file and return it
    """
    # read uncalibrated data
    uncalibrated_data=read_sds_binary_data(filename, sds_name, start, stride, edge)
    # read calibration
    cal, cal_err, offset, offset_err, data_type = get_sds_calibration(filename, sds_name)
    # apply calibration
    return cal * ( uncalibrated_data + offset )

def get_file_info(filename):
    """
    @brief obtain informations about the structure of an HDF file
    @param filename [IN] name of the HDF file to open
    @return (n_sds, v_sds_name, n_file_attr, v_file_attr_name) where :
    - n_sds number of SDS
    - v_sds_name list of SDS names, sorted by SDS index
    - n_file_attr number of file attributes
    - v_file_attr_name list of file attributes names
    """
    # open the hdf file for reading
    sd=SD.SD(filename)

    # retrieve list of sds
    sds=sd.datasets()
    # create a list of (sds_name,sds_index)
    i_sds_name = [ ( sds[k][3],k ) for k in sds  ]
    # sort it by index
    i_sds_name.sort()
    # build the sorted names list
    v_sds_name = [ k[1] for k in i_sds_name ]
    n_sds = len(v_sds_name)

    # retrieve list of file attributes
    v_file_attr_name = sd.attributes().keys()
    n_file_attr = len(v_file_attr_name)
    return (n_sds, v_sds_name, n_file_attr, v_file_attr_name)

def get_sds_info(filename,sds_name):
    """
    @brief obtain informations about the structure of a SDS
    @param filename [IN] name of the HDF file to open
    @param sds_name [IN] name of the SDS to open
    @return a SDS_INFO object
    """
    # open the hdf file for reading
    sd=SD.SD(filename)
    # select the sds to read
    sds=sd.select(sds_name)
    # read SDS informations and initialize the returned object with it
    sds_name, sds_rank, sds_dim_size, sds_data_type, sds_n_attr = sds.info()
    sds_info = SDS_INFO(sds_name, sds_rank, sds_dim_size, sds_data_type, sds_n_attr)
    # build the list of attributes as (index,name,data_type,n_val,value) sorted by increasing index
    v_sds_attr_info = [ (item[1],name,item[2],item[3],item[0]) for name,item in sds.attributes(full=True).items() ]
    v_sds_attr_info.sort()
    # store all attributes of this list in sds_infos
    for index,name,data_type,n_val,value in iter(v_sds_attr_info):
        # initialize the new attribute object
        attr = ATTR(name=name,data_type=data_type,n_val=n_val,value=value)
        # store it
        sds_info.v_attr.append(attr)
    return sds_info

def get_file_attr(filename, attr_name):
    """
    @brief read a file attribute and return it
    @return a ATTR object or NULL in case of error
    """
    # open the hdf file for reading
    sd=SD.SD(filename)
    # retrieve list of file attributes
    v_file_attr_info = sd.attributes(full=1)
    # check attribute existence
    # print(v_file_attr_info.keys())
    if attr_name not in v_file_attr_info:
        return None
    # read attribute data and return it
    value,index,data_type,n_val = v_file_attr_info[attr_name]
    return ATTR(name=attr_name,data_type=data_type,n_val=n_val,value=value)

def get_sds_attr(filename, sds_name, attr_name):
    """
    @brief read a SDS attribute and return it
    @return a ATTR object or NULL in case of error
    """
    # open the hdf file for reading
    sd=SD.SD(filename)
    # select the sds to read
    sds=sd.select(sds_name)
    # retrieve list of file attributes
    v_file_attr_info = sds.attributes(full=1)
    print(v_file_attr_info.keys())
    # check attribute existence
    if attr_name not in v_file_attr_info:
        return None
    # read attribute data and return it
    value,index,data_type,n_val = v_file_attr_info[attr_name]
    return ATTR(name=attr_name,data_type=data_type,n_val=n_val,value=value)

def get_sds_calibration(filename, sds_name):
    """
    @brief read the calibration of an SDS
    @throw an exception if no calibration set
    @return cal, cal_err, offset, offset_err, data_type where
        - cal the scaling factor
        - cal_err the scaling factor error
        - offset the uncalibrated data offset
        - offset_err the uncalibrated data offset error
        - data_type the HDF data type code of the attribute
    """
    # open the hdf file for reading
    sd=SD.SD(filename)
    # select the sds to read
    sds=sd.select(sds_name)
    # return the calibration
    return sds.getcal()