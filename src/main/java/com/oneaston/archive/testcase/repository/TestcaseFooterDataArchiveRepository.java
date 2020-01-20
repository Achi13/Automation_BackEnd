package com.oneaston.archive.testcase.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oneaston.archive.testcase.domain.TestcaseFooterDataArchive;

public interface TestcaseFooterDataArchiveRepository extends JpaRepository<TestcaseFooterDataArchive, Long>{
	
	List<TestcaseFooterDataArchive> findTestcaseFooterDataArchiveByTestcaseNumber(String testCaseNumber);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM TestcaseFooterDataArchive t WHERE t.testcaseNumber = :testcaseNumber  ")
	void deleteTestcaseFooterDataArchiveByTestcaseNumber(@Param("testcaseNumber")String testCaseNumber);
	
	
}
