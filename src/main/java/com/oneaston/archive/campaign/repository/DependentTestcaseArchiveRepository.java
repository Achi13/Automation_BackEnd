package com.oneaston.archive.campaign.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneaston.archive.campaign.domain.DependentTestcaseArchive;

public interface DependentTestcaseArchiveRepository extends JpaRepository<DependentTestcaseArchive, String>{
	
	List<DependentTestcaseArchive> findDependentTestcaseArchiveByStoryId(long storyId);
	DependentTestcaseArchive findDependentTestcaseArchiveByTestcaseNumber(String testCaseNumber);
	
}
