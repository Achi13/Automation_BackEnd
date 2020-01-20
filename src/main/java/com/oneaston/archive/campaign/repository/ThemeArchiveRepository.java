package com.oneaston.archive.campaign.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneaston.archive.campaign.domain.ThemeArchive;

public interface ThemeArchiveRepository extends JpaRepository<ThemeArchive, Long>{
	
	List<ThemeArchive> findThemeArchiveByCampaignId(long campaignId);
	ThemeArchive findThemeArchiveByThemeId(long themeId);

}
