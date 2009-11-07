package testMains;

import models.database.Hotel;
import models.messages.Header;
import models.messages.RoomMessage;

public class RoomTest {
	public static void test_allRoom(){
		System.out.println("Start Test Rooms \r");
		for (int i=0; i<8; i++){
			test_addRoom(i);
		}
		for (int j=0; j<8; j++){
			test_editRoom(j);
		}
		for (int k=0; k<8; k++){
			test_deleteRoom(k);
		}
		for (int l=0; l<8; l++){
			test_viewRoom(l);
		}
		System.out.println("Finish Test Rooms \r");
	}
	
	private static void test_addRoom (int i_num){
		
		//=====================Rooms Tests==========================
		
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success Added room");
			
			//filling the RoomInventory
			int[] i = new int[7];
			for (int j = 0; j < 7; j++) {
				i[j] = 1;
			}
			//Filling Header information
			RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
			//fill room info:
			//roomID, roomType, roomNumber, floor, price, available, clean, RoomInventory
			h_msg.fill_All(1, "Single", 101, 1, 500, true, true, i );
			Hotel hotel = new Hotel("OHMS");
			hotel.processMessage(h_msg);
			Header head=h_msg.return_Header();
			if(Header.Response.SUCCESS==head.response_code){
			System.out.println("Passed Test ID 1");
			System.out.println("Input:");
			System.out.println("Output: " + head.response_string);
			System.out.println("\r");
			}
		else{
			System.out.println("Failed Test ID 1");
			System.out.println("Expected Output:");
			System.out.println("Expected Change:");
			System.out.println("Error Message: " + head.response_string);
			System.out.println("\r");
			}
		}
		
		if(i_num==1){
			System.out.println("Test ID 2");
			System.out.println("Description: Failed to Add Room due to invalid room type");
			System.out.println("NOT IMPLEMENTED: no room types are not set yet");
			
			//filling the RoomInventory
			int[] i = new int[7];
			for (int j = 0; j < 7; j++) {
				i[j] = 1;
			}
			//Filling Header information
			RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
			//fill room info:
			//roomID, roomType, roomNumber, floor, price, available, clean, RoomInventory
			h_msg.fill_All(2, "Single", 102, 1, 500, true, true, i );
			Hotel hotel = new Hotel("OHMS");
			hotel.processMessage(h_msg);
			Header head=h_msg.return_Header();
			if(Header.Response.FAIL==head.response_code){
			System.out.println("Passed Test ID 2");
			System.out.println("Input:");
			System.out.println("Output: " + head.response_string);
			System.out.println("\r");
			}
		else{
			System.out.println("Failed Test ID 2");
			System.out.println("Expected Output:");
			System.out.println("Expected Change:");
			System.out.println("Error Message: " + head.response_string);
			System.out.println("\r");
			}
		}
		if(i_num==2){
			System.out.println("Test ID 3");
			System.out.println("Description: Failed to add room due to invalid blank room type input field");
			
			//filling the RoomInventory
			int[] i = new int[7];
			for (int j = 0; j < 7; j++) {
				i[j] = 1;
			}
			//Filling Header information
			RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
			//fill room info:
			//roomID, roomType, roomNumber, floor, price, available, clean, RoomInventory
			h_msg.fill_All(3, "", 103, 1, 500, true, true, i );
			Hotel hotel = new Hotel("OHMS");
			hotel.processMessage(h_msg);
			Header head=h_msg.return_Header();
			if(Header.Response.FAIL==head.response_code){
			System.out.println("Passed Test ID 3");
			System.out.println("Input:");
			System.out.println("Output: " + head.response_string);
			System.out.println("\r");
			}
		else{
			System.out.println("Failed Test ID 3");
			System.out.println("Expected Output:");
			System.out.println("Expected Change:");
			System.out.println("Error Message: " + head.response_string);
			System.out.println("\r");
			}
		}
		
		if(i_num==3){
			System.out.println("Test ID 4");
			System.out.println("Description: Failed to add room due to room already exists");
			
			//filling the RoomInventory
			int[] i = new int[7];
			for (int j = 0; j < 7; j++) {
				i[j] = 1;
			}
			//Filling Header information
			RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
			//fill room info:
			//roomID, roomType, roomNumber, floor, price, available, clean, RoomInventory
			h_msg.fill_All(4, "Single", 101, 1, 500, true, true, i );
			Hotel hotel = new Hotel("OHMS");
			hotel.processMessage(h_msg);
			Header head=h_msg.return_Header();
			if(Header.Response.FAIL==head.response_code){
			System.out.println("Passed Test ID 4");
			System.out.println("Input:");
			System.out.println("Output: " + head.response_string);
			System.out.println("\r");
			}
		else{
			System.out.println("Failed Test ID 4");
			System.out.println("Expected Output:");
			System.out.println("Expected Change:");
			System.out.println("Error Message: " + head.response_string);
			System.out.println("\r");
			}
		}
		
	}	


	private static void test_editRoom (int i_num){
		//=====================Edit Room Tests==========================
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success Edit Room: Price");
			//filling the RoomInventory
			int[] i = new int[7];
			for (int j = 0; j < 7; j++) {
				i[j] = 1;
			}
			
			RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
			//roomID, roomType, roomNumber, floor, price, available, clean, RoomInventory
			h_msg.fill_All(11, "Single", 201, 1, 500, true, true, i );
			Hotel hotel = new Hotel("OHMS");
			hotel.processMessage(h_msg);
			Header head = h_msg.return_Header();
			if(Header.Response.SUCCESS==head.response_code){
				RoomMessage g_msg = new RoomMessage(0, 0, "OHMS", Header.Action.EDIT);
				//roomID, roomType, roomNumber, floor, price, available, clean, RoomInventory
				g_msg.fill_All(11, "Single", 201, 1, 750, true, true, i);
				hotel.processMessage(g_msg);
				Header head1 = g_msg.return_Header();
				if(Header.Response.SUCCESS==head1.response_code){
					System.out.println("Passed Test ID 1");
					System.out.println("Input:");
					System.out.println("Output: " + head1.response_string);
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 1");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + head1.response_string);
					System.out.println("\r");
					}
				}
			else{
				System.out.println("Failed Test ID 1 at verify");
				System.out.println("Error Message: " + head.response_string);
				System.out.println("\r");
			}
		}
		
		if(i_num==1){
			System.out.println("Test ID 2");
			System.out.println("Description: Failed to edit room due to no change");
			System.out.println("NOT IMPLEMENTED: require confirmation on spec");
			//filling the RoomInventory
			int[] i = new int[7];
			for (int j = 0; j < 7; j++) {
				i[j] = 1;
			}
			
			RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
			//roomID, roomType, roomNumber, floor, price, available, clean, RoomInventory
			h_msg.fill_All(12, "Single", 202, 1, 500, true, true, i );
			Hotel hotel = new Hotel("OHMS");
			hotel.processMessage(h_msg);
			Header head = h_msg.return_Header();
			if(Header.Response.SUCCESS==head.response_code){
				RoomMessage g_msg = new RoomMessage(0, 0, "OHMS", Header.Action.EDIT);
				//roomID, roomType, roomNumber, floor, price, available, clean, RoomInventory
				g_msg.fill_All(12, "Single", 202, 1, 500, true, true, i);
				hotel.processMessage(g_msg);
				Header head1 = g_msg.return_Header();
				if(Header.Response.SUCCESS==head1.response_code){
					System.out.println("Passed Test ID 2");
					System.out.println("Input:");
					System.out.println("Output: " + head1.response_string);
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 2");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + head1.response_string);
					System.out.println("\r");
					}
				}
			else{
				System.out.println("Failed Test ID 2 at verify");
				System.out.println("Error Message: " + head.response_string);
				System.out.println("\r");
			}
		}
		
		if(i_num==2){
			System.out.println("Test ID 3");
			System.out.println("Description: Failed to edit room due to invalid room number");
			System.out.println("NOT IMPLEMENTED: How? can't call function if room number is invalid");
			//filling the RoomInventory
			int[] i = new int[7];
			for (int j = 0; j < 7; j++) {
				i[j] = 1;
			}
			
			RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
			//roomID, roomType, roomNumber, floor, price, available, clean, RoomInventory
			h_msg.fill_All(13, "Single", 203, 1, 500, true, true, i );
			Hotel hotel = new Hotel("OHMS");
			hotel.processMessage(h_msg);
			Header head = h_msg.return_Header();
			if(Header.Response.SUCCESS==head.response_code){
				RoomMessage g_msg = new RoomMessage(0, 0, "OHMS", Header.Action.EDIT);
				//roomID, roomType, roomNumber, floor, price, available, clean, RoomInventory
				g_msg.fill_All(13, "Single", 203, 1, 500, true, true, i);
				hotel.processMessage(g_msg);
				Header head1 = g_msg.return_Header();
				if(Header.Response.SUCCESS==head1.response_code){
					System.out.println("Passed Test ID 3");
					System.out.println("Input:");
					System.out.println("Output: " + head1.response_string);
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 3");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + head1.response_string);
					System.out.println("\r");
					}
				}
			else{
				System.out.println("Failed Test ID 3 at verify");
				System.out.println("Error Message: " + head.response_string);
				System.out.println("\r");
			}
		}
		
		if(i_num==3){
			System.out.println("Test ID 4");
			System.out.println("Description: Failed to edit room due to blank input");
			System.out.println("NOT FULLY IMPLEMENTED: check for all inputs types?");
			//filling the RoomInventory
			int[] i = new int[7];
			for (int j = 0; j < 7; j++) {
				i[j] = 1;
			}
			
			RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
			//roomID, roomType, roomNumber, floor, price, available, clean, RoomInventory
			h_msg.fill_All(14, "Single", 204, 1, 500, true, true, i );
			Hotel hotel = new Hotel("OHMS");
			hotel.processMessage(h_msg);
			Header head = h_msg.return_Header();
			if(Header.Response.SUCCESS==head.response_code){
				RoomMessage g_msg = new RoomMessage(0, 0, "OHMS", Header.Action.EDIT);
				//roomID, roomType, roomNumber, floor, price, available, clean, RoomInventory
				g_msg.fill_All(14, "", 204, 1, 500, true, true, i);
				hotel.processMessage(g_msg);
				Header head1 = g_msg.return_Header();
				if(Header.Response.SUCCESS==head1.response_code){
					System.out.println("Passed Test ID 4");
					System.out.println("Input:");
					System.out.println("Output: " + head1.response_string);
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 4");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + head1.response_string);
					System.out.println("\r");
					}
				}
			else{
				System.out.println("Failed Test ID 4 at verify");
				System.out.println("Error Message: " + head.response_string);
				System.out.println("\r");
			}
		}
	}

	private static void test_deleteRoom (int i_num){
		//=====================Delete Tests==========================
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success Delete Room");
			//filling the RoomInventory
			int[] i = new int[7];
			for (int j = 0; j < 7; j++) {
				i[j] = 1;
			}
			
			RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
			h_msg.fill_All(101, "Single", 301, 1, 500, true, true, i );
			Hotel hotel = new Hotel("OHMS");
			hotel.processMessage(h_msg);
			Header head = h_msg.return_Header();
			if(Header.Response.SUCCESS==head.response_code){
			
			RoomMessage g_msg = new RoomMessage(0,0,"OHMS", Header.Action.DELETE);
				g_msg.room_number = h_msg.room_number;
				hotel.processMessage(g_msg);
				Header head1 = g_msg.return_Header();
				if(Header.Response.SUCCESS==head1.response_code){
					System.out.println("Passed Test ID 1");
					System.out.println("Input:");
					System.out.println("Output: " + head1.response_string);
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 1");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + head1.response_string);
					System.out.println("\r");
					}
			}
				else{
					System.out.println("Failed Test ID 1 at verify");
					System.out.println("Error Message: " + head.response_string);
					System.out.println("\r");
					}
		}
		
		if(i_num==1){
			System.out.println("Test ID 2");
			System.out.println("Description: Failed to delete room due to nonexistant room number");
			//filling the RoomInventory
			int[] i = new int[7];
			for (int j = 0; j < 7; j++) {
				i[j] = 1;
			}
			
			RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
			h_msg.fill_All(102, "Single", 302, 1, 500, true, true, i );
			Hotel hotel = new Hotel("OHMS");
			hotel.processMessage(h_msg);
			Header head = h_msg.return_Header();
			if(Header.Response.SUCCESS==head.response_code){
			
			RoomMessage g_msg = new RoomMessage(0,0,"OHMS", Header.Action.DELETE);
				g_msg.room_number = 999;
				hotel.processMessage(g_msg);
				Header head1 = g_msg.return_Header();
				if(Header.Response.SUCCESS==head1.response_code){
					System.out.println("Passed Test ID 2");
					System.out.println("Input:");
					System.out.println("Output: " + head1.response_string);
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 2");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + head1.response_string);
					System.out.println("\r");
					}
			}
				else{
					System.out.println("Failed Test ID 2 at verify");
					System.out.println("Error Message: " + head.response_string);
					System.out.println("\r");
					}
		}
		
		if(i_num==2){
			System.out.println("Test ID 3");
			System.out.println("Description: Failed to delete room due to invalid room number");
			System.out.println("NOT IMPLEMENTED: functions would not allow non-Int to be entered");
			
			//filling the RoomInventory
			int[] i = new int[7];
			for (int j = 0; j < 7; j++) {
				i[j] = 1;
			}
			
			RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.ADD);
			h_msg.fill_All(101, "Single", 301, 1, 500, true, true, i );
			Hotel hotel = new Hotel("OHMS");
			hotel.processMessage(h_msg);
			Header head = h_msg.return_Header();
			if(Header.Response.SUCCESS==head.response_code){
			
			RoomMessage g_msg = new RoomMessage(0,0,"OHMS", Header.Action.DELETE);
				g_msg.room_number = h_msg.room_number;
				hotel.processMessage(g_msg);
				Header head1 = g_msg.return_Header();
				if(Header.Response.SUCCESS==head1.response_code){
					System.out.println("Passed Test ID 13");
					System.out.println("Input:");
					System.out.println("Output: " + head1.response_string);
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 3");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + head1.response_string);
					System.out.println("\r");
					}
			}
				else{
					System.out.println("Failed Test ID 3 at verify");
					System.out.println("Error Message: " + head.response_string);
					System.out.println("\r");
					}
		}
	}

	private static void test_viewRoom (int i_num){
		//=====================Delete Tests==========================
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success View Room");
			
			RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.VIEW);
			h_msg.room_number = 101;
			Hotel hotel = new Hotel("OHMS");
			hotel.processMessage(h_msg);
			Header head = h_msg.return_Header();
				if(Header.Response.SUCCESS==head.response_code){
					System.out.println("Passed Test ID 1");
					System.out.println("Input: " + h_msg.room_number);
					System.out.println("Output: ");
					test_printRoom(h_msg);
					
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 1");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + head.response_string);
					System.out.println("\r");
					}
		}
		if(i_num==1){
			System.out.println("Test ID 2");
			System.out.println("Description: Success View Room");
			
			RoomMessage h_msg = new RoomMessage(0, 0, "OHMS", Header.Action.VIEW);
			h_msg.room_number = 999;
			Hotel hotel = new Hotel("OHMS");
			hotel.processMessage(h_msg);
			Header head = h_msg.return_Header();
				if(Header.Response.SUCCESS==head.response_code){
					System.out.println("Passed Test ID 2");
					System.out.println("Input: " + h_msg.room_number);
					System.out.println("Output: ");
					test_printRoom(h_msg);
					
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 1");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + head.response_string);
					System.out.println("\r");
					}
		}
	}
	private static void test_printRoom (RoomMessage h_msg){
		System.out.println("   Room ID: " + h_msg.room_id);
		System.out.println("   Room Number: " + h_msg.room_number);
		System.out.println("   Floor: " + h_msg.room_floor);
		System.out.println("   Room Type: " + h_msg.room_type);
		System.out.println("   Price: " + h_msg.price);
		System.out.println("   Onsuite: " + h_msg.room_specs.search_Specs("onsuite").toString());
		System.out.println("   TV: " + h_msg.room_specs.search_Specs("tv").toString());
		System.out.println("   Disability: " + h_msg.room_specs.search_Specs("disability").toString());
		System.out.println("   Elevator Access: " + h_msg.room_specs.search_Specs("elevator").toString());
		System.out.println("   Earlybird Call: " + h_msg.room_specs.search_Specs("ebirdcall").toString());
		System.out.println("   Early Morning Paper: " + h_msg.room_specs.search_Specs("emornpaper").toString());
		System.out.println("   Availability: " + h_msg.available);
		System.out.println("   Number of Beds: " + h_msg.room_specs.search_Specs("numBed"));
	}
	
}
