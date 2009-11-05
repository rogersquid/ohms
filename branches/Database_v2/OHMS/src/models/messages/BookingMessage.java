package models.messages;

import java.util.*;


public class BookingMessage extends Message{
	// attributes

	public int 				booking_id;
	public int 				owner_id;
	public Date				bookingDate;
	public Date 			startDate;
	public int 				duration;
	public int 				room_id;
	public int 				status; // 0= not yet checked in
									// 1= checked in // 2= checked out
	
	public BookingMessage(int i_id, int i_auth, String i_name_Hotel, Header.Action i_action){
		header=new Header (i_id, i_auth, i_name_Hotel, Header.To.BOOKING, i_action);
	}
	
	public void fillAll( int bID, int oID, Date bDate, Date sDate, int d, int rID, int s){
		booking_id=bID;
		owner_id=oID;
		bookingDate=bDate;
		startDate=sDate;
		duration=d;
		room_id=rID;
		status=s; // 0= n
	}
	
	public void print_All(){
		System.out.println("Booking_message is printing out");
		System.out.println("Rest:");
		System.out.println(booking_id);
		System.out.println(owner_id);
		System.out.println(duration);
		System.out.println(room_id);
		System.out.println(status);
		System.out.println("*******************");
	}
	
	@Override public String toString(){
		return "This is a Booking_Message";
	}
}
