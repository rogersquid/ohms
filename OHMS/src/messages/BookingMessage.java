package messages;

import java.util.*;


public class BookingMessage extends Message{
	// attributes

	public Header.Action 	action;
	public int 				booking_id;
	public Date 			date;
	public int 				owner_id;
	public int 				duration;
	public String 			room_type;
	public int 				room_id;
	public RoomInventory 	room_specs;
	public boolean 			check_in;
	public boolean 			check_out;
	
	public BookingMessage(int i_id, int i_auth, String i_name_Hotel, Header.Action i_action){
		header=new Header (i_id, i_auth, i_name_Hotel, Header.To.BOOKING, i_action);
		room_specs= new RoomInventory();
	}
	public void fill_Specs (int[] i_in ){
		room_specs.fill_Specs(i_in);
	}
	
	public void print_All(){
		System.out.println("Booking_message is printing out");
		System.out.println("Rest:");
		System.out.println(action);
		System.out.println(booking_id);
		System.out.println(date);
		System.out.println(owner_id);
		System.out.println(duration);
		System.out.println(room_type);
		System.out.println(room_id);
		System.out.println(check_in);
		System.out.println(check_out);
		room_specs.print_All();
		System.out.println("*******************");
	}
	
	@Override public String toString(){
		return "This is a Booking_Message";
	}
}
