/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.config;

import br.sp.telesul.model.CustomPasswordEncoder;
import br.sp.telesul.service.UserAndRoleService;
import java.io.IOException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

/**
 *
 * @author ebranco
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new CustomPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAccessDeniedHandler customAccess = new CustomAccessDeniedHandler();
        int maxSessions = SystemConfig.maxSessions;
        Check403 check = new Check403();

        http.authorizeRequests()
               .antMatchers(HttpMethod.POST, "/crud/**").hasAnyRole("MANAGER", "ADMIN", "EMPLOYEE")
                .antMatchers("/").permitAll()
                .antMatchers("/index").hasAnyRole("MANAGER", "ADMIN", "EMPLOYEE")
                .antMatchers("/login").permitAll()
                .antMatchers("/atualizarFuncionario").hasAnyRole("MANAGER", "ADMIN", "EMPLOYEE")
                .antMatchers("/cadastrarFuncionario").hasAnyRole("ADMIN")
                .antMatchers("/gerenciarUsuarios").hasRole("ADMIN")                
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/j_spring_security_check").usernameParameter("j_username").passwordParameter("j_password")
                .defaultSuccessUrl("/index")
                .failureUrl("/login?error")
                .and().logout().logoutUrl("/j_spring_security_logout").logoutSuccessUrl("/login?logout")
                .and().csrf().disable()
                .exceptionHandling().accessDeniedPage("/loginError")
                .and().sessionManagement().maximumSessions(maxSessions).expiredUrl("/login?expirado");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

}
