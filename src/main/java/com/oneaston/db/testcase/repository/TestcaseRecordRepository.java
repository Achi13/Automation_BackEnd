package com.oneaston.db.testcase.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oneaston.db.campaign.domain.DependentTestcase;
import com.oneaston.db.testcase.domain.TestcaseRecord;

public interface TestcaseRecordRepository extends JpaRepository<TestcaseRecord, Long>{
	
	TestcaseRecord findTestcaseRecordByRecordId(long recordId);
	
	List<TestcaseRecord> findTestcaseRecordsByTestcaseNumber(DependentTestcase testcaseNumber);
	@Transactional
	@Query("select tr from TestcaseRecord tr where tr.testcaseNumber = :testcaseNumber and "
			+ "tr.executionVersion = :executionVersion")
	List<TestcaseRecord> findTestcaseRecordByTestcaseNumberAndExecutionVersion(@Param("testcaseNumber")DependentTestcase testcaseNumber,
			@Param("executionVersion")int executionVersion);

}
