package com.trustline.demo;

import java.util.Arrays;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.web.context.support.StandardServletEnvironment;

@SpringBootApplication
public class DemoApplication {
	   private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);
	    
	    static String[] profiles ;

		public static void main(String[] args) {
	        SpringApplication springApplication = new SpringApplication(DemoApplication.class);
	        ApplicationContext ctx = springApplication.run(args);
	        
	        log.debug("Let's inspect the beans provided by Spring Boot:"); 
	        String[] beanNames = ctx.getBeanDefinitionNames();
	        Arrays.sort(beanNames);
	        for (String beanName : beanNames)
	        {
	            log.debug(beanName);
	        }

	        log.debug("Let's inspect the profiles provided by Spring Boot:"); 
	        profiles = ctx.getEnvironment().getActiveProfiles();
	        for (int i = 0; i < profiles.length; i++)
	            log.debug("profile****=" + profiles[i]); 

	        log.info("Let's inspect the properties provided by Spring Boot:"); 
	        MutablePropertySources propertySources = ((StandardServletEnvironment) ctx.getEnvironment())
	                .getPropertySources();
	        Iterator<org.springframework.core.env.PropertySource<?>> iterator = propertySources.iterator();
	        while (iterator.hasNext())
	        {
	            Object propertySourceObject = iterator.next();
	            if ( propertySourceObject instanceof org.springframework.core.env.PropertySource )
	            {
	                org.springframework.core.env.PropertySource<?> propertySource = (org.springframework.core.env.PropertySource<?>) propertySourceObject;
	                log.info("propertySource=" + propertySource.getName() + " values=" + propertySource.getSource() 
	                        + "class=" + propertySource.getClass()); 
	            }
	        }
	    }
}