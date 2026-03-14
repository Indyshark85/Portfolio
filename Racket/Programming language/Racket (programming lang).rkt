#lang racket
(require Rackunit)
(struct point (x y) #:transparent)

;norm : Point -> Number
;tale a point and return its distance from the origin
;; (define norm
;;   (lambda (p)
;;      (sqrt (+ (*(point-x p)(point-x p))
;;                (* (point-y p) (point-y p))))))
;; (check-equal? (norm(point(3 4)) 5))
;; (check-equal? (norm(point(5 12)) 13))


;; (define norm
;;   (lambda (p)
;;     (let ((x(point-x p))
;;           (y (point-y p))
;;           (square (lambda (n) (*n n))))
;;       (sqrt (+ (square x)
;;                (square y))))))
(define norm
  (lambda (p)
    (let ((square (lambda (n) (* n n))))
      (match p
        [(point x y)
         (sqrt (+ (square x)
                  (square y)))]))))

(begin
  (check-equal? (norm (point 3 4)) 5)
  (check-equal? (norm (point 5 12)) 13))


(define (distance p q)
  (let ((square (lambda (n) (* n n))))
    (match p
      [(point x1 y1)
       (match q
         [(point x2 y2)
          (sqrt (+ (square (- x1 x2))
                   (square (- y1 y2))))])])))

(check-equal? (distance (point 10 20) (point 13 24)) 5)
;general syntax for let:
;   (let ((NAME VALUE) (NAME VLAUE) (NAME VALUE)) ANSWER)
;general syntax for match:
;   (match SCRUTINEE (PATTERN ANSWER))
(struct lit (n) #:transparent)
(struct add (l r) #:transparent)
(struct sub (l r) #:transparent)
(struct mul (l r) #:transparent)

(add (lit 10) (mul (lit 20) (lit 30)))
; Expr :: =  Number
;         | (add Expr Expr)
;         | (sub Expr Expr)
;         | (mul Expr Expr)

;vlaue-of : Expr -> Number
(define value-of
  (lambda (e)
    (match e
    (n #:when(number? n) n)
      ;natural recursion
    ((add l r) (+  (value-of l)   (value-of r) ) )
    ((sub l r) (-  (value-of l)   (value-of r) ) )
    ((mul l r) (*  (value-of l)   (value-of r) )))))
    
(check-equal? (value-of (add 10 (mul 20 30)))
                       (+ 10(* 20 30)))
(check-equal? (value-of 678) 678)


(struct z () #:transparent)
(struct s (n) #:transparent)
; Nat (natural number) ::= (z) | (s Nat)

(define my0 (z))
(define my1 (s my0))
(define my2 (s my1))
(define my3 (s my2))
(define my4 (s my3))
(define my5 (s my4))

; plus : Nat Nat -> Nat
(define plus
  (lambda (n m)
    (match n
      ((z) m) 
      ((s k) (s (plus k m)  )   ))))
(check-equal? (plus my2 my3) my5)


;; nat  -> number : Nat -> number
(define nat -> number
  (lambda (n)
    (match n
      ((z) 0)
      ((s k) (+ 1 (nat->number k) )))))
(check-equal?(nat -> number my5) 5)

