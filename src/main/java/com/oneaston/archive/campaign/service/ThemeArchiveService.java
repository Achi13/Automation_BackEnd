package com.oneaston.archive.campaign.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneaston.archive.campaign.domain.ThemeArchive;
import com.oneaston.archive.campaign.repository.ThemeArchiveRepository;

@Service
public class ThemeArchiveService {

	@Autowired 
	ThemeArchiveRepository themeRepository;
	
	public List<ThemeArchive> selectAllThemeByCampaignId(Long[] id){
		
		List<ThemeArchive> result = new ArrayList<ThemeArchive>();
		
		for(long iterator: id) {
			for(ThemeArchive iterator2: themeRepository.findThemeArchiveByCampaignId(iterator)) {
				result.add(iterator2);
			}
		}
		
		return result;
	}
	
	public List<ThemeArchive> selectAllThemeByThemeId(Long[] id){
		
		List<ThemeArchive> result = new ArrayList<ThemeArchive>();
		
		for(long iterator: id) {
			result.add(themeRepository.findThemeArchiveByThemeId(iterator));
		}
		
		return result;
		
	}
	
	public void deleteAllThemeByThemeId(Long[] themeId) {
		
		for(long iterator: themeId) {
			themeRepository.delete(iterator);
		}
	}
	
	public List<ThemeArchive>selectAllThemeArchive(){
		
		return themeRepository.findAll();
		
	}
	
}
