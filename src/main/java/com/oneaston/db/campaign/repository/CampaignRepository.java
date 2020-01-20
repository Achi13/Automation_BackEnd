package com.oneaston.db.campaign.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneaston.db.campaign.domain.Campaign;
import com.oneaston.db.universe.domain.Universe;
import com.oneaston.db.user.domain.User;

public interface CampaignRepository extends JpaRepository<Campaign, Long>{
	
	Campaign findCampaignByCampaignId(long campaignId);
	Campaign findCampaignByCampaignName(String campaignName);
	List<Campaign> findCampaignsByUserId(User userId);
	
	
	List<Campaign> findCampaignsByUserIdAndUniverseId(User userId, Universe universeId);

}
