package com.oneaston.archive.testcase.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneaston.archive.testcase.domain.TestcaseFooterDataArchive;
import com.oneaston.archive.testcase.repository.TestcaseFooterDataArchiveRepository;

@Service
public class TestcaseFooterDataArchiveService {
	
	@Autowired 
	TestcaseFooterDataArchiveRepository footerDataRepository;
	
	public List<TestcaseFooterDataArchive> selectAllFooterDataByTestcaseNumber(String[] testCaseNumber){
		
		List<TestcaseFooterDataArchive> result = new ArrayList<TestcaseFooterDataArchive>();
		
		for(String iterator: testCaseNumber) {
			for(TestcaseFooterDataArchive iterator2: footerDataRepository.findTestcaseFooterDataArchiveByTestcaseNumber(iterator)) {
				result.add(iterator2);
			}
		}
		
		return result;
	}
	
	public void deleteAlltestCaseFooterDataByTestCaseNumber(String[] testCaseNumber) {
		
		for(String iterator: testCaseNumber) {
			footerDataRepository.deleteTestcaseFooterDataArchiveByTestcaseNumber(iterator);
		}
	}
	
}
