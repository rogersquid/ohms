/*
 * BillMessage.java
 * Objects of this class are instantiated to pass Bill specific information from Interface through 
 * to Database and everywhere in between. Is always harbored in a Message.java object
 *  
 */


package models.messages;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BillMessage{
	// attributes
	public int				billID;
	public int 				bookingID;
	public String			paymentType;
	public boolean			status; // 0 = not paid , 1 = paid
	public float 			totalPrice;
	
	public void fillAll(int bID, int bookID, String pType, boolean bStatus ){
		/*
		 * OVERVIEW: Fills in the Bill details. Subject to verification if not validated
		 * PRECONDITIONS: None
		 * POSTCONDITIONS: BillMessage object's parameters are initialized
		 */
		billID=bID;
		bookingID=bookID;
		paymentType=pType;
		status=bStatus;
	}
	
	public void print_All(){
		/*
		 * OVERVIEW: Prints out fields of this BillMessage
		 * PRECONDITIONS: None
		 * POSTCONDITIONS: The details of the BillMessage are printed out
		 */
		System.out.println("Bill_message is printing out");
		System.out.println(billID);
		System.out.println(bookingID);
		System.out.println(paymentType);
		System.out.println(status);
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
		if (paymentType==null || paymentType.length() == 0){
	
			response.responseCode = ResponseMessage.ResponseCode.FAIL;
			response.responseString = response.responseString + "Payment Type description cannot be empty\n";
		}
		else{
			Pattern validPaymentType = Pattern.compile("(^[A-Za-z]+$)");	
			m = validPaymentType.matcher(paymentType);
			if (!m.matches()){
				response.responseCode = ResponseMessage.ResponseCode.FAIL;
				response.responseString = response.responseString + "Payment type must be letters only.\n";
			}
		}
		
		return response;
	}
	
}
