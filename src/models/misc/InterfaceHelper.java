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
		String hotelname = "test";
		
		Message userMessage = new Message(0, userid, hotelname);
		Account userAccount = new Account();
		userMessage.initializeAccounts(1);
		userMessage.accounts[0].accountID = userid;
		Message userInfo = userAccount.getAccount(userMessage);
		if(userInfo.response.responseCode==ResponseMessage.ResponseCode.SUCCESS && userInfo.accounts.length > 0) {
			request.setAttribute("logged_in", true);
			request.setAttribute("userInfo", userInfo.accounts[0]);
		} else {
			request.setAttribute("logged_in", false);
		}
		
		int authlevel = 3;
		
		request.setAttribute("userID", userid);
		request.setAttribute("authLevel", authlevel);
		request.setAttribute("hotelName", hotelname);
		
		return request;
	}

}