package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.*;

public class registerServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		getServletContext().getRequestDispatcher("/views/register_form.jsp").include(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		
		getServletContext().getRequestDispatcher("/views/register_form.jsp").include(request, response);
	}

}
