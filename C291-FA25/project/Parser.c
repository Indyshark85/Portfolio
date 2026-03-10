/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Bison implementation for Yacc-like parsers in C

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

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
   especially those whose name start with YY_ or yy_.  They are
   private implementation details that can be changed or removed.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output, and Bison version.  */
#define YYBISON 30802

/* Bison version string.  */
#define YYBISON_VERSION "3.8.2"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 1

/* Push parsers.  */
#define YYPUSH 0

/* Pull parsers.  */
#define YYPULL 1


/* Substitute the variable and function names.  */
#define yyparse         foo_parse
#define yylex           foo_lex
#define yyerror         foo_error
#define yydebug         foo_debug
#define yynerrs         foo_nerrs

/* First part of user prologue.  */
#line 20 "foo.y"

/* Begin C preamble code */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Absyn.h"

#define YYMAXDEPTH 10000000

/* The type yyscan_t is defined by flex, but we need it in the parser already. */
#ifndef YY_TYPEDEF_YY_SCANNER_T
#define YY_TYPEDEF_YY_SCANNER_T
typedef void* yyscan_t;
#endif

typedef struct yy_buffer_state *YY_BUFFER_STATE;
extern YY_BUFFER_STATE foo__scan_string(const char *str, yyscan_t scanner);
extern void foo__delete_buffer(YY_BUFFER_STATE buf, yyscan_t scanner);

extern void foo_lex_destroy(yyscan_t scanner);
extern char* foo_get_text(yyscan_t scanner);

extern yyscan_t foo__initialize_lexer(FILE * inp);

/* List reversal functions. */

/* End C preamble code */

#line 106 "Parser.c"

# ifndef YY_CAST
#  ifdef __cplusplus
#   define YY_CAST(Type, Val) static_cast<Type> (Val)
#   define YY_REINTERPRET_CAST(Type, Val) reinterpret_cast<Type> (Val)
#  else
#   define YY_CAST(Type, Val) ((Type) (Val))
#   define YY_REINTERPRET_CAST(Type, Val) ((Type) (Val))
#  endif
# endif
# ifndef YY_NULLPTR
#  if defined __cplusplus
#   if 201103L <= __cplusplus
#    define YY_NULLPTR nullptr
#   else
#    define YY_NULLPTR 0
#   endif
#  else
#   define YY_NULLPTR ((void*)0)
#  endif
# endif

#include "Bison.h"
/* Symbol kind.  */
enum yysymbol_kind_t
{
  YYSYMBOL_YYEMPTY = -2,
  YYSYMBOL_YYEOF = 0,                      /* "end of file"  */
  YYSYMBOL_YYerror = 1,                    /* error  */
  YYSYMBOL_YYUNDEF = 2,                    /* "invalid token"  */
  YYSYMBOL__ERROR_ = 3,                    /* _ERROR_  */
  YYSYMBOL__BANGEQ = 4,                    /* _BANGEQ  */
  YYSYMBOL__DAMP = 5,                      /* _DAMP  */
  YYSYMBOL__LPAREN = 6,                    /* _LPAREN  */
  YYSYMBOL__RPAREN = 7,                    /* _RPAREN  */
  YYSYMBOL__STAR = 8,                      /* _STAR  */
  YYSYMBOL__PLUS = 9,                      /* _PLUS  */
  YYSYMBOL__COMMA = 10,                    /* _COMMA  */
  YYSYMBOL__MINUS = 11,                    /* _MINUS  */
  YYSYMBOL__SLASH = 12,                    /* _SLASH  */
  YYSYMBOL__SEMI = 13,                     /* _SEMI  */
  YYSYMBOL__LT = 14,                       /* _LT  */
  YYSYMBOL__LDARROW = 15,                  /* _LDARROW  */
  YYSYMBOL__EQ = 16,                       /* _EQ  */
  YYSYMBOL__DEQ = 17,                      /* _DEQ  */
  YYSYMBOL__GT = 18,                       /* _GT  */
  YYSYMBOL__GTEQ = 19,                     /* _GTEQ  */
  YYSYMBOL__KW_do = 20,                    /* _KW_do  */
  YYSYMBOL__KW_else = 21,                  /* _KW_else  */
  YYSYMBOL__KW_end = 22,                   /* _KW_end  */
  YYSYMBOL__KW_false = 23,                 /* _KW_false  */
  YYSYMBOL__KW_fi = 24,                    /* _KW_fi  */
  YYSYMBOL__KW_fn = 25,                    /* _KW_fn  */
  YYSYMBOL__KW_if = 26,                    /* _KW_if  */
  YYSYMBOL__KW_print = 27,                 /* _KW_print  */
  YYSYMBOL__KW_return = 28,                /* _KW_return  */
  YYSYMBOL__KW_then = 29,                  /* _KW_then  */
  YYSYMBOL__KW_true = 30,                  /* _KW_true  */
  YYSYMBOL__KW_while = 31,                 /* _KW_while  */
  YYSYMBOL__DBAR = 32,                     /* _DBAR  */
  YYSYMBOL__INTEGER_ = 33,                 /* _INTEGER_  */
  YYSYMBOL__IDENT_ = 34,                   /* _IDENT_  */
  YYSYMBOL_YYACCEPT = 35,                  /* $accept  */
  YYSYMBOL_TopLevel = 36,                  /* TopLevel  */
  YYSYMBOL_Expr = 37,                      /* Expr  */
  YYSYMBOL_Binop = 38,                     /* Binop  */
  YYSYMBOL_Block = 39,                     /* Block  */
  YYSYMBOL_State = 40,                     /* State  */
  YYSYMBOL_FunDecl = 41,                   /* FunDecl  */
  YYSYMBOL_Args = 42,                      /* Args  */
  YYSYMBOL_ArgsRest = 43,                  /* ArgsRest  */
  YYSYMBOL_FArgs = 44,                     /* FArgs  */
  YYSYMBOL_FArgsRest = 45                  /* FArgsRest  */
};
typedef enum yysymbol_kind_t yysymbol_kind_t;


/* Second part of user prologue.  */
#line 68 "foo.y"

void yyerror(YYLTYPE *loc, yyscan_t scanner, YYSTYPE *result, const char *msg)
{
  fprintf(stderr, "error: %d,%d: %s at %s\n",
    loc->first_line, loc->first_column, msg, foo_get_text(scanner));
}

int yyparse(yyscan_t scanner, YYSTYPE *result);

extern int yylex(YYSTYPE *lvalp, YYLTYPE *llocp, yyscan_t scanner);

#line 197 "Parser.c"


#ifdef short
# undef short
#endif

/* On compilers that do not define __PTRDIFF_MAX__ etc., make sure
   <limits.h> and (if available) <stdint.h> are included
   so that the code can choose integer types of a good width.  */

#ifndef __PTRDIFF_MAX__
# include <limits.h> /* INFRINGES ON USER NAME SPACE */
# if defined __STDC_VERSION__ && 199901 <= __STDC_VERSION__
#  include <stdint.h> /* INFRINGES ON USER NAME SPACE */
#  define YY_STDINT_H
# endif
#endif

/* Narrow types that promote to a signed type and that can represent a
   signed or unsigned integer of at least N bits.  In tables they can
   save space and decrease cache pressure.  Promoting to a signed type
   helps avoid bugs in integer arithmetic.  */

#ifdef __INT_LEAST8_MAX__
typedef __INT_LEAST8_TYPE__ yytype_int8;
#elif defined YY_STDINT_H
typedef int_least8_t yytype_int8;
#else
typedef signed char yytype_int8;
#endif

#ifdef __INT_LEAST16_MAX__
typedef __INT_LEAST16_TYPE__ yytype_int16;
#elif defined YY_STDINT_H
typedef int_least16_t yytype_int16;
#else
typedef short yytype_int16;
#endif

/* Work around bug in HP-UX 11.23, which defines these macros
   incorrectly for preprocessor constants.  This workaround can likely
   be removed in 2023, as HPE has promised support for HP-UX 11.23
   (aka HP-UX 11i v2) only through the end of 2022; see Table 2 of
   <https://h20195.www2.hpe.com/V2/getpdf.aspx/4AA4-7673ENW.pdf>.  */
#ifdef __hpux
# undef UINT_LEAST8_MAX
# undef UINT_LEAST16_MAX
# define UINT_LEAST8_MAX 255
# define UINT_LEAST16_MAX 65535
#endif

#if defined __UINT_LEAST8_MAX__ && __UINT_LEAST8_MAX__ <= __INT_MAX__
typedef __UINT_LEAST8_TYPE__ yytype_uint8;
#elif (!defined __UINT_LEAST8_MAX__ && defined YY_STDINT_H \
       && UINT_LEAST8_MAX <= INT_MAX)
typedef uint_least8_t yytype_uint8;
#elif !defined __UINT_LEAST8_MAX__ && UCHAR_MAX <= INT_MAX
typedef unsigned char yytype_uint8;
#else
typedef short yytype_uint8;
#endif

#if defined __UINT_LEAST16_MAX__ && __UINT_LEAST16_MAX__ <= __INT_MAX__
typedef __UINT_LEAST16_TYPE__ yytype_uint16;
#elif (!defined __UINT_LEAST16_MAX__ && defined YY_STDINT_H \
       && UINT_LEAST16_MAX <= INT_MAX)
typedef uint_least16_t yytype_uint16;
#elif !defined __UINT_LEAST16_MAX__ && USHRT_MAX <= INT_MAX
typedef unsigned short yytype_uint16;
#else
typedef int yytype_uint16;
#endif

#ifndef YYPTRDIFF_T
# if defined __PTRDIFF_TYPE__ && defined __PTRDIFF_MAX__
#  define YYPTRDIFF_T __PTRDIFF_TYPE__
#  define YYPTRDIFF_MAXIMUM __PTRDIFF_MAX__
# elif defined PTRDIFF_MAX
#  ifndef ptrdiff_t
#   include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  endif
#  define YYPTRDIFF_T ptrdiff_t
#  define YYPTRDIFF_MAXIMUM PTRDIFF_MAX
# else
#  define YYPTRDIFF_T long
#  define YYPTRDIFF_MAXIMUM LONG_MAX
# endif
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif defined __STDC_VERSION__ && 199901 <= __STDC_VERSION__
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned
# endif
#endif

#define YYSIZE_MAXIMUM                                  \
  YY_CAST (YYPTRDIFF_T,                                 \
           (YYPTRDIFF_MAXIMUM < YY_CAST (YYSIZE_T, -1)  \
            ? YYPTRDIFF_MAXIMUM                         \
            : YY_CAST (YYSIZE_T, -1)))

#define YYSIZEOF(X) YY_CAST (YYPTRDIFF_T, sizeof (X))


/* Stored state numbers (used for stacks). */
typedef yytype_int8 yy_state_t;

/* State numbers in computations.  */
typedef int yy_state_fast_t;

#ifndef YY_
# if defined YYENABLE_NLS && YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(Msgid) dgettext ("bison-runtime", Msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(Msgid) Msgid
# endif
#endif


#ifndef YY_ATTRIBUTE_PURE
# if defined __GNUC__ && 2 < __GNUC__ + (96 <= __GNUC_MINOR__)
#  define YY_ATTRIBUTE_PURE __attribute__ ((__pure__))
# else
#  define YY_ATTRIBUTE_PURE
# endif
#endif

#ifndef YY_ATTRIBUTE_UNUSED
# if defined __GNUC__ && 2 < __GNUC__ + (7 <= __GNUC_MINOR__)
#  define YY_ATTRIBUTE_UNUSED __attribute__ ((__unused__))
# else
#  define YY_ATTRIBUTE_UNUSED
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YY_USE(E) ((void) (E))
#else
# define YY_USE(E) /* empty */
#endif

/* Suppress an incorrect diagnostic about yylval being uninitialized.  */
#if defined __GNUC__ && ! defined __ICC && 406 <= __GNUC__ * 100 + __GNUC_MINOR__
# if __GNUC__ * 100 + __GNUC_MINOR__ < 407
#  define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN                           \
    _Pragma ("GCC diagnostic push")                                     \
    _Pragma ("GCC diagnostic ignored \"-Wuninitialized\"")
# else
#  define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN                           \
    _Pragma ("GCC diagnostic push")                                     \
    _Pragma ("GCC diagnostic ignored \"-Wuninitialized\"")              \
    _Pragma ("GCC diagnostic ignored \"-Wmaybe-uninitialized\"")
# endif
# define YY_IGNORE_MAYBE_UNINITIALIZED_END      \
    _Pragma ("GCC diagnostic pop")
#else
# define YY_INITIAL_VALUE(Value) Value
#endif
#ifndef YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_END
#endif
#ifndef YY_INITIAL_VALUE
# define YY_INITIAL_VALUE(Value) /* Nothing. */
#endif

#if defined __cplusplus && defined __GNUC__ && ! defined __ICC && 6 <= __GNUC__
# define YY_IGNORE_USELESS_CAST_BEGIN                          \
    _Pragma ("GCC diagnostic push")                            \
    _Pragma ("GCC diagnostic ignored \"-Wuseless-cast\"")
# define YY_IGNORE_USELESS_CAST_END            \
    _Pragma ("GCC diagnostic pop")
#endif
#ifndef YY_IGNORE_USELESS_CAST_BEGIN
# define YY_IGNORE_USELESS_CAST_BEGIN
# define YY_IGNORE_USELESS_CAST_END
#endif


#define YY_ASSERT(E) ((void) (0 && (E)))

#if !defined yyoverflow

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_USE_ALLOCA
#  if YYSTACK_USE_ALLOCA
#   ifdef __GNUC__
#    define YYSTACK_ALLOC __builtin_alloca
#   elif defined __BUILTIN_VA_ARG_INCR
#    include <alloca.h> /* INFRINGES ON USER NAME SPACE */
#   elif defined _AIX
#    define YYSTACK_ALLOC __alloca
#   elif defined _MSC_VER
#    include <malloc.h> /* INFRINGES ON USER NAME SPACE */
#    define alloca _alloca
#   else
#    define YYSTACK_ALLOC alloca
#    if ! defined _ALLOCA_H && ! defined EXIT_SUCCESS
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
      /* Use EXIT_SUCCESS as a witness for stdlib.h.  */
#     ifndef EXIT_SUCCESS
#      define EXIT_SUCCESS 0
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's 'empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (0)
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined EXIT_SUCCESS \
       && ! ((defined YYMALLOC || defined malloc) \
             && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef EXIT_SUCCESS
#    define EXIT_SUCCESS 0
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined EXIT_SUCCESS
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined EXIT_SUCCESS
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* !defined yyoverflow */

#if (! defined yyoverflow \
     && (! defined __cplusplus \
         || (defined YYLTYPE_IS_TRIVIAL && YYLTYPE_IS_TRIVIAL \
             && defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yy_state_t yyss_alloc;
  YYSTYPE yyvs_alloc;
  YYLTYPE yyls_alloc;
};

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (YYSIZEOF (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (YYSIZEOF (yy_state_t) + YYSIZEOF (YYSTYPE) \
             + YYSIZEOF (YYLTYPE)) \
      + 2 * YYSTACK_GAP_MAXIMUM)

# define YYCOPY_NEEDED 1

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack_alloc, Stack)                           \
    do                                                                  \
      {                                                                 \
        YYPTRDIFF_T yynewbytes;                                         \
        YYCOPY (&yyptr->Stack_alloc, Stack, yysize);                    \
        Stack = &yyptr->Stack_alloc;                                    \
        yynewbytes = yystacksize * YYSIZEOF (*Stack) + YYSTACK_GAP_MAXIMUM; \
        yyptr += yynewbytes / YYSIZEOF (*yyptr);                        \
      }                                                                 \
    while (0)

#endif

#if defined YYCOPY_NEEDED && YYCOPY_NEEDED
/* Copy COUNT objects from SRC to DST.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(Dst, Src, Count) \
      __builtin_memcpy (Dst, Src, YY_CAST (YYSIZE_T, (Count)) * sizeof (*(Src)))
#  else
#   define YYCOPY(Dst, Src, Count)              \
      do                                        \
        {                                       \
          YYPTRDIFF_T yyi;                      \
          for (yyi = 0; yyi < (Count); yyi++)   \
            (Dst)[yyi] = (Src)[yyi];            \
        }                                       \
      while (0)
#  endif
# endif
#endif /* !YYCOPY_NEEDED */

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  5
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   204

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  35
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  11
/* YYNRULES -- Number of rules.  */
#define YYNRULES  40
/* YYNSTATES -- Number of states.  */
#define YYNSTATES  73

/* YYMAXUTOK -- Last valid token kind.  */
#define YYMAXUTOK   289


/* YYTRANSLATE(TOKEN-NUM) -- Symbol number corresponding to TOKEN-NUM
   as returned by yylex, with out-of-bounds checking.  */
#define YYTRANSLATE(YYX)                                \
  (0 <= (YYX) && (YYX) <= YYMAXUTOK                     \
   ? YY_CAST (yysymbol_kind_t, yytranslate[YYX])        \
   : YYSYMBOL_YYUNDEF)

/* YYTRANSLATE[TOKEN-NUM] -- Symbol number corresponding to TOKEN-NUM
   as returned by yylex.  */
static const yytype_int8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34
};

#if YYDEBUG
/* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
static const yytype_uint8 yyrline[] =
{
       0,   128,   128,   129,   131,   132,   133,   134,   135,   136,
     137,   139,   140,   141,   142,   143,   144,   145,   146,   147,
     148,   149,   150,   152,   153,   155,   156,   157,   158,   159,
     160,   161,   163,   165,   166,   168,   169,   171,   172,   174,
     175
};
#endif

/** Accessing symbol of state STATE.  */
#define YY_ACCESSING_SYMBOL(State) YY_CAST (yysymbol_kind_t, yystos[State])

#if YYDEBUG || 0
/* The user-facing name of the symbol whose (internal) number is
   YYSYMBOL.  No bounds checking.  */
static const char *yysymbol_name (yysymbol_kind_t yysymbol) YY_ATTRIBUTE_UNUSED;

/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "\"end of file\"", "error", "\"invalid token\"", "_ERROR_", "_BANGEQ",
  "_DAMP", "_LPAREN", "_RPAREN", "_STAR", "_PLUS", "_COMMA", "_MINUS",
  "_SLASH", "_SEMI", "_LT", "_LDARROW", "_EQ", "_DEQ", "_GT", "_GTEQ",
  "_KW_do", "_KW_else", "_KW_end", "_KW_false", "_KW_fi", "_KW_fn",
  "_KW_if", "_KW_print", "_KW_return", "_KW_then", "_KW_true", "_KW_while",
  "_DBAR", "_INTEGER_", "_IDENT_", "$accept", "TopLevel", "Expr", "Binop",
  "Block", "State", "FunDecl", "Args", "ArgsRest", "FArgs", "FArgsRest", YY_NULLPTR
};

static const char *
yysymbol_name (yysymbol_kind_t yysymbol)
{
  return yytname[yysymbol];
}
#endif

#define YYPACT_NINF (-25)

#define yypact_value_is_default(Yyn) \
  ((Yyn) == YYPACT_NINF)

#define YYTABLE_NINF (-1)

#define yytable_value_is_error(Yyn) \
  0

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
static const yytype_int16 yypact[] =
{
     -16,   -22,    10,   -16,     7,   -25,   -25,   -20,     5,     9,
     -11,   -25,    -1,     5,   170,   -25,   170,   170,   170,   -25,
     170,   -25,     1,    32,    -4,    -1,   -25,    15,    48,    64,
      86,   102,   118,   170,   170,   -25,   -25,   -25,   -25,   -25,
     -25,   -25,   -25,   -25,   -25,   -25,   -25,   -25,   170,   -25,
     -25,   -25,    -1,   -25,   -25,    -1,   137,    17,   153,   169,
     -13,    13,   170,   -25,   -25,   -25,    -1,   -25,   -25,   137,
      14,   -25,   -25
};

/* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
   Performed when YYTABLE does not specify something else to do.  Zero
   means the default is an error.  */
static const yytype_int8 yydefact[] =
{
       3,     0,     0,     3,     0,     1,     2,    34,    36,     0,
       0,    33,    24,    36,     0,     6,     0,     0,     0,     5,
       0,     7,     4,     0,     0,    24,    35,     4,     0,     0,
       0,     0,     0,    38,     0,    17,    21,    13,    11,    12,
      14,    30,    19,    15,    16,    20,    18,    22,     0,    32,
      23,    10,    24,    26,    31,    24,    40,     0,     0,     8,
       0,     0,     0,    37,     9,    25,    24,    27,    29,    40,
       0,    39,    28
};

/* YYPGOTO[NTERM-NUM].  */
static const yytype_int8 yypgoto[] =
{
     -25,    36,   -14,   -25,   -24,   -25,   -25,   -25,    41,   -25,
      -8
};

/* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int8 yydefgoto[] =
{
       0,     2,    23,    48,    24,    25,     3,     9,    11,    57,
      63
};

/* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule whose
   number is the opposite.  If YYTABLE_NINF, syntax error.  */
static const yytype_int8 yytable[] =
{
      28,    50,    29,    30,    31,    14,    32,    33,    66,     1,
       5,    67,     4,     7,     8,    10,    12,    34,    49,    56,
      58,    33,    15,    13,    64,    16,    17,    18,    60,    19,
      20,    61,    21,    22,    59,    68,    35,    36,    72,     6,
      37,    38,    70,    39,    40,    41,    42,    43,    69,    44,
      45,    46,    35,    36,    26,    51,    37,    38,     0,    39,
      40,    71,    42,    43,    47,    44,    45,    46,    35,    36,
       0,     0,    37,    38,     0,    39,    40,     0,    42,    43,
      47,    44,    45,    46,     0,     0,     0,     0,     0,     0,
      35,    36,     0,    52,    37,    38,    47,    39,    40,    53,
      42,    43,     0,    44,    45,    46,    35,    36,     0,     0,
      37,    38,     0,    39,    40,    54,    42,    43,    47,    44,
      45,    46,    35,    36,     0,     0,    37,    38,     0,    39,
      40,     0,    42,    43,    47,    44,    45,    46,    55,     0,
       0,    35,    36,     0,     0,    37,    38,    62,    39,    40,
      47,    42,    43,     0,    44,    45,    46,    35,    36,     0,
       0,    37,    38,     0,    39,    40,    65,    42,    43,    47,
      44,    45,    46,    35,    36,     0,    14,    37,    38,     0,
      39,    40,     0,    42,    43,    47,    44,    45,    46,     0,
       0,     0,     0,    15,     0,     0,     0,     0,     0,     0,
      19,    47,     0,    21,    27
};

static const yytype_int8 yycheck[] =
{
      14,    25,    16,    17,    18,     6,    20,     6,    21,    25,
       0,    24,    34,     6,    34,    10,     7,    16,    22,    33,
      34,     6,    23,    34,     7,    26,    27,    28,    52,    30,
      31,    55,    33,    34,    48,    22,     4,     5,    24,     3,
       8,     9,    66,    11,    12,    13,    14,    15,    62,    17,
      18,    19,     4,     5,    13,     7,     8,     9,    -1,    11,
      12,    69,    14,    15,    32,    17,    18,    19,     4,     5,
      -1,    -1,     8,     9,    -1,    11,    12,    -1,    14,    15,
      32,    17,    18,    19,    -1,    -1,    -1,    -1,    -1,    -1,
       4,     5,    -1,    29,     8,     9,    32,    11,    12,    13,
      14,    15,    -1,    17,    18,    19,     4,     5,    -1,    -1,
       8,     9,    -1,    11,    12,    13,    14,    15,    32,    17,
      18,    19,     4,     5,    -1,    -1,     8,     9,    -1,    11,
      12,    -1,    14,    15,    32,    17,    18,    19,    20,    -1,
      -1,     4,     5,    -1,    -1,     8,     9,    10,    11,    12,
      32,    14,    15,    -1,    17,    18,    19,     4,     5,    -1,
      -1,     8,     9,    -1,    11,    12,    13,    14,    15,    32,
      17,    18,    19,     4,     5,    -1,     6,     8,     9,    -1,
      11,    12,    -1,    14,    15,    32,    17,    18,    19,    -1,
      -1,    -1,    -1,    23,    -1,    -1,    -1,    -1,    -1,    -1,
      30,    32,    -1,    33,    34
};

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
static const yytype_int8 yystos[] =
{
       0,    25,    36,    41,    34,     0,    36,     6,    34,    42,
      10,    43,     7,    34,     6,    23,    26,    27,    28,    30,
      31,    33,    34,    37,    39,    40,    43,    34,    37,    37,
      37,    37,    37,     6,    16,     4,     5,     8,     9,    11,
      12,    13,    14,    15,    17,    18,    19,    32,    38,    22,
      39,     7,    29,    13,    13,    20,    37,    44,    37,    37,
      39,    39,    10,    45,     7,    13,    21,    24,    22,    37,
      39,    45,    24
};

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
static const yytype_int8 yyr1[] =
{
       0,    35,    36,    36,    37,    37,    37,    37,    37,    37,
      37,    38,    38,    38,    38,    38,    38,    38,    38,    38,
      38,    38,    38,    39,    39,    40,    40,    40,    40,    40,
      40,    40,    41,    42,    42,    43,    43,    44,    44,    45,
      45
};

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
static const yytype_int8 yyr2[] =
{
       0,     2,     2,     0,     1,     1,     1,     1,     3,     4,
       3,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     2,     0,     4,     3,     5,     7,     5,
       2,     3,     7,     2,     0,     3,     0,     2,     0,     3,
       0
};


enum { YYENOMEM = -2 };

#define yyerrok         (yyerrstatus = 0)
#define yyclearin       (yychar = YYEMPTY)

#define YYACCEPT        goto yyacceptlab
#define YYABORT         goto yyabortlab
#define YYERROR         goto yyerrorlab
#define YYNOMEM         goto yyexhaustedlab


#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)                                    \
  do                                                              \
    if (yychar == YYEMPTY)                                        \
      {                                                           \
        yychar = (Token);                                         \
        yylval = (Value);                                         \
        YYPOPSTACK (yylen);                                       \
        yystate = *yyssp;                                         \
        goto yybackup;                                            \
      }                                                           \
    else                                                          \
      {                                                           \
        yyerror (&yylloc, scanner, result, YY_("syntax error: cannot back up")); \
        YYERROR;                                                  \
      }                                                           \
  while (0)

/* Backward compatibility with an undocumented macro.
   Use YYerror or YYUNDEF. */
#define YYERRCODE YYUNDEF

/* YYLLOC_DEFAULT -- Set CURRENT to span from RHS[1] to RHS[N].
   If N is 0, then set CURRENT to the empty location which ends
   the previous symbol: RHS[0] (always defined).  */

#ifndef YYLLOC_DEFAULT
# define YYLLOC_DEFAULT(Current, Rhs, N)                                \
    do                                                                  \
      if (N)                                                            \
        {                                                               \
          (Current).first_line   = YYRHSLOC (Rhs, 1).first_line;        \
          (Current).first_column = YYRHSLOC (Rhs, 1).first_column;      \
          (Current).last_line    = YYRHSLOC (Rhs, N).last_line;         \
          (Current).last_column  = YYRHSLOC (Rhs, N).last_column;       \
        }                                                               \
      else                                                              \
        {                                                               \
          (Current).first_line   = (Current).last_line   =              \
            YYRHSLOC (Rhs, 0).last_line;                                \
          (Current).first_column = (Current).last_column =              \
            YYRHSLOC (Rhs, 0).last_column;                              \
        }                                                               \
    while (0)
#endif

#define YYRHSLOC(Rhs, K) ((Rhs)[K])


/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)                        \
do {                                            \
  if (yydebug)                                  \
    YYFPRINTF Args;                             \
} while (0)


/* YYLOCATION_PRINT -- Print the location on the stream.
   This macro was not mandated originally: define only if we know
   we won't break user code: when these are the locations we know.  */

# ifndef YYLOCATION_PRINT

#  if defined YY_LOCATION_PRINT

   /* Temporary convenience wrapper in case some people defined the
      undocumented and private YY_LOCATION_PRINT macros.  */
#   define YYLOCATION_PRINT(File, Loc)  YY_LOCATION_PRINT(File, *(Loc))

#  elif defined YYLTYPE_IS_TRIVIAL && YYLTYPE_IS_TRIVIAL

/* Print *YYLOCP on YYO.  Private, do not rely on its existence. */

YY_ATTRIBUTE_UNUSED
static int
yy_location_print_ (FILE *yyo, YYLTYPE const * const yylocp)
{
  int res = 0;
  int end_col = 0 != yylocp->last_column ? yylocp->last_column - 1 : 0;
  if (0 <= yylocp->first_line)
    {
      res += YYFPRINTF (yyo, "%d", yylocp->first_line);
      if (0 <= yylocp->first_column)
        res += YYFPRINTF (yyo, ".%d", yylocp->first_column);
    }
  if (0 <= yylocp->last_line)
    {
      if (yylocp->first_line < yylocp->last_line)
        {
          res += YYFPRINTF (yyo, "-%d", yylocp->last_line);
          if (0 <= end_col)
            res += YYFPRINTF (yyo, ".%d", end_col);
        }
      else if (0 <= end_col && yylocp->first_column < end_col)
        res += YYFPRINTF (yyo, "-%d", end_col);
    }
  return res;
}

#   define YYLOCATION_PRINT  yy_location_print_

    /* Temporary convenience wrapper in case some people defined the
       undocumented and private YY_LOCATION_PRINT macros.  */
#   define YY_LOCATION_PRINT(File, Loc)  YYLOCATION_PRINT(File, &(Loc))

#  else

#   define YYLOCATION_PRINT(File, Loc) ((void) 0)
    /* Temporary convenience wrapper in case some people defined the
       undocumented and private YY_LOCATION_PRINT macros.  */
#   define YY_LOCATION_PRINT  YYLOCATION_PRINT

#  endif
# endif /* !defined YYLOCATION_PRINT */


# define YY_SYMBOL_PRINT(Title, Kind, Value, Location)                    \
do {                                                                      \
  if (yydebug)                                                            \
    {                                                                     \
      YYFPRINTF (stderr, "%s ", Title);                                   \
      yy_symbol_print (stderr,                                            \
                  Kind, Value, Location, scanner, result); \
      YYFPRINTF (stderr, "\n");                                           \
    }                                                                     \
} while (0)


/*-----------------------------------.
| Print this symbol's value on YYO.  |
`-----------------------------------*/

static void
yy_symbol_value_print (FILE *yyo,
                       yysymbol_kind_t yykind, YYSTYPE const * const yyvaluep, YYLTYPE const * const yylocationp, yyscan_t scanner, YYSTYPE *result)
{
  FILE *yyoutput = yyo;
  YY_USE (yyoutput);
  YY_USE (yylocationp);
  YY_USE (scanner);
  YY_USE (result);
  if (!yyvaluep)
    return;
  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  YY_USE (yykind);
  YY_IGNORE_MAYBE_UNINITIALIZED_END
}


/*---------------------------.
| Print this symbol on YYO.  |
`---------------------------*/

static void
yy_symbol_print (FILE *yyo,
                 yysymbol_kind_t yykind, YYSTYPE const * const yyvaluep, YYLTYPE const * const yylocationp, yyscan_t scanner, YYSTYPE *result)
{
  YYFPRINTF (yyo, "%s %s (",
             yykind < YYNTOKENS ? "token" : "nterm", yysymbol_name (yykind));

  YYLOCATION_PRINT (yyo, yylocationp);
  YYFPRINTF (yyo, ": ");
  yy_symbol_value_print (yyo, yykind, yyvaluep, yylocationp, scanner, result);
  YYFPRINTF (yyo, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

static void
yy_stack_print (yy_state_t *yybottom, yy_state_t *yytop)
{
  YYFPRINTF (stderr, "Stack now");
  for (; yybottom <= yytop; yybottom++)
    {
      int yybot = *yybottom;
      YYFPRINTF (stderr, " %d", yybot);
    }
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)                            \
do {                                                            \
  if (yydebug)                                                  \
    yy_stack_print ((Bottom), (Top));                           \
} while (0)


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

static void
yy_reduce_print (yy_state_t *yyssp, YYSTYPE *yyvsp, YYLTYPE *yylsp,
                 int yyrule, yyscan_t scanner, YYSTYPE *result)
{
  int yylno = yyrline[yyrule];
  int yynrhs = yyr2[yyrule];
  int yyi;
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %d):\n",
             yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr,
                       YY_ACCESSING_SYMBOL (+yyssp[yyi + 1 - yynrhs]),
                       &yyvsp[(yyi + 1) - (yynrhs)],
                       &(yylsp[(yyi + 1) - (yynrhs)]), scanner, result);
      YYFPRINTF (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)          \
do {                                    \
  if (yydebug)                          \
    yy_reduce_print (yyssp, yyvsp, yylsp, Rule, scanner, result); \
} while (0)

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args) ((void) 0)
# define YY_SYMBOL_PRINT(Title, Kind, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif






/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

static void
yydestruct (const char *yymsg,
            yysymbol_kind_t yykind, YYSTYPE *yyvaluep, YYLTYPE *yylocationp, yyscan_t scanner, YYSTYPE *result)
{
  YY_USE (yyvaluep);
  YY_USE (yylocationp);
  YY_USE (scanner);
  YY_USE (result);
  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yykind, yyvaluep, yylocationp);

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  YY_USE (yykind);
  YY_IGNORE_MAYBE_UNINITIALIZED_END
}






/*----------.
| yyparse.  |
`----------*/

int
yyparse (yyscan_t scanner, YYSTYPE *result)
{
/* Lookahead token kind.  */
int yychar;


/* The semantic value of the lookahead symbol.  */
/* Default value used for initialization, for pacifying older GCCs
   or non-GCC compilers.  */
YY_INITIAL_VALUE (static YYSTYPE yyval_default;)
YYSTYPE yylval YY_INITIAL_VALUE (= yyval_default);

/* Location data for the lookahead symbol.  */
static YYLTYPE yyloc_default
# if defined YYLTYPE_IS_TRIVIAL && YYLTYPE_IS_TRIVIAL
  = { 1, 1, 1, 1 }
# endif
;
YYLTYPE yylloc = yyloc_default;

    /* Number of syntax errors so far.  */
    int yynerrs = 0;

    yy_state_fast_t yystate = 0;
    /* Number of tokens to shift before error messages enabled.  */
    int yyerrstatus = 0;

    /* Refer to the stacks through separate pointers, to allow yyoverflow
       to reallocate them elsewhere.  */

    /* Their size.  */
    YYPTRDIFF_T yystacksize = YYINITDEPTH;

    /* The state stack: array, bottom, top.  */
    yy_state_t yyssa[YYINITDEPTH];
    yy_state_t *yyss = yyssa;
    yy_state_t *yyssp = yyss;

    /* The semantic value stack: array, bottom, top.  */
    YYSTYPE yyvsa[YYINITDEPTH];
    YYSTYPE *yyvs = yyvsa;
    YYSTYPE *yyvsp = yyvs;

    /* The location stack: array, bottom, top.  */
    YYLTYPE yylsa[YYINITDEPTH];
    YYLTYPE *yyls = yylsa;
    YYLTYPE *yylsp = yyls;

  int yyn;
  /* The return value of yyparse.  */
  int yyresult;
  /* Lookahead symbol kind.  */
  yysymbol_kind_t yytoken = YYSYMBOL_YYEMPTY;
  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;
  YYLTYPE yyloc;

  /* The locations where the error started and ended.  */
  YYLTYPE yyerror_range[3];



#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N), yylsp -= (N))

  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yychar = YYEMPTY; /* Cause a token to be read.  */

  yylsp[0] = yylloc;
  goto yysetstate;


/*------------------------------------------------------------.
| yynewstate -- push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;


/*--------------------------------------------------------------------.
| yysetstate -- set current state (the top of the stack) to yystate.  |
`--------------------------------------------------------------------*/
yysetstate:
  YYDPRINTF ((stderr, "Entering state %d\n", yystate));
  YY_ASSERT (0 <= yystate && yystate < YYNSTATES);
  YY_IGNORE_USELESS_CAST_BEGIN
  *yyssp = YY_CAST (yy_state_t, yystate);
  YY_IGNORE_USELESS_CAST_END
  YY_STACK_PRINT (yyss, yyssp);

  if (yyss + yystacksize - 1 <= yyssp)
#if !defined yyoverflow && !defined YYSTACK_RELOCATE
    YYNOMEM;
#else
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYPTRDIFF_T yysize = yyssp - yyss + 1;

# if defined yyoverflow
      {
        /* Give user a chance to reallocate the stack.  Use copies of
           these so that the &'s don't force the real ones into
           memory.  */
        yy_state_t *yyss1 = yyss;
        YYSTYPE *yyvs1 = yyvs;
        YYLTYPE *yyls1 = yyls;

        /* Each stack pointer address is followed by the size of the
           data in use in that stack, in bytes.  This used to be a
           conditional around just the two extra args, but that might
           be undefined if yyoverflow is a macro.  */
        yyoverflow (YY_("memory exhausted"),
                    &yyss1, yysize * YYSIZEOF (*yyssp),
                    &yyvs1, yysize * YYSIZEOF (*yyvsp),
                    &yyls1, yysize * YYSIZEOF (*yylsp),
                    &yystacksize);
        yyss = yyss1;
        yyvs = yyvs1;
        yyls = yyls1;
      }
# else /* defined YYSTACK_RELOCATE */
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
        YYNOMEM;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
        yystacksize = YYMAXDEPTH;

      {
        yy_state_t *yyss1 = yyss;
        union yyalloc *yyptr =
          YY_CAST (union yyalloc *,
                   YYSTACK_ALLOC (YY_CAST (YYSIZE_T, YYSTACK_BYTES (yystacksize))));
        if (! yyptr)
          YYNOMEM;
        YYSTACK_RELOCATE (yyss_alloc, yyss);
        YYSTACK_RELOCATE (yyvs_alloc, yyvs);
        YYSTACK_RELOCATE (yyls_alloc, yyls);
#  undef YYSTACK_RELOCATE
        if (yyss1 != yyssa)
          YYSTACK_FREE (yyss1);
      }
# endif

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;
      yylsp = yyls + yysize - 1;

      YY_IGNORE_USELESS_CAST_BEGIN
      YYDPRINTF ((stderr, "Stack size increased to %ld\n",
                  YY_CAST (long, yystacksize)));
      YY_IGNORE_USELESS_CAST_END

      if (yyss + yystacksize - 1 <= yyssp)
        YYABORT;
    }
#endif /* !defined yyoverflow && !defined YYSTACK_RELOCATE */


  if (yystate == YYFINAL)
    YYACCEPT;

  goto yybackup;


/*-----------.
| yybackup.  |
`-----------*/
yybackup:
  /* Do appropriate processing given the current state.  Read a
     lookahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to lookahead token.  */
  yyn = yypact[yystate];
  if (yypact_value_is_default (yyn))
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either empty, or end-of-input, or a valid lookahead.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token\n"));
      yychar = yylex (&yylval, &yylloc, scanner);
    }

  if (yychar <= YYEOF)
    {
      yychar = YYEOF;
      yytoken = YYSYMBOL_YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else if (yychar == YYerror)
    {
      /* The scanner already issued an error message, process directly
         to error recovery.  But do not keep the error token as
         lookahead, it is too special and may lead us to an endless
         loop in error recovery. */
      yychar = YYUNDEF;
      yytoken = YYSYMBOL_YYerror;
      yyerror_range[1] = yylloc;
      goto yyerrlab1;
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yytable_value_is_error (yyn))
        goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the lookahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);
  yystate = yyn;
  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END
  *++yylsp = yylloc;

  /* Discard the shifted token.  */
  yychar = YYEMPTY;
  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     '$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];

  /* Default location. */
  YYLLOC_DEFAULT (yyloc, (yylsp - yylen), yylen);
  yyerror_range[1] = yyloc;
  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
  case 2: /* TopLevel: FunDecl TopLevel  */
#line 128 "foo.y"
                            { (yyval.toplevel_) = make_TLCons((yyvsp[-1].fundecl_), (yyvsp[0].toplevel_)); result->toplevel_ = (yyval.toplevel_); }
#line 1349 "Parser.c"
    break;

  case 3: /* TopLevel: %empty  */
#line 129 "foo.y"
                { (yyval.toplevel_) = make_TLNil(); result->toplevel_ = (yyval.toplevel_); }
#line 1355 "Parser.c"
    break;

  case 4: /* Expr: _IDENT_  */
#line 131 "foo.y"
               { (yyval.expr_) = make_EVar((yyvsp[0]._string)); result->expr_ = (yyval.expr_); }
#line 1361 "Parser.c"
    break;

  case 5: /* Expr: _KW_true  */
#line 132 "foo.y"
             { (yyval.expr_) = make_ETrue(); result->expr_ = (yyval.expr_); }
#line 1367 "Parser.c"
    break;

  case 6: /* Expr: _KW_false  */
#line 133 "foo.y"
              { (yyval.expr_) = make_EFalse(); result->expr_ = (yyval.expr_); }
#line 1373 "Parser.c"
    break;

  case 7: /* Expr: _INTEGER_  */
#line 134 "foo.y"
              { (yyval.expr_) = make_EInt((yyvsp[0]._int)); result->expr_ = (yyval.expr_); }
#line 1379 "Parser.c"
    break;

  case 8: /* Expr: Expr Binop Expr  */
#line 135 "foo.y"
                    { (yyval.expr_) = make_EBinOp((yyvsp[-2].expr_), (yyvsp[-1].binop_), (yyvsp[0].expr_)); result->expr_ = (yyval.expr_); }
#line 1385 "Parser.c"
    break;

  case 9: /* Expr: _IDENT_ _LPAREN FArgs _RPAREN  */
#line 136 "foo.y"
                                  { (yyval.expr_) = make_EFnCall((yyvsp[-3]._string), (yyvsp[-1].fargs_)); result->expr_ = (yyval.expr_); }
#line 1391 "Parser.c"
    break;

  case 10: /* Expr: _LPAREN Expr _RPAREN  */
#line 137 "foo.y"
                         { (yyval.expr_) = (yyvsp[-1].expr_); result->expr_ = (yyval.expr_); }
#line 1397 "Parser.c"
    break;

  case 11: /* Binop: _PLUS  */
#line 139 "foo.y"
              { (yyval.binop_) = make_Add(); result->binop_ = (yyval.binop_); }
#line 1403 "Parser.c"
    break;

  case 12: /* Binop: _MINUS  */
#line 140 "foo.y"
           { (yyval.binop_) = make_Sub(); result->binop_ = (yyval.binop_); }
#line 1409 "Parser.c"
    break;

  case 13: /* Binop: _STAR  */
#line 141 "foo.y"
          { (yyval.binop_) = make_Mul(); result->binop_ = (yyval.binop_); }
#line 1415 "Parser.c"
    break;

  case 14: /* Binop: _SLASH  */
#line 142 "foo.y"
           { (yyval.binop_) = make_Div(); result->binop_ = (yyval.binop_); }
#line 1421 "Parser.c"
    break;

  case 15: /* Binop: _LDARROW  */
#line 143 "foo.y"
             { (yyval.binop_) = make_Lte(); result->binop_ = (yyval.binop_); }
#line 1427 "Parser.c"
    break;

  case 16: /* Binop: _DEQ  */
#line 144 "foo.y"
         { (yyval.binop_) = make_Eq(); result->binop_ = (yyval.binop_); }
#line 1433 "Parser.c"
    break;

  case 17: /* Binop: _BANGEQ  */
#line 145 "foo.y"
            { (yyval.binop_) = make_Neq(); result->binop_ = (yyval.binop_); }
#line 1439 "Parser.c"
    break;

  case 18: /* Binop: _GTEQ  */
#line 146 "foo.y"
          { (yyval.binop_) = make_Gte(); result->binop_ = (yyval.binop_); }
#line 1445 "Parser.c"
    break;

  case 19: /* Binop: _LT  */
#line 147 "foo.y"
        { (yyval.binop_) = make_Lt(); result->binop_ = (yyval.binop_); }
#line 1451 "Parser.c"
    break;

  case 20: /* Binop: _GT  */
#line 148 "foo.y"
        { (yyval.binop_) = make_Gt(); result->binop_ = (yyval.binop_); }
#line 1457 "Parser.c"
    break;

  case 21: /* Binop: _DAMP  */
#line 149 "foo.y"
          { (yyval.binop_) = make_And(); result->binop_ = (yyval.binop_); }
#line 1463 "Parser.c"
    break;

  case 22: /* Binop: _DBAR  */
#line 150 "foo.y"
          { (yyval.binop_) = make_Or(); result->binop_ = (yyval.binop_); }
#line 1469 "Parser.c"
    break;

  case 23: /* Block: State Block  */
#line 152 "foo.y"
                    { (yyval.block_) = make_BCons((yyvsp[-1].state_), (yyvsp[0].block_)); result->block_ = (yyval.block_); }
#line 1475 "Parser.c"
    break;

  case 24: /* Block: %empty  */
#line 153 "foo.y"
                { (yyval.block_) = make_BNil(); result->block_ = (yyval.block_); }
#line 1481 "Parser.c"
    break;

  case 25: /* State: _IDENT_ _EQ Expr _SEMI  */
#line 155 "foo.y"
                               { (yyval.state_) = make_SAssn((yyvsp[-3]._string), (yyvsp[-1].expr_)); result->state_ = (yyval.state_); }
#line 1487 "Parser.c"
    break;

  case 26: /* State: _KW_print Expr _SEMI  */
#line 156 "foo.y"
                         { (yyval.state_) = make_SPrint((yyvsp[-1].expr_)); result->state_ = (yyval.state_); }
#line 1493 "Parser.c"
    break;

  case 27: /* State: _KW_if Expr _KW_then Block _KW_fi  */
#line 157 "foo.y"
                                      { (yyval.state_) = make_SIf((yyvsp[-3].expr_), (yyvsp[-1].block_)); result->state_ = (yyval.state_); }
#line 1499 "Parser.c"
    break;

  case 28: /* State: _KW_if Expr _KW_then Block _KW_else Block _KW_fi  */
#line 158 "foo.y"
                                                     { (yyval.state_) = make_SIfEl((yyvsp[-5].expr_), (yyvsp[-3].block_), (yyvsp[-1].block_)); result->state_ = (yyval.state_); }
#line 1505 "Parser.c"
    break;

  case 29: /* State: _KW_while Expr _KW_do Block _KW_end  */
#line 159 "foo.y"
                                        { (yyval.state_) = make_SWhile((yyvsp[-3].expr_), (yyvsp[-1].block_)); result->state_ = (yyval.state_); }
#line 1511 "Parser.c"
    break;

  case 30: /* State: Expr _SEMI  */
#line 160 "foo.y"
               { (yyval.state_) = make_SExpr((yyvsp[-1].expr_)); result->state_ = (yyval.state_); }
#line 1517 "Parser.c"
    break;

  case 31: /* State: _KW_return Expr _SEMI  */
#line 161 "foo.y"
                          { (yyval.state_) = make_SRet((yyvsp[-1].expr_)); result->state_ = (yyval.state_); }
#line 1523 "Parser.c"
    break;

  case 32: /* FunDecl: _KW_fn _IDENT_ _LPAREN Args _RPAREN Block _KW_end  */
#line 163 "foo.y"
                                                            { (yyval.fundecl_) = make_FDecl((yyvsp[-5]._string), (yyvsp[-3].args_), (yyvsp[-1].block_)); result->fundecl_ = (yyval.fundecl_); }
#line 1529 "Parser.c"
    break;

  case 33: /* Args: _IDENT_ ArgsRest  */
#line 165 "foo.y"
                        { (yyval.args_) = make_AArgs((yyvsp[-1]._string), (yyvsp[0].argsrest_)); result->args_ = (yyval.args_); }
#line 1535 "Parser.c"
    break;

  case 34: /* Args: %empty  */
#line 166 "foo.y"
                { (yyval.args_) = make_ANone(); result->args_ = (yyval.args_); }
#line 1541 "Parser.c"
    break;

  case 35: /* ArgsRest: _COMMA _IDENT_ ArgsRest  */
#line 168 "foo.y"
                                   { (yyval.argsrest_) = make_ACons((yyvsp[-1]._string), (yyvsp[0].argsrest_)); result->argsrest_ = (yyval.argsrest_); }
#line 1547 "Parser.c"
    break;

  case 36: /* ArgsRest: %empty  */
#line 169 "foo.y"
                { (yyval.argsrest_) = make_ANil(); result->argsrest_ = (yyval.argsrest_); }
#line 1553 "Parser.c"
    break;

  case 37: /* FArgs: Expr FArgsRest  */
#line 171 "foo.y"
                       { (yyval.fargs_) = make_FAArgs((yyvsp[-1].expr_), (yyvsp[0].fargsrest_)); result->fargs_ = (yyval.fargs_); }
#line 1559 "Parser.c"
    break;

  case 38: /* FArgs: %empty  */
#line 172 "foo.y"
                { (yyval.fargs_) = make_FANone(); result->fargs_ = (yyval.fargs_); }
#line 1565 "Parser.c"
    break;

  case 39: /* FArgsRest: _COMMA Expr FArgsRest  */
#line 174 "foo.y"
                                  { (yyval.fargsrest_) = make_FACons((yyvsp[-1].expr_), (yyvsp[0].fargsrest_)); result->fargsrest_ = (yyval.fargsrest_); }
#line 1571 "Parser.c"
    break;

  case 40: /* FArgsRest: %empty  */
#line 175 "foo.y"
                { (yyval.fargsrest_) = make_FANil(); result->fargsrest_ = (yyval.fargsrest_); }
#line 1577 "Parser.c"
    break;


#line 1581 "Parser.c"

      default: break;
    }
  /* User semantic actions sometimes alter yychar, and that requires
     that yytoken be updated with the new translation.  We take the
     approach of translating immediately before every use of yytoken.
     One alternative is translating here after every semantic action,
     but that translation would be missed if the semantic action invokes
     YYABORT, YYACCEPT, or YYERROR immediately after altering yychar or
     if it invokes YYBACKUP.  In the case of YYABORT or YYACCEPT, an
     incorrect destructor might then be invoked immediately.  In the
     case of YYERROR or YYBACKUP, subsequent parser actions might lead
     to an incorrect destructor call or verbose syntax error message
     before the lookahead is translated.  */
  YY_SYMBOL_PRINT ("-> $$ =", YY_CAST (yysymbol_kind_t, yyr1[yyn]), &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;

  *++yyvsp = yyval;
  *++yylsp = yyloc;

  /* Now 'shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */
  {
    const int yylhs = yyr1[yyn] - YYNTOKENS;
    const int yyi = yypgoto[yylhs] + *yyssp;
    yystate = (0 <= yyi && yyi <= YYLAST && yycheck[yyi] == *yyssp
               ? yytable[yyi]
               : yydefgoto[yylhs]);
  }

  goto yynewstate;


/*--------------------------------------.
| yyerrlab -- here on detecting error.  |
`--------------------------------------*/
yyerrlab:
  /* Make sure we have latest lookahead translation.  See comments at
     user semantic actions for why this is necessary.  */
  yytoken = yychar == YYEMPTY ? YYSYMBOL_YYEMPTY : YYTRANSLATE (yychar);
  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
      yyerror (&yylloc, scanner, result, YY_("syntax error"));
    }

  yyerror_range[1] = yylloc;
  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse lookahead token after an
         error, discard it.  */

      if (yychar <= YYEOF)
        {
          /* Return failure if at end of input.  */
          if (yychar == YYEOF)
            YYABORT;
        }
      else
        {
          yydestruct ("Error: discarding",
                      yytoken, &yylval, &yylloc, scanner, result);
          yychar = YYEMPTY;
        }
    }

  /* Else will try to reuse lookahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:
  /* Pacify compilers when the user code never invokes YYERROR and the
     label yyerrorlab therefore never appears in user code.  */
  if (0)
    YYERROR;
  ++yynerrs;

  /* Do not reclaim the symbols of the rule whose action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;      /* Each real token shifted decrements this.  */

  /* Pop stack until we find a state that shifts the error token.  */
  for (;;)
    {
      yyn = yypact[yystate];
      if (!yypact_value_is_default (yyn))
        {
          yyn += YYSYMBOL_YYerror;
          if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYSYMBOL_YYerror)
            {
              yyn = yytable[yyn];
              if (0 < yyn)
                break;
            }
        }

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
        YYABORT;

      yyerror_range[1] = *yylsp;
      yydestruct ("Error: popping",
                  YY_ACCESSING_SYMBOL (yystate), yyvsp, yylsp, scanner, result);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END

  yyerror_range[2] = yylloc;
  ++yylsp;
  YYLLOC_DEFAULT (*yylsp, yyerror_range, 2);

  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", YY_ACCESSING_SYMBOL (yyn), yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturnlab;


/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturnlab;


/*-----------------------------------------------------------.
| yyexhaustedlab -- YYNOMEM (memory exhaustion) comes here.  |
`-----------------------------------------------------------*/
yyexhaustedlab:
  yyerror (&yylloc, scanner, result, YY_("memory exhausted"));
  yyresult = 2;
  goto yyreturnlab;


/*----------------------------------------------------------.
| yyreturnlab -- parsing is finished, clean up and return.  |
`----------------------------------------------------------*/
yyreturnlab:
  if (yychar != YYEMPTY)
    {
      /* Make sure we have latest lookahead translation.  See comments at
         user semantic actions for why this is necessary.  */
      yytoken = YYTRANSLATE (yychar);
      yydestruct ("Cleanup: discarding lookahead",
                  yytoken, &yylval, &yylloc, scanner, result);
    }
  /* Do not reclaim the symbols of the rule whose action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
                  YY_ACCESSING_SYMBOL (+*yyssp), yyvsp, yylsp, scanner, result);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif

  return yyresult;
}

#line 178 "foo.y"



/* Entrypoint: parse TopLevel from file. */
TopLevel pTopLevel(FILE *inp)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(inp);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  int error = yyparse(scanner, &result);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.toplevel_;
  }
}

/* Entrypoint: parse TopLevel from string. */
TopLevel psTopLevel(const char *str)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(0);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  YY_BUFFER_STATE buf = foo__scan_string(str, scanner);
  int error = yyparse(scanner, &result);
  foo__delete_buffer(buf, scanner);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.toplevel_;
  }
}

/* Entrypoint: parse Expr from file. */
Expr pExpr(FILE *inp)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(inp);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  int error = yyparse(scanner, &result);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.expr_;
  }
}

/* Entrypoint: parse Expr from string. */
Expr psExpr(const char *str)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(0);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  YY_BUFFER_STATE buf = foo__scan_string(str, scanner);
  int error = yyparse(scanner, &result);
  foo__delete_buffer(buf, scanner);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.expr_;
  }
}

/* Entrypoint: parse Binop from file. */
Binop pBinop(FILE *inp)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(inp);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  int error = yyparse(scanner, &result);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.binop_;
  }
}

/* Entrypoint: parse Binop from string. */
Binop psBinop(const char *str)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(0);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  YY_BUFFER_STATE buf = foo__scan_string(str, scanner);
  int error = yyparse(scanner, &result);
  foo__delete_buffer(buf, scanner);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.binop_;
  }
}

/* Entrypoint: parse Block from file. */
Block pBlock(FILE *inp)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(inp);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  int error = yyparse(scanner, &result);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.block_;
  }
}

/* Entrypoint: parse Block from string. */
Block psBlock(const char *str)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(0);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  YY_BUFFER_STATE buf = foo__scan_string(str, scanner);
  int error = yyparse(scanner, &result);
  foo__delete_buffer(buf, scanner);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.block_;
  }
}

/* Entrypoint: parse State from file. */
State pState(FILE *inp)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(inp);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  int error = yyparse(scanner, &result);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.state_;
  }
}

/* Entrypoint: parse State from string. */
State psState(const char *str)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(0);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  YY_BUFFER_STATE buf = foo__scan_string(str, scanner);
  int error = yyparse(scanner, &result);
  foo__delete_buffer(buf, scanner);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.state_;
  }
}

/* Entrypoint: parse FunDecl from file. */
FunDecl pFunDecl(FILE *inp)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(inp);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  int error = yyparse(scanner, &result);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.fundecl_;
  }
}

/* Entrypoint: parse FunDecl from string. */
FunDecl psFunDecl(const char *str)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(0);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  YY_BUFFER_STATE buf = foo__scan_string(str, scanner);
  int error = yyparse(scanner, &result);
  foo__delete_buffer(buf, scanner);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.fundecl_;
  }
}

/* Entrypoint: parse Args from file. */
Args pArgs(FILE *inp)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(inp);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  int error = yyparse(scanner, &result);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.args_;
  }
}

/* Entrypoint: parse Args from string. */
Args psArgs(const char *str)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(0);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  YY_BUFFER_STATE buf = foo__scan_string(str, scanner);
  int error = yyparse(scanner, &result);
  foo__delete_buffer(buf, scanner);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.args_;
  }
}

/* Entrypoint: parse ArgsRest from file. */
ArgsRest pArgsRest(FILE *inp)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(inp);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  int error = yyparse(scanner, &result);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.argsrest_;
  }
}

/* Entrypoint: parse ArgsRest from string. */
ArgsRest psArgsRest(const char *str)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(0);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  YY_BUFFER_STATE buf = foo__scan_string(str, scanner);
  int error = yyparse(scanner, &result);
  foo__delete_buffer(buf, scanner);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.argsrest_;
  }
}

/* Entrypoint: parse FArgs from file. */
FArgs pFArgs(FILE *inp)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(inp);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  int error = yyparse(scanner, &result);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.fargs_;
  }
}

/* Entrypoint: parse FArgs from string. */
FArgs psFArgs(const char *str)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(0);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  YY_BUFFER_STATE buf = foo__scan_string(str, scanner);
  int error = yyparse(scanner, &result);
  foo__delete_buffer(buf, scanner);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.fargs_;
  }
}

/* Entrypoint: parse FArgsRest from file. */
FArgsRest pFArgsRest(FILE *inp)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(inp);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  int error = yyparse(scanner, &result);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.fargsrest_;
  }
}

/* Entrypoint: parse FArgsRest from string. */
FArgsRest psFArgsRest(const char *str)
{
  YYSTYPE result;
  yyscan_t scanner = foo__initialize_lexer(0);
  if (!scanner) {
    fprintf(stderr, "Failed to initialize lexer.\n");
    return 0;
  }
  YY_BUFFER_STATE buf = foo__scan_string(str, scanner);
  int error = yyparse(scanner, &result);
  foo__delete_buffer(buf, scanner);
  foo_lex_destroy(scanner);
  if (error)
  { /* Failure */
    return 0;
  }
  else
  { /* Success */
    return result.fargsrest_;
  }
}



