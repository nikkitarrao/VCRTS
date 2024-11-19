import java.awt.*;

import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.Duration;

import javax.swing.*;
/*
 * Project: Stage 2 
 * Class: CUS 1166
 * Authors: Nikkita Tarrao,Shivanni Rambaran, Payal Moorti, Lorena Vazquez, Jenn Venus
 * 10.8.2024
 * This program is stage 2 of our VCRTS project. The ClickListener class has our arrayLists and initializations. Essentially, this class is where 
 * we created methods for each of our users with the information they are collecting as well as the printwriter to store the information from
 * the arraylists we created
 */



public class ClickListener implements ActionListener {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String jobDuration;
    private String clientID;
    
    //added from vc controller
    static ServerSocket serverSocket;
	static Socket socket;
	static DataInputStream inputStream;
	static DataOutputStream outputStream;
    

    // ArrayLists to hold account information
    private ArrayList<String> vehicleOwnerInfo = new ArrayList<>();
    public ArrayList<Client> clientInfo = new ArrayList<>();
    private ArrayList<String> cloudControllerInfo = new ArrayList<>();
    public Queue<Integer>jobDurations = new LinkedList<>();
    ArrayList<String> completionTime = new ArrayList<String>();

    // Constructor to initialize the main panel and layout
    public ClickListener(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
    	//getActionCommand method returns the name associated with the event (i.e. the name of the button, etc.)
        String command = event.getActionCommand(); 
        //if the create account button is clicked
        if (command.equals("Create an Account")) {
        	//cardlayout will display the create account method display on the main panel
            cardLayout.show(mainPanel, "CreateAccount");
            if(command.equals("Cloud Controller")) {
            	//cardlayout will display the cloud controller method display on the main panel
            	cardLayout.show(mainPanel, "CloudController");
            } else if(command.equals("Register a vehicle")) {
            	//cardlayout will display the user1 method display on the main panel
            	//cardLayout.show(mainPanel, "User1");
            	} else {
            		//cardlayout will display the user2 method display on the main panel
            		//cardLayout.show(mainPanel, "User2");
            }
        } else if (command.equals("Sign In")) {
        	cardLayout.show(mainPanel, "SignIn");
        }
    }
    
    public JPanel signIn() {
        JPanel signInPanel = new JPanel(new BorderLayout());
        signInPanel.setLayout(new BoxLayout(signInPanel, BoxLayout.Y_AXIS));
        signInPanel.setBorder(BorderFactory.createEmptyBorder(175, 20, 20, 20));
        
        //Establish background color
        Color specificColor = new Color(199, 230, 246);
        signInPanel.setBackground(specificColor);
        
        JLabel signInLabel = new JLabel("<html><b><u>Sign In</u></b></html>", JLabel.CENTER);
        signInLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        signInLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
     
        JLabel label = new JLabel("ID: ");
        JLabel label2= new JLabel("Password: ");
        JButton button = new JButton("Submit");
        JButton backButton = new JButton("Back");
        JTextField t1 = new JTextField(20);
    	JPasswordField t2 = new JPasswordField(20);
 
    	
    	//Adjusting JPanel Size
    	t1.setMinimumSize(new Dimension(200,15));
    	t1.setMaximumSize(new Dimension (300,35));
    	
    	t2.setMinimumSize(new Dimension(200,15));
    	t2.setMaximumSize(new Dimension (300,35));
    	
    	signInLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    	label.setAlignmentX(Component.CENTER_ALIGNMENT);
    	t1.setAlignmentX(Component.CENTER_ALIGNMENT);
    	label2.setAlignmentX(Component.CENTER_ALIGNMENT);
    	t2.setAlignmentX(Component.CENTER_ALIGNMENT);
    	button.setAlignmentX(Component.CENTER_ALIGNMENT);
    	backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    	
        //adding stuff to the panel
        signInPanel.add(signInLabel);
        signInPanel.add(Box.createVerticalStrut(15));  // Space between "Sign In" and "ID"
        signInPanel.add(label);
        signInPanel.add(t1);
        signInPanel.add(Box.createVerticalStrut(10));  // Space between ID and Password
        signInPanel.add(label2);
        signInPanel.add(t2);
        signInPanel.add(Box.createVerticalStrut(10));
        signInPanel.add(button);
        signInPanel.add(backButton);
        
        
        
      //line break to make more neat
       signInPanel.setLayout(new BoxLayout(signInPanel, BoxLayout.Y_AXIS)); 
        
      //event listener on submit button to enter account
       if(vehicleOwnerInfo.isEmpty() && vehicleOwnerInfo.isEmpty() && clientInfo.isEmpty()) {
    	   button.addActionListener(e -> {
    		   JOptionPane.showMessageDialog(new JFrame("Error"), "User not Found, Please try again");
                cardLayout.show(mainPanel, "Welcome");
            });
        //else let them sign in

       backButton.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));
       signInPanel.add(backButton);
       
       }
        //returning the panel so that it can be displayed when 'SignIn' button option is chosen 
        return signInPanel;
    }

    public JPanel createAccount() {
    	JPanel outerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	
    	JPanel createAccountPanel = new JPanel();
        createAccountPanel.setLayout(new BoxLayout(createAccountPanel, BoxLayout.Y_AXIS));
        createAccountPanel.setBorder(BorderFactory.createEmptyBorder(200, 20, 20, 20));
        createAccountPanel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Ensure the main panel itself is centered

        Color specificColor = new Color(199, 230, 246);
        outerPanel.setBackground(specificColor);
        createAccountPanel.setBackground(specificColor);
        
        JLabel label = new JLabel("<html><center><b><u>What Type of Account Would You Like to Create?</u></b></center></html>");
        label.setFont(new Font("Serif", Font.PLAIN, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center-align the label

        JButton button1 = new JButton("Register a vehicle");
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton button2 = new JButton("Submit jobs");
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton button3 = new JButton("Cloud Controller");
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //adjust button size
        Dimension buttonSize = new Dimension(200, 40); // Adjust width and height as needed
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);
        

        createAccountPanel.add(label);
        createAccountPanel.add(Box.createVerticalStrut(20)); // Add space between components
        createAccountPanel.add(button1);
        createAccountPanel.add(Box.createVerticalStrut(10));
        createAccountPanel.add(button2);
        createAccountPanel.add(Box.createVerticalStrut(10)); // Add space between components
        createAccountPanel.add(button3);
        createAccountPanel.add(Box.createVerticalStrut(20)); // Add space between components
        createAccountPanel.add(backButton);        
    
        outerPanel.add(createAccountPanel);
        
      //event listener on user 1 button
        button1.addActionListener(e -> {
        	
            cardLayout.show(mainPanel, "User1");
        });
        //event listener on user 2 button
        button2.addActionListener(e -> {
            cardLayout.show(mainPanel, "User2");
        });

        //event listener on cloud controller button
        button3.addActionListener(e -> {
            cardLayout.show(mainPanel, "CloudController");
        });
        
        backButton.addActionListener(e -> {
        	cardLayout.show(mainPanel, "Welcome");
    	});
       
        //returning the panel so that it can be displayed when 'Create Account' button option is chosen 
        return outerPanel;
    }
    
    
    public JPanel user1() throws FileNotFoundException {
    	//full name, email, phone number, username password, car make model, vin, year
    	JPanel user1Panel = new JPanel();
    	
    	   //Establish background color
        Color specificColor = new Color(199, 230, 246);
        user1Panel.setBackground(specificColor);
        
        
        //
        user1Panel.setAlignmentX(Component.CENTER_ALIGNMENT);
    	
    	
    	JLabel label = new JLabel("Create User 1 Account");
        //JLabel label = new JLabel("<html><center><b><u>Create User 1 Account</u></b></center></html>");
    	//label.setFont(new Font("Serif", Font.PLAIN, 20));
    	label.setFont(new Font("Serif", Font.BOLD, 20));
    	label.setAlignmentX(Component.CENTER_ALIGNMENT);
        user1Panel.add(label);
        
        JLabel infoText = new JLabel("Enter the following information below");
        infoText.setAlignmentX(Component.CENTER_ALIGNMENT);
        user1Panel.add(infoText);
        
        
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    	JTextField nameText = new JTextField(10);
    	JTextField userNameText = new JTextField(20);
    	JPasswordField passwordText = new JPasswordField(20);
    	JTextField emailText = new JTextField(20);
    	JTextField phoneText = new JTextField(20);
    	JTextField makeText = new JTextField(10);
    	JTextField modelText = new JTextField(10);
    	JTextField vinText = new JTextField(10);
    	JTextField yearText = new JTextField(4);
    	JTextField colorText = new JTextField(4);
    	JTextField residencyTimeText = new JTextField(10);
        //user info
    	
 	
    	
    	user1Panel.add(Box.createVerticalStrut(10));
    	JLabel userInfoLabel = new JLabel("User Information");
    	userInfoLabel.setFont(new Font("Apotos", Font.BOLD, 15));
    	userInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    	user1Panel.add(userInfoLabel);
    	
    	
    	Dimension format = new Dimension(400,27);
    	
    	//Owner Name
    	JLabel ownerNameCenter = new JLabel("Owner Name: ");
    	user1Panel.add(ownerNameCenter);
    	ownerNameCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
    	nameText.setMinimumSize(format);
    	nameText.setMaximumSize(format);
    	nameText.setAlignmentX(Component.CENTER_ALIGNMENT);
        user1Panel.add(nameText);
        
        //Username
        JLabel userNameCenter = new JLabel("Owner ID: ");
        user1Panel.add(userNameCenter);
        userNameCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
    	userNameText.setMinimumSize(format);
    	userNameText.setMaximumSize(format);
    	userNameText.setAlignmentX(Component.CENTER_ALIGNMENT);
        user1Panel.add(userNameText);
        
        //Password
        JLabel passwordCenter = new JLabel("Password: ");
        user1Panel.add(passwordCenter);
        passwordCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordText.setMinimumSize(format);
        passwordText.setMaximumSize(format);
        passwordText.setAlignmentX(Component.CENTER_ALIGNMENT);
        user1Panel.add(passwordText);
        
        //Phone Number
        JLabel numberCenter = new JLabel("Phone Number: ");
        user1Panel.add(numberCenter);
        numberCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
        phoneText.setMinimumSize(format);
        phoneText.setMaximumSize(format);
        phoneText.setAlignmentX(Component.CENTER_ALIGNMENT);
        user1Panel.add(phoneText);
        
        //Email
        JLabel emailCenter = new JLabel("Email: ");
        user1Panel.add(emailCenter);
        emailCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailText.setMinimumSize(format);
        emailText.setMaximumSize(format);
        emailText.setAlignmentX(Component.CENTER_ALIGNMENT);
        user1Panel.add(emailText);
        
        
        //car info
        user1Panel.add(Box.createVerticalStrut(10));
        
        
        //Vehicle Label
        JLabel vehicleInfoLabel = new JLabel("Vehicle Information");
        vehicleInfoLabel.setFont(new Font("Apotos", Font.BOLD, 15));
        vehicleInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    	user1Panel.add(vehicleInfoLabel);
    	
    	//Make
    	JLabel makeCenter = new JLabel("Make: ");
    	user1Panel.add(makeCenter);
    	makeCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
    	makeText.setMinimumSize(format);
    	makeText.setMaximumSize(format);
    	makeText.setAlignmentX(Component.CENTER_ALIGNMENT);
        user1Panel.add(makeText);
        
        //Model
        JLabel modelCenter = new JLabel("Model: ");
        user1Panel.add(modelCenter);
    	modelCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
    	modelText.setMinimumSize(format);
    	modelText.setMaximumSize(format);
    	modelText.setAlignmentX(Component.CENTER_ALIGNMENT);
        user1Panel.add(modelText);
        
        //VIN
        JLabel vinCenter = new JLabel("VIN: ");
        user1Panel.add(vinCenter);
        vinCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
    	vinText.setMinimumSize(format);
    	vinText.setMaximumSize(format);
    	vinText.setAlignmentX(Component.CENTER_ALIGNMENT);
        user1Panel.add(vinText);
        
        //Year
        JLabel yearCenter = new JLabel("Year: ");
        user1Panel.add(yearCenter);
        yearCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
    	yearText.setMinimumSize(format);
    	yearText.setMaximumSize(format);
    	yearText.setAlignmentX(Component.CENTER_ALIGNMENT);
        user1Panel.add(yearText);
        
        //Color
        JLabel colorCenter = new JLabel("Color: ");
        user1Panel.add(colorCenter);
        colorCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
    	colorText.setMinimumSize(format);
    	colorText.setMaximumSize(format);
    	colorText.setAlignmentX(Component.CENTER_ALIGNMENT);
        user1Panel.add(colorText);
        
        //Vehicle Residency Time
        JLabel residencyTimeCenter = new JLabel("Approximate Vehicle Residency Time (in hrs): ");
        user1Panel.add(residencyTimeCenter);
        residencyTimeCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
    	residencyTimeText.setMinimumSize(format);
    	residencyTimeText.setMaximumSize(format);
    	residencyTimeText.setAlignmentX(Component.CENTER_ALIGNMENT);
        user1Panel.add(residencyTimeText);
        
    
        
        
     // File reading operations
    	PrintStream output = new PrintStream(new File("User1Out.txt"));

        JButton submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(this);
        user1Panel.add(submitButton);

        
        JButton backButton = new JButton("Back"); 
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> {
        	cardLayout.show(mainPanel, "CreateAccount");
    	});
        user1Panel.add(backButton);
        
      //line break to make more neat
       user1Panel.setLayout(new BoxLayout(user1Panel, BoxLayout.Y_AXIS)); 
       

    	   submitButton.addActionListener(e -> {
    		
    		    //retrieves the info from the input fields 
    	   		String ownerName = nameText.getText();
    	   		String ownerID = userNameText.getText();
    	   		String pw = passwordText.getText();
    	   		String phone = phoneText.getText();
    	   		String email = emailText.getText();
    	   		String make = makeText.getText();
    	   		String model = modelText.getText();
    	   		String year = yearText.getText();
    	   		String vin = vinText.getText();
    	   		String duration = residencyTimeText.getText();
    	   		String color = colorText.getText();
    	   		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    	   		
    	   		Vehicle vehicle = new Vehicle(make, model, year, vin);
    	        //VehicleOwner owner = new VehicleOwner(ownerName,username, email, phone, pw, vin, timestamp, vehicle);

    	   	
    		 	//event listener on submit button 
    	       if(ownerName.isEmpty() || ownerID.isEmpty()||pw.isEmpty() || email.isEmpty() || make.isEmpty()|| make.isEmpty() || model.isEmpty() || year.isEmpty() || vin.isEmpty()|| color.isEmpty()|| duration.isEmpty()) {
    	    		   JOptionPane.showMessageDialog(new JFrame("Error"), "Missing Infomation");
    	       } else {
    	    	 	//save it into the arraylist
    	           	vehicleOwnerInfo.add(ownerName);
    	           	vehicleOwnerInfo.add(ownerID);
    	           	vehicleOwnerInfo.add(pw);
    	           	vehicleOwnerInfo.add(phone);
    	           	vehicleOwnerInfo.add(email);
    	           	vehicleOwnerInfo.add(make);
    	           	vehicleOwnerInfo.add(model);
    	           	vehicleOwnerInfo.add(year);
    	           	vehicleOwnerInfo.add(vin);  	
    	           	vehicleOwnerInfo.add(color);
    	           	vehicleOwnerInfo.add(duration);

    	           	//prints info gathered to printstream output folder
    	           	output.println("Owner Name: " + ownerName + ", ");
    	           	output.println("Owner ID: " + ownerID + ", ");
    	           	output.println("Password: " + pw + ", ");
    	           	output.println("Email: " + email + ", ");
    	           	output.println("Make: " + make + ", ");
    	           	output.println("Model: " + model + ", ");
    	           	output.println("Year: " + year + ", ");
    	           	output.println("VIN: " + vin + ", ");
    	           	output.println("Color: " + color + ", ");
    	           	output.println("Residency time (hrs): " + duration + ", ");
    	           	output.println("Timestamp: " + timestamp + ", ");
    	           	output.println("");
    	           	
    	           	//clear fields
    	           	
    	           	nameText.setText("");
    	           	userNameText.setText("");
    	        	passwordText.setText("");
    	           	emailText.setText("");
    	         	phoneText.setText("");
    	           	makeText.setText("");
    	        	modelText.setText("");
    	        	yearText.setText("");
    	        	vinText.setText("");
    	        	colorText.setText("");
    	           	residencyTimeText.setText("");
    	           	
    	         // Stays on the same page
    	           	JOptionPane.showMessageDialog(null, "Vehicle Submitted Successfully ","Alert", JOptionPane.INFORMATION_MESSAGE);	
    	       }
           	
           });
    	   
    		
        return user1Panel;
    }
    
    
    public JPanel user2() throws FileNotFoundException {
        JPanel user2Panel = new JPanel();
        user2Panel.setLayout(new BoxLayout(user2Panel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical stacking
        
        //Establish background color
        Color specificColor = new Color(199, 230, 246);
        user2Panel.setBackground(specificColor);
        
        JLabel label = new JLabel("Create User 2 Account");
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    	user2Panel.add(label);
    	
    	
        user2Panel.add(new JLabel("Enter the following information below"));
        
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
        
        

    	JPanel clientIdPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	clientIdPanel.add(new JLabel("Client ID: "));
    	clientIdPanel.add(t1);
    	user2Panel.add(clientIdPanel);
    	
    	
    	JPanel fullNamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	fullNamePanel.add(new JLabel("Full Name: "));
    	fullNamePanel.add(t7);
    	user2Panel.add(fullNamePanel);
        
    	JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	passwordPanel.add(new JLabel("Password: "));
    	passwordPanel.add(t2);
    	user2Panel.add(passwordPanel);
    	
    	JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	emailPanel.add(new JLabel("Email: "));
    	emailPanel.add(t3);
    	user2Panel.add(emailPanel);
    	
    	JPanel companyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	companyPanel.add(new JLabel("Company/Organization: "));
    	companyPanel.add(t4);
    	user2Panel.add(companyPanel);
    
    	JPanel jobDurationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jobDurationPanel.add(new JLabel("Approx Job Duration (in mins): "));
        jobDurationPanel.add(t5);
    	user2Panel.add(jobDurationPanel);
        
        JPanel deadlinePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        deadlinePanel.add(new JLabel("Job Deadline (MM-DD-YYYY): "));
        deadlinePanel.add(t6);
    	user2Panel.add(deadlinePanel);
  
        
        // File reading operations
    	PrintStream output = new PrintStream(new File("User2Out.txt"));

    	
    	//Submit Button
    	JPanel submitButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	JButton submitButton = new JButton("Submit");
    	submitButton.setPreferredSize(new Dimension(100,40));
    	submitButton.addActionListener(this);

    	submitButtonPanel.add(submitButton);

    	user2Panel.add(submitButtonPanel);
    	
    	//Back Button
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 40)); // Set preferred size if needed
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "CreateAccount"));

        // Add the button to the wrapper panel
        backButtonPanel.add(backButton);
        user2Panel.add(backButtonPanel); 
        
      //Establishing Background Colors
        clientIdPanel.setBackground(specificColor);
        fullNamePanel.setBackground(specificColor);
        passwordPanel.setBackground(specificColor);
        emailPanel.setBackground(specificColor);
        companyPanel.setBackground(specificColor);
        jobDurationPanel.setBackground(specificColor);
        deadlinePanel.setBackground(specificColor);
        submitButtonPanel.setBackground(specificColor);
        backButtonPanel.setBackground(specificColor);
        
        
      //line break to make more neat
       user2Panel.setLayout(new BoxLayout(user2Panel, BoxLayout.Y_AXIS)); 
        
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
        	String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        	
        	//check if fields are empty
 	       if(clientID.isEmpty() || password.isEmpty() || email.isEmpty() || company.isEmpty()|| jobDuration.isEmpty() || deadline.isEmpty() ||name.isEmpty()) {
 	    		   JOptionPane.showMessageDialog(new JFrame("Error"), "Missing Infomation");
 	       } else {
 	    	 	//save it into the arraylist
 	    	   Client client = new Client (clientID, name, password, email, company, jobDuration, deadline);
 	        	clientInfo.add(client);
 	        	String jobDetails = "Client ID: " + clientID + "\nJob Duration:  " + jobDuration + "\nDeadline: " + deadline + "\n";
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
        
        
        return user2Panel;
    }
    
    public JPanel cloudController() throws FileNotFoundException {
        JPanel cloudControllerPanel = new JPanel();
        cloudControllerPanel.setLayout(new BoxLayout(cloudControllerPanel, BoxLayout.Y_AXIS));
        
        //Establish background color
        Color specificColor = new Color(199, 230, 246);
        cloudControllerPanel.setBackground(specificColor);
        
        // Centered Title
        JLabel label = new JLabel("<html><center><b>Create Cloud Controller Account</b></center></html>");
        label.setFont(new Font("Serif", Font.PLAIN, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        cloudControllerPanel.add(label);
        
        // Centered Sub-Title
        JLabel label2 = new JLabel("<html><center><b>Authorized Users Only</b></center></html>");
        label.setFont(new Font("Serif", Font.PLAIN, 15));
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        cloudControllerPanel.add(label2);
        
        // Centered Instructions
        JLabel label3 = new JLabel("Enter the following information below");
        label3.setAlignmentX(Component.CENTER_ALIGNMENT);
        cloudControllerPanel.add(label3);
        
        cloudControllerPanel.add(Box.createVerticalStrut(10));
        
        // Text fields
        JTextField t1 = new JTextField(20);
        JTextField t2 = new JTextField(20);
        JTextField t3 = new JTextField(20);
        JTextField t4 = new JTextField(20);
        JPasswordField t5 = new JPasswordField(20);
        
        t1.setAlignmentX(Component.CENTER_ALIGNMENT);
        t2.setAlignmentX(Component.CENTER_ALIGNMENT);
        t3.setAlignmentX(Component.CENTER_ALIGNMENT);
        t4.setAlignmentX(Component.CENTER_ALIGNMENT);
        t5.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Dimension textFieldSize = new Dimension(300, 30);
        Dimension format = new Dimension(400,27);
        t1.setPreferredSize(format);
        t2.setPreferredSize(format);
        t3.setPreferredSize(format);
        t4.setPreferredSize(format);
        t5.setPreferredSize(format);

        t1.setMaximumSize(format);
        t2.setMaximumSize(format);
        t3.setMaximumSize(format);
        t4.setMaximumSize(format);
        t5.setMaximumSize(format);
        
        t1.setAlignmentX(Component.CENTER_ALIGNMENT);
        t2.setAlignmentX(Component.CENTER_ALIGNMENT);
        t3.setAlignmentX(Component.CENTER_ALIGNMENT);
        t4.setAlignmentX(Component.CENTER_ALIGNMENT);
        t5.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // File reading operations
        PrintStream output = new PrintStream(new File("CloudOut.txt"));
        
        // Adding labels and text fields
       
        
        JLabel adminCenter = new JLabel("Admin ID:");
        adminCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
        cloudControllerPanel.add(adminCenter);
        cloudControllerPanel.add(t1);
        cloudControllerPanel.add(Box.createVerticalStrut(3)); // Small spacing

        JLabel fnameCenter = new JLabel("First Name: ");
        fnameCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
        cloudControllerPanel.add(fnameCenter);
        cloudControllerPanel.add(t2);
        cloudControllerPanel.add(Box.createVerticalStrut(3));

        JLabel lnameCenter = new JLabel("Last Name: ");
        lnameCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
        cloudControllerPanel.add(lnameCenter);
        cloudControllerPanel.add(t3);
        cloudControllerPanel.add(Box.createVerticalStrut(3));

        JLabel emailCenter = new JLabel("Email: ");
        emailCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
        cloudControllerPanel.add(emailCenter);
        cloudControllerPanel.add(t4);
        cloudControllerPanel.add(Box.createVerticalStrut(3));

        JLabel passwordCenter = new JLabel("Password: ");
        passwordCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
        cloudControllerPanel.add(passwordCenter);
        cloudControllerPanel.add(t5);
        cloudControllerPanel.add(Box.createVerticalStrut(10));

        // Button panel for centering
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        buttonPanel.setBackground(specificColor);
        
        JButton submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setPreferredSize(new Dimension(100, 40));
        submitButton.addActionListener(this);
        
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "CreateAccount"));
        
        // Add horizontal glue before and after buttons to center them
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(submitButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Space between buttons
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createHorizontalGlue());

        // Add button panel to main panel
        cloudControllerPanel.add(buttonPanel);

        // Event listener on submit button 
        submitButton.addActionListener(e -> {
            // Retrieves the info from the input fields 
            String adminCode = t1.getText();
            String fname = t2.getText();
            String lname = t3.getText();
            String email = t4.getText();
            String password = t5.getText();
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // Check if fields are empty
            if (adminCode.isEmpty() || password.isEmpty() || email.isEmpty() || fname.isEmpty() || lname.isEmpty()) {
                JOptionPane.showMessageDialog(new JFrame("Error"), "Missing Information");
            } 
            else {
                // Save it into the array list
                cloudControllerInfo.add(adminCode);
                cloudControllerInfo.add(fname);
                cloudControllerInfo.add(lname);
                cloudControllerInfo.add(email);
                cloudControllerInfo.add(password);
                
                VC_Controller vc = new VC_Controller(adminCode, fname, password);
                completionTime = vc.computeCompletionTime(jobDurations);
                
       

                // Prints info gathered to print stream output folder
                output.println("Admin Code: " + adminCode + ", ");
                output.println("First Name: " + fname + ", ");
                output.println("Last Name: " + lname + ", ");
                output.println("Email: " + email + ", ");
                output.println("Password: " + password + ", ");
                output.println("timestamp: " + timestamp + ", ");
                output.println("");

                // Clear text fields
                t1.setText("");
                t2.setText("");
                t3.setText("");
                t4.setText("");
                t5.setText("");
                
                JPanel computePanel = compute(completionTime);
                mainPanel.add(computePanel, "Compute");

                // Returns to the welcome page
                cardLayout.show(mainPanel, "Compute");
            }
        });

        return cloudControllerPanel;
    }
    
    //new panel to have a button to compute completion time
    public JPanel compute(ArrayList<String>completionTime) {
        JPanel computePanel = new JPanel(new BorderLayout());
        computePanel.setLayout(new BoxLayout(computePanel, BoxLayout.Y_AXIS));
        computePanel.setBorder(BorderFactory.createEmptyBorder(175, 20, 20, 20));
        
        //Establish background color
        Color specificColor = new Color(199, 230, 246);
        computePanel.setBackground(specificColor);
        
        JLabel label = new JLabel("Welcome Cloud Controller", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 25));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        computePanel.add(label);
        
        computePanel.add(Box.createVerticalStrut(100));
        
        JButton computeButton = new JButton("Compute Completion Time");
        computeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        computePanel.add(computeButton);
        
        JButton backButton = new JButton("Log Out");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));
        computePanel.add(backButton);
        
       
        computeButton.addActionListener(e -> {
        	
        	// Initialize a StringBuilder to accumulate all the client info
        	StringBuilder messageBuilder = new StringBuilder("<html>");
        	
            for(int i = 0; i < clientInfo.size(); i++) {
            	
            	Client client = clientInfo.get(i);
            	String currentCompletionTime = completionTime.get(i);
            	  // Add the client's information to the message
                String clientMessage = String.format("Client ID: %s<br> Job Duration: %s<br>Time till completion: %s<br><br>",
                        client.getId(), client.getDuration() + " mins", currentCompletionTime );
                
                messageBuilder.append(clientMessage);
            	} 
            
         // Close the HTML tags for the message
            messageBuilder.append("</html>");
    
            JOptionPane.showMessageDialog(null, messageBuilder.toString(), "Estimated Completion Times", JOptionPane.INFORMATION_MESSAGE);
            
        });
        
        return computePanel;
    }
    
  
        
       
}
