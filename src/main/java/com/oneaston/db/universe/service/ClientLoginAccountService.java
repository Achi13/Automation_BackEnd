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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.oneaston.configuration.bean.StoragePathBean;
import com.oneaston.configuration.enums.AccountType;
import com.oneaston.db.universe.domain.ClientLoginAccount;
import com.oneaston.db.universe.repository.ClientLoginAccountRepository;
import com.oneaston.db.universe.repository.WebAddressRepository;

@Path("/clientloginaccount")
public class ClientLoginAccountService {
	
	@Autowired
	ClientLoginAccountRepository clientLoginAccountRepo;
	
	@Autowired
	WebAddressRepository webAddressRepo;
	
	//PRIVATE FUNCTIONS--------------------------------------------------
	
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
	
	//CLIENT LOGIN ACCOUNT SERVICES
	
	//============================================PROCESS CREATE LOGIN CREDENTIAL============================================
	@POST
	@Path("/processcreatecredential")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response processCreateCredential(String json) {
		
		//INSTANTIATE JSON RESPONSE
		JSONObject jsonResponse = new JSONObject();
		
		//GET DATA FROM JSON
		JSONObject credentialData = new JSONObject(json);
		
		//REGISTER CREDENTIAL
		ClientLoginAccount clientLoginAccount = new ClientLoginAccount();
		clientLoginAccount.setAccountType(AccountType.script);
		clientLoginAccount.setHostname(credentialData.getString("hostname"));
		clientLoginAccount.setUsername(credentialData.getString("username"));
		clientLoginAccount.setPassword(credentialData.getString("password"));
		clientLoginAccount.setDescription(credentialData.getString("description"));
		
		//GET PPK FOLDER
		File[]ppkFiles = new File(StoragePathBean.PPK_FOLDER).listFiles();
		
		//ITERATE THROUGH PPK FILE
		for(int i=0; i<ppkFiles.length; i++) {
			if(ppkFiles[i].getName().contains(credentialData.getString("ppkFile"))) {
				clientLoginAccount.setPpkFilepath(ppkFiles[i].getAbsolutePath());
			}
		}
		
		//SAVE CREDENTIAL
		clientLoginAccountRepo.save(clientLoginAccount);
		
		
		//DELETE FILES UPLOADED THAT IS NOT ON DB
		List<ClientLoginAccount>clientLoginAccountList = clientLoginAccountRepo.findAll();
		List<File>fileToDelete = new ArrayList<File>();
		fileLoop:
		for(File file : ppkFiles) {
			for(ClientLoginAccount ppkPath : clientLoginAccountList) {
				if(ppkPath.getAccountType().equals(AccountType.script)) {
					if(ppkPath.getPpkFilepath().contains(file.getName())) {
						continue fileLoop;
					}
				}
			}
			fileToDelete.add(file);
		}
		//DELETE FILE NOT IN THE DATABASE
		for(File delete : fileToDelete) {
			FileUtils.deleteQuietly(delete);
		}
		
		//RETURN JSON RESPONSE
		jsonResponse.put("Info", "Credential successfully registered.");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	//============================================GET CLIENT LOGIN ACCOUNT BY WEB========================================
	@GET
	@Path("/getloginaccount")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLoginAccount(@QueryParam("url")String url) {
		
		//GET CLIENT LOGIN ACCOUNT LIST BY WEB ADDRESS ID
		List<ClientLoginAccount>loginAccountList = clientLoginAccountRepo.findClientLoginAccountByWebAddressIdAndAccountType(
				webAddressRepo.findWebAddressByUrl(url));
		
		//PUT LIST IN JSON DATA
		JSONObject loginAccountData = new JSONObject();
		loginAccountData.put("loginAccountData", loginAccountList);
		
		//RETURN LOGIN ACCOUNT DATA RESPONSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(loginAccountData.toString()).build();
	}
	
	
	//=========================================GET ALL CLIENT LOGIN ACCOUNT BY ACCOUNT TYPE SCRIPT==================================================
	@GET
	@Path("/getclientloginaccountscript")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClientLoginAccountScript() {
		
		//GET ALL LOGIN ACCOUNT WITH SCRIPT
		List<ClientLoginAccount>clientLoginAccountList = clientLoginAccountRepo.findAllClientLoginAccountsByAccountType();
		
		//PUT LIST IN JSON DATA
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("clientLoginAccountList", clientLoginAccountList);
		
		//RETURN LOGIN ACCOUNT LIST
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	@POST
	@Path("/uploadppk")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadPPK(@FormDataParam("ppk")InputStream ppkFile,
			@FormDataParam("ppk")FormDataContentDisposition ppkDetails) {
		
		Logger log = Logger.getLogger(getClass());
		log.info(ppkFile);
		log.info(ppkDetails);
		
		
		
		//INSTANTIATE JSON RESPONSE
		JSONObject jsonResponse = new JSONObject();
		
		//UPLOAD FILE
		try {
			
			//INSTANTIATE BOOLEAN CHECK
			boolean checkFlag = false;
			
			//GET ALL CLIENT LOGIN ACCOUNT 
			List<ClientLoginAccount>ppkFiles = clientLoginAccountRepo.findAll();
			
			//LOOP THROUGH THE LIST AND CHECK IF THERE IS EXISTING
			for(ClientLoginAccount ppk : ppkFiles) {
				
				if(ppk.getAccountType().equals(AccountType.tap)) {
					if(ppk.getPpkFilepath().contains(ppkDetails.getFileName())) {
						checkFlag = true;
						break;
					}
				}
			}
			
			log.info(checkFlag);
			//CHECK STATUS OF CHECK FLAG BOOLEAN
			if(!checkFlag) {
				log.info("false");
				
				//CALL UPLOAD FILE FUNCTION
				uploadFile(ppkFile, StoragePathBean.PPK_FOLDER, ppkDetails.getFileName());
				
				//RETURN JSON RESPONSE
				jsonResponse.put("Info", "File Uploaded successfully.");
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
	
}
