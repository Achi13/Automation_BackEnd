package com.oneaston.archive.campaign.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneaston.archive.campaign.domain.CampaignArchive;

public interface CampaignArchiveRepository extends JpaRepository<CampaignArchive, Long>{
	
	CampaignArchive findCampaignBeanByCampaignId(long id);
	
}
