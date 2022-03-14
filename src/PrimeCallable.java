//********************************************************************
//
//  Author:        Jeremy Aubrey
//
//  Program #:     5
//
//  File Name:     PrimeCallable.java
//
//  Course:        COSC-4301 Modern Programming
//
//  Due Date:      03/13/2022
//
//  Instructor:    Fred Kumi 
//
//  Chapter:       5
//
//  Description:   A class that implements the Callable interface
//                 allowing values to be returned from its call method 
//                 which is executed in a seperate thread. This class
//                 accepts 2 integers, a start and end value, and returns 
//                 a list of prime numbers within that range.
//
//*********************************************************************

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrimeCallable implements Callable<List<Integer>> {

	int start;
	int end;

	// constructor
	public PrimeCallable(int start, int end) {

		this.start = start;
		this.end = end;
	
	}// end constructor

    //***************************************************************
    //
    //  Method:       isPrime (Non Static)
    // 
    //  Description:  This method determines whether a positive integer is
    //                a prime number.  It returns true if the integer a prime
    //                number, and false if it is not.
    //
    //  Parameters:   A Positive Integer
    //
    //  Returns:      boolean
    //
    //**************************************************************
	public boolean isPrime(int number) {
		
		boolean rtnValue = true;
	  
		if (number < 2) {           // Integers < 2 cannot be prime
         rtnValue = false;
		} else if (number == 2) {     // Special case: 2 is the only even prime number
         rtnValue = true;
		} else if (number % 2 == 0) { // Other even numbers are not prime
         rtnValue = false;
		} else {
         // Test odd divisors up to the square root of number
         // If any of them divide evenly into it, then number is not prime
         for (int divisor = 3; divisor <= Math.sqrt(number); divisor += 2) {
        	 
		     if (number % divisor == 0)
                rtnValue = false;
         }
      }
      
		return rtnValue;
		
   }// end isPrime method

    //***************************************************************
    //
    //  Method:       call (Non Static)
    // 
    //  Description:  Creates a List of prime Integers between the range 
    //                of the start and end fields.
    //
    //  Parameters:   None
    //
    //  Returns:      List<Integer>
    //
    //**************************************************************
	@Override
	public List<Integer> call() throws Exception {
		
		List<Integer> primes = IntStream.rangeClosed(start, end)
		.filter(num -> isPrime(num))   // filter out non-prime numbers
		.boxed()                       // box each int to Integer
		.collect(Collectors.toList()); // convert to List<Integer>
		
		return primes;
		
	}// end call method

}// end PrimeCallable class