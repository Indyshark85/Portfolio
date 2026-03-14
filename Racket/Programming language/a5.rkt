#lang racket


;sorry this looks like a mess the formatting of the assingment really confused me
;======================================================================
;           Part 1: Defunctionalizing filter and map
;======================================================================

(define fn-apply-closure
  (lambda (f x) (f x)))

(define fn-filter
  (lambda (f)
    (lambda (v)
      (match v
        ('() '())
        ((cons x y)
         (if (fn-apply-closure f x)
             (cons x ((fn-filter f) y))
             ((fn-filter f) y)))
        (_ (error "not list"))))))

(define fn-map
  (lambda (f)
    (lambda (v)
      (match v
        ('() '())
        ((cons x y)
         (cons (fn-apply-closure f x)
               ((fn-map f) y)))
        (_ (error "not list"))))))

;helpers
(define fn-closure-<0
  (lambda () (lambda (x) (< 0 x))))

(define fn-closure-*10
  (lambda () (lambda (x) (* 10 x))))

(define fn-closure-inner
  (lambda (x)
    (lambda (y) (* x y))))

(define fn-closure-outer
  (lambda ()
    (lambda (x)
      ((fn-map (fn-closure-inner x))
       (cons 1 (cons 2 (cons 3 (cons 4 '()))))))))


;DS section
(struct ds-closure-<0 () #:transparent)
(struct ds-closure-*10 () #:transparent)
(struct ds-closure-outer () #:transparent)
(struct ds-closure-inner (x) #:transparent)

;; (define ds-apply-closure
;;   (lambda (f x)
;;     (match f
;;       ((ds-closure-<0) (< 0 x))
;;       ((ds-closure-*10) (* 10 x))
;;       ((ds-closure-outer)
;;        ((ds-map (ds-closure-inner x))
;;         (cons 1 (cons 2 (cons 3 (cons 4 '()))))))
;;       ((ds-closure-inner x0) (* x0 x)))))

(define ds-filter
  (lambda (f)
    (lambda (v)
      (match v
        ('() '())
        ((cons x y)
         (if (ds-apply-closure f x)
             (cons x ((ds-filter f) y))
             ((ds-filter f) y)))
        (_ (error "not list"))))))

(define ds-map
  (lambda (f)
    (lambda (v)
      (match v
        ('() '())
        ((cons x y)
         (cons (ds-apply-closure f x)
               ((ds-map f) y)))
        (_ (error "not list"))))))

;======================================================================
;     Part 2: Adding useful functions to the initial environment
;======================================================================

;provided code
(struct ifte (bool true false) #:transparent)
(struct app (rator rand) #:transparent)
(struct lam (param body) #:transparent)

(struct empty-env () #:transparent)
(struct extend-env (name value rest) #:transparent)

(define apply-env
  (lambda (env n)
    (match env
      ((empty-env) (error "unbound variable" n))
      ((extend-env name value rest)
       (if (string=? name n) value (apply-env rest n))))))

;this is the interpreter
(define fn-value-of
  (lambda (e env)
    (match e
      (e #:when (number? e) e)
      (e #:when (boolean? e) e)
      (e #:when (string? e) (apply-env env e))
      
      ((ifte e0 e1 e2)
       (if (fn-value-of e0 env)
           (fn-value-of e1 env)
           (fn-value-of e2 env)))
      
      ((app rator rand)
       ((fn-value-of rator env)
        (fn-value-of rand env)))
      
      ((lam param body) #:when (string? param)
       (lambda (argument)
         (fn-value-of body (extend-env param argument env)))))))
 

(define fn-init-env
  (extend-env "+"     (lambda (x) (lambda (y) (+ x y)))
  (extend-env "-"     (lambda (x) (lambda (y) (- x y)))
  (extend-env "*"     (lambda (x) (lambda (y) (* x y)))
  (extend-env "<"     (lambda (x) (lambda (y) (< x y)))
  (extend-env "zero?"  zero?
  (extend-env "null"   '()
  (extend-env "null?"  null?
  (extend-env "cons"  (lambda (x) (lambda (y) (cons x y)))
  (extend-env "cons?"  pair?
  (extend-env "car"    car
  (extend-env "cdr"    cdr
  (extend-env "filter" fn-filter
  (extend-env "map"    fn-map
  (empty-env)))))))))))))))

;====================================================
;Part 3: Defunctionalizing the initial environment
;====================================================

;fn-apply-closure alr exists

;replace lam closures
(define fn-closure-lam
  (lambda (param body env)
    (lambda (argument)
      (fn-value-of body
                   (extend-env param argument env)))))

(define fn-closure-add         (lambda () (lambda (x) (fn-closure-add-to x))))
(define fn-closure-add-to      (lambda (x) (lambda (y) (+ x y))))

(define fn-closure-sub         (lambda () (lambda (x) (fn-closure-sub-from x))))
(define fn-closure-sub-from    (lambda (x) (lambda (y) (- x y))))

(define fn-closure-mul         (lambda () (lambda (x) (fn-closure-mul-by x))))
(define fn-closure-mul-by      (lambda (x) (lambda (y) (* x y))))

(define fn-closure-lt          (lambda () (lambda (x) (fn-closure-lt-with x))))
(define fn-closure-lt-with     (lambda (x) (lambda (y) (< x y))))

(define fn-closure-zero?       (lambda () (lambda (x) (zero? x))))

(define fn-closure-null?       (lambda () (lambda (x) (null? x))))

(define fn-closure-cons        (lambda () (lambda (x) (fn-closure-cons-with x))))
(define fn-closure-cons-with   (lambda (x) (lambda (y) (cons x y))))

(define fn-closure-consp       (lambda () (lambda (x) (pair? x))))

(define fn-closure-car         (lambda () (lambda (x) (car x))))

(define fn-closure-cdr         (lambda () (lambda (x) (cdr x))))

(define fn-closure-map         (lambda () (lambda (f) (fn-closure-map-with f))))
(define fn-closure-map-with    (lambda (f) (lambda (v) ((fn-map f) v))))

(define fn-closure-filter      (lambda () (lambda (f) (fn-closure-filter-with f))))
(define fn-closure-filter-with (lambda (f) (lambda (v) ((fn-filter f) v))))

;ds version of above
(struct ds-closure-lam (param body env) #:transparent)

(struct ds-closure-add () #:transparent)
(struct ds-closure-add-to (x) #:transparent)

(struct ds-closure-sub () #:transparent)
(struct ds-closure-sub-from (x) #:transparent)

(struct ds-closure-mul () #:transparent)
(struct ds-closure-mul-by (x) #:transparent)

(struct ds-closure-lt () #:transparent)
(struct ds-closure-lt-with (x) #:transparent)

(struct ds-closure-zero? () #:transparent)
(struct ds-closure-null? () #:transparent)

(struct ds-closure-cons () #:transparent)
(struct ds-closure-cons-with (x) #:transparent)

(struct ds-closure-consp () #:transparent)
(struct ds-closure-car () #:transparent)
(struct ds-closure-cdr () #:transparent)

(struct ds-closure-map () #:transparent)
(struct ds-closure-map-with (f) #:transparent)

(struct ds-closure-filter () #:transparent)
(struct ds-closure-filter-with (f) #:transparent)

(define ds-apply-closure
  (lambda (f x)
    (match f
      ;Part 1 stuff
      ((ds-closure-<0) (< 0 x))
      ((ds-closure-*10) (* 10 x))
      ((ds-closure-outer)
       ((ds-map (ds-closure-inner x))
        (cons 1 (cons 2 (cons 3 (cons 4 '()))))))
      ((ds-closure-inner x0) (* x0 x))

      ;interpreter closures
      ((ds-closure-lam param body env)
       (ds-value-of body (extend-env param x env)))

      ((ds-closure-add) (ds-closure-add-to x))
      ((ds-closure-add-to x0) (+ x0 x))

      ((ds-closure-sub) (ds-closure-sub-from x))
      ((ds-closure-sub-from x0) (- x0 x))

      ((ds-closure-mul) (ds-closure-mul-by x))
      ((ds-closure-mul-by x0) (* x0 x))

      ((ds-closure-lt) (ds-closure-lt-with x))
      ((ds-closure-lt-with x0) (< x0 x))

      ((ds-closure-zero?) (zero? x))
      ((ds-closure-null?) (null? x))

      ((ds-closure-cons) (ds-closure-cons-with x))
      ((ds-closure-cons-with x0) (cons x0 x))

      ((ds-closure-consp) (pair? x))
      ((ds-closure-car) (car x))
      ((ds-closure-cdr) (cdr x))

      ((ds-closure-map) (ds-closure-map-with x))
      ((ds-closure-map-with f) ((ds-map f) x))

      ((ds-closure-filter) (ds-closure-filter-with x))
      ((ds-closure-filter-with f) ((ds-filter f) x)))))

(define ds-value-of
  (lambda (e env)
    (match e
      (e #:when (number? e) e)
      (e #:when (boolean? e) e)
      (e #:when (string? e) (apply-env env e))

      ((ifte e0 e1 e2)
       (if (ds-value-of e0 env)
           (ds-value-of e1 env)
           (ds-value-of e2 env)))

      ((app rator rand)
       (ds-apply-closure
        (ds-value-of rator env)
        (ds-value-of rand env)))

      ((lam param body)
       (ds-closure-lam param body env)))))

(define ds-init-env
  (extend-env "+"     (ds-closure-add)
  (extend-env "-"     (ds-closure-sub)
  (extend-env "*"     (ds-closure-mul)
  (extend-env "<"     (ds-closure-lt)
  (extend-env "zero?" (ds-closure-zero?)
  (extend-env "null"  '()
  (extend-env "null?" (ds-closure-null?)
  (extend-env "cons"  (ds-closure-cons)
  (extend-env "cons?" (ds-closure-consp)
  (extend-env "car"   (ds-closure-car)
  (extend-env "cdr"   (ds-closure-cdr)
  (extend-env "filter" (ds-closure-filter)
  (extend-env "map"    (ds-closure-map)
  (empty-env)))))))))))))))


