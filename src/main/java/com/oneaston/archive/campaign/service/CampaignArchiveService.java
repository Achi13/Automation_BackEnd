package com.oneaston.archive.campaign.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneaston.archive.campaign.domain.CampaignArchive;
import com.oneaston.archive.campaign.repository.CampaignArchiveRepository;

@Service
public class CampaignArchiveService {
	
	@Autowired
	private CampaignArchiveRepository campaignRepository;
	
	public List<CampaignArchive> selectAllCampaignById(Long[] campaignId){
		
		List<CampaignArchive> result = new ArrayList<CampaignArchive>();
		
		for(int i=0; i<campaignId.length; i++) {
			result.add(campaignRepository.findCampaignBeanByCampaignId(campaignId[i]));
		}
		
		return result;
	}
	
	public void deleteAllCampaignById(Long[] campaignId) {
		
		for(int i=0; i<campaignId.length; i++) {
			campaignRepository.delete(campaignId[i]);
		}
	}
	
	public List<CampaignArchive>selectAllCampaignArchive(){
		
		return campaignRepository.findAll();
		
	}

}
