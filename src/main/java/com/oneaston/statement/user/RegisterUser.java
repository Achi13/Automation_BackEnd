package com.oneaston.statement.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterUser {
	
	public String dbUrl =  "jdbc:mysql://localhost/oneqa_db"; //kulang ng port number to
	public String username = "root";
	public String password = "aaaaaa";
	
	//query's parameters
	private String accessibleUniverse;
	private int serviceType;
	private String accountUserName;
	private String accountPassword;
	private String userType;
	private String status;
	private long userId;
	
	public ResultSet dataBaseController(int serviceType, String accesibleUniverse, String accountUserName, String accountPassword, String userType, String status, long userId) {
		
		//set up values
		setQueryParameterValues(serviceType, accesibleUniverse, accountUserName, accountPassword, userType, status, userId);
		//set up database
		loadDriver();
		Connection con = createConnection();
		//generate a statement depending on service type
		ResultSet rs = statamentGenerationExecutionController(con);
		
		try{
			//con.close();
		}catch(Exception e) {/*nothing to do here8*/}
		
		return rs;
	}
	
	public void setQueryParameterValues(int serviceType, String accessibleUniverse, String accountUserName, String accountPassword, String userType, String status, long userId) {
		this.serviceType = serviceType;
		this.accessibleUniverse = accessibleUniverse;
		this.accountUserName = accountUserName;
		this.accountPassword = accountPassword;
		this.userType = userType;
		this.status = status;
		this.userId = userId;
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
		/*
		 * 1: insert new user
		 * 2: insert (perRowData into table)
		 * 3: insert (footer of a testcase into table)
		 */
		ResultSet rs = null;
		PreparedStatement stmt = null;
		
		if(serviceType==1) {
			stmt = statementToInsertNewUser(con);
			executeQuery(2, stmt);
		}
		else if(serviceType==2) {
			stmt = statementToUpdateUser(con);
			executeQuery(2, stmt);
		}
		
		return rs;
	}
	
	public PreparedStatement statementToInsertNewUser(Connection con) {
		
		String query = "INSERT INTO user(universe_access_list, username, password, user_type, status) VALUES (?,?,?,?,?)";
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			//set parameters
 			stmt.setString(1, accessibleUniverse);
 			stmt.setString(2, accountUserName);
 			stmt.setString(3, accountPassword);
 			stmt.setString(4, userType);
 			stmt.setString(5, status);
		} catch (SQLException e) {/*nothing to do here*/e.printStackTrace();}
		
		return stmt;
		
	}
	
	public PreparedStatement statementToUpdateUser(Connection con) {
		
		String query = "UPDATE user SET `universe_access_list` = ?, `password` = ?, `user_type` = ?, `status` = ? WHERE `user_id` = ?";
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			//set parameters
 			stmt.setString(1, accessibleUniverse);
 			stmt.setString(2, accountPassword);
 			stmt.setString(3, userType);
 			stmt.setString(4, status);
 			stmt.setLong(5, userId);
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
