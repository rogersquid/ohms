package database;

import messages.*;
import messages.Message.To;

public class Hotel {
	String c_name;
	String c_path;
	Account_Keeper account_keeper;
	
	public Hotel(String i_name, String i_path){
		c_name=i_name;
		c_path=i_path;
		account_keeper= new Account_Keeper();
	}
	public String return_Name(){
		return c_name;
	}
	public void process_Message(Message i_msg){
		System.err.print("hotel message processing");
		if(i_msg.return_To()==To.ACCOUNT){
			account_keeper.process_Message((Account_Message) i_msg);
		}
	}
}
