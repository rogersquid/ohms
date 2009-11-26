package testMains;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import models.database.*;
import models.messages.*;


public class ExtrasTest {
	public static void main(String [ ] args){
		System.out.println("Start Test Extras \r");
		System.out.println("\r");
		//test_addExtra(0);
		//test_editExtra(0);
		//test_deleteExtra(0);
		//test_viewExtra(0);
		//test_viewAllExtra(0);
		test_viewAllExtra(2);
		System.out.println("Finish Test Extras \r");
	}

	
	private static void test_addExtra (int i_num){
		
		//=====================add Extras Tests==========================
		
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success Added Extra");

			//Filling Header information
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeExtras(1);
			h_msg.extras[0] = new ExtraMessage();
			//fill Extra info:
			java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
			java.sql.Timestamp creationTime=new java.sql.Timestamp(new java.util.Date().getTime());
			h_msg.extras[0].bookingID = 10;
			h_msg.extras[0].extraName = "MorningPaper";
			h_msg.extras[0].price = (float)0;
			h_msg.extras[0].date = date;
			h_msg.extras[0].creationTime = creationTime; 
			h_msg.response = h_msg.extras[0].validateParams();
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
	
		//=====================Edit Extra Tests==========================
		if(i_num==0){
			System.out.println("Test ID 2");
			System.out.println("Description: Success Edit Extra: Price");
			//Filling Header information
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeExtras(1);
			h_msg.extras[0] = new ExtraMessage();
			//fill Extra info:
			java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
			java.sql.Timestamp creationTime=new java.sql.Timestamp(new java.util.Date().getTime());
			h_msg.extras[0].extraID = 25;
			h_msg.extras[0].extraName = "Beer";
			h_msg.extras[0].price = (float)512345;
			h_msg.extras[0].date = date;
			h_msg.extras[0].creationTime = creationTime;
			
			Extra Extra = new Extra();
			h_msg.response = h_msg.extras[0].validateParams();
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				//fill Extra info:
				h_msg.response = h_msg.extras[0].validateParams();
				h_msg = Extra.editExtra(h_msg);
				if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
					System.out.println("Passed Test ID 2");
					System.out.println("Input:");
					System.out.println("Output: " + h_msg.response.responseString);
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
			else{
				System.out.println("Failed Test ID 2 at verify");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}
	}

	private static void test_deleteExtra (int i_num){
		
		//=====================Delete Tests==========================
		if(i_num==0){
			System.out.println("Test ID 3");
			System.out.println("Description: Success Delete Extra");
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeExtras(1);
			h_msg.extras[0] = new ExtraMessage();
			//fill Extra info:
			java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
			java.sql.Timestamp creationTime=new java.sql.Timestamp(new java.util.Date().getTime());
			h_msg.extras[0].extraID = 25;
			h_msg.extras[0].bookingID = 13;
			h_msg.extras[0].extraName = "Pool";
			h_msg.extras[0].price = (float)299.99;
			h_msg.extras[0].date = date;
			h_msg.extras[0].creationTime = creationTime; 
			Extra Extra = new Extra();
			h_msg.response = h_msg.extras[0].validateParams();
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				Message g_msg= new Message(0, 0, "test");
				g_msg.initializeExtras(1);
				g_msg.extras[0] = new ExtraMessage();
				//fill Extra info:
				g_msg.extras[0].extraID = h_msg.extras[0].extraID;
				g_msg = Extra.deleteExtra(g_msg);
				if(g_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
					System.out.println("Passed Test ID 3");
					System.out.println("Input:");
					System.out.println("Output: " + g_msg.response.responseString);
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 3");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + g_msg.response.responseString);
					System.out.println("\r");
					}
			} else{
				System.out.println("Failed Test ID 3 at verify");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}
		}

	private static void test_viewExtra (int i_num){
		java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
		java.sql.Timestamp creationTime=new java.sql.Timestamp(new java.util.Date().getTime());
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
			h_msg.extras[0].extraID = 19;
			h_msg.extras[0].bookingID = 13;
			h_msg.extras[0].extraName = "Pool";
			h_msg.extras[0].price = (float)299.99;
			h_msg.extras[0].date = date;
			h_msg.extras[0].creationTime = creationTime;
			Extra Extra = new Extra();
			h_msg = Extra.getExtra(h_msg);
			h_msg.response = h_msg.extras[0].validateParams();
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
		
		//=====================View All Tests==========================
		if(i_num==0){
			java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
			java.sql.Timestamp creationTime=new java.sql.Timestamp(new java.util.Date().getTime());
			System.out.println("Test ID 1");
			System.out.println("Description: Success View All Extra");
			System.out.println("   Test case depends on success addition of Extra 101");
			
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeExtras(1);
			h_msg.extras[0] = new ExtraMessage();
			//fill Extra info:
			h_msg.extras[0].fillAll(1, 2, "Beer", 1000, date, creationTime);
			Extra Extra = new Extra();
			h_msg = Extra.getAllExtras(h_msg);
			h_msg.response = h_msg.extras[0].validateParams();
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
			System.out.println("Description: Success View all Extras with same extraName");
			java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
			DateFormat df= new SimpleDateFormat("yyyy/mm/dd");
			try {
				date = new java.sql.Date(df.parse("2009/01/21").getTime());
				//create message
				Message h_msg= new Message(0, 0, "test");
				h_msg.initializeExtras(2);
				//fill filter toggle Extra Message
				h_msg.extras[0].extraID = 1;
				h_msg.extras[0].bookingID = 1;
				h_msg.extras[0].extraName = "CHECK";
				h_msg.extras[0].price = 1;
				h_msg.extras[0].date = date;
				
				//fill filter value extra Message
				h_msg.extras[1].extraID = 23;
				h_msg.extras[1].bookingID = 15;
				h_msg.extras[1].extraName = "Beer";
				h_msg.extras[1].price = 100;
				h_msg.extras[1].date = date;
				
				Extra Extra = new Extra();
				h_msg.response = h_msg.extras[0].validateParams();
				if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
					h_msg = Extra.getFilteredExtra(h_msg);
				} 
				
				if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
					System.out.println("Passed Test ID 2");
					System.out.println("Input: " + h_msg.extras[0].extraID);
					System.out.println("Output: # of extras: " + h_msg.extras.length);
					for (int i = 0; i < h_msg.extras.length; i++) {
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
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(i_num==2){
			System.out.println("Test ID 3");
			System.out.println("Description: Success View all Extras with same date");
			java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
			DateFormat df= new SimpleDateFormat("yyyy/MM/dd");
			try {
				date = new java.sql.Date(df.parse("2009/11/22").getTime());
				System.out.println(date);
				//create message
				Message h_msg= new Message(0, 0, "test");
				h_msg.initializeExtras(2);
				//fill filter toggle Extra Message
				h_msg.extras[0].extraID = 1;
				h_msg.extras[0].bookingID = 1;
				h_msg.extras[0].extraName = "none";
				h_msg.extras[0].price = 1;
				h_msg.extras[0].date = null;
				
				//fill filter value extra Message
				h_msg.extras[1].extraID = 1;
				h_msg.extras[1].bookingID = 1;
				h_msg.extras[1].extraName = "none";
				h_msg.extras[1].price = 1;
				h_msg.extras[1].date = date;
				
				Extra Extra = new Extra();
				h_msg.response = h_msg.extras[0].validateParams();
				if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
					h_msg = Extra.getFilteredExtra(h_msg);
				} 
				
				if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
					System.out.println("Passed Test ID 2");
					System.out.println("Input: " + h_msg.extras[0].extraID);
					System.out.println("Output: # of extras: " + h_msg.extras.length);
					for (int i = 0; i < h_msg.extras.length; i++) {
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
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static void test_printExtra (ExtraMessage h_msg){
		System.out.println("   Extra ID: " + h_msg.extraID);
		System.out.println("   Booking ID: " + h_msg.bookingID);
		System.out.println("   Extra Name: " + h_msg.extraName);
		System.out.println("   Price: " + h_msg.price);
		System.out.println("   Date: " + h_msg.date);
		System.out.println("   Creation Date: " + h_msg.creationTime);

		System.out.println("\r");
	}
}