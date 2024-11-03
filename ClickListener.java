import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Scanner;

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

    // ArrayLists to hold account information
    private ArrayList<String> vehicleOwnerInfo = new ArrayList<>();
    private ArrayList<String> clientInfo = new ArrayList<>();
    private ArrayList<String> cloudControllerInfo = new ArrayList<>();

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
        
        JLabel signInLabel = new JLabel("<html><b><u>Sign In</u></b></html>", JLabel.CENTER);
        signInLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        signInLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel label = new JLabel("ID: ");
        JLabel label2= new JLabel("Password: ");
        JButton button = new JButton("Submit");
        JButton backButton = new JButton("Back");
        JTextField t1 = new JTextField(20);
    	JTextField t2 = new JTextField(20);
 
    	
    	//my edits to adjust jpanel size
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

        
        JLabel label = new JLabel("<html><center><b><u>What type of account would you like to create?</u></b></center></html>");
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
    	
    	JLabel label = new JLabel("<html><center><b><u>Create User 1 Account</u></b></center></html>");
    	label.setFont(new Font("Arial", Font.PLAIN, 20));
        user1Panel.add(label);
        user1Panel.add(new JLabel("Enter the following information below"));
    	JTextField nameText = new JTextField(10);
    	JTextField userNameText = new JTextField(20);
    	JTextField passwordText = new JTextField(20);
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
    	userInfoLabel.setFont(new Font("Apotos", Font.PLAIN, 15));
    	user1Panel.add(userInfoLabel);
    	user1Panel.add(new JLabel("Owner Name: "));
        user1Panel.add(nameText);
        user1Panel.add(new JLabel("Username: "));
        user1Panel.add(userNameText);
        user1Panel.add(new JLabel("Password: "));
        user1Panel.add(passwordText);
        user1Panel.add(new JLabel("Phone Number: "));
        user1Panel.add(phoneText);
        user1Panel.add(new JLabel("Email: "));
        user1Panel.add(emailText);
        //car info
        user1Panel.add(Box.createVerticalStrut(10));
        JLabel vehicleInfoLabel = new JLabel("Vehicle Information");
        vehicleInfoLabel.setFont(new Font("Apotos", Font.PLAIN, 15));
    	user1Panel.add(vehicleInfoLabel);
        user1Panel.add(new JLabel("Make: "));
        user1Panel.add(makeText);
        user1Panel.add(new JLabel("Model: "));
        user1Panel.add(modelText);
        user1Panel.add(new JLabel("VIN: "));
        user1Panel.add(vinText);
        user1Panel.add(new JLabel("Year: "));
        user1Panel.add(yearText);
        user1Panel.add(new JLabel("Color: "));
        user1Panel.add(colorText);
        user1Panel.add(new JLabel("Approximate Vehicle Residency Time (in hrs): "));
        user1Panel.add(residencyTimeText);
        
     // File reading operations
    	PrintStream output = new PrintStream(new File("User1Out.txt"));

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        user1Panel.add(submitButton);

        
        JButton backButton = new JButton("Back"); 
        backButton.addActionListener(e -> {
        	cardLayout.show(mainPanel, "Welcome");
    	});
        user1Panel.add(backButton);
        
      //line break to make more neat
       user1Panel.setLayout(new BoxLayout(user1Panel, BoxLayout.Y_AXIS)); 
       

    	   submitButton.addActionListener(e -> {
    		
    		    //retrieves the info from the input fields 
    	   		String ownerName = nameText.getText();
    	   		String username = userNameText.getText();
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
    	       if(ownerName.isEmpty() || pw.isEmpty() || email.isEmpty() || make.isEmpty()|| make.isEmpty() || model.isEmpty() || year.isEmpty() || vin.isEmpty()|| color.isEmpty()|| duration.isEmpty()) {
    	    		   JOptionPane.showMessageDialog(new JFrame("Error"), "Missing Infomation");
    	       } else {
    	    	 	//save it into the arraylist
    	           	vehicleOwnerInfo.add(ownerName);
    	           	vehicleOwnerInfo.add(username);
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
    	           	output.println("Owner ID: " + ownerName + ", ");
    	           	output.println("Password: " + passwordText + ", ");
    	           	output.println("Email: " + emailText + ", ");
    	           	output.println("Make: " + make + ", ");
    	           	output.println("Model: " + model + ", ");
    	           	output.println("Year: " + year + ", ");
    	           	output.println("VIN: " + vin + ", ");
    	           	output.println("color: " + color + ", ");
    	           	output.println("duration: " + duration + ", ");
    	           	output.println("timestamp: " + timestamp + ", ");
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
    	           	
    	           	// returns to the welcome page
    	               cardLayout.show(mainPanel, "Welcome");
    	       }
           	
           });
    	   
    		
        return user1Panel;
    }
    
    
    public JPanel user2() throws FileNotFoundException {
        JPanel user2Panel = new JPanel();
        user2Panel.setLayout(new BoxLayout(user2Panel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical stacking

        JLabel label = new JLabel("<html><center><b><u>Create User 2 Account</u></b></center></html>");
    	label.setFont(new Font("Arial", Font.PLAIN, 20));
    	user2Panel.add(label);
    	
        user2Panel.add(new JLabel("Enter the following information below"));
        
        JTextField t1 = new JTextField();
        t1.setPreferredSize(new Dimension(200, 30)); 

        JTextField t2 = new JTextField();
        t2.setPreferredSize(new Dimension(200, 30)); 
        
        JTextField t3 = new JTextField();
        t3.setPreferredSize(new Dimension(200, 30));

        JTextField t4 = new JTextField();
        t4.setPreferredSize(new Dimension(200, 30));

        JTextField t5 = new JTextField();
        t5.setPreferredSize(new Dimension(200, 30));

        JTextField t6 = new JTextField();
        t6.setPreferredSize(new Dimension(200, 30));

        JTextField t7 = new JTextField();
        t7.setPreferredSize(new Dimension(200, 30));


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
        deadlinePanel.add(new JLabel("Job Deadline: "));
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
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));

        // Add the button to the wrapper panel
        backButtonPanel.add(backButton);
        user2Panel.add(backButtonPanel); 
        
      //line break to make more neat
       user2Panel.setLayout(new BoxLayout(user2Panel, BoxLayout.Y_AXIS)); 
        
      //event listener on submit button 
        submitButton.addActionListener(e -> {
        	//retrieves the info from the input fields 
        	String clientID = t1.getText();
        	String password = t2.getText();
        	String email = t3.getText();
        	String company = t4.getText();
        	String duration = t5.getText();
        	String deadline = t6.getText();
        	String name = t7.getText();
        	String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        	
        	//check if fields are empty
 	       if(clientID.isEmpty() || password.isEmpty() || email.isEmpty() || company.isEmpty()|| duration.isEmpty() || deadline.isEmpty() ||name.isEmpty()) {
 	    		   JOptionPane.showMessageDialog(new JFrame("Error"), "Missing Infomation");
 	       } else {
 	    	 	//save it into the arraylist
 	        	clientInfo.add(clientID);
 	        	clientInfo.add(password);
 	        	clientInfo.add(email);
 	        	//create a new job class saved here 
 	        	clientInfo.add(company);
 	        	clientInfo.add(duration);
 	        	clientInfo.add(deadline);


 	        	//prints info gathered to printstream output folder
 	        	output.println("Client ID: " + clientID + ", ");
 	        	output.println("Full Name: " + name + ", ");
 	        	output.println("Password: " + password + ", ");
 	        	output.println("Email: " + email + ", ");
 	        	output.println("Company: " + company + ", ");
 	        	
 	        	//save into the other outputfile
 	        	// .submitJob(job)
 	        	output.println("Duration: " + duration + ", ");
 	        	output.println("Deadline: " + deadline + ", ");
 	        	output.println("timestamp: " + timestamp + ", ");
 	        	output.println("");
 	        	
 	        	t1.setText("");
 	        	t2.setText("");
 	        	t3.setText("");
           		t4.setText("");
           		t5.setText("");
           		t6.setText("");
           		t7.setText("");
           	
 	        	// returns to the welcome page
 	            cardLayout.show(mainPanel, "Welcome");
 	       }
  
        });
        
        
        return user2Panel;
    }
    
    public JPanel cloudController() throws FileNotFoundException {
        JPanel cloudControllerPanel = new JPanel();
        cloudControllerPanel.setLayout(new BoxLayout(cloudControllerPanel, BoxLayout.Y_AXIS));
        
        // Centered Title
        JLabel label = new JLabel("<html><center><b><u>Create Cloud Controller Account</u></b></center></html>");
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        cloudControllerPanel.add(label);
        
        // Centered Sub-Title
        JLabel label2 = new JLabel("<html><center><b>Authorized Users Only</b></center></html>");
        label2.setFont(new Font("Arial", Font.PLAIN, 15));
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
        JTextField t5 = new JTextField(20);

        Dimension textFieldSize = new Dimension(300, 30);
        t1.setPreferredSize(textFieldSize);
        t2.setPreferredSize(textFieldSize);
        t3.setPreferredSize(textFieldSize);
        t4.setPreferredSize(textFieldSize);
        t5.setPreferredSize(textFieldSize);

        t1.setMaximumSize(textFieldSize);
        t2.setMaximumSize(textFieldSize);
        t3.setMaximumSize(textFieldSize);
        t4.setMaximumSize(textFieldSize);
        t5.setMaximumSize(textFieldSize);
        
        // File reading operations
        PrintStream output = new PrintStream(new File("CloudOut.txt"));
        
        // Adding labels and text fields
       
        cloudControllerPanel.add(new JLabel("Admin ID:"));
        cloudControllerPanel.add(t1);
        cloudControllerPanel.add(Box.createVerticalStrut(3)); // Small spacing

        cloudControllerPanel.add(new JLabel("First Name:"));
        cloudControllerPanel.add(t2);
        cloudControllerPanel.add(Box.createVerticalStrut(3));

        cloudControllerPanel.add(new JLabel("Last Name:"));
        cloudControllerPanel.add(t3);
        cloudControllerPanel.add(Box.createVerticalStrut(3));

        cloudControllerPanel.add(new JLabel("Email:"));
        cloudControllerPanel.add(t4);
        cloudControllerPanel.add(Box.createVerticalStrut(3));

        cloudControllerPanel.add(new JLabel("Password:"));
        cloudControllerPanel.add(t5);
        cloudControllerPanel.add(Box.createVerticalStrut(10));

        // Button panel for centering
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        
        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(100, 40));
        submitButton.addActionListener(this);
        
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));
        
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
            } else if (!adminCode.equals("SR1") && !adminCode.equals("LV2") && !adminCode.equals("NT3") &&
                       !adminCode.equals("PM4") && !adminCode.equals("JV5")) {
                JOptionPane.showMessageDialog(new JFrame("Error"), "INCORRECT ADMIN CODE!");
            } else {
                // Save it into the array list
                cloudControllerInfo.add(adminCode);
                cloudControllerInfo.add(fname);
                cloudControllerInfo.add(lname);
                cloudControllerInfo.add(email);
                cloudControllerInfo.add(password);

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

                // Returns to the welcome page
                cardLayout.show(mainPanel, "Welcome");
            }
        });

        return cloudControllerPanel;
    }

}
