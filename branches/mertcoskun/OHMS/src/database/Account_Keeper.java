package database;

import messages.*;

public class Account_Keeper {
	public void process_Message(Account_Message i_msg){
		System.err.print("in keeper");
		i_msg.print_All();
	}
}