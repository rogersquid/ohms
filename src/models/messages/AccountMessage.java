package models.messages;

public class AccountMessage{
	public int accountID;
	public String accountType;
	public String firstName;
	public String lastName;
	public String password;
	public String phone;
	public String address;
	public String email;
	public boolean gender;
	public java.sql.Timestamp creationDate;
	
	public boolean fill_All(int i_account_id, String i_account_type, String i_first_name, String i_surname, String i_pw, boolean i_gender, String i_phone, String i_add, String i_mail){
		accountID		= i_account_id;
		accountType		= i_account_type;
		firstName		= i_first_name;
		lastName		= i_surname;
		password		= i_pw;
		gender			= i_gender;
		phone			= i_phone;
		address			= i_add;
		email			= i_mail;
		return true;
	}

	public void print_Middle(){
		System.out.println(accountID);
		System.out.println(accountType);
		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(password);
		System.out.println(gender);
		System.out.println(phone);
		System.out.println(address);
		System.out.println(email);
	}
}
