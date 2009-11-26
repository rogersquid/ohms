package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.database.*;
import models.messages.*;
import models.misc.*;

public class testServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		request = InterfaceHelper.initialize(request, response);
		getServletContext().getRequestDispatcher("/views/test.jsp").include(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
	}


}
