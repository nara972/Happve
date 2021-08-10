package com.kh.happve.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity 
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		    .mvcMatchers("/","/login","/signup","/api/basic").permitAll()
		    .anyRequest().authenticated();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring()
		   .antMatchers("/favicon.ico", "/resources/**", "/error")
		   .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

}
