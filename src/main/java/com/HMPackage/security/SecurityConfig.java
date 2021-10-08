package com.HMPackage.security;

import com.HMPackage.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true,jsr250Enabled = true,securedEnabled = true)
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationEntryPointHandler entryPointException;
    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.cors();
        http.csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(entryPointException);
        http.authorizeRequests().antMatchers("/user/create","/user/login").permitAll()
                .anyRequest().authenticated().and()
                .formLogin().permitAll();
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
