package models.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import models.messages.*;

public class Room {
	public Message addRoom(Message i_msg){
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.rooms=i_msg.rooms;
		try {
			// create connection
			dbcon = new databaseHelper(i_msg.header.nameHotel);
			// query the database to see if the room number exist already
			ResultSet rs = dbcon.select("SELECT * FROM " + i_msg.header.nameHotel + "_rooms WHERE roomNumber = " + i_msg.rooms[0].roomNumber);
			// check query result
			if (rs.next()) {
				// room number already exist
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
				replyMessage.response.responseString = "Room number already in database.";
			} else {
				// insert room into the database
				int insertStatus = dbcon.modify("INSERT INTO " + i_msg.header.nameHotel + "_rooms (roomID, roomNumber, roomFloor, roomType, price, onsuite, " +
						"tv, disability, elevator, availability, phone, internet, kitchen, clean, singleBeds, queenBeds, kingBeds ) VALUES (" + 
						i_msg.rooms[0].roomID + ", " + 
						i_msg.rooms[0].roomNumber + ", " + 
						i_msg.rooms[0].floor + ", '" + 
						i_msg.rooms[0].roomType + "', " + 
						i_msg.rooms[0].price + ", " + 
						((i_msg.rooms[0].onsuite)?1:0) + ", " + 
						((i_msg.rooms[0].tv)?1:0) + ", " + 
						((i_msg.rooms[0].disabilityAccess)?1:0) + ", " + 
						((i_msg.rooms[0].elevator)?1:0) + ", " + 
						((i_msg.rooms[0].available)?1:0) + ", " + 
						((i_msg.rooms[0].phone)?1:0) + ", " + 
						((i_msg.rooms[0].internet)?1:0) + ", " + 
						((i_msg.rooms[0].kitchen)?1:0) + ", " + 
						((i_msg.rooms[0].cleaned)?1:0) + ", " +
						i_msg.rooms[0].singleBeds + ", " +
						i_msg.rooms[0].queenBeds + ", " +
						i_msg.rooms[0].kingBeds + ")");
				if (insertStatus == 1) {
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Room created in database.";
				} else {
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
					replyMessage.response.responseString = "Insert operation failed.";
				}
			}
		
		} catch (SQLException e) {
			System.err.println("Error in 'addRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Insert failed.";
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'addRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Insert failed.";
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		
		return replyMessage;
	}
	
	public Message editRoom(Message i_msg){
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.rooms=i_msg.rooms;
		try {
			// create connection
			dbcon = new databaseHelper(i_msg.header.nameHotel);
			// query the database for the room that matches roomID
			ResultSet rs = dbcon.select("SELECT * FROM " + i_msg.header.nameHotel + "_rooms WHERE roomID = " + i_msg.rooms[0].roomID);
			rs.next();	
			// see if request want to edit roomNumber
			if (rs.getInt("roomNumber") != i_msg.rooms[0].roomNumber) {
				// query database for room that matches roomNumber
				rs = dbcon.select("SELECT * FROM " + i_msg.header.nameHotel + "_rooms WHERE roomNumber = " + i_msg.rooms[0].roomNumber);
				// check that the roomNumber does not already exists
				if (rs.next()) {
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
					replyMessage.response.responseString = "Room number already in database.";
					if (dbcon != null) dbcon.close();
					return replyMessage;
				} 
			}
			// update room in database that match roomID
			int updateStatus = dbcon.modify("UPDATE " + i_msg.header.nameHotel + "_rooms SET " +
					"roomNumber="	+ i_msg.rooms[0].roomNumber + ", " +
					"roomFloor="	+ i_msg.rooms[0].floor + ", " +
					"roomType="		+ i_msg.rooms[0].roomType + ", " +
					"price="		+ i_msg.rooms[0].price + ", " +
					"onsuite="		+ ((i_msg.rooms[0].onsuite)?1:0) + ", " +
					"tv="			+ ((i_msg.rooms[0].tv)?1:0) + ", " +
					"disability="	+ ((i_msg.rooms[0].disabilityAccess)?1:0) + ", " +
					"elevator="		+ ((i_msg.rooms[0].elevator)?1:0) + ", " +
					"availability="	+ ((i_msg.rooms[0].available)?1:0) + ", " +
					"phone=" 		+ ((i_msg.rooms[0].phone)?1:0) + ", " +
					"internet=" 	+ ((i_msg.rooms[0].internet)?1:0) + ", " +
					"kitchen=" 		+ ((i_msg.rooms[0].kitchen)?1:0) + ", " +
					"clean="		+ ((i_msg.rooms[0].cleaned)?1:0) + ", " +
					"singleBeds=" 	+ i_msg.rooms[0].singleBeds + ", " +
					"queenBeds="	+ i_msg.rooms[0].queenBeds + ", " +
					"kingBeds="		+ i_msg.rooms[0].kingBeds + " " +
					"WHERE roomID=" + i_msg.rooms[0].roomID);
			if (updateStatus == 1) {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Room updated.";
			} else {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
				replyMessage.response.responseString = "Update operation failed.";
			}
		} catch (SQLException e) {
			System.err.println("Error in 'editRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Update failed.";
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'editRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Update failed.";
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		return replyMessage;
	}
	
	public Message deleteRoom(Message i_msg) {
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.rooms=i_msg.rooms;
		try {
			// create connection
			dbcon = new databaseHelper(i_msg.header.nameHotel);
			// query the database for 
			ResultSet rs = dbcon.select("SELECT * FROM " + i_msg.header.nameHotel + "_rooms WHERE roomID = " + i_msg.rooms[0].roomID);
			rs.next();
			int deleteStatus = dbcon.modify("DELETE FROM " + i_msg.header.nameHotel + "_rooms WHERE roomID = " + i_msg.rooms[0].roomID);
			if (deleteStatus == 1) {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Room deleted.";
			} else {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
				replyMessage.response.responseString = "Delete operation failed.";
			}
		} catch (SQLException e) {
			System.err.println("Error in 'deleteRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Delete failed.";
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'deleteRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Delete failed.";
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		return replyMessage;
	}
	
	public Message getAllRooms(Message i_msg) {
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.rooms=i_msg.rooms;
		try {
			// create connection
			dbcon = new databaseHelper(i_msg.header.nameHotel);
			// query the database for all rooms
			ResultSet rs = dbcon.select("SELECT * FROM " + i_msg.header.nameHotel + "_rooms");
			if (!rs.next()) {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "No Rooms in database.";
			} else {
				int i = 0;
				rs = dbcon.select("SELECT * FROM " + i_msg.header.nameHotel + "_rooms");
				while (rs.next()) {
					i++;
				}
				rs = dbcon.select("SELECT * FROM " + i_msg.header.nameHotel + "_rooms");
				replyMessage.rooms = new RoomMessage[i];
				i = 0;
				
				while (rs.next()) {
					replyMessage.rooms[i].roomID = rs.getInt("roomID");
					replyMessage.rooms[i].roomNumber = rs.getInt("roomNumber");
					replyMessage.rooms[i].floor = rs.getInt("roomFloor");
					replyMessage.rooms[i].roomType = rs.getString("roomType");
					replyMessage.rooms[i].price = rs.getFloat("price");
					replyMessage.rooms[i].onsuite = rs.getBoolean("onsuite");
					replyMessage.rooms[i].tv = rs.getBoolean("tv");
					replyMessage.rooms[i].disabilityAccess = rs.getBoolean("disability");
					replyMessage.rooms[i].elevator = rs.getBoolean("elevator");
					replyMessage.rooms[i].available = rs.getBoolean("availability");
					replyMessage.rooms[i].phone = rs.getBoolean("phone");
					replyMessage.rooms[i].internet = rs.getBoolean("internet");
					replyMessage.rooms[i].kitchen = rs.getBoolean("kitchen");
					replyMessage.rooms[i].cleaned = rs.getBoolean("clean");
					replyMessage.rooms[i].singleBeds = rs.getInt("singleBeds");
					replyMessage.rooms[i].queenBeds = rs.getInt("queenBeds");
					replyMessage.rooms[i].kingBeds = rs.getInt("kingBeds");
					i++;
				}
			}
		} catch (SQLException e) {
			System.err.println("Error in 'getAllRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'getAllRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		return replyMessage;
	}
	
	public Message getRoom(Message i_msg) {
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.rooms=i_msg.rooms;
		try {
			// create connection
			dbcon = new databaseHelper(i_msg.header.nameHotel);
			// query the database for all rooms
			ResultSet rs = dbcon.select("SELECT * FROM " + i_msg.header.nameHotel + "_rooms WHERE roomID = " + i_msg.rooms[0].roomID);
			while (rs.next()) {
				replyMessage.rooms[0].roomID = rs.getInt("roomID");
				replyMessage.rooms[0].roomNumber = rs.getInt("roomNumber");
				replyMessage.rooms[0].floor = rs.getInt("roomFloor");
				replyMessage.rooms[0].roomType = rs.getString("roomType");
				replyMessage.rooms[0].price = rs.getFloat("price");
				replyMessage.rooms[0].onsuite = rs.getBoolean("onsuite");
				replyMessage.rooms[0].tv = rs.getBoolean("tv");
				replyMessage.rooms[0].disabilityAccess = rs.getBoolean("disability");
				replyMessage.rooms[0].elevator = rs.getBoolean("elevator");
				replyMessage.rooms[0].available = rs.getBoolean("availability");
				replyMessage.rooms[0].phone = rs.getBoolean("phone");
				replyMessage.rooms[0].internet = rs.getBoolean("internet");
				replyMessage.rooms[0].kitchen = rs.getBoolean("kitchen");
				replyMessage.rooms[0].cleaned = rs.getBoolean("clean");
				replyMessage.rooms[0].singleBeds = rs.getInt("singleBeds");
				replyMessage.rooms[0].queenBeds = rs.getInt("queenBeds");
				replyMessage.rooms[0].kingBeds = rs.getInt("kingBeds");
			}
		} catch (SQLException e) {
			System.err.println("Error in 'deleteRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Delete failed.";
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'deleteRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Delete failed.";
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		return replyMessage;
	}
	
	public Message getFilteredRooms(Message i_msg) {
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.rooms=i_msg.rooms;
		try {
			// create connection
			dbcon = new databaseHelper(i_msg.header.nameHotel);
			
			String queryString = "SELECT * FROM " + i_msg.header.nameHotel + "_rooms WHERE ";
			
			boolean nonFirst = false;
			if (i_msg.rooms[0].available) {
				queryString = queryString + "availability=1";
				nonFirst = true;
			}
			if (i_msg.rooms[0].roomType != "") {
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "roomType='" + i_msg.rooms[0].roomType + "'";
				nonFirst = true;
			}
			if (i_msg.rooms[0].cleaned) {
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "clean=1";
				nonFirst = true;
			}
			if (i_msg.rooms[0].floor != 0) {
				if (nonFirst) queryString = queryString + " AND ";
				queryString = queryString + "roomFloor=" + i_msg.rooms[0].floor;
				nonFirst = true;
			}
			
			// query the database for all rooms
			ResultSet rs = dbcon.select(queryString);
			if (!rs.next()) {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "No Rooms in database.";
			} else {
				int i = 0;
				rs = dbcon.select(queryString);
				while (rs.next()) {
					i++;
				}
				rs = dbcon.select(queryString);
				replyMessage.rooms = new RoomMessage[i];
				i = 0;
				
				while (rs.next()) {
					replyMessage.rooms[i].roomID = rs.getInt("roomID");
					replyMessage.rooms[i].roomNumber = rs.getInt("roomNumber");
					replyMessage.rooms[i].floor = rs.getInt("roomFloor");
					replyMessage.rooms[i].roomType = rs.getString("roomType");
					replyMessage.rooms[i].price = rs.getFloat("price");
					replyMessage.rooms[i].onsuite = rs.getBoolean("onsuite");
					replyMessage.rooms[i].tv = rs.getBoolean("tv");
					replyMessage.rooms[i].disabilityAccess = rs.getBoolean("disability");
					replyMessage.rooms[i].elevator = rs.getBoolean("elevator");
					replyMessage.rooms[i].available = rs.getBoolean("availability");
					replyMessage.rooms[i].phone = rs.getBoolean("phone");
					replyMessage.rooms[i].internet = rs.getBoolean("internet");
					replyMessage.rooms[i].kitchen = rs.getBoolean("kitchen");
					replyMessage.rooms[i].cleaned = rs.getBoolean("clean");
					replyMessage.rooms[i].singleBeds = rs.getInt("singleBeds");
					replyMessage.rooms[i].queenBeds = rs.getInt("queenBeds");
					replyMessage.rooms[i].kingBeds = rs.getInt("kingBeds");
					i++;
				}
			}
		} catch (SQLException e) {
			System.err.println("Error in 'getAllRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'getAllRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		return replyMessage;
	}
}
