import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/* public class DatabaseConnection {
	// Database credentials
	private static final String url = "jdbc:mysql://localhost:3306/VCRTS"; 
	private static final String user = "root"; 
	private static final String password = "nrt123"; 
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}
	
	  

        // Establish the connection
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection to the database successful!");

            // Example query (optional)
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM your_table";  // Replace with your SQL query
            statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } 
	
} */