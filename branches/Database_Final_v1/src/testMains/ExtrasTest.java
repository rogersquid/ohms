package testMains;

import models.database.*;
import models.messages.*;


public class ExtrasTest {
	public static void main(String [ ] args){
		System.out.println("\r");
		test_allExtra();
	}
	
	public static void test_allExtra(){
		System.out.println("Start Test Extras \r");
		for (int i=0; i<1; i++){
			test_addExtra(i);
		}
		for (int j=0; j<1; j++){
			test_editExtra(j);
		}
		for (int k=0; k<1; k++){
			test_deleteExtra(k);
		}
		for (int l=0; l<1; l++){
			test_viewExtra(l);
		}
		for (int m=0; m<1; m++){
			test_viewAllExtra(m);
		}
		
		System.out.println("Finish Test Extras \r");
	}
	
	private static void test_addExtra (int i_num){
		
		//=====================Extras Tests==========================
		
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success Added Extra");

			//Filling Header information
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeExtras(1);
			h_msg.extras[0] = new ExtraMessage();
			//fill Extra info:
			java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
			java.sql.Timestamp creationDate=new java.sql.Timestamp(new java.util.Date().getTime());
			h_msg.extras[0].fillAll(1, 2, "Beer", 10, date, creationDate);
			//h_msg.response = h_msg.Extras[0].validateParam(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				Extra Extra = new Extra();
				h_msg = Extra.addExtra(h_msg);
			}
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("Passed Test ID 1");
				System.out.println("Input:");
				System.out.println("Output: " + h_msg.response.responseString);
				System.out.println("\r");
			} else{
				System.out.println("Failed Test ID 1");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}		
	}	


	private static void test_editExtra(int i_num){
		java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
		java.sql.Timestamp creationDate=new java.sql.Timestamp(new java.util.Date().getTime());
		//=====================Edit Extra Tests==========================
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success Edit Extra: Price");
			//Filling Header information
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeExtras(1);
			h_msg.extras[0] = new ExtraMessage();
			//fill Extra info:
			h_msg.extras[0].fillAll(1, 2, "Beer", 1000, date, creationDate);
			
			Extra Extra = new Extra();
			//h_msg.response = h_msg.extras[0].validateParam(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				h_msg = Extra.addExtra(h_msg);
			}
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				Message g_msg= new Message(0, 0, "test");
				g_msg.initializeExtras(1);
				g_msg.extras[0] = new ExtraMessage();
				//fill Extra info:
				//test_fillExtra(g_msg, t_set, i_num);
				g_msg.extras[0].extraID = h_msg.extras[0].extraID;
				g_msg.extras[0].price = (float)199.99;
				//g_msg.response = h_msg.extras[0].validateParam(h_msg);
				if (g_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
					g_msg = Extra.editExtra(g_msg);
				}
				if(g_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
					System.out.println("Passed Test ID 1");
					System.out.println("Input:");
					System.out.println("Output: " + g_msg.response.responseString);
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 1");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + g_msg.response.responseString);
					System.out.println("\r");
					}
				}
			else{
				System.out.println("Failed Test ID 1 at verify");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}
	}

	private static void test_deleteExtra (int i_num){
		java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
		java.sql.Timestamp creationDate=new java.sql.Timestamp(new java.util.Date().getTime());
		//=====================Delete Tests==========================
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success Delete Extra");
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeExtras(1);
			h_msg.extras[0] = new ExtraMessage();
			//fill Extra info:
			h_msg.extras[0].fillAll(1, 2, "Beer", 1000, date, creationDate);
			Extra Extra = new Extra();
			//h_msg.response = h_msg.extras[0].validateParam(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				h_msg = Extra.addExtra(h_msg);
			} 
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				Message g_msg= new Message(0, 0, "test");
				g_msg.initializeExtras(1);
				g_msg.extras[0] = new ExtraMessage();
				//fill Extra info:
				h_msg.extras[0].fillAll(1, 2, "Beer", 1000, date, creationDate);
				g_msg.extras[0].extraID = h_msg.extras[0].extraID;
				g_msg = Extra.deleteExtra(g_msg);
				if(g_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
					System.out.println("Passed Test ID 1");
					System.out.println("Input:");
					System.out.println("Output: " + g_msg.response.responseString);
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 1");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + g_msg.response.responseString);
					System.out.println("\r");
					}
			} else{
				System.out.println("Failed Test ID 1 at verify");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}
		}

	private static void test_viewExtra (int i_num){
		java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
		java.sql.Timestamp creationDate=new java.sql.Timestamp(new java.util.Date().getTime());
		//=====================View Tests==========================
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success View Extra");
			System.out.println("   Test case depends on success addition of Extra 101");
			
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeExtras(1);
			h_msg.extras[0] = new ExtraMessage();
			//fill Extra info:
			h_msg.extras[0].fillAll(1, 2, "Beer", 1000, date, creationDate);
			Extra Extra = new Extra();
			h_msg = Extra.getExtra(h_msg);
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("Passed Test ID 1");
				System.out.println("Input: " + h_msg.extras[0].extraID);
				System.out.println("Output: ");
				test_printExtra(h_msg.extras[0]);
				
				System.out.println("\r");
			}
			else{
				System.out.println("Failed Test ID 1");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}		
	}

	private static void test_viewAllExtra (int i_num){
		java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
		java.sql.Timestamp creationDate=new java.sql.Timestamp(new java.util.Date().getTime());
		//=====================View All Tests==========================
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success View All Extra");
			System.out.println("   Test case depends on success addition of Extra 101");
			
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeExtras(1);
			h_msg.extras[0] = new ExtraMessage();
			//fill Extra info:
			h_msg.extras[0].fillAll(1, 2, "Beer", 1000, date, creationDate);
			Extra Extra = new Extra();
			h_msg = Extra.getAllExtra(h_msg);
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("Passed Test ID 1");
				System.out.println("Input: " + h_msg.extras[0].extraID);
				System.out.println("Output: # of Extras: " + h_msg.extras.length);
				for (int i = 0; i < h_msg.extras.length; i ++) {
					test_printExtra(h_msg.extras[i]);
				}
				
				System.out.println("\r");
			}
			else{
				System.out.println("Failed Test ID 1");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}

		if(i_num==1){
			System.out.println("Test ID 2");
			System.out.println("Description: Success View all Extra available");
			
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeExtras(2);
			//fill filter toggle Extra Message
			h_msg.extras[0] = new ExtraMessage();
			h_msg.extras[0].extraID = 2;
			h_msg.extras[0].bookingID = 3;
			h_msg.extras[0].extraName = "Beer";
			h_msg.extras[0].price = 10;
			h_msg.extras[0].date = date;
			h_msg.extras[0].creationDate = creationDate;
			
			//fill filter value extra Message
			h_msg.extras[1] = new ExtraMessage();
			h_msg.extras[1].extraID = 3;
			h_msg.extras[1].bookingID = 40;
			h_msg.extras[1].extraName = "Wine";
			h_msg.extras[1].price = 100;
			h_msg.extras[1].date = date;
			h_msg.extras[1].creationDate = creationDate;
			
			Extra Extra = new Extra();
			//h_msg.response = h_msg.extras[0].validateParam(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				h_msg = Extra.getFilteredExtra(h_msg);
			} 
			
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("Passed Test ID 2");
				System.out.println("Input: " + h_msg.extras[0].extraID);
				System.out.println("Output: # of extras: " + h_msg.extras.length);
				for (int i = 0; i < h_msg.extras.length; i ++) {
					test_printExtra(h_msg.extras[i]);
				}
				
				System.out.println("\r");
			}
			else{
				System.out.println("Failed Test ID 2");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}

		if(i_num==2){
			System.out.println("Test ID 3");
			System.out.println("Description: Success View all Single extra on the 1st floor");
			
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeExtras(2);
			//fill filter toggle extra Message
			h_msg.extras[0] = new ExtraMessage();
			h_msg.extras[0].extraID = 2;
			h_msg.extras[0].bookingID = 3;
			h_msg.extras[0].extraName = "Beer";
			h_msg.extras[0].price = 10;
			h_msg.extras[0].date = date;
			h_msg.extras[0].creationDate = creationDate;
			
			//fill filter value extra Message
			h_msg.extras[1] = new ExtraMessage();
			h_msg.extras[1].extraID = 3;
			h_msg.extras[1].bookingID = 40;
			h_msg.extras[1].extraName = "Wine";
			h_msg.extras[1].price = 100;
			h_msg.extras[1].date = date;
			h_msg.extras[1].creationDate = creationDate;
			
			Extra Extra = new Extra();
			//h_msg.response = h_msg.extras[0].validateParam(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				h_msg = Extra.getFilteredExtra(h_msg);
			} 
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("Passed Test ID 3");
				System.out.println("Input: " + h_msg.extras[0].extraID);
				System.out.println("Output: # of extras: " + h_msg.extras.length);
				for (int i = 0; i < h_msg.extras.length; i ++) {
					test_printExtra(h_msg.extras[i]);
				}
				
				System.out.println("\r");
			}
			else{
				System.out.println("Failed Test ID 3");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}
		
	}
	
	private static void test_printExtra (ExtraMessage h_msg){
		System.out.println("   Extra ID: " + h_msg.extraID);
		System.out.println("   Booking ID: " + h_msg.bookingID);
		System.out.println("   Extra Name: " + h_msg.extraName);
		System.out.println("   Price: " + h_msg.price);
		System.out.println("   Date: " + h_msg.date);
		System.out.println("   Creation Date: " + h_msg.creationDate);

		System.out.println("\r");
	}
}
