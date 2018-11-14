import rasterio
import sys
import struct
import os, fnmatch

# Get folder list in tifFiles
listOfFolders = sorted(os.listdir('tifFiles'))
pattern = "A*"
for folder in listOfFolders:
	if fnmatch.fnmatch(folder, pattern):
		print(folder)
		listOfFiles = sorted(os.listdir("tifFiles/"+str(folder)))
		pattern = "*b2_merged.tif"
		for file in listOfFiles:
			if fnmatch.fnmatch(file, pattern):
				print(file)
				dataset = rasterio.open('A2017001_Cloud_Mask_b2_merged.tif')
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
				section = dataset.read(1)
				# here we list rows
				for i, row in enumerate(section):
					# this is a column
					for x, item in enumerate(row):
						# this is an item
						if item > 20:
							counter = counter+ 1
							print("Pixel: "+str(i)+","+str(x))
							print("Value: "+str(item))
							binary = format(item, '08b')
							print("Binary: "+str(binary))
							# print("".join(reversed(format(item, '08b'))))
							print(str(dataset.transform * (i, x))) # print pixel coordinates
							print("Flag: "+binary[7])
							print("CM: "+binary[6]+binary[5])
							print("Day/Night: "+binary[4])
							print("Sunglint flag flint: "+binary[3])
							print("Snow/Ice background flag: "+binary[2])
							print("Land/Water background flag: "+binary[1]+binary[0])
							# sys.exit(1)
				print('Total pixels: '+str(counter))