package testMains;

import models.messages.*;
import models.database.Hotel;

public class DatabaseTest {
	public static void main(String [ ] args){
		System.out.println("\r");
		//testBooking();
		//AccountTest.test_alladdAcc();
		//AccountTest.test_alleditAcc();
		RoomTest.test_allRoom();
		//test_Extras(0);
		//test_Extras(1);
		// WILLIAM - THE CODE LOOKS GOOD AND WELL ORGANISED. TRY TO TAKE THE NAME OF THE HOTEL FROM ARGS
		// ONCE YOU HAVE THE DATABASE CHECK READY TOO, I THINK THIS WILL WORK WELL

		// test_alladdRoom();
		//testBooking();

	}
	private static void testBooking(){
		System.out.println("Start Running Test for Add booking");
		Hotel myHotel= new Hotel("test");
		java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
		BookingMessage input= new BookingMessage(1, 1, "test", Header.Action.ADD);
		input.startDate=date;
		input.duration=2;
		input.ownerID=1;
		input.roomID=1;
		input.status=0;
		BookingMessage reply=(BookingMessage) myHotel.processMessage(input);
		Header myHeader=reply.returnHeader();
		System.out.println(myHeader.responseCode);
		System.out.println(myHeader.responseString);
		
		System.out.println("Start Running Test for booking");
		input= new BookingMessage(1, 1, "test", Header.Action.ADD);
		input.ownerID=1;
		input.startDate= date;
		reply=(BookingMessage) myHotel.processMessage(input);
		myHeader=reply.returnHeader();
		System.out.println(myHeader.responseCode);
		System.out.println(myHeader.responseString);
	}
}
/*	private static void test_Extras(int i_num){	
		if (i_num==0){
			ExtraMessage h_msg = new ExtraMessage(0, 0, 0, "test", Header.Action.ADD);
			java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
			h_msg.fillAll(date,4, "TV", 700);
			Hotel hotel = new Hotel("test");
			hotel.processMessage(h_msg);
			Header head=h_msg.return_Header();
			if(Header.Response.SUCCESS==head.response_code){
			System.out.println("Passed Test ID 1");
			System.out.println("\r");
		}
		else{
			System.out.println("Failed Test ID 1");
			System.out.println("\r");
		}
	}
		if (i_num==1){
			ExtraMessage h_msg = new ExtraMessage(0, 0, 0, "test", Header.Action.ADD);
			java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
			h_msg.fillAll(date, 5, "TV", 700);
			Hotel hotel = new Hotel("test");
			hotel.processMessage(h_msg);
			
			ExtraMessage g_msg = new ExtraMessage(0, 0, 0, "test", Header.Action.DELETE);
			java.sql.Date date1 = new java.sql.Date(new java.util.Date().getTime());
			h_msg.fillAll(date1, 5, "TV", 700);
			hotel.processMessage(g_msg);
			Header head=h_msg.return_Header();
			if(Header.Response.SUCCESS==head.response_code){
			System.out.println("Passed Test ID 2");
			System.out.println("\r");
			}
		else{
			System.out.println("Failed Test ID 2");
			System.out.println("\r");
			}
		}
		
	}
	private static void testBooking(){
		System.out.println("Start Running Test for Add booking");
		Hotel myHotel= new Hotel("test");
		java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
		BookingMessage input= new BookingMessage(1, 1, "test", Header.Action.ADD);
		input.startDate=date;
		input.duration=2;
		input.ownerID=1;
		input.roomID=1;
		input.status=0;
		BookingMessage reply=(BookingMessage) myHotel.processMessage(input);
		Header myHeader=reply.return_Header();
		System.out.println(myHeader.response_code);
		System.out.println(myHeader.response_string);
		
		System.out.println("Start Running Test for booking");
		input= new BookingMessage(1, 1, "test", Header.Action.ADD);
		input.ownerID=1;
		input.startDate= date;
		reply=(BookingMessage) myHotel.processMessage(input);
		myHeader=reply.return_Header();
		System.out.println(myHeader.response_code);
		System.out.println(myHeader.response_string);
	}
private static void test_all(){
	
	AccountTest.test_alladdAcc();
	AccountTest.test_alleditAcc();
	RoomTest.test_allRoom();
}

/*
private static void test_addBook (int i_num){
	//=====================Add Bookings Tests==========================
	if(i_num==0){
		System.out.println("Test ID 1");
		System.out.println("Description: Success Added Booking");
		Booking_Message h_msg = new Booking_Message(0,0,"OHMS", Header.Action.ADD);
		//h_msg.fill_All
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.SUCCESS==head.response_code){
			System.out.println("Passed Test ID 1");
			System.out.println("Input:");
			//h_msg.print_Middle();
			System.out.println("Output:");
			System.out.println("\r");
		}
		else{
			System.out.println("Failed Test ID 1");
			System.out.println("Expected Output:");
			System.out.println("Expected Change:");
			System.out.println("\r");
		}		
	}	
	else{
		System.out.println("Failed Test ID 1 at verify");
		}
}



private static void test_editBook (int i_num){
	//=====================Edit Bookings Tests==========================
	if(i_num==0){
		System.out.println("Test ID 1");
		System.out.println("Description: Success Edit Booking");
		Booking_Message h_msg = new Booking_Message(0,0,"OHMS", Header.Action.ADD);
		//h_msg.fill_All
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
		Header head = h_msg.return_Header();
		
		Booking_Message g_msg = new Booking_Message (0,0,"OHMS", Header.Action.EDIT);
		//g.msg.fill_All
		hotel.process_Message(g_msg);
		Header head1 = g_msg.return_Header();
		if(Header.Response.SUCCESS==head1.response_code){
			System.out.println("Passed Test ID 1");
			System.out.println("Input:");
			//h_msg.print_Middle();
			System.out.println("Output:");
			System.out.println("\r");
		}
		else{
			System.out.println("Failed Test ID 1");
			System.out.println("Expected Output:");
			System.out.println("Expected Change:");
			System.out.println("\r");
		}		
	}	

}

private static void test_deleteBook (int i_num){
	//=====================Edit Bookings Tests==========================
	if(i_num==0){
		System.out.println("Test ID 1");
		System.out.println("Description: Success Delete Booking");
		Booking_Message h_msg = new Booking_Message(0,0,"OHMS", Header.Action.ADD);
		//h_msg.fill_All
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
		Header head = h_msg.return_Header();
		if(Header.Response.SUCCESS==head.response_code){
		Booking_Message g_msg = new Booking_Message (0,0,"OHMS", Header.Action.DELETE);
		//g.msg.fill_All
		hotel.process_Message(g_msg);
		Header head1 = g_msg.return_Header();
		if(Header.Response.SUCCESS==head1.response_code){
			System.out.println("Passed Test ID 1");
			System.out.println("Input:");
			//h_msg.print_Middle();
			System.out.println("Output:");
			System.out.println("\r");
		}
		else{
			System.out.println("Failed Test ID 1");
			System.out.println("Expected Output:");
			System.out.println("Expected Change:");
			System.out.println("\r");
		}
		}
		else{
				System.out.println("Failed Test ID 1 at verify");
				}
		}
	}	*/



