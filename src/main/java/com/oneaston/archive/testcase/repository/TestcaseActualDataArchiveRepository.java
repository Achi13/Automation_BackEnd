package com.oneaston.archive.testcase.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oneaston.archive.testcase.domain.TestcaseActualDataArchive;

public interface TestcaseActualDataArchiveRepository extends JpaRepository<TestcaseActualDataArchive, Long>{
	
	List<TestcaseActualDataArchive> findTestcaseActualDataArchiveByTestcaseNumber(String testCaseNumber);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM TestcaseActualDataArchive t WHERE t.testcaseNumber = :testcaseNumber  ")
	void deleteTestcaseActualDataArchiveByTestcaseNumber(@Param("testcaseNumber")String testCaseNumber);
	


}
