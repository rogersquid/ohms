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
	public java.sql.Timestamp	creationDate;
	
	public void fillAll(int eID, int bID, String eName, float ePrice, java.sql.Date eDate, java.sql.Timestamp cDate ){
		extraID=eID;
		bookingID=bID;
		extraName=eName;
		price=ePrice;
		date=eDate;
		creationDate=cDate;		
	}
	
	public void print_All(){
		System.out.println("Extras Message is printing out");
		System.out.println(extraID);
		System.out.println(bookingID);
		System.out.println(extraName);
		System.out.println(price);
		System.out.println(date);
		System.out.println(creationDate);
		System.out.println("*******************");
	}
	
	public ResponseMessage validateParams(){
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
