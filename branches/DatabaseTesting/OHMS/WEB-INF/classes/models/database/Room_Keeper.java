package database;

import messages.*;
import messages.message_Helper.*;
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
		try {
			statement = connect.prepareStatement("SELECT * FROM rooms WHERE num = " + rMsg.room_number);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				rMsg.fill_Header_Response(Header.Response.FAIL, "Room number already in database.");
				return rMsg;
			}
		} catch (Exception e) {
			rMsg.fill_Header_Response(Header.Response.FAIL, "Unable to query database.");
			return rMsg;
		}
		
		statement = createStatement("INSERT INTO", connect);
		
		// add in variable into statement
		try {
			statement.setInt(1, rMsg.room_id);
			statement.setInt(2, rMsg.room_number);
			statement.setInt(3, rMsg.room_floor);
			statement.setString(4, rMsg.room_type);
			statement.setFloat(5, rMsg.price);
			statement.setBoolean(6, (Boolean)rMsg.room_specs.search_Specs("onsuite"));
			statement.setBoolean(7, (Boolean)rMsg.room_specs.search_Specs("tv"));
			statement.setBoolean(8, (Boolean)rMsg.room_specs.search_Specs("disability"));
			statement.setBoolean(9, (Boolean)rMsg.room_specs.search_Specs("elevator"));
			statement.setBoolean(10, (Boolean)rMsg.room_specs.search_Specs("ebirdcall"));
			statement.setBoolean(11, (Boolean)rMsg.room_specs.search_Specs("emornpaper"));
			statement.setBoolean(12, (Boolean)rMsg.room_specs.search_Specs("availablity"));
			statement.setBoolean(13, (Boolean)rMsg.room_specs.search_Specs("numBed"));
			statement.setBoolean(14, false);
			// submits to database 
			statement.executeUpdate();
		} catch (Exception e) {
			rMsg.fill_Header_Response(Header.Response.FAIL, "Unable to add new room to database.");
			return rMsg;
		}
		close(connect);
		
		//set status to success
		rMsg.fill_Header_Response(Header.Response.SUCCESS, "Room created in database.");
		return rMsg;
	}
	

	
	private Room_Message ChangeRoom(Room_Message msg)
	{
		Room_Message rMsg = msg;
		Connection connect = connect();
		PreparedStatement statement = createStatement("UPDATE", connect);
		
		// add in variable into statement
		try {
			statement.setInt(1, rMsg.room_id);
			statement.setInt(2, rMsg.room_number);
			statement.setInt(3, rMsg.room_floor);
			statement.setString(4, rMsg.room_type);
			statement.setFloat(5, rMsg.price);
			statement.setBoolean(6, (Boolean)rMsg.room_specs.search_Specs("onsuite"));
			statement.setBoolean(7, (Boolean)rMsg.room_specs.search_Specs("tv"));
			statement.setBoolean(8, (Boolean)rMsg.room_specs.search_Specs("disability"));
			statement.setBoolean(9, (Boolean)rMsg.room_specs.search_Specs("elevator"));
			statement.setBoolean(10, (Boolean)rMsg.room_specs.search_Specs("ebirdcall"));
			statement.setBoolean(11, (Boolean)rMsg.room_specs.search_Specs("emornpaper"));
			statement.setBoolean(12, (Boolean)rMsg.room_specs.search_Specs("availablity"));
			statement.setBoolean(13, (Boolean)rMsg.room_specs.search_Specs("numBed"));
			statement.setBoolean(14, false);
			
			// submits to database 
			statement.executeUpdate();
		} catch (Exception e) {
			rMsg.fill_Header_Response(Header.Response.FAIL, "Unable to modify room in database.");
			return rMsg;
		}
		close(connect);
		
		//set status to success
		rMsg.fill_Header_Response(Header.Response.SUCCESS, "Changes submitted to database.");
		return rMsg;
	}
	
	private Room_Message DeleteRoom(Room_Message msg)
	{
		Room_Message rMsg = msg;
		Connection connect = connect();
		PreparedStatement statement = null;
		
		// check if room is in database already
		try {
			statement = connect.prepareStatement("SELECT * FROM rooms WHERE num = " + rMsg.room_number);
			ResultSet rs = statement.executeQuery();
			if (!(rs.next())) {
				rMsg.fill_Header_Response(Header.Response.FAIL, "Room number not in database.");
				return rMsg;
			}
		} catch (Exception e) {
			rMsg.fill_Header_Response(Header.Response.FAIL, "Unable to query database.");
			return rMsg;
		}
		
		//set deleted variable to true
		try {
			statement = connect.prepareStatement("UPDATE rooms SET deleted = TRUE WHERE num = " + rMsg.room_number);
			statement.executeUpdate();
		} catch (Exception e) {
			rMsg.fill_Header_Response(Header.Response.FAIL, "Unable to query database.");
			return rMsg;
		}
		
		// set status as success
		rMsg.fill_Header_Response(Header.Response.SUCCESS, "Room deleted from database.");
		return rMsg;
	}

	
	private Room_Message InquireRoom(Room_Message msg)
	{
		Room_Message rMsg = msg;
		Connection connect = connect();
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		// check if room is in database already
		try {
			statement = connect.prepareStatement("SELECT * FROM rooms WHERE num = " + rMsg.room_number);
			rs = statement.executeQuery();
			if (!(rs.next())) {
				rMsg.fill_Header_Response(Header.Response.FAIL, "Room number not in database.");
				return rMsg;
			}
		} catch (Exception e) {
			rMsg.fill_Header_Response(Header.Response.FAIL, "Unable to query database.");
			return rMsg;
		}
		
		// query for room variables (deleted room?)
		try {
		statement = connect.prepareStatement("SELECT * FROM rooms WHERE num = " + rMsg.room_number + "AND ");
		rs = statement.executeQuery();
		} catch (Exception e) {
			rMsg.fill_Header_Response(Header.Response.FAIL, "Unable to query database.");
			return rMsg;
		}
		//loop through to get room variables
		
		// set status as success
		rMsg.fill_Header_Response(Header.Response.SUCCESS, "Room information acquired from database.");
		return rMsg;
	}
	
	private Room_Message RoomOccupied (Room_Message rMsg)
	{
		
		return rMsg;
	}
	
	private Room_Message RoomCleaned (Room_Message rMsg)
	{
		return rMsg;
	}
	
	private PreparedStatement createStatement (String type, Connection connect) {
		PreparedStatement statement = null;
		Connection pconnect = null;
		pconnect = connect;
		
		try {
		statement = pconnect.prepareStatement(type + " rooms (" +
				"rid, num, floor, type, price, onsuite, tv, disability, elevator, ebirdcall, " +
				"emornpaper, availability, numBed, deleted) " +
				"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
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
		statement.setNull(13, java.sql.Types.INTEGER);
		} catch (Exception e) {
			
		}
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
			} 
		} catch (Exception e) {
			System.err.println("error while disconnecting");
		}
	}
	
}