/**
 * @file read_sds_structure.c
 * @brief How to read the structure of an HDF4 SDS : retrieve its name, type, rank, dimensions and list of attributes
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
    sds_info.v_attr = NULL; // !!! mandatory initialisation of vector of attributes. a glibc's free error can occur if not !!!
    /* return status */
    intn status    = -1;

    /**********************************************/
    /* RETRIEVE SDS STRUCTURE :                   */
    /* name, type, rank, dimensions and list of   */
    /* attributes                                 */
    /**********************************************/

    printf("*** Structure of SDS %s ***\n",sds_name);
    status = get_sds_info(filename,sds_name,&sds_info);

    if (status == FAIL) // retrieve infos error
        return FAIL;
    /* print it out */
    print_sds_info(&sds_info);
    /* properly free internal buffers */
    free_sds_info(&sds_info);

    return 0;
}
