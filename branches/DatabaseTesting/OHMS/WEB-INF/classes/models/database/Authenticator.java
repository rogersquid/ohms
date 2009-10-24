package database;

import java.io.*;
import java.util.*;
import java.sql.*;
import java.security.*;
import messages.*;
import misc.*;

public class Authenticator {

	public static int get_auth_level(int i_Account_ID) {
// Account_keeper may ask for the auth_level of a given account.
// Check the DB for the input account id, and return the auth level
// of that account.
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection dbcon = DriverManager.getConnection("jdbc:mysql://localhost/eece419", "eece419", "dude");
			Statement statement = dbcon.createStatement();

			Array	getArray(String columnLabel) 

			String query = "SELECT authLevel FROM users WHERE id='" + i_Account_ID + "'s";
			ResultSet rs = statement.executeQuery(query);
			int[] authLevel = rs.getArray();
			return authLevel[0];

			if(rs.getArray()) {
				result = true;
			} else {
				result = false;
			}
			rs.close();
			statement.close();
			dbcon.close();
		} catch(Exception e) {
			// asd
		}
		return result;
	}
