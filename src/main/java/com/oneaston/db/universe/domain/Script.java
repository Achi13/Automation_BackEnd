package com.oneaston.db.universe.domain;

import javax.persistence.*;

@Entity
@Table(name="script")
public class Script {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="script_id")
	private long scriptId;
	
	@JoinColumn(name="login_account_id")
	@ManyToOne
	private ClientLoginAccount loginAccountId;
	
	@JoinColumn(name="universe_id")
	@ManyToOne
	private Universe universeId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="script_filepath")
	private String scriptFilepath;
	
	public Script() {}

	public Script(ClientLoginAccount loginAccountId, Universe universeId, String name, String description,
			String scriptFilepath) {
		super();
		this.loginAccountId = loginAccountId;
		this.universeId = universeId;
		this.name = name;
		this.description = description;
		this.scriptFilepath = scriptFilepath;
	}

	public long getScriptId() {
		return scriptId;
	}

	public void setScriptId(long scriptId) {
		this.scriptId = scriptId;
	}

	public ClientLoginAccount getLoginAccountId() {
		return loginAccountId;
	}

	public void setLoginAccountId(ClientLoginAccount loginAccountId) {
		this.loginAccountId = loginAccountId;
	}

	public Universe getUniverseId() {
		return universeId;
	}

	public void setUniverseId(Universe universeId) {
		this.universeId = universeId;
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

	public String getScriptFilepath() {
		return scriptFilepath;
	}

	public void setScriptFilepath(String scriptFilepath) {
		this.scriptFilepath = scriptFilepath;
	}
	
	
}
