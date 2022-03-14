//  Author:        Jeremy Aubrey
//
//  Program #:     4
//
//  File Name:     Server.java
//
//  Course:        COSC 4301 - Modern Programming
//
//  Due Date:      03/06/2022
//
//  Instructor:    Fred Kumi 
//
//  Description:   A server that accepts integer data from a client 
//                 and generates statistics (sum, mean, and standard
//                 deviation). Upon client termination the server 
//                 resumes listening for more clients connections.
//
//********************************************************************

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class Server {
	
    //***************************************************************
    //
    //  Method:       main
    // 
    //  Description:  The main method of the Server
    //
    //  Parameters:   String array
    //
    //  Returns:      N/A 
    //
    //**************************************************************
	public static void main(String[] args) {
		
		// create an object of the main class and use it to call
		// the non-static developerInfo and other non-static methods
		Server server = new Server();
		server.developerInfo();
		
		int portNumber = 4301;
		
		ServerSocket socket = server.createSocket(portNumber);
		
		while (true) {
			server.startListening(socket); // process client responses
		}	
	}// end main method
	
    //***************************************************************
    //
    //  Method:       createSocket (Non Static)
    // 
    //  Description:  Attempts to create a new socket on the specified
    //                port number.
    //
    //  Parameters:   int
    //
    //  Returns:      ServerSocket
    //
    //**************************************************************
	public ServerSocket createSocket(int portNumber) {
		
		ServerSocket socket = null;
		
		try {
			
			socket = new ServerSocket(portNumber); //instantiate new socket
			
		} catch (IOException | SecurityException | IllegalArgumentException e) {
			
			System.err.println(e);
		}
		
		return socket;
		
	}// end createSocket method
	
    //***************************************************************
    //
    //  Method:       startListening (Non Static)
    // 
    //  Description:  Begins listening and responding to client
    //                requests on the provided socket.
    //
    //  Parameters:   ServerSocket
    //
    //  Returns:      N/A
    //
    //**************************************************************
	public void startListening(ServerSocket socket) {
			
		try {
			
			System.out.println("\n[ SERVER LISTENTING ON PORT " + socket.getLocalPort() + " ]");
			Socket client = socket.accept(); // blocks until client connects
			System.out.println("[ CLIENT CONNECTED ]");
			
			// socket input and output
			BufferedReader input = new BufferedReader( 
					new InputStreamReader(client.getInputStream()));
			PrintWriter output = new PrintWriter(client.getOutputStream(), true); // auto flush output (true)
			
			// process requests until client sends "bye"
			while (true) {
				
				String recievedMessage = input.readLine().toLowerCase();
				if(recievedMessage.equals("bye")) {
					break;
				} else {
					String processed = processData(recievedMessage); //process client request
					output.println(processed); // send response to client
				}
			}
			
			// close the socket
			client.close();
			System.out.println("[ CLIENT CLOSED ]");
		
		} catch (IOException | SecurityException e) {
			
			System.out.println(e.getMessage());
		}
	}// end startListening method
	
    //***************************************************************
    //
    //  Method:       processData (Non Static)
    // 
    //  Description:  Attempts to parse client response into int array.
    //                Will pass data on for further processing or return
    //                an error message if input cannot be parsed to int.  
    //
    //  Parameters:   String
    //
    //  Returns:      String
    //
    //**************************************************************
	public String processData(String data) {
		
		// convert String into String[]
		String result = "";
		String[] strArray = data.trim().replaceAll("\\s+", ",").split(",");
		int[] intArray = new int[strArray.length];
		
		try {
			// populate a new int[] from new String[]
			for(int i = 0; i < strArray.length; i++) {
				intArray[i] = Integer.parseInt(strArray[i]);
			}
			
			result = getStatisticsOrError(intArray); // get statistics
			
		} catch (NumberFormatException e) {
			
			result = "Invalid data, enter integers seperated by a space\n";
		}
		
		return result + "\nEND"; // END signifies end of data to client
		
	}// end processData method
	
    //***************************************************************
    //
    //  Method:       getStatisticsOrError (Non Static)
    // 
    //  Description:  Validates data and gets statistics or returns 
    //                an error message to the client.
    //
    //  Parameters:   int[]
    //
    //  Returns:      String
    //
    //**************************************************************
	public String getStatisticsOrError(int[] data) {
		
		String result = "";
		ExecutorService pool = Executors.newFixedThreadPool(3); // fixed at 3
		
		// validation 
		if(data.length < 2 || data.length > 2) {
			result = "Must enter 2 integers";
		} else if (data[0] <= 0 || data[1] <= 0) {
			result = "All integers must be greater than zero.";
		} else if(data[0] >= data[1]) {
			result = "The first integer must be less than the second";
		} else {
		
			try {
				// get statistics
				Future<List<Integer>> primesList = 	pool.submit(new PrimeCallable(data[0], data[1]));
				String primes = primesList.get().toString();
				
				// format results
				result = String.format("%s%s%n%-20s %s%n%-20s %s%n%-20s %s%n",
						"Primes: ", primes,
						"Sum: ", 0, 
						"Mean: ", 0,
						"Standard Deviation: ", String.format("%.2f", 0.0));
				
			} catch (InterruptedException | ExecutionException | CancellationException e) {
				
				System.out.println(e.getMessage());
			}
		}
		
		return result;
		
	}// end getStatisticsOrError method
	
    //***************************************************************
    //
    //  Method:       getSum (Non Static)
    // 
    //  Description:  Uses terminal sum method on IntStream to obtain 
    //                sum.
    //
    //  Parameters:   IntStream
    //
    //  Returns:      int
    //
    //**************************************************************
	public int getSum(IntStream data) {
		
		return data.sum();
		
	}// end getSum method
	
    //***************************************************************
    //
    //  Method:       getMean (Non Static)
    // 
    //  Description:  Uses terminal average method on IntStream to obtain 
    //                mean.
    //
    //  Parameters:   IntStream
    //
    //  Returns:      double
    //
    //**************************************************************
	public double getMean(IntStream data) {
		
		double mean = 0;
		OptionalDouble result = data.average();
		
		if(result.isPresent()) {
			mean = result.getAsDouble(); // get double value
		}
		
		return mean;
		
	}// end getMean method
	
	
    //***************************************************************
    //
    //  Method:       getStandardDeviation (Non Static)
    // 
    //  Description:  Maps each int to a double, each double to the square
    //                of its distance to the mean, sums the squares, then 
    //                gets the square root of the sum divided by the count.
    //                Uses previously calculated mean to prevent unnecessary
    //                calculations. 
    //
    //  Parameters:   IntStream, double, int
    //
    //  Returns:      double
    //
    //**************************************************************
	public double getStandardDeviation(IntStream data, double mean, int count) {

		double sumOfSquares = data.mapToDouble(num -> Double.valueOf(num)) // to double
				.map(num -> Math.pow((num - mean), 2)) // square distance to mean
				.sum(); // sum squares
		
		return Math.sqrt(sumOfSquares / count); // get square root of sum / count 
		
	}// end getStandardDeviation method
	
    //***************************************************************
    //
    //  Method:       developerInfo (Non Static)
    // 
    //  Description:  The developer information method of the program.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void developerInfo()
    {
       System.out.println("Name:    Jeremy Aubrey");
       System.out.println("Course:  COSC 4302 Modern Programming");
       System.out.println("Program: 4");

    } // end developerInfo method
    
}// end Server class