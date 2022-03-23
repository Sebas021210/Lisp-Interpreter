(defun factorial (num)
    (if (= num 0) 1
      (* num (factorial (- num 1)))
    )
)

(defun fibonacci (num)
  (if (< num 2) num
    (+ (fibonacci (- num 1)) (fibonacci (- num 2)))
  )
)

(defun getNums (num)
  (print "Sucesion de Fibonacci:")
  (loop for x from 0 to num
    do(print (fibonacci x))
  )
  (print "Factorial:")
  (factorial num)
)
