<?xml version='1.0' encoding='ISO-8859-1' standalone='yes' ?>
<tagfile>
  <compound kind="file">
    <name>check_hdf_include.c</name>
    <path>/home/pascal/depot/c_hdf_reader/configure/</path>
    <filename>check__hdf__include_8c</filename>
    <member kind="function">
      <type>int</type>
      <name>main</name>
      <anchorfile>check__hdf__include_8c.html</anchorfile>
      <anchor>a0</anchor>
      <arglist>()</arglist>
    </member>
  </compound>
  <compound kind="file">
    <name>test_hdf_reader.c</name>
    <path>/home/pascal/depot/c_hdf_reader/example/</path>
    <filename>test__hdf__reader_8c</filename>
    <includes id="c__hdf__reader_8h" name="c_hdf_reader.h" local="yes" imported="no">c_hdf_reader.h</includes>
    <member kind="function">
      <type>int</type>
      <name>main</name>
      <anchorfile>test__hdf__reader_8c.html</anchorfile>
      <anchor>a0</anchor>
      <arglist>(int argc, char **argv)</arglist>
    </member>
  </compound>
  <compound kind="file">
    <name>c_hdf_reader.h</name>
    <path>/home/pascal/depot/c_hdf_reader/include/</path>
    <filename>c__hdf__reader_8h</filename>
    <class kind="struct">ATTR</class>
    <class kind="struct">SDS_INFO</class>
    <member kind="function">
      <type>int32</type>
      <name>open_hdf_file</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a0</anchor>
      <arglist>(const char *filename)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>close_hdf_file</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a1</anchor>
      <arglist>(const int32 hdf_id)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_file_info</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a2</anchor>
      <arglist>(const char *filename, int32 *n_sds, char ***v_sds_name, int32 *n_file_attr, char ***v_file_attr_name)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_sds_info</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a3</anchor>
      <arglist>(const char *filename, const char *sds_name, SDS_INFO *sds_info)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_sds_info_by_id</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a4</anchor>
      <arglist>(const int32 sd_id, const char *sds_name, SDS_INFO *sds_info)</arglist>
    </member>
    <member kind="function">
      <type>void</type>
      <name>print_sds_info</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a5</anchor>
      <arglist>(const SDS_INFO *sds_info)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_file_attr</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a6</anchor>
      <arglist>(const char *filename, const char *attr_name, ATTR *attr)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_file_attr_by_id</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a7</anchor>
      <arglist>(const int32 sd_id, const char *attr_name, ATTR *attr)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_sds_attr</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a8</anchor>
      <arglist>(const char *filename, const char *sds_name, const char *attr_name, ATTR *attr)</arglist>
    </member>
    <member kind="function">
      <type>void</type>
      <name>print_attr</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a9</anchor>
      <arglist>(const ATTR *attr)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_obj_attr</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a10</anchor>
      <arglist>(const int32 obj_id, const char *attr_name, ATTR *attr)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_obj_attr_by_index</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a11</anchor>
      <arglist>(const int32 obj_id, const int32 i_attr, ATTR *attr)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>read_scaled_sds</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a12</anchor>
      <arglist>(const char *filename, const char *sds_name, double **sds_data, int32 *start, int32 *stride, int32 *edges)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>read_scaled_sds_by_id</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a13</anchor>
      <arglist>(const int32 sd_id, const char *sds_name, double **sds_data, int32 *start, int32 *stride, int32 *edges)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>read_sds</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a14</anchor>
      <arglist>(const char *filename, const char *sds_name, void **sds_data, int32 *start, int32 *stride, int32 *edges)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>read_sds_by_id</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a15</anchor>
      <arglist>(const int32 sd_id, const char *sds_name, void **sds_data, int32 *start, int32 *stride, int32 *edges)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_sds_calibration</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a16</anchor>
      <arglist>(const char *filename, const char *sds_name, float64 *cal, float64 *cal_err, float64 *offset, float64 *offset_err, int32 *data_type)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_sds_calibration_by_id</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a17</anchor>
      <arglist>(const int32 sd_id, const char *sds_name, float64 *cal, float64 *cal_err, float64 *offset, float64 *offset_err, int32 *data_type)</arglist>
    </member>
    <member kind="function">
      <type>int16</type>
      <name>get_data_type_size</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a18</anchor>
      <arglist>(const int32 data_type)</arglist>
    </member>
    <member kind="function">
      <type>void</type>
      <name>print_data_type_description</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a19</anchor>
      <arglist>(const int32 data_type)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_sds_id</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a20</anchor>
      <arglist>(const int32 sd_id, const char *sds_name)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>set_start_stride_edge</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a21</anchor>
      <arglist>(const int32 sd_id, const char *sds_name, int32 **start, const int32 *stride, int32 **edges)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>scale_data</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a22</anchor>
      <arglist>(const void *unscaled_data, const int32 unscaled_data_type, const int32 nb_data, const double scale_factor, const double offset, double *scaled_data)</arglist>
    </member>
    <member kind="function">
      <type>void</type>
      <name>free_typed_data</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a23</anchor>
      <arglist>(void **data, const int32 data_type)</arglist>
    </member>
    <member kind="function">
      <type>void</type>
      <name>free_attr</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a24</anchor>
      <arglist>(ATTR *attr)</arglist>
    </member>
    <member kind="function">
      <type>void</type>
      <name>free_sds_info</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a25</anchor>
      <arglist>(SDS_INFO *sds_info)</arglist>
    </member>
    <member kind="function">
      <type>void</type>
      <name>free_v_string</name>
      <anchorfile>c__hdf__reader_8h.html</anchorfile>
      <anchor>a26</anchor>
      <arglist>(char ***v_string, const int32 n_string)</arglist>
    </member>
  </compound>
  <compound kind="file">
    <name>c_hdf_reader.c</name>
    <path>/home/pascal/depot/c_hdf_reader/src/</path>
    <filename>c__hdf__reader_8c</filename>
    <includes id="c__hdf__reader_8h" name="c_hdf_reader.h" local="yes" imported="no">c_hdf_reader.h</includes>
    <member kind="function">
      <type>int32</type>
      <name>open_hdf_file</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a0</anchor>
      <arglist>(const char *filename)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>close_hdf_file</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a1</anchor>
      <arglist>(const int32 hdf_id)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_sds_info</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a2</anchor>
      <arglist>(const char *filename, const char *sds_name, SDS_INFO *sds_info)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_sds_info_by_id</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a3</anchor>
      <arglist>(const int32 sd_id, const char *sds_name, SDS_INFO *sds_info)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_file_info</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a4</anchor>
      <arglist>(const char *filename, int32 *n_sds, char ***v_sds_name, int32 *n_file_attr, char ***v_file_attr_name)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_file_attr_by_id</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a5</anchor>
      <arglist>(const int32 sd_id, const char *attr_name, ATTR *attr)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_file_attr</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a6</anchor>
      <arglist>(const char *filename, const char *attr_name, ATTR *attr)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_sds_attr</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a7</anchor>
      <arglist>(const char *filename, const char *sds_name, const char *attr_name, ATTR *attr)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_obj_attr_by_index</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a8</anchor>
      <arglist>(const int32 obj_id, const int32 i_attr, ATTR *attr)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_obj_attr</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a9</anchor>
      <arglist>(const int32 obj_id, const char *attr_name, ATTR *attr)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>read_scaled_sds</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a10</anchor>
      <arglist>(const char *filename, const char *sds_name, double **sds_data, int32 *start, int32 *stride, int32 *edges)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>read_sds</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a11</anchor>
      <arglist>(const char *filename, const char *sds_name, void **sds_data, int32 *start, int32 *stride, int32 *edges)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>set_start_stride_edge</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a12</anchor>
      <arglist>(const int32 sd_id, const char *sds_name, int32 **start, const int32 *stride, int32 **edge)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>read_scaled_sds_by_id</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a13</anchor>
      <arglist>(const int32 sd_id, const char *sds_name, double **sds_data, int32 *start, int32 *stride, int32 *edge)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>read_sds_by_id</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a14</anchor>
      <arglist>(const int32 sd_id, const char *sds_name, void **sds_data, int32 *start, int32 *stride, int32 *edge)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_sds_id</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a15</anchor>
      <arglist>(const int32 sd_id, const char *sds_name)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_sds_calibration</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a16</anchor>
      <arglist>(const char *filename, const char *sds_name, float64 *cal, float64 *cal_err, float64 *offset, float64 *offset_err, int32 *data_type)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>get_sds_calibration_by_id</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a17</anchor>
      <arglist>(const int32 sd_id, const char *sds_name, float64 *cal, float64 *cal_err, float64 *offset, float64 *offset_err, int32 *data_type)</arglist>
    </member>
    <member kind="function">
      <type>void</type>
      <name>print_sds_info</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a18</anchor>
      <arglist>(const SDS_INFO *sds_info)</arglist>
    </member>
    <member kind="function">
      <type>void</type>
      <name>print_attr</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a19</anchor>
      <arglist>(const ATTR *attr)</arglist>
    </member>
    <member kind="function">
      <type>void</type>
      <name>print_data_type_description</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a20</anchor>
      <arglist>(const int32 data_type)</arglist>
    </member>
    <member kind="function">
      <type>int16</type>
      <name>get_data_type_size</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a21</anchor>
      <arglist>(const int32 data_type)</arglist>
    </member>
    <member kind="function">
      <type>void</type>
      <name>free_typed_data</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a22</anchor>
      <arglist>(void **data, const int32 data_type)</arglist>
    </member>
    <member kind="function">
      <type>int32</type>
      <name>scale_data</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a23</anchor>
      <arglist>(const void *unscaled_data, const int32 unscaled_data_type, const int32 nb_data, const double scale_factor, const double offset, double *scaled_data)</arglist>
    </member>
    <member kind="function">
      <type>void</type>
      <name>free_attr</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a24</anchor>
      <arglist>(ATTR *attr)</arglist>
    </member>
    <member kind="function">
      <type>void</type>
      <name>free_sds_info</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a25</anchor>
      <arglist>(SDS_INFO *sds_info)</arglist>
    </member>
    <member kind="function">
      <type>void</type>
      <name>free_v_string</name>
      <anchorfile>c__hdf__reader_8c.html</anchorfile>
      <anchor>a26</anchor>
      <arglist>(char ***v_string, const int32 n_string)</arglist>
    </member>
  </compound>
  <compound kind="struct">
    <name>ATTR</name>
    <filename>structATTR.html</filename>
    <member kind="variable">
      <type>char</type>
      <name>name</name>
      <anchorfile>structATTR.html</anchorfile>
      <anchor>o0</anchor>
      <arglist>[MAX_NC_NAME]</arglist>
    </member>
    <member kind="variable">
      <type>int32</type>
      <name>data_type</name>
      <anchorfile>structATTR.html</anchorfile>
      <anchor>o1</anchor>
      <arglist></arglist>
    </member>
    <member kind="variable">
      <type>int32</type>
      <name>n_val</name>
      <anchorfile>structATTR.html</anchorfile>
      <anchor>o2</anchor>
      <arglist></arglist>
    </member>
    <member kind="variable">
      <type>void *</type>
      <name>value</name>
      <anchorfile>structATTR.html</anchorfile>
      <anchor>o3</anchor>
      <arglist></arglist>
    </member>
  </compound>
  <compound kind="struct">
    <name>SDS_INFO</name>
    <filename>structSDS__INFO.html</filename>
    <member kind="variable">
      <type>char</type>
      <name>name</name>
      <anchorfile>structSDS__INFO.html</anchorfile>
      <anchor>o0</anchor>
      <arglist>[MAX_NC_NAME]</arglist>
    </member>
    <member kind="variable">
      <type>int32</type>
      <name>data_type</name>
      <anchorfile>structSDS__INFO.html</anchorfile>
      <anchor>o1</anchor>
      <arglist></arglist>
    </member>
    <member kind="variable">
      <type>int32</type>
      <name>rank</name>
      <anchorfile>structSDS__INFO.html</anchorfile>
      <anchor>o2</anchor>
      <arglist></arglist>
    </member>
    <member kind="variable">
      <type>int32</type>
      <name>dim_size</name>
      <anchorfile>structSDS__INFO.html</anchorfile>
      <anchor>o3</anchor>
      <arglist>[MAX_VAR_DIMS]</arglist>
    </member>
    <member kind="variable">
      <type>int32</type>
      <name>n_attr</name>
      <anchorfile>structSDS__INFO.html</anchorfile>
      <anchor>o4</anchor>
      <arglist></arglist>
    </member>
    <member kind="variable">
      <type>ATTR *</type>
      <name>v_attr</name>
      <anchorfile>structSDS__INFO.html</anchorfile>
      <anchor>o5</anchor>
      <arglist></arglist>
    </member>
  </compound>
  <compound kind="dir">
    <name>configure/</name>
    <path>/home/pascal/depot/c_hdf_reader/configure/</path>
    <filename>dir_000000.html</filename>
    <file>check_hdf_include.c</file>
  </compound>
  <compound kind="dir">
    <name>example/</name>
    <path>/home/pascal/depot/c_hdf_reader/example/</path>
    <filename>dir_000001.html</filename>
    <file>test_hdf_reader.c</file>
  </compound>
  <compound kind="dir">
    <name>include/</name>
    <path>/home/pascal/depot/c_hdf_reader/include/</path>
    <filename>dir_000002.html</filename>
    <file>c_hdf_reader.h</file>
  </compound>
  <compound kind="dir">
    <name>src/</name>
    <path>/home/pascal/depot/c_hdf_reader/src/</path>
    <filename>dir_000003.html</filename>
    <file>c_hdf_reader.c</file>
  </compound>
</tagfile>
