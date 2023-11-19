/**
 * kunal
 * SpringBootNamedParametreJdbcTemplate
 * com.org.kunal.parametrejdbc.stockitemnew
 */
package com.org.kunal.parametrejdbc.stockitemnew;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.org.kunal.parametrejdbc.stockitemnew.LeaveRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * kunal SpringBootNamedParametreJdbcTemplate 2023
 */
@Repository
@Slf4j
public class EmployeeDaoImpl implements EmployeeDao {
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public EmployeeDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(com.org.kunal.parametrejdbc.stockitemnew.Employee employee) {
		log.info("saveEmployee dao impl ---- '{}'" , employee);
		String sql = "INSERT INTO employees (id ,username,password, department_requesting, stock_request_date ,department_code ,purpose_of_issue ,stock_date ," +
                "item_no ,item_reference_no , item_description ,date_of_previous_issue ,previous_issue_quantity,quantity_requested ,department_initiated_by," +
                "department_authorised_by , department_confirmed_by,department_received_by,designated_person_approval_name ,signature ,date_of_confirmation ,role )  " +
                "VALUES (:id, :username, :password, :department_requesting, :stock_request_date, :department_code, :purpose_of_issue, :stock_date, :item_no, " +
                ":item_reference_no," +
                ":item_description, :date_of_previous_issue, :previous_issue_quantity, :quantity_requested, :department_initiated_by, :department_authorised_by, " +
                ":department_confirmed_by, :department_received_by, :designated_person_approval_name, :signature, :date_of_confirmation, :role)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", employee.getId());
		params.addValue("username", employee.getUsername());
		params.addValue("password", employee.getPassword());
		params.addValue("department_requesting", employee.getDepartmentRequesting());
		params.addValue("stock_request_date", employee.getStockRequestDate());
		params.addValue("department_code", employee.getDepartmentCode());
		params.addValue("purpose_of_issue", employee.getPurposeOfIssue());
		params.addValue("stock_date", employee.getStockDate());
		params.addValue("item_no", employee.getItemNo());
		params.addValue("item_reference_no", employee.getItemReferenceNo());
		params.addValue("item_description", employee.getItemDescription());
		params.addValue("date_of_previous_issue", employee.getDateOfPreviousIssue());
		params.addValue("previous_issue_quantity", employee.getPreviousIssueQuantity());
		params.addValue("quantity_requested", employee.getQuantityRequested());
		params.addValue("department_initiated_by", employee.getDepartmentInitiatedBy());
		params.addValue("department_authorised_by", employee.getDepartmentAuthorisedBy());
		params.addValue("department_confirmed_by", employee.getDepartmentConfirmedBy());
		params.addValue("department_received_by", employee.getDepartmentReceivedBy());
		params.addValue("designated_person_approval_name", employee.getDesignatedPersonApprovalName());
		params.addValue("signature", employee.getSignature());
		params.addValue("date_of_confirmation", employee.getDateOfConfirmation());
		params.addValue("role", employee.getRole());
		jdbcTemplate.update(sql, params);

		for (LeaveRequest leaveRequest : employee.getLeaveRequests()) {
			log.info("saveEmployee dao impl in Leave Request for loop  ---- '{}'" , employee);
			saveLeaveRequest(leaveRequest, employee.getId());
		}
	}

	@Override
	public void update(Employee employee) {
		String sql = "UPDATE employees SET item_description = :itemDescription, purpose_of_issue = :purposeOfIssue WHERE id = :id";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", employee.getId());
		params.addValue("purpose_of_issue", employee.getPurposeOfIssue());
		params.addValue("item_description", employee.getItemDescription());
		jdbcTemplate.update(sql, params);

		String deleteSql = "DELETE FROM leave_request WHERE employee_id = :employeeId";
		MapSqlParameterSource deleteParams = new MapSqlParameterSource();
		deleteParams.addValue("employeeId", employee.getId());
		jdbcTemplate.update(deleteSql, deleteParams);

		for (LeaveRequest leaveRequest : employee.getLeaveRequests()) {
			saveLeaveRequest(leaveRequest, employee.getId());
		}
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM employees WHERE id = :id";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		jdbcTemplate.update(sql, params);
	}

	@Override
	public Employee getById(int id) {
		String sql = "SELECT * FROM employees WHERE id = :id";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		return jdbcTemplate.queryForObject(sql, params, new EmployeeRowMapper());
	}

	@Override
	public List<Employee> getAll() {
		String sql = "SELECT * FROM employees";
		return jdbcTemplate.query(sql, new EmployeeRowMapper());
	}

	private void saveLeaveRequest(LeaveRequest leaveRequest, int employeeId) {
		log.info("saveEmployee saveLeaveRequest dao impl ---- '{}'" , employeeId);
		String sql = "INSERT INTO leave_request (id, employee_id, start_date, end_date, status) "
				+ "VALUES (:id, :employeeId, :startDate, :endDate, :status)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", leaveRequest.getId());
		params.addValue("employeeId", employeeId);
		params.addValue("startDate", leaveRequest.getStartDate());
		params.addValue("endDate", leaveRequest.getEndDate());
		params.addValue("status", leaveRequest.getStatus());
		log.info("After saveEmployee saveLeaveRequest dao impl ---- '{}'" , params);
		jdbcTemplate.update(sql, params);
	}

}
