package com.oneaston.db.testcase.domain;

import javax.persistence.*;

import com.oneaston.db.campaign.domain.DependentTestcase;



@Entity
@Table(name="testcase_footer_data")
public class TestcaseFooterData {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="footer_data_id")
	private long footerDataId;
	
	@JoinColumn(name="testcase_number")
	@ManyToOne
	private DependentTestcase testcaseNumber;
	
	@Column(name="assigned_account")
	private String assignedAccount;
	
	@Column(name="client_name")
	private String clientName;
	
	@Column(name="is_ignore_severity")
	private boolean isIgnoreSeverity;
	
	@Column(name="sender")
	private String sender;
	
	@Column(name="is_server_import")
	private boolean isServerimport;
	
	@Column(name="testcase_status")
	private String testcaseStatus;
	
	@Column(name="transaction_type")
	private String transactionType;
	
	@Column(name="url")
	private String url;
	
	@Column(name="tap_import_status")
	private String tapImportStatus;
	
	@Column(name="execution_version")
	private int executionVersion;
	
	public TestcaseFooterData() {}

	public TestcaseFooterData(DependentTestcase testcaseNumber, String assignedAccount, String clientName,
			boolean isIgnoreSeverity, String sender, boolean isServerimport, String testcaseStatus,
			String transactionType, String url, int executionVersion) {
		super();
		this.testcaseNumber = testcaseNumber;
		this.assignedAccount = assignedAccount;
		this.clientName = clientName;
		this.isIgnoreSeverity = isIgnoreSeverity;
		this.sender = sender;
		this.isServerimport = isServerimport;
		this.testcaseStatus = testcaseStatus;
		this.transactionType = transactionType;
		this.url = url;
		this.executionVersion = executionVersion;
	}

	public long getFooterDataId() {
		return footerDataId;
	}

	public void setFooterDataId(long footerDataId) {
		this.footerDataId = footerDataId;
	}

	public DependentTestcase getTestcaseNumber() {
		return testcaseNumber;
	}

	public void setTestcaseNumber(DependentTestcase testcaseNumber) {
		this.testcaseNumber = testcaseNumber;
	}

	public String getAssignedAccount() {
		return assignedAccount;
	}

	public void setAssignedAccount(String assignedAccount) {
		this.assignedAccount = assignedAccount;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public boolean getIsIgnoreSeverity() {
		return isIgnoreSeverity;
	}

	public void setIsIgnoreSeverity(boolean isIgnoreSeverity) {
		this.isIgnoreSeverity = isIgnoreSeverity;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public boolean getIsServerimport() {
		return isServerimport;
	}

	public void setIsServerimport(boolean isServerimport) {
		this.isServerimport = isServerimport;
	}

	public String getTestcaseStatus() {
		return testcaseStatus;
	}

	public void setTestcaseStatus(String testcaseStatus) {
		this.testcaseStatus = testcaseStatus;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getExecutionVersion() {
		return executionVersion;
	}

	public void setExecutionVersion(int executionVersion) {
		this.executionVersion = executionVersion;
	}

	public String getTapImportStatus() {
		return tapImportStatus;
	}

	public void setTapImportStatus(String tapImportStatus) {
		this.tapImportStatus = tapImportStatus;
	}
	
}
