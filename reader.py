#!/usr/bin/env python
# https://www.science-emergence.com/Codes/Plot-MODIS-C6-Cloud-Mask-1km-with-python-and-matplotlib/
#Author: Benjamin Marchant

from pyhdf.SD import SD, SDC

import numpy as np
import matplotlib.pyplot as plt
import matplotlib as mpl
import matplotlib.cm as cm

#----------------------------------------------------------------------------------------#
# Inputs

file_name = 'MOD35_L2.A2017001.1355.061.2017312070506.hdf'

#----------------------------------------------------------------------------------------#
# Read SDS keys

file = SD(file_name, SDC.READ)
print(file.info())
datasets_dic = file.datasets()

print("Keys:")
for idx,sds in enumerate(datasets_dic.keys()):
    print(idx,sds)
print("=====")
#----------------------------------------------------------------------------------------#
# Read HDF Files

# file = SD(file_name, SDC.READ)

# data_selected_id = file.select('Cloud_Mask')

# data = data_selected_id.get()

# # print(data)
# #----------------------------------------------------------------------------------------#
# # Read bits function

# def bits_stripping(bit_start,bit_count,value):
# 	bitmask=pow(2,bit_start+bit_count)-1
# 	return np.right_shift(np.bitwise_and(value,bitmask),bit_start)

# #----------------------------------------------------------------------------------------#
# # Plot data

# #status_flag = bits_stripping(0,1,data[i,j,0])

# cloud_mask_flag = bits_stripping(1,2,data[:,:,0])

# cmap =  [(0.5,0.5,0.5)] + [(0.0,0.0,0.0)]
# cmap = mpl.colors.ListedColormap(cmap)

# bounds = [-0.5, 1.5, 3.5]

# norm = mpl.colors.BoundaryNorm(bounds, cmap.N)

# img = plt.imshow(np.fliplr(cloud_mask_flag), cmap=cmap, norm=norm,
# 				  interpolation='none', origin='lower')

# cbar_bounds = [-0.5, 1.5, 3.5]
# cbar_ticks = [0.5,2.5]
# cbar_labels = ['Cloudy','Clear']

# cbar = plt.colorbar(img, cmap=cmap, norm=norm, boundaries=cbar_bounds, ticks=cbar_ticks)
# cbar.ax.set_yticklabels(cbar_labels, fontsize=10)

# plt.title('Cloud Mask 1km \n MYD06 C6 (2008-01-15; 14h35)', fontsize=10)

# plt.savefig("myd06_c6_cloud_mask_1km_baseline.png",
# 			bbox_inches='tight', dpi=200)
# plt.show()