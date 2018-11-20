'''
This list all data from TIF file.
Width: 2784
Height: 2250

'''
import rasterio
import sys
import struct

dataset = rasterio.open('A2017001_Cloud_Mask_b2_merged.tif')

# print('Name: '+str(dataset.name))
# print('Width: '+str(dataset.width))
# print('Height: '+str(dataset.height))
# print('Type: '+str({i: dtype for i, dtype in zip(dataset.indexes, dataset.dtypes)}))
# print('Bounds: '+str(dataset.bounds))
# print('pixel 0,0: '+str(dataset.transform * (0, 0)))
# print('Last Pixel: '+str(dataset.transform * (dataset.width, dataset.height)))
# print('CRS: '+str(dataset.crs))
# print('Indexes: '+str(dataset.indexes))

# print("Pixel: "+str(i)+","+str(x))
# print("Value: "+str(item))
# binary = format(item, '08b')
# print("Binary: "+str(binary))

# print('CRS: '+str(dataset.crs))
# print("Flag: "+binary[7])
# print("CM: "+binary[6]+binary[5])
# print("Day/Night: "+binary[4])
# print("Sunglint flag flint: "+binary[3])
# print("Snow/Ice background flag: "+binary[2])
# print("Land/Water background flag: "+binary[1]+binary[0])

print('========')
binary = format(29, '08b')
print("Cloud Free: "+str(binary))
# binaryreversed = binary[6] + binary[7] + binary[4] + binary[5] + binary[2] + binary[3] + binary[0] + binary[1]
print("Flag: "+binary[7])
print("CM: "+binary[6]+binary[5])
print("Day/Night: "+binary[4])
print("Sunglint flag flint: "+binary[3])
print("Snow/Ice background flag: "+binary[2])
print("Land/Water background flag: "+binary[1]+binary[0])
print('========')
binary = format(5, '08b')
print("Cloudy: "+str(binary))
# binaryreversed = binary[6] + binary[7] + binary[4] + binary[5] + binary[2] + binary[3] + binary[0] + binary[1]
print("Flag: "+binary[7])
print("CM: "+binary[6]+binary[5])
print("Day/Night: "+binary[4])
print("Sunglint flag flint: "+binary[3])
print("Snow/Ice background flag: "+binary[2])
print("Land/Water background flag: "+binary[1]+binary[0])
print('========')
binary = format(60, '08b')
print("Snow/Ice: "+str(binary))
# binaryreversed = binary[6] + binary[7] + binary[4] + binary[5] + binary[2] + binary[3] + binary[0] + binary[1]
print("Flag: "+binary[7])
print("CM: "+binary[6]+binary[5])
print("Day/Night: "+binary[4])
print("Sunglint flag flint: "+binary[3])
print("Snow/Ice background flag: "+binary[2])
print("Land/Water background flag: "+binary[1]+binary[0])
print('========')
binary = format(125, '08b')
print("Water: "+str(binary))
# binaryreversed = binary[6] + binary[7] + binary[4] + binary[5] + binary[2] + binary[3] + binary[0] + binary[1]
print("Flag: "+binary[7])
print("CM: "+binary[6]+binary[5])
print("Day/Night: "+binary[4])
print("Sunglint flag flint: "+binary[3])
print("Snow/Ice background flag: "+binary[2])
print("Land/Water background flag: "+binary[1]+binary[0])
print('========')
binary = format(17, '08b')
print("Black (cloud): "+str(binary))
# binaryreversed = binary[6] + binary[7] + binary[4] + binary[5] + binary[2] + binary[3] + binary[0] + binary[1]
print("Flag: "+binary[7])
print("CM: "+binary[6]+binary[5])
print("Day/Night: "+binary[4])
print("Sunglint flag flint: "+binary[3])
print("Snow/Ice background flag: "+binary[2])
print("Land/Water background flag: "+binary[1]+binary[0])
print('========')

sys.exit(1)

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
			print(str(dataset.transform * (i, x))) # print pixel coordinates
		

print('Total pixels: '+str(counter))
print('Total pixels non "0": '+str(nonCeroCounter))