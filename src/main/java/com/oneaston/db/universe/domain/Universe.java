package com.oneaston.db.universe.domain;

import javax.persistence.*;

@Entity
@Table(name="universe")
public class Universe {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="universe_id")
	private long universeId;
	
	@Column(name="universe_name")
	private String universeName;

	public Universe() {}
	
	public Universe(String universeName) {
		super();
		this.universeName = universeName;
	}

	public long getUniverseId() {
		return universeId;
	}

	public void setUniverseId(long universeId) {
		this.universeId = universeId;
	}

	public String getUniverseName() {
		return universeName;
	}

	public void setUniverseName(String universeName) {
		this.universeName = universeName;
	}
	
	
	
	

}
