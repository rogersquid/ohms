package testMains;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import models.messages.*;
import models.database.*;

public class BookingTest {
	public static void main(String [ ] args){
		System.out.println("\r");
		testBooking();
		//testCheckIn();
	}
	private static void testBooking(){
		System.out.println("Start Running Test for Add booking");
		Message input=null;
		Message reply=null;
		Booking mybooking = new Booking();
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
			input= new Message(99, 1, "test");
			input.initializeBookings(1);
			input.bookings[0].startDate=date;
			input.bookings[0].endDate=endDate;
			input.bookings[0].ownerID=1;
			input.bookings[0].roomID=1;
			input.bookings[0].status=0;
			reply= mybooking.addBooking(input);
			System.out.println(reply.response.responseCode);
			System.out.println(reply.response.responseString);
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
		System.out.println("Start Running Test for get booking");
		input= new Message(1, 1, "test");
		input.initializeBookings(1);
		input.bookings[0].bookingID=reply.bookings[0].bookingID;
		reply=mybooking.getBooking(input);
		System.out.println(reply.response.responseCode);
		System.out.println(reply.response.responseString);
		
		try {
	    	userName = br.readLine();
	    	} catch (IOException ioe) {
	    		System.out.println("IO error trying to read your name!");
	    		System.exit(1);
	    	}
		System.out.println("Start Running Test for booking");
		input= new Message(1, 1, "test");
		input.bookings[0].bookingID=reply.bookings[0].bookingID;
		reply=mybooking.getBooking(input);
		System.out.println(reply.response.responseCode);
		System.out.println(reply.response.responseString);
		
	    try {
	    	userName = br.readLine();
	    	} catch (IOException ioe) {
	    		System.out.println("IO error trying to read your name!");
	    		System.exit(1);
	    	}
		System.out.println("Start Running Test for booking");
		input= new Message(1, 1, "test");
		input.bookings[0].bookingID=reply.bookings[0].bookingID;
		reply=mybooking.deleteBooking(input);
		System.out.println(reply.response.responseCode);
		System.out.println(reply.response.responseString);
	}
	/*
	private static void testCheckIn(){
		System.out.println("Running Test for Checkin");
	
		Hotel myHotel= new Hotel("test");
		Message input= null;
		Message reply=null;
		Header myHeader=null;

		input= new Message(0, 0, "test", Header.Action.CHECKIN);
		
		System.out.println("Test ID 1");
		System.out.println("Description: Success Checkin room");
		input.bookingID = 41;
		reply = (Message) myHotel.processMessage(input);
		myHeader=reply.returnHeader();
		System.out.println(myHeader.responseCode);
		System.out.println(myHeader.responseString);
		System.out.println("\r");
		
		System.out.println("Test ID 2");
		System.out.println("Description: Failed Checkin room due to invalid Booking Id");
		input.bookingID = -42;
		reply = (Message) myHotel.processMessage(input);
		myHeader=reply.returnHeader();
		System.out.println(myHeader.responseCode);
		System.out.println(myHeader.responseString);
		System.out.println("\r");
		
		System.out.println("Test ID 3");
		System.out.println("Description: Failed Checkin room due to checkin being too late");
		input.bookingID = 43;
		reply = (Message) myHotel.processMessage(input);
		myHeader=reply.returnHeader();
		System.out.println(myHeader.responseCode);
		System.out.println(myHeader.responseString);
		System.out.println("\r");
		
		System.out.println("Test ID 4");
		System.out.println("Description: Failed Checkin room due to checkin being too early");
		input.bookingID = 44;
		reply = (Message) myHotel.processMessage(input);
		myHeader=reply.returnHeader();
		System.out.println(myHeader.responseCode);
		System.out.println(myHeader.responseString);	
		System.out.println("\r");
		
		
		
	
	}*/
}
