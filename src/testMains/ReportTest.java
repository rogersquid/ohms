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
		System.out.println("Description: Success View Report");
		
		//create message
		Message h_msg= new Message(0, 0, "test");
		h_msg.initializeReports(1);
		//fill room info:
		Report report = new Report();
		h_msg = report.statusReport(h_msg);
		
		if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
			System.out.println("Passed Test ID 1");
			System.out.println("Input: ");
			System.out.println("Output: # of Rooms: " + h_msg.reports[0].roomArray.length);
			System.out.println(" % of available rooms occupied: " + (float)((float)h_msg.reports[0].occupiedRoomsCount / (float)h_msg.reports[0].availRoomCount));
			System.out.println(" % of available rooms unoccupied: " + (float)((float)h_msg.reports[0].availUnoccuRoomsCount / (float)h_msg.reports[0].availRoomCount));
			System.out.println("\r");
			for (int i = 0; i < h_msg.reports[0].roomArray.length; i ++) {
				System.out.println("   Room ID: " + h_msg.reports[0].roomArray[i][0]);
				System.out.println("   Room Number: " + h_msg.reports[0].roomArray[i][1]);
				System.out.println("   Floor: " + h_msg.reports[0].roomArray[i][2]);
				System.out.println("   Occupied?: " + h_msg.reports[0].roomArray[i][3]);
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
