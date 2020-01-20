package com.oneaston.db.testcase.domain;

import javax.persistence.*;

import com.oneaston.db.campaign.domain.DependentTestcase;
import com.oneaston.db.universe.domain.Client;
import com.oneaston.db.universe.domain.Universe;
import com.oneaston.db.user.domain.User;



@Entity
@Table(name="testcase_record")
public class TestcaseRecord {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="record_id")
	private long recordId;
	
	@JoinColumn(name="client_id")
	@ManyToOne
	private Client clientId;
	
	@JoinColumn(name="testcase_number")
	@ManyToOne
	private DependentTestcase testcaseNumber;
	
	@JoinColumn(name="user_id")
	@ManyToOne
	private User userId;
	
	@JoinColumn(name="universe_id")
	@ManyToOne
	private Universe universeId;
	
	@Column(name="description")
	private String description;
	
	@Column(name="status")
	private String status;
	
	@Column(name="execution_schedule")
	private String executionSchedule;
	
	@Column(name="execution_version")
	private int executionVersion;
	
	public TestcaseRecord() {}

	public TestcaseRecord(Client clientId, DependentTestcase testcaseNumber, User userId, Universe universeId,
			String description, String status, String executionSchedule, int executionVersion) {
		super();
		this.clientId = clientId;
		this.testcaseNumber = testcaseNumber;
		this.userId = userId;
		this.universeId = universeId;
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

	public Client getClientId() {
		return clientId;
	}

	public void setClientId(Client clientId) {
		this.clientId = clientId;
	}

	public DependentTestcase getTestcaseNumber() {
		return testcaseNumber;
	}

	public void setTestcaseNumber(DependentTestcase testcaseNumber) {
		this.testcaseNumber = testcaseNumber;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Universe getUniverseId() {
		return universeId;
	}

	public void setUniverseId(Universe universeId) {
		this.universeId = universeId;
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
