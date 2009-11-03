package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.*;

public class createRoomServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		getServletContext().getRequestDispatcher("/views/create_room_form.jsp").include(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{ /*
		int room_floor = (int)request.getParameter("room_floor");
		int room_number = (int)request.getParameter("room_number");
		float price = (float)request.getParameter("price");
		String room_type = request.getParameter("room_type");
		boolean available = (boolean)request.getParameter("available");
		boolean cleaned = (boolean)request.getParameter("cleaned");
		int numBeds = (int)request.getParameter("numBeds");
		boolean onsuite = (boolean)request.getParameter("onsuite");
		boolean tv = (boolean)request.getParameter("tv");
		boolean disability = (boolean)request.getParameter("disability");
		boolean elevator = (boolean)request.getParameter("elevator");
		boolean ebirdcall = (boolean)request.getParameter("ebirdcall");
		boolean emornpaper = (boolean)request.getParameter("emornpaper"); */
		
		getServletContext().getRequestDispatcher("/views/create_room_form.jsp").include(request, response);
	}

}
