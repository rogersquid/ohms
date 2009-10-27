package database;

import java.sql.*;

public class database_Helper {
  	
  private static final String jdbcDriver = "com.mysql.jdbc.Driver";
	private static final String dbURL = "jdbc:mysql://localhost/";
    
	private Connection connection; 
  	
  public database_Helper(String dbname) throws SQLException, ClassNotFoundException 
  	{
    	Class.forName(jdbcDriver); //set Java database connectivity driver
    	connection = DriverManager.getConnection(dbURL+dbname, "eece419", "dude");
	}
	
	public ResultSet select(String query)throws SQLException 
	{
	    PreparedStatement st  = connection.prepareStatement(query);
	    return st.executeQuery();
	}
	  
	public int modify(String statement)throws SQLException 
	{
		PreparedStatement st  = connection.prepareStatement(statement);
	    return st.executeUpdate();
	}
  
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
