package models.messages;

public class ResponseMessage {
	public enum ResponseCode { SUCCESS, FAILURE }
	public ResponseCode responseCode;
	public String responseString;
}
