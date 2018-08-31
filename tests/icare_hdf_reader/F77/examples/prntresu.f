      subroutine prntinfo(
     +     sdsnam, dtype, nd, dims, nval, ctype, utype,
     +     rank, dimsizes, data, clbsw, clb, nattr, nattnam,
     +     att_name
     +     )

      implicit none

      include 'netcdf.inc'

      character*(*)
     +     sdsnam,
     +     att_name(*)

      integer
     +     nval,
     +     dtype,
     +     ctype,
     +     utype,
     +     nd,
     +     dims(nd),
     +     rank,
     +     nattr,
     +     nattnam,
     +     dimsizes(rank)

      byte
     +     data(*)

      logical
     +     clbsw

      real*8
     +     clb(4)

      character*14
     +     cname,
     +     uname,
     +     dname

      character*60
     +     cdesc,
     +     udesc,
     +     ddesc

      integer
     +     i,
     +     csize,
     +     usize,
     +     dsize,
     +     arrsiz__

      print *
      print *, 'Nom du SDS: ', sdsnam(1:len_trim(sdsnam))

      print *, "Nombre d'attributs du SDS: ", nattr
      if (nattr.gt.0) then
         print *, 'SDS attributes names: '
         do 10 i = 1, min(nattr, nattnam)
 10         print *, '  ', att_name(i)(1:len_trim(att_name(i)))
      endif

      call htypinfo(ctype, cname, cdesc, csize)
      call htypinfo(utype, uname, udesc, usize)

      if (clbsw) then
         print *, 'Taille en octets des données extraites: ', nval*csize
      else
         print *, 'Taille en octets des données extraites: ', nval*usize
      endif

      print *, "Nombre d'éléments du SDS: ", nval
      print *, 'Rang du SDS: ', rank
      print *, 'Dimensions du SDS: ', (dimsizes(i), i = 1, rank)

      if (clbsw) then

         print *, 'Données du SDS calibrées'
         print *, 'Coefficients de calibration:', clb
         print *, 'Type des données calibrées du SDS: ', cname
         print *, '    --> ', cdesc
         print *, 'Type des données décalibrées du SDS: ', uname
         print *, '    --> ', udesc

      else

         if (dtype.lt.0) then
            print *, 'Données du SDS non calibrées'
         else
            print *, 'Données du SDS non calibrées ou décalibrées'
         endif

         print *, 'Type des données du SDS: ', uname
         print *, '    --> ', udesc

      endif

      if (dtype.lt.0) return

      if (dtype.gt.0) then

         call htypinfo(dtype, dname, ddesc, dsize)

         print *
         print *, 'Taille en octets du tableau: ',
     +        arrsiz__(nd, dims)*dsize
         print *, "Nombre d'éléments du tableau: ", arrsiz__(nd, dims)
         print *, 'Rang du tableau: ', nd
         print *, 'Dimension(s) du tableau: ', (dims(i),i=1,nd)
         print *, 'Type de donnée du tableau: ', dname
         print *, '    --> ', ddesc

      endif
      
      return
      end


c-------------------------------------------------------------------------------

      subroutine prntdata(
     +     comment, rtype, rnd, rdims, ptype, pnd, pdims, data
     +     )

      implicit none

      include 'netcdf.inc'

      character*(*)
     +     comment

      integer
     +     rtype,
     +     ptype

      byte
     +     data(*)

      integer
     +     i, j, id,
     +     d,
     +     rnd,
     +     pnd,
     +     pdims(MAXNCDIM),
     +     rdims(MAXNCDIM),
     +     dind(pnd),
     +     psize,
     +     rsize,
     +     htypsize

      print *
      print *, comment
      print *, '================='

      psize = htypsize(ptype)
      if (rtype.le.0) then
         rsize = psize
      else
         rsize = htypsize(rtype)
      endif

      if (pnd.eq.1) then

         call imprimer(data, pdims, ptype)

      else if (pnd.eq.2) then

         i = 1
         do 100 j = 1, pdims(2)
            call imprimer(data(i), pdims, ptype)
            i = i + rdims(1)*rsize
 100     continue

      else

         do 200 id = 1, pnd
 200        dind(id) = 1

 250     continue

         print *
         print *, '(', (dind(id),',', id = pnd, 3, -1), ' *, * )'
         print *, '--------------------------------'

         i = dind(1)
         d = rsize*rdims(1)
         do 300 j = 2, pnd
            i = i + (dind(j)-1)*d
 300        d = d*rdims(j)

         do 400 j = 1, pdims(2)
            call imprimer(data(i), pdims, ptype)
            i = i + rdims(1)*rsize
 400     continue

         do 500 j = 3, pnd
            if (dind(j).ge.pdims(j)) then
               if ((j.eq.pnd)) goto 1000
               dind(j) = 1
            else
               dind(j) = dind(j) + 1
               goto 250
            endif
 500     continue

 1000    continue

      endif

      return
      end

c-------------------------------------------------------------------------------

      subroutine imprimer(data, n, htyp)

      implicit none

      include 'hdf.inc'
      include 'icadffunc.inc'
      include 'netcdf.inc'

      integer
     +     n,
     +     htyp

      byte
     +     data(n)

      if     (htyp.eq.DFNT_CHAR8  ) then
         call imprc1(n, data)
      elseif (htyp.eq.DFNT_INT8   ) then
         call impri1(n, data)
      elseif (htyp.eq.DFNT_UINT8  ) then
         call impri1(n, data)
      elseif (htyp.eq.DFNT_INT16  ) then
         call impri2(n, data)
      elseif (htyp.eq.DFNT_UINT16 ) then
         call impri2(n, data)
      elseif (htyp.eq.DFNT_INT32  ) then
         call impri4(n, data)
      elseif (htyp.eq.DFNT_UINT32 ) then
         call impri4(n, data)
      elseif (htyp.eq.DFNT_FLOAT32) then
         call imprr4(n, data)
      elseif (htyp.eq.DFNT_FLOAT64) then
         call imprr8(n, data)
      else
         print *, 'Bad type'
      endif

      return
      end

c-------------------------------------------------------------------------------

      subroutine impr__(n,
     +     c1data, i1data, i2data, i4data, r4data, r8data)

      implicit none

      integer n                 ! Taille du tableau en entrée
      character c1data(n)       ! (IN) Tableau à imprimer pour imprc1
      integer*1 i1data(n)       ! (IN) Tableau à imprimer pour impri1
      integer*2 i2data(n)       ! (IN) Tableau à imprimer pour impri2
      integer*4 i4data(n)       ! (IN) Tableau à imprimer pour impri4
      real*4    r4data(n)       ! (IN) Tableau à imprimer pour imprr4
      real*8    r8data(n)       ! (IN) Tableau à imprimer pour imprr8
      integer i                 ! Indice de boucle

      entry imprc1(n, c1data)
      print *, (c1data(i), i = 1, n)
      return

      entry impri1(n, i1data)
      print *, (i1data(i), i = 1, n)
      return

      entry impri2(n, i2data)
      print *, (i2data(i), i = 1, n)
      return

      entry impri4(n, i4data)
      print *, (i4data(i), i = 1, n)
      return

      entry imprr4(n, r4data)
      print *, (r4data(i), i = 1, n)
      return

      entry imprr8(n, r8data)
      print *, (r8data(i), i = 1, n)
      return

      end
