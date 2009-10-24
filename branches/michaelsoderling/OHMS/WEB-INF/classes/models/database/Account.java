package database;

import java.io.*;
import java.util.*;
import java.sql.*;
import java.security.*;
import misc.*;
import messages.*;

public class Account {
	
	
	private int c_Account_ID;
	private String c_Account_Type;
	private String c_F_name;
	private String c_S_name;
	private String c_Password;
	private boolean c_Gender;
	private String c_Phone;
	private String c_Email;
	private String c_Address;
	private java.util.Date c_Date;
	
	private Connection dbConnection;
	private Statement stmt = null;
	private String dbUrl;
	
	public static boolean login(String username, String password) {
		return accountIsValid(username, password);
	}
	
	public static boolean accountIsValid(String username, String password) {
		String md5_password = MD5.hashString(password);
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection dbcon = DriverManager.getConnection("jdbc:mysql://localhost/eece419", "eece419", "dude");
			Statement statement = dbcon.createStatement();
			String query = "SELECT * FROM users WHERE username='" + username + "' AND password='" + md5_password + "'";
			ResultSet rs = statement.executeQuery(query);

			if(rs.next()) {
				result = true;
			} else {
				result = false;
			}
			rs.close();
			statement.close();
			dbcon.close();
		} catch(Exception e) {
			// asd
		}
		return result;
	}

	public static String test() {
		String output = "TESTING: ";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection dbcon = DriverManager.getConnection("jdbc:mysql://localhost/eece419", "eece419", "dude");
			Statement statement = dbcon.createStatement();
			String query = "SELECT * FROM users";
			ResultSet rs = statement.executeQuery(query);

			while(rs.next()) {
				output += rs.getString("username");
			}
			rs.close();
			statement.close();
			dbcon.close();
		} catch(Exception e) {
			output += "(exception) ";
			output += e.toString();
		}
		return output;
	}



	
		
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
		public void Fill_Message(Account_Message i_obj)
		{
			i_obj.account_id = c_Account_ID;
			i_obj.account_type = c_Account_Type;
			i_obj.firstname = c_F_name;
			i_obj.lastname = c_S_name;
			i_obj.password = c_Password;
			i_obj.phone = c_Phone;
			i_obj.address = c_Address;
			i_obj.email = c_Email;
			i_obj.gender = c_Gender;
			i_obj.date = c_Date;
		}
		
	}
}