package models.messages;

import java.util.*;


public class ExtraMessage extends Message{
	// attributes
	public int				bookingID;
	public int				extraID;
	public String			extraName;
	public float			extraCost;
	public Date				orderDate;
	
	public ExtraMessage(int mOID, int i_auth, String i_name_Hotel, Header.Action i_action){
		header=new Header (mOID, i_auth, i_name_Hotel, Header.To.EXTRA, i_action);
	}

	public void fillAll(int eID, int bID, String eName, float eCost, Date oDate){
		extraID=eID;
		bookingID=bID;
		orderDate=oDate;
		extraName=eName;
		extraCost=eCost;
		
		
	}
	
	public void print_All(){
		System.out.println("Extras Message is printing out");
		System.out.println(extraID);
		System.out.println(bookingID);
		System.out.println(orderDate);
		System.out.println(extraName);
		System.out.println(extraCost);
		System.out.println("*******************");
	}	
}
