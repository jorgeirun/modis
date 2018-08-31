c     Exemple 2: extraction brute des donn�es d�calibr�es (i.e. en valeurs
c     physique) d'un SDS
c     -> Appel de la fonction hgsdsrc
c =============================================================================

      program example2

      include 'icahdf.inc'

c     D�claration des variables "param�tres utilisateur"
      character*20
     +     hdf_file,            ! Chemin du fichier HDF
     +     sds_name             ! Noms du SDS � explorer

      integer
     +     n                    ! Taille du tableau d'octets utilis�

      parameter
     +     (n = 160)            ! n doit �tre suffisant pour la taille du SDS

      byte
     +     data(n)              ! Tableau d'octets utilis� pour r�cup�rer le SDS

c     D�claration des autres variables
      real*8
     +     r8data(n/8)          ! Tableau de type real*8 utilis� pour exploiter
                                ! les donn�es extraites
      equivalence
     +     (data, r8data)       ! Mise en �quivalence des 2 tableaux

c     Initialisations
      data hdf_file, sds_name, data
     +     /'testfile.hdf', 'SDS_I2', n*0/

c     Ex�cution

      if (hgsdsru(hdf_file, sds_name, n, data).ge.0) then
         print *
         print *, data
         print *
         print *, r8data
      else
         print *, 'File ', hdf_file, ': error with SDS ', sds_name
      endif

      end
