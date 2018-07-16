import os
import subprocess

def main(argv):
    print(get_value(*argv[1:]))

if __name__ == "__main__":
    import sys
    main(sys.argv)

def process(*args):
	# collection to save all items separated by product
	lists = {}
	# read all args
	argsArray = list(map(str, args))
	for item in argsArray:
		productName = item
		# Get the current work directory (cwd)
		currentDir = os.getcwd()
		# Get file list from each folder (ls)
		filesInFolder = os.listdir(currentDir+'/'+productName)
		# Sort array by filename and save in 'lists' collection using product name as key
		lists[productName] = sorted(filesInFolder, key = str.lower)
	#
	for item1, item2 in zip(lists["MOD35_L2"], lists["MOD03"]):
		# process CloudMask and GeoLocation files in order to get GeoTiff images
		print(item1, " - ", item2)
		command = "MRTSwath/bin/swath2grid -if=MOD35_L2/"+item1+" -of=sample.tif -gf=MOD03/"+item2+" -pf=defaultparametersfile.prm"
		print('===== Completed =====')
		completed = subprocess.run(command, shell=True)
		print('returncode:', completed)
