package testMains;

import models.database.*;
import models.messages.*;

public class ReportTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("\r");
		test_allReport();
	}

	private static void test_allReport() {
		System.out.println("Start Test Rooms \r");
		for (int i=0; i<8; i++){
			test_generateReports(i);
		}
	}
	
	private static void test_generateReports(int i_num){
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success Generate Cleanness Report");
			
			//create message
			ReportMessage h_msg = new ReportMessage(0, 0, "test");
			//fill room info:
			Report report = new Report();
			h_msg = report.generateCleannessReport(h_msg);
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("Passed Test ID 1");
				System.out.println("Input: ");
				System.out.println("Output: # of Rooms: " + h_msg.tables[0].rooms.length);
				System.out.println(" % of uncleaned rooms: " + h_msg.stats[0]);
				System.out.println(" % of cleaned rooms: " + h_msg.stats[1]);
				System.out.println("\r");
				for (int i = 0; i < h_msg.tables[0].rooms.length; i ++) {
					test_printRoom (h_msg.tables[0].rooms[i]);
				}
				
				System.out.println("\r");
			}
			else{
				System.out.println("Failed Test ID 1");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}
		
		if(i_num==1){
			System.out.println("Test ID 2");
			System.out.println("Description: Success Generate Extra Report");
			
			//create message
			ReportMessage h_msg = new ReportMessage(0, 0, "test");
			//fill room info:
			Report report = new Report();
			h_msg = report.generateExtraReport(h_msg);
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("Passed Test ID 2");
				System.out.println("Input: ");
				System.out.println("Output: ");
				
				System.out.println("Number of Extra Per Booking");
				for (int i = 0; i < h_msg.tables[0].extras.length; i ++) {
					System.out.println("BookingID: " + h_msg.tables[0].extras[i].bookingID + " number of Extras: " 
							+ (int)h_msg.stats[i]);
				}
				System.out.println("\r\r\r");
				

				System.out.println("Extras for Today");
				for (int i = 0; i < h_msg.tables[1].extras.length; i ++) {
					test_printExtra(h_msg.tables[1].extras[i]);
					System.out.println("\r");
				}
				System.out.println("\r\r\r");
				
				
				System.out.println("Extras for Tomorrow");
				for (int i = 0; i < h_msg.tables[2].extras.length; i ++) {
					test_printExtra(h_msg.tables[2].extras[i]);
					System.out.println("\r");
				}
				System.out.println("\r\r\r");
				
				
				System.out.println("Earning Per Extra Type Today");
				for (int i = 0; i < h_msg.tables[3].extras.length; i ++) {
					System.out.println("Extra Name: " + h_msg.tables[3].extras[i].extraName);
					System.out.println("Price: " + h_msg.tables[3].extras[i].price);
					System.out.println("\r");
				}
				System.out.println("\r\r\r");

				System.out.println("Earning By Date");
				for (int i = 0; i < h_msg.tables[4].extras.length; i ++) {
					System.out.println("Date: " + h_msg.tables[4].extras[i].date);
					System.out.println("Price: " + h_msg.tables[4].extras[i].price);
					System.out.println("\r");
				}
				System.out.println("\r\r\r");
				
				System.out.println("Most Earning Extra Today");
				for (int i = 0; i < h_msg.tables[5].extras.length; i ++) {
					System.out.println("Date: " + h_msg.tables[5].extras[i].extraName);
					System.out.println("Price: " + h_msg.tables[5].extras[i].price);
					System.out.println("\r");
				}
				System.out.println("\r\r\r");
				System.out.println("\r");
			}
			else{
				System.out.println("Failed Test ID 1");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}
		
		
		
		
		
		
		if(i_num==2){
			System.out.println("Test ID 3");
			System.out.println("Description: Success Generate Room Status Report");
			
			//create message
			ReportMessage h_msg = new ReportMessage(0, 0, "test");
			//fill room info:
			Report report = new Report();
			h_msg = report.generateRoomStatusReport(h_msg);
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("Passed Test ID 3");
				System.out.println("Input: ");
				System.out.println("Output: ");
				
				System.out.println("curently Available Rooms:");
				for (int i = 0; i < h_msg.tables[0].rooms.length; i ++) {
					test_printRoom(h_msg.tables[0].rooms[i]);
				}
				System.out.println("\r\r\r");
				

				System.out.println("Unavailable Rooms:");
				for (int i = 0; i < h_msg.tables[1].rooms.length; i ++) {
					test_printRoom(h_msg.tables[1].rooms[i]);
				}
				System.out.println("\r\r\r");
				
				
				System.out.println("Available Occupied Rooms:");
				for (int i = 0; i < h_msg.tables[2].rooms.length; i ++) {
					test_printRoom(h_msg.tables[2].rooms[i]);
				}
				System.out.println("\r\r\r");
				
				
				System.out.println("Available Unoccupied Rooms:");
				for (int i = 0; i < h_msg.tables[3].rooms.length; i ++) {
					test_printRoom(h_msg.tables[3].rooms[i]);
				}
				System.out.println("\r\r\r");
			}
			else{
				System.out.println("Failed Test ID 3");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}
		
		

		if(i_num==3){
			System.out.println("Test ID 4");
			System.out.println("Description: Success Generate Statisical Analysis Room Report");
			
			//create message
			ReportMessage h_msg = new ReportMessage(0, 0, "test");
			//fill room info:
			Report report = new Report();
			h_msg = report.generateStatisticalRoomAnalysisReport(h_msg);
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("Passed Test ID 4");
				System.out.println("Input: ");
				System.out.println("Output: ");
				
				System.out.println("Room Occupancy:");
				for (int i = 0; i < h_msg.tables[0].rooms.length; i ++) {
					System.out.println("Room Number: " + h_msg.tables[0].rooms[i].roomNumber);
					System.out.println("Occupancy Duration:  " + h_msg.stats[i]);
				}
				System.out.println("\r\r\r");
				

				System.out.println("Room Type Occupancy:");
				for (int i = 0; i < h_msg.tables[1].rooms.length; i ++) {
					System.out.println("Room Type: " + h_msg.tables[1].rooms[i].roomType);
					System.out.println("Occupancy Duration: " + h_msg.graphs[i]);
				}
				System.out.println("\r\r\r");
				
				
				System.out.println("Most Occupied Room:");
				for (int i = 0; i < h_msg.tables[2].rooms.length; i ++) {
					System.out.println("Room Number: " + h_msg.tables[2].rooms[i].roomNumber);
					System.out.println("Occupied for " + h_msg.values[0] + " days");
				}
				System.out.println("\r\r\r");
				
				
				System.out.println("Most Booked, Occupied Room:");
				for (int i = 0; i < h_msg.tables[3].rooms.length; i ++) {
					System.out.println("Room Number: " + h_msg.tables[3].rooms[i].roomNumber);
					System.out.println("Occupied " + h_msg.values[1] + " times");
				}
				System.out.println("\r\r\r");
			}
			else{
				System.out.println("Failed Test ID 3");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}
	}
	
	
	private static void test_printRoom (RoomMessage h_msg){
		System.out.println("   Room ID: " + h_msg.roomID);
		System.out.println("   Room Number: " + h_msg.roomNumber);
		System.out.println("   Floor: " + h_msg.floor);
		System.out.println("   Room Type: " + h_msg.roomType);
		System.out.println("   Price: " + h_msg.price);
		System.out.println("   Cleaned: " + h_msg.cleaned);
		System.out.println("   Onsuite: " + h_msg.onsuite);
		System.out.println("   TV: " + h_msg.tv);
		System.out.println("   Disability: " + h_msg.disabilityAccess);
		System.out.println("   Elevator Access: " + h_msg.elevator);
		System.out.println("   Availability: " + h_msg.available);
		System.out.println("   phone: " + h_msg.phone);
		System.out.println("   internet: " + h_msg.internet);
		System.out.println("   kitchen: " + h_msg.kitchen);
		System.out.println("   Number of Single Beds: " + h_msg.singleBeds);
		System.out.println("   Number of Queen Beds: " + h_msg.queenBeds);
		System.out.println("   Number of King Beds: " + h_msg.kingBeds);
		System.out.println("\r");
	}
	
	
	private static void test_printExtra (ExtraMessage h_msg){
		System.out.println("   Extra ID: " + h_msg.extraID);
		System.out.println("   Booking ID: " + h_msg.bookingID);
		System.out.println("   Extra Name: " + h_msg.extraName);
		System.out.println("   Price: " + h_msg.price);
		System.out.println("   Date: " + h_msg.date);
		System.out.println("   Creation Date: " + h_msg.creationTime);

		System.out.println("\r");
	}
}
