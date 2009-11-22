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
		String re_password = request.getParameter("re_password");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		boolean gender = (request.getParameter("gender").equals("0")) ? false : true;

		int userid = 6;
		int authlevel = 3;
		String hotelname = "test";
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeAccounts(1);
		message.accounts[0].email = email;
		message.accounts[0].password = password;
		message.accounts[0].re_password = re_password;
		message.accounts[0].firstName = firstname;
		message.accounts[0].lastName = lastname;
		message.accounts[0].phone = phone;
		message.accounts[0].address = address;
		message.accounts[0].gender = gender;

		request.setAttribute("account", message.accounts[0]);

		if(message.validate()) {
			if(message.response.responseCode == ResponseMessage.ResponseCode.SUCCESS) {
				Account account = new Account();
				Message reply = account.addAccount(message);

				if(reply.response.responseCode == ResponseMessage.ResponseCode.SUCCESS) {
					getServletContext().getRequestDispatcher("/views/register_success.jsp").include(request, response);
				} else {
					request.setAttribute("status", "register_failed");
					request.setAttribute("message", reply.response.responseString);
					getServletContext().getRequestDispatcher("/views/register_form.jsp").include(request, response);
				}
			} else {
				request.setAttribute("status", "register_failed");
				request.setAttribute("message", message.response.responseString);
				getServletContext().getRequestDispatcher("/views/register_form.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", "Message validation failed, all message objects are null.");
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

}
