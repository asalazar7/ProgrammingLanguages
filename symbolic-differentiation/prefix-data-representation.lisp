;;; Load the common constant definitions
(load "constants.lisp")

;;; Load the common predicate functions
(load "predicates.lisp")

;;; Load the constructor functions
(load "constructors.lisp")

;;; Load common calculation engine
(load "calculator.lisp")

(defun make-fn-expr (fn-symbol first-operand second-operand)  
  "Returns a prefix function expression of the function symbol applied 
   to the first and second operand"
  (if (and (not (operator-p first-operand))
	   (not (operator-p second-operand)))
      (list fn-symbol first-operand second-operand)))

(defun select-operator (function)
  "Selects the operator of an expression"
  (first function))

(defun select-first-operand (function)
  "Selects the first operand from a function expression"
  (cond ((negation-p function) 
	 (if (negation-p (rest function))
	     (rest function)
	   (first (rest function))))
	(t (second function))))

(defun select-second-operand (function)
  "Selects the second operand from a function expression"
  (third function))