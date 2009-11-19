package testMains;

import models.messages.*;
import models.messages.ResponseMessage.ResponseCode;
import models.database.*;
import java.util.*;

public class AccountTest {
	private static LinkedList<Integer> accountsCreated = new LinkedList<Integer>();
	public static void main(String [ ] args){
		test_alladdAcc();
		test_alleditAcc();
		test_viewAllAcc();
		test_alldeleteAcc();
	}
	protected static void test_alladdAcc(){
		System.out.println("Start Test Add Accounts \r");
		for (int i=0; i<9; i++){
			test_addAcc(i);
		}
		System.out.println("Finish Test Add Accounts \r");
	}
	
	protected static void test_alldeleteAcc(){
		System.out.println("Start Test Delete Accounts \r");
			test_deleteAcc();
		System.out.println("Finish Test Delete Accounts \r");
	}
	
	protected static void test_alleditAcc(){
		System.out.println("Start Test Edit Accounts \r");
		for (int i=0; i<1; i++){
			test_editAcc(i);
		}
		System.out.println("Finish Test Edit Accounts \r");
	}

	private static void test_addAcc (int i_num){
		// Create an Account to work with
		Account testAccount = new Account();
		Message returnMessage;
		//=====================Add Accounts Tests==========================
		switch(i_num)
		{
		case 0:
		{
			System.out.println("Test ID 1");

			System.out.println("Description: Successfully Add account");
			Message h_msg = new Message(4, 1, "OHMS");
			h_msg.accounts = new AccountMessage[1];
			h_msg.accounts[0] = new AccountMessage();
			h_msg.accounts[0].fill_All(1, "Staff", "William", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong1@gmail.com");
			returnMessage = testAccount.addAccount(h_msg);
			
			if(ResponseCode.SUCCESS == returnMessage.response.responseCode){
				System.out.println("Passed Test ID 1");
				System.out.println("Input:");
				h_msg.accounts[0].print_Middle();
				System.out.println("Output:");
				System.out.println("Response string:" +
						returnMessage.response.responseString );
				System.out.println("\r");
				// Add the added account to the list of accounts to delete
				accountsCreated.add(returnMessage.accounts[0].accountID);
			}
			else{
				System.out.println("Failed Test ID 1");
				System.out.println("Response string:" +
						returnMessage.response.responseString );
				System.out.println("Expected Change:");
				System.out.println("\r");
			}	
		}
		break;

		case 1:
		{
			System.out.println("Test ID 2");
			System.out.println("Description: Failed to Added account due to invalid Account Type");
			Message h_msg = new Message(4, 1, "OHMS");
			h_msg.accounts = new AccountMessage[1];
			h_msg.accounts[0] = new AccountMessage();
			h_msg.accounts[0].fill_All(1, "!s@. #TZ", "William", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong2@gmail.com");
			returnMessage = testAccount.addAccount(h_msg);
			if(ResponseCode.FAIL==returnMessage.response.responseCode){
				System.out.println("Passed Test ID 2");
				System.out.println("Input:");
				h_msg.accounts[0].print_Middle();
				System.out.println("Output:");
				System.out.println("Response string:" +
						returnMessage.response.responseString );
				System.out.println("\r");
			}
			else{
				System.out.println("Failed Test ID 2");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Response string:" +
						returnMessage.response.responseString );
				System.out.println("\r");
				// Add the added account to the list of accounts to delete
				accountsCreated.add(returnMessage.accounts[0].accountID);
			}
		}
		break;

		case 2:

		{
			System.out.println("Test ID 3");
			System.out.println("Description: Failed to Added account due to invalid First Name");
			Message h_msg = new Message(4,1,"OHMS");
			h_msg.accounts = new AccountMessage[1];
			h_msg.accounts[0] = new AccountMessage();
			h_msg.accounts[0].fill_All(1, "Staff", "G.E&*%s", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong3@gmail.com");
			returnMessage = testAccount.addAccount(h_msg);
			if(ResponseCode.FAIL==returnMessage.response.responseCode){
				System.out.println("Passed Test ID 3");
				System.out.println("Input:");
				h_msg.accounts[0].print_Middle();
				System.out.println("Output:");
				System.out.println("Response string:" +
						returnMessage.response.responseString );
				System.out.println("\r");
			}
			else{
				System.out.println("Failed Test ID 3");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Response string:" +
						returnMessage.response.responseString );
				System.out.println("\r");
				// Add the added account to the list of accounts to delete
				accountsCreated.add(returnMessage.accounts[0].accountID);
			}		
		}
		break;

		case 3:
		{
			System.out.println("Test ID 4");
			System.out.println("Description: Failed to Added account due to invalid Surname");
			Message h_msg = new Message(4,1,"OHMS");
			h_msg.accounts = new AccountMessage[1];
			h_msg.accounts[0] = new AccountMessage();
			h_msg.accounts[0].fill_All(1, "Staff", "William", "b%^.S", "passwd", true, "6047738298", "123 Fake Street", "wwong4@gmail.com");
			returnMessage = testAccount.addAccount(h_msg);
			if(ResponseCode.FAIL==returnMessage.response.responseCode){
				System.out.println("Passed Test ID 4");
				System.out.println("Input:");
				//h_msg.print_Middle();
				System.out.println("Output:");
				System.out.println("Response string:" +
						returnMessage.response.responseString );
				System.out.println("\r");
			}
			else{
				System.out.println("Failed Test ID 4");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Response string:" +
						returnMessage.response.responseString );
				System.out.println("\r");
				// Add the added account to the list of accounts to delete
				accountsCreated.add(returnMessage.accounts[0].accountID);
			}		
		}
		break;

		case 4:
		{
			System.out.println("Test ID 5");
			System.out.println("Description: Failed to Added account due to invalid password");
			Message h_msg = new Message(4,1,"OHMS");
			h_msg.accounts = new AccountMessage[1];
			h_msg.accounts[0] = new AccountMessage();
			h_msg.accounts[0].fill_All(1, "Staff", "William", "Wong", "o/%h m.s", true, "6047738298", "123 Fake Street", "wwong5@gmail.com");
			returnMessage = testAccount.addAccount(h_msg);
			if(ResponseCode.FAIL==returnMessage.response.responseCode){
				System.out.println("Passed Test ID 5");
				System.out.println("Input:");
				h_msg.accounts[0].print_Middle();
				System.out.println("Output:");
				System.out.println("Response string:" +
						returnMessage.response.responseString );
				System.out.println("\r");
			}
			else{
				System.out.println("Failed Test ID 5");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Response string:" +
						returnMessage.response.responseString );
				System.out.println("\r");
				// Add the added account to the list of accounts to delete
				accountsCreated.add(returnMessage.accounts[0].accountID);
			}		
		}
		break;

		case 5:
		{
			System.out.println("Test ID 6");
			System.out.println("Description: Failed to Added account due to invalid Address");
			Message h_msg = new Message(4,1,"OHMS");
			h_msg.accounts = new AccountMessage[1];
			h_msg.accounts[0] = new AccountMessage();
			h_msg.accounts[0].fill_All(1, "Staff", "William", "Wong", "passwd", true, "6047738298", "Bo!ul.e%!@vard St.", "wwong6@gmail.com");
			returnMessage = testAccount.addAccount(h_msg);
			if(ResponseCode.FAIL==returnMessage.response.responseCode){
				System.out.println("Passed Test ID 6");
				System.out.println("Input:");
				//h_msg.print_Middle();
				System.out.println("Output:");
				System.out.println("Response string:" +
						returnMessage.response.responseString );
				System.out.println("\r");
			}
			else{
				System.out.println("Failed Test ID 6");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Response string:" +
						returnMessage.response.responseString );
				System.out.println("\r");
				// Add the added account to the list of accounts to delete
				accountsCreated.add(returnMessage.accounts[0].accountID);
			}		
		}
		break;

		case 6:
		{
			System.out.println("Test ID 7");
			System.out.println("Description: Failed to Added account due to invalid email");
			Message h_msg = new Message(4,1,"OHMS");
			h_msg.accounts = new AccountMessage[1];
			h_msg.accounts[0] = new AccountMessage();
			h_msg.accounts[0].fill_All(1, "Staff", "William", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwon#g!gmai*l com");
			returnMessage = testAccount.addAccount(h_msg);
			if(ResponseCode.FAIL==returnMessage.response.responseCode){
				System.out.println("Passed Test ID 7");
				System.out.println("Input:");
				//h_msg.print_Middle();
				System.out.println("Output:");
				System.out.println("Response string:" +
						returnMessage.response.responseString );
				System.out.println("\r");
			}
			else{
				System.out.println("Failed Test ID 7");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Response string:" +
						returnMessage.response.responseString );
				System.out.println("\r");
				// Add the added account to the list of accounts to delete
				accountsCreated.add(returnMessage.accounts[0].accountID);
			}		
		}
		break;

		case 7:
		{
			System.out.println("Test ID 8");
			System.out.println("Description: Failed to Added account due to duplicate user");
			Message h_msg = new Message(4,1,"OHMS");
			h_msg.accounts = new AccountMessage[1];
			h_msg.accounts[0] = new AccountMessage();
			h_msg.accounts[0].fill_All(1, "Staff", "William", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong8@gmail.com");
			returnMessage = testAccount.addAccount(h_msg);
			if(ResponseCode.SUCCESS==returnMessage.response.responseCode)
			{
				// Add the added account to the list of accounts to delete
				accountsCreated.add(returnMessage.accounts[0].accountID);
				
				Message g_msg = new Message(4,1,"OHMS");
				g_msg.accounts = new AccountMessage[1];
				g_msg.accounts[0] = new AccountMessage();
				g_msg.accounts[0].fill_All(1, "Staff", "William", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong8@gmail.com");
				Message returnInnerMessage = testAccount.addAccount(h_msg);

				if(ResponseCode.FAIL==returnInnerMessage.response.responseCode)
				{
					System.out.println("Passed Test ID 8");
					System.out.println("Input:");
					//	h_msg.print_Middle();
					System.out.println("Output:");
					System.out.println("Response string:" +
							returnInnerMessage.response.responseString );
					System.out.println("\r");
				}
				else
				{
					System.out.println("Failed Test ID 8");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Response string:" +
							returnInnerMessage.response.responseString );
					System.out.println("\r");
					// Add the added account to the list of accounts to delete
					accountsCreated.add(returnInnerMessage.accounts[0].accountID);
				}
			}
			else
			{
				System.out.println("Failed Test ID 1 at verify");
			}

		}
		break;

		case 8:
		{
			System.out.println("Test ID 9");
			System.out.println("Description: Failed to Added account due to multiple blank fields");
			Message h_msg = new Message(4,1,"OHMS");
			h_msg.accounts = new AccountMessage[1];
			h_msg.accounts[0] = new AccountMessage();
			h_msg.accounts[0].fill_All(1, "Staff", "", "Wong", "passwd", true, "6047738298", "", "");
			returnMessage = testAccount.addAccount(h_msg);
			if(ResponseCode.FAIL==returnMessage.response.responseCode){
				System.out.println("Passed Test ID 9");
				System.out.println("Input:");
				//h_msg.print_Middle();
				System.out.println("Output:");
				System.out.println("Response string:" +
						returnMessage.response.responseString );
				System.out.println("\r");
			}
			else{
				System.out.println("Failed Test ID 9");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Response string:" +
						returnMessage.response.responseString );
				System.out.println("\r");
				// Add the added account to the list of accounts to delete
				accountsCreated.add(returnMessage.accounts[0].accountID);
			}		
		}
		break;
		default:
			break;
		}
	}


	private static void test_editAcc (int i_num){
		// Create an Account to work with
		Account testAccount = new Account();
		Message returnMessage;
		//=====================Edit Account Tests==========================
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success Edit Account: First Name");

			Message h_msg = new Message(4,1,"OHMS");
			h_msg.accounts = new AccountMessage[1];
			h_msg.accounts[0] = new AccountMessage();
			h_msg.accounts[0].fill_All(1, "Staff", "William", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong.edit@gmail.com");
			returnMessage = testAccount.addAccount(h_msg);
			if(ResponseCode.SUCCESS==returnMessage.response.responseCode){
				// Add the added account to the list of accounts to delete
				accountsCreated.add(returnMessage.accounts[0].accountID);
				
				Message g_msg = new Message(4,1,"OHMS");
				g_msg.accounts = new AccountMessage[1];
				g_msg.accounts[0] = new AccountMessage();
				g_msg.accounts[0].fill_All(1, "Staff", "Will", "Wong", "passwd", true, "6047738298", "123 Fake Street", "wwong.edit@gmail.com");
				Message returnInnerMessage = testAccount.editAccount(g_msg);
				if(ResponseCode.SUCCESS==returnInnerMessage.response.responseCode){
					System.out.println("Passed Test ID 1");
					System.out.println("Input:");
					System.out.println("Output:");
					System.out.println("Response string:" +
							returnInnerMessage.response.responseString );
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 1");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Response string:" +
							returnInnerMessage.response.responseString );
					System.out.println("\r");
				}		
			}
			else{
				System.out.println("Failed Test ID 1 at verify");
			}
		}
	}

	private static void test_deleteAcc (){
		Account testAccount = new Account();
		Message returnMessage;
		Message h_msg;
		
		while(!accountsCreated.isEmpty())
		{
			h_msg = new Message(4,1,"OHMS");
			h_msg.accounts = new AccountMessage[1];
			h_msg.accounts[0] = new AccountMessage();
			h_msg.accounts[0].accountID = accountsCreated.getFirst();
			returnMessage = testAccount.deleteAccount(h_msg);
			if(ResponseCode.SUCCESS==returnMessage.response.responseCode){
				System.out.println("Passed Test ID 1");
				System.out.println("Response string:" +
						returnMessage.response.responseString );
				System.out.println("\r");
			}
			else{
				System.out.println("Failed Test ID 1");
				System.out.println("Response string:" +
						returnMessage.response.responseString );
				System.out.println("\r");
			}		
			accountsCreated.removeFirst();
		}
	}

	private static void test_viewAllAcc()
	{
		Account testAccount = new Account();
		Message returnMessage;
		Message h_msg;
		
		h_msg = new Message(4,1,"OHMS");
		returnMessage = testAccount.viewAllAccount(h_msg);
		if(ResponseCode.SUCCESS==returnMessage.response.responseCode){
			System.out.println("Passed Test ID 1");
			System.out.println("Response string:" +
					returnMessage.response.responseString );
			for(int i = 0 ; i < returnMessage.accounts.length; i++)
			{
				printAccount(returnMessage.accounts[i]);
			}
			System.out.println("\r");
		}
		else{
			System.out.println("Failed Test ID 1");
			System.out.println("Response string:" +
					returnMessage.response.responseString );
			for(int i = 0 ; i < returnMessage.accounts.length; i++)
			{
				printAccount(returnMessage.accounts[i]);
			}
			System.out.println("\r");
		}		
		
		
	}
	
	private static void printAccount(AccountMessage i_msg)
	{
		System.out.println("   Account ID: " + i_msg.accountID);
		System.out.println(" Account Type: " + i_msg.accountType);
		System.out.println("      Address: " + i_msg.address);
		System.out.println("        Email: " + i_msg.email);
		System.out.println("   First Name: " + i_msg.firstName);
		System.out.println("    Last Name: " + i_msg.lastName);
		System.out.println("        Phone: " + i_msg.phone);
		System.out.println("         Date: " + i_msg.creationTime);
		System.out.println("       Gender: " + i_msg.gender);
		
		
		
	}
}
