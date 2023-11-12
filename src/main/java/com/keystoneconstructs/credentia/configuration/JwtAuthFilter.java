package com.keystoneconstructs.credentia.configuration;

import com.keystoneconstructs.credentia.service.UserService;
import com.keystoneconstructs.credentia.service.implementation.JwtServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {


    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private UserService userService;

    public JwtAuthFilter( JwtServiceImpl jwtService, UserService userService ) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain ) throws ServletException, IOException {

        String authHeader = request.getHeader( "Authorization" );

        if( StringUtils.isEmpty( authHeader ) ) {
            filterChain.doFilter( request, response );
            return;
        }


        String token;
        String username;
        if( authHeader.startsWith( "Bearer " ) ) {
            token = authHeader.substring( 7 );
        } else {
            token = authHeader;
        }

        username = jwtService.extractUsername( token );

        if( username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
            UserDetails userEntity = userService.loadUserByUsername( username );
            if( jwtService.validateToken( token, userEntity ).booleanValue() ) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken( userEntity,
                        null, userEntity.getAuthorities() );
                authToken.setDetails( new WebAuthenticationDetailsSource().buildDetails( request ) );
                SecurityContextHolder.getContext().setAuthentication( authToken );
            }
        }

        filterChain.doFilter( request, response );
    }

}
