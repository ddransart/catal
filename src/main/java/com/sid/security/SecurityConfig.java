package com.sid.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Cette méthode spécifie de manière statique ou sont stockés les users et leurs roles
		// peut être une solution provisoire avant de mettre des données dans la BDD
		
		//auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("ADMIN","USER");
		//auth.inMemoryAuthentication().withUser("user").password("{noop}1234").roles("USER");
		
		// On peut utiliser BCryptPassWordEncoder pour que le password ne soit pas en clair
		BCryptPasswordEncoder bcpe = getBCPE();
		System.out.println(bcpe.encode("1234"));
		
		// Auth by InMemoryAuth
		/*
		 * auth.inMemoryAuthentication().withUser("admin").password(bcpe.encode("1234"))
		 * .roles("ADMIN","USER");
		 * auth.inMemoryAuthentication().withUser("user").password(bcpe.encode("1234")).
		 * roles("USER"); auth.inMemoryAuthentication().passwordEncoder(new
		 * BCryptPasswordEncoder());
		 */
		
		// Auth by JDBC Auth
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("select username as principal, password as credentials, active from user where username=?")
		.authoritiesByUsernameQuery("select username as principal, role as role from user_role where username=?")
		.rolePrefix("ROLE_")
		.passwordEncoder(getBCPE());
		
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super.configure(http);
		http.formLogin().loginPage("/login");
		// http.authorizeRequests().anyRequest().authenticated(); // toutes les requetes nécessitent une authentification
		// http.authorizeRequests().antMatchers("/saveProduit","/delete","/saveFormProduit","/editFormProduit").hasRole("ADMIN");
		http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");
		http.authorizeRequests().antMatchers("/user/*").hasRole("USER");
		http.exceptionHandling().accessDeniedPage("/403");
	}
	
	@Bean
	BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}
}
