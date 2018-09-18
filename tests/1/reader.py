# General
# https://www.science-emergence.com/Libraries/Plot-MODIS-level-2-cloud-optical-properties-with-python-gallery/

# https://www.science-emergence.com/Articles/How-to-read-a-MODIS-HDF-file-using-python-/
#Author: Benjamin Marchant

from pyhdf.SD import SD, SDC

#----------------------------------------------------------------------------------------#
# Inputs
file_name = 'MOD35_L2.A2017001.1355.061.2017312070506.hdf'
# sdskey = 'Latitude'
# sdskey = 'Longitude'
sdskey = 'Cloud_Mask'
# sdskey = 'Quality_Assurance'
#----------------------------------------------------------------------------------------#
# Read SDS keys
file = SD(file_name, SDC.READ)
# print(file.info())
#----------------------------------------------------------------------------------------#
# List SDS Keys
datasets_dic = file.datasets()
print("=====")
print("Keys:")
for idx,sds in enumerate(datasets_dic.keys()):
    print(idx,sds)
print("=====")
# print("Selected Key: "+sdskey)
#----------------------------------------------------------------------------------------#
# Read HDF Files - Cloud Mask
data_selected_id = file.select(sdskey)
data = data_selected_id.get()
# print('Counts: ')
# print(len(data))
# print("==========")
print(data[0])
print("==========")
# print("==========")
# print(data[1])
# print("==========")
# print(data[2])
# print("==========")
# print(data[3])
# print("==========")
# print(data[4])
# print("==========")
# print(data[5])
# print("=====")
#----------------------------------------------------------------------------------------#
# Read HDF Files - Cloud Mask
data_selected_id = file.select(sdskey)
data = data_selected_id.get()
# print(data)
print(data.shape)
print("==========")
#----------------------------------------------------------------------------------------#
# Example with Cloud_Mask
# import pprint
# sds_obj = file.select(sdskey) # select sds
# data = sds_obj.get()
# pprint.pprint( sds_obj.attributes() )
#----------------------------------------------------------------------------------------#
# Extract info from a byte
import numpy as np
def bits_stripping(bit_start,bit_count,value):
    bitmask = pow(2, bit_start + bit_count) - 1
    return np.right_shift(np.bitwise_and(value,bitmask),bit_start)
i = 0 # random pixel
j = 0
# print("Pixels: " + str(i) + "," + str(j))
# status_flag = bits_stripping(0,1,data[i,j,0]) 
# # day_flag = bits_stripping(3,1,data[i,j,0]) 
# cloud_mask_flag = bits_stripping(1,2,data[i,j,0])
# print("=====")
# print('Cloud Mask determined: '+  str(status_flag))
# # print('Cloud Mask day or night: '+  str(day_flag))
# print('Cloud Mask Flag: '+  str(cloud_mask_flag))
# print("=====")

# for i in range(len(data)):
# 	# print("--"+str(i))
# 	for j in range(len(data[0])):
# 		# print("----"+str(j))
# 		print("Pixels: " + str(i) + "," + str(j))
# 		status_flag = bits_stripping(0,1,data[i,j,0]) 
# 		# day_flag = bits_stripping(3,1,data[i,j,0]) 
# 		cloud_mask_flag = bits_stripping(1,2,data[i,j,0])
# 		print('Cloud Mask determined: '+  str(status_flag))
# 		# print('Cloud Mask day or night: '+  str(day_flag))
# 		print('Cloud Mask Flag: '+  str(cloud_mask_flag))
# 		print("=====")