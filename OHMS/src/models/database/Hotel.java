package models.database;

import models.messages.*;

public class Hotel {
	String				hotelName;
	AccountKeeper		accountKeeper;
	RoomKeeper			roomKeeper;
	BookingKeeper		bookingKeeper;
	
	public Hotel(String i_name){
		hotelName		= i_name.toLowerCase();
		accountKeeper	= new AccountKeeper();
		roomKeeper	 	= new RoomKeeper();
		bookingKeeper 	= new BookingKeeper();
	}
	public Message processMessage(Message i_msg){
		// Hotel class simply hands the input message to one of the 
		// Object-Handlers for processing
		System.out.println("Inside Hotel");
		Message reply=null;
		Header head = i_msg.return_Header();
		if(head.to == Header.To.ACCOUNT){
			reply	= accountKeeper.processMessage((AccountMessage) i_msg);
		}
		if(head.to == Header.To.ROOM){
			reply	= roomKeeper.processMessage((RoomMessage) i_msg);
		}
		if(head.to == Header.To.BOOKING){
			reply = bookingKeeper.processMessage((BookingMessage) i_msg);
		}
		return reply;
	}
	public Message[] processMessageReturnBunch(Message i_msg){
		// Hotel class simply hands the input message to one of the 
		// Object-Handlers for processing
		Message reply[]=null;
		Header head = i_msg.return_Header();
		if(head.to == Header.To.ACCOUNT){
			reply	= accountKeeper.processMessageReturnBunch((AccountMessage) i_msg);
		}
		if(head.to == Header.To.ROOM){
			reply	= roomKeeper.processMessageReturnBunch((RoomMessage) i_msg);
		}
		if(head.to == Header.To.BOOKING){
			reply	= bookingKeeper.processMessageReturnBunch((BookingMessage) i_msg);
		}
		return reply;
	}
	public String returnName(){
		return hotelName;
	}
	

}
