/*
 * Bill.java
 * 
 * This class does the bill keeping functions and talks directly to the database. It can add, delete, retrieve, and edit
 * bill(s) information
 * 
 */
package models.database;

import models.messages.*;
import models.messages.ResponseMessage.ResponseCode;
import java.sql.*;

public class Bill {

	

	
	/*
	 * PRE: Parameters have been validated
	 * POST: If addition was successful, Database contains the new bill
	 * 
	 * 
	 * Adds a bill to the database
	 * 
	 */
	public Message addBill(Message i_msg){
		// All the information is filled in. This puts all the information into the database.
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.bills=i_msg.bills;
		try {
			// create connection
			dbcon = new databaseHelper();
			// insert the bill in to appropriate hotel
			int returnedRows 	= dbcon.insert("INSERT INTO "
					+ i_msg.header.nameHotel
					+ "_bills (bookingID, paymentType, status) "
					+ "VALUES ('" 
					+ i_msg.bills[0].bookingID + "', '"
					+ "none" + "', '"
					+ 0 + "')");

			// check the number of rows changed to see whether response is as expected
			if (returnedRows > 0) {
				System.out.println("Success");
				replyMessage.response.fillResponse(ResponseCode.SUCCESS, "Added one Bill as Requested." +
						" billID: " + i_msg.header.messageOwnerID);
			} else {
				System.out.println("Failure");
				replyMessage.response.fillResponse(ResponseCode.FAIL, "Adding Bill failed.");
			}
		// check the exceptions. If an exception is thrown, operation failed
		} catch (SQLException e) {
			System.err.println("Error.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Adding Bill failed.");
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Adding Bill failed.");
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return replyMessage;
	}

	
	
	
	
	/*
	 * PRE: Parameters have been validated
	 * POST: Database contains the edited bill
	 * 
	 * 
	 * Edits an account that is already in the database
	 * 
	 */
	public Message editBill(Message i_msg){

		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.bills=i_msg.bills;
		try {
			dbcon = new databaseHelper();
			ResultSet rs = dbcon.select("SELECT * FROM " + i_msg.header.nameHotel + "_bills WHERE billID = " + i_msg.bills[0].billID);
			rs.next();
			int returnedRows = dbcon.modify("UPDATE " + i_msg.header.nameHotel + "_bills SET bookingID='"
					+ i_msg.bills[0].bookingID 
					+ "', paymentType='" + i_msg.bills[0].paymentType 
					+ "', status='" +((i_msg.bills[0].status)?1:0) + "' "
					+ "WHERE billID='" + i_msg.bills[0].billID
					+ "'");
		if (returnedRows != 1) {
				replyMessage.response.fillResponse(ResponseCode.FAIL, "Editting Bill failed.");
				return replyMessage;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Editting Bill failed.");
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Editting Bill failed.");
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		replyMessage.response.fillResponse(ResponseCode.SUCCESS, "Edited one Bill as Requested.");
		return replyMessage;
	}

	
	/*
	 * PRE: Parameters have been validated
	 * POST: Specified bill is deleted from the database 
	 * 
	 * 
	 * Deletes a bill from the database that is identified by the bill Id or owner ID and date made 
	 * 
	 */
	public Message deleteBill(Message i_msg){
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		try {
			dbcon 				= new databaseHelper();
			// Bill id or owner + sDate
			int returnedRows = dbcon.modify("DELETE FROM  "+ i_msg.header.nameHotel + "_bills WHERE billID='" + i_msg.bills[0].billID
					+ "'");
			if (returnedRows != 1){
				replyMessage.response.fillResponse(ResponseCode.FAIL, "Deleting Bill failed." +
						" billID: " + i_msg.bills[0].billID);
				return replyMessage;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Deleting Bill failed." +
					" billID: " + i_msg.bills[0].billID);
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Deleting bill failed." +
					" billID: " + i_msg.bills[0].billID);
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		replyMessage.response.fillResponse(ResponseCode.SUCCESS, "Edited one bill as Requested.");
		return replyMessage;
	}

	
	/*
	 * PRE: The specified bill is selected from the list of accounts returned by getAllBills. Parameters
	 * have been validated.
	 * POST: The specified bill is returned, if found; placed in bills[0] of returned Message
	 * 
	 * 
	 * Retrieves a specific bill. Used to select a bill to view
	 * from the list of bill returned by getAllBills function
	 * 
	 */
	public Message getBill(Message i_msg){
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.initializeBills(1);
		replyMessage.initializeRooms(1);
		replyMessage.initializeAccounts(1);

		try {
			dbcon 				= new databaseHelper();
			// Bill id
			ResultSet rs=dbcon.select("Select count(*) FROM  "
					+ i_msg.header.nameHotel + "_bills WHERE billID='"
					+ i_msg.bills[0].billID +"'");
			rs.next();
			int i=rs.getInt(1);
			if(i!=1){
				replyMessage.response.fillResponse(ResponseCode.FAIL, "view bill failed.");
				return replyMessage;
			}
			rs=dbcon.select("SELECT bills.*, r.roomID, r.roomNumber, a.firstName, a.lastName, a.accountID FROM " + i_msg.header.nameHotel + "_bills AS bills LEFT JOIN " + i_msg.header.nameHotel + "_bookings AS bookings ON bills.bookingID=bookings.bookingID LEFT JOIN " + i_msg.header.nameHotel + "_rooms AS r ON bookings.roomID=r.roomID LEFT JOIN accounts AS a ON bookings.bookingOwnerID=a.accountID WHERE billID='"
					+ i_msg.bills[0].billID +"'");
			while (rs.next()) {
	            replyMessage.bills[0].billID = rs.getInt("billID");
				replyMessage.bills[0].bookingID = rs.getInt("bookingID");
	            replyMessage.bills[0].paymentType = rs.getString("paymentType");
	            replyMessage.bills[0].status = rs.getBoolean("status");
				replyMessage.rooms[0].roomID = rs.getInt("roomID");
				replyMessage.rooms[0].roomNumber = rs.getInt("roomNumber");
				replyMessage.accounts[0].accountID = rs.getInt("accountID");
				replyMessage.accounts[0].firstName = rs.getString("firstName");
				replyMessage.accounts[0].lastName = rs.getString("lastName");
	        }
		} catch (SQLException e) {
			System.err.println("Error.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "view Bill failed.");
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "view Bill failed.");
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		replyMessage.response.fillResponse(ResponseCode.SUCCESS, "View one Bill as Requested.");
		return replyMessage;
	}

	
	
	
	
	
	/*
	 * PRE: None
	 * POST: Message contains an array of BillMessage objects that represent the list of bills
	 * viewable by this user
	 * 
	 * Returns the list of all bills that this user has authority to view. Returns a Message class with an array 
	 * BillMessage objects.
	 * 
	 */
	public Message getAllBill(Message i_msg) {
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.bills=i_msg.bills;

		try {
			// create connection
			dbcon = new databaseHelper();
			// query the database for all rooms
			ResultSet rs = dbcon.select("SELECT bills.*, r.roomID, r.roomNumber, a.firstName, a.lastName, a.accountID FROM " + i_msg.header.nameHotel + "_bills AS bills LEFT JOIN " + i_msg.header.nameHotel + "_bookings AS bookings ON bills.bookingID=bookings.bookingID LEFT JOIN " + i_msg.header.nameHotel + "_rooms AS r ON bookings.roomID=r.roomID LEFT JOIN accounts AS a ON bookings.bookingOwnerID=a.accountID");
			rs.last();
			int numRows = rs.getRow();
			
			replyMessage.initializeBills(numRows);
			replyMessage.initializeAccounts(numRows);
			replyMessage.initializeRooms(numRows);
			rs.beforeFirst();
			
			if (!rs.next()) {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "No extras in database.";
			} else {
				int i = 0;

				while (rs.next()) {
					replyMessage.bills[i].billID = rs.getInt("billID");
					replyMessage.bills[i].bookingID = rs.getInt("bookingID");
					replyMessage.bills[i].paymentType = rs.getString("paymentType");
					replyMessage.bills[i].status = rs.getBoolean("status");		
					replyMessage.rooms[i].roomID = rs.getInt("roomID");
					replyMessage.rooms[i].roomNumber = rs.getInt("roomNumber");
					replyMessage.accounts[i].accountID = rs.getInt("accountID");
					replyMessage.accounts[i].firstName = rs.getString("firstName");
					replyMessage.accounts[i].lastName = rs.getString("lastName");			
					i++;
				}
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Query succeeded.";
			}
		} catch (SQLException e) {
			System.err.println("Error in 'getAllBill'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'getAllBill'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		return replyMessage;
	}

	
	
	/*
	 * PRE: <William could you fill this in?>
	 * POST: <This too>
	 * 
	 * 	
	 * Returns a filtered list of bills specified by <William could you fill this in?> 
	 */
	public Message getFilteredBill(Message i_msg) {
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.bills=i_msg.bills;

		try {
			// create connection
			dbcon = new databaseHelper();

			String queryString = "SELECT * FROM " + i_msg.header.nameHotel + "_bills WHERE ";

			//first RoomMessage holds the filter toggles, and the second message hold the filter values
			boolean nonFirst = false;
			if (i_msg.bills[0].billID != 0) {
				queryString = queryString + "billID=" + i_msg.bills[1].billID;
				nonFirst = true;
			}
			if (i_msg.bills[0].bookingID != 0) {
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "bookingID=" + i_msg.bills[1].bookingID;
				nonFirst = true;
			}
			if (i_msg.bills[0].paymentType=="CHECK") {
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "paymentType='" + i_msg.bills[1].paymentType + "'";
				nonFirst = true;
			}
			if (i_msg.bills[0].status){
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "status=" + i_msg.bills[1].status;
				nonFirst = true;
			}

			// query the database for all rooms
			ResultSet rs = dbcon.select(queryString);
			if (!rs.next()) {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "No Bills in database.";
			} else {
				int i = 0;
				rs.beforeFirst();
				while (rs.next()) {
					i++;
				}
				rs.beforeFirst();
				replyMessage.initializeBills(i);

				i = 0;

				while (rs.next()) {
					replyMessage.bills[i].billID = rs.getInt("billID");
					replyMessage.bills[i].bookingID = rs.getInt("bookingID");
					replyMessage.bills[i].paymentType = rs.getString("paymentType");
					replyMessage.bills[i].status = rs.getBoolean("status");
					i++;
				}
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Query succeeded.";
			}
		} catch (SQLException e) {
			System.err.println("Error in 'getAllBill'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'getAllBill'.  ClassNotFoundException was thrown:");
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