// VC_Controller.java
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.text.SimpleDateFormat;

public class VC_Controller extends User {
    private static JTextArea outputArea;
    private ArrayList<Job> activeJobs;
    private ArrayList<Job> completedJobs;
    public Queue<Integer> jobDurations;
    private ArrayList<Client> clientInfo;
    private static boolean serverStarted = false;  // Track if server is running
    private static VC_Controller instance = null;  // Singleton instance
    
    // Server components
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static DataInputStream inputStream;
    private static DataOutputStream outputStream;
    private static boolean requestPending = false;
    private static String currentRequest = "";
    
    // GUI Components
    private JFrame controllerFrame;
    private static JTextArea requestArea;
    private static JButton acceptButton;
    private static JButton rejectButton;
    private static JButton computeTimeButton;
    private static JButton logoutButton;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    public static VC_Controller getInstance() {
        return instance;
    }

    public VC_Controller(String id, String name, String password, ArrayList<Client> clientInfo, 
                        Queue<Integer> jobDurations, JPanel mainPanel, CardLayout cardLayout) {
        super(id, name, password);
        this.activeJobs = new ArrayList<>();
        this.completedJobs = new ArrayList<>();
        this.clientInfo = clientInfo;
        this.jobDurations = new LinkedList<>();
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        instance = this;
        setupVcGUI();
        startServer();
    }
    
    private void setupVcGUI() {
        controllerFrame = new JFrame("VC Controller Interface");
        controllerFrame.setSize(800, 600);
        controllerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        Color specificColor = new Color(199, 230, 246);
        mainPanel.setBackground(specificColor);

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(specificColor);
        JLabel titleLabel = new JLabel("VC Controller Dashboard");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titlePanel.add(titleLabel);

        // Request Area Panel
        requestArea = new JTextArea(15, 40);
        requestArea.setEditable(false);
        requestArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(requestArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Incoming Requests"));

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(specificColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        acceptButton = new JButton("Accept Request");
        rejectButton = new JButton("Reject Request");
        computeTimeButton = new JButton("Compute Completion Time");
        logoutButton = new JButton("Log Out");
        
        // Style buttons
        Dimension buttonSize = new Dimension(200, 40);
        acceptButton.setPreferredSize(buttonSize);
        rejectButton.setPreferredSize(buttonSize);
        computeTimeButton.setPreferredSize(buttonSize);
        logoutButton.setPreferredSize(buttonSize);
        
        // Center align buttons
        acceptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rejectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        computeTimeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Initially disable buttons until request comes in
        acceptButton.setEnabled(false);
        rejectButton.setEnabled(false);

        // Add buttons to panel with spacing
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(acceptButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(rejectButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(computeTimeButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(logoutButton);

        // Add components to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);

        // Add button listeners
        setupButtonListeners();

        // Add initial message
        requestArea.append("VC Controller Interface Started\n");
        requestArea.append("------------------------------\n");
        requestArea.append("Waiting for incoming requests...\n");
        
        controllerFrame.add(mainPanel);
        controllerFrame.setLocationRelativeTo(null);
        controllerFrame.setVisible(true);
    }

    private void setupButtonListeners() {
        acceptButton.addActionListener(e -> handleRequest(true));
        rejectButton.addActionListener(e -> handleRequest(false));
        
        computeTimeButton.addActionListener(e -> {
            if (jobDurations.isEmpty()) {
                JOptionPane.showMessageDialog(controllerFrame,
                    "No jobs to compute time for",
                    "No Jobs",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            ArrayList<String> times = computeCompletionTime(new LinkedList<>(jobDurations));
            StringBuilder messageBuilder = new StringBuilder("<html><b>Estimated Completion Times:</b><br><br>");
            
            int jobIndex = 0;
            for (Client client : clientInfo) {
                if (jobIndex < times.size()) {
                    String completionTime = times.get(jobIndex);
                    messageBuilder.append(String.format(
                        "Client ID: %s<br>Job Duration: %s<br>Completion Time: %s<br><br>",
                        client.getId(),
                        client.getJobDuration(),
                        completionTime
                    ));
                    jobIndex++;
                }
            }
            
            messageBuilder.append("</html>");
            JOptionPane.showMessageDialog(controllerFrame,
                messageBuilder.toString(),
                "Completion Times",
                JOptionPane.INFORMATION_MESSAGE);
        });

        logoutButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(controllerFrame,
                "Are you sure you want to log out?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);
                
            if (response == JOptionPane.YES_OPTION) {
                controllerFrame.dispose();
                cardLayout.show(mainPanel, "Welcome");
            }
        });
    }

    public static void startServer() {
    	 if (!serverStarted) {
             try {
                 serverSocket = new ServerSocket(1010);
                 serverStarted = true;
                 System.out.println("Server started and waiting for clients");
                 
                 new Thread(() -> {
                     while (true) {
                         try {
                             Socket clientSocket = serverSocket.accept();
                             System.out.println("Client connected!");
                             if (instance != null && instance.requestArea != null) {
                                 SwingUtilities.invokeLater(() -> 
                                     instance.requestArea.append("Client connected!\n")
                                 );
                             }
                             new Thread(new ClientHandler(clientSocket)).start();
                         } catch (IOException e) {
                             System.err.println("Error accepting client: " + e.getMessage());
                         }
                     }
                 }).start();
             } catch (IOException e) {
                 System.err.println("Server error: " + e.getMessage());
             }
         }
    }
    
 //  controllerFrame is accessible
    public JFrame getControllerFrame() {
        return controllerFrame;
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
            	DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                VC_Controller.inputStream = inputStream;
                VC_Controller.outputStream = outputStream;
                
                String messageIn = inputStream.readUTF();
                System.out.println("Job data received from client: " + messageIn);
                currentRequest = messageIn;
                requestPending = true;

                // Parse important information for display
                String clientId = "";
                String duration = "";
                String deadline = "";
                try {
                    // Parse the client information from the toString() format
                    String[] parts = messageIn.split(",");
                    for (String part : parts) {
                        if (part.contains("id='")) {
                            clientId = part.split("'")[1];
                        }
                        if (part.contains("jobDuration='")) {
                            duration = part.split("'")[1];
                        }
                        if (part.contains("deadline='")) {
                            deadline = part.split("'")[1];
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing job details: " + e.getMessage());
                }

                // Format the display message
                String displayMessage = String.format("""
                    
                    =================================
                    NEW JOB REQUEST RECEIVED:
                    Client ID: %s
                    Duration: %s
                    Deadline: %s
                    =================================
                    """, clientId, duration, deadline);

                SwingUtilities.invokeLater(() -> {
                    requestArea.append(displayMessage);
                    requestArea.setCaretPosition(requestArea.getDocument().getLength());
                    
                    acceptButton.setEnabled(true);
                    rejectButton.setEnabled(true);
                });
                
            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
                SwingUtilities.invokeLater(() -> {
                    requestArea.append("Error: " + e.getMessage() + "\n");
                    acceptButton.setEnabled(false);
                    rejectButton.setEnabled(false);
                });
            }
        }
    }
    
    private void handleRequest(boolean accepted) {
        if (!requestPending) return;

        try {
            if (outputStream != null) {
                String response = accepted ? "Accepted" : "Rejected";
                outputStream.writeUTF(response);
                
                SwingUtilities.invokeLater(() -> {
                    requestArea.append("\nRequest " + response + "\n");
                    requestArea.append("=================================\n");
                    requestArea.setCaretPosition(requestArea.getDocument().getLength());

                    if (accepted && currentRequest != null && !currentRequest.isEmpty()) {
                        try {
                            // Parse job duration and add to queue
                            String[] parts = currentRequest.split(",");
                            String durationStr = "";
                            for (String part : parts) {
                                if (part.contains("jobDuration='")) {
                                    durationStr = part.split("'")[1].replaceAll("[^0-9]", "");
                                    break;
                                }
                            }
                            
                            if (!durationStr.isEmpty()) {
                                int duration = Integer.parseInt(durationStr);
                                jobDurations.offer(duration);
                                saveJobData(currentRequest);
                                
                                requestArea.append("Job added to queue. Duration: " + duration + " minutes\n");
                                requestArea.append("Current queue size: " + jobDurations.size() + "\n");
                                requestArea.append("=================================\n");
                            }
                        } catch (Exception e) {
                            requestArea.append("Error processing job duration: " + e.getMessage() + "\n");
                            e.printStackTrace();
                        }
                    }

                    acceptButton.setEnabled(false);
                    rejectButton.setEnabled(false);
                });

                requestPending = false;
                currentRequest = "";
            }
        } catch (IOException e) {
            SwingUtilities.invokeLater(() -> 
                requestArea.append("Error sending response: " + e.getMessage() + "\n")
            );
        }
    }

    private static void saveJobData(String jobData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("AcceptedJobs.txt", true))) {
            writer.write(jobData);
            writer.newLine();
            writer.write("-----------------------------------");
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving job to file: " + e.getMessage());
        }
    }

    public ArrayList<String> computeCompletionTime(Queue<Integer> jobDurations) {
        int totalDuration = 0;
        ArrayList<String> computedTimes = new ArrayList<>();
        Queue<Integer> tempQueue = new LinkedList<>(jobDurations);
        
        while (!tempQueue.isEmpty()) {
            totalDuration += tempQueue.poll();
            computedTimes.add(totalDuration + " mins");
        }
        return computedTimes;
    }
}