import rasterio

dataset = rasterio.open('A2017001.1355_Cloud_Mask_b1.tif')
# dataset = rasterio.open('A2017001_Cloud_Mask_b1_merged.tif')

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
band = dataset.read(1)
# here we list rows
for i, row in enumerate(band):
	# this is a column
	# print(i)
	# print(row)
	for x, item in enumerate(row):
		# this is an item
		# counter = counter+ 1
		# print(x)
		# print(item)
		if item != 0:
			# print(item)
			counter = counter+ 1
		

print('Total pixels: '+str(counter))