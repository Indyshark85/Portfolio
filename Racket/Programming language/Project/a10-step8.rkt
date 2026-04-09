#lang racket
(require rackunit)

;Step 8: define helper functions to construct your continuations, and replace your explicitly higher-order function continuations with calls to these constructors

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
;; (define (apply-env env y k)
;;   (env y k))

;apply env changed in step 4
(define (apply-env env y k)
  (match env
    [(empty-env)
     (error 'value-of "unbound variable")]
    
    [(extend-env a^ env^)
     (if (zero? y)
         (apply-k k a^)
         (apply-env env^ (sub1 y) k))]))
;updated in step 7 bc clos is no longer a function and is a struct now
(define (apply-closure clos a k)
  (match clos
    [(closure-lam body env)
     (value-of-cps body
                   (extend-env a env)
                   k)]))

(define (apply-k k v)
  (k v))

;Step 7: closure helper but now a struct
(struct closure-lam (body env) #:transparent)

;==========================================================
;Step 3: Add ^ to formals of extend-env (outer parameters only)
;==========================================================

(struct extend-env (a^ env^) #:transparent)

;=================================================
;continuation helpers - step 8


;First continuation for multiplication.
;After evaluating the left operand (lv), we evaluate the right operand.
(define (mult-left-k r env k)
  (lambda (lv)
    (value-of-cps r env
                  (mult-right-k lv k))))

;Second continuation for multiplication.
;Once the right operand (rv) is known, multiply and pass result to k.
(define (mult-right-k lv k)
  (lambda (rv)
    (apply-k k (* lv rv))))

;Continuation for decrement.
;Once r evaluates to rv, subtract 1 and pass to k.
(define (decr-k k)
  (lambda (rv)
    (apply-k k (sub1 rv))))

;Continuation for zero test.
;Applies zero? and forwards the boolean to k.
(define (zerop-k k)
  (lambda (rv)
    (apply-k k (zero? rv))))

;Continuation for if expressions.
;Once the test value (tv) is known, select the branch to evaluate.
(define (ifte-k conseq alt env k)
  (lambda (tv)
    (if tv
        (value-of-cps conseq env k)
        (value-of-cps alt env k))))

;Continuation for application after evaluating the rator.
;After obtaining the function f, evaluate the argument.
(define (app-rator-k rand env k)
  (lambda (f)
    (value-of-cps rand env
                  (app-rand-k f k))))

;Continuation after argument evaluation.
;Apply the closure once argument value is known.
(define (app-rand-k f k)
  (lambda (arg)
    (apply-closure f arg k)))

;Continuation for local bindings.
;After evaluating rhs to value a, extend environment and evaluate body.
(define (local-k body env k)
  (lambda (a)
    (value-of-cps body
                  (extend-env a env)
                  k)))

;First continuation for throw.
;Evaluate the continuation expression first.
(define (throw-k result env)
  (lambda (k2)
    (value-of-cps result env
                  (throw-result-k k2))))

;Second continuation for throw.
;Apply the captured continuation to the result value.
(define (throw-result-k k2)
  (lambda (v)
    (apply-k k2 v)))



;modified value of cps from A9 - modified step 8
(define value-of-cps
  (lambda (e env k)
    (match e
      [(const v)
       (apply-k k v)]

      [(mult l r)
       (value-of-cps l env
                     (mult-left-k r env k))]

      [(decr r)
       (value-of-cps r env
                     (decr-k k))]

      [(zerop r)
       (value-of-cps r env
                     (zerop-k k))]

      [(ifte test conseq alt)
       (value-of-cps test env
                     (ifte-k conseq alt env k))]

      [(dBvar y)
       (apply-env env y k)]

      ;Step 6 change: lambdas now produce closure structs
      [(dBlam body)
       (apply-k k
                (closure-lam body env))]

      [(app rator rand)
       (value-of-cps rator env
                     (app-rator-k rand env k))]

      [(dBlocal rhs body)
       (value-of-cps rhs env
                     (local-k body env k))]

      [(throw continuation result)
       (value-of-cps continuation env
                     (throw-k result env))]

      [(dBcatch body)
       (value-of-cps body
                     (extend-env k env)
                     k)])))

;empty-env from A9 changed step 4
;kept to show flow
;; (define empty-env
;;   (lambda ()
;;     (lambda (y)
;;       (error 'value-of "unbound variable"))))

(struct empty-env () #:transparent)

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

;=================================================
; Step 5: Remove fallback case in apply-env (if present)
;=================================================
;No fallback case was used, so no changes needed.

;whats left

;=================================================
;       Defunctionalizing Continuations
;=================================================
;step 8

;step 9

;step 10

;step 11

