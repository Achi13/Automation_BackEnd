package com.oneaston.db.template.domain;

import javax.persistence.*;

import com.oneaston.db.universe.domain.Client;
import com.oneaston.db.user.domain.User;



@Entity
@Table(name="templates")
public class Templates {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="template_id")
	private long templateId;
	
	@JoinColumn(name="client_id")
	@ManyToOne
	private Client clientId;
	
	@JoinColumn(name="user_id")
	@ManyToOne
	private User userId;
	
	@Column(name="template_name")
	private String templateName;
	
	@Column(name="is_public")
	boolean isPublic;
	
	@Column(name="template_version")
	int templateVersion = 1;
	
	public Templates() {}
	
	public Templates(Client clientId, User userId, String templateName, boolean isPublic) {
		super();
		this.clientId = clientId;
		this.userId = userId;
		this.templateName = templateName;
		this.isPublic = isPublic;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public Client getClientId() {
		return clientId;
	}

	public void setClientId(Client clientId) {
		this.clientId = clientId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public int getTemplateVersion() {
		return templateVersion;
	}

	public void setTemplateVersion(int templateVersion) {
		this.templateVersion = templateVersion;
	}
	
	

}
