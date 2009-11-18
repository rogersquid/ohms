package models.database;

import models.messages.*;
import models.messages.ResponseMessage.ResponseCode;
import java.sql.*;

public class Booking {
	public Message addBooking(Message i_msg){
		// COMMENT YOUR CODE
		// All the information is filled in. This puts all the information into the database.
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		// Not the best way to do it but should be a deep Copy - I will investigate
		replyMessage.bookings=i_msg.bookings;
		try {
			// create connection
			dbcon = new databaseHelper(i_msg.header.nameHotel);
			// insert the booking in to appropriate hotel booking
			int returnedRows 	= dbcon.insert("INSERT INTO " + i_msg.header.nameHotel + "_bookings (creationDate, startDate, ownerID, endDate, roomID, status) " +
					"VALUES ('" + i_msg.bookings[0].startDate + "', '" 
					+ i_msg.bookings[0].ownerID + "', '" + i_msg.bookings[0].endDate + "', '" 
					+ i_msg.bookings[0].roomID + "', '" + i_msg.bookings[0].status + "')");
			// check the number of rows changed to see whether response is as expected
			if (returnedRows > 0) {
				System.out.println("Success");
				replyMessage.response.fillResponse(ResponseCode.SUCCESS, "Added one Booking as Requested." +
						" OwnerID: " + i_msg.header.messageOwnerID);
			} else {
				System.out.println("Failure");
				replyMessage.response.fillResponse(ResponseCode.FAIL, "Adding Booking failed." +
						" StartDate: " + i_msg.bookings[0].startDate);
			}
		// check the exceptions. If an exception is thrown, operation failed
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Adding Booking failed." +
					" StartDate: " + i_msg.bookings[0].startDate);
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Adding Booking failed." +
					" StartDate: " + i_msg.bookings[0].startDate);
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return replyMessage;
	}
	public Message editBooking(Message i_msg){
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		// Not the best way to do it but should be a deep Copy - I will investigate
		replyMessage.bookings=i_msg.bookings;
		try {
			dbcon = new databaseHelper(i_msg.header.nameHotel);
			int returnedRows = dbcon.modify("UPDATE "+ i_msg.header.nameHotel + "_bookings SET ownerID='" + i_msg.bookings[0].ownerID
					+ "', creationDate='" +i_msg.bookings[0].creationDate + "', startDate='" + i_msg.bookings[0].startDate
					+ "', endDate='" + i_msg.bookings[0].endDate + "', roomID='" + i_msg.bookings[0].roomID
					+ "', status='" + i_msg.bookings[0].status + "'");
			if (returnedRows != 1) {
				replyMessage.response.fillResponse(ResponseCode.FAIL, "Editting Booking failed." +
						" StartDate: " + i_msg.bookings[0].startDate);
				return replyMessage;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Editting Booking failed." +
					" StartDate: " + i_msg.bookings[0].startDate);
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Editting Booking failed." +
					" StartDate: " + i_msg.bookings[0].startDate);
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		replyMessage.response.fillResponse(ResponseCode.SUCCESS, "Edited one Booking as Requested." +
				" StartDate: " + i_msg.bookings[0].startDate);
		return replyMessage;
	}
	public Message deleteBooking(Message i_msg){
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		try {
			dbcon 				= new databaseHelper(i_msg.header.nameHotel);
			// booking id or owner + sDate
			int returnedRows = dbcon.modify("DELETE FROM  "+ i_msg.header.nameHotel + "_bookings WHERE bookingID='" + i_msg.bookings[0].bookingID
					+ "'");
			if (returnedRows != 1){
				replyMessage.response.fillResponse(ResponseCode.FAIL, "Deleting Booking failed." +
						" bookingID: " + i_msg.bookings[0].bookingID);
				return replyMessage;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Deleting Booking failed." +
					" bookingID: " + i_msg.bookings[0].bookingID);
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Deleting Booking failed." +
					" bookingID: " + i_msg.bookings[0].bookingID);
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		replyMessage.response.fillResponse(ResponseCode.SUCCESS, "Edited one Booking as Requested." +
				" StartDate: " + i_msg.bookings[0].startDate);
		return replyMessage;
	}
	public Message getBooking(Message i_msg){
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		// Not the best way to do it but should be a deep Copy - I will investigate
		replyMessage.initializeBookings(1);
		try {
			dbcon 				= new databaseHelper(i_msg.header.nameHotel);
			// booking id
			ResultSet rs=dbcon.select("Select count(*) FROM  "+ i_msg.header.nameHotel + "_bookings WHERE bookingID='" 
					+ i_msg.bookings[0].bookingID +"'");
			rs.next();
			int i=rs.getInt(1);
			if(i!=1){
				replyMessage.response.fillResponse(ResponseCode.FAIL, "view Booking failed." +
						" StartDate: " + i_msg.bookings[0].startDate);
				return replyMessage;
			}
			rs=dbcon.select("Select * FROM test_bookings WHERE bookingID='" 
					+ i_msg.bookings[0].bookingID +"'");
			while (rs.next()) {
	            int cbookingID = rs.getInt("bookingID");
	            int cownerID = rs.getInt("ownerId");
	            java.sql.Timestamp creationDate= new java.sql.Timestamp(rs.getDate("creationDate").getTime());
	            java.sql.Date cstartDate = rs.getDate("startDate");
	            java.sql.Date endDate = rs.getDate("endDate");
	            int croomID = rs.getInt("roomID");
	            int cstatus = rs.getInt("status");
	            replyMessage.bookings[0].fillAll(cbookingID, cownerID, creationDate, cstartDate, endDate, croomID, cstatus);
	        }
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "view Booking failed." +
					" StartDate: " + i_msg.bookings[0].startDate);
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "view Booking failed." +
					" StartDate: " + i_msg.bookings[0].startDate);
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		replyMessage.response.fillResponse(ResponseCode.SUCCESS, "View one Booking as Requested." +
				" StartDate: " + i_msg.bookings[0].startDate);
		return replyMessage;
	}
	public Message getAllBooking(Message i_msg){
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		
		try {
			dbcon 				= new databaseHelper(i_msg.header.nameHotel);
			ResultSet rs=dbcon.select("Select count(*) FROM " + i_msg.header.nameHotel + "_bookings");
			rs.next();
			int numberofrows=rs.getInt(1);
			if(numberofrows<0){
				replyMessage.response.fillResponse(ResponseCode.FAIL, "get All Booking failed." +
						" OwnerID: " + i_msg.bookings[0].ownerID);
				return replyMessage;
			}
			else if (numberofrows==0){
				replyMessage.response.fillResponse(ResponseCode.SUCCESS, "There is no booking." +
						" OwnerID: " + i_msg.bookings[0].ownerID);
				return replyMessage;
			}else{
				replyMessage.initializeBookings(numberofrows);
			}
			rs=dbcon.select("Select * FROM " + i_msg.header.nameHotel + "_bookings");
			int i=0;
			while (rs.next()) {
				replyMessage.bookings[i].bookingID=rs.getInt("bookingID");
				replyMessage.bookings[i].ownerID= rs.getInt("ownerId");
				replyMessage.bookings[i].creationDate= new java.sql.Timestamp(rs.getDate("creationDate").getTime());
				replyMessage.bookings[i].startDate= rs.getDate("startDate");
				replyMessage.bookings[i].endDate= rs.getDate("endDate");
				replyMessage.bookings[i].roomID= rs.getInt("roomID");
				replyMessage.bookings[i].status = rs.getInt("status");
				i++;
	        }
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "view Booking failed." +
					" StartDate: " + i_msg.bookings[0].startDate);
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "view Booking failed." +
					" StartDate: " + i_msg.bookings[0].startDate);
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		replyMessage.response.fillResponse(ResponseCode.SUCCESS, "ViewAll one Booking as Requested." +
				" StartDate: " + i_msg.bookings[0].startDate);
		return replyMessage;
	}
	public Message getFilteredBooking(Message i_msg){
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);

		if(i_msg.bookings[0].ownerID>0){
			// Bookings of a specific owner
			try {
				dbcon = new databaseHelper(i_msg.header.nameHotel);
				ResultSet rs=dbcon.select("Select count(*) FROM " + i_msg.header.nameHotel + 
										"_bookings WHERE ownerID='" + i_msg.bookings[0].ownerID +"'");
				int numberofrows=rs.getInt(1);
				if(numberofrows<0){
					replyMessage.response.fillResponse(ResponseCode.FAIL, "viewAll Booking failed." +
							" OwnerID: " + i_msg.bookings[0].ownerID);
					return replyMessage;
				}
				else if (numberofrows==0){
					replyMessage.response.fillResponse(ResponseCode.SUCCESS, "There is no booking." +
							" OwnerID: " + i_msg.bookings[0].ownerID);
					return replyMessage;
				}
				rs=dbcon.select("Select * FROM booking WHERE ownerID='" + i_msg.bookings[0].ownerID + "'");
				int i=0;
				while (rs.next()) {
					replyMessage.bookings[i].bookingID=rs.getInt("bookingID");
					replyMessage.bookings[i].ownerID= rs.getInt("ownerId");
					replyMessage.bookings[i].creationDate= new java.sql.Timestamp(rs.getDate("bookingDate").getTime());
					replyMessage.bookings[i].startDate= rs.getDate("startDate");
					replyMessage.bookings[i].endDate= rs.getDate("endDate");
					replyMessage.bookings[i].roomID= rs.getInt("roomID");
					replyMessage.bookings[i].status = rs.getInt("status");
					i++;
		        }
			} catch (SQLException e) {
				System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
				e.printStackTrace(System.err);
				replyMessage.response.fillResponse(ResponseCode.FAIL, "view Booking failed." +
						" StartDate: " + i_msg.bookings[0].startDate);
				return replyMessage;
			} catch (ClassNotFoundException e) {
				System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
				e.printStackTrace(System.err);
				replyMessage.response.fillResponse(ResponseCode.FAIL, "view Booking failed." +
						" StartDate: " + i_msg.bookings[0].startDate);
				return replyMessage;
			}
			finally {
				if (dbcon != null) {
					dbcon.close();
				}
			}
		}
		else if(i_msg.bookings[0].startDate!=null && i_msg.bookings[0].endDate!=null){
			// ROOM ID that are available in a given range of dates
			try {
				dbcon 	= new databaseHelper(i_msg.header.nameHotel);
				// for a given day range what rooms are free?  -- QUESTION FOR MURAT 
				/*
				 * ' ve " serpistirmek gerekio mu
				 * what about the extra joins?
				 */
				ResultSet rs=dbcon.select("SELECT count(*) FROM "+ i_msg.header.nameHotel +"_rooms WHERE roomID NOT IN " +
						"(SELECT roomID FROM "+ i_msg.header.nameHotel +"_bookings WHERE " +
						"(endDate > "+ i_msg.bookings[0].startDate +" AND endDate < "+ i_msg.bookings[0].endDate +") "+
						"OR "+
						"(endDate> "+ i_msg.bookings[0].startDate +" AND startDate <= "+ i_msg.bookings[0].endDate +"))");
				int numberofrows=rs.getInt(1);
				if(numberofrows<0){
					replyMessage.response.fillResponse(ResponseCode.FAIL, "viewAll Booking failed." +
							" OwnerID: " + i_msg.bookings[0].ownerID);
					return replyMessage;
				}
				else if (numberofrows==0){
					replyMessage.response.fillResponse(ResponseCode.SUCCESS, "There is no booking." +
							" OwnerID: " + i_msg.bookings[0].ownerID);
					return replyMessage;
				}
				replyMessage.initializeBookings(numberofrows);
				rs=dbcon.select("SELECT roomID FROM "+ i_msg.header.nameHotel +"_rooms WHERE roomID NOT IN " +
						"(SELECT roomID FROM "+ i_msg.header.nameHotel +"_bookings WHERE " +
						"(endDate > "+ i_msg.bookings[0].startDate +" AND endDate < "+ i_msg.bookings[0].endDate +") "+
						"OR "+
						"(endDate> "+ i_msg.bookings[0].startDate +" AND startDate <= "+ i_msg.bookings[0].endDate +"))");
				int i=0;
				while (rs.next()) {
					replyMessage.bookings[i].roomID=rs.getInt("roomID");
					i++;
		        }
			} catch (SQLException e) {
				System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
				e.printStackTrace(System.err);
				replyMessage.response.fillResponse(ResponseCode.FAIL, "view Booking failed." +
						" StartDate: " + i_msg.bookings[0].startDate);
				return replyMessage;
			} catch (ClassNotFoundException e) {
				System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
				e.printStackTrace(System.err);
				replyMessage.response.fillResponse(ResponseCode.FAIL, "view Booking failed." +
						" StartDate: " + i_msg.bookings[0].startDate);
				return replyMessage;
			}
			finally {
				if (dbcon != null) {
					dbcon.close();
				}
			}
		}
		replyMessage.response.fillResponse(ResponseCode.SUCCESS, "ViewAll one Booking as Requested." +
				" StartDate: " + i_msg.bookings[0].startDate);
		return replyMessage;
	}
}
	/*
	public BookingMessage editBooking(BookingMessage i_msg){
		BookingMessage output=i_msg;
		Header iheader=i_msg.returnHeader();
		databaseHelper dbcon 	= null;
		try {
			dbcon 				= new databaseHelper(iheader.nameHotel);
			// bookingDate should it be editttable???
			int returnedRows = dbcon.modify("UPDATE test_bookings SET ownerID='" + i_msg.ownerID
					+ "', creationDate='" +i_msg.creationDate + "', startDate='" + i_msg.startDate
					+ "', endDate='" + i_msg.endDate + "', roomID='" + i_msg.roomID
					+ "', status='" + i_msg.status + "'");
			if (returnedRows == 1) {
				output.fillHeaderResponse(Header.Response.SUCCESS, "Edited one Booking as Requested." +
						" StartDate: " + i_msg.startDate);
			} else {
				output.fillHeaderResponse(Header.Response.FAIL, "Editting Booking failed." +
						" StartDate: " + i_msg.startDate);
			}
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			output.fillHeaderResponse(Header.Response.FAIL, "Editting Booking failed." +
					" StartDate: " + i_msg.startDate);
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output.fillHeaderResponse(Header.Response.FAIL, "Editting Booking failed." +
					" StartDate: " + i_msg.startDate);
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return output;
	}
	public BookingMessage deleteBooking(BookingMessage i_msg){
		BookingMessage output=i_msg;
		Header iheader=i_msg.returnHeader();
		databaseHelper dbcon 	= null;
		try {
			dbcon 				= new databaseHelper(iheader.nameHotel);
			// booking id or owner + sDate
			int returnedRows = dbcon.modify("DELETE FROM test_bookings WHERE bookingID='" + i_msg.bookingID
					+ "'");
			if (returnedRows == 1) {
				output.fillHeaderResponse(Header.Response.SUCCESS, "Delete one Booking as Requested." +
						" bookingID: " + i_msg.bookingID);
			} else {
				output.fillHeaderResponse(Header.Response.FAIL, "Deleting Booking failed." +
						" bookingID: " + i_msg.bookingID);
			}
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			output.fillHeaderResponse(Header.Response.FAIL, "Deleting Booking failed." +
					" bookingID: " + i_msg.bookingID);
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output.fillHeaderResponse(Header.Response.FAIL, "Deleting Booking failed." +
					" bookingID: " + i_msg.bookingID);
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return output;
	}
	public BookingMessage viewBooking(BookingMessage i_msg){
		BookingMessage output=i_msg;
		Header iheader=i_msg.returnHeader();
		databaseHelper dbcon 	= null;
		try {
			dbcon 				= new databaseHelper(iheader.nameHotel);
			// booking id or owner + sDate
			ResultSet rs=dbcon.select("Select count(*) FROM test_bookings WHERE bookingID='" 
					+ i_msg.bookingID +"'");
			rs.next();
			int i=rs.getInt(1);
			if(i!=1){
				output.fillHeaderResponse(Header.Response.FAIL, "view Booking failed." +
						" StartDate: " + i_msg.startDate);
				return output;
			}
			rs=dbcon.select("Select * FROM test_bookings WHERE bookingID='" 
					+ i_msg.bookingID +"'");
			while (rs.next()) {
	            int cbookingID = rs.getInt("bookingID");
	            int cownerID = rs.getInt("ownerId");
	            java.sql.Date creationDate = rs.getDate("creationDate");
	            java.sql.Date cstartDate = rs.getDate("startDate");
	            java.sql.Date endDate = rs.getDate("endDate");
	            int croomID = rs.getInt("roomID");
	            int cstatus = rs.getInt("status");
	            output.fillAll(cbookingID, cownerID, creationDate, cstartDate, endDate, croomID, cstatus);
	        }
			output.fillHeaderResponse(Header.Response.SUCCESS, "View one Booking as Requested." +
						" StartDate: " + i_msg.startDate);
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			output.fillHeaderResponse(Header.Response.FAIL, "view Booking failed." +
					" StartDate: " + i_msg.startDate);
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output.fillHeaderResponse(Header.Response.FAIL, "view Booking failed." +
					" StartDate: " + i_msg.startDate);
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return output;
	}
	public BookingMessage[] viewAllBooking(BookingMessage i_msg){
		BookingMessage[] output= new BookingMessage[1];
		Header iheader=i_msg.returnHeader();
		output[0]=new BookingMessage(iheader.messageOwnerID, iheader.authLevel, iheader.nameHotel, iheader.action);
		databaseHelper dbcon 	= null;
		// for customer
		try {
			dbcon 				= new databaseHelper(iheader.nameHotel);
			ResultSet rs=dbcon.select("Select count(*) FROM " + iheader.nameHotel + "_bookings");
			rs.next();
			int numberofrows=rs.getInt(1);
			if(numberofrows<0){
				output[0].fillHeaderResponse(Header.Response.FAIL, "viewAll Booking failed." +
						" OwnerID: " + i_msg.ownerID);
				return output;
			}
			else if (numberofrows==0){
				output[0].fillHeaderResponse(Header.Response.SUCCESS, "There is no booking." +
						" OwnerID: " + i_msg.ownerID);
				return output;
			}else{
				output=new BookingMessage[numberofrows];
			}
			rs=dbcon.select("Select * FROM " + iheader.nameHotel + "_bookings");
			int i=0;
			while (rs.next()) {
				output[i]=new BookingMessage(iheader.messageOwnerID, iheader.authLevel, iheader.nameHotel, iheader.action);
				output[i].bookingID=rs.getInt("bookingID");
				output[i].ownerID= rs.getInt("ownerId");
				output[i].creationDate= rs.getDate("creationDate");
				output[i].startDate= rs.getDate("startDate");
				output[i].endDate= rs.getDate("endDate");
				output[i].roomID= rs.getInt("roomID");
				output[i].status = rs.getInt("status");
				output[i].fillHeaderResponse(Header.Response.SUCCESS, "ViewAll one Booking as Requested." +
						" StartDate: " + i_msg.startDate);
				i++;
	        }
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			output[0].fillHeaderResponse(Header.Response.FAIL, "view Booking failed." +
					" StartDate: " + i_msg.startDate);
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output[0].fillHeaderResponse(Header.Response.FAIL, "view Booking failed." +
					" StartDate: " + i_msg.startDate);
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return output;
	}
	public BookingMessage[] viewAllSpecificBooking(BookingMessage i_msg){
		BookingMessage[] output= null;
		Header iheader=i_msg.returnHeader();
		databaseHelper dbcon 	= null;
		
		if(i_msg.ownerID>0){
			// owner Search
			try {
				dbcon 				= new databaseHelper(iheader.nameHotel);
				ResultSet rs=dbcon.select("Select count(*) FROM " + iheader.nameHotel + 
										"_bookings WHERE ownerID='" + i_msg.ownerID +"'");
				int numberofrows=rs.getInt(1);
				if(numberofrows<0){
					output[0].fillHeaderResponse(Header.Response.FAIL, "viewAll Booking failed." +
							" OwnerID: " + i_msg.ownerID);
					return output;
				}
				else if (numberofrows==0){
					output[0].fillHeaderResponse(Header.Response.SUCCESS, "There is no booking." +
							" OwnerID: " + i_msg.ownerID);
					return output;
				}
				rs=dbcon.select("Select * FROM booking WHERE ownerID='" + i_msg.ownerID + "'");
				int i=0;
				while (rs.next()) {
					output[i]=new BookingMessage(iheader.messageOwnerID, iheader.authLevel, iheader.nameHotel, iheader.action);
					output[i].bookingID=rs.getInt("bookingID");
					output[i].ownerID= rs.getInt("ownerId");
					output[i].creationDate= rs.getDate("bookingDate");
					output[i].startDate= rs.getDate("startDate");
					output[i].endDate= rs.getDate("endDate");
					output[i].roomID= rs.getInt("roomID");
					output[i].status = rs.getInt("status");
					output[i].fillHeaderResponse(Header.Response.SUCCESS, "ViewAll one Booking as Requested." +
							" StartDate: " + i_msg.startDate);
					i++;
		        }
			} catch (SQLException e) {
				System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
				e.printStackTrace(System.err);
				output[0].fillHeaderResponse(Header.Response.FAIL, "view Booking failed." +
						" StartDate: " + i_msg.startDate);
			} catch (ClassNotFoundException e) {
				System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
				e.printStackTrace(System.err);
				output[0].fillHeaderResponse(Header.Response.FAIL, "view Booking failed." +
						" StartDate: " + i_msg.startDate);
			}
			finally {
				if (dbcon != null) {
					dbcon.close();
				}
			}
		}
		else if(i_msg.startDate!=null && i_msg.endDate!=null){
			// owner Search
			try {
				dbcon 				= new databaseHelper(iheader.nameHotel);
				// nadd
				/*SELECT roomID FROM test_rooms WHERE roomID NOT IN
				(SELECT roomID FROM test_bookings WHERE 
				(endDate > startd AND endDate < endd)
				OR
				(endDate> endd AND startDate <= endd))*/
	/*
				
				ResultSet rs=dbcon.select("Select count(*) FROM " + iheader.nameHotel + 
										"_bookings WHERE ownerID='" + i_msg.ownerID +"'");
				int numberofrows=rs.getInt(1);
				if(numberofrows<0){
					output[0].fillHeaderResponse(Header.Response.FAIL, "viewAll Booking failed." +
							" OwnerID: " + i_msg.ownerID);
					return output;
				}
				else if (numberofrows==0){
					output[0].fillHeaderResponse(Header.Response.SUCCESS, "There is no booking." +
							" OwnerID: " + i_msg.ownerID);
					return output;
				}
				rs=dbcon.select("Select * FROM booking WHERE ownerID='" + i_msg.ownerID + "'");
				int i=0;
				while (rs.next()) {
					output[i]=new BookingMessage(iheader.messageOwnerID, iheader.authLevel, iheader.nameHotel, iheader.action);
					output[i].bookingID=rs.getInt("bookingID");
					output[i].ownerID= rs.getInt("ownerId");
					output[i].creationDate= rs.getDate("bookingDate");
					output[i].startDate= rs.getDate("startDate");
					output[i].endDate= rs.getDate("endDate");
					output[i].roomID= rs.getInt("roomID");
					output[i].status = rs.getInt("status");
					output[i].fillHeaderResponse(Header.Response.SUCCESS, "ViewAll one Booking as Requested." +
							" StartDate: " + i_msg.startDate);
					i++;
		        }
			} catch (SQLException e) {
				System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
				e.printStackTrace(System.err);
				output[0].fillHeaderResponse(Header.Response.FAIL, "view Booking failed." +
						" StartDate: " + i_msg.startDate);
			} catch (ClassNotFoundException e) {
				System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
				e.printStackTrace(System.err);
				output[0].fillHeaderResponse(Header.Response.FAIL, "view Booking failed." +
						" StartDate: " + i_msg.startDate);
			}
			finally {
				if (dbcon != null) {
					dbcon.close();
				}
			}
		}
		return output;
	}
}*/
