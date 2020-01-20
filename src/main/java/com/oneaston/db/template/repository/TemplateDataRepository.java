package com.oneaston.db.template.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.oneaston.db.template.domain.TemplateData;
import com.oneaston.db.template.domain.Templates;

public interface TemplateDataRepository extends JpaRepository<TemplateData, Long>{
	
	TemplateData findTemplateDataByTemplateDataId(long templateDataId);
	List<TemplateData> findTemplateDatasByTemplateId(Templates templateId);
	
	//UPDATE QUERY
	@Modifying(clearAutomatically = true)
	@Query("UPDATE TemplateData td SET td.label=?1, td.natureOfAction=?2, td.isScreenCapture=?3, "
			+ "td.isTriggerEnter=?4, td.webElementName=?5, td.webElementNature=?6 "
			+ "WHERE td.templateDataId=?7")
	void updateTemplateDataByTemplateDataId(String label, String natureOfAction, boolean isScreenCapture,
			boolean isTriggerEnter, String webElementName, String webElementNature, long templateDataId);
	
}
