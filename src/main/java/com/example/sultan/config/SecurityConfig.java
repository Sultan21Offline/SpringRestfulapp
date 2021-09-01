package com.example.sultan.config;


import com.example.sultan.filter.CustomAuthenticationFilter;
import com.example.sultan.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/api/v1/login/**","/api/v1/token/refresh/**").permitAll();
        http.authorizeRequests().antMatchers(GET,"/api/v1/users").hasAnyAuthority("USER","ADMIN");
        http.authorizeRequests().antMatchers(POST,"/api/v1/user/save").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(POST,"/api/v1/user/role/save").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(POST,"/api/v1/user/role/addtouser").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(PUT,"/api/v1/user/update").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(DELETE,"/api/v1/user/delete").hasAnyAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated().and().formLogin();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
