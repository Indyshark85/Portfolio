#lang racket
(require rackunit)
(require "parenthec.rkt")
;this is step 8 just renamed for the remainder of the assignment

; Step 8: Trampoline — convert all label calls to pc assignments
;   - Every call to a define-label becomes (set! pc label-name)
;   - apply-k's (empty) case calls (dismount-trampoline *k*)
;     where *k* holds the dismount continuation installed by mount-trampoline
;   - empty-k stores a special kt variant that will trigger dismount
;   - mount-trampoline drives the loop; main sets up registers and mounts
;   - printf prints the final value at the end of main
;

; For my Interigation
; Key rule for empty-k / dismount:
;   mount-trampoline takes a 1-arg constructor, a register, and the pc.
;   It does: (set! reg (constructor dismount-k)) then loops calling (pc).
;   So empty-k must be a 1-arg function that wraps the dismount continuation.
;   We represent it in the kt union as (kt_empty) but we store the actual
;   Racket call/cc continuation in *k* via mount-trampoline.
;   When apply-k hits kt_empty, it calls (dismount-trampoline *k*) — but
;   *k* is still the kt_empty tag at that point.  Instead, we keep the
;   dismount continuation in a dedicated register *dismount* and call it.

;============================================================
; Program Counter
;============================================================
(define-program-counter pc)

;============================================================
; Global Registers
;============================================================
(define-registers *e* *env* *k* *v* *y*
  *clos* *a*
  *r^* *env^* *k^*
  *lv^* *f^* *rand^* *body^*
  *conseq^* *alt^* *result^* *k2^*
  *dismount*)

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
; Continuations
;   empty now takes the dismount continuation as its payload
;============================================================
(define-union kt
  (empty dismount)
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
; Continuation Constructor Helpers
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
; empty-k: takes the dismount continuation injected by mount-trampoline
(define (empty-k dismount) (kt_empty dismount))

;============================================================
; apply-env — label invocations become pc assignments
;============================================================
(define-label apply-env
  (union-case *env* envr
              ((empty)
               (error 'value-of "unbound variable"))
              ((extend a^ env^)
               (if (zero? *y*)
                   (begin
                     (set! *v* a^)
                     (set! pc apply-k))
                   (begin
                     (set! *env* env^)
                     (set! *y*   (sub1 *y*))
                     (set! pc apply-env))))))

;============================================================
; apply-closure
;============================================================
(define-label apply-closure
  (union-case *clos* clos
              ((lam body env)
               (begin
                 (set! *e*   body)
                 (set! *env* (envr_extend *a* env))
                 (set! pc value-of-cps)))))

;============================================================
; apply-k
;============================================================
(define-label apply-k
  (union-case *k* kt
              ((empty dismount)
               ; dismount the trampoline — IT IS FINISHED
               (dismount-trampoline dismount))
              ((mult-left r^ env^ k2^)
               (begin
                 (set! *lv^* *v*)
                 (set! *k^*  k2^)
                 (set! *k*   (mult-right-k))
                 (set! *e*   r^)
                 (set! *env* env^)
                 (set! pc value-of-cps)))
              ((mult-right lv^ k2^)
               (begin
                 (set! *v* (* lv^ *v*))
                 (set! *k* k2^)
                 (set! pc apply-k)))
              ((decr k2^)
               (begin
                 (set! *v* (sub1 *v*))
                 (set! *k* k2^)
                 (set! pc apply-k)))
              ((zerop k2^)
               (begin
                 (set! *v* (zero? *v*))
                 (set! *k* k2^)
                 (set! pc apply-k)))
              ((ifte conseq^ alt^ env^ k2^)
               (if *v*
                   (begin
                     (set! *e*   conseq^)
                     (set! *env* env^)
                     (set! *k*   k2^)
                     (set! pc value-of-cps))
                   (begin
                     (set! *e*   alt^)
                     (set! *env* env^)
                     (set! *k*   k2^)
                     (set! pc value-of-cps))))
              ((app-rator rand^ env^ k2^)
               (begin
                 (set! *f^*  *v*)
                 (set! *k^*  k2^)
                 (set! *k*   (app-rand-k))
                 (set! *e*   rand^)
                 (set! *env* env^)
                 (set! pc value-of-cps)))
              ((app-rand f^ k2^)
               (begin
                 (set! *clos* f^)
                 (set! *a*    *v*)
                 (set! *k*    k2^)
                 (set! pc apply-closure)))
              ((local body^ env^ k2^)
               (begin
                 (set! *e*   body^)
                 (set! *env* (envr_extend *v* env^))
                 (set! *k*   k2^)
                 (set! pc value-of-cps)))
              ((throw result^ env^)
               (begin
                 (set! *e*   result^)
                 (set! *env* env^)
                 (set! *k*   *v*)
                 (set! pc value-of-cps)))
              ((throw-result k2^)
               (begin
                 (set! *k* k2^)
                 (set! pc apply-k)))))

;============================================================
; value-of-cps
;============================================================
(define-label value-of-cps
  (union-case *e* expr
              ((const v)
               (begin
                 (set! *v* v)
                 (set! pc apply-k)))
              ((dBvar y)
               (begin
                 (set! *y* y)
                 (set! pc apply-env)))
              ((mult l r)
               (begin
                 (set! *r^*   r)
                 (set! *env^* *env*)
                 (set! *k^*   *k*)
                 (set! *k*    (mult-left-k))
                 (set! *e*    l)
                 (set! pc value-of-cps)))
              ((decr r)
               (begin
                 (set! *k^* *k*)
                 (set! *k*  (decr-k))
                 (set! *e*  r)
                 (set! pc value-of-cps)))
              ((zerop r)
               (begin
                 (set! *k^* *k*)
                 (set! *k*  (zerop-k))
                 (set! *e*  r)
                 (set! pc value-of-cps)))
              ((dBlam body)
               (begin
                 (set! *v* (clos_lam body *env*))
                 (set! pc apply-k)))
              ((app rator rand)
               (begin
                 (set! *rand^* rand)
                 (set! *env^*  *env*)
                 (set! *k^*    *k*)
                 (set! *k*     (app-rator-k))
                 (set! *e*     rator)
                 (set! pc value-of-cps)))
              ((dBlocal rhs body)
               (begin
                 (set! *body^* body)
                 (set! *env^*  *env*)
                 (set! *k^*    *k*)
                 (set! *k*     (local-k))
                 (set! *e*     rhs)
                 (set! pc value-of-cps)))
              ((ifte test conseq alt)
               (begin
                 (set! *conseq^* conseq)
                 (set! *alt^*    alt)
                 (set! *env^*    *env*)
                 (set! *k^*      *k*)
                 (set! *k*       (ifte-k))
                 (set! *e*       test)
                 (set! pc value-of-cps)))
              ((dBcatch body)
               (begin
                 (set! *e*   body)
                 (set! *env* (envr_extend *k* *env*))
                 (set! pc value-of-cps)))
              ((throw continuation result)
               (begin
                 (set! *result^* result)
                 (set! *env^*    *env*)
                 (set! *k*       (throw-k))
                 (set! *e*       continuation)
                 (set! pc value-of-cps)))))

;============================================================
; main — mounts trampoline, then prints result
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
    (set! pc value-of-cps)
    (mount-trampoline empty-k *k* pc)
    (printf "Fact 5: ~s\n" *v*)))

(main)