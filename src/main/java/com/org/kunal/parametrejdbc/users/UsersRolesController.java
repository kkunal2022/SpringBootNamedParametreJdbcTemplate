package com.org.kunal.parametrejdbc.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kunal
 * @project SpringBootNamedParameterJdbcTemplate
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/test")
@Slf4j
public class UsersRolesController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/greet/user")
    public ResponseEntity<String> greetingUser() {
        log.info("Entering User Role ---- ");
        return new ResponseEntity<String>("Welcome, you have USER role", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/greet/admin")
    public ResponseEntity<String> greetingAdmin() {
        log.info("Entering Admin Role ---- ");
        return new ResponseEntity<String>("Welcome, you have ADMIN role", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/greet/userOrAdmin")
    public ResponseEntity<String> greetingUserOrAdmin() {
        log.info("Entering User OR Admin Role ---- ");
        return new ResponseEntity<String>("Welcome, you have USER and ADMIN role", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ANONYMOUS')")
    @GetMapping("/greet/anonymous")
    public ResponseEntity<String> greetingAnonymous() {
        return new ResponseEntity<String>("Welcome, you have USER and ADMIN role", HttpStatus.OK);
    }
}
