package messages;



public class Message {
	public enum To { ACCOUNT, BOOKING, ROOM}
	public enum Action { ADD, EDIT, View, Delete}
	public enum Response { SUCCESS, FAIL}
	
	int messenger_id;
	int auth_level;
	String name_hotel;
	
	To c_to;
	Action action;
	
	Response response_code;
	String response_string;
	
	// Input Functions
	public void fill_Header(int i_id, int i_auth, String i_name_Hotel){
		messenger_id=i_id;
		auth_level=i_auth;
		name_hotel=i_name_Hotel;
	}
	public void fill_Response(Response i_r, String i_s){
		response_code=i_r;
		response_string=i_s;
	}
	
	// Output Functions
	public int return_Messenger_ID(){
		return messenger_id;
	}
	public int return_Auth(){
		return auth_level;
	}
	public String return_NameHotel(){
		return name_hotel;
	}
	public To return_To(){
		return c_to;
	}
	public Action return_Action(){
		return action;
	}
	public Response return_Response_Code(){
		return response_code;
	}
	public String return_Response_String(){
		return response_string;
	}
	
	// Print Out
	public void print_Header(){
		System.out.println("Header:");
		System.out.println("Messenger ID: " + messenger_id);
		System.out.println("Authentication Level: "  + auth_level);
	}
}
