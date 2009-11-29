package models.database;

import models.messages.*;
import models.messages.ResponseMessage.ResponseCode;
import java.sql.*;

public class Booking {

	public Message addBooking(Message i_msg){
		/*
		 * OVERVIEW: Adds a booking to the database
		 * PRECONDITIONS: Parameters have been validated
		 * MODIFIES: Adds booking with given parameters in the bookings table of the database
		 * POSTCONDITIONS: If addition was successful, the booking with the correct parameters will be added to the Database
		 */
		// All the information is filled in. This puts all the information into the database.
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
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
		/*
		 * OVERVIEW: Edits an booking that is already in the database
		 * PRECONDITIONS: Parameters have been validated
		 * MODIFIES: Edits the booking details with the given parameters in the bookings table of the database
		 * POSTCONDITIONS: The specified booking will be edited with the given parameters in the preconditions
		 */
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		// Not the best way to do it but should be a deep Copy - I will investigate
		//creationTime is not changeable
		replyMessage.bookings=i_msg.bookings;
		try {
			dbcon = new databaseHelper();
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
		/*
		 * OVERVIEW: Deletes a booking from the database that is identified by the booking ID
		 * PRECONDITIONS: Parameters have been validated
		 * MODIFIES: Deletes the booking and its information from the booking table of the database
		 * POSTCONDITIONS: The specified booking will be deleted with the given booking ID from preconditions
		 */
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		try {
			dbcon 				= new databaseHelper();
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
		replyMessage.response.fillResponse(ResponseCode.SUCCESS, "Deleted booking.");
		return replyMessage;
	}

	public Message getBooking(Message i_msg){
		/*
		 * OVERVIEW: Retrieves a specific booking. Used to select a booking to view from the list of booking returned by getAllBookings function
		 * PRECONDITIONS: The specified booking is selected from the list of bookings returned by getAllBookings. Parameters have been validated.
		 * MODIFIES: None
		 * POSTCONDITIONS: The specified booking is returned, if found; placed in bookings[0] of returned Message
		 */
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.initializeBookings(1);
		replyMessage.initializeAccounts(1);
		replyMessage.initializeRooms(1);
		replyMessage.initializeBills(1);
		try {
			dbcon 				= new databaseHelper();
			// booking id
			ResultSet rs=dbcon.select("Select count(*) FROM  "+ i_msg.header.nameHotel + "_bookings WHERE bookingID='"
					+ i_msg.bookings[0].bookingID +"'");
			rs.next();
			int i=rs.getInt(1);
			if(i!=1){
				replyMessage.response.fillResponse(ResponseCode.FAIL, "Booking does not exist." +
						" bookingID: " + i_msg.bookings[0].bookingID);
				return replyMessage;
			}
			rs=dbcon.select("SELECT b.*, r.roomNumber, a.firstName, a.lastName, bill.billID FROM " + i_msg.header.nameHotel + "_bookings AS b LEFT JOIN " + i_msg.header.nameHotel + "_rooms AS r ON b.roomID=r.roomID LEFT JOIN accounts AS a ON b.bookingOwnerID=a.accountID LEFT JOIN " + i_msg.header.nameHotel + "_bills AS bill ON b.bookingID=bill.bookingID WHERE b.bookingID='"
					+ i_msg.bookings[0].bookingID +"'");
			while (rs.next()) {
				replyMessage.bookings[0].bookingID=rs.getInt("bookingID");
				replyMessage.bookings[0].ownerID= rs.getInt("bookingOwnerID");
				replyMessage.bookings[0].creationDate= new java.sql.Timestamp(rs.getDate("creationTime").getTime());
				replyMessage.bookings[0].startDate= rs.getDate("startDate");
				replyMessage.bookings[0].endDate= rs.getDate("endDate");
				replyMessage.bookings[0].roomID= rs.getInt("roomID");
				replyMessage.bookings[0].status = rs.getInt("status");
				replyMessage.rooms[0].roomNumber = rs.getInt("roomNumber");
				replyMessage.accounts[0].firstName = rs.getString("firstName");
				replyMessage.accounts[0].lastName = rs.getString("lastName");
				replyMessage.bills[0].billID = rs.getInt("billID");
	        }
		} catch (SQLException e) {
			System.err.println("Error in 'get_booking'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "View booking failed." +
					" SQLException BookingID: " + i_msg.bookings[0].bookingID);
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "View booking failed.");
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
		/*
		 * OVERVIEW: Returns the list of all bookings that this user has authority to view. Returns a Message class with an array BookingMessage objects.
		 * PRECONDITIONS: None
		 * MODIFIES: None
		 * POSTCONDITIONS: Message contains an array of BookingMessage objects that represent the list of bookings viewable by this user
		 */
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);

		try {
			dbcon 				= new databaseHelper();
			ResultSet rs=dbcon.select("Select count(*) FROM " + i_msg.header.nameHotel + "_bookings");
			rs.next();
			int numberofrows=rs.getInt(1);
			if(numberofrows<0){
				replyMessage.response.fillResponse(ResponseCode.FAIL, "get All Booking failed." +
						" OwnerID: " + i_msg.bookings[0].ownerID);
				return replyMessage;
			}
			else if (numberofrows==0){
				replyMessage.response.fillResponse(ResponseCode.SUCCESS, "There is no booking.");
				replyMessage.initializeBookings(numberofrows);
				replyMessage.initializeRooms(numberofrows);
				replyMessage.initializeAccounts(numberofrows);
				return replyMessage;
			}else{
				replyMessage.initializeBookings(numberofrows);
				replyMessage.initializeRooms(numberofrows);
				replyMessage.initializeAccounts(numberofrows);
			}
			rs=dbcon.select("SELECT b.*, r.roomNumber, a.firstName, a.lastName FROM " + i_msg.header.nameHotel + "_bookings AS b LEFT JOIN " + i_msg.header.nameHotel + "_rooms AS r ON b.roomID=r.roomID LEFT JOIN accounts AS a ON b.bookingOwnerID=a.accountID ORDER BY b.creationTime DESC");
			int i=0;
			while (rs.next()) {
				replyMessage.bookings[i].bookingID=rs.getInt("bookingID");
				replyMessage.bookings[i].ownerID= rs.getInt("bookingOwnerID");
				replyMessage.bookings[i].creationDate= new java.sql.Timestamp(rs.getDate("creationTime").getTime());
				replyMessage.bookings[i].startDate= rs.getDate("startDate");
				replyMessage.bookings[i].endDate= rs.getDate("endDate");
				replyMessage.bookings[i].roomID= rs.getInt("roomID");
				replyMessage.bookings[i].status = rs.getInt("status");
				replyMessage.accounts[i].firstName = rs.getString("firstName");
				replyMessage.accounts[i].lastName = rs.getString("lastName");
				replyMessage.rooms[i].roomNumber = rs.getInt("roomNumber");
				i++;
	        }
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "view Booking failed.");
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "view Booking failed.");
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		replyMessage.response.fillResponse(ResponseCode.SUCCESS, "ViewAll one Booking as Requested.");
		return replyMessage;
	}
	public Message getFilteredBooking(Message i_msg){
		/*
		 * OVERVIEW: Returns a list of bookings matching the specified parameters
		 * PRECONDITIONS: Desired filtered properties (OwnerID, Date, Date Range)
		 * MODIFIES: None
		 * POSTCONDITIONS: Print out all bookings with given properties from preconditions
		 */
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		System.out.println("I am in the Filter Get");
		if(i_msg.bookings[0].ownerID>0){
			/*
			 *  This is the CustomerID filter.
			 *  Returns all bookings of a specific owner
			 */
			System.err.println("I am in the CustomerID search");
			try {
				dbcon = new databaseHelper();
				ResultSet rs=dbcon.select("Select count(*) FROM " + i_msg.header.nameHotel +
										"_bookings WHERE bookingOwnerID='" + i_msg.bookings[0].ownerID +"'");
				rs.next();
				int numberofrows=rs.getInt(1);
				if(numberofrows<0){
					replyMessage.response.fillResponse(ResponseCode.FAIL, "viewAll Booking failed." +
							" OwnerID: " + i_msg.bookings[0].ownerID);
					return replyMessage;
				}
				else if (numberofrows==0){
					replyMessage.response.fillResponse(ResponseCode.SUCCESS, "Customer has no Bookings" +
							" OwnerID: " + i_msg.bookings[0].ownerID);
					replyMessage.initializeBookings(numberofrows);
					replyMessage.initializeAccounts(numberofrows);
					replyMessage.initializeRooms(numberofrows);
					return replyMessage;
				}
				//rs=dbcon.select("Select * FROM " + i_msg.header.nameHotel + "_bookings WHERE bookingOwnerID='" + i_msg.bookings[0].ownerID + "'");
				rs=dbcon.select("SELECT b.*, r.roomNumber, a.firstName, a.lastName FROM " + i_msg.header.nameHotel + "_bookings AS b LEFT JOIN " + i_msg.header.nameHotel + "_rooms AS r ON b.roomID=r.roomID LEFT JOIN accounts AS a ON b.bookingOwnerID=a.accountID WHERE b.bookingOwnerID='" + i_msg.bookings[0].ownerID + "' ORDER BY b.creationTime DESC");
				replyMessage.initializeBookings(numberofrows);
				replyMessage.initializeAccounts(numberofrows);
				replyMessage.initializeRooms(numberofrows);

				int i=0;
				while (rs.next()) {
					replyMessage.bookings[i].bookingID=rs.getInt("bookingID");
					replyMessage.bookings[i].ownerID= rs.getInt("bookingOwnerID");
					replyMessage.bookings[i].creationDate= new java.sql.Timestamp(rs.getDate("creationTime").getTime());
					replyMessage.bookings[i].startDate= rs.getDate("startDate");
					replyMessage.bookings[i].endDate= rs.getDate("endDate");
					replyMessage.bookings[i].roomID= rs.getInt("roomID");
					replyMessage.bookings[i].status = rs.getInt("status");
					replyMessage.rooms[i].roomNumber = rs.getInt("roomNumber");
					replyMessage.accounts[i].firstName = rs.getString("firstName");
					replyMessage.accounts[i].lastName = rs.getString("lastName");
					i++;
		        }
			} catch (SQLException e) {
				e.printStackTrace(System.err);
				replyMessage.response.fillResponse(ResponseCode.FAIL, "view Booking failed.");
				return replyMessage;
			} catch (ClassNotFoundException e) {
				e.printStackTrace(System.err);
				replyMessage.response.fillResponse(ResponseCode.FAIL, "view Booking failed.");
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
			System.err.println("I am in the Date range search");
			try {
				dbcon 	= new databaseHelper();
				ResultSet rs=dbcon.select("SELECT count(*) FROM "+ i_msg.header.nameHotel +"_rooms WHERE roomID NOT IN (SELECT roomID FROM "+
						i_msg.header.nameHotel +"_bookings WHERE " +
						"(endDate >'"+ i_msg.bookings[0].startDate +"' AND endDate < '"+ i_msg.bookings[0].endDate +"') "+
						"OR (endDate> '"+ i_msg.bookings[0].startDate +"' AND startDate <='"+ i_msg.bookings[0].endDate +"'))");
				rs.next();
				int numberofrows=rs.getInt(1);
				if(numberofrows<0){
					replyMessage.response.fillResponse(ResponseCode.FAIL, "viewAll Booking failed.");
					return replyMessage;
				}
				else if (numberofrows==0){
					replyMessage.response.fillResponse(ResponseCode.SUCCESS, "There is no booking.");
					return replyMessage;
				}
				rs=dbcon.select("SELECT * FROM "+ i_msg.header.nameHotel +"_rooms WHERE roomID NOT IN (SELECT roomID FROM "+
						i_msg.header.nameHotel +"_bookings WHERE " +
						"(endDate >'"+ i_msg.bookings[0].startDate +"' AND endDate < '"+ i_msg.bookings[0].endDate +"') "+
						"OR (endDate> '"+ i_msg.bookings[0].startDate +"' AND startDate <='"+ i_msg.bookings[0].endDate +"')) ORDER BY roomNumber ASC");
				int i=0;
				replyMessage.initializeRooms(numberofrows);
				System.out.println("Rooms ready " + replyMessage.rooms.length);
				while (rs.next()) {
					replyMessage.rooms[i].roomID = rs.getInt("roomID");
					replyMessage.rooms[i].roomNumber = rs.getInt("roomNumber");
					replyMessage.rooms[i].floor = rs.getInt("floor");
					replyMessage.rooms[i].roomType = rs.getString("roomType");
					replyMessage.rooms[i].price = rs.getFloat("price");
					replyMessage.rooms[i].onsuite = rs.getBoolean("onsuite");
					replyMessage.rooms[i].tv = rs.getBoolean("tv");
					replyMessage.rooms[i].disabilityAccess = rs.getBoolean("disabilityAccess");
					replyMessage.rooms[i].elevator = rs.getBoolean("elevator");
					replyMessage.rooms[i].available = rs.getBoolean("available");
					replyMessage.rooms[i].phone = rs.getBoolean("phone");
					replyMessage.rooms[i].internet = rs.getBoolean("internet");
					replyMessage.rooms[i].kitchen = rs.getBoolean("kitchen");
					replyMessage.rooms[i].cleaned = rs.getBoolean("cleaned");
					replyMessage.rooms[i].singleBeds = rs.getInt("singleBeds");
					replyMessage.rooms[i].queenBeds = rs.getInt("queenBeds");
					replyMessage.rooms[i].kingBeds = rs.getInt("kingBeds");
					replyMessage.rooms[i].roomNumber=rs.getInt("roomNumber");
					i++;
		        }
			} catch (SQLException e) {
				System.err.println("Error in Filter Date Booking.  SQLException was thrown:");
				e.printStackTrace(System.err);
				replyMessage.response.fillResponse(ResponseCode.FAIL, "view Booking failed.");
				return replyMessage;
			} catch (ClassNotFoundException e) {
				System.err.println("Error in Filter Date Booking.  ClassNotFoundException was thrown:");
				e.printStackTrace(System.err);
				replyMessage.response.fillResponse(ResponseCode.FAIL, "view Booking failed.");
				return replyMessage;
			}
			finally {
				if (dbcon != null) {
					dbcon.close();
				}
			}
		}
		replyMessage.response.fillResponse(ResponseCode.SUCCESS, "get Filter Booking was successful.");
		return replyMessage;
	}
	public Message checkIn (Message i_msg){
		/*
		 * OVERVIEW: Checks in the user when given a specific booking
		 * PRECONDITIONS: Booking needs to exist and Booking has not been checked in
		 * MODIFIES: Booking status is set to '1' and creates a bill in the bill table in the database with the detail of the booking
		 * POSTCONDITIONS: Status for booking changed to "Checked in" and a bill is created with the given booking information
		 */
		Message output=i_msg;
		databaseHelper dbcon 	= null;
		try {
			dbcon 				= new databaseHelper();
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
			            	output.initializeBills(1);
			            	output.bills[0].bookingID=output.bookings[0].bookingID;
			            	mybill.addBill(output);
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
			output.response.fillResponse(ResponseCode.FAIL, "Checkin failed.");
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Checkin'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output.response.fillResponse(ResponseCode.FAIL, "Checkin failed.");
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return output;
	}
	public Message checkOut (Message i_msg){
		/*
		 * OVERVIEW: Checks out the user given a specific booking
		 * PRECONDITIONS: Booking exist and is checked in, Staff receives payment from customer
		 * MODIFIES: Booking status is changed to '2' and room cleaned is set to '0'
		 * POSTCONDITIONS: Sets the booking status to "Checked out" and edits the bill status to paid
		 */
		// MUST INCLUDE BILL ID, PAYMENT TYPE, BOOKING ID
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		// Not the best way to do it but should be a deep Copy - I will investigate
		//creationTime is not changeable
		replyMessage.bookings=i_msg.bookings;
		try {
			dbcon = new databaseHelper();
			ResultSet rs = dbcon.select("SELECT status "+ i_msg.header.nameHotel + "_bills " +
					"WHERE bookingID='" + i_msg.bookings[0].bookingID +"'");
			int mert=0;
			while (rs.next()) {
				int ma_status=rs.getInt("status");
				if(mert!=0){
					replyMessage.response.fillResponse(ResponseCode.FAIL, "INCONSISTENCY in bills. More than one bill for one booking" +
							" BookingID: " + i_msg.bookings[0].bookingID);
					return replyMessage;
				}
				if(ma_status==0){
					replyMessage.response.fillResponse(ResponseCode.FAIL, "Checkout failed, because bill is not PAID" +
							" BookingID: " + i_msg.bookings[0].bookingID);
					return replyMessage;
				}
				mert++;
			}
			
			int returnedRows = dbcon.update("UPDATE "+ i_msg.header.nameHotel + "_bookings SET status='2'" + 
						" WHERE bookingID='" + i_msg.bookings[0].bookingID +"'");
			if (returnedRows != 1) {
				replyMessage.response.fillResponse(ResponseCode.FAIL, "Editting Booking failed. Because the number of updated is not = 1. INCONSISTENCY" +
						" BookingID: " + i_msg.bookings[0].bookingID);
				return replyMessage;
			} else {
				i_msg = getBooking(i_msg);
				returnedRows  = dbcon.update("UPDATE " + i_msg.header.nameHotel + "_rooms SET cleaned='1' " +
					"WHERE roomID='" + i_msg.bookings[0].roomID + "'");
			}
			returnedRows = dbcon.update("UPDATE "+ i_msg.header.nameHotel + "_rooms SET cleaned='0'" +
					" WHERE roomID='" + i_msg.bookings[0].roomID +"'");
			if (returnedRows != 1) {
				replyMessage.response.fillResponse(ResponseCode.FAIL, "Editting Room failed." +
						" BookingID: " + i_msg.bookings[0].bookingID);
				System.err.println(returnedRows);
				return replyMessage;
			}

		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Editting Booking failed.");
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Editting Booking failed.");
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		replyMessage.response.fillResponse(ResponseCode.SUCCESS, "YOU ARE NOW CHECCKED OUT" +
				" BookingID: " + i_msg.bookings[0].bookingID);
		return replyMessage;
	}
}
