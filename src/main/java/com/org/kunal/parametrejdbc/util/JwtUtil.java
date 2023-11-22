package com.org.kunal.parametrejdbc.util;

import com.org.kunal.parametrejdbc.users.UsersVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author kunal
 * @project SpringBootNamedParametreJdbcTemplate
 */
@Component
public class JwtUtil {

    public UsersVo getUser(final String token) {
        try {
            Claims body = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();

            UsersVo user = new UsersVo();
            user.setUsername(body.getSubject());

            Set<String> roles = Collections.unmodifiableSet(Arrays.asList(body.get("roles").toString().split(",")).stream().map(r -> new String(r))
                    .collect(Collectors.toSet()));
            user.setRoles(roles);

            return user;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " => " + e);
        }
        return null;
    }

    public String generateToken(UsersVo u) {
        Claims claims = Jwts.claims().setSubject(u.getUsername());
        claims.put("roles", u.getRoles());
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 180000;
        Date exp = new Date(expMillis);
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
                .signWith(key(), SignatureAlgorithm.HS256).compact();
    }

    public boolean validateToken(final String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
        } catch (SignatureException ex) {
            return false;
        }

        return true;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode("jwtsecretkeyshouldbelongenoughatleast256bits"));
    }
}
