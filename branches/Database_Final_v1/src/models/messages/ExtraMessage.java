package models.messages;

import java.util.Date;

public class ExtraMessage{
	// attributes
	public int 					extraID;
	public int					bookingID;
	public String				extraName;
	public float				price;
	public java.sql.Timestamp	date;
	public java.sql.Timestamp	creationDate;
	
	public void fillAll(int eID, int bID, String eName, float ePrice, java.sql.Timestamp eDate, java.sql.Timestamp cDate ){
		extraID=eID;
		bookingID=bID;
		extraName=eName;
		price=ePrice;
		date=eDate;
		creationDate=cDate;		
	}
	
	public void print_All(){
		System.out.println("Extras Message is printing out");
		System.out.println(extraID);
		System.out.println(bookingID);
		System.out.println(extraName);
		System.out.println(price);
		System.out.println(date);
		System.out.println(creationDate);
		System.out.println("*******************");
	}
}
