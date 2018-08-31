DIR_EXE=exe

all : 
	mkdir -p exe
	cat src/hdfgetsds__.pro src/hdfprtsds__.pro src/hdfprtatt__.pro src/hdflstsds__.pro \
		> $(DIR_EXE)/hdfgetsds.pro
	cat src/hdfdump__.pro   src/hdfprtsds__.pro src/hdfprtatt__.pro \
		> $(DIR_EXE)/hdfdump.pro

clean :
	rm -rf exe *~ src/*.BAK src/*~


