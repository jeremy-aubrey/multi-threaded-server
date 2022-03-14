import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class MeanCallable implements Callable<Double>{

	IntStream data;
	
	public MeanCallable(Future<List<Integer>> data) {
		
		try {
			
			this.data = data.get()
					.stream()
					.mapToInt(Integer::intValue);
			
		} catch (InterruptedException | ExecutionException e) {
			
			System.out.println(e.getMessage());
		}
	}
	
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
	}

}
