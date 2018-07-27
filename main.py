import sys
import modisProductDownloader
import filesProcessor
import mergeTifFiles

# TEST Section
# item1 = "MOD35_L2.A2017001.1355.061.2017312070506.hdf"
# item2 = "MOD03.A2017001.1355.061.2017312061337.hdf"
# item3 = item1.split(".")
# print(item3[1])
# print(item3[2])
# mergeFiles = mergeTifFiles.mergeFilesFromFolder("A2017001")
# sys.exit()
# END TEST Section


# DEFINE VARS
#
startDate 	= "2017-01-01"
endDate 	= "2017-01-01"
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
# dwnld_MOD03 	= 	modisProductDownloader.downloadFiles("MOD03", collection, startDate, endDate, coordsWest, coordsNorth, coordsEast, coordsSouth)

#
# PROCESS FILES
#
# Process downloaded files
# processFiles = filesProcessor.process("MOD35_L2", "MOD03")

#
# MERGE TIF FILES
#
mergeFiles = mergeTifFiles.mergeFilesFromFolder("A2017001") # TODO: IN PROGRESS