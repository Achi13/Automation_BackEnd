package com.oneaston.db.universe.service;

import java.io.File;
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
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.oneaston.configuration.bean.StoragePathBean;
import com.oneaston.configuration.enums.AccountType;
import com.oneaston.db.campaign.domain.Story;
import com.oneaston.db.campaign.repository.StoryRepository;
import com.oneaston.db.universe.domain.Client;
import com.oneaston.db.universe.domain.ClientLoginAccount;
import com.oneaston.db.universe.domain.Headers;
import com.oneaston.db.universe.domain.Universe;
import com.oneaston.db.universe.domain.WebAddress;
import com.oneaston.db.universe.repository.ClientLoginAccountRepository;
import com.oneaston.db.universe.repository.ClientRepository;
import com.oneaston.db.universe.repository.HeadersRepository;
import com.oneaston.db.universe.repository.UniverseRepository;
import com.oneaston.db.universe.repository.WebAddressRepository;

@Path("/client")
public class ClientService {
	
	@Autowired
	UniverseRepository universeRepo;
	
	@Autowired
	ClientRepository clientRepo;
	
	@Autowired
	WebAddressRepository webAddressRepo;
	
	@Autowired
	ClientLoginAccountRepository clientLoginAccountRepo;
	
	@Autowired
	HeadersRepository headerRepo;
	
	@Autowired
	StoryRepository storyRepo;
	
	//CLIENT SERVICES----------------------------------------
	
	//========================================================GET ALL CLIENT BY UNIVERSE ID====================================================
	@GET
	@Path("/getclientbyuniverseid")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClientByUniverseId(@QueryParam("universeId")long universeId) {
		
		//GET CLIENTS BY UNIVERSE ID
		List<Client>clientList = clientRepo.findClientsByUniverseId(universeRepo.findUniverseByUniverseId(universeId));
		
		//PUT CLIENT LIST IN JSON OBJECT
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("clientData", clientList);
		
		//RETURN JSON RESPONSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	@GET
	@Path("/getclientbyuniverseidandstoryid")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClientByUniverseIdAndStoryId(@QueryParam("universeId")long universeId,
			@QueryParam("storyId")long storyId) {
		
		//GET CLIENTS BY UNIVERSE ID
		List<Client>clientList = clientRepo.findClientsByUniverseId(universeRepo.findUniverseByUniverseId(universeId));
		
		//GET STORY BY STORY ID
		Story story = storyRepo.findStoryByStoryId(storyId);
		
		//PUT CLIENT LIST AND SERVER IMPORT IN JSON OBJECT
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("clientData", clientList);
		jsonResponse.put("serverImport", story.getIsServerImport());
		
		//RETURN JSON RESPONSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}

	
	@POST
	@Path("/processcreateclient")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response processCreateClient(String jsonData) {
		
		Logger log = Logger.getLogger(getClass());
		
		
		
		//INSTANTIATE JSON RESPONSE
		JSONObject jsonResponse = new JSONObject();
		
		//GET CLIENT DATA IN JSON DATA
		JSONObject data = new JSONObject(jsonData);
		
		log.info(data.toString());
		
		//GET UNIVERSE IN CLIENT DATA
		Universe universeId = universeRepo.findUniverseByUniverseId(data.getLong("universeId"));
		
		
		
		
		log.info(data.get("webAddressList"));
		log.info(data.get("loginCredentialList"));
		log.info(data.get("headerList"));
		log.info(data.get("tapLoginAccountList"));
		
		if(universeId.getUniverseId() == 1) {
			
			//CREATE CLIENT
			Client client = new Client(universeId, data.getString("clientName"));
			clientRepo.save(client);
			
			//GET WEB ADDRESS ARRAY IN WEB ADDRESS DATA
			JSONArray webAddressArray = data.getJSONArray("webAddressList");
			
			//GET ALL PPK IN FOLDER
			File[] ppkFolder = new File(StoragePathBean.PPK_FOLDER).listFiles();
			
			//LOOP THROUGH THE WEB ADDRESS ARRAY
			for(int i=0; i<webAddressArray.length(); i++) {
				
				JSONObject webAddressData = webAddressArray.getJSONObject(i);
				
				log.info(webAddressData);
				
				//CREATE WEBADDRESS
				WebAddress webAddress = new WebAddress(client, webAddressData.getString("url"),
						webAddressData.getString("name"),webAddressData.getString("description"));
				webAddressRepo.save(webAddress);
				
				//GET TAP CONNECTION ARRAY`
				JSONArray tapConnection = data.getJSONArray("tapLoginAccountList");
				
				
				
				//LOOP THROUGH TAP CONNECTION ARRAY
				mainLoop:
				for(int j=0; j<tapConnection.length(); j++) {
					
					JSONObject tapData = tapConnection.getJSONObject(j);
					
					for(File ppkFile : ppkFolder) {
						
						if(ppkFile.getName().equals(tapData.get("ppkFilepath"))) {
							
							log.info(tapData);
							
							ClientLoginAccount tapLogin = new ClientLoginAccount(webAddress,
									tapData.getString("username"), tapData.getString("password"), tapData.getString("hostname"),
									ppkFile.getAbsolutePath(), AccountType.tap);
							clientLoginAccountRepo.save(tapLogin);
							continue mainLoop;
						}
					}
				}
			}
			//GET CLIENT LOGIN ACCOUNT ARRAY
			JSONArray loginCredential = data.getJSONArray("loginCredentialList");
			
			//LOOP THROUGH CLIENT LOGIN ACCOUNT ARRAY
			for(int i=0; i<loginCredential.length(); i++) {
				
				JSONObject loginData = loginCredential.getJSONObject(i);
				
				//CREATE CLIENT LOGIN ACCOUNT
				ClientLoginAccount clientLoginAccount = new ClientLoginAccount(webAddressRepo.findWebAddressByUrl(loginData.getString("webAddress")),
						loginData.getString("username"), loginData.getString("password"), null, null, AccountType.generic);
				clientLoginAccountRepo.save(clientLoginAccount);
			}
			
			//GET LOGIN HEADER ARRAY
			JSONArray loginHeader = data.getJSONArray("headerList");
			
			//LOOP THROUGH LOGIN HEADER DATA ARRAY
			for(int i=0; i<loginHeader.length(); i++) {
				
				JSONObject headerData = loginHeader.getJSONObject(i);
				
				//CREATE LOGIN HEADER DATA
				Headers header = new Headers(webAddressRepo.findWebAddressByUrl(headerData.getString("webAddress")),
						headerData.getString("webElementName"), headerData.getString("webElementNature"),
						headerData.getString("natureOfAction"), headerData.getString("label"));
				headerRepo.save(header);
			}
			
			
			List<ClientLoginAccount>clientLoginAccountList = clientLoginAccountRepo.findAll();
			List<File>fileToDelete = new ArrayList<File>();
			fileLoop:
			for(File file : ppkFolder) {
				for(ClientLoginAccount ppkPath : clientLoginAccountList) {
					if(ppkPath.getAccountType().equals(AccountType.tap)) {
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
		}
		
		
		
		//RETURN JSON RESPONSE
		jsonResponse.put("Info", "Client succesfully registered");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		
		
	}
	
}
