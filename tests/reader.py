# General
# https://www.science-emergence.com/Libraries/Plot-MODIS-level-2-cloud-optical-properties-with-python-gallery/

# https://www.science-emergence.com/Articles/How-to-read-a-MODIS-HDF-file-using-python-/
#Author: Benjamin Marchant

from pyhdf.SD import SD, SDC

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
file = SD(file_name, SDC.READ)
data_selected_id = file.select('Cloud_Mask')
data = data_selected_id.get()
print(data[0])