/*Message.java
 * The class that holds specific message types for transmission across the system classes
 * <to be filled with standardized stuff once all code is complete>
 * 
 * 
 * 
 * 
 */
package models.messages;

public class Message {
	//Arrays of each types of specific messages. For messages going out of the interface (i.e. User input), 
	//only the first element of the array of the relevant type is used. For outgoing messages, i.e. messages 
	//coming back to the user, the relevant message array may contain more than one message item (view all bookings)
	//is an example
	public HeaderMessage header;
	public AccountMessage[] accounts;
	public RoomMessage[] rooms;
	public BookingMessage[] bookings;
	public BillMessage[] bills;
	public ExtraMessage[] extras;
	public ResponseMessage response;

	//The only constructor
	//Authorization level of user and hotel name are stored in the message header
	public Message(int i_authlevel, int i_mOID, String i_hotelname){
		header= new HeaderMessage(i_authlevel, i_mOID, i_hotelname);
		accounts=null;
		rooms=null;
		bookings=null;
		bills=null;
		extras=null;
		response=new ResponseMessage();
	}
	
	public void initializeAccounts(int arraysize){
		//REQUIRES:	ENOUGH HEAP SPACE
		//MODIFIES: ALLOCATES THE CORRESPONDING SPECIFIC MESSAGE ARRAY ENOUGH SPACE TO HOLD SPECIFIED AMOUNT OF SPECIFIC MESSAGE TYPE STRING IS FILLED WITH DESCRIPTION OF INVALID PARAMETERS
		//EFFECTS: THE SPECIFIC MESSAGE ARRAY NOW CONTAINS ENOUGH SPACE TO HOLD THE SPECIFIED NUMBER OF THE SPECIFIC MESSAGE TYPE
		//Interface and any other class that wishes to use one of the specific message types 
		//will have to initialize the array with the desired number of message elements to store
		accounts=new AccountMessage[arraysize];
		for(int i=0; i<arraysize; i++){
			accounts[i]=new AccountMessage();
		}
	}
	public void initializeBookings(int arraysize){
		//REQUIRES:	ENOUGH HEAP SPACE
		//MODIFIES: ALLOCATES THE CORRESPONDING SPECIFIC MESSAGE ARRAY ENOUGH SPACE TO HOLD SPECIFIED AMOUNT OF SPECIFIC MESSAGE TYPE STRING IS FILLED WITH DESCRIPTION OF INVALID PARAMETERS
		//EFFECTS: THE SPECIFIC MESSAGE ARRAY NOW CONTAINS ENOUGH SPACE TO HOLD THE SPECIFIED NUMBER OF THE SPECIFIC MESSAGE TYPE
		//Interface and any other class that wishes to use one of the specific message types 
		//will have to initialize the array with the desired number of message elements to store
		bookings=new BookingMessage[arraysize];
		for(int i=0; i<arraysize; i++){
			bookings[i]=new BookingMessage();
		}
	}
	public void initializeRooms(int arraysize){
		//REQUIRES:	ENOUGH HEAP SPACE
		//MODIFIES: ALLOCATES THE CORRESPONDING SPECIFIC MESSAGE ARRAY ENOUGH SPACE TO HOLD SPECIFIED AMOUNT OF SPECIFIC MESSAGE TYPE STRING IS FILLED WITH DESCRIPTION OF INVALID PARAMETERS
		//EFFECTS: THE SPECIFIC MESSAGE ARRAY NOW CONTAINS ENOUGH SPACE TO HOLD THE SPECIFIED NUMBER OF THE SPECIFIC MESSAGE TYPE
		//Interface and any other class that wishes to use one of the specific message types 
		//will have to initialize the array with the desired number of message elements to store
		rooms=new RoomMessage[arraysize];
		for(int i=0; i<arraysize; i++){
			rooms[i]=new RoomMessage();
		}
	}
	public void initializeExtras(int arraysize){
		//REQUIRES:	ENOUGH HEAP SPACE
		//MODIFIES: ALLOCATES THE CORRESPONDING SPECIFIC MESSAGE ARRAY ENOUGH SPACE TO HOLD SPECIFIED AMOUNT OF SPECIFIC MESSAGE TYPE STRING IS FILLED WITH DESCRIPTION OF INVALID PARAMETERS
		//EFFECTS: THE SPECIFIC MESSAGE ARRAY NOW CONTAINS ENOUGH SPACE TO HOLD THE SPECIFIED NUMBER OF THE SPECIFIC MESSAGE TYPE
		//Interface and any other class that wishes to use one of the specific message types 
		//will have to initialize the array with the desired number of message elements to store
		extras=new ExtraMessage[arraysize];
		for(int i=0; i<arraysize; i++){
			extras[i]=new ExtraMessage();
		}
	}
	public void initializeBills(int arraysize){
		//REQUIRES:	ENOUGH HEAP SPACE
		//MODIFIES: ALLOCATES THE CORRESPONDING SPECIFIC MESSAGE ARRAY ENOUGH SPACE TO HOLD SPECIFIED AMOUNT OF SPECIFIC MESSAGE TYPE STRING IS FILLED WITH DESCRIPTION OF INVALID PARAMETERS
		//EFFECTS: THE SPECIFIC MESSAGE ARRAY NOW CONTAINS ENOUGH SPACE TO HOLD THE SPECIFIED NUMBER OF THE SPECIFIC MESSAGE TYPE
		//Interface and any other class that wishes to use one of the specific message types 
		//will have to initialize the array with the desired number of message elements to store
		bills=new BillMessage[arraysize];
		for(int i=0; i<arraysize; i++){
			bills[i]=new BillMessage();
		}
	}
	
	public boolean validate() {
		//REQUIRES:	NONE (IF THERE IS NO MESSAGE TO CHECK THEN TRUE IS RETURNED.
		//MODIFIES: FILLS RESPONSE MESSAGE WITH VALIDATION CHECK; T OR F AND RESPONSE
		//STRING IS FILLED WITH DESCRIPTION OF INVALID PARAMETERS
		//EFFECTS: NONE
		//This function validates the parameters in a specific message type.
		//Which specific message type is used is determined by which of the message type arrays
		//is not empty. If not empty (i.e. not null), it contains one of the specific message.
		//Makes heavy use of regular expressions
		if(accounts!=null) {
			response = accounts[0].validateParams();
		} else if(rooms!=null) {
			response = rooms[0].validateParams();
		} else if(bookings!=null) {
			//response = bookings[0].validateParams();
		} else if(bills!=null) {
			response = bills[0].validateParams();
		} else if(extras!=null) {
			response = extras[0].validateParams();
		} else {
			return false;
		}
		return true;
	}
}

