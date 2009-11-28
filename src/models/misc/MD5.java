/*
 * MD5.java
 * 
 * This is the class used to encrypt sensitive information (passwords) in a format
 * in which any user cannot view the password in plain text
 */

package models.misc;
import java.security.*;

public class MD5 {
	
	public static String hashString(String pwd) {
		/*
		 * OVERVIEW: Gets the non plain text string of the password which is what will be stored in the database 
		 * PRECONDITIONS: String is not empty. 
		 * POSTCONDITIONS: Returns a hash string of the password
		 */
		StringBuffer hex = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(pwd.getBytes());
			byte[] d = md.digest();
			String plaintxt;
			for(int i = 0; i < d.length; i++) {
				plaintxt = Integer.toHexString(0xFF & d[i]);
				if(plaintxt.length() < 2) {
					plaintxt = "0" + plaintxt;
				}
				hex.append(plaintxt);
			}
		} catch (NoSuchAlgorithmException nsae) {}
		return hex.toString();
	}
}