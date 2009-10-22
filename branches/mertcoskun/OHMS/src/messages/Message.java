package messages;



public class Message {
	public enum Action { ADD, EDIT, DELETE }
	
	int messenger_id;
	int auth_level;
	
	public void fill_Header(int i_id, int i_auth){
		messenger_id=i_id;
		auth_level=i_auth;
	}
	
	public void print_Header(){
		System.out.println("Header:");
		System.out.println("Messenger ID: " + messenger_id);
		System.out.println("Authentication Level: "  + auth_level);
	}
}
