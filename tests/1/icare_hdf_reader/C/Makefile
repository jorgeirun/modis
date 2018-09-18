#------------------------------------------------------------------------
#                       Main makefile
#------------------------------------------------------------------------

default:
	cd src ; make
	if [ ! -e lib ] ; then mkdir lib ; fi
	cp src/*.a lib/
	rm -rf src/*.a
	cd example ; make
	if [ ! -e bin ] ; then mkdir bin ; fi
	cp example/*.exe bin/
	rm -rf example/*.exe

all:
	make clean ; make
	
clean:
	rm -rf lib/*;
	rm -rf bin/*;
	cd src ; make clean
	cd example ; make clean

