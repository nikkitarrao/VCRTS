import java.io.*;
import java.net.Socket;
import java.util.*;
public class ClientTester {
	
	/*public static void talkToServer(String jobDetails) {
        try (Socket socket = new Socket("localhost", 1111); // Connect to server at localhost:1111
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {

            System.out.println("Connected to server!");

            // Send job details to the server
            outputStream.writeUTF(jobDetails);
            outputStream.flush();

            // Read response from the server
            String response = inputStream.readUTF();
            System.out.println("Response from server: " + response);

        } catch (IOException e) {
            System.err.println("Error communicating with server: " + e.getMessage());
        }
    }*/

	public static void main(String[] args) {
		//id, name, password, email, company, duration, deadline
		//Client client = new Client("1", "John Doe", "password123", "john@example.com", "TechCorp", "2 hours", "5 PM");
		/*Scanner in = new Scanner(System.in);
		System.out.println("Enter ID: ");
		String id = in.nextLine();
		
		System.out.println("Name: ");
		String name = in.nextLine();
		
		System.out.println("Password: ");
		String pw = in.nextLine();
		
		System.out.println("Email: ");
		String email = in.nextLine();
		
		System.out.println("Company: ");
		String company = in.nextLine();
		
		System.out.println("Job Duration (mins): ");
		String duration = in.nextLine();
		
		System.out.println("Deadline (hrs): ");
		String deadline = in.nextLine();
		
		Client client = new Client(id, name, pw, email, company, duration, deadline);*/
		
		//ClientTester.talkToServer(client.toString());
	}

}
