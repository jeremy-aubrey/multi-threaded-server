//********************************************************************
//
//  Author:        Jeremy Aubrey
//
//  Program #:     5
//
//  File Name:     MeanCallable.java
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
//                 the mean of its elements.
//
//*********************************************************************

import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class MeanCallable implements Callable<Double>{

	IntStream data;
	
	// constructor
	public MeanCallable(Future<List<Integer>> data) {
		
		try { // get Intstream from List data
			
			this.data = data.get() 
					.stream()
					.mapToInt(Integer::intValue);
			
		} catch (InterruptedException | ExecutionException e) {
			
			System.out.println(e.getMessage());
		}
	}// end constructor
	
    //***************************************************************
    //
    //  Method:       call (Non Static)
    // 
    //  Description:  Uses terminal average method on IntStream to obtain 
    //                mean.
    //
    //  Parameters:   None
    //
    //  Returns:      Double
    //
    //**************************************************************
	@Override
	public Double call() throws Exception {
		
		double mean = 0;
		OptionalDouble result = data.average();
		
		if(result.isPresent()) {
			mean = result.getAsDouble(); // get double value
		}
		
		return mean;
		
	}// end call method

}// end MeanCallable method