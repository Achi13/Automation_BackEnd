package com.oneaston.db.campaign.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneaston.db.campaign.domain.Story;
import com.oneaston.db.campaign.domain.Theme;

public interface StoryRepository extends JpaRepository<Story, Long>{
	
	Story findStoryByStoryId(long storyId);
	Story findStoryByStoryName(String storyName);
	List<Story> findStoriesByThemeId(Theme themeId);

}
