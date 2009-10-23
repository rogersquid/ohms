package messages;

public class Add_Account_Message extends Account_Message {
	
	public Add_Account_Message(int i_id, int i_auth, String i_name_Hotel){
		c_to=To.ACCOUNT;
		action=Action.ADD;
		this.fill_Header(i_id, i_auth, i_name_Hotel);
	}
	public void print_All(){
		this.print_Header();
		this.print_Middle();
	}
}
