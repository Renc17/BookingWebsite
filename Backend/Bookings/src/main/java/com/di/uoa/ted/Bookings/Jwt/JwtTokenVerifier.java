package com.di.uoa.ted.Bookings.Jwt;

import com.di.uoa.ted.Bookings.Auth.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenVerifier extends OncePerRequestFilter {


    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    private ApplicationUserService applicationUserService;


    @Override
    protected void doFilterInternal(HttpServletRequest Request,
                                    HttpServletResponse Response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {

            String jwt = getJwt(Request);

            if (jwt != null && tokenProvider.validateJwtToken(jwt)) {
                String username = tokenProvider.getUsernameFromJwtToken(jwt);

                UserDetails userDetails = applicationUserService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(Request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }catch(Exception e){
            logger.error("Cant set user authentication -> Message: {}", e);
        }
            filterChain.doFilter(Request, Response);
    }

    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

        if (authHeader != null && authHeader.startsWith(jwtConfig.getTokenPrefix())) {
            return authHeader.replace(jwtConfig.getTokenPrefix(),"");
        }

        return null;
    }


}
