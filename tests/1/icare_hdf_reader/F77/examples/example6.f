c     Exemple 6: extraction des données décalibrées (i.e. en valeurs physique)
c     d'un SDS avec dimensionnement et avec récupération des infos de type.
c     -> Appel de la fonction hgsdsdu.
c        Le type de donnée du tableau fourni doit obligatoirement être précisé.
c =============================================================================

      program example6

      include 'icahdf.inc'

      include 'hdf.inc'         ! Pour les constantes "type de donnée HDF"
c     DFNT_FLOAT32: type de donnée real*4

      include 'netcdf.inc'      ! Pour les constantes "limite"
c     MAXNCDIM: nombre maximal de dimensions d'un SDS

c     Déclaration des variables "paramètres utilisateur"
      character*20
     +     hdf_file,            ! Chemin du fichier HDF
     +     sds_name             ! Noms du SDS à explorer

      integer
     +     kr4,                 ! 1ère dimension du tableau fourni
     +     lr4,                 ! 2ème dimension du tableau fourni
     +     mr4,                 ! 3ème dimension du tableau fourni
     +     nr4,                 ! 4ème dimension du tableau fourni
     +     ndr4                 ! Nombre de dimensions du tableau fourni

      parameter(
     +     kr4  = 6,            ! Les dimensions du tableau fourni doivent être
     +     lr4  = 7,            ! respectivement supérieures ou égales à celles
     +     mr4  = 3,            ! du SDS. De même, le nombre de dimensions du
     +     nr4  = 3,            ! tableau fourni doit être supérieur ou égal au
     +     ndr4 = 4             ! nombre de dimensions du SDS
     +     )               

      integer
     +     r4dims(ndr4),        ! Valeurs des dimensions du tableau fourni
     +     dtype,               ! Type du tableau fourni
     +     nval,                ! Nombre de valeurs du SDS extrait
     +     utype,               ! Type de donnée décalibrée du SDS extrait
     +     rank,                ! Nombre de dimensions du SDS extrait
     +     dimsizes(MAXNCDIM)   ! Valeurs des dimensions du SDS extrait

      real*4
     +     r4data(kr4, lr4, mr4, nr4)  ! Tableau fourni

c     Initialisations
      data hdf_file, sds_name, r4dims, r4data
     +     /'testfile.hdf', 'SDS_R4', kr4, lr4, mr4, nr4,
     +     (kr4*lr4*mr4*nr4)*0.0/

c     Exécution
c     Ici, le type de donnée du tableau fourni est requis

      dtype = DFNT_FLOAT32

      if (hgsdsdu(hdf_file, sds_name, r4data, dtype, ndr4, r4dims, nval,
     +     utype, rank, dimsizes).ge.0) then
         call prntinfo(sds_name, dtype, ndr4, r4dims, nval, -1, utype,
     +        rank, dimsizes, r4data, .false., 0, -1, 0, '')
         call prntdata('DONNEES EXTRAITES', dtype, ndr4, r4dims,
     +        utype, rank, dimsizes, r4data)
         call prntdata("TABLEAU", dtype, ndr4, r4dims, dtype, ndr4,
     +        r4dims, r4data)
      else
         print *, 'File ', hdf_file, ': error with SDS ', sds_name
      endif

      end

