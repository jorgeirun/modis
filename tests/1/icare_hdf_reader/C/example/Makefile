#------------------------------------------------------------------------
#                       Makefile c_hdf_reader
#------------------------------------------------------------------------

SRC= $(wildcard *.c)
OBJ= $(SRC:.c=.o)

INC_FLAG= $(HDF_INC) -I../include/
LDFLAGS = -L../lib -lc_hdf_reader $(HDF_LIBS)

# #--------- Default
default: $(OBJ) read_file_attribute read_sds_attribute read_sds_calibration read_hdf_file_structure read_sds_structure read_sds_data_custom_calibration read_sds_data

# #--------- executables
read_file_attribute: read_file_attribute.o
	 $(C) $(CFLAGS) -o $@.exe $^ $(LDFLAGS)
read_sds_attribute: read_sds_attribute.o
	 $(C) $(CFLAGS) -o $@.exe $^ $(LDFLAGS)
read_sds_calibration: read_sds_calibration.o
	 $(C) $(CFLAGS) -o $@.exe $^ $(LDFLAGS)
read_hdf_file_structure: read_hdf_file_structure.o
	 $(C) $(CFLAGS) -o $@.exe $^ $(LDFLAGS)
read_sds_structure: read_sds_structure.o
	 $(C) $(CFLAGS) -o $@.exe $^ $(LDFLAGS)
read_sds_data_custom_calibration: read_sds_data_custom_calibration.o
	 $(C) $(CFLAGS) -o $@.exe $^ $(LDFLAGS)
read_sds_data: read_sds_data.o
	 $(C) $(CFLAGS) -o $@.exe $^ $(LDFLAGS)

# #--------- Default
# default: $(OBJ) test_hdf_reader
# 
# #--------- executable
# test_hdf_reader: test_hdf_reader.o
# 	 $(C) $(CFLAGS) -o $@.exe $^ $(LDFLAGS)

#--------- All
all: 
	make clean ; make 

#--------- Clean
clean:
	rm -rf *.exe $(OBJ)
	
#--------- Suffixes
.SUFFIXES: .o .c

%.o: %.c
	@$(CC) -o $@ -c $< $(CFLAGS) $(INC_FLAG)
