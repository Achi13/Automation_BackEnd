package com.oneaston.archive.campaign.domain;

import javax.persistence.*;

@Entity
@Table(name="story")
public class StoryArchive {

	@Id
	@Column(name="story_id")
	private long storyId;
	
	@Column(name="theme_id")
	private long themeId;
	
	@Column(name="user_id")
	private long userId;
	
	@Column(name="universe_id")
	private long universeId;
	
	@Column(name="story_name")
	private String storyName;
	
	@Column(name="description")
	private String description;
	
	@Column(name="status")
	private String status;
	
	@Column(name="timestamp")
	private String timestamp;
	
	@Column(name="is_ignore_severity")
	private boolean isIgnoreSeverity;
	
	@Column(name="is_server_import")
	private boolean isServerImport;
	
	@Column(name="execution_version_start")
	private int executionVersionStart;
	
	@Column(name="execution_version_current")
	private int executionVersionCurrent;
	
	public StoryArchive() {}

	public StoryArchive(long storyId, long themeId, long userId, long universeId, String storyName,
			String description, String status, String timestamp, boolean isIgnoreSeverity, boolean isServerImport,
			int executionVersionStart, int executionVersionCurrent) {
		super();
		this.storyId = storyId;
		this.themeId = themeId;
		this.userId = userId;
		this.universeId = universeId;
		this.storyName = storyName;
		this.description = description;
		this.status = status;
		this.timestamp = timestamp;
		this.isIgnoreSeverity = isIgnoreSeverity;
		this.isServerImport = isServerImport;
		this.executionVersionStart = executionVersionStart;
		this.executionVersionCurrent = executionVersionCurrent;
	}

	public long getStoryId() {
		return storyId;
	}

	public void setStoryId(long storyId) {
		this.storyId = storyId;
	}

	public long getThemeId() {
		return themeId;
	}

	public void setThemeId(long themeId) {
		this.themeId = themeId;
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

	public String getStoryName() {
		return storyName;
	}

	public void setStoryName(String storyName) {
		this.storyName = storyName;
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

	public boolean getIsIgnoreSeverity() {
		return isIgnoreSeverity;
	}

	public void setIsIgnoreSeverity(boolean isIgnoreSeverity) {
		this.isIgnoreSeverity = isIgnoreSeverity;
	}

	public boolean getIsServerImport() {
		return isServerImport;
	}

	public void setIsServerImport(boolean isServerImport) {
		this.isServerImport = isServerImport;
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
