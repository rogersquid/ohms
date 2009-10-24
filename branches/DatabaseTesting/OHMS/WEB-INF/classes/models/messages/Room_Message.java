package messages;

import messages.message_Helper.*;

public class Room_Message extends Message {
	public Header.Action action;
	public int room_id;
	public String room_type;
	public int room_number;
	public float price;
	public boolean available;
	public boolean cleaned;
	public int room_floor;
	public Room_Specs room_specs;
	
	public Room_Message(int i_id, int i_auth, String i_name_Hotel, Header.Action i_action) {
		header = new Header(i_id, i_auth, i_name_Hotel, Header.To.ROOM, i_action);
		room_specs = new Room_Specs();
	}
	
	public boolean fill_All(int i_room_id, String i_room_type, int i_room_number, int i_floor, float i_price, boolean i_available, boolean i_clean, int[] i_room_specs){
		room_id=i_room_id;
		room_type= i_room_type;
		room_number=i_room_number;
		price=i_price;
		available=i_available;
		cleaned=i_clean;
		room_specs.fill_Specs (i_room_specs);
		return true;
	}
	
	public void print_All(){
		System.out.println("Room_Message is printing out");
		System.out.println("Rest:");
		System.out.println(action);
		System.out.println(room_id);
		System.out.println(room_type);
		System.out.println(room_number);
		System.out.println(price);
		System.out.println(available);
		System.out.println(cleaned);
		room_specs.print_All();
		System.out.println("*******************");
	}
}