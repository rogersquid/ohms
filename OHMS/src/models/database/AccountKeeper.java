package models.database;

import models.messages.*;
import java.util.regex.*;

public class AccountKeeper {
	// People logged into the system
	public Message processMessage(AccountMessage i_msg){
		switch(i_msg.returnHeader().action)
		{
		case ADD:
			i_msg = validateParams(i_msg);
			// Check if the response flag has already been set.
			if(i_msg.returnHeader().responseCode != Header.Response.FAIL){
				i_msg = add(i_msg);
			}
			break;
		case EDIT:
			i_msg = validateParams(i_msg);
			// Check if the response flag has already been set.
			if(i_msg.returnHeader().responseCode != Header.Response.FAIL){
				i_msg = edit(i_msg);
			}
			break;
		case VIEW:
			i_msg = view(i_msg);
			break;
		case LOGIN:
			return login(i_msg);
		case DELETE:
			i_msg = delete(i_msg);
			break;
		default:
			//needs implementation
			break;
		}
		return i_msg;
	}
	public Message[] processMessageReturnBunch(AccountMessage i_msg){
		BookingMessage[] reply=null;
		switch(i_msg.returnHeader().action)
		{
		case VIEWALL:
			return viewAll(i_msg);
		default:
			//needs implementation
			return reply;
		}
	}
	private AccountMessage add(AccountMessage i_msg){
		Account anAccount = new Account();
		return 	anAccount.addAccount(i_msg);
	}
	private AccountMessage edit(AccountMessage i_msg){
		Account anAccount = new Account();
		return 	anAccount.editAccount(i_msg);
	}
	private AccountMessage delete(AccountMessage i_msg){
		Account anAccount = new Account();
		return 	anAccount.deleteAccount(i_msg);
	}
	private AccountMessage view(AccountMessage i_msg){
		Account anAccount = new Account();
		return 	anAccount.viewAccount(i_msg);
	}
	private AccountMessage login(AccountMessage i_msg){
		Account anAccount = new Account();
		return 	anAccount.login(i_msg);
	}
	private AccountMessage authenticate(AccountMessage i_msg){
		//usign autherizations
		Account anAccount = new Account();
		return 	anAccount.deleteAccount(i_msg);
	}
	private AccountMessage[] viewAll(AccountMessage i_msg){
		//usign autherizations
		Account aAccount = new Account();
		return 	aAccount.viewAllAccount(i_msg);
	}

	public AccountMessage validateParams(AccountMessage i_msg){

		AccountMessage 	reply;
		Matcher m;
		// Verify accountType
		// guest, maid, customer, staff, or admin
		if(!(i_msg.accountType.equalsIgnoreCase("guest") ||
				i_msg.accountType.equalsIgnoreCase("customer") ||
				i_msg.accountType.equalsIgnoreCase("maid") ||
				i_msg.accountType.equalsIgnoreCase("staff") ||
				i_msg.accountType.equalsIgnoreCase("admin")))
		{
			i_msg.concatHeaderResponse(Header.Response.FAIL, "Invaild accountType: " + i_msg.accountType +
					". Account ID: " + i_msg.accountID );
		}

		// Verify password
		String testPwd = i_msg.password;
		if(testPwd.isEmpty())
		{
			i_msg.concatHeaderResponse(Header.Response.FAIL, "Password String should not be empty. " +
					"Account ID: " + i_msg.accountID );
		}
		else
		{
			Pattern validPwd = Pattern.compile("[\\da-zA-Z-!@#\\$%\\^&\\*\\?,\\.\\|;:]+");
			m = validPwd.matcher(testPwd);
				if(!m.matches() || (m.groupCount() > 1))
			{
				i_msg.concatHeaderResponse(Header.Response.FAIL, "Password string is not vaild " +
						"Account ID: " + i_msg.accountID );
			}			
		}

		
		// Verify address
		String testAddr = i_msg.address;
		if(testAddr.isEmpty())
		{
			i_msg.concatHeaderResponse(Header.Response.FAIL, "Address String should not be empty. " +
					"Account ID: " + i_msg.accountID );
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
		String testEmail = i_msg.email;
		if(testEmail.isEmpty())
		{
			i_msg.concatHeaderResponse(Header.Response.FAIL, "Email address should not be empty. " +
					"Account ID: " + i_msg.accountID );
		}
		else
		{
			Pattern validEmail = Pattern.compile(".+@.+\\.[a-z]+");
			m = validEmail.matcher(testEmail);
				if(!m.matches() || (m.groupCount() > 1))
			{
				i_msg.concatHeaderResponse(Header.Response.FAIL, "Email address is not vaild " +
						"Account ID: " + i_msg.accountID );
			}
		}

		// Verify First Name
		String testFName = i_msg.firstname;
		if(testFName.isEmpty())
		{
			i_msg.concatHeaderResponse(Header.Response.FAIL, "First Name should not be empty. " +
					"Account ID: " + i_msg.accountID );
		}
		else {
			Pattern validName = Pattern.compile("[a-zA-Z-]+");
			m = validName.matcher(testFName);
			if (!m.matches() || (m.groupCount() > 1)) {

				i_msg.concatHeaderResponse(Header.Response.FAIL, "First Name is not vaild " + 
						"Account ID: " + i_msg.accountID);
			}
		}

		// Verify Last Name
		String testLName = i_msg.lastname;
		if(testLName.isEmpty())
		{
			i_msg.concatHeaderResponse(Header.Response.FAIL, "Last Name should not be empty. " +
					"Account ID: " + i_msg.accountID );
		}
		else {
			Pattern validName = Pattern.compile("[a-zA-Z-]+");
			m = validName.matcher(testLName);
			if (!m.matches() || (m.groupCount() > 1)) {
				i_msg.concatHeaderResponse(Header.Response.FAIL, "Last Name is not vaild " + 
						"Account ID: " + i_msg.accountID);
			}
		}

		// Verify Phone
		String testPhone = i_msg.phone;
		if(testLName.isEmpty())
		{
			i_msg.concatHeaderResponse(Header.Response.FAIL, "Phone number should not be empty. " +
					"Account ID: " + i_msg.accountID );
		}
		else {
			Pattern validPhone = Pattern.compile("[\\d-]+");
			m = validPhone.matcher(testPhone);
			if (!m.matches() || (m.groupCount() > 1)) {
				i_msg.concatHeaderResponse(Header.Response.FAIL, "Phone number is not vaild " + 
						"Account ID: " + i_msg.accountID);
			}
		}
		reply = i_msg;
		return reply;
	}
}
