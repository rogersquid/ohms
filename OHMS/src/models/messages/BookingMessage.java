package models.messages;

import java.util.*;

public class BookingMessage extends Message{
	public int 				bookingID;
	public int 				ownerID;
	public Date				bookingDate;
	public Date 			startDate;
	public int 				duration;
	public int 				roomID;
	public int 				status; // 0= not yet checked in
									// 1= checked in // 2= checked out
	public BookingMessage(int mOID, int i_auth, String i_name_Hotel, Header.Action i_action){
		header=new Header (mOID, i_auth, i_name_Hotel, Header.To.BOOKING, i_action);
	}
	public void fillAll( int bID, int oID, Date bDate, Date sDate, int d, int rID, int s){
		bookingID=bID;
		ownerID=oID;
		bookingDate=bDate;
		startDate=sDate;
		duration=d;
		roomID=rID;
		status=s; // 0= n
	}
	public void print_All(){
		System.out.println("Booking_message is printing out");
		System.out.println("Rest:");
		System.out.println(bookingID);
		System.out.println(ownerID);
		System.out.println(duration);
		System.out.println(roomID);
		System.out.println(status);
		System.out.println("*******************");
	}
}
