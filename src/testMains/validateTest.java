package testMains;
import models.database.*;
import models.messages.*;

public class ValidateTest {

	public static void main(String[] args) {
			AccountMessage am1 = new AccountMessage();
			Message m1 = new Message(0, 0, "11");
			
			
			//ACCOUNT MESSAGE TESTS
			//Test first name is invalid
			am1.fill_All(0, "maid", "a1aa", "bbb", "fff", false, "345678", "222 hhh ggg", "hello@hloo.com");
			m1.initializeAccounts(1);
			m1.accounts[0] = am1;
			m1.validate();
			System.out.println("\n\nTest 1\n");
			if (m1.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m1.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m1.response.responseString);				
			}
			
			//Test first name empty
			am1.fill_All(0, "maid", "", "bbb", "fff", false, "345678", "222 hhh ggg", "hello@hloo.com");
			m1.validate();
			System.out.println("\n\nTest 2\n");
			if (m1.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m1.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m1.response.responseString);				
			}
			
			
			//Test account type invalid
			am1.fill_All(0, "id", "aaa", "bbb", "fff", false, "345678", "222 hhh ggg", "hello@hloo.com");
			m1.validate();
			System.out.println("\n\nTest 3\n");
			if (m1.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m1.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m1.response.responseString);				
			}
			
			//Test account type empty
			am1.fill_All(0, "", "aaa", "bbb", "fff", false, "345678", "222 hhh ggg", "hello@hloo.com");
			m1.validate();
			System.out.println("\n\nTest 4\n");
			if (m1.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m1.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m1.response.responseString);				
			}			
			
			
			//Test last name invalid
			am1.fill_All(0, "maid", "aaa", "b2bb", "f@ff", false, "345678", "222 hhh ggg", "hello@hloo.com");
			m1.validate();
			System.out.println("\n\nTest 5\n");
			if (m1.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m1.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m1.response.responseString);				
			}
			
			
			//Test last name empty
			am1.fill_All(0, "maid", "aaa", "bbb", "", false, "345678", "222 hhh ggg", "hello@hloo.com");
			m1.validate();
			System.out.println("\n\nTest 6\n");
			if (m1.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m1.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m1.response.responseString);				
			}
			
			//Test Phone number invalid -1
			am1.fill_All(0, "maid", "aaa", "bbb", "fff", false, "3@45678", "222 hhh ggg", "hello@hloo.com");
			m1.validate();
			System.out.println("\n\nTest 7\n");
			if (m1.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m1.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m1.response.responseString);				
			}
			
			
			//Test phone number invalid -2
			am1.fill_All(0, "maid", "aaa", "bbb", "fff", false, "3sss", "222 hhh ggg", "hello@hloo.com");
			m1.validate();
			System.out.println("\n\nTest 8\n");
			if (m1.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m1.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m1.response.responseString);				
			}
			
			//Test phone number empty
			am1.fill_All(0, "maid", "aaa", "bbb", "fff", false, "", "222 hhh ggg", "hello@hloo.com");
			m1.validate();
			System.out.println("\n\nTest 9\n");
			if (m1.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m1.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m1.response.responseString);				
			}
			
			
			//Test invalid address
			am1.fill_All(0, "maid", "aaa", "bbb", "fff", false, "345678", "21g", "hello@hloo.com");
			m1.validate();			
			System.out.println("\n\nTest 10\n");
			if (m1.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m1.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m1.response.responseString);				
			}
			
			
			
			//Test empty address
			am1.fill_All(0, "maid", "aaa", "bbb", "fff", false, "345678", "", "hello@hloo.com");
			m1.validate();			
			System.out.println("\n\nTest 11\n");
			if (m1.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m1.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m1.response.responseString);				
			}		
			
			
			//Test email invalid -1
			am1.fill_All(0, "maid", "aaa", "bbb", "fff", false, "345678", "222 hhh ggg", "hello@@hloo.com");
			m1.validate();			
			System.out.println("\n\nTest 12\n");
			if (m1.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m1.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m1.response.responseString);				
			}
			
			
			
			//Test email invalid -2
			am1.fill_All(0, "maid", "aaa", "bbb", "fff", false, "345678", "222 hhh ggg", "@hloo.com");
			m1.validate();	
			System.out.println("\n\nTest 13\n");
			if (m1.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m1.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m1.response.responseString);				
			}
			

			//Test email invalid -3
			am1.fill_All(0, "maid", "aaa", "bbb", "fff", false, "345678", "222 hhh ggg", "hello@.com");
			m1.validate();			
			System.out.println("\n\nTest 14\n");
			if (m1.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m1.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m1.response.responseString);				
			}
			
			
			//Test email empty
			am1.fill_All(0, "maid", "aaa", "bbb", "fff", false, "345678", "222 hhh ggg", "");
			m1.validate();			
			System.out.println("\n\nTest 15\n");
			if (m1.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m1.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m1.response.responseString);				
			}

			
			
			
			//BILL MESSAGE TESTS

			BillMessage bm1 = new BillMessage();			
			Message m2 = new Message(0, 0, "11");
			m2.initializeBills(1);
			m2.bills[0] = bm1;


			//Test payment type empty
			bm1.fillAll(0, 1, "", true);
			m2.validate();
			System.out.println("\n\nTest 16\n");
			if (m2.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m2.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m2.response.responseString);				
			}
			
			
			//Test payment type is invalid
			bm1.fillAll(0, 1, "visas2asddd", true);
			m2.validate();
			System.out.println("\n\nTest 17\n");			
			if (m2.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m2.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m2.response.responseString);				
			}
		
		
			//EXTRA MESSAGE TESTS
			//Test empty extra services name
			Message m3 = new Message(0,0,"11");
			m3.initializeExtras(1);
			ExtraMessage em1 = new ExtraMessage();
			m3.extras[0] = em1;
			em1.fillAll(0, 0, "", 5, null, null);
			m3.validate();
			System.out.println("\n\nTest 18\n");			
			if (m3.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m3.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m3.response.responseString);				
			}			
			
			
			
			//Test invalid extra services name
			em1.fillAll(0, 0, "jjj1", 5, null, null);
			m3.validate();
			System.out.println("\n\nTest 19\n");			
			if (m3.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("PASS: ");
				System.out.println(m3.response.responseString);
			}
			else{
				System.out.println("FAIL");
				System.out.println(m3.response.responseString);				
			}
	}

}
