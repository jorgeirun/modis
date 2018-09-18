  implicit none
  
  ! Includes n�cessaires � l'utilisation des librairies HDF

  include 'icadffunc.f90'
  include 'hdf.f90'
  include 'netcdf.f90'

  integer, parameter :: &
       UNCLBRTD = -1, &
       DECLBRTD =  0, &
       CALIBRTD =  1

  character(len=15), dimension(-1:1) :: &
       clbrstat = (/'SDS non calibr�', 'SDS d�calibr�  ', 'SDS calibr�    '/)

  ! D�claration du nouveau type de donn�e hdf_att

  type hdf_data

     integer                                    :: &
          type,                        & ! Type des donn�es
          datasize,                    & ! Taille en octets du type des donn�es
          size,                        & ! Taille en octets des donn�es
          nval,                        & ! Nombre de valeurs des donn�es
          rank,                        & ! Nombre de dimensions des donn�es
          dimsize(MAXNCDIM),           & ! Valeurs des dimensions des donn�es
          utype,                       & ! Type des donn�es d�calibr�es
          calbrtd                        ! Statut de calibration des donn�es

     real(kind=8) &
          calibr(4)                      ! Coefficients �ventuels de calibration des donn�es

     character,       dimension(:), allocatable :: &
          c1values                       ! Valeurs des donn�es

     integer(kind=1), dimension(:), allocatable :: &
          i1values                       ! Valeurs des donn�es

     integer(kind=2), dimension(:), allocatable :: &
          i2values                       ! Valeurs des donn�es

     integer(kind=4), dimension(:), allocatable :: &
          i4values                       ! Valeurs des donn�es

! En pr�vision...
!     integer(kind=8), dimension(:), allocatable :: &
!          i8values                       ! Valeurs des donn�es

     real   (kind=4), dimension(:), allocatable :: &
          r4values                       ! Valeurs des donn�es

     real   (kind=8), dimension(:), allocatable :: &
          r8values                       ! Valeurs des donn�es

  end type hdf_data

  ! D�claration du nouveau type de donn�e hdf_att

  type hdf_att

     character (len=MAXNCNAM)                   :: &
          name                           ! Nom de l'attribut

     type(hdf_data)                             :: &
          data

  end type hdf_att

  ! D�claration du nouveau type de donn�e sds_struct

  type hdf_sds

     character (len=MAXNCNAM)                   :: &
          name                           ! Nom du SDS

     integer                                    :: &
          nattr                          ! Nombre d'attributs du SDS                     

     type(hdf_data)                             :: &
          data                           ! Donn�es du SDS

     type(hdf_att), dimension(:), allocatable   :: &
          attr                           ! Attributs du SDS

  end type hdf_sds
