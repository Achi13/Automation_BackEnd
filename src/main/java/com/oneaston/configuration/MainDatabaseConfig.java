package com.oneaston.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "mainEntityManagerFactory",
		transactionManagerRef = "mainTransactionManager",
		basePackages = {"com.oneaston.db.campaign.repository",
				"com.oneaston.db.template.repository",
				"com.oneaston.db.testcase.repository",
				"com.oneaston.db.universe.repository",
				"com.oneaston.db.user.repository"})
public class MainDatabaseConfig {
	
	@Primary
	@Bean(name="mainDataSource")
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource mainDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	
	
	@Primary
	@Bean(name="mainEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("mainDataSource")DataSource dataSource) {
		
		Map<String, Object>propertyMap = new HashMap<String, Object>();
		
		propertyMap.put("hibernate.ddl-auto", "none");
		propertyMap.put("hibernate.use-new-id-generator-mappings", "false");
		propertyMap.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		/*
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.MYSQL);
		vendorAdapter.setGenerateDdl(false);
		
		builder.setDataSource(dataSource);
		builder.setPackagesToScan("com.oneaston.db.campaign.domain",
				"com.oneaston.db.template.domain",
				"com.oneaston.db.testcase.domain",
				"com.oneaston.db.universe.domain",
				"com.oneaston.db.user.domain");
		builder.setJpaVendorAdapter(vendorAdapter);
		builder.setPersistenceUnitName("main");
		
		return builder;
		*/
		return builder.dataSource(dataSource).packages("com.oneaston.db.campaign.domain",
			"com.oneaston.db.template.domain",
			"com.oneaston.db.testcase.domain",
			"com.oneaston.db.universe.domain",
			"com.oneaston.db.user.domain")
				.persistenceUnit("main")
				.properties(propertyMap)
				.build();
		
	}
	
	@Primary
	@Bean(name="mainTransactionManager")
	public PlatformTransactionManager mainTransactionManager(
			@Qualifier("mainEntityManagerFactory")EntityManagerFactory mainEntityManagerFactory) {
		
		return new JpaTransactionManager(mainEntityManagerFactory);
		
	}
}
