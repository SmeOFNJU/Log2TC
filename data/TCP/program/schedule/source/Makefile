schedule.exe:schedule.o
	gcc -O2 -fprofile-arcs -ftest-coverage -o schedule.exe schedule.o
schedule.o:schedule.c
	gcc -fprofile-arcs -ftest-coverage -c $< 
clean:
	rm schedule.exe schedule.o
