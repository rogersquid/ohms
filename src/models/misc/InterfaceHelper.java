package models.misc;import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.messages.*;
import models.database.*;
import models.misc.*;

public class InterfaceHelper {
	public static HttpServletRequest initialize(HttpServletRequest request, HttpServletResponse response) {
		int userid = CookieHelper.getAccountID(request);
		String md5_password = CookieHelper.getMD5Password(request);
		int authlevel;
		String hotelname = "test";

		Account userAccount = new Account();
		authlevel = userAccount.getAuthLevel(userid, md5_password);
		if(authlevel > 0) {
			Message userMessage = new Message(0, userid, hotelname);
			userMessage.initializeAccounts(1);
			userMessage.accounts[0].accountID = userid;
			Message userInfo = userAccount.getAccount(userMessage);
			
			request.setAttribute("logged_in", true);
			request.setAttribute("userInfo", userInfo.accounts[0]);
		} else {
			request.setAttribute("logged_in", false);
			authlevel = 0;
		}

		String urlToParse = request.getRequestURI();
		String[] parsedURL = urlToParse.split("/");
		String test = parsedURL[1];

		request.setAttribute("userID", userid);
		request.setAttribute("authLevel", authlevel);
		request.setAttribute("hotelName", hotelname);
		request.setAttribute("test", test);

		return request;
	}

}