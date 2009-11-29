package controllers;

import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.database.*;
import models.messages.*;
import models.misc.*;

public class bookingServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		request = InterfaceHelper.initialize(request, response);
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		String action = (request.getParameter("action")==null) ? "" : request.getParameter("action");

		if(action.equals("all_bookings")) {
			allBookings(request, response);
		} else if(action.equals("my_bookings")) {
			myBookings(request, response);
		} else if(action.equals("view")) {
			viewBooking(request, response);
		} else if(action.equals("checkin")) {
			checkIn(request, response);
		} else if(action.equals("checkout")) {
			checkOut(request, response);
		} else if(action.equals("delete")) {
			deleteBooking(request, response);
		} else if(action.equals("search")) {
			getServletContext().getRequestDispatcher("/views/room/search_rooms.jsp").include(request, response);
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
				getServletContext().getRequestDispatcher("/views/booking/view_booking.jsp").include(request, response);
			} else {
				request.setAttribute("message", "You are not authorized to view this booking.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void checkIn(HttpServletRequest request, HttpServletResponse response)
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
		Message bookingReply = booking.getBooking(message);

		if(bookingReply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && bookingReply.bookings.length > 0) {
			if(authlevel >= 3 || bookingReply.bookings[0].ownerID==userid) {
				Message checkInReply = booking.checkIn(message);
				if(checkInReply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS) {
					response.sendRedirect(response.encodeRedirectURL("bookings.html?action=view&id="+bookingID+"&status=checkin"));
				} else {
					request.setAttribute("message", "Check in falied: "+checkInReply.response.responseString);
					getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
				}
			} else {
				request.setAttribute("message", "You are not authorized to check in.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", bookingReply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}
	public void checkOut(HttpServletRequest request, HttpServletResponse response)
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
		Message bookingReply = booking.getBooking(message);

		if(bookingReply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && bookingReply.bookings.length > 0) {
			if(authlevel >= 3 || bookingReply.bookings[0].ownerID==userid) {
				Message checkInReply = booking.checkOut(message);
				if(checkInReply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS) {
					response.sendRedirect(response.encodeRedirectURL("bookings.html?action=view&id="+bookingID+"&status=checkin"));
				} else {
					request.setAttribute("message", "Check in falied: "+checkInReply.response.responseString);
					getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
				}
			} else {
				request.setAttribute("message", "You are not authorized to check in.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", bookingReply.response.responseString);
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

			if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.bookings!=null) {
				request.setAttribute("data", reply);
				getServletContext().getRequestDispatcher("/views/booking/bookings.jsp").include(request, response);
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

		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.bookings!=null) {
			request.setAttribute("data", reply);
			getServletContext().getRequestDispatcher("/views/booking/my_bookings.jsp").include(request, response);
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		request = InterfaceHelper.initialize(request, response);
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		String action = request.getParameter("action");
		if(action.equals("search")) {
			searchRooms(request, response);
		} else if(action.equals("confirm_booking")) {
			confirmBooking(request, response);
		} else if(action.equals("add_booking")) {
			addBooking(request, response);
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
			
			if(!endDate.after(startDate)) {
				request.setAttribute("message", "The start date must be before the end date.");
				request.setAttribute("status", "search_failed");
				getServletContext().getRequestDispatcher("/views/room/search_rooms.jsp").include(request, response);
			} else {

				Message message = new Message(authlevel, userid, hotelname);
				message.initializeBookings(1);
				message.initializeRooms(2);
				message.bookings[0].startDate = startDate;
				message.bookings[0].endDate = endDate;


				// set what to search for first
				message.rooms[0].singleBeds = Integer.parseInt(request.getParameter("singleBeds"));
				message.rooms[0].queenBeds = Integer.parseInt(request.getParameter("queenBeds"));
				message.rooms[0].kingBeds = Integer.parseInt(request.getParameter("kingBeds"));
				message.rooms[0].tv = (request.getParameter("tv")!=null && request.getParameter("tv").equals("1")) ? true : false;
				message.rooms[0].available = (request.getParameter("available")!=null && request.getParameter("available").equals("1")) ? true : false;
				message.rooms[0].cleaned = (request.getParameter("cleaned")!=null && request.getParameter("cleaned").equals("1")) ? true : false;
				message.rooms[0].disabilityAccess = (request.getParameter("disabilityAccess")!=null && request.getParameter("disabilityAccess").equals("1")) ? true : false;
				message.rooms[0].phone = (request.getParameter("phone")!=null && request.getParameter("phone").equals("1")) ? true : false;
				message.rooms[0].internet = (request.getParameter("interneg")!=null && request.getParameter("internet").equals("1")) ? true : false;
				message.rooms[0].kitchen = (request.getParameter("kitchen")!=null && request.getParameter("kitchen").equals("1")) ? true : false;
				message.rooms[0].onsuite = (request.getParameter("onsuite")!=null && request.getParameter("onsuite").equals("1")) ? true : false;
				message.rooms[0].elevator = (request.getParameter("elevator")!=null && request.getParameter("elevator").equals("1")) ? true : false;


				// set values to search for
				message.rooms[1].singleBeds = Integer.parseInt(request.getParameter("singleBeds"));
				message.rooms[1].queenBeds = Integer.parseInt(request.getParameter("queenBeds"));
				message.rooms[1].kingBeds = Integer.parseInt(request.getParameter("kingBeds"));
				message.rooms[1].tv = (request.getParameter("tv")!=null && request.getParameter("tv").equals("1")) ? true : false;
				message.rooms[1].available = (request.getParameter("available")!=null && request.getParameter("available").equals("1")) ? true : false;
				message.rooms[1].cleaned = (request.getParameter("cleaned")!=null && request.getParameter("cleaned").equals("1")) ? true : false;
				message.rooms[1].disabilityAccess = (request.getParameter("disabilityAccess")!=null && request.getParameter("disabilityAccess").equals("1")) ? true : false;
				message.rooms[1].phone = (request.getParameter("phone")!=null && request.getParameter("phone").equals("1")) ? true : false;
				message.rooms[1].internet = (request.getParameter("interneg")!=null && request.getParameter("internet").equals("1")) ? true : false;
				message.rooms[1].kitchen = (request.getParameter("kitchen")!=null && request.getParameter("kitchen").equals("1")) ? true : false;
				message.rooms[1].onsuite = (request.getParameter("onsuite")!=null && request.getParameter("onsuite").equals("1")) ? true : false;
				message.rooms[1].elevator = (request.getParameter("elevator")!=null && request.getParameter("elevator").equals("1")) ? true : false;

				//Booking booking = new Booking();
				//Message reply = booking.getFilteredBooking(message);
				Room room = new Room();
				Message reply = room.getFilteredRooms(message);

				if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS) {
					if(reply.rooms==null) {
						request.setAttribute("status", "search_failed");
						request.setAttribute("message", "No available rooms found. Please try different dates.");
						getServletContext().getRequestDispatcher("/views/room/search_rooms.jsp").include(request, response);
					} else {
						request.setAttribute("data", reply);
						getServletContext().getRequestDispatcher("/views/room/available_rooms.jsp").include(request, response);		}
				} else {
					request.setAttribute("message", reply.response.responseString);
					getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
				}
			}
		} catch(ParseException e) {
			request.setAttribute("status", "search_failed");
			request.setAttribute("message", "Invalid date formats.");
			getServletContext().getRequestDispatcher("/views/room/search_rooms.jsp").include(request,
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

		getServletContext().getRequestDispatcher("/views/booking/confirm_booking.jsp").include(request, response);
	}

	public void addBooking(HttpServletRequest request, HttpServletResponse response)
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
