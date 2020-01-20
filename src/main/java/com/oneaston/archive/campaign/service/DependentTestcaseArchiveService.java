package com.oneaston.archive.campaign.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneaston.archive.campaign.domain.DependentTestcaseArchive;
import com.oneaston.archive.campaign.repository.DependentTestcaseArchiveRepository;

@Service
public class DependentTestcaseArchiveService {
	
	@Autowired
	DependentTestcaseArchiveRepository dependentTestcaseRepository;
	
	public List<DependentTestcaseArchive> selectAllDependentTestcaseByStoryId(Long[] id){
		
		List<DependentTestcaseArchive> result = new ArrayList<DependentTestcaseArchive>();
		
		for(long iterator: id) {
			for(DependentTestcaseArchive iterator2: dependentTestcaseRepository.findDependentTestcaseArchiveByStoryId(iterator)){
				result.add(iterator2);
			}
		}
		
		return result;
	}
	
	public List<DependentTestcaseArchive> selectAllDependentTestcaseByTestcaseNumber(String[] testCaseNumber){
		
		List<DependentTestcaseArchive> result = new ArrayList<DependentTestcaseArchive>();
		
		for(String iterator: testCaseNumber) {
			result.add(dependentTestcaseRepository.findDependentTestcaseArchiveByTestcaseNumber(iterator));
		}
		
		return result;
		
	}
	
	public void deleteAllDependentTestcaseByTestcaseNumber(String[] testCaseNumber) {
		
		for(String iterator: testCaseNumber) {
			dependentTestcaseRepository.delete(iterator);
		}
	}
	
	public List<DependentTestcaseArchive>selectAllDependentTestcaseArchive(){
		
		return dependentTestcaseRepository.findAll();
	}

}
