package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import messages.*;

public class Room {
	String dbname = "default_hotel";
	
	public RoomMessage addRoom(RoomMessage i_msg) {
		RoomMessage reply=null;
		databaseHelper 	dbcon = null;
		try {
			dbcon = new databaseHelper(dbname);
			
			ResultSet rs = dbcon.select("SELECT * FROM rooms WHERE num = " + i_msg.room_number);
			if (rs.next()) {
				i_msg.fill_Header_Response(Header.Response.FAIL, "Room number already in database.");
				
				reply	= i_msg;
			} else {
				dbcon.modify("INSERT INTO rooms ( " + i_msg.room_id + ", " + i_msg.room_number + ", " + i_msg.room_floor +
						", " + i_msg.room_type + ", " + i_msg.price + ", " +
						(Boolean)i_msg.room_specs.search_Specs("onsuite") + ", " +
						(Boolean)i_msg.room_specs.search_Specs("tv") + ", " +
						(Boolean)i_msg.room_specs.search_Specs("disability") + ", " +
						(Boolean)i_msg.room_specs.search_Specs("elevator") + ", " +
						(Boolean)i_msg.room_specs.search_Specs("ebirdcall") + ", " +
						(Boolean)i_msg.room_specs.search_Specs("emornpaper") + ", " +
						(Boolean)i_msg.room_specs.search_Specs("availability") + ", " +
						(Integer)i_msg.room_specs.search_Specs("numBed") + ")");
				i_msg.fill_Header_Response(Header.Response.SUCCESS, "Room created in database.");
				
				reply = i_msg;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'addRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fill_Header_Response(Header.Response.FAIL, "Update failed.");
			reply	= i_msg;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'addRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fill_Header_Response(Header.Response.FAIL, "Update failed.");
			reply	= i_msg;
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		
		return reply.deepCopy();
	}
	public RoomMessage editRoom(RoomMessage i_msg){
		RoomMessage reply=null;
		databaseHelper 	dbcon = null;
		try {
			dbcon = new databaseHelper(dbname);
			
			ResultSet rs = dbcon.select("SELECT * FROM rooms WHERE num = " + i_msg.room_number);
			if (rs.next()) {
				i_msg.fill_Header_Response(Header.Response.FAIL, "Room number already in database.");
				
				reply	= i_msg;
			} else {
				dbcon.modify("UPDATE rooms ( " + i_msg.room_id + ", " + i_msg.room_number + ", " + i_msg.room_floor +
						", " + i_msg.room_type + ", " + i_msg.price + ", " +
						(Boolean)i_msg.room_specs.search_Specs("onsuite") + ", " +
						(Boolean)i_msg.room_specs.search_Specs("tv") + ", " +
						(Boolean)i_msg.room_specs.search_Specs("disability") + ", " +
						(Boolean)i_msg.room_specs.search_Specs("elevator") + ", " +
						(Boolean)i_msg.room_specs.search_Specs("ebirdcall") + ", " +
						(Boolean)i_msg.room_specs.search_Specs("emornpaper") + ", " +
						(Boolean)i_msg.room_specs.search_Specs("availability") + ", " +
						(Integer)i_msg.room_specs.search_Specs("numBed") + ")");
				i_msg.fill_Header_Response(Header.Response.SUCCESS, "Room updated in database.");
				
				reply = i_msg;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'editRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fill_Header_Response(Header.Response.FAIL, "Update failed.");
			reply	= i_msg;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'editRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fill_Header_Response(Header.Response.FAIL, "Update failed.");
			reply	= i_msg;
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		return reply.deepCopy();
	}
	public RoomMessage deleteRoom(RoomMessage i_msg) {
		RoomMessage reply=null;
		databaseHelper 	dbcon = null;
		try {
			dbcon = new databaseHelper(dbname);
			
			ResultSet rs = dbcon.select("SELECT * FROM rooms WHERE num = " + i_msg.room_number);
			if (!rs.next()) {
				i_msg.fill_Header_Response(Header.Response.FAIL, "Room number not in database.");
				
				reply	= i_msg;
			} else {
				dbcon.modify("UPDATE rooms SET deleted = TRUE WHERE num = " + i_msg.room_number);
				i_msg.fill_Header_Response(Header.Response.SUCCESS, "Room deleted from database.");
				
				reply = i_msg;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'editRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fill_Header_Response(Header.Response.FAIL, "Update failed.");
			reply	= i_msg;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'editRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fill_Header_Response(Header.Response.FAIL, "Update failed.");
			reply	= i_msg;
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		return reply.deepCopy();
	}
	public RoomMessage viewRoom(RoomMessage i_msg) {
		RoomMessage reply=null;
		databaseHelper 	dbcon = null;
		try {
			dbcon = new databaseHelper(dbname);
			
			ResultSet rs = dbcon.select("SELECT * FROM rooms WHERE num = " + i_msg.room_number);
			if (!rs.next()) {
				i_msg.fill_Header_Response(Header.Response.FAIL, "Room number not in database.");
				
				reply	= i_msg;
			} else {
				rs = dbcon.select("SELECT * FROM rooms WHERE num = " + i_msg.room_number);
				while (rs.next()) {
					i_msg.room_number = rs.getInt("num");
					i_msg.room_floor = rs.getInt("floor");
					i_msg.room_type = rs.getString("type");
					i_msg.price = rs.getFloat("price");
					i_msg.available = rs.getBoolean("availability");
					i_msg.available = rs.getBoolean("cleaned");
					i_msg.room_specs.fill_Specs(rs.getBoolean("onsuite"), rs.getBoolean("tv"), rs.getBoolean("disability"), 
							rs.getBoolean("elevator"), rs.getBoolean("ebirdcall"), rs.getBoolean("emornpaper"), 
							rs.getInt("numBed"));
				}
				
				i_msg.fill_Header_Response(Header.Response.SUCCESS, "Room retrieved from database.");
				
				reply = i_msg;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'editRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fill_Header_Response(Header.Response.FAIL, "Update failed.");
			reply	= i_msg;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'editRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fill_Header_Response(Header.Response.FAIL, "Update failed.");
			reply	= i_msg;
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		return reply.deepCopy();
	}
}
