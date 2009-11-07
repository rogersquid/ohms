package models.database;

import models.messages.*;

import java.sql.*;
import java.util.Date;

public class Extras {

	public ExtraMessage addExtra(ExtraMessage i_msg){
		ExtraMessage output= i_msg;
		Header iheader=i_msg.return_Header();
		databaseHelper dbcon 	= null;
		try {
			dbcon 				= new databaseHelper(iheader.name_hotel);
			java.util.Date today 	= new java.util.Date();
			java.sql.Date now = new java.sql.Date(today.getTime());
			int returnedRows = dbcon.modify("INSERT INTO extra (orderDate, extraName, extraCost, bookingID) VALUES ('" 
					+ now
					+ i_msg.extraName + "', '" 
					+ i_msg.extraCost + "', '" 
					+ i_msg.bookingID + "')");
			if (returnedRows == 1) {
				output.fill_Header_Response(Header.Response.SUCCESS, "Added one extra Requested.");
			} else {
				output.fill_Header_Response(Header.Response.FAIL, "Adding extra failed.");
			}
			
		} catch (SQLException e) {
			System.err.println("Error SQLException was thrown:");
			e.printStackTrace(System.err);
			output.fill_Header_Response(Header.Response.FAIL, "Failed: SQLException");
		} catch (ClassNotFoundException e) {
			System.err.println("Error ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output.fill_Header_Response(Header.Response.FAIL, "Failed: ClassNotFoundException");
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return output;
	}
	
	public ExtraMessage deleteExtra(ExtraMessage i_msg){
		ExtraMessage output=i_msg;
		Header iheader=i_msg.return_Header();
		databaseHelper dbcon 	= null;
		try {
			dbcon 				= new databaseHelper(iheader.name_hotel);

			int returnedRows = dbcon.modify("DELETE FROM extra WHERE bookingID='" + i_msg.bookingID + "'");
			if (returnedRows == 1) {
				output.fill_Header_Response(Header.Response.SUCCESS, "Delete one extra as Requested.");
			} else {
				output.fill_Header_Response(Header.Response.FAIL, "Deleting extra failed.");
			}			
		} catch (SQLException e) {
			System.err.println("Error SQLException was thrown:");
			e.printStackTrace(System.err);
			output.fill_Header_Response(Header.Response.FAIL, "Failed: SQLException");
		} catch (ClassNotFoundException e) {
			System.err.println("Error ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output.fill_Header_Response(Header.Response.FAIL, "Failed: ClassNotFoundException");
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return output;
	}
	
	public ExtraMessage viewExtra(ExtraMessage i_msg){
		ExtraMessage output=i_msg;
		Header iheader=i_msg.return_Header();
		databaseHelper dbcon 	= null;
		
		try {
			dbcon 				= new databaseHelper(iheader.name_hotel);
					
			ResultSet rs=dbcon.select("Select * FROM extra WHERE bookingID='" + i_msg.bookingID 
					+ " AND extraName='" + i_msg.extraName +"'");
			while (rs.next()) {
				int cbookingID = rs.getInt("bookingID");
				Date corderDate = rs.getDate("orderDate");
				String cextraName = rs.getString("extraName");
				float cextraCost = rs.getFloat("extraCost");
				output.fillAll(corderDate, cbookingID, cextraName, cextraCost);	      
	            }
			output.fill_Header_Response(Header.Response.SUCCESS, "View one extra as Requested.");
		} catch (SQLException e) {
			System.err.println("Error SQLException was thrown:");
			e.printStackTrace(System.err);
			output.fill_Header_Response(Header.Response.FAIL, "view extra failed.");
		} catch (ClassNotFoundException e) {
			System.err.println("Error ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output.fill_Header_Response(Header.Response.FAIL, "view extra failed.");
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return output;
	}

	public ExtraMessage editExtra(ExtraMessage i_msg){		
		ExtraMessage output=i_msg;
		Header iheader=i_msg.return_Header();
		databaseHelper dbcon 	= null;
		try {
			dbcon 				= new databaseHelper(iheader.name_hotel);

			int returnedRows = dbcon.modify("UPDATE extra SET extraName='" + i_msg.extraName
					+ "', extraCost='" + i_msg.extraCost +  "'");
			if (returnedRows == 1) {
				output.fill_Header_Response(Header.Response.SUCCESS, "Edited one extra as Requested.");
			} else {
				output.fill_Header_Response(Header.Response.FAIL, "Editting extra failed.");
			}
		} catch (SQLException e) {
			System.err.println("Error SQLException was thrown:");
			e.printStackTrace(System.err);
			output.fill_Header_Response(Header.Response.FAIL, "Editting extra failed.");
		} catch (ClassNotFoundException e) {
			System.err.println("Error ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output.fill_Header_Response(Header.Response.FAIL, "Editting extra failed.");
		}
		finally {
			if (dbcon != null) {
				dbcon.close();
			}
		}
		return output;
	
	}
	
	public ExtraMessage[] viewAllExtra(ExtraMessage i_msg){
		ExtraMessage[] output= null;
		return output;
	}
	
}
