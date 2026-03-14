#lang racket
;took bad advice to change this to #lang racket/base changed back and works now

(struct lam (param body) #:transparent)
(struct app (rator rand) #:transparent)
(struct local (lhs rhs body) #:transparent)
(struct ifte (bool true false) #:transparent)

;attempt 4 what changed ds-apply-env, value1, value1-program, value2, value2-program, ds-apply-closure
;BRO WHAT IS THE ISSUE OMG
;are you kidding it was parenthesis this was not fun



;==========================================;
;Part 1: Two Ways to Represent Environments;
;==========================================;

;1

(struct environments
  (empty-env extend-env apply-env)
  #:transparent)

; fn-empty-env : -> [String -> Value]
(define fn-empty-env
  (lambda ()
    (lambda (name)
      (error "unbound variable" name))))

 
; fn-extend-env : String Value [String -> Value] -> [String -> Value]
(define fn-extend-env
  (lambda(name val env)
    (lambda (q)
      (if (string=? q name)
          val
          (env q)))))
 
; fn-apply-env : [String -> Value] String -> Value
(define fn-apply-env
  (lambda (env name)
    (env name)))
 
; fn-environments : [Environments [String -> Value]]
(define fn-environments
  (environments fn-empty-env
                fn-extend-env
                fn-apply-env))

(define env1
  (fn-extend-env "b" 5
                 (fn-extend-env "a" 1
                                (fn-extend-env "b" 2
                                               (fn-extend-env "c" 3
                                                              (fn-empty-env))))))

;2

; ds-empty-env : -> DsEnv
(struct ds-empty-env() #:transparent)
 
; ds-extend-env : String Value DsEnv -> DsEnv
(struct ds-extend-env(name val env) #:transparent)
 
; ds-apply-env : DsEnv String -> Value
(define ds-apply-env
  (lambda (env name)
    (cond
      [(ds-empty-env? env)
       (error "unbound variable" name)] ; was worried the 
      [(ds-extend-env? env)
       (if (string=? name (ds-extend-env-name env))
           (ds-extend-env-val env)
           (ds-apply-env (ds-extend-env-env env) name))])))
       
 
; ds-environments : [Environments DsEnv]
(define ds-environments
  (environments ds-empty-env
                ds-extend-env
                ds-apply-env))

;3
(define value1
  (lambda (e envs env)
    (match envs
      [(environments empty-env extend-env apply-env)
       (cond
         [(number? e) e]
         [(boolean? e) e]
         [(string? e) (apply-env env e)]

         [(lam? e)
          (lambda (arg)
            (value1 (lam-body e)
                    envs
                    (extend-env (lam-param e) arg env)))]

         [(app? e)
          ((value1 (app-rator e) envs env)
           (value1 (app-rand e) envs env))]

         [(local? e)
          (value1 (local-body e)
                  envs
                  (extend-env (local-lhs e)
                              (value1 (local-rhs e) envs env)
                              env))]

         [(ifte? e)
          (if (value1 (ifte-bool e) envs env)
              (value1 (ifte-true e) envs env)
              (value1 (ifte-false e) envs env))])])))


;4

(define value1-program
  (lambda (e envs)
    (match envs
      [(environments empty-env extend-env apply-env)
       (value1 e envs
               (extend-env "+"
                           (lambda (x) (lambda (y) (+ x y)))
                           (extend-env "-"
                                       (lambda (x) (lambda (y) (- x y)))
                                       (extend-env "zero?"
                                                   (lambda (x) (zero? x))
                                                   (empty-env)))))])))
                         

;==========================================;
;Part 2: Two Ways to Represent Closures    ;
;==========================================;


;5

; value2 : Expr DsEnv -> Value
(define value2
  (lambda (e clos env)
    (match clos
      [(closures closure-lam closure-add closure-sub closure-zero? apply-closure)
       (match e
         [(? number?) e]
         [(? boolean?) e]
         [(? string?) (ds-apply-env env e)]

         [(lam param body)
          (closure-lam param body env)]

         [(app rator rand)
          (apply-closure
           (value2 rator clos env)
           (value2 rand clos env))]

         [(local lhs rhs body)
          (value2 body
                  clos
                  (ds-extend-env lhs
                                 (value2 rhs clos env)
                                 env))]

         [(ifte b t f)
          (if (value2 b clos env)
              (value2 t clos env)
              (value2 f clos env))])])))

 
; value2-program : Expr -> Value
(define value2-program
  (lambda (e clos)
    (match clos
      [(closures closure-lam closure-add closure-sub closure-zero? apply-closure)
       (value2 e clos
               (ds-extend-env "+"
                              (closure-add)
                              (ds-extend-env "-"
                                             (closure-sub)
                                             (ds-extend-env "zero?"
                                                            (closure-zero?)
                                                            (ds-empty-env)))))])))


(struct closures
  (closure-lam closure-add closure-sub closure-zero? apply-closure)
  #:transparent)

;6
; fn-closure-lam : String Expr DsEnv -> [Value -> Value]
(define fn-closure-lam
  (lambda (param body env)
    (lambda (arg)
      (value2 body
              fn-closures
              (ds-extend-env param arg env)))))

; fn-closure-add : -> [Value -> Value]
(define fn-closure-add
  (lambda ()
    (lambda (x)
      (lambda (y) (+ x y)))))

; fn-apply-closure : [Value -> Value] Value -> Value
(define fn-apply-closure
  (lambda (clos arg)
    (clos arg)))

; fn-closure-sub : -> [Value -> Value]
(define fn-closure-sub
  (lambda ()
    (lambda (x)
      (lambda (y) (- x y)))))

; fn-closure-zero? : -> [Value -> Value]
(define fn-closure-zero?
  (lambda ()
    (lambda (x) (zero? x))))

; fn-closures : [Closures [Value -> Value]]
(define fn-closures
  (closures fn-closure-lam
            fn-closure-add
            fn-closure-sub
            fn-closure-zero?
            fn-apply-closure))


;7

;removed the value 2 from here and moved them up top to see if the autograder passes
;this was one of the issues

;8
(struct ds-closure-add-to (x) #:transparent)
(struct ds-closure-sub-from (x) #:transparent)

; ds-closure-lam : String Expr DsEnv -> DsClosure
(struct ds-closure-lam (param body env) #:transparent)
 
; ds-closure-add : -> DsClosure
(struct ds-closure-add () #:transparent)
 
; ds-closure-sub : -> DsClosure
(struct ds-closure-sub () #:transparent)
 
; ds-closure-zero? : -> DsClosure
(struct ds-closure-zero? () #:transparent)
 
; ds-apply-closure : DsClosure Value -> Value
(define ds-apply-closure
  (lambda (clos arg)
    (match clos
      [(ds-closure-lam param body env)
       (value2 body
               ds-closures
               (ds-extend-env param arg env))]

      [(ds-closure-add)
       (ds-closure-add-to arg)]

      [(ds-closure-add-to x)
       (+ x arg)]

      [(ds-closure-sub)
       (ds-closure-sub-from arg)]

      [(ds-closure-sub-from x)
       (- x arg)]

      [(ds-closure-zero?)
       (zero? arg)])))
 
; ds-closures : [Closures DsClosure]
(define ds-closures
  (closures ds-closure-lam
            ds-closure-add
            ds-closure-sub
            ds-closure-zero?
            ds-apply-closure))

