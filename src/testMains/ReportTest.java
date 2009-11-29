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
			for (int i = 0; i < h_msg.tables.length; i ++) {
				System.out.println("   Room ID: " + h_msg.tables[0].rooms[i].roomID);
				System.out.println("   Room Number: " + h_msg.tables[0].rooms[i].roomNumber);
				System.out.println("   Floor: " + h_msg.tables[0].rooms[i].floor);
				System.out.println("   Room Type: " + h_msg.tables[0].rooms[i].roomType);
				System.out.println("   Price: " + h_msg.tables[0].rooms[i].price);
				System.out.println("   Cleaned: " + h_msg.tables[0].rooms[i].cleaned);
				System.out.println("   Onsuite: " + h_msg.tables[0].rooms[i].onsuite);
				System.out.println("   TV: " + h_msg.tables[0].rooms[i].tv);
				System.out.println("   Disability: " + h_msg.tables[0].rooms[i].disabilityAccess);
				System.out.println("   Elevator Access: " + h_msg.tables[0].rooms[i].elevator);
				System.out.println("   Availability: " + h_msg.tables[0].rooms[i].available);
				System.out.println("   phone: " + h_msg.tables[0].rooms[i].phone);
				System.out.println("   internet: " + h_msg.tables[0].rooms[i].internet);
				System.out.println("   kitchen: " + h_msg.tables[0].rooms[i].kitchen);
				System.out.println("   Number of Single Beds: " + h_msg.tables[0].rooms[i].singleBeds);
				System.out.println("   Number of Queen Beds: " + h_msg.tables[0].rooms[i].queenBeds);
				System.out.println("   Number of King Beds: " + h_msg.tables[0].rooms[i].kingBeds);
				System.out.println("\r");
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
}
