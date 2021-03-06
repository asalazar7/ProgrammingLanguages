;;; Load the data representation
(load "infix-data-representation.lisp")

;;; Test Cases
(pretty-diff 'a 'x)
(pretty-diff '(x / 3) 'x)
(pretty-diff '(- x) 'x)
(pretty-diff '(- - x) 'x)
(pretty-diff '(- - - x) 'x)
(pretty-diff '(x / 5) 'y)
(pretty-diff '(x + x) 'x)
(pretty-diff '(- - (x + x)) 'x)
(pretty-diff '((x + x) + x) 'x)
(pretty-diff '(x - (- x)) 'x)
(pretty-diff '(4 * x) 'x)
(pretty-diff '(x * x) 'x)
(pretty-diff '((- x) + (- x)) 'x)
(pretty-diff '(x / x) 'x)
(pretty-diff '(x / (2 * x)) 'x)
(pretty-diff '((x * x) * (x * x)) 'x)
(pretty-diff '(** x 4) 'x)
(pretty-diff '(3 * (** x 4)) 'x)
(pretty-diff '(a * (** x 3)) 'x)
(pretty-diff '(-2 * (** x -3)) 'x)