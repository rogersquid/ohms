package models.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import models.messages.*;


public class Accountant {
	/*
	 * RoomKeeper mert= AccountKeeper()
	 * RoomMessage msg
	 * RoomMessage response=mert.view(msg)
	 * response.price
	 */
	public BillMessage showBill(BillMessage i_msg){
		RoomKeeper room = new RoomKeeper();
		Header header = i_msg.returnHeader();
		RoomMessage msgR= new RoomMessage(header.messageOwnerID, header.authLevel, header.nameHotel, Header.Action.VIEW);
		BookingKeeper book = new BookingKeeper();
		BookingMessage msgB=new BookingMessage(header.messageOwnerID, header.authLevel, header.nameHotel, Header.Action.VIEW);
		msgB.bookingID=i_msg.bookingID;
		BookingMessage replyB=(BookingMessage)book.processMessage(msgB);
		msgR.room_id=replyB.roomID;
		RoomMessage replyR = room.processMessage(msgR);
		BillMessage myReply= new BillMessage(header.messageOwnerID, header.authLevel,
											header.nameHotel, header.action);
		myReply.initialize(1);
		myReply.nameOfItem[0]="Price of the Room";
		myReply.priceOfItem[0]=replyR.price*(float)((replyB.endDate.getTime()- replyB.startDate.getTime())/86400000); //86400000 = MILIsecs per day

		return myReply;	 
	}
}
	/*public Accountant(){
	float costs[];
	String name[];
	RoomKeeper test = new RoomKeeper();
	RoomMessage g_msg = null;
	float responsePrice = g_msg.price;
	
	BookingMessage j_msg = null;
	int responseDuration = j_msg.duration;
	
	float basePrice = responsePrice * responseDuration;
	String name[]
	}
}
	/*
	 * RoomKeeper mert= AccountKeeper()
	 * RoomMessage msg
	 * RoomMessage response=mert.view(msg)
	 * response.price
	 *//*
	public float addPrice(RoomMessage i_msg, BookingMessage g_msg){
		float base_price = i_msg.price * g_msg.duration;
		return base_price;t
	}
	*/