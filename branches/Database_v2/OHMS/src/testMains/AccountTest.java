package testMains;

import models.database.Hotel;
import models.messages.AccountMessage;
import models.messages.Header;

public class AccountTest {
	protected static void test_alladdAcc(){
		System.out.println("Start Test Add Accounts \r");
		for (int i=0; i<9; i++){
			test_addAcc(i);
			}
		System.out.println("Finish Test Add Accounts \r");
	}
	protected static void test_alleditAcc(){
		System.out.println("Start Test Edit Accounts \r");
		for (int i=0; i<1; i++){
			test_editAcc(i);
			}
		System.out.println("Finish Test Edit Accounts \r");
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

}
