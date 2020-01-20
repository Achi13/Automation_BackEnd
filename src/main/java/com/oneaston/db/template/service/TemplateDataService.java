
package com.oneaston.db.template.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.oneaston.db.template.domain.TemplateData;
import com.oneaston.db.template.repository.TemplateDataRepository;
import com.oneaston.db.template.repository.TemplatesRepository;


@Path("/templatedata")
public class TemplateDataService {
	
	@Autowired
	TemplateDataRepository templateDataRepo;
	
	@Autowired
	TemplatesRepository templatesRepo;
	
	//TEMPLATE DATA SERVICES-----------------------------------------------------------------
	
	//===========================================================GET TEMPLATE DATA FUNCTION====================================================================
	@GET
	@Path("/gettemplatedata")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTemplateData(@QueryParam("templateId")long templateId) {
		
		//GET ALL TEMPLATE DATA BY TEMPLATE ID
		List<TemplateData>templateDataList = templateDataRepo.findTemplateDatasByTemplateId(templatesRepo.findTemplatesByTemplateId(templateId));
		
		//PUT DATA IN JSON
		JSONObject templateData = new JSONObject();
		templateData.put("templateData", templateDataList);
		
		//RETURN JSON OBJECT
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(templateData.toString()).build();
	}
	
}
