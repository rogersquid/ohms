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
		int userid 				= 6;
		int authlevel 			= 3;
		String hotelname 		= "test";

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

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		try {
			DateFormat df 	= new SimpleDateFormat("dd/MM/yyyy");
			java.sql.Date startDate = new java.sql.Date(df.parse(request.getParameter("startDate")).getTime());
			java.sql.Date endDate = new java.sql.Date(df.parse(request.getParameter("endDate")).getTime());
			int ownerID 	= Integer.parseInt(request.getParameter("ownerID"));
			int roomID 		= Integer.parseInt(request.getParameter("roomID"));

			int userID = ownerID;
			int authLevel = 0;
			String hotelname = "test";
			Message message = new Message(authLevel, userID, hotelname);
			message.initializeBookings(1);
			message.bookings[0].startDate = startDate;
			message.bookings[0].endDate = endDate;
			message.bookings[0].ownerID = ownerID;
			message.bookings[0].roomID = roomID;

			Booking booking = new Booking();
			Message reply = booking.addBooking(message);

			if(reply.response.responseCode == ResponseMessage.ResponseCode.SUCCESS) {
				request.setAttribute("status", "booking_success");
				request.setAttribute("message", "Your booking has been made.");
				doGet(request, response);
				//response.sendRedirect(response.encodeRedirectURL("bookings.html"));
				//getServletContext().getRequestDispatcher("/views/bookings.jsp").include(request, response);
			} else {
				request.setAttribute("status", "booking_failed");
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
