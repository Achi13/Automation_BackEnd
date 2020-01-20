package com.oneaston.configuration.standalone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProcessExtension {
	
	//DATABASE CREDENTIALS
	public String dbUrl =  "jdbc:mysql://localhost/oneqa_db"; //kulang ng port number to
	public String username = "root";
	public String password = "aaaaaa";
	
	
	public void processController(String[] testCaseNumber, String[] description, long[] clientId, String executionSchedule, int executionVersion, long universeId, long userId) throws SQLException {
	
		//insert testCaseNumber in testcase_record
		
		loadDriver();
		Connection con = createConnection();
		List<PreparedStatement> insertStatements = statementToInsertTestCaseNumber(con, testCaseNumber, description, clientId, executionSchedule, executionVersion, universeId, userId);
		executeMultipleRelatedQuery(2, insertStatements);
		
		con.close();
		
	}
	
	public void loadDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver");	
		}catch(Exception e) {/*nothing to do here*/e.printStackTrace();}
	}
	
	public Connection createConnection() {
		//Create Connection to DB	
		Connection con = null;
		
    	try {
			con = DriverManager.getConnection(dbUrl,username,password);
		} catch (SQLException e) {/*nothing to do here*/e.printStackTrace();}
    	
    	return con;
	}

	public List<PreparedStatement> statementToInsertTestCaseNumber(Connection con, String[] testCaseNumber, String[] description, long[] clientId, String executionSchedule, int executionVersion, long universeId, long userId){
		
		List<PreparedStatement> insertStatements = new ArrayList<PreparedStatement>();
		
		for(int i=0; i<testCaseNumber.length;i++) {
			
			String query = "INSERT INTO testcase_record(`client_id`, `testcase_number`, `user_id`, `universe_id`, `description`, `status`, `execution_schedule`, `execution_version`) VALUES (?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = null;
			
			try {
				stmt = con.prepareStatement(query);
				//set parameters
	 			stmt.setLong(1, clientId[i]);
	 			stmt.setString(2, testCaseNumber[i]);
	 			stmt.setLong(3, userId);
	 			stmt.setLong(4, universeId);
	 			stmt.setString(5, description[i]);
	 			stmt.setString(6, "Pending");
	 			stmt.setString(7, executionSchedule);
	 			stmt.setInt(8, executionVersion);
			} catch (SQLException e) {e.printStackTrace();}
			
			insertStatements.add(stmt);
			
		}
		
		return insertStatements;
	}
	
	public List<ResultSet> executeMultipleRelatedQuery(int service, List<PreparedStatement> statements) {
		
		List<ResultSet> rsOutputs = new ArrayList<ResultSet>();
		
		for(int i=0; i<statements.size();i++) {
			rsOutputs.add(executeQuery(service, statements.get(i)));
		}
		
		return rsOutputs;
	}
	
	public ResultSet executeQuery(int service, PreparedStatement stmt) {
		
		ResultSet rs = null;
		
		try {
			
			if(service==1) {
				rs = stmt.executeQuery();
			}else if(service==2) {
				stmt.executeUpdate();
			}
			
		}catch(Exception e) {e.printStackTrace();}
		
		return rs;

}


}