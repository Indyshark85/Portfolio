#lang racket

;1
(struct toy () #:transparent)
(struct saran (inner) #:transparent)

(struct none () #:transparent)
(struct some (first rest) #:transparent)

(define unwrap
  (lambda (n)
    (cond
      [(toy? n)
       (some n (none))]
      [(saran? n)
       (some n (unwrap (saran-inner n)))])))
;test
(equal?
 (unwrap (toy))
 (some (toy) (none)))

(equal?
 (unwrap (saran (saran (toy))))
 (some (saran (saran (toy)))
       (some (saran (toy))
             (some (toy) (none)))))

(equal?
 (unwrap (saran (saran (saran (toy)))))
 (some (saran (saran (saran (toy))))
       (some (saran (saran (toy)))
             (some (saran (toy))
                   (some (toy) (none))))))

;2
(define countdown
  (lambda (n)
    (cond
      [(zero? n)
       (list 0)]
      [else
       (cons n (countdown (sub1 n)))])))
;test
(equal? (countdown 0) (list 0))
(equal? (countdown 2) (list 2 1 0))
(equal? (countdown 5) (list 5 4 3 2 1 0))

;3
(struct tail () #:transparent)
(struct disc (n rest) #:transparent)
(struct rib (x rest y) #:transparent)

(define mirror
  (lambda (s)
    (cond
      [(tail? s)
       (tail)]
      [(disc? s)
       (disc (disc-n s)
             (mirror (disc-rest s)))]
      [(rib? s)
       (rib (rib-y s)
            (mirror (rib-rest s))
            (rib-x s))])))
 ;test      
(equal?
 (mirror (tail))
 (tail))

(equal?
 (mirror (disc 1 (tail)))
 (disc 1 (tail)))

(equal?
 (mirror (rib 2 (tail) 3))
 (rib 3 (tail) 2))

(equal?
 (mirror
  (disc 1
        (rib 20
             (rib 3
                  (disc 40
                        (rib 5 (tail) 60))
                  70)
             8)))
 (disc 1
       (rib 8
            (rib 70
                 (disc 40
                       (rib 60 (tail) 5))
                 3)
            20)))
    
;4
(define removeB
  (lambda (x b)
    (cond
      [(none? b)
       (none)]
      [(some? b)
       (if (= x (some-first b))
           (removeB x (some-rest b))
           (some (some-first b)
                 (removeB x (some-rest b))))])))
;test
(equal?
 (removeB 2 (some 5 (some 2 (none))))
 (some 5 (none)))

(equal?
 (removeB 2
          (some 5
                (some 2
                      (some 3
                            (some 2
                                  (some 6
                                        (some 1 (none))))))))
 (some 5
       (some 3
             (some 6
                   (some 1 (none))))))

;5
(define repeat-1st
  (lambda (p ls)
    (define helper
      (lambda (ls repeated?)
        (cond
          [(empty? ls)
           '()]
          [(and (not repeated?) (p (car ls)))
           (cons (car ls)
                 (cons (car ls)
                       (helper (cdr ls) #t)))]
          [else
           (cons (car ls)
                 (helper (cdr ls) repeated?))])))
    (helper ls #f)))

;tests
(equal?
 (repeat-1st zero? (list 5 6 3 4 1))
 (list 5 6 3 4 1))

(equal?
 (repeat-1st even? (list 5 2 6 3 4 1))
 (list 5 2 2 6 3 4 1))

(equal?
 (repeat-1st odd? (list 2 4 6 7 8))
 (list 2 4 6 7 7 8))

;6
(struct couple (left right) #:transparent)

(define zip
  (lambda (ls1 ls2)
    (cond
      [(or (empty? ls1) (empty? ls2))
       '()]
      [else
       (cons (couple (car ls1) (car ls2))
             (zip (cdr ls1) (cdr ls2)))])))
;test
(equal?
 (zip (list 1 2 3) (list "a" "b" "c"))
 (list (couple 1 "a")
       (couple 2 "b")
       (couple 3 "c")))

(equal?
 (zip (list 1 2 3 4 5 6) (list "a" "b" "c"))
 (list (couple 1 "a")
       (couple 2 "b")
       (couple 3 "c")))

(equal?
 (zip (list 1 2 3) (list "a" "b" "c" "d" "e"))
 (list (couple 1 "a")
       (couple 2 "b")
       (couple 3 "c")))

;7
(define index-in-bunch
  (lambda (str b)
    (cond
      [(none? b)
       ;found the error syntax
       (error "what you gave me doesnt work bro")]
      [(string=? str (some-first b))
       0]
        [else
         (+ 1( index-in-bunch str (some-rest b)))])))

;test
(equal?
 (index-in-bunch "x"
   (some "x" (some "y" (some "z" (some "x" (some "x" (none)))))))
 0)

(equal?
 (index-in-bunch "x"
   (some "y" (some "z" (some "x" (some "x" (none))))))
 2)

;8
(define append
  (lambda (ls1 ls2)
    (cond
      [(empty? ls1)
       ls2]
      [else
       (cons (car ls1)
             (append (cdr ls1) ls2))])))
;test
(equal?
 (append (list 42 120) (list 1 2 3))
 (list 42 120 1 2 3))

(equal?
 (append (list "a" "b" "c") (list "cat" "dog"))
 (list "a" "b" "c" "cat" "dog"))

(equal?
 (append '() (list 1 2 3))
 (list 1 2 3))

;9
(define reverse
  (lambda (ls)
    (cond
      [(empty? ls)
       '()]
      [else
       (append (reverse (cdr ls))
               (list (car ls)))])))
;test
(equal?
 (reverse (list "apple" 3 "banana"))
 (list "banana" 3 "apple"))

(equal?
 (reverse (list 1))
 (list 1))

(equal?
 (reverse '())
 '())

;10
(define repeat
  (lambda (ls n)
    (cond
      [(zero? n)
       '()]
      [else
       (append ls
               (repeat ls(sub1 n)))])))
;test
(equal?
 (repeat (list 4 8 11) 4)
 (list 4 8 11 4 8 11 4 8 11 4 8 11))

(equal?
 (repeat (list "a" "b") 0)
 '())

(equal?
 (repeat '() 5)
 '())

;11
(define mirrored?
  (lambda (t1 t2)
    (cond
      [(and (integer? t1) (integer? t2))
       (= t1 t2)]
      [(or (integer? t1) (integer? t2))
           #f]
      [else
       (and (mirrored? (couple-left t1) (couple-right t2))
            (mirrored? (couple-right t1)(couple-left t2)))])))
;test
(equal? (mirrored? 1 1) #t)
(equal? (mirrored? 1 2) #f)
(equal? (mirrored? (couple 1 2) (couple 2 1)) #t)
(equal? (mirrored? 1 (couple 2 1)) #f)
(equal?
 (mirrored? (couple (couple 1 3) 2)
            (couple 2 (couple 3 1)))
 #t)
(equal?
 (mirrored? (couple 1 (couple 3 2))
            (couple (couple 2 3) 1))
 #t)
(equal?
 (mirrored? (couple (couple 1 3) 2)
            (couple (couple 2 3) 1))
 #f)

;12 - I hate this language it breaks all coding norms >:(
(struct branch (x y z) #:transparent)

(define total-wood
  (lambda (w)
    (cond
      [(none? w)
       0]
      [else
       (+ (branch-x w)
          (total-lawn (branch-y w))
          (total-wood (branch-z w)))])))

;helper func
(define total-lawn
  (lambda (l)
    (cond
      [(none? l)
       0]
      [else
       (+ (total-wood (branch-y l))
          (total-lawn (branch-z l)))])))


;test
(equal?
 (total-wood (branch 3 (none)
                     (branch 4 (none)
                             (branch 5 (none) (none)))))
 12)

(equal?
 (total-wood (branch 3 (none)
                     (branch 4
                             (branch 5 (none) (none))
                             (none))))
 7)

(equal?
 (total-wood (branch 3
                     (branch 5 (none) (none))
                     (branch 4 (none) (none))))
 7)

(equal?
 (total-wood (branch 3
                     (branch 5
                             (branch 4 (none) (none))
                             (none))
                     (none)))
 7)

;13
(cons (cons "w" (cons "x" '()))
      (cons "y"
            (cons (cons "z" '())
                  '())))

;test
(equal?
 (list (list "w" "x") "y" (list "z"))
 (cons (cons "w" (cons "x" '()))
       (cons "y"
             (cons (cons "z" '())
                   '()))))

;14
(define binary->natural
  (lambda (bits)
    (cond
      [(empty? bits)
       0]
      [else
       (+ (car bits)
          (* 2 (binary->natural (cdr bits))))])))

;test
(equal? (binary->natural '()) 0)
(equal? (binary->natural (list 0 0 1)) 4)
(equal? (binary->natural (list 0 0 1 1)) 12)
(equal? (binary->natural (list 1 1 1 1)) 15)
(equal? (binary->natural (list 1 0 1 0 1)) 21)
(equal? (binary->natural (list 1 1 1 1 1 1 1 1 1 1 1 1 1)) 8191)

;15
(define append-map
  (lambda (f ls)
    (cond
      [(empty? ls)
       '()]
      [else
       (append (f (car ls))
               (append-map f (cdr ls)))])))

;test
(equal? (append-map (lambda (x) (list x x))
                    '(1 2 3))
        '(1 1 2 2 3 3))

(equal? (append-map (lambda (x) '())
                    '(1 2 3))
        '())

(equal? (append-map (lambda (x) (list x))
                    '())
        '())

(equal? (append-map (lambda (x) (list x (+ x 1)))
                    '(1 3 5))
        '(1 2 3 4 5 6))


;16
(define remove-last
  (lambda (ls)
    (cond
      [(empty? (cdr ls))
       '()]
      [else
       (cons (car ls)
             (remove-last (cdr ls)))])))
      
;test
(equal?
 (remove-last (list 2 6 3 8))
 (list 2 6 3))

(equal?
 (remove-last (list 1))
 '())

;17 - thus begins my confusion and needing to look things up 
(define powerset
  (lambda (ls)
    (cond
      [(empty? ls)
       (list '())]
      [else
       (append (powerset (cdr ls))
               (map (lambda (s)
                      (cons (car ls) s))
                    (powerset (cdr ls))))])))

;test
(equal?
 (powerset '())
 (list '()))

(equal?
 (powerset '(1))
 (list '() '(1)))

(equal?
 (powerset '(1 2))
 (list '() '(2) '(1) '(1 2)))

;18 - nothing like feeling dumb in A1 (not a good look)
(define cartesian-product
  (lambda (sets)
    (cond
      [(empty? sets)
       (list '())]
      [else
       (append-map
        (lambda (x)
          (map (lambda (tuple)
                 (cons x tuple))
               (cartesian-product (cdr sets))))
        (car sets))])))

;test
(equal?
 (cartesian-product '())
 (list '()))

(equal?
 (cartesian-product (list (list 1 2)))
 (list (list 1) (list 2)))

(equal?
 (cartesian-product (list (list 5 4) (list 3 2 1)))
 (list (list 5 3) (list 5 2) (list 5 1)
       (list 4 3) (list 4 2) (list 4 1)))


;19 - huh
(define quine
  '((lambda (x)
      (list x (list 'quote x)))
    '(lambda (x)
       (list x (list 'quote x)))))

;test

(equal? quine (eval quine))
(equal? quine (eval (eval quine)))

