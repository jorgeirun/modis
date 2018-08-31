! Cet exemple illustre les manières les plus directes de récupérer le contenu d'un SDS d'un fichier HDF

program example1

  use ica_f90_hdf_dim_sds

  integer*1, dimension(:)    , pointer :: i1data
  integer  , dimension(:,:)  , pointer :: idata2
  integer  , dimension(:,:,:), pointer :: idata3
  real*8   , dimension(:,:)  , pointer :: rdata2
  real*8   , dimension(:,:,:), pointer :: rdata3

  external my_dclb

  print *
  print *, "Extraction des valeurs de codage du SDS de nom 'SDS_I2' dans un tableau d'entiers a 2 dimensions"
  print *, "------------------------------------------------------------------------------------------------"
  idata2 => hdf_get_int_sds_2D('testfile.hdf', 'SDS_I2', .true.)
  if (associated(idata2)) then
     do i = 1, size(idata2, 2)
        print *, idata2(:,i)
     enddo
     deallocate(idata2)
  else
     print *, "Erreur dans l'extraction du SDS"
  endif

  print *
  print *, "Extraction des valeurs réelles du SDS de nom 'SDS_I2' dans un tableau de doubles a 2 dimensions"
  print *, "-----------------------------------------------------------------------------------------------"
  rdata2 => hdf_get_dbl_sds_2D('testfile.hdf', 'SDS_I2')
  if (associated(rdata2)) then
     do i = 1, size(rdata2, 2)
        print *, rdata2(:,i)
     enddo
     deallocate(rdata2)
  else
     print *, "Erreur dans l'extraction du SDS"
  endif

  print *
  print *, "Même extraction, mais en fournissant la procédure de passage aux valeurs réelles"
  print *, "--------------------------------------------------------------------------------"
  rdata2 => hdf_get_dbl_sds_2D('testfile.hdf', 'SDS_I2', cal_sub=my_dclb)
  if (associated(rdata2)) then
     do i = 1, size(rdata2, 2)
        print *, rdata2(:,i)
     enddo
     deallocate(rdata2)
  else
     print *, "Erreur dans l'extraction du SDS"
  endif

  print *
  print *, "Extraction des valeurs de codage du SDS de nom 'SDS_I4' dans un tableau d'entiers a 3 dimensions"
  print *, "------------------------------------------------------------------------------------------------"
  idata3 => hdf_get_int_sds_3D('testfile.hdf', 'SDS_I4', .true.)
  if (associated(idata3)) then
     do i = 1, size(idata3, 3)
        do j = 1, size(idata3, 2)
           print *, idata3(:,j,i)
        enddo
        print *
     enddo
     deallocate(idata3)
  else
     print *, "Erreur dans l'extraction du SDS"
  endif

  print *
  print *, "Extraction des valeurs réelles du SDS de nom 'SDS_I4' dans un tableau de doubles a 3 dimensions"
  print *, "-----------------------------------------------------------------------------------------------"
  rdata3 => hdf_get_dbl_sds_3D('testfile.hdf', 'SDS_I4')
  if (associated(rdata3)) then
     do i = 1, size(rdata3, 3)
        do j = 1, size(rdata3, 2)
           print *, rdata3(:,j,i)
        enddo
        print *
     enddo
     deallocate(rdata3)
  else
     print *, "Erreur dans l'extraction du SDS"
  endif
  
  print *
  print *, "Extraction brute du SDS de nom 'SDS_R8' dans un vecteur d'octets (integer*1)"
  print *, "----------------------------------------------------------------------------"
  i1data => hdf_get_sds('testfile.hdf', 'SDS_R8')
  if (associated(i1data)) then
     print *, i1data
     print *
     print *, "On sait que ce SDS contient en réalité des doubles, on l'affiche donc comme tel"
     print *, "-------------------------------------------------------------------------------"
     print *, transfer(i1data, (/1.D0, 2.D0/)) 
     deallocate(i1data)
  else
     print *, "Erreur dans l'extraction du SDS"
  endif

end program example1

subroutine my_dclb(n, data, pente, offset)

  implicit none

  integer                    :: n
  real(kind=8), dimension(n) :: data
  real(kind=8)               :: pente, offset

  data = offset + data*pente

end subroutine my_dclb
