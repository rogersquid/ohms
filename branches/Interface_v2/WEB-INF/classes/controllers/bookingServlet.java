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
		int userid = 1;
		int authlevel = 3;
		String hotelname = "test";
		BookingMessage message = new BookingMessage(userid, authlevel, hotelname, Header.Action.VIEWALL);

		Hotel hotel = new Hotel(hotelname);
		BookingMessage reply = (BookingMessage)hotel.processMessage(message);
		Header replyHeader = reply.return_Header();

		getServletContext().getRequestDispatcher("/views/bookings.jsp").include(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			java.sql.Date startDate = new java.sql.Date(df.parse(request.getParameter("startDate")).getTime());
			int duration = Integer.parseInt(request.getParameter("duration"));
			int ownerID = Integer.parseInt(request.getParameter("ownerID"));
			int roomID = Integer.parseInt(request.getParameter("roomID"));

			int userID = ownerID;
			int authLevel = 0;
			String hotelname = "test";
			BookingMessage message = new BookingMessage(userID, authLevel, hotelname, Header.Action.ADD);
			message.startDate = startDate;
			message.duration = duration;
			message.ownerID = ownerID;
			message.roomID = roomID;

			Hotel hotel = new Hotel(hotelname);
			BookingMessage reply = (BookingMessage)hotel.processMessage(message);
			Header replyHeader = reply.return_Header();

			if(replyHeader.responseCode == Header.Response.SUCCESS) {
				request.setAttribute("status", "booking_success");
				request.setAttribute("message", "Your booking has been made.");
				request.setAttribute("booking", reply);
				getServletContext().getRequestDispatcher("/views/bookings.jsp").include(request, response);
			} else {
				request.setAttribute("status", "booking_failed");
				request.setAttribute("message", replyHeader.responseString);
				getServletContext().getRequestDispatcher("/views/bookings.jsp").include(request, response);
			}
		} catch(Exception e) {

		}
	}

}
