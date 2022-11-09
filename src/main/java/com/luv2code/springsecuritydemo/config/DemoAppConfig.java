package com.luv2code.springsecuritydemo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.logging.Logger;

@Configuration
@PropertySource("classpath:persistence-postgresql.properties")
public class DemoAppConfig implements WebMvcConfigurer {
    @Autowired
    private Environment env;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/leaders").setViewName("leaders");
        registry.addViewController("/system").setViewName("system");
        registry.addViewController("/access-denied").setViewName("access-denied");
    }

    @Bean
    public DataSource securityDataSource() {
        SimpleDriverDataSource dataSource =  DataSourceBuilder.create().type(SimpleDriverDataSource.class)
                .driverClassName(env.getProperty("jdbc.driver"))
                .url(env.getProperty("jdbc.url"))
                .username(env.getProperty("jdbc.username"))
                .password(env.getProperty("jdbc.password"))
                .build();
        logger.info(String.format("Data Source built: Driver=%s, url=%s, username=%s, password=%s",
                dataSource.getDriver().getClass().getName(),
                dataSource.getUrl(),
                dataSource.getUsername(),
                dataSource.getPassword()));
        return dataSource;
    }
}
