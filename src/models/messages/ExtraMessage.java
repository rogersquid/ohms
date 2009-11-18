package models.messages;

public class ExtraMessage{
	// attributes
	public int 					extraID;
	public int					bookingID;
	public String				extraName;
	public float				price;
	public java.sql.Date		date;
	public java.sql.Date		creationDate;
	
	public void fillAll(int eID, int bID, String eName, float ePrice, java.sql.Date eDate, java.sql.Date cDate ){
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
