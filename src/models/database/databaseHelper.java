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

	public databaseHelper() throws SQLException, ClassNotFoundException {
		/*
		 * OVERVIEW: The default constructor
		 * PRECONDITIONS: None
		 * POSTCONDITIONS: Returns a databaseHelper object
		 */
    	Class.forName(jdbcDriver); //set Java database connectivity driver
    	connection = DriverManager.getConnection(dbURL, username, password);
	}

	public ResultSet select(String query)throws SQLException {
		/*
		 * OVERVIEW: The function used to retrieve (get/getAll) entries from the database
		 * PRECONDITIONS: String is not empty and is formatted to mySQL specifications
		 * POSTCONDITIONS: Returns a ResultSet that contains requested database entries else throws exception
		 */
	    PreparedStatement st  = connection.prepareStatement(query);
	    return st.executeQuery();
	}

	// modify() is DEPRECATED. use insert or update instead.
	public int modify(String statement)throws SQLException
	{
		PreparedStatement st  = connection.prepareStatement(statement);
	    return st.executeUpdate();
	}

	public int update(String statement)throws SQLException {
		/*
		 * OVERVIEW: This function is used to edit and delete database entries
		 * PRECONDITIONS: String is not empty and is formatted to mySQL specifications. Database connection has been opened
		 * POSTCONDITIONS: The database update entry request is performed else throws exception
		 */
		PreparedStatement st  = connection.prepareStatement(statement);
	    return st.executeUpdate();
	}
	
	public int insert(String query)throws SQLException {
		/*
		 * OVERVIEW: This function is used to add entries to the database
		 * PRECONDITIONS: String is not empty and is formatted to mySQL specifications. Database connection has been opened
		 * POSTCONDITIONS: The database insert entry request is performed else throws exception
		 */
		PreparedStatement st  = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    st.executeUpdate();
		ResultSet res = st.getGeneratedKeys();
		if(res.next()) {
			return res.getInt(1);
		} else {
			return 0;
		}
	}
	
	public void close() {
		/*
		 * OVERVIEW: This function is used to close the database connection once opened
		 * PRECONDITIONS: Database connection is opened
		 * POSTCONDITIONS: The database connection is closed
		 */
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
