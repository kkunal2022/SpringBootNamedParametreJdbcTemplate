package com.org.kunal.parametrejdbc.security;

import com.org.kunal.parametrejdbc.config.ApiAuthenticationEntryPoint;
import com.org.kunal.parametrejdbc.filter.JwtAuthenticationFilter;
import com.org.kunal.parametrejdbc.users.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

/**
 * @author kunal
 * @project SpringBootNamedParameterJdbcTemplate
 */
@Configuration
public class ApiSecurityConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ApiAuthenticationEntryPoint authenticationEntryPoint;

    private static final String H2_CONSOLE = "/h2-console/**";

    @Autowired
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userAuthService).passwordEncoder(passwordEncoder);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests().requestMatchers(antMatcher(H2_CONSOLE)).permitAll().and()
                        .csrf(csrf -> csrf.ignoringRequestMatchers(antMatcher(H2_CONSOLE)));

        http.authorizeHttpRequests().requestMatchers(AntPathRequestMatcher.antMatcher(H2_CONSOLE)).permitAll();

        http.authorizeHttpRequests(
                auth -> auth.requestMatchers("/api/users/signup", "/api/users/signin")
                        .permitAll().anyRequest().authenticated());

        http.exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint));

        http.csrf(csrf -> csrf.disable());
        http.csrf(AbstractHttpConfigurer::disable);
        http.headers(headers -> headers.frameOptions().disable());
        http.headers(headers -> headers.frameOptions().sameOrigin());

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
