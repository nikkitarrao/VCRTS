
import javax.swing.*;
import java.awt.*;

public class ServerTester {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("VCRTS");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            
            // Create the card layout and main panel
            CardLayout cardLayout = new CardLayout();
            JPanel mainPanel = new JPanel(cardLayout);
            
            // Create the ClickListener instance
            ClickListener clickListener = new ClickListener(mainPanel, cardLayout);
            
            // Create welcome panel
            JPanel welcomePanel = createWelcomePanel(clickListener);
            mainPanel.add(welcomePanel, "Welcome");
            
            // Add other panels using the ClickListener methods
            try {
                mainPanel.add(clickListener.signIn(), "SignIn");
                mainPanel.add(clickListener.createAccount(), "CreateAccount");
                mainPanel.add(clickListener.user1(), "User1");
                mainPanel.add(clickListener.user2(), "User2");
                mainPanel.add(clickListener.cloudController(), "CloudController");
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            frame.add(mainPanel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            // Show welcome panel first
            cardLayout.show(mainPanel, "Welcome");
        });
    }
    
    private static JPanel createWelcomePanel(ClickListener listener) {
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(200, 20, 20, 20));
        
        Color specificColor = new Color(199, 230, 246);
        welcomePanel.setBackground(specificColor);
        
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
            
        welcomePanel.add(titleLabel);
        welcomePanel.add(descriptionLabel);
        
        JButton createAccountButton = new JButton("Create an Account");
        createAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createAccountButton.addActionListener(listener);
        
        //JButton signInButton = new JButton("Sign In");
       // signInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
       // signInButton.addActionListener(listener);
        
        welcomePanel.add(Box.createVerticalGlue());
        welcomePanel.add(Box.createVerticalStrut(50));
        welcomePanel.add(createAccountButton);
        welcomePanel.add(Box.createVerticalStrut(20));
       // welcomePanel.add(signInButton);
        welcomePanel.add(Box.createVerticalGlue());
        
        return welcomePanel;
    }
}