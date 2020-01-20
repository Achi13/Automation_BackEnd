package com.oneaston.statement.user.impl;


import com.oneaston.statement.user.RegisterUser;

public class RegisterImpl {
	
	public void addUser(String accessibleUniverse, String userName, String password, String userType, String status) {
		
		
		RegisterUser dbConnectJr = new RegisterUser();
		dbConnectJr.dataBaseController(1, accessibleUniverse, userName, password, userType, status, 0);
	}
	
	public void modifyUser(String accessibleUniverse, String password, String userType, String status, Long userId) {
		
		
		RegisterUser dbConnectJr = new RegisterUser();
		dbConnectJr.dataBaseController(2, accessibleUniverse, null, password, userType, status, userId);
		
	}
	
}
