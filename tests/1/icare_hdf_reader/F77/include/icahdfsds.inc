      implicit none

c     D�claration des includes HDF
      include 'hdf.inc'
      include 'icadffunc.inc'
      include 'netcdf.inc'

c     D�claration des arguments et/ou variables locales (selon la proc�dure)
      character*(*)
     +     hdf_file,            ! Chemin du fichier HDF
     +     sds_name             ! Nom du SDS � extraire

      character*(MAXNCNAM)
     +     att_name(1)          ! Noms des attributs du SDS

      integer
     +     n,                   ! Nombre d'�l�ments du tableau fourni
     +     dtype,               ! Type de donn�e du tableau fourni
     +     ctype,               ! Type de donn�e du SDS extrait
     +     utype,               ! Type de donn�e d�calibr�e du SDS extrait
     +     rank,                ! Nombre de dimensions du SDS extrait
     +     dimsizes(MAXNCDIM),  ! Valeurs des dimensions du SDS extrait
     +     nval,                ! Nombre de valeurs du SDS extrait
     +     nd,                  ! Nombre de dimensions du tableau fourni
     +     action,              ! Action � effectuer
     +     natt,                ! Nombre d'attributs du SDS
     +     nattnam,             ! Dimension du tableau att_name
     +     dims(MAXNCDIM)       ! Valeurs des dimensions du tableau fourni

      byte
     +     data(1)              ! Tableau fourni pour stocker les donn�es du SDS

      logical
     +     clbsw                ! Flag de retour: vrai ssi SDS calibr�

      real*8
     +     clb(4)               ! Coefficients de calibration des donn�es
                                ! extraites; dans l'ordre: pente, erreur sur la
                                ! pente, offset, erreur sur l'offset

c     D�claration de fonctions
      integer
     +     hgsds__,             ! Fonction g�n�rique d'extraction d'un SDS
     +     htypsize,            ! Retourne la taille en octets d'un type HDF
     +     arrsiz__             ! Retourne le nombre d'�l�ments d'un tableau

      logical
     +     errstd__,            ! Fonction de test d'erreur standard
     +     errhdf__             ! Fonction de test d'erreur HDF

      real*8
     +     dft_dclb,            ! Fonction de d�calibration par d�faut
     +     cal_func             ! Fonction de d�calibration de l'utilisateur

      external dft_dclb
     