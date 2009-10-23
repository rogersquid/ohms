package messages;

import message_Helper.*;

public class Message {
	Header header;
	public void fill_Header_Input(int i_id, int i_auth, String i_name_Hotel, Header.To i_to, Header.Action i_action){
		header.input(i_id, i_auth, i_name_Hotel, i_to, i_action);
	}
	public void fill_Header_Response(Header.Response i_res, String i_res_str){
		header.fill_response(i_res, i_res_str);
	}
	public Header return_Header(){
		Header h=new Header(header.c_msg_id, header.c_auth_level, header.c_name_hotel, header.c_to, header.c_action, header.c_response_code, header.c_response_string);
		return h;
	}
}

