package models.messages;

public class Message {
	Header header;
	public void fillHeaderInput(int i_id, int i_auth, 
									String i_nameHotel, Header.To i_to, 
									Header.Action i_action){
		header.input(i_id, i_auth, i_nameHotel, i_to, i_action);
	}
	public void fillHeaderResponse(Header.Response i_res, String i_resStr){
		header.fillResponse(i_res, i_resStr);
	}
	public void concatHeaderResponse(Header.Response i_res, String i_resStr){
		header.concatResponse(i_res, i_resStr);
	}
	public Header returnHeader(){
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

