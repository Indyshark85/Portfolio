#lang racket
(struct ifte (test conseq altern) #:transparent)
(struct app (operator operand) #:transparent)
(struct lam (parameter body) #:transparent)


;===================================================================
;                     Part 1: Beta Reduction
;===================================================================

;1
(define q1
  (list
   (app (lam "x" (app "bake" (app "x" "x")))
        (lam "x" (app "shake" "x")))
   (app "bake"
        (app (lam "x" (app "shake" "x"))
             (lam "x" (app "shake" "x"))))
   (app "bake"
        (app "shake"
             (lam "x" (app "shake" "x"))))))
;=================================================================
;2
(define q2
  (list
   (app
    (lam "x"
         (lam "y"
              (app (app "toss" (app "x" "y"))
                   (app "y" "x"))))
    (lam "x" (app (app "rinse" "x") "y")))
   
   (lam "y1"
        (app
         (app "toss"
              (app (lam "x" (app (app "rinse" "x") "y")) "y1"))
         (app "y1"
              (lam "x" (app (app "rinse" "x") "y")))))

   (lam "y1"
        (app
         (app "toss"
              (app (app "rinse" "y1") "y"))
         (app "y1"
              (lam "x" (app (app "rinse" "x") "y")))))))
;=================================================================
;3
(define q3
  (list
   (app
    (lam "x"
         (app (app "y"
                   (lam "y" (app "y" "x")))
              (lam "x" (app "x" "z"))))
    (lam "x" (app (app "rinse" "x") "y")))

   (app
    (app "y"
         (lam "y1"
              (app "y1"
                   (lam "x" (app (app "rinse" "x") "y")))))
    (lam "x" (app "x" "z")))))
;=================================================================
;please work please pleeeeeease
;4
(define q4
  (list

   ;0
   (app (lam "f" (app (app "f" "f") (app "f" "f")))
        (lam "f" (lam "x" (app "f" (app "f" "x")))))

   ;1
   (app
    (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
         (lam "f" (lam "x" (app "f" (app "f" "x")))))
    (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
         (lam "f" (lam "x" (app "f" (app "f" "x"))))))

   ;2
   (app
    (lam "x"
         (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
              (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
                   "x")))
    (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
         (lam "f" (lam "x" (app "f" (app "f" "x"))))))

   ;3
   (app
    (lam "x"
         (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
              (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
                   "x")))
    (lam "x"
         (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
              (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
                   "x"))))

   ;4
   (app
    (lam "f" (lam "x" (app "f" (app "f" "x"))))
    (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
         (lam "x"
              (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
                   (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
                        "x")))))

   ;5
   (lam "x"
        (app
         (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
              (lam "x"
                   (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
                        (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
                             "x"))))
         (app
          (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
               (lam "x"
                    (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
                         (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
                              "x"))))
          "x")))
   
   ;6 - hopfully the final reduction - hint it was not
   
   (lam "x"
        (app
         (lam "x"
              (app
               (lam "x"
                    (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
                         (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
                              "x")))
               (app
                (lam "x"
                     (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
                          (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
                               "x")))
                "x")))
         (app
          (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
               (lam "x"
                    (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
                         (app (lam "f" (lam "x" (app "f" (app "f" "x"))))
                              "x"))))
          "x")))
   
   ;7 FINAL IM LITERALLY BEGGING WHERE DO THEY WANT IT TO END - it did not end here ... 
   
   ))

;===================================================================
;                     Part 2: State Passing
;===================================================================
(struct couple (left right) #:transparent)
(struct answer (value state) #:transparent)

;renumber : Tree Int -> Answer
;5
(define (renumber t s)
  (cond
    [(integer? t)
     (answer s (+ s t))]

    [(couple? t)
     (let* ([left-ans (renumber (couple-left t) s)]
            [right-ans (renumber (couple-right t)
                                 (answer-state left-ans))])
       (answer
        (couple (answer-value left-ans)
                (answer-value right-ans))
        (answer-state right-ans)))]))
                


;===================================================================
;                Part 3: Programming with Mutation
;===================================================================

;6
(define first-time?
  (app
   (lam "cell"
        (lam "x"
             (ifte (app "deref" "cell")
                   (app
                    (lam "_"
                         #t)
                    (app (app "setref" "cell") #f))
                   #f)))
   (app "newref" #t)))


;7
(define swap
  (lam "x"
       (lam "y"
            (app
             (lam "tmp"
                  (app
                   (lam "_"
                        (app (app "setref" "y") "tmp"))
                   (app (app "setref" "x")
                        (app "deref" "y"))))
             (app "deref" "x")))))

;8
(define previous
  (app
   (lam "cell"
        (lam "x"
             (app
              (lam "old"
                   (app
                    (lam "_"
                         "old")
                    (app (app "setref" "cell") "x")))
              (app "deref" "cell"))))
   (app "newref" 0)))


;9
(define explicit-accumulate
  (app
   (lam "cell"
        (lam "x"
             (app
              (lam "sum"
                   (app
                    (lam "_"
                         "sum")
                    (app (app "setref" "cell") "sum")))
              (app (app "+" (app "deref" "cell")) "x"))))
   (app "newref" 0)))

;===================================================================
;                          Brainteasers
;===================================================================
;10
(struct assign (lhs rhs) #:transparent)

(define implicit-accumulate
  (app
   (lam "total"
        (lam "x"
             (app
              (lam "_"
                   "total")
              (assign "total"
                      (app (app "+" "total") "x")))))
   0))