import sys
from osgeo import gdal
import numpy as np
import rasterio
from rasterio.merge import merge
from rasterio.plot import show
import glob
import os

def main(argv):
    print(get_value(*argv[1:]))

if __name__ == "__main__":
    import sys
    main(sys.argv)

def mergeFilesFromFolder(*args):
	print("===== Step 3: Merge TIF files =====")
	date = list(map(str, args))[0] # get date/folder name from args
	dirpath = os.getcwd()+"/tifFiles/"+date
	# Make a search criteria to select the DEM files
	bands = ["b0", "b1", "b2", "b3", "b4", "b5"]
	for band in bands:
		# output file pathname
		out_fp = os.getcwd()+"/tifFiles/"+date+"/"+date+"_Cloud_Mask_"+band+"_merged.tif"
		search_criteria = "*"+band+".tif"
		q = os.path.join(dirpath, search_criteria)
		print(q)
		dem_fps = glob.glob(q)
		print(dem_fps)
		src_files_to_mosaic = []
		for fp in dem_fps:
			src = rasterio.open(fp)
			src_files_to_mosaic.append(src)
		print(src_files_to_mosaic[0])
		# sys.exit(1)
		mosaic, out_trans = merge(src_files_to_mosaic)
		# show(mosaic, cmap='terrain')
		out_meta = src.meta.copy()
		# Update the metadata
		out_meta.update({"driver": "GTiff","height": mosaic.shape[1],"width": mosaic.shape[2],"transform": out_trans,"crs": "+proj=utm +zone=35 +ellps=GRS80 +units=m +no_defs "})
		with rasterio.open(out_fp, "w", **out_meta) as dest:
			dest.write(mosaic)

	print("======== Step 3 Completed =========")