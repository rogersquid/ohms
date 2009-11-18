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
	public String phone;
	public String address;
	public String email;
	public boolean gender;
	public java.sql.Timestamp creationDate;
	
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
	
	public Message validateParams(Message i_msg){
		Message r_i_msg = i_msg;
		Matcher m;
		// Verify accountType
		// guest, maid, customer, staff, or admin
		if(!(i_msg.accounts[0].accountType.equalsIgnoreCase("guest") ||
				i_msg.accounts[0].accountType.equalsIgnoreCase("customer") ||
				i_msg.accounts[0].accountType.equalsIgnoreCase("maid") ||
				i_msg.accounts[0].accountType.equalsIgnoreCase("staff") ||
				i_msg.accounts[0].accountType.equalsIgnoreCase("admin")))
		{
			//i_msg.concatHeaderResponse(Header.Response.FAIL, "Invaild accountType: " + i_msg.accountType +
			//		". Account ID: " + i_msg.accounts[0].accountID );
			i_msg.response.responseString = i_msg.response.responseString + ("Invalid account type: " + i_msg.accounts[0].accountType + ". Account id: " + i_msg.accounts[0].accountID);
			//CHANGE RESPONSE CODE
		}

		
		// Verify password
		String testPwd = i_msg.accounts[0].password;
		if(testPwd.isEmpty())
		{
			//i_msg.concatHeaderResponse(Header.Response.FAIL, "Password String should not be empty. " +
				//	"Account ID: " + i_msg.accountID );
			i_msg.response.responseString = i_msg.response.responseString +  ("Password String should not be empty. " + "Account ID: " + i_msg.accounts[0].accountID);
		}
		else
		{
			Pattern validPwd = Pattern.compile("[\\da-zA-Z-!@#\\$%\\^&\\*\\?,\\.\\|;:]+");
			m = validPwd.matcher(testPwd);
				if(!m.matches() || (m.groupCount() > 1))
			{
				//i_msg.concatHeaderResponse(Header.Response.FAIL, "Password string is not vaild " +
					//	"Account ID: " + i_msg.accountID );
					i_msg.response.responseString = i_msg.response.responseString +  ("Password string is not vaild " + "Account ID: " + i_msg.accounts[0].accountID);
			}			
		}
		
		// Verify address
		String testAddr = i_msg.accounts[0].address;
		if(testAddr.isEmpty())
		{
			//i_msg.concatHeaderResponse(Header.Response.FAIL, "Address String should not be empty. " +
				//	"Account ID: " + i_msg.accountID );
			i_msg.response.responseString = i_msg.response.responseString + ("Address String should not be empty. " + "Account ID: " + i_msg.accounts[0].accountID);			
		}
		else
		{
//			Pattern validAddr = Pattern.compile("[\\da-zA-Z-!@#\\$%\\^&\\*\\?,\\.\\|;:]+");
//			m = validAddr.matcher(testAddr);
//				if(!m.matches())
//			{
//				i_msg.concatHeaderResponse(Header.Response.FAIL, "Address is not vaild " +
//						"Account ID: " + i_msg.accountID );
//			}			
		}

		
		// Verify email
		String testEmail = i_msg.accounts[0].email;
		if(testEmail.isEmpty())
		{
			//i_msg.concatHeaderResponse(Header.Response.FAIL, "Email address should not be empty. " +
				//	"Account ID: " + i_msg.accountID );
			i_msg.response.responseString = i_msg.response.responseString + ("Email address should not be empty. " + "Account ID: " + i_msg.accounts[0].accountID);
		}
		else
		{
			Pattern validEmail = Pattern.compile(".+@.+\\.[a-z]+");
			m = validEmail.matcher(testEmail);
				if(!m.matches() || (m.groupCount() > 1))
			{
				//i_msg.concatHeaderResponse(Header.Response.FAIL, "Email address is not vaild " +
					//	"Account ID: " + i_msg.accountID );
			i_msg.response.responseString = i_msg.response.responseString + ("Email address is not vaild " + "Account ID: " + i_msg.accounts[0].accountID ); 
			}
		}

		// Verify First Name
		String testFName = i_msg.accounts[0].firstName;
		if(testFName.isEmpty())
		{
		//	i_msg.concatHeaderResponse(Header.Response.FAIL, "First Name should not be empty. " +
			//		"Account ID: " + i_msg.accountID );
			i_msg.response.responseString = i_msg.response.responseString + ("First Name should not be empty. " + "Account ID: " + i_msg.accounts[0].accountID );
		}
		else {
			Pattern validName = Pattern.compile("[a-zA-Z-]+");
			m = validName.matcher(testFName);
			if (!m.matches() || (m.groupCount() > 1)) {

				//i_msg.concatHeaderResponse(Header.Response.FAIL, "First Name is not vaild " + 
					//	"Account ID: " + i_msg.accountID);
				i_msg.response.responseString = i_msg.response.responseString + ("First Name is not vaild " + "Account ID: " + i_msg.accounts[0].accountID);
			}
		}
		
		// Verify Last Name
		String testLName = i_msg.accounts[0].lastName;
		if(testLName.isEmpty())
		{
			//i_msg.concatHeaderResponse(Header.Response.FAIL, "Last Name should not be empty. " +
				//	"Account ID: " + i_msg.accountID );
			i_msg.response.responseString = i_msg.response.responseString + ("Last Name should not be empty. " + "Account ID: " + i_msg.accounts[0].accountID );
		}
		else {
			Pattern validName = Pattern.compile("[a-zA-Z-]+");
			m = validName.matcher(testLName);
			if (!m.matches() || (m.groupCount() > 1)) {
				//i_msg.concatHeaderResponse(Header.Response.FAIL, "Last Name is not vaild " + 
					//	"Account ID: " + i_msg.accountID);
				i_msg.response.responseString = i_msg.response.responseString + ("Last Name is not vaild " + "Account ID: " + i_msg.accounts[0].accountID);
			}
		}		
		
		// Verify Phone
		String testPhone = i_msg.accounts[0].phone;
		if(testLName.isEmpty())
		{
			//i_msg.concatHeaderResponse(Header.Response.FAIL, "Phone number should not be empty. " +
				//	"Account ID: " + i_msg.accountID );
			i_msg.response.responseString = i_msg.response.responseString + ("Phone number should not be empty. " + "Account ID: " + i_msg.accounts[0].accountID );
		}
		else {
			Pattern validPhone = Pattern.compile("[\\d-]+");
			m = validPhone.matcher(testPhone);
			if (!m.matches() || (m.groupCount() > 1)) {
				//i_msg.concatHeaderResponse(Header.Response.FAIL, "Phone number is not vaild " + 
					//	"Account ID: " + i_msg.accountID);
				i_msg.response.responseString = i_msg.response.responseString + ("Phone number is not vaild " + "Account ID: " + i_msg.accounts[0].accountID);
			}
		}
	
		
		
		return r_i_msg;
	}
	
}
