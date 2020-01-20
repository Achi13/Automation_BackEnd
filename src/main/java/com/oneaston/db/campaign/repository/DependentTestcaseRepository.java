package com.oneaston.db.campaign.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oneaston.db.campaign.domain.DependentTestcase;
import com.oneaston.db.campaign.domain.Story;
import com.oneaston.db.universe.domain.Universe;
import com.oneaston.db.user.domain.User;

public interface DependentTestcaseRepository extends JpaRepository<DependentTestcase, Long>{
	
	DependentTestcase findDependentTestcaseByTestcaseNumber(String testcaseNumber);
	List<DependentTestcase> findDependentTestcasesByStoryId(Story storyId);
	List<DependentTestcase> findDependentTestcasesByUserId(User username);
	List<DependentTestcase> findDependentTestcaseByUniverseId(Universe universeId);
	
	@Transactional
	@Query("SELECT dt FROM DependentTestcase dt WHERE dt.storyId = :storyId ORDER BY dt.priority")
	List<DependentTestcase> findAllDependentTestcaseOrderByPriority(@Param("storyId")Story storyId);
	
}
