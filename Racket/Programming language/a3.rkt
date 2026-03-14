#lang racket

;decided to make these look good again
;===================================================================;
;                             Part 1                                ;
;===================================================================;
;1

(define list-ref
  (lambda (ls n)
    (letrec
        ([nth-cdr (lambda (n)
                    (if (zero? n)
                        ls
                        ((lambda () (cdr (nth-cdr (sub1 n)))))))])
      (car (nth-cdr n)))))
;had to fix during debugging
;===================================================================;
;                             Part 2                                ;
;===================================================================;
(struct lam (param body) #:transparent)
(struct app (rator rand) #:transparent)
;2
; You need to fix the function - belive I have fixed this function
(define lambda-exp?
  (lambda (E)
    (letrec
        ([p
          (lambda (e)
            (match e
              ;if string true
              [(? string?) #t]
              ;if lambda (lam) A must be a valid string and B must have a valid body
              [(lam param body) (and (string? param) (p body))]
              ;if application (app) A rator must be valid and B rand must be valid
              [(app rator rand) (and (p rator) (p rand))]
            
              [_ #f]))])
      (p E))))

;just in case -- had to take extra care to see how these are actually used and need to make sure
(lambda-exp? "x")                                   ; #t
(lambda-exp? (lam "x" "x"))                         ; #t
(lambda-exp? (lam "f" (lam "x" (app "f" "x"))))     ; #t

(lambda-exp? (lam 5 "x"))                           ; #f
(lambda-exp? (app "x" 5))                           ; #f
(lambda-exp? (app (lam "x" "x") '(1 2)))            ; #f

;3
(define var-occurs?
  (lambda (x E)
    (match E

      ;variable
      [(? string?) (string=? x E)]

      ;lambda
      [(lam param body)
       (var-occurs? x body)]

      ;application
      [(app rator rand)
       (or (var-occurs? x rator)
           (var-occurs? x rand))]

      ;just in case
      [_ #f])))

;Pulled these from the assingment please work im begging

(var-occurs? "x" "x")                         ; #t
(var-occurs? "x" (lam "x" "y"))               ; #f
(var-occurs? "x" (lam "y" "x"))               ; #t
(var-occurs? "x" (app (app "z" "y") "x"))     ; #t



;4
(define vars
  (lambda (E)
    (match E
      ;if string true
      [(? string?) (list E)]
      ;if lambda (lam)
      [(lam param body) (vars body)]
      ;if application (app)
      [(app rator rand) (append (vars rator) (vars rand))]

      ;just in case
      [_ '()])))
;tests
(vars "x")
;(list "x")

(vars (lam "x" "x"))
;(list "x")

(vars (app (lam "y" (app "x" "x")) (app "x" "y")))
;(list "x" "x" "x" "y")

(vars (lam "z"
           (app (lam "y" (app "a" "z"))
                (app "h" (lam "x" (app "h" "a"))))))
;(list "a" "z" "h" "h" "a")

;5
(define unique-vars
  (lambda (E)
    (match E
      ;if string true - doesnt produce dups
      [(? string?) (list E)]
      
      ;if lambda (lam)- doesnt produce dups
      [(lam param body) (unique-vars body)]

      
      ;if application (app) - does
      [(app rator rand)
       (let ([left (unique-vars rator)]
             [right (unique-vars rand)])
         (append left
                 (filter (lambda (x) (not (member x left)))
                         right)))]

      ;just in case
      [_ '()])))
    
;tests

(unique-vars (app (lam "y" (app "x" "x")) (app "x" "y")))
;(list "x" "y")

(unique-vars (app (lam "z" (lam "y" (app "z" "y"))) "x"))
;(list "z" "y" "x")

(unique-vars
 (app (lam "a" (app "a" "b"))
      (app (lam "c" (app "a" "c"))
           (app "b" "a"))))
;(list "c" "b" "a")

;6

(define var-occurs-free?
  (lambda (x E)
    (match E
      ;string
      [(? string?) (string=? x E)]
      ;lambda
      [(lam param body)
       (if (string=? param x)
           #f
           (var-occurs-free? x body))]

      ;the ever confusing application
      [(app rator rand)
       (or (var-occurs-free? x rator)
           (var-occurs-free? x rand))]

      ;anything else that isnt wanted
      [_ #f])))


;tests                                      -I made these line up bc its pretty alr-
(var-occurs-free? "x" "x")                                   ;#t
(var-occurs-free? "x" (lam "y" "y"))                         ;#f
(var-occurs-free? "x" (lam "x" (app "x" "y")))               ;#f
(var-occurs-free? "x" (lam "x" (lam "x" "x")))               ;#f
(var-occurs-free? "y" (lam "x" (app "x" "y")))               ;#t
(var-occurs-free? "y"
                  (app (lam "y" (app "x" "y"))
                       (lam "x" (app "x" "y"))))                             ;#t
(var-occurs-free? "x"
                  (app (lam "x" (app "x" "x"))
                       (app "x" "x")))                                       ;#t


;7
(define var-occurs-bound?
  (lambda (x E)
    (cond
      ;string
      [(string? E) #f]

      ;app - mixed it up this time for ya TA reading this
      [(app? E)
       (or (var-occurs-bound? x (app-rator E))
           (var-occurs-bound? x (app-rand E)))]

      ;lambda
      [(lam? E)
       (or
        ;lambda binds x AND x appears in body
        (and (string=? x (lam-param E))
             (var-occurs-free? x (lam-body E)))
        ;or bound deeper
        (var-occurs-bound? x (lam-body E)))]

      ;just in case
      [else #f])))

;test
(var-occurs-bound? "x" "x")                                   ;#f
(var-occurs-bound? "x" (lam "x" "x"))                         ;#t
(var-occurs-bound? "y" (lam "x" "x"))                         ;#f
(var-occurs-bound? "x"
                   (app (lam "x" (app "x" "x"))
                        (app "x" "x")))                                        ;#t
(var-occurs-bound? "z"
                   (lam "y" (lam "x" (app "y" "z"))))                          ;#f
(var-occurs-bound? "z"
                   (lam "y" (lam "z" (app "y" "z"))))                          ;#t
(var-occurs-bound? "x" (lam "x" "y"))                         ;#f
(var-occurs-bound? "x" (lam "x" (lam "x" "x")))               ;#t



;8
(define unique-free-vars
  (lambda(E)

    ;imma keep it a buck fifty with you super tired and these are not getting easier
    (letrec
        ([free-vars
          (lambda (e bound)
            (match e

              ;string
              [(? string?)
               ;if alr bound, not free return empt lst
               (if (member e bound)
                   '()
                   ;otherwise return list e
                   (list e))]
              
              [(lam param body)
               (free-vars body (cons param bound))]

              ;you get the point bro...
              [(app rator rand)
               (let ([left (free-vars rator bound)]
                     [right (free-vars rand bound)])
                 (append left
                         (filter (lambda (x) (not (member x left)))
                                 right)))]
              ;just in case
              [_ '()]))])
      (free-vars E '()))))


;a test to make sure i aint crazy
(lam "x" (app "x" "y"))

;9
(define unique-bound-vars
  (lambda (E)
    (letrec
        ([bound-vars
          (lambda (e bound)
            (match e
              
              ;string
              [(? string?)
               (if (member e bound)
                   (list e)
                   '())]
              ;lambda - top bit is about the only bit i can copy
              [(lam param body)
               (let ([inner (bound-vars body (cons param bound))])
                 (if (member param inner)
                     inner
                     (filter (lambda (x) (not (string=? x param)))
                             inner)))]

              ;app - ha just a copy with a replace to bound-vars
              [(app rator rand)
               (let ([left (bound-vars rator bound)]
                     [right (bound-vars rand bound)])
                 (append left
                         (filter (lambda (x) (not (member x left)))
                                 right)))]
              ;just in case
              [_ '()]))])
      (bound-vars E '()))))

;tests
(unique-bound-vars "x")                                                     ;'()

(unique-bound-vars (lam "x" "y"))                                           ;'()

(unique-bound-vars (lam "x" (app "x" "y")))                                 ;(list "x")

(unique-bound-vars
 (app (lam "x" (app (app "x" "y") "e"))
      (lam "c" (app "x" (lam "x" (app "x" (app "e" "c")))))))               ;(list "x" "c")

(unique-bound-vars (lam "y" "y"))                                           ;(list "y")

(unique-bound-vars (lam "x" (app "y" "z")))                                 ;'()

(unique-bound-vars (lam "x" (lam "x" "x")))                                 ;(list "x")

    
;===================================================================;
;                             Part 3                                ;
;===================================================================;

(struct add (l r) #:transparent)
(struct sub (l r) #:transparent)
(struct local (lhs rhs body) #:transparent)
(struct dBadd (l r) #:transparent)
(struct dBsub (l r) #:transparent)
(struct dBvar (index) #:transparent)
(struct dBlocal (rhs body) #:transparent)

;10
(define value-de-bruijn
  (lambda (E env)
    (match E

      ;number
      [(? number?) E]

      ;add
      [(dBadd l r)
       (+ (value-de-bruijn l env)
          (value-de-bruijn r env))]
      ;sub
      [(dBsub l r)
       (- (value-de-bruijn l env)
          (value-de-bruijn r env))]

      ;var
      [(dBvar index)
       (list-ref env index)]

      ;local stuffs
      [(dBlocal rhs body)
       (let ([val (value-de-bruijn rhs env)])
         (value-de-bruijn body (cons val env)))]
      
      ;just in case
      [_ #f])))


;testing
(value-de-bruijn
 (dBadd 10
        (dBlocal (dBadd 1 1)
                 (dBsub 5 (dBvar 0))))
 '())                                            ;13
(value-de-bruijn
 (dBadd 10
        (dBlocal 5
                 (dBlocal (dBadd 1 1)
                          (dBsub (dBvar 1) (dBvar 0)))))
 '())                                            ;13
(value-de-bruijn
 (dBadd 10
        (dBlocal 1
                 (dBlocal (dBadd (dBvar 0) (dBvar 0))
                          (dBsub 5 (dBvar 0)))))
 '())                                            ;13
(value-de-bruijn (dBvar 0) (list 10 11 12 13 14));10
(value-de-bruijn (dBvar 1) (list 10 11 12 13 14));11
(value-de-bruijn (dBvar 2) (list 10 11 12 13 14));12


;11
(struct dBlam (body) #:transparent)
(struct dBapp (rator rand) #:transparent)

(define lambda-lex
  (lambda (exp acc)
    (cond
      [(string? exp)
       (let ([idx (index-of acc exp)])
         (if idx
             (dBvar idx)
             exp))]

      [(lam? exp)
       (dBlam
        (lambda-lex (lam-body exp)
                    (cons (lam-param exp) acc)))]

      [(app? exp)
       (dBapp (lambda-lex (app-rator exp) acc)
              (lambda-lex (app-rand exp) acc))]

      [else exp])))


;12

(define unlex
  (lambda (E binders frees)
    (match E

      ;variable
      [(dBvar n) (list-ref frees n)]

      ;lambda
      [(dBlam body)(let ([v (car binders)])
                     (lam v
                          (unlex body
                                 (cdr binders)
                                 (cons v frees))))]
      ;application
      [(dBapp rator rand)
       (app (unlex rator binders frees)
            (unlex rand binders frees))]

      ;num and literals
      [(? number?) E])))

;13
(define e1=e2?
  (lambda (e1 e2)
    (letrec
        ([eq?
          (lambda (a b env)
            (cond

              ;variables
              [(and (string? a) (string? b))
               (let ([p (assoc a env)])
                 (if p
                     (string=? (cdr p) b)
                     (string=? a b)))]

              ;both lambs (baa baa baa)
              [(and (lam? a)(lam? b))
               (eq? (lam-body a)
                    (lam-body b)
                    (cons (cons (lam-param a)
                                (lam-param b))
                          env))]

              ;both apps
              [(and (app? a) (app? b))
               (and (eq? (app-rator a)(app-rator b) env)
                    (eq? (app-rand a)(app-rand b) env))]
              [else #f]))])
      (eq? e1 e2 '()))))

;14

(struct result (free? bound?) #:transparent)

(define var-occurs-both?
  (lambda (x E)
    (match E
      ;variables
      [(? string?)
       (if (string=? x E)
           (result #t #f)
           (result #f #f))]

      ;lamb (baaaaaaaaa!)
      [(lam param body)
       (let ([r (var-occurs-both? x body)])
         (if (string=? param x)
             (result #f
                     (or (result-free? r)
                         (result-bound? r)))
             r))]

      ;application
      [(app rator rand)
       (let ([r1 (var-occurs-both? x rator)]
             [r2 (var-occurs-both? x rand)])
         (result (or (result-free? r1)
                     (result-free? r2))
                 (or (result-bound? r1)
                     (result-bound? r2))))]

      ;if all else fails
      [_ (result #f #f)])))

(e1=e2? (lam "x" "x") (lam "y" "y"))                     ; #t
(e1=e2? (lam "z" "x") (lam "z" "y"))                     ; #f
(e1=e2? (lam "x" (lam "y" (app "x" "y")))
        (lam "x" (lam "z" (app "x" "z"))))               ; #t

;still aint work but most of it does this was super hard and took a ton of debugging