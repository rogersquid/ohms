package models.database;

import models.messages.*;

public class Account_Keeper {
	public void process_Message(Account_Message i_msg){
		System.err.print("in keeper");
		i_msg.print_Middle();
	}
}
