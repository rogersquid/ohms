package models.messages;

public class BillMessage{
	// attributes
	public int				billID;
	public int 				bookingID;
	public String			paymentType;
	public boolean			status; 
	
	public void print_All(){
		System.out.println("Bill_message is printing out");
		System.out.println(billID);
		System.out.println(bookingID);
		System.out.println("*******************");
	}	
}
