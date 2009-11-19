package models.messages;

public class BookingMessage{
	public int 					bookingID;
	public int 					ownerID;
	public java.sql.Timestamp	creationDate;
	public java.sql.Date 		startDate;
	public java.sql.Date		endDate;
	public int 					roomID;
	public int 					status; // 0= not yet checked in
										// 1= checked in // 2= checked out
	public void fillAll( int bID, int oID, java.sql.Timestamp cDate, java.sql.Date sDate, 
										java.sql.Date eDate, int rID, int s){
		bookingID=bID;
		ownerID=oID;
		creationDate=cDate;
		startDate=sDate;
		endDate=eDate;
		roomID=rID;
		status=s; // 0= n
	}
	public void print_All(){
		System.out.println("Booking_message is printing out");
		System.out.println(bookingID);
		System.out.println(ownerID);
		System.out.println(endDate);
		System.out.println(roomID);
		System.out.println(status);
	}
}