/**
 * @file read_sds_data_custom_calibration.c
 * @brief This example describes how to read the binary data of an HDF4 SDS, read its calibration factors and apply a non standard calibration to it.
 * @warning What is called a non-standard calibration is one different than "science_value = scaling_factor ( binary_file_value - offset ) ". If the SDS you are reading uses a calibration of this type you can check the "read_sds_data.c" example, which automatically do it for you.
 * @warning Products that are using a non-standard calibration are : CALIPSO IIR L2, CLOUDSAT...
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
    /* generic iterator */
    int i = 0;
    /* path to the file to read */
    char filename[] = "../data/test.hdf";
    /* name of an sds to access */
    char sds_name[] = "P3L1_Solar_Zenith_Angle";
    /* structure that stores SDS informations */
    SDS_INFO sds_info;
    /* return status */
    intn status    = -1;
    /* calibration factor */
    float64 cal=0.;
    /* calibration error */
    float64 cal_err=0.;
    /* calibration offset */
    float64 offset=0.;
    /* calibration offset error */
    float64 offset_err=0.;
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

    /* buffer that stores the uncalibrated data of an SDS. Set it to NULL to let the library does the memory allocation. This buffer must also have the good type. This type can be determined using the get_file_info function */
    uint16 * binary_data = NULL;
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
    /*           READ A SDS BINARY DATA           */
    /**********************************************/
    printf("*** Read Binary data of SDS %s ***\n",sds_name);

    /* READ THE WHOLE DATASET : set start, stride and edge parameters of read sds data to NULL. When delegating the allocation to the function, the data will be accessed lineary. So, in a dataset with 2 dimensions, to access the value at (i,j) you will do sds_data[j*dim_x+i] instead of the usual sds_data[j][i] (with dim_x the size of the x dimension) */
    binary_data = read_sds_binary_data(filename, sds_name, binary_data, NULL, NULL, NULL);
    if (binary_data == NULL) // read data error
        return FAIL;

    /* print out first read binary data */
    for ( i_y = 0 ; i_y < 10 ; ++i_y)
        for ( i_x = 0 ; i_x < dim_x ; ++i_x)
            /* buffer allocated by the read sds function are accessed lineary */
            printf("%d\t",binary_data[i_y*dim_x+i_x]);
        printf("\n");

    /**********************************************/
    /*           READ A SDS CALIBRATION           */
    /**********************************************/
    printf("*** Calibration of SDS %s ***\n",sds_name);
    status = get_sds_calibration(filename, sds_name, &cal, &cal_err, &offset, &offset_err, &data_type);
    if (status == FAIL) // read calibration error
        return FAIL;
    printf("sds %s : cal=%f cal_err=%f offset=%f offset_err=%f data_type=%d\n", sds_name, cal, cal_err, offset, offset_err, (int)(data_type));

    /**********************************************************/
    /*          APPLY CUSTOM CALIBRATION                      */
    /* using for example an equation like :                   */
    /* science_value = scaling_factor * binary_value + offset */
    /**********************************************************/

    /* allocate scaled data buffer : same size than binary one */
    scaled_data = (double*) malloc ( sizeof(double) * nb_data );

    /* apply scaling */
    for ( i=0 ; i<nb_data ; ++i ) {
        scaled_data[i] = cal * binary_data[i] + offset;
    }

    /* print out first scaled data */
    for ( i_y = 0 ; i_y < 10 ; ++i_y)
        for ( i_x = 0 ; i_x < dim_x ; ++i_x)
            /* buffer allocated by the read sds function are accessed lineary */
            printf("%4.2f\t",scaled_data[i_y*dim_x+i_x]);
        printf("\n");

    /* properly free data buffer */
    free(binary_data);
    binary_data = NULL;
    free(scaled_data);
    scaled_data = NULL;

    return 0;
}
