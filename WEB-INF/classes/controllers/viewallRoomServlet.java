package controllers;

import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.database.*;
import models.messages.*;

public class viewallRoomServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int userid 				= 1;
		int authlevel 			= 3;
		String hotelname 		= "test";
		BookingMessage message 	= new RoomMessage(userid, authlevel, hotelname, Header.Action.VIEWALL);

		Hotel hotel 			= new Hotel(hotelname);
		BookingMessage[] reply 	= (RoomMessage)hotel.processMessageReturnBunch(message);
		Header replyHeader 		= reply.return_Header();
		
		request.setAttribute("roomsArray", 		reply);
		getServletContext().getRequestDispatcher("/views/viewall_room.jsp").include(request, response);
	}

}
