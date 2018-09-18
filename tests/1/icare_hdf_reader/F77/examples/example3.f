c     Exemple 3: extraction brute des données non décalibrées (i.e. en valeurs
c     de codage) d'un SDS
c     -> Appel de la fonction hgsdsrc
c =============================================================================

      program example3

      include 'icahdf.inc'

c     Déclaration des variables "paramètres utilisateur"
      character*20
     +     hdf_file,            ! Chemin du fichier HDF
     +     sds_name             ! Noms du SDS à explorer

      integer
     +     n                    ! Taille du tableau d'octets utilisé

      parameter
     +     (n = 40)             ! n doit être "suffisant" pour la taille du SDS

      byte
     +     data(n)              ! Tableau d'octets utilisé pour récupérer le SDS

c     Déclaration des autres variables
      integer*2
     +     i2data(n/2)          ! Tableau de type integer*2 utilisé pour exploiter
                                ! les données extraites
      equivalence
     +     (data, i2data)       ! Mise en équivalence des 2 tableaux

c     Initialisations
      data hdf_file, sds_name, data
     +     /'testfile.hdf', 'SDS_I2', n*0/

c     Exécution

      if (hgsdsrc(hdf_file, sds_name, n, data).ge.0) then
         print *
         print *,data
         print *
         print *,i2data
      else
         print *, 'File ', hdf_file, ': error with SDS ', sds_name
      endif

      end

