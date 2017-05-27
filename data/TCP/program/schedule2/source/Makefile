schedule2.exe:schedule2.o
	gcc -O2 -fprofile-arcs -ftest-coverage -o schedule2.exe schedule2.o
schedule2.o:schedule2.c schedule2.h
	gcc -fprofile-arcs -ftest-coverage -c $< 
clean:
	rm schedule2.exe schedule2.o
