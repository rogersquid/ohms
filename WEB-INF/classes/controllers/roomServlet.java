package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.database.*;
import models.messages.*;
import models.misc.*;

public class roomServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		request = InterfaceHelper.initialize(request, response);
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		String action = (request.getParameter("action")==null) ? "" : request.getParameter("action");

		if(action.equals("all_rooms")) {
			allRooms(request, response);
		} else if(action.equals("add")) {
			//addRoom(request, response);
			getServletContext().getRequestDispatcher("/views/room/create_room_form.jsp").include(request, response);
		} else if(action.equals("edit")) {
			editRoom(request, response);
		} else if(action.equals("view")) {
			viewRoom(request, response);
		} else if(action.equals("clean")) {
			cleanRoom(request, response);
		} else if(action.equals("delete")) {
			//deleteRoom(request, response);
		} else if(action.equals("search")) {
			//getServletContext().getRequestDispatcher("/views/search_rooms.jsp").include(request, response);
		} else { // defaults
			if(authlevel >= 3) {
				//allRooms(request, response);
			} else {
				request.setAttribute("message", "Invalid page");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
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
		if(action.equals("add")) {
			addRoom(request, response);
		} else if(action.equals("edit")) {
			doEditRoom(request, response);
		}
	}

	public void viewRoom(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		int roomID = Integer.parseInt(request.getParameter("id"));
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeRooms(1);
		message.rooms[0].roomID = roomID;
		Room room = new Room();
		Message reply = room.getRoom(message);

		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.rooms.length > 0) {
			request.setAttribute("room", reply.rooms[0]);
			getServletContext().getRequestDispatcher("/views/room/room.jsp").include(request, response);
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void cleanRoom(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		int roomID = Integer.parseInt(request.getParameter("id"));
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeRooms(1);
		message.rooms[0].roomID = roomID;
		Room room = new Room();

		Message roomReply = room.getRoom(message);

		if(roomReply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && roomReply.rooms.length > 0) {
			message.rooms[0].cleaned=true;
			Message reply = room.editRoom(message);
			if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS) {
				response.sendRedirect(response.encodeRedirectURL("room.html?action=view&id="+roomID+"&status=cleaned"));
			} else {
				response.sendRedirect(response.encodeRedirectURL("room.html?action=view&id="+roomID+"&status=clean_failed"));
			}
		} else {
			request.setAttribute("message", roomReply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void allRooms(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		Message message = new Message(authlevel, userid, hotelname);
		Room room = new Room();
		Message reply = room.getAllRooms(message);

		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.rooms != null) {
			if(authlevel >= 3) {
				request.setAttribute("data", reply);
				getServletContext().getRequestDispatcher("/views/room/all_rooms.jsp").include(request, response);
			} else {
				request.setAttribute("message", "You are not authorized to view rooms.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void addRoom(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		request = InterfaceHelper.initialize(request, response);
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		Message message = new Message(authlevel, userid, hotelname);
		message.initializeRooms(1);

		message.rooms[0].roomType = request.getParameter("roomType");
		message.rooms[0].roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
		message.rooms[0].price = Float.parseFloat(request.getParameter("price"));
		message.rooms[0].floor = Integer.parseInt(request.getParameter("floor"));
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

		request.setAttribute("room", message.rooms[0]);

		if(message.validate()) {
			if(message.response.responseCode == ResponseMessage.ResponseCode.SUCCESS) {
				Room room = new Room();
				Message reply = room.addRoom(message);

				if(reply.response.responseCode == ResponseMessage.ResponseCode.SUCCESS) {
					//viewRoom(request, response);
					response.sendRedirect(response.encodeRedirectURL("room.html?action=view&id="+reply.rooms[0].roomID+"&status=add_success"));
				} else {
					request.setAttribute("status", "room_failed");
					request.setAttribute("message", "Cannot insert into DB. "+reply.response.responseString);
					getServletContext().getRequestDispatcher("/views/room/create_room_form.jsp").include(request, response);
				}
			} else {
				request.setAttribute("status", "validation_failed");
				request.setAttribute("message", message.response.responseString);
				getServletContext().getRequestDispatcher("/views/room/create_room_form.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", "Message validation failed, all message objects are null.");
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void editRoom(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		int roomID = Integer.parseInt(request.getParameter("id"));
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeRooms(1);
		message.rooms[0].roomID = roomID;
		Room room = new Room();
		if(authlevel >= 3 )
		{
			Message reply = room.getRoom(message);

			if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.rooms.length > 0)
			{
				request.setAttribute("room", reply.rooms[0]);
				getServletContext().getRequestDispatcher("/views/room/edit_room_form.jsp").include(request, response);
			}
			else
			{
				request.setAttribute("message", reply.response.responseString);
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		}
		else
		{
			request.setAttribute("message", "You are not authorized to edit this rooms.");
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void doEditRoom(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		int roomID = Integer.parseInt(request.getParameter("id"));
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeRooms(1);
		message.rooms[0].roomID = roomID;

		message.rooms[0].roomType = request.getParameter("roomType");
		message.rooms[0].roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
		message.rooms[0].price = Float.parseFloat(request.getParameter("price"));
		message.rooms[0].floor = Integer.parseInt(request.getParameter("floor"));
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

		Room room = new Room();
		if(authlevel >= 3 )
		{
			if(message.validate())
			{
				if(message.response.responseCode == ResponseMessage.ResponseCode.SUCCESS)
				{
					Message reply = room.editRoom(message);

					if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS)
					{
						response.sendRedirect(response.encodeRedirectURL("room.html?action=view&id="+roomID+"&status=edit_success"));
					}
					else
					{
						request.setAttribute("message", reply.response.responseString);
						getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
					}
				}
				else
				{
					request.setAttribute("status", "edit_room_failed");
					request.setAttribute("message", message.response.responseString);
					editRoom(request, response);
				}
			}
			else
			{
				request.setAttribute("message", "Message validation failed, all message objects are null.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		}
		else
		{
			request.setAttribute("message", "You are not authorized to edit this room.");
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}
}
