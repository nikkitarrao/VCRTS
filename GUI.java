import javax.swing.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;
/*
 * Project: Stage 2 GUI
 * Class: CUS 1166
 * Authors: Nikkita Tarrao,Shivanni Rambaran, Payal Moorti, Lorena Vazquez, Jenn Venus
 * 10.8.2024
 * This program is stage 2 of our VCRTS project. The GUI class has our main method where we have the panels for when the program is first run
 * and what the user should initially see as well as the other cardlayouts we created.
 *
*/

public class GUI {

    public static void main(String[] args) throws FileNotFoundException {
        JFrame frame = new JFrame("Vehicular Cloud Real-Time System Console");
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
       // welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS)); 
       

        // Create the welcome panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Vehicular Cloud Real-Time System Console", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel descriptionLabel = new JLabel(
                "<html><p style='text-align: center;'>"
                + "The Vehicular Cloud Real-Time System (VCRTS) manages and organizes "
                + "computation resources in a vehicular cloud.<br><br>"
                + "It allows owners of smart vehicles equipped with computing capabilities to rent out their "
                + "vehicle's computing power and users to submit jobs for cloud-based computation."
                + "</p></html>", JLabel.CENTER);
            descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton createAccountButton = new JButton("Create an Account");
            createAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton signInButton = new JButton("Sign In");
            signInButton.setAlignmentX(Component.CENTER_ALIGNMENT);

       
        welcomePanel.add(titleLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 20)));  // Spacing
        welcomePanel.add(descriptionLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 30)));  // Spacing
        welcomePanel.add(createAccountButton);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Spacing
        welcomePanel.add(signInButton);

        // Add welcome panel to main panel
        mainPanel.add(welcomePanel, "Welcome");
        
        // Create ClickListener and add the create account panel
        ClickListener listener = new ClickListener(mainPanel, cardLayout);
        
        mainPanel.add(listener.createAccount(), "CreateAccount");// Add create account panel
        mainPanel.add(listener.signIn(), "SignIn"); // Add sign in panel
        mainPanel.add(listener.cloudController(), "CloudController"); // Add Cloud Controller panel
        mainPanel.add(listener.user2(), "User2"); // Add User2 panel
        mainPanel.add(listener.user1(), "User1"); // Add User1 panel
        

        // Set up the frame
        frame.add(mainPanel);
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Add action listeners
        createAccountButton.addActionListener(listener);
        signInButton.addActionListener(listener);

    }
}











