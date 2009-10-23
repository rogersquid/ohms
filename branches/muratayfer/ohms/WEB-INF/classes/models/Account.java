package models;

import java.io.*;
import java.util.*;
import java.sql.*;
import java.security.*;
import models.misc.*;

public class Account {
	public boolean accountIsValid(String username, String password) {
		String md5_password = MD5.hashString(password);
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection dbcon = DriverManager.getConnection("jdbc:mysql://localhost/eece419", "eece419", "dude");
			Statement statement = dbcon.createStatement();
			String query = "SELECT * FROM users WHERE username='" + username + "' AND password='" + md5_password + "'";
			ResultSet rs = statement.executeQuery(query);

			if(rs.next()) {
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

	public static String test() {
		String output = "TESTING: ";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection dbcon = DriverManager.getConnection("jdbc:mysql://localhost/eece419", "eece419", "dude");
			Statement statement = dbcon.createStatement();
			String query = "SELECT * FROM users";
			ResultSet rs = statement.executeQuery(query);

			while(rs.next()) {
				output += rs.getString("username");
			}
			rs.close();
			statement.close();
			dbcon.close();
		} catch(Exception e) {
			output += "(exception) ";
			output += e.toString();
		}
		return output;
	}

}