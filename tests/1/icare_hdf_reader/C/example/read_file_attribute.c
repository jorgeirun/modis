/**
 * @file read_file_attribute.c
 * @brief describes how to read an HDF4 file attribute, using the c_hdf_reader library
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
    /* return status */
    intn status    = -1;
    /* name of a file attribute */
    char file_attr_name[]="Date";
    /* structure that will store a file attribute information */
    ATTR   file_attr;

    /**********************************************/
    /*          READ A FILE ATTRIBUTE             */
    /**********************************************/
    printf("*** Read file attribute %s ***\n",file_attr_name);
    /* read its value */
    status=get_file_attr(filename, file_attr_name, &file_attr);
    if (status == FAIL) // retrieve file attribute error
        return FAIL;
    /* print it out */
    print_attr(&file_attr);
    /* properly free internal buffers */
    free_attr(&file_attr);

    return 0;
}
