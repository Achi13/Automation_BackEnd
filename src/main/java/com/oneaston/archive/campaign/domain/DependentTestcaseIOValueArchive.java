package com.oneaston.archive.campaign.domain;

import javax.persistence.*;

@Entity
@Table(name="dependent_testcase_io_value")
public class DependentTestcaseIOValueArchive {

	@Id
	@Column(name="io_id")
	private long ioId;
	
	@Column(name="testcase_number")
	private String testcaseNumber;
	
	@Column(name="template_data_id")
	private long templateDataId;
	
	@Column(name="io_value")
	private String ioValue;
	
	public DependentTestcaseIOValueArchive() {}

	public DependentTestcaseIOValueArchive(long ioId, String testcaseNumber, long templateDataId, String ioValue) {
		super();
		this.ioId = ioId;
		this.testcaseNumber = testcaseNumber;
		this.templateDataId = templateDataId;
		this.ioValue = ioValue;
	}

	public long getIoId() {
		return ioId;
	}

	public void setIoId(long ioId) {
		this.ioId = ioId;
	}

	public String getTestcaseNumber() {
		return testcaseNumber;
	}

	public void setTestcaseNumber(String testcaseNumber) {
		this.testcaseNumber = testcaseNumber;
	}

	public long getTemplateDataId() {
		return templateDataId;
	}

	public void setTemplateDataId(long templateDataId) {
		this.templateDataId = templateDataId;
	}

	public String getIoValue() {
		return ioValue;
	}

	public void setIoValue(String ioValue) {
		this.ioValue = ioValue;
	}

	
}
