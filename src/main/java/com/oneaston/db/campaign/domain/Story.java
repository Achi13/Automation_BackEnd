package com.oneaston.db.campaign.domain;

import javax.persistence.*;

import com.oneaston.db.universe.domain.Universe;
import com.oneaston.db.user.domain.User;

@Entity
@Table(name="story")
public class Story {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="story_id")
	private long storyId;
	
	@JoinColumn(name="theme_id")
	@ManyToOne
	private Theme themeId;
	
	@JoinColumn(name="user_id")
	@ManyToOne
	private User userId;
	
	@JoinColumn(name="universe_id")
	@ManyToOne
	private Universe universeId;
	
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
	
	public Story() {}

	public Story(Theme themeId, User userId, Universe universeId, String storyName, String description, String status,
			String timestamp, boolean isIgnoreSeverity, boolean isServerImport, int executionVersionStart,
			int executionVersionCurrent) {
		super();
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

	public Theme getThemeId() {
		return themeId;
	}

	public void setThemeId(Theme themeId) {
		this.themeId = themeId;
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
