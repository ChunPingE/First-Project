package com.example.demo.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;

import com.example.demo.security.*;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();

		// http.formLogin(Customizer.withDefaults());

		http.formLogin()
				.loginPage("/member/login");

		http.logout()
				.logoutUrl("/member/logout");

		// http.authorizeHttpRequests().requestMatchers("/add").authenticated();
		// http.authorizeHttpRequests().requestMatchers("/member/signup").anonymous();
		// http.authorizeHttpRequests().requestMatchers("/**").permitAll();

		/*
		 * http.authorizeHttpRequests().requestMatchers("/add") .access(new
		 * WebExpressionAuthorizationManager("isAuthenticated()"));
		 * 
		 * http.authorizeHttpRequestsㅛ().requestMatchers("/member/signup") .access(new
		 * WebExpressionAuthorizationManager("isAnonymous()"));
		 * 
		 * http.authorizeHttpRequests().requestMatchers("/**") .access(new
		 * WebExpressionAuthorizationManager("permitAll"));
		 */
		return http.build();
	}
	
	@Bean
	public CustomSecurityChecker customSecurityChecker() {
		return new CustomSecurityChecker();
	}
}
