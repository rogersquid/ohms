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
		String action = request.getParameter("action");
		if(action!=null && action.equals("logout")) {
			Cookie c = CookieHelper.getAccountCookie(request);
			c.setMaxAge(0);
			response.addCookie(c);
			request.setAttribute("status", "logout_successful");
		}
		
		
		int userid = CookieHelper.getAccountID(request);
		String hotelname = "test";
		
		Message userMessage = new Message(0, userid, hotelname);
		Account userAccount = new Account();
		userMessage.initializeAccounts(1);
		userMessage.accounts[0].accountID = userid;
		Message userInfo = userAccount.getAccount(userMessage);
		if(userInfo.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && userInfo.accounts.length > 0) {
			request.setAttribute("logged_in", true);
			request.setAttribute("userInfo", userInfo.accounts[0]);
		} else {
			request.setAttribute("logged_in", false);
		}
		
		int authlevel = 3;
		
		request.setAttribute("userID", userid);
		request.setAttribute("authLevel", authlevel);
		request.setAttribute("hotelName", hotelname);
		
		getServletContext().getRequestDispatcher("/views/login_form.jsp").include(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		int userid = CookieHelper.getAccountID(request);
		String hotelname = "test";
		
		Message userMessage = new Message(0, userid, hotelname);
		Account userAccount = new Account();
		Message userInfo = userAccount.getAccount(userMessage);
		if(userInfo.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && userInfo.accounts.length > 0) {
			request.setAttribute("logged_in", true);
			request.setAttribute("userInfo", userInfo.accounts[0]);
		} else {
			request.setAttribute("logged_in", false);
		}
		
		int authlevel = 3;
		
		request.setAttribute("userID", userid);
		request.setAttribute("authLevel", authlevel);
		request.setAttribute("hotelName", hotelname);
		
		Message message = new Message(authlevel, userid, hotelname);
		message.initializeAccounts(1);
		message.accounts[0].email = email;
		message.accounts[0].password = password;

		Account account = new Account();
		Message reply = account.login(message);


		if(reply.response.responseCode == ResponseMessage.ResponseCode.SUCCESS) {
			Cookie userCookie = new Cookie("accountID", Integer.toString(reply.accounts[0].accountID));
			String md5_password = MD5.hashString(reply.accounts[0].password);
			Cookie passwordCookie = new Cookie("md5_password", md5_password);
			response.addCookie(userCookie);
			request.setAttribute("name", reply.accounts[0].firstName+" "+reply.accounts[0].lastName);
			getServletContext().getRequestDispatcher("/views/login_success.jsp").include(request, response);
		} else {
			request.setAttribute("status", "login_failed");
			request.setAttribute("message", reply.response.responseString);
			getServletContext().getRequestDispatcher("/views/login_form.jsp").include(request, response);
		}
	}

}
