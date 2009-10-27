package messages;

public class Header {
	public enum To { ACCOUNT, BOOKING, ROOM }
	public enum Action { ADD, EDIT, VIEW, DELETE }
	public enum Response { SUCCESS, FAIL }
	
	public int msg_id;
	public int auth_level;
	public String name_hotel;
	public To to; // which class inside the hotel
	public Action action; // specific action in that class
	
	public Response response_code;
	public String response_string;
	
	public Header(int i_m, int i_a, String i_n, To i_t, Action i_action){
		msg_id=i_m;
		auth_level=i_a;
		name_hotel=i_n;
		to=i_t; // which class inside the hotel
		action=i_action; // specific action in that class
	}
	
	public Header(int i_m, int i_a, String i_n, To i_t, Action i_action, Response i_res, String res_str){
		msg_id=i_m;
		auth_level=i_a;
		name_hotel=i_n;
		to=i_t; // which class inside the hotel
		action=i_action; // specific action in that class
		response_code=i_res;
		response_string=res_str;
		
	}
	
	public void input(int i_m, int i_a, String i_n, To i_t, Action i_action){
		msg_id=i_m;
		auth_level=i_a;
		name_hotel=i_n;
		to=i_t; // which class inside the hotel
		action=i_action; // specific action in that class
	}
	
	public void fill_response(Response i_r_c, String i_r_str){
		response_code=i_r_c;
		response_string=i_r_str;
	}
}
