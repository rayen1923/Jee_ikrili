package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbc {
	 public static Connection createConnection() {
	        Connection con = null;
	        String url = "jdbc:mysql://127.0.0.1:3306/ikrili";
	        String username = "root"; 
	        String password = ""; 

	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver"); 
	            System.out.println("JDBC driver loaded successfully.");
	            
	            con = DriverManager.getConnection(url, username, password);
	            System.out.println("Database connection established successfully.");
	        } catch (ClassNotFoundException e) {
	            System.err.println("JDBC Driver not found.");
	            e.printStackTrace();
	        } catch (SQLException e) {
	            System.err.println("Connection failed.");
	            e.printStackTrace();
	        }
	        
	        return con;
    }
	 public static void main(String[] args) {
	        Connection con = createConnection();
	        if (con != null) {
	            System.out.println("Connection is successful!");
	            try {
	                // Optionally, you can run a simple query to test
	                // con.createStatement().executeQuery("SELECT 1"); // Example query
	                con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("Connection failed.");
	        }
	    }
}
