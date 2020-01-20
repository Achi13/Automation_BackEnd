package com.oneaston.db.universe.domain;

import javax.persistence.*;

import com.oneaston.configuration.enums.AccountType;


@Entity
@Table(name="client_login_account")
public class ClientLoginAccount {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="login_account_id")
	private long loginAccountId;
	
	@JoinColumn(name="web_address_id")
	@ManyToOne
	private WebAddress webAddressId;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="hostname")
	private String hostname;
	
	@Column(name="ppk_filepath")
	private String ppkFilepath;
	
	@Column(name="account_type")
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	
	@Column(name="description")
	private String description;
	
	
	public ClientLoginAccount() {}


	public ClientLoginAccount(WebAddress webAddressId, String username, String password, String hostname,
			String ppkFilepath, AccountType accountType) {
		super();
		this.webAddressId = webAddressId;
		this.username = username;
		this.password = password;
		this.hostname = hostname;
		this.ppkFilepath = ppkFilepath;
		this.accountType = accountType;
	}


	public long getLoginAccountId() {
		return loginAccountId;
	}


	public void setLoginAccountId(long loginAccountId) {
		this.loginAccountId = loginAccountId;
	}


	public WebAddress getWebAddressId() {
		return webAddressId;
	}


	public void setWebAddressId(WebAddress webAddressId) {
		this.webAddressId = webAddressId;
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


	public String getHostname() {
		return hostname;
	}


	public void setHostname(String hostname) {
		this.hostname = hostname;
	}


	public String getPpkFilepath() {
		return ppkFilepath;
	}


	public void setPpkFilepath(String ppkFilepath) {
		this.ppkFilepath = ppkFilepath;
	}


	public AccountType getAccountType() {
		return accountType;
	}


	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
}
