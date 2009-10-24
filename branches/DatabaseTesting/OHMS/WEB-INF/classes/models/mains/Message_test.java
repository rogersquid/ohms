package mains;

import messages.message_Helper.Header;
import messages.*;
import database.Hotel;

public class Message_test {
	public static void main(String [ ] args)
	{
		/*
		System_Brain brain=new System_Brain("test_data", "path");
		Account_Message a_message= new Account_Message(0, 0 , "test_data");
		System.err.print("testing process message");
		brain.process_Message(a_message);
*/
		System.out.println("\r");
		//test_all();
		test_alladdRoom();
		
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
		int[] i = new int[3];
		i[0]=1;
		i[1]=1;
		i[2]=1;
		Room_Message h_msg = new Room_Message(0, 0, "OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Single", 1000, 1, 500, true, true, i );
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
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
		int[] i = new int[3];
		i[0]=1;
		i[1]=1;
		i[2]=1;
		Room_Message h_msg = new Room_Message(0, 0, "OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "B!E1ST.E ST", 1000, 1, 500, true, true, i );
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
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
		int[] i = new int[3];
		i[0]=1;
		i[1]=2;
		i[2]=999999;
		Room_Message h_msg = new Room_Message(0, 0, "OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Suite", 1000, 1, 500, true, true, i );
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
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
		int[] i = new int[3];
		i[0]=1;
		i[1]=1;
		i[2]=1;
		Room_Message h_msg = new Room_Message(0, 0, "OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "", 1000, 1, 500, true, true, i );
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
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
		int[] i = new int[3];
		i[0]=1;
		i[1]=1;
		i[2]=1;
		Room_Message h_msg = new Room_Message(0, 0, "OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Single", 1000, 1, 500, true, true, i );
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		Room_Message g_msg = new Room_Message(0, 0, "OHMS", Header.Action.ADD);
		g_msg.fill_All(1, "Suite", 1000, 1, 500, true, true, i );
		hotel.process_Message(g_msg);
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
		Account_Message h_msg = new Account_Message(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "William", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong@gmail.com");
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
	
	if(i_num==1){
		System.out.println("Test ID 2");
		System.out.println("Description: Failed to Added account due to invalid Account Type");
		Account_Message h_msg = new Account_Message(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "!s@. #TZ", "William", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
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
		Account_Message h_msg = new Account_Message(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "G.E&*%s", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
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
		Account_Message h_msg = new Account_Message(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "William", "b%^.S", "passwd", true, "6047738298", "123 Fake Street", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
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
		Account_Message h_msg = new Account_Message(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "William", "Wong", "o/%h m.s", true, "6047738298", "123 Fake Street", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
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
		Account_Message h_msg = new Account_Message(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "William", "Wong", "passwd", true, "6047738298", "Bo!ul.e%!@vard St.", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
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
		Account_Message h_msg = new Account_Message(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "William", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwon#g!gmai*l com");
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
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
		Account_Message h_msg = new Account_Message(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "William", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code){
		
		Account_Message g_msg = new Account_Message(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "William", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong@gmail.com");
		hotel.process_Message(g_msg);
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
		Account_Message h_msg = new Account_Message(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "", "Wong", "passwd", true, "6047738298", "", "");
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
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
		
		Account_Message h_msg = new Account_Message(0,0,"OHMS", Header.Action.ADD);
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		h_msg.fill_All(1, "Staff", "William", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong@gmail.com");
		hotel.process_Message(h_msg);
		Header head = h_msg.return_Header();
		if(Header.Response.SUCCESS==head.response_code){
		Account_Message g_msg = new Account_Message(0,0,"OHMS", Header.Action.EDIT);
		g_msg.fill_All(1, "Staff", "Will", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong@gmail.com");
		hotel.process_Message(g_msg);
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
		
		Room_Message h_msg = new Room_Message(0, 0, "OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Single", 1000, 1, 500, true, true, i );
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
		Header head = h_msg.return_Header();
		if(Header.Response.SUCCESS==head.response_code){
		
		Room_Message g_msg = new Room_Message(0, 0, "OHMS", Header.Action.EDIT);
		g_msg.fill_All(1, "Single", 1000, 1, 750, true, true, i);
		hotel.process_Message(g_msg);
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
		
		Room_Message h_msg = new Room_Message(0, 0, "OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Single", 1000, 1, 500, true, true, i );
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
		Header head = h_msg.return_Header();
		if(Header.Response.SUCCESS==head.response_code){
		
		Room_Message g_msg = new Room_Message(0,0,"OHMS", Header.Action.DELETE);

			hotel.process_Message(g_msg);
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
	}	
}



