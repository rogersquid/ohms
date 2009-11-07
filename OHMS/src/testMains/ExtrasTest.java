package testMains;

import models.database.Hotel;
import models.messages.ExtraMessage;
import models.messages.Header;

public class ExtrasTest {
	public static void main(String [ ] args){
		System.out.println("\r");
		test_Extras(0);
	}
	
		private static void test_Extras(int i_num){	
			if (i_num==0){
				System.out.println("Start Add Extra Test");
				ExtraMessage h_msg = new ExtraMessage(0, 0, "test", Header.Action.ADD);
				java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
				h_msg.fillAll(date,4, "TV", 700);
				Hotel hotel = new Hotel("test");
				//hotel.processMessage(h_msg);
				//Header head=h_msg.return_Header();
				
				ExtraMessage reply=(ExtraMessage) hotel.processMessage(h_msg);
				Header head=reply.return_Header();
				if(Header.Response.SUCCESS==head.responseCode){
				System.out.println("Passed Test ID 1");
				System.out.println("\r");
			}
			else{
				System.out.println("Failed Test ID 1");
				System.out.println("\r");
			}
				System.out.println("Finish Add Extra Test");		
		}
			if (i_num==1){
				System.out.println("Start Delete Extra Test");
				ExtraMessage h_msg = new ExtraMessage(0, 0, "test", Header.Action.ADD);
				java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
				h_msg.fillAll(date, 5, "TV", 700);
				Hotel hotel = new Hotel("test");
				hotel.processMessage(h_msg);
				
				ExtraMessage g_msg = new ExtraMessage(0, 0, "test", Header.Action.DELETE);
				java.sql.Date date1 = new java.sql.Date(new java.util.Date().getTime());
				h_msg.fillAll(date1, 5, "TV", 700);
				hotel.processMessage(g_msg);
				Header head=h_msg.return_Header();
				if(Header.Response.SUCCESS==head.responseCode){
				System.out.println("Passed Test ID 2");
				System.out.println("\r");
				}
			else{
				System.out.println("Failed Test ID 2");
				System.out.println("\r");
				}
			}
			System.out.println("Finish Delete extra Test");
		}
}
