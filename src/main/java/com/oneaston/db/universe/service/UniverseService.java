package com.oneaston.db.universe.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.oneaston.db.universe.domain.Universe;
import com.oneaston.db.universe.repository.UniverseRepository;

@Path("/universe")
public class UniverseService {
	
	@Autowired
	UniverseRepository universeRepo;
	
	//UNIVERSE SERVICES----------------------------------------
	
	//==============================================GET ALL UNIVERSE=====================================
	@GET
	@Path("/getalluniverse")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUniverse() {
		Logger log = Logger.getLogger(getClass());
		log.info("access: Get Universe");
		
		//GET ALL UNIVERSE IN DATABASE
		List<Universe>universeList = universeRepo.findAll();
		
		//PUT UNIVERSE LIST IN DATABASE
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("universeList", universeList);
		
		//RETURN JSON RESPONSE WITH UNIVERSE LIST
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	@GET
	@Path("/getuniverse")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUniverse(@QueryParam("universeId")long universeId) {
		//GET UNIVERSE BY UNIVERSE ID
		Universe universe = universeRepo.findUniverseByUniverseId(universeId);
		
		//PUT UNIVERSE IN JSON 
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("universeId", universe.getUniverseId());
		jsonResponse.put("universeName", universe.getUniverseName());
		
		//RETURN JSON RESPONSE WITH UNIVERSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
}
