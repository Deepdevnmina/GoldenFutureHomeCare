package com.homecare.GoldenFutureHomeCare;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringApplicationContext implements ApplicationContextAware{
 private  static ApplicationContext CONTEXT;

@Override
public void setApplicationContext(ApplicationContext context) throws BeansException {
	
	 CONTEXT= context;
}
 
 public static Object getBeans(String beanName) {
	 return CONTEXT.getBean(beanName);
 }
}
