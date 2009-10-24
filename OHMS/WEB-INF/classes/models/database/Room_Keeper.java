package database;

import messages.*;
import message_Helper.*;
import java.io.*;
import java.sql.*;

public class Room_Keeper{
	static String dUser = "eece419";
	static String dPass = "dude";
	static String dbLocation = "jdbc:mysql://mayfer.homelinux.com";
	
	public Room_Message Process_Message(Room_Message msg)
	{
		Room_Message rMsg = msg;
		//switch statement to decide which Room functions to call
		switch(rMsg.action) {
			case ADD:
				rMsg = AddRoom(rMsg);
				break;
			case EDIT:
				rMsg = ChangeRoom(rMsg);
				break;
			case VIEW:
				rMsg = InquireRoom(rMsg);
				break;
			case DELETE:
				rMsg = DeleteRoom(rMsg);
				break;
		}		
		return rMsg;
	}
	
	private Room_Message AddRoom(Room_Message msg)
	{
		Room_Message rMsg = msg;
		Connection connect = connect();
	
		PreparedStatement statement = null;
		
		// check if room is in database already
		statement = connect.prepareStatement("SELECT * FROM MasterListRoom WHERE num = " + rMsg.room_number);
		ResultSet rs = statement.executeQuery();
		
		if (!(rs.next())) {
			rMsg.fill_Header_Response(Header.Response.FAIL, "Room number already in database.");
			return rMsg;
		}
		
		statement = createStatememt("INSERT INTO");
		
		// add in variable into statement
		
		statement.setInt(1, rMsg.room_id);
		statement.setInt(2, rMsg.room_number);
		statement.setInt(3, rMsg.room_floor);
		statement.setString(4, rMsg.room_type);
		statement.setFloat(5, rMsg.price);
		statement.setBoolean(6, rMsg.onsuite);
		statement.setBoolean(7, rMsg.tv);
		statement.setBoolean(8, rMsg.disability);
		statement.setBoolean(9, rMsg.elevator);
		statement.setBoolean(10, rMsg.ebirdcall);
		statement.setBoolean(11, rMsg.emornpaper);
		statement.setBoolean(12, rMsg.availability);
		statement.setBoolean(13, false);
		
		
		// submits to database 
		statement.executeUpdate();
		close(connect);
		
		//set status to success
		//accMsg.status = "success"
		return rMsg;
	}
	

	
	private Room_Message ChangeRoom(Room_Message msg)
	{
		Room_Message accMsg = msg;
		Connection connect = connect();
		PreparedStatement statement = createStatement("UPDATE");
		
		// add in variable into statement
		/*
		statement.setInt(1, msg.rid);
		statement.setInt(2, msg.num);
		statement.setInt(3, msg.floor);
		statement.setString(4, msg.type);
		statement.setFloat(5, msg.price);
		statement.setBoolean(6, msg.onsuite);
		statement.setBoolean(7, msg.tv);
		statement.setBoolean(8, msg.disability);
		statement.setBoolean(9, msg.elevator);
		statement.setBoolean(10, msg.ebirdcall);
		statement.setBoolean(11, msg.emornpaper);
		statement.setBoolean(12, msg.availability);
		statement.setBoolean(13, false);
		*/
		
		// submits to database 
		statement.executeUpdate();
		close(connect);
		
		//set status to success
		//accMsg.status = "success"
		return accMsg;
	}
	
	private Room_Message DeleteRoom(Room_Message msg)
	{
		Room_Message accMsg = msg;
		Connection connect = connect();
		
		// check if room is in database already
		//statement = connect.prepareStatement("SELECT * FROM MasterListRoom WHERE num = " + accMsg.num);
		ResultSet rs = statement.executeQuery();
		
		if (!(rs.next())) {
			//accMsg.status = "failed";
			//accMsg.error = "room number is not in database";
			return accMsg;
		}
		
		//set deleted vairable to true
		PreparedStatement statement = connect.prepareStatement("UPDATE MasterListRoom SET deleted = TRUE WHERE num = " + accMsg);
		statement.executeUpdate();
		
		// set status as success
		//accMsg.status = "success"
		return accMsg;
	}
	
	private Room_Message RoomCleaned(Room_Message msg)
	{
		Room_Message accMsg = msg;
		Connection connect = connect();
		
		// check if room is in database already
		//statement = connect.prepareStatement("SELECT * FROM MasterListRoom WHERE num = " + accMsg.num);
		ResultSet rs = statement.executeQuery();
		
		if (!(rs.next())) {
			//accMsg.status = "failed";
			//accMsg.error = "room is not in database";
			return accMsg;
		}
		
		//set deleted vairable to true
		PreparedStatement statement = connect.prepareStatement("UPDATE MasterListRoom SET cleaned = TRUE WHERE num = " + accMsg);
		statement.executeUpdate();
		
		// set status as success
		//accMsg.status = "success"
		return accMsg;
	}
	
	private Room_Message InquireRoom(Room_Message msg)
	{
		Room_Message accMsg = msg;
		Connection connect = connect();
		
		// check if room is in database already
		//statement = connect.prepareStatement("SELECT * FROM MasterListRoom WHERE num = " + accMsg.num);
		ResultSet rs = statement.executeQuery();
		
		if (!(rs.next())) {
			//accMsg.status = "failed";
			//accMsg.error = "room is not in database";
			return accMsg;
		}
		
		// query for room variables (deleted?)
		PreparedStatement statement = connect.prepareStatement("SELECT * FROM MasterListRoom WHERE num = " + accMsg.num);
		rs = statement.executeQuery();
		
		//loop through to get room variables
		
		// set status as success
		//accMsg.status = "success"
		return accMsg;
	}
	
	private Room_Message RoomOccupied()
	{
		
	}
	
	private PreparedStatement createStatememt (String type) {
		PreparedStatement statement = null;
		
		statement = connect.prepareStatement(type + " MasterListRooms (" +
				"rid, num, floor, type, price, onsuite, tv, disability, elevator, ebirdcall, " +
				"emornpaper, availability, deleted, rinserttime) " +
				"VALUES (?,?,?,?,?,?,?,?,?,?,?,?, NOW())");
		
		statement.setNull(1, java.sql.Types.INTEGER);
		statement.setNull(2, java.sql.Types.INTEGER);
		statement.setNull(3, java.sql.Types.INTEGER);
		statement.setString(4, null);
		statement.setNull(5, java.sql.Types.FLOAT);
		statement.setNull(6, java.sql.Types.BOOLEAN);
		statement.setNull(7, java.sql.Types.BOOLEAN);
		statement.setNull(8, java.sql.Types.BOOLEAN);
		statement.setNull(9, java.sql.Types.BOOLEAN);
		statement.setNull(10, java.sql.Types.BOOLEAN);
		statement.setNull(11, java.sql.Types.BOOLEAN);
		statement.setNull(12, java.sql.Types.BOOLEAN);
		statement.setNull(13, java.sql.Types.BOOLEAN);
		
		return statement;
	}
	
	static public Connection connect() {
		Connection connect = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(dbLocation, dUser, dPass);
		} catch (Exception e) {
			System.out.print("Unable to connect");
		}
		return connect;
	}
	
	static public void close(Connection connect) {
		Connection closeconnect = connect;
		try {
			if (closeconnect != null) {
				closeconnect.close();
			} catch (Exception e) {
				System.err.println("error while disconnecting");
			}
		}
	}
	
}