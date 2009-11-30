package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.database.*;
import models.messages.*;
import models.misc.*;

public class reportServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		request = InterfaceHelper.initialize(request, response);
		int authlevel = ((Integer)request.getAttribute("authLevel")).intValue();
		int userid = ((Integer)request.getAttribute("userID")).intValue();
		String hotelname = (String)request.getAttribute("hotelName");
		
		ReportMessage message = new ReportMessage(authlevel, userid, hotelname);
		
		ReportMessage cleannessReport = generateCleannessReport(message);
		ReportMessage extraReport = generateExtraReport(message);
		ReportMessage roomStatusReport = generateRoomStatusReport(message);
		ReportMessage roomAnalysisReport = generateStatisticalRoomAnalysisReport(message);
		
		request.setAttribute("cleannessReport", cleannessReport);
		request.setAttribute("extraReport", extraReport);
		request.setAttribute("roomStatusReport", roomStatusReport);
		request.setAttribute("roomAnalysisReport", roomAnalysisReport);
		
		getServletContext().getRequestDispatcher("/views/reports/report.jsp").include(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
	}

}
