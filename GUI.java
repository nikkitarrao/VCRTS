import javax.swing.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.CardLayout;

/*
 * Project: Stage 2 GUI
 * Class: CUS 1166
 * Authors: Nikkita Tarrao,Shivanni Rambaran, Payal Moorti, Lorena Vazquez, Jenn Venus
 * 10.8.2024
 * 
 * 
 * This program is stage 2 of our VCRTS project. The GUI class has our main method where we have the panels for when the program is first run
 * and what the user should initially see as well as the other cardlayouts we created.
 */

public class GUI {

    public static void main(String[] args) throws FileNotFoundException {
        JFrame frame = new JFrame("Vehicular Cloud Real-Time System Console");
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
       // welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS)); 

        // Create the welcome panel
        JPanel welcomePanel = new JPanel();
        JLabel label = new JLabel("Vehicular Cloud Real-Time System Console");
        JLabel description = new JLabel("<html>The Vehicular Cloud Real-Time System (VCRTS) is a service that manages and organizes computation resources and jobs in a vehicular cloud.<br>"
                + "\nThis system is intended for owners of smart modern vehicles equipped with computing capabilities and users looking to utilize cloud-based computation power to submit jobs and simulations.<br>"
                + "\nThe VCRTS will be implemented in parking lots, where vehicles can connect to the cloud via Wi-Fi, facilitating cohesive real-time interactions.<br></html>");
        System.out.println("<br>");

        JButton createAccountButton = new JButton("Create an Account");
        JButton signInButton = new JButton("Sign In");

        welcomePanel.add(label);
        welcomePanel.add(description);
        System.out.println("");
        welcomePanel.add(createAccountButton);
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











