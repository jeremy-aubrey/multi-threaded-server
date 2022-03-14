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
    //  Description:  Returns a boolean value based on whether a number
    //                is prime.
    //
    //  Parameters:   int
    //
    //  Returns:      boolean
    //
    //**************************************************************
	private boolean isPrime(int num) {
		
		boolean isPrime = false;
		
		if (num <= 2) {
			isPrime = num == 2;
		} else { 
			isPrime = (num % 2) != 0 && IntStream.rangeClosed(3, (int) Math.sqrt(num))
					.allMatch(i -> num % i != 0);
		}
		
		return isPrime;
		
	}// end isPrime method

    //***************************************************************
    //
    //  Method:       call (Non Static)
    // 
    //  Description:  
    //
    //  Parameters:   None
    //
    //  Returns:      List<Integer>
    //
    //**************************************************************
	@Override
	public List<Integer> call() throws Exception {
		
		List<Integer> primes = IntStream.rangeClosed(start, end)
		.filter(num -> isPrime(num))
		.boxed()
		.collect(Collectors.toList());
		
		return primes;
		
	}// end call method

}// end PrimeCallable class