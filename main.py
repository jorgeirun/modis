import sys
import os, fnmatch
import modisProductDownloader
import filesProcessor
import mergeTifFiles
import database
import insertData

# DEFINE VARIABLES
#
startDate 	= "2017-01-01"
endDate 	= "2017-01-31"
coordsWest 	= "-65"
coordsNorth = "-18"
coordsEast 	= "-53"
coordsSouth = "-29"
collection	= "61"

#
# DOWNLOAD FILES
#
# Download files from MODIS repository
# dwnld_MOD35_L2 	= 	modisProductDownloader.downloadFiles("MOD35_L2", collection, startDate, endDate, coordsWest, coordsNorth, coordsEast, coordsSouth)
# dwnld_MOD03	 	= 	modisProductDownloader.downloadFiles("MOD03", collection, startDate, endDate, coordsWest, coordsNorth, coordsEast, coordsSouth)

#
# PROCESS FILES
#
# Process downloaded files
# processFiles = filesProcessor.process("MOD35_L2", "MOD03")

#
# MERGE TIF FILES
#
# List all folder inside tifFiles and merge files within each sub-folder
# check merge in folders with more than 2 tif files
#
# listOfFolders = sorted(os.listdir('tifFiles'))
# pattern = "A*"
# for folder in listOfFolders:
# 	if fnmatch.fnmatch(folder, pattern):
# 		mergeFiles = mergeTifFiles.mergeFilesFromFolder(folder);

#
# DATABASE
#
# Connect to postgres and create db and tables
#
# createDB = database.checkForDB()
# createHeaderTable = database.createHeaderTable()
# createDetailsTable = database.createDetailsTable()
# createModisTable = database.createModisTable()

#
# SAVE DATA TO DB
#
# Connect to DB and save data
#
insertData = insertData.saveData();