package messages;

public class Account_Message extends Message{
	
	public Action action;
	public int account_id;
	public String account_type;
	public String first_name;
	public String Surname;
	public String pw;
	public boolean gender;
	public int phone_number;
	public String address;
	public String email;
	
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
