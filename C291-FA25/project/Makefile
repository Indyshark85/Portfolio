# Project Makefile
CC = gcc -g
CCFLAGS =  -W -Wall -Wsign-conversion -Wno-unused-parameter -Wno-unused-function  ${CC_OPTS}

FLEX = flex
FLEX_OPTS = -Pexpr_

BISON = bison
BISON_OPTS = -t -pexpr_

OBJS = Absyn.o Buffer.o Lexer.o Parser.o Printer.o Ast.o Eval.o

.PHONY : clean distclean

all : interpreter

help :
	@echo 'Typical usage is:'
	@echo '  > make                          # build all programs'
	@echo '  > make test                     # run all tests'
	@echo '  > make clean                    # remove all compiled items'
	@echo '  > make clean-test               # remove all test results'



clean :
	rm -f *.o interpreter

interpreter : ${OBJS} Main.c
	${CC} Main.c ${OBJS} -o interpreter

Eval.o : Eval.c Eval.h
	${CC} ${CCFLAGS} -c Eval.c 

Ast.o : Ast.c Ast.h
	${CC} ${CCFLAGS} -c Ast.c 

Absyn.o : Absyn.c Absyn.h
	${CC} ${CCFLAGS} -c Absyn.c

Buffer.o : Buffer.c Buffer.h
	${CC} ${CCFLAGS} -c Buffer.c  

# foo.c : foo.l
# 	${FLEX} ${FLEX_OPTS} -oLexer.c foo.l 

# Parser.c Bison.h : foo.y
# 	${BISON} ${BISON_OPTS} foo.y -o Parser.c  

Lexer.o : CCFLAGS+=-Wno-sign-conversion

Lexer.o : Lexer.c Bison.h
	${CC} ${CCFLAGS} -c Lexer.c

Parser.o : Parser.c Absyn.h Bison.h
	${CC} ${CCFLAGS} -c Parser.c 

Printer.o : Printer.c Printer.h Absyn.h
	${CC} ${CCFLAGS} -c Printer.c  

test : interpreter
	./testy test_project.org

test-setup:
	@chmod u+x testy
