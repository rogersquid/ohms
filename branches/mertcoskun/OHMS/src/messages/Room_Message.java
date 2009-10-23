package messages;

import message_Helper.Room_Specs;

public class Room_Message extends Message {
	
	int room_id;
	String room_type;
	int room_number;
	int floor_number;
	int Price;
	boolean available;
	boolean cleaned;
	Room_Specs room_specs;
	
	public boolean fill_All(int i_room_id, String i_room_type, int i_room_number, int i_floor, int i_price, boolean i_available, boolean i_clean, int[] i_room_specs){
		room_id=i_room_id;
		room_type= i_room_type;
		room_number=i_room_number;
		floor_number=i_floor;
		Price=i_price;
		available=i_available;
		cleaned=i_clean;
		room_specs.fill_Specs (i_room_specs);
		return true;
	}
}