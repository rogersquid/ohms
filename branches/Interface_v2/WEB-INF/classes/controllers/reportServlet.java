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

		Report report = new Report();
		ReportMessage cleannessReport = report.generateCleannessReport(message);
		if(cleannessReport.response.responseCode == ResponseMessage.ResponseCode.FAIL) {
			request.setAttribute("message", cleannessReport.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}

		ReportMessage extraReport = report.generateExtraReport(message);
		if(extraReport.response.responseCode == ResponseMessage.ResponseCode.FAIL) {
			request.setAttribute("message", extraReport.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}

		ReportMessage roomStatusReport = report.generateRoomStatusReport(message);
		if(roomStatusReport.response.responseCode == ResponseMessage.ResponseCode.FAIL) {
			request.setAttribute("message", roomStatusReport.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}

		ReportMessage roomAnalysisReport = report.generateStatisticalRoomAnalysisReport(message);
		if(roomAnalysisReport.response.responseCode == ResponseMessage.ResponseCode.FAIL) {
			request.setAttribute("message", roomAnalysisReport.response.responseString);
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}

		request.setAttribute("cleannessReport", cleannessReport);
		request.setAttribute("extraReport", extraReport);
		request.setAttribute("roomStatusReport", roomStatusReport);
		request.setAttribute("roomAnalysisReport", roomAnalysisReport);

		if(authlevel >=2) {
			getServletContext().getRequestDispatcher("/views/reports/report.jsp").include(request, response);
		} else {
			request.setAttribute("message", "You are not authorized to view reports.");
			getServletContext().getRequestDispatcher("/views/error.jsp").include(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
	}

}
