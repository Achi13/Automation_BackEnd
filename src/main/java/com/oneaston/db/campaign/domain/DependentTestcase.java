package com.oneaston.db.campaign.domain;

import javax.persistence.*;

import com.oneaston.db.template.domain.Templates;
import com.oneaston.db.universe.domain.Client;
import com.oneaston.db.universe.domain.ClientLoginAccount;
import com.oneaston.db.universe.domain.Universe;
import com.oneaston.db.universe.domain.WebAddress;
import com.oneaston.db.user.domain.User;

@Entity
@Table(name="dependent_testcase")
public class DependentTestcase {
	
	@Id
	@Column(name="testcase_number")
	private String testcaseNumber;
	
	@JoinColumn(name="story_id")
	@ManyToOne
	private Story storyId;
	
	@JoinColumn(name="user_id")
	@ManyToOne
	private User userId;
	
	@JoinColumn(name="template_id")
	@ManyToOne
	private Templates templateId;
	
	@JoinColumn(name="universe_id")
	@ManyToOne
	private Universe universeId;
	
	@JoinColumn(name="client_id")
	@ManyToOne
	private Client clientId;
	
	@JoinColumn(name="web_address_id")
	@ManyToOne
	private WebAddress webAddressId;
	
	@JoinColumn(name="login_account_id")
	@ManyToOne
	private ClientLoginAccount loginAccountId;
	
	@Column(name="embedded_Script")
	private String embeddedScript;
	
	@Column(name="description")
	private String description;
	
	@Column(name="status")
	private String status;

	@Column(name="tap_import_status")
	private String tapImportStatus;
	
	@Column(name="stored_values")
	private String storedValues;
	
	@Column(name="execution_version_start")
	private int executionVersionStart;
	
	@Column(name="execution_version_current")
	private int executionVersionCurrent;
	
	@Column(name="priority")
	private long priority;
	
	@Column(name="template_version")
	int templateVersion;
	
	public DependentTestcase() {}

	public DependentTestcase(String testcaseNumber, Story storyId, User userId, Templates templateId,
			Universe universeId, Client clientId, WebAddress webAddressId, ClientLoginAccount loginAccountId,
			String description, String status, int executionVersionStart, int executionVersionCurrent,
			int templateVersion) {
		super();
		this.testcaseNumber = testcaseNumber;
		this.storyId = storyId;
		this.userId = userId;
		this.templateId = templateId;
		this.universeId = universeId;
		this.clientId = clientId;
		this.webAddressId = webAddressId;
		this.loginAccountId = loginAccountId;
		this.description = description;
		this.status = status;
		this.executionVersionStart = executionVersionStart;
		this.executionVersionCurrent = executionVersionCurrent;
		this.templateVersion = templateVersion;
	}

	public String getTestcaseNumber() {
		return testcaseNumber;
	}

	public void setTestcaseNumber(String testcaseNumber) {
		this.testcaseNumber = testcaseNumber;
	}

	public Story getStoryId() {
		return storyId;
	}

	public void setStoryId(Story storyId) {
		this.storyId = storyId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Templates getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Templates templateId) {
		this.templateId = templateId;
	}

	public Universe getUniverseId() {
		return universeId;
	}

	public void setUniverseId(Universe universeId) {
		this.universeId = universeId;
	}

	public Client getClientId() {
		return clientId;
	}

	public void setClientId(Client clientId) {
		this.clientId = clientId;
	}

	public WebAddress getWebAddressId() {
		return webAddressId;
	}

	public void setWebAddressId(WebAddress webAddressId) {
		this.webAddressId = webAddressId;
	}

	public ClientLoginAccount getLoginAccountId() {
		return loginAccountId;
	}

	public void setLoginAccountId(ClientLoginAccount loginAccountId) {
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

	public int getTemplateVersion() {
		return templateVersion;
	}

	public void setTemplateVersion(int templateVersion) {
		this.templateVersion = templateVersion;
	}

	public String getTapImportStatus() {
		return tapImportStatus;
	}

	public void setTapImportStatus(String tapImportStatus) {
		this.tapImportStatus = tapImportStatus;
	}

	public String getEmbeddedScript() {
		return embeddedScript;
	}

	public void setEmbeddedScript(String embeddedScript) {
		this.embeddedScript = embeddedScript;
	}

	public String getStoredValues() {
		return storedValues;
	}

	public void setStoredValues(String storedValues) {
		this.storedValues = storedValues;
	}

	public long getPriority() {
		return priority;
	}

	public void setPriority(long priority) {
		this.priority = priority;
	}
	
}
