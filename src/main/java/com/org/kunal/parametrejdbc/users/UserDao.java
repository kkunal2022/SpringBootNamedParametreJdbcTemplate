package com.org.kunal.parametrejdbc.users;

import lombok.extern.slf4j.Slf4j;
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


    public Users getUser(String username) {
        List<Users> users = jdbcTemplate.query("select user_name, user_pass from users where user_name = ?",
                new UserRowMapper(), new Object[] { username });

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }

    public List<Role> getRoles(String username) {
        List<Map<String, Object>> getRolesJdbcResults = jdbcTemplate
                .queryForList("select user_role from user_role where user_name = ?", new Object[] { username });

        return getRolesJdbcResults.stream().map(kunal -> {
            Role role = new Role();
            role.setRole(String.valueOf(kunal.get("user_role")));
            log.info("Requested Usr Role Found In Backend As ---- '{}'" , role);
            return role;
        }).collect(Collectors.toList());
    }

    public void saveUser(UserRole saveUserWithRole) {
        jdbcTemplate.update("insert into users(user_name, user_pass) values(?, ?)",
                new Object[] { saveUserWithRole.getUsername(), saveUserWithRole.getUserpwd() });

        saveUserWithRole.getRoles().forEach(kunal -> jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "insert into user_role(user_name, user_role) values(?, ?)",
                        new String[] { "user_name", "user_role" });
                preparedStatement.setString(1, saveUserWithRole.getUsername());
                preparedStatement.setString(2, kunal);
                return preparedStatement;
            }
        }));
    }
}
