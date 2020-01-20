package com.oneaston;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.oneaston.configuration.bean.StoragePathBean;
import com.oneaston.configuration.threads.ScheduleThread;


@SpringBootApplication
public class AutomationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomationServerApplication.class, args);
		
		ScheduleThread schedule = new ScheduleThread("Jaison", StoragePathBean.BRIDGE_FOLDER, 
				StoragePathBean.TODO_FOLDER);
		schedule.start();
	}

}
