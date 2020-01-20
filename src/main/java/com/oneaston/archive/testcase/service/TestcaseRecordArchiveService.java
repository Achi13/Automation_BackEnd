package com.oneaston.archive.testcase.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneaston.archive.testcase.domain.TestcaseRecordArchive;
import com.oneaston.archive.testcase.repository.TestcaseRecordArchiveRepository;

@Service
public class TestcaseRecordArchiveService {

	@Autowired 
	TestcaseRecordArchiveRepository testCaseRecordRepository;
	
	public List<TestcaseRecordArchive> selectAllTestcaseRecordByTestcaseNumber(String[] testCaseNumber){
		
		List<TestcaseRecordArchive> result = new ArrayList<TestcaseRecordArchive>();
		
		for(String iterator: testCaseNumber) {
			
			for(TestcaseRecordArchive iterator2: testCaseRecordRepository.findTestcaseRecordArchiveByTestcaseNumber(iterator)) {
				result.add(iterator2);
			}
		}
		
		return result;
	}
	
	public void deleteAllTestcaseRecordByTestcaseNumber(String[] testCaseNumber) {
		
		for(String iterator: testCaseNumber) {
			testCaseRecordRepository.deleteTestcaseRecordArchiveByTestcaseNumber(iterator);
		}
	}
	
}
