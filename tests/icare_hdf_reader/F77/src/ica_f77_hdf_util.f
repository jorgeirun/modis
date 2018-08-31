c-------------------------------------------------------------------------------
c Fonction errhdf__
c =================
c     Prend en entr�e le code de retour d'une routine de la biblioth�que HDF et
c     Retourne vrai si ce code est < 0 (et affiche le message d'erreur HDF
c     associ�. Retourne faux sinon.

      logical function errhdf__(hdfcode)

      implicit none

      include 'icadffunc.inc'

      integer
     +     hdfcode,             ! (IN)  Code de retour d'une fonction HDF
     +     dummy                ! (LOC) Variable de manoeuvre

      errhdf__ = (hdfcode.lt.0)
      if (errhdf__) dummy = heprnt(0)

      return
      end

c-------------------------------------------------------------------------------
c Fonction htypsize
c =================
c     La fonction htypsize retourne la taille en octets d'un type de donn�e HDF
c     pass� en argument.

      integer function htypsize(htyp)

      implicit none

      include 'hdf.inc'
      include 'icadffunc.inc'
      include 'netcdf.inc'

      integer
     +     htyp                 ! (IN)  Type de donn�e HDF

      character*(14)
     +     tname                ! (OUT) Nom du type de donn�e HDF
      character*(60)
     +     tdesc                ! (OUT) Description du type de donn�e HDF

      call htypinfo(htyp, tname, tdesc, htypsize)
      return
      end

c-------------------------------------------------------------------------------
c Sous-programme htypinfo
c =======================
c     La fonction htypinfo donne le nom, la description et la taille en octets 
c     d'un type de donn�e HDF pass� en argument. Si le type de donn�e est
c     invalide, 'UNKNOWN', 'unknow data type' et 0 sont respectivement rendus.

      subroutine htypinfo(htyp, tname, tdesc, tsize)

      implicit none

      include 'hdf.inc'
      include 'icadffunc.inc'
      include 'netcdf.inc'

      integer
     +     htyp,                ! (IN)  Type de donn�e HDF
     +     tsize                ! (OUT) Taille en octets du type de donn�e HDF

      character*(*)
     +     tname,               ! (OUT) Nom du type de donn�e HDF
     +     tdesc                ! (OUT) Description du type de donn�e HDF

      if     (htyp.eq.DFNT_CHAR8  ) then
         tsize = 1
         tname = 'DFNT_CHAR8'
         tdesc = '8-bit character / character*1 (character)'
      elseif (htyp.eq.DFNT_UCHAR8 ) then
         tname = 'DFNT_UCHAR8'
         tdesc = '8-bit unsigned char / integer*1'
         tsize = 1
      elseif (htyp.eq.DFNT_INT8   ) then
         tname = 'DFNT_INT8'
         tdesc = '8-bit signed integer / integer*1'
         tsize = 1
      elseif (htyp.eq.DFNT_UINT8  ) then
         tname = 'DFNT_UINT8'
         tdesc = '8-bit unsigned integer / integer*1'
         tsize = 1
      elseif (htyp.eq.DFNT_INT16  ) then
         tname = 'DFNT_INT16'
         tdesc = '16-bit signed integer / integer*2'
         tsize = 2
      elseif (htyp.eq.DFNT_UINT16 ) then
         tname = 'DFNT_UINT16'
         tdesc = '16-bit unsigned integer / not supported'
         tsize = 2
      elseif (htyp.eq.DFNT_INT32  ) then
         tname = 'DFNT_INT32'
         tdesc = '32-bit signed integer / integer*4 (integer)'
         tsize = 4
      elseif (htyp.eq.DFNT_UINT32 ) then
         tname = 'DFNT_UINT32'
         tdesc = '32-bit unsigned integer / not supported'
         tsize = 4
c     En pr�vision...
c      elseif (htyp.eq.DFNT_INT64  ) then
c         tname = 'DFNT_INT64'
c         tdesc = '64-bit signed integer / integer*8'
c         tsize = 8
c      elseif (htyp.eq.DFNT_UINT64 ) then
c         tname = 'DFNT_UINT64'
c         tdesc = '64-bit unsigned integer / not supported'
c         tsize = 8
      elseif (htyp.eq.DFNT_FLOAT32) then
         tname = 'DFNT_FLOAT32'
         tdesc = '32-bit floating point number / real*4 (real)'
         tsize = 4
      elseif (htyp.eq.DFNT_FLOAT64) then
         tname = 'DFNT_FLOAT64'
         tdesc = '64-bit floating point number / real*8 ' //
     +        '(double precision)'
         tsize = 8
      else
         tname = 'UNKNOWN'
         tdesc = 'unsupported data type'
         tsize = 0
      endif

      return
      end

c-------------------------------------------------------------------------------
c Fonctions hr8dec__, hr4dec__, hi8dec__, hi4dec__, hi2dec__, hi1dec__
c ====================================================================

c     Ces fonctions calculent la valeur d�calibr�e d'un �l�ment d'un SDS HDF, en
c     fonction des param�tres de calibration HDF fournis, et retournent le
c     r�sultat dans le type souhait� (resp. real*8, real*4, integer*8,
c     integer*4, integer*2 et integer*1)

c     En pr�vision...
c      real*8 function htypdc__(r8in, r4in, i8in, i4in, i2in, i1in, clb)
      real*8 function 
     +     htypdc__(r8in, r4in, i4in, i2in, i1in, clb, cal_func)

      implicit none

      integer*1 i1in            ! (IN) Valeur � d�calibrer pour hi1dec__
      integer*2 i2in            ! (IN) Valeur � d�calibrer pour hi2dec__
      integer*4 i4in            ! (IN) Valeur � d�calibrer pour hi4dec__
c     En pr�vision...
c      integer*8 i8in            ! (IN) Valeur � d�calibrer pour hi8dec__
      real*4    r4in            ! (IN) Valeur � d�calibrer pour hr4dec__
      real*8    r8in            ! (IN) Valeur � d�calibrer pour hr8dec__

      real *8 clb(4)            ! (IN) param�tres de calibration HDF

      real *8
     +     hi1dec__,            ! (OUT) Valeur d�calibr�e de type integer*1
     +     hi2dec__,            ! (OUT) Valeur d�calibr�e de type integer*2
     +     hi4dec__,            ! (OUT) Valeur d�calibr�e de type integer*4
c     En pr�vision...
c     +     hi8dec__,            ! (OUT) Valeur d�calibr�e de type integer*8
     +     hr4dec__,            ! (OUT) Valeur d�calibr�e de type real*4
     +     hr8dec__             ! (OUT) Valeur d�calibr�e de type real*8

      real*8
     +     r8man,
     +     cal_func

      entry hr8dec__(r8in, clb, cal_func)
      r8man = r8in
      hr8dec__ = cal_func(r8man, clb(1), clb(3))
!      hr8dec__ = clb(1)*(r8in - clb(3))
      return

      entry hr4dec__(r4in, clb, cal_func)
      r8man = r4in
      hr4dec__ = cal_func(r8man, clb(1), clb(3))
!      hr4dec__ = clb(1)*(r4in - clb(3))
      return

c     En pr�vision...
c      entry hi8dec__(i8in, clb, cal_func)
c      r8man = i8in
c      hi8dec__ = cal_func(r8man, clb(1), clb(3))
cc      hi8dec__ = clb(1)*(i8in - clb(3))
c      return

      entry hi4dec__(i4in, clb, cal_func)
      r8man = i4in
      hi4dec__ = cal_func(r8man, clb(1), clb(3))
c      hi4dec__ = clb(1)*(i4in - clb(3))
      return

      entry hi2dec__(i2in, clb, cal_func)
      r8man = i2in
      hi2dec__ = cal_func(r8man, clb(1), clb(3))
c      hi2dec__ = clb(1)*(i2in - clb(3))
      return

      entry hi1dec__(i1in, clb, cal_func)
      r8man = i1in
      hi1dec__ = cal_func(r8man, clb(1), clb(3))
c      hi1dec__ = clb(1)*(i1in - clb(3))
      return

      end

c-------------------------------------------------------------------------------
c Fonction dft_dclb
c =================

c     Cette fonction applique la d�calibration par d�faut, i.e. selon la
c     convention HDF

      real*8 function dft_dclb(inval, pente, offset)
      real*8 inval, pente, offset
      dft_dclb = pente*(inval - offset)
      end

c-------------------------------------------------------------------------------
c Sous-programme hpdclb
c =======================
c     Ce sous-programme ne fait que pr�parer le terrain au sous-programme
c     hdclbr__ en d�terminant le sous-programme d'affectation adapt� au type de
c     donn�e d�calibr�e du SDS

      subroutine hpdclb__(declb__, outdata, sdsdata, n, clb, ctype,
     +     utype, outdims, nd, sdsdims, rank, cal_func)

      implicit none

      include 'hdf.inc'

      integer
     +     n,                   ! (IN)  Taille en octets du tableau cible
     +     ctype,               ! (IN)  Type de donn�e calibr�e du SDS extrait
     +     utype,               ! (IN)  Type de donn�e d�calibr�e du SDS extrait
     +     nd,                  ! (IN)  Nombre de dimensions du tableau cible
     +     rank,                ! (IN)  Nombre de dimensions du SDS extrait
     +     sdsdims(rank),       ! (IN)  Valeurs des dimensions du SDS extrait
     +     outdims(nd)          ! (IN)  Valeurs des dimensions du tableau cible

      byte
     +     outdata(*),          ! (OUT) Tableau cible
     +     sdsdata(*)           ! (IN)  Tableau contenant les donn�es calibr�es

      real*8
     +     cal_func,            !
     +     clb(4)               ! (IN)  Coefficients de calibration des donn�es

c     D�claration des fonctions externes
      external
c     En pr�vision...
c     +     declb__, seti1__, seti2__, seti4__, seti8__, setr4__, setr8__
     +     declb__, seti1__, seti2__, seti4__, setr4__, setr8__

c     Appel de la routine adapt�e au type de donn�e d�calibr�e
      if  (utype.eq.DFNT_CHAR8) then
         print *, 'Character values cannot be decalibrated'
      elseif ((utype.eq.DFNT_UCHAR8) .or. 
     +        (utype.eq.DFNT_INT8  ) .or.
     +        (utype.eq.DFNT_UINT8 )) then
         call hdclbr__(declb__, seti1__, outdata, sdsdata, n, clb,
     +        ctype, utype, outdims, nd, sdsdims, rank, cal_func)
      elseif ((utype.eq.DFNT_INT16 ) .or.
     +        (utype.eq.DFNT_UINT16)) then
         call hdclbr__(declb__, seti2__, outdata, sdsdata, n, clb,
     +        ctype, utype, outdims, nd, sdsdims, rank, cal_func)
      elseif ((utype.eq.DFNT_INT32 ) .or.
     +        (utype.eq.DFNT_UINT32)) then
         call hdclbr__(declb__, seti4__, outdata, sdsdata, n, clb,
     +        ctype, utype, outdims, nd, sdsdims, rank, cal_func)
c     En pr�vision...
c      elseif ((utype.eq.DFNT_INT64 ) .or.
c     +        (utype.eq.DFNT_UINT64)) then
c         call hdclbr__(declb__, seti8__, outdata, sdsdata, n, clb,
c     +        ctype, utype, outdims, nd, sdsdims, rank, cal_func)
      elseif  (utype.eq.DFNT_FLOAT32) then
         call hdclbr__(declb__, setr4__, outdata, sdsdata, n, clb,
     +        ctype, utype, outdims, nd, sdsdims, rank, cal_func)
      elseif  (utype.eq.DFNT_FLOAT64) then
         call hdclbr__(declb__, setr8__, outdata, sdsdata, n, clb,
     +        ctype, utype, outdims, nd, sdsdims, rank, cal_func)
      else
         print *, 'Bad Type: ', utype
      endif

      return
      end

c-------------------------------------------------------------------------------
c Sous-programme hdclbr__
c =======================
c     Ce sous-programme r�alise la d�calibration de tout un tableau et r�arrange
c     �ventuellement les �l�ments issus du SDS de mani�re � satisfaire au
c     dimensionnement du tableau fourni par l'utilisateur.
c     declb__ et set__ sont la fonction de d�calibration et le sous-programme
c     d'affectation ad�quats

      subroutine hdclbr__(declb__, set__, outdata, sdsdata, n, clb,
     +     ctype, utype, outdims, nd, sdsdims, rank, cal_func)

      implicit none

c     D�claration des arguments
      integer
     +     n,                   ! (IN)  Taille en octets du tableau cible
     +     ctype,               ! (IN)  Type de donn�e calibr�e du SDS extrait
     +     utype,               ! (IN)  Type de donn�e d�calibr�e du SDS extrait
     +     nd,                  ! (IN)  Nombre de dimensions du tableau cible
     +     rank,                ! (IN)  Nombre de dimensions du SDS extrait
     +     sdsdims(rank),       ! (IN)  Valeurs des dimensions du SDS extrait
     +     outdims(nd)          ! (IN)  Valeurs des dimensions du tableau cible

      byte
     +     outdata(*),          ! (OUT) Tableau cible
     +     sdsdata(*)           ! (IN)  Tableau contenant les donn�es calibr�es

      real*8
     +     clb(4),              ! (IN)  Coefficients de calibration des donn�es
     +     declb__              ! (IN)  Fonction de d�calibration ad�quate

c     D�claration des variables locales
      integer
     +     i, j, k,             ! Indices de boucles
     +     d, add,              ! Variables de manoeuvre
     +     ctsize,              ! Taille de la donn�e calibr�e du SDS extrait
     +     utsize,              ! Taille de la donn�e d�calibr�e du SDS extrait
     +     dind(nd)             ! Tableau des indices courants des dimensions

      logical
     +     affectsw

c     D�claration des fonctions
      integer htypsize          ! Retourne la taille en octets d'un type HDF
      real*8 cal_func

c     R�cup�ration des tailles en octets des types calibr�s et d�calibr�s
      ctsize = htypsize(ctype)
      utsize = htypsize(utype)

      affectsw = .false.
      if (nd.gt.1) then
         do 50 i = 1, rank
 50         if (outdims(i).ne.sdsdims(i)) affectsw = .true.
      endif

      if (.not.affectsw) then

c     Ici, seulement d�calibration, le tableau cible �tant mono-dimensionnel ou
c     pouvant �tre consid�r� comme tel

         j = 1
         do 100 i = 1, n, utsize
            call set__(outdata(i), declb__(sdsdata(j), clb, cal_func))
 100        j = j + ctsize   

      else
         print *, 'Affectation'

c     Ici, d�calibration, puis rangement en fonction des dimensions du tableau
c     cible.
         do 200 i = 1, nd
 200        dind(i) = 1

         do 1000 i = 1, n, ctsize
            
c     Calcul de l'indice mono-dimensionnel du tableau cible
            k = dind(1)
            d = outdims(1)
            do 500 j = 2, nd
               k = k + (dind(j)-1)*d
 500           d = d*outdims(j)

c     Affectation de l'�l�ment courant 
            call set__(outdata(1+utsize*(k-1)),
     +              declb__(sdsdata(i), clb, cal_func))
 
c     Calcul des prochains indices multi-dimensionnels du tableau cible
            add = 1
            do 700 j = 1, nd
               dind(j) = dind(j) + add
               add = 0
               if (dind(j).gt.sdsdims(j)) then
                  dind(j) = 1
                  add = 1
               endif
 700        continue

 1000    continue

      endif

      return
      end

c-------------------------------------------------------------------------------
