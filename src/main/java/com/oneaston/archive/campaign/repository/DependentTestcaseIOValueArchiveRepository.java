package com.oneaston.archive.campaign.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oneaston.archive.campaign.domain.DependentTestcaseIOValueArchive;

public interface DependentTestcaseIOValueArchiveRepository extends JpaRepository<DependentTestcaseIOValueArchive, Long>{
	
	List<DependentTestcaseIOValueArchive> findDependentTestcaseIOValueArchiveByTestcaseNumber(String testcaseNumber);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM DependentTestcaseIOValueArchive t WHERE t.testcaseNumber = :testcaseNumber  ")
	void deleteDependentTestcaseIOValueArchiveByTestcaseNumber(@Param("testcaseNumber")String testCaseNumber);
	
	
}
