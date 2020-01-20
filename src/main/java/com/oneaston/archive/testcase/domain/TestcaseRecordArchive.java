package com.oneaston.archive.testcase.domain;

import javax.persistence.*;


@Entity
@Table(name="testcase_record")
public class TestcaseRecordArchive {
	
	@Id
	@Column(name="record_id")
	private long recordId;
	
	@Column(name="client_id")
	private long clientId;
	
	@Column(name="testcase_number")
	private String testcaseNumber;
	
	@Column(name="user_id")
	private long userId;
	
	@Column(name="universe_id")
	private long universeId;
	
	@Column(name="client_name")
	private String clientName;
	
	@Column(name="description")
	private String description;
	
	@Column(name="status")
	private String status;
	
	@Column(name="execution_schedule")
	private String executionSchedule;
	
	@Column(name="execution_version")
	private int executionVersion;
	
	public TestcaseRecordArchive() {}

	public TestcaseRecordArchive(long recordId, long clientId, String testcaseNumber, long userId,
			long universeId, String clientName, String description, String status, String executionSchedule,
			int executionVersion) {
		super();
		this.recordId = recordId;
		this.clientId = clientId;
		this.testcaseNumber = testcaseNumber;
		this.userId = userId;
		this.universeId = universeId;
		this.clientName = clientName;
		this.description = description;
		this.status = status;
		this.executionSchedule = executionSchedule;
		this.executionVersion = executionVersion;
	}

	public long getRecordId() {
		return recordId;
	}

	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public String getTestcaseNumber() {
		return testcaseNumber;
	}

	public void setTestcaseNumber(String testcaseNumber) {
		this.testcaseNumber = testcaseNumber;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUniverseId() {
		return universeId;
	}

	public void setUniverseId(long universeId) {
		this.universeId = universeId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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

	public String getExecutionSchedule() {
		return executionSchedule;
	}

	public void setExecutionSchedule(String executionSchedule) {
		this.executionSchedule = executionSchedule;
	}

	public int getExecutionVersion() {
		return executionVersion;
	}

	public void setExecutionVersion(int executionVersion) {
		this.executionVersion = executionVersion;
	}
	
	
	
}
