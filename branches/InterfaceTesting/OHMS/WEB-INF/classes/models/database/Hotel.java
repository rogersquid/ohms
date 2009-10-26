package models.database;

import models.messages.*;
import models.messages.message_Helper.*;

// A Hotel object has keeper objects for each DB type (room, account, booking)
// and it maintains the database connection string.

public class Hotel {
	String				c_name;
	Account_Keeper		c_account_keeper;
	Room_Keeper			c_room_keeper;
//	Booking_Keeper	c_booking_keeper;
	
	public Hotel(String i_name){
		c_name				= i_name.toLowerCase();
		c_account_keeper	= new Account_Keeper();
		//c_room_keeper		= new Room_Keeper();
//		c_booking_keeper	= new Booking_Keeper();
	}
	
	public String return_Name(){
		return c_name;
	}
	
	public void process_Message(Message i_msg){
		// Hotel class simply hands the input message to one of the 
		// Object-Handlers for processing
		
		Header head = i_msg.return_Header();
		
		if(head.to == Header.To.ACCOUNT){
			c_account_keeper.process_Message((Account_Message) i_msg);
		}
		if(head.to == Header.To.ROOM){
			//c_room_keeper.Process_Message((Room_Message) i_msg);
		}
	/*	if(head.to == Header.To.BOOKING){
			booking_keeper.process_Message((Booking_Message) i_msg);
		}
	 */
	}
}
