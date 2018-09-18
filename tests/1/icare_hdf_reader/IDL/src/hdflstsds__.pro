FUNCTION liste_sds, file_handle
	PRINT, 'Available parameters:'
	HDF_SD_FILEINFO, file_handle, Nsds, Natt
	data = STRARR(Nsds)
	FOR id = 0, Nsds - 1 DO BEGIN
		sd_id = HDF_SD_SELECT(file_handle, id)
		HDF_SD_GETINFO, sd_id, NAME=name
		data[id] = name
		PRINT, name
	ENDFOR
	RETURN, data
END

