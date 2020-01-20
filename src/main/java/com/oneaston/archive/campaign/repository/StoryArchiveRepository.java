package com.oneaston.archive.campaign.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneaston.archive.campaign.domain.StoryArchive;

public interface StoryArchiveRepository extends JpaRepository<StoryArchive, Long>{
	
	List<StoryArchive> findStoryArchiveByThemeId(long themeId);
	StoryArchive findStoryArchiveByStoryId(long storyId);
	
}
