package messages;

public class View_Delete_Room_Message extends Room_Message {
	public View_Delete_Room_Message(int i_id, int i_auth, String i_name_Hotel, boolean delete){
		c_to=To.ACCOUNT;
		if (delete){
			action=Action.Delete;
		}
		else{
			action=Action.View;
		}
		this.fill_Header(i_id, i_auth, i_name_Hotel);
	}
	public boolean fill_input(int i_room_id){
		room_id=i_room_id;
		return true;
	}
	public boolean fill_input(int i_room_number, int i_room_floor){
		room_number= i_room_number;
		floor_number= i_room_floor;
		return true;
	}
}
