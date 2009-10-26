package database;

import java.io.*;
import java.util.*;
import java.sql.*;
import java.security.*;
import messages.*;
import misc.*;
import database.database_Helper.*;

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

	private String dname = "default_hotel";

	public boolean login(String username, String password) {
		return accountIsValid(username, password);
	}

	public boolean accountIsValid(String username, String password) {
		String md5_password = MD5.hashString(password);
		boolean result = false;
		database_Helper dbcon = null;
		try {
			dbcon = new database_Helper(dname);
			String query = "SELECT * FROM users WHERE email='" + username + "' AND password='" + md5_password + "'";
			ResultSet rs = dbcon.select(query);

			if(rs.next()) {
				result = true;
			} else {
				result = false;
			}
			dbcon.close();
		} 
		catch(SQLException e){
			System.err.println("Error in 'accountIsValid'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			result = false;
		}
		catch(ClassNotFoundException e){
			System.err.println("Error in 'accountIsValid'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			result = false;
		}finally
		{
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return result;
	}

	// Fetch Variable data from the database
	// If user does not exist, return false
	public boolean Fetch_All(String i_email, String i_pwd) 
	{
		boolean result = false;
		database_Helper dbcon = null;
		try {
			dbcon = new database_Helper(dname);
			ResultSet returnedSet = dbcon.select("Select from account where email='" + i_email + "'");
			if (returnedSet.first()) {
				c_Account_ID = (int)returnedSet.getDouble("accountID");
				c_F_name = returnedSet.getString("firstName");
				c_S_name = returnedSet.getString("lastName");
				c_Gender = returnedSet.getBoolean("gender");
				c_Phone = returnedSet.getString("phone");
				c_Email = returnedSet.getString("email");
				c_Address = returnedSet.getString("address");
				c_Date = returnedSet.getDate("date");
				result = true;
			}else result = false;
		} catch (SQLException e) {
			System.err.println("Error in 'Fetch_All'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			result = false;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Fetch_All'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			result = false;
		}finally
		{
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return result;
	}

	// Creates a unique ID, inserts today's date
	// Returns true if successful, false if error
	public boolean Add_Account(String i_F_name,
			String i_S_name, boolean i_Gender,
			String i_Phone, String i_Email,
			String i_Address) 
	{
		// Verify parameters are valid.
		// No date error checking implemented 

		boolean result = false;
		database_Helper dbcon = null;
		try {
			dbcon = new database_Helper(dbname);
			java.util.Date now = new java.util.Date();
			int returnedRows = dbcon.modify("INSERT INTO account (firstName, lastName, gender, phone, email, address, date) VALUES ('" 
					+ i_F_name
					+ "', '" + i_S_name + "', '" + i_Gender
					+ "' + '" + i_Phone + "', '" + i_Email
					+ "', '" + i_Address + "', '" + now + "')");
			if (returnedRows == 1) {
				System.out.println("Inserted Account with e-mail: " + i_Email
						+ " sucessfully");
				result = true;
			} else {
				System.out.println("Error inserting Account with e-mail: "
						+ i_Email + " to the database");
				result = false;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			result = false;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			result = false;
		}finally
		{
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return result;
	}

	// Deletes the account selected by the email
	// Returns true if successful
	public boolean Delete_Account(String i_email) 
	{
		boolean result = false;
		database_Helper dbcon = null;
		try {
			dbcon = new database_Helper(dname);
			int returnedRows = dbcon.modify("DELETE FROM account WHERE email = '"
						+ i_email + "'");
			if (returnedRows == 1) {
				System.out.println("Account with email " + i_email
						+ " was deleted sucessfully");
				result = true;
			} else {
				System.out.println("Error deleting Account " + i_email
						+ " from the database");
				result = false;
				}
		} catch (SQLException e) {
			System.err.println("Error in 'Delete_Account(i_email)'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			result = false;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account(i_email)'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			result = false;
		}finally
		{
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return result;
	}

	// Deletes the account selected by the account ID
	// Returns true if successful
	public boolean Delete_Account(int i_Account_ID)
	{
		boolean result = false;
		database_Helper dbcon = null;
		try {
			dbcon = new database_Helper(dname);
			int returnedRows = dbcon.modify("DELETE FROM account WHERE accountID = '"
					+ i_Account_ID + "'");
			if (returnedRows == 1) {
				System.out.println("Account with account ID " + i_Account_ID
						+ " was deleted sucessfully");
				result = true;
			} else {
				System.out.println("Error deleting Account " + i_Account_ID
						+ " from the database");
				result = false;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'Delete_Account(i_Account_ID)'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			result = false;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account(i_Account_ID)'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			result = false;
		} finally
		{
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return result;
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
