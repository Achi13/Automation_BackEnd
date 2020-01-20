package com.oneaston.configuration;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.oneaston.archive.controller.ArchiveController;
import com.oneaston.db.campaign.service.CampaignService;
import com.oneaston.db.campaign.service.DependentTestcaseService;
import com.oneaston.db.campaign.service.StoryService;
import com.oneaston.db.campaign.service.ThemeService;
import com.oneaston.db.template.service.TemplateDataService;
import com.oneaston.db.template.service.TemplatesService;
import com.oneaston.db.testcase.service.TestcaseRecordService;
import com.oneaston.db.universe.service.ClientLoginAccountService;
import com.oneaston.db.universe.service.ClientService;
import com.oneaston.db.universe.service.ScriptService;
import com.oneaston.db.universe.service.UniverseService;
import com.oneaston.db.universe.service.WebAddressService;
import com.oneaston.db.user.service.UserService;

@Configuration
//@javax.ws.rs.ApplicationPath(ResourcePath.API)
public class ServerConfig extends ResourceConfig{
	
	public ServerConfig() {
		register(UserService.class);
		register(CampaignService.class);
		register(ThemeService.class);
		register(StoryService.class);
		register(DependentTestcaseService.class);
		register(TestcaseRecordService.class);
		register(TemplatesService.class);
		register(TemplateDataService.class);
		register(UniverseService.class);
		register(ClientService.class);
		register(WebAddressService.class);
		register(ClientLoginAccountService.class);
		register(ArchiveController.class);
		register(ScriptService.class);
		register(MultiPartFeature.class);
	}
}
