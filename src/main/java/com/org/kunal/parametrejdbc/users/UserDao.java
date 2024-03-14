package com.org.kunal.parametrejdbc.users;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author kunal
 * @project SpringBootNamedParametreJdbcTemplate
 */
@Repository
@Slf4j
public class UserDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }


    public Users getUser(String email) {
        List<Users> users = jdbcTemplate.query("select email, user_pass from users where email = ?",
                new UserRowMapper(), new Object[] { email });

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }

    public List<Role> getRoles(String email) {
        List<Map<String, Object>> getRolesJdbcResults = jdbcTemplate
                .queryForList("select user_role from user_role where email = ?", new Object[] { email });

        return getRolesJdbcResults.stream().map(kunal -> {
            Role role = new Role();
            role.setRole(String.valueOf(kunal.get("user_role")));
            log.info("Requested Usr Role Found In Backend As ---- '{}'" , role);
            return role;
        }).collect(Collectors.toList());
    }

	public void saveUser(UserRole saveUserWithRole) {
		String email = saveUserWithRole.getEmail();
		String password = saveUserWithRole.getUserpwd();

		if (StringUtils.isBlank(email) || StringUtils.isEmpty(email)) {
			throw new IllegalArgumentException("Email cannot be null, empty, or blank");
		}

		// Check if the email format is valid
		if (!isValidEmail(email)) {
			throw new IllegalArgumentException("Invalid email format");
		}

		if (StringUtils.isBlank(password)) {
			throw new IllegalArgumentException("Password cannot be null, empty, or blank");
		}

		// Validate password
		/*
		if (!isValidPassword(password)) {
			throw new IllegalArgumentException(
					"Password must be alphanumeric with length from 4 to 12, include a capital letter, use at least one lowercase letter, consist of at least one digit, need to have one special symbol, and shouldn’t contain space, tab, etc.");
		}
		*/

		jdbcTemplate.update("insert into users(email, user_pass) values(?, ?)",
				new Object[] { saveUserWithRole.getEmail(), saveUserWithRole.getUserpwd() });

		saveUserWithRole.getRoles().forEach(kunal -> jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(
						"insert into user_role(email, user_role) values(?, ?)", new String[] { "email", "user_role" });
				preparedStatement.setString(1, saveUserWithRole.getEmail());
				preparedStatement.setString(2, kunal);
				return preparedStatement;
			}
		}));
	}

	private boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		return email.matches(emailRegex);
	}

	// Validate password according to specified conditions
	private boolean isValidPassword(String password) {
		String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,12}$";
		return password.matches(passwordRegex);
	}
}
