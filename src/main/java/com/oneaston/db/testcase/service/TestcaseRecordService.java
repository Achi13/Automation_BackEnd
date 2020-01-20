package com.oneaston.db.testcase.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import java.nio.file.Paths;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.oneaston.configuration.bean.StoragePathBean;
import com.oneaston.db.campaign.domain.DependentTestcase;
import com.oneaston.db.campaign.repository.DependentTestcaseRepository;
import com.oneaston.db.testcase.domain.TestcaseActualData;
import com.oneaston.db.testcase.domain.TestcaseFooterData;
import com.oneaston.db.testcase.domain.TestcaseRecord;
import com.oneaston.db.testcase.repository.TestcaseActualDataRepository;
import com.oneaston.db.testcase.repository.TestcaseFooterDataRepository;
import com.oneaston.db.testcase.repository.TestcaseRecordRepository;
import com.oneaston.db.universe.repository.UniverseRepository;


@Path("/testcaserecord")
public class TestcaseRecordService {
	
	@Autowired
	DependentTestcaseRepository dependentTestcaseRepo;
	
	@Autowired
	TestcaseRecordRepository testcaseRecordRepo;
	
	@Autowired
	TestcaseActualDataRepository testcaseActualDataRepo;
	
	@Autowired
	TestcaseFooterDataRepository testcaseFooterDataRepo;
	
	@Autowired
	UniverseRepository universeRepo;
	
	//TESTCASE RECORD SERVICES-------------------------------------------
	
	//=====================================DOWNLOAD SELECTED TESTCASE RECORD==============================================
	@POST
	@Path("/downloadtestcaserecord")
	public Response downloadTestcaseRecord(@FormParam("file")List<String> json) {
		
		Logger log = Logger.getLogger(getClass());
		log.info("Access: Download Testcase");
		
		//INSTANTIATE EXCEL WRITER
		HSSFWorkbook excelFile = new HSSFWorkbook();
		
		//INSTANTIATE ROW COUNTER
		int rowNum = 0;
		
		//INSTANTIATE SHEET HOLDER
		HSSFSheet sheetHolder;
		
		//ITERATE JSON
		for(int i=0; i<json.size(); i++) {
			
			//GET TESTCASE RECORD BY RECORD ID
			TestcaseRecord testcaseRecord = testcaseRecordRepo.findTestcaseRecordByRecordId(Long.parseLong(json.get(i)));
			
			
			//FORMAT SHEET NAME
			String sheetName = String.format("%s-v.%d", testcaseRecord.getTestcaseNumber().getTestcaseNumber(), 
					testcaseRecord.getExecutionVersion());
			
			//SET SHEET HOLDER NAME
			sheetHolder = excelFile.createSheet(sheetName);
			
			//INSTANTIATE HEADER ROW
			HSSFRow headerRow = sheetHolder.createRow(rowNum);
			
			//CREATE HEADER ROW
			for(int j=0; j<11; j++) {
				
				switch(j) {
					case 0:
						headerRow.createCell(j).setCellValue("Web Element Name");
						break;
					case 1:
						headerRow.createCell(j).setCellValue("Web Element Nature");
						break;
					case 2:
						headerRow.createCell(j).setCellValue("Nature of Action");
						break;
					case 3:
						headerRow.createCell(j).setCellValue("is Screen Capture");
						break;
					case 4:
						headerRow.createCell(j).setCellValue("is Trigger Enter");
						break;
					case 5:
						headerRow.createCell(j).setCellValue("Input Output Value");
						break;
					case 6:
						headerRow.createCell(j).setCellValue("Label");
						break;
					case 7:
						headerRow.createCell(j).setCellValue("Timestamp");
						break;
					case 8:
						headerRow.createCell(j).setCellValue("Screenshot Path");
						break;
					case 9:
						headerRow.createCell(j).setCellValue("Remarks");
						break;
					case 10:
						headerRow.createCell(j).setCellValue("Logfield");
						//INCREMENT ROW NUMBER
						rowNum++;
						break;
						
				}
				
			}
			
			//GET TESTCASE ACTUAL DATA BY TESTCASE
			List<TestcaseActualData>testcaseActualData = testcaseActualDataRepo
					.findTestcaseActualDatasByTestcaseNumberAndExecutionVersion(testcaseRecord.getTestcaseNumber(), testcaseRecord.getExecutionVersion());
			
			//LOOP TESTCASE ACTUAL DATA
			for(int j=0; j<testcaseActualData.size(); j++) {
				
				//INSTANTIATE ROW 
				HSSFRow currentRow = sheetHolder.createRow(rowNum);
				
				//GET TESTCASEACTUAL DATA
				TestcaseActualData actualData = testcaseActualData.get(j);
				
				//CREATE CELL AND VALUES FOR CELL
				currentRow.createCell(0).setCellValue(actualData.getWebElementName());
				currentRow.createCell(1).setCellValue(actualData.getWebElementNature());
				currentRow.createCell(2).setCellValue(actualData.getNatureOfAction());
				currentRow.createCell(3).setCellValue(actualData.isScreenCapture());
				currentRow.createCell(4).setCellValue(actualData.isTriggerEnter());
				currentRow.createCell(5).setCellValue(actualData.getInputOutputValue());
				currentRow.createCell(6).setCellValue(actualData.getLabel());
				currentRow.createCell(7).setCellValue(actualData.getTimestamp());
				currentRow.createCell(8).setCellValue(actualData.getScreenshotPath());
				currentRow.createCell(9).setCellValue(actualData.getRemarks());
				currentRow.createCell(10).setCellValue(actualData.getLogField());
				
				//INCREMENT ROW NUMBER
				rowNum++;
				
				
			}
			
			//GET TESTCASE FOOTER DATA BY TESTCASE
			TestcaseFooterData testcaseFooterData = testcaseFooterDataRepo
					.findTestcaseFooterDataByTestcaseNumberAndExecutionVersion(testcaseRecord.getTestcaseNumber(), testcaseRecord.getExecutionVersion());
			
			log.info(testcaseFooterData);
			
			//CREATE FOOTER FOR SHEET
			for(int j=0; j<10; j++) {
				
				HSSFRow footerRow = sheetHolder.createRow(rowNum);
				
				switch(j) {
					case 0:
						footerRow.createCell(0).setCellValue("Client Name");
						footerRow.createCell(1).setCellValue(testcaseFooterData.getClientName());
						break;
					case 1:
						footerRow.createCell(0).setCellValue("Transaction type");
						footerRow.createCell(1).setCellValue(testcaseFooterData.getTransactionType());
						break;
					case 2:
						footerRow.createCell(0).setCellValue("Website");
						footerRow.createCell(1).setCellValue(testcaseFooterData.getUrl());
						break;
					case 3:
						footerRow.createCell(0).setCellValue("Server Import");
						footerRow.createCell(1).setCellValue(testcaseFooterData.getIsServerimport());
						break;
					case 4:
						footerRow.createCell(0).setCellValue("Ignore Severity");
						footerRow.createCell(1).setCellValue(testcaseFooterData.getIsIgnoreSeverity());
						break;
					case 5:
						footerRow.createCell(0).setCellValue("Assigned Account");
						footerRow.createCell(1).setCellValue(testcaseFooterData.getAssignedAccount());
						break;
					case 6:
						footerRow.createCell(0).setCellValue("Sender");
						footerRow.createCell(1).setCellValue(testcaseFooterData.getSender());
						break;
					case 7:
						footerRow.createCell(0).setCellValue("Testcase Number");
						footerRow.createCell(1).setCellValue(testcaseFooterData.getTestcaseNumber().getTestcaseNumber());
						break;
					case 8:
						footerRow.createCell(0).setCellValue("Tap Import Status");
						footerRow.createCell(1).setCellValue(testcaseFooterData.getTapImportStatus());
						break;
					case 9:
						footerRow.createCell(0).setCellValue("Testcase Status");
						footerRow.createCell(1).setCellValue(testcaseFooterData.getTestcaseStatus());
						break;
				}
				
				//INCREMENT ROW NUMBER
				rowNum++;
			}
			
			//RESET ROW NUMBER
			rowNum = 0;
			
		}
		
		//INSTANTIATE STREAMING OUTPUT
		StreamingOutput fileStream = new StreamingOutput() {

			@Override
			public void write(OutputStream output) throws IOException, WebApplicationException {
				//EXCEL FILE NAME
				String tempFilename = String.format("%s\\%s.xls", StoragePathBean.TEMPORARY_FOLDER, Long.toString(System.currentTimeMillis()));
				
				//WRITE EXCEL FILE
				File excelFinalFile = new File(tempFilename);
				try {
					
					//CREATE FILE OUTPUT STREAM
					FileOutputStream out = new FileOutputStream(excelFinalFile);
					
					//WRITE DATA
					excelFile.write(out);
					
					//CLOSE FILE OUTPUT STREAM
					out.close();
					
					java.nio.file.Path excelFilepath = Paths.get(tempFilename);
					
					byte[]fileByte = java.nio.file.Files.readAllBytes(excelFilepath);
					
					output.write(fileByte);
					output.flush();
					
					
				}catch(Exception e) {
					
				}finally {
					excelFile.close();
					FileUtils.deleteQuietly(excelFinalFile);
				}
				
				
			}
			
		};
		
		//RETURN FILE DOWNLOAD RESPONSE
		return Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM).header("content-disposition", 
				"attachment; filename = records.xls").build();
	}
	
	//=============================================GET TESTCASE RECORD====================================================
	@GET
	@Path("/gettestcasesbyuniverseid")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTestcaseByUniverseId(@QueryParam("universeId")long universeId) {
		
		//FIND DEPENDENT TESTCASE BY UNIVERSE ID
		List<DependentTestcase>dependentTestcaseList = dependentTestcaseRepo
				.findDependentTestcaseByUniverseId(universeRepo.findUniverseByUniverseId(universeId));
		
		//FIND TESTCASE RECORD BY DEPENDENT TESTCASE
		List<TestcaseRecord>testcaseRecordList = new ArrayList<TestcaseRecord>();
		for(DependentTestcase dTestcase : dependentTestcaseList) {
			
			List<TestcaseRecord>testcaseRecords = testcaseRecordRepo.findTestcaseRecordsByTestcaseNumber(dTestcase);
			
			//ITERATE TESTCASE RECORD LIST AND ADD IT TO FINAL LIST
			for(TestcaseRecord testcaseRecord : testcaseRecords) {
				
				//ADD TESTCASE RECORD TO LIST
				testcaseRecordList.add(testcaseRecord);
				
			}
			
		}
		
		//PUT LIST IN JSON DATA
		JSONObject jsonData = new JSONObject();
		jsonData.put("testcaseRecordList", testcaseRecordList);
		
		//RETURN JSON DATA
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonData.toString()).build();
	}
	

}
