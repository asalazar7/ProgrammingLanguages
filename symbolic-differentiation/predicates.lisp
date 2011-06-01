(defun function-instance-p (function-symbol candidate)
  "A convenience abstraction. Checks if the candidate is an instance 
   of a function-type represented by the function-symbol"
  (and (function-p candidate)
       (eql function-symbol (select-operator candidate))))

(defun constant-p (candidate)
  "Returns true if the candidate is a constant, false otherwise."
  (or (numberp candidate)
      (member candidate *constant-symbols*)))

(defun variable-p (candidate)
  "Returns true if the candidate is a variable, false otherwise."
  (member candidate *variable-symbols*))

(defun operator-p (candidate)
  "Returns true if the candidate is an operator, false otherwise."
  (member candidate *operator-symbols*))

(defun function-p (candidate)
  "Returns true if hte candidate is a function, false otherwise."
  (not (or (constant-p candidate) 
	    (variable-p candidate))))

(defun negation-p (candidate)
  "Returns true if the candidate is a negation, false otherwise."
  (and 
   (function-p candidate)
   (equal (select-operator candidate) *negation-symbol*)
   (subsetp (butlast candidate) (list *negation-symbol*) 
	    :test #'equalp)))

(defun addition-p (candidate)
  "Returns true if the candidate is an addition operation, 
   false otherwise."
  (function-instance-p *addition-symbol* candidate))

(defun subtraction-p (candidate)
  "Returns true if the candidate is a subtraction operation, 
   false otherwise."
  (if (not (negation-p candidate))
      (function-instance-p *subtraction-symbol* candidate)))

(defun multiplication-p (candidate)
  "Returns true if the candidate is a multiplication operation, 
   false otherwise."
  (function-instance-p *multiplication-symbol* candidate))

(defun division-p (candidate)
  "Returns true if the candidate is a division operation, 
   false otherwise."
  (function-instance-p *division-symbol* candidate))

(defun exponentiation-p (candidate)
  "Returns true if the candidate is a exponential operation, 
   false otherwise."
  (function-instance-p *exponentiation-symbol* candidate))