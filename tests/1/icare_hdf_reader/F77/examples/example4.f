c     Exemple 4: extraction de donn�es d�calibr�es (i.e. en valeurs physique)
c     d'un SDS, sans dimensionnement, mais avec r�cup�ration des infos de type
c     et de dimensionnement. Le r�tablissement des valeurs physiques d'origine
c     utilise le calcul par d�faut ainsi qu'un calcul diff�rent fourni dans la
c     fonction own_dclb
c     -> Appel des fonctions hgsdsuu et hgsdsuus
c =============================================================================

      program example4

      include 'icahdf.inc'

      include 'netcdf.inc'      ! Pour les constantes "limites"
c     MAXNCDIM: nombre maximal de dimensions d'un SDS

c     D�claration des variables "param�tres utilisateur"
      character*20
     +     hdf_file,            ! Chemin du fichier HDF
     +     sds_name             ! Noms du SDS � explorer

      integer
     +     n,                   ! Taille du tableau d'octets utilis�
     +     nval,                ! Nombre de valeurs du SDS extrait
     +     utype,               ! Type de donn�e d�calibr�e du SDS extrait
     +     rank,                ! Nombre de dimensions du SDS extrait
     +     dimsizes(MAXNCDIM)   ! Valeurs des dimensions du SDS extrait

      parameter
     +     (n = 1000)           ! n doit �tre suffisant pour la taille du SDS

      byte
     +     data(n)              ! Tableau d'octets utilis� pour r�cup�rer le SDS

      real*8
     +     own_dclb             ! Fonction de d�calibration "utilisateur"

      external own_dclb

c     Initialisations
      data hdf_file, sds_name, data
     +     /'testfile.hdf', 'SDS_I4', n*0/

c     Ex�cution

      if (hgsdsuu(hdf_file, sds_name, n, data, -1, nval, utype, rank,
     +     dimsizes).ge.0) then
         call prntinfo(sds_name, 0, 1, n, nval, -1, utype,
     +        rank, dimsizes, data, .false., 0, -1, 0, '')
         call prntdata('DONNEES EXTRAITES - D�CALIBRATION PAR D�FAUT',
     +        utype, rank, dimsizes, utype, rank, dimsizes, data)
      else
         print *, 'File ', hdf_file, ': error with SDS ', sds_name
      endif

      if (hgsdsuus(hdf_file, sds_name, n, data, -1, nval, utype, rank,
     +     dimsizes, own_dclb).ge.0) then
         call prntdata('DONNEES EXTRAITES - D�CALIBRATION UTILISATEUR',
     +        utype, rank, dimsizes, utype, rank, dimsizes, data)
      else
         print *, 'File ', hdf_file, ': error with SDS ', sds_name
      endif

      end

