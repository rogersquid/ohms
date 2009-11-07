package models.database;

import models.messages.*;

import java.sql.*;
import java.util.Date;

public class Booking {
	public BookingMessage addBooking(BookingMessage i_msg){
		System.out.println("Inside Add booking");
		Header iheader=i_msg.return_Header();
		BookingMessage output=new BookingMessage(iheader.messageOwnerID, iheader.authLevel,iheader.nameHotel, iheader.action);
		databaseHelper dbcon 	= null;
		try {
			dbcon 				= new databaseHelper(iheader.nameHotel);
			java.util.Date today 	= new java.util.Date();
			java.sql.Date now= new java.sql.Date(today.getTime());
			int returnedRows 	= dbcon.modify("INSERT INTO test_bookings (creationDate, startDate, ownerID, duration, roomID, status) " +
					"VALUES ('" 
					+ now + "', '" + i_msg.startDate + "', '" 
					+ i_msg.ownerID + "', '" + i_msg.duration + "', '" 
					+ i_msg.roomID + "', '" + i_msg.status + "')");
			if (returnedRows == 1) {
				System.out.println("Success");
				output.fill_Header_Response(Header.Response.SUCCESS, "Added one Booking as Requested." +
						" OwnerID: " + iheader.messageOwnerID);
			} else {
				System.out.println("Failure");
				output.fill_Header_Response(Header.Response.FAIL, "Adding Booking failed." +
						" StartDate: " + i_msg.startDate);
			}
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			output.fill_Header_Response(Header.Response.FAIL, "Adding Booking failed." +
					" StartDate: " + i_msg.startDate);
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output.fill_Header_Response(Header.Response.FAIL, "Adding Booking failed." +
					" StartDate: " + i_msg.startDate);
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return output;
	}
	public BookingMessage editBooking(BookingMessage i_msg){
		BookingMessage output=i_msg;
		Header iheader=i_msg.return_Header();
		databaseHelper dbcon 	= null;
		try {
			dbcon 				= new databaseHelper(iheader.nameHotel);
			// bookingDate should it be editttable???
			int returnedRows = dbcon.modify("UPDATE test_bookings SET ownerID='" + i_msg.ownerID
					+ "', creationDate='" +i_msg.creationDate + "', startDate='" + i_msg.startDate
					+ "', duration='" + i_msg.duration + "', roomID='" + i_msg.roomID
					+ "', status='" + i_msg.status + "'");
			if (returnedRows == 1) {
				output.fill_Header_Response(Header.Response.SUCCESS, "Edited one Booking as Requested." +
						" StartDate: " + i_msg.startDate);
			} else {
				output.fill_Header_Response(Header.Response.FAIL, "Editting Booking failed." +
						" StartDate: " + i_msg.startDate);
			}
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			output.fill_Header_Response(Header.Response.FAIL, "Editting Booking failed." +
					" StartDate: " + i_msg.startDate);
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output.fill_Header_Response(Header.Response.FAIL, "Editting Booking failed." +
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
		Header iheader=i_msg.return_Header();
		databaseHelper dbcon 	= null;
		try {
			dbcon 				= new databaseHelper(iheader.nameHotel);
			// booking id or owner + sDate
			int returnedRows = dbcon.modify("DELETE FROM test_bookings WHERE bookingID='" + i_msg.bookingID
					+ "'");
			if (returnedRows == 1) {
				output.fill_Header_Response(Header.Response.SUCCESS, "Delete one Booking as Requested." +
						" StartDate: " + i_msg.startDate);
			} else {
				output.fill_Header_Response(Header.Response.FAIL, "Deleting Booking failed." +
						" StartDate: " + i_msg.startDate);
			}
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			output.fill_Header_Response(Header.Response.FAIL, "Deleting Booking failed." +
					" StartDate: " + i_msg.startDate);
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output.fill_Header_Response(Header.Response.FAIL, "Deleting Booking failed." +
					" StartDate: " + i_msg.startDate);
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
		Header iheader=i_msg.return_Header();
		databaseHelper dbcon 	= null;
		try {
			dbcon 				= new databaseHelper(iheader.nameHotel);
			// booking id or owner + sDate
			ResultSet rs=dbcon.select("Select count(*) FROM test_bookings WHERE bookingID='" 
					+ i_msg.bookingID +"'");
			int i=rs.getInt(1);
			if(i!=1){
				output.fill_Header_Response(Header.Response.FAIL, "view Booking failed." +
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
	            int cduration = rs.getInt("duration");
	            int croomID = rs.getInt("roomID");
	            int cstatus = rs.getInt("status");
	            output.fillAll(cbookingID, cownerID, creationDate, cstartDate, cduration, croomID, cstatus);
	        }
			output.fill_Header_Response(Header.Response.SUCCESS, "View one Booking as Requested." +
						" StartDate: " + i_msg.startDate);
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			output.fill_Header_Response(Header.Response.FAIL, "view Booking failed." +
					" StartDate: " + i_msg.startDate);
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output.fill_Header_Response(Header.Response.FAIL, "view Booking failed." +
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
		BookingMessage[] output= null;
		Header iheader=i_msg.return_Header();
		databaseHelper dbcon 	= null;
		// for customer
		try {
			dbcon 				= new databaseHelper(iheader.nameHotel);
			// booking id or owner + sDate
			ResultSet rs=dbcon.select("Select count(*) FROM " + iheader.nameHotel + 
									"_bookings WHERE bookingID='" + i_msg.bookingID +"'");
			int numberofrows=rs.getInt(1);
			if(numberofrows<0){
				output[0].fill_Header_Response(Header.Response.FAIL, "viewAll Booking failed." +
						" OwnerID: " + i_msg.ownerID);
				return output;
			}
			else if (numberofrows==0){
				output[0].fill_Header_Response(Header.Response.SUCCESS, "There is no booking." +
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
				output[i].duration= rs.getInt("duration");
				output[i].roomID= rs.getInt("roomID");
				output[i].status = rs.getInt("status");
				output[i].fill_Header_Response(Header.Response.SUCCESS, "ViewAll one Booking as Requested." +
						" StartDate: " + i_msg.startDate);
				i++;
	        }
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			output[0].fill_Header_Response(Header.Response.FAIL, "view Booking failed." +
					" StartDate: " + i_msg.startDate);
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output[0].fill_Header_Response(Header.Response.FAIL, "view Booking failed." +
					" StartDate: " + i_msg.startDate);
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return output;
	}
}
