import sys
import subprocess
import modisProductDownloader
import filesProcessor
import os


#
# TEST Section
#

# item1 = "MOD35_L2/MOD35_L2.A2017001.1355.061.2017312070506.hdf"
# item2 = "MOD03/MOD03.A2017001.1355.061.2017312061337.hdf"
# command = "MRTSwath/bin/swath2grid -if="+item1+" -of=sample.tif -gf="+item2+" -pf=defaultparametersfile.prm"
# print('===== Completed =====')
# completed = subprocess.run(command, shell=True)
# print('returncode:', completed)
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

print("===================================")
print("===== Step 1: Download Files ======")
print("===================================")
#
# Download files from MODIS repository
# REFERENCE: modisProductDownloader.downloadFiles(productCode, collection, startDate, endDate, coordsWest, coordsNorth, coordsEast, coordsSouth)
#
dwnld_MOD35_L2 	= 	modisProductDownloader.downloadFiles("MOD35_L2", "61", startDate, endDate, coordsWest, coordsNorth, coordsEast, coordsSouth)
dwnld_MOD03 	= 	modisProductDownloader.downloadFiles("MOD03", "61", startDate, endDate, coordsWest, coordsNorth, coordsEast, coordsSouth)

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

print("===================================")
print("======== Step 2 Completed =========")
print("===================================")