# C291 Lab 1 Makefile
AN = Lab1
CLASS = C291

# basic parameters used pervasively
CFLAGS = -Wall -Wno-comment -Werror -g 
CC     = gcc $(CFLAGS)
SHELL  = bash
CWD    = $(shell pwd | sed 's/.*\///g')

PROGRAMS = \
	containers 


all : containers

# cleaning target to remove compiled programs/objects
clean :
	rm -f $(PROGRAMS) *.o vgcore.* 

help :
	@echo 'Typical usage is:'
	@echo '  > make                          # build all programs'
	@echo '  > make test                     # run all tests'
	@echo '  > make clean                    # remove all compiled items'


################################################################################
# hashset problem
containers : containers_lab.o
	$(CC) -o $@ $^

hashset_main.o : containers_lab.c 
	$(CC) -c $<



test-setup:
	@chmod u+x testy

test : containers test-setup
	./testy test_lab1.org $(testnum)


