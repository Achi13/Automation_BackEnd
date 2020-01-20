package com.oneaston.db.universe.domain;

import javax.persistence.*;

@Entity
@Table(name="headers")
public class Headers {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="header_id")
	private long headerId;
	
	@JoinColumn(name="web_address_id")
	@ManyToOne
	private WebAddress webAddressId;
	
	@Column(name="web_element_name")
	private String webElementName;
	
	@Column(name="web_element_nature")
	private String webElementNature;
	
	@Column(name="nature_of_action")
	private String natureOfAction;
	
	@Column(name="label")
	private String label;

	public Headers() {}
	
	public Headers(WebAddress webAddressId, String webElementName, String webElementNature, String natureOfAction, String label) {
		super();
		this.webAddressId = webAddressId;
		this.webElementName = webElementName;
		this.webElementNature = webElementNature;
		this.natureOfAction = natureOfAction;
		this.label = label;
	}

	public long getHeaderId() {
		return headerId;
	}

	public void setHeaderId(long headerId) {
		this.headerId = headerId;
	}

	public WebAddress getWebAddressId() {
		return webAddressId;
	}

	public void setWebAddressId(WebAddress webAddressId) {
		this.webAddressId = webAddressId;
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

	public String getNatureOfAction() {
		return natureOfAction;
	}

	public void setNatureOfAction(String natureOfAction) {
		this.natureOfAction = natureOfAction;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
