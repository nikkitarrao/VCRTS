import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class Client extends User {
	private ArrayList<Client> acceptedClient;
    private String email;
    private String company;
    private String jobDuration;
    private String deadline;
    private String timeStamp;
    private boolean jobAccepted = false;
    private static final int TIMEOUT_MS = 1000; //10 seconds 


    public Client(String id, String name, String password, String email, String company, String jobDuration, String deadline,  String timeStamp, boolean jobAccepted) {
        super(id, name, password);
        this.email = email;
        this.company = company;
        this.jobDuration = jobDuration;
        this.deadline = deadline;
        this.timeStamp = timeStamp;
        this.jobAccepted = jobAccepted;
    }

    public String getEmail() { return email; }
    public String getCompany() { return company; }
    public String getJobDuration() { return jobDuration; }
    public String getDeadline() { return deadline; }
    public String getTimeStamp() { return timeStamp; }
    public boolean isJobAccepted() { return jobAccepted; }

    public String toString() {
        return "Client{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", jobDuration='" + jobDuration + '\'' +
                ", deadline='" + deadline + '\'' +
                ", timestamp='" + timeStamp + '\'' +
                ", jobstatus='" + jobAccepted + '\'' +
                '}';
    }
    
    public void talkToServer(String jobDetails) {
    	Socket socket = null;
        DataInputStream inputStream = null;
        DataOutputStream outputStream = null;
        
        try {
            System.out.println("[CLIENT] Attempting to connect to server...");
            socket = new Socket("localhost", 1010);
            socket.setSoTimeout(TIMEOUT_MS);  // Set the timeout
            System.out.println("[CLIENT] Socket created successfully");
            
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("[CLIENT] Streams established");

            System.out.println("[CLIENT] Sending job details: " + jobDetails);
            outputStream.writeUTF(jobDetails);
            outputStream.flush();
            System.out.println("[CLIENT] Job details sent, waiting for response...");

            String response = inputStream.readUTF();
            System.out.println("[CLIENT] Received response from server: " + response);

            // Update job status based on response
            jobAccepted = response.contains("Accepted");
            
           
            
            // Show detailed notification to user
            SwingUtilities.invokeLater(() -> {
                // Create a custom dialog with the response message
                JDialog dialog = new JDialog();
                dialog.setTitle(jobAccepted ? "Accepted" : "Rejected");
                dialog.setModal(true);
                dialog.setSize(400, 150);
                dialog.setLocationRelativeTo(null);

                JPanel panel = new JPanel(new BorderLayout(10, 10));
                panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                
                JTextArea messageArea = new JTextArea(response);
                messageArea.setWrapStyleWord(true);
                messageArea.setLineWrap(true);
                messageArea.setOpaque(false);
                messageArea.setEditable(false);
                messageArea.setFont(new Font("Arial", Font.PLAIN, 14));

                JButton okButton = new JButton("OK");
                okButton.addActionListener(e -> dialog.dispose());

                panel.add(messageArea, BorderLayout.CENTER);
                panel.add(okButton, BorderLayout.SOUTH);

                dialog.add(panel);
                dialog.setVisible(true);
            });

        } catch (IOException e) {
            System.err.println("[CLIENT] Error: " + e.getMessage());
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null,
                    "Job Received! Waiting for server response",
                    "Job Submission",
                    JOptionPane.INFORMATION_MESSAGE);
            });
        } finally {
            try {
                System.out.println("[CLIENT] Cleaning up connections...");
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
                if (socket != null) socket.close();
                System.out.println("[CLIENT] Connections cleaned up successfully");
            } catch (IOException e) {
                System.err.println("[CLIENT] Error cleaning up: " + e.getMessage());
            }
        }
    }

  
    public String checkJobStatus(Job job) {
        return job.getStatus();
    }
    
   
}