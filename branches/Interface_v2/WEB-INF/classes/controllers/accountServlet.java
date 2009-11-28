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
		} else if(action!=null && action.equals("edit")) {
			editAccount(request, response);
		} else if(action!=null && action.equals("all_accounts")) {
			allAccounts(request, response);
		} else if(action!=null && action.equals("delete")) {
			deleteAccount(request, response);
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
				getServletContext().getRequestDispatcher("/views/account/view_account.jsp").include(request, response);
			} else {
				request.setAttribute("message", "You are not authorized to view this account.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void allAccounts(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		Message message = new Message(authlevel, userid, hotelname);
		Account account = new Account();
		Message reply = account.getAllAccounts(message);

		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.accounts != null) {
			if(authlevel >= 3) {
				request.setAttribute("data", reply);
				getServletContext().getRequestDispatcher("/views/account/all_accounts.jsp").include(request, response);
			} else {
				request.setAttribute("message", "You are not authorized to view accounts.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void deleteAccount(HttpServletRequest request, HttpServletResponse response)
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
		Message reply = account.deleteAccount(message);

		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS) {
			if(authlevel > 3) {
				request.setAttribute("status", "delete_success");
				request.setAttribute("message", "Account successfully deleted.");
				allAccounts(request, response);
			} else {
				request.setAttribute("message", "You are not authorized to delete this booking.");
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
				getServletContext().getRequestDispatcher("/views/account/view_account.jsp").include(request, response);
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
				getServletContext().getRequestDispatcher("/views/account/edit_account_form.jsp").include(request, response);
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

		if(authlevel > 3) message.accounts[0].accountType = request.getParameter("accountType");
		message.accounts[0].password = request.getParameter("password");
		message.accounts[0].re_password = request.getParameter("re_password");
		message.accounts[0].firstName = request.getParameter("firstName");
		message.accounts[0].lastName = request.getParameter("lastName");
		message.accounts[0].phone = request.getParameter("phone");
		message.accounts[0].address = request.getParameter("address");
		message.accounts[0].gender = (request.getParameter("gender").equals("0")) ? false : true;

		Account account = new Account();
		if(authlevel >= 3 || message.accounts[0].accountID==userid)
		{
			ResponseMessage resp = message.accounts[0].validateEditParams();
			if(resp.responseCode == ResponseMessage.ResponseCode.SUCCESS)
			{
					Message reply = account.editAccount(message);

					if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS)
					{
						response.sendRedirect(response.encodeRedirectURL("account.html?action=view&id="+accountID+"&status=edit_success"));
					}
					else
					{
						request.setAttribute("message", reply.response.responseString);
						getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
					}
			}
			else
			{
				request.setAttribute("status", "edit_account_failed");
				request.setAttribute("message", resp.responseString);
				editAccount(request, response);
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
		request = InterfaceHelper.initialize(request, response);
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		String action = request.getParameter("action");
		if(action!=null && action.equals("edit"))
		{
			doEditAccount(request, response);
		}
	}

}
