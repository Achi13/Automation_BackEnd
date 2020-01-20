package com.oneaston.archive.campaign.domain;

import javax.persistence.*;

@Entity
@Table(name="dependent_testcase")
public class DependentTestcaseArchive {
	
	@Id
	@Column(name="testcase_number")
	private String testcaseNumber;
	
	@Column(name="story_id")
	private long storyId;
	
	@Column(name="user_id")
	private long userId;
	
	@Column(name="template_id")
	private long templateId;
	
	@Column(name="universe_id")
	private long universeId;
	
	@Column(name="web_address_id")
	private long webAddressId;
	
	@Column(name="client_id")
	private long clientId;
	
	@Column(name="login_account_id")
	private long loginAccountId;
	
	@Column(name="description")
	private String description;
	
	@Column(name="status")
	private String status;
	
	@Column(name="template_version")
	private int templateVersion;
	
	@Column(name="execution_version_start")
	private int executionVersionStart;
	
	@Column(name="execution_version_current")
	private int executionVersionCurrent;
	
	public DependentTestcaseArchive() {}

	public DependentTestcaseArchive(String testcaseNumber, long storyId, long userId, long templateId,
			long universeId, long webAddressId, long clientId, long loginAccountId, String description, String status,
			int templateVersion, int executionVersionStart, int executionVersionCurrent) {
		super();
		this.testcaseNumber = testcaseNumber;
		this.storyId = storyId;
		this.userId = userId;
		this.templateId = templateId;
		this.universeId = universeId;
		this.webAddressId = webAddressId;
		this.clientId = clientId;
		this.loginAccountId = loginAccountId;
		this.description = description;
		this.status = status;
		this.templateVersion = templateVersion;
		this.executionVersionStart = executionVersionStart;
		this.executionVersionCurrent = executionVersionCurrent;
	}

	public String getTestcaseNumber() {
		return testcaseNumber;
	}

	public void setTestcaseNumber(String testcaseNumber) {
		this.testcaseNumber = testcaseNumber;
	}

	public long getStoryId() {
		return storyId;
	}

	public void setStoryId(long storyId) {
		this.storyId = storyId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public long getUniverseId() {
		return universeId;
	}

	public void setUniverseId(long universeId) {
		this.universeId = universeId;
	}

	public long getWebAddressId() {
		return webAddressId;
	}

	public void setWebAddressId(long webAddressId) {
		this.webAddressId = webAddressId;
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public long getLoginAccountId() {
		return loginAccountId;
	}

	public void setLoginAccountId(long loginAccountId) {
		this.loginAccountId = loginAccountId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTemplateVersion() {
		return templateVersion;
	}

	public void setTemplateVersion(int templateVersion) {
		this.templateVersion = templateVersion;
	}

	public int getExecutionVersionStart() {
		return executionVersionStart;
	}

	public void setExecutionVersionStart(int executionVersionStart) {
		this.executionVersionStart = executionVersionStart;
	}

	public int getExecutionVersionCurrent() {
		return executionVersionCurrent;
	}

	public void setExecutionVersionCurrent(int executionVersionCurrent) {
		this.executionVersionCurrent = executionVersionCurrent;
	}

	

	
	
}
