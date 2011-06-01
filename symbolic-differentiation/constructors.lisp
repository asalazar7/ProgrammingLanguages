(defun eval-fn-or-make-expr (fn-symbol first-operand second-operand)
  "If the first and second operands are numbers, they are evaluated
   as arguments to the function and the result is returned, otherwise
   an expression representing the function applied to the two operands
   is returned"
  (cond ((and (numberp first-operand) (numberp second-operand))
	  (funcall fn-symbol first-operand second-operand))
	 (t
	  (make-fn-expr fn-symbol first-operand second-operand))))

(defun make-constant (constant)
  "Returns the constant value for the input constant"
  (if (numberp constant)
      constant
    (first (member constant *constant-symbols*))))

(defun make-variable (variable)
  "Returns the symbol for the input variable"
  (first (member variable *variable-symbols*)))

(defun make-negation (function)
  "Returns the negation of the input function."
  (cond
   ((not (function-p function)) (* -1 function))
   ((negation-p function) (cons *negation-symbol* function))
   (t (list *negation-symbol* function))))

(defun make-sum (augend addend)
  "Returns the sum expression of the augend and addend."
  (cond ((equalp 0 augend) addend)
        ((equalp 0 addend) augend)
        (t (eval-fn-or-make-expr *addition-symbol* augend addend))))

(defun make-difference (minuend subtrahend)
  "Returns the difference expression of the minuend and the
   subtrahend"
  (cond ((equalp minuend subtrahend) 0)
	((equalp 0 minuend) (make-negation subtrahend))
	((equalp 0 subtrahend) minuend)
	(t
	 (eval-fn-or-make-expr
	  *subtraction-symbol* minuend subtrahend))))

(defun make-product (multiplier multiplicand)
  "Returns the product expression of the multiplier and multiplicand."
  (cond ((or (equalp 0 multiplier) (equalp 0 multiplicand)) 0)
        ((equalp 1 multiplier) multiplicand)
        ((equalp 1 multiplicand) multiplier)
        ((equalp -1 multiplier) (make-negation multiplicand))
        ((equalp -1 multiplicand) (make-negation multiplier))
        (t (eval-fn-or-make-expr *multiplication-symbol*
                                 multiplier multiplicand))))

(defun make-quotient (dividend divisor)
  "Returns the quotient expression of the dividend and divisor."
  (cond ((equalp 0 dividend) 0)
        ((equalp dividend divisor) 1)
        (t (eval-fn-or-make-expr *division-symbol* dividend divisor))))

(defun make-exponentiation (base exponent)
  "Returns the exponential expression of the base and exponent."
  (cond ((and (numberp base) (numberp exponent))
	 (expt base exponent))
	(t (make-fn-expr *exponentiation-symbol* base exponent))))