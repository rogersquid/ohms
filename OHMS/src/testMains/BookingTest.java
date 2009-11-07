package testMains;

import java.io.*;

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
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	    String userName = null;

	      //  read the username from the command-line; need to use try/catch with the
	      //  readLine() method
	    try {
	    	userName = br.readLine();
	    	} catch (IOException ioe) {
	    		System.out.println("IO error trying to read your name!");
	    		System.exit(1);
	    	}
		input= new BookingMessage(1, 1, "test", Header.Action.ADD);
		input.startDate=date;
		input.duration=2;
		input.ownerID=1;
		input.roomID=1;
		input.status=0;
		reply=(BookingMessage) myHotel.processMessage(input);
		myHeader=reply.returnHeader();
		System.out.println(myHeader.responseCode);
		System.out.println(myHeader.responseString);
		
	    try {
	    	userName = br.readLine();
	    	} catch (IOException ioe) {
	    		System.out.println("IO error trying to read your name!");
	    		System.exit(1);
	    	}
		
		System.out.println("Start Running Test for booking");
		input= new BookingMessage(1, 1, "test", Header.Action.VIEW);
		input.bookingID=reply.bookingID;
		reply=(BookingMessage) myHotel.processMessage(input);
		myHeader=reply.returnHeader();
		System.out.println(myHeader.responseCode);
		System.out.println(myHeader.responseString);
		
	    try {
	    	userName = br.readLine();
	    	} catch (IOException ioe) {
	    		System.out.println("IO error trying to read your name!");
	    		System.exit(1);
	    	}
		
		System.out.println("Start Running Test for booking");
		input= new BookingMessage(1, 1, "test", Header.Action.DELETE);
		input.bookingID=reply.bookingID;
		reply=(BookingMessage) myHotel.processMessage(input);
		myHeader=reply.returnHeader();
		System.out.println(myHeader.responseCode);
		System.out.println(myHeader.responseString);
	}
}
