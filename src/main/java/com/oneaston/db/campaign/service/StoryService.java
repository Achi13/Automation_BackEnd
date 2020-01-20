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

@Path("/story")
public class StoryService {
	
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
	
	//STORY SERVICES-----------------------------------
	
	//========================================================PROCESS CREATE THEME=======================================================
		@POST
		@Path("/processcreatestory")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response processCreateStory(String jsonData) {
			//INSTANTIATE JSON RESPONSE OBJECT
			JSONObject jsonResponse = new JSONObject();
			
			//GET DATA FROM JSON DATA STRING
			JSONObject storyDetails = new JSONObject(jsonData);
			
			//POPULATE STORY WITH STORY DETAILS JSON
			Story story = new Story(themeRepo.findThemeByThemeId(storyDetails.getLong("themeId")), userRepo.findUserByUserId(storyDetails.getLong("userId")),
					universeRepo.findUniverseByUniverseId(storyDetails.getLong("universeId")), storyDetails.getString("storyName"),
					storyDetails.getString("description"), "", timeStamp(), storyDetails.getBoolean("isIgnoreSeverity"),
					storyDetails.getBoolean("isServerImport"), themeRepo.findThemeByThemeId(storyDetails.getLong("themeId")).getCampaignId().getExecutionVersionCurrent(), 
					themeRepo.findThemeByThemeId(storyDetails.getLong("themeId")).getCampaignId().getExecutionVersionCurrent());
			
			//REGISTER STORY
			storyRepo.save(story);
			
			//PUT INFO MESSAGE ING JSON RESPONSE
			jsonResponse.put("Info", "Category successfully created.");
			
			//RETURN RESPONSE
			return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		}
		
		//=======================================================PROCESS MODIFY THEME=====================================================
		@POST
		@Path("/processmodifystory")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response processModifyTheme(String jsonData) {
			
			//INSTANTIATE JSON RESPONSE OBJECT
			JSONObject jsonResponse = new JSONObject();
			
			//GET DATA FROM JSON DATA
			JSONObject storyDetails = new JSONObject(jsonData);
			
			//GET STORY TO MODIFY
			Story story = storyRepo.findStoryByStoryId(storyDetails.getLong("storyId"));
			
			//SET DATA TO SELECTED STORY
			story.setStoryName(storyDetails.getString("storyName"));
			story.setDescription(storyDetails.getString("description"));
			story.setIsIgnoreSeverity(storyDetails.getBoolean("isIgnoreSeverity"));
			story.setIsServerImport(storyDetails.getBoolean("isServerImport"));
			
			//SAVE CHANGES
			storyRepo.save(story);
			
			//PUT INFO MESSAGE ING JSON RESPONSE
			jsonResponse.put("Info", "Category successfully modified.");
			
			//RETURN INFO MESSAGE
			return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		}
		
		//=========================================================ARCHIVE STORY=============================================================
		@POST
		@Path("/archivestory")
		@Produces(MediaType.APPLICATION_JSON)
		public Response archiveStory(String jsonData) {
			
			//INSTANTIATE JSON RESPONSE OBJECT
			JSONObject jsonResponse = new JSONObject();
			
			//GET STORY ID IN JSON DATA
			JSONObject storyData = new JSONObject(jsonData);
			long storyId = storyData.getLong("storyId");
			
			//GET STORY BY STORY ID
			Story story = storyRepo.findStoryByStoryId(storyId);
			
			//GET DEPENDENT TESTCASES BY STORY ID
			List<DependentTestcase>dependentTestcase = dependentTestcaseRepo.findDependentTestcasesByStoryId(story);
			
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
				
				//SAVE STORY TO STORY ARCHIVE
				StoryArchive archiveStory = new StoryArchive(story.getStoryId(), story.getThemeId().getThemeId(), story.getUserId().getUserId(),
						story.getUniverseId().getUniverseId(), story.getStoryName(), story.getDescription(), story.getStatus(),
						story.getTimestamp(), story.getIsIgnoreSeverity(), story.getIsServerImport(), story.getExecutionVersionStart(),
						story.getExecutionVersionCurrent());
				storyArchiveRepo.save(archiveStory);
				
				//COPY DEPENDENT TESTCASE TO ARCHIVE DEPENDENT TESTCASE TABLE
				for(DependentTestcase dependentTestcases : dependentTestcase) {
					if(dependentTestcases.getStoryId().getStoryId() == story.getStoryId()) {
						
						DependentTestcaseArchive archiveDependentTestcase = new DependentTestcaseArchive(dependentTestcases.getTestcaseNumber(),
								story.getStoryId(), dependentTestcases.getUserId().getUserId(), dependentTestcases.getTemplateId().getTemplateId(), 
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
				
				storyRepo.delete(story);
				
				//RETURN SUCCESS MESSAGE
				jsonResponse.put("Info", "Category successfully archived.");
				return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
			}else {
				//RETURN ERROR MESSAGE
				jsonResponse.put("Error", "No Testcases available for archive in the Project");
				return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
			}
			
		}
		
		
		//===========================================================GET THEME LIST==========================================================
		@GET
		@Path("/getstorydata")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getStoryData(@QueryParam("themeId")long themeId) {
			
			//GET ALL THEME BASED ON THEME ID
			List<Story>storyList = storyRepo.findStoriesByThemeId(themeRepo.findThemeByThemeId(themeId));
			
			//GET ALL ARCHIVE STORY LIST
			List<StoryArchive>storyArchive = storyArchiveRepo.findStoryArchiveByThemeId(themeId);
			
			//PUT LIST INTO JSON DATA
			JSONObject themeData = new JSONObject();
			themeData.put("storyData", storyList);
			themeData.put("storyArchive", storyArchive);
			
			//RETURN JSON DATA
			return Response.status(200).type(MediaType.APPLICATION_JSON).entity(themeData.toString()).build();
		}

}
