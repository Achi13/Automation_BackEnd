package com.oneaston.db.universe.domain;

import javax.persistence.*;

@Entity
@Table(name="tap_server_credential")
public class TapServerCredential {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="credential_id")
	private long credentialId;
	
	@JoinColumn(name="web_address_id")
	@OneToOne
	private WebAddress webAddressId;
	
	@Column(name="hostname")
	private String hostname;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="ppk_path")
	private String ppkPath;
	
	public TapServerCredential() {}

	public TapServerCredential(WebAddress webAddressId, String hostname, String username, String password,
			String ppkPath) {
		super();
		this.webAddressId = webAddressId;
		this.hostname = hostname;
		this.username = username;
		this.password = password;
		this.ppkPath = ppkPath;
	}

	public long getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(long credentialId) {
		this.credentialId = credentialId;
	}

	public WebAddress getWebAddressId() {
		return webAddressId;
	}

	public void setWebAddressId(WebAddress webAddressId) {
		this.webAddressId = webAddressId;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPpkPath() {
		return ppkPath;
	}

	public void setPpkPath(String ppkPath) {
		this.ppkPath = ppkPath;
	}
	
	
	
}
