package message_Helper;



public class Header {
	public enum To { ACCOUNT, BOOKING, ROOM}
	public enum Action { ADD, EDIT, View, Delete}
	public enum Response { SUCCESS, FAIL}
	
	public int c_msg_id;
	public int c_auth_level;
	public String c_name_hotel;
	public To c_to; // which class inside the hotel
	public Action c_action; // specific action in that class
	
	public Response c_response_code;
	public String c_response_string;
	
	public Header(int i_m, int i_a, String i_n, To i_t, Action i_action){
		c_msg_id=i_m;
		c_auth_level=i_a;
		c_name_hotel=i_n;
		c_to=i_t; // which class inside the hotel
		c_action=i_action; // specific action in that class
	}
	
	public Header(int i_m, int i_a, String i_n, To i_t, Action i_action, Response i_res, String res_str){
		c_msg_id=i_m;
		c_auth_level=i_a;
		c_name_hotel=i_n;
		c_to=i_t; // which class inside the hotel
		c_action=i_action; // specific action in that class
		c_response_code=i_res;
		c_response_string=res_str;
		
	}
	
	public void input(int i_m, int i_a, String i_n, To i_t, Action i_action){
		c_msg_id=i_m;
		c_auth_level=i_a;
		c_name_hotel=i_n;
		c_to=i_t; // which class inside the hotel
		c_action=i_action; // specific action in that class
	}
	
	public void fill_response(Response i_r_c, String i_r_str){
		c_response_code=i_r_c;
		c_response_string=i_r_str;
	}
}
