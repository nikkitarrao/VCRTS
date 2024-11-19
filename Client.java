import java.awt.CardLayout;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.JPanel;

public class Client extends User{
	
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String jobDuration;
    private String clientID;

    // ArrayLists to hold account information
    private ArrayList<String> vehicleOwnerInfo = new ArrayList<>();
    public ArrayList<Client> clientInfo = new ArrayList<>();
    private ArrayList<String> cloudControllerInfo = new ArrayList<>();
    public Queue<Integer>jobDurations = new LinkedList<>();
    ArrayList<String> completionTime = new ArrayList<String>();

    private String email;
	private String company;
	private String duration;
	private String deadline;




	public Client(String id, String name, String password, String email, String company, String duration, String deadline) {
		super(id, name, password);
		this.email = email;
		this.company = company;
		this.duration=duration;
		this.deadline = deadline;
	

	}
	
	public String getEmail() {
		return email;
	}

	public String getCompany() {
		return company;
	}


	public String getDuration() {
		return duration;
	}

	public String getDeadline() {
		return deadline;
	}
	
	public String toString() {
        return "Client{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", jobDuration='" + duration + '\'' +
                ", deadline='" + deadline + '\'' +
                '}';
    }


	/*public void talkToServer(String jobDetails) {
        try (Socket socket = new Socket("localhost", 1010); // Connect to server at localhost:12345
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {

            System.out.println("Connected to server!");

            // Send job details to the server
            outputStream.writeUTF(jobDetails);
            outputStream.flush();

            // Read response from the server
            String response = inputStream.readUTF();
            System.out.println("Response from server: " + response);

        } catch (IOException e) {
            System.err.println("Error communicating with server: " + e.getMessage());
        }
    }*/


	public void submitJob(int duration, Queue<Integer>jobDurations) throws FileNotFoundException {
		
		   try {
		    	if(!jobDurations.offer(duration)) {
		    		System.err.println("Failed to add job duration: Queue is full");
		    		return;
		    	}
		    	System.out.println("Submitted: " + duration);
		    	
		        // Write job information to file
		        try (PrintWriter output = new PrintWriter(new FileWriter("Jobs.txt", true))) {
		            output.println("Job Information: " + jobDurations);
		            output.println("");
		        }
		    

		        // Communicate with server
		      // talkToServer();
		    } catch (IOException e) {
		        System.err.println("I/O Error: " + e.getMessage());
		   } 
			catch (Exception e) {
		        e.printStackTrace();
		    }
		

}
	
	
	
	public String checkJobStatus(Job job) {
		return job.getStatus();
	}
	
	
		
		
		
	
	
	
}
