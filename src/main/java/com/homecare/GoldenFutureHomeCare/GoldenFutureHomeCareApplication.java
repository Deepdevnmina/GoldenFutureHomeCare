package com.homecare.GoldenFutureHomeCare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.homecare.GoldenFutureHomeCare.security.AppProperties;
import com.homecare.GoldenFutureHomeCare.shared.AmazonSES;


 

/*Inorder to run our application on archive with different diploable apps we need to extend
 our Apllication as "public class GoldenFutureHomeCareApplication extends SpringBootServlet
 Initializer" and override one method as shown below.*/
@SpringBootApplication
public class GoldenFutureHomeCareApplication extends SpringBootServletInitializer {

	/*
	 * @Override protected SpringApplicationBuilder
	 * configure(SpringApplicationBuilder application){ return
	 * application.sources(MobileApplication.class);}
	 */
	public static void main(String[] args) {
		SpringApplication.run(GoldenFutureHomeCareApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}
	@Bean
	public AmazonSES getAmazonSES() {
		return new AmazonSES();
	}
	
	@Bean(name = "AppProperties")
	public AppProperties appProperties() {
		return new AppProperties();
	}
}
