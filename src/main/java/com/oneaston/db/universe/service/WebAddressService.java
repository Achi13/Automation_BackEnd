package com.oneaston.db.universe.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.oneaston.db.universe.domain.WebAddress;
import com.oneaston.db.universe.repository.ClientRepository;
import com.oneaston.db.universe.repository.WebAddressRepository;

@Path("/webaddress")
public class WebAddressService {
	
	@Autowired
	ClientRepository clientRepo;
	
	@Autowired
	WebAddressRepository webAddressRepo;
	
	//WEB ADDRESS SERVICES
	
	//============================================================GET WEB ADDRESS BY CLIENT ID=========================================================
	@GET
	@Path("/getwebaddress")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWebAddress(@QueryParam("clientId")long clientId) {
		
		//GET LIST OF WEB ADDRESS BY CLIENT ID
		List<WebAddress>webAddressList = webAddressRepo.findWebAddressesByClientId(clientRepo.findClientByClientId(clientId));
		
		//PUT WEB ADDRESS LIST IN JSON OBJECT
		JSONObject webAddressData = new JSONObject();
		webAddressData.put("webAddressData", webAddressList);
		
		//RETURN WEB ADDRESS RESPONSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(webAddressData.toString()).build();
	}

}
