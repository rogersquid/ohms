package models.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import models.messages.*;
import models.messages.ResponseMessage.ResponseCode;

public class Report {
	
	/*
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
			dbcon = new databaseHelper();
			replyMessage.initializeReports(1);
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
	*/
	
	
	/* Rooms that have been reserved but not checked in yet
	SELECT *
	FROM test_bookings
	WHERE (startDate < NOW() AND endDate > NOW())
	AND status=0
	*/
	
	/* Income for today from all rooms checking out today
	SELECT R.roomNumber, R.price AS roomPrice, Bi.paymentType, Bi.status AS paidStatus, Bk.status AS checkedInStatus, E.extraName, E.price AS extraPrice
	FROM test_bookings Bk, test_bills Bi, test_extras E, test_rooms R
	WHERE (Bk.endDate = '2009-11-26')
	AND Bk.status = 1
	AND Bk.bookingID = Bi.bookingID
	AND Bk.bookingID = E.bookingID
	AND Bk.roomID = R.roomID
	ORDER BY R.roomID
	 */
	
	/* Income for today from all rooms checking in today
	SELECT R.roomNumber, R.price AS roomPrice, Bi.paymentType, Bi.status AS paidStatus, Bk.status AS checkedInStatus, E.extraName, E.price AS extraPrice
	FROM test_bookings Bk, test_bills Bi, test_extras E, test_rooms R
	WHERE (Bk.endDate >= '2009-11-26' AND Bk.startDate <= '2009-11-26' )
	AND Bk.status = 1
	AND Bk.bookingID = Bi.bookingID
	AND Bk.bookingID = E.bookingID
	AND Bk.roomID = R.roomID
	ORDER BY R.roomID
	 */
	
	/* Find all rooms that did not order extra
	SELECT *
	FROM test_bookings Book
	WHERE bookingID NOT IN
	(SELECT Bk.bookingID AS orderedExtras
	FROM test_bookings Bk, test_extras E
	WHERE (Bk.endDate >= '2009-11-26' AND Bk.startDate <= '2009-11-26' )
	AND Bk.bookingID = E.bookingID
	AND Bk.status = 1
	GROUP BY Bk.bookingID)
	AND status = 1
	*/
	
	/* How many guests used what type of payment (previous guests too??)
	SELECT Bi.paymentType, COUNT(Bk.bookingID) AS count
	FROM test_bookings Bk, test_bills Bi
	WHERE (Bk.endDate >= '2009-11-26' AND Bk.startDate <= '2009-11-26' )
	AND Bk.bookingID = Bi.bookingID
	AND Bk.status = 1
	GROUP BY Bi.paymentType
	*/
	
	public ReportMessage generateCleannessReport(ReportMessage i_msg){
		// stats[0] - Uncleanness percentage
		// stats[1] - Cleanness percentage
		// tables.rooms[0] - call getFilteredRooms() to get uncleaned rooms
		String cleannessPercentageQuery = 
			"SELECT COUNT(*) AS cleannessCount " +
			"FROM " + i_msg.header.nameHotel + "_rooms " +
			"GROUP BY cleaned";
		String uncleanRoomQuery = 
			"SELECT * " +
			"FROM "+ i_msg.header.nameHotel + "_rooms " +
			"WHERE cleaned =0";
		
		databaseHelper dbcon = null;
		ReportMessage replyMessage = new ReportMessage(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		
		try {
			// create connection
			dbcon = new databaseHelper();
			String queryString = cleannessPercentageQuery;
			// query the database for all rooms
			ResultSet rs = dbcon.select(queryString);
			if (rs.next()) {
				rs.beforeFirst();
				rs.next();
				float numUncleaned = (float)rs.getInt("cleannessCount");
				rs.next();
				float numCleaned = (float)rs.getInt("cleannessCount");
				float maxRoom = numUncleaned + numCleaned;
				replyMessage.initializeStats(2);
				replyMessage.stats[0] = numUncleaned / maxRoom;
				replyMessage.stats[1] = numCleaned / maxRoom;
				
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Query succeeded.";
			} else {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Empty Results from cleannessPercentageQuery.";
				replyMessage.initializeStats(0);
			}

			if (replyMessage.response.responseCode == ResponseMessage.ResponseCode.SUCCESS) {
				ResultSet rs2 = dbcon.select(uncleanRoomQuery);
				replyMessage.initializeTables(1);
				if (rs2.next()) {
					rs2.last();
					int numRow2 = rs2.getRow();
					rs2.beforeFirst();
					replyMessage.tables[0].initializeRooms(numRow2);
					int j = 0;
					while (rs2.next()) {
						replyMessage.tables[0].rooms[j].roomID = 		rs2.getInt("roomID");
						replyMessage.tables[0].rooms[j].roomNumber = 	rs2.getInt("roomNumber");
						replyMessage.tables[0].rooms[j].floor = 		rs2.getInt("floor");
						replyMessage.tables[0].rooms[j].roomType = 		rs2.getString("roomType");
						replyMessage.tables[0].rooms[j].price = 		rs2.getFloat("price");
						replyMessage.tables[0].rooms[j].onsuite = 		rs2.getBoolean("onsuite");
						replyMessage.tables[0].rooms[j].tv = 			rs2.getBoolean("tv");
						replyMessage.tables[0].rooms[j].disabilityAccess = rs2.getBoolean("disabilityAccess");
						replyMessage.tables[0].rooms[j].elevator = 		rs2.getBoolean("elevator");
						replyMessage.tables[0].rooms[j].available = 	rs2.getBoolean("available");
						replyMessage.tables[0].rooms[j].phone = 		rs2.getBoolean("phone");
						replyMessage.tables[0].rooms[j].internet = 		rs2.getBoolean("internet");
						replyMessage.tables[0].rooms[j].kitchen = 		rs2.getBoolean("kitchen");
						replyMessage.tables[0].rooms[j].cleaned = 		rs2.getBoolean("cleaned");
						replyMessage.tables[0].rooms[j].singleBeds = 	rs2.getInt("singleBeds");
						replyMessage.tables[0].rooms[j].queenBeds = 	rs2.getInt("queenBeds");
						replyMessage.tables[0].rooms[j].kingBeds = 		rs2.getInt("kingBeds");
						j++;
					}
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Query succeeded.";
				} else {
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Empty Results from uncleanRoomQuery.";
					replyMessage.tables[0].initializeRooms(0);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error in 'generateCleannessReport'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Report generation failed.");
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'generateCleannessReport'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Report generation failed.");
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return replyMessage;
	}
	
	public ReportMessage generateExtraReport(ReportMessage i_msg){
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date tmr = calendar.getTime();
		DateFormat df= new SimpleDateFormat("dd/MM/yyyy");
		String numExtraPerBookingQuery = null; 
		String todayExtraQuery = null;
		String tmrExtraQuery = null;
		String earningPerExtraTypeTodayQuery = null; 
		String earningPerDateQuery = null;
		String mostEarningExtraTodayQuery = null;
		try {
			java.sql.Date currentDate = new java.sql.Date(df.parse(String.valueOf(now.getDate()) + "/" + 
					String.valueOf(now.getMonth() + 1) + "/" + String.valueOf(now.getYear() + 1900)).getTime());
			java.sql.Date tmrDate = new java.sql.Date(df.parse(String.valueOf(tmr.getDate()) + "/" + 
					String.valueOf(tmr.getMonth() + 1) + "/" + String.valueOf(tmr.getYear() + 1900)).getTime());

			numExtraPerBookingQuery = 
				"SELECT bookingID, COUNT(*) AS numExtra " +
				"FROM " + i_msg.header.nameHotel + "_extras " +
				"GROUP BY bookingID";
			todayExtraQuery = 
				"SELECT * " +
				"FROM " + i_msg.header.nameHotel + "_extras " +
				"WHERE date = '" + currentDate + "'";
			tmrExtraQuery = 
				"SELECT * " +
				"FROM " + i_msg.header.nameHotel + "_extras " +
				"WHERE date = '" + tmrDate + "'";
			earningPerExtraTypeTodayQuery = 
				"SELECT extraName, ROUND(SUM(price), 2) AS sumPrice " + 
				"FROM " + i_msg.header.nameHotel + "_extras " +
				"WHERE date = '" + currentDate + "' " +
				"GROUP BY extraName";
			earningPerDateQuery = 
				"SELECT date, ROUND(SUM(price), 2) AS sumPrice " +
				"FROM " + i_msg.header.nameHotel + "_extras " +
				"GROUP BY date";
			mostEarningExtraTodayQuery = 
				"SELECT extraName, price " +
				"FROM " + i_msg.header.nameHotel + "_extras " +
				"WHERE price= (" +
					"SELECT MAX(price) AS maxPrice " +
					"FROM " + i_msg.header.nameHotel + "_extras " +
					"WHERE date = '" + currentDate + "') " +
					"AND date = '" + currentDate + "'";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
		
		databaseHelper dbcon = null;
		ReportMessage replyMessage = new ReportMessage(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);

		try {
			// create connection
			dbcon = new databaseHelper();
			replyMessage.initializeTables(6);
			int numRow;
			int i;
			
			ResultSet rs = dbcon.select(numExtraPerBookingQuery);
			if (rs.next()) {
				rs.last();
				numRow = rs.getRow();
				rs.beforeFirst();
				replyMessage.tables[0].initializeExtras(numRow);
				replyMessage.initializeStats(numRow);
				i = 0;
				while (rs.next()) {
					replyMessage.tables[0].extras[i].bookingID = rs.getInt("bookingID");
					replyMessage.stats[i] = (float)rs.getInt("numExtra");
					i++;
				}
				
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Query succeeded.";
			} else {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Empty Results from numExtraPerBookingQuery.";
				replyMessage.tables[0].initializeExtras(0);
				replyMessage.initializeStats(0);
			}
			
			if (replyMessage.response.responseCode == ResponseMessage.ResponseCode.SUCCESS) {
				rs = dbcon.select(todayExtraQuery);
				if (rs.next()) {
					rs.last();
					numRow = rs.getRow();
					rs.beforeFirst();
					replyMessage.tables[1].initializeExtras(numRow);
					i = 0;
					while (rs.next()) {
						replyMessage.tables[1].extras[i].extraID = rs.getInt("extraID");
						replyMessage.tables[1].extras[i].bookingID = rs.getInt("bookingID");
						replyMessage.tables[1].extras[i].extraName = rs.getString("extraName");
						replyMessage.tables[1].extras[i].price = rs.getFloat("price");
						replyMessage.tables[1].extras[i].date = rs.getDate("date");
						replyMessage.tables[1].extras[i].creationTime = rs.getTimestamp("creationTime");
						i++;
					}
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Query succeeded.";
				} else {
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Empty Results from todayExtraQuery.";
					replyMessage.tables[1].initializeExtras(0);
				}
			}
			
			if (replyMessage.response.responseCode == ResponseMessage.ResponseCode.SUCCESS) {
				rs = dbcon.select(tmrExtraQuery);
				if (rs.next()) {
					rs.last();
					numRow = rs.getRow();
					rs.beforeFirst();
					replyMessage.tables[2].initializeExtras(numRow);
					i = 0;
					while (rs.next()) {
						replyMessage.tables[2].extras[i].extraID = rs.getInt("extraID");
						replyMessage.tables[2].extras[i].bookingID = rs.getInt("bookingID");
						replyMessage.tables[2].extras[i].extraName = rs.getString("extraName");
						replyMessage.tables[2].extras[i].price = rs.getFloat("price");
						replyMessage.tables[2].extras[i].date = rs.getDate("date");
						replyMessage.tables[2].extras[i].creationTime = rs.getTimestamp("creationTime");
						i++;
					}
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Query succeeded.";
				} else {
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Empty Results from tmrExtraQuery.";
					replyMessage.tables[2].initializeExtras(0);
				}
			}
			
			if (replyMessage.response.responseCode == ResponseMessage.ResponseCode.SUCCESS) {
				rs = dbcon.select(earningPerExtraTypeTodayQuery);
				if (rs.next()) {
					rs.last();
					numRow = rs.getRow();
					rs.beforeFirst();
					replyMessage.tables[3].initializeExtras(numRow);
					i = 0;
					while (rs.next()) {
						replyMessage.tables[3].extras[i].extraName = rs.getString("extraName");
						replyMessage.tables[3].extras[i].price = rs.getFloat("sumPrice");
						i++;
					}
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Query succeeded.";
				} else {
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Empty Results from earningPerExtraTypeTodayQuery.";
					replyMessage.tables[3].initializeExtras(0);
				}
			}
			
			if (replyMessage.response.responseCode == ResponseMessage.ResponseCode.SUCCESS) {
				rs = dbcon.select(earningPerDateQuery);
				if (rs.next()) {
					rs.last();
					numRow = rs.getRow();
					rs.beforeFirst();
					replyMessage.tables[4].initializeExtras(numRow);
					i = 0;
					while (rs.next()) {
						replyMessage.tables[4].extras[i].price = rs.getFloat("sumPrice");
						replyMessage.tables[4].extras[i].date = rs.getDate("date");
						i++;
					}
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Query succeeded.";
				} else {
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Empty Results from earningPerDateQuery.";
					replyMessage.tables[4].initializeExtras(0);
				}
			}
			
			if (replyMessage.response.responseCode == ResponseMessage.ResponseCode.SUCCESS) {
				rs = dbcon.select(mostEarningExtraTodayQuery);
				if (rs.next()) {
					rs.last();
					numRow = rs.getRow();
					rs.beforeFirst();
					replyMessage.tables[5].initializeExtras(numRow);
					i = 0;
					while (rs.next()) {
						replyMessage.tables[5].extras[i].extraName = rs.getString("extraName");
						replyMessage.tables[5].extras[i].price = rs.getFloat("price");
						i++;
					}
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Query succeeded.";
				} else {
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Empty Results from tmrExtraQuery.";
					replyMessage.tables[5].initializeExtras(0);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error in 'generateExtraReport'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Report generation failed.");
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'generateExtraReport'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Report generation failed.");
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return replyMessage;
	}
	
	
	
	public ReportMessage generateRoomStatusReport(ReportMessage i_msg){
		String curAvailRoomsQuery = 
			"SELECT * " +
			"FROM " + i_msg.header.nameHotel + "_rooms " +
			"WHERE available = 1"; 
		String unavailRoomsQuery = 
			"SELECT * " +
			"FROM " + i_msg.header.nameHotel + "_rooms " +
			"WHERE available = 0";
		String curOccAvailRoomsQuery = 
			"SELECT *, R.roomID AS room2ID, B.roomID AS bookRoomID " +
			"FROM " + i_msg.header.nameHotel + "_rooms R, " + i_msg.header.nameHotel + "_bookings B	" +
			"WHERE R.available = 1 " +
			"AND B.startDate < NOW() AND B.endDate > NOW() " +
			"AND R.roomID = B.roomID " +
			"AND B.status = 1";
		String curUnoccAvailRoomsQuery = 
			"SELECT * " +
			"FROM " + i_msg.header.nameHotel + "_rooms R2 " + 
			"WHERE R2.available = 1 AND " + 
			"R2.roomID NOT IN ( " +
				"SELECT R.roomID " +
				"FROM " + i_msg.header.nameHotel + "_rooms R, " + i_msg.header.nameHotel + "_bookings B " + 
				"WHERE R.roomID = B.roomID AND " + 
				"B.status=1 AND " + 
				"B.startDate < NOW() AND " + 
				"B.endDate > NOW() AND " + 
				"R.available=1)";

		databaseHelper dbcon = null;
		ReportMessage replyMessage = new ReportMessage(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		try {
			// create connection
			dbcon = new databaseHelper();
			replyMessage.initializeTables(4);
			int numRow;
			int i;
			
			ResultSet rs = dbcon.select(curAvailRoomsQuery);
			if (rs.next()) {
				rs.last();
				numRow = rs.getRow();
				rs.beforeFirst();
				replyMessage.tables[0].initializeRooms(numRow);
				i = 0;
				while (rs.next()) {
					replyMessage.tables[0].rooms[i].roomID = rs.getInt("roomID");
					replyMessage.tables[0].rooms[i].roomNumber = rs.getInt("roomNumber");
					replyMessage.tables[0].rooms[i].floor = rs.getInt("floor");
					replyMessage.tables[0].rooms[i].roomType = rs.getString("roomType");
					replyMessage.tables[0].rooms[i].price = rs.getFloat("price");
					replyMessage.tables[0].rooms[i].onsuite = rs.getBoolean("onsuite");
					replyMessage.tables[0].rooms[i].tv = rs.getBoolean("tv");
					replyMessage.tables[0].rooms[i].disabilityAccess = rs.getBoolean("disabilityAccess");
					replyMessage.tables[0].rooms[i].elevator = rs.getBoolean("elevator");
					replyMessage.tables[0].rooms[i].available = rs.getBoolean("available");
					replyMessage.tables[0].rooms[i].phone = rs.getBoolean("phone");
					replyMessage.tables[0].rooms[i].internet = rs.getBoolean("internet");
					replyMessage.tables[0].rooms[i].kitchen = rs.getBoolean("kitchen");
					replyMessage.tables[0].rooms[i].cleaned = rs.getBoolean("cleaned");
					replyMessage.tables[0].rooms[i].singleBeds = rs.getInt("singleBeds");
					replyMessage.tables[0].rooms[i].queenBeds = rs.getInt("queenBeds");
					replyMessage.tables[0].rooms[i].kingBeds = rs.getInt("kingBeds");
					i++;
				}
				
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Query succeeded.";
			} else {
				replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
				replyMessage.response.responseString = "Empty Results.";
				replyMessage.tables[0].initializeRooms(0);
			}
			
			if (replyMessage.response.responseCode == ResponseMessage.ResponseCode.SUCCESS) {
				rs = dbcon.select(unavailRoomsQuery);
				if (rs.next()) {
					rs.last();
					numRow = rs.getRow();
					rs.beforeFirst();
					replyMessage.tables[1].initializeRooms(numRow);
					i = 0;
					while (rs.next()) {
						replyMessage.tables[1].rooms[i].roomID = rs.getInt("roomID");
						replyMessage.tables[1].rooms[i].roomNumber = rs.getInt("roomNumber");
						replyMessage.tables[1].rooms[i].floor = rs.getInt("floor");
						replyMessage.tables[1].rooms[i].roomType = rs.getString("roomType");
						replyMessage.tables[1].rooms[i].price = rs.getFloat("price");
						replyMessage.tables[1].rooms[i].onsuite = rs.getBoolean("onsuite");
						replyMessage.tables[1].rooms[i].tv = rs.getBoolean("tv");
						replyMessage.tables[1].rooms[i].disabilityAccess = rs.getBoolean("disabilityAccess");
						replyMessage.tables[1].rooms[i].elevator = rs.getBoolean("elevator");
						replyMessage.tables[1].rooms[i].available = rs.getBoolean("available");
						replyMessage.tables[1].rooms[i].phone = rs.getBoolean("phone");
						replyMessage.tables[1].rooms[i].internet = rs.getBoolean("internet");
						replyMessage.tables[1].rooms[i].kitchen = rs.getBoolean("kitchen");
						replyMessage.tables[1].rooms[i].cleaned = rs.getBoolean("cleaned");
						replyMessage.tables[1].rooms[i].singleBeds = rs.getInt("singleBeds");
						replyMessage.tables[1].rooms[i].queenBeds = rs.getInt("queenBeds");
						replyMessage.tables[1].rooms[i].kingBeds = rs.getInt("kingBeds");
						i++;
					}
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Query succeeded.";
				} else {
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Empty Results.";
					replyMessage.tables[1].initializeRooms(0);
				}
			}
			
			if (replyMessage.response.responseCode == ResponseMessage.ResponseCode.SUCCESS) {
				rs = dbcon.select(curOccAvailRoomsQuery);
				if (rs.next()) {
					rs.last();
					numRow = rs.getRow();
					rs.beforeFirst();
					replyMessage.tables[2].initializeRooms(numRow);
					replyMessage.tables[2].initializeBookings(numRow);
					i = 0;
					while (rs.next()) {
						replyMessage.tables[2].rooms[i].roomID = rs.getInt("room2ID");
						replyMessage.tables[2].rooms[i].roomNumber = rs.getInt("roomNumber");
						replyMessage.tables[2].rooms[i].floor = rs.getInt("floor");
						replyMessage.tables[2].rooms[i].roomType = rs.getString("roomType");
						replyMessage.tables[2].rooms[i].price = rs.getFloat("price");
						replyMessage.tables[2].rooms[i].onsuite = rs.getBoolean("onsuite");
						replyMessage.tables[2].rooms[i].tv = rs.getBoolean("tv");
						replyMessage.tables[2].rooms[i].disabilityAccess = rs.getBoolean("disabilityAccess");
						replyMessage.tables[2].rooms[i].elevator = rs.getBoolean("elevator");
						replyMessage.tables[2].rooms[i].available = rs.getBoolean("available");
						replyMessage.tables[2].rooms[i].phone = rs.getBoolean("phone");
						replyMessage.tables[2].rooms[i].internet = rs.getBoolean("internet");
						replyMessage.tables[2].rooms[i].kitchen = rs.getBoolean("kitchen");
						replyMessage.tables[2].rooms[i].cleaned = rs.getBoolean("cleaned");
						replyMessage.tables[2].rooms[i].singleBeds = rs.getInt("singleBeds");
						replyMessage.tables[2].rooms[i].queenBeds = rs.getInt("queenBeds");
						replyMessage.tables[2].rooms[i].kingBeds = rs.getInt("kingBeds");
						
						replyMessage.tables[2].bookings[i].bookingID=rs.getInt("bookingID");
						replyMessage.tables[2].bookings[i].ownerID= rs.getInt("bookingOwnerID");
						replyMessage.tables[2].bookings[i].creationDate= new java.sql.Timestamp(rs.getDate("creationTime").getTime());
						replyMessage.tables[2].bookings[i].startDate= rs.getDate("startDate");
						replyMessage.tables[2].bookings[i].endDate= rs.getDate("endDate");
						replyMessage.tables[2].bookings[i].roomID= rs.getInt("bookRoomID");
						replyMessage.tables[2].bookings[i].status = rs.getInt("status");
						i++;
					}
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Query succeeded.";
				} else {
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Empty Results.";
					replyMessage.tables[2].initializeRooms(0);
				}
			}
			
			if (replyMessage.response.responseCode == ResponseMessage.ResponseCode.SUCCESS) {
				rs = dbcon.select(curUnoccAvailRoomsQuery);
				if (rs.next()) {
					rs.last();
					numRow = rs.getRow();
					rs.beforeFirst();
					replyMessage.tables[3].initializeRooms(numRow);
					i = 0;
					while (rs.next()) {
						replyMessage.tables[3].rooms[i].roomID = rs.getInt("roomID");
						replyMessage.tables[3].rooms[i].roomNumber = rs.getInt("roomNumber");
						replyMessage.tables[3].rooms[i].floor = rs.getInt("floor");
						replyMessage.tables[3].rooms[i].roomType = rs.getString("roomType");
						replyMessage.tables[3].rooms[i].price = rs.getFloat("price");
						replyMessage.tables[3].rooms[i].onsuite = rs.getBoolean("onsuite");
						replyMessage.tables[3].rooms[i].tv = rs.getBoolean("tv");
						replyMessage.tables[3].rooms[i].disabilityAccess = rs.getBoolean("disabilityAccess");
						replyMessage.tables[3].rooms[i].elevator = rs.getBoolean("elevator");
						replyMessage.tables[3].rooms[i].available = rs.getBoolean("available");
						replyMessage.tables[3].rooms[i].phone = rs.getBoolean("phone");
						replyMessage.tables[3].rooms[i].internet = rs.getBoolean("internet");
						replyMessage.tables[3].rooms[i].kitchen = rs.getBoolean("kitchen");
						replyMessage.tables[3].rooms[i].cleaned = rs.getBoolean("cleaned");
						replyMessage.tables[3].rooms[i].singleBeds = rs.getInt("singleBeds");
						replyMessage.tables[3].rooms[i].queenBeds = rs.getInt("queenBeds");
						replyMessage.tables[3].rooms[i].kingBeds = rs.getInt("kingBeds");
						i++;
					}
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Query succeeded.";
				} else {
					replyMessage.response.responseCode = ResponseMessage.ResponseCode.SUCCESS;
					replyMessage.response.responseString = "Empty Results.";
					replyMessage.tables[3].initializeRooms(0);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error in 'generateRoomStatusReport'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Report generation failed.");
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'generateRoomStatusReport'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Report generation failed.");
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return replyMessage;
	}
	
	
	public ReportMessage generateExpenseReport(ReportMessage i_msg){
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		DateFormat df= new SimpleDateFormat("dd/MM/yyyy");
		String todayIncomePerRoom = null; 
		String todayExtraQuery = null;
		String tmrExtraQuery = null;
		String earningPerExtraTypeTodayQuery = null; 
		try {
			java.sql.Date currentDate = new java.sql.Date(df.parse(String.valueOf(now.getDate()) + "/" + 
					String.valueOf(now.getMonth()) + "/" + String.valueOf(now.getYear() + 1900)).getTime());

			todayIncomePerRoom = 
				"SELECT R.roomID, SUM(E.price) AS sumExtra, R.price AS roomPrice " +
				"FROM " + i_msg.header.nameHotel + "_bookings Bk, " + i_msg.header.nameHotel + "_bills Bi, " 
					+ i_msg.header.nameHotel + "_extras E, " + i_msg.header.nameHotel + "_rooms R " +
				"WHERE (Bk.endDate = '" + currentDate + "') " +
				"AND Bk.status = 2 " +
				"AND Bk.bookingID = Bi.bookingID " +
				"AND Bk.bookingID = E.bookingID " +
				"AND Bk.roomID = R.roomID " +
				"GROUP BY R.roomID";
			String todayTotalIncome = 
				"SELECT R.roomID, SUM(E.price) AS sumExtra, R.price AS roomPrice " +
				"FROM " + i_msg.header.nameHotel + "_bookings Bk, " + i_msg.header.nameHotel + "_bills Bi, " 
					+ i_msg.header.nameHotel + "_extras E, " + i_msg.header.nameHotel + "_rooms R " +
				"WHERE (Bk.endDate = '" + currentDate + "') " +
				"AND Bk.status = 2 " +
				"AND Bk.bookingID = Bi.bookingID " +
				"AND Bk.bookingID = E.bookingID " +
				"AND Bk.roomID = R.roomID ";
			String curOccAvailRoomsQuery = 
				"SELECT *, R.roomID AS room2ID, B.roomID AS bookRoomID " +
				"FROM " + i_msg.header.nameHotel + "_rooms R, " + i_msg.header.nameHotel + "_bookings B	" +
				"WHERE R.available = 1 " +
				"AND B.startDate < NOW() AND B.endDate > NOW() " +
				"AND R.roomID = B.roomID " +
				"AND B.status = 1";
			String numPaymentTypeQuery = 
				"SELECT Bi.paymentType, COUNT(Bk.bookingID) AS count " +
				"FROM " + i_msg.header.nameHotel + "_bookings Bk, " + i_msg.header.nameHotel + "_bills Bi " +
				"WHERE (Bk.endDate >= '" + currentDate + "' AND Bk.startDate <= '" + currentDate + "' ) " +
				"AND Bk.bookingID = Bi.bookingID " +
				"AND Bk.status = 2 " +
				"GROUP BY Bi.paymentType";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
		


		databaseHelper dbcon = null;
		ReportMessage replyMessage = new ReportMessage(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		try {
			// create connection
			dbcon = new databaseHelper();
			replyMessage.initializeTables(4);
			int numRow;
			int i;
			

		} catch (SQLException e) {
			System.err.println("Error in 'generateRoomStatusReport'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Report generation failed.");
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'generateRoomStatusReport'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Report generation failed.");
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return replyMessage;
	}
	
	
	
	
	
	public ReportMessage generateStatisticalRoomAnalysisReport(ReportMessage i_msg){
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		DateFormat df= new SimpleDateFormat("dd/MM/yyyy");
		String todayIncomePerRoom = null; 
		String todayExtraQuery = null;
		String tmrExtraQuery = null;
		String earningPerExtraTypeTodayQuery = null; 
		try {
			java.sql.Date currentDate = new java.sql.Date(df.parse(String.valueOf(now.getDate()) + "/" + 
					String.valueOf(now.getMonth()) + "/" + String.valueOf(now.getYear() + 1900)).getTime());

			todayIncomePerRoom = 
				"SELECT R.roomID, SUM(E.price) AS sumExtra, R.price AS roomPrice " +
				"FROM " + i_msg.header.nameHotel + "_bookings Bk, " + i_msg.header.nameHotel + "_bills Bi, " 
					+ i_msg.header.nameHotel + "_extras E, " + i_msg.header.nameHotel + "_rooms R " +
				"WHERE (Bk.endDate = '" + currentDate + "') " +
				"AND Bk.status = 2 " +
				"AND Bk.bookingID = Bi.bookingID " +
				"AND Bk.bookingID = E.bookingID " +
				"AND Bk.roomID = R.roomID " +
				"GROUP BY R.roomID";
			String todayTotalIncome = 
				"SELECT R.roomID, SUM(E.price) AS sumExtra, R.price AS roomPrice " +
				"FROM " + i_msg.header.nameHotel + "_bookings Bk, " + i_msg.header.nameHotel + "_bills Bi, " 
					+ i_msg.header.nameHotel + "_extras E, " + i_msg.header.nameHotel + "_rooms R " +
				"WHERE (Bk.endDate = '" + currentDate + "') " +
				"AND Bk.status = 2 " +
				"AND Bk.bookingID = Bi.bookingID " +
				"AND Bk.bookingID = E.bookingID " +
				"AND Bk.roomID = R.roomID ";
			String curOccAvailRoomsQuery = 
				"SELECT *, R.roomID AS room2ID, B.roomID AS bookRoomID " +
				"FROM " + i_msg.header.nameHotel + "_rooms R, " + i_msg.header.nameHotel + "_bookings B	" +
				"WHERE R.available = 1 " +
				"AND B.startDate < NOW() AND B.endDate > NOW() " +
				"AND R.roomID = B.roomID " +
				"AND B.status = 1";
			String numPaymentTypeQuery = 
				"SELECT Bi.paymentType, COUNT(Bk.bookingID) AS count " +
				"FROM " + i_msg.header.nameHotel + "_bookings Bk, " + i_msg.header.nameHotel + "_bills Bi " +
				"WHERE (Bk.endDate >= '" + currentDate + "' AND Bk.startDate <= '" + currentDate + "' ) " +
				"AND Bk.bookingID = Bi.bookingID " +
				"AND Bk.status = 2 " +
				"GROUP BY Bi.paymentType";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
		


		databaseHelper dbcon = null;
		ReportMessage replyMessage = new ReportMessage(i_msg.header.messageOwnerID, i_msg.header.authLevel, i_msg.header.nameHotel);
		try {
			// create connection
			dbcon = new databaseHelper();
			replyMessage.initializeTables(4);
			int numRow;
			int i;
			

		} catch (SQLException e) {
			System.err.println("Error in 'generateRoomStatusReport'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Report generation failed.");
			return replyMessage;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'generateRoomStatusReport'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			replyMessage.response.fillResponse(ResponseCode.FAIL, "Report generation failed.");
			return replyMessage;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return replyMessage;
	}
}
