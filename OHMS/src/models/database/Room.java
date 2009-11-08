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
			if (!validateParam(i_msg)){
				i_msg.fillHeaderResponse(Header.Response.FAIL, "Invalid input(s).");
				
				reply	= i_msg;
			} else {
				dbcon = new databaseHelper(dbname);
				Header tempHead = i_msg.returnHeader();
				ResultSet rs = dbcon.select("SELECT * FROM " + tempHead.nameHotel + "_rooms WHERE roomNumber = " + i_msg.room_number);
				if (rs.next()) {
					i_msg.fillHeaderResponse(Header.Response.FAIL, "Room number already in database.");
					
					reply	= i_msg;
				} else {
					int insertStatus = dbcon.modify("INSERT INTO test_rooms (roomID, roomNumber, roomFloor, roomType, price, onsuite, " +
							"tv, disability, elevator, availability, numBed, phone, internet, kitchen, clean) VALUES (" + 
							i_msg.room_id + ", " + 
							i_msg.room_number + ", " + 
							i_msg.room_floor + ", '" + 
							i_msg.room_type + "', " + 
							i_msg.price + ", " + 
							i_msg.room_specs.search_Specs("onsuite") + ", " + 
							i_msg.room_specs.search_Specs("tv") + ", " + 
							i_msg.room_specs.search_Specs("disability") + ", " + 
							i_msg.room_specs.search_Specs("elevator") + ", " + 
							((i_msg.available)?1:0) + ", " + 
							i_msg.room_specs.search_Specs("numBed") + ", " + 
							i_msg.room_specs.search_Specs("phone") + ", " + 
							i_msg.room_specs.search_Specs("internet") + ", " + 
							i_msg.room_specs.search_Specs("kitchen") + ", " + 
							i_msg.cleaned + ")");
					if (insertStatus == 1) {
						i_msg.fillHeaderResponse(Header.Response.SUCCESS, "Room created in database.");
						
						reply = i_msg;
					} else {
						i_msg.fillHeaderResponse(Header.Response.FAIL, "Insert operation failed.");
						
						reply = i_msg;
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Error in 'addRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "Update failed.");
			reply	= i_msg;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'addRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "Update failed.");
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
			if (!validateParam(i_msg)){
				i_msg.fillHeaderResponse(Header.Response.FAIL, "Invalid input(s).");
				
				reply	= i_msg;
			} else {
				dbcon = new databaseHelper(dbname);
				Header tempHead = i_msg.returnHeader();
				ResultSet rs = dbcon.select("SELECT * FROM " + tempHead.nameHotel + "_rooms WHERE roomNumber = " + i_msg.room_number);
				if (!rs.next()) {
					i_msg.fillHeaderResponse(Header.Response.FAIL, "Room number is not in database.");
					
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
						"availability=" + ((i_msg.available)?1:0) + ", " + 
						"numBed=" 		+ i_msg.room_specs.search_Specs("numBed") + ", " + 
						"phone="		+ i_msg.room_specs.search_Specs("phone") + ", " + 
						"internet="		+ i_msg.room_specs.search_Specs("internet") + ", " + 
						"kitchen=" 		+ i_msg.room_specs.search_Specs("kitchen") + ", " + 
						"clean="		+ i_msg.cleaned + " " + 
						"WHERE roomID=" + i_msg.room_id);
					if (updateStatus ==1) {
						i_msg.fillHeaderResponse(Header.Response.SUCCESS, "Room updated in database.");
						
						reply = i_msg;
					} else {
						i_msg.fillHeaderResponse(Header.Response.FAIL, "Update operation failed.");
						
						reply = i_msg;
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Error in 'editRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "Update failed.");
			reply	= i_msg;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'editRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "Update failed.");
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
			if (i_msg.room_number < 0) {
				i_msg.fillHeaderResponse(Header.Response.FAIL, "Invalid input(s).");
				
				reply	= i_msg;
			} else {
				dbcon = new databaseHelper(dbname);
				Header tempHead = i_msg.returnHeader();
				ResultSet rs = dbcon.select("SELECT * FROM " + tempHead.nameHotel + "_rooms WHERE roomNumber = " + i_msg.room_number);

				if (!rs.next()) {
					i_msg.fillHeaderResponse(Header.Response.FAIL, "Room number not in database.");
					
					reply	= i_msg;
				} else {
					int deleteStatus = dbcon.modify("DELETE FROM test_rooms WHERE roomNumber = " + i_msg.room_number);
					
					if (deleteStatus == 1) {
						i_msg.fillHeaderResponse(Header.Response.SUCCESS, "Room deleted from database.");
						
						reply = i_msg;
					} else {
						i_msg.fillHeaderResponse(Header.Response.FAIL, "Delete operation failed.");
						
						reply = i_msg;
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Error in 'editRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "Update failed.");
			reply	= i_msg;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'editRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "Update failed.");
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
			if (i_msg.room_number < 0){
				i_msg.fillHeaderResponse(Header.Response.FAIL, "Invalid input(s).");
				
				reply	= i_msg;
			} else {
				dbcon = new databaseHelper(dbname);
				Header tempHead = i_msg.returnHeader();
				ResultSet rs = dbcon.select("SELECT * FROM " + tempHead.nameHotel + "_rooms WHERE roomNumber = " + i_msg.room_number);
				if (!rs.next()) {
					i_msg.fillHeaderResponse(Header.Response.FAIL, "Room number not in database.");
					
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
						i_msg.cleaned = rs.getBoolean("clean");
						i_msg.room_specs.fill_Specs(rs.getBoolean("onsuite"), rs.getBoolean("tv"), rs.getBoolean("disability"), 
								rs.getBoolean("elevator"), (Integer)rs.getInt("numBed"), rs.getBoolean("phone"), 
								rs.getBoolean("internet"), rs.getBoolean("kitchen"));
					}
					
					i_msg.fillHeaderResponse(Header.Response.SUCCESS, "Room retrieved from database.");
					
					reply = i_msg;
				}
			}
		} catch (SQLException e) {
			System.err.println("Error in 'editRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "Retrival failed.");
			reply	= i_msg;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'editRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "Retrival failed.");
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
		try {
			dbcon = new databaseHelper(dbname);
			Header tempHead = i_msg.returnHeader();
			ResultSet rs = dbcon.select("SELECT * FROM " + tempHead.nameHotel + "_rooms");
			if (!rs.next()) {
				i_msg.fillHeaderResponse(Header.Response.FAIL, "No Rooms in database.");
				output[0] = i_msg.deepCopy();
			} else {
				int i = 0;
				rs = dbcon.select("SELECT * FROM " + tempHead.nameHotel + "_rooms");
				while (rs.next()) {
					i++;
				}
				rs = dbcon.select("SELECT * FROM " + tempHead.nameHotel + "_rooms");
				output = new RoomMessage[i];
				i = 0;
				while (rs.next()) {
					tempMsg = new RoomMessage(tempHead.authLevel, tempHead.messageOwnerID, tempHead.nameHotel, Header.Action.VIEWALL);;
					tempMsg.room_id = rs.getInt("roomID");
					tempMsg.room_number = rs.getInt("roomNumber");
					tempMsg.room_floor = rs.getInt("roomFloor");
					tempMsg.room_type = rs.getString("roomType");
					tempMsg.price = rs.getFloat("price");
					tempMsg.available = rs.getBoolean("availability");
					tempMsg.cleaned = rs.getBoolean("clean");
					tempMsg.room_specs.fill_Specs(rs.getBoolean("onsuite"), rs.getBoolean("tv"), rs.getBoolean("disability"), 
							rs.getBoolean("elevator"), (Integer)rs.getInt("numBed"), rs.getBoolean("phone"), 
							rs.getBoolean("internet"), rs.getBoolean("kitchen"));
					tempMsg.fillHeaderResponse(Header.Response.SUCCESS, "Rooms retrieved from database.");
					output[i] = tempMsg;
					i++;
				}
			}
		} catch (SQLException e) {
			System.err.println("Error in 'editRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "Update failed.");
			output[0] = i_msg.deepCopy();
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'editRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "Update failed.");
			output[0] = i_msg.deepCopy();
		}
		finally {
			if (dbcon != null) dbcon.close();
		}	
		return output;
	}

	public RoomMessage[] viewSpecificRoom(RoomMessage i_msg){
		RoomMessage[] output= new RoomMessage[1];
		RoomMessage reply = null;
		databaseHelper 	dbcon = null;
		RoomMessage tempMsg = null;
		try {
			dbcon = new databaseHelper(dbname);
			Header tempHead = i_msg.returnHeader();
			String dbString = "SELECT * FROM " + tempHead.nameHotel + "_rooms WHERE ";
			
			boolean nonFirst = false;
			if (i_msg.available) {
				dbString = dbString + "availability=1";
				nonFirst = true;
			}
			if (i_msg.room_type != "") {
				if (nonFirst) dbString = dbString + " AND ";
				dbString = dbString + "roomType='" + i_msg.room_type + "'";
				nonFirst = true;
			}
			if (i_msg.cleaned) {
				if (nonFirst) dbString = dbString + " AND ";
				dbString = dbString + "clean=1";
				nonFirst = true;
			}
			if (i_msg.room_floor != 0) {
				if (nonFirst) dbString = dbString + " AND ";
				dbString = dbString + "roomFloor=" + i_msg.room_floor;
				nonFirst = true;
			}
			ResultSet rs = dbcon.select(dbString);
			if (!rs.next()) {
				i_msg.fillHeaderResponse(Header.Response.FAIL, "No Rooms matched in database.");
				output[0] = i_msg;
			} else {
				int i = 0;
				rs = dbcon.select(dbString);
				while (rs.next()) {
					i++;
				}
				rs = dbcon.select(dbString);
				output = new RoomMessage[i];
				i = 0;
				while (rs.next()) {
					tempMsg = new RoomMessage(tempHead.authLevel, tempHead.messageOwnerID, tempHead.nameHotel, Header.Action.VIEWALL);;
					tempMsg.room_id = rs.getInt("roomID");
					tempMsg.room_number = rs.getInt("roomNumber");
					tempMsg.room_floor = rs.getInt("roomFloor");
					tempMsg.room_type = rs.getString("roomType");
					tempMsg.price = rs.getFloat("price");
					tempMsg.available = rs.getBoolean("availability");
					tempMsg.cleaned = rs.getBoolean("clean");
					tempMsg.room_specs.fill_Specs(rs.getBoolean("onsuite"), rs.getBoolean("tv"), rs.getBoolean("disability"), 
							rs.getBoolean("elevator"), (Integer)rs.getInt("numBed"), rs.getBoolean("phone"), 
							rs.getBoolean("internet"), rs.getBoolean("kitchen"));
					tempMsg.fillHeaderResponse(Header.Response.SUCCESS, "Rooms retrieved from database.");
					output[i] = tempMsg;
					i++;
				}
			}
		} catch (SQLException e) {
			System.err.println("Error in 'editRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "Update failed.");
			output[0] = i_msg.deepCopy();
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'editRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "Update failed.");
			output[0] = i_msg.deepCopy();
		}
		finally {
			if (dbcon != null) dbcon.close();
		}	
		return output;
	}

	
	private boolean validateParam(RoomMessage i_msg){
		boolean validity = true;
		
		if (i_msg.room_id < 0 || i_msg.room_number < 0 || i_msg.room_floor < 0 || i_msg.price < 0 ||
				(Integer)i_msg.room_specs.search_Specs("numBed") < 0) {
			validity = false;		
		} if (i_msg.room_type == "" || i_msg.room_type == null) {
			validity = false;
		}
		return validity;
	}
}
