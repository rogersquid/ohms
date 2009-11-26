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
		} 
		else if(action!=null && action.equals("viewCurrent")){
			viewCurrentAccount(request, response);
		}
		else if(action!=null && action.equals("edit"))
		{
			editAccount(request, response);
		}
		else if(action!=null && action.equals("doEdit"))
		{
			doEditAccount(request, response);
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
	
	public void editAccount(HttpServletRequest request, HttpServletResponse response)
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
		if(authlevel >= 3 || message.accounts[0].accountID==userid) 
		{	
			Message reply = account.getAccount(message);
			
			if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.accounts.length > 0) 
			{
				request.setAttribute("account", reply.accounts[0]);
				getServletContext().getRequestDispatcher("/views/edit_account_form.jsp").include(request, response);
			}
			else 
			{
				request.setAttribute("message", reply.response.responseString);
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		}
		else 
		{
			request.setAttribute("message", "You are not authorized to edit this account.");
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		} 
	}
	
	public void doEditAccount(HttpServletRequest request, HttpServletResponse response)
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
		if(authlevel >= 3 || message.accounts[0].accountID==userid) 
		{	
			if(message.accounts.validateEditParams())
			{
				if(message.response.responseCode == ResponseMessage.ResponseCode.SUCCESS) {

					Message reply = account.editAccount(message);

					if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.accounts.length > 0) 
					{
						request.setAttribute("account", reply.accounts[0]);
						request.setAttribute("status", "edit_account_success");
						getServletContext().getRequestDispatcher("/views/edit_account_success.jsp").include(request, response);
					}
					else 
					{
						request.setAttribute("message", reply.response.responseString);
						getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
					}
				}
				else 
				{
					request.setAttribute("status", "edit_failed");
					request.setAttribute("message", message.response.responseString);
					getServletContext().getRequestDispatcher("/views/edit_account_form.jsp").include(request, response);
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
			request.setAttribute("message", "You are not authorized to edit this account.");
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		// nothing
	}

}
