FUNCTION printsds, sd_id, id, Nsds
	HDF_SD_GETINFO, sd_id, DIMS=dims, NAME=name, NDIMS=ndims, TYPE=type, NATTS=Natt
	IF KEYWORD_SET(Nsds) THEN IF Nsds GT 0 THEN $
		PRINT, id + 1, format="('SDS n°',I"	+ STRTRIM(STRING(FIX(1 + ALOG10(Nsds)) > 2), 2) + ",': ',$)"
	PRINT, name, type, dims[0], format="(A, 2x, A, ' [', I" + STRTRIM(STRING(FIX(1 + ALOG10(dims[0])))) + ",$)"
	FOR idim = 1, Ndims - 1 DO $
		PRINT, dims[idim], format= '(", ", I' + STRTRIM(STRING(FIX(1 + ALOG10(dims[idim])))) + ",$)"
	PRINT, "]"
	return, Natt
END

