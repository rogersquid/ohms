package models.database;

import models.messages.*;
import models.messages.message_Helper.*;
import java.sql.SQLException;

public class Account_Keeper {
	// People logged into the system
	private Account_Message c_Act_Msg;

	public void process_Message(Account_Message i_msg){
		System.err.print("in keeper");
		c_Act_Msg = i_msg;
		switch(i_msg.return_Header().action)
		{
		case ADD:
			Add(i_msg);
			break;
		case EDIT:
			Edit();
			break;
		case VIEW:
			View();
			break;
		case DELETE:
			Delete();
			break;
		case LOGIN:
			Login();
			break;
		}
		
		i_msg.print_Middle();
	}
	private void Add(Account_Message i_msg)
	{
		Account anAccount = new Account();

		anAccount.Add_Account(i_msg);

	}
	private void Add()
	{
		Account anAccount = new Account();

		if(anAccount.Add_Account(c_Act_Msg.firstname, c_Act_Msg.lastname, 
				c_Act_Msg.gender, c_Act_Msg.phone, c_Act_Msg.email, c_Act_Msg.address))
		{
			c_Act_Msg.fill_Header_Response(Header.Response.SUCCESS, "Account " + c_Act_Msg.email + " added.");
		}
		else
		{	
			c_Act_Msg.fill_Header_Response(Header.Response.FAIL, "Account " + c_Act_Msg.email + " was not added.");
		}

	}

	private void Edit()
	{
		Delete();
		Add();
	}

	private void Delete()
	{
		Account anAccount = new Account();

		if(c_Act_Msg.email != "")
		{
			anAccount.Delete_Account(c_Act_Msg.email);
			c_Act_Msg.fill_Header_Response(Header.Response.SUCCESS, "Account " + c_Act_Msg.email + " deleted.");
		}
		else if(c_Act_Msg.account_id > 0)
		{
			anAccount.Delete_Account(c_Act_Msg.account_id);
			c_Act_Msg.fill_Header_Response(Header.Response.SUCCESS, "Account " + c_Act_Msg.account_id + " deleted.");
		}
		else
		{
			c_Act_Msg.fill_Header_Response(Header.Response.FAIL, "Account " + c_Act_Msg.email + " was not found.");
		}
		anAccount.Fetch_All(c_Act_Msg.email, c_Act_Msg.password);

	}

	private void View()
	{
		Account anAccount = new Account();

		anAccount.Fetch_All(c_Act_Msg.email, c_Act_Msg.password);
		c_Act_Msg.fill_Header_Response(Header.Response.SUCCESS, "Results fetched");

	}
	
	private void Login()
	{
		Account anAccount = new Account();
		if(anAccount.login(c_Act_Msg.email, c_Act_Msg.password)) {
			c_Act_Msg.fill_Header_Response(Header.Response.SUCCESS, "Login successful");
		} else {
			c_Act_Msg.fill_Header_Response(Header.Response.FAIL, "Login failed");
		}
	}

}
