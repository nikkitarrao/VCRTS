import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerTester {
	
	public static void startServer() {
	    try (ServerSocket serverSocket = new ServerSocket(1111)) {
	        System.out.println("Server started and waiting for clients...");
	        while (true) {
	            Socket socket = serverSocket.accept();
	            System.out.println("Client connected!");
	            // Create a new thread for each client
	            new Thread(new ClientHandler(socket)).start();
	        }
	    } catch (IOException e) {
	        System.err.println("Server error: " + e.getMessage());
	    }
	}

	static class ClientHandler implements Runnable {
	    private Socket socket;

	    public ClientHandler(Socket socket) {
	        this.socket = socket;
	    }

	    @Override
	    public void run() {
	        try (DataInputStream inputStream = new DataInputStream(socket.getInputStream());
	             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {

	            String messageIn = inputStream.readUTF();
	            System.out.println("Job data received from client: " + messageIn);

	            System.out.println("Do you want to accept or reject the job? (yes/no):");
	            Scanner scanner = new Scanner(System.in);
	            String decision = scanner.nextLine();

	            if (decision.equalsIgnoreCase("yes")) {
	                outputStream.writeUTF("Accepted");
	                saveJobData(messageIn);
	            } else {
	                outputStream.writeUTF("Rejected");
	            }

	        } catch (IOException e) {
	            System.err.println("Error handling client: " + e.getMessage());
	        }
	    }
        }

        // Helper method to save job data to a file
        private static void saveJobData(String jobData) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("AcceptedJobs.txt", true))) {
                writer.write(jobData);
                writer.newLine();
            } catch (IOException e) {
                System.err.println("Error saving job to file: " + e.getMessage());
            }
        }

	public static void main(String[] args) {
		ServerTester.startServer();
		
		ServerTester.saveJobData(null);

	}

}
