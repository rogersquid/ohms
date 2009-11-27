/*
 * AccountMessage.java
 * Objects of this class are instantiated to pass Account specific information from Interface through 
 * to Database and everywhere in between. Is always harboured in a Message.java object
 */

package models.messages;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.messages.*;
public class AccountMessage{
	public int accountID;
	public String accountType;
	public String firstName;
	public String lastName;
	public String password;
	public String re_password;
	public String phone;
	public String address;
	public String email;
	public boolean gender;
	public java.sql.Timestamp creationTime;

	/*
	 * PRE: None
	 * POST: The AccountMessage has its account details filled in 
	 * 
	 *  Fills in the account details. Subject to verification if not validated
	 */
	public boolean fill_All(int i_account_id, String i_account_type, String i_first_name, String i_surname, String i_pw, boolean i_gender, String i_phone, String i_add, String i_mail){
		accountID		= i_account_id;
		accountType		= i_account_type;
		firstName		= i_first_name;
		lastName		= i_surname;
		password		= i_pw;
		gender			= i_gender;
		phone			= i_phone;
		address			= i_add;
		email			= i_mail;
		return true;
	}

	/*
	 * PRE: None
	 * POST: The details of the AccountMessage are printed out
	 * 
	 * Spills out fields of this AccountMessage
	 */
	public void print_Middle(){
		System.out.println(accountID);
		System.out.println(accountType);
		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(password);
		System.out.println(gender);
		System.out.println(phone);
		System.out.println(address);
		System.out.println(email);
	}

	/* 
	 * PRE: None
	 * POST: A ResponseMessage is returned that contains a description of the errors in its ResponseString
	 * and the ResponseCode is set accordingly 
	 * 
	 * Checks to see if the parameters entered are valid.  
	 * 
	 */
	public ResponseMessage validateParams(){
		ResponseMessage response = new ResponseMessage();
		response.responseString = "";
		Matcher m;
		// Verify accountType
		// guest, maid, customer, staff, or admin
		if(accountType!=null && !(accountType.equalsIgnoreCase("guest") ||
				accountType.equalsIgnoreCase("customer") ||
				accountType.equalsIgnoreCase("maid") ||
				accountType.equalsIgnoreCase("staff") ||
				accountType.equalsIgnoreCase("admin")))
		{
			response.responseString = response.responseString + ("- Invalid account type: " + accountType + ". Account id: " + accountID + "<br />");
			//CHANGE RESPONSE CODE
			response.responseCode = ResponseMessage.ResponseCode.FAIL;
		}


		// Verify password
		if(password.isEmpty())
		{
			response.responseString = response.responseString +  ("- Password should not be blank. <br />");
			response.responseCode = ResponseMessage.ResponseCode.FAIL;
		}
		else
		{
			Pattern validPwd = Pattern.compile("[\\da-zA-Z-!@#\\$%\\^&\\*\\?,\\.\\|;:]+");
			m = validPwd.matcher(password);
				if(!m.matches() || (m.groupCount() > 1))
			{
					response.responseString = response.responseString +  ("- Password string is not vaild <br />");
					response.responseCode = ResponseMessage.ResponseCode.FAIL;
			}
			if(!password.equals(re_password))
			{
				response.responseString = response.responseString +  ("- Passwords do not match<br />");
				response.responseCode = ResponseMessage.ResponseCode.FAIL;
			}
		}

		// Verify address
		if(address.isEmpty())
		{
			response.responseString = response.responseString + ("- Address field should not be blank.<br />");
			response.responseCode = ResponseMessage.ResponseCode.FAIL;
		}


		// Verify email
		if(email.isEmpty())
		{
			response.responseString = response.responseString + ("- Email address should not be blank. <br />");
			response.responseCode = ResponseMessage.ResponseCode.FAIL;
		}
		else
		{
			Pattern validEmail = Pattern.compile(".+@.+\\.[a-z]+");
			m = validEmail.matcher(email);
				if(!m.matches() || (m.groupCount() > 1))
			{
			response.responseString = response.responseString + ("- Email address is not vaild.<br />");
			response.responseCode = ResponseMessage.ResponseCode.FAIL;
			}
		}

		// Verify First Name
		if(firstName.isEmpty())
		{
			response.responseString = response.responseString + ("- First Name should not be blank. <br />");
			response.responseCode = ResponseMessage.ResponseCode.FAIL;
		}
		else {
			Pattern validName = Pattern.compile("[a-zA-Z-]+");
			m = validName.matcher(firstName);
			if (!m.matches() || (m.groupCount() > 1)) {
				response.responseString = response.responseString + ("- First Name is not valid.<br />");
				response.responseCode = ResponseMessage.ResponseCode.FAIL;
			}
		}

		// Verify Last Name
		if(lastName.isEmpty())
		{
			response.responseString = response.responseString + ("- Last Name should not be blank. <br />");
			response.responseCode = ResponseMessage.ResponseCode.FAIL;
		}
		else {
			Pattern validName = Pattern.compile("[a-zA-Z-]+");
			m = validName.matcher(lastName);
			if (!m.matches() || (m.groupCount() > 1)) {
				response.responseString = response.responseString + ("- Last Name is not valid. <br />");
				response.responseCode = ResponseMessage.ResponseCode.FAIL;
			}
		}

		// Verify Phone
		if(phone.isEmpty())
		{
			response.responseString = response.responseString + ("- Phone number should not be blank. <br />");
			response.responseCode = ResponseMessage.ResponseCode.FAIL;
		}
		else {
			Pattern validPhone = Pattern.compile("\\+?[\\d-\\s]+");
			m = validPhone.matcher(phone);
			if (!m.matches() || (m.groupCount() > 1)) {
				response.responseString = response.responseString + ("- Phone number is not valid. <br />");
				response.responseCode = ResponseMessage.ResponseCode.FAIL;
			}
		}

		return response;
	}

	
	/* 
	 * PRE: None
	 * POST: A ResponseMessage is returned that contains a description of the errors in its ResponseString
	 * and the ResponseCode is set accordingly 
	 * 
	 * This is similar to the validateParams() but this is tailored for the case of edit accounts  
	 * 
	 */
	public ResponseMessage validateEditParams(){
		ResponseMessage response = new ResponseMessage();
			response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
		response.responseString = "";
		Matcher m;
		// Verify accountType
		// guest, maid, customer, staff, or admin
		if(accountType!=null && !(accountType.equalsIgnoreCase("guest") ||
				accountType.equalsIgnoreCase("customer") ||
				accountType.equalsIgnoreCase("maid") ||
				accountType.equalsIgnoreCase("staff") ||
				accountType.equalsIgnoreCase("admin")))
		{
			response.responseString = response.responseString + ("- Invalid account type: " + accountType + ". Account id: " + accountID + "<br />");
			//CHANGE RESPONSE CODE
			response.responseCode = ResponseMessage.ResponseCode.FAIL;
		}


		// Verify password

		if(password!=null && !password.isEmpty())
		{
			Pattern validPwd = Pattern.compile("[\\da-zA-Z-!@#\\$%\\^&\\*\\?,\\.\\|;:]+");
			m = validPwd.matcher(password);
				if(!m.matches() || (m.groupCount() > 1))
			{
					response.responseString = response.responseString +  ("- Password string is not vaild <br />");
					response.responseCode = ResponseMessage.ResponseCode.FAIL;
			}
			if(!password.equals(re_password))
			{
				response.responseString = response.responseString +  ("- Passwords do not match<br />");
				response.responseCode = ResponseMessage.ResponseCode.FAIL;
			}
		}

		// Verify First Name

		if(firstName!=null && !firstName.isEmpty()){
			Pattern validName = Pattern.compile("[a-zA-Z-]+");
			m = validName.matcher(firstName);
			if (!m.matches() || (m.groupCount() > 1)) {
				response.responseString = response.responseString + ("- First Name is not valid.<br />");
				response.responseCode = ResponseMessage.ResponseCode.FAIL;
			}
		}

		// Verify Last Name

		if(lastName!=null && !lastName.isEmpty()){
			Pattern validName = Pattern.compile("[a-zA-Z-]+");
			m = validName.matcher(lastName);
			if (!m.matches() || (m.groupCount() > 1)) {
				response.responseString = response.responseString + ("- Last Name is not valid. <br />");
				response.responseCode = ResponseMessage.ResponseCode.FAIL;
			}
		}

		// Verify Phone

		if(phone!=null && !phone.isEmpty()){
			Pattern validPhone = Pattern.compile("\\+?[\\d-\\s]+");
			m = validPhone.matcher(phone);
			if (!m.matches() || (m.groupCount() > 1)) {
				response.responseString = response.responseString + ("- Phone number is not valid. <br />");
				response.responseCode = ResponseMessage.ResponseCode.FAIL;
			}
		}

		return response;
	}
}
