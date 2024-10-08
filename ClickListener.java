import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ClickListener implements ActionListener {
    private JFrame parentFrame;

    // Constructor to initialize the parent frame
    public ClickListener(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command.equals("Create an Account")) {
        	//System.out.print("Create Account");
        	parentFrame.getContentPane().removeAll();
        	JPanel panel2 = new JPanel();
        	JLabel labelUser = new JLabel("Which account would you like to create?");
        	panel2.add(labelUser);
        	
   		 	JButton button1_1= new JButton("User 1 - Register Vehicle"); //1_1 suggests that it is related to button 1 which is create account so these buttons show up if you click button 1
   		 	panel2.add(button1_1);
   		 	button1_1.addActionListener(e -> GUI.createUser1(parentFrame));
   		 	JButton button1_2=new  JButton ("User 2 - Use Vehicle");
   		 	JButton button1_3 = new JButton ("User 3 - Cloud Controller");

         // Add buttons to the panel
   		 	//panel2.add(button1_1);
   		 	panel2.add(button1_2);
   		 	panel2.add(button1_3);

         // Re-add the panel to the frame
   		 	parentFrame.add(panel2);
   		 	parentFrame.revalidate();  // Refresh the frame to show new content
   		 	parentFrame.repaint();  // Repaint the frame
        
            
        } 
        else if (command.equals("Sign in")) {
        	 //System.out.print("Sign in");
        	 JPanel panelSignIn = new JPanel();
        	 JLabel labelSignIn = new JLabel ("Please enter your login information");
        	 
        	 JButton button2_1 = new JButton("Sign in");
        	 //button2_1.addActionListener(e -> GUI.createUser1(parentFrame)); ADD this section when you get lorena's part for the sign in
        }
    }
}

