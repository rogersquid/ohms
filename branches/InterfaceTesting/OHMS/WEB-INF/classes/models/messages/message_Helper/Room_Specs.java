package models.messages.message_Helper;

import java.util.Hashtable;

public class Room_Specs {
	Hashtable specs;
	
	public Room_Specs (){
		specs = new Hashtable();
		specs.put("onsuite", new Boolean(false));
	    specs.put("tv", new Boolean(false));
	    specs.put("disability", new Boolean(false));
	    specs.put("elevator", new Boolean(false));
	    specs.put("ebirdcall", new Boolean(false));
	    specs.put("emornpaper", new Boolean(false));
	    specs.put("availability", new Boolean(false));
	    specs.put("numBeds", new Integer(0));
	}
	public int search_Specs(String s_index){
		return (Integer)specs.get(s_index);
	}
	public void fill_Specs (boolean onsuite, boolean tv, boolean disability, boolean elevator, boolean ebirdcall,
			boolean emornpaper, boolean availability, int numBed)  {
		specs.put("onsuite", onsuite);
	    specs.put("tv", tv);
	    specs.put("disability", disability);
	    specs.put("elevator", elevator);
	    specs.put("ebirdcall", ebirdcall);
	    specs.put("emornpaper", emornpaper);
	    specs.put("availability", availability);
	    specs.put("numBeds", numBed);
	}
	
	public void fill_Specs (int[] i_in )  {
		if (i_in[0] == 0) {
			specs.put("onsuite", false);
		} else if (i_in[0] == 1) {
			specs.put("onsuite", true);
		} else {
			System.err.println("error filling room specs");
		}
		if (i_in[0] == 0) {
			specs.put("tv", false);
		} else if (i_in[0] == 1) {
			specs.put("tv", true);
		} else {
			System.err.println("error filling room specs");
		}
		if (i_in[0] == 0) {
			specs.put("disability", false);
		} else if (i_in[0] == 1) {
			specs.put("disability", true);
		} else {
			System.err.println("error filling room specs");
		}
		if (i_in[0] == 0) {
			specs.put("elevator", false);
		} else if (i_in[0] == 1) {
			specs.put("elevator", true);
		} else {
			System.err.println("error filling room specs");
		}
		if (i_in[0] == 0) {
			specs.put("ebirdcall", false);
		} else if (i_in[0] == 1) {
			specs.put("ebirdcall", true);
		} else {
			System.err.println("error filling room specs");
		}
		if (i_in[0] == 0) {
			specs.put("emornpaper", false);
		} else if (i_in[0] == 1) {
			specs.put("emornpaper", true);
		} else {
			System.err.println("error filling room specs");
		}
		if (i_in[0] == 0) {
			specs.put("availability", false);
		} else if (i_in[0] == 1) {
			specs.put("availability", true);
		} else {
			System.err.println("error filling room specs");
		}
		specs.put("numBed", i_in[8]);
	}
	
	public void print_All(){
		System.out.println("Specs: " + specs.entrySet());
	}
}