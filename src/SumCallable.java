import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class SumCallable implements Callable<Integer>{

	IntStream data;
	
	public SumCallable(Future<List<Integer>> data) {
		
		try {
			
			this.data = data.get()
					.stream()
					.mapToInt(Integer::intValue);
			
		} catch (InterruptedException | ExecutionException e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public Integer call() throws Exception {
		Integer sum = 0;
				
		if (data != null) {
			sum = data.sum();
		} 
		
		return sum;
	}

}
