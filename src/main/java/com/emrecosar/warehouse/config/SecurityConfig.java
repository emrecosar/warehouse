package com.emrecosar.warehouse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.DELETE).hasRole(ROLE_ADMIN)
                .antMatchers(HttpMethod.POST).hasRole(ROLE_ADMIN)
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth,
                                @Value("${user.username}") String userUsername, @Value("${user.password}") String userPassword,
                                @Value("${admin.username}") String adminUserName, @Value("${admin.password}") String adminPassword) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser(userUsername).password(passwordEncoder.encode(userPassword)).roles(ROLE_USER)
                .and()
                .withUser(adminUserName).password(passwordEncoder.encode(adminPassword))
                .roles(ROLE_USER, ROLE_ADMIN);
    }
}
