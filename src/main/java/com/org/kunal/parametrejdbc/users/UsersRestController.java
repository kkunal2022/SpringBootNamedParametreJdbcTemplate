package com.org.kunal.parametrejdbc.users;

import com.org.kunal.parametrejdbc.exception.DisabledUserException;
import com.org.kunal.parametrejdbc.exception.InvalidUserCredentialsException;
import com.org.kunal.parametrejdbc.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author kunal
 * @project SpringBootNamedParameterJdbcTemplate
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/users")
@Slf4j
public class UsersRestController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UsersVo userVo) {
        UsersVo userByUsername = userAuthService.getUserByUsername(userVo.getUsername());

        if (userByUsername == null) {
            userAuthService.saveUser(userVo);
            return new ResponseEntity<>("User successfully registered", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> generateJwtToken(@RequestBody JwtRequest jwtRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getUserpwd()));
        } catch (DisabledException e) {
            throw new DisabledUserException("User Inactive");
        } catch (BadCredentialsException e) {
            throw new InvalidUserCredentialsException("Invalid Credentials");
        }
        UserDetails userDetails = userAuthService.loadUserByUsername(jwtRequest.getUsername());
        String username = userDetails.getUsername();
        String userPassword = userDetails.getPassword();
        Set<String> roles = userDetails.getAuthorities().stream().map(k -> k.getAuthority())
                .collect(Collectors.toSet());
        UsersVo user = new UsersVo();
        user.setUsername(username);
        user.setUserpwd(userPassword);
        user.setRoles(roles);
        String token = jwtUtil.generateToken(user);
        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }
}
