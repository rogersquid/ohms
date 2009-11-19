package models.messages;

public class BillMessage{
	// attributes
	public int				billID;
	public int 				bookingID;
	public String			paymentType;
	public boolean			status; // 0 = not paid , 1 = paid
	
	public void fillAll(int bID, int bookID, String pType, boolean bStatus ){
		billID=bID;
		bookingID=bookID;
		paymentType=pType;
		status=bStatus;
	}
	
	public void print_All(){
		System.out.println("Bill_message is printing out");
		System.out.println(billID);
		System.out.println(bookingID);
		System.out.println(paymentType);
		System.out.println(status);
		System.out.println("*******************");
	}	
}
