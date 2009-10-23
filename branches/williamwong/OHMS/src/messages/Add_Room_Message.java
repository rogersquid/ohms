package messages;

public class Add_Room_Message extends Room_Message {
	public Add_Room_Message(int i_id, int i_auth, String i_name_Hotel){
		room_specs= new Room_Specs();
		c_to=To.ROOM;
		action=Action.ADD;
		this.fill_Header(i_id, i_auth, i_name_Hotel);
	}
}
