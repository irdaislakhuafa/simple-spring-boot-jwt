package com.irdaislakhuafa.simplespringbootjwt.security;

import com.irdaislakhuafa.simplespringbootjwt.services.UserService;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO configure authentication manager
        auth.authenticationProvider(new DaoAuthenticationProvider() {
            {
                setUserDetailsService(userService);
                setPasswordEncoder(passwordEncoder);
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO: configure web security (public/private/authentication URL/URI)

        // start
        http
                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .httpBasic()
        // end
        ;
    }

}
