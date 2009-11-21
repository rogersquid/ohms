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
			dbcon = new databaseHelper();
			// insert the booking in to appropriate hotel booking
			int returnedRows 	= dbcon.insert("INSERT INTO " + i_msg.header.nameHotel + "_bookings (startDate, bookingOwnerID, endDate, roomID, status) " +
					"VALUES ('" + i_msg.bookings[0].startDate + "', '" 
					+ i_msg.bookings[0].ownerID + "', '" + i_msg.bookings[0].endDate + "', '" 
					+ i_msg.bookings[0].roomID + "', '" + i_msg.bookings[0].status + "')");
			// check the number of rows changed to see whether response is as expected
			if (returnedRows > 0) {
				System.out.println("Success");
				replyMessage.bookings[0].bookingID=returnedRows;
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
		//creationTime is not changeable
		replyMessage.bookings=i_msg.bookings;
		try {
			dbcon = new databaseHelper(i_msg.header.nameHotel);
			int returnedRows = dbcon.update("UPDATE "+ i_msg.header.nameHotel + "_bookings SET bookingOwnerID='" + i_msg.bookings[0].ownerID
					+ "', startDate='" + i_msg.bookings[0].startDate
					+ "', endDate='" + i_msg.bookings[0].endDate + "', roomID='" + i_msg.bookings[0].roomID
					+ "', status='" + i_msg.bookings[0].status + "' WHERE bookingID='" + i_msg.bookings[0].bookingID +"'");
			if (returnedRows != 1) {
				replyMessage.response.fillResponse(ResponseCode.FAIL, "Editting Booking failed. Because the updated is not = 1" +
						" StartDate: " + i_msg.bookings[0].startDate);
				System.err.println(returnedRows);
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
				System.err.println(returnedRows);
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
				replyMessage.response.fillResponse(ResponseCode.FAIL, "view Booking failed. Number of Rows Problem" +
						" bookingID: " + i_msg.bookings[0].bookingID);
				return replyMessage;
			}
			rs=dbcon.select("Select * FROM test_bookings WHERE bookingID='" 
					+ i_msg.bookings[0].bookingID +"'");
			while (rs.next()) {
	            int cbookingID = rs.getInt("bookingID");
	            int cownerID = rs.getInt("bookingOwnerID");
	            java.sql.Timestamp creationDate= new java.sql.Timestamp(rs.getTimestamp("creationTime").getTime());
	            java.sql.Date cstartDate = rs.getDate("startDate");
	            java.sql.Date endDate = rs.getDate("endDate");
	            int croomID = rs.getInt("roomID");
	            int cstatus = rs.getInt("status");
	            replyMessage.bookings[0].fillAll(cbookingID, cownerID, creationDate, cstartDate, endDate, croomID, cstatus);
	        }
		} catch (SQLException e) {
			System.err.println("Error in 'get_booking'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "view Booking failed." +
					" SQLException BookingID: " + i_msg.bookings[0].bookingID);
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
				" BookingID: " + i_msg.bookings[0].bookingID);
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
				replyMessage.bookings[i].ownerID= rs.getInt("bookingOwnerID");
				replyMessage.bookings[i].creationDate= new java.sql.Timestamp(rs.getDate("creationTime").getTime());
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
	public Message checkIn (Message i_msg){
		Message output=i_msg;
		databaseHelper dbcon 	= null;
		try {
			dbcon 				= new databaseHelper(i_msg.header.nameHotel);
			// booking id or owner + sDate
			if (i_msg.bookings[0].bookingID < 0) {
				output.response.fillResponse(ResponseCode.FAIL, "Checkin Failed: invalid Booking ID");
				return output;
			} else {
				ResultSet rs=dbcon.select("Select count(*) FROM test_bookings WHERE bookingID='" 
						+ i_msg.bookings[0].bookingID +"'");
				rs.next();
				int i=rs.getInt(1);
				if(i!=1){
					output.response.fillResponse(ResponseCode.FAIL, "Checkin Failed: Booking ID do not exist");
					return output;
				}
				rs=dbcon.select("Select * FROM test_bookings WHERE bookingID='" 
						+ i_msg.bookings[0].bookingID +"'");
				while (rs.next()) {
					java.util.Date today 	= new java.util.Date();
					java.sql.Date now = new java.sql.Date(today.getTime());
					java.sql.Date bookedTime = rs.getDate("startDate");
					if (rs.getInt("status") == 1) {
						output.response.fillResponse(ResponseCode.FAIL, "Checkin Failed: Booking has been checked in already");
						return output;
						/*
		            } else if (bookedTime.getYear() == now.getYear() &&
		            		bookedTime.getMonth() == now.getMonth() &&
		            		bookedTime.getDate() == now.getDate()) {
		            		*/
					} else if (now.getTime() >= bookedTime.getTime() && now.getTime() <= (bookedTime.getTime() + 86400000)) {
						int cbookingID = rs.getInt("bookingID");
			            int cownerID = rs.getInt("bookingOwnerID");
			            java.sql.Timestamp creationTime = rs.getTimestamp("creationTime");
			            java.sql.Date cstartDate = rs.getDate("startDate");
			            java.sql.Date endDate = rs.getDate("endDate");
			            int croomID = rs.getInt("roomID");
			            output.bookings[0].fillAll(cbookingID, cownerID, creationTime, cstartDate, endDate, croomID, 1);
			            output=editBooking(output);
			            if (output.response.responseCode == ResponseCode.SUCCESS) {
			            	output.response.fillResponse(ResponseCode.SUCCESS, "Check-in request succceed");
			            	Bill mybill=new Bill();
			            	//mybill.addBill(output);
			            } else {
			            	output.response.fillResponse(ResponseCode.FAIL, "Check-in request failed during update");
			            }
		            } else {
		            	output.response.fillResponse(ResponseCode.FAIL, "Checkin Failed: Checkin Time does not match");
						return output;
		            } 
		        }
				
			}
		} catch (SQLException e) {
			System.err.println("Error in 'Checkin'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			output.response.fillResponse(ResponseCode.FAIL, "Checkin failed." +
					" StartDate: " + i_msg.bookings[0].startDate);
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Checkin'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output.response.fillResponse(ResponseCode.FAIL, "Checkin failed." +
					" StartDate: " + i_msg.bookings[0].startDate);
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return output;
	}
}