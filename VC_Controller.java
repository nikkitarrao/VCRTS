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
	
	public void startServer() {
		System.out.println("----------$$$ This is server side $$$--------");
		System.out.println("wating for client to connect...");
		while(true) {
			try {
				Socket clientSocket = serverSocket.accept();//accepts the client
			}
			catch(IOException e){
				System.out.println("Error" + e.getMessage());
			}
			
		}
	}
	
	
	public void collectJobs() {
		
	}
	
	//compute completion time
	//duration length + duration length in the queue
	public ArrayList<String> computeCompletionTime(Queue<Integer>jobs) {
		int totalDuration = 0;
		ArrayList<String> computedTimes = new ArrayList<String>();
		while (!jobs.isEmpty()) {
            // Add the head of the queue to the cumulative sum
            totalDuration += jobs.poll(); //.poll() deletes the value in the queue that it has already read through
            computedTimes.add(totalDuration + " mins");
            System.out.println("Completion time (in order of first to last job): " + totalDuration + " mins");
            System.out.println("times: " + computedTimes);
            
        }
		return computedTimes ;
		
	}
	
	


}
