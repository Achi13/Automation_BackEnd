package com.oneaston.db.campaign.service;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.oneaston.archive.campaign.domain.CampaignArchive;
import com.oneaston.archive.campaign.domain.DependentTestcaseArchive;
import com.oneaston.archive.campaign.domain.DependentTestcaseIOValueArchive;
import com.oneaston.archive.campaign.domain.StoryArchive;
import com.oneaston.archive.campaign.domain.ThemeArchive;
import com.oneaston.archive.campaign.repository.CampaignArchiveRepository;
import com.oneaston.archive.campaign.repository.DependentTestcaseArchiveRepository;
import com.oneaston.archive.campaign.repository.DependentTestcaseIOValueArchiveRepository;
import com.oneaston.archive.campaign.repository.StoryArchiveRepository;
import com.oneaston.archive.campaign.repository.ThemeArchiveRepository;
import com.oneaston.archive.testcase.domain.TestcaseActualDataArchive;
import com.oneaston.archive.testcase.domain.TestcaseFooterDataArchive;
import com.oneaston.archive.testcase.domain.TestcaseRecordArchive;
import com.oneaston.archive.testcase.repository.TestcaseActualDataArchiveRepository;
import com.oneaston.archive.testcase.repository.TestcaseFooterDataArchiveRepository;
import com.oneaston.archive.testcase.repository.TestcaseRecordArchiveRepository;
import com.oneaston.configuration.bean.StoragePathBean;
import com.oneaston.configuration.standalone.ProcessExtension;
import com.oneaston.configuration.threads.CampaignThread;
import com.oneaston.db.campaign.domain.Campaign;
import com.oneaston.db.campaign.domain.DependentTestcase;
import com.oneaston.db.campaign.domain.Story;
import com.oneaston.db.campaign.domain.Theme;
import com.oneaston.db.campaign.repository.CampaignRepository;
import com.oneaston.db.campaign.repository.DependentTestcaseRepository;
import com.oneaston.db.campaign.repository.StoryRepository;
import com.oneaston.db.campaign.repository.ThemeRepository;
import com.oneaston.db.template.domain.DependentTestcaseIOValue;
import com.oneaston.db.template.domain.TemplateData;
import com.oneaston.db.template.domain.Templates;
import com.oneaston.db.template.repository.DependentTestcaseIOValueRepository;
import com.oneaston.db.template.repository.TemplateDataRepository;
import com.oneaston.db.template.repository.TemplatesRepository;
import com.oneaston.db.testcase.domain.TestcaseActualData;
import com.oneaston.db.testcase.domain.TestcaseFooterData;
import com.oneaston.db.testcase.domain.TestcaseRecord;
import com.oneaston.db.testcase.repository.TestcaseActualDataRepository;
import com.oneaston.db.testcase.repository.TestcaseFooterDataRepository;
import com.oneaston.db.testcase.repository.TestcaseRecordRepository;
import com.oneaston.db.universe.domain.Headers;
import com.oneaston.db.universe.repository.HeadersRepository;
import com.oneaston.db.universe.repository.UniverseRepository;
import com.oneaston.db.user.repository.UserRepository;
import com.opencsv.CSVWriter;

@Path("/campaign")
public class CampaignService {
	
	@Autowired
	UniverseRepository universeRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	CampaignRepository campaignRepo;
	
	@Autowired
	ThemeRepository themeRepo;
	
	@Autowired
	StoryRepository storyRepo;
	
	@Autowired
	DependentTestcaseRepository dependentTestcaseRepo;
	
	@Autowired
	TestcaseRecordRepository testcaseRecordRepo;
	
	@Autowired
	TestcaseActualDataRepository testcaseActualDataRepo;
	
	@Autowired
	TestcaseFooterDataRepository testcaseFooterDataRepo;
	
	@Autowired
	CampaignArchiveRepository campaignArchiveRepo;
	
	@Autowired
	ThemeArchiveRepository themeArchiveRepo;
	
	@Autowired
	StoryArchiveRepository storyArchiveRepo;
	
	@Autowired
	DependentTestcaseArchiveRepository dependentTestcaseArchiveRepo;
	
	@Autowired
	TestcaseRecordArchiveRepository testcaseRecordArchiveRepo;
	
	@Autowired
	TestcaseActualDataArchiveRepository testcaseActualDataArchiveRepo;
	
	@Autowired
	TestcaseFooterDataArchiveRepository testcaseFooterDataArchiveRepo;
	
	@Autowired
	TemplatesRepository templatesRepo;
	
	@Autowired
	TemplateDataRepository templateDataRepo;
	
	@Autowired
	DependentTestcaseIOValueRepository dependentTestcaseIOValueRepo;
	
	@Autowired
	DependentTestcaseIOValueArchiveRepository dependentTestcaseIOValueArchiveRepo;
	
	@Autowired
	HeadersRepository headersRepo;
	
	//PRIVATE FUCNTIONS--------------------------------
	//FOR GETTING CURRENT TIME
	private String timeStamp(){
		Date date = new Date();
		String dateFileName;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd - HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
		String tempA = dateFormat.format(date).toString();
		dateFileName = tempA;
		return dateFileName;
	}
	
	//======================================CHECK DEPENDENT TESTCASE SIZE ===================================
	private boolean isDependentNotEmpty(List<DependentTestcase>dependentTestcase) {
		
		if(dependentTestcase.size() > 0) {
			return true;
		}else {
			return false;
		}
		
	}
	
	//======================================CHECK IF IO VALUE LENGTH == TEMPLATE DATA LENGTH==================================================== 
	public boolean isIOTempDataLengthEqual(List<DependentTestcaseIOValue>ioValue,
			List<TemplateData>templateData) {
		
		if(ioValue.size() == templateData.size()) {
			return true;
		}else {
			return false;
		}
	}
	
	//==============================================GIVE SCHEDULE TO NULL CAMPAIGN SCHED====================================================
	public String getCurrentDateAndTime() {
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm");
		dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
		String tempA = dateFormat.format(date).toString();
		
		return tempA;
	}

	//CAMPAIGN SERVICES--------------------------------
	@POST
	@Path("/addexecutionschedule")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addExecutionSchedule(String json) {
		
		Logger log = Logger.getLogger(getClass());
		
		
		//GET JSON DATA
		JSONObject jsonData = new JSONObject(json);
		
		//FIND CAMPAIGN BY ID
		Campaign campaign = campaignRepo.findCampaignByCampaignId(Long.parseLong(jsonData.getString("campaignId")));
		
		if(!jsonData.getString("executionSchedule").isEmpty()) {
			
			log.info("if");
			
			String executionData = jsonData.getString("executionSchedule").replaceAll("-", ".").replace("T", ".").replace(":", ".");
			
			campaign.setExecutionSchedule(executionData);
			
			campaignRepo.save(campaign);
			
			log.info(executionData);
			
		}else {
			log.info("else");
			
			campaign.setExecutionSchedule(getCurrentDateAndTime());
			
			campaignRepo.save(campaign);
			
			log.info(campaign.getExecutionSchedule());
		}
		return Response.status(200).build();
		
	}
	
	//===============================================================PROCESS CREATE CAMPAIGN=================================================
	@POST
	@Path("/processcreatecampaign")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response processCreateCampaign(String jsonData) {
		
		//INSTANTIATE JSON RESPONSE OBJECT
		JSONObject jsonResponse = new JSONObject();
		
		//GET DATA FROM JSON DATA STRING
		JSONObject campaignDetails = new JSONObject(jsonData);
		
		//POPULATE CAMPAIGN WITH CAMPAIGN DETAILS JSON
		Campaign campaign = new Campaign(userRepo.findUserByUserId(campaignDetails.getLong("userId")),
				universeRepo.findUniverseByUniverseId(campaignDetails.getInt("universeId")), campaignDetails.getString("campaignName"), 
				campaignDetails.getString("description"), "", timeStamp(), "", 0);
		
		//REGISTER CAMPAIGN IN DB
		campaignRepo.save(campaign);
		
		//PUT INFO MESSAGE ING JSON RESPONSE
		jsonResponse.put("Info", "Project successfully created.");
		
		//RETURN RESPONSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	//===================================================PROCESS MODIFY CAMPAIGN==============================================================
	@POST
	@Path("/processmodifycampaign")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response processModifyCampaign(String jsonData) {
		
		//INSTANTIATE JSON RESPONSE OBJECT
		JSONObject jsonResponse = new JSONObject();
		
		//GET DATA FROM JSON DATA
		JSONObject campaignDetails = new JSONObject(jsonData);
		
		//GET CAMPAIGN TO MODIFY
		Campaign campaign = campaignRepo.findCampaignByCampaignId(campaignDetails.getLong("campaignId"));
		
		//SET DATA TO SELECTED CAMPAIGN
		campaign.setCampaignName(campaignDetails.getString("campaignName"));
		campaign.setDescription(campaignDetails.getString("description"));
		
		//SAVE CHANGES
		campaignRepo.save(campaign);
		
		//PUT INFO MESSAGE ING JSON RESPONSE
		jsonResponse.put("Info", "Project successfully modified.");
		
		//RETURN INFO MESSAGE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	//=====================================================EXECUTE CAMPAIGN=========================================================================
	@GET
	@Path("/executecampaign")
	@Produces(MediaType.APPLICATION_JSON)
	public Response executeCampaign(@QueryParam("campaignId")long campaignId) throws IOException {
		
		Logger log = Logger.getLogger(getClass());
		
		log.info("Campaign id: " + campaignId);
		
		
		
		//INSTANTIATE JSON RESPONSE OBJECT
		JSONObject jsonResponse = new JSONObject();
		
		//GET CAMPAIGN BY CAMPAIGN ID
		Campaign campaign = campaignRepo.findCampaignByCampaignId(campaignId);
		campaign.setExecutionVersionCurrent(campaign.getExecutionVersionCurrent() + 1);
		campaign.setStatus("Executing");
		campaignRepo.save(campaign);
		
		//GET THEME BY CAMPAIGN
		List<Theme>theme = themeRepo.findThemesByCampaignId(campaign);
		
		//GET STORY BY THEME ID
		List<Story>story = new ArrayList<Story>();
		for(Theme themeId: theme) {
			
			//INCREMENT THEME VERSION
			themeId.setExecutionVersionCurrent(themeId.getExecutionVersionCurrent() + 1);
			themeId.setStatus("Executing");
			themeRepo.save(themeId);
			
			List<Story>storyList = storyRepo.findStoriesByThemeId(themeId);
			for(Story stories : storyList) {
				
				//INCREMENT STORY VERSION
				stories.setExecutionVersionCurrent(stories.getExecutionVersionCurrent() + 1);
				stories.setStatus("Executing");
				storyRepo.save(stories);
				
				story.add(stories);
			}
		}
		
		//GET DEPENDENT TESTCASE BY STORY ID
		List<DependentTestcase>dependentTestcase = new ArrayList<DependentTestcase>();
		for(Story storyId : story) {
			List<DependentTestcase>dependentTestcaseList = dependentTestcaseRepo.findDependentTestcasesByStoryId(storyId);
			for(DependentTestcase dependentTestcases : dependentTestcaseList) {
				
				dependentTestcases.setExecutionVersionCurrent(dependentTestcases.getExecutionVersionCurrent() + 1);
				dependentTestcaseRepo.save(dependentTestcases);
				dependentTestcase.add(dependentTestcases);
				
			}
		}
		
		
		
		
		log.info(dependentTestcase.size());
		
		
		if(isDependentNotEmpty(dependentTestcase)) {
			
			
			
			for(DependentTestcase dTestcase : dependentTestcase) {
				
				//INSTANTIATE THE LENGTH OF THE COLUMNS
				final int columnLength = 11;
				
				//INSTANTIATE LIST OF STRING ARRAY FOR DATA WRITING
				List<String[]>dataToWrite = new ArrayList<String[]>();
				
				//GET TEMPLATES BY TEMPLATE ID
				Templates templateId = templatesRepo.findTemplatesByTemplateId(dTestcase.getTemplateId().getTemplateId());
				
				//GET IO VALUE BY TESTCASE NUMBER
				List<DependentTestcaseIOValue>ioValue = dependentTestcaseIOValueRepo.findIOValuesByTestcaseNumber(dTestcase);
				
				//GET TEMPLATE DATA BY TEMPLATES ID
				List<TemplateData>templateData = templateDataRepo.findTemplateDatasByTemplateId(templateId);
				
				
				//STRING FORMAT FOR CSV PATH
				String csvPath = String.format("%s\\%s.csv", StoragePathBean.CSVHOLDER_FOLDER, dTestcase.getTestcaseNumber());
				
				//FILE OPERATIONS FOR WRITING IN THE CSV-----------------------------------------
				FileWriter csvFile = new FileWriter(csvPath);
				CSVWriter csvWriter = new CSVWriter(csvFile);
				
				//FOR CSV HEADER
				String[] header = new String[columnLength];
				header[0] = "WebElementName";
				header[1] = "WebElementNature";
				header[2] = "NatureOfAction";
				header[3] = "ScreenCapture";
				header[4] = "TriggerEnter";
				header[5] = "InputOutputValue";
				header[6] = "Label";
				header[7] = "Timestamp";
				header[8] = "SCPath";
				header[9] = "Remarks";
				header[10] = "LogField";
				dataToWrite.add(header);
				
				List<Headers>headerList = headersRepo.findHeadersByWebAddressId(dTestcase.getWebAddressId());
				
				for(Headers headerData : headerList) {
					
					String[] dataHeader = new String[columnLength];
					dataHeader[0] = headerData.getWebElementName();
					dataHeader[1] = headerData.getWebElementNature();
					dataHeader[2] = headerData.getNatureOfAction();
					dataHeader[3] = "false";
					dataHeader[4] = "false";
					if(headerData.getLabel().equalsIgnoreCase("username")) {
						dataHeader[5] = dTestcase.getLoginAccountId().getUsername();
					}else if(headerData.getLabel().equalsIgnoreCase("password")) {
						dataHeader[5] = dTestcase.getLoginAccountId().getPassword();
					}
					dataHeader[6] = headerData.getLabel();
					dataToWrite.add(dataHeader);
				}
				
				for(int i=0; i<templateData.size(); i++) {
					//FOR CSV BODY
					String[]csvBody = new String[columnLength];
					csvBody[0] = templateData.get(i).getWebElementName();
					csvBody[1] = templateData.get(i).getWebElementNature();
					csvBody[2] = templateData.get(i).getNatureOfAction();
					csvBody[3] = Boolean.toString(templateData.get(i).isScreenCapture());
					csvBody[4] = Boolean.toString(templateData.get(i).isTriggerEnter());
					for(int j=0; j<ioValue.size(); j++) {
						if(ioValue.get(j).getTemplateDataId().getTemplateDataId() == templateData.get(i).getTemplateDataId()) {
							csvBody[5] = ioValue.get(j).getIoValue();
							break;
						}else {
							csvBody[5] = "";
						}
						
					}
					
					csvBody[6] = templateData.get(i).getLabel();
					dataToWrite.add(csvBody);
				}
				
				
				
				//CLIENT NAME
				String[]clientNameFooter = new String[columnLength];
				clientNameFooter[0] = "ClientName";
				clientNameFooter[1] = dTestcase.getClientId().getClientName();
				dataToWrite.add(clientNameFooter);
				
				//TRANSACTION TYPE
				String[]transactionType = new String[columnLength];
				transactionType[0] = "TransactionType";
				transactionType[1] = dTestcase.getTemplateId().getTemplateName();
				dataToWrite.add(transactionType);
				
				//WEBSITE
				String[]website = new String[columnLength];
				website[0] = "Website";
				website[1] = dTestcase.getWebAddressId().getUrl();
				dataToWrite.add(website);
				
				//SERVER IMPORT
				String[]serverImportFooter = new String[columnLength];
				serverImportFooter[0] = "ServerImport";
				serverImportFooter[1] = dTestcase.getStoryId().getIsServerImport() ? "on" : "off";
				dataToWrite.add(serverImportFooter);
				
				//IGNORE SEVERITY
				String[]ignoreSeverityFooter = new String[columnLength];
				ignoreSeverityFooter[0] = "IgnoreSeverity";
				ignoreSeverityFooter[1] = dTestcase.getStoryId().getIsIgnoreSeverity() ? "on" : "off";
				dataToWrite.add(ignoreSeverityFooter);
				
				//ASSIGNED ACCOUNT
				String[]assignedAccountFooter = new String[columnLength];
				assignedAccountFooter[0] = "AssignedAccount";
				assignedAccountFooter[1] = Long.toString(dTestcase.getLoginAccountId().getLoginAccountId());
				dataToWrite.add(assignedAccountFooter);
				
				//SENDER
				String[]sender = new String[columnLength];
				sender[0] = "Sender";
				sender[1] = dTestcase.getUserId().getUsername();
				dataToWrite.add(sender);
				
				//TESTCASE NUMBER
				String[]testcaseNumberFooter = new String[columnLength];
				testcaseNumberFooter[0] = "TestcaseNumber";
				testcaseNumberFooter[1] = dTestcase.getTestcaseNumber();
				dataToWrite.add(testcaseNumberFooter);
				
				String[]testcaseImportStatus = new String[columnLength];
				testcaseImportStatus[0] = "TapImportStatus";
				testcaseImportStatus[1] = dTestcase.getTapImportStatus();
				dataToWrite.add(testcaseImportStatus);
				
				//TESTCASE STATUS
				String[]testcaseStatusFooter = new String[columnLength];
				testcaseStatusFooter[0] = "TestCaseStatus";
				dataToWrite.add(testcaseStatusFooter);
				
				
				
				
				//PASS DATA TO WRITE LIST TO CSV WRITER
				csvWriter.writeAll(dataToWrite);
				csvWriter.close();
				
				
			}
			
			//INSTANTIATE ARRAYS FOR PROCESS EXTENSION
			String[]testCaseNumber = new String[dependentTestcase.size()];
			String[]description = new String[dependentTestcase.size()];
			long[]clientId = new long[dependentTestcase.size()];
			
			for(int i=0; i<dependentTestcase.size(); i++) {
				testCaseNumber[i] = dependentTestcase.get(i).getTestcaseNumber();
				description[i] = dependentTestcase.get(i).getDescription();
				clientId[i] = dependentTestcase.get(i).getClientId().getClientId();
			}
			
			//UPDATE TESTCASE RECORD 
			ProcessExtension process = new ProcessExtension();
			try {
				campaign.setExecutionSchedule(getCurrentDateAndTime());
				campaignRepo.save(campaign);
				process.processController(testCaseNumber, description, clientId, campaign.getExecutionSchedule().equals(null) ? getCurrentDateAndTime() : campaign.getExecutionSchedule(), 
						campaign.getExecutionVersionCurrent(), campaign.getUniverseId().getUniverseId(), 
						campaign.getUserId().getUserId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//EXECUTE CAMPAIGN PROCESS
			CampaignThread campaignProcess = new CampaignThread(campaign.getCampaignId(), StoragePathBean.BRIDGE_FOLDER, StoragePathBean.CSVHOLDER_FOLDER);
			campaignProcess.start();
			
			
			jsonResponse.put("Info", "Csv successfully written");
			return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		}else {
			campaign.setStatus("");
			campaignRepo.save(campaign);
			jsonResponse.put("Error", "No Testcases to be executed.");
			return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		}
		
		
		
	}
	
	
	
	//==============================================================PROCESS DELETE CAMPAIG======================================================
	@POST
	@Path("/archivecampaign")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response archiveCampaign(String jsonData) {
		
		Logger log = Logger.getLogger(getClass());
		log.info("Access:/Archive Campaign");
		log.info(jsonData);
		
		//GET CAMPAIGN ID IN JSON DATA
		JSONObject campaignData = new JSONObject(jsonData);
		long campaignId = campaignData.getLong("campaignId");
		
		//INSTANTIATE JSON RESPONSE OBJECT
		JSONObject jsonResponse = new JSONObject();
		
		//GET CAMPAIGN BY CAMPAIGN ID
		Campaign campaign = campaignRepo.findCampaignByCampaignId(campaignId);
		
		//GET THEME BY CAMPAIGN
		List<Theme>theme = themeRepo.findThemesByCampaignId(campaign);
		
		//GET STORY BY THEME ID
		List<Story>story = new ArrayList<Story>();
		for(Theme themeId: theme) {
			List<Story>storyList = storyRepo.findStoriesByThemeId(themeId);
			for(Story stories : storyList) {
				story.add(stories);
			}
		}
		
		//GET DEPENDENT TESTCASE BY STORY ID
		List<DependentTestcase>dependentTestcase = new ArrayList<DependentTestcase>();
		for(Story storyId : story) {
			List<DependentTestcase>dependentTestcaseList = dependentTestcaseRepo.findDependentTestcasesByStoryId(storyId);
			for(DependentTestcase dependentTestcases : dependentTestcaseList) {
				dependentTestcase.add(dependentTestcases);
			}
		}
		
		//GET DEPENDENT TESTCASE IO VALUE BY DEPENDENT TSTCASE
		List<DependentTestcaseIOValue>dependentTestcaseIOValue = new ArrayList<DependentTestcaseIOValue>();
		for(DependentTestcase testcaseNumber : dependentTestcase) {
			List<DependentTestcaseIOValue>ioValueList = dependentTestcaseIOValueRepo.findIOValuesByTestcaseNumber(testcaseNumber);
			for(DependentTestcaseIOValue ioValue : ioValueList) {
				dependentTestcaseIOValue.add(ioValue);
			}
		}
		
		//GET TESTCASE RECORD BY DEPENDENT TESTCASE TESTCASE NUMBER
		List<TestcaseRecord>testcaseRecord = new ArrayList<TestcaseRecord>();
		for(DependentTestcase testcaseNumber : dependentTestcase) {
			List<TestcaseRecord>testcaseRecordList = testcaseRecordRepo.findTestcaseRecordsByTestcaseNumber(testcaseNumber);
			for(TestcaseRecord testcaseRecords : testcaseRecordList) {
				testcaseRecord.add(testcaseRecords);
			}
		}
		
		//GET TESTCASE ACTUAL DATA BY DEPENDENT TESTCASE TESTCASE NUMBER
		List<TestcaseActualData>testcaseActualData = new ArrayList<TestcaseActualData>();
		for(DependentTestcase testcaseNumber : dependentTestcase) {
			List<TestcaseActualData>testcaseActualDataList = testcaseActualDataRepo.findTestcaseActualDatasByTestcaseNumber(testcaseNumber);
			for(TestcaseActualData testcaseActualDatas : testcaseActualDataList) {
				testcaseActualData.add(testcaseActualDatas);
			}
		}
		
		//GET TESTCASE FOOTER DATA BY DEPENDENT TESTCASE TESTCASE NUMBER
		List<TestcaseFooterData>testcaseFooterData = new ArrayList<TestcaseFooterData>();
		for(DependentTestcase testcaseNumber : dependentTestcase) {
			List<TestcaseFooterData>testcaseFooterDataList = testcaseFooterDataRepo.findTestcaseFooterDatasByTestcaseNumber(testcaseNumber);
			for(TestcaseFooterData testcaseFooterDatas : testcaseFooterDataList) {
				testcaseFooterData.add(testcaseFooterDatas);
			}
		}
		
		//IF NO DEPENDENT TESTCASE, THROW ERROR
		if(dependentTestcase.size() != 0) {
			//COPY CAMPAIGN TO ARCHIVE CAMPAIGN TABLE
			CampaignArchive archiveCampaign = new CampaignArchive(campaign.getCampaignId(), campaign.getUserId().getUserId(),
					campaign.getUniverseId().getUniverseId(), campaign.getCampaignName(), campaign.getDescription(),
					campaign.getStatus(), campaign.getTimestamp(), campaign.getExecutionVersionCurrent());
			campaignArchiveRepo.save(archiveCampaign);
			
			//COPY THEME TO ARCHIVE THEME TABLE
			for(Theme themes : theme) {
				ThemeArchive archiveTheme = new ThemeArchive(themes.getThemeId(), campaign.getCampaignId(), themes.getUserId().getUserId(),
						themes.getUniverseId().getUniverseId(), themes.getThemeName(), themes.getDescription(), themes.getStatus(),
						themes.getTimestamp(), themes.getExecutionVersionStart(), themes.getExecutionVersionCurrent());
				themeArchiveRepo.save(archiveTheme);
				long themeId = themes.getThemeId();
				//COPY STORY TO ARCHIVE STORY TABLE
				for(Story stories : story) {
					if(stories.getThemeId().getThemeId() == themeId) {
						StoryArchive archiveStory = new StoryArchive(stories.getStoryId(), themeId, stories.getUserId().getUserId(),
								stories.getUniverseId().getUniverseId(), stories.getStoryName(), stories.getDescription(), stories.getStatus(),
								stories.getTimestamp(), stories.getIsIgnoreSeverity(), stories.getIsServerImport(), stories.getExecutionVersionStart(),
								stories.getExecutionVersionCurrent());
						storyArchiveRepo.save(archiveStory);
						long storyId = stories.getStoryId();
						//COPY DEPENDENT TESTCASE TO ARCHIVE DEPENDENT TESTCASE TABLE
						dependentTestcaseLoop:
						for(DependentTestcase dependentTestcases : dependentTestcase) {
							if(dependentTestcases.getStoryId().getStoryId() == storyId) {
								DependentTestcaseArchive archiveDependentTestcase = new DependentTestcaseArchive(dependentTestcases.getTestcaseNumber(),
										storyId, dependentTestcases.getUserId().getUserId(), dependentTestcases.getTemplateId().getTemplateId(), 
										dependentTestcases.getUniverseId().getUniverseId(), dependentTestcases.getWebAddressId().getWebAddressId(),
										dependentTestcases.getClientId().getClientId(), dependentTestcases.getLoginAccountId().getLoginAccountId(), 
										dependentTestcases.getDescription(), dependentTestcases.getStatus(), dependentTestcases.getTemplateVersion(),
										dependentTestcases.getExecutionVersionStart(), dependentTestcases.getExecutionVersionCurrent());
								dependentTestcaseArchiveRepo.save(archiveDependentTestcase);
								String testcaseNumber = dependentTestcases.getTestcaseNumber();
								
								//COPY DEPENDENT TESTCASE IO VALUE IN ARCHIVE DEPENDENT TESTCASE IO VALUE
								for(DependentTestcaseIOValue ioValues : dependentTestcaseIOValue) {
									if(ioValues.getTestcaseNumber().getTestcaseNumber().equals(testcaseNumber)) {
										DependentTestcaseIOValueArchive ioValueArchive = new DependentTestcaseIOValueArchive(ioValues.getIoId(), ioValues.getTestcaseNumber().getTestcaseNumber(),
												ioValues.getTemplateDataId().getTemplateDataId(), ioValues.getIoValue());
										dependentTestcaseIOValueArchiveRepo.save(ioValueArchive);
										
										dependentTestcaseIOValueRepo.delete(ioValues);
									}
								}
								
								//COPY TESTCASE RECORD IN ARCHIVE TESTCASE RECORD TABLE
								for(TestcaseRecord testcaseRecords : testcaseRecord) {
									if(testcaseRecords.getTestcaseNumber().getTestcaseNumber().equals(testcaseNumber)) {
										TestcaseRecordArchive archiveTestcaseRecord = new TestcaseRecordArchive(testcaseRecords.getRecordId(),
												testcaseRecords.getClientId().getClientId(), testcaseNumber, testcaseRecords.getUserId().getUserId(),
												testcaseRecords.getUniverseId().getUniverseId(), testcaseRecords.getClientId().getClientName(), testcaseRecords.getDescription(),
												testcaseRecords.getStatus(), testcaseRecords.getExecutionSchedule(), testcaseRecords.getExecutionVersion());
										testcaseRecordArchiveRepo.save(archiveTestcaseRecord);
										
										testcaseRecordRepo.delete(testcaseRecords);
									}
								}
								//COPY TESTCASE ACTUAL DATA IN ARCHIVE TESTCASE ACTUAL DATA
								for(TestcaseActualData testcaseActualDatas : testcaseActualData) {
									if(testcaseActualDatas.getTestcaseNumber().getTestcaseNumber().equals(testcaseNumber)) {
										TestcaseActualDataArchive archiveTestcaseActualData = new TestcaseActualDataArchive(testcaseActualDatas.getActualDataId(),
												testcaseNumber, testcaseActualDatas.getInputOutputValue(), testcaseActualDatas.getLabel(),
												testcaseActualDatas.getNatureOfAction(), testcaseActualDatas.getRemarks(), testcaseActualDatas.getScreenshotPath(),
												testcaseActualDatas.isScreenCapture(), testcaseActualDatas.getTimestamp(), testcaseActualDatas.isTriggerEnter(),
												testcaseActualDatas.getWebElementName(), testcaseActualDatas.getWebElementNature(), testcaseActualDatas.getLogField(),
												testcaseActualDatas.getExecutionVersion());
										testcaseActualDataArchiveRepo.save(archiveTestcaseActualData);
										
										testcaseActualDataRepo.delete(testcaseActualDatas);
									}
								}
								//COPY TESTCASE FOOTER DATA IN ARCHIVE TESTCASE FOOTER DATA
								for(TestcaseFooterData testcaseFooterDatas : testcaseFooterData) {
									if(testcaseFooterDatas.getTestcaseNumber().getTestcaseNumber().equals(testcaseNumber)) {
										TestcaseFooterDataArchive archiveTestcaseFooterData = new TestcaseFooterDataArchive(testcaseFooterDatas.getFooterDataId(),
												testcaseNumber, testcaseFooterDatas.getAssignedAccount(), testcaseFooterDatas.getClientName(),
												Boolean.valueOf(testcaseFooterDatas.getIsIgnoreSeverity()), testcaseFooterDatas.getSender(), Boolean.valueOf(testcaseFooterDatas.getIsServerimport()),
												testcaseFooterDatas.getTestcaseStatus(), testcaseFooterDatas.getTransactionType(), testcaseFooterDatas.getUrl(),
												testcaseFooterDatas.getExecutionVersion());
										testcaseFooterDataArchiveRepo.save(archiveTestcaseFooterData);
										
										testcaseFooterDataRepo.delete(testcaseFooterDatas);
									}
								}
								continue dependentTestcaseLoop;
							}
						}
					}
				}
				
			}
			
			for(DependentTestcase dTestcase : dependentTestcase) {
				dependentTestcaseRepo.delete(dTestcase);
			}
			
			for(Story stories : story) {
				storyRepo.delete(stories);
			}
			
			for(Theme themes : theme) {
				themeRepo.delete(themes);
			}
			
			campaignRepo.delete(campaign);
			//RETURN SUCCESS MESSAGE
			jsonResponse.put("Info", "Project successfully archived.");
			return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		}else {
			//RETURN ERROR MESSAGE
			jsonResponse.put("Error", "No Testcases available for archive in the Project");
			return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		}
	}
	
	//==================================================================GET CAMPAIGN LIST====================================================
	@GET
	@Path("/getcampaigndata")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCampaignData(@QueryParam("userId")long userId) {
		
		Logger log = Logger.getLogger(getClass());
		log.info("Access:/Get Campaign");
		
		//GET ALL CAMPAIGN BASED ON USER ID
		List<Campaign>campaignList = campaignRepo.findCampaignsByUserId(userRepo.findUserByUserId(userId));
		
		JSONObject campaignData = new JSONObject();
		
		//TRAVERSE CAMPAIGN TREE
		for(Campaign campaign : campaignList) {
			
			JSONArray dTestcaseArray = new JSONArray();
;			
			List<Theme>themes = themeRepo.findThemesByCampaignId(campaign);
			
			for(Theme theme : themes) {
				
				List<Story>stories = storyRepo.findStoriesByThemeId(theme);
				
				for(Story story : stories) {
					
					List<DependentTestcase> dependentTestcases = dependentTestcaseRepo.findDependentTestcasesByStoryId(story);
					
					for(DependentTestcase dependentTestcase : dependentTestcases) {
						
						//GET TEMPLATES BY TEMPLATE ID
						Templates template = templatesRepo.findTemplatesByTemplateId(dependentTestcase.getTemplateId().getTemplateId());
						
						//CHECK IF TESTCASE TEMPLATE DATA IS EQUAL TO CURRENT TEMPLATE
						if(dependentTestcase.getTemplateVersion() != template.getTemplateVersion()) {
							
							//PUT OUTDATED TESTCASE IN JSON ARRAY
							dTestcaseArray.put(dependentTestcase.getTestcaseNumber());
							
						}
						
					}
					
				}
				
			}
			campaignData.put(Long.toString(campaign.getCampaignId()), dTestcaseArray);
			
		}
		
		//GET ALL CAMPAIGN ARCHIVE
		List<CampaignArchive> campaignArchiveList = campaignArchiveRepo.findAll();
		
		//GET SIZE OF CAMPAIGN THAT IS BASED ON USER ID
		int campaignSize = campaignList.size();
		
		//PUT LIST TO JSON DATA
		JSONObject campaignJson = new JSONObject();
		campaignJson.put("campaignArchiveList", campaignArchiveList);
		campaignJson.put("campaignList", campaignList);
		campaignJson.put("campaignSize", campaignSize);
		campaignJson.put("checker", campaignData);
		
		//RETURN RESPONSE WITH LIST
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(campaignJson.toString()).build();
	}
	
	//==================================================GET CAMPAIGN SCHEULE DATA==========================================================
	@GET
	@Path("/getschedule")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getScheduleCampaign() {
		
		//GET ALL CAMPAIGN
		List<Campaign>campaignList = campaignRepo.findAll();
		
		//ITERATE CAMPAIGN LIST AND GET SCHEDULE
		JSONObject campaignScheduleJson = new JSONObject();
		List<Campaign>campaignSchedList = new ArrayList<Campaign>();
		for(Campaign campaign : campaignList) {
			
			try {
				
				//PARSE CAMPAIGN SCHEDULE AND SET CAMPAIGN SCHEDULE
				String scheduleString = campaign.getExecutionSchedule().substring(0, 10).replace(".", "-");
				campaign.setExecutionSchedule(scheduleString);
				
				List<DependentTestcase>dependentTestcaseList = new ArrayList<DependentTestcase>();
				
				//GET THEME
				List<Theme>themeList = themeRepo.findThemesByCampaignId(campaign);
				List<Story>storyList = new ArrayList<Story>();
				//ITERATE THEME
				for(Theme theme : themeList) {
					
					List<Story>stories = storyRepo.findStoriesByThemeId(theme);
					for(Story story : stories) {
						storyList.add(story);
					}
				}
				
				//ITERATE STORY
				for(Story story : storyList) {
					List<DependentTestcase>dependentTestcases = dependentTestcaseRepo.findDependentTestcasesByStoryId(story);
					for(DependentTestcase dTestcase : dependentTestcases) {
						dependentTestcaseList.add(dTestcase);
					}
				}
				
				//SET NUMBER OF TESTCASES
				campaign.setExecutionVersionCurrent(dependentTestcaseList.size());
				
				int passed = 0;
				int failed = 0;
				int pending = 0;
				
				for(DependentTestcase dTestcase : dependentTestcaseList) {
					
					passed = dTestcase.getStatus().equals("Passed") ? passed+1 : passed;
					failed = dTestcase.getStatus().equals("Failed") ? failed+1 : failed;
					pending = dTestcase.getStatus().equals("Pending") ? pending+1 : pending;
					
				}
				
				//SET SUMMARY
				campaign.setTimestamp(String.format("%d Passed, %d Failed, %d Pending.", passed, failed, pending));
				
				//SET CAMPAIGN STATUS
				switch(campaign.getStatus()) {
					case "Failed":
						campaign.setStatus("#ef5350");
						break;
					case "Passed":
						campaign.setStatus("#43a047");
						break;
					case "Executing":
						campaign.setStatus("#039be5");
						break;
					case "Pending":
						campaign.setStatus("#ff8f00");
						break;
					case "Ignored":
						campaign.setStatus("#e0e0e0");
						break;
					default:
						campaign.setStatus("#ff8f00");
						break;
						
				}
				
				
				
				//ADD TO LIST
				campaignSchedList.add(campaign);
			}catch(Exception e) {
				continue;
			}
			
		}
		
		campaignScheduleJson.put("campaignList", campaignSchedList);
		
		//RETURN RESPONSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(campaignScheduleJson.toString()).build();
	}
	
	//==========================================GET ALL ACCUSTOMED DATA IN CAMPAIGN BY USER================================================
	@GET
	@Path("/getallcampaigndatabyuser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCampaignByUser(@QueryParam("userId")long userId,
			@QueryParam("universeId")long universeId) {
		
		//INSTANTIATE JSON OBJECT
		JSONObject jsonData = new JSONObject();
		
		//GET ALL CAMPAIGN BY USER
		List<Campaign>campaignList = campaignRepo.findCampaignsByUserIdAndUniverseId(userRepo.findUserByUserId(userId),
				universeRepo.findUniverseByUniverseId(universeId));
		
		//GET THEMES BY CAMPAIGN
		List<Theme>themeList = new ArrayList<Theme>();
		for(Campaign campaign : campaignList) {
			
			List<Theme>themeHolderList = themeRepo.findThemesByCampaignId(campaign);
			
			//ITERATE TEMP HOLDER AND ADD THEME TO LIST
			for(Theme theme : themeHolderList) {
				themeList.add(theme);
			}
			
		}
		
		//GET STORIES BY THEME
		List<Story>storyList = new ArrayList<Story>();
		for(Theme theme : themeList) {
			
			List<Story>storyHolderList = storyRepo.findStoriesByThemeId(theme);
			
			//ITERATE TEMP HOLDER AND ADD STORY TO LIST
			for(Story story : storyHolderList) {
				storyList.add(story);
			}
		}
		
		//GET DEPENDENT TESTCASES BY STORY
		List<DependentTestcase>dependentTestcaseList = new ArrayList<DependentTestcase>();
		for(Story story : storyList) {
			
			List<DependentTestcase>dependentTestcaseHolderList = dependentTestcaseRepo.findDependentTestcasesByStoryId(story);
			
			//ITERATE TEMP HOLDER AND ADD DEPENDENT TESTCASE TO LIST
			for(DependentTestcase dTestcase : dependentTestcaseHolderList) {
				dependentTestcaseList.add(dTestcase);
			}
		}
		
		//PUT LISTS TO JSON DATA
		jsonData.put("campaignList", campaignList);
		jsonData.put("themeList", themeList);
		jsonData.put("storyList", storyList);
		jsonData.put("dependentTestcaseList", dependentTestcaseList);
		
		//RETURN JSON DATA
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonData.toString()).build();
	}
	
}
