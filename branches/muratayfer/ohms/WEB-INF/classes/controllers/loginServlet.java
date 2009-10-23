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
		//response.setContentType("text/html");
		//PrintWriter out = response.getWriter();
		//out.print(Account.test());
		getServletContext().getRequestDispatcher("/views/login_form.jsp").include(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if(login(username, password)) {
			request.setAttribute("username", username);
			getServletContext().getRequestDispatcher("/views/login_success.jsp").include(request, response);
		} else {
			request.setAttribute("status", "login_failed");
			getServletContext().getRequestDispatcher("/views/login_form.jsp").include(request, response);
		}
	}

	public boolean login(String username, String password) {
		Account account = new Account();
		return account.accountIsValid(username, password);
	}

}
