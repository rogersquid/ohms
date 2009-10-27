package messages;

public class RoomMessage extends Message {
	
	public int room_id;
	public String room_type;
	public int room_number;
	public float price;
	public boolean available;
	public boolean cleaned;
	public int room_floor;
	public RoomInventory room_specs;
	
	public RoomMessage(int i_id, int i_auth, String i_name_Hotel, Header.Action i_action) {
		header = new Header(i_id, i_auth, i_name_Hotel, Header.To.ROOM, i_action);
		room_specs = new RoomInventory();
	}
	public RoomMessage(Header i_header, RoomInventory i_inventory) {
		header = new Header(i_header.msg_id, i_header.auth_level, i_header.name_hotel, i_header.to, i_header.action);
		room_specs = i_inventory;
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
	private boolean fill_All(int i_room_id, String i_room_type, int i_room_number, int i_floor, float i_price, boolean i_available, boolean i_clean){
		room_id=i_room_id;
		room_type= i_room_type;
		room_number=i_room_number;
		price=i_price;
		available=i_available;
		cleaned=i_clean;
		return true;
	}
	public RoomMessage deepCopy(){
		RoomMessage reply= new RoomMessage(this.header, this.room_specs);
		reply.fill_All(room_id, room_type, room_number, room_floor, price, available, cleaned);
		return reply;
	}
	public void print_All(){
		System.out.println("Room_Message is printing out");
		System.out.println("Rest:");
		
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