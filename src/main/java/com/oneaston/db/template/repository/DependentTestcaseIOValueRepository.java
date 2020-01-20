package com.oneaston.db.template.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneaston.db.campaign.domain.DependentTestcase;
import com.oneaston.db.template.domain.DependentTestcaseIOValue;
import com.oneaston.db.template.domain.TemplateData;

public interface DependentTestcaseIOValueRepository extends JpaRepository<DependentTestcaseIOValue, Long>{
	
	List<DependentTestcaseIOValue> findIOValuesByTestcaseNumber(DependentTestcase testcaseNumber);
	DependentTestcaseIOValue findIoValueByTemplateDataId(TemplateData templateDataId);
	
}
