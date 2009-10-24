package database;

import messages.*;
import message_Helper.*;

public class Hotel {
	String c_name;
	Account_Keeper account_keeper;
	
	public Hotel(String i_name, String i_path){
		c_name=i_name.toLowerCase();
		account_keeper= new Account_Keeper();
	}
	public String return_Name(){
		return c_name;
	}
	public void process_Message(Message i_msg){
		System.err.print("hotel message processing");
		Header head=i_msg.return_Header();
		if(head.to==Header.To.ACCOUNT){
			account_keeper.process_Message((Account_Message) i_msg);
		}
	}
}
