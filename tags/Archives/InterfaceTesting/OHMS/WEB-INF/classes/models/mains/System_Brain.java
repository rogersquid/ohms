package mains;

import database.*;
import message_Helper.Header;
import messages.*;
import java.util.Random;

public class System_Brain {
	
	Hotel[] hotels;
	int num_hotels;
	
	public System_Brain(String names, String i_path){
		num_hotels=1;
		hotels=new Hotel[num_hotels];
		hotels[0]= new Hotel(names, i_path);	
	}
	
	public System_Brain(String[] names, String[] i_path){
		num_hotels=names.length;
		hotels=new Hotel[num_hotels];
		for (int i=0; i<names.length; i++){
			hotels[i]= new Hotel(names[i], i_path[i]);
		}	
	}
	
	public void process_Message(Message i_msg){
		System.err.println(num_hotels);
		for(int i=0; i<num_hotels; i++){
			System.err.print("brain process  message - comparison");
			Header head=i_msg.return_Header();
			if((hotels[i].return_Name()).compareTo(head.name_hotel)==0){
				hotels[i].process_Message(i_msg);
				System.err.print("brain process  message - passed comparison");
				break;
			}
			System.err.print(hotels[i].return_Name());
			System.err.print(head.name_hotel);
		}
	}
}
