package models.database;

import java.sql.*;

public class databaseHelper {
  	
  private static final String jdbcDriver = "com.mysql.jdbc.Driver";
	private static final String dbURL = "jdbc:mysql://mayfer.homelinux.com/ohms";
	private static final String username  = "eece419";
    private static final String password  = "dude";
	
	private Connection connection; 
  	
    public databaseHelper(String dbname) throws SQLException, ClassNotFoundException 
  	{
    	Class.forName(jdbcDriver); //set Java database connectivity driver
    	connection = DriverManager.getConnection(dbURL, username, password);
	}
	
	public databaseHelper() throws SQLException, ClassNotFoundException 
  	{
    	Class.forName(jdbcDriver); //set Java database connectivity driver
    	connection = DriverManager.getConnection(dbURL, username, password);
	}
	
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
	
	public int update(String statement)throws SQLException 
	{
		PreparedStatement st  = connection.prepareStatement(statement);
	    return st.executeUpdate();
	}	
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
