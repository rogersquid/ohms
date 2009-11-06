package models.database;

import models.messages.*;

public class AccountKeeper {
	// People logged into the system
	public Message processMessage(AccountMessage i_msg){
		switch(i_msg.return_Header().action)
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
	public Message[] processMessageReturnBunch(AccountMessage i_msg){
		BookingMessage[] reply=null;
		switch(i_msg.return_Header().action)
		{
		case VIEWALL:
			return viewAll(i_msg);
		default:
			//needs implementation
			return reply;
		}
	}
	private AccountMessage add(AccountMessage i_msg){
		Account anAccount = new Account();
		return 	anAccount.addAccount(i_msg);
	}
	private AccountMessage edit(AccountMessage i_msg){
		Account anAccount = new Account();
		return 	anAccount.editAccount(i_msg);
	}
	private AccountMessage delete(AccountMessage i_msg){
		Account anAccount = new Account();
		return 	anAccount.deleteAccount(i_msg);
	}
	private AccountMessage view(AccountMessage i_msg){
		Account anAccount = new Account();
		return 	anAccount.deleteAccount(i_msg);
	}
	private AccountMessage authenticate(AccountMessage i_msg){
		//usign autherizations
		Account anAccount = new Account();
		return 	anAccount.deleteAccount(i_msg);
	}
	private AccountMessage[] viewAll(AccountMessage i_msg){
		//usign autherizations
		Account aAccount = new Account();
		return 	aAccount.viewAllAccount(i_msg);
	}
}
