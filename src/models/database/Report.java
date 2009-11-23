package models.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import models.messages.*;

public class Report {
	public Message statusReport(Message i_msg){
		int 	roomCount= 0;
		int 	availRoomCount= 0;
		int 	occupiedRoomsCount= 0;
		int 	availUnoccuRoomsCount= 0;
		String 	htmlString = null;
		int[][]	tempOccRoom;
		int[][]	tempUnoccRoom;
		
		String availRoomCountQuery = 
			"SELECT COUNT(*) AS availRoomCount " +
			"FROM " + i_msg.header.nameHotel + "_rooms " +
			"WHERE available=1";
		String occupiedRoomsCountQuery = 
			"SELECT COUNT(R.roomID) AS occupiedRoomsCount " +
			"FROM " + i_msg.header.nameHotel + "_rooms R, " + i_msg.header.nameHotel + "_bookings B " +
			"WHERE R.roomID = B.roomID " +
			"AND B.status =1 " +
			"AND B.startDate < NOW( ) " +
			"AND B.endDate > NOW( ) " +
			"AND R.available =1 ";
		String availUnoccuRoomsCountQuery = 
			"SELECT COUNT(*) AS availUnoccuRoomsCount " +
			"FROM " + i_msg.header.nameHotel + "_rooms R2 " +
			"WHERE R2.available = 1 AND " +
			"R2.roomID NOT IN (SELECT R.roomID " +
			"FROM " + i_msg.header.nameHotel + "_rooms R, " + i_msg.header.nameHotel + "_bookings B " +
			"WHERE R.roomID = B.roomID AND " +
			"B.status=1 AND " +
			"B.startDate < NOW() AND " +
			"B.endDate > NOW() AND " +
			"R.available=1)";
		
		databaseHelper dbcon = null;
		Message replyMessage= new Message(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		replyMessage.reports=i_msg.reports;
		
		try {
			// create connection
			dbcon = new databaseHelper(i_msg.header.nameHotel);
			replyMessage.initializeReports(1);
			replyMessage.reports[0] = new ReportMessage();
			String queryString = availRoomCountQuery;
			// query the database for all rooms
			ResultSet rs = dbcon.select(queryString);
			if (rs.next()) {
				rs.beforeFirst();
				while (rs.next()) {
					replyMessage.reports[0].availRoomCount = rs.getInt("availRoomCount");
				} 
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Query succeeded.";
			} else {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
				replyMessage.response.responseString = "Query failed in one of the queries.";
			}
			
			queryString = occupiedRoomsCountQuery;
			rs = dbcon.select(queryString);
			if (rs.next()) {
				rs.beforeFirst();
				while (rs.next()) {
					replyMessage.reports[0].occupiedRoomsCount = rs.getInt("occupiedRoomsCount");
				} 
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Query succeeded.";
			} else {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
				replyMessage.response.responseString = "Query failed in one of the queries.";
			}
			
			queryString = availUnoccuRoomsCountQuery;
			rs = dbcon.select(queryString);
			if (rs.next()) {
				rs.beforeFirst();
				while (rs.next()) {
					replyMessage.reports[0].availUnoccuRoomsCount = rs.getInt("availUnoccuRoomsCount");
				} 
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Query succeeded.";
			} else {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
				replyMessage.response.responseString = "Query failed in one of the queries.";
			}
			
			String occupiedRoomsQuery = 
				"SELECT R.roomID, R.roomNumber, R.floor " +
				"FROM " + i_msg.header.nameHotel + "_rooms R, " + i_msg.header.nameHotel + "_bookings B " +
				"WHERE R.roomID = B.roomID " +
				"AND B.status =1 " +
				"AND B.startDate < NOW( ) " +
				"AND B.endDate > NOW( ) " +
				"AND R.available =1 " +
				"ORDER BY R.floor";
			String availUnoccuRoomsQuery = 
				"SELECT R2.roomID, R2.roomNumber, R2.floor " +
				"FROM " + i_msg.header.nameHotel + "_rooms R2 " +
				"WHERE R2.available = 1 AND " +
				"R2.roomID NOT IN (SELECT R.roomID " +
				"FROM " + i_msg.header.nameHotel + "_rooms R, " + i_msg.header.nameHotel + "_bookings B " +
				"WHERE R.roomID = B.roomID AND " +
				"B.status=1 AND " +
				"B.startDate < NOW() AND " +
				"B.endDate > NOW() AND " +
				"R.available=1) " +
				"ORDER BY R2.floor";
			queryString = availUnoccuRoomsQuery;
			rs = dbcon.select(queryString);
			int i;
			i = 0;
			rs.beforeFirst();
			while (rs.next()) {
				i++;
			}
			rs.beforeFirst();
			replyMessage.initializeRooms(i);
			tempUnoccRoom = new int[i][3];
			i = 0;
			while (rs.next()) {
				tempUnoccRoom[i][0] = rs.getInt("roomID");
				tempUnoccRoom[i][1] = rs.getInt("roomNumber");
				tempUnoccRoom[i][2] = rs.getInt("floor");
				i++;
			}
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
			replyMessage.response.responseString = "Query succeeded.";
			
			
			int j;
			//tempOccRoom
			queryString = occupiedRoomsQuery;
			rs = dbcon.select(queryString);
			
			j = 0;
			rs.beforeFirst();
			while (rs.next()) {
				j++;
			}
			rs.beforeFirst();
			tempOccRoom = new int[j][3];
			j = 0;
			while (rs.next()) {
				tempOccRoom[j][0] = rs.getInt("roomID");
				tempOccRoom[j][1] = rs.getInt("roomNumber");
				tempOccRoom[j][2] = rs.getInt("floor");
				j++;
			}
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
			replyMessage.response.responseString = "Query succeeded.";
			
			
			replyMessage.reports[0].initializeRoomARray(i+j);
			int max = i + j;
			i = 0;
			j = 0;
			for (int m = 0; m < max; m++) {
				
				if (i != tempUnoccRoom.length && j != tempOccRoom.length && tempUnoccRoom[i][2] < tempOccRoom[j][2] ) {
					replyMessage.reports[0].roomArray[m][0] = tempUnoccRoom[i][0];
					replyMessage.reports[0].roomArray[m][1] = tempUnoccRoom[i][1];
					replyMessage.reports[0].roomArray[m][2] = tempUnoccRoom[i][2];
					replyMessage.reports[0].roomArray[m][3] = 0;
					i++;
				} else if (i != tempUnoccRoom.length && j != tempOccRoom.length && tempUnoccRoom[i][2] ==  tempOccRoom[j][2]) {
					if (tempUnoccRoom[i][1] < tempOccRoom[j][1]) {
						replyMessage.reports[0].roomArray[m][0] = tempUnoccRoom[i][0];
						replyMessage.reports[0].roomArray[m][1] = tempUnoccRoom[i][1];
						replyMessage.reports[0].roomArray[m][2] = tempUnoccRoom[i][2];
						replyMessage.reports[0].roomArray[m][3] = 0;
						i++;
					} else {
						replyMessage.reports[0].roomArray[m][0] = tempOccRoom[j][0];
						replyMessage.reports[0].roomArray[m][1] = tempOccRoom[j][1];
						replyMessage.reports[0].roomArray[m][2] = tempOccRoom[j][2];
						replyMessage.reports[0].roomArray[m][3] = 1;
						j++;
					}
				} else if (j != tempOccRoom.length) {
					replyMessage.reports[0].roomArray[m][0] = tempOccRoom[j][0];
					replyMessage.reports[0].roomArray[m][1] = tempOccRoom[j][1];
					replyMessage.reports[0].roomArray[m][2] = tempOccRoom[j][2];
					replyMessage.reports[0].roomArray[m][3] = 1;
					j++;
				} else {
					replyMessage.reports[0].roomArray[m][0] = tempUnoccRoom[i][0];
					replyMessage.reports[0].roomArray[m][1] = tempUnoccRoom[i][1];
					replyMessage.reports[0].roomArray[m][2] = tempUnoccRoom[i][2];
					replyMessage.reports[0].roomArray[m][3] = 0;
					i++;
				}
			}

		} catch (SQLException e) {
			System.err.println("Error in 'generateStatusReport'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.responseCode = ResponseMessage.ResponseCode.FAIL;
			replyMessage.response.responseString = "Query failed.";
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'generateStatusReport'.  ClassNotFoundException was thrown:");
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
