/**
 * kunal
 * SpringBootNamedParametreJdbcTemplate
 * com.org.kunal.parametrejdbc.stockitemnew
 */
package com.org.kunal.parametrejdbc.stockitemnew;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * kunal SpringBootNamedParametreJdbcTemplate 2023
 */
@Component
public class EmployeeRowMapper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Employee employee = new Employee();
		employee.setId(resultSet.getInt("id"));
		employee.setUsername(resultSet.getString("username"));
		employee.setPassword(resultSet.getString("password"));
		employee.setDepartmentRequesting(resultSet.getString("department_requesting"));
		employee.setStockRequestDate(resultSet.getDate("stock_request_date"));
		employee.setDepartmentCode(resultSet.getInt("department_code"));
		employee.setPurposeOfIssue(resultSet.getString("purpose_of_issue"));
		employee.setStockDate(resultSet.getDate("stock_date"));
		employee.setItemNo(resultSet.getInt("item_no"));
		employee.setItemReferenceNo(resultSet.getString("item_reference_no"));
		employee.setItemDescription(resultSet.getString("item_description"));
		employee.setDateOfPreviousIssue(resultSet.getDate("date_of_previous_issue"));
		employee.setPreviousIssueQuantity(resultSet.getInt("previous_issue_quantity"));
		employee.setQuantityRequested(resultSet.getInt("quantity_requested"));
		employee.setDepartmentInitiatedBy(resultSet.getString("department_initiated_by"));
		employee.setDepartmentAuthorisedBy(resultSet.getString("department_authorised_by"));
		employee.setDepartmentConfirmedBy(resultSet.getString("department_confirmed_by"));
		employee.setDepartmentReceivedBy(resultSet.getString("department_received_by"));
		employee.setDesignatedPersonApprovalName(resultSet.getString("designated_person_approval_name"));
		employee.setSignature(resultSet.getString("signature"));
		employee.setDateOfConfirmation(resultSet.getDate("date_of_confirmation"));
		employee.setRole(resultSet.getString("role"));

		employee.setLeaveRequests(new ArrayList<>());
		
		return employee;
	}

}
