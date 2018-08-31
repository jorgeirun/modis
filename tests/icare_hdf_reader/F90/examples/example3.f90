! Cet exemple illustre la manière de récupérer l'intégralité des SDS d'un fichier HDF:
! tous les SDS avec tous leurs attributs

program example3

  use ica_f90_hdf_sds

  ! Déclaration des variables "paramètres"

  character(len=MAXNCNAM)                          :: &
       hdf_file = 'testfile.hdf'    ! Chemin du fichier HDF

  integer                                          :: &
       nsds                         ! Nombre de SDS extraits

  type(hdf_sds), dimension(:), allocatable, target :: &
       sds                          ! Tableau des structures des SDS extraits

  ! Déclaration des variables "utilisateur"

  integer                                          :: &
       isds,                      & ! Indice du SDS en cours de traitement
       iatt                         ! Indice de l'attribut en cours de traitement

  ! Les pointeurs ci-dessous sont définis uniquement pour alléger l'écriture

  type(hdf_sds), pointer                           :: &
       ps                           ! Pointeur sur la structure du SDS courant
  
  type(hdf_att), pointer                           :: &
       pa                           ! Pointeur sur la structure de l'attribut courant

  type(hdf_data), pointer                          :: &
       psd,                       & ! Pointeur sur les données du SDS courant
       pad                          ! Pointeur sur les données de l'attribut courant

  real(kind=8), dimension(:,:),allocatable:: rt8
  real(kind=8), dimension(:,:),pointer:: prt8

  ! Début de l'exécution

  print *
  print *, 'HDF File: ', trim(hdf_file)

  ! Récupération des SDS du fichier

  if (hdf_get_file_sds(hdf_file, nsds, sds) < 0) stop

  if (nsds > 0) print *, 'File SDS:'

  ! Affichage des SDS

  do isds = 1, nsds

     ps => sds(isds); psd => ps%data
     print *
     print *, trim(sds(isds)%name)
     print *, "  Nombre d'attributs: ", sds(isds)%nattr

     ! Affichage des attributs du SDS

     do iatt = 1, sds(isds)%nattr
        pa => ps%attr(iatt); pad => pa%data
        print *, '  Attribute name: ', trim(pa%name)
        print *, '  Attribute type: ', hdf_typedesc(pad%type)
        print *, '  Attribute dimension: ', pad%dimsize(1)
        print *, '  Attribute values: '
        call prntvals(sds(isds)%attr(iatt)%data)
        print *
     enddo

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
  end do

end program example3
