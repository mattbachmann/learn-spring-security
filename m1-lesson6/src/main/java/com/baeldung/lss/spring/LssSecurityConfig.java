package com.baeldung.lss.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class LssSecurityConfig extends WebSecurityConfigurerAdapter {
//    Logger LOG = LoggerFactory.getLogger(LssSecurityConfig.class);

    public LssSecurityConfig() {
        super();
    }

    //

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off 
        auth.
            inMemoryAuthentication().passwordEncoder(passwordEncoder()).
            withUser("coco").password(passwordEncoder().encode("C0c0")).roles("USER")
            .and()
            .withUser("admin").password(passwordEncoder().encode("Adm1n")).roles("ADMIN");
    } // @formatter:on

    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        http
            .authorizeRequests()
            .anyRequest().authenticated()

            .and()
            .formLogin().
            loginPage("/login").permitAll().
            loginProcessingUrl("/doLogin")

            .and()
            .logout().permitAll().logoutUrl("/logout")
            .logoutSuccessHandler((HttpServletRequest var1, HttpServletResponse var2, Authentication var3) -> {
//                LOG.info("Logged out " + var3.getName());
                System.out.println("Logged out " + var3.getName());
            })

            .and()
            .csrf().disable()
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
