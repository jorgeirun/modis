import sys
import modisProductDownloader
import filesProcessor
import mergeTifFiles

# DEFINE VARIABLES
#
startDate 	= "2017-11-01"
endDate 	= "2017-11-30"
coordsWest 	= "-65"
coordsNorth = "-18"
coordsEast 	= "-53"
coordsSouth = "-29"
collection	= "61"

#
# DOWNLOAD FILES
#
# Download files from MODIS repository
dwnld_MOD35_L2 	= 	modisProductDownloader.downloadFiles("MOD35_L2", collection, startDate, endDate, coordsWest, coordsNorth, coordsEast, coordsSouth)
dwnld_MOD03	 	= 	modisProductDownloader.downloadFiles("MOD03", collection, startDate, endDate, coordsWest, coordsNorth, coordsEast, coordsSouth)

#
# PROCESS FILES
#
# Process downloaded files
processFiles = filesProcessor.process("MOD35_L2", "MOD03")

#
# MERGE TIF FILES
#
# List all folder inside tifFiles and merge files within each sub-folder
# check merge in folders with more than 2 tif files
#