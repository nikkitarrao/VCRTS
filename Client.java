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

	
	public void connectToServer() {
        try {
        	//connecting to server
            socket = new Socket("localhost", 3000);
            
            //client reads message from server
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public Client(String id, String name, String password, String email, String company, String jobDuration, String deadline) {
		super(id, name, password);
		this.email = email;
		this.company = company;
		this.jobDuration=jobDuration;
		this.deadline = deadline;
	
		//jobs = new LinkedList<>();
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getJobDuration() {
		return jobDuration;
	}

	public void setJobDuration(String jobDuration) {
		this.jobDuration = jobDuration;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public void submitJob(int duration, Queue<Integer>jobs) throws FileNotFoundException {
	try {
		//Sending job info to server
		outputStream.writeUTF(getName());
		outputStream.writeUTF(getId());
		outputStream.writeUTF(company);
		outputStream.writeUTF(email);
		outputStream.writeUTF(company);
		outputStream.writeUTF(jobDuration);
		outputStream.writeUTF(deadline);
		
		// Getting response from server
        String vc_response = inputStream.readUTF();
        
        if(vc_response.equalsIgnoreCase("Accepted")){
        	jobs.add(duration);
    		System.out.println("Submit" + jobs);
    		
    		//store to file
            PrintStream output = new PrintStream(new File("Jobs.txt"));
            
          //prints info gathered to printstream output folder
            output.println("Job Information: " + jobs);
            output.println("");
            
            System.out.println("Job accepted by VC Controller");      
            }
        else {
        	System.out.println("Job Rejected");
        }
        

    }
	catch (IOException e) {
		System.out.println("Failure connecting to server");
		e.printStackTrace();
	}
	
		
		
	/**
		// adds element to the end of the list
		jobs.add(duration);
		System.out.println("Submit" + jobs);


		//store to file
    	PrintStream output = new PrintStream(new File("Jobs.txt"));
    	//prints info gathered to printstream output folder
      	output.println("Job Information: " + jobs);
      	output.println("");
      	**/
	}
	
	
	public String checkJobStatus(Job job) {
		return job.getStatus();
	}
	
	
		
		
		
		
	
	
	
}
