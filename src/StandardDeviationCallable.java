import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class StandardDeviationCallable implements Callable<Double>{

	IntStream data;
	double mean;
	int count;
	
	public StandardDeviationCallable(Future<List<Integer>> data, Future<Double> mean) {
		
		try {
			
			this.data = data.get()
					.stream()
					.mapToInt(Integer::intValue);
			
			this.mean = mean.get();
			
			this.count = data.get().size();
			
		} catch (InterruptedException | ExecutionException e) {
			
			System.out.println(e.getMessage());
		}
	}
	
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
	}

}
