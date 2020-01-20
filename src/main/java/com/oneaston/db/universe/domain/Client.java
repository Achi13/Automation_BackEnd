package com.oneaston.db.universe.domain;

import javax.persistence.*;

@Entity
@Table(name="client")
public class Client {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="client_id")
	private long clientId;
	
	@JoinColumn(name="universe_id")
	@ManyToOne
	private Universe universeId;
	
	@Column(name="client_name", unique=true)
	private String clientName;
	
	public Client() {}

	public Client(Universe universeId, String clientName) {
		super();
		this.universeId = universeId;
		this.clientName = clientName;
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public Universe getUniverseId() {
		return universeId;
	}

	public void setUniverseId(Universe universeId) {
		this.universeId = universeId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	

}
