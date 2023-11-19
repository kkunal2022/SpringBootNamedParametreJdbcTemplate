/**
 * kunal
 * parametrejdbc
 * com.org.kunal.parametrejdbc.stockitem
 */
package com.org.kunal.parametrejdbc.stockitem;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
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
public class EmployeeRepositoryImpl implements EmployeeRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (resultSet, rowNum) -> {
		Employee employee = new Employee();
		employee.setId(resultSet.getLong("id"));
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
		return employee;
	};

	@Override
	public List<Employee> getAllEmployees() {
		String sql = "SELECT * FROM employees";
		return namedParameterJdbcTemplate.query(sql, EMPLOYEE_ROW_MAPPER);
	}

	@Override
	public Employee getEmployeeById(Long id) {
		String sql = "SELECT * FROM employees WHERE id = :id";
		Map<String, Object> params = Collections.singletonMap("id", id);
		return namedParameterJdbcTemplate.queryForObject(sql, params, EMPLOYEE_ROW_MAPPER);
	}

	@Override
	public Long saveEmployee(Employee employee) {
		log.info("Entering saveEmployee --- '{}'");
		String sql = "INSERT INTO employees (id ,username,password, department_requesting, stock_request_date ,department_code ,purpose_of_issue ,stock_date ,\n"
				+ "				item_no ,item_reference_no , item_description ,date_of_previous_issue ,previous_issue_quantity,quantity_requested ,department_initiated_by,\n"
				+ "				department_authorised_by , department_confirmed_by,department_received_by,designated_person_approval_name ,signature ,date_of_confirmation ,role )  \n"
				+ "				VALUES (:id, :username, :password, :department_requesting, :stock_request_date, :department_code, :purpose_of_issue, :stock_date, :item_no, \n"
				+ "				:item_reference_no,\n"
				+ "				:item_description, :date_of_previous_issue, :previous_issue_quantity, :quantity_requested, :department_initiated_by, :department_authorised_by, \n"
				+ "				:department_confirmed_by, :department_received_by, :designated_person_approval_name, :signature, :date_of_confirmation, :role);";
		
		log.info("saveEmployee --- '{}'",  sql);
		SqlParameterSource params = new BeanPropertySqlParameterSource(employee);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, params, keyHolder);
		return keyHolder.getKey().longValue();
	}
}
