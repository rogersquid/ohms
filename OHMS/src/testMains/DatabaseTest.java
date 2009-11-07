package testMains;

import java.sql.Date;
import java.util.*;

import models.messages.*;
import models.database.Hotel;
import java.sql.*;

public class DatabaseTest {
	public static void main(String [ ] args){
		System.out.println("\r");
		testBooking();
		//test_alladdRoom();
		//test_Extras(0);
		//test_Extras(1);
		// WILLIAM - THE CODE LOOKS GOOD AND WELL ORGANISED. TRY TO TAKE THE NAME OF THE HOTEL FROM ARGS
		// ONCE YOU HAVE THE DATABASE CHECK READY TOO, I THINK THIS WILL WORK WELL

		// test_alladdRoom();
		testBooking();

	}
	private static void test_Extras(int i_num){	
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
		System.out.println("Start Running Test for booking");
		Hotel myHotel= new Hotel("test");
		java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
		BookingMessage input= new BookingMessage(1, 1, "test", Header.Action.ADD);
		input.bookingID=10;
		input.startDate=date;
		input.duration=1;
		input.ownerID=1;
		input.roomID=1;
		input.status=0;
		BookingMessage reply=(BookingMessage) myHotel.processMessage(input);
		Header myHeader=reply.return_Header();
		System.out.println(myHeader.response_string);
	}
	
private static void test_all(){
	test_alladdRoom();
	test_alladdAcc();
	test_alleditAcc();
	test_alleditRoom();
	test_alldeleteRoom();
}
private static void test_alladdRoom(){
	System.out.println("Start Test Add Rooms \r");
	for (int i=0; i<5; i++){
		test_addRoom(i);
		}
	System.out.println("Finish Test Add Rooms \r");
}
private static void test_alladdAcc(){
	System.out.println("Start Test Add Accounts \r");
	for (int i=0; i<9; i++){
		test_addAcc(i);
		}
	System.out.println("Finish Test Add Accounts \r");
}
private static void test_alleditAcc(){
	System.out.println("Start Test Edit Accounts \r");
	for (int i=0; i<1; i++){
		test_editAcc(i);
		}
	System.out.println("Finish Test Edit Accounts \r");
}
private static void test_alleditRoom(){
	System.out.println("Start Test Edit Rooms \r");
	for (int i=0; i<1; i++){
		test_editRoom(i);
		}
	System.out.println("Finish Test Edit Rooms \r");
}
private static void test_alldeleteRoom(){
	System.out.println("Start Test Delete \r");
	for (int i=0; i<1; i++){
		test_deleteRoom(i);
		}
	System.out.println("Finish Test Delete \r");
}
private static void test_addRoom (int i_num){
	
	//=====================Add Rooms Tests==========================
	
	/*
	 * if(i_num==1){
			System.out.println("Test ID 1");
			//Account_Message h_msg= new Account_Message(0, 0, "Mert", Header.Action.ADD) ;
			h_msg.fill_All(1, "a", "Mert", "i_surname", "i_pw", true , "555", "a", "a");
			Hotel hotel= new Hotel("OHMS", "/tmp/var/");
			hotel.process_Message(h_msg);
			//Header head=h_msg.return_Header();
			//if(Header.Response.SUCCESS==head.response_code){
				System.out.println("Output was");
			}else{
				System.out.println("SUCCESS");
			}
		}
	 */
	if(i_num==0){
		System.out.println("Test ID 1");
		System.out.println("Description: Success Added room");
		int[] i = new int[7];
		i[0]=1;
		i[1]=1;
		i[2]=1;
		i[3]=1;
		i[4]=1;
		i[5]=1;
		i[6]=1;
		RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Single", 1000, 1, 500, true, true, i );
		Hotel hotel = new Hotel("OHMS");
		hotel.processMessage(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.SUCCESS==head.response_code){
		System.out.println("Passed Test ID 1");
		System.out.println("Input:");
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
	
	if(i_num==1){
		System.out.println("Test ID 2");
		System.out.println("Description: Failed to add room due to invalid room type");
		int[] i = new int[7];
		i[0]=1;
		i[1]=1;
		i[2]=1;
		i[3]=1;
		i[4]=1;
		i[5]=1;
		i[6]=1;
		RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "B!E1ST.E ST", 1000, 1, 500, true, true, i );
		Hotel hotel = new Hotel("OHMS");
		hotel.processMessage(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code){
		System.out.println("Passed Test ID 2");
		System.out.println("Input:");
		System.out.println("Output:");
		System.out.println("\r");
	}
	else{
		System.out.println("Failed Test ID 2");
		System.out.println("Expected Output:");
		System.out.println("Expected Change:");
		System.out.println("\r");
		}
	}
	
	if(i_num==2){
		System.out.println("Test ID 3");
		System.out.println("Description: Failed to add room due to invalid room spec");
		int[] i = new int[7];
		i[0]=1;
		i[1]=2;
		i[2]=999999;
		i[3]=1;
		i[4]=1;
		i[5]=1;
		i[6]=1;
		RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Suite", 1000, 1, 500, true, true, i );
		Hotel hotel = new Hotel("OHMS");
		hotel.processMessage(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code){
		System.out.println("Passed Test ID 3");
		System.out.println("Input:");
		System.out.println("Output:");
		System.out.println("\r");
	}
	else{
		System.out.println("Failed Test ID 3");
		System.out.println("Expected Output:");
		System.out.println("Expected Change:");
		System.out.println("\r");
		}
	}
	
	if(i_num==3){
		System.out.println("Test ID 4");
		System.out.println("Description: Failed to add room due to blank room type field");
		int[] i = new int[7];
		i[0]=1;
		i[1]=1;
		i[2]=1;
		i[3]=1;
		i[4]=1;
		i[5]=1;
		i[6]=1;
		RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "", 1000, 1, 500, true, true, i );
		Hotel hotel = new Hotel("OHMS");
		hotel.processMessage(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code){
		System.out.println("Passed Test ID 4");
		System.out.println("Input:");
		System.out.println("Output:");
		System.out.println("\r");
	}
	else{
		System.out.println("Failed Test ID 4");
		System.out.println("Expected Output:");
		System.out.println("Expected Change:");
		System.out.println("\r");
		}
	}
	
	if(i_num==4){
		System.out.println("Test ID 5");
		System.out.println("Description: Failed to add room due to room already existing");
		int[] i = new int[7];
		i[0]=1;
		i[1]=1;
		i[2]=1;
		i[3]=1;
		i[4]=1;
		i[5]=1;
		i[6]=1;
		RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Single", 1000, 1, 500, true, true, i );
		Hotel hotel = new Hotel("OHMS");
		RoomMessage g_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
		g_msg.fill_All(1, "Suite", 1000, 1, 500, true, true, i );
		hotel.processMessage(g_msg);
		Header head = h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code){
		System.out.println("Passed Test ID 5");
		System.out.println("Input:");
		System.out.println("Output:");
		System.out.println("\r");
	}
	else{
		System.out.println("Failed Test ID 5");
		System.out.println("Expected Output:");
		System.out.println("Expected Change:");
		System.out.println("\r");
		}
	}
}	
	
private static void test_addAcc (int i_num){
	//=====================Add Accounts Tests==========================
	if(i_num==0){
		System.out.println("Test ID 1");
		System.out.println("Description: Success Added account");
		AccountMessage h_msg = new AccountMessage(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "William", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS");
		hotel.processMessage(h_msg);
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
	
	if(i_num==1){
		System.out.println("Test ID 2");
		System.out.println("Description: Failed to Added account due to invalid Account Type");
		AccountMessage h_msg = new AccountMessage(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "!s@. #TZ", "William", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS");
		hotel.processMessage(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code){
			System.out.println("Passed Test ID 2");
			System.out.println("Input:");
			//h_msg.print_Middle();
			System.out.println("Output:");
			System.out.println("\r");
		}
		else{
			System.out.println("Failed Test ID 2");
			System.out.println("Expected Output:");
			System.out.println("Expected Change:");
			System.out.println("\r");
		}		
	}
	
	if(i_num==2){
		System.out.println("Test ID 3");
		System.out.println("Description: Failed to Added account due to invalid First Name");
		AccountMessage h_msg = new AccountMessage(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "G.E&*%s", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS");
		hotel.processMessage(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code){
			System.out.println("Passed Test ID 3");
			System.out.println("Input:");
			//h_msg.print_Middle();
			System.out.println("Output:");
			System.out.println("\r");
		}
		else{
			System.out.println("Failed Test ID 3");
			System.out.println("Expected Output:");
			System.out.println("Expected Change:");
			System.out.println("\r");
		}		
	}
	
	if(i_num==3){
		System.out.println("Test ID 4");
		System.out.println("Description: Failed to Added account due to invalid Surname");
		AccountMessage h_msg = new AccountMessage(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "William", "b%^.S", "passwd", true, "6047738298", "123 Fake Street", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS");
		hotel.processMessage(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code){
			System.out.println("Passed Test ID 4");
			System.out.println("Input:");
			//h_msg.print_Middle();
			System.out.println("Output:");
			System.out.println("\r");
		}
		else{
			System.out.println("Failed Test ID 4");
			System.out.println("Expected Output:");
			System.out.println("Expected Change:");
			System.out.println("\r");
		}		
	}
	
	if(i_num==4){
		System.out.println("Test ID 5");
		System.out.println("Description: Failed to Added account due to invalid password");
		AccountMessage h_msg = new AccountMessage(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "William", "Wong", "o/%h m.s", true, "6047738298", "123 Fake Street", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS");
		hotel.processMessage(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code){
			System.out.println("Passed Test ID 5");
			System.out.println("Input:");
			//h_msg.print_Middle();
			System.out.println("Output:");
			System.out.println("\r");
		}
		else{
			System.out.println("Failed Test ID 5");
			System.out.println("Expected Output:");
			System.out.println("Expected Change:");
			System.out.println("\r");
		}		
	}
	
	if(i_num==5){
		System.out.println("Test ID 6");
		System.out.println("Description: Failed to Added account due to invalid Address");
		AccountMessage h_msg = new AccountMessage(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "William", "Wong", "passwd", true, "6047738298", "Bo!ul.e%!@vard St.", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS");
		hotel.processMessage(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code){
			System.out.println("Passed Test ID 6");
			System.out.println("Input:");
			//h_msg.print_Middle();
			System.out.println("Output:");
			System.out.println("\r");
		}
		else{
			System.out.println("Failed Test ID 6");
			System.out.println("Expected Output:");
			System.out.println("Expected Change:");
			System.out.println("\r");
		}		
	}
	
	if(i_num==6){
		System.out.println("Test ID 7");
		System.out.println("Description: Failed to Added account due to invalid email");
		AccountMessage h_msg = new AccountMessage(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "William", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwon#g!gmai*l com");
		Hotel hotel = new Hotel("OHMS");
		hotel.processMessage(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code){
			System.out.println("Passed Test ID 7");
			System.out.println("Input:");
			//h_msg.print_Middle();
			System.out.println("Output:");
			System.out.println("\r");
		}
		else{
			System.out.println("Failed Test ID 7");
			System.out.println("Expected Output:");
			System.out.println("Expected Change:");
			System.out.println("\r");
		}		
	}
	
	if(i_num==7){
		System.out.println("Test ID 8");
		System.out.println("Description: Failed to Added account due to duplicate user");
		AccountMessage h_msg = new AccountMessage(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "William", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS");
		hotel.processMessage(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code){
		
		AccountMessage g_msg = new AccountMessage(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "William", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong@gmail.com");
		hotel.processMessage(g_msg);
		Header head1=g_msg.return_Header();
		if(Header.Response.FAIL==head1.response_code){
			System.out.println("Passed Test ID 8");
			System.out.println("Input:");
			//h_msg.print_Middle();
			System.out.println("Output:");
			System.out.println("\r");
		}
		else{
			System.out.println("Failed Test ID 8");
			System.out.println("Expected Output:");
			System.out.println("Expected Change:");
			System.out.println("\r");
		}
	}
		
		else{
			System.out.println("Failed Test ID 1 at verify");
		}
	
		}
	
	if(i_num==8){
		System.out.println("Test ID 9");
		System.out.println("Description: Failed to Added account due to multiple blank fields");
		AccountMessage h_msg = new AccountMessage(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "", "Wong", "passwd", true, "6047738298", "", "");
		Hotel hotel = new Hotel("OHMS");
		hotel.processMessage(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code){
			System.out.println("Passed Test ID 9");
			System.out.println("Input:");
			//h_msg.print_Middle();
			System.out.println("Output:");
			System.out.println("\r");
		}
		else{
			System.out.println("Failed Test ID 9");
			System.out.println("Expected Output:");
			System.out.println("Expected Change:");
			System.out.println("\r");
		}		
	}
}

private static void test_editAcc (int i_num){
	//=====================Edit Account Tests==========================
	if(i_num==0){
		System.out.println("Test ID 1");
		System.out.println("Description: Success Edit Account: First Name");
		
		AccountMessage h_msg = new AccountMessage(0,0,"OHMS", Header.Action.ADD);
		Hotel hotel = new Hotel("OHMS");
		h_msg.fill_All(1, "Staff", "William", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong@gmail.com");
		hotel.processMessage(h_msg);
		Header head = h_msg.return_Header();
		if(Header.Response.SUCCESS==head.response_code){
		AccountMessage g_msg = new AccountMessage(0,0,"OHMS", Header.Action.EDIT);
		g_msg.fill_All(1, "Staff", "Will", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong@gmail.com");
		hotel.processMessage(g_msg);
		Header head1 = g_msg.return_Header();
		if(Header.Response.SUCCESS==head1.response_code){
			System.out.println("Passed Test ID 1");
			System.out.println("Input:");
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
}


private static void test_editRoom (int i_num){
	//=====================Edit Room Tests==========================
	if(i_num==0){
		System.out.println("Test ID 1");
		System.out.println("Description: Success Edit Room: Price");
		
		int[] i = new int[3];
		i[0]=1;
		i[1]=1;
		i[2]=1;
		
		RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Single", 1000, 1, 500, true, true, i );
		Hotel hotel = new Hotel("OHMS");
		hotel.processMessage(h_msg);
		Header head = h_msg.return_Header();
		if(Header.Response.SUCCESS==head.response_code){
		
		RoomMessage g_msg = new RoomMessage(0, 0, "OHMS", Header.Action.EDIT);
		g_msg.fill_All(1, "Single", 1000, 1, 750, true, true, i);
		hotel.processMessage(g_msg);
		Header head1 = g_msg.return_Header();
		if(Header.Response.SUCCESS==head1.response_code){
			System.out.println("Passed Test ID 1");
			System.out.println("Input:");
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
}

private static void test_deleteRoom (int i_num){
	//=====================Delete Tests==========================
	if(i_num==0){
		System.out.println("Test ID 1");
		System.out.println("Description: Success Delete Room");
		int[] i = new int[3];
		i[0]=1;
		i[1]=1;
		i[2]=1;
		
		RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Single", 1000, 1, 500, true, true, i );
		Hotel hotel = new Hotel("OHMS");
		hotel.processMessage(h_msg);
		Header head = h_msg.return_Header();
		if(Header.Response.SUCCESS==head.response_code){
		
		RoomMessage g_msg = new RoomMessage(0,0,"OHMS", Header.Action.DELETE);

			hotel.processMessage(g_msg);
			Header head1 = g_msg.return_Header();
			if(Header.Response.SUCCESS==head1.response_code){
				System.out.println("Passed Test ID 1");
				System.out.println("Input:");
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
}/*

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
}



