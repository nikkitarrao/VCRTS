import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.GridLayout;

public class ClickListener implements ActionListener {
    private JFrame parentFrame;

    public ClickListener(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command.equals("Create an Account")) {
            System.out.println("Create Account");
            GUI.showPanel("CreateAccount");
        } else if (command.equals("Sign in")) {
            System.out.println("Sign in");
        } else if (command.equals("Submit")) {
            handleSubmit();
        } else if (command.equals("Register a vehicle")) {
            // Implement User1 logic
        } else if (command.equals("Submit jobs")) {
            GUI.showPanel("User2");
        } else if (command.equals("Cloud Controller")) {
            // Implement Cloud Controller logic
        }
    }

    public JPanel createAccount() {
        JPanel createAccountPanel = new JPanel(); 
        JLabel label = new JLabel("What type of account would you like to create?");
        JButton button1 = new JButton("Register a vehicle");
        JButton button2 = new JButton("Submit jobs");
        JButton button3 = new JButton("Cloud Controller");
        
        createAccountPanel.add(label);
        createAccountPanel.add(button1);
        createAccountPanel.add(button2);
        createAccountPanel.add(button3);
        
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        
        return createAccountPanel;
    }
    

    public JPanel user2() {
        JPanel user2Panel = new JPanel(new GridLayout(0, 2));
        user2Panel.add(new JLabel("Create User 2 Account"));
        user2Panel.add(new JLabel());
        user2Panel.add(new JLabel("Enter the following information below"));
        user2Panel.add(new JLabel());

        user2Panel.add(new JLabel("Username: "));
        user2Panel.add(new JTextField(20));
        user2Panel.add(new JLabel("Password: "));
        user2Panel.add(new JPasswordField(20));
        user2Panel.add(new JLabel("Email: "));
        user2Panel.add(new JTextField(20));
        user2Panel.add(new JLabel("Company/Organization: "));
        user2Panel.add(new JTextField(20));

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        user2Panel.add(submitButton);
        
        return user2Panel;
    }

    private void handleSubmit() {
        System.out.println("Form submitted");
        // TODO: Add logic to handle form submission
    }
}