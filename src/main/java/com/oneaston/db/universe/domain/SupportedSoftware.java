package com.oneaston.db.universe.domain;

import javax.persistence.*;

@Entity
@Table(name="supported_software")
public class SupportedSoftware {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="software_id")
	private long softwareId;
	
	@JoinColumn(name="client_id")
	@OneToOne
	private Client clientId;
	
	@Column(name="client_name")
	private String clientName;

	public SupportedSoftware(Client clientId, String clientName) {
		super();
		this.clientId = clientId;
		this.clientName = clientName;
	}

	public long getSoftwareId() {
		return softwareId;
	}

	public void setSoftwareId(long softwareId) {
		this.softwareId = softwareId;
	}

	public Client getClientId() {
		return clientId;
	}

	public void setClientId(Client clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	

}
