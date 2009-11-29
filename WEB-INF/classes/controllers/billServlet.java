package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.database.*;
import models.messages.*;
import models.misc.*;

public class billServlet extends HttpServlet {
	
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
			doEditBill(request, response);
		}
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		request = InterfaceHelper.initialize(request, response);
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		String action = request.getParameter("action");
		if(action!=null && action.equals("view")) {
			viewBill(request, response);
		} else if(action!=null && action.equals("edit")) {
			editBill(request, response);
		} else if(action!=null && action.equals("all_bills")) {
			allBills(request, response);
		} else if(action!=null && action.equals("delete")) {
			deleteBill(request, response);
		} else {
			viewCurrentBill(request, response);
		}
	}

	public void viewBill(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		int billID = Integer.parseInt(request.getParameter("id"));
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeBills(1);
		message.bills[0].billID = billID;
		Bill bill = new Bill();
		Message reply = bill.getBill(message);

		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.bills.length > 0) {
			if(authlevel >= 3 || reply.accounts[0].accountID==userid) {
				request.setAttribute("bill", reply.bills[0]);
				getServletContext().getRequestDispatcher("/views/bill/view_bill.jsp").include(request, response);
			} else {
				request.setAttribute("message", "You are not authorized to view this bill.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void allBills(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		Message message = new Message(authlevel, userid, hotelname);
		Bill bill = new Bill();
		Message reply = bill.getAllBills(message);

		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.bills != null) {
			if(authlevel >= 3) {
				request.setAttribute("data", reply);
				getServletContext().getRequestDispatcher("/views/bill/all_bills.jsp").include(request, response);
			} else {
				request.setAttribute("message", "You are not authorized to view bills.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void deleteBill(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		int billID = Integer.parseInt(request.getParameter("id"));
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeBills(1);
		message.bills[0].billID = billID;
		Bill bill = new Bill();
		Message reply = bill.deleteBill(message);

		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS) {
			if(authlevel >= 3) {
				request.setAttribute("status", "delete_success");
				request.setAttribute("message", "Bill successfully deleted.");
				allBills(request, response);
			} else {
				request.setAttribute("message", "You are not authorized to delete this bill.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void viewCurrentBill(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		int billID = userid;
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeBills(1);
		message.bills[0].billID = billID;
		
		Bill bill = new Bill();
		Message reply = bill.getBill(message);

		if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.bills.length > 0) {
			// MURAT
			if(authlevel >= 3 || reply.bills[0].billID==userid) {
				request.setAttribute("bill", reply.bills[0]);
				getServletContext().getRequestDispatcher("/views/bill/view_bill.jsp").include(request, response);
			} else {
				request.setAttribute("message", "You are not authorized to view this bill.");
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		} else {
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void editBill(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		int billID = Integer.parseInt(request.getParameter("id"));
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeBills(1);
		message.bills[0].billID = billID;
		Bill bill = new Bill();
		if(authlevel >= 3)
		{
			Message reply = bill.getBill(message);

			if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && reply.bills.length > 0)
			{
				request.setAttribute("bill", reply.bills[0]);
				getServletContext().getRequestDispatcher("/views/bill/edit_bill_form.jsp").include(request, response);
			}
			else
			{
				request.setAttribute("message", reply.response.responseString);
				getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
			}
		}
		else
		{
			request.setAttribute("message", "You are not authorized to edit this bill.");
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void doEditBill(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");

		int billID = Integer.parseInt(request.getParameter("id"));
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeBills(1);
		message.bills[0].billID = billID;

		message.bills[0].bookingID = Integer.parseInt(request.getParameter("bookingID"));
		message.bills[0].paymentType = request.getParameter("paymentType");
		message.bills[0].status = (request.getParameter("status").equals("1")) ? true : false;
		
		Bill bill = new Bill();
		if(authlevel >= 3)
		{
			message.validate();
			if(message.response.responseCode == ResponseMessage.ResponseCode.SUCCESS)
			{
					Message reply = bill.editBill(message);

					if(reply.response.responseCode==ResponseMessage.ResponseCode.SUCCESS)
					{
						response.sendRedirect(response.encodeRedirectURL("bill.html?action=view&id="+billID+"&status=edit_success"));
					}
					else
					{
						request.setAttribute("message", reply.response.responseString);
						getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
					}
			}
			else
			{
				request.setAttribute("status", "edit_bill_failed");
				request.setAttribute("message", resp.responseString);
				editBill(request, response);
			}
		}
		else
		{
			request.setAttribute("message", "You are not authorized to edit this bill.");
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}
}