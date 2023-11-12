package com.keystoneconstructs.credentia.configuration;

import com.keystoneconstructs.credentia.service.UserService;
import com.keystoneconstructs.credentia.service.implementation.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {


    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private UserService userService;

    @Bean
    public UserService userService() {
        return userService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {

        http.csrf( AbstractHttpConfigurer::disable ).sessionManagement( httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy( SessionCreationPolicy.STATELESS );
        } );

        http.authorizeHttpRequests(
                auth -> auth.requestMatchers( "/api/v1/user/add", "/api/v1/user/resetPassword", "/api/v1/user/login" )
                        .permitAll() );

        http.authorizeHttpRequests(
                        auth -> auth.requestMatchers( "/api/v1/user/**", "/api/v1/organization**", "/api/v1/certifier/**",
                                "/api/v1/certificate" ).authenticated() )
                .addFilterBefore( new JwtAuthFilter( jwtService, userService ),
                        UsernamePasswordAuthenticationFilter.class ).authenticationProvider( authenticationProvider() );

        http.authorizeHttpRequests( auth -> auth.anyRequest().permitAll() );

        return http.build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService( userService );
        authenticationProvider.setPasswordEncoder( passwordEncoder() );
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager( AuthenticationConfiguration config ) throws Exception {
        return config.getAuthenticationManager();
    }

}
