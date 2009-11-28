package models.messages;

public class ReportMessage {
	
	public int 				availRoomCount;
	public int 				availUnoccuRoomsCount;
	public int 				occupiedRoomsCount;
	public String 			htmlString;
	public String[]			Graph;
	public int[][]			roomArray;
	
	public void fill_All(int i_availRoomCount, int i_availUnoccuRoomsCount, 
			int i_occupiedRoomsCount, String i_htmlString){
		/*
		 * OVERVIEW: Fills in the report details. Subject to verification if not validated
		 * PRECONDITIONS: None
		 * POSTCONDITIONS: The ReportMessage has its report details filled in 
		 */
		availRoomCount = i_availRoomCount;
		availUnoccuRoomsCount = i_availUnoccuRoomsCount;
		occupiedRoomsCount = i_occupiedRoomsCount;
		htmlString = i_htmlString;
	}
	
	public void initializeRoomARray(int arraysize){
		/*
		 * OVERVIEW: Initialize array size
		 * PRECONDITIONS: arraysize is valid and not null
		 * POSTCONDITIONS: Creates an array with given inputs 
		 */
		roomArray= new int[arraysize][4];
	}
	
	public ResponseMessage validateParam(Message i_msg){
		/* 
		 * OVERVIEW: Checks to see if the parameters entered are valid.  
		 * PRECONDITIONS: None
		 * POSTCONDITIONS: A ResponseMessage is returned that contains a description of the errors in its ResponseString and the ResponseCode is set accordingly 
		 */
		ResponseMessage validity = new ResponseMessage();

		
		if (i_msg.reports[0].availRoomCount < 0 || i_msg.reports[0].availUnoccuRoomsCount < 0 || 
				i_msg.reports[0].occupiedRoomsCount < 0 ) {
			validity.responseCode = ResponseMessage.ResponseCode.FAIL;
			validity.responseString = "Input cannot be negative";
		} if (i_msg.reports[0].htmlString == "" || i_msg.reports[0].htmlString == null) {
			validity.responseCode = ResponseMessage.ResponseCode.FAIL;
			validity.responseString = "Input cannot be blank/null";
		}
		return validity;
	}
}