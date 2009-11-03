package messages;

import messages.message_Helper.*;
import java.util.*;

public class Account_Message extends Message{
	public int account_id;
	public String account_type;
	public String firstname;
	public String lastname;
	public String password;
	public String phone;
	public String address;
	public String email;
	public boolean gender;
	public Date date;
	
	public Account_Message(int i_id, int i_auth, String i_name_Hotel, Header.Action i_action){
		header=new Header (i_id, i_auth, i_name_Hotel, Header.To.ACCOUNT, i_action);
	}
	public boolean fill_All(int i_account_id, String i_account_type, String i_first_name, String i_surname, String i_pw, boolean i_gender, String i_phone, String i_add, String i_mail){
		account_id=i_account_id;
		account_type=i_account_type;
		firstname= i_first_name;
		lastname=i_surname;
		password=i_pw;
		gender=i_gender;
		phone=i_phone;
		address=i_add;
		email=i_mail;
		return true;
	}
	public void print_Middle(){
		System.out.println(account_id);
		System.out.println(account_type);
		System.out.println(firstname);
		System.out.println(lastname);
		System.out.println(password);
		System.out.println(gender);
		System.out.println(phone);
		System.out.println(address);
		System.out.println(email);
	}
}
