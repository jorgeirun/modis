c     Exemple 3: extraction brute des donn�es non d�calibr�es (i.e. en valeurs
c     de codage) d'un SDS
c     -> Appel de la fonction hgsdsrc
c =============================================================================

      program example3

      include 'icahdf.inc'

c     D�claration des variables "param�tres utilisateur"
      character*20
     +     hdf_file,            ! Chemin du fichier HDF
     +     sds_name             ! Noms du SDS � explorer

      integer
     +     n                    ! Taille du tableau d'octets utilis�

      parameter
     +     (n = 40)             ! n doit �tre "suffisant" pour la taille du SDS

      byte
     +     data(n)              ! Tableau d'octets utilis� pour r�cup�rer le SDS

c     D�claration des autres variables
      integer*2
     +     i2data(n/2)          ! Tableau de type integer*2 utilis� pour exploiter
                                ! les donn�es extraites
      equivalence
     +     (data, i2data)       ! Mise en �quivalence des 2 tableaux

c     Initialisations
      data hdf_file, sds_name, data
     +     /'testfile.hdf', 'SDS_I2', n*0/

c     Ex�cution

      if (hgsdsrc(hdf_file, sds_name, n, data).ge.0) then
         print *
         print *,data
         print *
         print *,i2data
      else
         print *, 'File ', hdf_file, ': error with SDS ', sds_name
      endif

      end

