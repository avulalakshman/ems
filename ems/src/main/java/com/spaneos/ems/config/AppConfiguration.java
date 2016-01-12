package com.spaneos.ems.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

/**
 * {@link AppConfiguration} class is used to configure the Hibernate
 * {@link SessionFactory} includes {@link DataSource} configuration for
 * connection pooling using {@link BasicDataSource}
 * 
 *
 */
@Configuration
@PropertySources({ @PropertySource("classpath:application.properties"),
		@PropertySource("classpath:datasource.properties"), @PropertySource("classpath:hibernate.properties") })
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.spaneos.ems.repository" })
@EnableCaching
public class AppConfiguration {

	private static final Logger LOGGER = Logger.getLogger(AppConfiguration.class);

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "datasource.driver-class-name";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "datasource.password";
	private static final String PROPERTY_NAME_DATABASE_URL = "datasource.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "datasource.username";

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	private static final String PROPERTY_NAME_HIBERNATE_CREATE_TABLES = "hibernate.hbm2ddl.auto";
	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
	private static final String EHCACHE_FILE = "ehcache.xml";

	@Resource
	private Environment environment;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(getDataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean
				.setPackagesToScan(environment.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
		entityManagerFactoryBean.setJpaProperties(getHibernateProperties());
		return entityManagerFactoryBean;
	}

	@Bean
	public DataSource getDataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(environment.getProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUsername(environment.getProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(environment.getProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		dataSource.setJdbcUrl(environment.getProperty(PROPERTY_NAME_DATABASE_URL));

		dataSource.setMaximumPoolSize(20);
		return dataSource;
	}

	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, environment.getProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, environment.getProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		properties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, environment.getProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
		properties.put(PROPERTY_NAME_HIBERNATE_CREATE_TABLES,
				environment.getProperty(PROPERTY_NAME_HIBERNATE_CREATE_TABLES));
		LOGGER.debug("Hibernate properties intialized " + properties);
		return properties;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Autowired
	@Bean
	public CacheManager getEhCacheManager() {
		return new EhCacheCacheManager(getEhCacheFactory().getObject());
	}

	@Autowired
	@Bean(name = "ehcache")
	public EhCacheManagerFactoryBean getEhCacheFactory() {
		EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
		factoryBean.setConfigLocation(new ClassPathResource(EHCACHE_FILE));
		factoryBean.setShared(true);
		return factoryBean;
	}

	
}