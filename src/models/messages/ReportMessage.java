package models.messages;

public class ReportMessage {
	
	public HeaderMessage header;
	public Message[] tables;
	public float[] stats;
	public String[] graphs;
	public ResponseMessage response;
	
	public ReportMessage (int i_authlevel, int i_mOID, String i_hotelname){
		header= new HeaderMessage(i_authlevel, i_mOID, i_hotelname);
		tables= null;
		stats= null;
		graphs= null;
		response=new ResponseMessage();
	}
	
	public void initializeTables(int arraysize){
		tables = new Message[arraysize];
		for (int i = 0; i < arraysize; i++) {
			tables[i] = new Message(header.authLevel, header.messageOwnerID, header.nameHotel);
		}
	}
	
	public void initializeStats(int arraysize){
		stats = new float [arraysize];
		for (int i = 0; i < arraysize; i++) {
			stats[i] = 0;
		}
	}
	
	public void initializeGraph(int arraysize){
		graphs = new String[arraysize];
		for (int i = 0; i < arraysize; i++) {
			graphs[i] = null;
		}
	}
}