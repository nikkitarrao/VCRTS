import java.io.IOException;
import java.net.*;
import java.util.*;
import java.io.*;

public class VC_Controller extends User {
	private int redundancyLevel;
	private ArrayList<Job> activeJobs; //queue/linked list
	private ArrayList<Job> completedJobs;
	
	//for the client server
	static ServerSocket serverSocket;
	static Socket socket;
	static DataInputStream inputStream;
	static DataOutputStream outputStream;

	public VC_Controller(String id, String name, String password) {
		super(id, name, password);
		activeJobs = new ArrayList<>();
		completedJobs = new ArrayList<>();
		//serverSocket = new ServerSocket(port); //start server on port
	}
	
	public void startServer() throws IOException {
		
		

		
		String messageIn = "";
		String messageOut = "";
		Scanner keyInput;

		try {

			System.out.println("----------$$$ This is server side $$$--------");
			System.out.println("wating for client to connect...");
			// creating the server
			serverSocket = new ServerSocket(9807);
			
			 socket = serverSocket.accept();
		     System.out.println("client is connected!");
		     System.out.println("go to client side and send me a message");

			// server reads a message message from client
			inputStream = new DataInputStream(socket.getInputStream());

			// server sends a message to client
			outputStream = new DataOutputStream(socket.getOutputStream());

			// as long as message is not exit keep reading and sending message to client
			while (!messageIn.equals("exit")) {

				// extract the message from client
				messageIn = inputStream.readUTF();
				// server prints the message received from client to console
				System.out.println("message received from client: " + "\"" + messageIn + "\"");

				// ********************************************************
				// server reads a message from keyboard
				System.out.println("Enter a message you want to send to client side: ");
				keyInput = new Scanner(System.in);
				messageOut = keyInput.nextLine();
				// server sends the message to client
				outputStream.writeUTF(messageOut);
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	
	
	
	public void collectJobs() {
		
	}
	
	//compute completion time
	//duration length + duration length in the queue
	public ArrayList<String> computeCompletionTime(Queue<Integer>jobDurations) {
		int totalDuration = 0;
		ArrayList<String> computedTimes = new ArrayList<String>();
		while (!jobDurations.isEmpty()) {
            // Add the head of the queue to the cumulative sum
            totalDuration += jobDurations.poll(); //.poll() deletes the value in the queue that it has already read through
            computedTimes.add(totalDuration + " mins");
            System.out.println("Completion time (in order of first to last job): " + totalDuration + " mins");
            System.out.println("times: " + computedTimes);
            
        }
		return computedTimes ;
		
	}
	
	
	
	


}
