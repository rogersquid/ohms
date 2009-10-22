package messages;

public class Room_Message extends Message {
	public Action action;
	public int room_id;
	public String room_type;
	public int room_number;
	public int floor_number;
	public int Price;
	public boolean available;
	public boolean cleaned;
	public Room_Specs room_specs;
	
	public Room_Message(){
		room_specs= new Room_Specs();
	}
	
	@Override public String toString(){
		return "This is a Room_Message";
	}
}