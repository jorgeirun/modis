;==========================================================================================================
; hdfdump.pro
;==========================================================================================================
; AUTEUR : F.M. Breon, Septembre 2006
;==========================================================================================================
;  Cette procedure IDL permet de voir rapidement ce que on peut trouver dans un fichier de format HDF.
;
;  USAGE : hdfdump, FILEIN=<nom_du_fichier>, /NOSTOP
;
;  Tous les parametres sont optionnels.
;
;  Si FILEIN n'est pas fourni, un dialogue permet de choisir le fichier.
;  La routine boucle sur les différents Scientific Datasets et s'arrete entre chaque.
;  La commande ".cont" permet de passer au suivant.
;  L'option /NOSTOP permet de tout imprimer sans s'arréter entre chaque sds.
;----------------------------------------------------------------------------------------------------
; MODIFS:
; - Mars 2008 - SIX Bruno - ICARE-USTL
;   Affinement des affichages tabulés et simplification (procédure d'affichage commune des attributs)
;----------------------------------------------------------------------------------------------------

PRO HDFdump, FILEIN=filein, NOSTOP=nostop
	FORWARD_FUNCTION printsds

	IF NOT KEYWORD_SET(filein) THEN filein = DIALOG_PICKFILE(FILTER='*.hdf')
	file_handle = HDF_SD_START(filein, /READ)
	HDF_SD_FILEINFO, file_handle, Nsds, Natt
	PRINT, 'FileName:', filein
	PRINT, 'Number of Datasets: ', Nsds
	PRINT, 'Number of attributes: ', Natt

	PRINT, "=================================================================================================="
	PRINT, "=                                       GLOBAL ATTRIBUTES                                        ="
	PRINT, "=================================================================================================="
	IF Natt GT 0 THEN printatt, file_handle, Natt

	PRINT, ""
	PRINT, "=================================================================================================="
	PRINT, "=                                          DATASETS                                              ="

	FOR id = 0, Nsds - 1 DO BEGIN
		PRINT, "=================================================================================================="
		sd_id = HDF_SD_SELECT(file_handle, id)
		Natt = printsds(sd_id, id, Nsds)
		PRINT, "--------------------------------------------------------------------------------------------------"
		PRINT, "                                       DATASET ATTRIBUTES                                         "
		PRINT, "--------------------------------------------------------------------------------------------------"
		IF Natt GT 0 THEN printatt, sd_id, Natt
		IF NOT KEYWORD_SET(nostop) THEN STOP
	ENDFOR
	PRINT, "=================================================================================================="
	HDF_SD_END, file_handle
END

