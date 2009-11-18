package testMains;

import models.database.Hotel;
import models.messages.ExtraMessage;
import models.messages.Header;

public class ExtrasTest {
	public static void main(String [ ] args){
		System.out.println("\r");
		//test_Extras(0);
		//test_Extras(1);
		test_Extras(2);
		//test_Extras(3);
	}
	
		private static void test_Extras(int i_num){	
			if (i_num==0){
				System.out.println("Start Add Extra Test");
				ExtraMessage h_msg = new ExtraMessage(1, 1, "test", Header.Action.ADD);
				java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
				h_msg.fillAll(2 , 4, "TV", 700,date);
				Hotel hotel = new Hotel("test");
				hotel.processMessage(h_msg);
				Header head=h_msg.returnHeader();				
				//ExtraMessage reply=(ExtraMessage) hotel.processMessage(h_msg);
				//Header head=reply.returnHeader();
				if(Header.Response.SUCCESS==head.responseCode){
				System.out.println("Passed Test ID 1");
				
			}
			else{
				System.out.println("Failed Test ID 1");
			}
				System.out.println("Finish Add Extra Test");
		}
			if (i_num==1){
				System.out.println("Start Delete Extra Test");
				Hotel hotel = new Hotel("test");
				ExtraMessage g_msg = new ExtraMessage(0, 0, "test", Header.Action.DELETE);
				java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
				g_msg.fillAll(2, 5, "TV", 700,date);
				hotel.processMessage(g_msg);
				Header head=g_msg.returnHeader();
				if(Header.Response.SUCCESS==head.responseCode){
				System.out.println("Passed Test ID 2");
				System.out.println("\r");
				}
			else{
				System.out.println("Failed Test ID 2");
				System.out.println("\r");
				}
				System.out.println("Finish Delete extra Test");
			}
			
			if(i_num==2){
				System.out.println("Start Edit Extra Test");
				ExtraMessage h_msg = new ExtraMessage(1, 1, "test", Header.Action.EDIT);
				h_msg.extraID=2;
				h_msg.extraName="beer";
				h_msg.extraCost=1000;
				Hotel hotel = new Hotel("test");
				hotel.processMessage(h_msg);
				Header head=h_msg.returnHeader();				
				//ExtraMessage reply=(ExtraMessage) hotel.processMessage(h_msg);
				//Header head=reply.returnHeader();
				if(Header.Response.SUCCESS==head.responseCode){
				System.out.println("Passed Test ID 1");
				
			}
			else{
				System.out.println("Failed Test ID 1");
			}
				System.out.println("Finish Edit Extra Test");
			}
			
			if(i_num==3){
				System.out.println("Start View Extra Test");
				Hotel hotel = new Hotel("test");
				ExtraMessage g_msg = new ExtraMessage(0, 0, "test", Header.Action.VIEW);
				java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
				g_msg.extraID=2;
				
				hotel.processMessage(g_msg);
				Header head=g_msg.returnHeader();
				if(Header.Response.SUCCESS==head.responseCode){
				System.out.println("Passed Test ID 2");
				System.out.println(head.responseCode);
				System.out.println(head.responseString);
				System.out.println("\r");
				}
			else{
				System.out.println("Failed Test ID 2");
				System.out.println("\r");
				}
				System.out.println("Finish View extra Test");
			}
		}
}

/*
 				System.out.println("Start Running Test for Add booking");
				Hotel hotel = new Hotel("test");
				ExtraMessage input=null;
				ExtraMessage reply=null;
				java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());			
			    input= new ExtraMessage(1, 1, "test", Header.Action.ADD);
			    input.extraID=1;
			    input.bookingID=1;
			    input.extraName="TV";
			    input.extraCost=700;
				input.orderDate=date;
				reply= (ExtraMessage) hotel.processMessage(input);
				Header head = reply.returnHeader();
				System.out.println("...");
				System.out.println(head.responseCode);
				System.out.println(head.responseString);
				System.out.println("....");
*/
