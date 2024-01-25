package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.demo.servicesImp.CustomUserDetailServiceImp;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailServiceImp customUserDetailServiceImp;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/signin", "/home", "/registration", "/index", "/persons22/{id}", "/previewpage",
						"/image/{id}", "/upload", "/persondetails/new", "/existEmail", "/persons",
						"/persons/{personId}", "/person11", "/captcha", "/validate-captcha", "/css/index.css",
						"/angularjs/index.js", "/css/login.css","/person123")
				.permitAll().antMatchers("/normal", "/persondetails/edit/{id}").hasRole("NORMAL")
				.antMatchers("/welcome", "/persondetails", "/persondetail", "/viewpdf/{id}", "/delete/{id}",
						"/excel/download", "/download/excel11", "/excel/download1111", "/users/count-male",
						"/users/count-female", "/users/count-trans", "/users/count-D", "/users/count-S",
						"/users/count-M", "/chart")
				.hasRole("ADMIN").anyRequest().authenticated().and().formLogin().loginPage("/signin")
				.loginProcessingUrl("/dologin").defaultSuccessUrl("/check").permitAll();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(customUserDetailServiceImp).passwordEncoder(passwordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

}
