package testMains;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import models.database.Hotel;
import models.messages.BookingMessage;
import models.messages.Header;
import models.messages.Message;

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
	    DateFormat df= new SimpleDateFormat("dd/MM/yyyy");
	    java.sql.Date endDate;
		try {
			endDate = new java.sql.Date(df.parse("22/02/2010").getTime());
			input= new BookingMessage(1, 1, "test", Header.Action.ADD);
			input.startDate=date;
			input.endDate=endDate;
			input.ownerID=1;
			input.roomID=1;
			input.status=0;
			reply=(BookingMessage) myHotel.processMessage(input);
			myHeader=reply.returnHeader();
			System.out.println(myHeader.responseCode);
			System.out.println(myHeader.responseString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
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
		input= new BookingMessage(1, 1, "test", Header.Action.VIEWALL);
		input.bookingID=reply.bookingID;
		Message[] replyArray=myHotel.processMessageReturnBunch(input);
		System.out.print(replyArray.length);
		myHeader=replyArray[0].returnHeader();
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
