package brand.shop.system.security;//package org.shop.system.security;

import brand.shop.system.helper.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import static brand.shop.system.helper.DateUtil.getCurrentDate;


@PropertySource(value = {"classpath:application.properties"})
@Component
public class JwtUtil {
    @Value("${jwt.secret.key}")
    private String key;


    public String generateToken(String id){
        return Jwts.builder()
                .claim("sub", id)
                .claim("iat", getCurrentDate())
                .claim("exp", DateUtil.getDateAfterFiveHour())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }


    public Object getClaim(String token, String claim){
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .get(claim);
    }

    public Boolean checkExpiration(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration().after(getCurrentDate());
    }


}
