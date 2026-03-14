# C291 Assignment 3 Makefile
AN = A3
CLASS = C291

# basic parameters used pervasively
CFLAGS = -Wall -Wno-comment -Werror -g 
CC     = gcc $(CFLAGS)
SHELL  = bash
CWD    = $(shell pwd | sed 's/.*\///g')

PROGRAMS = \
	hashset_main 


all : hashset_main

# cleaning target to remove compiled programs/objects
clean :
	rm -f $(PROGRAMS) *.o vgcore.*

help :
	@echo 'Typical usage is:'
	@echo '  > make                          # build all programs'
	@echo '  > make test                     # run all tests'
	@echo '  > make clean                    # remove all compiled items'
	@echo '  > make test testnum=5           # run test #5 only'


################################################################################
# hashset problem
hashset_main : hashset_main.o hashset_funcs.o
	$(CC) -o $@ $^

hashset_main.o : hashset_main.c hashset.h
	$(CC) -c $<

hashset_funcs.o : hashset_funcs.c hashset.h
	$(CC) -c $<


test-setup:
	@chmod u+x testy

test : hashset_main test-setup
	./testy test_hashset.org $(testnum)


