package com.bino.tracker.config;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.bino.tracker.*")
//@EnableTransactionManagement
@Import(value = { WebSocketConfig.class })
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public WebContentInterceptor webContentInterceptor() {
		WebContentInterceptor interceptor = new WebContentInterceptor();
		interceptor.setCacheSeconds(0);
		interceptor.setUseExpiresHeader(true);
		interceptor.setUseCacheControlHeader(true);
		interceptor.setUseCacheControlNoStore(true);
		return interceptor;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		registry.addResourceHandler("/img/**").addResourceLocations("/img/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(webContentInterceptor());
	}
	
//	@Bean(name = "dataSource")
//	public DriverManagerDataSource dataSource() {
//		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
//		driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
//		driverManagerDataSource.setUrl("jdbc:mysql://220.135.202.42:3306/tracker?autoReconnect=true&useSSL=false");
//		driverManagerDataSource.setUsername("root");
//		driverManagerDataSource.setPassword("binodata");
//		return driverManagerDataSource;
//	}
	
//	@Bean
//	public SessionFactory sessionFactory() {
//		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
//		builder.scanPackages("com.bino.tracker.model").addProperties(getHibernateProperties());
//		return builder.buildSessionFactory();
//	}

//	private Properties getHibernateProperties() {
//		Properties prop = new Properties();
//		prop.put("hibernate.format_sql", "true");
//		prop.put("hibernate.show_sql", "false");
//		prop.put("hibernate.use_sql_comments", "true");
//		prop.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//		return prop;
//	}

//	@Bean
//	public HibernateTransactionManager txManager() {
//		return new HibernateTransactionManager(sessionFactory());
//	}
}
