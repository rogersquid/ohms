package models.database;

import models.messages.*;
import models.message_Helper.*;
import java.io.*;
import java.sql.*;

public class Room_Keeper{
	// People logged into the system
	private Vector c_Accounts;
	static String dUser = "eece419";
	static String dPass = "dude";
	static String dbLocation = "jdbc:mysql://mayfer.homelinux.com";
	
	public AccountMessage Process_Message(AccountMessage msg)
	{
		AccountMessage accMsg = msg;
		//switch statement to decide which Room functions to call
		/*
		 * switch(msg.Type) {
		 * 	case enum:
		 * 		accMsg = AddRoom(accMsg);
		 * 		break;
		 * }
		 */
		
		return accMsg;
	}
	
	private AccountMessage AddRoom(AccountMessage msg)
	{
		AccountMessage accMsg = msg;
		Connection connect = connect();
		
		// check if room is in database already
		statement = connect.prepareStatement("SELECT * FROM MasterListRoom WHERE num = " + accMsg.room_number);
		ResultSet rs = statement.executeQuery();
		
		if (!(rs.next())) {
			//accMsg.status = "failed";
			//accMsg.error = "room number in database already";
			return accMsg;
		}
		
		PreparedStatement statement = createStatement("INSERT INTO");
		
		// add in variable into statement
		/*
		statement.setInt(1, msg.room_id);
		statement.setInt(2, msg.room_number);
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
	

	
	private AccountMessage ChangeRoom(AccountMessage msg)
	{
		AccountMessage accMsg = msg;
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
	
	private AccountMessage DeleteRoom(AccountMessage msg)
	{
		AccountMessage accMsg = msg;
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
	
	private AccountMessage RoomCleaned(AccountMessage msg)
	{
		AccountMessage accMsg = msg;
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
	
	private AccountMessage InquireRoom(AccountMessage msg)
	{
		AccountMessage accMsg = msg;
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
	
	private AccountMessage RoomOccupied()
	{
		
	}
	
	private Vector S_Clean()
	{
		
	}
	
	private Vector S_Occupied()
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