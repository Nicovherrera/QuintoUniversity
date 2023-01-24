package com.challenge.quinto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.challenge.quinto.servicios.UsuarioSevicio;


@EnableWebSecurity
public class Seguridad extends WebSecurityConfiguration {
	
	@Autowired
	private UsuarioSevicio usuarioServicio;
	
//	@Autowired
//	public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception{
//		
//		auth.userDetailsService(usuarioServicio)
//		.passwordEncoder(new BCryptPasswordEncoder());
//		
//	}
//	
	protected void configure (HttpSecurity http) throws Exception{
		
		http.
		
		authorizeHttpRequests()
        		.requestMatchers("assets/css/*", "assets/js/*", "assetes/img/*",
                "assets/**").permitAll()
        		.and().formLogin().loginPage("loguin")
        			.loginProcessingUrl("logincheck")
        			.usernameParameter("email")
        			.passwordParameter("clave")
        			.defaultSuccessUrl("administrcion")
        			.permitAll()
        		.and().logout()
        			.logoutUrl("/logout")
        			.logoutSuccessUrl("/loguin")
        			.permitAll()
        			.and().csrf().disable();
        		
	}

}
