package models.messages;

public class Header {
	public enum To { ACCOUNT, BOOKING, EXTRA, ROOM, REPORT_GENERATOR, AUTH }
	public enum Action { ADD, EDIT, VIEW, DELETE, LOGIN, AUTHENTICATE, VIEWALL, VIEWSPECIFIC  }
	public enum Response { SUCCESS, FAIL }
	
	public int		messageOwnerID;
	public int 		authLevel;
	public String 	nameHotel;
	public To 		to; // which class inside the hotel
	public Action 	action; // specific action in that class

	public Response responseCode;
	public String 	responseString;
	
	public Header(int i_authLevel,int mOID, String i_name, To i_t, Action i_action){
		authLevel	= i_authLevel;
		nameHotel	= i_name;
		to			= i_t; // which class inside the hotel
		action		= i_action; // specific action in that class
		messageOwnerID = mOID;
		
		responseString="";
	}
	public Header(int i_m, int i_a, String i_n, To i_t, Action i_action, Response i_res, String i_resStr){
		authLevel		= i_a;
		nameHotel		= i_n;
		to				= i_t; // which class inside the hotel
		action			= i_action; // specific action in that class
		responseCode	= i_res;
		responseString	= i_resStr;	
	}
	public void input(int i_mOID, int i_a, String i_n, To i_t, Action i_action){
		messageOwnerID=i_mOID;
		authLevel=i_a;
		nameHotel=i_n;
		to=i_t; // which class inside the hotel
		action=i_action; // specific action in that class
	}
	public void fillResponse(Response i_r_c, String i_r_str){
		responseCode=i_r_c;
		responseString=i_r_str;
	}
	public void concatResponse(Response i_r_c, String input){
		responseCode=i_r_c;
		responseString = responseString + input;
	}
}
