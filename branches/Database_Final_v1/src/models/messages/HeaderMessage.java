package models.messages;

public class HeaderMessage {

	public int		messageOwnerID;
	public int 		authLevel;
	public String 	nameHotel;
	
	public HeaderMessage(int i_authLevel,int mOID, String i_name){
		authLevel	= i_authLevel;
		nameHotel	= i_name;
		messageOwnerID = mOID;
	}
}
