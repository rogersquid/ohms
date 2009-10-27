package messages;

import java.util.Hashtable;

public class RoomInventory {
	Hashtable specs;
	//type of bed
	
	public RoomInventory (){
		specs = new Hashtable();
		specs.put("onsuite", new Boolean(false));
	    specs.put("tv", new Boolean(false));
	    specs.put("disability", new Boolean(false));
	    specs.put("elevator", new Boolean(false));
	    specs.put("ebirdcall", new Boolean(false));
	    specs.put("emornpaper", new Boolean(false));
	    specs.put("numBeds", new Integer(0));
	}
	public Object search_Specs(String s_index){
		return specs.get(s_index);
	}
	public void fill_Specs (boolean onsuite, boolean tv, boolean disability, boolean elevator, boolean ebirdcall,
			boolean emornpaper, int numBed)  {
		specs.put("onsuite", onsuite);
	    specs.put("tv", tv);
	    specs.put("disability", disability);
	    specs.put("elevator", elevator);
	    specs.put("ebirdcall", ebirdcall);
	    specs.put("emornpaper", emornpaper);
	    specs.put("numBeds", numBed);
	}
	
	public void fill_Specs (int[] i_in )  {
		specs.put("onsuite", (i_in[0] != 0 ));
		specs.put("tv", (i_in[1] != 0 ));
		specs.put("disability", (i_in[2] != 0 ));
	/*	specs.put("elevator", (i_in[3] != 0 ));
		specs.put("ebirdcall", (i_in[4] != 0 ));
		specs.put("emornpaper", (i_in[5] != 0 ));
		specs.put("numBed", i_in[6]); */
	}
	
	public void print_All(){
		System.out.println("Specs: " + specs.entrySet());
	}
}