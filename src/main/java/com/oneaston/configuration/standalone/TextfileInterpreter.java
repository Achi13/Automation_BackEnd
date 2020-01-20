package com.oneaston.configuration.standalone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TextfileInterpreter {

	public List<String> interpreterController(String filePath) throws IOException {

		BufferedReader br = readFile(filePath);
		List<String> noHeaderFile = removeHeaderAndEnter(br);
		List<String> noLineInitiatorFile = removeLineInitiator(noHeaderFile);
		List<String> finalData = extractData(noLineInitiatorFile);
		
		br.close();
		deleteFile(filePath);
		
		return finalData;
	}
	
	public BufferedReader readFile(String filePath) {
		
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(filePath));
		}catch(Exception e){/*nothing to do here*/}
		
		return br;
		
	}
	
	public List<String> removeHeaderAndEnter(BufferedReader br) {
		
		String line;
		boolean startAdding = false;
		List<String> noHeaderFile = new ArrayList<String>();
		
		
		try {
		    while ((line = br.readLine()) != null) {
		    	
		    	if(startAdding == true && !line.toLowerCase().contains("key_enter") && !line.trim().isEmpty()) {
		    		noHeaderFile.add(line);
		    	}
		    	else if(line.toLowerCase().contains("selenium.open")) {
		    	   startAdding = true;
		    	}
		    }
		}catch(Exception e) {/*nothing to do here*/}
		
		return noHeaderFile;
		
	}
	
	public List<String> removeLineInitiator(List<String> noHeaderFile){
		
		List<String> noLineInitiatorFile = new ArrayList<String>();
		
		for(String iterator: noHeaderFile) {
			noLineInitiatorFile.add(iterator.replace("selenium.",""));
		}
		
		return noLineInitiatorFile;
	}
	
	public List<String> extractData(List<String> noLineInitiatorFile){
		
		List<String> finalData = new ArrayList<String>();
		
		for(String iterator: noLineInitiatorFile) {
			String tempA = determineNature(iterator) + "," + determineAction(iterator) + "," + determineName(iterator);
			if(!tempA.contains("invaliddataentry404")) {
				finalData.add(tempA);
				System.out.println(tempA);
			}
		}
		
		return finalData;
	}
	
	public String determineAction(String line) {
		
		int endIndex = 0;
		String data = null;
		
		for(int i=0; i<line.length(); i++) {
			
			if(line.charAt(i) == '(') {
				endIndex = i;
				break;
			}
		}
		
		if(line.substring(0, endIndex).equalsIgnoreCase("type")) {
			data = "input";
		}
		else if(line.substring(0, endIndex).equalsIgnoreCase("doubleclick")){
			data = "click";
		}
		else {
			data = line.substring(0, endIndex);
		}
		
		return data;
		
	}
	
	public String determineNature(String line) {
		
		int endIndex = 0;
		int startIndex = 0;
		String data = null;
		
		for(int i=0; i<line.length(); i++) {
			
			if(line.charAt(i) == '"') {
				startIndex = i+1;
			}
			else if(line.charAt(i) == '=') {
				endIndex = i;
				break;
			}
		}
		
		try {
			if(line.substring(startIndex, endIndex).equalsIgnoreCase("id") || line.substring(startIndex, endIndex).equalsIgnoreCase("name") || line.substring(startIndex, endIndex).equalsIgnoreCase("class")) {
				data = line.substring(startIndex, endIndex);
			}else {
				data = "xpath";
			}
		}catch(Exception e) {
			return "invaliddataentry404";
		}
		
		
		return data;
	}
	
	public String determineName(String line) {
		
		int endIndex = 0;
		int startIndex = 0;
		boolean getStartIndex = true;
		char endChar = 'a';
		int endIndicator = 0;
		String data = null;
		
		for(int i=0; i<line.length(); i++) {
			
			if(getStartIndex == true) {
				
				if(line.charAt(i) == '=') {
					startIndex = i+1;
					getStartIndex = false;
					endIndicator = 1;
					endChar = '"';
				}
				else if(line.charAt(i) == '/'){
					startIndex = i;
					getStartIndex = false;
					endIndicator = 2;
					endChar = ']';
				}
				
			}else {
					
				if(line.charAt(i) == endChar) {
					if(endIndicator == 1) {
						endIndex = i;
					}else {
						endIndex = i+1;
					}
					break;
				}
					
			}
			
		}
		
		try {
			data = line.substring(startIndex, endIndex);
		}catch(Exception e) {
			return "invaliddataentry404";
		}
		
		return data;
		
	}
	
	public void deleteFile(String filePath) {
		
		Path fileToDelete = Paths.get(filePath);
		
		try {
			Files.delete(fileToDelete);
		} catch (IOException e) {/*nothing to do here*/}
		
	}
	
}
