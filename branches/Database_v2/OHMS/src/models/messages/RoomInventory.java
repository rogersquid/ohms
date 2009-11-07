package models.messages;

import java.util.Hashtable;

public class RoomInventory {
	Hashtable specs;
	//type of bed
	
	public RoomInventory (){
		specs = new Hashtable();
		specs.put("onsuite", 	new Integer(0));
	    specs.put("tv", 		new Integer(0));
	    specs.put("disability", new Integer(0));
	    specs.put("elevator", 	new Integer(0));
	    specs.put("numBeds", 	new Integer(0));
	    specs.put("phone", new Integer(0));
	    specs.put("internet", new Integer(0));
	    specs.put("kitchen", new Integer(0));
	}

	public Object search_Specs(String s_index){
		return specs.get(s_index);
	}
	public void fill_Specs (boolean onsuite, boolean tv, boolean disability, boolean elevator, int numBed, 
			boolean phone, boolean internet, boolean kitchen)  {
		specs.put("onsuite", 	onsuite);
	    specs.put("tv", 		tv);
	    specs.put("disability", disability);
	    specs.put("elevator", 	elevator);
	    specs.put("numBeds", 	numBed);
	    specs.put("phone", 	phone);
	    specs.put("internet", 	internet);
	    specs.put("kitchen", 	kitchen);
	    
	}
	
	public void fill_Specs (int[] i_in )  {
		specs.put("onsuite", i_in[0]);
		specs.put("tv", i_in[1]);
		specs.put("disability", i_in[2]);
		specs.put("elevator", i_in[3]);
		specs.put("numBed", i_in[4]); 
		specs.put("phone", i_in[5]);
		specs.put("internet", i_in[6]); 
		specs.put("kitchen", i_in[7]);
	}
	
	public void print_All(){
		System.out.println("Specs: " + specs.entrySet());
	}
}