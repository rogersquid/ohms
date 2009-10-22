package messages;

import java.util.*;

public class Booking_Message extends Message{
	// attributes

	public Action action;
	public int booking_id;
	public Date date;
	public int owner_id;
	public int duration;
	public String room_type;
	public int room_id;
	public Room_Specs room_specs;
	public boolean check_in;
	public boolean check_out;
	
	public Booking_Message(){
		room_specs= new Room_Specs();
	}
	
	public void print_All(){
		System.out.println("Booking_message is printing out");
		print_Header();
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
