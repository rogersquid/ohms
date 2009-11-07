package models.database;

import models.messages.*;

public class ExtraKeeper {
	public Message processMessage(ExtraMessage i_msg){
		switch(i_msg.returnHeader().action)
		{
		case ADD:
			return add(i_msg);
		case EDIT:
			return edit(i_msg);
		case VIEW:
			return view(i_msg);
		case DELETE:
			return delete(i_msg);
		default:
			//needs implementation
			return i_msg;
		}
	}
	public Message[] processMessageReturnBunch(ExtraMessage i_msg){
		ExtraMessage[] reply=null;
		switch(i_msg.returnHeader().action)
		{
		case VIEWALL:
			return viewAll(i_msg);
		default:
			//needs implementation
			return reply;
		}
	}
	private ExtraMessage add(ExtraMessage i_msg){
		Extras aExtra = new Extras();
		return 	aExtra.addExtra(i_msg);
	}
	private ExtraMessage edit(ExtraMessage i_msg){
		Extras aExtra = new Extras();
		return 	aExtra.editExtra(i_msg);
	}
	private ExtraMessage delete(ExtraMessage i_msg){
		Extras aExtra = new Extras();
		return 	aExtra.deleteExtra(i_msg);
	}
	private ExtraMessage view(ExtraMessage i_msg){
		Extras aExtra = new Extras();
		return 	aExtra.viewExtra(i_msg);
	}
	private ExtraMessage[] viewAll(ExtraMessage i_msg){
		Extras aExtra = new Extras();
		return 	aExtra.viewAllExtra(i_msg);
	}
}
