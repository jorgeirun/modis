import os, fnmatch, time,sys, subprocess
import psycopg2
from psycopg2 import Error
import rasterio


# Server
user = "modis"
password = "modis"
dbname = "modis"

def saveData():
	# Open DB connection
	connection = psycopg2.connect(user = user, password = password, host = "127.0.0.1", port = "5432", dbname = dbname)

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
					print("Inserting Data from "+str(dataset.name))
					# Loop pixels from file and get lat, long and int value
					counter = 0
					section = dataset.read(1)
					for i, row in enumerate(section):
						# this is a column
						for x, item in enumerate(row):
							while item:
								# this is a pixel
								counter = counter + 1
								filename = (str(dataset.name).split("/")[2]).replace('.tif', '')
								latitude = (str(dataset.transform * (i, x)).split(", ")[1]).replace(')', '')
								longitude = (str(dataset.transform * (i, x)).split(", ")[0]).replace('(', '')
								datevalue = (str(dataset.name).split("/")[2]).split('_Cloud')[0]
								pixelvalue = str(item)
								pixelvaluebinary = format(item, '08b')
								cmflag = pixelvaluebinary[7]
								cmconfvalue = pixelvaluebinary[6]+pixelvaluebinary[5]
								query =  "INSERT INTO modis_data (filename, latitude, longitude, datevalue, pixelvalue, pixelvaluebinary, cmflag, cmconfvalue) VALUES (%s, %s, %s, %s, %s, %s, %s, %s);"
								data = (filename, latitude, longitude, datevalue, pixelvalue, pixelvaluebinary, cmflag, cmconfvalue)
								cursor = connection.cursor()
								cursor.execute(query, data)
								connection.commit()
								# Message every 10.000 inserted rows
								if counter % 10000 == 0:
									print ("Inserted "+str(counter)+" rows")
	print("Inserted "+str(counter)+ " rows")