package models.database;

import java.sql.*;
import models.messages.*;
import models.messages.ResponseMessage.ResponseCode;
import models.misc.*;
import java.text.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account {
	java.util.Date date;
	// Creates a unique ID, inserts today's date
	// Returns true if successful, false if error

	/* ReMINDER FROM MERT:
	 * we need a viewALL method that return all the accounts
	 * so that the interface can display them
	 *
	 * another thing is to use SQL date rather tan java.util.date
	 * in booking I have a conversion example if you need it
	 */
	public Message viewAllAccount(Message i_msg){
		// needs to be implemented
		Message reply = new Message(i_msg.header.authLevel, i_msg.header.messageOwnerID, i_msg.header.nameHotel);
		ResponseMessage response = new ResponseMessage();
		databaseHelper dbcon 	= null;
		try {
			dbcon = new databaseHelper();
			// First find out the size of the array we need
			ResultSet returnedSet = dbcon.select("SELECT COUNT(*) FROM accounts");
			returnedSet.first();
			int resultSize = returnedSet.getInt(1);

			reply.accounts = new AccountMessage[resultSize];
			returnedSet = dbcon.select("SELECT * FROM accounts");
			int i = 0;
			// Assume Success unless someone writes a Fail flag.
			response.responseCode = ResponseCode.SUCCESS;

			while (returnedSet.next())
			{
				AccountMessage temp_msg = new AccountMessage();
				int r_account_id = returnedSet.getInt("accountID");
				String r_account_type = returnedSet.getString("accountType");
				String r_first_name = returnedSet.getString("firstName");
				String r_surname = returnedSet.getString("lastName");
				boolean r_gender = returnedSet.getBoolean("gender");
				String r_phone = returnedSet.getString("phone"); ;
				String r_mail = returnedSet.getString("email");
				String r_add = returnedSet.getString("address");
				Timestamp r_date = returnedSet.getTimestamp("creationTime");

				temp_msg.fill_All(r_account_id, r_account_type, r_first_name, r_surname, "", r_gender, r_phone, r_add, r_mail);
				temp_msg.creationTime = r_date;

				response.responseString = response.responseString + '\n' + "Return Account as Requested." +
						" Account ID: " + r_account_id;
				reply.accounts[i] = temp_msg;
				i++;
			}
		} catch (SQLException e) {
			reply.accounts = null;
			System.err.println("Error in 'viewAllAccount'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			response.fillResponse(ResponseCode.FAIL, "View All failed.");
		} catch (ClassNotFoundException e) {
			reply.accounts = null;
			System.err.println("Error in 'viewAllAccount'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			response.fillResponse(ResponseCode.FAIL, "View All failed.");
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
		reply.response = response;

		return reply;
	}
	public Message addAccount(Message i_msg) {
		// No date error checking implemented
		Message reply = new Message(i_msg.header.authLevel, i_msg.header.messageOwnerID, i_msg.header.nameHotel);
		ResponseMessage response = new ResponseMessage();
		AccountMessage accountInfo = new AccountMessage();
		AccountMessage inputAccount = i_msg.accounts[0];

		String pwmd = MD5.hashString(inputAccount.password);
		int genderInt;
		if (inputAccount.gender) {
			genderInt = 1;
		} else {
			genderInt = 0;
		}
		databaseHelper dbcon = null;
		try {
			dbcon = new databaseHelper();
			int returnedID = dbcon
			.insert("INSERT INTO accounts (firstName, lastName, accountType, password, gender, phone, email, address) VALUES ('"
					+ inputAccount.firstName
					+ "', '"
					+ inputAccount.lastName
					+ "', '"
					+ "customer"
					+ "', '"
					+ pwmd
					+ "', '"
					+ genderInt
					+ "', '"
					+ inputAccount.phone
					+ "', '"
					+ inputAccount.email
					+ "', '"
					+ inputAccount.address
					+ "')");
			if (returnedID <= 0) {
				// If returnedID is 0, then
				response.fillResponse(ResponseCode.FAIL,
						"Update failed." + " Account Email: "
						+ inputAccount.email);
			} else {
				accountInfo.accountID = returnedID;
				response.fillResponse(ResponseCode.SUCCESS,
						"Added one Account as Requested."
						+ " Account Email: " + inputAccount.email);
			}
		}
		catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			response.fillResponse(ResponseCode.FAIL, "Update failed." +
					" Account Email: " + i_msg.accounts[0].email);
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			response.fillResponse(ResponseCode.FAIL, "Update failed." +
					" Account Email: " + i_msg.accounts[0].email);

		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		reply.accounts = new AccountMessage[1];
		reply.accounts[0] = accountInfo;
		reply.response = response;

		return reply;
	}

	public Message editAccount(Message i_msg){
		// No date error checking implemented
		Message reply = new Message(i_msg.header.authLevel, i_msg.header.messageOwnerID, i_msg.header.nameHotel);
		ResponseMessage response = new ResponseMessage();
		AccountMessage inputAccount = i_msg.accounts[0];

		int genderInt;
		if (inputAccount.gender) {
			genderInt = 1;
		} else {
			genderInt = 0;
		}
		databaseHelper dbcon = null;
		try {
			dbcon = new databaseHelper();
			int returnedRows = dbcon
			.modify("UPDATE accounts SET firstName='"
					+ inputAccount.firstName + "', lastName='"
					+ inputAccount.lastName + "', gender='" + genderInt
					+ "', phone='" + inputAccount.phone + "', email='"
					+ inputAccount.email + "', address='" + inputAccount.address
					+ "' " + "WHERE accountID='" + inputAccount.accountID
					+ "'");
			if (returnedRows == 1) {
				response.fillResponse(ResponseCode.SUCCESS,
						"Updated One Account as Requested."
						+ " Account Email: " + i_msg.accounts[0].email);
			} else if (returnedRows > 1) {
				response.fillResponse(
						ResponseCode.FAIL,
						"Updated More than one Account. "
						+ "Indicated inconsistency. Account Email: "
						+ i_msg.accounts[0].email);
			} else {
				response.fillResponse(ResponseCode.FAIL,
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
				response.fillResponse(ResponseCode.FAIL, "Update Failed. Nothing changed.");
			}
		}
		reply.response = response;

		return reply;
	}
	// Deletes the account selected by the email or the id
	// Returns true if successful
	public Message deleteAccount(Message i_msg) {
		// No date error checking implemented
		Message reply = new Message(i_msg.header.authLevel, i_msg.header.messageOwnerID, i_msg.header.nameHotel);
		ResponseMessage response = new ResponseMessage();
		databaseHelper dbcon 	= null;
		try {
			dbcon = new databaseHelper();
			int returnedRows = dbcon.modify("DELETE FROM accounts WHERE accountID=" + i_msg.accounts[0].accountID );
			if (returnedRows == 1)
			{
				response.fillResponse(ResponseCode.SUCCESS, "Deleted Account as Requested." +
						" Account ID: " + i_msg.accounts[0].accountID);
			}
			else
			{
				response.fillResponse(ResponseCode.FAIL, "Delete failed." +
						" Account ID: " + i_msg.accounts[0].accountID);
			}
		} catch (SQLException e) {
			System.err.println("Error in 'deleteAccount'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			response.fillResponse(ResponseCode.FAIL, "Update failed." +
					" Account ID: " + i_msg.accounts[0].accountID);
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'deleteAccount'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			response.fillResponse(ResponseCode.FAIL, "Update failed." +
					" Account ID: " + i_msg.accounts[0].accountID);
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		reply.response = response;
		return reply;
	}
	// Fetch Variable data from the database
	// If user does not exist, return false
	public Message viewAccount(Message i_msg) {
		// Verify parameters are valid.
		// No date error checking implemented
		Message reply = new Message(i_msg.header.authLevel, i_msg.header.messageOwnerID, i_msg.header.nameHotel);
		AccountMessage accountInfo = new AccountMessage();
		ResponseMessage response = new ResponseMessage();
		databaseHelper dbcon 	= null;
		try {
			dbcon = new databaseHelper();
			ResultSet returnedSet = dbcon.select("SELECT * FROM accounts WHERE accountID='" + i_msg.accounts[0].accountID
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
				Timestamp r_date = returnedSet.getTimestamp("creationTime");

				accountInfo.fill_All(r_account_id, r_account_type, r_first_name, r_surname, "", r_gender, r_phone, r_add, r_mail);
				accountInfo.creationTime = r_date;
				response.fillResponse(ResponseMessage.ResponseCode.SUCCESS, "Return Account as Requested." +
						" Account ID: " + i_msg.accounts[0].accountID);
			}
			else
			{
				response.fillResponse(ResponseMessage.ResponseCode.FAIL, "View failed." +
						" Account ID: " + i_msg.accounts[0].accountID);
			}
		} catch (SQLException e) {
			System.err.println("Error in 'viewAccount'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			response.fillResponse(ResponseMessage.ResponseCode.FAIL, "View failed." +
					" Account ID: " + i_msg.accounts[0].accountID);
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'viewAccount'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			response.fillResponse(ResponseMessage.ResponseCode.FAIL, "View failed." +
					" Account ID: " + i_msg.accounts[0].accountID);
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

		reply.accounts = new AccountMessage[1];
		reply.accounts[0] = accountInfo;
		reply.response = response;

		return reply;
	}
	// authentication is job of authenticator he will  use view.

	public Message login(Message i_msg) {
		// Verify parameters are valid.
		// No date error checking implemented
		Message reply = new Message(i_msg.header.authLevel, i_msg.header.messageOwnerID, i_msg.header.nameHotel);
		AccountMessage accountInfo = new AccountMessage();
		ResponseMessage response = new ResponseMessage();
		databaseHelper dbcon 	= null;
		try {
			dbcon = new databaseHelper();
			String md5_password = MD5.hashString(i_msg.accounts[0].password);
			ResultSet returnedSet = dbcon.select("SELECT * FROM accounts WHERE email='" + i_msg.accounts[0].email
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

				accountInfo.fill_All(r_account_id, r_account_type, r_first_name, r_surname, "", r_gender, r_phone, r_add, r_mail);
				//DateFormat formatter = DateFormat.getDateInstance();
				//reply.date = formatter.parse(r_date);
				response.fillResponse(ResponseCode.SUCCESS, "Login successful." +
						" Account email: " + i_msg.accounts[0].email);
			}
			else
			{
				response.fillResponse(ResponseCode.FAIL, "Login failed." +
						" Account email: " + i_msg.accounts[0].email);
				reply	= i_msg;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'login'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			response.fillResponse(ResponseCode.FAIL, "View failed." +
					" Account email: " + i_msg.accounts[0].email);
			reply	= i_msg;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'login'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			response.fillResponse(ResponseCode.FAIL, "View failed." +
					" Account email: " + i_msg.accounts[0].email);
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
		reply.response = response;
		reply.accounts = new AccountMessage[1];
		reply.accounts[0] = accountInfo;

		return reply;
	}

}