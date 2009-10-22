package messages;

import java.util.Hashtable;

public class Room_Specs {
	
	Hashtable specs;
	
	public Room_Specs (){
		specs = new Hashtable();
		specs.put("tv", new Integer(0));
	    specs.put("num_bed", new Integer(0));
	    specs.put("AC", new Integer(0));
	}
	
	public int search_Specs(String s_index){
		return (Integer)specs.get(s_index);
	}
	
	public void fill_Specs (int i_tv, int i_num_bed, int i_AC )  {
		specs.put("tv", i_tv);
	    specs.put("num_bed", i_num_bed);
	    specs.put("AC", i_AC);
	}
}
