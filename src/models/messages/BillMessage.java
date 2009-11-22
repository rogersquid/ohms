package models.messages;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BillMessage{
	// attributes
	public int				billID;
	public int 				bookingID;
	public String			paymentType;
	public boolean			status; // 0 = not paid , 1 = paid
	
	public void fillAll(int bID, int bookID, String pType, boolean bStatus ){
		billID=bID;
		bookingID=bookID;
		paymentType=pType;
		status=bStatus;
	}
	
	public void print_All(){
		System.out.println("Bill_message is printing out");
		System.out.println(billID);
		System.out.println(bookingID);
		System.out.println(paymentType);
		System.out.println(status);
		System.out.println("*******************");
	}	
	
	public ResponseMessage validateParams(){
		Matcher m;
		ResponseMessage response = new ResponseMessage();
		
		response.fillResponse(ResponseMessage.ResponseCode.SUCCESS, new String(""));
		if (paymentType.length() == 0){
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
