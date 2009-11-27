/* ResponseMessage.java
 * 
 * This object is used to accrue response messages when actions are performed on the
 * specific message types(ex. validate parameters: this would be a return type with the
 * string field filled with descriptions of the errors in the parameters).
 *  
 * 
 */
package models.messages;



public class ResponseMessage {
	public enum ResponseCode { SUCCESS, FAIL }
	public ResponseCode responseCode;
	public String responseString;

	

	/*
	 * PRE: NONE
	 * POST: The responseCode and responseString fields are filled out 
	 * 
	 */
	public void fillResponse(ResponseCode resCode, String resStr){
		responseCode=resCode;
		responseString=resStr;
	}
}
