! Cet exemple illustre la manière de récupérer des informations sur un fichier HDF:
! liste des SDS du fichier avec leur nom et liste des attributs du fichier avec leur nom.

program example2

  use ica_f90_hdf_sds

  ! Déclaration des variables "paramètres"

  character(len=MAXNCNAM)                            :: &
       hdf_file = 'testfile.hdf'   ! Chemin du fichier HDF

  integer                                            :: &
       nsds,                     & ! Nombre de SDS du fichier
       natt                        ! Nombre d'attributs du fichier

  character(len=MAXNCNAM), dimension(:), allocatable :: &
       sds_name,                 & ! Tableau des noms des SDS du fichier
       att_name                    ! Tableau des noms des attributs du fichier

  ! Déclaration des variables "utilisateur"

  integer                                            :: &
       i                           ! Indice de boucle

  type(hdf_att), dimension(:), allocatable, target   :: &
       attr                        ! Tableau des attributs du fichier

  ! Récupération d'informations sur le fichier HDF

  if (hdf_get_finfo(hdf_file, nsds, sds_name, natt, att_name) < 0) stop

  print *
  print *, 'HDF File: ', trim(hdf_file)

  print *
  print *, 'Number of file attributes: ', natt
  if (natt /= 0) then
     print *, 'File attributes names: '
     do i = 1, natt
        print *, '  '//trim(att_name(i))
     enddo
  endif

  print *
  print *, 'Number of SDS in file: ', nsds
  if (nsds /= 0) then
     print *, 'SDS names: '
     do i = 1, nsds
        print *, '  '//trim(sds_name(i))
     enddo
  endif
  print *
     
  ! Récupération des attributs du fichier

  if (hdf_get_file_att(hdf_file, natt, attr) < 0) stop

  if (natt > 0) print *, 'File attributes:'
  do i = 1, natt
     print *, '  Attribute name: ', trim(attr(i)%name)
     print *, '  Attribute type: ', hdf_typedesc(attr(i)%data%type)
     print *, '  Attribute dimension: ', attr(i)%data%dimsize(1)
     print *, '  Attribute values: '
     call prntvals(attr(i)%data)
     print *
  enddo
  
end program example2
