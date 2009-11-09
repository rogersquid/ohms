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

		BookingMessage message 	= new BookingMessage(userid, authlevel, hotelname, Header.Action.VIEWALL);
		Hotel hotel 			= new Hotel(hotelname);
		BookingMessage reply[] 	= (BookingMessage[])hotel.processMessageReturnBunch(message);
		if(reply.length > 0) {
			request.setAttribute("bookingsArray", reply);
			getServletContext().getRequestDispatcher("/views/bookings.jsp").include(request, response);
		} else {
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

			int userID 				= ownerID;
			int authLevel 			= 0;
			String hotelname 		= "test";
			BookingMessage message 	= new BookingMessage(userID, authLevel, hotelname, Header.Action.ADD);
			message.startDate 		= startDate;
			message.endDate 		= endDate;
			message.ownerID 		= ownerID;
			message.roomID 			= roomID;

			Hotel hotel 			= new Hotel(hotelname);
			BookingMessage reply 	= (BookingMessage)hotel.processMessage(message);
			Header replyHeader 		= reply.returnHeader();

			if(replyHeader.responseCode == Header.Response.SUCCESS) {
				request.setAttribute("status", "booking_success");
				request.setAttribute("message", "Your booking has been made.");
				doGet(request, response);
				//response.sendRedirect(response.encodeRedirectURL("bookings.html"));
				//getServletContext().getRequestDispatcher("/views/bookings.jsp").include(request, response);
			} else {
				request.setAttribute("status", "booking_failed");
				request.setAttribute("message", replyHeader.responseString);
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} catch(Exception e) {
			request.setAttribute("message", "Exception: "+e.toString());
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

}
