! Cet exemple illustre la mani�re de r�cup�rer certains des SDS d'un fichier HDF,
! avec ou sans leurs attributs, d�calibr�s ou non

program example4

  use ica_f90_hdf_sds

  ! D�claration des variables "param�tres"

  character(len=MAXNCNAM)                          :: &
       hdf_file = 'testfile.hdf'         ! Chemin du fichier HDF

  integer                                          :: &
       nsds                              ! Nombre de SDS r�ellement extraits

  type(hdf_sds), dimension(:), allocatable, target :: &
       sds                               ! Tableau des structures des SDS extraits

  ! D�claration des variables "utilisateur"

!  integer                                          :: &
!       isds,                         & ! Indice du SDS en cours de traitement
!       iatt                            ! Indice de l'attribut en cours de traitement

  ! D�but de l'ex�cution

  print *
  print *, 'HDF File: ', trim(hdf_file)

  ! R�cup�ration du SDS de nom 'SDS_I2', avec d�calibration, sans attributs

  if (hdf_get_file_sds(hdf_file, nsds, sds, 1, (/'SDS_I2'/), attr = .false.) < 0) stop
  if (nsds > 0) call prnt_sds(sds(1))

  ! R�cup�ration du SDS de nom 'SDS_I4', sans d�calibration, avec attributs

  if (hdf_get_file_sds(hdf_file, nsds, sds, 1, (/'SDS_I4'/), dclb = .false.) < 0) stop
  if (nsds > 0) call prnt_sds(sds(1))

contains

  subroutine prnt_sds(sds)
    
    type(hdf_sds), target :: &
         sds                               ! Tableau des structures des SDS extraits

    ! Les pointeurs ci-dessous sont d�finis uniquement pour all�ger l'�criture

    type(hdf_att), pointer :: &
         pa                              ! Pointeur sur la structure de l'attribut courant du SDS courant

    type(hdf_data), pointer :: &
         psd,                          & ! Pointeur sur les donn�es du SDS courant
         pad                             ! Pointeur sur les donn�es de l'attribut courant
                                         ! du SDS courant

    psd => sds%data
    print *
    print *, trim(sds%name)
    print *, "  Nombre d'attributs: ", sds%nattr
  
    if (allocated(sds%attr)) then
       do iatt = 1, sds%nattr
          pa => sds%attr(iatt); pad => pa%data
          print *, '  Attribute name: ', trim(pa%name)
          print *, '  Attribute type: ', hdf_typedesc(pad%type)
          print *, '  Attribute dimension: ', pad%dimsize(1)
          print *, '  Attribute values: '
          call prntvals(pad)
          print *
       enddo
    else
       print *, '  Attributs non retourn�s'
    endif
  
    print *, "  Type de donn�e du SDS: ", trim(hdf_typedesc(psd%type))
    print *, "  ",clbrstat(psd%calbrtd)
    if (psd%calbrtd /= UNCLBRTD) print *, "  Type de donn�e d�calibr�e: ", &
         trim(hdf_typedesc(psd%utype))
    if (psd%calbrtd == CALIBRTD) print *, "  Coefficients de calibration: ", psd%calibr
    print *, "  Nombre de dimensions du SDS: ", psd%rank
    print *, "  Dimensions du SDS: ", psd%dimsize(1:psd%rank)
  
    ! Affichage des donn�es du SDS
    
    print *, 'SDS values: '
    call prntdimvals(psd)
    print *, '--------------------------------------------'
    
  end subroutine prnt_sds

end program example4
