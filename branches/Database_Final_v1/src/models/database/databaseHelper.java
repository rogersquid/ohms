/*
 * DatabaseHelper.java
 * 
 * This class handles database transactions for all the keeper classes (Account.java, Bill.java, ..)
 * Provides functions for adding, editing, retrieving, an deleting database entries
 */
package models.database;

import java.sql.*;

public class databaseHelper {

  private static final String jdbcDriver = "com.mysql.jdbc.Driver";
	private static final String dbURL = "jdbc:mysql://mayfer.homelinux.com/ohms";
	private static final String username  = "eece419";
    private static final String password  = "dude";

	private Connection connection;

	
	/*
	 * PRE: None
	 * POST: Returns a databaseHelper object
	 * 
	 * The default constructor
	 */
	public databaseHelper() throws SQLException, ClassNotFoundException
  	{
    	Class.forName(jdbcDriver); //set Java database connectivity driver
    	connection = DriverManager.getConnection(dbURL, username, password);
	}
	
	
	/*
	 * PRE: String is not empty and is formatted to mySQL specs
	 * POST: Returns a ResultSet that contains requested database entries else throws exception
	 * 
	 * 	
	 * The function used to retrieve (get/getAll) entries from the database
	 */

	public ResultSet select(String query)throws SQLException
	{
	    PreparedStatement st  = connection.prepareStatement(query);
	    return st.executeQuery();
	}

	// modify() is DEPRECATED. use insert or update instead.
	public int modify(String statement)throws SQLException
	{
		PreparedStatement st  = connection.prepareStatement(statement);
	    return st.executeUpdate();
	}

	
	/*
	 * PRE: String is not empty and is formatted to mySQL specs. Database connection has been opened
	 * POST: The database update entry request is performed else throws exception
	 * 
	 * 	
	 * This function is used to edit and delete database entries
	 */
	public int update(String statement)throws SQLException
	{
		PreparedStatement st  = connection.prepareStatement(statement);
	    return st.executeUpdate();
	}
	
	
	
	/*
	 * PRE: String is not empty and is formatted to mySQL specs. Database connection has been opened
	 * POST: The database insert entry request is performed else throws exception
	 * 
	 * 	
	 * This function is used to add entries to the database
	 */
	public int insert(String query)throws SQLException
	{
		PreparedStatement st  = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    st.executeUpdate();
		ResultSet res = st.getGeneratedKeys();
		if(res.next()) {
			return res.getInt(1);
		} else {
			return 0;
		}
	}
	
	
	
	/*
	 * PRE: Database connection is opened
	 * POST: The database connection is closed
	 * 
	 * 	
	 * This function is used to close the database connection once opened
	 */
	public void close()
	{
		try
	    {
			connection.close();
		}
	    catch (SQLException sqlException)
	    {
			sqlException.printStackTrace();
	    	connection = null;
	    }
	}
	protected void finalize()
	{
		close();
	}
}
