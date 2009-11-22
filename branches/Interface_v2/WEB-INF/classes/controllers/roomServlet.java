package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.database.*;
import models.messages.*;

public class registerServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		getServletContext().getRequestDispatcher("/views/room_form.jsp").include(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		
		int userid = 6;
		int authlevel = 3;
		String hotelname = "test";
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeRooms(1);
		public int 				message.rooms[0].roomID = request.getParameter("roomID");
		public String 			message.rooms[0].roomType = request.getParameter("roomType");
		public int 				message.rooms[0].roomNumber = request.getParameter("roomNumber");
		public float 			message.rooms[0].price = request.getParameter("price");
		public boolean 			message.rooms[0].available = (request.getParameter("available")).equals("0")) ? false : true;
		public boolean 			message.rooms[0].cleaned = (request.getParameter("cleaned")).equals("0")) ? false : true;
		public int 				message.rooms[0].floor = request.getParameter("floor");
		public boolean			message.rooms[0].tv = (request.getParameter("tv")).equals("0")) ? false : true;
		public int				message.rooms[0].singleBeds = request.getParameter("singleBeds");
		public int 				message.rooms[0].queenBeds = request.getParameter("queenBeds");
		public int				message.rooms[0].kingBeds = request.getParameter("kingBeds");
		public boolean			message.rooms[0].disabilityAccess = (request.getParameter("disabilityAccess")).equals("0")) ? false : true;
		public boolean			message.rooms[0].phone = (request.getParameter("phone")).equals("0")) ? false : true;
		public boolean			message.rooms[0].internet = (request.getParameter("internet")).equals("0")) ? false : true;
		public boolean			message.rooms[0].kitchen = (request.getParameter("kitchen")).equals("0")) ? false : true;
		public boolean			message.rooms[0].onsuite = (request.getParameter("onsuite")).equals("0")) ? false : true;
		public boolean			message.rooms[0].elevator = (request.getParameter("elevator")).equals("0")) ? false : true;
		
		request.setAttribute("room", message.rooms[0]);

		if(message.validate()) {
			if(message.response.responseCode == ResponseMessage.ResponseCode.SUCCESS) {
				Room room = new Room();
				Message reply = account.addRoom(message);

				if(reply.response.responseCode == ResponseMessage.ResponseCode.SUCCESS) {
					getServletContext().getRequestDispatcher("/views/room_add_success.jsp").include(request, response);
				} else {
					request.setAttribute("status", "register_failed");
					request.setAttribute("message", reply.response.responseString);
					getServletContext().getRequestDispatcher("/views/room_form.jsp").include(request, response);
				}
			} else {
				request.setAttribute("status", "register_failed");
				request.setAttribute("message", message.response.responseString);
				getServletContext().getRequestDispatcher("/views/room_form.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", "Message validation failed, all message objects are null.");
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

}
