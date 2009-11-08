package models.database;

import models.messages.*;

import java.sql.*;
import java.util.Date;

public class Extras {

	public ExtraMessage addExtra(ExtraMessage i_msg){
		ExtraMessage output= i_msg;
		Header iheader=i_msg.returnHeader();
		databaseHelper dbcon 	= null;
		try {
			dbcon 				= new databaseHelper(iheader.nameHotel);
			java.util.Date today 	= new java.util.Date();
			java.sql.Date now = new java.sql.Date(today.getTime());
			int returnedRows = dbcon.modify("INSERT INTO test_extras (extraID, bookingID, description, price, orderDate) VALUES ('" 
					+ i_msg.extraID + "', '"  
					+ i_msg.bookingID + "', '" 
					+ i_msg.extraName + "', '" 
					+ i_msg.extraCost + "', '" 
					+ now + "')");
			if (returnedRows > 0) {
				System.out.println("Success");
				output.fillHeaderResponse(Header.Response.SUCCESS, "Added one extra as Requested.");
				output.extraID=returnedRows;
			} else {
				System.out.println("Failure");
				output.fillHeaderResponse(Header.Response.FAIL, "Adding extra failed.");
			}
			
		} catch (SQLException e) {
			System.err.println("Error SQLException was thrown:");
			e.printStackTrace(System.err);
			output.fillHeaderResponse(Header.Response.FAIL, "Failed: SQLException");
		} catch (ClassNotFoundException e) {
			System.err.println("Error ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output.fillHeaderResponse(Header.Response.FAIL, "Failed: ClassNotFoundException");
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
		Header iheader=i_msg.returnHeader();
		databaseHelper dbcon 	= null;
		try {
			dbcon 				= new databaseHelper(iheader.nameHotel);

			int returnedRows = dbcon.modify("DELETE FROM test_extras WHERE extraID='" + i_msg.extraID + "'");
			if (returnedRows == 1) {
				output.fillHeaderResponse(Header.Response.SUCCESS, "Delete one extra as Requested.");
			} else {
				output.fillHeaderResponse(Header.Response.FAIL, "Deleting extra failed.");
			}			
		} catch (SQLException e) {
			System.err.println("Error SQLException was thrown:");
			e.printStackTrace(System.err);
			output.fillHeaderResponse(Header.Response.FAIL, "Failed: SQLException");
		} catch (ClassNotFoundException e) {
			System.err.println("Error ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output.fillHeaderResponse(Header.Response.FAIL, "Failed: ClassNotFoundException");
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
		Header iheader=i_msg.returnHeader();
		databaseHelper dbcon 	= null;
		
		try {
			dbcon 				= new databaseHelper(iheader.nameHotel);
					
			ResultSet rs=dbcon.select("Select * FROM test_extras WHERE extraID='" + i_msg.extraID 
					+"'");
			while (rs.next()) {
				int cextraID = rs.getInt("extraID");
				int cbookingID = rs.getInt("bookingID");
				Date corderDate = rs.getDate("orderDate");
				String cextraName = rs.getString("description");
				float cextraCost = rs.getFloat("price");
				output.fillAll(cextraID, cbookingID, cextraName, cextraCost, corderDate);      
	            }
			output.fillHeaderResponse(Header.Response.SUCCESS, "View one extra as Requested.");
		} catch (SQLException e) {
			System.err.println("Error SQLException was thrown:");
			e.printStackTrace(System.err);
			output.fillHeaderResponse(Header.Response.FAIL, "view extra failed.");
		} catch (ClassNotFoundException e) {
			System.err.println("Error ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output.fillHeaderResponse(Header.Response.FAIL, "view extra failed.");
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
		Header iheader=i_msg.returnHeader();
		databaseHelper dbcon 	= null;
		try {
			dbcon 				= new databaseHelper(iheader.nameHotel);

			int returnedRows = dbcon.modify("UPDATE test_extras SET description='" + i_msg.extraName
					+ "', price='" + i_msg.extraCost +  "'" + "WHERE extraID='" + i_msg.extraID
					+ "'");
			if (returnedRows == 1) {
				output.fillHeaderResponse(Header.Response.SUCCESS, "Edited one extra as Requested.");
			} else {
				output.fillHeaderResponse(Header.Response.FAIL, "Editting extra failed.");
			}
		} catch (SQLException e) {
			System.err.println("Error SQLException was thrown:");
			e.printStackTrace(System.err);
			output.fillHeaderResponse(Header.Response.FAIL, "Editting extra failed.");
		} catch (ClassNotFoundException e) {
			System.err.println("Error ClassNotFoundException was thrown:");
			e.printStackTrace(System.err);
			output.fillHeaderResponse(Header.Response.FAIL, "Editting extra failed.");
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
			Header iheader=i_msg.returnHeader();
			databaseHelper dbcon 	= null;
			// for customer
			try {
				dbcon 				= new databaseHelper(iheader.nameHotel);
				// booking id or owner + sDate
				ResultSet rs=dbcon.select("Select count(*) FROM " + iheader.nameHotel + 
										"_extra WHERE extraID='" + i_msg.extraID +"'");
				int numberofrows=rs.getInt(1);
				if(numberofrows<1){
					output[0].fillHeaderResponse(Header.Response.FAIL, "viewAll Booking failed." +
							" extraID: " + i_msg.extraID);
					return output;
				}
				rs=dbcon.select("Select * FROM extra WHERE extraID='" + i_msg.extraID + "'");
				int i=0;
				while (rs.next()) {
					output[i]=new ExtraMessage(iheader.messageOwnerID, iheader.authLevel, iheader.nameHotel, iheader.action);
					output[i].bookingID=rs.getInt("bookingID");
					output[i].extraName=rs.getString("extraName");
					output[i].extraCost=rs.getFloat("extraCost");
					output[i].orderDate= rs.getDate("orderDate");
					
					output[i].fillHeaderResponse(Header.Response.SUCCESS, "ViewAll one extra as Requested.");
					i++;
		        }
			} catch (SQLException e) {
				System.err.println("Error in 'Add_Account'.  SQLException was thrown:");
				e.printStackTrace(System.err);
				output[0].fillHeaderResponse(Header.Response.FAIL, "view extra failed.");
			} catch (ClassNotFoundException e) {
				System.err.println("Error in 'Add_Account'.  ClassNotFoundException was thrown:");
				e.printStackTrace(System.err);
				output[0].fillHeaderResponse(Header.Response.FAIL, "view extra failed.");
			}
			finally {
				if (dbcon != null) {
					dbcon.close();
				}
			}
			return output;
	}
	
	
}
