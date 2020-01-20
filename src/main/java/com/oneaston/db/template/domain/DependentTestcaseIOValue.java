package com.oneaston.db.template.domain;

import javax.persistence.*;

import com.oneaston.db.campaign.domain.DependentTestcase;

@Entity
@Table(name="dependent_testcase_io_value")
public class DependentTestcaseIOValue {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="io_id")
	private long ioId;
	
	@JoinColumn(name="testcase_number")
	@ManyToOne
	private DependentTestcase testcaseNumber;
	
	@JoinColumn(name="template_data_id")
	@ManyToOne
	private TemplateData templateDataId;
	
	@Column(name="io_value")
	private String ioValue;

	public DependentTestcaseIOValue() {}
	
	public DependentTestcaseIOValue(DependentTestcase testcaseNumber, TemplateData templateDataId, String ioValue) {
		super();
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

	public DependentTestcase getTestcaseNumber() {
		return testcaseNumber;
	}

	public void setTestcaseNumber(DependentTestcase testcaseNumber) {
		this.testcaseNumber = testcaseNumber;
	}

	public TemplateData getTemplateDataId() {
		return templateDataId;
	}

	public void setTemplateDataId(TemplateData templateDataId) {
		this.templateDataId = templateDataId;
	}

	public String getIoValue() {
		return ioValue;
	}

	public void setIoValue(String ioValue) {
		this.ioValue = ioValue;
	}
	
	
}
