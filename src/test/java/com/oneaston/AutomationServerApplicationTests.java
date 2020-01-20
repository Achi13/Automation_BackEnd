package com.oneaston;





import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oneaston.db.template.domain.TemplateData;
import com.oneaston.db.template.domain.Templates;
import com.oneaston.db.template.repository.TemplateDataRepository;
import com.oneaston.db.template.repository.TemplatesRepository;
import com.oneaston.db.universe.repository.ClientRepository;
import com.oneaston.db.user.domain.User;
import com.oneaston.db.user.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AutomationServerApplicationTests {

	@Autowired
	TemplatesRepository templatesRepo;
	
	@Autowired
	TemplateDataRepository templateDataRepo;
	
	@Autowired
	ClientRepository clientRepo;
	
	@Autowired
	UserRepository userRepo;
	
	//private final String TEMPLATE_FOLDER = "C:\\Users\\rtsbondoc\\Desktop\\Template";
	
	
	@Test
	public void loadTemplateToDb() throws IOException {
		/*
		Logger log = Logger.getLogger(getClass());
		
		
		File templateFolder = new File(TEMPLATE_FOLDER);
		List<Templates>templatesList = templatesRepo.findTemplatesByClientIdAndUserId(clientRepo.findClientByClientId(1), userRepo.findUserByUserId(1));
		
		System.out.print(templatesList.get(0));
		
		for(Templates templates : templatesList) {
			
			for(File csvTemplate : templateFolder.listFiles()) {
				if(csvTemplate.getName().contains(templates.getTemplateName())) {
					String holder = "";
					int counter = 0;
					FileReader reader = new FileReader(csvTemplate);
					BufferedReader buffer = new BufferedReader(reader);
					while((holder = buffer.readLine()) != null) {
						if(counter == 0) {
							counter++;
						}else {
							String[]holderArr = holder.split(",");
							TemplateData templateData = new TemplateData(templates, holderArr[6], holderArr[2], 
									Boolean.valueOf(holderArr[5].toLowerCase()), Boolean.valueOf(holderArr[3].toLowerCase()),
									holderArr[0], holderArr[1]);
							templateDataRepo.save(templateData);
						}
					}
					buffer.close();
					reader.close();
					counter = 0;
					break;
				}
			}
			
		}
		User user = userRepo.findUserByUserId(1);
		log.info(user.getUsername());
		assertTrue(user == null);*/
		
	}
	
	@Test
	public void putTestcases() {
		
		Logger log = Logger.getLogger(getClass());
		/*
		//Using Date class
		Date date = new Date();
		String dateFileName;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmssSSSS");
		dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
		String tempA = dateFormat.format(date).toString();
		dateFileName = tempA;
		log.info(dateFileName);
		
		
		File templateFolder = new File(TEMPLATE_FOLDER);
		
		for(File csvTemplate : templateFolder.listFiles()) {
			
			String fileName = csvTemplate.getName().replaceFirst("[.][^.]+$", "");
			
			Templates templates = new Templates(clientRepo.findClientByClientId(1), userRepo.findUserByUserId(1), 
					fileName, true);
			templatesRepo.save(templates);
		}*/
		
	}

}
