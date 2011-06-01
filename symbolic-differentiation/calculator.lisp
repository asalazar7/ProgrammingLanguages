(defun pretty-diff (function variable)
  "Pretty prints the input function, variable, and the result of 
   differentiating the function with respect to the variable"
  (format t "  FUNCTION: ~a~%  VARIABLE: ~a~%DERIVATIVE: ~A~%~%"
           function
           variable
           (differentiate function variable)))

(defun differentiate (function variable)
  "Performs a differentiation of the function with respect to the
   variable."
  (cond
   ((constant-p function)
    (make-constant 0))
   ((variable-p function)
    (differentiate-variable function variable))
   ((negation-p function)
    (differentiate-negation function variable))
   ((addition-p function)
    (differentiate-addition function variable))
   ((subtraction-p function)
    (differentiate-subtraction function variable))
   ((multiplication-p function)
    (differentiate-multiplication function variable))
   ((division-p function)
    (differentiate-division function variable))
   ((exponentiation-p function)
    (differentiate-exponentiation function variable))))
    
(defun differentiate-variable (variable with-respect-to)
  "Differentiates the variable with respect to another variable,
   potentially itself"
  (if (equalp (make-variable variable)
	      (make-variable with-respect-to))
      (make-constant 1)
    (make-constant 0)))

(defun differentiate-negation (function variable)
  "Differentiates the negation function with respect to the variable"
  (make-negation (differentiate
		  (select-first-operand function) variable)))

(defun differentiate-addition (function variable)
  "Differentiates the addition function with respect to the variable"
  (make-sum
   (differentiate (select-first-operand function) variable)
   (differentiate (select-second-operand function) variable)))

(defun differentiate-subtraction (function variable)
  "Differentiates the subtraction function with respect to the
   variable"
  (make-difference
   (differentiate (select-first-operand function) variable)
   (differentiate (select-second-operand function) variable)))

(defun differentiate-multiplication (function variable)
  "Differentiates the multiplication function with respect to the
   variable"
  (make-sum
   (make-product
    (select-first-operand function)
    (differentiate (select-second-operand function) variable))
   (make-product
    (select-second-operand function)
    (differentiate (select-first-operand function) variable))))

(defun differentiate-division (function variable)
  "Differentiates the division function with respect to the variable"
  (make-quotient
   (make-difference
    (make-product
     (select-second-operand function)
     (differentiate (select-first-operand function) variable))
    (make-product
     (select-first-operand function)
     (differentiate (select-second-operand function) variable)))
   (make-exponentiation (select-second-operand function) 2)))

(defun differentiate-exponentiation (function variable)
  "Differentiates the exponential function with respect to the
   variable"
  (make-product
   (make-product
    (select-second-operand function)
    (make-exponentiation
     (select-first-operand function)
     (make-difference (select-second-operand function) 1)))
   (differentiate (select-first-operand function) variable)))