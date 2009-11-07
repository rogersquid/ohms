package models.messages;

public class Message {
	Header header;
	public void fill_Header_Input(int i_id, int i_auth, 
									String i_name_Hotel, Header.To i_to, 
									Header.Action i_action){
		header.input(i_id, i_auth, i_name_Hotel, i_to, i_action);
	}
	public void fill_Header_Response(Header.Response i_res, String i_res_str){
		header.fillResponse(i_res, i_res_str);
	}
	public Header return_Header(){
		Header h=new Header(header.authLevel, 
							header.messageOwnerID,
							header.nameHotel, 
							header.to, 
							header.action, 
							header.responseCode, 
							header.responseString);
		return h;
	}
}

