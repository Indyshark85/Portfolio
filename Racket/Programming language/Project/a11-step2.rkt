#lang racket
(require rackunit)
(require "parenthec.rkt")

; Step 2: A-Normal Form
;   - Serious function formal parameters renamed to *name* (surrounded by asterisks)
;   - All serious calls transformed so actual arguments are exactly the formal
;     parameter names of the callee, using let* to name intermediate values.
;
; "Serious" functions are: value-of-cps, apply-env, apply-closure, apply-k,
;   and the continuation constructors.
; Trivial calls (constructors, arithmetic, zero?, sub1) are left inline.

;============================================================
; Expression Language
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
; Continuation Constructor Helpers — formals renamed to *name*
;============================================================
(define (mult-left-k *r^* *env^* *k^*)
  (k-mult-left *r^* *env^* *k^*))
(define (mult-right-k *lv^* *k^*)
  (k-mult-right *lv^* *k^*))
(define (decr-k *k^*)
  (k-decr *k^*))
(define (zerop-k *k^*)
  (k-zerop *k^*))
(define (ifte-k *conseq^* *alt^* *env^* *k^*)
  (k-ifte *conseq^* *alt^* *env^* *k^*))
(define (app-rator-k *rand^* *env^* *k^*)
  (k-app-rator *rand^* *env^* *k^*))
(define (app-rand-k *f^* *k^*)
  (k-app-rand *f^* *k^*))
(define (local-k *body^* *env^* *k^*)
  (k-local *body^* *env^* *k^*))
(define (throw-k *result^* *env^*)
  (k-throw *result^* *env^*))
(define (throw-result-k *k2^*)
  (k-throw-result *k2^*))

;============================================================
; apply-env — formals renamed to *name*
;============================================================
(define (apply-env *env* *y* *k*)
  (match *env*
    [(empty-env)
     (error 'value-of "unbound variable")]
    [(extend-env a^ env^)
     (if (zero? *y*)
         (apply-k *k* a^)
         (let* ([*env* env^]
                [*y*   (sub1 *y*)]
                [*k*   *k*])
           (apply-env *env* *y* *k*)))]))

;============================================================
; apply-closure — formals renamed to *name*
;============================================================
(define (apply-closure *clos* *a* *k*)
  (match *clos*
    [(closure-lam body env)
     (let* ([*e*   body]
            [*env* (extend-env *a* env)]
            [*k*   *k*])
       (value-of-cps *e* *env* *k*))]))

;============================================================
; apply-k — formals renamed to *name*
;============================================================
(define (apply-k *k* *v*)
  (match *k*
    [(k-empty)
     *v*]
    [(k-mult-left r^ env^ k2^)
     (let* ([*e*   r^]
            [*env* env^]
            [*k*   (let* ([*lv^* *v*] [*k^* k2^]) (mult-right-k *lv^* *k^*))])
       (value-of-cps *e* *env* *k*))]
    [(k-mult-right lv^ k2^)
     (let* ([*k* k2^]
            [*v* (* lv^ *v*)])
       (apply-k *k* *v*))]
    [(k-decr k2^)
     (let* ([*k* k2^]
            [*v* (sub1 *v*)])
       (apply-k *k* *v*))]
    [(k-zerop k2^)
     (let* ([*k* k2^]
            [*v* (zero? *v*)])
       (apply-k *k* *v*))]
    [(k-ifte conseq^ alt^ env^ k2^)
     (if *v*
         (let* ([*e*   conseq^]
                [*env* env^]
                [*k*   k2^])
           (value-of-cps *e* *env* *k*))
         (let* ([*e*   alt^]
                [*env* env^]
                [*k*   k2^])
           (value-of-cps *e* *env* *k*)))]
    [(k-app-rator rand^ env^ k2^)
     (let* ([*e*   rand^]
            [*env* env^]
            [*k*   (let* ([*f^* *v*] [*k^* k2^]) (app-rand-k *f^* *k^*))])
       (value-of-cps *e* *env* *k*))]
    [(k-app-rand f^ k2^)
     (let* ([*clos* f^]
            [*a*    *v*]
            [*k*    k2^])
       (apply-closure *clos* *a* *k*))]
    [(k-local body^ env^ k2^)
     (let* ([*e*   body^]
            [*env* (extend-env *v* env^)]
            [*k*   k2^])
       (value-of-cps *e* *env* *k*))]
    [(k-throw result^ env^)
     (let* ([*e*   result^]
            [*env* env^]
            [*k*   *v*])
       (value-of-cps *e* *env* *k*))]
    [(k-throw-result k2^)
     (let* ([*k* k2^]
            [*v* *v*])
       (apply-k *k* *v*))]))

;============================================================
; value-of-cps — formals renamed to *name*, ANF applied
;============================================================
(define value-of-cps
  (lambda (*e* *env* *k*)
    (union-case *e* expr
      ((const v)
       (let* ([*k* *k*]
              [*v* v])
         (apply-k *k* *v*)))
      ((dBvar y)
       (let* ([*env* *env*]
              [*y*   y]
              [*k*   *k*])
         (apply-env *env* *y* *k*)))
      ((mult l r)
       (let* ([*e*   l]
              [*env* *env*]
              [*k*   (let* ([*r^*  r]
                            [*env^* *env*]
                            [*k^*  *k*])
                       (mult-left-k *r^* *env^* *k^*))])
         (value-of-cps *e* *env* *k*)))
      ((decr r)
       (let* ([*e*   r]
              [*env* *env*]
              [*k*   (let* ([*k^* *k*]) (decr-k *k^*))])
         (value-of-cps *e* *env* *k*)))
      ((zerop r)
       (let* ([*e*   r]
              [*env* *env*]
              [*k*   (let* ([*k^* *k*]) (zerop-k *k^*))])
         (value-of-cps *e* *env* *k*)))
      ((dBlam body)
       (let* ([*k* *k*]
              [*v* (closure-lam body *env*)])
         (apply-k *k* *v*)))
      ((app rator rand)
       (let* ([*e*   rator]
              [*env* *env*]
              [*k*   (let* ([*rand^* rand]
                            [*env^*  *env*]
                            [*k^*    *k*])
                       (app-rator-k *rand^* *env^* *k^*))])
         (value-of-cps *e* *env* *k*)))
      ((dBlocal rhs body)
       (let* ([*e*   rhs]
              [*env* *env*]
              [*k*   (let* ([*body^* body]
                            [*env^*  *env*]
                            [*k^*    *k*])
                       (local-k *body^* *env^* *k^*))])
         (value-of-cps *e* *env* *k*)))
      ((ifte test conseq alt)
       (let* ([*e*   test]
              [*env* *env*]
              [*k*   (let* ([*conseq^* conseq]
                            [*alt^*    alt]
                            [*env^*    *env*]
                            [*k^*      *k*])
                       (ifte-k *conseq^* *alt^* *env^* *k^*))])
         (value-of-cps *e* *env* *k*)))
      ((dBcatch body)
       (let* ([*e*   body]
              [*env* (extend-env *k* *env*)]
              [*k*   *k*])
         (value-of-cps *e* *env* *k*)))
      ((throw continuation result)
       (let* ([*e*   continuation]
              [*env* *env*]
              [*k*   (let* ([*result^* result]
                            [*env^*    *env*])
                       (throw-k *result^* *env^*))])
         (value-of-cps *e* *env* *k*))))))

;============================================================
; Top-level helpers
;============================================================
(define (empty-k) (k-empty))

(define (run-cps expr)
  (value-of-cps expr (empty-env) (empty-k)))

;============================================================
; main
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

(check-equal? (main) 120)