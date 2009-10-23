package messages;

public class Account_Message extends Message{
	
	int account_id;
	String account_type;
	String first_name;
	String Surname;
	String pw;
	boolean gender;
	int phone_number;
	String address;
	String email;
	
	
	public void print_All(){
		System.out.println(action);
		System.out.println(account_id);
		System.out.println(account_type);
		System.out.println(first_name);
		System.out.println(Surname);
		System.out.println(pw);
		System.out.println(gender);
		System.out.println(phone_number);
		System.out.println(address);
		System.out.println(email);
	}
	
	
}
