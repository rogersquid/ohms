package messages;

import java.util.*;

public class Booking_Message extends Message{
	// attributes
	Action action;
	int booking_id;
	Date date;
	int owner_id;
	int duration;
	String room_type;
	int room_id;
	Room_Specs room_specs;
	boolean check_in;
	boolean check_out;
	
	public Booking_Message(){
		room_specs= new Room_Specs();
	}
	
	@Override public String toString(){
		return "This is a Booking_Message";
	}
}
