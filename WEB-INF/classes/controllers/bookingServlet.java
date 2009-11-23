package controllers;

import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.database.*;
import models.messages.*;

public class bookingServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int userid = 125;
		int authlevel = 3;
		String hotelname = "test";
		
		request.setAttribute("userID", userid);
		request.setAttribute("authLevel", authlevel);
		request.setAttribute("hotelName", hotelname);
		
		String action = request.getParameter("action");
		
		if(action.equals("all_bookings")) {
			allBookings(request, response);
		} else if(action.equals("my_bookings")) {
			myBookings(request, response);
		} else if(action.equals("view")) {
			viewBooking(request, response);
		} else if(action.equals("delete")) {
			deleteBooking(request, response);
		} else if(action.equals("search")) {
			getServletContext().getRequestDispatcher("/views/search_rooms.jsp").include(request, response);
		} else { // defaults
			if(authlevel < 3) {
				// my bookings
				myBookings(request, response);
			} else {
				// all bookings
				allBookings(request, response);
			}
		}
		
	}
	
	public void viewBooking(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");
		
		int bookingID = Integer.parseInt(request.getParameter("id"));
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeBookings(1);
		message.bookings[0].bookingID = bookingID;
		Booking booking = new Booking();
		Message reply = booking.getBooking(message);
		
		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.bookings.length > 0) {
			if(authlevel >= 3 || reply.bookings[0].ownerID==userid) {
				request.setAttribute("data", reply);
				getServletContext().getRequestDispatcher("/views/view_booking.jsp").include(request, response);
			} else {
				request.setAttribute("message", "You are not authorized to view this booking.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}
	
	public void deleteBooking(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");
		
		int bookingID = Integer.parseInt(request.getParameter("id"));
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeBookings(1);
		message.bookings[0].bookingID = bookingID;
		Booking booking = new Booking();
		Message reply = booking.deleteBooking(message);
		
		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS) {
			if(authlevel >= 3 || reply.bookings[0].ownerID==userid) {
				request.setAttribute("status", "booking_success");
				request.setAttribute("message", "Booking successfully deleted.");
				myBookings(request, response);
			} else {
				request.setAttribute("message", "You are not authorized to delete this booking.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}
	
	public void allBookings(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");
		
		if(authlevel < 3) {
			request.setAttribute("message", "Only staff has access to the list of all bookings.");
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		} else {
			Message message = new Message(authlevel, userid, hotelname);
			Booking booking = new Booking();
			Message reply = booking.getAllBooking(message);
			
			if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.bookings.length > 0) {
				request.setAttribute("data", reply);
				getServletContext().getRequestDispatcher("/views/bookings.jsp").include(request, response);
			} else {
				request.setAttribute("message", reply.response.responseString);
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		}
	}
	
	public void myBookings(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");
		
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeBookings(1);
		message.bookings[0].ownerID = userid;
		Booking booking = new Booking();
		Message reply = booking.getFilteredBooking(message);
		
		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.bookings.length > 0) {
			request.setAttribute("data", reply);
			getServletContext().getRequestDispatcher("/views/my_bookings.jsp").include(request, response);
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int userid = 125;
		int authlevel = 3;
		String hotelname = "test";
		
		request.setAttribute("userID", userid);
		request.setAttribute("authLevel", authlevel);
		request.setAttribute("hotelName", hotelname);
		
		String action = request.getParameter("action");
		if(action.equals("search")) {
			searchRooms(request, response);
		} else if(action.equals("confirm_booking")) {
			confirmBooking(request, response);
		}
	}
	
	public void searchRooms(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");
		
		try {
			DateFormat df 	= new SimpleDateFormat("dd/MM/yyyy");
			java.sql.Date startDate = new java.sql.Date(df.parse(request.getParameter("startDate")).getTime());
			java.sql.Date endDate = new java.sql.Date(df.parse(request.getParameter("endDate")).getTime());
			
			Message message = new Message(authlevel, userid, hotelname);
			message.initializeBookings(1);
			message.bookings[0].startDate = startDate;
			message.bookings[0].endDate = endDate;
			
			Booking booking = new Booking();
			Message reply = booking.getFilteredBooking(message);
			
			if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS) {
				if(reply.rooms==null) {
					request.setAttribute("status", "search_failed");
					request.setAttribute("message", "No available rooms found. Please try different dates.");
					getServletContext().getRequestDispatcher("/views/search_rooms.jsp").include(request, response);
				} else {
					request.setAttribute("data", reply);
					getServletContext().getRequestDispatcher("/views/available_rooms.jsp").include(request, response);		}
			} else {
				request.setAttribute("message", reply.response.responseString);
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} catch(Exception e) {
			request.setAttribute("message", "Exception: "+e.toString());
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}
	
	public void confirmBooking(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");
		
		try {
			DateFormat df 	= new SimpleDateFormat("dd/MM/yyyy");
			java.sql.Date startDate = new java.sql.Date(df.parse(request.getParameter("startDate")).getTime());
			java.sql.Date endDate = new java.sql.Date(df.parse(request.getParameter("endDate")).getTime());
			int bookingOwnerID = userid;
			int roomID = Integer.parseInt(request.getParameter("roomID"));
			
			Message message = new Message(authlevel, userid, hotelname);
			message.initializeBookings(1);
			message.bookings[0].startDate = startDate;
			message.bookings[0].endDate = endDate;
			message.bookings[0].ownerID = bookingOwnerID;
			message.bookings[0].roomID = roomID;
			
			Booking booking = new Booking();
			Message reply = booking.addBooking(message);
			
			if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS) {
				request.setAttribute("status", "booking_success");
				request.setAttribute("message", "Your booking has been made.");
				myBookings(request, response);
			} else {
				request.setAttribute("message", reply.response.responseString);
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} catch(Exception e) {
			request.setAttribute("message", "Exception: "+e.toString());
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}
}
