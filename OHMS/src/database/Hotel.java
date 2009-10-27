package database;

import messages.*;

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
	public Message process_Message(Message i_msg){
		// Hotel class simply hands the input message to one of the 
		// Object-Handlers for processing
		Header head = i_msg.return_Header();
		if(head.to == Header.To.ACCOUNT){
			accountKeeper.processMessage((AccountMessage) i_msg);
		}
		if(head.to == Header.To.ROOM){
			roomKeeper.processMessage((RoomMessage) i_msg);
		}
	/*	if(head.to == Header.To.BOOKING){
			booking_keeper.processMessage((Booking_Message) i_msg);
		}
	 */
		return i_msg;
	}
	public String return_Name(){
		return hotelName;
	}
	

}
