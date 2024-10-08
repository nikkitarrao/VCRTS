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


