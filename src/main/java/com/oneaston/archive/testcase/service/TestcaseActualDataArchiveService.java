package com.oneaston.archive.testcase.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneaston.archive.testcase.domain.TestcaseActualDataArchive;
import com.oneaston.archive.testcase.repository.TestcaseActualDataArchiveRepository;

@Service
public class TestcaseActualDataArchiveService {

	@Autowired
	TestcaseActualDataArchiveRepository actualDataRepository;

	public List<TestcaseActualDataArchive> selectAllActualDataByTestcaseNumber(String[] testCaseNumber){
		
		List<TestcaseActualDataArchive> result = new ArrayList<TestcaseActualDataArchive>();
		
		for(String iterator: testCaseNumber) {
			for(TestcaseActualDataArchive iterator2: actualDataRepository.findTestcaseActualDataArchiveByTestcaseNumber(iterator)) {
				result.add(iterator2);
			}
		}
		
		return result;
	}
	
	public void deleteAllActualDataByTestcaseNumber(String[] testCaseNumber) {
		
		for(String iterator: testCaseNumber) {
			actualDataRepository.deleteTestcaseActualDataArchiveByTestcaseNumber(iterator);
		}
	}
	
}
