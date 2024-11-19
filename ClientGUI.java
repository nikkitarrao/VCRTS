import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*public class ClientGUI implements ActionListener{
	public ArrayList<Client> clientInfo = new ArrayList<>();
	 public Queue<Integer>jobDurations = new LinkedList<>();
	CardLayout cardLayout;
    JPanel mainPanel;
    JPanel clientPanel;
    JButton backButton, submitButton;
    String messageIn;
    String jobDetails;
    
  //for the client server
  	static ServerSocket serverSocket = null;
  	static Socket socket;
  	static DataInputStream inputStream;
  	static DataOutputStream outputStream;
    
    public ClientGUI(CardLayout cardLayout, JPanel mainPanel) throws FileNotFoundException {
    	this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        
        clientPanel = new JPanel();
        clientPanel.setLayout(new BoxLayout(clientPanel, BoxLayout.Y_AXIS));
        clientPanel.setBorder(BorderFactory.createEmptyBorder(175, 20, 20, 20));
        clientPanel.setBackground(new Color(199, 230, 246));
        
        JLabel label = new JLabel("Create User 2 Account");
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    	clientPanel.add(label);
    	
    	
        clientPanel.add(new JLabel("Enter the following information below"));
        
        Dimension format = new Dimension(400,27);
        
        JTextField t1 = new JTextField();
        t1.setPreferredSize(format); 

        JPasswordField t2 = new JPasswordField();
        t2.setPreferredSize(format);
      
        
        JTextField t3 = new JTextField();
        t3.setPreferredSize(format);

        JTextField t4 = new JTextField();
        t4.setPreferredSize(format);

        JTextField t5 = new JTextField();
        t5.setPreferredSize(format);

        JTextField t6 = new JTextField();
        t6.setPreferredSize(format);

        JTextField t7 = new JTextField();
        t7.setPreferredSize(format);
       

        //new Dimension(200, 30)
        
        

    	JPanel clientIdPanel = new JPanel();
    	clientIdPanel.add(new JLabel("Client ID: "));
    	clientIdPanel.add(t1);
    	clientPanel.add(clientIdPanel);
    	
    	
    	JPanel fullNamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	fullNamePanel.add(new JLabel("Full Name: "));
    	fullNamePanel.add(t7);
    	clientPanel.add(fullNamePanel);
        
    	JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	passwordPanel.add(new JLabel("Password: "));
    	passwordPanel.add(t2);
    	clientPanel.add(passwordPanel);
    	
    	JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	emailPanel.add(new JLabel("Email: "));
    	emailPanel.add(t3);
    	clientPanel.add(emailPanel);
    	
    	JPanel companyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	companyPanel.add(new JLabel("Company/Organization: "));
    	companyPanel.add(t4);
    	clientPanel.add(companyPanel);
    
    	JPanel jobDurationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jobDurationPanel.add(new JLabel("Approx Job Duration (in mins): "));
        jobDurationPanel.add(t5);
    	clientPanel.add(jobDurationPanel);
        
        JPanel deadlinePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        deadlinePanel.add(new JLabel("Job Deadline (MM-DD-YYYY): "));
        deadlinePanel.add(t6);
    	clientPanel.add(deadlinePanel);
  
        
        // File reading operations
    	PrintStream output = new PrintStream(new File("clientOut.txt"));

    	
    	//Submit Button
    	JPanel submitButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	submitButton = new JButton("Submit");
    	submitButton.setPreferredSize(new Dimension(100,40));
    	submitButton.addActionListener((ActionListener) this);

    	submitButtonPanel.add(submitButton);

    	clientPanel.add(submitButtonPanel);
    	
    	//Back Button
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 40)); // Set preferred size if needed
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "CreateAccount"));

        // Add the button to the wrapper panel
        backButtonPanel.add(backButton);
        clientPanel.add(backButtonPanel); 
        
        
      //line break to make more neat
       clientPanel.setLayout(new BoxLayout(clientPanel, BoxLayout.Y_AXIS)); 
        
      //event listener on submit button 
        submitButton.addActionListener(e -> {
        	//retrieves the info from the input fields 
        	String clientID = t1.getText();
        	String password = t2.getText();
        	String email = t3.getText();
        	String company = t4.getText();
        	String jobDuration = t5.getText();
        	String deadline = t6.getText();
        	String name = t7.getText();
        	String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(0));
        	
        	//check if fields are empty
 	       if(clientID.isEmpty() || password.isEmpty() || email.isEmpty() || company.isEmpty()|| jobDuration.isEmpty() || deadline.isEmpty() ||name.isEmpty()) {
 	    		   JOptionPane.showMessageDialog(new JFrame("Error"), "Missing Infomation");
 	       } else {
 	    	 	//save it into the arraylist
 	    	   Client client = new Client (clientID, name, password, email, company, jobDuration, deadline);
 	        	clientInfo.add(client);
 	        	jobDetails = "Client ID: " + clientID + "\nJob Duration:  " + jobDuration + "\nDeadline: " + deadline + "\n";
 	        	System.out.println(jobDetails);
 	        	
 	        	ClientTester.talkToServer(jobDetails);
 	       
 	        	
 	        	int duration = Integer.parseInt(jobDuration);
 	        	System.out.print("Clients: " + clientInfo);
 	        	
 	        	
 	        	
 	        	try {
					client.submitJob(duration, jobDurations);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
 	        	
 	        
 	        	
 	        	
 	        		//prints info gathered to printstream output folder
 	 	        	output.println("Client ID: " + clientID + ", ");
 	 	        	output.println("Full Name: " + name + ", ");
 	 	        	output.println("Password: " + password + ", ");
 	 	        	output.println("Email: " + email + ", ");
 	 	        	output.println("Company: " + company + ", ");
 	 	        	
 	 	        	output.println("Duration (mins): " + duration + ", ");
 	 	        	output.println("Deadline: " + deadline + ", ");
 	 	        	output.println("Timestamp: " + timestamp + ", ");
 	 	        	output.println("");
 	        
 	        	

 	        
 	        	
 	        	t1.setText("");
 	        	t2.setText("");
 	        	t3.setText("");
           		t4.setText("");
           		t5.setText("");
           		t6.setText("");
           		t7.setText("");

           	
 	        	// Stays on the same page
           		JOptionPane.showMessageDialog(null, "Job Submitted Successfully ","Alert", JOptionPane.INFORMATION_MESSAGE);	
 	       }
  
        });
        
        
    
    }
    
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == submitButton) {
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

    	        } catch (IOException ex) {
    	            System.err.println("Error communicating with server: " + ex.getMessage());
    	        }
    		
    	}
    }
    
    public JPanel getPanel() {
        return clientPanel;
    }
}*/
