package messages;

public class Edit_Account_Message extends Account_Message{
	public Edit_Account_Message(int i_id, int i_auth, String i_name_Hotel){
		c_to=To.ACCOUNT;
		action=Action.EDIT;
		this.fill_Header(i_id, i_auth, i_name_Hotel);
	}
	public boolean fill_input(int i_account_id){
		account_id=i_account_id;
		return true;
	}
	public boolean fill_input(String i_mail){
		email=i_mail;
		return true;
	}
	public void print_All(){
		this.print_Header();
		this.print_Middle();
	}
}
