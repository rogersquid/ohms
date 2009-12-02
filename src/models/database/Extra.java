package models.database;

import models.messages.*;
import models.messages.ResponseMessage.ResponseCode;
import java.sql.*;

public class Extra {

	public Message addExtra(Message i_msg){
		/*
		 * OVERVIEW: Adds a extra to the database
		 * PRECONDITIONS: Parameters have been validated
		 * MODIFIES: Adds extra with given parameters in the extra table of the database
		 * POSTCONDITIONS: If addition was successful, the extra with the correct parameters will be added to the Database
		 */
		// All the information is filled in. This puts all the information into the database.
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.extras=i_msg.extras;
		try {
			// create connection
			dbcon = new databaseHelper();
			// insert the Extra in to appropriate hotel
			int returnedRows 	= dbcon.insert("INSERT INTO " + i_msg.header.nameHotel + "_extras (bookingID, extraName, price, date) "
					+ "VALUES ('"
					+ i_msg.extras[0].bookingID + "', '"
					+ i_msg.extras[0].extraName + "', '"
					+ i_msg.extras[0].price +"', '"
					+ i_msg.extras[0].date + "')");
			// check the number of rows changed to see whether response is as expected
			if (returnedRows > 0) {
				System.out.println("Success");
				replyMessage.extras[0].extraID = returnedRows;
				replyMessage.response.fillResponse(ResponseCode.SUCCESS, "Added one Extra as Requested." +
						" extraID: " + returnedRows);
			} else {
				System.out.println("Failure");
				replyMessage.response.fillResponse(ResponseCode.FAIL, "Adding Extra failed." +
						" StartDate: " + i_msg.extras[0].date);
			}
		// check the exceptions. If an exception is thrown, operation failed
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Adding Extra failed." +
					" StartDate: " + i_msg.extras[0].date);
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Adding Extra failed." +
					" StartDate: " + i_msg.extras[0].date);
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return replyMessage;
	}

	public Message editExtra(Message i_msg){
		/*
		 * OVERVIEW: Edits an extra that is already in the database
		 * PRECONDITIONS: Parameters have been validated
		 * MODIFIES: Edits the extra details with the given parameters in the extras table of the database
		 * POSTCONDITIONS: The specified extra will be edited with the given parameters in the preconditions
		 */
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.extras=i_msg.extras;
		try {
			dbcon = new databaseHelper();
			ResultSet rs = dbcon.select("SELECT * FROM " + i_msg.header.nameHotel + "_extras WHERE extraID = " + i_msg.extras[0].extraID);
			rs.next();
			int returnedRows = dbcon.modify("UPDATE "+ i_msg.header.nameHotel +"_extras SET extraName='"
					+ i_msg.extras[0].extraName + "', price='"
					+ i_msg.extras[0].price + "', date='"
					+ i_msg.extras[0].date + "' "
					+ "WHERE extraID='" + i_msg.extras[0].extraID
					+ "'");
			if (returnedRows != 1) {
				replyMessage.response.fillResponse(ResponseCode.FAIL, "Editting Extra failed.");
				return replyMessage;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Editting Extra failed.");
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Editting Extra failed.");
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		replyMessage.response.fillResponse(ResponseCode.SUCCESS, "Edited one Extra as Requested.");
		return replyMessage;
	}

	public Message deleteExtra(Message i_msg) {
		/*
		 * OVERVIEW: Deletes a extra from the database that is identified by the extra ID
		 * PRECONDITIONS: Parameters have been validated
		 * MODIFIES: Deletes the extra and its information from the extra table of the database
		 * POSTCONDITIONS: The specified extra will be deleted with the given extra ID from preconditions
		 */
		Message reply = new Message(i_msg.header.authLevel, i_msg.header.messageOwnerID, i_msg.header.nameHotel);
		ResponseMessage response = new ResponseMessage();
		databaseHelper dbcon 	= null;
		try {
			dbcon = new databaseHelper();
			int returnedRows = dbcon.modify("DELETE FROM test_extras WHERE extraID=" + i_msg.extras[0].extraID );
			if (returnedRows == 1)
			{
				response.fillResponse(ResponseCode.SUCCESS, "Deleted extra as Requested." +
						" extra ID: " + i_msg.extras[0].extraID);
			}
			else
			{
				response.fillResponse(ResponseCode.FAIL, "Delete failed." +
						" extra ID: " + i_msg.extras[0].extraID);
			}
		} catch (SQLException e) {
			System.err.println("Error in 'deleteextra'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			response.fillResponse(ResponseCode.FAIL, "Update failed." +
					" extra ID: " + i_msg.extras[0].extraID);
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'deleteextra'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			response.fillResponse(ResponseCode.FAIL, "Update failed." +
					" extra ID: " + i_msg.extras[0].extraID);
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		reply.response = response;
		return reply;
	}

	public Message getExtra(Message i_msg) {
		/*
		 * OVERVIEW: Retrieves a specific extra. Used to select a extra to view from the list of extra returned by getAllExtras function
		 * PRECONDITIONS: The specified extra is selected from the list of extras returned by getAllExtras. Parameters have been validated.
		 * MODIFIES: None
		 * POSTCONDITIONS: The specified extra is returned, if found; placed in extras[0] of returned Message
		 */
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.initializeExtras(1);
		replyMessage.initializeRooms(1);
		replyMessage.initializeAccounts(1);

		try {
			// create connection
			dbcon = new databaseHelper();
			// query the database for all rooms
			ResultSet rs = dbcon.select("SELECT e.*, r.roomID, r.roomNumber, a.firstName, a.lastName, a.accountID FROM " + i_msg.header.nameHotel + "_extras AS e LEFT JOIN " + i_msg.header.nameHotel + "_bookings AS b ON e.bookingID=b.bookingID LEFT JOIN " + i_msg.header.nameHotel + "_rooms AS r ON b.roomID=r.roomID LEFT JOIN accounts AS a ON b.bookingOwnerID=a.accountID WHERE e.extraID = " + i_msg.extras[0].extraID);
			while (rs.next()) {
				replyMessage.extras[0].extraID = rs.getInt("extraID");
				replyMessage.extras[0].bookingID = rs.getInt("bookingID");
				replyMessage.extras[0].extraName = rs.getString("extraName");
				replyMessage.extras[0].price = rs.getFloat("price");
				replyMessage.extras[0].date = rs.getDate("date");
				replyMessage.extras[0].creationTime = rs.getTimestamp("creationTime");
				replyMessage.rooms[0].roomID = rs.getInt("roomID");
				replyMessage.rooms[0].roomNumber = rs.getInt("roomNumber");
				replyMessage.accounts[0].accountID = rs.getInt("accountID");
				replyMessage.accounts[0].firstName = rs.getString("firstName");
				replyMessage.accounts[0].lastName = rs.getString("lastName");
			}
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
			replyMessage.response.responseString = "Query succeeded.";
		} catch (SQLException e) {
			System.err.println("Error in 'getRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'getRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		return replyMessage;
	}

	public Message getAllExtras(Message i_msg) {
		/*
		 * OVERVIEW: Returns the list of all extras that this user has authority to view. Returns a Message class with an array ExtraMessage objects.
		 * PRECONDITIONS: None
		 * MODIFIES: None
		 * POSTCONDITIONS: Message contains an array of ExtraMessage objects that represent the list of extras viewable by this user
		 */
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);

		try {
			// create connection
			dbcon = new databaseHelper();
			// query the database for all extras
			ResultSet rs = dbcon.select("SELECT e.*, r.roomID, r.roomNumber, a.firstName, a.lastName, a.accountID FROM " + i_msg.header.nameHotel + "_extras AS e LEFT JOIN " + i_msg.header.nameHotel + "_bookings AS b ON e.bookingID=b.bookingID LEFT JOIN " + i_msg.header.nameHotel + "_rooms AS r ON b.roomID=r.roomID LEFT JOIN accounts AS a ON b.bookingOwnerID=a.accountID");

			rs.last();
			int numRows = rs.getRow();

			replyMessage.initializeExtras(numRows);
			rs.beforeFirst();

			if (numRows==0) {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "No extras in database.";
			} else {
				int i = 0;

				while (rs.next()) {
					replyMessage.extras[i].extraID = rs.getInt("extraID");
					replyMessage.extras[i].bookingID = rs.getInt("bookingID");
					replyMessage.extras[i].extraName = rs.getString("extraName");
					replyMessage.extras[i].price = rs.getFloat("price");
					replyMessage.extras[i].date = rs.getDate("date");
					replyMessage.extras[i].creationTime = rs.getTimestamp("creationTime");
					i++;
				}
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Query succeeded.";
			}
		} catch (SQLException e) {
			System.err.println("Error in 'getAllRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'getAllRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		return replyMessage;
	}

	public Message getFilteredExtra(Message i_msg) {
		/*
		 * OVERVIEW: Returns a list of extras matching the specified parameters
		 * PRECONDITIONS: Desired filtered properties (extraID, bookingID, extraName, price, date)
		 * MODIFIES: None
		 * POSTCONDITIONS: Print out all extras with given properties from preconditions
		 */
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.extras=i_msg.extras;

		try {
			// create connection
			dbcon = new databaseHelper();

			String queryString = "SELECT * FROM " + i_msg.header.nameHotel + "_extras WHERE ";

			//first ExtraMessage holds the filter toggles, and the second message hold the filter values
			boolean nonFirst = false;
			if (i_msg.extras[0].extraID == 0) {
				queryString = queryString + "extraID=" + i_msg.extras[1].extraID;
				nonFirst = true;
			}
			if (i_msg.extras[0].bookingID == 0) {
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "bookingID=" + i_msg.extras[1].bookingID;
				nonFirst = true;
			}
			if (i_msg.extras[0].extraName=="CHECK") {
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "extraName='" + i_msg.extras[1].extraName + "'";
				nonFirst = true;
			}
			if (i_msg.extras[0].price==0){
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "price=" + i_msg.extras[1].price;
				nonFirst = true;
			}
			if (i_msg.extras[0].date==null){
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "date='" + i_msg.extras[1].date+ "'";
				nonFirst = true;
			}

			// query the database for all extras
			ResultSet rs = dbcon.select(queryString);
			if (!rs.next()) {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "No Extras in database.";
			} else {
				int i = 0;
				rs.beforeFirst();
				while (rs.next()) {
					i++;
				}
				rs.beforeFirst();
				replyMessage.initializeExtras(i);

				i = 0;

				while (rs.next()) {
					replyMessage.extras[i].extraID = rs.getInt("extraID");
					replyMessage.extras[i].bookingID = rs.getInt("bookingID");
					replyMessage.extras[i].extraName = rs.getString("extraName");
					replyMessage.extras[i].price = rs.getFloat("price");
					replyMessage.extras[i].date =rs.getDate("date");
					replyMessage.extras[i].creationTime =rs.getTimestamp("creationTime");
					i++;
				}
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Query succeeded.";
			}
		} catch (SQLException e) {
			System.err.println("Error in 'getAllExtra'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'getAllExtra'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		return replyMessage;
	}

	public Message getAccountExtras(Message i_msg) {
		/*
		 * OVERVIEW: Returns the list of all extras that this user has authority to view. Returns a Message class with an array ExtraMessage objects.
		 * PRECONDITIONS: None
		 * MODIFIES: None
		 * POSTCONDITIONS: Message contains an array of ExtraMessage objects that represent the list of extras viewable by this user
		 */
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);

		try {
			// create connection
			dbcon = new databaseHelper();
			// query the database for all extras
			ResultSet rs = dbcon.select("SELECT e.*, r.roomID, r.roomNumber, a.firstName, a.lastName, a.accountID FROM " + i_msg.header.nameHotel + "_extras AS e LEFT JOIN " + i_msg.header.nameHotel + "_bookings AS b ON e.bookingID=b.bookingID LEFT JOIN " + i_msg.header.nameHotel + "_rooms AS r ON b.roomID=r.roomID LEFT JOIN accounts AS a ON b.bookingOwnerID=a.accountID WHERE a.accountID="+ i_msg.accounts[0].accountID);

			rs.last();
			int numRows = rs.getRow();

			replyMessage.initializeExtras(numRows);
			rs.beforeFirst();

			if (!rs.next()) {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "No extras in database.";
			} else {
				int i = 0;
				rs.beforeFirst();
				while (rs.next()) {
					replyMessage.extras[i].extraID = rs.getInt("extraID");
					replyMessage.extras[i].bookingID = rs.getInt("bookingID");
					replyMessage.extras[i].extraName = rs.getString("extraName");
					replyMessage.extras[i].price = rs.getFloat("price");
					replyMessage.extras[i].date = rs.getDate("date");
					replyMessage.extras[i].creationTime = rs.getTimestamp("creationTime");
					i++;
				}
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Query succeeded.";
			}
		} catch (SQLException e) {
			System.err.println("Error in 'getAllRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'getAllRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		return replyMessage;
	}

	public Message getBookingExtras(Message i_msg) {
		/*
		 * OVERVIEW: Returns the list of all extras that this user has authority to view. Returns a Message class with an array ExtraMessage objects.
		 * PRECONDITIONS: None
		 * MODIFIES: None
		 * POSTCONDITIONS: Message contains an array of ExtraMessage objects that represent the list of extras viewable by this user
		 */
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);

		try {
			// create connection
			dbcon = new databaseHelper();
			// query the database for all extras
			ResultSet rs = dbcon.select("SELECT e.*, r.roomID, r.roomNumber, a.firstName, a.lastName, a.accountID FROM " + i_msg.header.nameHotel + "_extras AS e LEFT JOIN " + i_msg.header.nameHotel + "_bookings AS b ON e.bookingID=b.bookingID LEFT JOIN " + i_msg.header.nameHotel + "_rooms AS r ON b.roomID=r.roomID LEFT JOIN accounts AS a ON b.bookingOwnerID=a.accountID WHERE b.bookingID =" + i_msg.extras[0].bookingID);

			rs.last();
			int numRows = rs.getRow();

			replyMessage.initializeExtras(numRows);
			rs.beforeFirst();

			if (!rs.next()) {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "No extras in database.";
			} else {
				int i = 0;
				rs.beforeFirst();
				while (rs.next()) {
					replyMessage.extras[i].extraID = rs.getInt("extraID");
					replyMessage.extras[i].bookingID = rs.getInt("bookingID");
					replyMessage.extras[i].extraName = rs.getString("extraName");
					replyMessage.extras[i].price = rs.getFloat("price");
					replyMessage.extras[i].date = rs.getDate("date");
					replyMessage.extras[i].creationTime = rs.getTimestamp("creationTime");
					i++;
				}
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Query succeeded.";
			}
		} catch (SQLException e) {
			System.err.println("Error in 'getAllRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'getAllRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		return replyMessage;
	}


}