PRO printatt, sd_id, Natt

	; Une première passe pour préparer les formats
	mxnam = 0 & mxtyp = 0 & mxcnt = 0
	FOR ia = 0, Natt-1 DO BEGIN
		HDF_SD_ATTRINFO, sd_id, ia, COUNT=count, NAME=name, TYPE=type
		mxnam = mxnam > strlen(name)
		mxtyp = mxtyp > strlen(type)
		mxcnt = mxcnt > count
	ENDFOR
	tfmt = "(A"	+ STRTRIM(STRING(FIX(1 + ALOG10(Natt)) > 2), 2) + ",2x,A" + STRTRIM(STRING(mxnam>4), 2) + ",2x,A" $
		+ STRTRIM(STRING(FIX(1 + ALOG10(mxcnt)) > 3), 2) + ",2x,A" + STRTRIM(STRING(mxtyp > 4), 2) + ",2x,A)"
	fmt  = "(I"	+ STRTRIM(STRING(FIX(1 + ALOG10(Natt)) > 2), 2) + ",2x,A" + STRTRIM(STRING(mxnam>4), 2) + ",2x,I" $
		+ STRTRIM(STRING(FIX(1 + ALOG10(mxcnt)) > 3), 2) + ",2x,A" + STRTRIM(STRING(mxtyp > 4), 2) + ",$)"

	; Une seconde passe pour afficher les résultats
	PRINT, "N°", "Name", "Dim", "Type", "Values", format=tfmt
	FOR ia = 0, Natt - 1 DO BEGIN
		HDF_SD_ATTRINFO, sd_id, ia, COUNT=count, NAME=name, DATA=data, HDF_TYPE=htype, TYPE=type
		PRINT, ia + 1, name, count, type, format=fmt
		CASE type OF
		'STRING' : PRINT, data, format='(2x, A)'
		'BYTE'   : PRINT, data[0:(count-1)<19], format=("(20I4"   + (count le 20 ? ")" : ",2x,'...')"))
		'INT'    : PRINT, data[0:(count-1)<10], format=("(11I8"   + (count le 11 ? ")" : ",2x,'...')"))
		'UINT'   : PRINT, data[0:(count-1)<10], format=("(11I8"   + (count le 11 ? ")" : ",2x,'...')"))
		'LONG'   : PRINT, data[0:(count-1)< 7], format=("(8I12"   + (count le  8 ? ")" : ",2x,'...')"))
		'ULONG'  : PRINT, data[0:(count-1)< 7], format=("(8I12"   + (count le  8 ? ")" : ",2x,'...')"))
		'FLOAT'  : PRINT, data[0:(count-1)<10], format=("(11F8.3" + (count le 11 ? ")" : ",2x,'...')"))
		'DOUBLE' : PRINT, data[0:(count-1)<10], format=("(11D8.3" + (count le 11 ? ")" : ",2x,'...')"))
		ELSE     : PRINT, data
		ENDCASE
	ENDFOR
END

