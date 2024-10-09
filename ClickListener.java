import java.awt.CardLayout;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import javax.swing.*;

public class ClickListener implements ActionListener {
    private CardLayout cardLayout;
    private JPanel mainPanel;

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
        } else if (command.equals("Sign in")) {
            System.out.print("Sign in");
        }
    }

    public JPanel createAccount() {
        JPanel createAccountPanel = new JPanel(); 
        JLabel label = new JLabel("What type of account would you like to create?");
        JButton button1 = new JButton("Register a vehicle");
        JButton button2 = new JButton("Submit jobs");
        JButton button3 = new JButton("Cloud Controller");
        
        //adding stuff to the panel
        createAccountPanel.add(label);
        createAccountPanel.add(button1);
        createAccountPanel.add(button2);
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
    
    public JPanel cloudController() throws FileNotFoundException{
    	//ArrayList to save cloud controller account information
    	ArrayList <String> cloudControllerList = new ArrayList<String>();
    	
    	JPanel cloudControllerPanel = new JPanel();
    	JLabel label = new JLabel("Create Cloud Controller Account- Authorized Users Only");
    	JLabel label2 = new JLabel("Enter the following information below");
    	JLabel adminCodeLabel = new JLabel("Admin Code: ");
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
    	PrintStream output = new PrintStream(new File("out.txt"));
       // Scanner input = new Scanner(System.in);
    	
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
        	
        	//doesnt allow you to create repeat accounts
        	for(int i = 0; i < cloudControllerList.size(); i++) {
        		if(adminCode.equals(cloudControllerList.get(i))) {
            		
            	}
        	}
        	
        	
        	//save it into the arraylist
        	cloudControllerList.add(adminCode);
        	cloudControllerList.add(fname);
        	cloudControllerList.add(lname);
        	cloudControllerList.add(email);
        	cloudControllerList.add(password);
        	
        	//prints info gathered to printstream output folder
        	output.print("Admin Code: " + adminCode + ", ");
        	output.print("First Name: " + fname + ", ");
        	output.print("Last Name: " + lname + ", ");
        	output.print("Email: " + email + ", ");
        	output.print("Password: " + password + ", ");
        	
        	
        	// returns to the welcome page
            cardLayout.show(mainPanel, "Welcome");
        });
    	return cloudControllerPanel;
    		}

}
