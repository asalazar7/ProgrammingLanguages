! nth_root.f95
! Nth-Roots in FORTRAN
! Aaron Kuehler
! This program calculates the nth root of a real number using a 
! generalization of the Newton-Raphson method.
program Newton_Raphson

  implicit none ! Disallow implicit typing

  ! Assignment Part A: 
  print*, "Part A: SQUARES"
  call approx_nth_root_and_report(0.5, 2)
  call approx_nth_root_and_report(1.0, 2)
  call approx_nth_root_and_report(100.0, 2)
  call approx_nth_root_and_report(150.0, 2)
  call approx_nth_root_and_report(5000.0,2)
  call approx_nth_root_and_report(62632.52, 2)

  ! Assignment Part B:
  print*, "Part B: NTH ROOTS"
  call approx_nth_root_and_report(33.0, 3)
  call approx_nth_root_and_report(2255.0, 4)
  call approx_nth_root_and_report(148.0, 5)
  call approx_nth_root_and_report(5231.23, 6)

contains

  ! Recursively computes the nth root of the base using the 
  ! Newton-Raphson algorithm. 
  recursive real function nth_root(guess, base, degree, count) result(next_guess)
    real::next_guess,guess, base, delta
    integer::degree, count

    ! Calculate the next guess using the Newton-Raphson method.
    next_guess = make_next_guess(guess, base, degree)
    count = count + 1 ! Account for the new guess

    ! If the guess isn't good enough
    if(is_not_close_approx(guess, next_guess)) then

       ! Approximate using the next_guess
       next_guess = nth_root(next_guess, base, degree, count)
    end if
  end function nth_root

  ! Calculates the next guess using the Newton-Raphson method. If 
  ! calculating the square root, the special square root case of 
  ! the Newton-Raphson algorithm is used; otherwise, the general form
  ! of the algorithm is used to compute the next guess.
  real function make_next_guess(guess, base, degree) result(next_guess)
    real::next_guess,guess, base
    integer::degree

    if(degree == 2) then
       next_guess = (guess + (base / guess)) / 2 ! Special case for square roots
    else
       next_guess = ((degree-1) * guess + (base / power(guess, degree-1))) / degree
    end if
  end function make_next_guess
    

  ! Determines if a guess is sufficiently close to the actual value.
  logical function is_not_close_approx(guess, next_guess) result(is_not_close)

    ! The delta tolerance used to decide when the nth_root funciton 
    ! has found a close enough approximation of the nth root of 
    real,parameter::tolerance=0.0005

    real::guess,next_guess,delta
    logical::is_close
    
    ! Calculate the difference between the guesses
    delta = abs(guess-next_guess)

    ! If the difference is outside of the tolerance range
    if(delta > tolerance) then

       ! Then we are not close enough in our approximation
       is_not_close = .true.
    else
       is_not_close = .false.
    end if
  end function is_not_close_approx

  ! Recursively computes the result of raising the base to the 
  ! exponent in O(log n) time.
  recursive real function power(base, exponent) result(res)
    real::res,base,half_exp_power
    integer::exponent
    
    if(exponent == 0) then
       res  =  1.0 ! Base case
    else if (MOD(exponent,2) == 0) then
       half_exp_power = power(base, exponent/2) ! Recursion step
       res = half_exp_power * half_exp_power
    else
       res =  base * power(base, exponent-1) ! Recursion step
    end if
  end function power

  ! Runs the nth root approximation and prints info about the 
  ! calculation process.
  subroutine approx_nth_root_and_report(base, degree)
    real::res,base,initial_guess
    integer::count,degree

    count = 0
    initial_guess = 1.0

    ! Calculate the nth root
    res = nth_root(initial_guess, base, degree, count)

    ! Display the result
    print*, degree, " root of ", base, " = ",   res , " in ", count, " steps."
  end subroutine approx_nth_root_and_report
end program Newton_Raphson
