package com.org.kunal.parametrejdbc.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kunal
 * @project SpringBootNamedParametreJdbcTemplate
 */
@Repository
public class UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Users getUser(String username) {
        String sql = "SELECT user_name, user_pass FROM users where user_name = :username";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_name", username);
        return (Users) namedParameterJdbcTemplate.query(sql, params, new UserRowMapper());
    }

    public List<Role> getRoles(String username) {
        List<Map<String, Object>> getRolesJdbcResults = jdbcTemplate
                .queryForList("select user_role from user_role where user_name = ?", new Object[] { username });

        List<Role> roles = new ArrayList<>();
        getRolesJdbcResults.forEach(result -> {
            Role role = new Role();
            role.setRole(String.valueOf(result.get("user_role")));
            Role userRole = role;
            roles.add(userRole);
        });

        return roles;
    }

    public void saveUser(UserRole saveUserWithRole) {
        jdbcTemplate.update("insert into users(user_name, user_pass) values(?, ?)",
                new Object[] { saveUserWithRole.getUsername(), saveUserWithRole.getUserpwd() });

        saveUserWithRole.getRoles().forEach(k -> jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "insert into user_role(user_name, user_role) values(?, ?)",
                        new String[] { "user_name", "user_role" });
                preparedStatement.setString(1, saveUserWithRole.getUsername());
                preparedStatement.setString(2, k);
                return preparedStatement;
            }
        }));
    }
}
