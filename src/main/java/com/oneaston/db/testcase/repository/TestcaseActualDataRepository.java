package com.oneaston.db.testcase.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oneaston.db.campaign.domain.DependentTestcase;
import com.oneaston.db.testcase.domain.TestcaseActualData;

public interface TestcaseActualDataRepository extends JpaRepository<TestcaseActualData, Long>{
	
	TestcaseActualData findTestcaseActualDataByActualDataId(long actualDataId);
	
	List<TestcaseActualData> findTestcaseActualDatasByTestcaseNumber(DependentTestcase testcaseNumber);
	
	@Transactional
	@Query("select tad from TestcaseActualData tad where tad.testcaseNumber = :testcaseNumber and "
			+ "tad.executionVersion = :executionVersion")
	List<TestcaseActualData> findTestcaseActualDatasByTestcaseNumberAndExecutionVersion(@Param("testcaseNumber")DependentTestcase testcaseNumber,
			@Param("executionVersion")int executionVersion);

}
