/**
 * kunal
 * parametrejdbc
 * com.org.kunal.parametrejdbc.stockitem
 */
package com.org.kunal.parametrejdbc.stockitem;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * kunal parametrejdbc 2023
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class LeaveRequestRepositoryImpl implements LeaveRequestRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final RowMapper<LeaveRequest> LEAVE_REQUEST_ROW_MAPPER = (resultSet, rowNum) -> {
		LeaveRequest leaveRequest = new LeaveRequest();
		leaveRequest.setId(resultSet.getLong("id"));
		leaveRequest.setEmployeeId(resultSet.getLong("employee_id"));
		leaveRequest.setReason(resultSet.getString("reason"));
		leaveRequest.setStartDate(resultSet.getDate("start_date").toLocalDate());
		leaveRequest.setEndDate(resultSet.getObject("end_date", LocalDate.class));
		leaveRequest.setApproved(resultSet.getBoolean("approved"));
		return leaveRequest;
	};

	@Override
	public List<LeaveRequest> getLeaveRequestsByEmployeeId(Long employeeId) {
		String sql = "SELECT * FROM leave_requests WHERE employee_id = :employeeId";
		Map<String, Object> params = Collections.singletonMap("employeeId", employeeId);
		return namedParameterJdbcTemplate.query(sql, params, LEAVE_REQUEST_ROW_MAPPER);
	}

	@Override
	public Long requestLeave(LeaveRequest leaveRequest) {
		String sql = "INSERT INTO leave_requests (employee_id, reason, start_date, end_date, approved) "
				+ "VALUES (:employeeId, :reason, :startDate, :endDate, :approved)";
		
		SqlParameterSource params = new BeanPropertySqlParameterSource(leaveRequest);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, params, keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override
	public void updateLeaveRequestApproval(Long requestId, boolean approved) {
		String sql = "UPDATE leave_requests SET approved = :approved WHERE id = :requestId";
		Map<String, Object> params = new HashMap<>();
		params.put("requestId", requestId);
		params.put("approved", approved);
		namedParameterJdbcTemplate.update(sql, params);
	}

	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

}
