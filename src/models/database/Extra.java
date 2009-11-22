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
			int returnedRows 	= dbcon.insert("INSERT INTO "
					+ i_msg.header.nameHotel
					+ "_extras (bookingID, extraName, price, date, creationDate) "
					+ "VALUES ('" 
					//+ i_msg.extras[0].extraID + "', '"
					+ i_msg.extras[0].bookingID + "', '"
					+ i_msg.extras[0].extraName + "', '"
					+ i_msg.extras[0].date + "', '"
					+ i_msg.extras[0].creationDate + "')");
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
			int returnedRows = dbcon.modify("UPDATE " + i_msg.header.nameHotel
					+ "_extras SET extraID='"
					+ i_msg.extras[0].extraID
					+ "', extraName='" + i_msg.extras[0].extraName
					+ "', price='" + i_msg.extras[0].price + "'");
			if (returnedRows != 1) {
				replyMessage.response.fillResponse(ResponseCode.FAIL, "Editting Extra failed." +
						" StartDate: " + i_msg.extras[0].date);
				return replyMessage;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Editting Extra failed." +
					" StartDate: " + i_msg.extras[0].date);
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Editting Extra failed." +
					" StartDate: " + i_msg.extras[0].date);
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		replyMessage.response.fillResponse(ResponseCode.SUCCESS, "Edited one Extra as Requested." +
				" StartDate: " + i_msg.extras[0].date);
		return replyMessage;
	}

	public Message deleteExtra(Message i_msg){
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		try {
			dbcon 				= new databaseHelper();
			// Extra id or owner + sDate
			int returnedRows = dbcon.modify("DELETE FROM  "+ i_msg.header.nameHotel + "_extras WHERE extraID='" + i_msg.extras[0].extraID
					+ "'");
			if (returnedRows != 1){
				replyMessage.response.fillResponse(ResponseCode.FAIL, "Deleting Extra failed." +
						" extraID: " + i_msg.extras[0].extraID);
				return replyMessage;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Deleting Extra failed." +
					" extraID: " + i_msg.extras[0].extraID);
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Deleting Extra failed." +
					" extraID: " + i_msg.extras[0].extraID);
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		replyMessage.response.fillResponse(ResponseCode.SUCCESS, "Edited one Extra as Requested." +
				" StartDate: " + i_msg.extras[0].date);
		return replyMessage;
	}

	public Message getExtra(Message i_msg){
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		// Not the best way to do it but should be a deep Copy - I will investigate
		replyMessage.initializeExtras(1);
		try {
			dbcon 				= new databaseHelper();
			// Extra id
			ResultSet rs=dbcon.select("Select count(*) FROM  "
					+ i_msg.header.nameHotel + "_extras WHERE extraID='"
					+ i_msg.extras[0].extraID +"'");
			rs.next();
			int i=rs.getInt(1);
			if(i!=1){
				replyMessage.response.fillResponse(ResponseCode.FAIL, "view Extra failed." +
						" StartDate: " + i_msg.extras[0].date);
				return replyMessage;
			}
			rs=dbcon.select("Select * FROM test_extras WHERE extraID='"
					+ i_msg.extras[0].extraID +"'");
			while (rs.next()) {
	            int cbookingID = rs.getInt("bookingID");
	            int cextraID = rs.getInt("extraID");
	            String cextraName = rs.getString("extraName");
	            int cprice = rs.getInt("price");
	            java.sql.Timestamp creationDate= new java.sql.Timestamp(rs.getDate("creationDate").getTime());
	            java.sql.Date cdate = rs.getDate("startDate");
	            replyMessage.extras[0].fillAll(cextraID, cbookingID, cextraName, cprice, cdate, creationDate);
	        }
		} catch (SQLException e) {
			System.err.println("Error.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "view Extra failed." +
					" StartDate: " + i_msg.extras[0].date);
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error.  ClassNotFoundException was thrown:");
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
		replyMessage.response.fillResponse(ResponseCode.SUCCESS, "View one Extra as Requested." +
				" StartDate: " + i_msg.extras[0].date);
		return replyMessage;
	}

	public Message getAllExtra(Message i_msg){
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);

		try {
			dbcon 				= new databaseHelper();
			ResultSet rs=dbcon.select("Select count(*) FROM " + i_msg.header.nameHotel + "_extras");
			rs.next();
			int numberofrows=rs.getInt(1);
			if(numberofrows<0){
				replyMessage.response.fillResponse(ResponseCode.FAIL, "get All Extra failed." +
						" extraID: " + i_msg.extras[0].extraID);
				return replyMessage;
			}
			else if (numberofrows==0){
				replyMessage.response.fillResponse(ResponseCode.SUCCESS, "There is no Extra." +
						" extraID: " + i_msg.extras[0].extraID);
				return replyMessage;
			}else{
				replyMessage.initializeBookings(numberofrows);
			}
			rs=dbcon.select("Select * FROM " + i_msg.header.nameHotel + "_extras");
			int i=0;
			while (rs.next()) {
				replyMessage.extras[i].extraID=rs.getInt("extraID");
				replyMessage.extras[i].bookingID= rs.getInt("bookingID");
				replyMessage.extras[i].extraName= rs.getString("extraName");
				replyMessage.extras[i].price= rs.getFloat("price");
				replyMessage.extras[i].date= rs.getDate("date");
				replyMessage.extras[i].creationDate= new java.sql.Timestamp(rs.getDate("creationDate").getTime());
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
		replyMessage.response.fillResponse(ResponseCode.SUCCESS, "ViewAll one Extra as Requested." +
				" StartDate: " + i_msg.extras[0].date);
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
				queryString = queryString + "bookingID='" + i_msg.extras[0].bookingID + "'";
				nonFirst = true;
			}
			if (i_msg.extras[0].date != null) {
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "date='" + i_msg.extras[0].date + "'";
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
					replyMessage.extras[i].creationDate = rs.getTimestamp("creationDate");

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
					replyMessage.extras[i].creationDate= new java.sql.Timestamp(rs.getDate("creationDate").getTime());
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
						+"(creationDate = "+ i_msg.extras[0].date
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