import sys
import subprocess
import modisProductDownloader
import filesProcessor
import os

#
# TEST Section
#
# item1 = "MOD35_L2.A2017001.1355.061.2017312070506.hdf"
# item2 = "MOD03.A2017001.1355.061.2017312061337.hdf"
# item3 = item1.split(".")
# print(item3[1])
# print(item3[2])
# sys.exit()
#
# END TEST Section
#


#
# VARS
#
startDate 	= "2017-01-01"
endDate 	= "2017-01-01"
coordsWest 	= "-65"
coordsNorth = "-18"
coordsEast 	= "-53"
coordsSouth = "-29"
collection	= "61" 

print("===================================")
print("===== Step 1: Download Files ======")
print("===================================")
#
# Download files from MODIS repository
# REFERENCE: modisProductDownloader.downloadFiles(productCode, collection, startDate, endDate, coordsWest, coordsNorth, coordsEast, coordsSouth)
#
dwnld_MOD35_L2 	= 	modisProductDownloader.downloadFiles("MOD35_L2", collection, startDate, endDate, coordsWest, coordsNorth, coordsEast, coordsSouth)
dwnld_MOD03 	= 	modisProductDownloader.downloadFiles("MOD03", collection, startDate, endDate, coordsWest, coordsNorth, coordsEast, coordsSouth)

# dwnld_MYD35_L2 	= 	modisProductDownloader.downloadFiles("MYD35_L2", collection, startDate, endDate, coordsWest, coordsNorth, coordsEast, coordsSouth)
# dwnld_MYD03 	= 	modisProductDownloader.downloadFiles("MYD03", collection, startDate, endDate, coordsWest, coordsNorth, coordsEast, coordsSouth)

print("===================================")
print("======== Step 1 Completed =========")
print("===================================")

print("===================================")
print("====== Step 2: Process Files ======")
print("===================================")
#
# Process downloaded files
# 
processFiles = filesProcessor.process("MOD35_L2", "MOD03")
# processFiles = filesProcessor.process("MYD35_L2", "MYD03")

print("===================================")
print("======== Step 2 Completed =========")
print("===================================")