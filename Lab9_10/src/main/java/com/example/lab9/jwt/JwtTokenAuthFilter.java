package com.example.lab9.jwt;

import com.example.lab9.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Slf4j
public class JwtTokenAuthFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtTokenUtils tokenUtils;

    @Autowired
    private UserService userDetailService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        
        try {
            String jwtToken = getJwtTokenFromRequest(req);

            if(StringUtils.hasText(jwtToken) && tokenUtils.validateToken(jwtToken)) {

                String email = tokenUtils.getUserInfoFromJWT(jwtToken);
                UserDetails userDetails = userDetailService.loadUserByUsername(email);
                
                if(userDetails != null) {
                    setAuthenticationContext(userDetails, req);
                }
            }
        } catch (Exception e) {
            log.error("Failed on set user authentication", e);
        }

        filterChain.doFilter(req, res);
    }

    private void setAuthenticationContext(UserDetails userDetails, HttpServletRequest req) {
        UsernamePasswordAuthenticationToken
            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getJwtTokenFromRequest(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
    
}
