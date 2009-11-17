package models.messages;

public class Message {
	HeaderMessage header;
	AccountMessage[] accounts;
	RoomMessage[] rooms;
	BookingMessage[] bookings;
	BillMessage[] bills;
	ExtraMessage[] extras;
	ResponseMessage response;
	
	public Message(int i_authlevel, int i_mOID, String i_hotelname){ 
		header= new HeaderMessage(i_authlevel, i_mOID, i_hotelname);
		accounts=null;
		rooms=null;
		bookings=null;
		bills=null;
		extras=null;
		response=null;
	}
	public void initializeAccounts(int arraysize){
		accounts=new AccountMessage[arraysize];
	}
	public void initializeBookings(int arraysize){
		bookings=new BookingMessage[arraysize];
	}
	public void initializeRooms(int arraysize){
		rooms=new RoomMessage[arraysize];
	}
	public void initializeExtras(int arraysize){
		extras=new ExtraMessage[arraysize];
	}
	public void initializeBills(int arraysize){
		bills=new BillMessage[arraysize];
	}
}

