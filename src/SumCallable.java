//********************************************************************
//
//  Author:        Jeremy Aubrey
//
//  Program #:     5
//
//  File Name:     SumCallable.java
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
//                 accepts a Future object, a List of Integers and computes
//                 the sum of its elements.
//
//*********************************************************************

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class SumCallable implements Callable<Integer>{

	IntStream data;
	
	// constructor
	public SumCallable(Future<List<Integer>> data) {
		
		try {
			// get Intstream from List data
			this.data = data.get()
					.stream()
					.mapToInt(Integer::intValue);
			
		} catch (InterruptedException | ExecutionException e) {
			
			System.out.println(e.getMessage());
		}
	}//end constructor
	
    //***************************************************************
    //
    //  Method:       call (Non Static)
    // 
    //  Description:  Uses terminal sum method on IntStream to obtain 
    //                sum.
    //
    //  Parameters:   None
    //
    //  Returns:      Integer
    //
    //**************************************************************
	@Override
	public Integer call() throws Exception {
		Integer sum = 0;
				
		if (data != null) {
			sum = data.sum();
		} 
		
		return sum;
		
	}// end call method

}// end SumCallable class