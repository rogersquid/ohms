package models.messages;

public class ReportMessage {
	
	public HeaderMessage header;
	public Message[] tables;
	public float[] stats;
	public String[] graphs;
	
	public ReportMessage (int i_authlevel, int i_mOID, String i_hotelname, int numberoftables, int numberofstats, int numberofstrings){
		header= new HeaderMessage(i_authlevel, i_mOID, i_hotelname);
		tables= new Message[numberoftables];
		for (int i=0; i< numberoftables; i++){
			tables[0]= new Message(i_authlevel, i_mOID, i_hotelname);
		}
		stats= new float[numberofstats];

		graphs= new String[numberoftables];

	}
	
	/*public ResponseMessage validateParam(Message i_msg){
		/* 
		 * OVERVIEW: Checks to see if the parameters entered are valid.  
		 * PRECONDITIONS: None
		 * POSTCONDITIONS: A ResponseMessage is returned that contains a description of the errors in its ResponseString and the ResponseCode is set accordingly 
		 */
		/*
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
		return i_msg;
	}*/
}