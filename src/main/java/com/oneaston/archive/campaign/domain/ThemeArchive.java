package com.oneaston.archive.campaign.domain;

import javax.persistence.*;

@Entity
@Table(name="theme")
public class ThemeArchive {
	
	@Id
	@Column(name="theme_id")
	private long themeId;
	
	@Column(name="campaign_id")
	private long campaignId;
	
	@Column(name="user_id")
	private long userId;
	
	@Column(name="universe_id")
	private long universeId;
	
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
	
	public ThemeArchive() {}

	public ThemeArchive(long themeId, long campaignId, long userId, long universeId, String themeName,
			String description, String status, String timestamp, int executionVersionStart,
			int executionVersionCurrent) {
		super();
		this.themeId = themeId;
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

	public long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(long campaignId) {
		this.campaignId = campaignId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUniverseId() {
		return universeId;
	}

	public void setUniverseId(long universeId) {
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
