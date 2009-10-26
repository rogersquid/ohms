package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.database.*;
import models.messages.*;
import models.messages.message_Helper.*;

public class registerServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		getServletContext().getRequestDispatcher("/views/register_form.jsp").include(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		String password = request.getParameter("password");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		boolean gender = (request.getParameter("gender") == "1") ? true : false;
		
		int userid = 1;
		int authlevel = 3;
		String hotelname = "default_hotel";
		Account_Message message = new Account_Message(userid, authlevel, hotelname, Header.Action.ADD);
		message.email = email;
		message.firstname = firstname;
		message.lastname = lastname;
		message.phone = phone;
		message.address = address;
		message.gender = gender;
		
		Hotel hotel = new Hotel(hotelname);
		hotel.process_Message(message);
		if(message.header.response_code == Header.Response.SUCCESS) {
			request.setAttribute("firstname", firstname);
			request.setAttribute("lastname", lastname);
			getServletContext().getRequestDispatcher("/views/register_success.jsp").include(request, response);
		} else if(message.header.response_code == Header.Response.FAIL) {
			request.setAttribute("status", "register_failed");
			request.setAttribute("message", message.header.response_string);
			getServletContext().getRequestDispatcher("/views/register_form.jsp").include(request, response);
		}
	}

}
