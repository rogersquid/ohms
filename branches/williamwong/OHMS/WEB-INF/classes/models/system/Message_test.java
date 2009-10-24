package mains;

import messages.*;
import messages.Message.Response;
import database.Hotel;

public class Message_test {
	public static void main(String [ ] args)
	{
		System_Brain brain=new System_Brain("test_data", "path");
		Add_Account_Message a_message= new Add_Account_Message(0, 0 , "test_data");
		System.err.print("testing process message");
		brain.process_Message(a_message);

		System.out.println("\r");
		//test_all();
		test_alladdRoom();
		
	}
	
private static void test_all(){
	test_alladdRoom();
	test_alladdAcc();
	test_alleditAcc();
	test_alleditRoom();
	test_alldelete();
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

private static void test_alldelete(){
	System.out.println("Start Test Delete \r");
	for (int i=0; i<1; i++){
		test_delete(i);
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
		if(Header.Response.SUCCESS==head.response_code()){
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
		if(Header.Response.FAIL==head.response_code()){
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
		if(Header.Response.FAIL==head.response_code()){
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
		if(Header.Response.FAIL==head.response_code()){
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
		Add_Room_Message g_msg = new Add_Room_Message(0, 0, "OHMS");
		g_msg.fill_All(1, "Suite", 1000, 1, 500, true, true, i );
		hotel.process_Message(g_msg);
		Header head = h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code()){
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
		h_msg.fill_All(1, "Staff", "William", "Wong", "passwd", true, 7738298, "123 Fake Street", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.SUCCESS==head.response_code()){
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
		h_msg.fill_All(1, "!s@. #TZ", "William", "Wong", "passwd", true, 7738298, "123 Fake Street", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code()){
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
		h_msg.fill_All(1, "Staff", "G.E&*%s", "Wong", "passwd", true, 7738298, "123 Fake Street", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code()){
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
		h_msg.fill_All(1, "Staff", "William", "b%^.S", "passwd", true, 7738298, "123 Fake Street", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code()){
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
		h_msg.fill_All(1, "Staff", "William", "Wong", "o/%h m.s", true, 7738298, "123 Fake Street", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code()){
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
		h_msg.fill_All(1, "Staff", "William", "Wong", "passwd", true, 7738298, "Bo!ul.e%!@vard St.", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code()){
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
		h_msg.fill_All(1, "Staff", "William", "Wong", "passwd", true, 7738298, "123 Fake Street", "wwon#g!gmai*l com");
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code()){
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
		h_msg.fill_All(1, "Staff", "William", "Wong", "passwd", true, 7738298, "123 Fake Street", "wwong@gmail.com");
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
		Account_Message g_msg = new Account_Message(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "William", "Wong", "passwd", true, 7738298, "123 Fake Street", "wwong@gmail.com");
		hotel.process_Message(g_msg);
		Header head=g_msg.return_Header();
		if(Header.Response.FAIL==head.response_code()){
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
	
	if(i_num==8){
		System.out.println("Test ID 9");
		System.out.println("Description: Failed to Added account due to multiple blank fields");
		Account_Message h_msg = new Account_Message(0,0,"OHMS", Header.Action.ADD);
		h_msg.fill_All(1, "Staff", "", "Wong", "passwd", true, 7738298, "", "");
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
		Header head=h_msg.return_Header();
		if(Header.Response.FAIL==head.response_code()){
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
		h_msg.fill_All(1, "Staff", "William", "Wong", "passwd", true, 7738298, "123 Fake Street", "wwong@gmail.com");
		hotel.process_Message(h_msg);
		Header head = h_msg.return_Header();
		Account_Message g_msg = new Account_Message(0,0,"OHMS", Header.Action.EDIT);
		g_msg.fill_All(1, "Staff", "Will", "Wong", "passwd", true, 7738298, "123 Fake Street", "wwong@gmail.com");
		hotel.process_Message(g_msg);
		Header head = g_msg.return_Header();
		if(Header.Response.SUCCESS==head.response_code()){
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
		
		Add_Room_Message h_msg = new Add_Room_Message(0, 0, "OHMS");
		h_msg.fill_All(1, "Single", 1000, 1, 500, true, true, i );
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
		/*
		Edit_Room_Message j_msg = new Edit_Room_Message(0,0,"OHMS");
		if(j_msg.fill_input(1)){
		*/
		Edit_Room_Message g_msg = new Edit_Room_Message(0,0,"OHMS");
		g_msg.fill_All(1, "Single", 1000, 1, 750, true, true, i); //dont know why it doesnt work here but works with EditRoom
		hotel.process_Message(g_msg);
		
		if(Response.SUCCESS==g_msg.return_Response_Code()){
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

private static void test_delete (int i_num){
	//=====================Delete Tests==========================
	if(i_num==0){
		System.out.println("Test ID 1");
		System.out.println("Description: Success Delete Room");
		int[] i = new int[3];
		i[0]=1;
		i[1]=1;
		i[2]=1;
		
		Add_Room_Message h_msg = new Add_Room_Message(0, 0, "OHMS");
		h_msg.fill_All(1, "Single", 1000, 1, 500, true, true, i );
		Hotel hotel = new Hotel("OHMS", "/tmp/var/");
		hotel.process_Message(h_msg);
		
		View_Delete_Room_Message g_msg = new View_Delete_Room_Message(0,0,"OHMS", true);
		if(g_msg.fill_input(1)){
			hotel.process_Message(g_msg);
			if(Response.SUCCESS==g_msg.return_Response_Code()){
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
}


