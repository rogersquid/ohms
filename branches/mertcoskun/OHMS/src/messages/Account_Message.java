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
	
	public boolean fill_All(int i_account_id, String i_account_type, String i_first_name, String i_surname, String i_pw, boolean i_gender, int i_phone, String i_add, String i_mail){
		account_id=i_account_id;
		account_type=i_account_type;
		first_name= i_first_name;
		Surname=i_surname;
		pw=i_pw;
		gender=i_gender;
		phone_number=i_phone;
		address=i_add;
		email=i_mail;
		return true;
	}
	
	public void print_Middle(){
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
