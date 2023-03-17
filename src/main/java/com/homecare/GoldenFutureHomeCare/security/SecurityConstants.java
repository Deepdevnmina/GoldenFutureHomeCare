package com.homecare.GoldenFutureHomeCare.security;

import com.homecare.GoldenFutureHomeCare.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME=86400000;//10days
    public static final long PASSWORD_RESET_EXPIRATION_TIME = 3600000; // 1 hour
    public static final String TOKEN_PREFIX="Bearer ";
    public static final String HEADER_STRING="Authorization";
    public static final String SIGN_UP_URL="/consumers";
   // public static final String TOKEN_SECRET="jf9i4jgu83nfl0kht9zq"; 
    // this line is no required after creating AppProperties class.
    public static final String VERIFICATION_EMAIL_URL = "/consumers/email-verification";
    public static final String PASSWORD_RESET_REQUEST_URL = "/consumers/password-reset-request";
    public static final String PASSWORD_RESET_URL = "/consumers/password-reset";
    public static final String H2_CONSOLE ="/h2-console/**";
    
    public static String getTokenSecret() {
    	 
    	AppProperties appProperties = (AppProperties)SpringApplicationContext
    			.getBeans("AppProperties");
    	return appProperties.getTokenSecret();
    }
    
}
