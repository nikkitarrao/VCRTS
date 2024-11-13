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
	
	public static void startServer() {
	    String messageIn = "";
	    String messageOut = "";

	    try {
	        System.out.println("----------$$$ This is server side $$$--------");
	        System.out.println("Waiting for client to connect...");

	        // Start the server
	        serverSocket = new ServerSocket(12346);
	        socket = serverSocket.accept();
	        System.out.println("Client connected!");


	        while (!messageIn.equals("exit")) {
	            messageIn = inputStream.readUTF();
	            System.out.println("Job data received from client: " + messageIn);
	            
	            System.out.println("Do you want to accept or reject the job? (yes/no):" );
	            Scanner scanner = new Scanner(System.in);
	            String decision = scanner.nextLine();
	        }
	    } catch (Exception e ) {
	        e.printStackTrace();
	    }
	}

	// Helper method to save job data to a file
	private void saveJobToFile(String jobData) {
		//let's change the .txt to out user2 textfile. Just using this one to test everything out
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("AcceptedJobs.txt", true))) {
	        writer.write(jobData);
	        writer.newLine();
	        System.out.println("Job saved to file: AcceptedJobs.txt");
	    } catch (IOException e) {
	        System.out.println("Error saving job to file: " + e.getMessage());
	    }
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
