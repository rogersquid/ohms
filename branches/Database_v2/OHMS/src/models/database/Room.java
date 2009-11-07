package models.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import models.messages.*;

public class Room {
	String dbname = "default_hotel";
	
	public RoomMessage addRoom(RoomMessage i_msg) {
		RoomMessage reply=null;
		databaseHelper 	dbcon = null;
		try {
			dbcon = new databaseHelper(dbname);
			
			ResultSet rs = dbcon.select("SELECT * FROM test_rooms WHERE roomNumber = " + i_msg.room_number);
			if (rs.next()) {
				i_msg.fill_Header_Response(Header.Response.FAIL, "Room number already in database.");
				
				reply	= i_msg;
			} else if (i_msg.room_type == ""){
				i_msg.fill_Header_Response(Header.Response.FAIL, "Invalid input(s).");
				
				reply	= i_msg;
			} else {
				int insertStatus = dbcon.modify("INSERT INTO test_rooms (roomID, roomNumber, roomFloor, roomType, price, onsuite, " +
						"tv, disability, elevator, ebirdcall, emornpaper, availability, numBed) VALUES (" + 
						i_msg.room_id + ", " + 
						i_msg.room_number + ", " + 
						i_msg.room_floor + ", '" + 
						i_msg.room_type + "', " + 
						i_msg.price + ", " + 
						i_msg.room_specs.search_Specs("onsuite") + ", " + 
						i_msg.room_specs.search_Specs("tv") + ", " + 
						i_msg.room_specs.search_Specs("disability") + ", " + 
						i_msg.room_specs.search_Specs("elevator") + ", " + 
						i_msg.room_specs.search_Specs("ebirdcall") + ", " + 
						i_msg.room_specs.search_Specs("emornpaper") + ", " + 
						((i_msg.available)?1:0) + ", " + 
						i_msg.room_specs.search_Specs("numBed") + ")");
				if (insertStatus == 1) {
					i_msg.fill_Header_Response(Header.Response.SUCCESS, "Room created in database.");
					
					reply = i_msg;
				} else {
					i_msg.fill_Header_Response(Header.Response.FAIL, "Insert operation failed.");
					
					reply = i_msg;
				}
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
			
			ResultSet rs = dbcon.select("SELECT * FROM test_rooms WHERE roomNumber = " + i_msg.room_number);
			if (!rs.next()) {
				i_msg.fill_Header_Response(Header.Response.FAIL, "Room number is not in database.");
				
				reply	= i_msg;
			} else if (i_msg.room_type == ""){
				i_msg.fill_Header_Response(Header.Response.FAIL, "Invalid input(s).");
				
				reply	= i_msg;
			} else {
				int updateStatus = dbcon.modify("UPDATE test_rooms SET " +
					"roomNumber=" 	+ i_msg.room_number + ", " + 
					"roomFloor=" 	+ i_msg.room_floor + ", " + 
					"roomType='" 	+ i_msg.room_type + "', " + 
					"price=" 		+ i_msg.price + ", " + 
					"onsuite=" 		+ i_msg.room_specs.search_Specs("onsuite") + ", " + 
					"tv=" 			+ i_msg.room_specs.search_Specs("tv") + ", " + 
					"disability=" 	+ i_msg.room_specs.search_Specs("disability") + ", " + 
					"elevator=" 	+ i_msg.room_specs.search_Specs("elevator") + ", " + 
					"ebirdcall=" 	+ i_msg.room_specs.search_Specs("ebirdcall") + ", " + 
					"emornpaper=" 	+ i_msg.room_specs.search_Specs("emornpaper") + ", " + 
					"availability=" + ((i_msg.available)?1:0) + ", " + 
					"numBed=" 		+ i_msg.room_specs.search_Specs("numBed") + " " + 
					"WHERE roomID=" + i_msg.room_id);
				if (updateStatus ==1) {
					i_msg.fill_Header_Response(Header.Response.SUCCESS, "Room updated in database.");
					
					reply = i_msg;
				} else {
					i_msg.fill_Header_Response(Header.Response.FAIL, "Update operation failed.");
					
					reply = i_msg;
				}
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
			
			ResultSet rs = dbcon.select("SELECT * FROM test_rooms WHERE roomNumber = " + i_msg.room_number);
			if (!rs.next()) {
				i_msg.fill_Header_Response(Header.Response.FAIL, "Room number not in database.");
				
				reply	= i_msg;
			} else {
				int deleteStatus = dbcon.modify("DELETE FROM test_rooms WHERE roomNumber = " + i_msg.room_number);
				
				if (deleteStatus == 1) {
					i_msg.fill_Header_Response(Header.Response.SUCCESS, "Room deleted from database.");
					
					reply = i_msg;
				} else {
					i_msg.fill_Header_Response(Header.Response.FAIL, "Delete operation failed.");
					
					reply = i_msg;
				}
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
			
			ResultSet rs = dbcon.select("SELECT * FROM test_rooms WHERE roomNumber = " + i_msg.room_number);
			if (!rs.next()) {
				i_msg.fill_Header_Response(Header.Response.FAIL, "Room number not in database.");
				
				reply	= i_msg;
			} else {
				rs = dbcon.select("SELECT * FROM test_rooms WHERE roomNumber = " + i_msg.room_number);
				while (rs.next()) {
					i_msg.room_id = rs.getInt("roomID");
					i_msg.room_number = rs.getInt("roomNumber");
					i_msg.room_floor = rs.getInt("roomFloor");
					i_msg.room_type = rs.getString("roomType");
					i_msg.price = rs.getFloat("price");
					i_msg.available = rs.getBoolean("availability");
					//i_msg.cleaned = rs.getBoolean("cleaned");
					i_msg.room_specs.fill_Specs(rs.getBoolean("onsuite"), rs.getBoolean("tv"), rs.getBoolean("disability"), 
							rs.getBoolean("elevator"), rs.getBoolean("ebirdcall"), rs.getBoolean("emornpaper"), 
							(Integer)rs.getInt("numBed"));
				}
				
				i_msg.fill_Header_Response(Header.Response.SUCCESS, "Room retrieved from database.");
				
				reply = i_msg;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'editRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fill_Header_Response(Header.Response.FAIL, "Retrival failed.");
			reply	= i_msg;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'editRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fill_Header_Response(Header.Response.FAIL, "Retrival failed.");
			reply	= i_msg;
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		return reply.deepCopy();
	}
	public RoomMessage[] viewAllRoom(RoomMessage i_msg){
		RoomMessage[] output= new RoomMessage[1];
		RoomMessage reply = null;
		databaseHelper 	dbcon = null;
		RoomMessage tempMsg = null;
		int i = 0;
		try {
			dbcon = new databaseHelper(dbname);
			
			ResultSet rs = dbcon.select("SELECT * FROM test_rooms");
			if (!rs.next()) {
				i_msg.fill_Header_Response(Header.Response.FAIL, "Room number not in database.");
				output[0] = i_msg.deepCopy();
				i++;
			} else {
				while (rs.next()) {
					i++;
				}
				rs = dbcon.select("SELECT * FROM test_rooms");
				output = new RoomMessage[i];
				i = 0;
				tempMsg = i_msg.deepCopy();
				while (rs.next()) {
					tempMsg.room_number = rs.getInt("num");
					tempMsg.room_floor = rs.getInt("floor");
					tempMsg.room_type = rs.getString("type");
					tempMsg.price = rs.getFloat("price");
					tempMsg.available = rs.getBoolean("availability");
					tempMsg.available = rs.getBoolean("cleaned");
					tempMsg.room_specs.fill_Specs(rs.getBoolean("onsuite"), rs.getBoolean("tv"), rs.getBoolean("disability"), 
							rs.getBoolean("elevator"), rs.getBoolean("ebirdcall"), rs.getBoolean("emornpaper"), 
							rs.getInt("numBed"));
					tempMsg.fill_Header_Response(Header.Response.SUCCESS, "Room retrieved from database.");
					output[i++] = tempMsg.deepCopy();
				}
			}
		} catch (SQLException e) {
			System.err.println("Error in 'editRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fill_Header_Response(Header.Response.FAIL, "Update failed.");
			output[0] = i_msg.deepCopy();
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'editRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fill_Header_Response(Header.Response.FAIL, "Update failed.");
			output[0] = i_msg.deepCopy();
		}
		finally {
			if (dbcon != null) dbcon.close();
		}	
		return output;
	}
}
