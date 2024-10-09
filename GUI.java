import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.awt.CardLayout;

public class GUI {
    public static void main(String[] args) throws FileNotFoundException {
        JFrame frame = new JFrame("Vehicular Cloud Real-Time System Console");
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
       // welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS)); 
import java.util.*;
import java.io.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class SignIn extends JFrame implements ActionListener {
    //initialize button, panel, label, and text field
    JButton b1;
    JPanel newPanel;
    JLabel userLabel;
    JLabel passLabel;
    final JTextField textField1, textField2;

    public SignIn() {
        //Creating a label for email
        userLabel = new JLabel();
        userLabel.setText("Email: ");
        textField1 = new JTextField(75); //set length of the text

        passLabel = new JLabel();
        passLabel.setText("Password");
        textField2 = new JPasswordField(30);

        b1 = new JButton("SUBMIT"); // set label to button

        //create panel to put in form elements
        newPanel = new JPanel(new GridLayout(3,1));
        newPanel.add(userLabel);
        newPanel.add(textField1);
        newPanel.add(passLabel);
        newPanel.add(textField2);
        newPanel.add(b1);

        //set border to panel
        add(newPanel, BorderLayout.CENTER);

        b1.addActionListener(this);
        setTitle("SIGN IN FORM");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Allows the frame to be closed without exiting the app
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String userValue = textField1.getText();
        String passValue = textField2.getText();

        if(!userValue.isEmpty() && !passValue.isEmpty()) {
            JFrame page = new JFrame();
            page.setSize(300, 150);
            page.setTitle("Welcome");
            JLabel welLabel = new JLabel("Welcome: " + userValue);
            page.add(welLabel);
            page.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a valid username and password");
        }
    }
}



public class GUI {
	
	/*public static void signIn(JFrame frame) {
		frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();
        
        // Create a panel to hold the components
        JPanel newPanel = new JPanel();
        
        newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
        newPanel.setSize(5, 10);
        
        // Create components for the sign-in form
        JLabel userLabel = new JLabel("Email:");
        JTextField textField1 = new JTextField(20);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField textField2 = new JPasswordField(20);
        JButton b1 = new JButton("SUBMIT");
        
        newPanel.add(userLabel);
        newPanel.add(textField1);
        newPanel.add(passLabel);
        newPanel.add(textField2);
        newPanel.add(b1);
        
        
        // Add the panel to the frame
       frame.add(newPanel);
        frame.revalidate();
        frame.repaint();
        
        // Action listener for the submit button
        b1.addActionListener(e -> {
            String userValue = textField1.getText();
            String passValue = new String(textField2.getPassword());

            if (!userValue.isEmpty() && !passValue.isEmpty()) {
                JFrame welcomeFrame = new JFrame();
                welcomeFrame.setSize(300, 150);
                welcomeFrame.setTitle("Welcome");
                JLabel welcomeLabel = new JLabel("Welcome, " + userValue);
                welcomeFrame.add(welcomeLabel);
                welcomeFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a valid username and password");
            }
        });
	}
	*/
	
	
	 /* private static void setDefaultCloseOperation(int disposeOnClose) {
		// TODO Auto-generated method stub
		
	}*/



	public static void createUser1(JFrame frame) {
		  ArrayList<User> user1List = new ArrayList<>();
		// Clear the frame content to start fresh
	        frame.getContentPane().removeAll();

        // Create the welcome panel
        JPanel welcomePanel = new JPanel();
        JLabel label = new JLabel("Welcome to the Vehicular Cloud Real-Time System Console");
        JButton createAccountButton = new JButton("Create an Account");
        JButton signInButton = new JButton("Sign in");

        welcomePanel.add(label);
        welcomePanel.add(createAccountButton);
        welcomePanel.add(signInButton);
        
        // Add welcome panel to main panel
        mainPanel.add(welcomePanel, "Welcome");
        
        // Create ClickListener and add the create account panel
        ClickListener listener = new ClickListener(mainPanel, cardLayout);
        mainPanel.add(listener.createAccount(), "CreateAccount");
        mainPanel.add(listener.cloudController(), "CloudController"); // Add Cloud Controller panel

        // Set up the frame
        frame.add(mainPanel);
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Add action listeners
        createAccountButton.addActionListener(listener);
	        // Add action listener for submit button
	        submitButton.addActionListener(e -> {
	            // Gather data from input fields
	        	// Check if any field is empty
	            if (nameField.getText().isEmpty() ) {
	                JOptionPane.showMessageDialog(frame, "Please enter your full name.", "Error", JOptionPane.ERROR_MESSAGE);
	            } 
	            else if (emailField.getText().isEmpty()) {
	                JOptionPane.showMessageDialog(frame, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
	            } 
	            else if (phoneField.getText().isEmpty()) {
	                JOptionPane.showMessageDialog(frame, "Please enter a valid phone number.", "Error", JOptionPane.ERROR_MESSAGE);
	            } 
	            else if (userNameField.getText().isEmpty()) {
	                JOptionPane.showMessageDialog(frame, "Please enter a valid username.", "Error", JOptionPane.ERROR_MESSAGE);
	            } 
	            else if (passwordField.getText().isEmpty()) {
	                JOptionPane.showMessageDialog(frame, "Please enter a valid password.", "Error", JOptionPane.ERROR_MESSAGE);
	            } 
	            else if (makeField.getText().isEmpty()) {
	                JOptionPane.showMessageDialog(frame, "Please enter a valid car make.", "Error", JOptionPane.ERROR_MESSAGE);
	            } 
	            else if (modelField.getText().isEmpty()) {
	                JOptionPane.showMessageDialog(frame, "Please enter a valid car model.", "Error", JOptionPane.ERROR_MESSAGE);
	            } 
	            else if (yearField.getText().isEmpty()) {
	                JOptionPane.showMessageDialog(frame, "Please enter a valid car year.", "Error", JOptionPane.ERROR_MESSAGE);
	            } 
	            else if (vinField.getText().isEmpty()) {
	                JOptionPane.showMessageDialog(frame, "Please enter a valid VIN.", "Error", JOptionPane.ERROR_MESSAGE);
	            } 
	            else {
	                // If all fields are valid
	                JOptionPane.showMessageDialog(frame, "Vehicle registered. User created!");
	                
	        	
	            String name = nameField.getText();
	            String email = emailField.getText();
	            String phone = phoneField.getText();
	            String userName = userNameField.getText();
	            String pw = passwordField.getText();
	            String make = makeField.getText();
	            String model = modelField.getText();
	            int year = Integer.parseInt(yearField.getText());
	            String vin = vinField.getText();

	            // Create a Vehicle object and a User object
	            Vehicle vehicle = new Vehicle(make, model, year, vin);
	            User user = new User(name, email, phone, userName, pw, vehicle);

	            // Add the user to the list
	            user1List.add(user);
	            System.out.println("User information saved!");
	            
	            
	           

	            // Optionally clear the fields or reset the frame
	            frame.getContentPane().removeAll();
	            frame.revalidate();
	            frame.repaint();
	            }
	        });

	        // Add formPanel to the frame
	        frame.add(registerPanel);
	        frame.revalidate();
	        frame.repaint();
	        
	    }

	public static void main(String[] args) throws FileNotFoundException {
		
		 JFrame frame = new JFrame();
	      
		 JLabel label = new JLabel("Welcome to the Vehicular Cloud Real-Time System Console");
	      JButton button1 = new JButton("Create an Account");
	      JButton button2 = new JButton("Sign in");

	      JPanel panel = new JPanel();
	      panel.add(label);
	      panel.add(Box.createVerticalGlue()); //leaves a vertical space
	      panel.add(Box.createVerticalGlue());
	      panel.add(button1);
	      panel.add(Box.createVerticalGlue());
	      panel.add(Box.createVerticalGlue());
	      panel.add(button2);
	      frame.add(panel);

	      final int FRAME_WIDTH = 400;
	      final int FRAME_HEIGHT = 500;
	      frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
	      frame.setTitle("Vehicular Cloud Real Time System");
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.setVisible(true);
	      

	        // ActionListener to show SignIn form only when "Sign in" button is clicked
	       button2.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                SignIn signInForm = new SignIn();
	            	//GUI.signIn(frame);
	                signInForm.setVisible(true);
	            }
	        });
	      
	      ActionListener listener = new ClickListener(frame);
	      button1.addActionListener(listener);
	      button2.addActionListener(listener);
	      
	      //printing out the information
	      File file = new File("data.txt");
			
			 Scanner input = new Scanner (file);
			
			while (input.hasNextDouble()) {
	            double number = input.nextDouble();
	            System.out.println(number);
	        }
			
			
			PrintStream output = new PrintStream(new File("out.txt"));

	}

    }
}



