#lang racket
; listofnumbers ::= '() | (cons Number listofnumbers)
(define lon (cons 12 (cons 34 (cons 56 ( cons 78 '())))))

; bunchofnumbers ::= (none) | (some Number bunchofnumbers
(define my-bon (some 12 (some 34 (some 56 ( some 78 (none))))))

;sum-bunch : bunchofnumbers -> number

(define sum-bunch
  (lambda(bon)
    (match bon
      ((none) 0)
      ((some first rest) (+ first (sum-bunch rest))))))
;sum-list : listofnumbers -> number

(define sum-list
  (lambda(lon)
    (match lon
      ('() 0)
      ((some first rest) (+ first (sum-list rest))))))

; Nat ::= (z) | (s Nat)
;(define my5 (s(s(s(s(s(z)))))))

; Nest ::= (toy) | (saran Nest)

(struct toy () #:transparent)
;toy : -> Nest

(struct saran (inner) #:transparent)
;saran : nest -> nest
(define nest-5 (saran(saran(saran(saran(saran(toy)))))))