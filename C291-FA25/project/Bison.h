/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Bison interface for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015, 2018-2021 Free Software Foundation,
   Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
   especially those whose name start with YY_ or yy_.  They are
   private implementation details that can be changed or removed.  */

#ifndef YY_FOO_BISON_H_INCLUDED
# define YY_FOO_BISON_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 1
#endif
#if YYDEBUG
extern int foo_debug;
#endif

/* Token kinds.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    YYEMPTY = -2,
    YYEOF = 0,                     /* "end of file"  */
    YYerror = 256,                 /* error  */
    YYUNDEF = 257,                 /* "invalid token"  */
    _ERROR_ = 258,                 /* _ERROR_  */
    _BANGEQ = 259,                 /* _BANGEQ  */
    _DAMP = 260,                   /* _DAMP  */
    _LPAREN = 261,                 /* _LPAREN  */
    _RPAREN = 262,                 /* _RPAREN  */
    _STAR = 263,                   /* _STAR  */
    _PLUS = 264,                   /* _PLUS  */
    _COMMA = 265,                  /* _COMMA  */
    _MINUS = 266,                  /* _MINUS  */
    _SLASH = 267,                  /* _SLASH  */
    _SEMI = 268,                   /* _SEMI  */
    _LT = 269,                     /* _LT  */
    _LDARROW = 270,                /* _LDARROW  */
    _EQ = 271,                     /* _EQ  */
    _DEQ = 272,                    /* _DEQ  */
    _GT = 273,                     /* _GT  */
    _GTEQ = 274,                   /* _GTEQ  */
    _KW_do = 275,                  /* _KW_do  */
    _KW_else = 276,                /* _KW_else  */
    _KW_end = 277,                 /* _KW_end  */
    _KW_false = 278,               /* _KW_false  */
    _KW_fi = 279,                  /* _KW_fi  */
    _KW_fn = 280,                  /* _KW_fn  */
    _KW_if = 281,                  /* _KW_if  */
    _KW_print = 282,               /* _KW_print  */
    _KW_return = 283,              /* _KW_return  */
    _KW_then = 284,                /* _KW_then  */
    _KW_true = 285,                /* _KW_true  */
    _KW_while = 286,               /* _KW_while  */
    _DBAR = 287,                   /* _DBAR  */
    _INTEGER_ = 288,               /* _INTEGER_  */
    _IDENT_ = 289                  /* _IDENT_  */
  };
  typedef enum yytokentype yytoken_kind_t;
#endif

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
union YYSTYPE
{
#line 51 "foo.y"

  int    _int;
  char   _char;
  double _double;
  char*  _string;
  TopLevel toplevel_;
  Expr expr_;
  Binop binop_;
  Block block_;
  State state_;
  FunDecl fundecl_;
  Args args_;
  ArgsRest argsrest_;
  FArgs fargs_;
  FArgsRest fargsrest_;

#line 115 "Bison.h"

};
typedef union YYSTYPE YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif

/* Location type.  */
#if ! defined YYLTYPE && ! defined YYLTYPE_IS_DECLARED
typedef struct YYLTYPE YYLTYPE;
struct YYLTYPE
{
  int first_line;
  int first_column;
  int last_line;
  int last_column;
};
# define YYLTYPE_IS_DECLARED 1
# define YYLTYPE_IS_TRIVIAL 1
#endif




int foo_parse (yyscan_t scanner, YYSTYPE *result);


#endif /* !YY_FOO_BISON_H_INCLUDED  */
