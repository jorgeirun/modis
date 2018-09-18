c-------------------------------------------------------------------------------
c Fonction errstd__
c =================
c     Prend en entr�e un code d'erreur bool�en et affiche le message msg.
c     Retourne le bool�en entr�.

      logical function errstd__(ierr, msg)

      implicit none

      logical ierr              ! (IN)  Flag: vrai si erreur survenue
      character*(*) msg         ! (IN)  Message � afficher en cas d'erreur

      errstd__ = ierr
      if (ierr) print *, msg

      return
      end

c-------------------------------------------------------------------------------
c Fonction arrsiz__
c =================
c     Retourne le nombre d'�l�ments d'un tableau

      integer function arrsiz__(nd, dims)

      implicit none

      integer
     +     nd,                  ! (IN) Nombre de dimension du tableau
     +     dims(*),             ! (IN) Valeurs des dimension du tableau
     +     i                    ! Indice de boucle

      arrsiz__ = 1
      do 10 i = 1, nd
 10      arrsiz__ = arrsiz__*dims(i)

      return
      end

c-------------------------------------------------------------------------------
c Sous-programmes setr8__, setr4__, seti8_, seti4__, seti2__, seti1__
c ===================================================================

c     Ces sous-programmes convertissent une valeur de type real*8 dans le type
c     souhait� (resp. real*8, real*4, integer*8, integer*4, integer*2 et
c     integer*1)

c     En pr�vision...
c      subroutine settyp__
c     +     (r8in, r8out, r4out, i8out, i4out, i2out, i1out)
      subroutine settyp__(r8in, r8out, r4out, i4out, i2out, i1out)

      implicit none

      real*8    r8in            ! (IN)  Valeur de type real*8 � convertir

      integer*1 i1out           ! (OUT) R�sultat de la conversion pour seti1__
      integer*2 i2out           ! (OUT) R�sultat de la conversion pour seti2__
      integer*4 i4out           ! (OUT) R�sultat de la conversion pour seti4__
c     En pr�vision...
c      integer*8 i8out           ! (OUT) R�sultat de la conversion pour seti8__
      real*4    r4out           ! (OUT) R�sultat de la conversion pour setr4__
      real*8    r8out           ! (OUT) R�sultat de la conversion pour setr8__

      entry setr8__(r8out, r8in)
      r8out = r8in
      return

      entry setr4__(r4out, r8in)
      r4out = r8in
      return

c     En pr�vision...
c      entry seti8__(i8out, r8in)
c      i8out = r8in
c      return

      entry seti4__(i4out, r8in)
      i4out = r8in
      return

      entry seti2__(i2out, r8in)
      i2out = r8in
      return

      entry seti1__(i1out, r8in)
      i1out = r8in
      return

      end

c-------------------------------------------------------------------------------
