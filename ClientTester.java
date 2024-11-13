import java.io.*;
import java.util.*;
public class ClientTester {

	public static void main(String[] args) {
		try {
			 Client client = new Client("C001", "Client1", "pass123", "client1@example.com", "ABC Corp", "2h", "12/12/2024");
		     
				client.talkToServer();
				
		    } catch (Exception e) {
		        System.out.println("Error starting client: " + e.getMessage());
		    }

		
	}

}
