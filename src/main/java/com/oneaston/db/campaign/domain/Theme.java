package com.oneaston.db.campaign.domain;

import javax.persistence.*;

import com.oneaston.db.universe.domain.Universe;
import com.oneaston.db.user.domain.User;

@Entity
@Table(name="theme")
public class Theme {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="theme_id")
	private long themeId;
	
	@JoinColumn(name="campaign_id")
	@ManyToOne
	private Campaign campaignId;
	
	@JoinColumn(name="user_id")
	@ManyToOne
	private User userId;
	
	@JoinColumn(name="universe_id")
	@ManyToOne
	private Universe universeId;
	
	@Column(name="theme_name")
	private String themeName;
	
	@Column(name="description")
	private String description;
	
	@Column(name="status")
	private String status;
	
	@Column(name="timestamp")
	private String timestamp;
	
	@Column(name="execution_version_start")
	private int executionVersionStart;
	
	@Column(name="execution_version_current")
	private int executionVersionCurrent;
	
	public Theme() {}

	public Theme(Campaign campaignId, User userId, Universe universeId, String themeName, String description,
			String status, String timestamp, int executionVersionStart, int executionVersionCurrent) {
		super();
		this.campaignId = campaignId;
		this.userId = userId;
		this.universeId = universeId;
		this.themeName = themeName;
		this.description = description;
		this.status = status;
		this.timestamp = timestamp;
		this.executionVersionStart = executionVersionStart;
		this.executionVersionCurrent = executionVersionCurrent;
	}

	public long getThemeId() {
		return themeId;
	}

	public void setThemeId(long themeId) {
		this.themeId = themeId;
	}

	public Campaign getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Campaign campaignId) {
		this.campaignId = campaignId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Universe getUniverseId() {
		return universeId;
	}

	public void setUniverseId(Universe universeId) {
		this.universeId = universeId;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getExecutionVersionStart() {
		return executionVersionStart;
	}

	public void setExecutionVersionStart(int executionVersionStart) {
		this.executionVersionStart = executionVersionStart;
	}

	public int getExecutionVersionCurrent() {
		return executionVersionCurrent;
	}

	public void setExecutionVersionCurrent(int executionVersionCurrent) {
		this.executionVersionCurrent = executionVersionCurrent;
	}

	

}
