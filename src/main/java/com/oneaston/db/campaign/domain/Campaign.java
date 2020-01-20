package com.oneaston.db.campaign.domain;


import javax.persistence.*;


import com.oneaston.db.universe.domain.Universe;
import com.oneaston.db.user.domain.User;



@Entity
@Table(name="campaign")
public class Campaign {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="campaign_id")
	private long campaignId;
	
	@JoinColumn(name="user_id")
	@ManyToOne
	private User userId;
	
	@JoinColumn(name="universe_id")
	@ManyToOne
	private Universe universeId;
	
	@Column(name="campaign_name")
	private String campaignName;
	
	@Column(name="description")
	private String description;
	
	@Column(name="status")
	private String status;
	
	@Column(name="timestamp")
	private String timestamp;
	
	@Column(name="execution_schedule")
	private String executionSchedule;
	
	@Column(name="execution_version_current")
	private int executionVersionCurrent;
	
	public Campaign() {}

	public Campaign(User userId, Universe universeId, String campaignName, String description, String status,
			String timestamp, String executionSchedule, int executionVersionCurrent) {
		super();
		this.userId = userId;
		this.universeId = universeId;
		this.campaignName = campaignName;
		this.description = description;
		this.status = status;
		this.timestamp = timestamp;
		this.executionSchedule = executionSchedule;
		this.executionVersionCurrent = executionVersionCurrent;
	}
	
	

	public long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(long campaignId) {
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

	public String getExecutionSchedule() {
		return executionSchedule;
	}

	public void setExecutionSchedule(String executionSchedule) {
		this.executionSchedule = executionSchedule;
	}

	public int getExecutionVersionCurrent() {
		return executionVersionCurrent;
	}

	public void setExecutionVersionCurrent(int executionVersionCurrent) {
		this.executionVersionCurrent = executionVersionCurrent;
	}
	
}
