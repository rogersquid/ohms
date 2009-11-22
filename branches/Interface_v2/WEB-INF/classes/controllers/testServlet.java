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
		String action = request.getParameter("action");
		if(action=="create_room") {
			getServletContext().getRequestDispatcher("/views/create_room_form.jsp").include(request, response);
		} else if(action="edit_room") {
			getServletContext().getRequestDispatcher("/views/edit_room_form.jsp").include(request, response);
		} else if(action="rooms") {
			getServletContext().getRequestDispatcher("/views/viewall_room.jsp").include(request, response);
		} else if(action="edit_room") {
			getServletContext().getRequestDispatcher("/views/edit_room_form.jsp").include(request, response);
		} else {
			request.setAttribute("message", "Invalid action");
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
	}


}
