package mains;

import messages.*;

public class Message_test {
	public static void main(String [ ] args)
	{
		System_Brain brain=new System_Brain("test_data", "path");
		Add_Account_Message a_message= new Add_Account_Message(0, 0 , "test_data");
		System.err.print("testing process message");
		brain.process_Message(a_message);
	}
}
