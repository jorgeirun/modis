from py_hdf_reader import *

filename = 'MOD35_L2.A2017001.1355.061.2017312070506.hdf'
sds_name = 'Cloud_Mask'

data = get_sds_info(filename, sds_name);
print(data)