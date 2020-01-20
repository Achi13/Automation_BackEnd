package com.oneaston.archive.campaign.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneaston.archive.campaign.domain.DependentTestcaseIOValueArchive;
import com.oneaston.archive.campaign.repository.DependentTestcaseIOValueArchiveRepository;

@Service
public class DependentTestcaseIOValueArchiveService {
	
	@Autowired
	DependentTestcaseIOValueArchiveRepository ioValRepository;
	
	public List<DependentTestcaseIOValueArchive> selectAllIoValDataByTestcaseNumber(String[] testCaseNumber){
		
		List<DependentTestcaseIOValueArchive> result = new ArrayList<DependentTestcaseIOValueArchive>();
		
		for(String iterator: testCaseNumber) {
			for(DependentTestcaseIOValueArchive iterator2: ioValRepository.findDependentTestcaseIOValueArchiveByTestcaseNumber(iterator)) {
				result.add(iterator2);
			}
		}
		
		return result;
	}
	
	public void deleteAllIoValDataByTestcaseNumber(String[] testCaseNumber) {
		
		for(String iterator: testCaseNumber) {
			ioValRepository.deleteDependentTestcaseIOValueArchiveByTestcaseNumber(iterator);
		}
	}
	
}
