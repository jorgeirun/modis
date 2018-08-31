      implicit none

c     Déclaration des includes HDF
      include 'hdf.inc'
      include 'icadffunc.inc'
      include 'netcdf.inc'

c     Déclaration des arguments et/ou variables locales (selon la procédure)
      character*(*)
     +     hdf_file,            ! Chemin du fichier HDF
     +     sds_name             ! Nom du SDS à extraire

      character*(MAXNCNAM)
     +     att_name(1)          ! Noms des attributs du SDS

      integer
     +     n,                   ! Nombre d'éléments du tableau fourni
     +     dtype,               ! Type de donnée du tableau fourni
     +     ctype,               ! Type de donnée du SDS extrait
     +     utype,               ! Type de donnée décalibrée du SDS extrait
     +     rank,                ! Nombre de dimensions du SDS extrait
     +     dimsizes(MAXNCDIM),  ! Valeurs des dimensions du SDS extrait
     +     nval,                ! Nombre de valeurs du SDS extrait
     +     nd,                  ! Nombre de dimensions du tableau fourni
     +     action,              ! Action à effectuer
     +     natt,                ! Nombre d'attributs du SDS
     +     nattnam,             ! Dimension du tableau att_name
     +     dims(MAXNCDIM)       ! Valeurs des dimensions du tableau fourni

      byte
     +     data(1)              ! Tableau fourni pour stocker les données du SDS

      logical
     +     clbsw                ! Flag de retour: vrai ssi SDS calibré

      real*8
     +     clb(4)               ! Coefficients de calibration des données
                                ! extraites; dans l'ordre: pente, erreur sur la
                                ! pente, offset, erreur sur l'offset

c     Déclaration de fonctions
      integer
     +     hgsds__,             ! Fonction générique d'extraction d'un SDS
     +     htypsize,            ! Retourne la taille en octets d'un type HDF
     +     arrsiz__             ! Retourne le nombre d'éléments d'un tableau

      logical
     +     errstd__,            ! Fonction de test d'erreur standard
     +     errhdf__             ! Fonction de test d'erreur HDF

      real*8
     +     dft_dclb,            ! Fonction de décalibration par défaut
     +     cal_func             ! Fonction de décalibration de l'utilisateur

      external dft_dclb
     
