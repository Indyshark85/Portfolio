#lang racket
(require rackunit)
(require "parenthec.rkt")

; Step 1:
;   - Replaced 11 struct definitions for expressions with define-union expr
;   - Changed value-of-cps to use union-case instead of match
;   - Added main test function (must return 120)

;============================================================
; Expression Language — define-union (Step 1)
;============================================================
(define-union expr
  (const value)
  (dBvar index)
  (mult l r)
  (decr r)
  (zerop r)
  (dBlam body)
  (app rator rand)
  (dBlocal rhs body)
  (ifte test conseq alt)
  (dBcatch body)
  (throw continuation result))

;============================================================
; Environment Structs
;============================================================
(struct empty-env () #:transparent)
(struct extend-env (a^ env^) #:transparent)

;============================================================
; Closure Struct
;============================================================
(struct closure-lam (body env) #:transparent)

;============================================================
; Continuation Structs
;============================================================
(struct k-empty () #:transparent)
(struct k-mult-left (r^ env^ k^) #:transparent)
(struct k-mult-right (lv^ k^) #:transparent)
(struct k-decr (k^) #:transparent)
(struct k-zerop (k^) #:transparent)
(struct k-ifte (conseq^ alt^ env^ k^) #:transparent)
(struct k-app-rator (rand^ env^ k^) #:transparent)
(struct k-app-rand (f^ k^) #:transparent)
(struct k-local (body^ env^ k^) #:transparent)
(struct k-throw (result^ env^) #:transparent)
(struct k-throw-result (k2^) #:transparent)

;============================================================
; Continuation Constructor Helpers
;============================================================
(define (mult-left-k r^ env^ k^)      (k-mult-left r^ env^ k^))
(define (mult-right-k lv^ k^)         (k-mult-right lv^ k^))
(define (decr-k k^)                   (k-decr k^))
(define (zerop-k k^)                  (k-zerop k^))
(define (ifte-k conseq^ alt^ env^ k^) (k-ifte conseq^ alt^ env^ k^))
(define (app-rator-k rand^ env^ k^)   (k-app-rator rand^ env^ k^))
(define (app-rand-k f^ k^)            (k-app-rand f^ k^))
(define (local-k body^ env^ k^)       (k-local body^ env^ k^))
(define (throw-k result^ env^)        (k-throw result^ env^))
(define (throw-result-k k2^)          (k-throw-result k2^))

;============================================================
; apply-env
;============================================================
(define (apply-env env y k)
  (match env
    [(empty-env)
     (error 'value-of "unbound variable")]
    [(extend-env a^ env^)
     (if (zero? y)
         (apply-k k a^)
         (apply-env env^ (sub1 y) k))]))

;============================================================
; apply-closure
;============================================================
(define (apply-closure clos a k)
  (match clos
    [(closure-lam body env)
     (value-of-cps body (extend-env a env) k)]))

;============================================================
; apply-k
;============================================================
(define (apply-k k v)
  (match k
    [(k-empty) v]
    [(k-mult-left r^ env^ k2^)
     (value-of-cps r^ env^ (mult-right-k v k2^))]
    [(k-mult-right lv^ k2^)
     (apply-k k2^ (* lv^ v))]
    [(k-decr k2^)
     (apply-k k2^ (sub1 v))]
    [(k-zerop k2^)
     (apply-k k2^ (zero? v))]
    [(k-ifte conseq^ alt^ env^ k2^)
     (if v
         (value-of-cps conseq^ env^ k2^)
         (value-of-cps alt^ env^ k2^))]
    [(k-app-rator rand^ env^ k2^)
     (value-of-cps rand^ env^ (app-rand-k v k2^))]
    [(k-app-rand f^ k2^)
     (apply-closure f^ v k2^)]
    [(k-local body^ env^ k2^)
     (value-of-cps body^ (extend-env v env^) k2^)]
    [(k-throw result^ env^)
     (value-of-cps result^ env^ v)]
    [(k-throw-result k2^)
     (apply-k k2^ v)]))

;============================================================
; value-of-cps — now uses union-case (Step 1)
;============================================================
(define value-of-cps
  (lambda (e env k)
    (union-case e expr
      ((const v)
       (apply-k k v))
      ((dBvar y)
       (apply-env env y k))
      ((mult l r)
       (value-of-cps l env (mult-left-k r env k)))
      ((decr r)
       (value-of-cps r env (decr-k k)))
      ((zerop r)
       (value-of-cps r env (zerop-k k)))
      ((dBlam body)
       (apply-k k (closure-lam body env)))
      ((app rator rand)
       (value-of-cps rator env (app-rator-k rand env k)))
      ((dBlocal rhs body)
       (value-of-cps rhs env (local-k body env k)))
      ((ifte test conseq alt)
       (value-of-cps test env (ifte-k conseq alt env k)))
      ((dBcatch body)
       (value-of-cps body (extend-env k env) k))
      ((throw continuation result)
       (value-of-cps continuation env (throw-k result env))))))

;============================================================
; Top-level helpers
;============================================================
(define (empty-k) (k-empty))

(define (run-cps expr)
  (value-of-cps expr (empty-env) (empty-k)))

;============================================================
; main — required by Step 1 (must return 120)
;   Corresponds to:
;   (let ((f (lambda (f) (lambda (n)
;               (if (zero? n) 1 (* n ((f f) (sub1 n))))))))
;     (* (catch k ((f f) (throw k ((f f) 4)))) 5))
;============================================================
(define main
  (lambda ()
    (value-of-cps
     (expr_dBlocal
      (expr_dBlam
       (expr_dBlam
        (expr_ifte
         (expr_zerop (expr_dBvar 0))
         (expr_const 1)
         (expr_mult
          (expr_dBvar 0)
          (expr_app
           (expr_app (expr_dBvar 1) (expr_dBvar 1))
           (expr_decr (expr_dBvar 0)))))))
      (expr_mult
       (expr_dBcatch
        (expr_app
         (expr_app (expr_dBvar 1) (expr_dBvar 1))
         (expr_throw
          (expr_dBvar 0)
          (expr_app
           (expr_app (expr_dBvar 1) (expr_dBvar 1))
           (expr_const 4)))))
       (expr_const 5)))
     (empty-env)
     (empty-k))))

;============================================================
; Tests
;============================================================
(check-equal? (run-cps (expr_const 5)) 5)
(check-equal? (run-cps (expr_mult (expr_const 5) (expr_const 5))) 25)
(check-equal? (run-cps (expr_decr (expr_decr (expr_const 5)))) 3)
(check-equal?
 (run-cps (expr_ifte (expr_zerop (expr_const 0)) (expr_mult (expr_const 2) (expr_const 2)) (expr_const 3)))
 4)
(check-equal?
 (run-cps (expr_app (expr_app (expr_dBlam (expr_dBlam (expr_dBvar 1))) (expr_const 6)) (expr_const 5)))
 6)
(check-equal? (run-cps (expr_dBlocal (expr_const 5) (expr_dBvar 0))) 5)
(check-equal? (run-cps (expr_dBlocal (expr_const 6) (expr_const 4))) 4)
(check-equal? (run-cps (expr_mult (expr_const 5) (expr_dBlocal (expr_const 5) (expr_dBvar 0)))) 25)
(check-equal?
 (run-cps (expr_app (expr_ifte (expr_zerop (expr_const 4)) (expr_dBlam (expr_dBvar 0)) (expr_dBlam (expr_const 5))) (expr_const 3)))
 5)
(check-equal?
 (run-cps (expr_app (expr_ifte (expr_zerop (expr_const 0)) (expr_dBlam (expr_dBvar 0)) (expr_dBlam (expr_const 5))) (expr_const 3)))
 3)
(check-equal?
 (run-cps (expr_dBcatch (expr_throw (expr_throw (expr_dBvar 0) (expr_const 5)) (expr_const 6))))
 5)
(check-equal?
 (run-cps (expr_dBcatch (expr_throw (expr_const 6) (expr_throw (expr_dBvar 0) (expr_const 5)))))
 5)
(check-equal?
 (run-cps (expr_mult (expr_const 3) (expr_dBcatch (expr_throw (expr_const 5) (expr_throw (expr_dBvar 0) (expr_const 5))))))
 15)
(check-equal?
 (run-cps (expr_ifte (expr_zerop (expr_const 5))
                     (expr_app (expr_dBlam (expr_app (expr_dBvar 0) (expr_dBvar 0)))
                               (expr_dBlam (expr_app (expr_dBvar 0) (expr_dBvar 0))))
                     (expr_const 4)))
 4)
(check-equal?
 (run-cps (expr_ifte (expr_zerop (expr_const 0))
                     (expr_const 4)
                     (expr_app (expr_dBlam (expr_app (expr_dBvar 0) (expr_dBvar 0)))
                               (expr_dBlam (expr_app (expr_dBvar 0) (expr_dBvar 0))))))
 4)
(check-equal?
 (run-cps
  (expr_app (expr_dBlam (expr_app (expr_app (expr_dBvar 0) (expr_dBvar 0)) (expr_const 2)))
            (expr_dBlam (expr_dBlam (expr_ifte (expr_zerop (expr_dBvar 0))
                                               (expr_const 1)
                                               (expr_app (expr_app (expr_dBvar 1) (expr_dBvar 1))
                                                         (expr_decr (expr_dBvar 0))))))))
 1)
(check-equal?
 (run-cps
  (expr_dBcatch
   (expr_throw
    (expr_dBcatch
     (expr_mult (expr_const 10)
                (expr_throw (expr_dBvar 1)
                            (expr_mult (expr_dBcatch (expr_throw (expr_dBvar 1) (expr_dBvar 0)))
                                       (expr_dBcatch (expr_throw (expr_dBvar 1) (expr_dBvar 0)))))))
    (expr_const 3))))
 9)

; The main test — must produce 120
(check-equal? (main) 120)