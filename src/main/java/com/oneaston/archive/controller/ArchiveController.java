package com.oneaston.archive.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
import com.oneaston.archive.campaign.service.CampaignArchiveService;
import com.oneaston.archive.campaign.service.DependentTestcaseArchiveService;
import com.oneaston.archive.campaign.service.DependentTestcaseIOValueArchiveService;
import com.oneaston.archive.campaign.service.ExtendedFunctions;
import com.oneaston.archive.campaign.service.StoryArchiveService;
import com.oneaston.archive.campaign.service.ThemeArchiveService;
import com.oneaston.archive.testcase.domain.TestcaseActualDataArchive;
import com.oneaston.archive.testcase.domain.TestcaseFooterDataArchive;
import com.oneaston.archive.testcase.domain.TestcaseRecordArchive;
import com.oneaston.archive.testcase.service.TestcaseActualDataArchiveService;
import com.oneaston.archive.testcase.service.TestcaseFooterDataArchiveService;
import com.oneaston.archive.testcase.service.TestcaseRecordArchiveService;
import com.oneaston.configuration.standalone.SaveArchiveFunction;
import com.oneaston.configuration.storage.JsonStorage;

@Path("/archive")
public class ArchiveController {

	@Autowired
	private CampaignArchiveService campaignService;
	@Autowired
	private ThemeArchiveService themeService;
	@Autowired
	private StoryArchiveService storyService;
	@Autowired
	private DependentTestcaseArchiveService dependentTestcaseService;
	@Autowired
	private TestcaseRecordArchiveService testCaseRecordService;
	@Autowired
	private TestcaseFooterDataArchiveService footerDataService;
	@Autowired
	private TestcaseActualDataArchiveService actualDataService;
	@Autowired
	private DependentTestcaseIOValueArchiveService ioValService;
	@Autowired
	private ExtendedFunctions extendedFunctions;
	@Autowired
	private SaveArchiveFunction saveArchive;
	
	
	
	
	
	private JSONObject jsonList;
	
	@POST
	@Path("/reverseArchiveCampaign")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response reverseArchiveCampaign(String jsonData) throws URISyntaxException {
		
		Logger log = Logger.getLogger(getClass());
		
		log.info(jsonData);
		
		//GET CAMPAIGN DATA IN JSoN OBJECT
		JSONObject jsonObject = new JSONObject(jsonData);
		
		log.info("json: " + jsonObject.get("campaignIdList"));
		
		JSONArray jsonArray = jsonObject.getJSONArray("campaignIdList");
		
		//instantiate array storage
		Long[] campaignIdArray = new Long[jsonArray.length()];
		
		//convert campaignId from List to array 
		for(int i=0; i<jsonArray.length(); i++) {
			campaignIdArray[i] = jsonArray.getLong(i);
		}
		
		//find all campaign data using campaignIdArray
		List<CampaignArchive> CampaignArchiveList = campaignService.selectAllCampaignById(campaignIdArray);
		
		//add result to temporary JsonMap
		JSONObject tempMapJson = new JSONObject();
		tempMapJson.put("campaign", CampaignArchiveList);
		
		JsonStorage.getInstance().setTempMapJson(tempMapJson);
		
		log.info(CampaignArchiveList.size());
		
		//delete all campaign data using campaign id array
		campaignService.deleteAllCampaignById(campaignIdArray);
		
		//find all theme data using campaignIdArray
		List<ThemeArchive> ThemeArchiveList = themeService.selectAllThemeByCampaignId(campaignIdArray);
		
		
		
		//get themeId from ThemeArchivelist
		Long[] themeIdArray = new Long[ThemeArchiveList.size()];
		themeIdArray = extendedFunctions.getThemeIdFromThemeData(ThemeArchiveList);
		List<Long> themeIdList = Arrays.asList(themeIdArray);
		
		log.info("theme count: " + themeIdList.size());
		
		
		//set data to temporary Json Object
		this.jsonList = new JSONObject();
		this.jsonList.put("tempMap", tempMapJson);
		this.jsonList.put("themeIdList", themeIdList);
		
		JsonStorage.getInstance().setJsonList(jsonList);
		
		//set data into session
		/*
		 * session.setAttribute("reverseArchiveSender", "campaign");
		 */
		
		//configure mav
		String redirect = "/archive/reverseArchiveTheme";
		
		return Response.temporaryRedirect(new URI(redirect)).build();
	}
	
	@POST
	@Path("/reverseArchiveTheme")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response reverseArchiveTheme(String jsonData) throws URISyntaxException {
		
		Logger log = Logger.getLogger(getClass());
		
		//GET JSON LIST FROM STRIN JSONDATA
		JSONObject jsonObject = new JSONObject(jsonData);;
		JSONArray themeIdArrayList;
		
		//log.info(this.jsonList.toString());
		
		//determine if temp map is null
		
		if(JsonStorage.getInstance().getJsonList() != null && JsonStorage.getInstance().getJsonList().toString().contains("themeIdList")) {
			themeIdArrayList = JsonStorage.getInstance().getJsonList().getJSONArray("themeIdList");
		}else {
			JSONObject tempMap = new JSONObject();
			JsonStorage.getInstance().setTempMapJson(tempMap);
			themeIdArrayList = jsonObject.getJSONArray("themeIdList");
		}
		
		//Populate theme list id
		List<Long>themeId = new ArrayList<Long>();
		
		for(int i=0; i<themeIdArrayList.length(); i++) {
			themeId.add(themeIdArrayList.getLong(i));
		}
		
		//instantiate array storage
		Long[] themeIdArray = new Long[themeId.size()];
		
		log.info("Hello: " + themeId.get(0));
		
		//convert themeId from List to array
		themeIdArray = themeId.toArray(new Long[themeId.size()]);
		
		//find all theme data using themeIdArray <--
		List<ThemeArchive> ThemeArchiveList = themeService.selectAllThemeByThemeId(themeIdArray);
		
		log.info(themeIdArray[0]);
		
		//add result to temporary hashmap
		JsonStorage.getInstance().getTempMapJson().put("theme", ThemeArchiveList);
		
		//delete all theme data using themeIdArray
		themeService.deleteAllThemeByThemeId(themeIdArray);
		
		//find all story data using themeIdArray
		List<StoryArchive> StoryArchiveList = storyService.selectAllStoryByThemeId(themeIdArray);
		
		//get storyId from StoryArchiveList
		Long[] storyIdArray = new Long[StoryArchiveList.size()];
		storyIdArray = extendedFunctions.getStoryIdFromStoryData(StoryArchiveList);
		List<Long> storyIdList = Arrays.asList(storyIdArray);
		
		//set data to temporary Json Object
		JSONObject jsonList = new JSONObject();
		jsonList.put("tempMap", JsonStorage.getInstance().getTempMapJson());
		jsonList.put("storyIdList", storyIdList);
		
		JsonStorage.getInstance().setJsonList(jsonList);
		
		//configure mav
		String redirect = "/archive/reverseArchiveStory";
		
		return Response.temporaryRedirect(new URI(redirect)).type(MediaType.APPLICATION_JSON).entity(jsonList.toString()).build();

	}
	
	@POST
	@Path("/reverseArchiveStory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response reverseArchiveStory(String jsonData) throws URISyntaxException {
		
		
		
		//GET JSON LIST FROM STRIN JSONDATA
		JSONObject jsonObject = new JSONObject(jsonData);;
		JSONArray themeIdArrayList;
		
		//log.info(this.jsonList.toString());
		
		//determine if temp map is null
		
		if(JsonStorage.getInstance().getJsonList() != null && JsonStorage.getInstance().getJsonList().toString().contains("storyIdList")) {
			themeIdArrayList = JsonStorage.getInstance().getJsonList().getJSONArray("storyIdList");
		}else {
			JSONObject tempMap = new JSONObject();
			JsonStorage.getInstance().setTempMapJson(tempMap);
			themeIdArrayList = jsonObject.getJSONArray("storyIdList");
		}
		
		//Populate theme list id
		List<Long>storyId = new ArrayList<Long>();
		
		for(int i=0; i<themeIdArrayList.length(); i++) {
			
			storyId.add(themeIdArrayList.getLong(i));
			
		}
		
		//instantiate array storage
		Long[] storyIdArray = new Long[storyId.size()];
		
		//convert storyId from List to array
		storyIdArray = (Long[]) storyId.toArray(new Long[storyId.size()]);
		
		//find all story data using storyIdArray <--
		List<StoryArchive> StoryArchiveList = storyService.selectAllStoryByStoryId(storyIdArray);
		
		//add result to temporary hashmap
		
		JsonStorage.getInstance().getTempMapJson().put("story", StoryArchiveList);
		
		//delete all story data using storyIdArray
		storyService.deleteAllStoryByStoryId(storyIdArray);
		
		//find all dependenttestcase data using storyIdArray <--
		List<DependentTestcaseArchive> DependentTestcaseArchiveList = dependentTestcaseService.selectAllDependentTestcaseByStoryId(storyIdArray);
		
		//get testcaseNumber from DependentTestcaseArchiveList
		String[] testCaseNumberArray = new String[DependentTestcaseArchiveList.size()];
		testCaseNumberArray = extendedFunctions.getTestcaseNumberFromDependentTestcaseData(DependentTestcaseArchiveList);
		List<String> testCaseNumberList = Arrays.asList(testCaseNumberArray);
		
		//set data to temporary Json Object
		JSONObject jsonList = new JSONObject();
		jsonList.put("tempMap", JsonStorage.getInstance().getTempMapJson());
		jsonList.put("testcaseNumberList", testCaseNumberList);
		
		JsonStorage.getInstance().setJsonList(jsonList);
		 
		//configure mav
		String redirect = "/archive/reverseArchiveDependentTestcase";
		
		return Response.temporaryRedirect(new URI(redirect)).build();
		
	}
	
	@POST
	@Path("/reverseArchiveDependentTestcase")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response reverseArchiveDependentTestcase(String jsonData) {
		
		
		//GET JSON LIST FROM STRIN JSONDATA
		JSONObject jsonObject = new JSONObject(jsonData);;
		JSONArray themeIdArrayList;
		
		//log.info(this.jsonList.toString());
		
		//determine if temp map is null
		
		if(JsonStorage.getInstance().getJsonList() != null && JsonStorage.getInstance().getJsonList().toString().contains("testcaseNumberList")) {
			themeIdArrayList = JsonStorage.getInstance().getJsonList().getJSONArray("testcaseNumberList");
		}else {
			JSONObject tempMap = new JSONObject();
			JsonStorage.getInstance().setTempMapJson(tempMap);
			themeIdArrayList = jsonObject.getJSONArray("testcaseNumberList");
		}
		
		//Populate theme list id
		List<String>dependentTestCaseNumber = new ArrayList<String>();
		
		for(int i=0; i<themeIdArrayList.length(); i++) {
			
			dependentTestCaseNumber.add(themeIdArrayList.getString(i));
			
		}
		
		//instantiate array storage
		String[] dependentTestCaseNumberArray = new String[dependentTestCaseNumber.size()];
		
		//convert dependentTestCaseNumber from List to array
		dependentTestCaseNumberArray = (String[]) dependentTestCaseNumber.toArray(new String[dependentTestCaseNumber.size()]);
		
		//find all dependentTestcase data using testCasenumber
		List<DependentTestcaseArchive> DependentTestcaseArchiveList = dependentTestcaseService.selectAllDependentTestcaseByTestcaseNumber(dependentTestCaseNumberArray);
		
		//delete all dependentTestcase data by testcaseNumber
		dependentTestcaseService.deleteAllDependentTestcaseByTestcaseNumber(dependentTestCaseNumberArray);
		
		//find all testcase_record data using dependentTestCaseNumberArray
		List<TestcaseRecordArchive> testCaseRecordList = testCaseRecordService.selectAllTestcaseRecordByTestcaseNumber(dependentTestCaseNumberArray);
		
		//delete all testcase_record data using dependentTestCaseNumberArray
		testCaseRecordService.deleteAllTestcaseRecordByTestcaseNumber(dependentTestCaseNumberArray);
		
		//find all footer_data data using dependentTestCaseNumberArray
		List<TestcaseFooterDataArchive> footerDataList = footerDataService.selectAllFooterDataByTestcaseNumber(dependentTestCaseNumberArray);
		
		//delete all footer_data data using dependentTestCaseNumberArray
		footerDataService.deleteAlltestCaseFooterDataByTestCaseNumber(dependentTestCaseNumberArray);
		
		//find all actual_data data using dependentTestCaseNumberArray
		List<TestcaseActualDataArchive> actualDataList = actualDataService.selectAllActualDataByTestcaseNumber(dependentTestCaseNumberArray);
		
		//delete all actual_data data using dependentTestCaseNumberArray
		actualDataService.deleteAllActualDataByTestcaseNumber(dependentTestCaseNumberArray);
		
		//find all dependent_testcase_io_value data using dependentTestCaseNumberArray
		List<DependentTestcaseIOValueArchive> ioValBeanList = ioValService.selectAllIoValDataByTestcaseNumber(dependentTestCaseNumberArray);
		
		//delete all dependent_testcase_io_value data using dependentTestCaseNumberArray
		ioValService.deleteAllIoValDataByTestcaseNumber(dependentTestCaseNumberArray);
		
		//add result to temporary hashmap
		JsonStorage.getInstance().getTempMapJson().put("DependentTestcase", DependentTestcaseArchiveList);
		JsonStorage.getInstance().getTempMapJson().put("testCaseRecord", testCaseRecordList);
		JsonStorage.getInstance().getTempMapJson().put("footerData", footerDataList);
		JsonStorage.getInstance().getTempMapJson().put("actualData", actualDataList);
		JsonStorage.getInstance().getTempMapJson().put("ioValBean", ioValBeanList);
		
		
		
		
		//save function
		saveArchive.saveArchive();
		
		
		//INSTANTIATE JSON RESPONSE
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("Info", "Successfully archived.");
		
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	
	@GET
	@Path("/getarchivecampaign")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getArchiveCampaign() {
		
		//INSTANTIATE JSON RESPONSE
		JSONObject jsonResponse = new JSONObject();
		
		//GET ALL FROM DB
		List<CampaignArchive>campaignArchiveList = campaignService.selectAllCampaignArchive();
		
		//PUT LIST IN JSON RESPONSE
		jsonResponse.put("campaignArchiveList", campaignArchiveList);
		
		//RETURN JSON RESPONSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	@GET
	@Path("/getarchivetheme")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getArchiveTheme() {
		
		//INSTANTIATE JSON RESPONSE
		JSONObject jsonResponse = new JSONObject();
		
		//GET LSIT OF ARCHIVE THEME
		List<ThemeArchive>themeArchiveList = themeService.selectAllThemeArchive();
		
		//PUT LIST IN JSON RESPONS
		jsonResponse.put("themeArchiveList", themeArchiveList);
		
		//RETURN JSON RESPONSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	@GET
	@Path("/getarchivestory")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getArchiveStory() {
		
		//INSTANTIATE JSON RESPONSE
		JSONObject jsonResponse = new JSONObject();
		
		//GET LSIT OF ARCHIVE THEME
		List<StoryArchive>storyArchiveList = storyService.selectAllStoryArchive();
		
		//PUT LIST IN JSON RESPONS
		jsonResponse.put("storyArchiveList", storyArchiveList);
		
		//RETURN JSON RESPONSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		
	}
	
	@GET
	@Path("/getarchivedependenttestcase")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getArchiveDependentTestcase() {
		
		//INSTANTIATE JSON RESPONSE
		JSONObject jsonResponse = new JSONObject();
		
		//GET LSIT OF ARCHIVE THEME
		List<DependentTestcaseArchive>dependentTestcaseArchiveList = dependentTestcaseService.selectAllDependentTestcaseArchive();
		
		//PUT LIST IN JSON RESPONS
		jsonResponse.put("dependentTestcaseArchiveList", dependentTestcaseArchiveList);
		
		//RETURN JSON RESPONSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	
}
