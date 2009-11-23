package testMains;

import models.database.*;
import models.messages.*;


public class RoomTest {
	public static void main(String [ ] args){
		System.out.println("\r");
		test_allRoom();
	}
	
	public static void test_allRoom(){
		System.out.println("Start Test Rooms \r");
		for (int i=0; i<8; i++){
			test_addRoom(100, i);
		}
		for (int j=0; j<8; j++){
			test_editRoom(200, j);
		}
		for (int k=0; k<8; k++){
			test_deleteRoom(300, k);
		}
		for (int l=0; l<8; l++){
			test_viewRoom(400, l);
		}
		for (int m=0; m<8; m++){
			test_viewAllRoom(500, m);
		}
		
		System.out.println("Finish Test Rooms \r");
	}
	
	private static void test_addRoom (int t_set, int i_num){
		
		//=====================Rooms Tests==========================
		
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success Added room");

			//Filling Header information
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeRooms(1);
			h_msg.rooms[0] = new RoomMessage();
			//fill room info:
			test_fillRoom(h_msg, t_set, i_num);
			h_msg.response = h_msg.rooms[0].validateParams(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				Room room = new Room();
				h_msg = room.addRoom(h_msg);
			}
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("Passed Test ID 1");
				System.out.println("Input:");
				System.out.println("Output: " + h_msg.response.responseString);
				System.out.println("\r");
			} else{
				System.out.println("Failed Test ID 1");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}
		
		if(i_num==1){
			System.out.println("Test ID 2");
			System.out.println("Description: Failed to Add Room due to invalid room type");
			System.out.println("NOT IMPLEMENTED: no room types are not set yet");

			//Filling Header information
			Message h_msg = new Message(0,0,"test");
			h_msg.initializeRooms(1);
			h_msg.rooms[0] = new RoomMessage();
			//fill room info:
			test_fillRoom(h_msg, t_set, i_num);
			h_msg.rooms[0].roomType = "INVALID";

			h_msg.response = h_msg.rooms[0].validateParams(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				Room room = new Room();
				h_msg = room.addRoom(h_msg);
			}
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.FAIL){
			System.out.println("Passed Test ID 2");
			System.out.println("Input:");
			System.out.println("Output: " + h_msg.response.responseString);
			System.out.println("\r");
			}
		else{
			System.out.println("Failed Test ID 2");
			System.out.println("Expected Output:");
			System.out.println("Expected Change:");
			System.out.println("Error Message: " + h_msg.response.responseString);
			System.out.println("\r");
			}
		}
		if(i_num==2){
			System.out.println("Test ID 3");
			System.out.println("Description: Failed to add room due to invalid blank room type input field");
			
			//Filling Header information
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeRooms(1);
			h_msg.rooms[0] = new RoomMessage();
			//fill room info:
			test_fillRoom(h_msg, t_set, i_num);
			h_msg.rooms[0].roomType = "";

			h_msg.response = h_msg.rooms[0].validateParams(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				Room room = new Room();
				h_msg = room.addRoom(h_msg);
			} 
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.FAIL){
			System.out.println("Passed Test ID 3");
			System.out.println("Input:");
			System.out.println("Output: " + h_msg.response.responseString);
			System.out.println("\r");
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
			System.out.println("Description: Failed to add room due to room already exists");
			
			//Filling Header information
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeRooms(1);
			h_msg.rooms[0] = new RoomMessage();
			//fill room info:
			test_fillRoom(h_msg, t_set, 0);
			h_msg.response = h_msg.rooms[0].validateParams(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				Room room = new Room();
				h_msg = room.addRoom(h_msg);
			}
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.FAIL){
			System.out.println("Passed Test ID 4");
			System.out.println("Input:");
			System.out.println("Output: " + h_msg.response.responseString);
			System.out.println("\r");
			}
		else{
			System.out.println("Failed Test ID 4");
			System.out.println("Expected Output:");
			System.out.println("Expected Change:");
			System.out.println("Error Message: " + h_msg.response.responseString);
			System.out.println("\r");
			}
		}
		
	}	


	private static void test_editRoom (int t_set, int i_num){
		//=====================Edit Room Tests==========================
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success Edit Room: Price");
			//Filling Header information
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeRooms(1);
			h_msg.rooms[0] = new RoomMessage();
			//fill room info:
			test_fillRoom(h_msg, t_set, i_num);
			
			Room room = new Room();
			h_msg.response = h_msg.rooms[0].validateParams(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				h_msg = room.addRoom(h_msg);
			}
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				Message g_msg= new Message(0, 0, "test");
				g_msg.initializeRooms(1);
				g_msg.rooms[0] = new RoomMessage();
				//fill room info:
				test_fillRoom(g_msg, t_set, i_num);
				g_msg.rooms[0].roomID = h_msg.rooms[0].roomID;
				g_msg.rooms[0].price = (float)199.99;
				g_msg.response = h_msg.rooms[0].validateParams(h_msg);
				if (g_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
					g_msg = room.editRoom(g_msg);
				}
				if(g_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
					System.out.println("Passed Test ID 1");
					System.out.println("Input:");
					System.out.println("Output: " + g_msg.response.responseString);
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 1");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + g_msg.response.responseString);
					System.out.println("\r");
					}
				}
			else{
				System.out.println("Failed Test ID 1 at verify");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}
		
		if(i_num==1){
			System.out.println("Test ID 2");
			System.out.println("Description: Failed to edit room due to no change");
			System.out.println("NOT IMPLEMENTED: require confirmation on spec");
			
			//Filling Header information
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeRooms(1);
			h_msg.rooms[0] = new RoomMessage();
			//fill room info:
			test_fillRoom(h_msg, t_set, i_num);
			
			Room room = new Room();
			h_msg.response = h_msg.rooms[0].validateParams(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				h_msg = room.addRoom(h_msg);
			} 
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				/*
				Message g_msg= new Message(0, 0, "test");
				g_msg.initializeRooms(1);
				g_msg.rooms[0] = new RoomMessage();
				//fill room info:
				test_fillRoom(g_msg, t_set, i_num);
				g_msg.rooms[0].roomID = h_msg.rooms[0].roomID;
				g_msg.rooms[0].roomNumber = h_msg.rooms[0].roomNumber;
				*/
				Message g_msg = h_msg;
				g_msg.response = h_msg.rooms[0].validateParams(h_msg);
				if (g_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
					g_msg = room.editRoom(g_msg);
				} 
				if(g_msg.response.responseCode == ResponseMessage.ResponseCode.FAIL){
					System.out.println("Passed Test ID 2");
					System.out.println("Input:");
					System.out.println("Output: " + g_msg.response.responseString);
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 2");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + g_msg.response.responseString);
					System.out.println("\r");
					}
				}
			else{
				System.out.println("Failed Test ID 2 at verify");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}
		
		if(i_num==2){
			System.out.println("Test ID 3");
			System.out.println("Description: Failed to edit room due to invalid room number");
			//Filling Header information
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeRooms(1);
			h_msg.rooms[0] = new RoomMessage();
			//fill room info:
			test_fillRoom(h_msg, t_set, i_num);
			
			Room room = new Room();
			h_msg.response = h_msg.rooms[0].validateParams(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				h_msg = room.addRoom(h_msg);
			} 
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				Message g_msg= new Message(0, 0, "test");
				g_msg.initializeRooms(1);
				g_msg.rooms[0] = new RoomMessage();
				//fill room info:
				test_fillRoom(g_msg, t_set, i_num);
				g_msg.rooms[0].roomNumber = -211;
				g_msg.rooms[0].roomID = h_msg.rooms[0].roomID;
				g_msg.response = h_msg.rooms[0].validateParams(h_msg);
				if (g_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
					g_msg = room.editRoom(g_msg);
				} 
				if(g_msg.response.responseCode == ResponseMessage.ResponseCode.FAIL){
					System.out.println("Passed Test ID 3");
					System.out.println("Input:");
					System.out.println("Output: " + g_msg.response.responseString);
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 3");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + g_msg.response.responseString);
					System.out.println("\r");
					}
				}
			else{
				System.out.println("Failed Test ID 3 at verify");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}
		
		if(i_num==3){
			System.out.println("Test ID 4");
			System.out.println("Description: Failed to edit room due to blank input");
			System.out.println("NOT FULLY IMPLEMENTED: check for all inputs types?");
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeRooms(1);
			h_msg.rooms[0] = new RoomMessage();
			//fill room info:
			test_fillRoom(h_msg, t_set, i_num);
			
			Room room = new Room();
			h_msg.response = h_msg.rooms[0].validateParams(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				h_msg = room.addRoom(h_msg);
			} 
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				Message g_msg= new Message(0, 0, "test");
				g_msg.initializeRooms(1);
				g_msg.rooms[0] = new RoomMessage();
				//fill room info:
				test_fillRoom(g_msg, t_set, i_num);
				g_msg.rooms[0].roomID = h_msg.rooms[0].roomID;
				g_msg.rooms[0].roomType = null;
				g_msg.response = h_msg.rooms[0].validateParams(h_msg);
				if (g_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
					g_msg = room.editRoom(g_msg);
				} 
				if(g_msg.response.responseCode == ResponseMessage.ResponseCode.FAIL){
					System.out.println("Passed Test ID 4");
					System.out.println("Input:");
					System.out.println("Output: " + g_msg.response.responseString);
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 4");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + g_msg.response.responseString);
					System.out.println("\r");
					}
				}
			else{
				System.out.println("Failed Test ID 4 at verify");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}
		
		if(i_num==4){
			System.out.println("Test ID 5");
			System.out.println("Description: Successful Maid cleanup edit");
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeRooms(1);
			h_msg.rooms[0] = new RoomMessage();
			//fill room info:
			test_fillRoom(h_msg, t_set, i_num);
			h_msg.rooms[0].cleaned = false;
			
			Room room = new Room();
			h_msg.response = h_msg.rooms[0].validateParams(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				h_msg = room.addRoom(h_msg);
			} 
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				Message g_msg= new Message(3, 0, "test");
				g_msg.initializeRooms(1);
				g_msg.rooms[0] = new RoomMessage();
				//fill room info:
				test_fillRoom(g_msg, t_set, i_num);
				g_msg.rooms[0].roomID = h_msg.rooms[0].roomID;
				g_msg.rooms[0].cleaned = true;
				g_msg.response = h_msg.rooms[0].validateParams(h_msg);
				if (g_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
					g_msg = room.editRoom(g_msg);
				} 
				if(g_msg.response.responseCode == ResponseMessage.ResponseCode.FAIL){
					System.out.println("Passed Test ID 4");
					System.out.println("Input:");
					System.out.println("Output: " + g_msg.response.responseString);
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 4");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + g_msg.response.responseString);
					System.out.println("\r");
					}
				}
			else{
				System.out.println("Failed Test ID 4 at verify");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}
	}

	private static void test_deleteRoom (int t_set, int i_num){
		//=====================Delete Tests==========================
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success Delete Room");
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeRooms(1);
			h_msg.rooms[0] = new RoomMessage();
			//fill room info:
			test_fillRoom(h_msg, t_set, i_num);
			Room room = new Room();
			h_msg.response = h_msg.rooms[0].validateParams(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				h_msg = room.addRoom(h_msg);
			} 
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				Message g_msg= new Message(0, 0, "test");
				g_msg.initializeRooms(1);
				g_msg.rooms[0] = new RoomMessage();
				//fill room info:
				test_fillRoom(g_msg, t_set, i_num);
				g_msg.rooms[0].roomID = h_msg.rooms[0].roomID;
				g_msg = room.deleteRoom(g_msg);
				if(g_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
					System.out.println("Passed Test ID 1");
					System.out.println("Input:");
					System.out.println("Output: " + g_msg.response.responseString);
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 1");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + g_msg.response.responseString);
					System.out.println("\r");
					}
			} else{
				System.out.println("Failed Test ID 1 at verify");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}
		
		if(i_num==1){
			System.out.println("Test ID 2");
			System.out.println("Description: Failed to delete room due to nonexistant room number");
			System.out.println("DEPRECATED due to design change");
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeRooms(1);
			h_msg.rooms[0] = new RoomMessage();
			//fill room info:
			test_fillRoom(h_msg, t_set, i_num);
			Room room = new Room();
			h_msg.response = h_msg.rooms[0].validateParams(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				h_msg = room.addRoom(h_msg);
			} 
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				Message g_msg= new Message(0, 0, "test");
				g_msg.initializeRooms(1);
				g_msg.rooms[0] = new RoomMessage();
				//fill room info:
				test_fillRoom(g_msg, t_set, 999);
				g_msg.rooms[0].roomID = h_msg.rooms[0].roomID;
				g_msg = room.deleteRoom(g_msg);
				if(g_msg.response.responseCode == ResponseMessage.ResponseCode.FAIL){
					System.out.println("Passed Test ID 2");
					System.out.println("Input:");
					System.out.println("Output: " + g_msg.response.responseString);
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 2");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + g_msg.response.responseString);
					System.out.println("\r");
					}
			} else{
				System.out.println("Failed Test ID 2 at verify");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}
		
		if(i_num==2){
			System.out.println("Test ID 3");
			System.out.println("Description: Failed to delete room due to invalid room number");
			System.out.println("DEPRECATED due to design change");
			
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeRooms(1);
			h_msg.rooms[0] = new RoomMessage();
			//fill room info:
			test_fillRoom(h_msg, t_set, i_num);
			Room room = new Room();
			h_msg.response = h_msg.rooms[0].validateParams(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				h_msg = room.addRoom(h_msg);
			} 
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				Message g_msg= new Message(0, 0, "test");
				g_msg.initializeRooms(1);
				g_msg.rooms[0] = new RoomMessage();
				//fill room info:
				test_fillRoom(g_msg, t_set, -302);
				g_msg.rooms[0].roomID = h_msg.rooms[0].roomID;
				g_msg = room.deleteRoom(g_msg);
				if(g_msg.response.responseCode == ResponseMessage.ResponseCode.FAIL){
					System.out.println("Passed Test ID 3");
					System.out.println("Input:");
					System.out.println("Output: " + g_msg.response.responseString);
					System.out.println("\r");
				}
				else{
					System.out.println("Failed Test ID 3");
					System.out.println("Expected Output:");
					System.out.println("Expected Change:");
					System.out.println("Error Message: " + g_msg.response.responseString);
					System.out.println("\r");
					}
			} else{
				System.out.println("Failed Test ID 3 at verify");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}
	}

	private static void test_viewRoom (int t_set, int i_num){
		//=====================Delete Tests==========================
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success View Room");
			System.out.println("   Test case depends on success addition of Room 101");
			
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeRooms(1);
			h_msg.rooms[0] = new RoomMessage();
			//fill room info:
			test_fillRoom(h_msg, t_set, 101);
			Room room = new Room();
			h_msg = room.getRoom(h_msg);
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("Passed Test ID 1");
				System.out.println("Input: " + h_msg.rooms[0].roomNumber);
				System.out.println("Output: ");
				test_printRoom(h_msg.rooms[0]);
				
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
			System.out.println("Description: Failed to view room due to non-existant room number");
			System.out.println("DEPRECATED due to design change");
			
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeRooms(1);
			h_msg.rooms[0] = new RoomMessage();
			//fill room info:
			//test_fillRoom(h_msg, t_set, 999);
			test_fillRoom(h_msg, t_set, 101);
			Room room = new Room();
			h_msg = room.getRoom(h_msg);
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.FAIL){
				System.out.println("Passed Test ID 2");
				System.out.println("Input: " + h_msg.rooms[0].roomNumber);
				System.out.println("Output: ");
				
				
				System.out.println("\r");
			}
			else{
				System.out.println("Failed Test ID 2");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Error Message: " + h_msg.response.responseString);
				test_printRoom(h_msg.rooms[0]);
				System.out.println("\r");
			}
		}
		if(i_num==2){
			System.out.println("Test ID 3");
			System.out.println("Description: Failed to view room due to invalid room number");
			System.out.println("DEPRECATED");
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeRooms(1);
			h_msg.rooms[0] = new RoomMessage();
			//fill room info:
			test_fillRoom(h_msg, t_set, -101);
			Room room = new Room();
			h_msg = room.getRoom(h_msg);
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.FAIL){
				System.out.println("Passed Test ID 3");
				System.out.println("Input: " + h_msg.rooms[0].roomNumber);
				System.out.println("Output: ");
				
				
				System.out.println("\r");
			}
			else{
				System.out.println("Failed Test ID 3");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Error Message: " + h_msg.response.responseString);
				test_printRoom(h_msg.rooms[0]);
				System.out.println("\r");
			}
		}
	}

	private static void test_viewAllRoom (int t_set, int i_num){
		//=====================View All Tests==========================
		if(i_num==0){
			System.out.println("Test ID 1");
			System.out.println("Description: Success View All Room");
			System.out.println("   Test case depends on success addition of Room 101");
			
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeRooms(1);
			h_msg.rooms[0] = new RoomMessage();
			//fill room info:
			test_fillRoom(h_msg, t_set, i_num);
			Room room = new Room();
			h_msg = room.getAllRooms(h_msg);
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("Passed Test ID 1");
				System.out.println("Input: " + h_msg.rooms[0].roomNumber);
				System.out.println("Output: # of Rooms: " + h_msg.rooms.length);
				for (int i = 0; i < h_msg.rooms.length; i ++) {
					test_printRoom(h_msg.rooms[i]);
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
			System.out.println("Description: Success View all Room available");
			
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeRooms(2);
			//fill filter toggle Room Message
			h_msg.rooms[0] = new RoomMessage();
			h_msg.rooms[0].roomNumber = 0;
			h_msg.rooms[0].floor = 0;
			h_msg.rooms[0].roomType = "INVALID";
			h_msg.rooms[0].price = 0;
			h_msg.rooms[0].onsuite = false;
			h_msg.rooms[0].tv = false;
			h_msg.rooms[0].disabilityAccess = false; 
			h_msg.rooms[0].elevator = false;
			h_msg.rooms[0].available = true;
			h_msg.rooms[0].phone = false;
			h_msg.rooms[0].internet = false;
			h_msg.rooms[0].kitchen = false;
			h_msg.rooms[0].cleaned = false;
			h_msg.rooms[0].singleBeds = 0;
			h_msg.rooms[0].queenBeds = 0;
			h_msg.rooms[0].kingBeds = 0;
			
			//fill filter value Room Message
			h_msg.rooms[1] = new RoomMessage();
			h_msg.rooms[1].roomNumber = 0;
			h_msg.rooms[1].floor = 0;
			h_msg.rooms[1].roomType = "SINGLE";
			h_msg.rooms[1].price = (float)299.99;
			h_msg.rooms[1].onsuite = true;
			h_msg.rooms[1].tv = true;
			h_msg.rooms[1].disabilityAccess = true; 
			h_msg.rooms[1].elevator = true;
			h_msg.rooms[1].available = true;
			h_msg.rooms[1].phone = true;
			h_msg.rooms[1].internet = true;
			h_msg.rooms[1].kitchen = true;
			h_msg.rooms[1].cleaned = true;
			h_msg.rooms[1].singleBeds = 0;
			h_msg.rooms[1].queenBeds = 1;
			h_msg.rooms[1].kingBeds = 0;
			
			Room room = new Room();
			h_msg.response = h_msg.rooms[0].validateParams(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				h_msg = room.getFilteredRooms(h_msg);
			} 
			
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("Passed Test ID 2");
				System.out.println("Input: " + h_msg.rooms[0].roomNumber);
				System.out.println("Output: # of Rooms: " + h_msg.rooms.length);
				for (int i = 0; i < h_msg.rooms.length; i ++) {
					test_printRoom(h_msg.rooms[i]);
				}
				
				System.out.println("\r");
			}
			else{
				System.out.println("Failed Test ID 2");
				System.out.println("Expected Output:");
				System.out.println("Expected Change:");
				System.out.println("Error Message: " + h_msg.response.responseString);
				System.out.println("\r");
			}
		}

		if(i_num==2){
			System.out.println("Test ID 3");
			System.out.println("Description: Success View all Single Room on the 1st floor");
			
			//create message
			Message h_msg= new Message(0, 0, "test");
			h_msg.initializeRooms(2);
			//fill filter toggle Room Message
			h_msg.rooms[0] = new RoomMessage();
			h_msg.rooms[0].roomNumber = 0;
			h_msg.rooms[0].floor = 1;
			h_msg.rooms[0].roomType = "CHECK";
			h_msg.rooms[0].price = 0;
			h_msg.rooms[0].onsuite = false;
			h_msg.rooms[0].tv = false;
			h_msg.rooms[0].disabilityAccess = false; 
			h_msg.rooms[0].elevator = false;
			h_msg.rooms[0].available = false;
			h_msg.rooms[0].phone = false;
			h_msg.rooms[0].internet = false;
			h_msg.rooms[0].kitchen = false;
			h_msg.rooms[0].cleaned = false;
			h_msg.rooms[0].singleBeds = 0;
			h_msg.rooms[0].queenBeds = 0;
			h_msg.rooms[0].kingBeds = 0;
			
			//fill filter value Room Message
			h_msg.rooms[1] = new RoomMessage();
			h_msg.rooms[1].roomNumber = 0;
			h_msg.rooms[1].floor = 100;
			h_msg.rooms[1].roomType = "SINGLE";
			h_msg.rooms[1].price = (float)299.99;
			h_msg.rooms[1].onsuite = true;
			h_msg.rooms[1].tv = true;
			h_msg.rooms[1].disabilityAccess = true; 
			h_msg.rooms[1].elevator = true;
			h_msg.rooms[1].available = true;
			h_msg.rooms[1].phone = true;
			h_msg.rooms[1].internet = true;
			h_msg.rooms[1].kitchen = true;
			h_msg.rooms[1].cleaned = true;
			h_msg.rooms[1].singleBeds = 0;
			h_msg.rooms[1].queenBeds = 1;
			h_msg.rooms[1].kingBeds = 0;
			
			Room room = new Room();
			h_msg.response = h_msg.rooms[0].validateParams(h_msg);
			if (h_msg.response.responseCode != ResponseMessage.ResponseCode.FAIL) {
				h_msg = room.getFilteredRooms(h_msg);
			} 
			
			if(h_msg.response.responseCode == ResponseMessage.ResponseCode.SUCCESS){
				System.out.println("Passed Test ID 3");
				System.out.println("Input: " + h_msg.rooms[0].roomNumber);
				System.out.println("Output: # of Rooms: " + h_msg.rooms.length);
				for (int i = 0; i < h_msg.rooms.length; i ++) {
					test_printRoom(h_msg.rooms[i]);
				}
				
				System.out.println("\r");
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
	
	private static void test_fillRoom (Message h_msg, int testSet, int testCase) {
		h_msg.rooms[0].roomNumber = testSet + testCase;
		h_msg.rooms[0].floor = testSet;
		h_msg.rooms[0].roomType = "SINGLE";
		h_msg.rooms[0].price = (float)299.99;
		h_msg.rooms[0].onsuite = true;
		h_msg.rooms[0].tv = true;
		h_msg.rooms[0].disabilityAccess = true; 
		h_msg.rooms[0].elevator = true;
		h_msg.rooms[0].available = true;
		h_msg.rooms[0].phone = true;
		h_msg.rooms[0].internet = true;
		h_msg.rooms[0].kitchen = true;
		h_msg.rooms[0].cleaned = true;
		h_msg.rooms[0].singleBeds = 0;
		h_msg.rooms[0].queenBeds = 1;
		h_msg.rooms[0].kingBeds = 0;
	}
}
