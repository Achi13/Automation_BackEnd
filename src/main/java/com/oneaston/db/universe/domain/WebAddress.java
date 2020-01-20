package com.oneaston.db.universe.domain;

import javax.persistence.*;

@Entity
@Table(name="web_address")
public class WebAddress {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="web_address_id")
	private long webAddressId;
	
	@JoinColumn(name="client_id")
	@ManyToOne
	private Client clientId;
	
	@Column(name="url", unique=true)
	private String url;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;

	
	public WebAddress() {}

	public WebAddress(Client clientId, String url, String name, String description) {
		super();
		this.clientId = clientId;
		this.url = url;
		this.name = name;
		this.description = description;
	}

	public long getWebAddressId() {
		return webAddressId;
	}

	public void setWebAddressId(long webAddressId) {
		this.webAddressId = webAddressId;
	}

	public Client getClientId() {
		return clientId;
	}

	public void setClientId(Client clientId) {
		this.clientId = clientId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
