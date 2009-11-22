package models.database;

import models.messages.*;
import models.messages.ResponseMessage.ResponseCode;
import java.sql.*;

public class Extra {

	public Message addExtra(Message i_msg){
		// All the information is filled in. This puts all the information into the database.
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.extras=i_msg.extras;
		try {
			// create connection
			dbcon = new databaseHelper();
			// insert the Extra in to appropriate hotel
			int returnedRows 	= dbcon.insert("INSERT INTO " + i_msg.header.nameHotel + "_extras (bookingID, extraName, price, date, creationTime) "
					+ "VALUES ('" 
					+ i_msg.extras[0].bookingID + "', '"
					+ i_msg.extras[0].extraName + "', '"
					+ i_msg.extras[0].price +"', '"
					+ i_msg.extras[0].date + "', '"
					+ i_msg.extras[0].creationTime + "')");
			// check the number of rows changed to see whether response is as expected
			if (returnedRows > 0) {
				System.out.println("Success");
				replyMessage.response.fillResponse(ResponseCode.SUCCESS, "Added one Extra as Requested." +
						" extraID: " + i_msg.header.messageOwnerID);
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

		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.extras=i_msg.extras;
		try {
			dbcon = new databaseHelper();
			ResultSet rs = dbcon.select("SELECT * FROM " + i_msg.header.nameHotel + "_extras WHERE extraID = " + i_msg.extras[0].extraID);
			rs.next();
			int returnedRows = dbcon.modify("UPDATE test_extras SET extraName='"
					+ i_msg.extras[0].extraName + "', price='"
					+ i_msg.extras[0].price + "' "
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
		// No date error checking implemented
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
			replyMessage.initializeAccounts(numRows);
			replyMessage.initializeRooms(numRows);
			rs.beforeFirst();
			
			if (!rs.next()) {
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
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.extras=i_msg.extras;
		try {
			// create connection
			dbcon = new databaseHelper();

			String queryString = "SELECT * FROM " + i_msg.header.nameHotel + "_extras WHERE ";

			boolean nonFirst = false;
			if (i_msg.extras[0].bookingID !=0) {
				queryString = queryString + "bookingID='" + i_msg.extras[1].bookingID + "'";
				nonFirst = true;
			}
			if (i_msg.extras[0].extraName == "CHECK") {
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "extraName='" + i_msg.extras[1].extraName + "'";
				nonFirst = true;
			}
			if (i_msg.extras[0].price != 0) {
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "price=" + i_msg.extras[1].price;
				nonFirst = true;
			}
			if (i_msg.extras[0].date != null) {
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "date='" + i_msg.extras[1].date + "'";
				nonFirst = true;
			}
			// query the database for all rooms
			ResultSet rs = dbcon.select(queryString);
			if (!rs.next()) {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "No Extras in database.";
			} else {
				int i = 0;
				rs = dbcon.select(queryString);
				while (rs.next()) {
					i++;
				}
				rs = dbcon.select(queryString);
				replyMessage.extras = new ExtraMessage[i];
				i = 0;

				while (rs.next()) {
					replyMessage.extras[i].extraID = rs.getInt("extraID");
					replyMessage.extras[i].bookingID = rs.getInt("bookingID");
					replyMessage.extras[i].extraName = rs.getString("extraName");
					replyMessage.extras[i].price = rs.getFloat("price");
					replyMessage.extras[i].date = rs.getDate("date");
					replyMessage.extras[i].creationTime = rs.getTimestamp("creationTime");

					i++;
				}
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

	/*
	public Message getFilteredExtra(Message i_msg){
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);

		if(i_msg.extras[0].extraID>0){
			// Extras of a specific owner
			try {
				dbcon = new databaseHelper();
				ResultSet rs=dbcon.select("Select count(*) FROM " + i_msg.header.nameHotel +
										"_extras WHERE extraID='" + i_msg.extras[0].extraID +"'");
				int numberofrows=rs.getInt(1);
				if(numberofrows<0){
					replyMessage.response.fillResponse(ResponseCode.FAIL, "viewAll Extra failed." +
							" extraID: " + i_msg.extras[0].extraID);
					return replyMessage;
				}
				else if (numberofrows==0){
					replyMessage.response.fillResponse(ResponseCode.SUCCESS, "There is no Extra." +
							" extraID: " + i_msg.extras[0].extraID);
					return replyMessage;
				}
				rs=dbcon.select("Select * FROM extra WHERE extraID='" + i_msg.extras[0].extraID + "'");
				int i=0;
				while (rs.next()) {
					replyMessage.extras[i].extraID=rs.getInt("extraID");
					replyMessage.extras[i].bookingID= rs.getInt("bookingID");
					replyMessage.extras[i].extraName= rs.getString("extraName");
					replyMessage.extras[i].price = rs.getFloat("price");
					replyMessage.extras[i].date= rs.getDate("date");
					replyMessage.extras[i].creationTime= new java.sql.Timestamp(rs.getDate("creationTime").getTime());
					i++;
		        }
			} catch (SQLException e) {
				System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
				e.printStackTrace(System.err);
				replyMessage.response.fillResponse(ResponseCode.FAIL, "view Extra failed." +
						" StartDate: " + i_msg.extras[0].date);
				return replyMessage;
			} catch (ClassNotFoundException e) {
				System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
				e.printStackTrace(System.err);
				replyMessage.response.fillResponse(ResponseCode.FAIL, "view Extra failed." +
						" StartDate: " + i_msg.extras[0].date);
				return replyMessage;
			}
			finally {
				if (dbcon != null) {
					dbcon.close();
				}
			}
		}

		else if(i_msg.extras[0].date!=null && i_msg.extras[0].bookingID!=0){
			// ROOM ID that are available in a given range of dates
			try {
				dbcon 	= new databaseHelper();

				ResultSet rs=dbcon.select("SELECT count(*) FROM "+ i_msg.header.nameHotel +"_rooms WHERE roomID NOT IN " +
						"(SELECT roomID FROM "+ i_msg.header.nameHotel
						+"_extras WHERE "
						+"(creationTime = "+ i_msg.extras[0].date
						+"))");
				int numberofrows=rs.getInt(1);
				if(numberofrows<0){
					replyMessage.response.fillResponse(ResponseCode.FAIL, "viewAll Extra failed." +
							" OwnerID: " + i_msg.extras[0].date);
					return replyMessage;
				}
				else if (numberofrows==0){
					replyMessage.response.fillResponse(ResponseCode.SUCCESS, "There is no Extra." +
							" OwnerID: " + i_msg.extras[0].date);
					return replyMessage;
				}
				replyMessage.initializeExtras(numberofrows);
				rs=dbcon.select("SELECT extraID FROM "+ i_msg.header.nameHotel
						+"_extras WHERE extraID NOT IN "
						+"(SELECT extraID FROM "+ i_msg.header.nameHotel
						+"_extras WHERE "
						+"(date > "+ i_msg.extras[0].date
						+" AND endDate < "+ i_msg.extras[0].date +") "
						+"OR "
						+"(endDate> "+ i_msg.extras[0].date
						+" AND startDate <= "+ i_msg.extras[0].date +"))");
				int i=0;
				while (rs.next()) {
					replyMessage.extras[i].roomID=rs.getInt("roomID");
					i++;
		        }
			} catch (SQLException e) {
				System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
				e.printStackTrace(System.err);
				replyMessage.response.fillResponse(ResponseCode.FAIL, "view Extra failed." +
						" StartDate: " + i_msg.extras[0].startDate);
				return replyMessage;
			} catch (ClassNotFoundException e) {
				System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
				e.printStackTrace(System.err);
				replyMessage.response.fillResponse(ResponseCode.FAIL, "view Extra failed." +
						" StartDate: " + i_msg.extras[0].startDate);
				return replyMessage;
			}
			finally {
				if (dbcon != null) {
					dbcon.close();
				}
			}
		}

		replyMessage.response.fillResponse(ResponseCode.SUCCESS, "ViewAll one Extra as Requested." +
				" StartDate: " + i_msg.extras[0].date);
		return replyMessage;
	}*/
}