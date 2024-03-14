package com.org.kunal.parametrejdbc.users;

import com.org.kunal.parametrejdbc.exception.DisabledUserException;
import com.org.kunal.parametrejdbc.exception.InvalidUserCredentialsException;
import com.org.kunal.parametrejdbc.util.JwtUtil;

import jakarta.validation.Valid;
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
	public ResponseEntity<String> signup(@Valid @RequestBody UsersVo userVo) {
		if (userVo.getEmail() == null || userVo.getEmail().trim().isEmpty() || userVo.getUserpwd() == null
				|| userVo.getUserpwd().trim().isEmpty()) {
			return new ResponseEntity<>("Email or Password must not be empty", HttpStatus.BAD_REQUEST);
		}

		String password = userVo.getUserpwd();

		if (!isValidPassword(password)) {
			throw new IllegalArgumentException(
					"Invalid password. Password must be alphanumeric with length from 4 to 12, include a capital letter, use at least one lowercase letter, consist of at least one digit, need to have one special symbol, and shouldn’t contain space, tab, etc.");
		}

		UsersVo userByEmail = userAuthService.getUserByUsername(userVo.getEmail());

		if (userByEmail == null) {
			userAuthService.saveUser(userVo);
			return new ResponseEntity<>("User successfully registered", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> generateJwtToken(@Valid @RequestBody JwtRequest jwtRequest) {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getUserpwd()));
		} catch (DisabledException disabledException) {
			throw new DisabledUserException("User Inactive");
		} catch (BadCredentialsException badCredentialsException) {
			throw new InvalidUserCredentialsException("Invalid Credentials");
		}
		UserDetails userDetails = userAuthService.loadUserByUsername(jwtRequest.getEmail());
		String email = userDetails.getUsername();
		String userPassword = userDetails.getPassword();
		Set<String> roles = userDetails.getAuthorities().stream().map(k -> k.getAuthority())
				.collect(Collectors.toSet());
		UsersVo user = new UsersVo();
		user.setEmail(email);
		user.setUserpwd(userPassword);
		user.setRoles(roles);
		String token = jwtUtil.generateToken(user);
		return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
	}

	// Validate password using regular expression pattern
	private boolean isValidPassword(String password) {
		String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,12}$";
		return password.matches(passwordRegex);
	}
}
