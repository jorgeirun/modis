import sys
import os
import requests
import xml.etree.ElementTree as ET

def main(argv):
    print(get_value(*argv[1:]))

if __name__ == "__main__":
    import sys
    main(sys.argv)

#  Download files
def downloadFiles(*args):
	product = list(map(str, args))[0] # get product code from args
	print("===== Step 1: Download "+product+" Files ======")
	collection = list(map(str, args))[1] # get start date from args
	startDate = list(map(str, args))[2] # get start date from args
	endDate = list(map(str, args))[3] # get end date from args
	coordsOrTiles = "coords" # coords, tiles ?
	coords = "north="+list(map(str, args))[5]+"&south="+list(map(str, args))[7]+"&west="+list(map(str, args))[4]+"&east="+list(map(str, args))[6]
	dayNightBoth = "D" # D (default), DN, DNB
	url = "https://modwebsrv.modaps.eosdis.nasa.gov/axis2/services/MODAPSservices/searchForFiles?product="+product+"&collection="+collection+"&start="+startDate+"&stop="+endDate+"&"+coords+"&coordsOrTiles="+coordsOrTiles+"&dayNightBoth="+dayNightBoth
	# print(url)
	# create folder if it not exists
	if not os.path.exists(product):
		print("Folder "+product+" created")
		os.makedirs(product)
	else:
		print("File will be downloaded in folder: "+product)

	# Get codes, urls  and download files
	codesRequest = requests.get(url)
	if codesRequest.status_code == 200 :
		itemCode = ET.fromstring(codesRequest.text)
		print(str(len(itemCode))+" files found")
		for code in itemCode.iter('return'):
			pathRequest  = requests.get("https://modwebsrv.modaps.eosdis.nasa.gov/axis2/services/MODAPSservices/getFileUrls?fileIds="+code.text)
			if pathRequest.status_code == 200 :
				itemPath = ET.fromstring(pathRequest.text)
				for path in itemPath.iter('return'):
					filename = path.text.rsplit('/', 1)[-1]
					# check if file already exists
					if os.path.exists(product+"/"+filename):
						print(filename+" file already exists")
					else:
						# requesting metadata
						response = requests.head(path.text)
						# print("Downloading "+filename+" ("+str(int(response.headers['Content-length'])/1000000.0)+" MB)...")
						print("Downloading "+filename)
						# download file
						downloadRequest = requests.get(path.text)
						with open(product+"/"+filename, "wb") as code:
							code.write(downloadRequest.content)
	print(product+" files downloaded.")
	print("======== Step 1  "+product+" Completed =========")