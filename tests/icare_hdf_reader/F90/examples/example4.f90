! Cet exemple illustre la manière de récupérer certains des SDS d'un fichier HDF,
! avec ou sans leurs attributs, décalibrés ou non

program example4

  use ica_f90_hdf_sds

  ! Déclaration des variables "paramètres"

  character(len=MAXNCNAM)                          :: &
       hdf_file = 'testfile.hdf'         ! Chemin du fichier HDF

  integer                                          :: &
       nsds                              ! Nombre de SDS réellement extraits

  type(hdf_sds), dimension(:), allocatable, target :: &
       sds                               ! Tableau des structures des SDS extraits

  ! Déclaration des variables "utilisateur"

!  integer                                          :: &
!       isds,                         & ! Indice du SDS en cours de traitement
!       iatt                            ! Indice de l'attribut en cours de traitement

  ! Début de l'exécution

  print *
  print *, 'HDF File: ', trim(hdf_file)

  ! Récupération du SDS de nom 'SDS_I2', avec décalibration, sans attributs

  if (hdf_get_file_sds(hdf_file, nsds, sds, 1, (/'SDS_I2'/), attr = .false.) < 0) stop
  if (nsds > 0) call prnt_sds(sds(1))

  ! Récupération du SDS de nom 'SDS_I4', sans décalibration, avec attributs

  if (hdf_get_file_sds(hdf_file, nsds, sds, 1, (/'SDS_I4'/), dclb = .false.) < 0) stop
  if (nsds > 0) call prnt_sds(sds(1))

contains

  subroutine prnt_sds(sds)
    
    type(hdf_sds), target :: &
         sds                               ! Tableau des structures des SDS extraits

    ! Les pointeurs ci-dessous sont définis uniquement pour alléger l'écriture

    type(hdf_att), pointer :: &
         pa                              ! Pointeur sur la structure de l'attribut courant du SDS courant

    type(hdf_data), pointer :: &
         psd,                          & ! Pointeur sur les données du SDS courant
         pad                             ! Pointeur sur les données de l'attribut courant
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
       print *, '  Attributs non retournés'
    endif
  
    print *, "  Type de donnée du SDS: ", trim(hdf_typedesc(psd%type))
    print *, "  ",clbrstat(psd%calbrtd)
    if (psd%calbrtd /= UNCLBRTD) print *, "  Type de donnée décalibrée: ", &
         trim(hdf_typedesc(psd%utype))
    if (psd%calbrtd == CALIBRTD) print *, "  Coefficients de calibration: ", psd%calibr
    print *, "  Nombre de dimensions du SDS: ", psd%rank
    print *, "  Dimensions du SDS: ", psd%dimsize(1:psd%rank)
  
    ! Affichage des données du SDS
    
    print *, 'SDS values: '
    call prntdimvals(psd)
    print *, '--------------------------------------------'
    
  end subroutine prnt_sds

end program example4
