/*
 * RoomMessage.java
 * Objects of this class are instantiated to pass Room specific information from Interface through
 * to Database and everywhere in between. Is always harbored in a Message.java object
 *
 */

package models.messages;

public class RoomMessage {

	public int 				roomID;
	public String 			roomType;
	public int 				roomNumber;
	public float 			price;
	public boolean 			available;
	public boolean 			cleaned;
	public int 				floor;
	public boolean			tv;
	public int				singleBeds;
	public int 				queenBeds;
	public int				kingBeds;
	public boolean			disabilityAccess;
	public boolean			phone;
	public boolean			internet;
	public boolean			kitchen;
	public boolean			onsuite;
	public boolean			elevator;

	public void fill_All(int i_room_id, String i_room_type, int i_room_number,
			int i_floor, float i_price, boolean i_available, boolean i_clean,
			boolean i_tv, int i_sB, int i_qB, int i_kB, boolean i_dA, boolean i_phone,
			boolean i_internet, boolean i_kitchen, boolean i_onsuite, boolean i_elevator){
		/*
		 * OVERVIEW: Fills in the room details. Subject to verification if not validated
		 * PRECONDITIONS: None
		 * MODIFIES: None
		 * POSTCONDITIONS: RoomMessage object's parameters are initialized
		 */
		roomID		= i_room_id;
		roomType	= i_room_type;
		roomNumber	= i_room_number;
		price		= i_price;
		available	= i_available;
		cleaned		= i_clean;
		floor=i_floor;
		tv=i_tv;
		singleBeds=i_sB;
		queenBeds=i_qB;
		kingBeds=i_kB;
		disabilityAccess=i_dA;
		phone=i_phone;
		internet=i_internet;
		kitchen=i_kitchen;
		onsuite=i_onsuite;
		elevator=i_elevator;
	}

	public ResponseMessage validateParams(){
		/*
		 * OVERVIEW: Checks to see if the parameters entered are valid.
		 * PRECONDITIONS: None
		 * MODIFIES: None
		 * POSTCONDITIONS: A ResponseMessage is returned that contains a description of the errors in its ResponseString and the ResponseCode is set accordingly
		 */
		ResponseMessage validity = new ResponseMessage();
		validity.responseString = "";
		validity.responseCode = ResponseMessage.ResponseCode.SUCCESS;

		if(roomNumber <= 0) {
			validity.responseCode = ResponseMessage.ResponseCode.FAIL;
			validity.responseString = validity.responseString + "- Please enter a room number<br />";
		}
		if(floor <= 0) {
			validity.responseCode = ResponseMessage.ResponseCode.FAIL;
			validity.responseString = validity.responseString + "- Please enter a floor number<br />";
		}
		if( price <= 0 ) {
			validity.responseCode = ResponseMessage.ResponseCode.FAIL;
			validity.responseString = validity.responseString + "- Please enter a price <br />";
		}
		if (roomType == "" || roomType == null) {
			validity.responseCode = ResponseMessage.ResponseCode.FAIL;
			validity.responseString = validity.responseString + "- Room type cannot be blank <br />";
		}
		if (roomID < 0 || roomNumber < 0 || floor < 0 || price < 0 ||
				singleBeds < 0 || queenBeds < 0 || kingBeds < 0) {
			validity.responseCode = ResponseMessage.ResponseCode.FAIL;
			validity.responseString = validity.responseString + "- Input cannot be negative <br />";
		} if (roomType == "" || roomType == null) {
			validity.responseCode = ResponseMessage.ResponseCode.FAIL;
			validity.responseString = validity.responseString + "- Input cannot be blank <br />";
		}
		return validity;
	}
}