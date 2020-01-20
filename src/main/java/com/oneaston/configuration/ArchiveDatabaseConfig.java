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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "archiveEntityManagerFactory",
		transactionManagerRef = "archiveTransactionManager",
		basePackages= {"com.oneaston.archive.campaign.repository",
				"com.oneaston.archive.testcase.repository"})
public class ArchiveDatabaseConfig {
	
	
	@Bean(name="archiveDataSource")
	@ConfigurationProperties(prefix="spring.archive-datasource")
	public DataSource archiveDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name="archiveEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean archiveEntityManagerFactory(
			EntityManagerFactoryBuilder builder,
			@Qualifier("archiveDataSource")DataSource archiveDataSource) {
		
		Map<String, Object>propertyMap = new HashMap<String, Object>();
		
		propertyMap.put("hibernate.ddl-auto", "none");
		propertyMap.put("hibernate.use-new-id-generator-mappings", "false");
		propertyMap.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		
		/*
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.MYSQL);
		vendorAdapter.setGenerateDdl(false);
		
		builder.setDataSource(archiveDataSource);
		builder.setPackagesToScan("com.oneaston.archive.campaign.domain",
						"com.oneaston.archive.testcase.domain");
		builder.setJpaVendorAdapter(vendorAdapter);
		builder.setPersistenceUnitName("archive");
		
		return builder;
		*/
		
		return builder.dataSource(archiveDataSource)
				.packages("com.oneaston.archive.campaign.domain",
						"com.oneaston.archive.testcase.domain")
				.persistenceUnit("archive")
				.properties(propertyMap)
				.build();
		
	}
	
	@Bean(name="archiveTransactionManager")
	public PlatformTransactionManager archiveTransactionManager(
			@Qualifier("archiveEntityManagerFactory")EntityManagerFactory archiveEntityManagerFactory) {
		
		return new JpaTransactionManager(archiveEntityManagerFactory);
		
	}

}
