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
    private static boolean serverStarted = false;
    private static VC_Controller instance = null;
    
    private static ServerSocket serverSocket;
    private static volatile boolean requestPending = false;
    private static volatile String currentRequest = "";
    
    // GUI Components
    private JFrame controllerFrame;
    private static JTextArea requestArea;
    private static JButton acceptButton;
    private static JButton rejectButton;
    private static JButton computeTimeButton;
    private static JButton logoutButton;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    // Keep track of current client connection
    private static volatile Socket currentClientSocket = null;
    private static volatile DataOutputStream currentOutputStream = null;
    
    public static VC_Controller getInstance() {
        return instance;
    }

    public VC_Controller(String id, String name, String password, ArrayList<Client> clientInfo, 
                        Queue<Integer> jobDurations, JPanel mainPanel, CardLayout cardLayout) {
        super(id, name, password);
        this.activeJobs = new ArrayList<>();
        this.completedJobs = new ArrayList<>();
        this.clientInfo = clientInfo;
        this.jobDurations = jobDurations;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        instance = this;
        setupVcGUI();
        
     // Verify GUI components
        System.out.println("[SERVER] Verifying GUI components:");
        System.out.println("- Request Area: " + (requestArea != null ? "OK" : "NULL"));
        System.out.println("- Accept Button: " + (acceptButton != null ? "OK" : "NULL"));
        System.out.println("- Reject Button: " + (rejectButton != null ? "OK" : "NULL"));
        System.out.println("- Compute Time Button: " + (computeTimeButton != null ? "OK" : "NULL"));
        System.out.println("- Controller Frame: " + (controllerFrame != null ? "OK" : "NULL"));
        
        startServer();
        System.out.println("[SERVER] VC_Controller initialization complete");
    }
    
    // creates the GUI that displays the accept, decline, and received jobs 
    // is executed in clicklistener when cloud controller account is submitted
    private void setupVcGUI() {
        controllerFrame = new JFrame("VC Controller Interface");
        controllerFrame.setSize(800, 600);
        controllerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        Color specificColor = new Color(199, 230, 246);
        mainPanel.setBackground(specificColor);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(specificColor);
        JLabel titleLabel = new JLabel("VC Controller Dashboard");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titlePanel.add(titleLabel);

        requestArea = new JTextArea(15, 40);
        requestArea.setEditable(false);
        requestArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(requestArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Incoming Requests"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(specificColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        //adding the buttons
        acceptButton = new JButton("Accept Request");
        rejectButton = new JButton("Reject Request");
        computeTimeButton = new JButton("Compute Completion Time");
        logoutButton = new JButton("Log Out");
        
        Dimension buttonSize = new Dimension(200, 40);
        acceptButton.setPreferredSize(buttonSize);
        rejectButton.setPreferredSize(buttonSize);
        computeTimeButton.setPreferredSize(buttonSize);
        logoutButton.setPreferredSize(buttonSize);
        
        acceptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rejectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        computeTimeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        acceptButton.setEnabled(false);
        rejectButton.setEnabled(false);

        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(acceptButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(rejectButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(computeTimeButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(logoutButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);

        setupButtonListeners();

        requestArea.append("VC Controller Interface Started\n");
        requestArea.append("------------------------------\n");
        requestArea.append("Waiting for incoming requests...\n");
        
        controllerFrame.add(mainPanel);
        controllerFrame.setLocationRelativeTo(null);
        controllerFrame.setVisible(true);
    }

    private void setupButtonListeners() {
        acceptButton.addActionListener(e -> {
            System.out.println("[SERVER] Accept button clicked");
            handleRequest(true);
        });
        
        rejectButton.addActionListener(e -> {
            System.out.println("[SERVER] Reject button clicked");
            handleRequest(false);
        });
        
        computeTimeButton.addActionListener(e -> {
            System.out.println("[SERVER] Compute time button clicked");
            if (jobDurations.isEmpty()) {
                System.out.println("[SERVER] No jobs to compute time for");
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
            System.out.println("[SERVER] Logout button clicked");
            int response = JOptionPane.showConfirmDialog(controllerFrame,
                "Are you sure you want to log out?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);
                
            if (response == JOptionPane.YES_OPTION) {
                System.out.println("[SERVER] Logging out and cleaning up...");
                try {
                    if (serverSocket != null && !serverSocket.isClosed()) {
                        serverSocket.close();
                        System.out.println("[SERVER] Server socket closed");
                    }
                } catch (IOException ex) {
                    System.err.println("[SERVER] Error closing server socket: " + ex.getMessage());
                }
                controllerFrame.dispose();
                cardLayout.show(mainPanel, "Welcome");
                System.out.println("[SERVER] Logout complete");
            }
        });
    }
    public static void startServer() {
        if (!serverStarted) {
            System.out.println("[SERVER] Starting server...");
            new Thread(() -> {
                try {
                    serverSocket = new ServerSocket(1010);
                    serverStarted = true;
                    System.out.println("[SERVER] Server started successfully on port 1010");
                    
                    while (!serverSocket.isClosed()) {
                        try {
                            System.out.println("[SERVER] Waiting for client connection...");
                            Socket clientSocket = serverSocket.accept();
                            System.out.println("[SERVER] New client connected from: " + 
                                clientSocket.getInetAddress());
                            
                            SwingUtilities.invokeLater(() -> {
                                if (instance != null && instance.requestArea != null) {
                                    instance.requestArea.append("Client connected!\n");
                                }
                            });
                            
                            new ClientHandler(clientSocket).start();
                            
                        } catch (IOException e) {
                            if (!serverSocket.isClosed()) {
                                System.err.println("[SERVER] Error accepting client: " + e.getMessage());
                            }
                        }
                    }
                } catch (IOException e) {
                    System.err.println("[SERVER] Server error: " + e.getMessage());
                    e.printStackTrace();
                }
            }).start();
        } else {
            System.out.println("[SERVER] Server already started");
        }
    }
    
    
    public JFrame getControllerFrame() {
        return controllerFrame;
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private DataInputStream inputStream;
        private DataOutputStream outputStream;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            System.out.println("[SERVER] New ClientHandler created for socket: " + socket);
        }

        @Override
        public void run() {
            try {
                System.out.println("[SERVER] ClientHandler starting...");
                inputStream = new DataInputStream(socket.getInputStream());
                outputStream = new DataOutputStream(socket.getOutputStream());
                System.out.println("[SERVER] Streams established");
                
                String messageIn = inputStream.readUTF();
                System.out.println("[SERVER] Received message: " + messageIn);
                
                synchronized (VC_Controller.class) {
                    System.out.println("[SERVER] Setting current connection details");
                    currentClientSocket = socket;
                    currentOutputStream = outputStream;
                    currentRequest = messageIn;
                    requestPending = true;
                    System.out.println("[SERVER] Connection details set, requestPending: " + requestPending);
                }

                // Parse job details with debug logging
                String clientId = "";
                String duration = "";
                String deadline = "";
                try {
                    System.out.println("[SERVER] Parsing job details...");
                    String[] parts = messageIn.split(",");
                    for (String part : parts) {
                        if (part.contains("id='")) {
                            clientId = part.split("'")[1];
                            System.out.println("[SERVER] Found client ID: " + clientId);
                        }
                        if (part.contains("jobDuration='")) {
                            duration = part.split("'")[1];
                            System.out.println("[SERVER] Found duration: " + duration);
                        }
                        if (part.contains("deadline='")) {
                            deadline = part.split("'")[1];
                            System.out.println("[SERVER] Found deadline: " + deadline);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("[SERVER] Error parsing job details: " + e.getMessage());
                    e.printStackTrace();
                }

                final String displayMessage = String.format("""
                    
                    =================================
                    NEW JOB REQUEST RECEIVED:
                    Client ID: %s
                    Duration: %s
                    Deadline: %s
                    =================================
                    """, clientId, duration, deadline);

                System.out.println("[SERVER] Updating GUI...");
                SwingUtilities.invokeLater(() -> {
                    try {
                        requestArea.append(displayMessage);
                        requestArea.setCaretPosition(requestArea.getDocument().getLength());
                        acceptButton.setEnabled(true);
                        rejectButton.setEnabled(true);
                        System.out.println("[SERVER] GUI updated successfully");
                    } catch (Exception e) {
                        System.err.println("[SERVER] Error updating GUI: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
                
            } catch (IOException e) {
                System.err.println("[SERVER] Error in ClientHandler: " + e.getMessage());
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    requestArea.append("Error: " + e.getMessage() + "\n");
                    acceptButton.setEnabled(false);
                    rejectButton.setEnabled(false);
                });
                
            }
        }
    }
    
    private void handleRequest(boolean accepted) {
        System.out.println("[SERVER] handleRequest called with accepted=" + accepted);
        
        synchronized (VC_Controller.class) {
            if (!requestPending) {
                System.out.println("[SERVER] No request pending, returning");
                return;
            }

            try {
            	
            	// Show message dialog in VC Controller window first
                String status = accepted ? "Accepted" : "Rejected";
                JOptionPane.showMessageDialog(
                    controllerFrame,
                    "Job " + status,
                    "Response Sent",
                    accepted ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE
                );
            	
                // Send response to client
                String response = accepted ? "Accepted" : "Rejected";
                System.out.println("[SERVER] Sending response: " + response);
                currentOutputStream.writeUTF(response);
                currentOutputStream.flush();
                System.out.println("[SERVER] Response sent successfully");

                if (accepted) {
                    System.out.println("[SERVER] Processing accepted job");
                    processAcceptedJob(currentRequest);
                } else {
                    System.out.println("[SERVER] Job rejected - not adding to queue or saving");
                }

                SwingUtilities.invokeLater(() -> {
                    requestArea.append("\nRequest " + response + "\n");
                    if (!accepted) {
                        requestArea.append("Job rejected - not added to queue\n");
                    }
                    requestArea.append("=================================\n");
                    acceptButton.setEnabled(false);
                    rejectButton.setEnabled(false);
                });

            } catch (IOException e) {
                System.err.println("[SERVER] Error sending response: " + e.getMessage());
            } finally {
                try {
                    if (currentOutputStream != null) currentOutputStream.close();
                    if (currentClientSocket != null) currentClientSocket.close();
                } catch (IOException e) {
                    System.err.println("[SERVER] Error closing connections: " + e.getMessage());
                }
                requestPending = false;
                currentRequest = "";
                currentClientSocket = null;
                currentOutputStream = null;
            }
        }
    }

    private void processAcceptedJob(String jobRequest) {
        try {
            // First extract and add the duration to the queue
            String[] parts = jobRequest.split(",");
            String durationStr = "";
            String clientId = "";
            
            for (String part : parts) {
                if (part.contains("jobDuration='")) {
                    durationStr = part.split("'")[1].replaceAll("[^0-9]", "");
                }
                if (part.contains("id='")) {
                    clientId = part.split("'")[1];
                }
            }
            
            if (!durationStr.isEmpty()) {
                int duration = Integer.parseInt(durationStr);
                jobDurations.offer(duration);
                System.out.println("[SERVER] Added duration to queue: " + duration);
                
                // Now save the job to AcceptedJobs.txt
                System.out.println("[SERVER] Saving accepted job to file");
                saveJobData(jobRequest);
                
                SwingUtilities.invokeLater(() -> {
                    requestArea.append("Job added to queue. Duration: " + duration + " minutes\n");
                    requestArea.append("Current queue size: " + jobDurations.size() + "\n");
                    requestArea.append("=================================\n");
                });
            }
        } catch (Exception e) {
            System.err.println("[SERVER] Error processing accepted job: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveJobData(String jobData) {
        System.out.println("[SERVER] Starting to save job to AcceptedJobs.txt");
        String srcPath = "src/AcceptedJobs.txt";
        File file = new File(srcPath);
        
        // Create parent directory if it doesn't exist
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.write("=================================\n");
            writer.write("Accepted Job - " + timestamp + "\n");
            writer.write("Job Details: " + jobData + "\n");
            writer.write("=================================\n");
            writer.flush();
            System.out.println("[SERVER] Job saved successfully to AcceptedJobs.txt");
            System.out.println("[SERVER] File location: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("[SERVER] Error saving job to file: " + e.getMessage());
            e.printStackTrace();
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