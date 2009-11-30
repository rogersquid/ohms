package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.database.*;
import models.messages.*;
import models.misc.*;

public class extrasServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		request = InterfaceHelper.initialize(request, response);
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		String action = request.getParameter("action");
		if(action!=null && action.equals("view")) {
			viewExtras(request, response);
		} else if(action!=null && action.equals("edit")) {
			editExtras(request, response);
		} else if(action!=null && action.equals("all_extras")) {
			allExtras(request, response);
		} else if(action!=null && action.equals("booking_extras")) {
			bookingExtras(request, response);
		} else if(action!=null && action.equals("account_extras")) {
			accountExtras(request, response);
		} else if(action!=null && action.equals("delete")) {
			deleteExtras(request, response);
		} else {
			viewCurrentExtras(request, response);
		}
	}

	public void viewExtras(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		int extrasID = Integer.parseInt(request.getParameter("id"));
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeExtras(1);
		message.extras[0].extrasID = extrasID;
		Extras extras = new Extras();
		Message reply = extras.getExtras(message);

		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.extras.length > 0) {
			if(authlevel >= 3 || reply.extras[0].extrasID==userid) {
				request.setAttribute("extras", reply.extras[0]);
				getServletContext().getRequestDispatcher("/views/extras/view_extra.jsp").include(request, response);
			} else {
				request.setAttribute("message", "You are not authorized to view this extra.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void allExtras(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		Message message = new Message(authlevel, userid, hotelname);
		Extras extras = new Extras();
		Message reply = extras.getAllExtras(message);

		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.extras != null) {
			if(authlevel >= 3) {
				request.setAttribute("data", reply);
				getServletContext().getRequestDispatcher("/views/extras/view_extra_table.jsp").include(request, response);
			} else {
				request.setAttribute("message", "You are not authorized to view extras.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void accountExtras(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		Message message = new Message(authlevel, userid, hotelname);
		Extras extras = new Extras();
		AccountMessage amsg = new AccountMessage();
		amsg.accountID = request.getParameter("accountID");
		message.initalizeAccount(1);
		message.accounts = amsg;
		Message reply = extras.getAccountExtras(message);

		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.extras != null) {
			if(authlevel >= 3 || message.accounts[0].accountID==userid) {
				request.setAttribute("data", reply);
				getServletContext().getRequestDispatcher("/views/extras/view_extra_table.jsp").include(request, response);
			} else {
				request.setAttribute("message", "You are not authorized to view extras.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}
	
	public void bookingExtras(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		Message message = new Message(authlevel, userid, hotelname);
		Extras extras = new Extras();
		ExtraMessage emsg = new ExtraMessage();
		emsg.bookingID = request.getParameter("bookingID");
		message.initalizeExtras(1);
		message.extras = emsg;
		Message reply = extras.getBookingExtras(message);

		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.extras != null) {
			if(authlevel >= 3) {
				request.setAttribute("data", reply);
				getServletContext().getRequestDispatcher("/views/extras/view_extra_table.jsp").include(request, response);
			} else {
				request.setAttribute("message", "You are not authorized to view extras.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}
	
	public void deleteExtras(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		int extrasID = Integer.parseInt(request.getParameter("id"));
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeExtras(1);
		message.extras[0].extrasID = extraID;
		Extras extras = new Extras();
		Message reply = extras.deleteExtras(message);

		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS) {
			if(authlevel > 3) {
				request.setAttribute("status", "delete_success");
				request.setAttribute("message", "Extras successfully deleted.");
				allExtras(request, response);
			} else {
				request.setAttribute("message", "You are not authorized to delete this booking.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void editExtras(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		int extrasID = Integer.parseInt(request.getParameter("id"));
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeExtras(1);
		message.extras[0].extrasID = extrasID;
		Extras extras = new Extras();
		if(authlevel >= 3 || message.extras[0].extrasID==userid)
		{
			Message reply = extras.getExtras(message);

			if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.extras.length > 0)
			{
				request.setAttribute("extras", reply.extras[0]);
				getServletContext().getRequestDispatcher("/views/extras/edit_extra_form.jsp").include(request, response);
			}
			else
			{
				request.setAttribute("message", reply.response.responseString);
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		}
		else
		{
			request.setAttribute("message", "You are not authorized to edit this extras.");
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void doEditExtras(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		int extrasID = Integer.parseInt(request.getParameter("id"));
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeExtras(1);
		message.extras[0].extrasID = extrasID;

		if(authlevel > 3) message.extras[0].price = request.getParameter("price");
		// needs work
		message.extras[0].extraName = request.getParameter("extraName");
		message.extras[0].aate = request.getParameter("date");
		message.extras[0].creationTime = request.getParameter("creationTime");
		message.extras[0].bookingID = request.getParameter("bookingID");

		Extras extars = new Extras();
		if(authlevel >= 3 || message.extras[0].extrasID==userid)
		{
			ResponseMessage resp = message.extras[0].validateEditParams();
			if(resp.responseCode == ResponseMessage.ResponseCode.SUCCESS)
			{
					Message reply = extras.editExtras(message);

					if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS)
					{
						response.sendRedirect(response.encodeRedirectURL("extras.html?action=view&id="+extrasID+"&status=edit_success"));
					}
					else
					{
						request.setAttribute("message", reply.response.responseString);
						getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
					}
			}
			else
			{
				request.setAttribute("status", "edit_extras_failed");
				request.setAttribute("message", resp.responseString);
				editExtras(request, response);
			}
		}
		else
		{
			request.setAttribute("message", "You are not authorized to edit this extras.");
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
			doEditExtras(request, response);
		}
	}

}
