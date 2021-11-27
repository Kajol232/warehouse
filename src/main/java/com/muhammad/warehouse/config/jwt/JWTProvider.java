package com.muhammad.warehouse.config.jwt;

import com.muhammad.warehouse.model.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.muhammad.warehouse.config.constant.SecurityConstant.AUTHORITIES;

@Component
public class JWTProvider {
    @Value("$(jwt.secret)")
    private String jwtSecret;

    public String generateToken(UserPrincipal user){
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim(AUTHORITIES, getClaimsFromUser(user))
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public boolean validateToken(String token){
        try{
           Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
           return true;
        } catch(Exception e){
            System.out.println(e.getClass() + "\n" + e.getMessage());
        }
        return false;
    }

    public String getEmailFromToken(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
        List<String> authorities = new ArrayList<>();
        for(GrantedAuthority grantedAuthority : userPrincipal.getAuthorities()){
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(new String[0]);

    }
}
