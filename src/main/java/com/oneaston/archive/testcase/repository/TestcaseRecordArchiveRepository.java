package com.oneaston.archive.testcase.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oneaston.archive.testcase.domain.TestcaseRecordArchive;

public interface TestcaseRecordArchiveRepository extends JpaRepository<TestcaseRecordArchive, Long>{
	
	List<TestcaseRecordArchive> findTestcaseRecordArchiveByTestcaseNumber(String testCaseNumber);
	
	@Transactional
	@Modifying
	@Query("delete from TestcaseRecordArchive t where t.testcaseNumber =:testcaseNumber")
	void deleteTestcaseRecordArchiveByTestcaseNumber(@Param("testcaseNumber")String testcaseNumber);
	
}
