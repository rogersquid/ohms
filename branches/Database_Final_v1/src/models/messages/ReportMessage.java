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
		availRoomCount = i_availRoomCount;
		availUnoccuRoomsCount = i_availUnoccuRoomsCount;
		occupiedRoomsCount = i_occupiedRoomsCount;
		htmlString = i_htmlString;
	}
	
	public void initializeRoomARray(int arraysize){
		roomArray= new int[arraysize][4];
	}
	
	public ResponseMessage validateParam(Message i_msg){
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