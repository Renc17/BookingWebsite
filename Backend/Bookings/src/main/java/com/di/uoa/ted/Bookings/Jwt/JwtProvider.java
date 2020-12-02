package com.di.uoa.ted.Bookings.Jwt;

import com.di.uoa.ted.Bookings.Auth.ApplicationUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;


@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    public JwtProvider(SecretKey secretKey,
                            JwtConfig jwtConfig) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    public String generateJwtToken(Authentication authentication){
        ApplicationUserDetails applicationUserDetails = (ApplicationUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(applicationUserDetails.getUsername())
                .claim("authorities", applicationUserDetails.getAuthorities())
                .setIssuedAt(new Date())
                // .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();
    }


    public String getUsernameFromJwtToken(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }

        return false;
    }
}
