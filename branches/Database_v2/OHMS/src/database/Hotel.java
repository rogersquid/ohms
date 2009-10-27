package database;

import messages.*;
import messages.message_Helper.*;
// A Hotel object has keeper objects for each DB type (room, account, booking)
// and it maintains the database connection string.

public class Hotel {
	String				hotelName;
	AccountKeeper		accountKeeper;
	RoomKeeper			roomKeeper;
//	Booking_Keeper	c_booking_keeper;
	
	public Hotel(String i_name){
		hotelName		= i_name.toLowerCase();
		accountKeeper	= new AccountKeeper();
		roomKeeper	 	= new RoomKeeper();
//		bookingKeeper 	= new Booking_Keeper();
	}
	
	public String return_Name(){
		return hotelName;
	}
	
	public Header process_Message(Message i_msg){
		// Hotel class simply hands the input message to one of the 
		// Object-Handlers for processing
		
		Header head = i_msg.return_Header();
		
		if(head.to == Header.To.ACCOUNT){
			accountKeeper.process_Message((Account_Message) i_msg);
		}
		if(head.to == Header.To.ROOM){
			roomKeeper.Process_Message((Room_Message) i_msg);
		}
	/*	if(head.to == Header.To.BOOKING){
			booking_keeper.process_Message((Booking_Message) i_msg);
		}
	 */
		return Header head=new Header();
	}
}
