'''
This list all data from TIF file.
Width: 2784
Height: 2250

'''
import rasterio
import sys
import struct

# dataset = rasterio.open('A2017001.1355_Cloud_Mask_b0.tif')
# dataset = rasterio.open('A2017001.1355_Cloud_Mask_b1.tif')
# dataset = rasterio.open('A2017001.1355_Cloud_Mask_b2.tif')
dataset = rasterio.open('A2017001_Cloud_Mask_b2_merged.tif')
# dataset = rasterio.open('A2017001.1355_Cloud_Mask_b3.tif')
# dataset = rasterio.open('A2017001.1355_Cloud_Mask_b4.tif')
# dataset = rasterio.open('A2017001.1355_Cloud_Mask_b5.tif')
# dataset = rasterio.open('A2017001_Cloud_Mask_b1_merged.tif')
# dataset = rasterio.open('MOD35_L2.A2017001.1355.061.2017312070506_mod35.tif')

print('Name: '+str(dataset.name))
print('Width: '+str(dataset.width))
print('Height: '+str(dataset.height))
print('Type: '+str({i: dtype for i, dtype in zip(dataset.indexes, dataset.dtypes)}))
print('Bounds: '+str(dataset.bounds))
print('pixel 0,0: '+str(dataset.transform * (0, 0)))
print('Last Pixel: '+str(dataset.transform * (dataset.width, dataset.height)))
print('CRS: '+str(dataset.crs))
print('Indexes: '+str(dataset.indexes))

print('========')

counter = 0
nonCeroCounter = 0

band = dataset.read(1)

# here we list rows
for i, row in enumerate(band):
	# this is a column
	# print(i)
	for x, item in enumerate(row):
		# this is an item
		counter = counter+ 1
		# print(x)
		# print(str(i)+" "+str(x))
		# print(str(dataset.transform * (i, x))) # print pixel coordinates
		# print(item) # this is a pixel value (??)
		if item != 0:
			nonCeroCounter = nonCeroCounter + 1
			print("Pixel: "+str(i)+","+str(x))
			print("Value: "+str(item))
			print(format(item, '08b'))
			print("".join(reversed(format(item, '08b'))))
			# print(struct.unpack('B', item)[0])
			print(str(dataset.transform * (i, x))) # print pixel coordinates
		

print('Total pixels: '+str(counter))
print('Total pixels non "0": '+str(nonCeroCounter))