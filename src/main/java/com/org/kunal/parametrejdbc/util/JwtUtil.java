package com.org.kunal.parametrejdbc.util;

import com.org.kunal.parametrejdbc.users.UsersVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author kunal
 * @project SpringBootNamedParameterJdbcTemplate
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    public UsersVo getUser(final String token) {
        try {
            Claims body = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();

            UsersVo user = new UsersVo();
            user.setUsername(body.getSubject());

            Set<String> roles = Arrays.stream(body.get("roles").toString()
                    .split(",")).map(String::new)
                    .collect(Collectors.toUnmodifiableSet());
            user.setRoles(roles);

            return user;
        } catch (Exception e) {
            log.error("Exception in getting JWT Token for User " +
                    "with exceptionMessage-- '{}' and exception ----" , e.getMessage() , e);
        }
        return null;
    }

    public String generateToken(UsersVo generateTokenForUser) {
        Claims jwtClaims = Jwts.claims().setSubject(generateTokenForUser.getUsername());
        jwtClaims.put("roles", generateTokenForUser.getRoles());
        long nowMillis = System.currentTimeMillis();
        long expiryInMilliSecond = nowMillis + 1080000;
        Date expiryDate = new Date(expiryInMilliSecond);
        return Jwts.builder()
                .setClaims(jwtClaims)
                .setIssuedAt(new Date(nowMillis)).setExpiration(expiryDate)
                .signWith(key(), SignatureAlgorithm.HS256).compact();
    }

    public boolean validateToken(final String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
        } catch (SignatureException signatureException) {
            log.error("Error in validating token ---- '{}'" , signatureException.getMessage());
            return false;
        }

        return true;
    }

    private Key key() {
        byte[] keyBytes = Decoders.BASE64
                .decode("404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
