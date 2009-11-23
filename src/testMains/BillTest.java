package testMains;

import models.database.*;
import models.messages.*;


public class BillTest {
	public static void main(String [ ] args){
		System.out.println("Start Test Bills \r");
		System.out.println("\r");
		//test_addBill(0);
		//test_editBill(0);
		//test_deleteBill(0);
		//test_viewBill(0);
		test_viewAllBill(0);
		//test_viewAllBill(2);
		System.out.println("Finish Test Bills \r");
	}

	
	private static void test_addBill (int i_num){
		
		//=====================add Bills Tests==========================
		
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success Added Bill");

			//Filling Header information
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeBills(1);
			h_msg.bills[0] = new BillMessage();
			//fill Bill info:
			h_msg.bills[0].billID = 1;
			h_msg.bills[0].bookingID = 21;
			h_msg.bills[0].paymentType = "none";
			h_msg.bills[0].status = false;
			h_msg.response = h_msg.bills[0].validateParams();
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				Bill Bill = new Bill();
				h_msg = Bill.addBill(h_msg);
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


	private static void test_editBill(int i_num){
	
		//=====================Edit Bill Tests==========================
		if(i_num==0){
			System.out.println("Test ID 2");
			System.out.println("Description: Success Edit Bill: Price");
			//Filling Header information
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeBills(1);
			h_msg.bills[0] = new BillMessage();
			//fill Bill info:
			h_msg.bills[0].billID = 2;
			h_msg.bills[0].bookingID = 19;
			h_msg.bills[0].paymentType = "Cash";
			h_msg.bills[0].status = true;
			
			Bill Bill = new Bill();
			h_msg.response = h_msg.bills[0].validateParams();
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				//fill Bill info:
				h_msg.response = h_msg.bills[0].validateParams();
				h_msg = Bill.editBill(h_msg);
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

	private static void test_deleteBill (int i_num){
		
		//=====================Delete Tests==========================
		if(i_num==0){
			System.out.println("Test ID 3");
			System.out.println("Description: Success Delete Bill");
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeBills(1);
			h_msg.bills[0] = new BillMessage();
			//fill Bill info:
			h_msg.bills[0].billID = 2;
			h_msg.bills[0].bookingID = 13;
			h_msg.bills[0].paymentType = "Debit";
			h_msg.bills[0].status = false;
	
			Bill Bill = new Bill();
			h_msg.response = h_msg.bills[0].validateParams();
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				Message g_msg= new Message(0, 0, "test");
				g_msg.initializeBills(1);
				g_msg.bills[0] = new BillMessage();
				//fill Bill info:
				g_msg.bills[0].billID = h_msg.bills[0].billID;
				g_msg = Bill.deleteBill(g_msg);
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

	private static void test_viewBill (int i_num){
		
		//=====================View Tests==========================
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success View Bill");
			System.out.println("   Test case depends on success addition of Bill 101");
			
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeBills(1);
			h_msg.bills[0] = new BillMessage();
			//fill Bill info:
			h_msg.bills[0].billID = 3;
			h_msg.bills[0].bookingID = 13;
			h_msg.bills[0].paymentType = "Pool";
			h_msg.bills[0].status = false;
			Bill Bill = new Bill();
			h_msg = Bill.getBill(h_msg);
			h_msg.response = h_msg.bills[0].validateParams();
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("Passed Test ID 1");
				System.out.println("Input: " + h_msg.bills[0].billID);
				System.out.println("Output: ");
				test_printBill(h_msg.bills[0]);
				
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

	private static void test_viewAllBill (int i_num){

		//=====================View All Tests==========================
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success View All Bill");
			System.out.println("   Test case depends on success addition of Bill 101");
			
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeBills(1);
			h_msg.bills[0] = new BillMessage();
			//fill Bill info:
			h_msg.bills[0].fillAll(1, 3, "Cash", false);
			h_msg.bills[0].billID = 3;
			h_msg.bills[0].bookingID = 13;
			h_msg.bills[0].paymentType = "Beer";
			h_msg.bills[0].status = false;
			Bill Bill = new Bill();
			h_msg = Bill.getAllBill(h_msg);
			h_msg.response = h_msg.bills[0].validateParams();
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("Passed Test ID 1");
				System.out.println("Input: " + h_msg.bills[0].billID);
				System.out.println("Output: # of Bills: " + h_msg.bills.length);
				for (int i = 0; i < h_msg.bills.length; i ++) {
					test_printBill(h_msg.bills[i]);
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
			System.out.println("Description: Success View all Bill available");
			
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeBills(2);
			//fill filter toggle Bill Message
			h_msg.bills[0].billID = 18;
			h_msg.bills[0].bookingID = 13;
			h_msg.bills[0].paymentType = "Pool";
			h_msg.bills[0].status = false;
			
			//fill filter value Bill Message
			h_msg.bills[1].billID = 18;
			h_msg.bills[1].bookingID = 13;
			h_msg.bills[1].paymentType = "Pool";
			h_msg.bills[1].status = false;
			
			Bill Bill = new Bill();
			h_msg.response = h_msg.bills[0].validateParams();
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				h_msg = Bill.getFilteredBill(h_msg);
			} 
			
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("Passed Test ID 2");
				System.out.println("Input: " + h_msg.bills[0].billID);
				System.out.println("Output: # of Bills: " + h_msg.bills.length);
				for (int i = 0; i < h_msg.bills.length; i ++) {
					test_printBill(h_msg.bills[i]);
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
			System.out.println("Description: Success View all Bills with same BillName");
			
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeBills(2);
			//fill filter toggle Bill Message
			h_msg.bills[0].billID = 18;
			h_msg.bills[0].bookingID = 13;
			h_msg.bills[0].paymentType = "Pool";
			h_msg.bills[0].status = false;
			
			//fill filter value Bill Message
			h_msg.bills[1].billID = 18;
			h_msg.bills[1].bookingID = 13;
			h_msg.bills[1].paymentType = "Pool";
			h_msg.bills[1].status = false;
			
			Bill Bill = new Bill();
			h_msg.response = h_msg.bills[0].validateParams();
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				h_msg = Bill.getFilteredBill(h_msg);
			} 
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("Passed Test ID 3");
				System.out.println("Input: " + h_msg.bills[0].billID);
				System.out.println("Output: # of Bills: " + h_msg.bills.length);
				for (int i = 0; i < h_msg.bills.length; i ++) {
					test_printBill(h_msg.bills[i]);
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
	
	private static void test_printBill (BillMessage h_msg){
		System.out.println("   Bill ID: " + h_msg.billID);
		System.out.println("   Booking ID: " + h_msg.bookingID);
		System.out.println("   Payment Type: " + h_msg.paymentType);
		System.out.println("   Status: " + h_msg.status);

		System.out.println("\r");
	}
}
