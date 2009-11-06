package models.database;

import models.messages.*;

import java.sql.*;
import java.util.Date;

public class Booking {
	public BookingMessage addBooking(BookingMessage i_msg){
		BookingMessage output=i_msg;
		Header iheader=i_msg.return_Header();
		databaseHelper dbcon 	= null;
		try {
			dbcon 				= new databaseHelper(iheader.name_hotel);
			java.util.Date today 	= new java.util.Date();
			java.sql.Date now= new java.sql.Date(today.getTime());
			int returnedRows 	= dbcon.modify("INSERT INTO booking (date, startDate, owner_id, duration, room_id, status) VALUES ('" 
					+ now
					+ "', '" + i_msg.startDate + "', '" 
					+ i_msg.ownerID + "', '" + i_msg.duration
					+ "' + '" + i_msg.roomID + "', '" + i_msg.status + "')");
			if (returnedRows == 1) {
				output.fill_Header_Response(Header.Response.SUCCESS, "Added one Booking as Requested." +
						" StartDate: " + i_msg.startDate);
			} else {
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
			dbcon 				= new databaseHelper(iheader.name_hotel);
			// bookingDate should it be editttable???
			int returnedRows = dbcon.modify("UPDATE booking SET ownerID='" + i_msg.ownerID
					+ "', bookingDate='" +i_msg.bookingDate + "', startDate='" + i_msg.startDate
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
			dbcon 				= new databaseHelper(iheader.name_hotel);
			// booking id or owner + sDate
			int returnedRows = dbcon.modify("DELETE FROM booking WHERE bookingID='" + i_msg.bookingID
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
			dbcon 				= new databaseHelper(iheader.name_hotel);
			// booking id or owner + sDate
			ResultSet rs=dbcon.select("Select count(*) FROM booking WHERE ownerID='" + i_msg.ownerID 
					+ " AND bookingDate='" + i_msg.bookingDate +"'");
			int i=rs.getInt(1);
			if(i!=1){
				output.fill_Header_Response(Header.Response.FAIL, "view Booking failed." +
						" StartDate: " + i_msg.startDate);
				return output;
			}
			rs=dbcon.select("Select * FROM booking WHERE ownerID='" + i_msg.ownerID 
					+ " AND bookingDate='" + i_msg.bookingDate +"'");
			while (rs.next()) {
	            int cbookingID = rs.getInt("bookingID");
	            int cownerID = rs.getInt("ownerId");
	            Date cbookingDate = rs.getDate("bookingDate");
	            Date cstartDate = rs.getDate("startDate");
	            int cduration = rs.getInt("duration");
	            int croomID = rs.getInt("roomID");
	            int cstatus = rs.getInt("status");
	            output.fillAll(cbookingID, cownerID, cbookingDate, cstartDate, cduration, croomID, cstatus);
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
	public BookingMessage[] viewAllBooking(){
		BookingMessage[] output= null;
		return output;
	}
}
