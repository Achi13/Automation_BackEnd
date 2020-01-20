package com.oneaston.configuration.threads;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CampaignThread implements Runnable {
	
	public String dbUrl =  "jdbc:mysql://localhost/oneqa_db"; //kulang ng port number to
	public String username = "root";
	public String password = "aaaaaa";
	/////////////////////////////////
	public Thread t;
	private String threadName;
	private long campaign_id;
	private String filePathBridgeFolder;
	private String filePathTestCaseHolder;
	
	public CampaignThread(long campaign_id, String filePathBridgeFolder, String filePathTestCaseHolder) {
	  threadName = Long.toString(campaign_id);
	  this.campaign_id = campaign_id;
	  this.filePathBridgeFolder = filePathBridgeFolder;
	  this.filePathTestCaseHolder = filePathTestCaseHolder;
	}
	   
	public void start () {
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
	}
	
	public void run() {
		try {
			System.out.println(threadName + " is processing..." );
			beginCampaignProcess();
			System.out.println(threadName + " exiting.");
		}catch(Exception e){e.printStackTrace();}
		  
	}
	
	public void beginCampaignProcess() throws SQLException{
		List<Long> theme_id = new ArrayList<Long>();
		PreparedStatement stmt;
		ResultSet rs;
		
		//initiate mysql driver
		loadDriver();
		//initiate driver connection
		Connection con = createConnection();
		//generate prepared statement to query db
		stmt = statementToQueryThemeId(con);
		//execute prepared statement
		rs = executeQuery(1,stmt);
		//get data from result set
		theme_id = getDataFromResultSet(rs);
		//initiate theme thread
		List<Thread> threadList = initiateThemeThread(theme_id);
		//monitor threadList
		monitorThreadList(threadList);
		
		//requery db to get status and update table
		
		//generate prepared statement to re-query db for theme status
		stmt = statementToQueryThemeId(con);
		//execute prepared statement
		rs = executeQuery(1,stmt);
		//get data from result set
		String status = getDataFromResultSetForCheckingStatus(rs);
		//generate prepared statement to update db
		stmt = statementToUpdateCampaignIdStatus(con, status);
		//execute prepared statement
		executeQuery(2,stmt);
		
		con.close();
		stmt.close();
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

	public PreparedStatement statementToQueryThemeId(Connection con) {
		
		String query = "SELECT * FROM theme WHERE `campaign_id` = ?";
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			//set parameters
 			stmt.setLong(1, campaign_id);
		} catch (SQLException e) {e.printStackTrace();}
		
		return stmt;
	}

	public PreparedStatement statementToUpdateCampaignIdStatus(Connection con, String status) {
		
		//String query = "UPDATE ? SET ? = ? WHERE ? = ?";
		String query = "UPDATE campaign SET `status` = ? WHERE `campaign_id` = ?";
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			//set parameters
 			stmt.setString(1, status);
 			stmt.setLong(2, campaign_id);
		} catch (SQLException e) {e.printStackTrace();}
		
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

	public List<Long> getDataFromResultSet(ResultSet rs) throws SQLException {
		
		List<Long> theme_id = new ArrayList<Long>();
		
		while(rs.next()) {
			theme_id.add(rs.getLong("theme_id"));
		}
		
		return theme_id;
	}
	
	public String getDataFromResultSetForCheckingStatus(ResultSet rs) throws SQLException {
		
		String status = "Passed";
		
		while(rs.next()) {
			if(rs.getString("status").equalsIgnoreCase("Failed")) {
				status = "Failed";
				return status;
			}
		}
		
		return status;
	}

	public List<Thread> initiateThemeThread(List<Long> theme_id) {
		
		List<Thread> threadList = new ArrayList<Thread>();
		
		for(int i=0;i<theme_id.size();i++) {
			//create name for new thread
			String themeThreadName = campaign_id+"_theme_"+i;
			//execute thread
			ThemeThread themeJr = new ThemeThread(themeThreadName, theme_id.get(i), filePathBridgeFolder, filePathTestCaseHolder);
			themeJr.start();
			threadList.add(themeJr.t);
		}
		
		return threadList;
	}

	public void monitorThreadList(List<Thread> threadList) {
		
		boolean isListEmpty = false;
		
		while(isListEmpty==false) {
			sleep(10);
			for(int i=0; i<threadList.size(); i++) {
				if(!threadList.get(i).isAlive()) {
					threadList.remove(i);
				}
			}
			
			if(threadList.size()==0) {
				isListEmpty = true;
			}
		}
	}
	
	public void sleep(int seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
