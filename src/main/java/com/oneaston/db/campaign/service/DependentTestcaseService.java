package com.oneaston.db.campaign.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.oneaston.archive.campaign.domain.DependentTestcaseArchive;
import com.oneaston.archive.campaign.domain.DependentTestcaseIOValueArchive;
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
import com.oneaston.configuration.standalone.dbswap.dbSwap;
import com.oneaston.db.campaign.domain.DependentTestcase;
import com.oneaston.db.campaign.domain.Story;
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
import com.oneaston.db.universe.domain.Client;
import com.oneaston.db.universe.domain.ClientLoginAccount;
import com.oneaston.db.universe.domain.Script;
import com.oneaston.db.universe.domain.ScriptVariable;
import com.oneaston.db.universe.domain.Universe;
import com.oneaston.db.universe.domain.WebAddress;
import com.oneaston.db.universe.repository.ClientLoginAccountRepository;
import com.oneaston.db.universe.repository.ClientRepository;
import com.oneaston.db.universe.repository.ScriptRepository;
import com.oneaston.db.universe.repository.ScriptVariableRepository;
import com.oneaston.db.universe.repository.UniverseRepository;
import com.oneaston.db.universe.repository.WebAddressRepository;
import com.oneaston.db.user.domain.User;
import com.oneaston.db.user.repository.UserRepository;

@Path("/dependenttestcase")
public class DependentTestcaseService {
	
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
	ClientRepository clientRepo;
	
	@Autowired
	ClientLoginAccountRepository clientLoginAccountRepo;
	
	@Autowired
	TemplatesRepository templateRepo;
	
	@Autowired
	TemplateDataRepository templateDataRepo;
	
	@Autowired
	DependentTestcaseIOValueRepository DependentTestcaseIOValueRepo;
	
	@Autowired
	WebAddressRepository webAddressRepo;
	
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
	ScriptRepository scriptRepo;
	
	@Autowired
	ScriptVariableRepository scriptVariableRepo;
	
	@Autowired
	DependentTestcaseIOValueRepository dependentTestcaseIOValueRepo;
	
	@Autowired
	DependentTestcaseIOValueArchiveRepository dependentTestcaseIOValueArchiveRepo;
	
	//PRIVATE FUNCTIONS--------------------------------------------------
	
	//FOR GENERATING TESTCASE NUMBER STRING
	private String generateTestcaseNumber() {
		Date date = new Date();
		String dateFileName;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
		String tempA = dateFormat.format(date).toString();
		dateFileName = tempA;
		return dateFileName;
	}
	
	//DEPENDENT TESTCASE SERVICES------------------------------------------------------
	//================================================================WRITE CSV DATA=====================================================================
	@POST
	@Path("/createdependenttestcase")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createDependentTestcase(String jsonData) throws IOException {
		
		//INSTANTIATE JSON RESPONSE			
		JSONObject jsonResponse = new JSONObject();
		
		//GET CSV DATA FROM THE POST METHOD
		JSONObject credentials = new JSONObject(jsonData);
		
		
		Logger log = Logger.getLogger(getClass());
		
		
		log.info("Access: /dependent testcase");
		
		//GETTING ALL CREDENTIALS FROM JSON DATA-------------------------------
		
		//GET USER ID IN HASHMAP
		User user = userRepo.findUserByUserId(credentials.getLong("userId"));
		
		//GET CLIENT ID IN HASHMAP
		Client client;
		
		try {
			
			client = clientRepo.findClientByClientId(Long.parseLong(credentials.getString("clientId")));
			if(client == null) {
				jsonResponse.put("Error", "Invalid client.");
				return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
			}
			
		}catch(Exception e) {
			jsonResponse.put("Error", "Invalid client.");
			return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		}
		
		//GET TEMPLATE ID IN HASHMAP
		Templates template;
		
		try {
			template = templateRepo.findTemplatesByTemplateId(Long.parseLong(credentials.getString("templateId")));
			if(template == null) {
				jsonResponse.put("Error", "Invalid template.");
				return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
			}
		}catch(Exception e) {
			jsonResponse.put("Error", "Invalid template.");
			return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		}
		
		//GET STORY ID IN HASHMAP
		Story story = storyRepo.findStoryByStoryId(credentials.getLong("storyId"));
		
		//GET UNIVERSE ID IN HASHMAP
		Universe universe = universeRepo.findUniverseByUniverseId(credentials.getLong("universeId"));
		
		//GET WEBSITE ADDRESS ID
		WebAddress webAddress = webAddressRepo.findWebAddressByUrl(credentials.getString("webAddress"));
		
		if(webAddress == null) {
			jsonResponse.put("Error", "Invalid web address.");
			return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		}
		
		//GET CLIENT LOGIN ACCOUNT ID
		ClientLoginAccount clientLoginAccount;
		
		try {
			clientLoginAccount = clientLoginAccountRepo.findClientLoginAccountByLoginAccountId(Long.parseLong(credentials.getString("loginAccountId")));
			if(clientLoginAccount == null) {
				jsonResponse.put("Error", "Invalid client login account.");
				return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
			}
		}catch(Exception e) {
			jsonResponse.put("Error", "Invalid client login account.");
			return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		}
		
		//GET DEPENDENT TESTCASE COUNT IN STORY
		List<DependentTestcase>dTestcaseCount = dependentTestcaseRepo.findDependentTestcasesByStoryId(story);
		
		//SAVE DEPENDENT TESTCASE DATA TO DB
		DependentTestcase dependentTestcase = new DependentTestcase(generateTestcaseNumber(), story, user, template, universe,
				client, webAddress, clientLoginAccount, credentials.getString("description"), "Pending", story.getThemeId().getCampaignId().getExecutionVersionCurrent(), 
				story.getThemeId().getCampaignId().getExecutionVersionCurrent(), template.getTemplateVersion());
		dependentTestcase.setTapImportStatus(credentials
				.getString("tapImportStatus") != null ? credentials.getString("tapImportStatus") : null);
		dependentTestcase.setPriority(dTestcaseCount.size() + 1);
		dependentTestcaseRepo.save(dependentTestcase);
		
		//GET INPUT OUT VALUE IN CREDENTIALS
		JSONArray inputOutputValue = credentials.getJSONArray("inputOutputValue");
		
		//GET TEMPLATE DATA ID IN CREDENTIALS
		JSONArray templateDataId = credentials.getJSONArray("templateDataId");
		
		//SAVE INPUT OUTPUT VALUE IN IO_VALUE TABLE
		for(int i=0; i<inputOutputValue.length(); i++) {
			
			TemplateData templateData = templateDataRepo.findTemplateDataByTemplateDataId(templateDataId.getLong(i));
			
			if(!inputOutputValue.getString(i).equals("")) {
				
				DependentTestcaseIOValue ioValue = new DependentTestcaseIOValue(dependentTestcase,
						templateData, inputOutputValue.getString(i));
				DependentTestcaseIOValueRepo.save(ioValue);
			}
			
		}
		
		
		
		//RETURN SUCCESS RESPONSE
		jsonResponse.put("Info", "Testcase successfully created.");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	//=============================================================ARCHIVE DEPENDENT TESTCASE=================================================================
	@POST
	@Path("/archivedependenttestcase")
	@Produces(MediaType.APPLICATION_JSON)
	public Response archiveDependentTestcase(String jsonData) {
		
		//INSTANTIATE JSON RESPONSE OBJECT
		JSONObject jsonResponse = new JSONObject();
		
		//GET TESTCASE NUMBER IN JSON DATA
		JSONObject dependentTestcaseData = new JSONObject(jsonData);
		String testcaseNumber = dependentTestcaseData.getString("testcaseNumber");
		
		//GET DEPENDENT TESTCASE BY TESTCASE NUMBER
		DependentTestcase dependentTestcase = dependentTestcaseRepo.findDependentTestcaseByTestcaseNumber(testcaseNumber);
		
		//GET DEPENDENT TESTCASE IO VALUE BY DEPENDENT TSTCASE
		List<DependentTestcaseIOValue>ioValueList = dependentTestcaseIOValueRepo.findIOValuesByTestcaseNumber(dependentTestcase);
		
		//GET TESTCASE RECORD BY DEPENDENT TESTCASE TESTCASE NUMBER
		List<TestcaseRecord>testcaseRecordList = testcaseRecordRepo.findTestcaseRecordsByTestcaseNumber(dependentTestcase);
		
		//GET TESTCASE ACTUAL DATA BY DEPENDENT TESTCASE TESTCASE NUMBER
		List<TestcaseActualData>testcaseActualDataList = testcaseActualDataRepo.findTestcaseActualDatasByTestcaseNumber(dependentTestcase);
		
		//GET TESTCASE FOOTER DATA BY DEPENDENT TESTCASE TESTCASE NUMBER
		List<TestcaseFooterData>testcaseFooterDataList = testcaseFooterDataRepo.findTestcaseFooterDatasByTestcaseNumber(dependentTestcase);
		
		DependentTestcaseArchive archiveDependentTestcase = new DependentTestcaseArchive(dependentTestcase.getTestcaseNumber(),
				dependentTestcase.getStoryId().getStoryId(), dependentTestcase.getUserId().getUserId(), dependentTestcase.getTemplateId().getTemplateId(), 
				dependentTestcase.getUniverseId().getUniverseId(), dependentTestcase.getWebAddressId().getWebAddressId(),
				dependentTestcase.getClientId().getClientId(), dependentTestcase.getLoginAccountId().getLoginAccountId(), 
				dependentTestcase.getDescription(), dependentTestcase.getStatus(), dependentTestcase.getTemplateVersion(),
				dependentTestcase.getExecutionVersionStart(), dependentTestcase.getExecutionVersionCurrent());
		dependentTestcaseArchiveRepo.save(archiveDependentTestcase);
		
		//COPY DEPENDENT TESTCASE IO VALUE IN ARCHIVE DEPENDENT TESTCASE IO VALUE
		for(DependentTestcaseIOValue ioValues : ioValueList) {
			if(ioValues.getTestcaseNumber().getTestcaseNumber().equals(dependentTestcase.getTestcaseNumber())) {
				DependentTestcaseIOValueArchive ioValueArchive = new DependentTestcaseIOValueArchive(ioValues.getIoId(), ioValues.getTestcaseNumber().getTestcaseNumber(),
						ioValues.getTemplateDataId().getTemplateDataId(), ioValues.getIoValue());
				dependentTestcaseIOValueArchiveRepo.save(ioValueArchive);
			}
		}
		
		//COPY TESTCASE RECORD IN ARCHIVE TESTCASE RECORD TABLE
		for(TestcaseRecord testcaseRecords : testcaseRecordList) {
			if(testcaseRecords.getTestcaseNumber().getTestcaseNumber().equals(dependentTestcase.getTestcaseNumber())) {
				TestcaseRecordArchive archiveTestcaseRecord = new TestcaseRecordArchive(testcaseRecords.getRecordId(),
						testcaseRecords.getClientId().getClientId(), testcaseNumber, testcaseRecords.getUserId().getUserId(),
						testcaseRecords.getUniverseId().getUniverseId(), testcaseRecords.getClientId().getClientName(), testcaseRecords.getDescription(),
						testcaseRecords.getStatus(), testcaseRecords.getExecutionSchedule(), testcaseRecords.getExecutionVersion());
				testcaseRecordArchiveRepo.save(archiveTestcaseRecord);
			}
		}
		//COPY TESTCASE ACTUAL DATA IN ARCHIVE TESTCASE ACTUAL DATA
		for(TestcaseActualData testcaseActualDatas : testcaseActualDataList) {
			if(testcaseActualDatas.getTestcaseNumber().getTestcaseNumber().equals(dependentTestcase.getTestcaseNumber())) {
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
		for(TestcaseFooterData testcaseFooterDatas : testcaseFooterDataList) {
			if(testcaseFooterDatas.getTestcaseNumber().getTestcaseNumber().equals(dependentTestcase.getTestcaseNumber())) {
				TestcaseFooterDataArchive archiveTestcaseFooterData = new TestcaseFooterDataArchive(testcaseFooterDatas.getFooterDataId(),
						testcaseNumber, testcaseFooterDatas.getAssignedAccount(), testcaseFooterDatas.getClientName(),
						testcaseFooterDatas.getIsIgnoreSeverity(), testcaseFooterDatas.getSender(), testcaseFooterDatas.getIsServerimport(),
						testcaseFooterDatas.getTestcaseStatus(), testcaseFooterDatas.getTransactionType(), testcaseFooterDatas.getUrl(),
						testcaseFooterDatas.getExecutionVersion());
				testcaseFooterDataArchiveRepo.save(archiveTestcaseFooterData);
			}
		}
		
		for(DependentTestcaseIOValue ioValue : ioValueList) {
			dependentTestcaseIOValueRepo.delete(ioValue);
		}
		
		for(TestcaseActualData actualData : testcaseActualDataList) {
			testcaseActualDataRepo.delete(actualData);
		}
		
		for(TestcaseFooterData footerData : testcaseFooterDataList) {
			testcaseFooterDataRepo.delete(footerData);
		}
		
		for(TestcaseRecord records : testcaseRecordList) {
			testcaseRecordRepo.delete(records);
		}
		
		dependentTestcaseRepo.delete(dependentTestcase);
			
		//RETURN SUCCESS MESSAGE
		jsonResponse.put("Info", "Dependent testcase successfully deleted.");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	@POST
	@Path("/swapdependenttestcase")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response swapDependentTestcase(String json) {
		
		Logger log = Logger.getLogger(getClass());
		log.info(json);
		
		//GET DATA FROM JSON
		JSONObject jsonData = new JSONObject(json);
		
		//GET DEPENDENT TESTCASE STORY ID\
		long storyId = dependentTestcaseRepo.findDependentTestcaseByTestcaseNumber(jsonData.getString("testcaseNumber1")).getStoryId().getStoryId();
		
		//CAL DB SWAP
		dbSwap dbSwapJr = new dbSwap();
		dbSwapJr.rowSwap(jsonData.getString("testcaseNumber1"), jsonData.getString("testcaseNumber2"), jsonData.getString("testcaseNumber3"), storyId);
		
		//RETURN RESPONSE
		return Response.status(200).entity("Testcase successfully swapped.").build();
	}
	
	//======================================================GET TEMPLATE DATA AND IO VALUE OF DEPENDENT TESTCASE==============================================
	@POST
	@Path("/getmodifytestcasedata")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModifyTestcaseData(String json) {
		
		Logger log = Logger.getLogger(getClass());
		log.info("Access:/getmodifytestcasedata");
		
		//GET JSON DATA FROM JSON
		JSONObject jsonData = new JSONObject(json);
		
		//GET DEPENDENT TESTCASE IN JSON
		DependentTestcase testcaseNumber = dependentTestcaseRepo.findDependentTestcaseByTestcaseNumber(jsonData.getString("testcaseNumber"));
		
		log.info(testcaseNumber.getTestcaseNumber());
		
		//GET TEMPLATE DATA BY TESTCASE NUMBER TEMPLATES ID
		List<TemplateData>templateDataTempList = templateDataRepo.findTemplateDatasByTemplateId(testcaseNumber.getTemplateId());
		
		log.info("Temp List size: " + templateDataTempList.size());
		
		//GET IO VALUE BY TESTCASE NUMBER
		List<DependentTestcaseIOValue>ioValueList = dependentTestcaseIOValueRepo.findIOValuesByTestcaseNumber(testcaseNumber);
		
		log.info("io value size: " + ioValueList.size());
		
		//MATCH IO VALUE IN TEMPLATE DATA
		List<TemplateData>templateDataList = new ArrayList<TemplateData>();
		for(TemplateData templateData : templateDataTempList) { 
			for(DependentTestcaseIOValue ioValue : ioValueList) {
				if(ioValue.getTemplateDataId().getTemplateDataId() == templateData.getTemplateDataId()) {
					templateData.setInputOutputValue(ioValue.getIoValue());
				}
			}
			templateDataList.add(templateData);
		}
		
		List<Script>scriptList = new ArrayList<Script>();
		
		try {
			//GET ALL SCRIPTS IN DEPENDENT TESTCASE
			String[] embedScriptArray = testcaseNumber.getEmbeddedScript().split(",");
			
			log.info(embedScriptArray.length);
			
			for(int i=0; i<embedScriptArray.length; i++) {
				
				//FIND SCRIPT BY ID
				Script script = scriptRepo.findScriptByScriptId(Long.parseLong(embedScriptArray[i]));
				
				//ADD SCRIPT
				scriptList.add(script);
				
			}
		}catch(Exception e) {/**/}
		
		
		//PUT DATA IN JSON OBJECT
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("templateDataList", templateDataList);
		jsonResponse.put("scriptList", scriptList);
		jsonResponse.put("isServerImport", testcaseNumber.getStoryId().getIsServerImport());
		jsonResponse.put("tapImportStatus", testcaseNumber.getTapImportStatus());
		jsonResponse.put("description", testcaseNumber.getDescription());
		
		//RETURN JSON RESPONSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	//============================================================MODIFY DEPENDENT TESTCASE===================================================================
	@POST
	@Path("/modifydependenttestcase")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyDependentTestcase(String json) {
		
		Logger log = Logger.getLogger(getClass());
		log.info("Access: /Modify Dependent Testcase");
		
		//GET JSON DATA FROM JSON STRING
		JSONObject jsonData = new JSONObject(json);
		
		//GET DEPENDENT TESTCASE BY TESTCASE NUMBER
		DependentTestcase dependentTestcase = dependentTestcaseRepo.findDependentTestcaseByTestcaseNumber(jsonData.getString("testcaseNumber"));
		
		//GET TEMPLATE BY TEMPLATE ID
		Templates templatesData = templatesRepo.findTemplatesByTemplateId(dependentTestcase.getTemplateId().getTemplateId());
		
		//SET TEMPLATE VERSION OF TESTCASE TO UPDATED ONE
		dependentTestcase.setTemplateVersion(templatesData.getTemplateVersion());
		dependentTestcase.setTemplateId(templatesData);
		try {
			dependentTestcase.setTapImportStatus(jsonData.getString("tapImportStatus"));
		}catch(Exception e) {
			dependentTestcase.setTapImportStatus("-1");
		}
		
		dependentTestcase.setDescription(jsonData.getString("description"));

		//SAVE CHANGES
		dependentTestcaseRepo.save(dependentTestcase);
		
		//GET INPUT OUT VALUE IN CREDENTIALS
		JSONArray inputOutputValue = jsonData.getJSONArray("inputOutputValue");
		
		//GET TEMPLATE DATA ID IN CREDENTIALS
		JSONArray templateDataId = jsonData.getJSONArray("templateDataId");
		
		//FIND DEPENDENT IO VALUE BY TESTCASE NUMBER
		List<DependentTestcaseIOValue>ioValueList = dependentTestcaseIOValueRepo.findIOValuesByTestcaseNumber(dependentTestcase);
		
		log.info(ioValueList.size());
		log.info(inputOutputValue.length() + ": " + templateDataId.length());
		
		//ITERATE IO VALUE LIST
		ioValueLoop:
		for(int i=0; i<ioValueList.size(); i++) {
			
			DependentTestcaseIOValue ioValue = ioValueList.get(i);
			
			for(int j=0; j<templateDataId.length(); j++) {
				
				
				if(ioValue.getTemplateDataId().getTemplateDataId() == templateDataId.getLong(j)) {
					log.info(ioValue.getTemplateDataId().getTemplateDataId() + ": " + templateDataId.getInt(j) + ": " + inputOutputValue.getString(i));
					ioValue.setIoValue(inputOutputValue.getString(i));
					dependentTestcaseIOValueRepo.save(ioValue);
					continue ioValueLoop;
				}
			}
			
			
			
		}
		
		//INSTANTIATE JSON RESPONSE
		JSONObject jsonResponse = new JSONObject();
		
		//RETURN SUCCESS RESPONSE
		jsonResponse.put("Info", "Testcase successfully modified.");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		
	}
	
	//==================================================MODIFY SCRIPT OF DEPENDENT TESTCASE===================================================================
	@POST
	@Path("/deleteembedscript")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteEmbedScript(String json) {
		
		Logger log = Logger.getLogger(getClass());
		
		//INSTANTIATE JSON RESPONSE
		JSONObject jsonResponse = new JSONObject();
		
		//GET DATA FROM JSON
		JSONObject jsonData = new JSONObject(json);
		
		//GET DEPENDENT TESTCASE BY TESTCASE NUMBER
		DependentTestcase dependentTestcase = dependentTestcaseRepo.findDependentTestcaseByTestcaseNumber(jsonData.getString("testcaseNumber"));
		
		log.info(jsonData.toString());
		
		//GET EMBED SCRIPT
		String[]embeddedScript = dependentTestcase.getEmbeddedScript().split(",");
		
		
		String[]storedValues;
		List<String>storedValuesList = null;
		try {
			//GET STORED VALUE
			storedValues = dependentTestcase.getStoredValues().split(",");
			storedValuesList = new ArrayList<String>(Arrays.asList(storedValues));
		}catch(Exception e) {}
		
		List<String>embeddedScriptList = new ArrayList<String>(Arrays.asList(embeddedScript));
		
		
		//TEMPORARY COUNTER HOLDER
		int ctr = 0;
		
		//LOOP THROUGH EMBEDDED SCRIPT ARRAY
		
		outer:
		for(int i=0; i<embeddedScriptList.size(); i++) {
			
			System.out.println(ctr);
			
			if(i == jsonData.getInt("rowCount")) {
				
				//GET SCRIPT TO BE DELETED
				Script scriptToBeDeleted = scriptRepo.findScriptByScriptId(Long.parseLong(embeddedScriptList.get(i)));
				
				//GET VALUE LIST OF SCRIPT TO BE DELETED
				List<ScriptVariable>scriptVariableList = scriptVariableRepo.findScriptVariablesByScriptId(scriptToBeDeleted);
				
				//INSTANTIATE SIZE OF VARIABLE
				int variableSize = scriptVariableList.size();
				
				
				embeddedScriptList.remove(i);
				
				try {
					for(int j=0; j < variableSize; j++) {
						
						storedValuesList.remove(ctr);
					}
				}catch(Exception e) {}
				
				
				System.out.println("hahahaha");
				
				break outer;
			}else {
				ctr += scriptVariableRepo.findScriptVariablesByScriptId(scriptRepo.findScriptByScriptId(Long.parseLong(embeddedScriptList.get(i)))).size();
			}
		}
		
		String[] newStoredValues = null;
		
		try {
			newStoredValues = new String[storedValuesList.size()];
			for(int i=0; i<storedValuesList.size(); i++) {
				newStoredValues[i] = storedValuesList.get(i);
			}
		}catch(Exception e) {}
		
		
		
		String[] newEmbedScript = new String[embeddedScriptList.size()];
		
		for(int i=0; i<embeddedScriptList.size(); i++) {
			newEmbedScript[i] = embeddedScriptList.get(i);
		}
		
		
		System.out.print("Stored values : " + Arrays.toString(newStoredValues).replaceAll(" ", "").replace("[", "").replace("]", "").replaceAll("\"", "").trim());
		
		System.out.print("Embedded values : " + Arrays.toString(newEmbedScript).replaceAll(" ", "").replace("[", "").replace("]", "").replaceAll("\"", "").trim());
		
		dependentTestcase.setStoredValues(Arrays.toString(newStoredValues).replaceAll(" ", "").replace("[", "").replace("]", "").replaceAll("\"", "").trim());
		dependentTestcase.setEmbeddedScript(Arrays.toString(newEmbedScript).replaceAll(" ", "").replace("[", "").replace("]", "").replaceAll("\"", "").trim());
		
		dependentTestcaseRepo.save(dependentTestcase);
		
		//RETURN RESPONSE
		jsonResponse.put("Info", "Sucess");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	//===================================================ADD SCRIPT TO DEPENDENT TESTCASE FUNCTION============================================================
	@POST
	@Path("/addscripttotestcase")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addScriptToTestcase(String json) {
		
		//INSTANTIATE JSON RESPONSE
		JSONObject jsonResponse = new JSONObject();
		
		//GET DATA FROM JSON
		JSONObject jsonData = new JSONObject(json);
		
		//FIND DEPENDENT TESTCASE BY TESTCASE NUMBER
		DependentTestcase dTestcase = dependentTestcaseRepo.findDependentTestcaseByTestcaseNumber(jsonData.getString("testcaseNumber"));
		
		if(dTestcase.getEmbeddedScript().equals(null)) {
			dTestcase.setEmbeddedScript(jsonData.getString("embeddedScript"));
		}else {
			String formatEmbeddedScript = String.format("%s,%s", dTestcase.getEmbeddedScript(), jsonData.getString("embeddedScript"));
			dTestcase.setEmbeddedScript(formatEmbeddedScript);
		}
		
		//RETURN JSON RESPONSE
		jsonResponse.put("Info", "Script successfully embedded.");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	//=======================================================GET DEPENDENT TESTCASE BY STORY ID========================================================
	@GET
	@Path("/getdependenttestcase")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDependentTestcase(@QueryParam("storyId")long storyId) {
		
		//GET ALL DEPENDENT TESTCASE BASED ON STORY ID
		List<DependentTestcase>dependentTestcaseList = dependentTestcaseRepo.findAllDependentTestcaseOrderByPriority(storyRepo.findStoryByStoryId(storyId));
		
		//GET ALL DEPENDENT TESTCASE ARCHIVE LIST
		List<DependentTestcaseArchive>dependentTestcaseArchive = dependentTestcaseArchiveRepo.findDependentTestcaseArchiveByStoryId(storyId);
		
		//PUT LIST IN JSON DATA
		JSONObject dependentTestcaseData = new JSONObject();
		dependentTestcaseData.put("dependentTestcaseData", dependentTestcaseList);
		dependentTestcaseData.put("dependentTestcaseArchive", dependentTestcaseArchive);
		
		//RETURN JSON DATA
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(dependentTestcaseData.toString()).build();
	}
	
	//========================================================GET ALL DEPENDENT TESTCASE==============================================================
	@GET
	@Path("/getalldependenttestcase")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllDependentTestcase() {
		//GET ALL DEPENDENT TESTCASE BASED ON STORY ID
		List<DependentTestcase>dependentTestcaseList = dependentTestcaseRepo.findAll();
		
		//PUT LIST IN JSON DATA
		JSONObject dependentTestcaseData = new JSONObject();
		dependentTestcaseData.put("dependentTestcaseData", dependentTestcaseList);
		
		//RETURN JSON DATA
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(dependentTestcaseData.toString()).build();
	}
	
	//=================================================GET DEPENDENT TESTCASE BY TESTCASE NUMBER==============================================================
	@GET
	@Path("/getsolodependenttestcase")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSoloDependentTestcase(@QueryParam("testcaseNumber")String testcaseNumber) {
		
		Logger log = Logger.getLogger(getClass());
		
		//GET DEPENDENT TESTCASE BU TESTCASE NUMBER
		DependentTestcase dTestcase = dependentTestcaseRepo.findDependentTestcaseByTestcaseNumber(testcaseNumber);
		
		//GET DEPENDENT TESTCASE DATA NEEDED AND PUT TO JSON
		JSONObject testcaseData = new JSONObject();
		testcaseData.put("clientName", dTestcase.getClientId().getClientName());
		testcaseData.put("description", dTestcase.getDescription());
		testcaseData.put("testcaseNumber", dTestcase.getTestcaseNumber());
		testcaseData.put("username", dTestcase.getUserId().getUsername());
		
		log.info(dTestcase.getExecutionVersionCurrent());
		
		//GET ACTUAL DATA LIST BY DEPENDENT TESTCASE
		List<TestcaseActualData>actualData = testcaseActualDataRepo.findTestcaseActualDatasByTestcaseNumberAndExecutionVersion(dTestcase,
				dTestcase.getExecutionVersionCurrent());
		
		//GET FOOTER DATA BY DEPENDENT TESTCASE
		TestcaseFooterData footerData = testcaseFooterDataRepo.findTestcaseFooterDataByTestcaseNumberAndExecutionVersion(dTestcase, 
				dTestcase.getExecutionVersionCurrent());
		
		//GET FOOTER DATA NEEDED AND PUT TO JSON
		JSONObject footerJson = new JSONObject();
		footerJson.put("sender", footerData.getSender());
		
		
		//PUT OBJECTS IN JSON DATA
		JSONObject jsonData = new JSONObject();
		jsonData.put("dependentTestcase", testcaseData);
		jsonData.put("actualData", actualData);
		jsonData.put("footerData", footerJson);
		
		//RETURN JSON DATA
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonData.toString()).build();
	}
	
	//========================================================GET DEPENDENT TESTCASE BY USERNAME==============================================================
	@GET
	@Path("/gettestcasebyusername")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTestcaseByUserId(@QueryParam("username")String username,
			@QueryParam("universeId")long universeId) {
		
		//GET DEPENDENT TESTCASES BY USERNAME
		List<DependentTestcase>dependentTestcaseUsernameList = dependentTestcaseRepo
				.findDependentTestcasesByUserId(userRepo.findUserByUsername(username));
		
		//GET DEPENDENT TESTCASES BY UNIVERSE ID
		List<DependentTestcase>dependentTestcaseUniverseList = dependentTestcaseRepo
				.findDependentTestcaseByUniverseId(universeRepo.findUniverseByUniverseId(universeId));
		
		//GET TESTCASE BY USERNAME
		List<TestcaseRecord>testcaseRecordUsernameList = new ArrayList<TestcaseRecord>();
		for(DependentTestcase testcaseNumber : dependentTestcaseUsernameList) {
			
			List<TestcaseRecord>testcaseRecordList = testcaseRecordRepo.findTestcaseRecordsByTestcaseNumber(testcaseNumber);
			for(TestcaseRecord record : testcaseRecordList) {
				testcaseRecordUsernameList.add(record);
			}
			
		}
		
		//GET TESTCASE BY UNIVERSE ID
		List<TestcaseRecord>testcaseRecordUniverseList = new ArrayList<TestcaseRecord>();
		for(DependentTestcase testcaseNumber : dependentTestcaseUniverseList) {
			
			List<TestcaseRecord>testcaseRecordList = testcaseRecordRepo.findTestcaseRecordsByTestcaseNumber(testcaseNumber);
			for(TestcaseRecord record : testcaseRecordList) {
				testcaseRecordUniverseList.add(record);
			}
			
		}
		
		//PUT TESTCASES IN JSON DATA
		JSONObject jsonData = new JSONObject();
		jsonData.put("dependentTestcaseUsernameList", testcaseRecordUsernameList);
		jsonData.put("dependentTestcaseUniverseList", testcaseRecordUniverseList);
				
		//RETURN JSON DATA
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonData.toString()).build();
	}
	
	
	/*
	public void writeCsv() {
		
		//INSTANTIATE THE LENGTH OF THE COLUMNS
		final int columnLength = 11;
		
		//INSTANTIATE LIST OF STRING ARRAY FOR DATA WRITING
		List<String[]>dataToWrite = new ArrayList<String[]>();
		
		//STRING FORMAT FOR CSV PATH
		String csvPath = String.format("%s\\%s.csv", StoragePathBean.CSVHOLDER_FOLDER, generateTestcaseNumber());
		
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
		
		//FOR CSV BODY
		for(int i=0; i<webElementName.length(); i++) {
			String[]csvBody = new String[columnLength];
			csvBody[0] = (String) webElementName.get(i);
			csvBody[1] = (String) webElementNature.get(i);
			csvBody[2] = (String) natureOfAction.get(i);
			csvBody[3] = (String) screenCapture.get(i);
			csvBody[4] = (String) triggerEnter.get(i);
			csvBody[5] = (String) inputOutputValue.get(i);
			csvBody[6] = (String) label.get(i);
			dataToWrite.add(csvBody);
		}
		
		//FOR CSV FOOTER
		
		//CLIENT NAME
		String[]clientNameFooter = new String[columnLength];
		clientNameFooter[0] = "ClientName";
		clientNameFooter[1] = client.getClientName();
		dataToWrite.add(clientNameFooter);
		
		//TRANSACTION TYPE
		String[]transactionType = new String[columnLength];
		transactionType[0] = "TransactionType";
		transactionType[1] = template.getTemplateName();
		dataToWrite.add(transactionType);
		
		//WEBSITE
		String[]website = new String[columnLength];
		website[0] = "Website";
		website[1] = webAddress.getUrl();
		dataToWrite.add(website);
		
		//SERVER IMPORT
		String[]serverImportFooter = new String[columnLength];
		serverImportFooter[0] = "ServerImport";
		serverImportFooter[1] = story.getIsServerImport() ? "on": "off";
		dataToWrite.add(serverImportFooter);
		
		//IGNORE SEVERITY
		String[]ignoreSeverityFooter = new String[columnLength];
		ignoreSeverityFooter[0] = "IgnoreSeverity";
		ignoreSeverityFooter[1] = story.getIsIgnoreSeverity() ? "on" : "off";
		dataToWrite.add(ignoreSeverityFooter);
		
		//ASSIGNED ACCOUNT
		String[]assignedAccountFooter = new String[columnLength];
		assignedAccountFooter[0] = "AssignedAccount";
		assignedAccountFooter[1] = idCredentials.get(6).toString();
		dataToWrite.add(assignedAccountFooter);
		
		//SENDER
		String[]sender = new String[columnLength];
		sender[0] = "Sender";
		sender[1] = user.getUsername();
		dataToWrite.add(sender);
		
		//TESTCASE NUMBER
		String[]testcaseNumberFooter = new String[columnLength];
		testcaseNumberFooter[0] = "TestcaseNumber";
		testcaseNumberFooter[1] = generateTestcaseNumber();
		dataToWrite.add(testcaseNumberFooter);
		
		//TESTCASE STATUS
		String[]testcaseStatusFooter = new String[columnLength];
		testcaseStatusFooter[0] = "TestCaseStatus";
		dataToWrite.add(testcaseStatusFooter);
		
		//PASS DATA TO WRITE LIST TO CSV WRITER
		csvWriter.writeAll(dataToWrite);
		csvWriter.close();
	}*/

}
