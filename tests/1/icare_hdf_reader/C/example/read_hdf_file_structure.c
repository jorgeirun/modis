/**
 * @file read_hdf_file_structure.c
 * @brief How to read the structure of an HDF4 file : retrieve the names of the SDS and the names of the file attributes
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
    /* return status */
    intn status    = -1;
    /* number of SDS of the file */
    int32 n_sds = -1;
    /* list of the SDS names of a file. Set it to NULL to let the library does all memory allocations */
    char ** v_sds_name = NULL;
    /* number of file attibutes */
    int32 n_file_attr = -1;
    /* list of the attribute names of a file. Set it to NULL to let the library does all memory allocations */
    char ** v_file_attr_name = NULL;

    /**********************************************/
    /* RETRIEVE HDF STRUCTURE :                   */
    /* - LIST OF SDS NAMES and                    */
    /* - LIST OF FILE ATTRIBUTES NAMES            */
    /**********************************************/
    status = get_file_info(filename, &n_sds, &v_sds_name, &n_file_attr, &v_file_attr_name);
    if (status == FAIL) // retrieve infos error
        return FAIL;

    /* print them out */
    printf("*** Structure of file %s ***\n",filename);
    /* -> sds */
    printf("--- SDS ---\n");
    for ( i = 0 ; i < n_sds ; ++i )
        printf("\t%s\n", v_sds_name[i]);
    /* -> file attributes */
    printf("--- FILE ATTRIBUTES ---\n");
    for ( i = 0 ; i < n_file_attr ; ++i )
        printf("\t%s\n",v_file_attr_name[i]);
    printf("\n");

    /* free allocations made internally by get_file_info */
    free_v_string(&v_sds_name, n_sds);
    free_v_string(&v_file_attr_name, n_file_attr);

    return 0;
}
