#lang racket
(require rackunit)
(require "parenthec.rkt")

; Step 6: Replace all continuation structs with define-union kt
;   - Remove all (struct k-...) definitions
;   - Add (define-union kt ...) with one variant per continuation frame
;   - Change apply-k to use union-case instead of match
;   - Replace all k-... constructors with kt_ constructors
;   - empty-k now invokes kt_empty

;============================================================
; Global Registers
;============================================================
(define-registers *e* *env* *k* *v* *y*
  *clos* *a*
  *r^* *env^* *k^*
  *lv^* *f^* *rand^* *body^*
  *conseq^* *alt^* *result^* *k2^*)

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
; Closure
;============================================================
(define-union clos
  (lam body env))

;============================================================
; Environment
;============================================================
(define-union envr
  (empty)
  (extend a^ env^))

;============================================================
; Continuations — now a define-union (Step 6)
;============================================================
(define-union kt
  (empty)
  (mult-left  r^ env^ k^)
  (mult-right lv^ k^)
  (decr       k^)
  (zerop      k^)
  (ifte       conseq^ alt^ env^ k^)
  (app-rator  rand^ env^ k^)
  (app-rand   f^ k^)
  (local      body^ env^ k^)
  (throw      result^ env^)
  (throw-result k2^))

;============================================================
; Continuation Constructor Helpers — now use kt_ constructors
;============================================================
(define (mult-left-k)    (kt_mult-left  *r^* *env^* *k^*))
(define (mult-right-k)   (kt_mult-right *lv^* *k^*))
(define (decr-k)         (kt_decr       *k^*))
(define (zerop-k)        (kt_zerop      *k^*))
(define (ifte-k)         (kt_ifte       *conseq^* *alt^* *env^* *k^*))
(define (app-rator-k)    (kt_app-rator  *rand^* *env^* *k^*))
(define (app-rand-k)     (kt_app-rand   *f^* *k^*))
(define (local-k)        (kt_local      *body^* *env^* *k^*))
(define (throw-k)        (kt_throw      *result^* *env^*))
(define (throw-result-k) (kt_throw-result *k2^*))
(define (empty-k)        (kt_empty))

;============================================================
; apply-env
;============================================================
(define (apply-env)
  (union-case *env* envr
              ((empty)
               (error 'value-of "unbound variable"))
              ((extend a^ env^)
               (if (zero? *y*)
                   (begin
                     (set! *v* a^)
                     (apply-k))
                   (begin
                     (set! *env* env^)
                     (set! *y*   (sub1 *y*))
                     (apply-env))))))

;============================================================
; apply-closure
;============================================================
(define (apply-closure)
  (union-case *clos* clos
              ((lam body env)
               (begin
                 (set! *e*   body)
                 (set! *env* (envr_extend *a* env))
                 (value-of-cps)))))

;============================================================
; apply-k — now uses union-case on kt (Step 6)
;============================================================
(define (apply-k)
  (union-case *k* kt
              ((empty)
               *v*)
              ((mult-left r^ env^ k2^)
               (begin
                 (set! *lv^* *v*)
                 (set! *k^*  k2^)
                 (set! *k*   (mult-right-k))
                 (set! *e*   r^)
                 (set! *env* env^)
                 (value-of-cps)))
              ((mult-right lv^ k2^)
               (begin
                 (set! *v* (* lv^ *v*))
                 (set! *k* k2^)
                 (apply-k)))
              ((decr k2^)
               (begin
                 (set! *v* (sub1 *v*))
                 (set! *k* k2^)
                 (apply-k)))
              ((zerop k2^)
               (begin
                 (set! *v* (zero? *v*))
                 (set! *k* k2^)
                 (apply-k)))
              ((ifte conseq^ alt^ env^ k2^)
               (if *v*
                   (begin
                     (set! *e*   conseq^)
                     (set! *env* env^)
                     (set! *k*   k2^)
                     (value-of-cps))
                   (begin
                     (set! *e*   alt^)
                     (set! *env* env^)
                     (set! *k*   k2^)
                     (value-of-cps))))
              ((app-rator rand^ env^ k2^)
               (begin
                 (set! *f^*  *v*)
                 (set! *k^*  k2^)
                 (set! *k*   (app-rand-k))
                 (set! *e*   rand^)
                 (set! *env* env^)
                 (value-of-cps)))
              ((app-rand f^ k2^)
               (begin
                 (set! *clos* f^)
                 (set! *a*    *v*)
                 (set! *k*    k2^)
                 (apply-closure)))
              ((local body^ env^ k2^)
               (begin
                 (set! *e*   body^)
                 (set! *env* (envr_extend *v* env^))
                 (set! *k*   k2^)
                 (value-of-cps)))
              ((throw result^ env^)
               (begin
                 (set! *e*   result^)
                 (set! *env* env^)
                 (set! *k*   *v*)
                 (value-of-cps)))
              ((throw-result k2^)
               (begin
                 (set! *k* k2^)
                 (apply-k)))))

;============================================================
; value-of-cps
;============================================================
(define (value-of-cps)
  (union-case *e* expr
              ((const v)
               (begin (set! *v* v) (apply-k)))
              ((dBvar y)
               (begin (set! *y* y) (apply-env)))
              ((mult l r)
               (begin
                 (set! *r^*   r)
                 (set! *env^* *env*)
                 (set! *k^*   *k*)
                 (set! *k*    (mult-left-k))
                 (set! *e*    l)
                 (value-of-cps)))
              ((decr r)
               (begin
                 (set! *k^* *k*)
                 (set! *k*  (decr-k))
                 (set! *e*  r)
                 (value-of-cps)))
              ((zerop r)
               (begin
                 (set! *k^* *k*)
                 (set! *k*  (zerop-k))
                 (set! *e*  r)
                 (value-of-cps)))
              ((dBlam body)
               (begin
                 (set! *v* (clos_lam body *env*))
                 (apply-k)))
              ((app rator rand)
               (begin
                 (set! *rand^* rand)
                 (set! *env^*  *env*)
                 (set! *k^*    *k*)
                 (set! *k*     (app-rator-k))
                 (set! *e*     rator)
                 (value-of-cps)))
              ((dBlocal rhs body)
               (begin
                 (set! *body^* body)
                 (set! *env^*  *env*)
                 (set! *k^*    *k*)
                 (set! *k*     (local-k))
                 (set! *e*     rhs)
                 (value-of-cps)))
              ((ifte test conseq alt)
               (begin
                 (set! *conseq^* conseq)
                 (set! *alt^*    alt)
                 (set! *env^*    *env*)
                 (set! *k^*      *k*)
                 (set! *k*       (ifte-k))
                 (set! *e*       test)
                 (value-of-cps)))
              ((dBcatch body)
               (begin
                 (set! *e*   body)
                 (set! *env* (envr_extend *k* *env*))
                 (value-of-cps)))
              ((throw continuation result)
               (begin
                 (set! *result^* result)
                 (set! *env^*    *env*)
                 (set! *k*       (throw-k))
                 (set! *e*       continuation)
                 (value-of-cps)))))

;============================================================
; Top-level helpers
;============================================================
(define (run-cps expr)
  (set! *e*   expr)
  (set! *env* (envr_empty))
  (set! *k*   (empty-k))
  (value-of-cps))

;============================================================
; main
;============================================================
(define main
  (lambda ()
    (set! *e*
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
            (expr_const 5))))
    (set! *env* (envr_empty))
    (set! *k*   (empty-k))
    (value-of-cps)))

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