import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*public class ServerGUI implements ActionListener{
	CardLayout cardLayout;
    JPanel mainPanel;
    JPanel computePanel;
    JTextArea outputArea;
    JButton computeButton, acceptButton, rejectButton, backButton;
    String messageIn;
    
  //for the client server
  	static ServerSocket serverSocket = null;
  	static Socket socket;
  	static DataInputStream inputStream;
  	static DataOutputStream outputStream;
    
    public ServerGUI(CardLayout cardLayout, JPanel mainPanel) {
    	this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        
        computePanel = new JPanel();
        computePanel.setLayout(new BoxLayout(computePanel, BoxLayout.Y_AXIS));
        computePanel.setBorder(BorderFactory.createEmptyBorder(175, 20, 20, 20));
        computePanel.setBackground(new Color(199, 230, 246));
        
    	outputArea = new JTextArea();
        
        JButton computeButton = new JButton("Compute");
        JButton acceptButton = new JButton("Accept");
        JButton rejectButton = new JButton("Reject");
        JButton backButton = new JButton("Log Out");
        
        computePanel.add(acceptButton);
        computePanel.add(rejectButton);
        computePanel.add(computeButton);
        computePanel.add(backButton);
        
        computeButton.addActionListener(this);
        acceptButton.addActionListener(this);
        rejectButton.addActionListener(this);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));
        
    }
    
    public void actionPerformed(ActionEvent e) {
    	/*if(e.getSource() == backButton) {
    		cardLayout.show(mainPanel, "Welcome");
    	}
    	if(e.getSource() == computeButton) {
    		// Initialize a StringBuilder to accumulate all the client info
        	StringBuilder messageBuilder = new StringBuilder("<html>");
        	
            for(int i = 0; i < clientInfo.size(); i++) {
            	
            	Client client = clientInfo.get(i);
            	String currentCompletionTime = completionTime.get(i);
            	  // Add the client's information to the message
                String clientMessage = String.format("Client ID: %s<br> Job Duration: %s<br>Time till completion: %s<br><br>",
                        client.getId(), client.getDuration() + " mins", currentCompletionTime );
                
                messageBuilder.append(clientMessage);
            	} 
            
         // Close the HTML tags for the message
            messageBuilder.append("</html>"); 
    
            JOptionPane.showMessageDialog(null, messageBuilder.toString(), "Estimated Completion Times", JOptionPane.INFORMATION_MESSAGE);
    	}
    	if(e.getSource() == acceptButton) {
    		if(serverSocket == null) {
    			JOptionPane.showMessageDialog(new JFrame("Error"), "Start Server Please");
    		} else {
    			try {
					socket = serverSocket.accept();
					InputStream is = socket.getInputStream();
					inputStream = new DataInputStream(is);
					messageIn = inputStream.readUTF();
					outputArea.setText("JobDetails: " + messageIn);
					//save the data to file
					outputStream.writeUTF("Accepted");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
    		}
    	}
    	if(e.getSource() == rejectButton) {
    		try {
				outputStream.writeUTF("Rejected");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
    }
    
    public JPanel getPanel() {
        return computePanel;
    }
    
    public static void main(String args []) {
    
    	//new ServerGUI(cardLayout, mainPanel);
    
    }
}*/