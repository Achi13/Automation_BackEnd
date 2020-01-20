package com.oneaston.archive.campaign.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneaston.archive.campaign.domain.StoryArchive;
import com.oneaston.archive.campaign.repository.StoryArchiveRepository;

@Service
public class StoryArchiveService {

	@Autowired
	StoryArchiveRepository storyRepository;
	
	public List<StoryArchive> selectAllStoryByThemeId(Long[] id){
		
		List<StoryArchive> result = new ArrayList<StoryArchive>();
		
		for(long iterator: id) {
			for(StoryArchive iterator2: storyRepository.findStoryArchiveByThemeId(iterator)) {
				result.add(iterator2);
			}
		}
		
		return result;
	}
	
	public List<StoryArchive> selectAllStoryByStoryId(Long[] id){
		
		List<StoryArchive> result = new ArrayList<StoryArchive>();
		
		for(long iterator: id) {
			result.add(storyRepository.findStoryArchiveByStoryId(iterator));
		}
		
		return result;
		
	}
	
	public void deleteAllStoryByStoryId(Long[] storyId) {
		
		for(long iterator: storyId) {
			storyRepository.delete(iterator);
		}
	}
	
	public List<StoryArchive>selectAllStoryArchive(){
		
		return storyRepository.findAll();
	}
	
}
