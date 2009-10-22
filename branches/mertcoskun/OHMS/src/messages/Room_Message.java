package messages;

public class Room_Message extends Message {
	Action action;
	int room_id;
	String room_type;
	int room_number;
	int floor_number;
	int Price;
	boolean available;
	boolean cleaned;
	Room_Specs room_specs;
	
	public Room_Message(){
		room_specs= new Room_Specs();
	}
	
	@Override public String toString(){
		return "This is a Room_Message";
	}
}