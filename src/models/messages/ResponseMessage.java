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

	public void fillResponse(ResponseCode resCode, String resStr){
		/*
		 * OVERVIEW: Fills in the response details. Subject to verification if not validated
		 * PRECONDITIONS: None
		 * MODIFIES: None
		 * POSTCONDITIONS: ResponseMessage object's parameters are initialized
		 */
		responseCode=resCode;
		responseString=resStr;
	}
}
