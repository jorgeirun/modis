      integer function hdffinfo(
     +     hdf_file, nsds, natt, nsdsnam, sds_name, nattnam, att_name
     +     )

      implicit none

c     D�claration des includes HDF
      include 'hdf.inc'
      include 'icadffunc.inc'
      include 'netcdf.inc'

c     D�claration des arguments de la fonction
      character*(*)
     +     hdf_file             ! Chemin du fichier HDF

      integer
     +     nsds,                ! Nombre de SDS du fichier HDF
     +     natt,                ! Nombre d'attributs du fichier HDF
     +     nsdsnam,             ! Dimension r�elle du tableau sds_name
     +     nattnam              ! Dimension r�elle du tableau att_name

      character*(MAXNCNAM)
     +     sds_name(*),         ! Noms des SDS du fichier
     +     att_name(*)          ! Noms des attributs du fichier

c     D�claration des variables sp�cifiques HDF
      integer
     +     sd_id,               ! Identificateur HDF du fichier
     +     isds,                ! Indice du SDS explor�
     +     sds_id,              ! Identificateur HDF du SDS
     +     iatt,                ! Indice de l'attribut explor�
     +     dummy(MAXNCDIM)      ! Variable de manoeuvre pour argument inutilis�

c     D�claration des fonctions utilis�es
      logical
     +     errhdf__

c     Initialisations
      hdffinfo = -1

c     Ouverture du fichier HDF
      sd_id = sfstart(hdf_file, DFACC_READ)
      if (errhdf__(sd_id)) return

c     R�cup�ration des infos
      if (errhdf__(sffinfo(sd_id, nsds, natt))) goto 99999

c     R�cup�ration des noms des SDS
      do 10 isds = 1, min(nsds, nsdsnam)

         sds_id = sfselect(sd_id, isds - 1)
         if (errhdf__(sds_id)) goto 99999

         if (errhdf__(sfginfo(sds_id, sds_name(isds), dummy,
     +        dummy, dummy, dummy))) goto 99999

 10   continue

c     R�cup�ration des noms des attributs du fichier
      do 20 iatt = 1, min(natt, nattnam)
         if (errhdf__(sfgainfo(sd_id, iatt - 1, att_name(iatt), dummy,
     +        dummy))) goto 99999
 20   continue
      
c     Tout s'est bien pass�: code de retour � 0
      hdffinfo = 0

99999 continue

c     Fermeture du fichier HDF
      if (errhdf__(sfend(sd_id))) hdffinfo = -1

      end

c-------------------------------------------------------------------------------

      integer function hdffattr(
     +     hdf_file, natt, nattnam, att_name
     +     )

      implicit none

c     D�claration des includes HDF
      include 'hdf.inc'
      include 'icadffunc.inc'
      include 'netcdf.inc'

c     D�claration des arguments de la fonction
      character*(*)
     +     hdf_file             ! Chemin du fichier HDF

      integer
     +     natt,                ! Nombre d'attributs du fichier rendus
     +     nattnam              ! Dimension r�elle du tableau att_name

      character*(MAXNCNAM)
     +     att_name(*)          ! Noms des attributs du fichier

c     D�claration des variables sp�cifiques HDF
      integer
     +     sd_id                ! Identificateur HDF du fichier

c     D�claration des fonctions utilis�es
      logical
     +     errhdf__

c     Initialisations
      hdffattr = -1

c     Ouverture du fichier HDF
      sd_id = sfstart(hdf_file, DFACC_READ)
      if (errhdf__(sd_id)) return

c     Tout s'est bien pass�: code de retour � 0
      hdffattr = 0

99999 continue

c     Fermeture du fichier HDF
      if (errhdf__(sfend(sd_id))) hdffattr = -1

      end

c-------------------------------------------------------------------------------

      integer function hsdsinfo(
     +     hdf_file, sds_name, nval, ctype, utype, clbsw, clb, rank,
     +     dimsizes, natt, nattnam, att_name
     +     )

      include 'icahdfsds.inc'

      hsdsinfo = hgsds__(
     +     hdf_file, sds_name, 1, data, dtype, 1, dims, nval,
     +     ctype, utype, clbsw, clb, rank, dimsizes, 0, natt,
     +     nattnam, att_name, dft_dclb
     +     )

      return
      end

c-------------------------------------------------------------------------------

      integer function hgsdsru(
     +     hdf_file, sds_name, n, data
     +     )

      include 'icahdfsds.inc'

      hgsdsru = hgsds__(
     +     hdf_file, sds_name, n, data, -1, 1, dims, nval, ctype, utype,
     +     clbsw, clb, rank, dimsizes, 2, natt, nattnam, att_name,
     +     dft_dclb
     +     )

      return
      end

c-------------------------------------------------------------------------------

      integer function hgsdsrus(
     +     hdf_file, sds_name, n, data, cal_func
     +     )

      include 'icahdfsds.inc'

      hgsdsrus = hgsds__(
     +     hdf_file, sds_name, n, data, -1, 1, dims, nval, ctype, utype,
     +     clbsw, clb, rank, dimsizes, 2, natt, nattnam, att_name,
     +     cal_func
     +     )

      return
      end

c-------------------------------------------------------------------------------

      integer function hgsdsrc(
     +     hdf_file, sds_name, n, data
     +     )

      include 'icahdfsds.inc'

      hgsdsrc = hgsds__(
     +     hdf_file, sds_name, n, data, -1, 1, dims, nval, ctype, utype,
     +     clbsw, clb, rank, dimsizes, 1, natt, nattnam, att_name,
     +     dft_dclb
     +     )

      return
      end

c-------------------------------------------------------------------------------

      integer function hgsdsuu(
     +     hdf_file, sds_name, n, data, dtype, nval, utype, rank,
     +     dimsizes
     +     )

      include 'icahdfsds.inc'

      hgsdsuu = hgsds__(
     +     hdf_file, sds_name, n*max(1, htypsize(dtype)), data, dtype,
     +     1, dims, nval, ctype, utype, clbsw, clb, rank, dimsizes, 2,
     +     natt, nattnam, att_name, dft_dclb
     +     )

      return
      end

c-------------------------------------------------------------------------------

      integer function hgsdsuus(
     +     hdf_file, sds_name, n, data, dtype, nval, utype, rank,
     +     dimsizes, cal_func
     +     )

      include 'icahdfsds.inc'

      hgsdsuus = hgsds__(
     +     hdf_file, sds_name, n*max(1, htypsize(dtype)), data, dtype,
     +     1, dims, nval, ctype, utype, clbsw, clb, rank, dimsizes, 2,
     +     natt, nattnam, att_name, cal_func
     +     )

      return
      end

c-------------------------------------------------------------------------------

      integer function hgsdsuc(
     +     hdf_file, sds_name, n, data, dtype, nval, ctype, utype,
     +     clbsw, clb, rank, dimsizes
     +     )

      include 'icahdfsds.inc'

      hgsdsuc = hgsds__(
     +     hdf_file, sds_name, n*max(1, htypsize(dtype)), data, dtype,
     +     1, dims, nval, ctype, utype, clbsw, clb, rank, dimsizes, 1,
     +     natt, nattnam, att_name, dft_dclb
     +     )

      return
      end

c-------------------------------------------------------------------------------

      integer function hgsdsdu(
     +     hdf_file, sds_name, data, dtype, nd, dims, nval, utype, rank,
     +     dimsizes
     +     )

      include 'icahdfsds.inc'

      hgsdsdu = -1
      if (errstd__(htypsize(dtype).le.0,
     +     "Le type de donn�es du tableau entr� est invalide")) return

      hgsdsdu = hgsds__(
     +     hdf_file, sds_name, htypsize(dtype)*arrsiz__(nd, dims), data,
     +     dtype, nd, dims, nval, ctype, utype, clbsw, clb, rank,
     +     dimsizes, 2, natt, nattnam, att_name, dft_dclb
     +     )

      return
      end

c-------------------------------------------------------------------------------

      integer function hgsdsdus(
     +     hdf_file, sds_name, data, dtype, nd, dims, nval, utype, rank,
     +     dimsizes, cal_func
     +     )

      include 'icahdfsds.inc'

      hgsdsdus = -1
      if (errstd__(htypsize(dtype).le.0,
     +     "Le type de donn�es du tableau entr� est invalide")) return

      hgsdsdus = hgsds__(
     +     hdf_file, sds_name, htypsize(dtype)*arrsiz__(nd, dims), data,
     +     dtype, nd, dims, nval, ctype, utype, clbsw, clb, rank,
     +     dimsizes, 2, natt, nattnam, att_name, cal_func
     +     )

      return
      end

c-------------------------------------------------------------------------------

      integer function hgsdsdc(
     +     hdf_file, sds_name, data, dtype, nd, dims, nval, ctype,
     +     utype, clbsw, clb, rank, dimsizes
     +     )

      include 'icahdfsds.inc'

      hgsdsdc = -1
      if (errstd__(htypsize(dtype).le.0,
     +     "Le type de donn�es du tableau entr� est invalide")) return

      hgsdsdc = hgsds__(
     +     hdf_file, sds_name, htypsize(dtype)*arrsiz__(nd, dims), data,
     +     dtype, nd, dims, nval, ctype, utype, clbsw, clb, rank,
     +     dimsizes, 1, natt, nattnam, att_name, dft_dclb
     +     )
 
      return
      end

c-------------------------------------------------------------------------------
c Fonction hgsds__
c =======================
c     Fonction g�n�rique de lecture d'un SDS dans un fichier HDF
c     Prend en entr�e un code d'erreur bool�en et affiche le message msg.
c     Retourne le bool�en entr�.

      function hgsds__(
     +     hdf_file, sds_name, n, data, dtype, nd, dims, nval, ctype,
     +     utype, clbsw, clb, rank, dimsizes, action, natt, nattnam, 
     +     att_name, cal_func
     +     )

      include 'icahdfsds.inc'

c     D�claration des variables sp�cifiques HDF
      character*(MAXNCNAM)
     +     name                 ! Nom du SDS (var de manoeuvre pour sfginfo)

      integer
     +     rutype,              ! Type r�ellement utilis� en sortie
     +     sd_id,               ! Identificateur HDF du fichier
     +     sds_id,              ! Identificateur HDF du SDS
     +     sds_idx,             ! Indice du SDS dans la liste des SDS
     +     att_idx,             ! Indice d'un attribut dans la liste
     +     start(MAXNCDIM),     ! Table des indices de d�but pour l'extraction
     +     stride(MAXNCDIM)     ! Table des pas par dimension pour l'extraction

c     D�claration des variables standard
      character*200
     +     msg                  ! Message d'erreur

      integer
     +     i, j,                ! Indices de boucle
     +     nbytes,              ! Taille en octets des donn�es du SDS
     +     dind(MAXNCDIM)       ! Tableau d'indices sur chaque dimension

      byte
     +     tmpdata(n)           ! Tableau pour stocker les donn�es calibr�es

      logical
     +     extrsw,              ! Y aura-t-il vraiment extraction?
     +     dclbsw               ! Y aura-t-il vraiment d�calibration?

      real*8
     +     rclb(4)              ! Coefficients r�els de calibration

      data rclb / 1.0, 0.0, 0.0, 0.0 /

c     D�claration des fonctions externes
      external
c     En pr�vision...
c     +     hi1dec__, hi2dec__, hi4dec__, hi8dec__, hr4dec__, hr8dec__
     +     hi1dec__, hi2dec__, hi4dec__, hr4dec__, hr8dec__

c     Initialisations
      hgsds__ = -1
      extrsw = (action.gt.0)
      dclbsw = (action.ne.1)
      rutype = dtype

c     Ouverture du fichier HDF
      sd_id = sfstart(hdf_file, DFACC_READ)
      if (errhdf__(sd_id)) return

c     Recherche du SDS par son nom
      sds_idx = sfn2index(sd_id, sds_name)
      if (errhdf__(sds_idx)) goto 99999
 
c     Initialisation de l'acc�s au SDS
      sds_id = sfselect(sd_id, sds_idx)
      if (errhdf__(sds_id)) goto 99999

c     R�cup�ration d'informations sur le SDS
      if (errhdf__(sfginfo(sds_id, name, rank, dimsizes, ctype, natt)))
     +     goto 99999
      
c     R�cup�ration des �ventuels param�tres de calibration des donn�es du SDS.
c     Il est impossible de faire la distinction entre erreur HDF et absence de
c     coefficients de calibration (dans les 2 cas, le code de retour de sfgcal
c     vaut -1). Si cela se produit, on essaye d'atteindre les coeff. de
c     calibration en tant qu'attributs par leur nom. Si on les trouve, alors
c     c'est que sfgcal a retourn� une erreur et on fait la m�me chose; sinon,
c     c'est qu'il n'y a pas de calibration et on initialise les coefficients
c     respectifs � 1 et 0

      clbsw = (sfgcal(sds_id, clb(1), clb(2), clb(3), clb(4),
     +     utype).ge.0)

      if (.not.clbsw) then
         att_idx = sffattr(sds_id, 'scale_factor')
         if (att_idx.ge.0) att_idx = sffattr(sds_id, 'add_offset')
         if (errstd__(att_idx.ge.0, 'Error while getting calibration'))
     +        goto 99999
         dclbsw = .false.
         utype = ctype
         do 100 i = 1, 4
 100        clb(i) = rclb(i)
      endif

      if (dclbsw) then
         do 150 i = 1, 4
 150        rclb(i) = clb(i)
      elseif (dtype.le.0) then
         utype = ctype         
      endif
      if (dtype.le.0) rutype = utype

c     Calcul de la taille n�cessaire au stockage des donn�es extraites
      nval = 1
      do 200 i = 1, rank
 200     nval = nval*dimsizes(i)
      nbytes = nval*htypsize(rutype)

c     On r�cup�re les attributs et on s'arr�te l� si seules des infos sont demand�es
      if (.not.extrsw) then

         do 220 i = 1, min(natt, nattnam)
 220        if (errhdf__(sfgainfo(sds_id, i - 1, att_name(i), j, j)))
     +           goto 99999

         goto 500

      endif

c     V�rification de la taille du tableau entr�
      write (msg, *) 'Taille en octets du tableau fourni (', n,
     +        ') insuffisante (', nbytes, ' requis)'
      if (errstd__(nbytes.gt.n, msg)) goto 99999

c     V�rification de la validit� du nombre de dimensions fournies et de leur
c     valeur

      if (nd.gt.1) then

         write (msg, *) 'Nombre de dimensions fournies (', nd, 
     +        ') insuffisant (', rank, ' requis)'
         if (errstd__(rank.gt.nd, msg)) goto 99999

         do 250 i = 1, rank
            if (dims(i).lt.dimsizes(i)) then
               print *,
     +              'Au moins une dimension fournie est insuffisante:'
               print *, 'Fournies: ', (dims(j),j=1,rank)
               print *, 'Requises: ', (dimsizes(j),j=1,rank)
               goto 99999
            endif
 250     continue

      endif

c     Renseignements des �ventuelles fractions des donn�es � r�cup�rer
c     Dans cette version, on r�cup�re tout
      do 300 i = 1, rank
         stride(i) = 1
 300     start (i) = 0

c     Extraction des donn�es du SDS et d�calibration �ventuelle
      if ((dclbsw).or.(rutype.ne.ctype).or.(nd.gt.1)) then
         if (errhdf__(sfrdata(sds_id, start, stride,
     +        dimsizes, tmpdata))) goto 99999

c     Appel de la routine adapt�e au type de donn�e calibr�e
         if     ((ctype.eq.DFNT_CHAR8 ) .or. 
     +           (ctype.eq.DFNT_INT8  ) .or.
     +           (ctype.eq.DFNT_UINT8 )) then
            call hpdclb__(hi1dec__, data, tmpdata, nbytes, rclb, ctype,
     +           rutype, dims, nd, dimsizes, rank, cal_func)
         elseif ((ctype.eq.DFNT_INT16 ) .or.
     +           (ctype.eq.DFNT_UINT16)) then
            call hpdclb__(hi2dec__, data, tmpdata, nbytes, rclb, ctype,
     +           rutype, dims, nd, dimsizes, rank, cal_func)
         elseif ((ctype.eq.DFNT_INT32 ) .or.
     +           (ctype.eq.DFNT_UINT32)) then
            call hpdclb__(hi4dec__, data, tmpdata, nbytes, rclb, ctype,
     +           rutype, dims, nd, dimsizes, rank, cal_func)
c     En pr�vision...
c         elseif ((ctype.eq.DFNT_INT64 ) .or.
c     +           (ctype.eq.DFNT_UINT64)) then
c            call hpdclb__(hi8dec__, data, tmpdata, nbytes, rclb, ctype,
c     +           rutype, dims, nd, dimsizes, rank, cal_func)
         elseif  (ctype.eq.DFNT_FLOAT32) then
            call hpdclb__(hr4dec__, data, tmpdata, nbytes, rclb, ctype,
     +           rutype, dims, nd, dimsizes, rank, cal_func)
         elseif  (ctype.eq.DFNT_FLOAT64) then
            call hpdclb__(hr8dec__, data, tmpdata, nbytes, rclb, ctype,
     +           rutype, dims, nd, dimsizes, rank, cal_func)
         else
            print *, 'Bad Type'
            goto 99999
         endif
      else
         if (errhdf__(sfrdata(sds_id, start, stride, dimsizes, data)))
     +        goto 99999
      endif

c     On arrive l� pour terminer et sortir proprement
 500  continue

c     Fin d'acc�s au SDS
      if (errhdf__(sfendacc(sds_id))) goto 99999

c     Tout s'est bien pass�: code de retour � 0
      hgsds__ = 0

99999 continue

c     Fermeture du fichier HDF
      if (errhdf__(sfend(sd_id))) hgsds__ = -1

      end

c-------------------------------------------------------------------------------
