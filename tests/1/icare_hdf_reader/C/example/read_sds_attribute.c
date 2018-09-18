/**
 * @file read_sds_attribute.c
 * @brief describes how to read an HDF4 SDS attribute, using the c_hdf_reader library
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
    /* name of a file attribute */
    char sds_attr_name[]="long_name";
    /* structure that will store a SDS attribute information */
    ATTR   sds_attr;

    /**********************************************/
    /*          READ A SDS ATTRIBUTE              */
    /**********************************************/
    printf("*** Read SDS attribute %s ***\n", sds_attr_name);
    /* read its value */
    status=get_sds_attr(filename, sds_name, sds_attr_name, &sds_attr);

    if (status == FAIL) // retrieve file attribute error
        return FAIL;
    /* print it out */
    print_attr(&sds_attr);
    /* properly free internal buffers */
    free_attr(&sds_attr);

    return 0;
}
