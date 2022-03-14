//********************************************************************
//
//  Author:        Jeremy Aubrey
//
//  Program #:     5
//
//  File Name:     StandardDeviationCallable.java
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
//                 accepts a Future object (List of Integers), a mean,
//                 and computes the standard deviation. 
//
//*********************************************************************

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class StandardDeviationCallable implements Callable<Double>{

	IntStream data; 
	double mean;   
	int count;
	
	// constructor
	public StandardDeviationCallable(Future<List<Integer>> data, Future<Double> mean) {
		
		try { // get Intstream from List data
			
			this.data = data.get()
					.stream()
					.mapToInt(Integer::intValue);
			
			this.mean = mean.get();
			
			this.count = data.get().size();
			
		} catch (InterruptedException | ExecutionException e) {
			
			System.out.println(e.getMessage());
		}
	}// end constructor
	
    //***************************************************************
    //
    //  Method:       call (Non Static)
    // 
    //  Description:  Maps each int to a double, each double to the square
    //                of its distance to the mean, sums the squares, then 
    //                gets the square root of the sum divided by the count.
    //                Uses previously calculated mean to prevent unnecessary
    //                calculations. 
    //
    //  Parameters:   None
    //
    //  Returns:      Double
    //
    //**************************************************************
	@Override
	public Double call() throws Exception {
		
		double sumOfSquares = data.mapToDouble(num -> Double.valueOf(num)) // to double
				.map(num -> Math.pow((num - mean), 2)) // square distance to mean
				.sum(); // sum squares
		
		return Math.sqrt(sumOfSquares / count); // get square root of sum / count 
		
	}// end call method

}// end StandardDeviationCallable class