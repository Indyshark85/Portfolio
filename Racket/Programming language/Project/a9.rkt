#lang racket


;=====================================================
;                  Part I: let/cc
;=====================================================

;1
(define last-non-zero
  (lambda (ls)
    (let/cc k
      (letrec
          ((last-non-zero
            (lambda (ls)
              (match ls
                ;empty list so return empty
                ['() '()]
                
                ;first element is 0 so skip everything before it
                [(cons 0 xs) (k (last-non-zero xs))]
                
                ;first element is non-zero so recurse on rest
                [(cons x xs)
                 (let ((rest (last-non-zero xs)))
                   ;if rest is empty, this might be the last suffix
                   (if (null? rest)
                       (cons x '())
                       (cons x rest)))]))))
        (last-non-zero ls)))))


(last-non-zero '(1 0 2 3 0 4 5)) ;'(4 5)
(last-non-zero '(1 2 3 0 4 5))   ;'(4 5)
(last-non-zero '(0 0 1 2))       ;'(1 2)
(last-non-zero '(0 0 0))         ;'()
(last-non-zero '(1 2 3 4 5))     ;'(1 2 3 4 5)
(last-non-zero '(1 2))           ;'(1 2)
(last-non-zero '())              ;'()
;=====================================================
;            Part II: CPS interpreter
;=====================================================
(struct const (value) #:transparent)
(struct dBvar (index) #:transparent)
(struct mult (l r) #:transparent)
(struct decr (r) #:transparent)
(struct zerop (r) #:transparent)
(struct dBlam (body) #:transparent)
(struct app (rator rand) #:transparent)
(struct dBlocal (rhs body) #:transparent)
(struct ifte (test conseq alt) #:transparent)
(struct dBcatch (body) #:transparent)
(struct throw (continuation result) #:transparent)

(define value-of
  (lambda (e env)
    (match e
      ((const v) v)
      ((mult l r) (* (value-of l env) (value-of r env)))
      ((decr r) (sub1 (value-of r env)))
      ((zerop r) (zero? (value-of r env)))
      ((ifte test conseq alt)
       (if (value-of test env)
           (value-of conseq env)
           (value-of alt env)))
      ((dBcatch body)
       (let/cc k
         (value-of body (lambda (y) (if (zero? y) k (env (sub1 y)))))))
      ((throw continuation result)
       ((value-of continuation env) (value-of result env)))
      ((dBlocal rhs body)
       (let ((a (value-of rhs env)))
         (value-of body (lambda (y) (if (zero? y) a (env (sub1 y)))))))
      ((dBvar y) (env y))
      ((dBlam body)
       (lambda (a) (value-of body (lambda (y) (if (zero? y) a (env (sub1 y)))))))
      ((app rator rand)
       ((value-of rator env) (value-of rand env))))))
 
(define empty-env
  (lambda ()
    (lambda (y)
      (error 'value-of "unbound variable"))))
 
(require rackunit)
(check-equal? (value-of (const 5) (empty-env)) 5)
(check-equal? (value-of (mult (const 5) (const 5)) (empty-env)) 25)
(check-equal? (value-of (decr (decr (const 5))) (empty-env)) 3)
(check-equal? (value-of (ifte (zerop (const 0)) (mult (const 2) (const 2)) (const 3))
                        (empty-env))
              4)
(check-equal? (value-of (app (app (dBlam (dBlam (dBvar 1))) (const 6)) (const 5))
                        (empty-env))
              6)
(check-equal? (value-of (app (dBlam (app (dBlam (dBvar 1)) (const 6))) (const 5))
                        (empty-env))
              5)
(check-equal? (value-of (dBlocal (const 6) (const 4)) (empty-env)) 4)
(check-equal? (value-of (dBlocal (const 5) (dBvar 0)) (empty-env)) 5)
(check-equal? (value-of (mult (const 5) (dBlocal (const 5) (dBvar 0))) (empty-env)) 25)
(check-equal? (value-of (app (ifte (zerop (const 4))
                                   (dBlam (dBvar 0))
                                   (dBlam (const 5)))
                             (const 3))
                        (empty-env))
              5)
(check-equal? (value-of (app (ifte (zerop (const 0))
                                   (dBlam (dBvar 0))
                                   (dBlam (const 5)))
                             (const 3))
                        (empty-env))
              3)
(check-equal? (value-of (dBcatch (throw (throw (dBvar 0) (const 5)) (const 6))) (empty-env)) 5)
(check-equal? (value-of (dBcatch (throw (const 6) (throw (dBvar 0) (const 5)))) (empty-env)) 5)
(check-equal? (value-of (mult (const 3) (dBcatch (throw (const 5) (throw (dBvar 0) (const 5)))))
                        (empty-env))
              15)
(check-equal? (value-of (ifte (zerop (const 5))
                              (app (dBlam (app (dBvar 0) (dBvar 0)))
                                   (dBlam (app (dBvar 0) (dBvar 0))))
                              (const 4))
                        (empty-env))
              4)
(check-equal? (value-of (ifte (zerop (const 0))
                              (const 4)
                              (app (dBlam (app (dBvar 0) (dBvar 0)))
                                   (dBlam (app (dBvar 0) (dBvar 0)))))
                        (empty-env))
              4)
(check-equal? (value-of (app (dBlam (app (app (dBvar 0) (dBvar 0)) (const 2)))
                             (dBlam
                              (dBlam
                               (ifte (zerop (dBvar 0))
                                     (const 1)
                                     (app (app (dBvar 1) (dBvar 1)) (decr (dBvar 0)))))))
                        (empty-env))
              1)
(check-equal? (value-of (dBcatch
                         (throw
                          (dBcatch (mult (const 10)
                                         (throw (dBvar 1)
                                                (mult (dBcatch (throw (dBvar 1) (dBvar 0)))
                                                      (dBcatch (throw (dBvar 1) (dBvar 0)))))))
                          (const 3)))
                        (empty-env))
              9)

;2
(define value-of-cps
  (lambda (e env k)
    (match e
      [(const v) (k v)]

      [(mult l r)
       (value-of-cps l env
                     (lambda (lv)
                       (value-of-cps r env
                                     (lambda (rv)
                                       (k (* lv rv))))))]

      [(decr r)
       (value-of-cps r env
                     (lambda (rv)
                       (k (sub1 rv))))]

      [(zerop r)
       (value-of-cps r env
                     (lambda (rv)
                       (k (zero? rv))))]

      [(ifte test conseq alt)
       (value-of-cps test env
                     (lambda (tv)
                       (if tv
                           (value-of-cps conseq env k)
                           (value-of-cps alt env k))))]

      [(dBvar y) (env y k)]

      [(dBlam body)
       (k
        (lambda (a k^)
          (value-of-cps body
                        (lambda (y k^^)
                          (if (zero? y)
                              (k^^ a)
                              (env (sub1 y) k^^)))
                        k^)))]

      [(app rator rand)
       (value-of-cps rator env
                     (lambda (f)
                       (value-of-cps rand env
                                     (lambda (arg)
                                       (f arg k)))))]
      [(dBlocal rhs body)
       (value-of-cps rhs env
                     (lambda (a)
                       (value-of-cps body
                                     (lambda (y k^)
                                       (if (zero? y)
                                           (k^ a)
                                           (env (sub1 y) k^)))
                                     k)))]

      [(throw continuation result)
       (value-of-cps continuation env
                     (lambda (k2)
                       (value-of-cps result env
                                     (lambda (v)
                                       (k2 v)))))]
      [(dBcatch body)
       (value-of-cps body
                     (lambda (y k^)
                       (if (zero? y)
                           (k^ k)
                           (env (sub1 y) k^)))
                     k)]
      [(throw continuation result)
       (value-of-cps continuation env
                     (lambda (k2)
                       (value-of-cps result env
                                     (lambda (v)
                                       (k2 v)))))]
      )))


(define empty-k
  (lambda ()
    (lambda (v) v)))

(check-equal?
 (value-of-cps (const 5)
               (empty-env)
               (empty-k))
 5)

(check-equal?
 (value-of-cps (mult (const 5) (const 5))
               (empty-env)
               (empty-k))
 25)

(check-equal?
 (value-of-cps (decr (decr (const 5)))
               (empty-env)
               (empty-k))
 3)

(check-equal?
 (value-of-cps
  (ifte (zerop (const 0))
        (mult (const 2) (const 2))
        (const 3))
  (empty-env)
  (empty-k))
 4)

(check-equal?
 (value-of-cps
  (app (app (dBlam (dBlam (dBvar 1))) (const 6)) (const 5))
  (empty-env)
  (empty-k))
 6)

(check-equal?
 (value-of-cps (dBlocal (const 5) (dBvar 0))
               (empty-env)
               (empty-k))
 5)
(check-equal?
 (value-of-cps (dBlocal (const 6) (const 4))
               (empty-env)
               (empty-k))
 4)

(check-equal?
 (value-of-cps (dBlocal (const 5) (dBvar 0))
               (empty-env)
               (empty-k))
 5)

(check-equal?
 (value-of-cps (mult (const 5) (dBlocal (const 5) (dBvar 0)))
               (empty-env)
               (empty-k))
 25)

(check-equal?
 (value-of-cps
  (app (ifte (zerop (const 4))
             (dBlam (dBvar 0))
             (dBlam (const 5)))
       (const 3))
  (empty-env)
  (empty-k))
 5)

(check-equal?
 (value-of-cps
  (app (ifte (zerop (const 0))
             (dBlam (dBvar 0))
             (dBlam (const 5)))
       (const 3))
  (empty-env)
  (empty-k))
 3)

(check-equal?
 (value-of-cps
  (dBcatch (throw (throw (dBvar 0) (const 5)) (const 6)))
  (empty-env)
  (empty-k))
 5)

(check-equal?
 (value-of-cps
  (dBcatch (throw (const 6) (throw (dBvar 0) (const 5))))
  (empty-env)
  (empty-k))
 5)

(check-equal?
 (value-of-cps
  (mult (const 3)
        (dBcatch (throw (const 5) (throw (dBvar 0) (const 5)))))
  (empty-env)
  (empty-k))
 15)

(check-equal?
 (value-of-cps
  (ifte (zerop (const 5))
        (app (dBlam (app (dBvar 0) (dBvar 0)))
             (dBlam (app (dBvar 0) (dBvar 0))))
        (const 4))
  (empty-env)
  (empty-k))
 4)

(check-equal?
 (value-of-cps
  (ifte (zerop (const 0))
        (const 4)
        (app (dBlam (app (dBvar 0) (dBvar 0)))
             (dBlam (app (dBvar 0) (dBvar 0)))))
  (empty-env)
  (empty-k))
 4)

(check-equal?
 (value-of-cps
  (app (dBlam (app (app (dBvar 0) (dBvar 0)) (const 2)))
       (dBlam
        (dBlam
         (ifte (zerop (dBvar 0))
               (const 1)
               (app (app (dBvar 1) (dBvar 1))
                    (decr (dBvar 0)))))))
  (empty-env)
  (empty-k))
 1)

(check-equal?
 (value-of-cps
  (dBcatch
   (throw
    (dBcatch
     (mult (const 10)
           (throw (dBvar 1)
                  (mult (dBcatch (throw (dBvar 1) (dBvar 0)))
                        (dBcatch (throw (dBvar 1) (dBvar 0)))))))
    (const 3)))
  (empty-env)
  (empty-k))
 9)

;=====================================================
;                   Brainteaser
;=====================================================
(struct lam (param body) #:transparent)
(struct local (lhs rhs body) #:transparent)
(struct catch (param body) #:transparent)
;3
(define (lex expr acc)
  (match expr
    [(? number? n) (const n)]
    [(? boolean? b) (const b)]
    [(? string? s) (dBvar (index-of acc s))] ;fixed hopefully IT IS YAY TIME TO STUDY
    [(lam param body) (dBlam (lex body (cons param acc)))]
    [(catch param body) (dBcatch (lex body (cons param acc)))]
    [(local lhs rhs body)
     (dBlocal (lex rhs acc)
              (lex body (cons lhs acc)))]
    [(app rator rand) (app (lex rator acc) (lex rand acc))]
    [(mult l r) (mult (lex l acc) (lex r acc))]
    [(decr r) (decr (lex r acc))]
    [(zerop r) (zerop (lex r acc))]
    [(ifte test conseq alt)
     (ifte (lex test acc)
           (lex conseq acc)
           (lex alt acc))]
    [(throw cont res)
     (throw (lex cont acc) (lex res acc))]
    [else (error "unknown expression type" expr)]))