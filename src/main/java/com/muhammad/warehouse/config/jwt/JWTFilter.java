package com.muhammad.warehouse.config.jwt;


import com.muhammad.warehouse.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Component
public class JWTFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION = "Authorization";

    private JWTProvider jwtProvider;

    UserServiceImpl userService;

    public String getTokenFromRequest(HttpServletRequest request){
        String bearer = request.getHeader(AUTHORIZATION);
        if(hasText(bearer) && bearer.startsWith("Bearer ")){
            return bearer.substring(7);
        }
        return null;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(httpServletRequest);
        if(token != null && jwtProvider.validateToken(token)){
            String email = jwtProvider.getEmailFromToken(token);
            UserDetails userDetails =  userService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
