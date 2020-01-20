package com.oneaston.configuration.standalone.dbswap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class dbConnect {
	
	public String dbUrl =  "jdbc:mysql://localhost/oneqa_db"; //kulang ng port number to
	public String username = "root";
	public String password = "aaaaaa";
	
	//query's parameters
	private String testCaseNumber;
	private int serviceType;
	private long priorityVal;
	private long storyId;
	private long priorityValDragged;
	
	public ResultSet dataBaseController(Connection con, int serviceType, String testCaseNumber, long priorityVal, long storyId, long priorityValDragged) {
		
		//set up values
		setQueryParameterValues(serviceType, testCaseNumber, priorityVal, storyId, priorityValDragged);

		//generate a statement depending on service type
		ResultSet rs = statamentGenerationExecutionController(con);
		
		try{
			//con.close();
		}catch(Exception e) {/*nothing to do here8*/}
		
		return rs;
	}
	
	public void setQueryParameterValues(int serviceType, String testCaseNumber, long priorityVal, long storyId, long priorityValDragged){
		this.serviceType = serviceType;
		this.testCaseNumber = testCaseNumber;
		this.priorityVal = priorityVal;
		this.storyId = storyId;
		this.priorityValDragged = priorityValDragged;
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
	
	public ResultSet statamentGenerationExecutionController(Connection con) {
		
		ResultSet rs = null;
		PreparedStatement stmt = null;
		
		if(serviceType==1) {
			stmt = statementToQueryFromDependentTestcaseUsingTestCaseNumber(con);
			rs = executeQuery(1, stmt);
		}
		
		else if(serviceType==2) {
			stmt = statementToUpdateDependentTestCaseUsingTestCaseNumber(con);
			executeQuery(2, stmt);
		}
		
		else if(serviceType==3) {
			stmt = statementToUpdateDependentTestCasePriorityValuesForDragDown(con);
			executeQuery(2, stmt);
		}
		
		else if(serviceType==4) {
			stmt = statementToUpdateIncreaseAllDependentTestCasePriorityValues(con);
			executeQuery(2, stmt);
		}
		
		else if(serviceType==5) {
			stmt = statementToUpdateDependentTestCasePriorityValuesForDragUp(con);
			executeQuery(2, stmt);
		}
		
		return rs;
	}
	
	public PreparedStatement statementToQueryFromDependentTestcaseUsingTestCaseNumber(Connection con) {
		
		String query = "SELECT * FROM dependent_testcase WHERE `testcase_number` = ?";
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			//set parameters
 			stmt.setString(1, testCaseNumber);
		} catch (SQLException e) {/*nothing to do here*/e.printStackTrace();}
		
		return stmt;
		
	}
	
	public PreparedStatement statementToUpdateDependentTestCasePriorityValuesForDragDown(Connection con) {
		
		String query = "UPDATE dependent_testcase SET `priority` = `priority`-1 WHERE `priority`<= ? and `priority` > ? and `story_id` = ?";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			//set parameters
			stmt.setLong(1, priorityVal);
			stmt.setLong(2, priorityValDragged);
			stmt.setLong(3, storyId);
 			
		} catch (SQLException e) {/*nothing to do here*/e.printStackTrace();}
		
		return stmt;
	}
	
	public PreparedStatement statementToUpdateDependentTestCasePriorityValuesForDragUp(Connection con) {
		
		String query = "UPDATE dependent_testcase SET `priority` = `priority`+1 WHERE `priority` >= ? and `story_id` = ?";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			//set parameters
			stmt.setLong(1, priorityVal);
			stmt.setLong(2, storyId);
 			
		} catch (SQLException e) {/*nothing to do here*/e.printStackTrace();}
		
		return stmt;
	}

	public PreparedStatement statementToUpdateDependentTestCaseUsingTestCaseNumber(Connection con) {
		
		String query = "UPDATE dependent_testcase SET `priority` = ? WHERE `testcase_number` = ?";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			//set parameters
			stmt.setLong(1, priorityVal);
			stmt.setString(2, testCaseNumber);
 			
		} catch (SQLException e) {/*nothing to do here*/e.printStackTrace();}
		
		return stmt;
		
	}
	
	public PreparedStatement statementToUpdateIncreaseAllDependentTestCasePriorityValues(Connection con) {
		
		System.out.println("pikachu");
		String query = "UPDATE dependent_testcase SET `priority` = `priority`+1 where `story_id` = ?";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			//set query parameters
			stmt.setLong(1, storyId);
		} catch (SQLException e) {/*nothing to do here*/e.printStackTrace();}
		
		return stmt;
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
