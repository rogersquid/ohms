package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.*;

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
		Account_Message message = new Account_Message(userid, authlevel, hotelname, Header.Action.LOGIN);
		message.email = email;
		message.password = password;
		
		Hotel hotel = new Hotel(hotelname);
		hotel.process_Message(message);
		
		if(message.header.response_code == Header.Response.SUCCESS) {
			request.setAttribute("email", email);
			getServletContext().getRequestDispatcher("/views/login_success.jsp").include(request, response);
		} else {
			request.setAttribute("status", "login_failed");
			getServletContext().getRequestDispatcher("/views/login_form.jsp").include(request, response);
		}
	}

}
