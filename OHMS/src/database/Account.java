package database;

import java.sql.*;
import messages.*;
import misc.*;

public class Account {
	java.util.Date date;
	String dbname = "default_hotel";
	// Creates a unique ID, inserts today's date
	// Returns true if successful, false if error
	public AccountMessage addAccount(AccountMessage i_msg) {
		// Verify parameters are valid.
		// No date error checking implemented 
		AccountMessage reply;
		String pwmd=MD5.hashString(i_msg.password);
		int g;
		if(i_msg.gender)g=1;
		else g=0;
		databaseHelper dbcon = null;
		try {
			dbcon = new databaseHelper(dbname);
			java.util.Date now = new java.util.Date();
			int returnedRows = dbcon.modify("INSERT INTO account (firstName, lastName, password, gender, phone, email, address, date) VALUES ('" 
					+ i_msg.firstname
					+ "', '" + i_msg.lastname + "', '" 
					+ pwmd + "', '" + g
					+ "' + '" + i_msg.phone + "', '" + i_msg.email
					+ "', '" + i_msg.address + "', '" + now + "')");
			if (returnedRows == 1) {
				i_msg.fill_Header_Response(Header.Response.SUCCESS, "Added one Account as Requested." +
						" Account Email: " + i_msg.email);
				reply=i_msg;
			} else {
				i_msg.fill_Header_Response(Header.Response.FAIL, "Update failed." +
						" Account Email: " + i_msg.email);
				reply=i_msg;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fill_Header_Response(Header.Response.FAIL, "Update failed." +
					" Account Email: " + i_msg.email);
			reply=i_msg;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fill_Header_Response(Header.Response.FAIL, "Update failed." +
					" Account Email: " + i_msg.email);
			reply=i_msg;
		}finally
		{
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return reply;
	}
	public AccountMessage editAccount(AccountMessage i_msg){
		// Verify parameters are valid.
		// No date error checking implemented 
		AccountMessage reply=null;
		int g;
		if(i_msg.gender)g=1;
		else g=0;
		databaseHelper dbcon = null;
		try {
			dbcon = new databaseHelper(dbname);
			int returnedRows = dbcon.modify("UPDATE account SET firstName='" + i_msg.firstname
					+ "', lastName='" +i_msg.lastname + "', gender='" + g
					+ "', phone='" + i_msg.phone + "', email='" + i_msg.email
					+ "', address='" + i_msg.address + "' "
					+ "WHERE accountID='" + i_msg.account_id + "'");
			if (returnedRows == 1) {
				i_msg.fill_Header_Response(Header.Response.SUCCESS, "Updated One Account as Requested." +
						" Account Email: " + i_msg.email);
			} else if (returnedRows>1) {
				i_msg.fill_Header_Response(Header.Response.FAIL, "Updated More than one Account. " +
						"Indicated inconsistency. Account Email: " + i_msg.email);	
			}
			else {
				i_msg.fill_Header_Response(Header.Response.FAIL, "Update Failed. Nothing changed.");	
			}
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
		}finally
		{
			if (dbcon != null) {
				dbcon.close();
			}
		}
		reply=i_msg;
		return reply;
	}
	// Deletes the account selected by the email or the id
	// Returns true if successful
	public AccountMessage deleteAccount(AccountMessage i_msg) {
		AccountMessage reply= null;
		return reply;
	}
	// Fetch Variable data from the database
	// If user does not exist, return false
	public AccountMessage viewAccount(AccountMessage i_msg) {
		AccountMessage reply= null;
		return reply;
	}
	// authentication is job of authenticator he will  use view.
}