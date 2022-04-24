package com.irdaislakhuafa.simplespringbootjwt.utils.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.irdaislakhuafa.simplespringbootjwt.model.entities.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtility {
    @Value(value = "${jwt.secret.key}")
    private String SECRET_KEY;

    // 1 hour
    private final Date TOKEN_EXPIRED = new Date(System.currentTimeMillis() * 1000 * 60 * 60);

    // now
    private final Date TOKEN_CREATED = new Date();

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public String generateJwtToken(User user) {
        log.info("Generating JSON Web Token");

        log.info("Preparing claims");
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("roles", user.getAuthorities());
        log.info("Success creating claims from: " + user.getClass().getSimpleName());

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getId())
                .setIssuedAt(TOKEN_CREATED)
                .setExpiration(TOKEN_EXPIRED)
                .signWith(SIGNATURE_ALGORITHM, SECRET_KEY)
                .compact();
        log.info("Success generating JSON Web Token");

        return token;
    }

    public boolean isTokenValid(String token, User user) {
        final String userId = this.getParticularClaim(token, Claims::getSubject);
        return (userId.equals(user.getId()) && !this.isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return this.getParticularClaim(token, Claims::getExpiration).before(TOKEN_CREATED);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private <A> A getParticularClaim(String token, Function<Claims, A> claimsResolver) {
        final Claims claims = this.getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
}
