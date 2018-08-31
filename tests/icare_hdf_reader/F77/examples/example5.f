c     Exemple 5: extraction de donn�es non d�calibr�es (i.e. en valeurs de
c     codage) d'un SDS, sans dimensionnement, avec r�cup�ration des infos de
c     type, de dimensionnement et de calibration
c     ->Appel de la fonction hgsdsuc
c ===========================================================================

      program example5

      include 'icahdf.inc'

      include 'hdf.inc'         ! Pour les constantes "type de donn�e HDF"
c     DFNT_INT32: type de donn�e integer*4

      include 'netcdf.inc'      ! Pour les constantes "limite"
c     MAXNCDIM: nombre maximal de dimensions d'un SDS

c     D�claration des variables "param�tres utilisateur"
      character*20
     +     hdf_file,            ! Chemin du fichier HDF
     +     sds_name             ! Noms du SDS � explorer

      integer
     +     n,                   ! Taille du tableau d'octets utilis�
     +     nval,                ! Nombre de valeurs du SDS extrait
     +     dtype,               ! Type du tableau fourni
     +     ctype,               ! Type de donn�e calibr�e du SDS extrait
     +     utype,               ! Type de donn�e d�calibr�e du SDS extrait
     +     rank,                ! Nombre de dimensions du SDS extrait
     +     dimsizes(MAXNCDIM)   ! Valeurs des dimensions du SDS extrait

      parameter
     +     (n = 300)            ! n doit �tre suffisant pour la taille du SDS

      integer*4
     +     i4data(n)            ! Tableau utilis� pour r�cup�rer le SDS

      logical
     +     clbsw                ! Flag: vrai si le SDS est calibr�

      real*8
     +     clb(4)               ! Coefficients de calibration des donn�es
                                ! extraites; dans l'ordre: pente, erreur sur la
                                ! pente, offset, erreur sur l'offset

c     Initialisations
      data hdf_file, sds_name, i4data
     +     /'testfile.hdf', 'SDS_I4', n*0/

c     Ex�cution

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

