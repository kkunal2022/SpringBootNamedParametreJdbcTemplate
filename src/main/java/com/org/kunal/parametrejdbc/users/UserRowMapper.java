package com.org.kunal.parametrejdbc.users;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author kunal
 * @project SpringBootNamedParametreJdbcTemplate
 */
public class UserRowMapper implements RowMapper<Users> {

	@Override
	public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
		Users user = new Users();
		user.setEmail(rs.getString("email"));
		user.setUserpwd(rs.getString("user_pass"));
		return user;
	}

}
