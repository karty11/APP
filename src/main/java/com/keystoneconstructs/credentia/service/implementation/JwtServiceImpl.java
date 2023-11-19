package com.keystoneconstructs.credentia.service.implementation;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.keystoneconstructs.credentia.pojo.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
@Slf4j
public class JwtServiceImpl {

    public static final String SECRET = "qCuqcUjUyZcrhDMh09mgk60dmlz3tBrFwLNVyLUCsdGyHdDznoJgxzVyNHQUNr86";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();


    public String generateToken( UserDetails user ) {

        Map<String, Object> claims = new HashMap<>();
        return createToken( claims, user );
    }


    private String createToken( Map<String, Object> claims, UserDetails user ) {

        return Jwts.builder().setClaims( claims ).setSubject( gson.toJson( user ) )
                .setIssuedAt( new Date( System.currentTimeMillis() ) )
                .setExpiration( new Date( System.currentTimeMillis() + ( 1000 * 60 * 60 * 3 ) ) )
                .signWith( getSignKey(), SignatureAlgorithm.HS256 ).compact();
    }


    private Key getSignKey() {

        byte[] keyBytes = Decoders.BASE64.decode( SECRET );
        return Keys.hmacShaKeyFor( keyBytes );
    }


    public String extractUsername( String token ) {

        return gson.fromJson( extractClaim( token, Claims::getSubject ), UserEntity.class ).getUsername();
    }


    public Date extractExpiration( String token ) {

        return extractClaim( token, Claims::getExpiration );
    }


    public <T> T extractClaim( String token, Function<Claims, T> claimsResolver ) {

        final Claims claims = extractAllClaims( token );
        return claimsResolver.apply( claims );
    }


    private Claims extractAllClaims( String token ) {

        return Jwts.parserBuilder().setSigningKey( getSignKey() ).build().parseClaimsJws( token ).getBody();
    }


    private Boolean isTokenExpired( String token ) {

        return extractExpiration( token ).before( new Date() );
    }


    public Boolean validateToken( String token, UserDetails userEntity ) {

        final String userName = extractUsername( token );
        return ( userName.equals( userEntity.getUsername() ) && !isTokenExpired( token ) );
    }

}
