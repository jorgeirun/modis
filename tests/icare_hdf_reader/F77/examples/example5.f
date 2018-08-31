c     Exemple 5: extraction de données non décalibrées (i.e. en valeurs de
c     codage) d'un SDS, sans dimensionnement, avec récupération des infos de
c     type, de dimensionnement et de calibration
c     ->Appel de la fonction hgsdsuc
c ===========================================================================

      program example5

      include 'icahdf.inc'

      include 'hdf.inc'         ! Pour les constantes "type de donnée HDF"
c     DFNT_INT32: type de donnée integer*4

      include 'netcdf.inc'      ! Pour les constantes "limite"
c     MAXNCDIM: nombre maximal de dimensions d'un SDS

c     Déclaration des variables "paramètres utilisateur"
      character*20
     +     hdf_file,            ! Chemin du fichier HDF
     +     sds_name             ! Noms du SDS à explorer

      integer
     +     n,                   ! Taille du tableau d'octets utilisé
     +     nval,                ! Nombre de valeurs du SDS extrait
     +     dtype,               ! Type du tableau fourni
     +     ctype,               ! Type de donnée calibrée du SDS extrait
     +     utype,               ! Type de donnée décalibrée du SDS extrait
     +     rank,                ! Nombre de dimensions du SDS extrait
     +     dimsizes(MAXNCDIM)   ! Valeurs des dimensions du SDS extrait

      parameter
     +     (n = 300)            ! n doit être suffisant pour la taille du SDS

      integer*4
     +     i4data(n)            ! Tableau utilisé pour récupérer le SDS

      logical
     +     clbsw                ! Flag: vrai si le SDS est calibré

      real*8
     +     clb(4)               ! Coefficients de calibration des données
                                ! extraites; dans l'ordre: pente, erreur sur la
                                ! pente, offset, erreur sur l'offset

c     Initialisations
      data hdf_file, sds_name, i4data
     +     /'testfile.hdf', 'SDS_I4', n*0/

c     Exécution

      dtype = DFNT_INT32

      if (hgsdsuc(hdf_file, sds_name, n, i4data, dtype, nval, ctype,
     +     utype, clbsw, clb, rank, dimsizes).ge.0) then
         call prntinfo(sds_name, dtype, 1, n, nval, ctype, utype,
     +        rank, dimsizes, i4data, clbsw, clb, -1, 0, '')
         call prntdata('DONNEES EXTRAITES', ctype, rank, dimsizes,
     +        ctype, rank, dimsizes, i4data)
      else
         print *, 'File ', hdf_file, ': error with SDS ', sds_name
      endif

      end

