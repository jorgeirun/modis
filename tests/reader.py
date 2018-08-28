# General
# https://www.science-emergence.com/Libraries/Plot-MODIS-level-2-cloud-optical-properties-with-python-gallery/

# https://www.science-emergence.com/Articles/How-to-read-a-MODIS-HDF-file-using-python-/
#Author: Benjamin Marchant

from pyhdf.SD import SD, SDC

#----------------------------------------------------------------------------------------#
# Inputs
file_name = 'MOD35_L2.A2017001.1355.061.2017312070506.hdf'
sdskey = 'Cloud_Mask'
#----------------------------------------------------------------------------------------#
# Read SDS keys
file = SD(file_name, SDC.READ)
# print(file.info())
#----------------------------------------------------------------------------------------#
# Read HDF Files - Cloud Mask
data_selected_id = file.select(sdskey)
data = data_selected_id.get()
# print('Counts: ')
# print(len(data[4]))
#----------------------------------------------------------------------------------------#
# List SDS Keys
datasets_dic = file.datasets()
print("Keys:")
for idx,sds in enumerate(datasets_dic.keys()):
    print(idx,sds)
print("=====")
#----------------------------------------------------------------------------------------#
# Read HDF Files - Cloud Mask
# data_selected_id = file.select(sdskey)
# data = data_selected_id.get()
# print(sdskey)
# print(data)
#----------------------------------------------------------------------------------------#
# Get attributes
# import pprint
# pprint.pprint( data_selected_id.attributes() )
# for key, value in data_selected_id.attributes().iteritems():
    # print (key, value)
    # if key == 'add_offset':
        # add_offset = value  
    # if key == 'scale_factor':
        # scale_factor = value
# print('add_offset', add_offset, type(add_offset))
# print('scale_factor', scale_factor, type(scale_factor))
# data = (data - add_offset) * scale_factor
# print(data)
#----------------------------------------------------------------------------------------#
# Example with Cloud_Mask_1km
# import pprint
# sds_obj = file.select(sdskey) # select sds
# data = sds_obj.get()
# pprint.pprint( sds_obj.attributes() )
# print(data.shape)
#----------------------------------------------------------------------------------------#
# Extract info from a byte
# import numpy as np
# def bits_stripping(bit_start,bit_count,value):
#     bitmask = pow(2, bit_start + bit_count) - 1
#     return np.right_shift(np.bitwise_and(value,bitmask),bit_start)
# i = 5 # random pixel
# j = 5
# print("=====")
# print("Pixels: " + str(i) + "," + str(j))
# status_flag = bits_stripping(0,1,data[i,j,0]) 
# day_flag = bits_stripping(3,1,data[i,j,0]) 
# cloud_mask_flag = bits_stripping(1,2,data[i,j,0])
# print("=====")
# print('Cloud Mask determined: '+  str(status_flag))
# print('Cloud Mask day or night: '+  str(day_flag))
# print('Cloud Mask: '+  str(cloud_mask_flag))
# print("=====")