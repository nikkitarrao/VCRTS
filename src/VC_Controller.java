// VC_Controller.java
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.text.SimpleDateFormat;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import java.util.Date;

public class VC_Controller extends User {
	// Define the enum to distinguish between job and car requests
    private enum RequestType {
        JOB, CAR
    }

    // Variables to track the current request type and data
    private static RequestType currentRequestType;
    public String currentRequestData;  // Data related to the request, whether it's a job or car

    private static JTextArea outputArea;
    private ArrayList<Job> activeJobs;
    private ArrayList<Job> completedJobs;
    private ArrayList<Client> acceptedClients = new ArrayList<>();
    public Queue<Integer> jobDurations;
    private ArrayList<Client> clientInfo;
    private static boolean serverStarted = false;
    private static VC_Controller instance = null;
    private static String currentClientId;
    private static String currentDuration;
    private static String currentDeadline;
    private static String currentVehicleId;
    private static String currentMake;
    private static String currentModel;
   
    
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
    private static JTextArea acceptedJobsArea;
    private static JTextArea rejectedJobsArea;
    private static JTextArea acceptedVehiclesArea;
    private static JTextArea rejectedVehiclesArea;
    
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
        this.acceptedClients = new ArrayList<>();
        this.currentClientId = currentClientId;
        this.currentDuration = currentDuration;
        this.currentDeadline = currentDeadline;
        this.currentVehicleId = currentVehicleId;
        this.currentMake = currentMake;
        this.currentModel = currentModel;
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
        controllerFrame.setSize(1000, 800);
        controllerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        Color specificColor = new Color(199, 230, 246);
        mainPanel.setBackground(specificColor);
        
        //title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(specificColor);
        JLabel titleLabel = new JLabel("VC Controller Dashboard");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        
        //creating split panels
        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
  
        
     // Accepted Items Panel 
        acceptedJobsArea = new JTextArea(10, 30);
        acceptedJobsArea.setEditable(false);
        acceptedJobsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane acceptedJobsScroll = new JScrollPane(acceptedJobsArea);
        TitledBorder acceptedJobsBorder = BorderFactory.createTitledBorder("Accepted Jobs");
        acceptedJobsBorder.setTitleFont(new Font("Serif", Font.BOLD, 16));
        acceptedJobsScroll.setBorder(acceptedJobsBorder); 
        
        acceptedVehiclesArea = new JTextArea(10, 30);
        acceptedVehiclesArea.setEditable(false);
        acceptedVehiclesArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane acceptedVehiclesScroll = new JScrollPane(acceptedVehiclesArea);
        TitledBorder acceptedVehiclesBorder = BorderFactory.createTitledBorder("Accepted Vehicles");
        acceptedVehiclesBorder.setTitleFont(new Font("Serif", Font.BOLD, 16));
        acceptedVehiclesScroll.setBorder(acceptedVehiclesBorder); 
        
        topPanel.add(acceptedJobsScroll);
        topPanel.add(acceptedVehiclesScroll);
        
     // Rejected Items Panel 
        rejectedJobsArea = new JTextArea(10, 30);
        rejectedJobsArea.setEditable(false);
        rejectedJobsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane rejectedJobsScroll = new JScrollPane(rejectedJobsArea);
        TitledBorder rejectedJobsBorder = BorderFactory.createTitledBorder("Rejected Jobs");
        rejectedJobsBorder.setTitleFont(new Font("Serif", Font.BOLD, 16));
        rejectedJobsScroll.setBorder(rejectedJobsBorder);        
        
        

        rejectedVehiclesArea = new JTextArea(10, 30);
        rejectedVehiclesArea.setEditable(false);
        rejectedVehiclesArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane rejectedVehiclesScroll = new JScrollPane(rejectedVehiclesArea);
        TitledBorder rejectedVehiclesBorder = BorderFactory.createTitledBorder("Rejected Vehicles");
        rejectedVehiclesBorder.setTitleFont(new Font("Serif", Font.BOLD, 16));
        rejectedVehiclesScroll.setBorder(rejectedVehiclesBorder);        
        
        
        bottomPanel.add(rejectedJobsScroll);
        bottomPanel.add(rejectedVehiclesScroll);
        
     // Incoming Requests Panel
        requestArea = new JTextArea(10, 40);
        requestArea.setEditable(false);
        requestArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane incomingRequestsScroll = new JScrollPane(requestArea);
        TitledBorder incomingRequestsBorder = BorderFactory.createTitledBorder("Incoming Requests");
        incomingRequestsBorder.setTitleFont(new Font("Serif", Font.BOLD, 16));
        incomingRequestsScroll.setBorder(incomingRequestsBorder);        
        
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(specificColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
        
     // Central Panel to hold everything except buttons
        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.add(incomingRequestsScroll, BorderLayout.NORTH);
        centralPanel.add(topPanel, BorderLayout.CENTER);
        centralPanel.add(bottomPanel, BorderLayout.SOUTH);
        
       // requestArea = new JTextArea(15, 40);
       // requestArea.setEditable(false);
       // requestArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
       // JScrollPane scrollPane = new JScrollPane(requestArea);
        //scrollPane.setBorder(BorderFactory.createTitledBorder("Incoming Requests"));

       // JPanel buttonPanel = new JPanel();
        //buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        //buttonPanel.setBackground(specificColor);
       // buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        //adding the buttons
        //acceptButton = new JButton("Accept Request");
       // rejectButton = new JButton("Reject Request");
       // acceptButton2 = new JButton("Accept Car Request");
        //rejectButton2 = new JButton("Reject Car Request");
        //computeTimeButton = new JButton("Compute Completion Time");
        //logoutButton = new JButton("Log Out");
        
       // Dimension buttonSize = new Dimension(200, 40);
       // acceptButton.setPreferredSize(buttonSize);
       // rejectButton.setPreferredSize(buttonSize);
       // acceptButton2.setPreferredSize(buttonSize);
       // rejectButton2.setPreferredSize(buttonSize);
        //computeTimeButton.setPreferredSize(buttonSize);
        //logoutButton.setPreferredSize(buttonSize);
        
       // acceptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
       // rejectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //acceptButton2.setAlignmentX(Component.CENTER_ALIGNMENT);
       // rejectButton2.setAlignmentX(Component.CENTER_ALIGNMENT);
        //computeTimeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
       // logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
       // acceptButton.setEnabled(false);
       // rejectButton.setEnabled(false);
       // acceptButton2.setEnabled(false);
       // rejectButton2.setEnabled(false);

      /**  buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(acceptButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(rejectButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(computeTimeButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(logoutButton);
        **/
       // buttonPanel.add(Box.createVerticalStrut(10));
       // buttonPanel.add(acceptButton2);
       // buttonPanel.add(Box.createVerticalStrut(10));
       // buttonPanel.add(rejectButton2);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(centralPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        
        controllerFrame.add(mainPanel);
        controllerFrame.setLocationRelativeTo(null);
        controllerFrame.setVisible(true);
        
        
        requestArea.append("VC Controller Interface Started\n");
        requestArea.append("------------------------------\n");
        requestArea.append("Waiting for incoming requests...\n");
        
       
        setupButtonListeners();
    }

    private void setupButtonListeners() {
    	//check to see if 
    	System.out.println("[SERVER] Accept button clicked");
        acceptButton.addActionListener(e -> {
        	if (currentRequestType == RequestType.JOB) {
        		handleJobRequest(true);
            } else {
            	handleCarRequest(true);
            }
            
        });
        
        rejectButton.addActionListener(e -> {
            System.out.println("[SERVER] Reject button clicked");
            if (currentRequestType == RequestType.JOB) {
        		handleJobRequest(false);
            } else {
            	handleCarRequest(false);
            }
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
              
           
            //iterate through accepted clients arraylist to print out only accepted jobs 
            for (int i = 0; i < acceptedClients.size() && i < times.size(); i++) {
                Client client = acceptedClients.get(i);
                String completionTime = times.get(i);
                messageBuilder.append(String.format(
                    "Client ID: %s<br>Job Duration: %s<br>Completion Time: %s<br><br>",
                    client.getId(),
                    client.getJobDuration(),
                    completionTime
                ));
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
                	// Create socket and set timeouts
     

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

                // Read incoming message
                String messageIn = inputStream.readUTF();
                System.out.println("[SERVER] Received message: " + messageIn);

                // Synchronize access to shared variables
                synchronized (VC_Controller.class) {
                    System.out.println("[SERVER] Setting current connection details");
                    currentClientSocket = socket;
                    currentOutputStream = outputStream;

                    // Detect job or vehicle request
                    if (messageIn.contains("jobDuration=")) {
                        currentRequestType = RequestType.JOB;
                        VC_Controller.currentRequest = messageIn;
                        VC_Controller.requestPending = true;
                        System.out.println("[SERVER] Job request detected");
                    } else if (messageIn.contains("Make=")) {
                        currentRequestType = RequestType.CAR;
                        VC_Controller.currentRequest = messageIn;
                        VC_Controller.requestPending = true;
                        System.out.println("[SERVER] Car request detected");
                    }

                    currentRequest = messageIn;
                    requestPending = true;
                    System.out.println("[SERVER] Connection details set, requestPending: " + requestPending);
                }

                // Parse job details if it's a job request
                if (currentRequestType == RequestType.JOB) {
                    parseJobDetails(messageIn);
                }
                // Parse vehicle details if it's a vehicle request
                else if (currentRequestType == RequestType.CAR) {
                    parseVehicleDetails(messageIn);
                }

            } catch (IOException e) {
                System.err.println("[SERVER] Error in ClientHandler: " + e.getMessage());
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    requestArea.append("Error: " + e.getMessage() + "\n");
                    acceptButton.setEnabled(false);
                    rejectButton.setEnabled(false);
                   // acceptButton2.setEnabled(false);
                   // rejectButton2.setEnabled(false);
                });
            }
        }

        // Method to parse job details
        private void parseJobDetails(String messageIn) {
            String clientId = "";
            String duration = "";
            String deadline = "";
            
            try {
                System.out.println("[SERVER] Parsing Job details...");
                String[] parts = messageIn.split(",");
                for (String part : parts) {
                    if (part.contains("id='")) {
                        clientId = part.split("'")[1];
                        System.out.println("[SERVER] Found client ID: " + clientId);
                        currentClientId = clientId;
                        
                    }
                    if (part.contains("jobDuration='")) {
                        duration = part.split("'")[1];
                        System.out.println("[SERVER] Found duration: " + duration);
                        currentDuration= duration;
                    }
                    if (part.contains("deadline='")) {
                        deadline = part.split("'")[1];
                        System.out.println("[SERVER] Found deadline: " + deadline);
                        currentDeadline= deadline;
                    }
                }
            } catch (Exception e) {
                System.err.println("[SERVER] Error parsing job details: " + e.getMessage());
                e.printStackTrace();
            }

            // Construct message to display job request in the GUI
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
                   // acceptButton2.setEnabled(true);
                   // rejectButton2.setEnabled(true);
                    System.out.println("[SERVER] GUI updated successfully");
                } catch (Exception e) {
                    System.err.println("[SERVER] Error updating GUI: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        }

        // Method to parse vehicle details
        private void parseVehicleDetails(String messageIn) {
            String id = "";
            String model = "";
            String make = "";

            try {
                System.out.println("[SERVER] Parsing Vehicle details...");
                String[] parts = messageIn.split(",");
                for (String part : parts) {
               	 // Check for specific fields in the vehicle part
                   if (part.contains("ID=")) {
                       id = part.split("=")[1];
                       System.out.println("[SERVER] Found id: " + id);
                       currentVehicleId = id;
                       
                   }
              	 // Check for specific fields in the vehicle part}
                   if (part.contains("Make=")) {
                       make = part.split("=")[1];
                       System.out.println("[SERVER] Found make: " + make);
                       currentMake = make;
                   }
                   if (part.contains("Model=")) {
                       model = part.split("=")[1];
                       System.out.println("[SERVER] Found model: " + model);
                       currentModel = model;

                   } 
                }
            } catch (Exception e) {
                System.err.println("[SERVER] Error parsing vehicle details: " + e.getMessage());
                e.printStackTrace();
            }

            // Construct message to display vehicle request in the GUI
            final String vehicleMessage = String.format("""
            
                    =================================
                    NEW VEHICLE REQUEST RECEIVED:
                    Owner ID: %s
                    Make: %s
                    Model: %s
                    =================================
                    """, id, make, model);

            System.out.println("[SERVER] Updating GUI with vehicle request...");
            SwingUtilities.invokeLater(() -> {
                try {
                    requestArea.append(vehicleMessage);
                    requestArea.setCaretPosition(requestArea.getDocument().getLength());
                    acceptButton.setEnabled(true);
                    rejectButton.setEnabled(true);
                   // acceptButton2.setEnabled(true);
                   // rejectButton2.setEnabled(true);
                    System.out.println("[SERVER] GUI updated successfully with vehicle request");
                } catch (Exception e) {
                    System.err.println("[SERVER] Error updating GUI: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        }
    }
    
    private void handleJobRequest(boolean accepted) {
        System.out.println("[SERVER] handleRequest called with accepted=" + accepted);
        for (Client client : clientInfo) {
	        // Assuming clientId uniquely identifies each job
	    }
        
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
                    SwingUtilities.invokeLater(() -> {
                        if (acceptedJobsArea != null) {
                        	acceptedJobsArea.append("\nJob Accepted:\n");
                            acceptedJobsArea.append("Client ID: " + currentClientId + "\n");
                            acceptedJobsArea.append("Duration: " + currentDuration + "\n");
                            acceptedJobsArea.append("Deadline: " + currentDeadline + "\n");
                            acceptedJobsArea.append("=================================\n");
                        }
                    });
                    processAcceptedJob(currentRequest);
                } else {
                    System.out.println("[SERVER] Job rejected - not adding to queue or saving");
                    SwingUtilities.invokeLater(() -> {
                        if (rejectedJobsArea != null) {
                            rejectedJobsArea.append("\nJob Rejected:\n");
                            rejectedJobsArea.append("Client ID: " + currentClientId + "\n");
                            rejectedJobsArea.append("Duration: " + currentDuration + "\n");
                            rejectedJobsArea.append("Deadline: " + currentDeadline + "\n");
                            rejectedJobsArea.append("=================================\n");
                        }
                    });
                }
                
                
                SwingUtilities.invokeLater(() -> {
                    requestArea.append("\nRequest " + response + "\n");
                    if (!accepted) {
                        requestArea.append("rejected - not added to queue\n");
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
   
    
    private void handleCarRequest(boolean accepted) {
        // Start time for the request processing
        long startTime = System.currentTimeMillis();
        
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
                    "Car " + status,
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
                    System.out.println("[SERVER] Processing accepted vehicle");
                    SwingUtilities.invokeLater(() -> {
                        if (acceptedVehiclesArea != null) {
                            acceptedVehiclesArea.append("\nVehicle Accepted:\n");
                            acceptedVehiclesArea.append("Owner ID: " + currentVehicleId + "\n");
                            acceptedVehiclesArea.append("Make: " + currentMake + "\n");
                            acceptedVehiclesArea.append("Model: " + currentModel + "\n");
                            acceptedVehiclesArea.append("=================================\n");
                        }
                    });
                    processAcceptedCar(currentRequest);
                } else {
                    System.out.println("[SERVER] Vehicle rejected - not adding to system");
                    SwingUtilities.invokeLater(() -> {
                        if (rejectedVehiclesArea != null) {
                            rejectedVehiclesArea.append("\nVehicle Rejected:\n");
                            rejectedVehiclesArea.append("Owner ID: " + currentVehicleId + "\n");
                            rejectedVehiclesArea.append("Make: " + currentMake + "\n");
                            rejectedVehiclesArea.append("Model: " + currentModel + "\n");
                            rejectedVehiclesArea.append("=================================\n");
                        }
                    });
                }


                SwingUtilities.invokeLater(() -> {
                    requestArea.append("\nRequest " + response + "\n");
                    if (!accepted) {
                        requestArea.append("rejected - not added to queue\n");
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

                // End time for the request processing
                long endTime = System.currentTimeMillis();
                // Log the duration it took to process the car request
                System.out.println("[SERVER] Car request processing time: " + (endTime - startTime) + "ms");
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
                
             // Find and add the client to acceptedClients
                for (Client client : clientInfo) {
                    if (client.getId().equals(clientId)) { // Find matching client
                        acceptedClients.add(client); // Add to accepted list
                        break;
                    }
                }
                
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
    
    private void processAcceptedCar(String carRequest) {
        try {
            // First extract and add the duration to the queue
            String[] parts = carRequest.split(",");
            String name = "";
            String ownerid = "";
            
            for (String part : parts) {
                if (part.contains("Name='")) {
                    name = part.split("'")[1].replaceAll("[^0-9]", "");
                }
                if (part.contains("ID='")) {
                    ownerid = part.split("'")[1];
                }
            }
            
                
                // Now save the job to AcceptedJobs.txt
                System.out.println("[SERVER] Saving accepted car to file");
                saveCarData(carRequest);
                
                SwingUtilities.invokeLater(() -> {
                    requestArea.append("Car added to file");
                    //requestArea.append("Current queue size: " + jobDurations.size() + "\n");
                    //requestArea.append("=================================\n");
                });
           // }
        } catch (Exception e) {
            System.err.println("[SERVER] Error processing accepted car: " + e.getMessage());
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
        
     // Extract clientId, duration, and deadline from jobData
        String clientId = "";
        String duration = "";
        String deadline = "";
        String timestamp = "";
       
        String[] parts = jobData.split(",");
        for (String part : parts) {
            if (part.contains("jobDuration='")) {
                duration = part.split("'")[1];
            }
            if (part.contains("id='")) {
                clientId = part.split("'")[1];
            }
            if (part.contains("deadline='")) {
                deadline = part.split("'")[1];
            }
            if (part.contains("timestamp='")) {
                timestamp = part.split("'")[1];
            }
        }
        
     // Database credentials
        String url = "jdbc:mysql://127.0.0.1:3306/vcrts"; 
        String user = "root"; 
        String password = "2daughters"; 
        
        //adding the job data to SQL database
        try {
        	Connection connection =  DriverManager.getConnection(url, user, password);
        	System.out.println("Database Connection Successful");
        	String sql = "INSERT INTO job_owners (clientID, duration, deadline, timeStamp) VALUES (?, ?, ?, ?)";
        	try(PreparedStatement stmt = connection.prepareStatement(sql)){
        		stmt.setString(1, clientId);
        		stmt.setString(2, duration);
        		stmt.setString(3, deadline); 
        		stmt.setString(4, timestamp); 
        		stmt.executeUpdate();
        	}
        }
        	catch(Exception e) {
        		e.printStackTrace();
        	//	return "Error processing request";
        	} 
      }
    
    
    private void saveCarData(String carData) {
        System.out.println("[SERVER] Starting to save car to AcceptedCars.txt");
        String srcPath = "src/AcceptedCars.txt"; // Use a consistent file path
        File file = new File(srcPath);

        // Ensure parent directory exists
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.write("=================================\n");
            writer.write("Accepted Car - " + timestamp + "\n");
            writer.write("Car Details: " + carData + "\n");
            writer.write("=================================\n");
            writer.flush();
            System.out.println("[SERVER] Job saved successfully to AcceptedCars.txt");
            System.out.println("[SERVER] File location: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("[SERVER] Error saving job to file: " + e.getMessage());
            e.printStackTrace();
        }
        

        System.out.println("Received car data: " + carData);
        
        

        String ownerId = "";
        String make = "";
        String model = "";
        String year = "";
        String vin = "";

        String timestamp = "";
       
        String[] parts = carData.split(",");
        for (String part : parts) {
            part = part.trim();  // Trim extra spaces
            String[] keyValue = part.split("=");
            
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].replace("'", "").trim(); // Remove quotes if they exist

                switch (key) {
                    case "ID":
                        ownerId = value;
                        break;
                    case "Make":
                        make = value;
                        break;
                    case "Model":
                        model = value;
                        break;
                    case "Year":
                        year = value;
                        break;
                    case "VIN":
                        vin = value;
                        break;
                    case "timestamp":
                        timestamp = value;
                        break;

                }
            }
        }


        System.out.println("Owner ID: " + ownerId);
        System.out.println("Make: " + make);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("VIN: " + vin);
        
        
        // Database credentials
        String url = "jdbc:mysql://127.0.0.1:3306/vcrts"; 
        String user = "root"; 
        String password = "2daughters"; 
        
        //adding the job data to SQL database
        try {
        	Connection connection =  DriverManager.getConnection(url, user, password);
        	System.out.println("Database Connection Successful");
        	String sql = "INSERT INTO car_owners (ownerId, make, model, year, vin, timestamp) VALUES (?, ?, ?, ?, ?, ?)";
        	
        	try(PreparedStatement stmt = connection.prepareStatement(sql)){
        		stmt.setString(1, ownerId);
        		stmt.setString(2, make);
        		stmt.setString(3, model); 

        		stmt.setString(4, year);
        		stmt.setString(5, vin);
        		stmt.setString(6, timestamp);
        		stmt.executeUpdate();
        	}
        
        	
        }
        	catch(Exception e) {
        		System.err.println("Error inserting data into the database.");
        		e.printStackTrace();
        	//	return "Error processing request";
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

