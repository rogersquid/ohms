package models.database;

import java.sql.*;

import models.messages.*;
import models.messages.ResponseMessage.ResponseCode;
import models.misc.*;

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
	public Message getAllAccounts(Message i_msg){
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
		// Decode the user's authorization level
		int userAuthLevel = 0;
		if (inputAccount.accountType.compareToIgnoreCase("customer") == 0)
			userAuthLevel = 1;
		if (inputAccount.accountType.compareToIgnoreCase("maid") == 0)
			userAuthLevel = 2;
		if (inputAccount.accountType.compareToIgnoreCase("staff") == 0)
			userAuthLevel = 3;
		if (inputAccount.accountType.compareToIgnoreCase("admin") == 0)
			userAuthLevel = 4;

		databaseHelper dbcon = null;
		try {
			dbcon = new databaseHelper();
			int returnedID = dbcon
			.insert("INSERT INTO accounts (firstName, lastName, accountType, password, gender, phone, email, address, authLevel) VALUES ('"
					+ inputAccount.firstName
					+ "', '"
					+ inputAccount.lastName
					+ "', '"
					+ inputAccount.accountType
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
					+ "', '"
					+ userAuthLevel
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
		// Decode the user's authorization level
		int userAuthLevel = 0;
		if(inputAccount.accountType != null) {
			if (inputAccount.accountType.compareToIgnoreCase("customer") == 0)
				userAuthLevel = 1;
			if (inputAccount.accountType.compareToIgnoreCase("maid") == 0)
				userAuthLevel = 2;
			if (inputAccount.accountType.compareToIgnoreCase("staff") == 0)
				userAuthLevel = 3;
			if (inputAccount.accountType.compareToIgnoreCase("admin") == 0)
				userAuthLevel = 4;
		}

		databaseHelper dbcon = null;
		try {
			dbcon = new databaseHelper();

			String updateStmt = "";

			updateStmt = updateStmt.concat("gender='" + genderInt);
			if(userAuthLevel > 0)
			{
				updateStmt = updateStmt.concat("', authLevel='" + userAuthLevel);
			}
			if(inputAccount.accountType != null && !inputAccount.accountType.isEmpty())
			{
				updateStmt = updateStmt.concat("', accountType='" + inputAccount.accountType);
			}
			if(inputAccount.firstName != null && !inputAccount.firstName.isEmpty())
			{
				updateStmt = updateStmt.concat("', firstName='" + inputAccount.firstName);
			}
			if(inputAccount.lastName != null && !inputAccount.lastName.isEmpty())
			{
				updateStmt = updateStmt.concat("', lastName='" + inputAccount.lastName);
			}
			if(inputAccount.password != null && !inputAccount.password.isEmpty())
			{
				updateStmt = updateStmt.concat("', password='" + inputAccount.password);
			}
			if(inputAccount.phone != null && !inputAccount.phone.isEmpty())
			{
				updateStmt = updateStmt.concat("', phone='" + inputAccount.phone);
			}
			if(inputAccount.address != null && !inputAccount.address.isEmpty())
			{
				updateStmt = updateStmt.concat("', address='" + inputAccount.address);
			}
			updateStmt = updateStmt.concat("' ");

			int returnedRows = dbcon.modify("UPDATE accounts SET " + updateStmt + "WHERE accountID=" + inputAccount.accountID);

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
			response.fillResponse(ResponseCode.FAIL, "Update Failed. SQLException.");
			e.printStackTrace(System.err);
		} catch (ClassNotFoundException e) {
			System.err
			.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			response.fillResponse(ResponseCode.FAIL, "Update Failed. ClassNotFoundException.");
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
	public Message getAccount(Message i_msg) {
		// Verify parameters are valid.
		// No date error checking implemented
		Message reply = new Message(i_msg.header.authLevel, i_msg.header.messageOwnerID, i_msg.header.nameHotel);
		AccountMessage accountInfo = new AccountMessage();
		ResponseMessage response = new ResponseMessage();
		databaseHelper dbcon 	= null;
		try {
			dbcon = new databaseHelper();
			ResultSet returnedSet = dbcon.select("SELECT * FROM accounts WHERE accountID=" + i_msg.accounts[0].accountID);
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
				Timestamp r_date = returnedSet.getTimestamp("creationTime");


				accountInfo.fill_All(r_account_id, r_account_type, r_first_name, r_surname, "", r_gender, r_phone, r_add, r_mail);
				accountInfo.creationTime = r_date;
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

	public int getAuthLevel(int i_accountID, String i_md5)
	{
		databaseHelper dbcon = null;
		int authLevel = 0;
		try
		{
			dbcon = new databaseHelper();

			String queryString = "SELECT authLevel, password FROM accounts WHERE accountID=" + i_accountID;
			ResultSet rs = dbcon.select(queryString);
			if (rs.next())
			{
				// Get the database's password string for this accountID
				String dbMD5 = rs.getString("password");
				// Compare to the password string passed by parameter
				if(dbMD5.compareTo(i_md5) == 0)
				{
					// Password is good
					authLevel = rs.getInt("authLevel");
				}
				// else, password is bad, so leave the default authLevel = 0
			}
		}catch (SQLException e) {
			System.err.println("Error in 'getAuthLevel'.  SQLException was thrown:");
			e.printStackTrace(System.err);
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'getAuthLevel'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
		}
		finally {
			if (dbcon != null) dbcon.close();
		}

		return authLevel;
	}

	public Message getFilteredAccount(Message i_msg) {
		databaseHelper dbcon = null;
		Message reply = new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		ResponseMessage response = new ResponseMessage();

		try {
			// create connection
			dbcon = new databaseHelper();

			String queryString = "SELECT * FROM accounts WHERE ";

			// Prepare the query string based on what was in the message
			boolean nonFirst = false;
			if (i_msg.accounts[0].accountID != 0) {
				queryString = queryString + "accountID=" + i_msg.accounts[0].accountID;
				nonFirst = true;
			}
			if (!i_msg.accounts[0].accountType.isEmpty()) {
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "accountType='" + i_msg.accounts[0].accountType + "'";
				nonFirst = true;
			}
			if (!i_msg.accounts[0].firstName.isEmpty()) {
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "firstName='" + i_msg.accounts[0].firstName + "'";
				nonFirst = true;
			}
			if (!i_msg.accounts[0].lastName.isEmpty()) {
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "lastName='" + i_msg.accounts[0].lastName + "'";
				nonFirst = true;
			}
			if (i_msg.accounts[0].phone.isEmpty()) {
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "phone='" + i_msg.accounts[0].phone + "'";
				nonFirst = true;
			}
			if (i_msg.accounts[0].email.isEmpty()) {
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "email='" + i_msg.accounts[0].email + "'";
				nonFirst = true;
			}
			if (i_msg.accounts[0].address.isEmpty()) {
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "address='" + i_msg.accounts[0].address + "'";
				nonFirst = true;
			}

			// query the database for all rooms
			ResultSet rs = dbcon.select(queryString);
			if (!rs.next()) {
				response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				response.responseString = "No Accounts in database matching your search.";
			} else {
				int i = 0;
				rs.beforeFirst();
				// Count the number of entries in the result set
				while (rs.next()) {
					i++;
				}
				rs.beforeFirst();
				reply.accounts = new AccountMessage[i];

				i = 0;

				while (rs.next()) {
					reply.accounts[i] = new AccountMessage();
					reply.accounts[i].accountID = rs.getInt("accountID");
					reply.accounts[i].accountType = rs.getString("accountType");
					reply.accounts[i].firstName = rs.getString("firstName");
					reply.accounts[i].lastName = rs.getString("lastName");
					reply.accounts[i].gender = rs.getBoolean("gender");
					reply.accounts[i].phone = rs.getString("phone"); ;
					reply.accounts[i].email = rs.getString("email");
					reply.accounts[i].address = rs.getString("address");
					reply.accounts[i].creationTime = rs.getTimestamp("creationTime");
					i++;
				}
				response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				response.responseString = "Query succeeded.";
			}
		} catch (SQLException e) {
			System.err.println("Error in 'getAllAccount'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			reply.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			reply.response.responseString = "Query failed.";
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'getAllAccount'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			reply.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			reply.response.responseString = "Query failed.";
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		reply.response = response;

		return reply;
	}
}