package com.oneaston.db.universe.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.oneaston.configuration.bean.StoragePathBean;
import com.oneaston.db.campaign.domain.DependentTestcase;
import com.oneaston.db.campaign.repository.DependentTestcaseRepository;
import com.oneaston.db.universe.domain.Script;
import com.oneaston.db.universe.domain.ScriptVariable;
import com.oneaston.db.universe.repository.ClientLoginAccountRepository;
import com.oneaston.db.universe.repository.ScriptRepository;
import com.oneaston.db.universe.repository.ScriptVariableRepository;
import com.oneaston.db.universe.repository.UniverseRepository;

@Path("/script")
public class ScriptService {
	
	@Autowired
	ScriptRepository scriptRepo;
	
	@Autowired
	UniverseRepository universeRepo;
	
	@Autowired
	ScriptVariableRepository scriptVariableRepo;
	
	@Autowired
	DependentTestcaseRepository dependentTestcaseRepo;
	
	@Autowired
	ClientLoginAccountRepository clientLoginAccountRepo;
	
	//PRIVATE FUNCTIONS-------------------------------------------------------------------------------------
	//================================================FUNCTION FOR FILE UPLOADING========================================
		private void uploadFile(InputStream fileStream, String location,
				String fileName) {
			
			try {
				int read = 0;
				byte[]bytes = new byte[1024];
				
				OutputStream out = new FileOutputStream(new File(String.format("%s\\%s", location, fileName)));
				while((read = fileStream.read(bytes)) != -1) {
					out.write(bytes, 0 , read);
				}
				out.flush();
				out.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	
	
	//SCRIPT SERVICES---------------------------------------------------------------------------------------
	//=====================================================UPLOAD SH FILE===================================================================
	@POST
	@Path("/uploadshfile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadShFile(@FormDataParam("shFile")InputStream shFile,
			@FormDataParam("shFile")FormDataContentDisposition shDetails) {
		
		Logger log = Logger.getLogger(getClass());
		log.info(shFile);
		log.info(shDetails);
		
		//INSTANTIATE JSON RESPONSE
		JSONObject jsonResponse = new JSONObject();
		
		try {
			
			//INSTANTIATE BOOLEAN CHECK
			boolean checkFlag = false;
			
			//GET ALL SCRIPT 
			List<Script>shFiles = scriptRepo.findAll();
			
			//LOOP THROUGH THE LIST AND CHECK IF THERE IS EXISTING
			for(Script sh : shFiles) {
				
				if(sh.getScriptFilepath().contains(shDetails.getFileName())) {
					checkFlag = true;
					break;
				}
			}
			
			log.info(checkFlag);
			//CHECK STATUS OF CHECK FLAG BOOLEAN
			if(!checkFlag) {
				log.info("false");
				
				//CALL UPLOAD FILE FUNCTION
				uploadFile(shFile, StoragePathBean.SHFILE_FOLDER, shDetails.getFileName());
				
				//RETURN JSON RESPONSE
				jsonResponse.put("Info", "SH File Uploaded successfully.");
				return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
			}else {
				log.info("true");
				jsonResponse.put("Info", "File already exists!");
				return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
			}
			
		}catch(Exception e) {
			//RETURN ERROR RESPONSE
			jsonResponse.put("Error", "File upload unsuccessful.");
			return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		}
		
	}
	
	
	//=======================================================GET SCRIPTS BY UNIVERSE ID=====================================================
	@POST
	@Path("/processaddscript")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response processAddScript(String json) {
		
		//INSTANTIATE JSON RESPONSE
		JSONObject jsonResponse = new JSONObject();
		
		//GET DATA FROM JSON
		JSONObject scriptCredential = new JSONObject(json);
		
		//REGISTER SCRIPT
		Script script = new Script();
		script.setLoginAccountId(clientLoginAccountRepo.findClientLoginAccountByLoginAccountId(scriptCredential.getLong("loginAccountId")));
		script.setName(scriptCredential.getString("name"));
		script.setUniverseId(universeRepo.findUniverseByUniverseId(scriptCredential.getLong("universeId")));
		script.setDescription(scriptCredential.getString("description"));
		
		//GET SH FILE FOLDER
		File[]shFiles = new File(StoragePathBean.SHFILE_FOLDER).listFiles();
		
		//ITERATE ARRAY
		for(int i=0; i<shFiles.length; i++) {
			if(shFiles[i].getName().contains(scriptCredential.getString("shFile"))) {
				script.setScriptFilepath(shFiles[i].getAbsolutePath());
			}
		}
		
		//SAVE SCRIPT
		scriptRepo.save(script);
		
		//DELETE UPLOADED SH FILE THAT IS NOT ON DB
		List<Script>scriptList = scriptRepo.findAll();
		List<File>filesToDelete = new ArrayList<File>();
		fileLoop:
		for(File file : shFiles) {
			for(Script scripts : scriptList) {
				if(scripts.getScriptFilepath().contains(file.getName())) {
					continue fileLoop;
				}
			}
			filesToDelete.add(file);
		}
		
		//DELETE FILE NOT IN THE DATABASE
		for(File delete : filesToDelete) {
			FileUtils.deleteQuietly(delete);
		}
		
		//RETURN JSON RESPONSE
		jsonResponse.put("Info", "Script Successfully added.");
		jsonResponse.put("scriptId", script.getScriptId());
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	
	@POST
	@Path("/finalizeaddscript")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response finalizeAddScript(String json) {
		
		Logger log = Logger.getLogger(getClass());
		
		log.info("Access: /Finalize");
		
		//INSTANTIATE JSON RESPONSE
		JSONObject jsonResponse = new JSONObject();
		
		//GET DATA FROM JSON
		JSONObject jsonData = new JSONObject(json);
		
		DependentTestcase dTestcase = dependentTestcaseRepo.findDependentTestcaseByTestcaseNumber(jsonData.getString("testcaseNumber"));
		
		//INSTANTIATE ERROR LINE VARIABLE
		int errLine = 0;
		
		try {
			
			
			
			if(dTestcase.getEmbeddedScript() == null || dTestcase.getEmbeddedScript().equals("")) {
				
				
				errLine = 1;
				
				
				if(dTestcase.getStoredValues() == null || dTestcase.getStoredValues().equals("")) {
					
					JSONArray scriptVariableArray = jsonData.getJSONArray("scriptVariableList");
					
					dTestcase.setEmbeddedScript(jsonData.getString("scriptId"));
					
					System.out.println(scriptVariableArray.toString());
					
					System.out.println("Sample: ");
					
					System.out.println(scriptVariableArray.toString().replace("[", "").replace("]", "").replaceAll("\"", "").trim());
					dTestcase.setStoredValues(scriptVariableArray.toString().replace("[", "").replace("]", "").replaceAll("\"", "").trim());
					
				}else {
					
					JSONArray scriptVariableArray = jsonData.getJSONArray("scriptVariableList");
					
					System.out.println(scriptVariableArray.toString());
					
					System.out.println("Sample: ");
					
					System.out.println(scriptVariableArray.toString().replace("[", "").replace("]", "").replaceAll("\"", "").trim());
					dTestcase.setStoredValues(dTestcase.getStoredValues() + "," + scriptVariableArray.toString().replace("[", "").replace("]", "").replaceAll("\"", "").trim());
					
				}
				
			}else {
				
				errLine = 2;
				
				
				if(dTestcase.getStoredValues() == null || dTestcase.getStoredValues().equals("")) {
					JSONArray scriptVariableArray = jsonData.getJSONArray("scriptVariableList");
					
					dTestcase.setEmbeddedScript(dTestcase.getEmbeddedScript() + "," + jsonData.getString("scriptId"));
					
					System.out.println(scriptVariableArray.toString().replace("[", "").replace("]", "").replaceAll("\"", "").trim());
					dTestcase.setStoredValues(scriptVariableArray.toString().replace("[", "").replace("]", "").replaceAll("\"", "").trim());
					
				}else {
					
					JSONArray scriptVariableArray = jsonData.getJSONArray("scriptVariableList");
					System.out.println(scriptVariableArray.toString().replace("[", "").replace("]", "").replaceAll("\"", "").trim());
					dTestcase.setStoredValues(dTestcase.getStoredValues() + "," + scriptVariableArray.toString().replace("[", "").replace("]", "").replaceAll("\"", "").trim());
					
				}
				
			}
			
			
		}catch(Exception e) {
			
			if(errLine == 1 || errLine == 2) {
				if(dTestcase.getEmbeddedScript() == null || dTestcase.getEmbeddedScript().equals("")) {
					log.info("if");
					dTestcase.setEmbeddedScript(jsonData.getString("scriptId"));
				}else {
					log.info(dTestcase.getEmbeddedScript());
					dTestcase.setEmbeddedScript(dTestcase.getEmbeddedScript() + "," + jsonData.getString("scriptId"));
				}
			}else {
				e.printStackTrace();
			}
			
		}
		
		dependentTestcaseRepo.save(dTestcase);
		
		//RETURN JSON RESPONSE
		jsonResponse.put("Info", "Script successfully attached.");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	
	//========================================================PROCESS ADD SCRIPT VALUE======================================================================
	@POST
	@Path("/processaddscriptvariable")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response processAddScriptVariable(String json) {
		
		//INSTANTIATE JSON RESPONSE
		JSONObject jsonResponse = new JSONObject();
		
		//GET DATA IN JSON
		JSONObject jsonData = new JSONObject(json);
		
		//GET ARRAY IN JSON
		JSONArray variableName = jsonData.getJSONArray("name");
		JSONArray variableDescription = jsonData.getJSONArray("description");
		
		for(int i=0; i<variableName.length(); i++) {
			
			//CREATE SCRIPOT VARIABLE
			ScriptVariable scriptVariable = new ScriptVariable();
			scriptVariable.setName(variableName.getString(i));
			scriptVariable.setDescription(variableDescription.getString(i));
			scriptVariable.setScriptId(scriptRepo.findScriptByScriptId(jsonData.getLong("scriptId")));
			
			scriptVariableRepo.save(scriptVariable);
			
		}
		
		//RETURN JSON RESPONSE
		jsonResponse.put("Info", "Script successfully registered.");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	
	//=========================================================GET SCRIPTS BY CLIENT LOGIN ACCOUNT ID========================================================
	@GET
	@Path("/getscriptbyloginaccountid")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getScriptByLoginAcountId(@QueryParam("loginAccountId")long loginAccountId) {
		
		//GET SCRIPTS BY LOGIN ACCOUNT ID
		List<Script>scriptList = scriptRepo.findScriptsByLoginAccountId(clientLoginAccountRepo.findClientLoginAccountByLoginAccountId(loginAccountId));
		
		//PUT LIST IN JSON DATA
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("scriptList", scriptList);
		
		//RETURN JSON RESPONSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	//=============================================================GET SCRIPT BY UNIVERWSE ID===================================================
	@GET
	@Path("/getscriptbyuniverseid")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getScriptByUniverseId(@QueryParam("universeId")long universeId) {
		
		//GET SCRIPTS BY UNIVERSE ID
		List<Script>scriptList = scriptRepo.findScriptsByUniverseId(universeRepo.findUniverseByUniverseId(universeId));
		
		//PUT LIST IN JSON DATA
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("scriptList", scriptList);
		
		//RETURN JSON RESPONSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	//===========================================GET SIZE OF SCRIPT VARIABLE IN SCRIPT============================================================
	@GET
	@Path("/getsizeofscriptvariable")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSizeOfScriptVariable(@QueryParam("scriptId")String scriptId) {
		
		//INSTANTIATE JSON RESPONSE
		JSONObject jsonResponse = new JSONObject();
		
		Script script;
		try {
			//GET SCRIPT BY SCRIPT ID
			script = scriptRepo.findScriptByScriptId(Long.parseLong(scriptId));
			if(script == null) {
				jsonResponse.put("Error", "Invalid script.");
				return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
			}
		}catch(Exception e) {
			jsonResponse.put("Error", "Invalid script.");
			return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
			
		}
		
		
		//GET LIST OF SCRIPT VATRIABLE IN SCRIPT
		List<ScriptVariable>scriptVariableList = scriptVariableRepo.findScriptVariablesByScriptId(script);
		
		//PUT LIST IN JSON DATA
		jsonResponse.put("scriptVariableList", scriptVariableList);
		
		//RETURN JSON RESPONSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
}
