package models.messages;

public class RoomMessage {
	
	public int 				roomID;
	public String 			roomType;
	public int 				roomNumber;
	public float 			price;
	public boolean 			available;
	public boolean 			cleaned;
	public int 				floor;
	public boolean			tv;
	public int				singleBeds;
	public int 				queenBeds;
	public int				kingBeds;
	public boolean			disabilityAccess;
	public boolean			phone;
	public boolean			internet;
	public boolean			kitchen;
	public boolean			onsuite;
	public boolean			elevator;
	
	public void fill_All(int i_room_id, String i_room_type, int i_room_number, 
			int i_floor, float i_price, boolean i_available, boolean i_clean,
			boolean i_tv, int i_sB, int i_qB, int i_kB, boolean i_dA, boolean i_phone, 
			boolean i_internet, boolean i_kitchen, boolean i_onsuite, boolean i_elevator){
		roomID		= i_room_id;
		roomType	= i_room_type;
		roomNumber	= i_room_number;
		price		= i_price;
		available	= i_available;
		cleaned		= i_clean;
		floor=i_floor;
		tv=i_tv;
		singleBeds=i_sB;
		queenBeds=i_qB;
		kingBeds=i_kB;
		disabilityAccess=i_dA;
		phone=i_phone;
		internet=i_internet;
		kitchen=i_kitchen;
		onsuite=i_onsuite;
		elevator=i_elevator;
	}
	
	public ResponseMessage validateParams(Message i_msg){
		ResponseMessage validity = new ResponseMessage();

		
		if (i_msg.rooms[0].roomID < 0 || i_msg.rooms[0].roomNumber < 0 || i_msg.rooms[0].floor < 0 || i_msg.rooms[0].price < 0 ||
				i_msg.rooms[0].singleBeds < 0 || i_msg.rooms[0].queenBeds < 0 || i_msg.rooms[0].kingBeds < 0) {
			validity.responseCode = ResponseMessage.ResponseCode.FAIL;
			validity.responseString = "Input cannot be negative";
		} if (i_msg.rooms[0].roomType == "" || i_msg.rooms[0].roomType == null) {
			validity.responseCode = ResponseMessage.ResponseCode.FAIL;
			validity.responseString = "Input cannot be blank/null";
		}
		return validity;
	}
}