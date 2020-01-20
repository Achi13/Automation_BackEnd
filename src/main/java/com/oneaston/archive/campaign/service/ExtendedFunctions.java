package com.oneaston.archive.campaign.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.oneaston.archive.campaign.domain.DependentTestcaseArchive;
import com.oneaston.archive.campaign.domain.StoryArchive;
import com.oneaston.archive.campaign.domain.ThemeArchive;



@Service
public class ExtendedFunctions {

public Long[] getThemeIdFromThemeData(List<ThemeArchive> themeBeanList) {
		
		Long[] themeIdArray = new Long[themeBeanList.size()];
		
		for(int i=0; i<themeBeanList.size();i++) {
			themeIdArray[i] = themeBeanList.get(i).getThemeId();
		}
		
		return themeIdArray;
	}
	
	public Long[] getStoryIdFromStoryData(List<StoryArchive> storyBeanList) {
		
		Long[] storyIdArray = new Long[storyBeanList.size()];
		
		for(int i=0; i<storyBeanList.size();i++) {
			storyIdArray[i] = storyBeanList.get(i).getStoryId();
		}
		
		return storyIdArray;
	}
	
	public String[] getTestcaseNumberFromDependentTestcaseData(List<DependentTestcaseArchive> dependentTestcaseBeanList) {
		
		String[] testCaseNumberArray = new String[dependentTestcaseBeanList.size()];
		
		for(int i=0; i<dependentTestcaseBeanList.size();i++) {
			testCaseNumberArray[i] = dependentTestcaseBeanList.get(i).getTestcaseNumber();
		}
		
		return testCaseNumberArray;
	}
	
	public String determineReverseArchiveSender(HttpSession session) {
		
		String reverseArchiveSender = (String) session.getAttribute("reverseArchiveSender");
		String mavViewName;
		
		if(reverseArchiveSender.equalsIgnoreCase("campaign")) {
			mavViewName = "redirect:/campaign/campaignpage"; //palitan mo to ron
		}else if(reverseArchiveSender.equalsIgnoreCase("theme")) {
			mavViewName = ""; //palitan mo to ron
		}else if(reverseArchiveSender.equalsIgnoreCase("story")) {
			mavViewName = ""; //palitan mo to ron
		}else { 
			mavViewName = ""; //palitan mo to ron
		}
		
		return mavViewName;
	}
	
}
