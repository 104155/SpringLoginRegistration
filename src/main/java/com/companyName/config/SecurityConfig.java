package com.companyName.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private String usersQuery = "SELECT email, user_password, 1 AS enabled " +
								"FROM app_user " +
								"WHERE email = ? AND status = 'VERIFIED'";
	
	private String rolesQuery = "SELECT u.email, r.role_name " + 
								"FROM app_user u " + 
								"INNER JOIN app_user_auth_role auar ON(u.user_id = auar.user_id) " + 
								"INNER JOIN auth_role r ON(auar.role_id = r.role_id) " + 
								"WHERE u.email=?";
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private CustomLoginSuccessHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				// URLs matching for access rights
				.antMatchers("/", "/login", "/resetPassword", "/register").permitAll()
				// ORDER/SEQUENCE OF ROLES IS IMPORTANT! first comes role with most to role with
				// least privilege
//				.antMatchers("/homeUser/**").hasAnyAuthority("SUPER_USER", "ADMIN_USER", "APP_USER")
				.antMatchers("/homeUser", "/userProfile").hasAnyAuthority("SUPER_USER", "ADMIN_USER", "APP_USER")
				.antMatchers("/homeAdmin/**").hasAnyAuthority("SUPER_USER", "ADMIN_USER")
				.anyRequest().authenticated()
				.and()			
				// form login
				.csrf().disable().formLogin().loginPage("/login").failureUrl("/login?error=true")
				.successHandler(successHandler).usernameParameter("email").passwordParameter("password")
				.and()				
				// logout
				.logout().permitAll();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/styles/**" ,"/css/**" ,"/scripts/**" ,"/js/**" ,"/images/**");
	}
}
