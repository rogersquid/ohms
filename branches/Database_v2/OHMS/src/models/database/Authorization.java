package models.database;
import java.sql.SQLException;

import models.messages.*;
import models.misc.*;
import java.sql.*;

public class Authorization {
	public static AuthMessage getAuthLevel(AuthMessage i_msg)
	{
		AuthMessage reply = null;
		databaseHelper 	dbcon 	= null;
		try {
			dbcon 				= new databaseHelper();
			ResultSet results = dbcon.select("SELECT authLevel FROM account WHERE accountID="+ i_msg.account_id);
			if (results.first()) {
				i_msg.authLevel = results.getInt(results.findColumn("authLevel"));
				i_msg.fillHeaderResponse(Header.Response.SUCCESS, "Got authorization level for" +
						" Account Email: " + i_msg.email);
				reply	= i_msg;
			} else {
				i_msg.fillHeaderResponse(Header.Response.FAIL, "Authorization check failed for" +
						" Account ID: " + i_msg.account_id);
				reply	= i_msg;
			}
		} catch (SQLException e) {
			System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "Authorization check failed for" +
					" Account ID: " + i_msg.account_id);
			reply	= i_msg;
		} catch (ClassNotFoundException e) {
			System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			i_msg.fillHeaderResponse(Header.Response.FAIL, "Authorization check failed for" +
					" Account ID: " + i_msg.account_id);
			reply	= i_msg;
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return reply.deepCopy();
	}

}
