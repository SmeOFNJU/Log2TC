print_tokens.exe:print_tokens.o
	gcc -O2 -fprofile-arcs -ftest-coverage -o print_tokens.exe print_tokens.o
print_tokens.o:print_tokens.c stream.h tokens.h
	gcc -fprofile-arcs -ftest-coverage -c $< 
clean:
	rm print_tokens.exe print_tokens.o
