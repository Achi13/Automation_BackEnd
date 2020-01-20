
package com.oneaston.db.template.domain;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="template_data")
@DynamicUpdate
public class TemplateData {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="template_data_id")
	private long templateDataId;
	
	@JoinColumn(name="template_id")
	@ManyToOne
	private Templates templateId;
	
	@Column(name="input_output_value")
	private String inputOutputValue;
	
	@Column(name="label")
	private String label;
	
	@Column(name="nature_of_action")
	private String natureOfAction;
	
	@Column(name="is_screen_capture")
	private boolean isScreenCapture;
	
	@Column(name="is_trigger_enter")
	private boolean isTriggerEnter;
	
	@Column(name="web_element_name")
	private String webElementName;
	
	@Column(name="web_element_nature")
	private String webElementNature;
	
	public TemplateData() {}

	public TemplateData(Templates templateId, String label, String natureOfAction,
			boolean isScreenCapture, boolean isTriggerEnter, String webElementName, String webElementNature) {
		super();
		this.templateId = templateId;
		this.label = label;
		this.natureOfAction = natureOfAction;
		this.isScreenCapture = isScreenCapture;
		this.isTriggerEnter = isTriggerEnter;
		this.webElementName = webElementName;
		this.webElementNature = webElementNature;
	}

	public long getTemplateDataId() {
		return templateDataId;
	}

	public void setTemplateDataId(long templateDataId) {
		this.templateDataId = templateDataId;
	}

	public Templates getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Templates templateId) {
		this.templateId = templateId;
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

	public boolean isScreenCapture() {
		return isScreenCapture;
	}

	public void setScreenCapture(boolean isScreenCapture) {
		this.isScreenCapture = isScreenCapture;
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
	
	
	
}
