class Account{
	private int c_Account_ID;
	private String c_F_name;
	private String c_S_name;
	private boolean c_Gender;
	private String c_Phone;
	private String c_Email;
	private String c_Address;
	private Date c_Date;
	
	// Fetch Variable data from the database
	// If user does not exist, return false
	public boolean Fetch_All(String i_email, String i_pwd)
	{
		
	}
	
	// Creates a unique ID, inserts today's date
	// Returns true if successful, false if error
	public boolean Add_Account(String i_F_name,
			String i_S_name, boolean i_Gender,
			String i_Phone, String c_Email,
			String c_Address)
	{
		
	}
	
	// Deletes the account selected by the email
	// Returns true if successful
	public boolean Delete_Account(String i_email)
	{
		
	}
	
	// Deletes the account selected by the account ID
	// Returns true if successful
	public boolean Delete_Account(int i_Account_ID)
	{
		
	}
	
	// Fills the AccountMessage structure obj
	public void Fill_Message(AccountMessage obj)
	{
		
	}
}