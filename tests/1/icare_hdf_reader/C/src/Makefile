#------------------------------------------------------------------------
#                       Makefile c_hdf_reader
#------------------------------------------------------------------------

LIB  = libc_hdf_reader.a

SRC= $(wildcard *.c)
OBJ= $(SRC:.c=.o)

INC_FLAG= $(HDF_INC) -I../include/

#--------- Default
default: $(OBJ) $(LIB)

#--------- library
$(LIB): $(OBJ)
	ar $(ARFLAGS) $@ $?

#--------- All
all: 
	make clean ; make 

#--------- Clean
clean:
	rm -rf *.exe *.a $(OBJ)
	
#--------- Suffixes
.SUFFIXES: .o .c

%.o: %.c
	@$(CC) -o $@ -c $< $(CFLAGS) $(INC_FLAG)
