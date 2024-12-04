import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class VehicleOwner extends User{
    private String email;
    private String phoneNumber;
    private String vehicleResidencyTime;
    private Vehicle vehicle;
    private String timeStamp;
    private boolean carAccepted = false;
    private static final int TIMEOUT_MS = 2000; //10 seconds 
    
  //for the client server
  	static ServerSocket serverSocket;
  	static Socket socket;
  	static DataInputStream inputStream;
  	static DataOutputStream outputStream;
    
    // Constructor
    public VehicleOwner(String id, String name, String password,  String email, String phoneNumber, String vehicleResidencyTime, String timeStamp, Vehicle vehicle) {
    	super(id, name, password);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.timeStamp = timeStamp;
        this.vehicle = vehicle;
    }

    // Getters and Setters (optional if you need to access or modify the fields later)
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
    
    public String getTimeStamp() {
        return timeStamp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
   
    public void setPassword(String password) {
    	this.password=password;
    }
    public void setResidencyTime(String vehicleResidencyTime) {
    	this.vehicleResidencyTime = vehicleResidencyTime;
    }
    public String vehicleResidencyTime() {
		return vehicleResidencyTime;
    	
    }
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    public void rentVehicle(Vehicle vehicle) {
    	
    }
    
    public void talkToServer(String carDetails) {
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

            System.out.println("[CLIENT] Sending car details: " + carDetails);
            outputStream.writeUTF(carDetails);
            outputStream.flush();
            System.out.println("[CLIENT] Car details sent, waiting for response...");

            String response = inputStream.readUTF();
            System.out.println("[CLIENT] Received response from server: " + response);

            // Update job status based on response
            carAccepted = response.contains("Accepted");
            
            // Show detailed notification to user
            SwingUtilities.invokeLater(() -> {
                // Create a custom dialog with the response message
                JDialog dialog = new JDialog();
                dialog.setTitle(carAccepted ? "Car Accepted" : "Car Rejected");
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
                    "Car Recieved! Check Submission",
                    "Car Submission",
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
    
   

    // Optional: A toString method for easy printing of the user information
    @Override
    public String toString() {

        return "Owner [Name=" + name + ", ID=" + getId() + ", Phone=" + phoneNumber + ", timestamp=" + getTimeStamp() + ", " + vehicle;
    }
}
