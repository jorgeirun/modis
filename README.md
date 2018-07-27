MODIs Cloud Mask (and geolocation files) downloader

Components:
- main.py
File that run functions in other files, no processes in thi file

- modisProductDownloader.py
This file downloads Cloud Mask and geolocation files acording to the parameters entered in main.py

- filesProcessor.py
This file get the HDF downloaded previously and process them to generate the tiff files

- mergeTifFiles.py
This file gets all tif from a specific date and merges them to have only one tif file, saving it in the same folder




Requirements:
-------------

* Install MRTSwath in same folder

* Python libs
- numpy
- os
- gdal
- glob
- rasterio
- pyhdf
