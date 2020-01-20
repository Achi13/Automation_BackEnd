package com.oneaston.db.user.service;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.oneaston.db.user.domain.User;
import com.oneaston.db.user.repository.UserRepository;
import com.oneaston.statement.user.impl.RegisterImpl;


@Path("/user")
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
	//CRUD SERVICES------------------------------
	
	//======================================================PROCESS REGISTER===========================================================
	@POST
	@Path("/processregister")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response processRegister(String jsonData) {
		
		//GET DATA FROM JSON
		JSONObject userData = new JSONObject(jsonData);
		
		//INSTANTIATE JSON RESPONSE OBJECT
		JSONObject jsonResponse = new JSONObject();
		
		//CHECK IF USER IS EXISTING
		if(userRepo.findUserByUsername(userData.getString("username")) == null) {
			/*
			//POPULATE USER BEAN WITH JSON DATA
			User user = new User(userData.getString("universeAccessList"), userData.getString("username"),
					userData.getString("password"), userData.getString("userType"), userData.getString("status"));
			//INSERT USER IN DATABASE
			userRepo.save(user);
			
			
			//RETURN RESPONSE TO CLIENT
			*/
			RegisterImpl registerProcess = new RegisterImpl();
			registerProcess.addUser(userData.getString("useraccess"), userData.getString("username"), 
					userData.getString("password"), userData.getString("usertype"), "active");
			
			//RETURN SUCCESS MESSAGE
			jsonResponse.put("Info", "User successfully registered.");
			
			return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		}else {
			//RETURN ERROR MESSAGE
			jsonResponse.put("Error", "Username already taken!");
			//RETURN RESPONSE TO CLIENT
			return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		}
		
		
	}
	
	//=========================================================PROCESS LOGIN==============================================================
	@POST
	@Path("/processlogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response processLogin(String jsonData) {
		Logger log = Logger.getLogger(getClass());
		log.info("access: user service");
		//INSTANTIATE JSON RESPONSE FOR RETURN MESSAGE
		JSONObject jsonResponse = new JSONObject();
		
		//GET DATA FROM JSON
		JSONObject userCredential = new JSONObject(jsonData);
		
		//CHECK IF USER IS REGISTERED
		User user = userRepo.findUserByUsername(userCredential.getString("username"));
		//IF USER EXISTS
		if(user != null && user.getPassword().equals(userCredential.getString("password"))) {
			//CHECK IF USER IS ACTIVE
			if(user.getStatus().equals("active")) {
				
				//PUT USER DETAILS IN JSON OBJECT
				jsonResponse.put("userId", user.getUserId());
				jsonResponse.put("username", user.getUsername());
				jsonResponse.put("password", user.getPassword());
				jsonResponse.put("status", user.getStatus());
				jsonResponse.put("universeAccessList", user.getUniverseAccessList());
				jsonResponse.put("userType", user.getUserType());
				
				//RETURN RESPONSE ENTITY
				return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
			}else {
				jsonResponse.put("Error", "ser inactive. Please contact admin for assistance.");
				return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
			}
		}else {
			jsonResponse.put("Error", "Invalid Username or Password!");
			return Response.status(405).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		}
	}
	
	//===========================================================PROCESS UPDATE======================================================
	@POST
	@Path("/processupdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response processUpdate(String jsonData) {
		//GET DATA FROM JSON
		JSONObject userData = new JSONObject(jsonData);
		
		//INSTANTIATE JSON RESPONSE OBJECT
		JSONObject jsonResponse = new JSONObject();
		
		//FIND USER BY USER ID
		User user = userRepo.findUserByUserId(userData.getLong("userId"));
		
		//POPULATE USER BEAN WITH USER DATA JSON
		user.setPassword(userData.getString("password"));
		user.setStatus(userData.getString("status"));
		user.setUserType(userData.getString("userType"));
		user.setUniverseAccessList(userData.getString("universeAccessList"));
		
		//REGISTER USER
		userRepo.save(user);
		
		//PUT INFO MESSAGE IN RESPONSE
		jsonResponse.put("Info", "User successfully updated.");
		
		//RETURN RESPONSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	
	//GET RESPONSE------------------------------
	
	//GET USER BY USER ID
	@GET
	@Path("/getuserbyid")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@QueryParam("userId")long userId) {
		
		//FIND USER BY USER ID
		User user = userRepo.findUserByUserId(userId);
		
		//INSTANTIATE JSON OBJECT FOR USER DATA
		JSONObject userData = new JSONObject();
		
		//POPULATE USERDATA JSON
		userData.put("universeAccessList", user.getUniverseAccessList());
		userData.put("username", user.getUsername());
		userData.put("password", user.getPassword());
		userData.put("userType", user.getUserType());
		userData.put("status", user.getStatus());
		
		//RETURN RESPONSE WITH DATA
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(userData.toString()).build();
		
	}
	
	//GET ALL USERS
	@GET
	@Path("/getalluser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		
		//GET SIZE OF ALL USER
		int userSize = userRepo.findAll().size();
		
		List<User>userList = userRepo.findAll();
		
		//PASS USER SIZE TO JSON DATA
		JSONObject jsonData = new JSONObject();
		jsonData.put("userSize", userSize);
		jsonData.put("userList", userList);
		
		//RETURN JSON RESPONSE
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonData.toString()).build();
	}
	
	
}
