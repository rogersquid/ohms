package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.misc.*;

public class exampleServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		String get = request.getParameter("test_get");
		if(get!=null) request.setAttribute("test_get", MD5.hashString(get));

		getServletContext().getRequestDispatcher("/views/example_form.jsp").include(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		String post = request.getParameter("test_post");
		if(post!=null) request.setAttribute("test_post", post.toUpperCase());

		getServletContext().getRequestDispatcher("/views/example_form.jsp").include(request, response);
	}

}
