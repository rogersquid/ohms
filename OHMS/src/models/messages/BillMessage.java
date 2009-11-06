package models.messages;

import java.util.*;


public class BillMessage extends Message{
	// attributes
	public int				billID;
	public int 				bookingID;
	
	public String[] 	nameOfItem;
	public float[]		priceOfItem;
	
	public BillMessage(int i_id, int mOID, int i_auth, String i_name_Hotel, Header.Action i_action){
		header=new Header (i_id, mOID, i_auth, i_name_Hotel, Header.To.BOOKING, i_action);
	}
	public void initialize(int size){
		nameOfItem= new String[size];
		priceOfItem= new float[size];
	}
	public void print_All(){
		System.out.println("Bill_message is printing out");
		System.out.println(billID);
		System.out.println(bookingID);
		System.out.println("*******************");
	}	
}
