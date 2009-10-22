package messages;

import java.util.*;

enum Action { ADD, EDIT, DELETE }

public class Message {
	int messenger_id;
	int auth_level;
	
	public void fill_Header(int i_id, int i_auth){
		messenger_id=i_id;
		auth_level=i_auth;
	}
}
