package mains;

import message_Helper.Header;
import messages.*;
import database.Hotel;

public class Message_test {
	public static void main(String [ ] args)
	{
		/*System_Brain brain=new System_Brain("test_data", "path");
		Add_Account_Message a_message= new Add_Account_Message(0, 0 , "test_data");
		System.err.print("testing process message");
		brain.process_Message(a_message);*/
		test_add(1);
	}
	// example for the test people
	private static void test_add (int i_num){
		if(i_num==1){
			System.out.println("Test ID 1");
			Account_Message h_msg= new Account_Message(0, 0, "Mert", Header.Action.ADD) ;
			h_msg.fill_All(1, "a", "Mert", "i_surname", "i_pw", true , "555", "a", "a");
			Hotel hotel= new Hotel("OHMS", "/tmp/var/");
			hotel.process_Message(h_msg);
			Header head=h_msg.return_Header();
			if(Header.Response.SUCCESS==head.response_code){
				System.out.println("Output was");
			}else{
				System.out.println("SUCCESS");
			}
		}
	}
}
