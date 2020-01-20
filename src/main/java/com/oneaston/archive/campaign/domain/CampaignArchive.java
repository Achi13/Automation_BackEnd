package com.oneaston.archive.campaign.domain;

import javax.persistence.*;


@Entity
@Table(name="campaign")
public class CampaignArchive {
	
	@Id
	@Column(name="campaign_id")
	private long campaignId;
	
	@Column(name="user_id")
	private long userId;
	
	@Column(name="universe_id")
	private long universeId;
	
	@Column(name="campaign_name")
	private String campaignName;
	
	@Column(name="description")
	private String description;
	
	@Column(name="status")
	private String status;
	
	@Column(name="timestamp")
	private String timestamp;
	
	@Column(name="execution_version_current")
	private int executionVersionCurrent;
	
	public CampaignArchive() {}

	public CampaignArchive(long campaignId, long userId, long universeId, String campaignName, String description,
			String status, String timestamp, int executionVersionCurrent) {
		super();
		this.campaignId = campaignId;
		this.userId = userId;
		this.universeId = universeId;
		this.campaignName = campaignName;
		this.description = description;
		this.status = status;
		this.timestamp = timestamp;
		this.executionVersionCurrent = executionVersionCurrent;
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

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
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

	public int getExecutionVersionCurrent() {
		return executionVersionCurrent;
	}

	public void setExecutionVersionCurrent(int executionVersionCurrent) {
		this.executionVersionCurrent = executionVersionCurrent;
	}
	
	
	
}
