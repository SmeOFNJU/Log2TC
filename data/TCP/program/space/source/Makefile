space.exe:space.o
	gcc -O2 -fprofile-arcs -ftest-coverage -o space.exe space.o -lm
space.o:space.c strutt.h
	gcc -fprofile-arcs -ftest-coverage -c $< 
clean:
	rm space.exe space.o
