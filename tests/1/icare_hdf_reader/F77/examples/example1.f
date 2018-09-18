c     Exemple 1: obtention d'information sur un fichier HDF et ses SDS
c     -> Appel des fonctions hdffinfo et hsdsinfo
c     ================================================================

      program example1

      include 'icahdf.inc'

      include 'netcdf.inc'      ! Pour les constantes "limites"
c     MAXNCNAM: longueur maximale du nom d'un SDS ou d'un attribut
c     MAXNCVAR: nombre maximal de SDS dans un fichier HDF
c     MAXNCATT: nombre maximal d'attributs d'un fichier HDF ou d'un SDS
c     MAXNCDIM: nombre maximal de dimensions d'un SDS

c     D�claration des variables "param�tres utilisateur"
      character*20
     +     hdf_file             ! Chemin du fichier HDF

      character*(MAXNCNAM)
     +     sds_name(MAXNCVAR),  ! Noms des SDS du fichier
     +     att_name(MAXNCATT)   ! Noms des attributs du fichier ou d'un SDS

      logical
     +     clbsw                ! Indique si les donn�es du SDS sont calibr�es

      integer
     +     nsds,                ! Nombre de SDS du fichier HDF
     +     nval,                ! Nombre d'�l�ments du SDS
     +     ctype,               ! Type de donn�e calibr�e du SDS (cf. doc. HDF4)
     +     utype,               ! Type de donn�e d�calibr�e du SDS (cf. doc. HDF4)
     +     rank,                ! Nombre de dimensions du SDS
     +     nattr,               ! Nombre d'attributs du fichier HDF ou du SDS
     +     dimsizes(MAXNCDIM)   ! Valeurs des dimensions du SDS

      real*8
     +     clb(4)               ! Coefficients de calibration �ventuels des
                                ! donn�es extraites; dans l'ordre: pente, erreur
                                ! sur la pente, offset, erreur sur l'offset

c     D�claration des autres variables
      integer
     +     i,                   ! Indice de boucle
     +     dummy                ! Variable muette pour arguments inutiles

      character*10
     +     rtrim

c     Initialisations
      data hdf_file /'testfile.hdf'/

c     Ex�cution

c     1. Obtention et affichage d'informations sur le fichier HDF
      if (hdffinfo(hdf_file, nsds, nattr, MAXNCVAR, sds_name, MAXNCATT,
     +     att_name).ge.0) then

         print *, 'HDF File: ', hdf_file
 
         print *, 'Number of file attributes: ', nattr
         if (nattr.gt.0) then
            print *, 'File attributes names: '
            do 10 i = 1, nattr
 10            print *, '  ', att_name(i)(1:len_trim(att_name(i)))
         endif
 
         print *, 'Number of SDS in file: ', nsds
         if (nsds.gt.0) then
            print *, 'SDS names: '
             do 20 i = 1, nsds
 20            print *, '  ', sds_name(i)(1:len_trim(sds_name(i)))
         endif

      else

         print *, 'ERROR with file ', hdf_file

      endif

c     2. Obtention et affichage d'informations sur les SDS du fichier
      do 100 i = 1, nsds

         if (hsdsinfo(hdf_file, sds_name(i), nval, ctype,
     +        utype, clbsw, clb, rank, dimsizes, nattr, MAXNCATT,
     +        att_name).ge.0) then

c     Le sous-programme prntresu affiche les propri�t�s pertinentes et
c     �ventuellement le contenu d'un SDS extrait
            call prntinfo(sds_name(i), -1, 0, 0, nval, ctype, utype,
     +           rank, dimsizes, dummy, clbsw, clb, nattr, MAXNCATT,
     +           att_name)

         else

            print *, 'File ', hdf_file, ': error with SDS ',
     +           sds_name(i)(1:len_trim(sds_name(i)))
 
        endif

 100  continue

         end

