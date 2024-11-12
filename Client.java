import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
	
		//jobs = new LinkedList<>();
	}
	
	public void submitJob(int duration, Queue<Integer>jobs) throws FileNotFoundException {
		// adds element to the end of the list
		jobs.add(duration);
		System.out.println("Submit" + jobs);


		//store to file
    	PrintStream output = new PrintStream(new File("Jobs.txt"));
    	//prints info gathered to printstream output folder
      	output.println("Job Information: " + jobs);
      	output.println("");
	}
	
	public String checkJobStatus(Job job) {
		return job.getStatus();
	}
	
	
		
		
		
		
	
	
	
}
