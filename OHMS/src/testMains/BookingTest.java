package testMains;

import models.database.Hotel;
import models.messages.BookingMessage;
import models.messages.Header;

public class BookingTest {
	public static void main(String [ ] args){
		System.out.println("\r");
		testBooking();
	}
	private static void testBooking(){
		System.out.println("Start Running Test for Add booking");
		Hotel myHotel= new Hotel("test");
		BookingMessage input=null;
		BookingMessage reply=null;
		Header myHeader=null;
		java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
		
		input= new BookingMessage(1, 1, "test", Header.Action.ADD);
		input.startDate=date;
		input.duration=2;
		input.ownerID=1;
		input.roomID=1;
		input.status=0;
		reply=(BookingMessage) myHotel.processMessage(input);
		myHeader=reply.return_Header();
		System.out.println(myHeader.responseCode);
		System.out.println(myHeader.responseString);
		
		System.out.println("Start Running Test for booking");
		input= new BookingMessage(1, 1, "test", Header.Action.DELETE);
		input.bookingID=10;
		input.startDate= date;
		reply=(BookingMessage) myHotel.processMessage(input);
		myHeader=reply.return_Header();
		System.out.println(myHeader.responseCode);
		System.out.println(myHeader.responseString);
	}
}
