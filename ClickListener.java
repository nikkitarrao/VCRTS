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
    private ArrayList<String> user1Info = new ArrayList<>();
    private ArrayList<String> user2Info = new ArrayList<>();
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
        JPanel signInPanel = new JPanel(); 
        JLabel label = new JLabel("ID: ");
        JLabel label2= new JLabel("Password: ");
        JButton button = new JButton("Submit");
        JTextField t1 = new JTextField(20);
    	JTextField t2 = new JTextField(20);
        
        //adding stuff to the panel
        signInPanel.add(label);
        signInPanel.add(t1);
        signInPanel.add(label2);
        signInPanel.add(t2);
        signInPanel.add(button);
        
      //line break to make more neat
       signInPanel.setLayout(new BoxLayout(signInPanel, BoxLayout.Y_AXIS)); 
        
      //event listener on submit button to enter account
       if(user1Info.isEmpty() && user2Info.isEmpty() && user2Info.isEmpty()) {
    	   button.addActionListener(e -> {
    		   JOptionPane.showMessageDialog(new JFrame("Error"), "User not Found, Please try again");
                cardLayout.show(mainPanel, "Welcome");
            });
       } //else let them sign in

        //returning the panel so that it can be displayed when 'SignIn' button option is chosen 
        return signInPanel;
    }

    public JPanel createAccount() {
        JPanel createAccountPanel = new JPanel(); 
        createAccountPanel.setLayout(new BoxLayout(createAccountPanel, BoxLayout.Y_AXIS));
        createAccountPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel label = new JLabel("What type of account would you like to create?");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton button1 = new JButton("Register a vehicle");
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton button2 = new JButton("Submit jobs");
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton button3 = new JButton("Cloud Controller");
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //adjust button size
        Dimension buttonSize = new Dimension(200, 40); // Adjust width and height as needed
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);
        
        createAccountPanel.add(label);
        createAccountPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between components
        createAccountPanel.add(button1);
        createAccountPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between components
        createAccountPanel.add(button2);
        createAccountPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between components
        createAccountPanel.add(button3);
        
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
        //returning the panel so that it can be displayed when 'Create Account' button option is chosen 
        return createAccountPanel;
    }
    
    
    public JPanel user1() throws FileNotFoundException {
    	//full name, email, phone number, username password, car make model, vin, year
    	JPanel user1Panel = new JPanel();
        user1Panel.add(new JLabel("Create User 1 Account"));
        user1Panel.add(new JLabel("Enter the following information below"));
    	JTextField t1 = new JTextField(10);
    	JTextField t2 = new JTextField(20);
    	JTextField t3 = new JTextField(20);
    	JTextField t4 = new JTextField(10);
    	JTextField t5 = new JTextField(10);
    	JTextField t6 = new JTextField(10);
    	JTextField t7 = new JTextField(4);
    	JTextField t8 = new JTextField(4);
    	JTextField t9 = new JTextField(10);
        //user info
        user1Panel.add(new JLabel("User Information"));
        user1Panel.add(new JLabel("Owner ID: "));
        user1Panel.add(t1);
        user1Panel.add(new JLabel("Password: "));
        user1Panel.add(t2);
        user1Panel.add(new JLabel("Email: "));
        user1Panel.add(t3);
        //car info
        user1Panel.add(new JLabel("Vehicle Information"));
        user1Panel.add(new JLabel("Make: "));
        user1Panel.add(t4);
        user1Panel.add(new JLabel("Model: "));
        user1Panel.add(t5);
        user1Panel.add(new JLabel("VIN: "));
        user1Panel.add(t6);
        user1Panel.add(new JLabel("Year: "));
        user1Panel.add(t7);
        user1Panel.add(new JLabel("Color: "));
        user1Panel.add(t9);
        user1Panel.add(new JLabel("Approximate Vehicle Residency Time: "));
        user1Panel.add(t8);
        
     // File reading operations
    	PrintStream output = new PrintStream(new File("User1Out.txt"));

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        user1Panel.add(submitButton);
        
      //line break to make more neat
       user1Panel.setLayout(new BoxLayout(user1Panel, BoxLayout.Y_AXIS)); 
       

    	   submitButton.addActionListener(e -> {
    		
    		    //retrieves the info from the input fields 
    	   		String ownerID = t1.getText();
    	   		String password = t2.getText();
    	   		String email = t3.getText();
    	   		String make = t4.getText();
    	   		String model = t5.getText();
    	   		String year = t6.getText();
    	   		String vin = t7.getText();
    	   		String duration = t8.getText();
    	   		String color = t9.getText();
    	   		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    	   	
    		 	//event listener on submit button 
    	       if(ownerID.isEmpty() || password.isEmpty() || email.isEmpty() || make.isEmpty()|| make.isEmpty() || model.isEmpty() || year.isEmpty() || vin.isEmpty()|| color.isEmpty()|| duration.isEmpty()) {
    	    		   JOptionPane.showMessageDialog(new JFrame("Error"), "Missing Infomation");
    	       } else {
    	    	 	//save it into the arraylist
    	           	user1Info.add(ownerID);
    	           	user1Info.add(password);
    	           	user1Info.add(email);
    	           	user1Info.add(make);
    	           	user1Info.add(model);
    	           	user1Info.add(year);
    	           	user1Info.add(vin);
    	           	user1Info.add(color);
    	           	user1Info.add(duration);

    	           	//prints info gathered to printstream output folder
    	           	output.println("Owner ID: " + ownerID + ", ");
    	           	output.println("Password: " + password + ", ");
    	           	output.println("Email: " + email + ", ");
    	           	output.println("Make: " + make + ", ");
    	           	output.println("Model: " + model + ", ");
    	           	output.println("Year: " + year + ", ");
    	           	output.println("VIN: " + vin + ", ");
    	           	output.println("color: " + color + ", ");
    	           	output.println("duration: " + duration + ", ");
    	           	output.println("timestamp: " + timestamp + ", ");
    	           	output.println("");
    	           	
    	           	//clear fields
    	           	
    	           	t1.setText("");
    	           	t2.setText("");
    	        	t3.setText("");
    	           	t4.setText("");
    	         	t5.setText("");
    	           	t6.setText("");
    	        	t7.setText("");
    	           	t8.setText("");
    	           	t9.setText("");
    	           	// returns to the welcome page
    	               cardLayout.show(mainPanel, "Welcome");
    	       }
           	
           });
    		
        return user1Panel;
    }
    
    
    public JPanel user2() throws FileNotFoundException {
        JPanel user2Panel = new JPanel(new GridLayout(0, 2));
        user2Panel.add(new JLabel("Create User 2 Account"));
        user2Panel.add(new JLabel("Enter the following information below"));
        JTextField t1 = new JTextField(10);
    	JTextField t2 = new JTextField(20);
    	JTextField t3 = new JTextField(20);
    	JTextField t4 = new JTextField(20);
    	JTextField t5 = new JTextField(10);
    	JTextField t6 = new JTextField(10);
    	JTextField t7 = new JTextField(20);


        user2Panel.add(new JLabel("Client ID: "));
        user2Panel.add(t1);
        user2Panel.add(new JLabel("Full Name: "));
        user2Panel.add(t7);
        user2Panel.add(new JLabel("Password: "));
        user2Panel.add(t2);
        user2Panel.add(new JLabel("Email: "));
        user2Panel.add(t3);
        user2Panel.add(new JLabel("Company/Organization: "));
        user2Panel.add(t4);
        user2Panel.add(new JLabel("Approximate Job Duration: "));
        user2Panel.add(t5);
        user2Panel.add(new JLabel("Job Deadline: "));
        user2Panel.add(t6);
        
        // File reading operations
    	PrintStream output = new PrintStream(new File("User2Out.txt"));

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        user2Panel.add(submitButton);
        
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
 	        	user2Info.add(clientID);
 	        	user2Info.add(password);
 	        	user2Info.add(email);
 	        	user2Info.add(company);
 	        	user2Info.add(duration);
 	        	user2Info.add(deadline);


 	        	//prints info gathered to printstream output folder
 	        	output.println("Client ID: " + clientID + ", ");
 	        	output.println("Full Name: " + name + ", ");
 	        	output.println("Password: " + password + ", ");
 	        	output.println("Email: " + email + ", ");
 	        	output.println("Company: " + company + ", ");
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
    
    public JPanel cloudController() throws FileNotFoundException{
    	
    	JPanel cloudControllerPanel = new JPanel();
    	JLabel label = new JLabel("Create Cloud Controller Account- Authorized Users Only");
    	JLabel label2 = new JLabel("Enter the following information below");
    	JLabel adminCodeLabel = new JLabel("Admin ID: ");
    	JTextField t1 = new JTextField(7);
    	JTextField t2 = new JTextField(20);
    	JTextField t3 = new JTextField(20);
    	JTextField t4 = new JTextField(20);
    	JTextField t5 = new JTextField(20);
    	JLabel fnameLabel = new JLabel("First Name: ");
    	JLabel lnameLabel = new JLabel("Last Name: ");
    	JLabel emailLabel = new JLabel("Email: ");
    	JLabel passwordLabel = new JLabel("Password: ");
    	JButton submit = new JButton("Submit");
    	
    	 // File reading operations
    	PrintStream output = new PrintStream(new File("CloudOut.txt"));
    	
    	cloudControllerPanel.add(label);
    	cloudControllerPanel.add(label2);
    	cloudControllerPanel.add(adminCodeLabel);
    	cloudControllerPanel.add(t1);
    	cloudControllerPanel.add(fnameLabel);
    	cloudControllerPanel.add(t2);
    	cloudControllerPanel.add(lnameLabel);
    	cloudControllerPanel.add(t3);
    	cloudControllerPanel.add(emailLabel);
    	cloudControllerPanel.add(t4);
    	cloudControllerPanel.add(passwordLabel);
    	cloudControllerPanel.add(t5);
    	cloudControllerPanel.add(submit);
    	//line break to make more neat
    	cloudControllerPanel.setLayout(new BoxLayout(cloudControllerPanel, BoxLayout.Y_AXIS)); 

    	//event listener on submit button 
        submit.addActionListener(e -> {
        	
        	//retrieves the info from the input fields 
        	String adminCode = t1.getText();
        	String fname = t2.getText();
        	String lname = t3.getText();
        	String email = t4.getText();
        	String password = t5.getText();
        	String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        	
        	//doesnt allow you to create repeat accounts
        	for(int i = 0; i < cloudControllerInfo.size(); i++) {
        		if(adminCode.equals(cloudControllerInfo.get(i))) {
            		
            	}
        	}
 
        	//check if fields are empty
  	       if(adminCode.isEmpty() || password.isEmpty() || email.isEmpty() || fname.isEmpty()|| lname.isEmpty()) {
  	    		   JOptionPane.showMessageDialog(new JFrame("Error"), "Missing Infomation");
  	       } else {
  	    	//save it into the arraylist
  	        	cloudControllerInfo.add(adminCode);
  	        	cloudControllerInfo.add(fname);
  	        	cloudControllerInfo.add(lname);
  	        	cloudControllerInfo.add(email);
  	        	cloudControllerInfo.add(password);
  	        	
  	        	//prints info gathered to printstream output folder
  	        	output.println("Admin Code: " + adminCode + ", ");
  	        	output.println("First Name: " + fname + ", ");
  	        	output.println("Last Name: " + lname + ", ");
  	        	output.println("Email: " + email + ", ");
  	        	output.println("Password: " + password + ", ");
  	        	output.println("timestamp: " + timestamp + ", ");
  	        	output.println("");
  	        	
  	        	
  	        	t1.setText("");
  	        	t2.setText("");
  	        	t3.setText("");
  	        	t4.setText("");
  	        	t5.setText("");
           
  	        	
  	        	// returns to the welcome page
  	            cardLayout.show(mainPanel, "Welcome");
  	       }
        	
        });
    	return cloudControllerPanel;
    		}

}
