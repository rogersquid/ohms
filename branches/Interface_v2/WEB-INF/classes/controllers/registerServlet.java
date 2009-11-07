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
		String hotelname = "test";
		AccountMessage message = new AccountMessage(userid, authlevel, hotelname, Header.Action.ADD);
		message.email = email;
		message.password = password;
		message.firstname = firstname;
		message.lastname = lastname;
		message.phone = phone;
		message.address = address;
		message.gender = gender;

		Hotel hotel = new Hotel(hotelname);
		AccountMessage reply = (AccountMessage)hotel.processMessage(message);
		Header replyHeader = reply.returnHeader();

		if(replyHeader.response_code == Header.Response.SUCCESS) {
			request.setAttribute("firstname", firstname);
			request.setAttribute("lastname", lastname);
			getServletContext().getRequestDispatcher("/views/register_success.jsp").include(request, response);
		} else {
			request.setAttribute("status", "register_failed");
			request.setAttribute("message", replyHeader.response_string);
			getServletContext().getRequestDispatcher("/views/register_form.jsp").include(request, response);
		}
	}

}
