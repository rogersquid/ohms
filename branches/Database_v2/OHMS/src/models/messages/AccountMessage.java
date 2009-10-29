package models.messages;

import java.util.*;

public class AccountMessage extends Message{
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
	
	public AccountMessage(int i_id, int i_auth, String i_name_Hotel, Header.Action i_action){
		header=new Header (i_id, i_auth, i_name_Hotel, Header.To.ACCOUNT, i_action);
	}
	public AccountMessage(Header i_header){
		header=i_header;
	}
	public boolean fill_All(int i_account_id, String i_account_type, String i_first_name, String i_surname, String i_pw, boolean i_gender, String i_phone, String i_add, String i_mail){
		account_id		= i_account_id;
		account_type	= i_account_type;
		firstname		= i_first_name;
		lastname		= i_surname;
		password		= i_pw;
		gender			= i_gender;
		phone			= i_phone;
		address			= i_add;
		email			= i_mail;
		return true;
	}
	public AccountMessage deepCopy(){
		AccountMessage reply= new AccountMessage(this.header);
		reply.fill_All(account_id, account_type, firstname, lastname, password, gender, phone, address, email);
		return reply;
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
