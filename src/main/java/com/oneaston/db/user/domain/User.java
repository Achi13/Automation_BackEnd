package com.oneaston.db.user.domain;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	private long userId;
	
	@Column(name="universe_access_list")
	private String universeAccessList;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="user_type")
	private String userType;
	
	@Column(name="status")
	private String status;

	public User() {
	}

	public User(String universeAccessList, String username, String password, String userType, String status) {
		super();
		this.universeAccessList = universeAccessList;
		this.username = username;
		this.password = password;
		this.userType = userType;
		this.status = status;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUniverseAccessList() {
		return universeAccessList;
	}

	public void setUniverseAccessList(String universeAccessList) {
		this.universeAccessList = universeAccessList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
