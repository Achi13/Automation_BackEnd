package com.oneaston.db.testcase.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oneaston.db.campaign.domain.DependentTestcase;
import com.oneaston.db.testcase.domain.TestcaseFooterData;

public interface TestcaseFooterDataRepository extends JpaRepository<TestcaseFooterData, Long>{
	
	TestcaseFooterData findTestcaseFooterDataByFooterDataId(long footerDataId);
	
	List<TestcaseFooterData> findTestcaseFooterDatasByTestcaseNumber(DependentTestcase testcaseNumber);
	
	@Transactional
	@Query("select tfd from TestcaseFooterData tfd where tfd.testcaseNumber = :testcaseNumber and "
			+ "tfd.executionVersion = :executionVersion")
	TestcaseFooterData findTestcaseFooterDataByTestcaseNumberAndExecutionVersion(@Param("testcaseNumber")DependentTestcase testcaseNumber,
			@Param("executionVersion")int executionVersion);
	
}
