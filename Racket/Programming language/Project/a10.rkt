#lang racket
(require rackunit)

;this is the final file this was SIGNIFICANTLY cleaned up from the a10-step11
;which i kept so that my progress could be tracked. I wanted this to be cleaned up to allow
;me easy acess to read a10 and what it does for the interview. Since I am unlikely to remember specifics next week.

;============================================================
;Assignment 10: Defunctionalized CPS Interpreter
;
;This interpreter is the result of fully defunctionalizing
;the CPS interpreter from A9. Environments, closures, and
;continuations are all represented as data structures (structs)
;rather than higher-order functions. No #<procedure> values
;appear anywhere at runtime.
;
;Steps completed:
;  Steps 1-5:  Defunctionalize environments
;  Steps 6-7:  Defunctionalize closures
;  Steps 8-11: Defunctionalize continuations
;============================================================


;============================================================
;Expression Language Structs (unchanged from A9)
;These represent the abstract syntax of the language.
;============================================================
(struct const (value) #:transparent)        ; numeric constant
(struct dBvar (index) #:transparent)        ; de Bruijn variable reference
(struct mult (l r) #:transparent)           ; multiplication
(struct decr (r) #:transparent)             ; decrement by 1
(struct zerop (r) #:transparent)            ; zero? test
(struct dBlam (body) #:transparent)         ; lambda (de Bruijn)
(struct app (rator rand) #:transparent)     ; function application
(struct dBlocal (rhs body) #:transparent)   ; local binding
(struct ifte (test conseq alt) #:transparent) ; if-then-else
(struct dBcatch (body) #:transparent)       ; capture current continuation as dBvar 0
(struct throw (continuation result) #:transparent) ; invoke a captured continuation


;============================================================
;Environment Structs (defunctionalized in steps 2-5)
;
;Environments map de Bruijn indices to values.
;empty-env  : the empty environment (looking anything up is an error)
;extend-env : adds a new binding on top of an existing environment
;============================================================
(struct empty-env () #:transparent)
(struct extend-env (a^ env^) #:transparent)


;============================================================
;Closure Struct (defunctionalized in steps 6-7)
;
;A closure pairs a lambda body with the environment it closed over.
;============================================================
(struct closure-lam (body env) #:transparent)


;============================================================
;Continuation Structs (defunctionalized in steps 8-11)
;
;Each struct represents a "frame" — what to do next after
;the current sub-expression finishes evaluating.
;============================================================
(struct k-empty () #:transparent)                        ; top-level: return the final value
(struct k-mult-left (r^ env^ k^) #:transparent)          ; waiting to evaluate right side of mult
(struct k-mult-right (lv^ k^) #:transparent)             ; have left value, now multiply
(struct k-decr (k^) #:transparent)                       ; decrement the result
(struct k-zerop (k^) #:transparent)                      ; apply zero? to the result
(struct k-ifte (conseq^ alt^ env^ k^) #:transparent)     ; branch on test result
(struct k-app-rator (rand^ env^ k^) #:transparent)       ; have rator, now evaluate rand
(struct k-app-rand (f^ k^) #:transparent)                ; have both, now apply closure
(struct k-local (body^ env^ k^) #:transparent)           ; extend env with rhs value, eval body
(struct k-throw (result^ env^) #:transparent)            ; have the target continuation, eval result
(struct k-throw-result (k2^) #:transparent)              ; resume a captured continuation with a value


;============================================================
;apply-env
;
;Looks up de Bruijn index y in env, passing the result to k.
;Index 0 = most recently bound variable.
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
;apply-closure
;
;Applies a closure to an argument by extending its saved
;environment with the argument and evaluating the body.
;============================================================
(define (apply-closure clos a k)
  (match clos
    [(closure-lam body env)
     (value-of-cps body (extend-env a env) k)]))


;============================================================
;apply-k
;
;The continuation dispatcher. Given a continuation struct k
;and a value v, performs the next step of computation.
;This replaces all higher-order function calls on continuations.
;============================================================
(define (apply-k k v)
  (match k
    ;Base case: nothing left to do, return the final value
    [(k-empty) v]

    ;mult: left side done (v), now evaluate right side
    [(k-mult-left r^ env^ k2^)
     (value-of-cps r^ env^ (mult-right-k v k2^))]

    ;mult: both sides done, multiply and continue
    [(k-mult-right lv^ k2^)
     (apply-k k2^ (* lv^ v))]

    ;decr: result is in, decrement and continue
    [(k-decr k2^)
     (apply-k k2^ (sub1 v))]

    ;zerop: result is in, apply zero? and continue
    [(k-zerop k2^)
     (apply-k k2^ (zero? v))]

    ;ifte: test result is in, branch on it
    [(k-ifte conseq^ alt^ env^ k2^)
     (if v
         (value-of-cps conseq^ env^ k2^)
         (value-of-cps alt^ env^ k2^))]

    ;app: rator evaluated to v, now evaluate rand
    [(k-app-rator rand^ env^ k2^)
     (value-of-cps rand^ env^ (app-rand-k v k2^))]

    ;app: rand evaluated to v, now apply closure f^ to v
    [(k-app-rand f^ k2^)
     (apply-closure f^ v k2^)]

    ;local: rhs evaluated to v, extend env and eval body
    [(k-local body^ env^ k2^)
     (value-of-cps body^ (extend-env v env^) k2^)]

    ;throw: continuation expression evaluated to v (a captured k struct),
    ;now evaluate the result expression under that continuation
    [(k-throw result^ env^)
     (value-of-cps result^ env^ v)]

    ;throw-result: result expression evaluated to v,
    ;resume the captured continuation k2^ with v
    [(k-throw-result k2^)
     (apply-k k2^ v)]))


;============================================================
;Continuation Constructor Helpers (from step 9)
;
;These are thin wrappers that construct continuation structs.
;They exist to match the calling convention used in value-of-cps.
;============================================================
(define (mult-left-k r^ env^ k^)   (k-mult-left r^ env^ k^))
(define (mult-right-k lv^ k^)      (k-mult-right lv^ k^))
(define (decr-k k^)                (k-decr k^))
(define (zerop-k k^)               (k-zerop k^))
(define (ifte-k conseq^ alt^ env^ k^) (k-ifte conseq^ alt^ env^ k^))
(define (app-rator-k rand^ env^ k^) (k-app-rator rand^ env^ k^))
(define (app-rand-k f^ k^)         (k-app-rand f^ k^))
(define (local-k body^ env^ k^)    (k-local body^ env^ k^))
(define (throw-k result^ env^)     (k-throw result^ env^))
(define (throw-result-k k2^)       (k-throw-result k2^))


;============================================================
;value-of-cps
;
;The main CPS interpreter. Evaluates expression e in
;environment env, passing the result to continuation k.
;All recursive calls are in tail position.
;============================================================
(define value-of-cps
  (lambda (e env k)
    (match e
      ;A constant: just pass its value to k
      [(const v)
       (apply-k k v)]

      ;Multiplication: evaluate left side first
      [(mult l r)
       (value-of-cps l env (mult-left-k r env k))]

      ;Decrement: evaluate operand, then decrement
      [(decr r)
       (value-of-cps r env (decr-k k))]

      ;Zero test: evaluate operand, then test
      [(zerop r)
       (value-of-cps r env (zerop-k k))]

      ;If-then-else: evaluate test first, then branch
      [(ifte test conseq alt)
       (value-of-cps test env (ifte-k conseq alt env k))]

      ;Variable lookup: find de Bruijn index in environment
      [(dBvar y)
       (apply-env env y k)]

      ;Lambda: package body + current env into a closure struct
      [(dBlam body)
       (apply-k k (closure-lam body env))]

      ;Application: evaluate rator first, then rand, then apply
      [(app rator rand)
       (value-of-cps rator env (app-rator-k rand env k))]

      ;Local binding: evaluate rhs, extend env, evaluate body
      [(dBlocal rhs body)
       (value-of-cps rhs env (local-k body env k))]

      ;Throw: evaluate the continuation expression first,
      ;then evaluate result under the captured continuation
      [(throw continuation result)
       (value-of-cps continuation env (throw-k result env))]

      ;Catch: bind the current continuation k as dBvar 0,
      ;so the body can capture and invoke it via throw
      [(dBcatch body)
       (value-of-cps body (extend-env k env) k)])))


;============================================================
;Top-level setup
;============================================================

;The empty environment: no bindings
;(struct definition above; kept here for reference in comments)

;The empty continuation: the top-level "return" — just yield the value
(define (empty-k) (k-empty))

;Convenience runner: evaluate expr from scratch
(define (run-cps expr)
  (value-of-cps expr (empty-env) (empty-k)))


;============================================================
;Tests
;============================================================

;Basic constants and arithmetic
(check-equal? (run-cps (const 5)) 5)
(check-equal? (run-cps (mult (const 5) (const 5))) 25)
(check-equal? (run-cps (decr (decr (const 5)))) 3)

;Conditionals
(check-equal?
 (run-cps (ifte (zerop (const 0)) (mult (const 2) (const 2)) (const 3)))
 4)

;Closures and application
(check-equal?
 (run-cps (app (app (dBlam (dBlam (dBvar 1))) (const 6)) (const 5)))
 6)

;Local bindings
(check-equal? (run-cps (dBlocal (const 5) (dBvar 0))) 5)
(check-equal? (run-cps (dBlocal (const 6) (const 4))) 4)
(check-equal? (run-cps (mult (const 5) (dBlocal (const 5) (dBvar 0)))) 25)

;Application with conditional rator
(check-equal?
 (run-cps (app (ifte (zerop (const 4)) (dBlam (dBvar 0)) (dBlam (const 5))) (const 3)))
 5)
(check-equal?
 (run-cps (app (ifte (zerop (const 0)) (dBlam (dBvar 0)) (dBlam (const 5))) (const 3)))
 3)

;throw/catch: basic cases
(check-equal?
 (run-cps (dBcatch (throw (throw (dBvar 0) (const 5)) (const 6))))
 5)
(check-equal?
 (run-cps (dBcatch (throw (const 6) (throw (dBvar 0) (const 5)))))
 5)
(check-equal?
 (run-cps (mult (const 3) (dBcatch (throw (const 5) (throw (dBvar 0) (const 5))))))
 15)

;Short-circuit evaluation (non-terminating branch not taken)
(check-equal?
 (run-cps (ifte (zerop (const 5))
                (app (dBlam (app (dBvar 0) (dBvar 0)))
                     (dBlam (app (dBvar 0) (dBvar 0))))
                (const 4)))
 4)
(check-equal?
 (run-cps (ifte (zerop (const 0))
                (const 4)
                (app (dBlam (app (dBvar 0) (dBvar 0)))
                     (dBlam (app (dBvar 0) (dBvar 0))))))
 4)

;Recursion via self-application (factorial-style)
(check-equal?
 (run-cps
  (app (dBlam (app (app (dBvar 0) (dBvar 0)) (const 2)))
       (dBlam (dBlam (ifte (zerop (dBvar 0))
                           (const 1)
                           (app (app (dBvar 1) (dBvar 1))
                                (decr (dBvar 0))))))))
 1)

;Nested throw/catch
(check-equal?
 (run-cps
  (dBcatch
   (throw
    (dBcatch
     (mult (const 10)
           (throw (dBvar 1)
                  (mult (dBcatch (throw (dBvar 1) (dBvar 0)))
                        (dBcatch (throw (dBvar 1) (dBvar 0)))))))
    (const 3))))
 9)