/*
 * BookingMessage.java
 * Objects of this class are instantiated to pass Booking service specific information from Interface through 
 * to Database and everywhere in between. Is always harbored in a Message.java object
 *  
 */

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
	
	
	
	/*
	 * PRE: None
	 * POST: BookingMessage object's parameters are initialized
	 * 
	 *  Fills in the Booking details. Subject to verification if not validated
	 */
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
	
	
	/*
	 * PRE: None
	 * POST: The details of the BookingMessage are printed out
	 * 
	 * Spills out fields of this BookingMessage
	 */
	public void print_All(){
		System.out.println("Booking_message is printing out");
		System.out.println(bookingID);
		System.out.println(ownerID);
		System.out.println(endDate);
		System.out.println(roomID);
		System.out.println(status);
	}
}
