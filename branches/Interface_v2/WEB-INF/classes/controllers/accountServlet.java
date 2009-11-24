package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.database.*;
import models.messages.*;
import models.misc.*;

public class accountServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		request = InterfaceHelper.initialize(request, response);
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");
		
		String action = request.getParameter("action");	
		if(action!=null && action.equals("view")) {
			viewAccount(request, response);
		} else {
			viewCurrentAccount(request, response);
		}
	}

	public void viewAccount(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");
		
		int accountID = Integer.parseInt(request.getParameter("id"));
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeAccounts(1);
		message.accounts[0].accountID = accountID;
		Account account = new Account();
		Message reply = account.getAccount(message);
		
		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.accounts.length > 0) {
			if(authlevel >= 3 || reply.accounts[0].accountID==userid) {
				request.setAttribute("account", reply.accounts[0]);
				getServletContext().getRequestDispatcher("/views/view_account.jsp").include(request, response);
			} else {
				request.setAttribute("message", "You are not authorized to view this account.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}
	
	public void viewCurrentAccount(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");
		
		int accountID = userid;
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeAccounts(1);
		message.accounts[0].accountID = accountID;
		Account account = new Account();
		Message reply = account.getAccount(message);
		
		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.accounts.length > 0) {
			if(authlevel >= 3 || reply.accounts[0].accountID==userid) {
				request.setAttribute("account", reply.accounts[0]);
				getServletContext().getRequestDispatcher("/views/view_account.jsp").include(request, response);
			} else {
				request.setAttribute("message", "You are not authorized to view this account.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		// nothing
	}

}
