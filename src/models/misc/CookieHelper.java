package models.misc;import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.messages.*;
import models.database.*;
import models.misc.*;

public class CookieHelper {
	public static int getAccountID(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			// Print a list of all cookies sent by browser
			for (int i =0; i< cookies.length; i++) {
				Cookie c = cookies[i];
				if(c.getName().equals("ohms_accountID")) {
					return Integer.parseInt(c.getValue());
				}
			}
			return 0;
		} else {
			return 0;
		}
	}
	
	public static String getMD5Password(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			// Print a list of all cookies sent by browser
			for (int i =0; i< cookies.length; i++) {
				Cookie c = cookies[i];
				if(c.getName().equals("ohms_password")) {
					return c.getValue();
				}
			}
			return "";
		} else {
			return "";
		}
	}
	
	public static Cookie getAccountCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			// Print a list of all cookies sent by browser
			for (int i =0; i< cookies.length; i++) {
				Cookie c = cookies[i];
				if(c.getName().equals("accountID")) {
					return c;
				}
			}
			return null;
		} else {
			return null;
		}
	}

}