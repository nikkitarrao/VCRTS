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

    }
}



