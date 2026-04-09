#lang racket
(require rackunit)

;Step 2: Defunctionalizing Environments (introduce extend-env helper)

;the great wall of structs unchanged from A9 - step 1
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

;=========================================================
;               Step 1: Add apply functions
;=========================================================
(define (apply-env env y k)
  (env y k))

(define (apply-closure clos arg k)
  (clos arg k))

(define (apply-k k v)
  (k v))

;==========================================================
;Step 2: Replace higher-order envs with extend-env helper
;==========================================================

(define (extend-env a env)
  (lambda (y k^)
    (if (zero? y)
        (apply-k k^ a)
        (apply-env env (sub1 y) k^))))

;=================================================

;modified value of cps from A9
(define value-of-cps
  (lambda (e env k)
    (match e
      [(const v) (apply-k k v)]

      [(mult l r)
       (value-of-cps l env
                     (lambda (lv)
                       (value-of-cps r env
                                     (lambda (rv)
                                       (apply-k k (* lv rv))))))]

      [(decr r)
       (value-of-cps r env
                     (lambda (rv)
                       (apply-k k (sub1 rv))))]

      [(zerop r)
       (value-of-cps r env
                     (lambda (rv)
                       (apply-k k (zero? rv))))]

      [(ifte test conseq alt)
       (value-of-cps test env
                     (lambda (tv)
                       (if tv
                           (value-of-cps conseq env k)
                           (value-of-cps alt env k))))]

      [(dBvar y)
       (apply-env env y k)]

      [(dBlam body)
       (apply-k k
                (lambda (a k^)
                  (value-of-cps body
                                ;modified in step 2
                                (extend-env a env)
                                k^)))]

      [(app rator rand)
       (value-of-cps rator env
                     (lambda (f)
                       (value-of-cps rand env
                                     (lambda (arg)
                                       (apply-closure f arg k)))))]
      
      [(dBlocal rhs body)
       (value-of-cps rhs env
                     (lambda (a)
                       (value-of-cps body
                                     ;modified in step 2
                                     (extend-env a env)
                                     k)))]

      [(throw continuation result)
       (value-of-cps continuation env
                     (lambda (k2)
                       (value-of-cps result env
                                     (lambda (v)
                                       (apply-k k2 v)))))]
      
      [(dBcatch body)
       (value-of-cps body
                     ;modified in step 2
                     (extend-env k env)
                     k)])))

;empty-env from A9 unchanged
(define empty-env
  (lambda ()
    (lambda (y)
      (error 'value-of "unbound variable"))))

;empty-k from A9 unchanged
(define empty-k
  (lambda ()
    (lambda (v) v)))

;Tests from A9 unchanged (forgot how many there were)

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

;whats left
;=================================================
;          Defunctionalizing Closures
;=================================================
;step 3

;step 4

;step 5

;=================================================
;          Defunctionalizing Closures
;=================================================
;step 6

;step 7

;=================================================
;       Defunctionalizing Continuations
;=================================================
;step 8

;step 9

;step 10

;step 11