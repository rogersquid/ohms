package messages;

public class Account_Message extends Message{
	
	Action action;
	int account_id;
	String account_type;
	String first_name;
	String Surname;
	String pw;
	boolean gender;
	int phone_number;
	String Address;
	String email;
	
	@Override public String toString(){
		return "This is a Account_Message";
	}
	
	
}
