package models.database;

import models.messages.*;

public class BookingKeeper {
	public Message processMessage(BookingMessage i_msg){
		switch(i_msg.returnHeader().action)
		{
		case ADD:
			return add(i_msg);
		case EDIT:
			return edit(i_msg);
		case VIEW:
			return view(i_msg);
		case DELETE:
			return delete(i_msg);
		case CHECKIN:
			return checkIn(i_msg);
		default:
			//needs implementation
			return i_msg;
		}
	}
	public Message[] processMessageReturnBunch(BookingMessage i_msg){
		BookingMessage[] reply=null;
		switch(i_msg.returnHeader().action)
		{
		case VIEWALL:
			return viewAll(i_msg);
		default:
			//needs implementation
			return reply;
		}
	}
	private BookingMessage add(BookingMessage i_msg){
		Booking aBooking = new Booking();
		return 	aBooking.addBooking(i_msg);
	}
	private BookingMessage edit(BookingMessage i_msg){
		Booking aBooking = new Booking();
		return 	aBooking.editBooking(i_msg);
	}
	private BookingMessage delete(BookingMessage i_msg){
		Booking aBooking = new Booking();
		return 	aBooking.deleteBooking(i_msg);
	}
	private BookingMessage view(BookingMessage i_msg){
		Booking aBooking = new Booking();
		return 	aBooking.viewBooking(i_msg);
	}
	private BookingMessage[] viewAll(BookingMessage i_msg){
		Booking aBooking = new Booking();
		return 	aBooking.viewAllBooking(i_msg);
	}
	private BookingMessage checkIn(BookingMessage i_msg){
		Booking aBooking = new Booking();
		return 	aBooking.checkIn(i_msg);
	}
}
