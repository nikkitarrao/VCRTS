import javax.swing.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(175, 20, 20, 20));
        
        Color specifcColor = new Color(199, 230, 246);
        welcomePanel.setBackground(specifcColor);
  
        
        JLabel titleLabel = new JLabel("Vehicular Cloud Real-Time System Console", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 25));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel descriptionLabel = new JLabel(
                "<html><p style='text-align: center;'>"
                + "The Vehicular Cloud Real-Time System (VCRTS) manages and organizes "
                + "computation resources in a vehicular cloud.<br><br>"
                + "We allow owners of smart vehicles equipped with computing capabilities to rent out their "
                + "vehicle's computing power and clients to submit jobs for cloud-based computation."
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
        frame.setSize(650, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Add action listeners
        createAccountButton.addActionListener(listener);
        signInButton.addActionListener(listener);
        
        // Database credentials
        String url = "jdbc:mysql://localhost:3306"; 
        String user = "root"; 
        String password = "nrt123"; 
        
        // Establishing the connection and handling the query
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection to the database successful!");

            // Example query (replace with your actual SQL query)
            String query = "SELECT * FROM your_table";  // Replace "your_table" with your actual table name

            // Create a statement and execute the query
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            System.out.println("Query executed successfully!");

            // Optionally, if you want to insert data into the database (e.g., job data)
            // Here’s an example of an insert query (replace with your actual data)
            String insertQuery = "INSERT INTO job_owners (clientID, duration, deadline) VALUES ('client123', '30', '2024-12-31')";
            statement.executeUpdate(insertQuery);
            System.out.println("Data inserted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }

       

    }
 
}












