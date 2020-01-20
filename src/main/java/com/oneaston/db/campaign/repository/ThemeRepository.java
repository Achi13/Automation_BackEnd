package com.oneaston.db.campaign.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneaston.db.campaign.domain.Campaign;
import com.oneaston.db.campaign.domain.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Long>{
	
	Theme findThemeByThemeId(long themeId);
	Theme findThemeByThemeName(String themeName);
	List<Theme> findThemesByCampaignId(Campaign campaignId);

}
