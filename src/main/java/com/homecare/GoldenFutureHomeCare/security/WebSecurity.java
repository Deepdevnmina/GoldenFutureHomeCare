package com.homecare.GoldenFutureHomeCare.security;


import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.homecare.GoldenFutureHomeCare.service.ConsumerService;


@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{

    private final ConsumerService consumerDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(ConsumerService consumerDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.consumerDetailsService = consumerDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .cors().and()
        .csrf().disable().authorizeRequests()
       
        .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
        .permitAll()
        .antMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_EMAIL_URL)
        .permitAll()
        .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_REQUEST_URL)
        .permitAll()
        .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_URL)
        .permitAll()
        .antMatchers(SecurityConstants.H2_CONSOLE)
        .permitAll()
       // .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
       // .permitAll()
        .anyRequest().authenticated().and()
        .addFilter(getAuthenticationFilter())
        .addFilter(new AuthorizationFilter(authenticationManager()))
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(consumerDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
    
    protected AuthenticationFilter getAuthenticationFilter() throws Exception {
	    final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
	    filter.setFilterProcessesUrl("/consumers/login");
	    return filter;
	}
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {
    	final CorsConfiguration configuration = new CorsConfiguration();
    	   
    	configuration.setAllowedOrigins(Arrays.asList("*"));
    	configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE","OPTIONS"));
    	configuration.setAllowCredentials(true);
    	configuration.setAllowedHeaders(Arrays.asList("*"));
    	
    	final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	source.registerCorsConfiguration("/**", configuration);
    	
    	return source;
    }
}
/*@EnableWebSecurity//Newer Version.
public class WebSecurity{
	// UserService is extends UserDetailsService.so we change to ConsumerService in line 54.
	private final ConsumerService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;// initialize using fields from source (Constructor)
	
	
	public WebSecurity(ConsumerService consumerService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = consumerService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		
	}
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
		AuthenticationManagerBuilder authenticationManagerBuilder=
				http.getSharedObject(AuthenticationManagerBuilder.class);
		
		authenticationManagerBuilder.userDetailsService(userDetailsService)
		.passwordEncoder(bCryptPasswordEncoder);
		
	//Newer versions of spring Security is written as follows	
		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
		http.authenticationManager(authenticationManager);
		
		http.csrf().disable()
		.cors().and()//This line is added to enable crossOrigin in Spring Security.
		.authorizeRequests()
		.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)//before securityConstants we use "/consumers/save"
		.permitAll()
        .antMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_EMAIL_URL)
        .permitAll()
        .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_REQUEST_URL)
        .permitAll()
        .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_URL)
        .permitAll()
        .antMatchers(SecurityConstants.H2_CONSOLE)
        .permitAll()
		.anyRequest()
		.authenticated()
		.and().addFilter(getAuthenticationFilter(authenticationManager))
		.addFilter(new AuthorizationFilter(authenticationManager))
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.headers().frameOptions().disable();//Please remove this line of code before you Commit to Git-hub.
		return http.build();
	}
	protected AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager)throws Exception{
		final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager);
		filter.setFilterProcessesUrl("/consumers/login");
		return filter;
	}
	 @Bean//This class-method is added after letting crossOrigin up.
	    public CorsConfigurationSource corsConfigurationSource()
	    {
	    	final CorsConfiguration configuration = new CorsConfiguration();
	    	   
	    	configuration.setAllowedOrigins(Arrays.asList("*"));
	    	configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE","OPTIONS"));
	    	configuration.setAllowCredentials(true);
	    	configuration.setAllowedHeaders(Arrays.asList("*"));
	    	
	    	final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    	source.registerCorsConfiguration("/**", configuration);
	    	
	    	return source;
	    }
}
	*/










