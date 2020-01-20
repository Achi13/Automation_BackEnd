package com.oneaston.archive.testcase.domain;

import javax.persistence.*;


@Entity
@Table(name="testcase_actual_data")
public class TestcaseActualDataArchive {
	
	@Id
	@Column(name="actual_data_id")
	private long actualDataId;
	
	@Column(name="testcase_number")
	private String testcaseNumber;
	
	@Column(name="input_output_value")
	private String inputOutputValue;
	
	@Column(name="label")
	private String label;
	
	@Column(name="nature_of_action")
	private String natureOfAction;
	
	@Column(name="remarks")
	private String remarks;
	
	@Column(name="screenshot_path")
	private String screenshotPath;
	
	@Column(name="is_screen_capture")
	private boolean isScreenCapture;
	
	@Column(name="timestamp")
	private String timestamp;
	
	@Column(name="is_trigger_enter")
	private boolean isTriggerEnter;
	
	@Column(name="web_element_name")
	private String webElementName;
	
	@Column(name="web_element_nature")
	private String webElementNature;
	
	@Column(name="log_field")
	private String logField;
	
	@Column(name="execution_version")
	private int executionVersion;
	
	public TestcaseActualDataArchive() {}

	public TestcaseActualDataArchive(long actualDataId, String testcaseNumber,
			String inputOutputValue, String label, String natureOfAction, String remarks, String screenshotPath,
			boolean isScreenCapture, String timestamp, boolean isTriggerEnter, String webElementName,
			String webElementNature, String logField, int executionVersion) {
		super();
		this.actualDataId = actualDataId;
		this.testcaseNumber = testcaseNumber;
		this.inputOutputValue = inputOutputValue;
		this.label = label;
		this.natureOfAction = natureOfAction;
		this.remarks = remarks;
		this.screenshotPath = screenshotPath;
		this.isScreenCapture = isScreenCapture;
		this.timestamp = timestamp;
		this.isTriggerEnter = isTriggerEnter;
		this.webElementName = webElementName;
		this.webElementNature = webElementNature;
		this.logField = logField;
		this.executionVersion = executionVersion;
	}

	public long getActualDataId() {
		return actualDataId;
	}

	public void setActualDataId(long actualDataId) {
		this.actualDataId = actualDataId;
	}

	public String getTestcaseNumber() {
		return testcaseNumber;
	}

	public void setTestcaseNumber(String testcaseNumber) {
		this.testcaseNumber = testcaseNumber;
	}

	public String getInputOutputValue() {
		return inputOutputValue;
	}

	public void setInputOutputValue(String inputOutputValue) {
		this.inputOutputValue = inputOutputValue;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getNatureOfAction() {
		return natureOfAction;
	}

	public void setNatureOfAction(String natureOfAction) {
		this.natureOfAction = natureOfAction;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getScreenshotPath() {
		return screenshotPath;
	}

	public void setScreenshotPath(String screenshotPath) {
		this.screenshotPath = screenshotPath;
	}

	public boolean isScreenCapture() {
		return isScreenCapture;
	}

	public void setScreenCapture(boolean isScreenCapture) {
		this.isScreenCapture = isScreenCapture;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isTriggerEnter() {
		return isTriggerEnter;
	}

	public void setTriggerEnter(boolean isTriggerEnter) {
		this.isTriggerEnter = isTriggerEnter;
	}

	public String getWebElementName() {
		return webElementName;
	}

	public void setWebElementName(String webElementName) {
		this.webElementName = webElementName;
	}

	public String getWebElementNature() {
		return webElementNature;
	}

	public void setWebElementNature(String webElementNature) {
		this.webElementNature = webElementNature;
	}

	public String getLogField() {
		return logField;
	}

	public void setLogField(String logField) {
		this.logField = logField;
	}

	public int getExecutionVersion() {
		return executionVersion;
	}

	public void setExecutionVersion(int executionVersion) {
		this.executionVersion = executionVersion;
	}

	
	
}
