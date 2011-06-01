;;; "The list of acceptable symbols used to represent constant values
(defconstant *constant-symbols*
  '(A B C D E F G H M N))

;;; The list of acceptable symbols used to represent variable values
(defconstant *variable-symbols*
  '(U V W X Y Z))

;;;  The symbol used to express the negation operation
(defconstant *negation-symbol*
  '-)

;;; The symbol used to express the addition operation
(defconstant *addition-symbol*
  '+)

;;; The symbol used to express the subtraction operation
(defconstant *subtraction-symbol* 
  *negation-symbol*)

;;; The symbol used to express the multiplication operation
(defconstant *multiplication-symbol* 
  '*)

;;;   The symbol used to express the division operation
(defconstant *division-symbol* 
  '/)

;;; The symbol used to express the exponentiation operation
(defconstant *exponentiation-symbol*
  '**)

;;; The list of acceptable symbols used to represent operators
(defconstant *operator-symbols*
  `(,*addition-symbol* 
    ,*subtraction-symbol* 
    ,*multiplication-symbol* 
    ,*division-symbol* 
    ,*exponentiation-symbol*))