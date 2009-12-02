package controllers;

import java.io.*;
import java.util.*;
import java.text.*;
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
		} else if(action!=null && action.equals("add")){
			addExtra(request, response);
		}
		else{
			//viewCurrentExtras(request, response);
		}
	}

	public void viewExtras(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		int extraID = Integer.parseInt(request.getParameter("id"));
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeExtras(1);
		message.extras[0].extraID = extraID;
		Extra extras = new Extra();
		Message reply = extras.getExtra(message);

		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.extras.length > 0) {
			if(authlevel >= 3 || reply.accounts[0].accountID==userid) {
				request.setAttribute("extra", reply.extras[0]);
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
		Extra extras = new Extra();
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
		Extra extras = new Extra();
		message.initializeAccounts(1);
		message.accounts[0].accountID = Integer.parseInt(request.getParameter("id"));
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

		Message bookingMessage = new Message(authlevel, userid, hotelname);
		Booking booking = new Booking();
		bookingMessage.initializeBookings(1);
		bookingMessage.bookings[0].bookingID = Integer.parseInt(request.getParameter("id"));
		Message bookingReply = booking.getBooking(bookingMessage);


		Message message = new Message(authlevel, userid, hotelname);
		Extra extras = new Extra();
		message.initializeExtras(1);
		message.extras[0].bookingID = Integer.parseInt(request.getParameter("id"));
		Message reply = extras.getBookingExtras(message);

		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.extras != null) {
			if(authlevel >= 3 || bookingReply.bookings[0].ownerID==userid) {
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

		int extraID = Integer.parseInt(request.getParameter("id"));
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeExtras(1);
		message.extras[0].extraID = extraID;
		Extra extras = new Extra();
		Message bookingDetails = extras.getExtra(message);
		Message reply = extras.deleteExtra(message);

		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS) {
			if(authlevel > 3) {
				request.setAttribute("status", "delete_success");
				request.setAttribute("message", "Extra successfully deleted.");
				response.sendRedirect(response.encodeRedirectURL("extras.html?action=booking_extras&id="+bookingDetails.extras[0].bookingID+"&status=delete"));
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

		int extraID = Integer.parseInt(request.getParameter("id"));
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeExtras(1);
		message.extras[0].extraID = extraID;
		Extra extras = new Extra();
		if(authlevel >= 3 || message.extras[0].extraID==userid)
		{
			Message reply = extras.getExtra(message);

			if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.extras.length > 0)
			{
				request.setAttribute("extra", reply.extras[0]);
				getServletContext().getRequestDispatcher("/views/extras/edit_extra.jsp").include(request, response);
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

		try {
			int extraID = Integer.parseInt(request.getParameter("id"));
			Message message = new Message(authlevel, userid, hotelname);
			message.initializeExtras(1);
			message.extras[0].extraID = extraID;

			message.extras[0].price = Float.parseFloat(request.getParameter("price"));
			// needs work
			message.extras[0].extraName = request.getParameter("extraName");
			DateFormat df 	= new SimpleDateFormat("dd/MM/yyyy");
			java.sql.Date date = new java.sql.Date(df.parse(request.getParameter("date")).getTime());
			message.extras[0].date = date;
			message.extras[0].bookingID = Integer.parseInt(request.getParameter("bookingID"));

			Extra extras	 = new Extra();
			if(authlevel >= 3 || message.extras[0].extraID==userid)
			{
				message.validate();
				if(message.response.responseCode == ResponseMessage.ResponseCode.SUCCESS)
				{
						Message reply = extras.editExtra(message);

						if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS)
						{
							response.sendRedirect(response.encodeRedirectURL("extras.html?action=view&id="+extraID+"&status=edit_success"));
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
					request.setAttribute("message", message.response.responseString);
					editExtras(request, response);
				}
			}
			else
			{
				request.setAttribute("message", "You are not authorized to edit this extras.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} catch(ParseException e) {
			request.setAttribute("status", "edit_extras_failed");
			request.setAttribute("message", "Invalid date formats.");
			editExtras(request, response);
		} catch(Exception e) {
			request.setAttribute("message", "Exception: "+e.toString());
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void doAddExtra(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		try {
			Message message = new Message(authlevel, userid, hotelname);
			message.initializeExtras(1);

			message.extras[0].price = Float.parseFloat(request.getParameter("price"));
			// needs work
			message.extras[0].extraName = request.getParameter("extraName");
			DateFormat df 	= new SimpleDateFormat("dd/MM/yyyy");
			java.sql.Date date = new java.sql.Date(df.parse(request.getParameter("date")).getTime());
			message.extras[0].date = date;
			message.extras[0].bookingID = Integer.parseInt(request.getParameter("bookingID"));

			Extra extras = new Extra();
			if(authlevel >= 3 || message.extras[0].extraID==userid)
			{
				message.validate();
				if(message.response.responseCode == ResponseMessage.ResponseCode.SUCCESS)
				{
					Message reply = extras.addExtra(message);

					if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS)
					{
						int extraID = reply.extras[0].extraID;
						response.sendRedirect(response.encodeRedirectURL("extras.html?action=view&id="+extraID+"&status=edit_success"));
					}
					else
					{
						request.setAttribute("message", reply.response.responseString);
						getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
					}
				}
				else
				{
					request.setAttribute("status", "add_failed");
					request.setAttribute("extra", message.extras[0]);
					request.setAttribute("message", message.response.responseString);
					addExtra(request, response);
				}
			}
			else
			{
				request.setAttribute("message", "You are not authorized to add this extras.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} catch(ParseException e) {
			request.setAttribute("status", "add_failed");
			request.setAttribute("message", "Invalid date formats.");
			addExtra(request, response);
		} catch(Exception e) {
			request.setAttribute("status", "add_failed");
			request.setAttribute("message", "Exception: "+e.toString());
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void addExtra(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		getServletContext().getRequestDispatcher("/views/extras/add_extra.jsp").include(request, response);
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
		if(action!=null && action.equals("add"))
		{
			doAddExtra(request, response);
		}
	}

}
