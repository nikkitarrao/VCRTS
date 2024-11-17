import java.io.*;
import java.util.*;
public class ClientTester {

	public static void main(String[] args) {
		Client client = new Client("1", "John Doe", "password123", "john@example.com", "TechCorp", "2 hours", "5 PM");
		client.talkToServer(client.toString());
	}

}
