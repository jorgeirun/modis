import os
import sys
import subprocess

def main(argv):
    print(get_value(*argv[1:]))

if __name__ == "__main__":
    import sys
    main(sys.argv)

def process(*args):
	print("===== Step 2: Process Files ======")
	# collection to save all items separated by product
	lists = {}
	# read all args
	argsArray = list(map(str, args))
	# print(argsArray)
	# sys.exit(1)
	for item in argsArray:
		productName = item
		# Get the current work directory (cwd)
		currentDir = os.getcwd()
		# Get file list from each folder (ls)
		filesInFolder = os.listdir(currentDir+'/'+productName)
		# Sort array by filename and save in 'lists' collection using product name as key
		lists[productName] = sorted(filesInFolder, key = str.lower)
	# print(lists)

	for item1, item2 in zip(lists["MOD35_L2"], lists["MOD03"]):
		# process CloudMask and GeoLocation files in order to get GeoTiff images
		# print(item1, " - ", item2)
		if item1[0] != ".": # this is to avoid using ".DS_Store" hidden file created in macosx
			# create TIFF file name
			tmptifFileName = item1.split(".")
			tifFileName = tmptifFileName[1]+"."+tmptifFileName[2]
			# create path to save tiff files
			folderName = "tifFiles/"+tmptifFileName[1] # tifFiles folder name and files date
			if not os.path.exists(folderName):
				os.makedirs(folderName)
			tifFilePath = os.getcwd()+"/"+folderName
			command = "MRTSwath/bin/swath2grid -if=MOD35_L2/"+item1+" -of="+tifFilePath+"/"+tifFileName+".tif -gf=MOD03/"+item2+" -pf=all-bands-defaultparametersfile.prm"
			# command = "MRTSwath/bin/swath2grid -if=MOD35_L2/"+item1+" -of="+tifFilePath+"/"+tifFileName+".tif -gf=MOD03/"+item2+" -pf=first-band-defaultparametersfile.prm"
			completed = subprocess.run(command, shell=True)
			print('returncode:', completed)
			# print(tmptifFileName[1])
			# Stop after day 100
			# if tmptifFileName[1] == "A2017100":
				# sys.exit(1)

	print("======== Step 2 Completed =========")