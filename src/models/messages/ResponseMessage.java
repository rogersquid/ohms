package models.messages;

public class ResponseMessage {
	public enum ResponseCode { SUCCESS, FAIL }
	public ResponseCode responseCode;
	public String responseString;
	public void fillResponse(ResponseCode resCode, String resStr){
		responseCode=resCode;
		responseString=resStr;
	}
}
