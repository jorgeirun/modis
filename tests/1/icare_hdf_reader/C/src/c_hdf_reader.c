/**
 * @file c_hdf_reader.c
 * @brief Generic library for reading HDF4 files
 * @author Nicolas PASCAL (nicolas.pascal@icare.univ-lille1.fr), (C) Centre de Gestion et de Traitement de Données (CGTD) ICARE 2008
 * @version 0.0.0
 * @date 2008/02/25
 *
 * Copyright: See COPYING file that comes with this distribution
 *
 * History :
 *   v0.0.0 : creation
 *
 */

#include "c_hdf_reader.h"

int32 open_hdf_file( const char * filename ) {
    int32 sd_id = FAIL;
    intn status = FALSE;

    /* test input filename validity */
    if ( filename == NULL ) {
        fprintf(stderr,"%s[L%d] Given Filename is NULL\n",__FILE__,__LINE__);
        return FAIL;
    }
    /* test if the file is really an HDF file */
    status = Hishdf(filename);
    if (status == FALSE) {
        fprintf(stderr,"%s[L%d] Invalid Filename %s\n",__FILE__,__LINE__,filename);
        return FAIL;
    }

    /* init SD interface */
    sd_id = SDstart(filename, DFACC_READ);
    if (sd_id == FAIL) {
        fprintf(stderr,"%s[L%d] Fail to open file %s\n",__FILE__,__LINE__,filename);
        return FAIL;
    }
    return sd_id;
}

int32 close_hdf_file( const int32 hdf_id ) {
    intn status = FALSE;

    status = SDend(hdf_id);
    if ( status == FAIL ) {
        fprintf(stderr,"%s[L%d] Fail to close file with SD id %d\n",__FILE__,__LINE__,(int)(hdf_id));
        return FAIL;
    }
    return status;
}

int32 get_sds_info( const char * filename, const char * sds_name, SDS_INFO * sds_info ) {
    int32 err     = SUCCEED; // returned error code
    int32 sd_id   = FAIL;    // SD interface identifier
    int32 status  = FAIL;    // return code of hdf functions

    /* open HDF file for reading */
    sd_id = open_hdf_file ( filename );
    if ( sd_id == FAIL ) {
        fprintf(stderr,"%s[L%d] Failed to open the file %s\n",__FILE__,__LINE__,filename);
        err = FAIL;
        goto exit;
    }

    /* read sds info */
    status = get_sds_info_by_id(sd_id, sds_name, sds_info);
    if (status == FAIL) {
        fprintf(stderr,"%s[L%d] Can not retrieve informations on the SDS %s in the file %s\n",__FILE__,__LINE__,sds_name,filename);
        err = FAIL;
        goto exit;
    }

exit:
    /* close the file */
    status = close_hdf_file(sd_id);
    if ( status == FAIL ) {
        fprintf(stderr,"%s[L%d] close hdf file failed\n",__FILE__,__LINE__);
        err = FAIL;
    }
    return err;
}

int32 get_sds_info_by_id( const int32 sd_id, const char * sds_name, SDS_INFO * sds_info ) {
    int32 err     = SUCCEED; // returned error code
    int32 status  = FAIL;    // return code of hdf functions
    int32 sds_id  = FAIL;    // SDS interface identifier
    int   i       =  0;       // generic iterator

    /* free eventually previously allocated sds infos */
    free_sds_info(sds_info);

    /* test sds_info structure validity */
    if ( sds_info == NULL ) {
        fprintf(stderr,"%s[L%d] NULL SDS_INFO structure error\n",__FILE__,__LINE__);
        return FAIL;
    }

    /* access SDS identifier */
    sds_id = get_sds_id(sd_id, sds_name);
    if (sds_id == FAIL) {
        fprintf(stderr,"%s[L%d] Failed to access the sds %s\n",__FILE__,__LINE__,sds_name);
        err = FAIL;
        goto exit;
    }

    /* retrieve its info */
    status = SDgetinfo(sds_id, sds_info->name, &sds_info->rank, sds_info->dim_size, &sds_info->data_type, &sds_info->n_attr);

    if (status == FAIL) {
        fprintf(stderr,"%s[L%d] SDgetinfo error\n",__FILE__,__LINE__);
        err = FAIL;
        goto exit;
    }

    /* allocate SDS attributes vector */
    sds_info->v_attr = (ATTR *) malloc ( sizeof(ATTR) * sds_info->n_attr );
    if ( sds_info->v_attr == NULL) { // allocation error
        fprintf(stderr,"%s[L%d] Memory allocation error\n",__FILE__,__LINE__);
        err = FAIL;
        goto exit;
    }

    /* read and stores SDS attributes */
    for ( i = 0 ; i < sds_info->n_attr ; ++i) {
        // init attr value buffer
        sds_info->v_attr[i].value = NULL;
        status = get_obj_attr_by_index(sds_id,i,&(sds_info->v_attr[i]));
        if (status == FAIL) {
            fprintf(stderr,"%s[L%d] Failed to read SDS attribute\n",__FILE__,__LINE__);
            err = FAIL;
            goto exit;
        }
    }

exit:
    /* properly close it */
    status = SDendaccess(sds_id);
    if ( status == FAIL ) {
        fprintf(stderr,"%s[L%d] SDendaccess error\n",__FILE__,__LINE__);
        err = FAIL;
    }
    return err;
}

int32 get_file_info( const char* filename, int32 * n_sds, char *** v_sds_name, int32 * n_file_attr, char *** v_file_attr_name ) {
    int32 err     =  SUCCEED; // returned error code
    int32 status  = -1;       // return code of hdf functions
    int32 sd_id   = -1;       // SD interface identifier
    int32 sds_id  = -1;       // SDS interface identifier
    int   i       =  0;       // generic iterator

    char  sds_name[MAX_NC_NAME]; // name of an sds
    int32 sds_rank; // rank of an SDS
    int32 sds_dim_size[MAX_NC_DIMS]; // size of the SDS dimensions
    int32 sds_data_type; // HDF type code of the SDS data
    int32 sds_n_attr; // number of attributes of the SDS

    char  file_attr_name[MAX_NC_NAME]; // name of a file attribute
    int32 file_attr_data_type; // HDF data type of a file attribute
    int32 file_attr_n_val; // number of values of a file attribute

    /* open HDF file for reading */
    sd_id = open_hdf_file ( filename );
    if ( sd_id == FAIL ) {
        fprintf(stderr,"%s[L%d] Failed to open the file %s\n",__FILE__,__LINE__,filename);
        err = FAIL;
        goto exit;
    }

    /* retrieve number of SDS and number of file attributes */
    status = SDfileinfo(sd_id, n_sds, n_file_attr);
    if (status == FAIL) {
        fprintf(stderr,"%s[L%d] Failed to rerieve informations of the file %s\n",__FILE__,__LINE__,filename);
        err = FAIL;
        goto exit;
    }

    /* read the sds informations */
    if ( (*n_sds) > 0 ) {
        /* eventually allocate table of SDS names */
        if ( (*v_sds_name) == NULL ) {
            /* Allocate the number of sds */
            (*v_sds_name) = (char **) malloc ( sizeof(char *) * (*n_sds) );
            if ( (*v_sds_name) == NULL) { // allocation error
                fprintf(stderr,"%s[L%d] Memory allocation error\n",__FILE__,__LINE__);
                err = FAIL;
                goto exit;
            }
            /* allocate each item of thsi list to store the name of the sds */
            for ( i = 0 ; i < (*n_sds) ; ++i ) {
                (*v_sds_name)[i] = (char *) malloc ( sizeof(char) * MAX_NC_NAME );
                /* initialize the buffer */
                memset( (*v_sds_name)[i] , 0, sizeof(char) * MAX_NC_NAME );
            }
        }

        /* fill SDS structures */
        for ( i = 0 ; i < (*n_sds) ; ++i ) {
            /* select the sds to read */
            sds_id = SDselect(sd_id, i);
            if (sds_id == FAIL){
                fprintf(stderr,"%s[L%d] SDselect error\n",__FILE__,__LINE__);
                err = FAIL;
                goto exit_sds;
            }
            /* retrieve its info */
            status = SDgetinfo(sds_id, sds_name, &sds_rank, sds_dim_size, &sds_data_type, &sds_n_attr);
            if (status == FAIL) {
                fprintf(stderr,"%s[L%d] SDgetinfo error\n",__FILE__,__LINE__);
                err = FAIL;
                goto exit_sds;
            }
            /* copy its name */
            strcpy((*v_sds_name)[i],sds_name);

exit_sds:
            /* properly close it */
            status = SDendaccess(sds_id);
            if ( status == FAIL ) {
                fprintf(stderr,"%s[L%d] SDendaccess error\n",__FILE__,__LINE__);
                err = FAIL;
                goto exit;
            }
        }
    } // end if ( (*n_sds) > 0 )

    /* read the file attribute informations */
    if ( (*n_file_attr) > 0 ) {
        /* eventually allocate table of SDS names */
        if ( (*v_file_attr_name) == NULL ) {
            /* Allocate the number of file_attr */
            (*v_file_attr_name) = (char **) malloc ( sizeof(char *) * (*n_file_attr) );
            if ( (*v_file_attr_name) == NULL) { // allocation error
                fprintf(stderr,"%s[L%d] Memory allocation error\n",__FILE__,__LINE__);
                err = FAIL;
                goto exit;
            }
            /* allocate each item of this list to store the name of the file_attr */
            for ( i = 0 ; i < (*n_file_attr) ; ++i ) {
                (*v_file_attr_name)[i] = (char *) malloc ( sizeof(char) * MAX_NC_NAME );
                /* initialize the buffer */
                memset( (*v_file_attr_name)[i] , 0, sizeof(char) * MAX_NC_NAME );
            }
        }
        /* retrieve file attribute infos */
        for ( i = 0 ; i < (*n_file_attr) ; ++i ) {
            status = SDattrinfo(sd_id, i, file_attr_name, &file_attr_data_type, &file_attr_n_val);
            if (status == FAIL) {
                fprintf(stderr,"%s[L%d] SDattrinfo error\n",__FILE__,__LINE__);
                err = FAIL;
                goto exit;
            }
            /* copy its name */
            strcpy((*v_file_attr_name)[i],file_attr_name);
        }

    } // end if ( (*n_attr) > 0 )

exit:
    /* close the file */
    status = close_hdf_file(sd_id);
    if ( status == FAIL ) {
        fprintf(stderr,"%s[L%d] close hdf file failed\n",__FILE__,__LINE__);
        err = FAIL;
    }
    return err;
}


int32 get_file_attr_by_id( const int32 sd_id, const char * attr_name, ATTR * attr ) {
    int32 status = FAIL;

    /* read the file attribute informations */
    status = get_obj_attr ( sd_id, attr_name, attr );
    if (status == FAIL ) {
        fprintf(stderr,"%s[L%d] Can not read the informations of the attribute %s\n",__FILE__,__LINE__,attr_name);
        return FAIL;
    }
    return SUCCEED;
}

int32 get_file_attr( const char * filename, const char * attr_name, ATTR * attr ) {
    int32  status  = -1; // return code of hdf functions
    int32 err     =  SUCCEED; // returned error code
    int32 sd_id   = -1; // SD interface identifier

    /* open HDF file for reading */
    sd_id = open_hdf_file ( filename );
    if ( sd_id == FAIL ) {
        fprintf(stderr,"%s[L%d] Failed to open the file %s\n",__FILE__,__LINE__,filename);
        err = FAIL;
        goto exit;
    }

    /* read the attribute */
    status = get_obj_attr (sd_id, attr_name, attr);
    if ( status == FAIL ) {
        fprintf(stderr,"%s[L%d] Failed to read the attribute %s in the file %s\n",__FILE__,__LINE__,attr_name,filename);
        err = FAIL;
        goto exit;
    }

exit:
    /* close the file */
    status = close_hdf_file(sd_id);
    if ( status == FAIL ) {
        fprintf(stderr,"%s[L%d] close hdf file failed\n",__FILE__,__LINE__);
        err = FAIL;
    }
    return err;
}

int32 get_sds_attr_by_id( const int32 sd_id, const char * sds_name, const char * attr_name, ATTR * attr ) {
    int32 status   = FAIL;    // return code of hdf functions
    int32 sds_id   = FAIL;    // SDS identifier

    /* access SDS identifier */
    sds_id = get_sds_id(sd_id, sds_name);
    if (sds_id == FAIL) {
        fprintf(stderr,"%s[L%d] Failed to access the sds %s\n",__FILE__,__LINE__,sds_name);
        return FAIL;
    }

    /* read the attribute */
    status = get_obj_attr (sds_id, attr_name, attr);
    if ( status == FAIL ) {
        fprintf(stderr,"%s[L%d] Failed to read the attribute %s of SDS %s\n",__FILE__,__LINE__,attr_name,sds_name);
        return FAIL;
    }

    return SUCCEED;
}

int32 get_sds_attr( const char * filename, const char * sds_name, const char * attr_name, ATTR * attr ) {
    int32 status   = FAIL;    // return code of hdf functions
    int32 err      = SUCCEED; // returned error code
    int32 sd_id    = FAIL;    // SD interface identifier

    /* open HDF file for reading */
    sd_id = open_hdf_file ( filename );
    if ( sd_id == FAIL ) {
        fprintf(stderr,"%s[L%d] Failed to open the file %s\n",__FILE__,__LINE__,filename);
        err = FAIL;
        goto exit;
    }

    /* read the attribute */
    status = get_sds_attr_by_id(sd_id,sds_name,attr_name,attr);
    if ( status == FAIL ) {
        fprintf(stderr,"%s[L%d] Failed to read the attribute %s of SDS %s in the file %s\n",__FILE__,__LINE__,attr_name,sds_name,filename);
        err = FAIL;
        goto exit;
    }

exit:
    /* close the file */
    status = close_hdf_file(sd_id);
    if ( status == FAIL ) {
        fprintf(stderr,"%s[L%d] close hdf file failed\n",__FILE__,__LINE__);
        err = FAIL;
    }
    return err;
}

int32 get_obj_attr_by_index( const int32 obj_id, const int32 i_attr, ATTR * attr ) {
    int32 status  = FAIL; // return code of hdf functions
    /* if attr has been previously allocated, free it */
    free_attr(attr);

    /* read the attribute informations */
    status = SDattrinfo(obj_id, i_attr, attr->name, &(attr->data_type), &(attr->n_val));
    if (status == FAIL) {
        fprintf(stderr,"%s[L%d] SDattrinfo error\n",__FILE__,__LINE__);
        return FAIL;
    }

    /* allocate the attr value buffer */
    attr->value = malloc ( get_data_type_size(attr->data_type) * attr->n_val );
    if ( attr->value == NULL) { // allocation error
        fprintf(stderr,"%s[L%d] Memory allocation error\n",__FILE__,__LINE__);
        return FAIL;
    }
    /* initialize it */
//     memset( attr->value , 0, get_data_type_size(attr->data_type) * attr->n_val );

    /* read the attribute value */
    status = SDreadattr( obj_id, i_attr, attr->value );

    if (status == FAIL) {
        fprintf(stderr,"%s[L%d] Failed to read attribute\n",__FILE__,__LINE__);
        return FAIL;
    }

    return SUCCEED;
}

int32 get_obj_attr( const int32 obj_id, const char * attr_name, ATTR * attr ) {
    int32 status  = FAIL; // return code of hdf functions
    int32 i_attr  = FAIL; // index of the attribute to read

    /* test attr structure validity */
    if ( attr == NULL ) {
        fprintf(stderr,"%s[L%d] NULL \"attr\" parametre error %s\n",__FILE__,__LINE__,attr_name);
        return FAIL;
    }

    /* retrieve the attribute index */
    i_attr = SDfindattr(obj_id, attr_name);
    if ( i_attr == FAIL ) {
        fprintf(stderr,"%s[L%d] Failed to access the attribute %s\n",__FILE__,__LINE__,attr_name);
        return FAIL;
    }

    /* read attribute data */
    status = get_obj_attr_by_index(obj_id,i_attr,attr);
    if ( status == FAIL ) {
        fprintf(stderr,"%s[L%d] Failed to read the attribute %s\n",__FILE__,__LINE__,attr_name);
        return FAIL;
    }

    return SUCCEED;
}

double * read_sds_data( const char * filename, const char * sds_name, double * sds_data, int32 * start, int32 * stride, int32 * edges ) {
    int32 status  =  FAIL;    // return code of hdf functions
    int32 sd_id   =  FAIL;    // SD interface identifier

    /* open HDF file for reading */
    sd_id = open_hdf_file ( filename );
    if ( sd_id == FAIL ) {
        fprintf(stderr,"%s[L%d] Failed to open the file %s\n",__FILE__,__LINE__,filename);
        sds_data = NULL;
        goto exit;
    }

    /* read the SDS data */
    sds_data = read_sds_data_by_id(sd_id, sds_name, sds_data, start, stride, edges);
    if ( sds_data == NULL ) {
        fprintf(stderr,"%s[L%d] Read data of SDS %s failed\n",__FILE__,__LINE__,sds_name);
        goto exit;
    }

exit:
    /* close the file */
    status = close_hdf_file(sd_id);
    if ( status == FAIL ) {
        fprintf(stderr,"%s[L%d] close hdf file failed\n",__FILE__,__LINE__);
    }
    return sds_data;
}

void * read_sds_binary_data( const char * filename, const char* sds_name, void * sds_data, int32 *start, int32 *stride, int32 *edges ) {
    int32 status  =  FAIL;    // return code of hdf functions
    int32 sd_id   =  FAIL;    // SD interface identifier

    /* open HDF file for reading */
    sd_id = open_hdf_file ( filename );
    if ( sd_id == FAIL ) {
        fprintf(stderr,"%s[L%d] Failed to open the file %s\n",__FILE__,__LINE__,filename);
        sds_data = NULL;
        goto exit;
    }

    /* read the SDS data */
    sds_data = read_sds_binary_data_by_id(sd_id, sds_name, sds_data, start, stride, edges);
    if ( sds_data == NULL ) {
        fprintf(stderr,"%s[L%d] Read data of SDS %s failed\n",__FILE__,__LINE__,sds_name);
        goto exit;
    }

exit:
    /* close the file */
    status = close_hdf_file(sd_id);
    if ( status == FAIL ) {
        fprintf(stderr,"%s[L%d] close hdf file failed\n",__FILE__,__LINE__);
        sds_data = NULL;
    }
    return sds_data;
}

int32 set_start_stride_edge( const int32 sd_id, const char * sds_name, int32 **start, const int32 *stride, int32 **edge ) {
    int   i         = 0; // generic iterator
    intn  status    = FAIL; // return code of hdf functions
    int32 step      = 1;    // step between 2 values to read along a dimension
    int8  is_start_set = 1; // tell if the start parameter has been set by the caller
    int8  is_edge_set = 1; // tell if the edge parameter has been set by the caller
    SDS_INFO sds_info;     // infos of the SDS to read

    /* read infos of the sds : rank, data_type and dimensions size */
    sds_info.v_attr = NULL;
    status = get_sds_info_by_id (sd_id,sds_name,&sds_info);
    if (status == FAIL) {
        fprintf(stderr,"%s[L%d] Failed to read the data\n",__FILE__,__LINE__);
        goto exit_error;
    }

    /* eventually set start of data to read */
    if ( *start == NULL ) {
        is_start_set=0;
        *start = (int32*)malloc ( sizeof(int32) * sds_info.rank );
        if (*start == NULL) {
            fprintf(stderr,"%s[L%d] Memory allocation error\n",__FILE__,__LINE__);
            goto exit_error;
        }
        /* initialize it */
        memset(*start, 0, sizeof(**start) * sds_info.rank );
    }

    /* eventually set the number of data to read along each dimension (edge) */
    if ( *edge == NULL ) {
        is_edge_set=0;
        *edge = (int32*)malloc ( sizeof(int32) * sds_info.rank );
        if (*edge == NULL) {
            fprintf(stderr,"%s[L%d] Memory allocation error\n",__FILE__,__LINE__);
            goto exit_error;
        }
        if (stride == NULL) {
            /* no stride set -> read all the dimensions */
            memcpy( *edge, sds_info.dim_size, sizeof(int32*) * sds_info.rank );
        } else {
            /* use stride to compute number of data to read */
            for ( i = 0 ; i < sds_info.rank ; ++i )
                *edge[i]=sds_info.dim_size[i]/stride[i];
        }
    }

    /* check if start, stride, edge are not out of range */
    for ( i = 0 ; i < sds_info.rank ; ++i ) {
        if (stride != NULL)
            step = stride[i];
        if ( ( (*start)[i] + step * (*edge)[i] ) > sds_info.dim_size[i] ) {
            fprintf(stderr,"%s[L%d] Invalid dimensions size\n",__FILE__,__LINE__);
            goto exit_error;
        }
    }

    // successfull exit
    free_sds_info(&sds_info);

    return SUCCEED;

exit_error:
    /* error occured : free unused allocations done by the function */
    free_sds_info(&sds_info);

    if ( is_start_set == 0 ) {
        free (*start);
        *start = NULL;
    }
    if ( is_edge_set == 0 ) {
        free (*edge);
        *edge  = NULL;
    }
    return FAIL;
}

double* read_sds_data_by_id( const int32 sd_id, const char * sds_name, double * sds_data, int32 * start, int32 * stride, int32 * edge ) {
    int   i         = 0;
    intn  status    = FAIL; // return code of hdf functions
    int32 nb_data   = 0;    // number of data to read
    int8  is_start_set = 1; // tell if the start parameter has been set by the caller
    int8  is_edge_set = 1; // tell if the edge parameter has been set by the caller
    float64 cal = 0.; // calibration factor
    float64 cal_err = 0.; // calibration error
    float64 offset = 0.; // calibration offset
    float64 offset_err = 0.; // calibration offset error
    int32   data_type = -1; // Data Type code
    int is_inner_allocation = 0; // tells if the method does the allocation of sds_data or not
    int is_error_occur = 0; // tells if an error occurs after the allocation of sds_data

    SDS_INFO sds_info;     // infos of the SDS to read
    void* unscaled_sds_data = NULL; // buffer that will store unscaled data

    /* read infos of the sds : rank, data_type and dimensions size */
    sds_info.v_attr = NULL;
    status = get_sds_info_by_id (sd_id,sds_name,&sds_info);
    if (status == FAIL) {
        fprintf(stderr,"%s[L%d] Failed to read the data\n",__FILE__,__LINE__);
        return NULL;
    }

    /* set if the function have to guess the number of data to read */
    if ( start == NULL ) is_start_set = 0;
    if ( edge  == NULL ) is_edge_set  = 0;

    /* eventually guess the number of data to read */
    status = set_start_stride_edge(sd_id,sds_name,&start,stride,&edge);
    if (status == FAIL) {
        fprintf(stderr,"%s[L%d] Failed to guess number of data to read in SDS %s\n",__FILE__,__LINE__,sds_name);
        sds_data = NULL;
        goto exit;
    }

    /* compute the number of data to read and so on the number of data of the buffer */
    nb_data = 1;
    for ( i = 0 ; i < sds_info.rank ; ++i )
        nb_data*=edge[i];

    /* read unscaled data */
    unscaled_sds_data = read_sds_binary_data_by_id(sd_id,sds_name,unscaled_sds_data,start,stride,edge);
    if (unscaled_sds_data == NULL) {
        fprintf(stderr,"%s[L%d] Failed to read data of SDS %s\n",__FILE__,__LINE__,sds_name);
        sds_data = NULL;
        goto exit;
    }

    /* read the scaling of the SDS */
    status = get_sds_calibration_by_id(sd_id, sds_name, &cal, &cal_err, &offset, &offset_err, &data_type);
    if (status == FAIL) {
        fprintf(stderr,"%s[L%d] Failed to read the calibration of SDS %s\n",__FILE__,__LINE__,sds_name);
        sds_data = NULL;
        goto exit;
    }

    /* eventually allocate scaled data buffer */
    if ( sds_data == NULL) {
        is_inner_allocation = 1;
        sds_data = (double*) malloc ( sizeof(double) * nb_data );
        if ( sds_data == NULL) {
            fprintf(stderr,"%s[L%d] Memory allocation error\n",__FILE__,__LINE__);
            goto exit;
        }
    }

    /* apply scaling to it */
    status = scale_data( unscaled_sds_data, data_type, nb_data, cal, offset, sds_data );
    if (status == FAIL) {
        fprintf(stderr,"%s[L%d] Failed to calibrate the data of the SDS %s\n",__FILE__,__LINE__,sds_name);
        is_error_occur = 1;
        goto exit;
    }

exit:
    /* free eventual allocations */
    free_sds_info(&sds_info);

    if ( is_start_set == 0 ) {
        free(start);
        start = NULL;
    }
    if ( is_edge_set == 0 ) {
        free(edge);
        edge = NULL;
    }

    /* in case of error, free inner allocated data buffer */
    if ( is_error_occur && is_inner_allocation ) {
        free(sds_data);
        sds_data=NULL;
    }

    /* free unscaled data buffer */
    free_typed_data(unscaled_sds_data,sds_info.data_type);

    return sds_data ;
}

void * read_sds_binary_data_by_id( const int32 sd_id, const char* sds_name, void * sds_data, int32 *start, int32 *stride, int32 *edge ) {
    int   i         = 0;
    int32 sds_id    = FAIL;
    intn  status    = FAIL;
    SDS_INFO sds_info;     // infos of the SDS to read
    int32 nb_data   = 0;    // number of data to read
    int8  is_start_set = 1; // tell if the start parameter has been set by the caller
    int8  is_edge_set = 1; // tell if the edge parameter has been set by the caller
    int   is_inner_allocation = 0; // tells if the method does the allocation of sds_data or not
    int   is_error_occur = 0; // tells if an error occurs after the allocation of sds_data

    /* read infos of the sds : rank, data_type and dimensions size */
    sds_info.v_attr = NULL;
    status = get_sds_info_by_id (sd_id,sds_name,&sds_info);
    if (status == FAIL) {
        fprintf(stderr,"%s[L%d] Failed to read the data\n",__FILE__,__LINE__);
        return NULL;
    }

    /* set if the function have to guess the number of data to read */
    if ( start == NULL ) is_start_set = 0;
    if ( edge  == NULL ) is_edge_set  = 0;

    /* eventually guess the number of data to read */
    status = set_start_stride_edge(sd_id,sds_name,&start,stride,&edge);
    if (status == FAIL) {
        fprintf(stderr,"%s[L%d] Failed to guess number of data to read in SDS %s\n",__FILE__,__LINE__,sds_name);
        sds_data = NULL;
        goto exit;
    }

    /* compute the number of data to read and so on the number of data of the buffer */
    nb_data = 1;
    for ( i = 0 ; i < sds_info.rank ; ++i )
        nb_data*=edge[i];

    /* eventually allocate read data buffer */
    if ( sds_data == NULL) {
        is_inner_allocation = 1;
        sds_data = malloc ( get_data_type_size(sds_info.data_type) * nb_data );
        if ( sds_data == NULL) {
            fprintf(stderr,"%s[L%d] Memory allocation error\n",__FILE__,__LINE__);
            is_error_occur = 1;
            goto exit;
        }
    }

    /* access SDS identifier */
    sds_id = get_sds_id(sd_id, sds_name);
    if (sds_id == FAIL) {
        fprintf(stderr,"%s[L%d] Failed to access the sds %s\n",__FILE__,__LINE__,sds_name);
        is_error_occur = 1;
        return NULL;
    }

    /* start reading the data */
    status = SDreaddata(sds_id, start, stride, edge, sds_data );
    if (status == FAIL) {
        fprintf(stderr,"%s[L%d] Failed to read the data\n",__FILE__,__LINE__);
        is_error_occur = 1;
        goto exit;
    }

exit:
    /* free eventual allocations */
    free_sds_info(&sds_info);

    if ( is_start_set == 0 ) {
        free(start);
        start = NULL;
    }
    if ( is_edge_set == 0 ) {
        free(edge);
        edge = NULL;
    }

    /* properly close SDS interface */
    status = SDendaccess(sds_id);
    if ( status == FAIL ) {
        fprintf(stderr,"%s[L%d] SDendaccess error\n",__FILE__,__LINE__);
        is_error_occur = 1;
    }

    /* in case of error, free inner allocated data buffer */
    if ( is_error_occur && is_inner_allocation ) {
        free(sds_data);
        sds_data=NULL;
    }

    return sds_data;
}

int32 get_sds_id( const int32 sd_id, const char * sds_name ) {
    int32 sds_index = FAIL;
    int32 sds_id    = FAIL;

    /* test input sds_name validity */
    if ( sds_name == NULL ) {
        fprintf(stderr,"%s[L%d] Given SDS is NULL\n",__FILE__,__LINE__);
        return FAIL;
    }

    /* access to the sds index using its name */
    sds_index = SDnametoindex(sd_id, sds_name); 
    if (sds_index == FAIL) {
        fprintf(stderr,"%s[L%d] Invalid SDS %s\n",__FILE__,__LINE__,sds_name);
        return FAIL;
    }

    /* select the sds to read */
    sds_id = SDselect(sd_id, sds_index);
    if (sds_id == FAIL) {
        fprintf(stderr,"%s[L%d] SDselect failed\n",__FILE__,__LINE__);
        return FAIL;
    }

    return sds_id;
}

int32 get_sds_calibration( const char * filename, const char * sds_name, float64 * cal, float64 * cal_err, float64 * offset, float64 * offset_err, int32 * data_type ) {
    int32 err     =  SUCCEED; // returned error code
    int32 status  =  FAIL;    // return code of hdf functions
    int32 sd_id   =  FAIL;    // SD interface identifier

    /* open HDF file for reading */
    sd_id = open_hdf_file ( filename );
    if ( sd_id == FAIL ) {
        fprintf(stderr,"%s[L%d] Failed to open the file %s\n",__FILE__,__LINE__,filename);
        err = FAIL;
        goto exit;
    }

    /* read the calibration */
    status = get_sds_calibration_by_id(sd_id, sds_name, cal, cal_err, offset, offset_err, data_type);
    if ( status == FAIL ) {
        fprintf(stderr,"%s[L%d] Failed to read the calibration in file %s of sds %s\n",__FILE__,__LINE__,filename,sds_name);
        err = FAIL;
        goto exit;
    }

exit:
    /* close the file */
    status = close_hdf_file(sd_id);
    if ( status == FAIL ) {
        fprintf(stderr,"%s[L%d] close hdf file failed\n",__FILE__,__LINE__);
        err = FAIL;
    }
    return err;
}

int32 get_sds_calibration_by_id( const int32 sd_id, const char * sds_name, float64 * cal, float64 * cal_err, float64 * offset, float64 * offset_err, int32 * data_type ) {
    int32 sds_index = FAIL;
    int32 sds_id    = FAIL;
    intn  status    = FAIL;

    /* test input sds_name validity */
    if ( sds_name == NULL ) {
        fprintf(stderr,"%s[L%d] Given SDS is NULL\n",__FILE__,__LINE__);
        return FAIL;
    }

    /* access top the sds index using its name */
    sds_index = SDnametoindex(sd_id, sds_name); 
    if (sds_index == FAIL) {
        fprintf(stderr,"%s[L%d] Invalid SDS %s\n",__FILE__,__LINE__,sds_name);
        return FAIL;
    }

    /* select the sds to read */
    sds_id = SDselect(sd_id, sds_index);
    if (sds_id == FAIL) {
        fprintf(stderr,"%s[L%d] SDselect failed\n",__FILE__,__LINE__);
        return FAIL;
    }

    /* read the calibration */
    status = SDgetcal(sds_id, cal, cal_err, offset, offset_err, data_type);
    if ( status == FAIL ) {
        fprintf(stderr,"%s[L%d] Can not read the calibration of SDS %s\n",__FILE__,__LINE__,sds_name);
        return FAIL;
    }

    /* close the sds */
    status = SDendaccess(sds_id);
    if ( status == FAIL ) {
        fprintf(stderr,"%s[L%d] SDendaccess failed\n",__FILE__,__LINE__);
        return FAIL;
    }

    return SUCCEED;
}

void print_sds_info( const SDS_INFO * sds_info ) {
    int i = 0;

    if (sds_info == NULL)
        printf("NULL sds_info");

    printf("--- SDS %s ---\n",sds_info->name);
    printf("Data Type : %ld (",sds_info->data_type);
    print_data_type_description((int)(sds_info->data_type));
    printf(")\n");
    printf("Rank : %d\n",(int)(sds_info->rank));
    printf("Dimensions : [");
    for ( i = 0 ; i < sds_info->rank ; ++i)
        printf("%d,",(int)(sds_info->dim_size[i]));
    printf("]\n");
    for ( i = 0 ; i < sds_info->n_attr ; ++i)
        print_attr(&sds_info->v_attr[i]);
}

void print_attr( const ATTR * attr ) {
    int32 i = 0;

    if (attr == NULL)
        printf("NULL attribute");

    printf("-> ATTRIBUTE %s\n",attr->name);
    printf("Data Type : %d (",(int)(attr->data_type));
    print_data_type_description((int)(attr->data_type));
    printf(")\n");
    printf("Nb of Values : %d\n",(int)(attr->n_val));
    printf("Value : ");

    /* what to print depends of the data_type */
    for ( i = 0 ; i < attr->n_val ; ++i) {
        switch (attr->data_type) {
            case (DFNT_CHAR8  )  : printf("%c" , ((char8   *)(attr->value))[i]); break;
            case (DFNT_UCHAR8  ) : printf("%u" , ((uchar8  *)(attr->value))[i]); break;
            case (DFNT_UINT8  )  : printf("%u" , ((uint8   *)(attr->value))[i]); break;
            case (DFNT_UINT16 )  : printf("%hu", ((uint16  *)(attr->value))[i]); break;
            case (DFNT_UINT32 )  : printf("%lu" , ((uint32  *)(attr->value))[i]); break;
            case (DFNT_INT8   )  : printf("%d" , ((int8    *)(attr->value))[i]); break;
            case (DFNT_INT16  )  : printf("%hd", ((int16   *)(attr->value))[i]); break;
            case (DFNT_INT32  )  : printf("%ld" , ((int32   *)(attr->value))[i]); break;
            case (DFNT_FLOAT32)  : printf("%f" , ((float32 *)(attr->value))[i]); break;
            case (DFNT_FLOAT64)  : printf("%f", ((float64 *)(attr->value))[i]); break;
            default              : break;
        }
    }

    printf("\n");
}

void print_data_type_description( const int32 data_type ) {
     switch (data_type) {
        case (DFNT_UCHAR8  ) : printf("8-bit unsigned character / uchar8"); break;
        case (DFNT_CHAR8  )  : printf("8-bit signed character / char8"); break;
        case (DFNT_INT8   )  : printf("8-bit signed integer / int8"); break;
        case (DFNT_UINT8  )  : printf("8-bit unsigned integer / uint8"); break;
        case (DFNT_INT16  )  : printf("16-bit signed integer / int16"); break;
        case (DFNT_UINT16 )  : printf("16-bit unsigned integer / uint16"); break;
        case (DFNT_INT32  )  : printf("32-bit signed integer / int32"); break;
        case (DFNT_UINT32 )  : printf("32-bit unsigned integer / uint32"); break;
        case (DFNT_INT64  )  : printf("64-bit signed integer / int64"); break;
        case (DFNT_UINT64 )  : printf("64-bit unsigned integer / uint64"); break;
        case (DFNT_FLOAT32)  : printf("32-bit floating point number / float32"); break;
        case (DFNT_FLOAT64)  : printf("64-bit floating point number / float64"); break;
        default              : fprintf(stderr,"%s[L%d] Invalid HDF data type\n",__FILE__,__LINE__); break;
     }
}

int16 get_data_type_size( const int32 data_type ) {
     switch (data_type) {
        case (DFNT_UCHAR8  ) :
        case (DFNT_CHAR8  )  :
        case (DFNT_INT8   )  :
        case (DFNT_UINT8  )  : return 1; break;
        case (DFNT_INT16  )  :
        case (DFNT_UINT16 )  : return 2; break;
        case (DFNT_INT32  )  :
        case (DFNT_UINT32 )  :
        case (DFNT_FLOAT32)  : return 4; break;
        case (DFNT_FLOAT64)  : return 8; break;
        default              : fprintf(stderr,"%s[L%d] Invalid HDF data type\n",__FILE__,__LINE__); return -1; break;
     }
}

void * allocate_data( const int32 data_type, const int32 data_size ) {
   return malloc ( get_data_type_size(data_type) * data_size ) ;
}

void free_typed_data( void * data, const int32 data_type ) {
    switch (data_type) {
        case (DFNT_UCHAR8  ) : free( (uchar8 *)(data) ); data = NULL ; break;
        case (DFNT_CHAR8  )  : free( (char8  *)(data) ); data = NULL ; break;
        case (DFNT_INT8   )  : free( (int8   *)(data) ); data = NULL ; break;
        case (DFNT_UINT8  )  : free( (uint8  *)(data) ); data = NULL ; break;
        case (DFNT_INT16  )  : free( (int16  *)(data) ); data = NULL ; break;
        case (DFNT_UINT16 )  : free( (uint16 *)(data) ); data = NULL ; break;
        case (DFNT_INT32  )  : free( (int32  *)(data) ); data = NULL ; break;
        case (DFNT_UINT32 )  : free( (uint32 *)(data) ); data = NULL ; break;
        case (DFNT_FLOAT32)  : free( (float32*)(data) ); data = NULL ; break;
        case (DFNT_FLOAT64)  : free( (float64*)(data) ); data = NULL ; break;
        default              : fprintf(stderr,"%s[L%d] Invalid HDF data type\n",__FILE__,__LINE__); break;
     }
}

int32 scale_data( const void * unscaled_data, const int32 unscaled_data_type, const int32 nb_data, const double scale_factor, const double offset, double * scaled_data ) {
    int i = 0; // generic iterator
    int32 err = SUCCEED; // returned error code

    switch (unscaled_data_type) {
        case (DFNT_UCHAR8  ) :
            for ( i = 0 ; i < nb_data ; ++i )
                scaled_data[i] = scale_factor * ( ((uchar8*)(unscaled_data))[i] - offset );
            break;
        case (DFNT_CHAR8  )  :
            for ( i = 0 ; i < nb_data ; ++i )
                scaled_data[i] = scale_factor * ( ((char8*)(unscaled_data))[i] - offset );
            break;
        case (DFNT_INT8   )  :
            for ( i = 0 ; i < nb_data ; ++i )
                scaled_data[i] = scale_factor * ( ((int8*)(unscaled_data))[i] - offset );
            break;
        case (DFNT_UINT8  )  :
            for ( i = 0 ; i < nb_data ; ++i )
                scaled_data[i] = scale_factor * ( ((uint8*)(unscaled_data))[i] - offset );
            break;
        case (DFNT_INT16  )  :
            for ( i = 0 ; i < nb_data ; ++i )
                scaled_data[i] = scale_factor * ( ((int16*)(unscaled_data))[i] - offset );
            break;
        case (DFNT_UINT16 )  :
            for ( i = 0 ; i < nb_data ; ++i )
                scaled_data[i] = scale_factor * ( ((uint16*)(unscaled_data))[i] - offset );
            break;
        case (DFNT_INT32  )  :
            for ( i = 0 ; i < nb_data ; ++i )
                scaled_data[i] = scale_factor * ( ((int32*)(unscaled_data))[i] - offset );
            break;
        case (DFNT_UINT32 )  :
            for ( i = 0 ; i < nb_data ; ++i )
                scaled_data[i] = scale_factor * ( ((uint32*)(unscaled_data))[i] - offset );
            break;
        case (DFNT_FLOAT32)  :
            for ( i = 0 ; i < nb_data ; ++i )
                scaled_data[i] = scale_factor * ( ((float32*)(unscaled_data))[i] - offset );
            break;
        case (DFNT_FLOAT64)  :
            for ( i = 0 ; i < nb_data ; ++i )
                scaled_data[i] = scale_factor * ( ((float64*)(unscaled_data))[i] - offset );
            break;
        default              :
            fprintf(stderr,"%s[L%d] Scaling Error : Invalid Data Type %d\n",__FILE__,__LINE__,(int)(unscaled_data_type));
            err = FAIL;
            break;
    }
    return err;
}

void free_attr( ATTR * attr ) {
    if (attr != NULL && attr->value != NULL) {
        switch (attr->data_type) {
            case (DFNT_UCHAR8  ) : free( (uchar8 *)(attr->value) ); attr->value = NULL ; break;
            case (DFNT_CHAR8  )  : free( (char8  *)(attr->value) ); attr->value = NULL ; break;
            case (DFNT_INT8   )  : free( (int8   *)(attr->value) ); attr->value = NULL ; break;
            case (DFNT_UINT8  )  : free( (uint8  *)(attr->value) ); attr->value = NULL ; break;
            case (DFNT_INT16  )  : free( (int16  *)(attr->value) ); attr->value = NULL ; break;
            case (DFNT_UINT16 )  : free( (uint16 *)(attr->value) ); attr->value = NULL ; break;
            case (DFNT_INT32  )  : free( (int32  *)(attr->value) ); attr->value = NULL ; break;
            case (DFNT_UINT32 )  : free( (uint32 *)(attr->value) ); attr->value = NULL ; break;
            case (DFNT_FLOAT32)  : free( (float32*)(attr->value) ); attr->value = NULL ; break;
            case (DFNT_FLOAT64)  : free( (float64*)(attr->value) ); attr->value = NULL ; break;
            default              : fprintf(stderr,"%s[L%d] Invalid HDF data type\n",__FILE__,__LINE__); break;
        }
    }
}

void free_sds_info( SDS_INFO * sds_info ) {
    int i_attr = 0; // attributes iterator

    /* test sds_info structure validity */
    if ( sds_info != NULL && sds_info->v_attr != NULL ) {
        /* free all inner attributes */
        for ( i_attr = 0 ; i_attr < sds_info->n_attr ; ++i_attr )
            free_attr(&(sds_info->v_attr[i_attr]));
        free(sds_info->v_attr);
        sds_info->v_attr = NULL;
    }
}

void free_v_string( char *** v_string, const int32 n_string ) {
    int i; // generic iterator

    for ( i = 0 ; i < n_string ; ++i )
        free((*v_string)[i]);
    free(*v_string);
    *v_string = NULL;
}

int32 array_cast( const double * indata, const int32 nb_data, const int32 typecode, void * outdata ) {
    int   i = 0; // generic iterator
    int32 hdf_type_size = get_data_type_size(typecode); // size in byte(s) of the given data type

    /* eventually allocate output buffer */
    if ( indata == NULL ) {
        outdata = allocate_data(typecode,nb_data);
        if ( outdata == NULL ) {
            fprintf(stderr,"%s[L%d] Memory Allocation Error\n",__FILE__,__LINE__);
            goto exit_error;
        }
    }

    /* cast all array values */
    for ( i=0 ; i<nb_data ; ++i) {
        value_cast ( indata[i], typecode , outdata + (hdf_type_size*i) );
    }

    return SUCCEED;

exit_error:
    /* free allocations */
    free(outdata);
    outdata = NULL;
    return FAIL;
}
int32 value_cast( const double inval, const int32 typecode, void * outval ) {
    /* check inputs */
    if ( outval == NULL ) {
        fprintf(stderr,"%s[L%d] Invalid Output Value\n",__FILE__,__LINE__);
        return FAIL;
    }
    /* process casting */
    switch (typecode) {
        case (DFNT_CHAR8  )  : *(char8*)   outval = (char8)   (inval) ; return SUCCEED ; break;
        case (DFNT_UCHAR8  ) : *(uchar8*)  outval = (uchar8)  (inval) ; return SUCCEED ; break;
        case (DFNT_INT8   )  : *(int8*)    outval = (int8)    (inval) ; return SUCCEED ; break;
        case (DFNT_UINT8  )  : *(uint8*)   outval = (uint8)   (inval) ; return SUCCEED ; break;
        case (DFNT_INT16  )  : *(int16*)   outval = (int16)   (inval) ; return SUCCEED ; break;
        case (DFNT_UINT16 )  : *(uint16*)  outval = (uint16)  (inval) ; return SUCCEED ; break;
        case (DFNT_INT32  )  : *(int32*)   outval = (int32)   (inval) ; return SUCCEED ; break;
        case (DFNT_UINT32 )  : *(uint32*)  outval = (uint32)  (inval) ; return SUCCEED ; break;
        case (DFNT_FLOAT32)  : *(float32*) outval = (float32) (inval) ; return SUCCEED ; break;
        case (DFNT_FLOAT64)  : *(float64*) outval = (float64) (inval) ; return SUCCEED ; break;
        default              : fprintf(stderr,"%s[L%d] Invalid HDF data type\n",__FILE__,__LINE__); return FAIL; break;
    }
}

