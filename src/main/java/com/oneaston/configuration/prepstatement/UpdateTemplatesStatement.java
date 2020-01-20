package com.oneaston.configuration.prepstatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class UpdateTemplatesStatement {
	
	public String dbUrl =  "jdbc:mysql://localhost/oneqa_db"; //kulang ng port number to
	public String username = "root";
	public String password = "aaaaaa";
	
	//query's parameters
	
	private int serviceType;
	String label;
	String natureOfAction;
	String webElementName;
	String webElementNature;
	boolean isScreenCapture;
	boolean isTriggerEnter;
	long priority;
	long templateDataId;
	LinkedHashMap<String, Object> campaign;
	LinkedHashMap<String, Object> theme;
	LinkedHashMap<String, Object> story;
	LinkedHashMap<String, Object> dependentTestcase;
	LinkedHashMap<String, Object> dependentTestcaseIoValue;
	LinkedHashMap<String, Object> testcaseRecord;
	LinkedHashMap<String, Object> footerData;
	LinkedHashMap<String, Object> actualData;
	

	
	public ResultSet dataBaseController(int serviceType, String label, String natureOfAction, boolean isScreenCapture, boolean isTriggerEnter, String webElementName, String webElementNature, long templateDataId, LinkedHashMap<String, Object> campaign, 
			LinkedHashMap<String, Object> theme, LinkedHashMap<String, Object> story, LinkedHashMap<String, Object> dependentTestcase, LinkedHashMap<String, Object> dependentTestcaseIoValue, LinkedHashMap<String, Object> testcaseRecord,
			LinkedHashMap<String, Object> footerData, LinkedHashMap<String, Object> actualData, long priority) {
		
		//set up values
		setQueryParameterValues(serviceType, label, natureOfAction, isScreenCapture, isTriggerEnter, webElementName, webElementNature, templateDataId, campaign, theme, story, dependentTestcase, dependentTestcaseIoValue, testcaseRecord, footerData, actualData, priority);
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
	
	public void setQueryParameterValues(int serviceType, String label, String natureOfAction, boolean isScreenCapture, boolean isTriggerEnter, String webElementName, String webElementNature, long templateDataId,
			LinkedHashMap<String, Object> campaign, LinkedHashMap<String, Object> theme, LinkedHashMap<String, Object> story, LinkedHashMap<String, Object> dependentTestcase, LinkedHashMap<String, Object> dependentTestcaseIoValue,
			LinkedHashMap<String, Object> testcaseRecord, LinkedHashMap<String, Object> footerData, LinkedHashMap<String, Object> actualData, long priority) {
		this.serviceType = serviceType;
		this.label = label;
		this.natureOfAction = natureOfAction;
		this.isScreenCapture = isScreenCapture;
		this.isTriggerEnter = isTriggerEnter;
		this.webElementName = webElementName;
		this.webElementNature = webElementNature;
		this.templateDataId = templateDataId;
		this.campaign = campaign;
		this.theme = theme;
		this.story = story;
		this.dependentTestcase = dependentTestcase;
		this.dependentTestcaseIoValue = dependentTestcaseIoValue;
		this.testcaseRecord = testcaseRecord;
		this.footerData = footerData;
		this.actualData = actualData;
		this.priority = priority;
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
			stmt = statementToUpdateTemplateData(con);
			executeQuery(2, stmt);
		}else if(serviceType==2) {
			stmt = statementToInsertCampaignData(con);
			executeQuery(2, stmt);
		}else if(serviceType==3) {
			stmt = statementToInsertThemeData(con);
			executeQuery(2, stmt);
		}else if(serviceType==4) {
			stmt = statementToInsertStoryData(con);
			executeQuery(2, stmt);
		}else if(serviceType==5) {
			stmt = statementToInsertDependentTestcase(con);
			executeQuery(2, stmt);
		}else if(serviceType==6) {
			stmt = statementToInsertDependentTestcaseIoValue(con);
			executeQuery(2, stmt);
		}else if(serviceType==7) {
			stmt = statementToInsertTestCaseRecord(con);
			executeQuery(2, stmt);
		}else if(serviceType==8) {
			stmt = statementToInsertFooterData(con);
			executeQuery(2, stmt);
		}else if(serviceType==9) {
			stmt = statementToInsertActualData(con);
			executeQuery(2, stmt);
		}
		
		return rs;
	}

	
	public PreparedStatement statementToUpdateTemplateData(Connection con) {
		
		String query = "UPDATE `template_data` SET `label` = ?, `nature_of_action` = ?, `is_screen_capture` = ?, "
				+ "`is_trigger_enter` = ?, `web_element_name` = ?, `web_element_nature` = ? "
				+ "WHERE `template_data_id` = ?";
	
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			//set parameters
			stmt.setString(1, label);
			stmt.setString(2, natureOfAction);
			stmt.setBoolean(3, isScreenCapture);
			stmt.setBoolean(4, isTriggerEnter);
			stmt.setString(5, webElementName);
			stmt.setString(6, webElementNature);
			stmt.setLong(7, templateDataId);
 			
		} catch (SQLException e) {/*nothing to do here*/e.printStackTrace();}
		
		return stmt;
		
		
	}
	
	public PreparedStatement statementToInsertCampaignData(Connection con) {
		
		String query = "INSERT INTO campaign(`campaign_id`, `user_id`, `universe_id`, `campaign_name`, "
				+ "`description`, `status`, `timestamp`, `execution_version_current`, `execution_schedule`) VALUES (?,?,?,?,?,?,?,?,?) ";
	
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			//set parameters
			stmt.setLong(1, (long) campaign.get("campaign_id"));
			stmt.setLong(2, (long) campaign.get("user_id"));
			stmt.setLong(3, (long) campaign.get("universe_id"));
			stmt.setString(4, (String) campaign.get("campaign_name"));
			stmt.setString(5, (String) campaign.get("description"));
			stmt.setString(6, (String) campaign.get("status"));
			stmt.setString(7, (String) campaign.get("timestamp"));
			stmt.setInt(8, (int) campaign.get("execution_version_current"));
			stmt.setString(9, " ");
 			
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
	
	public PreparedStatement statementToInsertThemeData(Connection con) {
		
		String query = "INSERT INTO theme(`theme_id`, `campaign_id`, `user_id`, `universe_id`, "
				+ "`theme_name`, `description`, `status`, `timestamp`, `execution_version_start`, `execution_version_current`) VALUES (?,?,?,?,?,?,?,?,?,?) ";
	
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			//set parameters
			stmt.setLong(1, (long) theme.get("theme_id"));
			stmt.setLong(2, (long) theme.get("campaign_id"));
			stmt.setLong(3, (long) theme.get("user_id"));
			stmt.setLong(4, (long) theme.get("universe_id"));
			stmt.setString(5, (String) theme.get("theme_name"));
			stmt.setString(6, (String) theme.get("description"));
			stmt.setString(7, (String) theme.get("status"));
			stmt.setString(8, (String) theme.get("timestamp"));
			stmt.setInt(9, (int) theme.get("execution_version_start"));
			stmt.setInt(10, (int) theme.get("execution_version_current"));
 			
		} catch (SQLException e) {/*nothing to do here*/e.printStackTrace();}
		
		return stmt;
	}
	
	public PreparedStatement statementToInsertStoryData(Connection con) {
		
		String query = "INSERT INTO story(`story_id`, `theme_id`, `user_id`, `universe_id`, "
				+ "`story_name`, `description`, `status`, `timestamp`, `is_ignore_severity`, `is_server_import`, `execution_version_start`, `execution_version_current`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";
	
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			//set parameters
			stmt.setLong(1, (long) story.get("story_id"));
			stmt.setLong(2, (long) story.get("theme_id"));
			stmt.setLong(3, (long) story.get("user_id"));
			stmt.setLong(4, (long) story.get("universe_id"));
			stmt.setString(5, (String) story.get("story_name"));
			stmt.setString(6, (String) story.get("description"));
			stmt.setString(7, (String) story.get("status"));
			stmt.setString(8, (String) story.get("timestamp"));
			stmt.setBoolean(9, (boolean) story.get("is_ignore_severity"));
			stmt.setBoolean(10, (boolean) story.get("is_server_import"));
			stmt.setInt(11, (int) story.get("execution_version_start"));
			stmt.setInt(12, (int) story.get("execution_version_current"));
 			
		} catch (SQLException e) {/*nothing to do here*/e.printStackTrace();}
		
		return stmt;
	}
	
	public PreparedStatement statementToInsertDependentTestcase(Connection con) {
		
		String query = "INSERT INTO dependent_testcase(`testcase_number`, `story_id`, `user_id`, `template_id`, `universe_id`, "
				+ "`web_address_id`, `client_id`, `login_account_id`, `description`, `status`, `template_version`, `execution_version_start`, `execution_version_current`, `priority`, `tap_import_status`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
	
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			//set parameters
			stmt.setString(1, (String) dependentTestcase.get("testcase_number"));
			stmt.setLong(2, (long) dependentTestcase.get("story_id"));
			stmt.setLong(3, (long) dependentTestcase.get("user_id"));
			stmt.setLong(4, (long) dependentTestcase.get("template_id"));
			stmt.setLong(5, (long) dependentTestcase.get("universe_id"));
			stmt.setLong(6, (long) dependentTestcase.get("web_address_id"));
			stmt.setLong(7, (long) dependentTestcase.get("client_id"));
			stmt.setLong(8, (long) dependentTestcase.get("login_account_id"));
			stmt.setString(9, (String) dependentTestcase.get("description"));
			stmt.setString(10, (String) dependentTestcase.get("status"));
			stmt.setInt(11, (int) dependentTestcase.get("template_version"));
			stmt.setInt(12, (int) dependentTestcase.get("execution_version_start"));
			stmt.setInt(13, (int) dependentTestcase.get("execution_version_current"));
			stmt.setLong(14, priority);
			stmt.setInt(15, 0);
			
 			
		} catch (SQLException e) {/*nothing to do here*/e.printStackTrace();}
		
		return stmt;
	}
	
	public PreparedStatement statementToInsertDependentTestcaseIoValue(Connection con) {
		
		String query = "INSERT INTO dependent_testcase_io_value(`io_id`, `testcase_number`, `template_data_id`, `io_value`) VALUES (?,?,?,?) ";
	
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			//set parameters
			stmt.setLong(1, (long) dependentTestcaseIoValue.get("io_id"));
			stmt.setString(2, (String) dependentTestcaseIoValue.get("testcase_number"));
			stmt.setLong(3, (long) dependentTestcaseIoValue.get("template_data_id"));
			stmt.setString(4, (String) dependentTestcaseIoValue.get("io_value"));
 			
		} catch (SQLException e) {/*nothing to do here*/e.printStackTrace();}
		
		return stmt;
	}
	
	public PreparedStatement statementToInsertTestCaseRecord(Connection con) {
		
		String query = "INSERT INTO testcase_record(`record_id`, `client_id`, `testcase_number`, `user_id`, `universe_id`, "
				+ "`client_name`, `description`, `status`, `execution_schedule`, `execution_version`) VALUES (?,?,?,?,?,?,?,?,?,?) ";
	
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			//set parameters
			stmt.setLong(1, (long) testcaseRecord.get("record_id"));
			stmt.setLong(2, (long) testcaseRecord.get("client_id"));
			stmt.setString(3, (String) testcaseRecord.get("testcase_number"));
			stmt.setLong(4, (long) testcaseRecord.get("user_id"));
			stmt.setLong(5, (long) testcaseRecord.get("universe_id"));
			stmt.setString(6, (String) testcaseRecord.get("client_name"));
			stmt.setString(7, (String) testcaseRecord.get("description"));
			stmt.setString(8, (String) testcaseRecord.get("status"));
			stmt.setString(9, (String) testcaseRecord.get("execution_schedule"));
			stmt.setInt(10, (int) testcaseRecord.get("execution_version"));
 			
		} catch (SQLException e) {/*nothing to do here*/e.printStackTrace();}
		
		return stmt;
	}
	
	public PreparedStatement statementToInsertFooterData(Connection con) {
		
		String query = "INSERT INTO testcase_footer_data(`footer_data_id`, `testcase_number`, `assigned_account`,  `client_name`, `is_ignore_severity`, "
				+ "`sender`, `is_server_import`, `testcase_status`, `transaction_type`, `url`, `execution_version`) VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
	
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			//set parameters
			stmt.setLong(1, (long) footerData.get("footer_data_id"));
			stmt.setString(2, (String) footerData.get("testcase_number"));
			stmt.setLong(3, Long.parseLong((String) footerData.get("assigned_account")));
			stmt.setString(4, (String) footerData.get("client_name"));
			stmt.setBoolean(5, (boolean) footerData.get("is_ignore_severity"));
			stmt.setString(6, (String) footerData.get("sender"));
			stmt.setBoolean(7, (boolean) footerData.get("is_server_import"));
			stmt.setString(8, (String) footerData.get("testcase_status"));
			stmt.setString(9, (String) footerData.get("transaction_type"));
			stmt.setString(10, (String) footerData.get("url"));
			stmt.setInt(11, (int) footerData.get("execution_version"));
		
 			
		} catch (SQLException e) {/*nothing to do here*/e.printStackTrace();}
		
		return stmt;
	}
	
	public PreparedStatement statementToInsertActualData(Connection con) {
		
		String query = "INSERT INTO testcase_actual_data(`actual_data_id`, `testcase_number`, `input_output_value`,  `label`, `nature_of_action`, "
				+ "`remarks`, `screenshot_path`, `is_screen_capture`, `timestamp`, `is_trigger_enter`, `web_element_name`, `web_element_nature`,"
				+ "`log_field`, `execution_version`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
	
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			//set parameters
			stmt.setLong(1, (long) actualData.get("actual_data_id"));
			stmt.setString(2, (String) actualData.get("testcase_number"));
			stmt.setString(3, (String) actualData.get("input_output_value"));
			stmt.setString(4, (String) actualData.get("label"));
			stmt.setString(5, (String) actualData.get("nature_of_action"));
			stmt.setString(6, (String) actualData.get("remarks"));
			stmt.setString(7, (String) actualData.get("screenshot_path"));
			stmt.setBoolean(8, (boolean) actualData.get("is_screen_capture"));
			stmt.setString(9, (String) actualData.get("timestamp"));
			stmt.setBoolean(10, (boolean) actualData.get("is_trgger_enter"));
			stmt.setString(11, (String) actualData.get("web_element_name"));
			stmt.setString(12, (String) actualData.get("web_element_nature"));
			stmt.setString(13, (String) actualData.get("log_field"));
			stmt.setInt(14, (int) actualData.get("execution_version"));
			
			
 			
		} catch (SQLException e) {/*nothing to do here*/e.printStackTrace();}
		
		return stmt;
	}

}
