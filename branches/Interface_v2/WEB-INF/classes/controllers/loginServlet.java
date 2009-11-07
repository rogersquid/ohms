package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.messages.*;
import models.database.*;

public class loginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		getServletContext().getRequestDispatcher("/views/login_form.jsp").include(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		int userid = 0;
		int authlevel = 0;
		String hotelname = "default_hotel";
		AccountMessage message = new AccountMessage(userid, authlevel, hotelname, Header.Action.AUTHENTICATE);
		message.email = email;
		message.password = password;

		Hotel hotel = new Hotel(hotelname);
		AccountMessage reply = (AccountMessage)hotel.processMessage(message);
		Header replyHeader = reply.returnHeader();

		if(replyHeader.response_code == Header.Response.SUCCESS) {
			request.setAttribute("email", email);
			getServletContext().getRequestDispatcher("/views/login_success.jsp").include(request, response);
		} else {
			request.setAttribute("status", "login_failed");
			request.setAttribute("message", replyHeader.response_string);
			getServletContext().getRequestDispatcher("/views/login_form.jsp").include(request, response);
		}
	}

}
