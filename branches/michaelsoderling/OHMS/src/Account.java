
import java.util.*;
import javax.sql.*;
import java.sql.*;
import java.util.Date;

public class Account {
	private int c_Account_ID;
	private String c_F_name;
	private String c_S_name;
	private boolean c_Gender;
	private String c_Phone;
	private String c_Email;
	private String c_Address;
	private Date c_Date;
	
	private Connection dbConnection;
	private Statement stmt = null;
	private String dbUrl;
	
	// Fetch Variable data from the database
	// If user does not exist, return false
	public boolean Fetch_All(String i_email, String i_pwd) throws SQLException
	{
		if (stmt != null)
		{
			ResultSet returnedSet = stmt.executeQuery("Select from account where email = '" + i_email + "'");
			if (returnedSet.first()) {
				c_Account_ID = (int)returnedSet.getDouble("accountID");
				c_F_name = returnedSet.getString("f_name");
				c_S_name = returnedSet.getString("s_name");
				c_Gender = returnedSet.getBoolean("gender");
				c_Phone = returnedSet.getString("phone");
				c_Email = returnedSet.getString("email");
				c_Address = returnedSet.getString("address");
				c_Date = returnedSet.getDate("date");
				return true;
			}else return false;
		}else return false;
	}
	
	// Creates a unique ID, inserts today's date
	// Returns true if successful, false if error
	public boolean Add_Account(String i_F_name,
			String i_S_name, boolean i_Gender,
			String i_Phone, String i_Email,
			String i_Address) throws SQLException
	{
		// Verify parameters are valid.
		// No date error checking implemented 
		
		if (stmt != null) {
			int returnedRows = stmt
					.executeUpdate("Insert into account values ('" + i_F_name
							+ "\', \'" + i_S_name + "\', \'" + i_Gender
							+ "\' + '" + i_Phone + "\', \'" + i_Email
							+ "\', \'" + i_Address + "\')");
			if (returnedRows == 1) {
				System.out.println("Inserted Account with e-mail: " + i_Email
						+ " sucessfully");
				return true;
			} else {
				System.out.println("Error inserting Account with e-mail: "
						+ i_Email + " to the database");
				return false;
			}
		} else return false;
	}
	
	// Deletes the account selected by the email
	// Returns true if successful
	public boolean Delete_Account(String i_email) throws SQLException
	{
		if (stmt != null) {
			int returnedRows = stmt
					.executeUpdate("Delete from account where email = '"
							+ i_email + "'");
			if (returnedRows == 1) {
				System.out.println("Account with email " + i_email
						+ " was deleted sucessfully");
				return true;
			} else {
				System.out.println("Error deleting Account " + i_email
						+ " from the database");
				return false;
			}
		} else return false;
	}
	
	// Deletes the account selected by the account ID
	// Returns true if successful
	public boolean Delete_Account(int i_Account_ID) throws SQLException
	{
		if (stmt != null) {
			int returnedRows = stmt
					.executeUpdate("Delete from account where accountID = '"
							+ i_Account_ID + "'");
			if (returnedRows == 1) {
				System.out.println("Account with account ID " + i_Account_ID
						+ " was deleted sucessfully");
				return true;
			} else {
				System.out.println("Error deleting Account " + i_Account_ID
						+ " from the database");
				return false;
			}
		} else return false;
	}
	
	// Fills the AccountMessage structure obj
	public void Fill_Message(AccountMessage obj)
	{
		
	}
	
	public Connection Connect_Database()
	{
		try{
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());  			//Load the JDBC Driver
		}
		catch(SQLException e)
		{
			System.out.println("Error loading JDBC driver; ClassNotFoundException was thrown");
			System.out.println("Terminating...");
			System.exit(1);
		}
		try{	// Try\Catch for SQLException
			this.dbConnection = DriverManager.getConnection(dbUrl,"ora_b3q6", "a45585072"); 	// Establish connection to DB (url, user, pwd)
//			this.dbConnection = DriverManager.getConnection(dbUrl, "ora_q1g6", "a81813065");	
			this.stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println("Error code: " + e.getErrorCode());
			System.out.println("SQLException was thrown while establishing connection.  Better Luck next time...");
			System.exit(1);
		}
	}
	
	public Statement getStatement()
	{
		return this.stmt;
	}
	
	public void closeConnection()
	{
		try{this.dbConnection.close();}
		catch(SQLException e){
			System.out.println("SQLException thrown when closing the database");
			System.out.println(e.getMessage());
			System.exit(1);
		}
		System.exit(1);
	}
}