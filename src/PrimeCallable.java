import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrimeCallable implements Callable<List<Integer>> {

	int start;
	int end;

	public PrimeCallable(int start, int end) {

		this.start = start;
		this.end = end;
	}

	private boolean isPrime(int num) {
		boolean isPrime = false;
		
		if (num <= 2) {
			isPrime = num == 2;
		} else {
			return (num % 2) != 0 && IntStream.rangeClosed(3, (int) Math.sqrt(num))
					.allMatch(i -> num % i != 0);
		}
		
		return isPrime;
	}

	@Override
	public List<Integer> call() throws Exception {
		
		List<Integer> primes = IntStream.rangeClosed(start, end)
		.filter(num -> isPrime(num))
		.boxed()
		.collect(Collectors.toList());
		
		return primes;
	}

}
