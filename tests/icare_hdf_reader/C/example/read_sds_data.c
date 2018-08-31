/**
 * @file read_sds_data.c
 * @brief This example describes how to read the data of an HDF4 SDS that uses a standard calibration
 * @warning What is called a standard calibration is one like "science_value = scaling_factor ( binary_file_value - offset ) "
 * @author Nicolas PASCAL (nicolas.pascal@icare.univ-lille1.fr), (C) Centre de Gestion et de Traitement de Données (CGTD) ICARE 2008
 * @version 0.0.0
 * @date 2008/04/07
 *
 * Copyright: See COPYING file that comes with this distribution
 *
 * History :
 *   v0.0.0 : creation
 */

#include "c_hdf_reader.h"

int main( int argc, char**argv ) {
    /* path to the file to read */
    char filename[] = "../data/test.hdf";
    /* name of an sds to access */
    char sds_name[] = "P3L1_Solar_Zenith_Angle";
    /* structure that stores SDS informations */
    SDS_INFO sds_info;
    /* return status */
    intn status    = -1;
    /* Data Type code */
    int32   data_type=-1;
    /* iterator along X axis */
    int i_x = 0;
    /* iterator along Y axis */
    int i_y = 0;
    /* size of an SDS along X axis */
    int32 dim_x = -1;
    /* size of an SDS along Y axis */
    int32 dim_y = -1;
    /* total number of data read */
    int32 nb_data = -1;
    /* buffer that stores the scaled data of an SDS. Set it to NULL to let the library does the memory allocation */
    double * scaled_data = NULL;

    /*************************************************/
    /* RETRIEVE SDS STRUCTURE to known               */
    /* its data type and size                        */
    /*************************************************/
    status = get_sds_info(filename,sds_name,&sds_info);
    if (status == FAIL) // retrieve infos error
        return FAIL;

    /* dimensions of the read sds have been retrieve using the get_sds_info function. HDF SDS dimensions are orderd as [Z,Y,X] */
    dim_y = sds_info.dim_size[0];
    dim_x = sds_info.dim_size[1];
    nb_data = dim_y * dim_x;
    /* Type code of the SDS */
    data_type = sds_info.data_type;

    /* properly free internal buffers */
    free_sds_info(&sds_info);

    /**********************************************/
    /*           READ A SCALED SDS DATA           */
    /**********************************************/
    printf("*** Read scaled data of SDS %s ***\n",sds_name);

    /* READ THE WHOLE DATASET : set start, stride and edge parameters of read sds data to NULL. When delegating the allocation to the function, the data will be accessed lineary. So, in a dataset with 2 dimensions, to access the value at (i,j) you will do sds_data[j*dim_x+i] instead of the usual sds_data[j][i] (with dim_x the size of the x dimension) */
    scaled_data = read_sds_data(filename, sds_name, scaled_data, NULL, NULL, NULL);
    if (scaled_data == NULL) // read data error
        return FAIL;
    /* print out first read data */
    for ( i_y = 0 ; i_y < 10 ; ++i_y)
        for ( i_x = 0 ; i_x < dim_x ; ++i_x)
            /* buffer allocated by the read sds function are accessed lineary */
            printf("%4.2f\t",scaled_data[i_y*dim_x+i_x]);
        printf("\n");

    /* properly free data buffer */
    free(scaled_data);
    scaled_data = NULL;

    return 0;
}
