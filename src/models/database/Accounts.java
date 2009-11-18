package models.database;
import models.messages.*;
import java.util.regex.Matcher;
public class Accounts {
	
	public Message deleteAccount(Message i_msg)
	{
		Message r_i_msg = i_msg;
		
		return r_i_msg;
	}
	
	public Message addAccount(Message i_msg)
	{
		Message r_i_msg = i_msg;
		
		return r_i_msg;
	}
	
	
	public Message getAccount(Message i_msg)
	{
		Message r_i_msg = i_msg;
		
		return r_i_msg;
	}

	public Message getAllAccounts(Message i_msg)
	{
		Message r_i_msg = i_msg;
		
		return r_i_msg;

	}

	public Message getFilteredAccount(Message i_msg)
	{
		Message r_i_msg = i_msg;
		
		return r_i_msg;
	}
	
	public Message validateParams(Message i_msg)
	{
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
		}

		return r_i_msg;
	}
}
