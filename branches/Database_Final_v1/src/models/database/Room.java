package models.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import models.messages.*;

public class Room {
	public Message addRoom(Message i_msg){
		/*
		 * OVERVIEW: Adds a room to the database
		 * PRECONDITIONS: Parameters have been validated
		 * MODIFIES: Adds room with given parameters in the rooms table of the database
		 * POSTCONDITIONS: If addition was successful, the room with the correct parameters will be added to the Database
		 */
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.rooms=i_msg.rooms;

		try {
			// create connection
			dbcon = new databaseHelper();
			// query the database to see if the room number exist already
			ResultSet rs = dbcon.select("SELECT * FROM " + i_msg.header.nameHotel + "_rooms WHERE roomNumber = " + i_msg.rooms[0].roomNumber);
			// check query result
			if (rs.next()) {
				// room number already exist
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
				replyMessage.response.responseString = "Room number already in database.";
			} else {
				// insert room into the database
				int insertID = dbcon.insert("INSERT INTO " + i_msg.header.nameHotel + "_rooms (roomNumber, floor, roomType, price, onsuite, " +
						"tv, disabilityAccess, elevator, available, phone, internet, kitchen, cleaned, singleBeds, queenBeds, kingBeds ) VALUES (" +
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
				replyMessage.rooms[0].roomID = insertID;
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Room created.";
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
		/*
		 * OVERVIEW: Edits an room that is already in the database
		 * PRECONDITIONS: Parameters have been validated
		 * MODIFIES: Edits the room details with the given parameters in the rooms table of the database
		 * POSTCONDITIONS: The specified room will be edited with the given parameters in the preconditions
		 */
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.rooms=i_msg.rooms;
		int updateStatus;
		try {
			// create connection
			dbcon = new databaseHelper();

			//assume that the maid has the authorization level of 3
			if (i_msg.header.authLevel != 3) {
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
				updateStatus = dbcon.modify("UPDATE " + i_msg.header.nameHotel + "_rooms SET " +
						"roomNumber="	+ i_msg.rooms[0].roomNumber + ", " +
						"floor="	+ i_msg.rooms[0].floor + ", " +
						"roomType='"		+ i_msg.rooms[0].roomType + "', " +
						"price="		+ i_msg.rooms[0].price + ", " +
						"onsuite="		+ ((i_msg.rooms[0].onsuite)?1:0) + ", " +
						"tv="			+ ((i_msg.rooms[0].tv)?1:0) + ", " +
						"disabilityAccess="	+ ((i_msg.rooms[0].disabilityAccess)?1:0) + ", " +
						"elevator="		+ ((i_msg.rooms[0].elevator)?1:0) + ", " +
						"available="	+ ((i_msg.rooms[0].available)?1:0) + ", " +
						"phone=" 		+ ((i_msg.rooms[0].phone)?1:0) + ", " +
						"internet=" 	+ ((i_msg.rooms[0].internet)?1:0) + ", " +
						"kitchen=" 		+ ((i_msg.rooms[0].kitchen)?1:0) + ", " +
						"cleaned="		+ ((i_msg.rooms[0].cleaned)?1:0) + ", " +
						"singleBeds=" 	+ i_msg.rooms[0].singleBeds + ", " +
						"queenBeds="	+ i_msg.rooms[0].queenBeds + ", " +
						"kingBeds="		+ i_msg.rooms[0].kingBeds + " " +
						"WHERE roomID=" + i_msg.rooms[0].roomID);
			} else {
				updateStatus = dbcon.modify("UPDATE " + i_msg.header.nameHotel + "_rooms SET cleaned=1 WHERE roomNumber=" +
						i_msg.rooms[0].roomNumber);
			}
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
		/*
		 * OVERVIEW: Deletes a room from the database that is identified by the room ID
		 * PRECONDITIONS: Parameters have been validated
		 * MODIFIES: Deletes the room and its information from the rooms table of the database
		 * POSTCONDITIONS: The specified room will be deleted with the given room ID from preconditions
		 */
		// Creating database handle and create return message
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.rooms=i_msg.rooms;

		try {
			// create connection
			dbcon = new databaseHelper();
			// query the database for
			ResultSet rs = dbcon.select("SELECT * FROM " + i_msg.header.nameHotel + "_rooms WHERE roomNumber = " + i_msg.rooms[0].roomNumber);
			rs.next();
			int deleteStatus = dbcon.modify("DELETE FROM " + i_msg.header.nameHotel + "_rooms WHERE roomNumber = " + i_msg.rooms[0].roomNumber);
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
		/*
		 * OVERVIEW: Returns the list of all rooms that this user has authority to view. Returns a Message class with an array RoomMessage objects.
		 * PRECONDITIONS: None
		 * MODIFIES: None
		 * POSTCONDITIONS: Message contains an array of RoomMessage objects that represent the list of rooms viewable by this user
		 */
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.rooms=i_msg.rooms;

		try {
			// create connection
			dbcon = new databaseHelper();
			// query the database for all rooms
			ResultSet rs = dbcon.select("SELECT * FROM " + i_msg.header.nameHotel + "_rooms ORDER BY roomNumber ASC");
			if (!rs.next()) {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.initializeRooms(0);
				replyMessage.response.responseString = "No Rooms in database.";
			} else {
				int i = 0;
				rs.beforeFirst();
				while (rs.next()) {
					i++;
				}
				rs.beforeFirst();
				replyMessage.initializeRooms(i);
				i = 0;

				while (rs.next()) {
					replyMessage.rooms[i].roomID = rs.getInt("roomID");
					replyMessage.rooms[i].roomNumber = rs.getInt("roomNumber");
					replyMessage.rooms[i].floor = rs.getInt("floor");
					replyMessage.rooms[i].roomType = rs.getString("roomType");
					replyMessage.rooms[i].price = rs.getFloat("price");
					replyMessage.rooms[i].onsuite = rs.getBoolean("onsuite");
					replyMessage.rooms[i].tv = rs.getBoolean("tv");
					replyMessage.rooms[i].disabilityAccess = rs.getBoolean("disabilityAccess");
					replyMessage.rooms[i].elevator = rs.getBoolean("elevator");
					replyMessage.rooms[i].available = rs.getBoolean("available");
					replyMessage.rooms[i].phone = rs.getBoolean("phone");
					replyMessage.rooms[i].internet = rs.getBoolean("internet");
					replyMessage.rooms[i].kitchen = rs.getBoolean("kitchen");
					replyMessage.rooms[i].cleaned = rs.getBoolean("cleaned");
					replyMessage.rooms[i].singleBeds = rs.getInt("singleBeds");
					replyMessage.rooms[i].queenBeds = rs.getInt("queenBeds");
					replyMessage.rooms[i].kingBeds = rs.getInt("kingBeds");
					i++;
				}
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Query succeeded.";
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
		/*
		 * OVERVIEW: Retrieves a specific room. Used to select a room to view from the list of room returned by getAllRooms function
		 * PRECONDITIONS: The specified room is selected from the list of rooms returned by getAllRooms. Parameters have been validated.
		 * MODIFIES: None
		 * POSTCONDITIONS: The specified room is returned, if found; placed in rooms[0] of returned Message
		 */
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.rooms=i_msg.rooms;

		try {
			// create connection
			dbcon = new databaseHelper();
			// query the database for all rooms
			ResultSet rs;
			if(i_msg.rooms[0].roomID > 0) {
				rs = dbcon.select("SELECT * FROM " + i_msg.header.nameHotel + "_rooms WHERE roomID = " + i_msg.rooms[0].roomID);
			} else if(i_msg.rooms[0].roomNumber > 0) {
				rs = dbcon.select("SELECT * FROM " + i_msg.header.nameHotel + "_rooms WHERE roomNumber = " + i_msg.rooms[0].roomNumber);
			} else {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
				replyMessage.response.responseString = "Both room ID and number are invalid.";
				return replyMessage;
			}
			while (rs.next()) {
				replyMessage.rooms[0].roomID = rs.getInt("roomID");
				replyMessage.rooms[0].roomNumber = rs.getInt("roomNumber");
				replyMessage.rooms[0].floor = rs.getInt("floor");
				replyMessage.rooms[0].roomType = rs.getString("roomType");
				replyMessage.rooms[0].price = rs.getFloat("price");
				replyMessage.rooms[0].onsuite = rs.getBoolean("onsuite");
				replyMessage.rooms[0].tv = rs.getBoolean("tv");
				replyMessage.rooms[0].disabilityAccess = rs.getBoolean("disabilityAccess");
				replyMessage.rooms[0].elevator = rs.getBoolean("elevator");
				replyMessage.rooms[0].available = rs.getBoolean("available");
				replyMessage.rooms[0].phone = rs.getBoolean("phone");
				replyMessage.rooms[0].internet = rs.getBoolean("internet");
				replyMessage.rooms[0].kitchen = rs.getBoolean("kitchen");
				replyMessage.rooms[0].cleaned = rs.getBoolean("cleaned");
				replyMessage.rooms[0].singleBeds = rs.getInt("singleBeds");
				replyMessage.rooms[0].queenBeds = rs.getInt("queenBeds");
				replyMessage.rooms[0].kingBeds = rs.getInt("kingBeds");
			}
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
			replyMessage.response.responseString = "Query succeeded.";
		} catch (SQLException e) {
			System.err.println("Error in 'getRoom'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'getRoom'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		}
		finally {
			if (dbcon != null) dbcon.close();
		}
		return replyMessage;
	}

	public Message getFilteredRooms(Message i_msg) {
		/*
		 * OVERVIEW: Returns a list of rooms matching the specified parameters
		 * PRECONDITIONS: Desired filtered properties (floor, roomNumber, roomType, price, onsuite, TV, disabilityAccess, elevator, available, phone, internet, kitchen, cleaned, singleBed, queenBed, kingBed)
		 * MODIFIES: None
		 * POSTCONDITIONS: Print out all rooms with given properties from preconditions
		 */
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.rooms = i_msg.rooms;
		replyMessage.bookings = i_msg.bookings;

		try {
			// create connection
			dbcon = new databaseHelper();

			String queryString = "SELECT * FROM " + i_msg.header.nameHotel + "_rooms WHERE 1 ";

			//first RoomMessage holds the filter toggles, and the second message hold the filter values
			if (i_msg.rooms[0].floor != 0) {
				queryString = queryString + " AND floor=" + i_msg.rooms[1].floor;
			}
			if (i_msg.rooms[0].roomNumber != 0) {
				queryString = queryString + " AND roomNumber=" + i_msg.rooms[1].roomNumber;
			}
			if (i_msg.rooms[0].roomType == "CHECK") {;
				queryString = queryString + " AND roomType='" + i_msg.rooms[1].roomType + "'";
			}
			if (i_msg.rooms[0].price != 0) {
				queryString = queryString + " AND price=" + i_msg.rooms[1].price;
			}
			if (i_msg.rooms[0].onsuite) {
				queryString = queryString + " AND onsuite=" + ((i_msg.rooms[1].onsuite)?1:0);
			}
			if (i_msg.rooms[0].tv) {
				queryString = queryString + " AND tv=" + ((i_msg.rooms[1].tv)?1:0);
			}
			if (i_msg.rooms[0].disabilityAccess) {
				queryString = queryString + " AND disabilityAccess=" + ((i_msg.rooms[1].disabilityAccess)?1:0);
			}
			if (i_msg.rooms[0].elevator) {
				queryString = queryString + " AND elevator=" + ((i_msg.rooms[1].elevator)?1:0);
			}
			if (i_msg.rooms[0].available) {
				queryString = queryString + " AND available=" + ((i_msg.rooms[1].available)?1:0);
			}
			if (i_msg.rooms[0].phone) {
				queryString = queryString + " AND phone=" + ((i_msg.rooms[1].phone)?1:0);
			}
			if (i_msg.rooms[0].internet) {
				queryString = queryString + " AND internet=" + ((i_msg.rooms[1].internet)?1:0);
			}
			if (i_msg.rooms[0].kitchen) {
				queryString = queryString + " AND kitchen=" + ((i_msg.rooms[1].kitchen)?1:0);
			}
			if (i_msg.rooms[0].cleaned) {
				queryString = queryString + " AND cleaned=" + ((i_msg.rooms[1].cleaned)?1:0);
			}
			if (i_msg.rooms[0].singleBeds != 0) {
				queryString = queryString + " AND singleBeds>=" + i_msg.rooms[1].singleBeds;
			}
			if (i_msg.rooms[0].queenBeds != 0) {
				queryString = queryString + " AND queenBeds>=" + i_msg.rooms[1].queenBeds;
			}
			if (i_msg.rooms[0].kingBeds != 0) {
				queryString = queryString + " AND kingBeds>=" + i_msg.rooms[1].kingBeds;
			}

			if (i_msg.bookings != null) {
				if(i_msg.bookings[0].startDate!=null && i_msg.bookings[0].endDate!=null) {
					queryString = queryString + " AND roomID NOT IN ( SELECT roomID FROM "
						+ i_msg.header.nameHotel + "_bookings WHERE " +
						"(endDate >= '" + i_msg.bookings[0].endDate + "' AND startDate <= '" + i_msg.bookings[0].endDate + "') OR " +
						"(endDate > '" + i_msg.bookings[0].startDate + "' AND startDate < '" + i_msg.bookings[0].startDate + "') OR " +
						"(endDate < '" + i_msg.bookings[0].endDate + "' AND startDate > '" + i_msg.bookings[0].startDate + "'))";
				}
			}
			queryString = queryString + " ORDER BY roomNumber ASC";
			System.out.println(queryString);

			// query the database for all rooms
			ResultSet rs = dbcon.select(queryString);
			if (!rs.next()) {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.initializeRooms(0);
				replyMessage.response.responseString = "No Rooms in database.";
			} else {
				int i = 0;
				rs.beforeFirst();
				while (rs.next()) {
					i++;
				}
				rs.beforeFirst();
				replyMessage.initializeRooms(i);

				i = 0;

				while (rs.next()) {
					replyMessage.rooms[i].roomID = rs.getInt("roomID");
					replyMessage.rooms[i].roomNumber = rs.getInt("roomNumber");
					replyMessage.rooms[i].floor = rs.getInt("floor");
					replyMessage.rooms[i].roomType = rs.getString("roomType");
					replyMessage.rooms[i].price = rs.getFloat("price");
					replyMessage.rooms[i].onsuite = rs.getBoolean("onsuite");
					replyMessage.rooms[i].tv = rs.getBoolean("tv");
					replyMessage.rooms[i].disabilityAccess = rs.getBoolean("disabilityAccess");
					replyMessage.rooms[i].elevator = rs.getBoolean("elevator");
					replyMessage.rooms[i].available = rs.getBoolean("available");
					replyMessage.rooms[i].phone = rs.getBoolean("phone");
					replyMessage.rooms[i].internet = rs.getBoolean("internet");
					replyMessage.rooms[i].kitchen = rs.getBoolean("kitchen");
					replyMessage.rooms[i].cleaned = rs.getBoolean("cleaned");
					replyMessage.rooms[i].singleBeds = rs.getInt("singleBeds");
					replyMessage.rooms[i].queenBeds = rs.getInt("queenBeds");
					replyMessage.rooms[i].kingBeds = rs.getInt("kingBeds");
					i++;
				}
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Query succeeded.";
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
