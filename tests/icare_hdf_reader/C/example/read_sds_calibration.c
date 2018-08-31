/**
 * @file read_sds_calibration.c
 * @brief How to read the calibration of a HDF4 SDS
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

    /**********************************************/
    /*           READ A SDS CALIBRATION           */
    /**********************************************/
    printf("*** Calibration of SDS %s ***\n",sds_name);
    status = get_sds_calibration(filename, sds_name, &cal, &cal_err, &offset, &offset_err, &data_type);
    if (status == FAIL) // read calibration error
        return FAIL;
    printf("sds %s : cal=%f cal_err=%f offset=%f offset_err=%f data_type=%d\n", sds_name, cal, cal_err, offset, offset_err, (int)(data_type));

    return 0;
}
