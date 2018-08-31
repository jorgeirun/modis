c     Exemple 7: extraction de données non décalibrées (i.e. en valeurs de
c     codage) d'un SDS avec dimensionnement et récupération des infos de type
c     -> Appel de la fonction hgsdsdu.
c        Le type de donnée du tableau fourni doit obligatoirement être précisé.
c =============================================================================

      program example7

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
     +     ki4,                 ! 1ère dimension du tableau fourni
     +     li4,                 ! 2ème dimension du tableau fourni
     +     mi4,                 ! 3ème dimension du tableau fourni
     +     ni4,                 ! 4ème dimension du tableau fourni
     +     ndi4                 ! Nombre de dimensions du tableau fourni

      parameter(
     +     ki4  =  6,           ! Les dimensions du tableau fourni doivent être
     +     li4  = 10,           ! respectivement supérieures ou égales à celles
     +     mi4  =  3,           ! du SDS. De même, le nombre de dimensions du
     +     ni4  =  2,           ! tableau fourni doit être supérieur ou égal au
     +     ndi4 =  4            ! nombre de dimensions du SDS
     +     )

      integer
     +     i4dims(ndi4),        ! Valeurs des dimensions du tableau fourni
     +     dtype,               ! Type du tableau fourni
     +     nval,                ! Nombre de valeurs du SDS extrait
     +     ctype,               ! Type de donnée calibrée du SDS extrait
     +     utype,               ! Type de donnée décalibrée du SDS extrait
     +     rank,                ! Nombre de dimensions du SDS extrait
     +     dimsizes(MAXNCDIM)   ! Valeurs des dimensions du SDS extrait

      integer*4
     +     i4data(ki4, li4, mi4, ni4)  ! Tableau fourni

      logical
     +     clbsw                ! Flag: vrai si le SDS est calibré

      real*8
     +     clb(4)               ! Coefficients de calibration des données
                                ! extraites; dans l'ordre: pente, erreur sur la
                                ! pente, offset, erreur sur l'offset
      
c     Initialisations
      data hdf_file, sds_name, i4dims, i4data
     +     /'testfile.hdf', 'SDS_I4', ki4, li4, mi4, ni4,
     +     (ki4*li4*mi4*ni4)*0/

c     Exécution
c     Ici, le type de donnée du tableau fourni est requis

      dtype = DFNT_INT32

      if (hgsdsdc(hdf_file, sds_name, i4data, dtype, ndi4, i4dims, nval,
     +     ctype, utype, clbsw, clb, rank, dimsizes).ge.0) then
         call prntinfo(sds_name, dtype, ndi4, i4dims, nval, ctype,
     +         utype, rank, dimsizes, i4data, clbsw, clb, -1, 0, '')
         call prntdata('DONNEES EXTRAITES', dtype, ndi4, i4dims,
     +        ctype, rank, dimsizes, i4data)
         call prntdata("TABLEAU", dtype, ndi4, i4dims, dtype, ndi4,
     +        i4dims, i4data)
      else
         print *, 'File ', hdf_file, ': error with SDS ', sds_name
      endif

      end

