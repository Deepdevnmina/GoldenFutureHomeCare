package com.homecare.GoldenFutureHomeCare;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
  

//This class is created to allowed crosOrigin from Global Level.
@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	public void addCorsMapping(CorsRegistry registry) {
		
		registry.addMapping("/**")
		.allowedMethods("*")
		.allowedOrigins("*");
		
	}

}
