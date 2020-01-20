package com.oneaston.configuration.standalone;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneaston.archive.campaign.repository.CampaignArchiveRepository;
import com.oneaston.archive.campaign.repository.DependentTestcaseArchiveRepository;
import com.oneaston.archive.campaign.repository.DependentTestcaseIOValueArchiveRepository;
import com.oneaston.archive.campaign.repository.StoryArchiveRepository;
import com.oneaston.archive.campaign.repository.ThemeArchiveRepository;
import com.oneaston.archive.testcase.repository.TestcaseActualDataArchiveRepository;
import com.oneaston.archive.testcase.repository.TestcaseFooterDataArchiveRepository;
import com.oneaston.archive.testcase.repository.TestcaseRecordArchiveRepository;
import com.oneaston.configuration.prepstatement.UpdateTemplatesStatement;
import com.oneaston.configuration.storage.JsonStorage;
import com.oneaston.db.campaign.domain.DependentTestcase;
import com.oneaston.db.campaign.repository.CampaignRepository;
import com.oneaston.db.campaign.repository.DependentTestcaseRepository;
import com.oneaston.db.campaign.repository.StoryRepository;
import com.oneaston.db.campaign.repository.ThemeRepository;
import com.oneaston.db.template.repository.DependentTestcaseIOValueRepository;
import com.oneaston.db.template.repository.TemplateDataRepository;
import com.oneaston.db.template.repository.TemplatesRepository;
import com.oneaston.db.testcase.repository.TestcaseActualDataRepository;
import com.oneaston.db.testcase.repository.TestcaseFooterDataRepository;
import com.oneaston.db.testcase.repository.TestcaseRecordRepository;
import com.oneaston.db.universe.repository.ClientLoginAccountRepository;
import com.oneaston.db.universe.repository.ClientRepository;
import com.oneaston.db.universe.repository.HeadersRepository;
import com.oneaston.db.universe.repository.UniverseRepository;
import com.oneaston.db.universe.repository.WebAddressRepository;
import com.oneaston.db.user.repository.UserRepository;

@Service
public class SaveArchiveFunction {
	
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
	ClientRepository clientRepo;
	
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
	WebAddressRepository webAddressRepo;
	
	@Autowired
	ClientLoginAccountRepository clientLoginAccountRepo;
	
	@Autowired
	HeadersRepository headerRepo;
	
	public void saveArchive() {
		
		
		
		Logger log = Logger.getLogger(getClass());
		
		JSONArray campaignArchiveArray = null;
		JSONArray themeArchiveArray = null;
		JSONArray storyArchiveArray = null;
		JSONArray dTestcaseArchiveArray = null;
		JSONArray ioValueArray = null;
		JSONArray testcaseRecordArray = null;
		JSONArray footerDataArray = null;
		JSONArray actualDataArray = null;
		
		//JSON ARRAYS
		try {
			campaignArchiveArray = JsonStorage.getInstance().getTempMapJson().getJSONArray("campaign");
		}catch(Exception e) {/**/}
		
		try {
			themeArchiveArray = JsonStorage.getInstance().getTempMapJson().getJSONArray("theme");
		}catch(Exception e) {/**/}
		
		try {
			storyArchiveArray = JsonStorage.getInstance().getTempMapJson().getJSONArray("story");
		}catch(Exception e) {/**/}
		
		try {
			dTestcaseArchiveArray = JsonStorage.getInstance().getTempMapJson().getJSONArray("DependentTestcase");
		}catch(Exception e) {/**/}
		
		try {
			ioValueArray = JsonStorage.getInstance().getTempMapJson().getJSONArray("ioValBean");
		}catch(Exception e) {/**/}
		
		try {
			testcaseRecordArray = JsonStorage.getInstance().getTempMapJson().getJSONArray("testCaseRecord");
		}catch(Exception e) {/**/}
		
		try {
			footerDataArray = JsonStorage.getInstance().getTempMapJson().getJSONArray("footerData");
		}catch(Exception e) {/**/}
		
		try {
			actualDataArray = JsonStorage.getInstance().getTempMapJson().getJSONArray("actualData");
		}catch(Exception e) {/**/}
		
		
		
		
		
		log.info(JsonStorage.getInstance().getTempMapJson().toString());
		
		//SAVING PROCESS
		try {
			for(int i=0; i<campaignArchiveArray.length(); i++) {
				
				JSONObject campaignData = campaignArchiveArray.getJSONObject(i);
				log.info("Iter " + i + ": " + userRepo.findUserByUserId(campaignData.getLong("userId")).getUsername());
				log.info(campaignData.toString());
				
				
				LinkedHashMap<String, Object>campaignHashmap = new LinkedHashMap<String,Object>();
				
				campaignHashmap.put("campaign_id", campaignData.get("campaignId"));
				campaignHashmap.put("user_id", campaignData.get("userId"));
				campaignHashmap.put("universe_id", campaignData.get("universeId"));
				campaignHashmap.put("campaign_name", campaignData.get("campaignName"));
				campaignHashmap.put("description", campaignData.get("description"));
				campaignHashmap.put("status", campaignData.get("status"));
				campaignHashmap.put("timestamp", campaignData.get("timestamp"));
				campaignHashmap.put("execution_version_current", campaignData.get("executionVersionCurrent"));
				
				UpdateTemplatesStatement saveFunction = new UpdateTemplatesStatement();
				saveFunction.dataBaseController(2, null, null, false, false, null, null, 0, campaignHashmap, null, null, null, null, null, null, null, 0);
				
			}
		}catch(Exception e) {
			System.out.println("Error: no Campaign detected");
		}
		
		try {
			for(int i=0; i<themeArchiveArray.length(); i++) {
				
				JSONObject themeData = themeArchiveArray.getJSONObject(i);
				
				LinkedHashMap<String, Object>themeHashmap = new LinkedHashMap<String, Object>();
				
				themeHashmap.put("theme_id", themeData.get("themeId"));
				themeHashmap.put("campaign_id", themeData.get("campaignId"));
				themeHashmap.put("user_id", themeData.get("userId"));
				themeHashmap.put("universe_id", themeData.get("universeId"));
				themeHashmap.put("theme_name", themeData.get("themeName"));
				themeHashmap.put("description", themeData.get("description"));
				themeHashmap.put("status", themeData.get("status"));
				themeHashmap.put("timestamp", themeData.get("timestamp"));
				themeHashmap.put("execution_version_start", themeData.get("executionVersionStart"));
				themeHashmap.put("execution_version_current", themeData.get("executionVersionCurrent"));
				
				UpdateTemplatesStatement saveFunction = new UpdateTemplatesStatement();
				saveFunction.dataBaseController(3, null, null, false, false, null, null, 0, null, themeHashmap, null, null, null, null, null, null, 0);
				
			}
		}catch(Exception e) {
			System.out.println("Error: no Stream detected");
		}
		
		
		try {
			for(int i=0; i<storyArchiveArray.length(); i++) {
				
				JSONObject storyData = storyArchiveArray.getJSONObject(i);
				
				
				LinkedHashMap<String, Object>storyHashmap = new LinkedHashMap<String, Object>();
				
				storyHashmap.put("story_id", storyData.get("storyId"));
				storyHashmap.put("theme_id", storyData.get("themeId"));
				storyHashmap.put("user_id", storyData.get("userId"));
				storyHashmap.put("universe_id", storyData.get("universeId"));
				storyHashmap.put("story_name", storyData.get("storyName"));
				storyHashmap.put("description", storyData.get("description"));
				storyHashmap.put("status", storyData.get("status"));
				storyHashmap.put("timestamp", storyData.get("timestamp"));
				storyHashmap.put("is_ignore_severity", storyData.get("isIgnoreSeverity"));
				storyHashmap.put("is_server_import", storyData.get("isServerImport"));
				storyHashmap.put("execution_version_start", storyData.get("executionVersionStart"));
				storyHashmap.put("execution_version_current", storyData.get("executionVersionCurrent"));
				
				UpdateTemplatesStatement saveFunction = new UpdateTemplatesStatement();
				saveFunction.dataBaseController(4, null, null, false, false, null, null, 0, null, null, storyHashmap, null, null, null, null, null, 0);
				
			}
		}catch(Exception e) {
			System.out.println("Error: no Category detected");
		}
		
		try {
			for(int i=0; i<dTestcaseArchiveArray.length(); i++) {
				
				
				JSONObject dTestcaseData = dTestcaseArchiveArray.getJSONObject(i);
				
				List<DependentTestcase>dependentTestcaseList = dependentTestcaseRepo.findAllDependentTestcaseOrderByPriority(storyRepo.findStoryByStoryId(dTestcaseData.getLong("storyId")));
				
				long priority;
				
				if(dependentTestcaseList.size() == 0) {
					priority = 1;
				}else {
					priority = dependentTestcaseList.get(dependentTestcaseList.size() - 1).getPriority() + 1;
				}
				
				LinkedHashMap<String, Object>dTestcaseHashmap = new LinkedHashMap<String, Object>();
				
				
				dTestcaseHashmap.put("testcase_number", dTestcaseData.get("testcaseNumber"));
				dTestcaseHashmap.put("story_id", dTestcaseData.get("storyId"));
				dTestcaseHashmap.put("user_id", dTestcaseData.get("userId"));
				dTestcaseHashmap.put("template_id", dTestcaseData.get("templateId"));
				dTestcaseHashmap.put("universe_id", dTestcaseData.get("universeId"));
				dTestcaseHashmap.put("web_address_id", dTestcaseData.get("webAddressId"));
				dTestcaseHashmap.put("login_account_id", dTestcaseData.get("loginAccountId"));
				dTestcaseHashmap.put("client_id", dTestcaseData.get("clientId"));
				dTestcaseHashmap.put("description", dTestcaseData.get("description"));
				dTestcaseHashmap.put("status", dTestcaseData.get("status"));
				dTestcaseHashmap.put("template_version", dTestcaseData.get("templateVersion"));
				dTestcaseHashmap.put("execution_version_start", dTestcaseData.get("executionVersionStart"));
				dTestcaseHashmap.put("execution_version_current", dTestcaseData.get("executionVersionCurrent"));
				
				UpdateTemplatesStatement saveFunction = new UpdateTemplatesStatement();
				saveFunction.dataBaseController(5, null, null, false, false, null, null, 0, null, null, null, dTestcaseHashmap, null, null, null, null, priority);
				
			}
		}catch(Exception e) {
			System.out.println("Error: Dependent testcase cannot be re-archived");
		}
		
		
		try {
			for(int i=0; i<ioValueArray.length(); i++) {
				
				JSONObject ioValueData = ioValueArray.getJSONObject(i);
				
				LinkedHashMap<String, Object>ioValueHashmap = new LinkedHashMap<String, Object>();
				
				ioValueHashmap.put("io_id", ioValueData.get("ioId"));
				ioValueHashmap.put("testcase_number", ioValueData.get("testcaseNumber"));
				ioValueHashmap.put("template_data_id", ioValueData.get("templateDataId"));
				ioValueHashmap.put("io_value", ioValueData.get("ioValue"));
				
				UpdateTemplatesStatement saveFunction = new UpdateTemplatesStatement();
				saveFunction.dataBaseController(6, null, null, false, false, null, null, 0, null, null, null, null, ioValueHashmap, null, null, null, 0);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			for(int i=0; i<testcaseRecordArray.length(); i++) {
				
				JSONObject testcaseRecordData = testcaseRecordArray.getJSONObject(i);
				
				LinkedHashMap<String, Object>testcaseRecordHashmap = new LinkedHashMap<String, Object>();
				
				testcaseRecordHashmap.put("record_id", testcaseRecordData.get("recordId"));
				testcaseRecordHashmap.put("client_id", testcaseRecordData.get("clientId"));
				testcaseRecordHashmap.put("testcase_number", testcaseRecordData.get("testcaseNumber"));
				testcaseRecordHashmap.put("user_id", testcaseRecordData.get("userId"));
				testcaseRecordHashmap.put("universe_id", testcaseRecordData.get("universeId"));
				testcaseRecordHashmap.put("client_name", testcaseRecordData.get("clientName"));
				testcaseRecordHashmap.put("description", testcaseRecordData.get("description"));
				testcaseRecordHashmap.put("status", testcaseRecordData.get("status"));
				testcaseRecordHashmap.put("execution_schedule", testcaseRecordData.get("executionSchedule"));
				testcaseRecordHashmap.put("execution_version", testcaseRecordData.get("executionVersion"));
				
				UpdateTemplatesStatement saveFunction = new UpdateTemplatesStatement();
				saveFunction.dataBaseController(7, null, null, false, false, null, null, 0, null, null, null, null, null, testcaseRecordHashmap, null, null, 0);
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			for(int i=0; i<actualDataArray.length(); i++) {
				
				JSONObject actualDataData = actualDataArray.getJSONObject(i);
				
				LinkedHashMap<String, Object>actualHashmap = new LinkedHashMap<String, Object>();
				
				actualHashmap.put("actual_data_id", actualDataData.get("actualDataId"));
				actualHashmap.put("testcase_number", actualDataData.get("testcaseNumber"));
				actualHashmap.put("input_output_value", actualDataData.get("inputOutputValue"));
				actualHashmap.put("label", actualDataData.get("label"));
				actualHashmap.put("nature_of_action", actualDataData.get("natureOfAction"));
				actualHashmap.put("remarks", actualDataData.get("remarks"));
				actualHashmap.put("screenshot_path", actualDataData.get("screenshotPath"));
				actualHashmap.put("is_screen_capture", actualDataData.get("screenCapture"));
				actualHashmap.put("timestamp", actualDataData.get("timestamp"));
				actualHashmap.put("is_trgger_enter", actualDataData.get("triggerEnter"));
				actualHashmap.put("web_element_name", actualDataData.get("webElementName"));
				actualHashmap.put("web_element_nature", actualDataData.get("webElementNature"));
				actualHashmap.put("log_field", actualDataData.get("logField"));
				actualHashmap.put("execution_version", actualDataData.get("executionVersion"));
				
				UpdateTemplatesStatement saveFunction = new UpdateTemplatesStatement();
				saveFunction.dataBaseController(9, null, null, false, false, null, null, 0, null, null, null, null, null, null, null, actualHashmap, 0);
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			for(int i=0; i<footerDataArray.length(); i++) {
				
				
				
				JSONObject footerDataData = footerDataArray.getJSONObject(i);
				
				System.out.println(footerDataData.toString());
				
				LinkedHashMap<String, Object>footerHashmap = new LinkedHashMap<String, Object>();
				
				footerHashmap.put("footer_data_id", footerDataData.get("footerDataId"));
				footerHashmap.put("testcase_number", footerDataData.get("testcaseNumber"));
				footerHashmap.put("assigned_account", footerDataData.get("assignedAccount"));
				footerHashmap.put("client_name", footerDataData.get("clientName"));
				footerHashmap.put("is_ignore_severity", footerDataData.get("isIgnoreSeverity"));
				footerHashmap.put("sender", footerDataData.get("sender"));
				footerHashmap.put("is_server_import", footerDataData.get("isServerimport"));
				footerHashmap.put("testcase_status", footerDataData.get("testcaseStatus"));
				footerHashmap.put("transaction_type", footerDataData.get("transactionType"));
				footerHashmap.put("url", footerDataData.get("url"));
				footerHashmap.put("execution_version", footerDataData.get("executionVersion"));
				
				UpdateTemplatesStatement saveFunction = new UpdateTemplatesStatement();
				saveFunction.dataBaseController(8, null, null, false, false, null, null, 0, null, null, null, null, null, null, footerHashmap, null, 0);
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
