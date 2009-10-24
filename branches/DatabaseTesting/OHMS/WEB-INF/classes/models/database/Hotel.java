package database;

import messages.*;
import message_Helper.*;

public class Hotel {
	String				c_name;
	Account_Keeper		c_account_keeper;
	Room_Keeper			c_room_keeper;
//	Booking_Keeper	c_booking_keeper;
	
	public Hotel(String i_name, String i_path){
		c_name				= i_name.toLowerCase();
		c_account_keeper	= new Account_Keeper();
		c_room_keeper		= new Room_Keeper();
//		c_booking_keeper	= new Booking_Keeper();
	}
	
	public String return_Name(){
		return c_name;
	}
	
	public void process_Message(Message i_msg){
		// Hotel class simply hands the input message to one of the 
		// Object-Handlers for processing
		System.err.print("hotel message processing");
		
		Header head = i_msg.return_Header();
		
		if(head.to == Header.To.ACCOUNT){
			account_keeper.process_Message((Account_Message) i_msg);
		}
		if(head.to == Header.To.ROOM){
			room_keeper.process_Message((Room_Message) i_msg);
		}
	/*	if(head.to == Header.To.BOOKING){
			booking_keeper.process_Message((Booking_Message) i_msg);
		}
	 */
	}
}
