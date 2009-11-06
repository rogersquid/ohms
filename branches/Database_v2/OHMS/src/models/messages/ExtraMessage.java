package models.messages;

import java.util.*;


public class ExtraMessage extends Message{
	// attributes
	public int				bookingID;
	public String			extraName;
	public float			extraCost;
	public Date				orderDate;
	
	public ExtraMessage(int i_id, int mOID, int i_auth, String i_name_Hotel, Header.Action i_action){
		header=new Header (mOID, i_auth, i_name_Hotel, Header.To.BOOKING, i_action);
	}

	public void fillAll(int bID, Date oDate, String eName, float eCost){
		orderDate=oDate;
		extraName=eName;
		extraCost=eCost;
		bookingID=bID;
		
	}
	
	public void print_All(){
		System.out.println("Extras Message is printing out");
		System.out.println(bookingID);
		System.out.println(orderDate);
		System.out.println(extraName);
		System.out.println(extraCost);
		System.out.println("*******************");
	}	
}
