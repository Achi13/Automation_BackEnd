package com.oneaston.db.campaign.service;

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

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

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
import com.oneaston.db.campaign.domain.DependentTestcase;
import com.oneaston.db.campaign.domain.Story;
import com.oneaston.db.campaign.domain.Theme;
import com.oneaston.db.campaign.repository.CampaignRepository;
import com.oneaston.db.campaign.repository.DependentTestcaseRepository;
import com.oneaston.db.campaign.repository.StoryRepository;
import com.oneaston.db.campaign.repository.ThemeRepository;
import com.oneaston.db.template.domain.DependentTestcaseIOValue;
import com.oneaston.db.template.repository.DependentTestcaseIOValueRepository;
import com.oneaston.db.template.repository.TemplateDataRepository;
import com.oneaston.db.template.repository.TemplatesRepository;
import com.oneaston.db.testcase.domain.TestcaseActualData;
import com.oneaston.db.testcase.domain.TestcaseFooterData;
import com.oneaston.db.testcase.domain.TestcaseRecord;
import com.oneaston.db.testcase.repository.TestcaseActualDataRepository;
import com.oneaston.db.testcase.repository.TestcaseFooterDataRepository;
import com.oneaston.db.testcase.repository.TestcaseRecordRepository;
import com.oneaston.db.universe.repository.UniverseRepository;
import com.oneaston.db.user.repository.UserRepository;

@Path("/theme")
public class ThemeService {
	
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
	
	//THEME SERVICES-----------------------------------
	
	//========================================================PROCESS CREATE THEME=======================================================
	@POST
	@Path("/processcreatetheme")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response processCreateTheme(String jsonData) {
		//INSTANTIATE JSON RESPONSE OBJECT
		JSONObject jsonResponse = new JSONObject();
		
		//GET DATA FROM JSON DATA STRING
		JSONObject themeDetails = new JSONObject(jsonData);
		
		//POPULATE THEME WITH THEME DETAILS JSON
		Theme theme = new Theme(campaignRepo.findCampaignByCampaignId(themeDetails.getLong("campaignId")), 
				userRepo.findUserByUserId(themeDetails.getLong("userId")), universeRepo.findUniverseByUniverseId(themeDetails.getLong("universeId")),
				themeDetails.getString("themeName"), themeDetails.getString("description"), "", timeStamp(), 
				campaignRepo.findCampaignByCampaignId(themeDetails.getLong("campaignId")).getExecutionVersionCurrent(), 
				campaignRepo.findCampaignByCampaignId(themeDetails.getLong("campaignId")).getExecutionVersionCurrent());
		
		//REGISTER THEME
		themeRepo.save(theme);
		
		//PUT INFO MESSAGE ING JSON RESPONSE
		jsonResponse.put("Info", "Stream successfully created.");
		
		//RETURN RESPONSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	//=======================================================PROCESS MODIFY THEME=====================================================
	@POST
	@Path("/processmodifytheme")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response processModifyTheme(String jsonData) {
		
		//INSTANTIATE JSON RESPONSE OBJECT
		JSONObject jsonResponse = new JSONObject();
		
		//GET DATA FROM JSON DATA
		JSONObject themeDetails = new JSONObject(jsonData);
		
		//GET THEME TO MODIFY
		Theme theme = themeRepo.findThemeByThemeId(themeDetails.getLong("themeId"));
		
		//SET DATA TO SELECTED THEME
		theme.setThemeName(themeDetails.getString("themeName"));
		theme.setDescription(themeDetails.getString("description"));
		
		//SAVE CHANGES
		themeRepo.save(theme);
		
		//PUT INFO MESSAGE ING JSON RESPONSE
		jsonResponse.put("Info", "Stream successfully modified.");
		
		//RETURN INFO MESSAGE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	//===================================================ARCHIVE THEME==================================================================
	@POST
	@Path("/archivetheme")
	@Produces(MediaType.APPLICATION_JSON)
	public Response archiveTheme(String jsonData) {
		
		//INSTANTIATE JSON RESPONSE OBJECT
		JSONObject jsonResponse = new JSONObject();
		
		//GET THEME ID IN JSON DATA
		JSONObject themeData = new JSONObject(jsonData);
		long themeId = themeData.getLong("themeId");
		
		//GET THEME BY THEME ID
		Theme theme = themeRepo.findThemeByThemeId(themeId);
		
		//GET STORIES BY THEME ID
		List<Story>story = storyRepo.findStoriesByThemeId(theme);
		
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
		
		if(dependentTestcase.size() != 0) {
			
			ThemeArchive archiveTheme = new ThemeArchive(theme.getThemeId(), theme.getCampaignId().getCampaignId(), theme.getUserId().getUserId(),
					theme.getUniverseId().getUniverseId(), theme.getThemeName(), theme.getDescription(), theme.getStatus(),
					theme.getTimestamp(), theme.getExecutionVersionStart(), theme.getExecutionVersionCurrent());
			themeArchiveRepo.save(archiveTheme);
			
			for(Story stories : story) {
				if(stories.getThemeId().getThemeId() == theme.getThemeId()) {
					StoryArchive archiveStory = new StoryArchive(stories.getStoryId(), theme.getThemeId(), stories.getUserId().getUserId(),
							stories.getUniverseId().getUniverseId(), stories.getStoryName(), stories.getDescription(), stories.getStatus(),
							stories.getTimestamp(), stories.getIsIgnoreSeverity(), stories.getIsServerImport(), stories.getExecutionVersionStart(),
							stories.getExecutionVersionCurrent());
					storyArchiveRepo.save(archiveStory);
					long storyId = stories.getStoryId();
					//COPY DEPENDENT TESTCASE TO ARCHIVE DEPENDENT TESTCASE TABLE
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
								}
							}
							//COPY TESTCASE FOOTER DATA IN ARCHIVE TESTCASE FOOTER DATA
							for(TestcaseFooterData testcaseFooterDatas : testcaseFooterData) {
								if(testcaseFooterDatas.getTestcaseNumber().getTestcaseNumber().equals(testcaseNumber)) {
									TestcaseFooterDataArchive archiveTestcaseFooterData = new TestcaseFooterDataArchive(testcaseFooterDatas.getFooterDataId(),
											testcaseNumber, testcaseFooterDatas.getAssignedAccount(), testcaseFooterDatas.getClientName(),
											testcaseFooterDatas.getIsIgnoreSeverity(), testcaseFooterDatas.getSender(), testcaseFooterDatas.getIsServerimport(),
											testcaseFooterDatas.getTestcaseStatus(), testcaseFooterDatas.getTransactionType(), testcaseFooterDatas.getUrl(),
											testcaseFooterDatas.getExecutionVersion());
									testcaseFooterDataArchiveRepo.save(archiveTestcaseFooterData);
								}
							}
						}
					}
				}
			}
			
			for(DependentTestcaseIOValue ioValue : dependentTestcaseIOValue) {
				dependentTestcaseIOValueRepo.delete(ioValue);
			}
			
			for(TestcaseActualData actualData : testcaseActualData) {
				testcaseActualDataRepo.delete(actualData);
			}
			
			for(TestcaseFooterData footerData : testcaseFooterData) {
				testcaseFooterDataRepo.delete(footerData);
			}
			
			for(TestcaseRecord records : testcaseRecord) {
				testcaseRecordRepo.delete(records);
			}
			
			for(DependentTestcase dTestcase : dependentTestcase) {
				dependentTestcaseRepo.delete(dTestcase);
			}
			
			for(Story stories : story) {
				storyRepo.delete(stories);
			}
			
			themeRepo.delete(theme);
			
			//RETURN SUCCESS MESSAGE
			jsonResponse.put("Info", "Stream successfully archived.");
			return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		}else {
			//RETURN ERROR MESSAGE
			jsonResponse.put("Error", "No Testcases available for archive in the Project");
			return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		}
	}
	
	
	//===========================================================GET THEME LIST==========================================================
	@GET
	@Path("/getthemedata")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getThemeData(@QueryParam("campaignId")long campaignId) {
		
		//GET ALL THEME BASED ON CAMPAIGN ID
		List<Theme>themeList = themeRepo.findThemesByCampaignId(campaignRepo.findCampaignByCampaignId(campaignId));
		
		//GET ALL ARCHIVED THEME BY CAMPAIGN ID
		List<ThemeArchive>archiveThemeList = themeArchiveRepo.findThemeArchiveByCampaignId(campaignId);
		
		//PUT LIST INTO JSON DATA
		JSONObject themeData = new JSONObject();
		themeData.put("themeData", themeList);
		themeData.put("themeArchive", archiveThemeList);
		
		//RETURN JSON DATA
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(themeData.toString()).build();
	}
	
}
