import os, fnmatch
import sys
import subprocess
import psycopg2
from psycopg2 import Error
import rasterio


def saveData():
	# Open DB connection
	# connection = psycopg2.connect(user = "postgres", password = "postgres", host = "127.0.0.1", port = "5432", database = "modis")

	# Loop folders to get files (b2_merged.tif)
	listOfFolders = sorted(os.listdir('tifFiles'))
	folderPattern = "A*"
	for folder in listOfFolders:
		if fnmatch.fnmatch(folder, folderPattern):
			listOfFiles = sorted(os.listdir("tifFiles/"+str(folder)))
			filePattern = "*b2_merged.tif"
			for file in listOfFiles:
				if fnmatch.fnmatch(file, filePattern):
					# Open file
					dataset = rasterio.open("tifFiles/"+str(folder)+"/"+str(file))
					print('Name: '+str(dataset.name))
					# Loop pixels from file and get lat, long and int value
					counter = 0
					section = dataset.read(1)
					for i, row in enumerate(section):
						# this is a column
						for x, item in enumerate(row):
							# this is a pixel
							counter = counter + 1
							print("Pixel: "+str(i)+","+str(x))
							print("Value: "+str(item))
							binary = format(item, '08b')
							print("Binary: "+str(binary))
							print("Latitude: " + (str(dataset.transform * (i, x)).split(", ")[1]).replace(')', ''))
							print("Longitude: " + (str(dataset.transform * (i, x)).split(", ")[0]).replace('(', ''))
							print('CRS: '+str(dataset.crs))
							print("Flag: "+binary[7])
							print("CM: "+binary[6]+binary[5])
							print("Day/Night: "+binary[4])
							print("Sunglint flag flint: "+binary[3])
							print("Snow/Ice background flag: "+binary[2])
							print("Land/Water background flag: "+binary[1]+binary[0])
							sys.exit(1)
	

	# Insert Data
	# Insert test data
	
	# query =  "INSERT INTO modis_data (filename, filedate, latitude, longitude, pixel_value, pixel_binary_value, cm_flag, cm_value) VALUES (%s, %s, %s, %s, %s, %s, %s, %s);"
	# data = ("A124431", "2017001", "45,233424", "67,324252", "29", "01010011", "01", "11")
	# cursor = connection.cursor()
	# cursor.execute(query, data)
	# connection.commit()
	# sys.exit(1)