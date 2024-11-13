import java.io.*;
import java.net.*;
import java.util.*;

public class Client extends User{
	private String email;
	private String company;
	private String jobDuration;
	private String deadline;
	
	//for the client server
	static ServerSocket serverSocket;
	static Socket socket;
	static DataInputStream inputStream;
	static DataOutputStream outputStream;



	public Client(String id, String name, String password, String email, String company, String jobDuration, String deadline) {
		super(id, name, password);
		this.email = email;
		this.company = company;
		this.jobDuration=jobDuration;
		this.deadline = deadline;
	

	}
	
	public String getEmail() {
		return email;
	}

	public String getCompany() {
		return company;
	}


	public String getJobDuration() {
		return jobDuration;
	}

	public String getDeadline() {
		return deadline;
	}

	public void talkToServer() {
	    String messageIn = "";
	    String messageOut = "";

	    try {
	        System.out.println("----------*** This is client side ***--------");
	        System.out.println("Client started!");

	        // Connect to the server
	        socket = new Socket("localhost", 12346);

	        // Set up input and output streams
	        inputStream = new DataInputStream(socket.getInputStream());
	        outputStream = new DataOutputStream(socket.getOutputStream());
	        
	        System.out.println("Enter Job Details to send to server: ");
	        Scanner in = new Scanner(System.in);
	        messageOut = in.nextLine();
	        
	        outputStream.writeUTF(messageOut);
	        
	        outputStream.flush();

	        messageIn = inputStream.readUTF();
	        System.out.println("Response from server: " + messageIn);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public void submitJob(int duration, Queue<Integer>jobDurations) throws FileNotFoundException {
		
			/*jobDurations.add(duration);
			System.out.println("submitted: " + jobDurations);*/
		
		    try {
		    	if(!jobDurations.offer(duration)) {
		    		System.err.println("Failed to add job duration: Queue is full");
		    		return;
		    	}
		    	System.out.println("Submitted: " + jobDurations);
		    	
		        // Write job information to file
		        try (PrintWriter output = new PrintWriter(new FileWriter("Jobs.txt", true))) {
		            output.println("Job Information: " + jobDurations);
		            output.println("");
		        }

		        // Communicate with server
		        talkToServer();
		    } catch (IOException e) {
		        System.err.println("I/O Error: " + e.getMessage());
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		

	}
	
	
	public String checkJobStatus(Job job) {
		return job.getStatus();
	}
	
	
		
		
		
		
	
	
	
}
