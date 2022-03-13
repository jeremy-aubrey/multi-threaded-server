//  Author:        Jeremy Aubrey
//
//  Program #:     4
//
//  File Name:     Client.java
//
//  Course:        COSC 4301 - Modern Programming
//
//  Due Date:      03/06/2022
//
//  Instructor:    Fred Kumi 
//
//  Description:   A client that sends integer data to a server in 
//                 order to obtain statistics (sum, mean, and standard
//                 deviation).
//
//********************************************************************

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
	
    //***************************************************************
    //
    //  Method:       main
    // 
    //  Description:  The main method of the Client
    //
    //  Parameters:   String array
    //
    //  Returns:      N/A 
    //
    //**************************************************************
	public static void main(String[] args) {
		
		// create an object of the main class and use it to call
		// the non-static developerInfo and other non-static methods
	    Client client = new Client();
		client.developerInfo();
		client.instructions();
		
		String host = "127.0.0.1"; 
		int port = 4301;
		
		Socket socket = client.connect(host, port); // connect to server
		client.sendRequest(socket);  // send requests

	} // end main method
	
    //***************************************************************
    //
    //  Method:       connect (Non Static)
    // 
    //  Description:  Attempts to create a socket connection with the 
    //                server on the specified host and port number (socket).
    //
    //  Parameters:   String, int
    //
    //  Returns:      Socket
    //
    //**************************************************************
	public Socket connect(String host, int port) {
		
		Socket socket = null;
		
		try {
			// make connection to server socket
			socket = new Socket(host, port);
				
		} catch (IOException | SecurityException | IllegalArgumentException e) {
				
			System.err.println(e.getMessage());	
		}
		
		return socket;
		
	}// end connect method
	
    //***************************************************************
    //
    //  Method:       sendRequest (Non Static)
    // 
    //  Description:  Establishes I/O for the socket connection and 
    //                prompts user for data to send to the server.
    //                The user can make as many requests as they wish
    //                until a sentinel value of "bye" is entered.
    //
    //  Parameters:   Socket
    //
    //  Returns:      N/A
    //
    //**************************************************************
	public void sendRequest(Socket socket) {
		
		try {
			
			// socket input and output
			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			
			Scanner scanner = new Scanner(System.in);
			String request; // to send to server
			String response; // to hold the server's response
			
			do { // send requests until user quits
				
				System.out.println("Enter 3 integers: ");
				request = scanner.nextLine().toLowerCase(); // get user input
				writer.println(request); // send input (request) to server
				
				// process multi-line response
				if(!request.equals("bye")) { 
					response = buffer.readLine();
					while(!response.equals("END")) {
						System.out.println(response);
						response = buffer.readLine();
					}
				}
				
			} while (!request.equals("bye")); // repeat until "bye" entered by user
			
			//close resources
			scanner.close();
			socket.close();
			System.out.println("[ CLOSED SOCKET ]");
	
		} catch (IOException e) {
			
			System.out.println(e.getMessage());
		
		} catch (NoSuchElementException | IllegalStateException e) {
			
			System.out.println(e.getMessage());
		}
	} // end sendRequest method
	
    //***************************************************************
    //
    //  Method:       instructions (Non Static)
    // 
    //  Description:  Displays user instructions.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
	public void instructions() {
		
		System.out.println("\n[ INSTRUCTIONS ]");
		System.out.println("----------------------------------------------");
		System.out.println("Enter 3 integers seperated by a space.");
		System.out.println("The first integer must be less than the second.");
		System.out.println("The third integer must be a 1 (odd) or 2 (even).");
		System.out.println("All integers must be greater than zero.");
		System.out.println("Enter 'bye' to quit");
		System.out.println("----------------------------------------------");
		
	}// end instructions method
	
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
    
}// end Client class