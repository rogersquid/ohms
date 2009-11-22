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
	    DateFormat df= new SimpleDateFormat("dd/MM/yyyy");
	    java.sql.Date endDate;
	    System.out.println("Trying ADD BOOKING *********************");
		try {
			endDate = new java.sql.Date(df.parse("22/02/2010").getTime());
			input= new Message(1, 99, "test");
			input.initializeBookings(1);
			input.bookings[0].startDate=date;
			input.bookings[0].endDate=endDate;
			input.bookings[0].ownerID=99;
			input.bookings[0].roomID=1;
			input.bookings[0].status=0;
			reply= mybooking.addBooking(input);
			System.out.println(reply.response.responseCode);
			System.out.println(reply.response.responseString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		System.out.println("Trying GET BOOKING *********************");
		input= new Message(1, 99, "test");
		input.initializeBookings(1);
		input.bookings[0].bookingID=reply.bookings[0].bookingID;
		System.out.println(reply.bookings[0].bookingID);
		reply=mybooking.getBooking(input);
		System.out.println(reply.response.responseCode);
		System.out.println(reply.response.responseString);
		System.out.println(reply.bookings[0].bookingID);
		
		System.out.println("trying EDIT BOOKING *********************");
		input= new Message(1, 99, "test");
		input.initializeBookings(1);
		input.bookings[0].bookingID=reply.bookings[0].bookingID;
		try {
			endDate = new java.sql.Date(df.parse("22/04/2010").getTime());
			input.bookings[0].bookingID=reply.bookings[0].bookingID;
			input.bookings[0].startDate=date;
			input.bookings[0].endDate=endDate;
			input.bookings[0].ownerID=99;
			input.bookings[0].roomID=99;
			input.bookings[0].status=0;
			reply=mybooking.editBooking(input);
			System.out.println(reply.response.responseCode);
			System.out.println(reply.response.responseString);
			System.out.println(reply.bookings[0].status);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Try Check in Booking ******************");
		input= new Message(1, 99, "test");
		input.initializeBookings(1);
		input.bookings[0].bookingID=reply.bookings[0].bookingID;
		reply=mybooking.checkIn(input);
		System.out.println(reply.response.responseCode);
		System.out.println(reply.response.responseString);
		
	    try {
	    	userName = br.readLine();
	    	} catch (IOException ioe) {
	    		System.out.println("IO error trying to read your name!");
	    		System.exit(1);
	    	}
		System.out.println("Try Delete Booking ******************");
		input= new Message(1, 99, "test");
		input.initializeBookings(1);
		input.bookings[0].bookingID=reply.bookings[0].bookingID;
		reply=mybooking.deleteBooking(input);
		System.out.println(reply.response.responseCode);
		System.out.println(reply.response.responseString);
		
		System.out.println("Try Get ALL Booking ******************");
		input= new Message(1, 99, "test");
		input.initializeBookings(1);
		reply=mybooking.getAllBooking(input);
		System.out.println(reply.response.responseCode);
		System.out.println(reply.response.responseString);
		System.out.println(reply.bookings[reply.bookings.length-1].bookingID);
		System.out.println(reply.bookings.length);
		
		System.out.println("Try Get Filtered Booking-- Filter is Date Range******************");
		input= new Message(1, 99, "test");
		input.initializeBookings(1);
		try {
			endDate = new java.sql.Date(df.parse("30/11/2010").getTime());
			input.bookings[0].ownerID=99;
			reply=mybooking.getFilteredBooking(input);
			System.out.println(reply.response.responseCode);
			System.out.println(reply.response.responseString);
			System.out.println(reply.bookings[(reply.bookings.length)-1].bookingID);
			System.out.println(reply.bookings.length);
			System.out.println("Testing Done ******************");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Try Get Filtered Booking-- Filter is Date Range******************");
		input= new Message(1, 99, "test");
		input.initializeBookings(1);
		try {
			endDate = new java.sql.Date(df.parse("30/11/2010").getTime());
			input.bookings[0].startDate=date;
			input.bookings[0].endDate=endDate;
			reply=mybooking.getFilteredBooking(input);
			
			System.out.println(reply.response.responseCode);
			System.out.println(reply.response.responseString);
			System.err.println("------------------------" +(reply.rooms));
			System.out.println(reply.rooms[(reply.rooms.length)-1].roomID);
			System.out.println(reply.rooms.length);
			System.out.println("Testing Done ******************");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
