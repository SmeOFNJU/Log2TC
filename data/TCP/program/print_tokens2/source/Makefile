print_tokens2.exe:print_tokens2.o
	gcc -O2 -fprofile-arcs -ftest-coverage -o print_tokens2.exe print_tokens2.o
print_tokens2.o:print_tokens2.c stream.h tokens.h
	gcc -fprofile-arcs -ftest-coverage -c $< 
clean:
	rm print_tokens2.exe print_tokens2.o
