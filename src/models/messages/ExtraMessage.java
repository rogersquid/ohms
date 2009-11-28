/*
 * ExtraMessage.java
 * Objects of this class are instantiated to pass Extra service specific information from Interface through 
 * to Database and everywhere in between. Is always harbored in a Message.java object
 *  
 */

package models.messages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ExtraMessage{
	// attributes
	public int 					extraID;
	public int					bookingID;
	public String				extraName;
	public float				price;
	public java.sql.Date		date;
	public java.sql.Timestamp	creationTime;
	
	public void fillAll(int eID, int bID, String eName, float ePrice, java.sql.Date eDate, java.sql.Timestamp cDate ){
		/*
		 * OVERVIEW: Fills in the Extra service details. Subject to verification if not validated
		 * PRECONDITIONS: None
		 * POSTCONDITIONS: ExtraMessage object's parameters are initialized
		 */
		
		extraID=eID;
		bookingID=bID;
		extraName=eName;
		price=ePrice;
		date=eDate;
		creationTime=cDate;		
	}
	
	public void print_All(){
		/*
		 * OVERVIEW: Prints out fields of this ExtraMessage
		 * PRECONDITIONS: None
		 * POSTCONDITIONS: The details of the ExtraMessage are printed out
		 */
		System.out.println("Extras Message is printing out");
		System.out.println(extraID);
		System.out.println(bookingID);
		System.out.println(extraName);
		System.out.println(price);
		System.out.println(date);
		System.out.println(creationTime);
		System.out.println("*******************");
	}

	public ResponseMessage validateParams(){
		/* 
		 * OVERVIEW: Checks to see if the parameters entered are valid.  
		 * PRECONDITIONS: None
		 * POSTCONDITIONS: A ResponseMessage is returned that contains a description of the errors in its ResponseString and the ResponseCode is set accordingly 
		 */
		Matcher m;
		ResponseMessage response = new ResponseMessage();
		response.fillResponse(ResponseMessage.ResponseCode.SUCCESS, new String(""));
		if (extraName.length() == 0)
		{
			response.responseString = response.responseString + ("Extra services name must not be empty\n");
			//CHANGE RESPONSE CODE
			response.responseCode = ResponseMessage.ResponseCode.FAIL;			
		}
		else{
			Pattern validExtraName = Pattern.compile("(^[A-Za-z]+$)");	
			m = validExtraName.matcher(extraName);
			if (!m.matches()){
				response.responseCode = ResponseMessage.ResponseCode.FAIL;
				response.responseString = response.responseString + "Extra service name must not contain numbers";
			}
		}
		if (price < 0){
			response.responseString = response.responseString + ("Extra services price cannot be less than 0\n");
			//CHANGE RESPONSE CODE
			response.responseCode = ResponseMessage.ResponseCode.FAIL;			
			
		}
		return response;
	}

}
