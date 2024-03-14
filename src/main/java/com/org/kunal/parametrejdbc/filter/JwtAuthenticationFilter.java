package com.org.kunal.parametrejdbc.filter;

import com.org.kunal.parametrejdbc.users.UserAuthService;
import com.org.kunal.parametrejdbc.users.UsersVo;
import com.org.kunal.parametrejdbc.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author kunal
 * @project SpringBootNamedParametreJdbcTemplate
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserAuthService userAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        String requestTokenHeader = httpServletRequest.getHeader("Authorization");

        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        final String token = requestTokenHeader.split(" ")[1].trim();
        log.info("Token value ---- " + token);

        if (!jwtUtil.validateToken(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        UsersVo user = jwtUtil.getUser(token);
        log.info("Token value from JWT Util class ---- '{}'", user);

        UserDetails userDetails = userAuthService.loadUserByUsername(user.getEmail());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
