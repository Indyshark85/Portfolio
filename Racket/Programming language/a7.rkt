#lang racket
;WHAT DOES HALF OF THIS EVEN MEAN????
(struct answer (value state) #:transparent)
;1
(struct ifte (bool true false) #:transparent)
(struct app (rator rand) #:transparent)
(struct lam (param body) #:transparent)
;3
(struct assign (lhs rhs) #:transparent)
;4
(struct hole () #:transparent)
;11
(struct couple (left right) #:transparent)

;---------------------------------------------------
;     Part 1: Programming More with Mutation
;---------------------------------------------------
(define explicit-multiply
  (app (lam "f"
            (app (lam "_" (app "deref" "f"))
                 (app (app "setref" "f")
                      (lam "x"
                           (lam "y"
                                (ifte (app "zero?" "x")
                                      0
                                      (app (app "+" "y")
                                           (app (app (app "deref" "f")
                                                     (app (app "-" "x") 1))
                                                "y"))))))))
       (app "newref" 311)))

(define explicit-even?
  (app (lam "e"
            (app (lam "o"
                      (app (lam "_"
                                (app (lam "_" (app "deref" "e"))
                                     (app (app "setref" "o")
                                          (lam "n" (ifte (app "zero?" "n")
                                                         #f
                                                         (app (app "deref" "e")
                                                              (app (app "-" "n") 1)))))))
                           (app (app "setref" "e")
                                (lam "n" (ifte (app "zero?" "n")
                                               #t
                                               (app (app "deref" "o")
                                                    (app (app "-" "n") 1)))))))
                 (app "newref" 311)))
       (app "newref" 311)))

;1

(define explicit-halve
  (app
   (lam "f"
        (app
         (lam "_" (app "deref" "f"))
         (app
          (app "setref" "f")
          (lam "n"
               (ifte (app "zero?" "n")
                     0
                     (app
                      (app "+" 1)
                      (app
                       (app "deref" "f")
                       (app
                        (app "-" "n")
                        2))))))))
   (app "newref" 311)))
  
;2
(define explicit-collatz
  (app
   (lam "f"
        (app
         (lam "_" (app "deref" "f"))
         (app
          (app "setref" "f")
          (lam "n"
               (ifte
                (app "zero?" (app (app "-" "n") 1))
                0
                (ifte
                 (app explicit-even? "n")
                 (app
                  (app "+" 1)
                  (app
                   (app "deref" "f")
                   (app explicit-halve "n")))
                 (app
                  (app "+" 1)
                  (app
                   (app "deref" "f")
                   (app
                    (app "+"
                         (app
                          (app "+"
                               "n")
                          (app
                           (app "+"
                                "n")
                           "n")))
                    1)))))))))
   (app "newref" 311)))

;3
(define implicit-multiply
  (app (lam "f"
            (app (lam "_" "f")
                 (assign "f"
                         (lam "x"
                              (lam "y"
                                   (ifte (app "zero?" "x")
                                         0
                                         (app (app "+" "y")
                                              (app (app "f"
                                                        (app (app "-" "x") 1))
                                                   "y"))))))))
       311))
 
(define implicit-collatz
  (app
   (lam "f"
        (app
         (lam "_" "f")
         (assign
          "f"
          (lam "n"
               (ifte
                (app "zero?" (app (app "-" "n") 1))
                0
                (ifte
                 (app
                  (app
                   (lam "e"
                        (app
                         (lam "_" "e")
                         (assign
                          "e"
                          (lam "m"
                               (ifte (app "zero?" "m")
                                     #t
                                     (ifte (app "zero?" (app (app "-" "m") 1))
                                           #f
                                           (app "e"
                                                (app (app "-" "m") 2))))))))
                   311)
                  "n")
                 (app
                  (app "+" 1)
                  (app "f"
                       (app
                        (app
                         (lam "h"
                              (app
                               (lam "_" "h")
                               (assign
                                "h"
                                (lam "m"
                                     (ifte (app "zero?" "m")
                                           0
                                           (app
                                            (app "+" 1)
                                            (app "h"
                                                 (app (app "-" "m") 2))))))))
                         311)
                        "n")))
                 (app
                  (app "+" 1)
                  (app "f"
                       (app
                        (app "+"
                             (app
                              (app "+"
                                   "n")
                              (app
                               (app "+"
                                    "n")
                               "n")))
                        1)))))))))
   311))



;---------------------------------------------------
;       Part 2: Distinguishing Expressions
;---------------------------------------------------
;4
(define distinguish-functions
  (app
   (lam "x"
        (app (hole) 5))
   10))
;5
(define distinguish-times
  (app
   (lam "r"
        (app
         (lam "x"
              (hole))
         (lam "_"
              (app
               (lam "_"
                    (app "deref" "r"))
               (app
                (app "setref" "r")
                (app (app "+" (app "deref" "r")) 1))))))
   (app "newref" 0)))

;6
(define distinguish-order
  (app
   (lam "f"
        (app
         (lam "g"
              (hole))
         (lam "_"
              (assign "f" (lam "_" 1)))))
   (lam "_" 0)))

;7

(define distinguish-call
  (app
   (lam "x"
        (app
         (lam "_"
              (app (lam "_" "x")
                   (hole)))
         0))
   #f))

;----------------------------------------------------
;Part 3: Distinguishing Parameter-Passing Variations
;----------------------------------------------------
;8
(define call-by-what
  (app
   (lam "x"
        (app
         (lam "y"
              (app
               (lam "z"
                    (app
                     (app "+"
                          (app "x" 0))
                     (app
                      (app "+"
                           (app "y" 0))
                      (app "z" 0))))
               (lam "_" 3)))
         (lam "_" 4)))
   (app
    (lam "r"
         (lam "_"
              (app
               (lam "_" "r")
               (assign "r"
                       (app
                        (app "+"
                             "r")
                        11)))))
    1)))

;----------------------------------------------------
;                     Brainteasers
;----------------------------------------------------
;lets give it the old college try

;9 thus begins research on how to fix this -- she works now

(define explicit-memo
  (lam "f"
       (app
        (lam "tbl"
             (lam "n"
                  (app
                   (app (app "deref" "tbl") "n")
                   (lam "_"
                        (app
                         (lam "result"
                              (app
                               (lam "old"
                                    (app
                                     (lam "_"
                                          "result")
                                     (app
                                      (app "setref" "tbl")
                                      (lam "m"
                                           (lam "miss"
                                                (ifte
                                                 (app "zero?" (app (app "-" "m") "n"))
                                                 "result"
                                                 (app (app "old" "m") "miss")))))))
                               (app "deref" "tbl")))   ;<== MOVED DEREF HERE
                         (app "f" "n"))))))
        (app "newref"
             (lam "m"
                  (lam "miss"
                       (app "miss" 0)))))))

;10 - Please RUN WHY DO YOU KEEP BREAKING AAAAAAAAAAAAAH
(define implicit-memo
  (lam "f"
       (app
        (lam "tbl"
             (app
              (lam "_"
                   (lam "arg"
                        (app
                         (app "tbl" "arg")
                         (lam "miss"
                              (app
                               (lam "res"
                                    (app
                                     (lam "old"
                                          (app
                                           (lam "_"
                                                "res")
                                           (assign
                                            "tbl"
                                            (lam "k"
                                                 (lam "fail"
                                                      (ifte
                                                       (app "zero?"
                                                            (app (app "-" "k") "arg"))
                                                       "res"
                                                       (app (app "old" "k") "fail")))))))
                                     "tbl"))
                               (app "f" "arg"))))))
              (assign "tbl"
                      (lam "k"
                           (lam "fail"
                                (app "fail" 0))))))
        (lam "k"
             (lam "fail"
                  (app "fail" #t))))))

;11 tried to do this without looking at renumber my bad probably should have.
(define (unnumber tree state)
  (cond
    [(number? tree)
     (answer (- state tree) tree)]

    [(couple? tree)
     (let* ([right (unnumber (couple-right tree) state)]
            [left  (unnumber (couple-left tree)
                             (answer-state right))])
       (answer
        (couple (answer-value left)
                (answer-value right))
        (answer-state left)))]

    [else (error "bad tree")]))