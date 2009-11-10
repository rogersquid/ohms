package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.messages.*;
import models.database.*;
import models.misc.*;

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
		String hotelname = "test";
		AccountMessage message = new AccountMessage(userid, authlevel, hotelname, Header.Action.LOGIN);
		message.email = email;
		message.password = password;

		Hotel hotel = new Hotel(hotelname);
		AccountMessage reply = (AccountMessage)hotel.processMessage(message);
		Header replyHeader = reply.returnHeader();
		

		if(replyHeader.responseCode == Header.Response.SUCCESS) {
			Cookie userCookie = new Cookie("accountID", reply.accountID.toString());
			String md5_password = MD5.hashString(reply.password);
			Cookie passwordCookie = new Cookie("md5_password", md5_password);
			response.addCookie(userCookie);
			
			getServletContext().getRequestDispatcher("/views/login_success.jsp").include(request, response);
		} else {
			request.setAttribute("status", "login_failed");
			request.setAttribute("message", replyHeader.responseString);
			getServletContext().getRequestDispatcher("/views/login_form.jsp").include(request, response);
		}
	}

}
