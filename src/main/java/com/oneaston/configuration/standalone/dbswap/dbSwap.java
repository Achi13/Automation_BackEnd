package com.oneaston.configuration.standalone.dbswap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class dbSwap {
	
	public void rowSwap(String testCaseNumber1, String testCaseNumber2, String testCaseNumber3, long storyId){
		
		//testcasenumber1 is row to be dragged
		//testcasenumber2 is upper row
		//testcasenumber3 is bottom row
	
		//assume testCaseNumber1 is the number to be dragged
		dbConnect dbConnectJr = new dbConnect();
		
		//create database connection
		dbConnectJr.loadDriver();
		Connection con = dbConnectJr.createConnection();
		
		ResultSet rs = null;
		long priorityVal2 = 0;
		long priorityVal1 = 0;
		long priorityVal3 = 0;
		
		rs = dbConnectJr.dataBaseController(con, 1, testCaseNumber1, 0, 0, 0);
		//get contents from query result of testcase1
		priorityVal1 = getContentFromDependentTestcaseResultSet(rs);
		
		
		
		if(!testCaseNumber2.equals("0")) {
			
			rs = dbConnectJr.dataBaseController(con, 1, testCaseNumber2, 0, 0,0);
			//get contents from query result of testcase2
			priorityVal2 = getContentFromDependentTestcaseResultSet(rs);
			
			rs = dbConnectJr.dataBaseController(con, 1, testCaseNumber3, 0, 0,0);
			//get contents from query result of testcase2
			priorityVal3 = getContentFromDependentTestcaseResultSet(rs);
			
			if(priorityVal1<priorityVal2) {
				//if dragged down
				caseDragDropDown(con, dbConnectJr, rs, priorityVal2, testCaseNumber1, testCaseNumber2, storyId, priorityVal1);
			}else{
				//if dragged up
				caseDragDropUp(con, dbConnectJr, rs, priorityVal3, testCaseNumber1, testCaseNumber3, storyId);
			}
			
		}else {
			//if dragged to first row
			System.out.println("darmanitan");
			caseRowIsOne(con, dbConnectJr, testCaseNumber1, storyId);
		}
		
		try {
			con.close();
		} catch (SQLException e) {System.out.println("Database connection failed to close in dbSwap.java");}
		
	}
	
	public long getContentFromDependentTestcaseResultSet(ResultSet rs) {
		
		//LinkedHashMap<String, Object> rsContent = new LinkedHashMap<String, Object>();
		long priorityVal = 0;
		
		try {
			rs.absolute(1);
			priorityVal = rs.getLong("priority");
			
		} catch (SQLException e) {/*nothing to do here*/e.printStackTrace();}
		
		return priorityVal;
	}
	
	public void caseDragDropDown(Connection con, dbConnect dbConnectJr, ResultSet rs, long priorityVal2, String testCaseNumber1, String testCaseNumber2, long storyId, long priorityVal1) {
		
		//move priority values
		dbConnectJr.dataBaseController(con, 3, null, priorityVal2, storyId, priorityVal1);
		
		//swap contents of rsContent1 with rsContent2
		dbConnectJr.dataBaseController(con, 2, testCaseNumber1, priorityVal2, 0, 0);
		
	}
	
	public void caseDragDropUp(Connection con, dbConnect dbConnectJr, ResultSet rs, long priorityVal2, String testCaseNumber1, String testCaseNumber2, long storyId) {
		
		//move priority values
		dbConnectJr.dataBaseController(con, 5, null, priorityVal2, storyId, 0);
		
		//swap contents of rsContent1 with rsContent2
		dbConnectJr.dataBaseController(con, 2, testCaseNumber1, priorityVal2, 0, 0);
	}

	public void caseRowIsOne(Connection con, dbConnect dbConnectJr, String testCaseNumber1, long storyId) {
		
		//increase all priority values
		dbConnectJr.dataBaseController(con, 4, null, 0, storyId, 0);
		
		//insert 1 priority to testCase
		dbConnectJr.dataBaseController(con, 2, testCaseNumber1, 1, 0, 0);
		
	}
	

}
