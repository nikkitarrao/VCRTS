import javax.swing.*;
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

/*
 * private static void CreateAccount() {
	// Create a new JFrame for create account
}
*/

public class GUI {
	
	  public static void createUser1(JFrame frame) {
		  ArrayList<User> user1List = new ArrayList<>();
		// Clear the frame content to start fresh
	        frame.getContentPane().removeAll();

	        // Create panel for users to register vehicles
	        JPanel registerPanel = new JPanel();
	        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS)); //vertically aligns all the panels to make it more organized
	        registerPanel.setSize(5, 10);
	        
	        // Create input fields for user info
	        JTextField nameField = new JTextField(40);
	        JTextField emailField = new JTextField(40);
	        JTextField phoneField = new JTextField(40);
	        JTextField userNameField = new JTextField(40);
	        JPasswordField passwordField = new JPasswordField(40);
	        JTextField makeField = new JTextField(40);
	        JTextField modelField = new JTextField(40);
	        JTextField yearField = new JTextField(4);
	        JTextField vinField = new JTextField(40);

	        // Add labels and input fields to the form
	        registerPanel.add(new JLabel("Full Name:"));
	        registerPanel.add(nameField);
	        registerPanel.add(new JLabel("Email:"));
	        registerPanel.add(emailField);
	        registerPanel.add(new JLabel("Phone Number:"));
	        registerPanel.add(phoneField);
	        registerPanel.add(new JLabel("Username:"));
	        registerPanel.add(userNameField);
	        registerPanel.add(new JLabel("Password:"));
	        registerPanel.add(passwordField);
	        registerPanel.add(new JLabel("Car Make:"));
	        registerPanel.add(makeField);
	        registerPanel.add(new JLabel("Car Model:"));
	        registerPanel.add(modelField);
	        registerPanel.add(new JLabel("Car Year:"));
	        registerPanel.add(yearField);
	        registerPanel.add(new JLabel("VIN:"));
	        registerPanel.add(vinField);

	        // Submit button
	        JButton submitButton = new JButton("Register");
	        registerPanel.add(submitButton);
	       

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
	                JLabel success = new JLabel("Vehicle registered, User Created!");
	                registerPanel.add(success);
	                registerPanel.revalidate();
	                registerPanel.repaint();
	        	
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


