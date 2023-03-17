package com.homecare.GoldenFutureHomeCare.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
/* this class is created to read any value that we want to read from properties file.Ater creating 
 class we need to write a method in security constants. Go to security constant from here....*/
@Component
public class AppProperties {
	
	@Autowired
	private Environment env;
	
	public String getTokenSecret() {
		return env.getProperty("tokenSecret");
	}

}
