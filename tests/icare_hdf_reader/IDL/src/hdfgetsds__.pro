;==========================================================================================================
; hdfgetsds.pro
;==========================================================================================================
; AUTEUR : F.M. Breon, Septembre 2006
;==========================================================================================================
; Cette fonction permet de r�cup�rer facilement un Scientific dataset d'un fichier HDF.
; Plusieurs autres fonctions d'information si les param�tres ne sont pas donn�s.
;
; USAGE:  resu = hdfgetsds(FILEIN=<nom_du_fichier>, PARAMETER=<nom_du_SDS>, /INFO, /BINARY_VALUE)
;
; Tous les parametres sont optionnels.
;
; FILEIN=filein   : Le nom du fichier HDF � lire. Si absent, un dialogue appara�t
; PARAMETER=param : Nom court du param�tre a r�cup�rer.
; /INFO           : Pour obtenir des informations suppl�mentaires (attributs) sur le param�tre en question
; /BINARY         : Pour retourner les valeurs binaires (par d�faut, valeurs physiques)
;
; EXEMPLES
; liste_param = hdfgetsds()
;		Ouvre un dialogue pour choisir le fichier,
;		Retourne la liste des param�tres accessibles
;
; liste_param = hdfgetsds(FILEIN=filein)
;		Retourne la liste des param�tres accessibles
;
; data = hdfgetsds(FILEIN=filein, PARAMETER=param).
;		Retourne le param�tre "param" en valeurs physiques
;
; data = hdfgetsds(FILEIN=filein, PARAMETER=param, /INFO).
;		Retourne le param�tre "param" et imprime ses attributs
;
; data = hdfgetsds(FILEIN=filein, PARAMETER=param, /BINARY).
;		Retourne le param�tre "param" en valeurs de codage
;
; Lorsque le fichier n'est pas trouv�, la fonction retourne -1
; Lorsque le param�tre choisi n'est pas disponible, la fonction retourne -2
;
;==========================================================================================================
; MODIFS:
; - Mars 2008 - SIX Bruno - ICARE-USTL
;   Affinement des affichages tabul�s et simplification (proc�dure d'affichage commune des attributs)
;==========================================================================================================

FUNCTION hdfgetsds, FILEIN=filein, PARAMETER=param, INFO=info, BINARY=binary
	FORWARD_FUNCTION liste_sds, printsds

	IF NOT KEYWORD_SET(filein) THEN filein = DIALOG_PICKFILE(FILTER='*.hdf')

	file_handle = HDF_SD_START(filein, /READ)
	IF file_handle LE 0 THEN BEGIN
		PRINT, ' File not found'
		RETURN, -1
	ENDIF

	IF NOT KEYWORD_SET(param) THEN BEGIN
		data = liste_sds(file_handle)
		PRINT, 'More info with the command: HDFdump, FILEIN=filein, /NOSTOP'
		GOTO, FIN
	ENDIF

	id = HDF_SD_NAMETOINDEX(file_handle, param)
	IF id LT 0 THEN BEGIN
		PRINT, format="('This parameter is not available. ',$)"
		data = liste_sds(file_handle)
		data = -2
		GOTO, FIN
	ENDIF

	sd_id = HDF_SD_SELECT(file_handle, id)
	HDF_SD_GETDATA, sd_id, data

	IF KEYWORD_SET(info) THEN BEGIN
		Natts = printsds(sd_id)
		PRINT, "--------------------------------------------------------------------------------------------------"
		IF Natts GT 0 THEN printatt, sd_id, Natts
	ENDIF

	IF NOT KEYWORD_SET(binary) THEN BEGIN
		HDF_SD_GETINFO, sd_id, NATTS=Natts
		scale = -999 & offset = -999
		FOR ia = 0, natts - 1 DO BEGIN
			HDF_SD_ATTRINFO, sd_id, ia, NAME=name, DATA=cdata
			IF NOT KEYWORD_SET(info) THEN PRINT, name, ": ", cdata[0]
			IF name EQ 'scale_factor' THEN scale  = cdata[0]
			IF name EQ 'add_offset'   THEN offset = cdata[0]
		ENDFOR
		IF scale EQ -999 XOR offset EQ -999 THEN PRINT, 'Problem while getting calibration information' $
		ELSE IF scale EQ -999 THEN PRINT, 'No calibration for this dataset' $
		ELSE data = FLOAT(data*scale + offset)
	ENDIF

FIN:
	HDF_SD_END, file_handle
	RETURN, data
END

