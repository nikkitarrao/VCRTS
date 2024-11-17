import java.io.IOException;
import java.net.*;
import java.util.*;

import javax.swing.JTextArea;

import java.io.*;

public class VC_Controller extends User {
	private static JTextArea outputArea;
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
	    try (ServerSocket serverSocket = new ServerSocket(1010)) {
	        System.out.println("Server started and waiting for clients...");
	        while (true) {
	            Socket socket = serverSocket.accept();
	            System.out.println("Client connected!");
	            // Create a new thread for each client
	            new Thread(new ClientHandler(socket)).start();
	        }
	    } catch (IOException e) {
	        System.err.println("Server error: " + e.getMessage());
	    }
	}

	static class ClientHandler implements Runnable {
	    private Socket socket;

	    public ClientHandler(Socket socket) {
	        this.socket = socket;
	    }

	    @Override
	    public void run() {
	        try (DataInputStream inputStream = new DataInputStream(socket.getInputStream());
	             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {

	            String messageIn = inputStream.readUTF();
	            System.out.println("Job data received from client: " + messageIn);

	            System.out.println("Do you want to accept or reject the job? (yes/no):");
	            Scanner scanner = new Scanner(System.in);
	            String decision = scanner.nextLine();

	            if (decision.equalsIgnoreCase("yes")) {
	                outputStream.writeUTF("Accepted");
	                saveJobData(messageIn);
	            } else {
	                outputStream.writeUTF("Rejected");
	            }

	        } catch (IOException e) {
	            System.err.println("Error handling client: " + e.getMessage());
	        }
	    }
        }

        // Helper method to save job data to a file
        private static void saveJobData(String jobData) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("AcceptedJobs.txt", true))) {
                writer.write(jobData);
                writer.newLine();
            } catch (IOException e) {
                System.err.println("Error saving job to file: " + e.getMessage());
            }
        }
    


	public ArrayList<String> computeCompletionTime(Queue<Integer> jobDurations) {
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
