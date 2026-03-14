# C291 Lab 1 Makefile
AN = Lab1
CLASS = C291

# basic parameters used pervasively
CFLAGS = -Wall -lm -Wno-comment -Werror -g 
CC     = gcc $(CFLAGS)
SHELL  = bash
CWD    = $(shell pwd | sed 's/.*\///g')

PROGRAMS = \
	scapegoat 


all : scapegoat

# cleaning target to remove compiled programs/objects
clean :
	rm -f $(PROGRAMS) *.o vgcore.* 

help :
	@echo 'Typical usage is:'
	@echo '  > make                          # build all programs'
	@echo '  > make test                     # run all tests'
	@echo '  > make clean                    # remove all compiled items'
	@echo '  > make clean-test               # remove all test results'


################################################################################
# hashset problem
scapegoat : scapegoat_main.o scapegoat_funcs.o
	$(CC) -o $@ $^

scapegoat_main.o : scapegoat_main.c scapegoat.h
	$(CC) -c $<
scapegoat_funcs.o : scapegoat_funcs.c scapegoat.h
	$(CC) -c $<



test-setup:
	@chmod u+x testy

test : scapegoat test-setup
	./testy test_a4.org $(testnum)

clean-test :
		rm -rf test-results

