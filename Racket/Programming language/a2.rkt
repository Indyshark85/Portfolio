#lang racket
(require rackunit)

(struct rectangles
  (from-center from-corners area contains? scale)
  #:transparent)
;======================================================================================================
;                                             1
;======================================================================================================
(define includes-edge?
  (lambda (rectangle)
    (match rectangle
      [(rectangles from-center from-corners area contains? scale)
       (let ([r (from-corners 0 0 2 2)])
         (contains? r 0 1))])))

;======================================================================================================
;                                             2
;======================================================================================================
(struct center (x y w h) #:transparent)

(define center-rect-from-center
  (lambda (cx cy w h)
    (center cx cy w h)))

(define center-rect-from-corners
  (lambda (x1 y1 x2 y2)
    (let
        ([centerX (/(+ x1 x2) 2)]
         [centerY (/(+ y1 y2) 2)]
         [width (abs ( - x2 x1))]
         [height (abs (- y2 y1))])
      (center centerX centerY width height))))

(define center-rect-area
  (lambda (c)
    (let
        ([w (center-w c)]
         [h (center-h c)])
      (* w h))))

(define center-rect-contains?
  (lambda (c px py)
    (let ([x (center-x c)]
          [y (center-y c)]
          [w (center-w c)]
          [h (center-h c)])
      (and (<= (- x (/ w 2)) px)
           (<= px (+ x (/ w 2)))
           (<= (- y (/ h 2)) py)
           (<= py (+ y (/ h 2)))))))

;tests (because this language is so dumb)
(define r (center 1 1 2 2))
(center-rect-contains? r 0 1) ; #t (edge)
(center-rect-contains? r 3 3) ; #f

(define center-rect-scale
  (lambda (k c)
    (center (center-x c)
            (center-y c)
            (* k (center-w c))
            (* k (center-h c)))))

(define center-rect
  (rectangles center-rect-from-center
              center-rect-from-corners
              center-rect-area
              center-rect-contains?
              center-rect-scale))

;======================================================================================================
;                                             3
;======================================================================================================
(struct corners (xmin ymin xmax ymax) #:transparent)


;had to fix used let* not allowed apparently
(define corners-rect-from-center
  (lambda (cx cy w h)
    (let ([half-w (/ w 2)])
      (let ([half-h (/ h 2)])
        (corners (- cx half-w)
                 (- cy half-h)
                 (+ cx half-w)
                 (+ cy half-h))))))

(define corners-rect-from-corners
  (lambda (x1 y1 x2 y2)
    (corners (min x1 x2)
             (min y1 y2)
             (max x1 x2)
             (max y1 y2))))

(define corners-rect-area
  (lambda (c)
    (* (- (corners-xmax c) (corners-xmin c))
       (- (corners-ymax c) (corners-ymin c)))))

(define corners-rect-contains?
  (lambda (c px py)
    (and (<= (corners-xmin c) px)
         (<= px (corners-xmax c))
         (<= (corners-ymin c) py)
         (<= py (corners-ymax c)))))

;equations were looked up havent done geometry in a while         
;centerX = (xmin + xmax) / 2
;centerY = (ymin + ymax) / 2
;half-width = (xmax - xmin) / 2
;half-height = (ymax - ymin) / 2
;new half-width = k × half-width
;new half-height = k × half-height

(define corners-rect-scale
  (lambda (k c)
    (let ([xmin (corners-xmin c)])
      (let ([ymin (corners-ymin c)])
        (let ([xmax (corners-xmax c)])
          (let ([ymax (corners-ymax c)])
            (let ([cx (/ (+ xmin xmax) 2)])
              (let ([cy (/ (+ ymin ymax) 2)])
                (let ([half-w (* k (/ (- xmax xmin) 2))])
                  (let ([half-h (* k (/ (- ymax ymin) 2))])
                    (corners (- cx half-w)
                             (- cy half-h)
                             (+ cx half-w)
                             (+ cy half-h))))))))))))

;messed up put let* had to redo
;; (define corners-rect-scale
;;   (lambda (k c)
;;     (let* ([xmin (corners-xmin c)]
;;            [ymin (corners-ymin c)]
;;            [xmax (corners-xmax c)]
;;            [ymax (corners-ymax c)]
;;            [cx (/ (+ xmin xmax) 2)]
;;            [cy (/ (+ ymin ymax) 2)]
;;            [half-w (* k (/ (- xmax xmin) 2))]
;;            [half-h (* k (/ (- ymax ymin) 2))])
;;       (corners (- cx half-w)
;;                (- cy half-h)
;;                (+ cx half-w)
;;                (+ cy half-h)))))

(define corners-rect
  (rectangles corners-rect-from-center
              corners-rect-from-corners
              corners-rect-area
              corners-rect-contains?
              corners-rect-scale))

;======================================================================================================;
;                                             4                                                        ;
;======================================================================================================;
(struct natsets
  (empty single union intersection difference contains?)
  #:transparent)

(define sparse-natset-empty
  (lambda ()
    '()))

(define sparse-natset-intersection
  (lambda (s1 s2)
    (filter (lambda (x) (memv x s2)) s1)))

(define sparse-natset-difference
  (lambda (s1 s2)
    (filter (lambda (x) (not (memv x s2))) s1)))


;this was not obvious how to do this at all

(define sparse-natset-union
  (lambda (s1 s2)
    (cond [(null? s1) s2]
          [(memv (car s1) s2)
           (sparse-natset-union (cdr s1) s2)]
          [else
           (cons (car s1)
                 (sparse-natset-union (cdr s1) s2))])))

;used let
;; (define sparse-natset-union
;;   (lambda (s1 s2)
;;     
;;     ; loop : List-of-NaturalNumber List-of-NaturalNumber -> List-of-NaturalNumber
;;     ; s = the remaining elements of s1 we still need to process
;;     ; acc = the accumulator (the union that has been built)
;;     (let loop ([s s1] [acc s2])
;;       
;;       ;If s is empty then the list is empty and all the elems have been gone through
;;       (cond [(null? s) acc]
;; 
;;             ;If the first elem of s is already in acc, skip it and continue with the rest of s.
;;             [(memv (car s) acc) (loop (cdr s) acc)]
;; 
;;             ;the elem is new. add it to acc, then continue processing the rest of s.
;; 
;;             [else (loop (cdr s) (cons (car s) acc))]))))

     ; sparse-natset-contains? : List-of-NaturalNumber NaturalNumber -> Boolean
(define sparse-natset-contains?
  (lambda (s x)
    (memv x s)))       
            
(define sparse-natset
  (natsets sparse-natset-empty
           list
           sparse-natset-union
           sparse-natset-intersection
           sparse-natset-difference
           sparse-natset-contains?))




;======================================================================================================;
;                                             5                                                        ;
;======================================================================================================;

; dense-natset-empty : -> List-of-Boolean
(define dense-natset-empty
  (lambda ()
    '()))
  
 
; dense-natset-single : NaturalNumber -> List-of-Boolean
(define dense-single-helper
  (lambda (i n)
    (cond [(> i n) '()]
          [(= i n) (cons #t (dense-single-helper (add1 i) n))]
          [else    (cons #f (dense-single-helper (add1 i) n))])))

(define dense-natset-single
  (lambda (n)
    (dense-single-helper 0 n)))


;used named let
;; (define dense-natset-single
;;   (lambda (n)
;;     (let loop ([i 0])
;;       (cond [(> i n) '()]
;;             [(= i n) (cons #t (loop (add1 i)))]
;;             [else    (cons #f (loop (add1 i)))]))))

 
; dense-natset-union : List-of-Boolean List-of-Boolean -> List-of-Boolean
(define dense-natset-union
  (lambda (s1 s2)
    (cond [(null? s1) s2]
          [(null? s2) s1]
          [else
           (cons (or (car s1) (car s2))
                 (dense-natset-union (cdr s1) (cdr s2)))])))
  
 
; dense-natset-intersection : List-of-Boolean List-of-Boolean -> List-of-Boolean
(define dense-natset-intersection
  (lambda (s1 s2)
    (cond [(or (null? s1) (null? s2)) '()]
          [else
           (cons (and (car s1) (car s2))
                 (dense-natset-intersection (cdr s1) (cdr s2)))])))
  
 
; dense-natset-difference : List-of-Boolean List-of-Boolean -> List-of-Boolean
(define dense-natset-difference
  (lambda (s1 s2)
    (cond [(null? s1) '()]
          [(null? s2) s1]
          [else
           (cons (and (car s1) (not (car s2)))
                 (dense-natset-difference (cdr s1) (cdr s2)))])))
  
 
; dense-natset-contains? : List-of-Boolean NaturalNumber -> Boolean
(define dense-natset-contains?
  (lambda (s n)
    (cond [(null? s) #f]
          [(zero? n) (car s)]
          [else (dense-natset-contains? (cdr s) (sub1 n))])))
  
 
; dense-natset : [NatSets List-of-Boolean]
(define dense-natset
  (natsets dense-natset-empty
           dense-natset-single
           dense-natset-union
           dense-natset-intersection
           dense-natset-difference
           dense-natset-contains?))

;======================================================================================================;
;                                             6                                                        ;
;======================================================================================================;

; function-natset-empty : -> [NaturalNumber -> Boolean]
(define function-natset-empty
  (lambda ()
    (lambda (n) #f)))
 
; function-natset-single : NaturalNumber -> [NaturalNumber -> Boolean]
(define function-natset-single
  (lambda (x)
    (lambda (n) (= n x))))
 
; function-natset-union : [NaturalNumber -> Boolean] [NaturalNumber -> Boolean] -> [NaturalNumber -> Boolean]
(define function-natset-union
  (lambda (s1 s2)
    (lambda (n) (or (s1 n) (s2 n)))))
 
; function-natset-intersection : [NaturalNumber -> Boolean] [NaturalNumber -> Boolean] -> [NaturalNumber -> Boolean]
(define function-natset-intersection
  (lambda (s1 s2)
    (lambda (n) (and (s1 n) (s2 n)))))
 
; function-natset-difference : [NaturalNumber -> Boolean] [NaturalNumber -> Boolean] -> [NaturalNumber -> Boolean]
(define function-natset-difference
  (lambda (s1 s2)
    (lambda (n) (and (s1 n) (not (s2 n))))))
 
; function-natset-contains? : [NaturalNumber -> Boolean] NaturalNumber -> Boolean
(define function-natset-contains?
  (lambda (s n)
    (s n)))
 
; function-natset : [NatSets [NaturalNumber -> Boolean]]
(define function-natset
  (natsets function-natset-empty
           function-natset-single
           function-natset-union
           function-natset-intersection
           function-natset-difference
           function-natset-contains?))


;this seems too easy (little sus after how hard the prev ones were to grasp)
(define s
  (function-natset-union
     (function-natset-single 3)
     (function-natset-single 5)))

(s 3) ; #t
(s 4) ; #f
(s 5) ; #t
;well it passes so ig this works but I dont trust it

;======================================================================================================;
;                                             7                                                        ;
;======================================================================================================;

;pair
(define pair
  (lambda (a)
    (lambda (b)
      (lambda (f)
        (f a b)))))

;first elem
(define fst
  (lambda (p)
    (p (lambda (a b) a))))

; second elem
(define snd
  (lambda (p)
    (p (lambda (a b) b))))

(define csub1
  (lambda (n)
    (lambda (f)
      (lambda (x)
        (((n
           (lambda (g)
             (lambda (h)
               (h (g f)))))
          (lambda (u) x))
         (lambda (u) u))))))

;bro what is even this function
(define p ((pair 10)20))
(fst p) ;10
(snd p) ;20
 


       


