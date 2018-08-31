#ifndef HDF_READER_H
#define HDF_READER_H

/**
 * @file c_hdf_reader.h
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

#include <stdio.h>
#include <string.h>
#include "mfhdf.h"

/**
 * @struct ATTR
 * @brief represents the available informations about an attribute (either a file or a SDS one)
 */
typedef struct {
    /** name of the SDS */
    char name[MAX_NC_NAME];
    /** data_type code */
    int32 data_type;
    /** number of values of this attribute */
    int32 n_val;
    /** value of this attribute */
    void * value;
} ATTR;

/**
 * @struct SDS_INFO
 * @brief represents the available informations about one SDS : name, rank, dimensions, and list of attributes
 */
typedef struct {
    /** name of the SDS */
    char name[MAX_NC_NAME];
    /** data_type code */
    int32 data_type;
    /** number of dimensions */
    int32 rank;
    /** size of each dimensions */
    int32 dim_size[MAX_VAR_DIMS];
    /** number of attributes */
    int32 n_attr;
    /** list of attributes */
    ATTR * v_attr;
} SDS_INFO;

/**
 * @brief open an hdf file for reading
 * @param filename [IN] the full path to the file to be read
 * @return an hdf id (>0) used to access the file. -1 in case of error
 */
int32 open_hdf_file(const char* filename);

/**
 * @brief close an opened hdf file
 * @param hdf_id [IN] hdf id used to access the file
 * @return 0 if successfull, -1 in case of error
 */
int32 close_hdf_file(const int32 hdf_id);

/**
 * @brief retrieve the structure of an HDF file
 * @param filename [IN] the full path to the file to be read
 * @param n_sds [OUT] number of SDS of the file
 * @param v_sds_name [OUT] list of SDS names. If NULL, the function manages the allocation. If not, the caller has the repsonbility to do it
 * @param n_file_attr [OUT] number of attributes of the file
 * @param v_file_attr_name [OUT] list of file attributes names. If NULL, the function manages the allocation. If not, the caller has the responsability to do it
 * @return 0 if successfull, -1 in case of error
 */
int32 get_file_info( const char* filename, int32 * n_sds, char *** v_sds_name, int32 * n_file_attr, char *** v_file_attr_name );

/**
 * @brief retrieve informations about an SDS : its name, rank, dimensions, and list of attributes
 * @param filename [IN] the full path to the file to be read
 * @param sds_name [IN] name of the sds to read
 * @param sds_info [OUT] a SDS_INFO structure that stores the read informations
 * @return 0 if successfull, -1 in case of error
 */
int32 get_sds_info( const char* filename, const char * sds_name, SDS_INFO * sds_info );

/**
 * @brief retrieve informations about an SDS : its name, rank, dimensions, and list of attributes
 * @param sd_id [IN] an SD id as returned by the open_hdf_file function
 * @param sds_name [IN] name of the sds to read
 * @param sds_info [OUT] a SDS_INFO structure that stores the read informations
 * @return 0 if successfull, -1 in case of error
 */
int32 get_sds_info_by_id( const int32 sd_id, const char * sds_name, SDS_INFO * sds_info );

/**
 * @brief print out SDS data type, rank, dimensions and list of attributes
 * @param sds_info SDS_INFO to print
 */
void print_sds_info (const SDS_INFO * sds_info);

/**
 * @brief read a file attribute
 * @param filename [IN] the full path to the file to read
 * @param attr_name [IN] name of the attribute to read
 * @param attr [OUT] an ATTR structure that will contain the read informations (type, number of values) and the attribute value
 * @return 0 if successfull, -1 in case of error
 */
int32 get_file_attr ( const char* filename, const char* attr_name, ATTR * attr);

/**
 * @brief read a file attribute
 * @param sd_id [IN] an SD id as returned by the open_hdf_file function
 * @param attr_name [IN] name of the attribute to read
 * @param attr [OUT] buffer that will contain the attribute value. If NULL, the function manages the allocation. If not, the caller has the responsability to do it
 * @return 0 if successfull, -1 in case of error
 */
int32 get_file_attr_by_id ( const int32 sd_id, const char* attr_name, ATTR * attr);

/**
 * @brief read a sds attribute
 * @param filename [IN] the full path to the file to read
 * @param sds_name [IN] name of the sds to read
 * @param attr_name [IN] name of the attribute to read
 * @param attr [OUT] buffer that will contain the attribute value. If NULL, the function manages the allocation. If not, the caller has the responsability to do it
 * @return 0 if successfull, -1 in case of error
 */
int32 get_sds_attr ( const char* filename, const char * sds_name, const char* attr_name, ATTR * attr);

/**
 * @brief read a sds attribute
 * @param sd_id [IN] an SD id as returned by the open_hdf_file function
 * @param sds_name [IN] name of the sds to read
 * @param attr_name [IN] name of the attribute to read
 * @param attr [OUT] buffer that will contain the attribute value. If NULL, the function manages the allocation. If not, the caller has the responsability to do it
 * @return 0 if successfull, -1 in case of error
 */
int32 get_sds_attr_by_id ( const int32 sd_id, const char * sds_name, const char* attr_name, ATTR * attr);


/**
 * @brief print out data type, number of values and value of the given attribute structure
 * @param attr attribute to print
 */
void print_attr (const ATTR * attr);

/**
 * @brief retrieve an attribute, either an SDS or a file one
 * This function is a low level one. Prefer get_file_attr, or get_sds_attr functions for a high level usage
 * @param obj_id [IN] identifier of the object
 * @param attr_name [IN] name of the attribute to read
 * @param attr [OUT] Structure that will contain the attribute informations and its value
 * @return 0 if successfull, -1 in case of error
 */
int32 get_obj_attr ( const int32 obj_id, const char* attr_name, ATTR * attr );

/**
 * @brief retrieve an attribute, either an SDS or a file one
 * This function is a low level one. Prefer get_file_attr, or get_sds_attr functions for a high level usage
 * @param obj_id [IN] identifier of the object
 * @param i_attr [IN] index of the attribute to read
 * @param attr [OUT] Structure that will contain the attribute informations and its value
 * @return 0 if successfull, -1 in case of error
 */
int32 get_obj_attr_by_index( const int32 obj_id, const int32 i_attr, ATTR * attr );

/**
 * @brief read the data of one dataset and apply the scaling to it
 * @warning if the caller delegate the allocation of the data buffer (@a sds_data) to the funtion by setting it to NULL, the data will be accessed lineary. So, if the dataset has 2 dimensions, to access the value at (i,j) you will do sds_data[j*dim_x+i] instead of the usual sds_data[j][i] (with dim_x the size of the x dimension). If you want to use an indexation as [j][i], the caller must manage the allocation
 * @param filename [IN] the full path to the file to read
 * @param sds_name [IN] name of the sds to read
 * @param sds_data [OUT] the buffer where is stored the read data. If set to NULL, the method will manage the memory allocation
 * @param start [IN] Array specifying the starting location from where data is read. Set it to NULL to read all the dataset
 * @param stride [IN] Array specifying the interval between the values that will be read along each dimension. Set it to NULL to read all the contigous data
 * @param edges [IN] Array specifying the number of values to be read along each dimension. Set it to NULL to read all the dataset
 * @return a pointer to the eventually allocate data buffer if successfull, NULL in case of error
 */
double * read_sds_data( const char * filename, const char* sds_name, double *sds_data, int32 *start, int32 *stride, int32 *edges );

/**
 * @brief read the data of one dataset and apply the scaling to it
 * @warning if the caller delegate the allocation of the data buffer (@a sds_data) to the funtion by setting it to NULL, the data will be accessed lineary. So, if the dataset has 2 dimensions, to access the value at (i,j) you will do sds_data[j*dim_x+i] instead of the usual sds_data[j][i] (with dim_x the size of the x dimension). If you want to use an indexation as [j][i], the caller must manage the allocation
 * @param sd_id [IN] an SD id as returned by the open_hdf_file function
 * @param sds_name [IN] name of the sds to read
 * @param sds_data [OUT] the buffer where is stored the read data. If set to NULL, the method will manage the memory allocation
 * @param start [IN] Array specifying the starting location from where data is read. Set it to NULL to read all the dataset
 * @param stride [IN] Array specifying the interval between the values that will be read along each dimension. Set it to NULL to read all the contigous data
 * @param edges [IN] Array specifying the number of values to be read along each dimension. Set it to NULL to read all the dataset
 * @return a pointer to the eventually allocate data buffer if successfull, NULL in case of error
 */
double * read_sds_data_by_id( const int32 sd_id, const char* sds_name, double *sds_data, int32 *start, int32 *stride, int32 *edges );

/**
 * @brief read the binary data of one dataset, without scaling it
 * This method is a wrapper to the HDF "SDreaddata" function (@see http://hdf.ncsa.uiuc.edu/old/RefMan41r3_html/RM_Section_II_SD24.html#430848 for more details)
 * @warning if the caller delegate the allocation of the data buffer (@a sds_data) to the funtion by setting it to NULL, the data will be accessed lineary. So, if the dataset has 2 dimensions, to access the value at (i,j) you will do sds_data[j*dim_x+i] instead of the usual sds_data[j][i] (with dim_x the size of the x dimension). If you want to use an indexation as [j][i], the caller must manage the allocation
 * @param filename [IN] the full path to the file to read
 * @param sds_name [IN] name of the sds to read
 * @param sds_data [OUT] the buffer where is stored the read data. If set to NULL, the method will manage the memory allocation
 * @param start [IN] Array specifying the starting location from where data is read. Set it to NULL to read all the dataset
 * @param stride [IN] Array specifying the interval between the values that will be read along each dimension. Set it to NULL to read all the contigous data
 * @param edges [IN] Array specifying the number of values to be read along each dimension. Set it to NULL to read all the dataset
 * @return a pointer to the eventually allocate data buffer if successfull, NULL in case of error
 */
void* read_sds_binary_data( const char * filename, const char* sds_name, void *sds_data, int32 *start, int32 *stride, int32 *edges );

/**
 * @brief @brief read the binary data of one dataset, without scaling it using a SD interface identifier
 * This method is a wrapper to the HDF "SDreaddata" function (@see http://hdf.ncsa.uiuc.edu/old/RefMan41r3_html/RM_Section_II_SD24.html#430848 for more details)
 * @warning if the caller delegate the allocation of the data buffer (@a sds_data) to the funtion by setting it to NULL, the data will be accessed lineary. So, if the dataset has 2 dimensions, to access the value at (i,j) you will do sds_data[j*dim_x+i] instead of the usual sds_data[j][i] (with dim_x the size of the x dimension). If you want to use an indexation as [j][i], the caller must manage the allocation
 * @param sd_id [IN] an SD id as returned by the open_hdf_file function
 * @param sds_name [IN] name of the sds to read
 * @param sds_data [OUT] the buffer where is stored the read data. Allocation must be done by the caller with the right type
 * @param start [IN] Array specifying the starting location from where data is read
 * @param stride [IN] Array specifying the interval between the values that will be read along each dimension. Set it to NULL to read all the contigous data
 * @param edges [IN] Array specifying the number of values to be read along each dimension
 * @return a pointer to the eventually allocate data buffer if successfull, NULL in case of error
 */
void* read_sds_binary_data_by_id( const int32 sd_id, const char* sds_name, void *sds_data, int32 *start, int32 *stride, int32 *edges );

/**
 * @brief read the calibration informations of an sds
 * @param filename [IN] the full path to the file to read
 * @param sds_name [IN] name of the sds to read
 * @param cal [OUT] Calibration factor
 * @param cal_err [OUT] Calibration error
 * @param offset [OUT] Uncalibrated offset
 * @param offset_err [OUT] Uncalibrated offset error
 * @param data_type [OUT] Data type of uncalibrated data (@see http://hdf.ncsa.uiuc.edu/old/RefMan41r3_html/DataTypeTable.fm.html for data_type table)
 * @return 0 if successfull, -1 in case of error
 */
int32 get_sds_calibration( const char* filename, const char* sds_name, float64 *cal, float64 *cal_err, float64 *offset, float64 *offset_err, int32 *data_type );

/**
 * @brief read the calibration informations of an sds using a SD interface identifier
 * This method is a wrapper to the HDF "SDgetcal" function (@see http://hdf.ncsa.uiuc.edu/old/RefMan41r3_html/RM_Section_II_SD8.html#441065)
 * @param sd_id [IN] a SD interface id as returned by the open_hdf_file function
 * @param sds_name [IN] name of the sds to read
 * @param cal [OUT] Calibration factor
 * @param cal_err [OUT] Calibration error
 * @param offset [OUT] Uncalibrated offset
 * @param offset_err [OUT] Uncalibrated offset error
 * @param data_type [OUT] Data type of uncalibrated data (@see http://hdf.ncsa.uiuc.edu/old/RefMan41r3_html/DataTypeTable.fm.html for data_type table)
 * @return 0 if successfull, -1 in case of error
 */
int32 get_sds_calibration_by_id( const int32 sd_id, const char* sds_name, float64 *cal, float64 *cal_err, float64 *offset, float64 *offset_err, int32 *data_type );

/**
 * @brief return the size in byte(s)  of the HDF data type code given as parameter
 * @param data_type an HDF type code
 * @return the size of the type in bytes. -1 is the type is unknown
 */
int16 get_data_type_size ( const int32 data_type );

/**
 * @brief display a description of the HDF data type code given as parameter
 * @param data_type an HDF type code
 */
void print_data_type_description ( const int32 data_type );

/**
 * @brief retrieve the identifier of an sds
 * LOW LEVEL FUNCTION INTERNAL USAGE
 * @param sd_id SD interface identifier
 * @param sds_name name of the SDS to read
 * @return a SDS identifier if successfull, -1 in case of error
 */
int32 get_sds_id ( const int32 sd_id, const char * sds_name ) ;

/**
 * @brief guess the start and the number of values to read in a SDS if they are set to NULL
 * INNER FUNCTION user should not have to use this function
 * @param sd_id [IN] SD interface identifier
 * @param sds_name [IN] name of the sds to read
 * @param start [OUT] Array specifying the starting location from where data is read. Unchanged if not NULL
 * @param stride [IN] Array specifying the interval between the values that will be read along each dimension
 * @param edges [OUT] Array specifying the number of values to be read along each dimension. Unchanged if not NULL
 * @return 0 if successfull, -1 in case of error
 */
int32 set_start_stride_edge (const int32 sd_id, const char * sds_name, int32 **start, const int32 *stride, int32 **edges);

/**
 * @brief apply a scaling y = scale_factor * ( x + offset ) to an unscaled data buffer
 * @param unscaled_data [IN] unscaled data buffer
 * @param unscaled_data_type [IN] HDF type code of unscaled data
 * @param nb_data [IN] number of data contained in the buffer
 * @param scale_factor [IN] calibration factor
 * @param offset [IN] unscaled data offset
 * @param scaled_data [OUT] scaled data buffer
 * @return 0 if successfull, -1 in case of error
 */
int32 scale_data( const void* unscaled_data, const int32 unscaled_data_type, const int32 nb_data, const double scale_factor, const double offset, double* scaled_data );

/**
 * @brief allocate a generic buffer with the given HDF type and size
 * @param data_type [IN] an HDF type code
 * @param data_size [IN] number of values
 * @return a pointer to the start of the allocated buffer. NULL if case of error
 */
inline void* allocate_data(const int32 data_type, const int32 data_size);

/**
 * @brief free the given void buffer with the good type
 * @param data [INOUT] the memory buffer to free
 * @param data_type [IN] an HDF type code
 */
void free_typed_data (void* data, const int32 data_type);

/**
 * @brief properly free the memory used for an attribute
 * @param attr [INOUT] the attribute to free
 */
void free_attr (ATTR * attr);

/**
 * @brief properly free the memory used for a sds_info structure
 * @param sds_info [INOUT] the sds_info to free
 */
void free_sds_info (SDS_INFO * sds_info);

/**
 * @brief free a vector of strings
 * @param v_string the vector of strings to free
 * @param n_string number of string contained by the vector
 */

void free_v_string( char *** v_string, const int32 n_string );

/**
 * @brief cast the values in the double array indata and put them in outdata typed as given by typecode
 * @param indata [IN] input data array (double typed)
 * @param nb_data [IN] number of values in @a indata
 * @param typecode [IN] The HDF type code of @a outdata
 * @param outdata [OUT] output data array, typed as @a typecode. If NUL, the method manages the allocation
 * @return 0 if successfull, -1 in case of error
 */
int32 array_cast ( const double* indata, const int32 nb_data, const int32 typecode, void* outdata );

/**
 * @brief cast a double input value to an output one typed as given by typecode
 * @param inval [IN] input value (double typed)
 * @param typecode [IN] The HDF type code of @a outval
 * @param outval [OUT] output value, typed as @a typecode.
 * @return 0 if successfull, -1 in case of error
 */
int32 value_cast ( const double inval, const int32 typecode, void* outval );

#endif

