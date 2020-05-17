package com.baeldung.lss.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class LssSecurityConfig extends WebSecurityConfigurerAdapter {

    public LssSecurityConfig() {
        super();
    }

    //

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off 
        auth.
            inMemoryAuthentication().passwordEncoder(passwordEncoder())
            .withUser("user").password(passwordEncoder().encode("pass")).roles("USER").and()
            .withUser("admin").password(passwordEncoder().encode("pass")).roles("ADMIN")
            ;
    } // @formatter:on

    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        http
        .authorizeRequests()
            
//             .antMatchers("/secured").access("hasRole('USER')")
//             .antMatchers("/secured").access("hasAuthority('ROLE_ADMIN')")
//             .antMatchers("/secured").access("hasAuthority('ROLE_ADMIN') and principal.username == 'admin'")
             .antMatchers("/secured").access("hasAuthority('ROLE_ADMIN') or principal.username == 'user'")
//             .antMatchers("/secured").hasIpAddress("192.168.100.13")
//             .antMatchers("/secured").hasIpAddress("::1")
//             .antMatchers("/secured").not().hasIpAddress("::1")
//             .antMatchers("/secured").anonymous()
//             .antMatchers("/secured").access("request.method == 'GET'")
//             .antMatchers("/secured").access("request.method != 'POST'")

            .anyRequest().authenticated()
        
        .and()
        .formLogin().
            loginPage("/login").permitAll().
            loginProcessingUrl("/doLogin")

        .and()
        .logout().permitAll().logoutUrl("/logout")
        
        .and()
        .csrf().disable()
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
