import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ServerTester {
	
	public static void startServer() {
	    try (ServerSocket serverSocket = new ServerSocket(1111)) {
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

	            System.out.println("Do you want to accept the request?? (yes/no):");
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

	public static void main(String[] args)throws FileNotFoundException {

		JFrame frame = new JFrame("Vehicular Cloud Real-Time System Console");
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
       // welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS)); 
       

        // Create the welcome panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(175, 20, 20, 20));
        
        Color specifcColor = new Color(199, 230, 246);
        welcomePanel.setBackground(specifcColor);
  
        
        JLabel titleLabel = new JLabel("Vehicular Cloud Real-Time System Console", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 25));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel descriptionLabel = new JLabel(
                "<html><p style='text-align: center;'>"
                + "The Vehicular Cloud Real-Time System (VCRTS) manages and organizes "
                + "computation resources in a vehicular cloud.<br><br>"
                + "We allow owners of smart vehicles equipped with computing capabilities to rent out their "
                + "vehicle's computing power and clients to submit jobs for cloud-based computation."
                + "</p></html>", JLabel.CENTER);
            descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton createAccountButton = new JButton("Create an Account");
            createAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton signInButton = new JButton("Sign In");
            signInButton.setAlignmentX(Component.CENTER_ALIGNMENT);

       
        welcomePanel.add(titleLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 20)));  // Spacing
        welcomePanel.add(descriptionLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 30)));  // Spacing
        welcomePanel.add(createAccountButton);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Spacing
        welcomePanel.add(signInButton);

        // Add welcome panel to main panel
        mainPanel.add(welcomePanel, "Welcome");
        
        // Create ClickListener and add the create account panel
        ClickListener listener = new ClickListener(mainPanel, cardLayout);
       
        
        mainPanel.add(listener.createAccount(), "CreateAccount");// Add create account panel
        mainPanel.add(listener.signIn(), "SignIn"); // Add sign in panel
        mainPanel.add(listener.cloudController(), "CloudController"); // Add Cloud Controller panel
        mainPanel.add(listener.user2(), "User2"); // Add User2 panel
        mainPanel.add(listener.user1(), "User1"); // Add User1 panel 
        
      /*  //server
        ServerGUI serverGUI = new ServerGUI(cardLayout, mainPanel);
        mainPanel.add(serverGUI.getPanel(), "ServerGUI");
        
        //client
        ClientGUI clientGUI = new ClientGUI(cardLayout, mainPanel);
        mainPanel.add(clientGUI.getPanel(), "ClientGUI");
        */

        // Set up the frame
        frame.add(mainPanel);
        frame.setSize(650, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Add action listeners
        createAccountButton.addActionListener(listener);
        signInButton.addActionListener(listener);
        ServerTester.startServer();
       
	}

}
