package database;

import messages.*;
import java.io.*;
import java.sql.*;

public class RoomKeeper{	
	public RoomMessage processMessage(RoomMessage i_msg){
		switch(i_msg.return_Header().action)
		{
		case ADD:
			return add(i_msg);
		case EDIT:
			return edit(i_msg);
		case VIEW:
			return view(i_msg);
		case DELETE:
			return delete(i_msg);
		default:
			//needs implementation
			return i_msg;
		}
	}
	private RoomMessage add(RoomMessage i_msg){
		Room aRoom = new Room();
		return aRoom.addRoom(i_msg);
	}
	private RoomMessage edit(RoomMessage i_msg){
		Room aRoom = new Room();
		return aRoom.editRoom(i_msg);
	}
	private RoomMessage delete(RoomMessage i_msg){
		Room aRoom = new Room();
		return aRoom.deleteRoom(i_msg);
	}
	private RoomMessage view(RoomMessage i_msg){
		Room aRoom = new Room();
		return aRoom.viewRoom(i_msg);
	}
}