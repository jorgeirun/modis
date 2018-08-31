c     Exemple 4: extraction de données décalibrées (i.e. en valeurs physique)
c     d'un SDS, sans dimensionnement, mais avec récupération des infos de type
c     et de dimensionnement. Le rétablissement des valeurs physiques d'origine
c     utilise le calcul par défaut ainsi qu'un calcul différent fourni dans la
c     fonction own_dclb
c     -> Appel des fonctions hgsdsuu et hgsdsuus
c =============================================================================

      program example4

      include 'icahdf.inc'

      include 'netcdf.inc'      ! Pour les constantes "limites"
c     MAXNCDIM: nombre maximal de dimensions d'un SDS

c     Déclaration des variables "paramètres utilisateur"
      character*20
     +     hdf_file,            ! Chemin du fichier HDF
     +     sds_name             ! Noms du SDS à explorer

      integer
     +     n,                   ! Taille du tableau d'octets utilisé
     +     nval,                ! Nombre de valeurs du SDS extrait
     +     utype,               ! Type de donnée décalibrée du SDS extrait
     +     rank,                ! Nombre de dimensions du SDS extrait
     +     dimsizes(MAXNCDIM)   ! Valeurs des dimensions du SDS extrait

      parameter
     +     (n = 1000)           ! n doit être suffisant pour la taille du SDS

      byte
     +     data(n)              ! Tableau d'octets utilisé pour récupérer le SDS

      real*8
     +     own_dclb             ! Fonction de décalibration "utilisateur"

      external own_dclb

c     Initialisations
      data hdf_file, sds_name, data
     +     /'testfile.hdf', 'SDS_I4', n*0/

c     Exécution

      if (hgsdsuu(hdf_file, sds_name, n, data, -1, nval, utype, rank,
     +     dimsizes).ge.0) then
         call prntinfo(sds_name, 0, 1, n, nval, -1, utype,
     +        rank, dimsizes, data, .false., 0, -1, 0, '')
         call prntdata('DONNEES EXTRAITES - DÉCALIBRATION PAR DÉFAUT',
     +        utype, rank, dimsizes, utype, rank, dimsizes, data)
      else
         print *, 'File ', hdf_file, ': error with SDS ', sds_name
      endif

      if (hgsdsuus(hdf_file, sds_name, n, data, -1, nval, utype, rank,
     +     dimsizes, own_dclb).ge.0) then
         call prntdata('DONNEES EXTRAITES - DÉCALIBRATION UTILISATEUR',
     +        utype, rank, dimsizes, utype, rank, dimsizes, data)
      else
         print *, 'File ', hdf_file, ': error with SDS ', sds_name
      endif

      end

