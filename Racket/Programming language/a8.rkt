#lang racket
;=========================================================
;                  Part 1: Tail Positions
;=========================================================
(define empty-k
  (lambda ()
    (let ((once-only #f))
      (lambda (v)
        (if once-only
            (error "You can only invoke the empty continuation once")
            (begin (set! once-only #t) v))))))

;1
(define binary-to-decimal-tails
  (list
   '(match n
      ('() 0)
      ((cons a d) (+ a (* 2 (binary-to-decimal d)))))
   '0
   '(+ a (* 2 (binary-to-decimal d)))))

;2
(define star-tails
  (list
   '(lambda (n) (* m n))
   '(* m n)))

;3
(define times-tails
  (list
   '(match ls
      ('() 1)
      ((cons 0 rest) 0)
      ((cons n rest) (* n (times rest))))
   '1
   '0
   '(* n (times rest))))

;4
(define remove*first9-tails
  (list
   '(match ls
      ('() '())
      ((cons 9 rest) rest)
      ((cons n rest) #:when (number? n)
                     (cons n (remove*first9 rest)))
      ((cons left rest) #:when (equal? left (remove*first9 left))
                        (cons left (remove*first9 rest)))
      ((cons left rest)
       (cons (remove*first9 left) rest)))
   ''()
   'rest
   '(cons n (remove*first9 rest))
   '(cons left (remove*first9 rest))
   '(cons (remove*first9 left) rest)))

;5
(define find-tails
  (list
   '(let ((value (seek env n)))
      (if value (find env value) n))
   '(if value (find env value) n)
   '(find env value)
   'n))

;6

(define fib-tails
  (list
   '((lambda (fib) (fib fib n))
     (lambda (fib n)
       (cond
         ((zero? n) 0)
         ((zero? (sub1 n)) 1)
         (else (+ (fib fib (sub1 n))
                  (fib fib (sub1 (sub1 n))))))))
   '(fib fib n)
   '(cond
      ((zero? n) 0)
      ((zero? (sub1 n)) 1)
      (else (+ (fib fib (sub1 n))
               (fib fib (sub1 (sub1 n))))))
   '0
   '1
   '(+ (fib fib (sub1 n))
       (fib fib (sub1 (sub1 n))))))

;==========================================================
;           Part 2: Continuation-Passing Style
;==========================================================
(struct ds-empty-env () #:transparent)
(struct ds-extend-env (name value env) #:transparent)

;7
(define binary-to-decimal-cps
  (lambda (n k)
    (match n
      ('() (k 0))
      ((cons a d)
       (binary-to-decimal-cps d
                              (lambda (v)
                                (k (+ a (* 2 v)))))))))
;8
(define star-cps
  (lambda (m k)
    (k (lambda (n k2)
         (k2 (* m n))))))

;9
(define times-cps
  (lambda (ls k)
    (match ls
      ('() (k 1))
      ((cons 0 rest) (k 0))
      ((cons n rest)
       (times-cps rest
                  (lambda (v)
                    (k (* n v))))))))

;10
(define times-cps-shortcut
  (lambda (ls k)
    (match ls
      ('() (k 1))
      ((cons 0 rest) 0)
      ((cons n rest)
       (times-cps-shortcut rest
                           (lambda (v)
                             (k (* n v))))))))

;11
(define remove*first9-cps
  (lambda (ls k)
    (match ls
      ('() (k '()))
      ((cons 9 rest) (k rest))
      ((cons n rest) #:when (number? n)
                     (remove*first9-cps rest
                                        (lambda (v)
                                          (k (cons n v)))))
      ((cons left rest)
       (remove*first9-cps left
                          (lambda (left2)
                            (if (equal? left left2)
                                (remove*first9-cps rest
                                                   (lambda (rest2)
                                                     (k (cons left rest2))))
                                (k (cons left2 rest)))))))))
;12
(define cons-cell-count-cps
  (lambda (ls k)
    (match ls
      ((cons a d)
       (cons-cell-count-cps a
                            (lambda (va)
                              (cons-cell-count-cps d
                                                   (lambda (vd)
                                                     (k (add1 (+ va vd))))))))
      (ls (k 0)))))
;13
(define seek-cps
  (lambda (env n k)
    (match env
      ((ds-empty-env) (k #f))
      ((ds-extend-env name value rest)
       (if (eqv? n name)
           (k value)
           (seek-cps rest n k))))))

;14
(define find-cps
  (lambda (env n k)
    (seek-cps env n
              (lambda (value)
                (if value
                    (find-cps env value k)
                    (k n))))))
;15
(define ack-cps
  (lambda (m n k)
    (cond
      ((zero? m) (k (add1 n)))
      ((zero? n) (ack-cps (sub1 m) 1 k))
      (else
       (ack-cps m (sub1 n)
                (lambda (v)
                  (ack-cps (sub1 m) v k)))))))
;16
(define fib-cps
  (lambda (n k)
    ((lambda (fib)
       (fib fib n k))
     (lambda (fib n k)
       (cond
         ((zero? n) (k 0))
         ((zero? (sub1 n)) (k 1))
         (else
          (fib fib (sub1 n)
               (lambda (v1)
                 (fib fib (sub1 (sub1 n))
                      (lambda (v2)
                        (k (+ v1 v2))))))))))))


;17
(define null?-cps
    (lambda (ls k)
      (k (null? ls))))
(define car-cps
    (lambda (pr k)
      (k (car pr))))
(define cdr-cps
    (lambda (pr k)
      (k (cdr pr))))

(define unfold-cps
  (lambda (p f g seed k)
    ((lambda (h)
       (h h seed '() k))
     (lambda (h seed ans k)
       (p seed
          (lambda (stop?)
            (if stop?
                (k ans)
                (f seed
                   (lambda (fv)
                     (g seed
                        (lambda (gv)
                          (h h gv (cons fv ans) k))))))))))))
;18
(define unify-cps
  (lambda (u v env k)
    (cond
      ((eqv? u v) (k env))
      ((string? u) (k (ds-extend-env u v env)))
      ((string? v) (unify-cps v u env k))
      ((and (pair? u) (pair? v))
       (find-cps env (car u)
         (lambda (fu)
           (find-cps env (car v)
             (lambda (fv)
               (unify-cps fu fv env
                 (lambda (env2)
                   (if env2
                       (find-cps env2 (cdr u)
                         (lambda (fu2)
                           (find-cps env2 (cdr v)
                             (lambda (fv2)
                               (unify-cps fu2 fv2 env2 k)))))
                       (k #f)))))))))
      (else (k #f)))))
;19
(define M-cps
  (lambda (f k)
    (k
     (lambda (ls k2)
       (cond
         ((null? ls) (k2 '()))
         (else
          (f (car ls)
             (lambda (v)
               (M-cps f
                      (lambda (mf)
                        (mf (cdr ls)
                            (lambda (rest)
                              (k2 (cons v rest))))))))))))))
;20
(define use-of-M-cps
  (M-cps
   (lambda (n k)
     (k (add1 n)))
   (lambda (mf)
     (mf '(1 2 3 4 5) (empty-k)))))
;=========================================================
;                     brainteasers
;=========================================================
;21
(define strange-cps
  (lambda (x k)
    ((lambda (g k2)
       (k2 (lambda (x k3)
             (g g (lambda (v) (k3 v))))))
     (lambda (g k2)
       (k2 (lambda (x k3)
             (g g (lambda (v) (k3 v))))))
     k)))
;22
(define use-of-strange-cps
  (strange-cps 5
               (lambda (f1)
                 (f1 6
                     (lambda (f2)
                       (f2 7
                           (lambda (strange^)
                             (strange^ 8
                                       (lambda (f3)
                                         (f3 9
                                             (lambda (f4)
                                               (f4 10 (empty-k)))))))))))))
;23
(define why-cps
  (lambda (f k)
    ((lambda (g k2)
       (f (lambda (x k3)
            (g g
               (lambda (gg)
                 (gg x k3))))
          k2))
     (lambda (g k2)
       (f (lambda (x k3)
            (g g
               (lambda (gg)
                 (gg x k3))))
          k2))
     k)))

;24
(define why-cps-cps
  (lambda (f k)
    ((lambda (g k2)
       (g g
          (lambda (h)
            (f (lambda (x k3)
                 (h x k3))
               k2))))
     (lambda (g k2)
       (g g
          (lambda (h)
            (f (lambda (x k3)
                 (h x k3))
               k2))))
     k)))


