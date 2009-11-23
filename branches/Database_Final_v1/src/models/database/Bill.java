package models.database;

import models.messages.*;
import models.messages.ResponseMessage.ResponseCode;
import java.sql.*;

public class Bill {

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
					+ "_bill (bookingID, paymentType, status) "
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

		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.bills=i_msg.bills;
		try {
			dbcon = new databaseHelper();
			ResultSet rs = dbcon.select("SELECT * FROM " + i_msg.header.nameHotel + "_bill WHERE billID = " + i_msg.bills[0].billID);
			rs.next();
			int returnedRows = dbcon.modify("UPDATE test_bill SET bookingID='"
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
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		try {
			dbcon 				= new databaseHelper();
			// Bill id or owner + sDate
			int returnedRows = dbcon.modify("DELETE FROM  "+ i_msg.header.nameHotel + "_bill WHERE billID='" + i_msg.bills[0].billID
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
					+ i_msg.header.nameHotel + "_bill WHERE billID='"
					+ i_msg.bills[0].billID +"'");
			rs.next();
			int i=rs.getInt(1);
			if(i!=1){
				replyMessage.response.fillResponse(ResponseCode.FAIL, "view bill failed.");
				return replyMessage;
			}
			rs=dbcon.select("Select * FROM test_bill WHERE billID='"
					+ i_msg.bills[0].billID +"'");
			while (rs.next()) {
	            int cbillID = rs.getInt("billID");
				int cbookingID = rs.getInt("bookingID");
	            String cpaymentType = rs.getString("paymentType");
	            boolean cstatus = rs.getBoolean("status");
	            replyMessage.bills[0].fillAll(cbillID, cbookingID, cpaymentType, cstatus);
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

	public Message getAllBill(Message i_msg) {
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.bills=i_msg.bills;

		try {
			// create connection
			dbcon = new databaseHelper();
			// query the database for all rooms
			ResultSet rs = dbcon.select("SELECT * FROM " + i_msg.header.nameHotel + "_bill");
			rs.last();
			int numRows = rs.getRow();
			
			replyMessage.initializeBills(numRows);
			//replyMessage.initializeAccounts(numRows);
			//replyMessage.initializeRooms(numRows);
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
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.bills=i_msg.bills;
		try {
			// create connection
			dbcon = new databaseHelper();

			String queryString = "SELECT * FROM " + i_msg.header.nameHotel + "_bill WHERE ";

			boolean nonFirst = false;
			if (i_msg.bills[0].status) {
				queryString = queryString + "status='" + i_msg.bills[0].status + "'";
				nonFirst = true;
			}
			if (i_msg.bills[0].paymentType != "") {
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "paymentType='" + i_msg.bills[0].paymentType + "'";
				nonFirst = true;
			}

			// query the database for all rooms
			ResultSet rs = dbcon.select(queryString);
			if (!rs.next()) {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "No Bills in database.";
			} else {
				int i = 0;
				rs = dbcon.select(queryString);
				while (rs.next()) {
					i++;
				}
				rs = dbcon.select(queryString);
				replyMessage.bills = new BillMessage[i];
				i = 0;

				while (rs.next()) {
					replyMessage.bills[i].billID = rs.getInt("billID");
					replyMessage.bills[i].bookingID = rs.getInt("bookingID");
					replyMessage.bills[i].paymentType = rs.getString("paymentType");
					replyMessage.bills[i].status = rs.getBoolean("status");
					i++;
				}
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