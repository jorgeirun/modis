c     Exemple 2: extraction brute des données décalibrées (i.e. en valeurs
c     physique) d'un SDS
c     -> Appel de la fonction hgsdsrc
c =============================================================================

      program example2

      include 'icahdf.inc'

c     Déclaration des variables "paramètres utilisateur"
      character*20
     +     hdf_file,            ! Chemin du fichier HDF
     +     sds_name             ! Noms du SDS à explorer

      integer
     +     n                    ! Taille du tableau d'octets utilisé

      parameter
     +     (n = 160)            ! n doit être suffisant pour la taille du SDS

      byte
     +     data(n)              ! Tableau d'octets utilisé pour récupérer le SDS

c     Déclaration des autres variables
      real*8
     +     r8data(n/8)          ! Tableau de type real*8 utilisé pour exploiter
                                ! les données extraites
      equivalence
     +     (data, r8data)       ! Mise en équivalence des 2 tableaux

c     Initialisations
      data hdf_file, sds_name, data
     +     /'testfile.hdf', 'SDS_I2', n*0/

c     Exécution

      if (hgsdsru(hdf_file, sds_name, n, data).ge.0) then
         print *
         print *, data
         print *
         print *, r8data
      else
         print *, 'File ', hdf_file, ': error with SDS ', sds_name
      endif

      end
