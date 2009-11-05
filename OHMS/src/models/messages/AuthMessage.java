package models.messages;

import java.util.Date;

public class AuthMessage extends Message{
	public int account_id;
	public String email;
	public int authLevel;
	
	public AuthMessage(int i_id, int i_auth, String i_name_Hotel, Header.Action i_action){
		header=new Header (i_id, i_auth, i_name_Hotel, Header.To.AUTH, i_action);
	}
	public AuthMessage(Header i_header){
		header=i_header;
	}
	public boolean fill_All(int i_account_id, String i_mail){
		account_id		= i_account_id;
		email			= i_mail;
		return true;
	}
	public AuthMessage deepCopy(){
		AuthMessage reply= new AuthMessage(this.header);
		reply.fill_All(account_id, email);
		return reply;
	}
	public void print_Middle(){
		System.out.println(account_id);

		System.out.println(email);
	}
}