package com.oneaston.db.universe.domain;

import javax.persistence.*;

@Entity
@Table(name="script_variable")
public class ScriptVariable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="variable_id")
	private long variableId;
	
	@JoinColumn(name="script_id")
	@ManyToOne
	private Script scriptId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;

	public ScriptVariable() {}
	
	public ScriptVariable(Script scriptId, String name, String description) {
		super();
		this.scriptId = scriptId;
		this.name = name;
		this.description = description;
	}

	public long getVariableId() {
		return variableId;
	}

	public void setVariableId(long variableId) {
		this.variableId = variableId;
	}

	public Script getScriptId() {
		return scriptId;
	}

	public void setScriptId(Script scriptId) {
		this.scriptId = scriptId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
