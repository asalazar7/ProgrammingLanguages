;;; Load common constant definitions
(load "constants.lisp")

;;; Load common predicate functions
(load "predicates.lisp")

;;; Load common constructor functions
(load "constructors.lisp")

;;; Load common calculation engine
(load "calculator.lisp")

(defun make-fn-expr (fn-symbol first-operand 
				     second-operand)  
  "Returns an infix function expression of the function symbol applied 
   to the first and second operand"
  (if (and (not (operator-p first-operand))
	   (not (operator-p second-operand)))
      (list first-operand fn-symbol second-operand)))

(defun operator-at-p (selector-fn candidate)
  "Builds an expression which checks if the candiate is a function and
   if so, if its operator is in the expected position indicated by the
   selector-fn"
  (and (function-p candidate)
	(operator-p (funcall selector-fn candidate))))

(defun prefix-p (candidate)
  "Returns true if the candiate is an prefix function expression, 
   false otherwise"
  (operator-at-p 'first candidate))

(defun infix-p (candidate)
  "Returns true if the candiate is an infix function expression, 
   false otherwise"
  (operator-at-p 'second candidate))

(defun select-operator (function)
  "Selects the operator of an expression"
  (cond 
    ((prefix-p function) (first function))
    ((infix-p function) (second function))))

(defun select-first-operand (function)
  "Selects the first operand from a function expression"
  (cond ((negation-p function) 
	 (if (negation-p (rest function))
	     (rest function)
	   (first (rest function))))
	((prefix-p function) (second function))
	((infix-p function) (first function))))

(defun select-second-operand (function)
  "Selects the second operand from a function expression"
  (third function))