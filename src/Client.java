import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class Client extends User {
    private String email;
    private String company;
    private String jobDuration;
    private String deadline;
    private boolean jobAccepted = false;

    public Client(String id, String name, String password, String email, String company, String jobDuration, String deadline) {
        super(id, name, password);
        this.email = email;
        this.company = company;
        this.jobDuration = jobDuration;
        this.deadline = deadline;
    }

    public String getEmail() { return email; }
    public String getCompany() { return company; }
    public String getJobDuration() { return jobDuration; }
    public String getDeadline() { return deadline; }
    public boolean isJobAccepted() { return jobAccepted; }

    public String toString() {
        return "Client{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", jobDuration='" + jobDuration + '\'' +
                ", deadline='" + deadline + '\'' +
                '}';
    }

    public void talkToServer(String jobDetails) {
        try (Socket socket = new Socket("localhost", 1010);
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {

            System.out.println("Connected to server!");

            // Send job details to server
            outputStream.writeUTF(jobDetails);
            System.out.println("Sent job details to server");

            // Wait for controller response
            String response = inputStream.readUTF();
            System.out.println("Server response: " + response);

            jobAccepted = response.equals("Accepted");
            
            // Show response to user
            JOptionPane.showMessageDialog(null, 
                "Request " + response,
                "Controller Response",
                jobAccepted ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);

        } catch (IOException e) {
            System.err.println("Error communicating with server: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                "Error connecting to server: " + e.getMessage(),
                "Connection Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void submitJob(int duration, Queue<Integer> jobDurations) throws FileNotFoundException {
        try {
            if (!jobDurations.offer(duration)) {
                System.err.println("Failed to add job duration: Queue is full");
                return;
            }
            System.out.println("Submitted: " + jobDuration);
            
            // Write job information to file if accepted by controller
            if (jobAccepted) {
                try (PrintWriter output = new PrintWriter(new FileWriter("Jobs.txt", true))) {
                    output.println("Job Information:");
                    output.println("Client ID: " + getId());
                    output.println("Name: " + getName());
                    output.println("Company: " + getCompany());
                    output.println("Duration: " + jobDuration);
                    output.println("Deadline: " + deadline);
                    output.println("------------------------");
                }
            }
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