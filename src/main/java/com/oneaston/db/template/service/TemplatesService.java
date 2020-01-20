package com.oneaston.db.template.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.oneaston.configuration.bean.StoragePathBean;
import com.oneaston.configuration.prepstatement.UpdateTemplatesStatement;
import com.oneaston.configuration.standalone.TextfileInterpreter;
import com.oneaston.db.template.domain.TemplateData;
import com.oneaston.db.template.domain.Templates;
import com.oneaston.db.template.repository.DependentTestcaseIOValueRepository;
import com.oneaston.db.template.repository.TemplateDataRepository;
import com.oneaston.db.template.repository.TemplatesRepository;
import com.oneaston.db.universe.domain.Client;
import com.oneaston.db.universe.repository.ClientRepository;
import com.oneaston.db.user.domain.User;
import com.oneaston.db.user.repository.UserRepository;

@Path("/templates")
public class TemplatesService {
	
	@Autowired
	TemplateDataRepository templateDataRepo;
	
	@Autowired
	TemplatesRepository templatesRepo;
	
	@Autowired
	ClientRepository clientRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	DependentTestcaseIOValueRepository dependentTestcaseIOValueRepo;
	
	//PRIVATE FUNCTIONS--------------------------------------------------
	
	//================================================FUNCTION FOR FILE UPLOADING========================================
	private String uploadFile(InputStream fileStream, String location,
			String fileName) {
		
		String fileLoc = String.format("%s\\%s", location, fileName);
		
		try {
			int read = 0;
			byte[]bytes = new byte[1024];
			
			OutputStream out = new FileOutputStream(new File(fileLoc));
			while((read = fileStream.read(bytes)) != -1) {
				out.write(bytes, 0 , read);
			}
			out.flush();
			out.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return fileLoc;
		
	}
	
	//FOR GENERATING TESTCASE NUMBER STRING
	private String generateTextFileName() {
		Date date = new Date();
		String dateFileName;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmssSSSS");
		dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
		String tempA = dateFormat.format(date).toString();
		dateFileName = tempA;
		return dateFileName;
	}
	
	//TEMPLATES SERVICES-----------------------------------------------------------------
	
	//================================================================CREATE TEMPLATE FUNCTION==========================================================
	@POST
	@Path("/processcreatetemplate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTemplate(String jsonData) {
		
		Logger log = Logger.getLogger(getClass());
		
		//GET CLIENT DATA IN JSON DATA STRING
		JSONObject clientData = new JSONObject(jsonData);
		
		//INSTANTIATE JSON RESPONSE
		JSONObject jsonResponse = new JSONObject();
		
		//GET CLIENT BY CLIENT ID
		Client client = clientRepo.findClientByClientName(clientData.getString("clientName"));
		
		if(client == null) {
			jsonResponse.put("Error", "Invalid client.");
			return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		}
		
		//GET USER BY USER ID
		User user = userRepo.findUserByUserId(clientData.getLong("userId"));
		
		//GET TEMPLATE DATA ARRAY IN CLIENT DATA
		JSONArray templateDataArray = clientData.getJSONArray("templateDataList");
		
		//UPDATE TEMPLATES TABLE
		Templates newTemplate = new Templates(client, user, clientData.getString("templateName"),
				clientData.getBoolean("isPublic"));
		templatesRepo.save(newTemplate);
		
		
		//UPDATE TEMPLATE DATA TABLE
		for(int i=0; i<templateDataArray.length(); i++) {
			
			JSONObject templateData = templateDataArray.getJSONObject(i);
			
			log.info(templateData);
			
			TemplateData newTemplateData = new TemplateData(newTemplate, templateData.getString("label"),
					templateData.getString("natureOfAction"), templateData.getBoolean("screenCapture"),
					templateData.getBoolean("triggerEnter"), templateData.getString("webElementName"),
					templateData.getString("webElementNature"));
			templateDataRepo.save(newTemplateData);
		}
		
		
		
		//RETURN SUCCESS RESPONSE
		jsonResponse.put("Info", "Template successfully created.");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	
	//===========================================================MODIFY TEMPLATE DATA FUNCTION=========================================================
	@POST
	@Path("/processmodifytemplate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response processModifyTemplate(String jsonData) {
		
		//Logger log = Logger.getLogger(getClass());
		
		//INSTANTIATE JSON RESPONSE OBJECT
		JSONObject jsonResponse = new JSONObject();
		
		//GET CLIENT DATA IN JSON DATA STRING
		JSONObject templateDataJson = new JSONObject(jsonData);
		
		Templates template;
		
		List<TemplateData>templateDataList;
		
		//GET ALL 
		JSONArray templateDataArray;
		try {
			//GET CLIENT BY CLIENT NAME
			template = templatesRepo.findTemplatesByTemplateId(Long.parseLong(templateDataJson.getString("templateId")));
			
			//GET LIST OF TEMPLATE DATA FROM TEMPLATE ID
			templateDataList = templateDataRepo.findTemplateDatasByTemplateId(template);
			
			//GET TEMPLATE DATA ARRAY IN CLIENT DATA
			templateDataArray = templateDataJson.getJSONArray("templateDataList");
		}catch (Exception e) {
			
			jsonResponse.put("Error", "No Available template to modify.");
			return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
			
		}
		
		
		
		//CHECK TEMPLATE DATA IF THERE IS DELETED DATA
		outer:
		for(int i=0; i<templateDataList.size(); i++) {
			for(int j=0; j<templateDataArray.length(); j++) {
				//GET TEMPLATE DATA FROM TEMPLATE DATA ARRAY
				JSONObject templateDataObject = templateDataArray.getJSONObject(j);
				
				//CHECK IF THERE IS OLD AND NEW TEMPLATE DATA ID IS SAME
				if(templateDataList.get(i).getTemplateDataId() == templateDataObject.getLong("templateDataId")) {
					//FIND TEMPLATE DATA BY TEMPLATE DATA ID
					TemplateData templateData = templateDataRepo.findTemplateDataByTemplateDataId(templateDataObject.getLong("templateDataId"));
					
					System.out.println(templateDataObject);
					
					//CALL PREPARED STATEMENT FOR UPDATING
					UpdateTemplatesStatement updateStatement = new UpdateTemplatesStatement();
					updateStatement.dataBaseController(1, templateDataObject.getString("label"), templateDataObject.getString("natureOfAction"), 
							Boolean.valueOf(templateDataObject.getBoolean("screenCapture")), Boolean.valueOf(templateDataObject.getBoolean("triggerEnter")), templateDataObject.getString("webElementName"), 
							templateDataObject.getString("webElementNature"), templateData.getTemplateDataId(), null, null, null, null, null, null, null, null, 0);
					continue outer;
				}
			}
			if(dependentTestcaseIOValueRepo.findIoValueByTemplateDataId(templateDataList.get(i)) != null) {
				dependentTestcaseIOValueRepo.delete(dependentTestcaseIOValueRepo.findIoValueByTemplateDataId(templateDataList.get(i)));
			}
			templateDataRepo.delete(templateDataList.get(i));
		}
		//CHECK IF THERE IS NEW TEMPLATE DATA
		for(int i=0; i<templateDataArray.length(); i++) {
			//GET TEMPLATE DATA FROM ARRAY
			JSONObject templateDataObject = templateDataArray.getJSONObject(i);
			//CHECK IF THERE IS ID OF 0
			if(templateDataObject.getLong("templateDataId") == 0) {
				
				
				//CREATE TEMPLATE DATA
				TemplateData templateData = new TemplateData(template, templateDataObject.getString("label"),
						templateDataObject.getString("natureOfAction"), templateDataObject.getBoolean("screenCapture"),
						templateDataObject.getBoolean("triggerEnter"), templateDataObject.getString("webElementName"),
						templateDataObject.getString("webElementNature"));
				templateDataRepo.save(templateData);
			}
		}
		
		
		
		//INCREMENT THE VERSION OF TEMPLATE
		template.setTemplateVersion(template.getTemplateVersion() + 1);
		template.setPublic(templateDataJson.getBoolean("isPublic"));
		templatesRepo.save(template);
		
		//RETURN JSON RESPONSE INFO
		jsonResponse.put("Info", "Template successfully modified.");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	//============================================================UPLOAD TEXT FILE FOR TEMPLATE DATA===================================================
	@POST
	@Path("/uploadtemplate")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadTemplate(@FormDataParam("textFile")InputStream textFile,
			@FormDataParam("textFile")FormDataContentDisposition textDetails) {
		Logger log = Logger.getLogger(getClass());
		log.info(textFile);
		log.info(textDetails);
		
		
		//INSTANTIATE JSON RESPONSE
		JSONObject jsonResponse = new JSONObject();
		
		//UPLOAD FILE
		try {
			
			//CALL UPLOAD FILE FUNCTION
			String filleName = uploadFile(textFile, StoragePathBean.TEXTFILE_FOLDER, generateTextFileName());
			
			//INSTANTIATE INTERPRETER
			TextfileInterpreter textfileInterpreter = new TextfileInterpreter();
			
			//GET FINAL DATA FROM INTERPRETER
			List<String>dataStringList = textfileInterpreter.interpreterController(filleName);
			
			//INSTANTIATE LIST OF TEMPLATE DATA
			List<TemplateData>templateDataList = new ArrayList<TemplateData>();
			
			System.out.println("-----------------------");
			
			//ITERATE THROUGH THE LIST
			for(String data : dataStringList) {
				
				
				
				//GET VALUES SEPERATED BY COMMA
				String[]values = data.split(",", 3);
				
				
				
				//CREATE TEMPLATE DATA
				TemplateData templateData = new TemplateData();
				templateData.setWebElementNature(values[0]);
				templateData.setNatureOfAction(values[1]);
				templateData.setWebElementName(values[2].replace("\\\"", "\""));
				
				//ADD TO LIST
				templateDataList.add(templateData);
			}
			
			//ADD LIST TO JSON DATA
			jsonResponse.put("templateDataList", templateDataList);
			
			//RETURN JSON RESPONSE
			return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
			
		}catch(Exception e) {
			//RETURN ERROR RESPONSE
			jsonResponse.put("Error", "File upload unsuccessful.");
			return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
			
		}
		
	}
	
	
	//=================================================================GET TEMPLATES FUNCTION==========================================================
	@GET
	@Path("/gettemplates")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTemplates(@QueryParam("userId")long userId, 
			@QueryParam("clientId")long clientId) {
		
		//GET TEMPLATES BY USER ID
		List<Templates>templatesList = templatesRepo.findTemplatesByClientIdAndUserId(clientRepo.findClientByClientId(clientId), 
				userRepo.findUserByUserId(userId));
		
		
		//GET PUBLIC TEMPLATES
		List<Templates>templatesList2 = templatesRepo.findTemplatesByClientIdAndIsPublicAndUserId(clientRepo.findClientByClientId(clientId),
				true, userRepo.findUserByUserId(userId));
		
		for(Templates templates : templatesList2) {
			templatesList.add(templates);
		}
		
		//PUT LIST IN JSON OBJECT
		JSONObject jsonData = new JSONObject();
		jsonData.put("templatesList", templatesList);
		
		//RETURN JSON RESPONSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonData.toString()).build();
	}

}
