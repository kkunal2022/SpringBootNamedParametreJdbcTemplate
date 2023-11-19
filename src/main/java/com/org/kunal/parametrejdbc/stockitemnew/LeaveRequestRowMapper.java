/**
 * kunal
 * SpringBootNamedParametreJdbcTemplate
 * com.org.kunal.parametrejdbc.stockitemnew
 */
package com.org.kunal.parametrejdbc.stockitemnew;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * kunal SpringBootNamedParametreJdbcTemplate 2023
 */
@Component
public class LeaveRequestRowMapper implements RowMapper<LeaveRequest> {

	@Override
	public LeaveRequest mapRow(ResultSet resultSet, int rowNum) throws SQLException {

		LeaveRequest leaveRequest = new LeaveRequest();
		leaveRequest.setId(resultSet.getInt("id"));
		leaveRequest.setStartDate(resultSet.getDate("start_date").toLocalDate());
		leaveRequest.setEndDate(resultSet.getDate("end_date").toLocalDate());
		leaveRequest.setStatus(resultSet.getString("status"));
		return leaveRequest;
	}

}
