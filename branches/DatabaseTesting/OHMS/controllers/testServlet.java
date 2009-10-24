package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.*;

public class testServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Account account = new Account();
		out.print("yo: " + account.test());

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
	}


}
