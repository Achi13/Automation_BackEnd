package com.oneaston.db.universe.domain;

import javax.persistence.*;

@Entity
@Table(name="login_status_tracker")
public class LoginStatusTracker {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="login_status_tracker_id")
	private long loginStatusTrackerId;
	
	@JoinColumn(name="login_account_id")
	@OneToOne
	private ClientLoginAccount loginAccountId;
	
	@Column(name="status")
	private String status;

	public LoginStatusTracker(ClientLoginAccount loginAccountId, String status) {
		super();
		this.loginAccountId = loginAccountId;
		this.status = status;
	}

	public long getLoginStatusTrackerId() {
		return loginStatusTrackerId;
	}

	public void setLoginStatusTrackerId(long loginStatusTrackerId) {
		this.loginStatusTrackerId = loginStatusTrackerId;
	}

	public ClientLoginAccount getLoginAccountId() {
		return loginAccountId;
	}

	public void setLoginAccountId(ClientLoginAccount loginAccountId) {
		this.loginAccountId = loginAccountId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

}
