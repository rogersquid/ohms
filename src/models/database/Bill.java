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

	public Message addBill(Message i_msg){
		/*
		 * OVERVIEW: Adds a bill to the database
		 * PRECONDITIONS: Parameters have been validated
		 * POSTCONDITIONS: If addition was successful, the bill with the correct parameters will be added to the Database
		 */
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

	public Message editBill(Message i_msg){
		/*
		 * OVERVIEW: Edits an bill that is already in the database
		 * PRECONDITIONS: Parameters have been validated
		 * POSTCONDITIONS: The specified bill will be edited with the given parameters in the preconditions 
		 */
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

	public Message deleteBill(Message i_msg){
		/*
		 * OVERVIEW: Deletes a bill from the database that is identified by the bill ID 
		 * PRECONDITIONS: Parameters have been validated
		 * POSTCONDITIONS: The specified bill will be deleted with the given bill ID from preconditions 
		 */
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

	public Message getBill(Message i_msg){
		/*
		 * OVERVIEW: Retrieves a specific bill. Used to select a bill to view from the list of bill returned by getAllBills function
		 * PRECONDITIONS: The specified bill is selected from the list of bills returned by getAllBills. Parameters have been validated.
		 * POSTCONDITIONS: The specified bill is returned, if found; placed in bills[0] of returned Message
		 */
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
			float totalprice=0;
			rs=dbcon.select("SELECT price FROM " + i_msg.header.nameHotel + "_extras WHERE bookingID='" + i_msg.header.nameHotel + "_bookings AS bookings ON bills.bookingID=bookings.bookingID LEFT JOIN " + i_msg.header.nameHotel + "_rooms AS r ON bookings.roomID=r.roomID LEFT JOIN accounts AS a ON bookings.bookingOwnerID=a.accountID WHERE billID='"
					+ replyMessage.bills[0].bookingID +"'");
			while (rs.next()) {
	            totalprice += rs.getInt("price");
	        }
			rs=dbcon.select("SELECT price FROM " + i_msg.header.nameHotel + "_rooms WHERE roomID='" + i_msg.header.nameHotel + "_bookings AS bookings ON bills.bookingID=bookings.bookingID LEFT JOIN " + i_msg.header.nameHotel + "_rooms AS r ON bookings.roomID=r.roomID LEFT JOIN accounts AS a ON bookings.bookingOwnerID=a.accountID WHERE billID='"
					+ replyMessage.rooms[0].roomID +"'");
			while (rs.next()) {
	            totalprice += rs.getInt("price");
	        }
			replyMessage.bills[0].totalPrice=totalprice;
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
	public Message getAllBills(Message i_msg) {
		/*
		 * OVERVIEW: Returns the list of all bills that this user has authority to view. Returns a Message class with an array BillMessage objects.
		 * PRECONDITIONS: None
		 * POSTCONDITIONS: Message contains an array of BillMessage objects that represent the list of bills viewable by this user
		 */
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
				float totalprice=0;

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
					ResultSet rstwo=dbcon.select("SELECT price FROM " + i_msg.header.nameHotel + "_extras WHERE bookingID='"
							+ replyMessage.bills[i].bookingID +"'");
					while (rstwo.next()) {
			            totalprice += rstwo.getInt("price");
			        }
					rstwo=dbcon.select("SELECT price FROM " + i_msg.header.nameHotel + "_rooms WHERE roomID='" 
							+ replyMessage.rooms[i].roomID +"'");
					while (rstwo.next()) {
			            totalprice += rstwo.getInt("price");
			        }
					replyMessage.bills[i].totalPrice=totalprice;
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
	
	public Message getFilteredBill(Message i_msg) {
		/*
		 * OVERVIEW: Returns a list of Bills matching the specified parameters 
		 * PRECONDITIONS: Desired filtered properties (billID, bookingID, paymentType, status) 
		 * POSTCONDITIONS: Print out all bills with given properties from preconditions
		 */
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.bills=i_msg.bills;

		try {
			// create connection
			dbcon = new databaseHelper();
			
			String queryString;
			if(i_msg.accounts!=null && i_msg.accounts[0].accountID>0) {
				queryString = "SELECT b.* FROM " + i_msg.header.nameHotel + "_bills AS b LEFT JOIN " + i_msg.header.nameHotel + "_bookings AS bk ON b.bookingID=bk.bookingID WHERE bk.bookingOwnerID='"+i_msg.accounts[0].accountID+"'";
			} else {
				queryString = "SELECT * FROM " + i_msg.header.nameHotel + "_bills WHERE ";

				//first BillMessage holds the filter toggles, and the second message hold the filter values
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
			}

			// query the database for all bills
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