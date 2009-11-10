package models.database;

import java.sql.*;
import models.messages.*;
import models.misc.*;
import java.text.*;

public class Account {
	java.util.Date date;
	String dbname = "ohms";
	// Creates a unique ID, inserts today's date
	// Returns true if successful, false if error

	/* ReMINDER FROM MERT:
	 * we need a viewALL method that return all the accounts
	 * so that the interface can display them
	 * 
	 * another thing is to use SQL date rather tan java.util.date
	 * in booking I have a conversion example if you need it
	 */
	public AccountMessage[] viewAllAccount(AccountMessage i_msg){
		// needs to be implemented
		AccountMessage[] reply = null;
		databaseHelper dbcon 	= null;
		try {
			dbcon = new databaseHelper(dbname);
			// First find out the size of the array we need
			ResultSet returnedSet = dbcon.select("SELECT COUNT(*) FROM account");
			returnedSet.first();
			int resultSize = returnedSet.getInt(1);
			
			reply = new AccountMessage[resultSize];
			returnedSet = dbcon.select("SELECT * FROM account");
			int i = 0;
			AccountMessage temp_msg = i_msg.deepCopy();
			while (returnedSet.next()) 
			{
				int r_account_id = returnedSet.getInt("accountID");
				String r_account_type = returnedSet.getString("accountType"); 
				String r_first_name = returnedSet.getString("firstName");  
				String r_surname = returnedSet.getString("lastName");  
				boolean r_gender = returnedSet.getBoolean("gender");  
				String r_phone = returnedSet.getString("phone"); ; 
				String r_mail = returnedSet.getString("email"); 
				String r_add = returnedSet.getString("address"); 
//				String r_date = returnedSet.getString("date"); 

				temp_msg.fill_All(r_account_id, r_account_type, r_first_name, r_surname, "", r_gender, r_phone, r_add, r_mail);
				DateFormat formatter = DateFormat.getDateInstance();
//				reply[i].date = returnedSet.getDate("date");
//				reply[i].date = formatter.parse(r_date);
				temp_msg.fillHeaderResponse(Header.Response.SUCCESS, "Return Account as Requested." +
						" Account ID: " + i_msg.accountID);
				reply[i] = temp_msg.deepCopy();
				i++;
			}
		} catch (SQLException e) {
			reply = new AccountMessage[1];
			System.err.println("Error in 'viewAllAccount'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "View All failed." +
					" Account ID: " + i_msg.accountID);
			reply[0] = i_msg;
		} catch (ClassNotFoundException e) {
			reply = new AccountMessage[1];
			System.err.println("Error in 'viewAllAccount'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "View All failed." +
					" Account ID: " + i_msg.accountID);
			reply[0] = i_msg;
//		} catch (ParseException e)
//		{
//			reply = new AccountMessage[1];
//			System.err.println("Error in 'viewAllAccount'.  ParseException was thrown from parsing the date:");
//			e.printStackTrace(System.err);
//			i_msg.fillHeaderResponse(Header.Response.FAIL, "View All failed." +
//					" Account ID: " + i_msg.accountID);
//			reply[0] = i_msg;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		
		
		return reply;
	}
	public AccountMessage addAccount(AccountMessage i_msg) {
		// No date error checking implemented 
		AccountMessage 	reply;
		String pwmd = MD5.hashString(i_msg.password);
		int genderInt;
		if (i_msg.gender) {
			genderInt = 1;
		} else {
			genderInt = 0;
		}
		databaseHelper dbcon = null;
		try {
			dbcon = new databaseHelper(dbname);
			java.util.Date now = new java.util.Date();
			int returnedRows = dbcon
			.modify("INSERT INTO account (firstName, lastName, accountType, password, gender, phone, email, address, date) VALUES ('"
					+ i_msg.firstname
					+ "', '"
					+ i_msg.lastname
					+ "', '"
					+ "customer"
					+ "', '"
					+ pwmd
					+ "', '"
					+ genderInt
					+ "', '"
					+ i_msg.phone
					+ "', '"
					+ i_msg.email
					+ "', '"
					+ i_msg.address
					+ "', '"
					+ now + "')");
			if (returnedRows == 1) {
				i_msg.fillHeaderResponse(Header.Response.SUCCESS,
						"Added one Account as Requested."
						+ " Account Email: " + i_msg.email);
			} else {
				i_msg.fillHeaderResponse(Header.Response.FAIL,
						"Update failed." + " Account Email: "
						+ i_msg.email);
			}
		}
		catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "Update failed." +
					" Account Email: " + i_msg.email);
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "Update failed." +
					" Account Email: " + i_msg.email);

		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}

		reply	= i_msg;
		return reply.deepCopy();
	}

	public AccountMessage editAccount(AccountMessage i_msg){
		// No date error checking implemented 
		AccountMessage 	reply;
		reply = null;
		int genderInt;
		if (i_msg.gender) {
			genderInt = 1;
		} else {
			genderInt = 0;
		}
		databaseHelper dbcon = null;
		try {
			dbcon = new databaseHelper(dbname);
			int returnedRows = dbcon
			.modify("UPDATE account SET firstName='"
					+ i_msg.firstname + "', lastName='"
					+ i_msg.lastname + "', gender='" + genderInt
					+ "', phone='" + i_msg.phone + "', email='"
					+ i_msg.email + "', address='" + i_msg.address
					+ "' " + "WHERE accountID='" + i_msg.accountID
					+ "'");
			if (returnedRows == 1) {
				i_msg.fillHeaderResponse(Header.Response.SUCCESS,
						"Updated One Account as Requested."
						+ " Account Email: " + i_msg.email);
			} else if (returnedRows > 1) {
				i_msg.fillHeaderResponse(
						Header.Response.FAIL,
						"Updated More than one Account. "
						+ "Indicated inconsistency. Account Email: "
						+ i_msg.email);
			} else {
				i_msg.fillHeaderResponse(Header.Response.FAIL,
						"Update Failed. Nothing changed.");
			}
		} catch (SQLException e) {
			System.err
			.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
		} catch (ClassNotFoundException e) {
			System.err
			.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
		} finally {
			if (dbcon != null) {
				dbcon.close();
			}
			else {
				i_msg.fillHeaderResponse(Header.Response.FAIL, "Update Failed. Nothing changed.");	
			}
		}
		reply = i_msg.deepCopy();
		return reply;
	}
	// Deletes the account selected by the email or the id
	// Returns true if successful
	public AccountMessage deleteAccount(AccountMessage i_msg) {
		// No date error checking implemented 
		AccountMessage reply = null;
		databaseHelper dbcon 	= null;
		try {
			dbcon = new databaseHelper(dbname);
			int returnedRows = dbcon.modify("DELETE FROM account WHERE accountID='" + i_msg.accountID 
					+ "')");
			if (returnedRows == 1) 
			{
				i_msg.fillHeaderResponse(Header.Response.SUCCESS, "Deleted Account as Requested." +
						" Account ID: " + i_msg.accountID);
				reply	= i_msg;
			}
			else 
			{
				i_msg.fillHeaderResponse(Header.Response.FAIL, "Delete failed." +
						" Account ID: " + i_msg.accountID);
				reply	= i_msg;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'deleteAccount'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "Update failed." +
					" Account ID: " + i_msg.accountID);
			reply	= i_msg;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'deleteAccount'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "Update failed." +
					" Account ID: " + i_msg.accountID);
			reply	= i_msg;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return reply.deepCopy();
	}
	// Fetch Variable data from the database
	// If user does not exist, return false
	public AccountMessage viewAccount(AccountMessage i_msg) {
		// Verify parameters are valid.
		// No date error checking implemented 
		AccountMessage reply = null;
		databaseHelper dbcon 	= null;
		try {
			dbcon = new databaseHelper(dbname);
			ResultSet returnedSet = dbcon.select("SELECT * FROM account WHERE accountID='" + i_msg.accountID 
					+ "')");
			if (returnedSet.first()) 
			{
				int r_account_id = returnedSet.getInt("accountID");
				String r_account_type = returnedSet.getString("accountType"); 
				String r_first_name = returnedSet.getString("firstName");  
				String r_surname = returnedSet.getString("lastName");  
				boolean r_gender = returnedSet.getBoolean("gender");  
				String r_phone = returnedSet.getString("phone"); ; 
				String r_mail = returnedSet.getString("email"); 
				String r_add = returnedSet.getString("address");
//				String r_date = returnedSet.getString("date"); 

				reply.fill_All(r_account_id, r_account_type, r_first_name, r_surname, "", r_gender, r_phone, r_add, r_mail);
//				DateFormat formatter = DateFormat.getDateInstance();
//				reply.date = returnedSet.getDate("date");
//				reply.date = formatter.parse(r_date);
				i_msg.fillHeaderResponse(Header.Response.SUCCESS, "Return Account as Requested." +
						" Account ID: " + i_msg.accountID);
				reply	= i_msg;
			}
			else 
			{
				i_msg.fillHeaderResponse(Header.Response.FAIL, "View failed." +
						" Account ID: " + i_msg.accountID);
				reply	= i_msg;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'viewAccount'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "View failed." +
					" Account ID: " + i_msg.accountID);
			reply	= i_msg;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'viewAccount'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "View failed." +
					" Account ID: " + i_msg.accountID);
			reply	= i_msg;
//		} catch (ParseException e)
//		{
//			System.err.println("Error in 'viewAccount'.  ParseException was thrown from parsing the date:");
//			e.printStackTrace(System.err);
//			i_msg.fillHeaderResponse(Header.Response.FAIL, "View failed." +
//					" Account ID: " + i_msg.accountID);
//			reply	= i_msg;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return reply.deepCopy();
	}
	// authentication is job of authenticator he will  use view.
	
	public AccountMessage login(AccountMessage i_msg) {
		// Verify parameters are valid.
		// No date error checking implemented 
		AccountMessage reply = null;
		databaseHelper dbcon 	= null;
		try {
			dbcon = new databaseHelper(dbname);
			String md5_password = MD5.hashString(i_msg.password);
			ResultSet returnedSet = dbcon.select("SELECT * FROM account WHERE email='" + i_msg.email 
					+ "' AND password='"+md5_password+"'");
			if (returnedSet.next()) 
			{
				int r_account_id = returnedSet.getInt("accountID");
				String r_account_type = returnedSet.getString("accountType"); 
				String r_first_name = returnedSet.getString("firstName");  
				String r_surname = returnedSet.getString("lastName");  
				boolean r_gender = returnedSet.getBoolean("gender");  
				String r_phone = returnedSet.getString("phone"); ; 
				String r_mail = returnedSet.getString("email"); 
				String r_add = returnedSet.getString("address"); 
				String r_date = returnedSet.getString("date"); 
				
				reply.fill_All(r_account_id, r_account_type, r_first_name, r_surname, "", r_gender, r_phone, r_add, r_mail);
				//DateFormat formatter = DateFormat.getDateInstance();
				//reply.date = formatter.parse(r_date);
				i_msg.fillHeaderResponse(Header.Response.SUCCESS, "Login successful." +
						" Account email: " + i_msg.email);
				reply	= i_msg;
			}
			else 
			{
				i_msg.fillHeaderResponse(Header.Response.FAIL, "Login failed." +
						" Account email: " + i_msg.email);
				reply	= i_msg;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'login'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "View failed." +
					" Account email: " + i_msg.email);
			reply	= i_msg;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'login'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "View failed." +
					" Account email: " + i_msg.email);
			reply	= i_msg;
		}/* catch (ParseException e)
		{
			System.err.println("Error in 'viewAccount'.  ParseException was thrown from parsing the date:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "View failed." +
					" Account email: " + i_msg.email);
			reply	= i_msg;
		}*/
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return reply.deepCopy();
	}


}

