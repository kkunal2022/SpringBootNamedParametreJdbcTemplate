/**
 * kunal
 * SpringBootNamedParametreJdbcTemplate
 * com.org.kunal.parametrejdbc.stockitemnew
 */
package com.org.kunal.parametrejdbc.stockitemnew;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * kunal SpringBootNamedParametreJdbcTemplate 2023
 */
@Repository
@Slf4j
public class EmployeeDaoImpl implements EmployeeDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public EmployeeDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void save(com.org.kunal.parametrejdbc.stockitemnew.Employee employee) {
        log.info("saveEmployee dao impl ---- '{}'", employee);
        String sql = "INSERT INTO employees (id ,username,password, department_requesting, stock_request_date ,department_code ,purpose_of_issue ,stock_date ," +
                "item_no ,item_reference_no , item_description ,date_of_previous_issue ,previous_issue_quantity,quantity_requested ,department_initiated_by," +
                "department_authorised_by , department_confirmed_by,department_received_by,designated_person_approval_name ,signature ,date_of_confirmation ,role )  " +
                "VALUES (:id, :username, :password, :department_requesting, :stock_request_date, :department_code, :purpose_of_issue, :stock_date, :item_no, " +
                ":item_reference_no," +
                ":item_description, :date_of_previous_issue, :previous_issue_quantity, :quantity_requested, :department_initiated_by, :department_authorised_by, " +
                ":department_confirmed_by, :department_received_by, :designated_person_approval_name, :signature, :date_of_confirmation, :role)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", employee.getId());
        mapSqlParameterSource.addValue("username", employee.getUsername());
        mapSqlParameterSource.addValue("password", employee.getPassword());
        mapSqlParameterSource.addValue("department_requesting", employee.getDepartmentRequesting());
        mapSqlParameterSource.addValue("stock_request_date", employee.getStockRequestDate());
        mapSqlParameterSource.addValue("department_code", employee.getDepartmentCode());
        mapSqlParameterSource.addValue("purpose_of_issue", employee.getPurposeOfIssue());
        mapSqlParameterSource.addValue("stock_date", employee.getStockDate());
        mapSqlParameterSource.addValue("item_no", employee.getItemNo());
        mapSqlParameterSource.addValue("item_reference_no", employee.getItemReferenceNo());
        mapSqlParameterSource.addValue("item_description", employee.getItemDescription());
        mapSqlParameterSource.addValue("date_of_previous_issue", employee.getDateOfPreviousIssue());
        mapSqlParameterSource.addValue("previous_issue_quantity", employee.getPreviousIssueQuantity());
        mapSqlParameterSource.addValue("quantity_requested", employee.getQuantityRequested());
        mapSqlParameterSource.addValue("department_initiated_by", employee.getDepartmentInitiatedBy());
        mapSqlParameterSource.addValue("department_authorised_by", employee.getDepartmentAuthorisedBy());
        mapSqlParameterSource.addValue("department_confirmed_by", employee.getDepartmentConfirmedBy());
        mapSqlParameterSource.addValue("department_received_by", employee.getDepartmentReceivedBy());
        mapSqlParameterSource.addValue("designated_person_approval_name", employee.getDesignatedPersonApprovalName());
        mapSqlParameterSource.addValue("signature", employee.getSignature());
        mapSqlParameterSource.addValue("date_of_confirmation", employee.getDateOfConfirmation());
        mapSqlParameterSource.addValue("role", employee.getRole());
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);

        employee.getLeaveRequests().forEach(leaveRequest -> {
            log.info("saveEmployee dao impl in Leave Request for loop  ---- '{}'", employee);
            saveLeaveRequest(leaveRequest, employee.getId());
        });
    }

    @Override
    public void update(Employee employee) {
        String updateSql = "UPDATE employees SET item_description = :itemDescription, purpose_of_issue = :purposeOfIssue WHERE id = :id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", employee.getId());
        mapSqlParameterSource.addValue("purpose_of_issue", employee.getPurposeOfIssue());
        mapSqlParameterSource.addValue("item_description", employee.getItemDescription());
        namedParameterJdbcTemplate.update(updateSql, mapSqlParameterSource);

        String deleteSql = "DELETE FROM leave_request WHERE employee_id = :employeeId";
        MapSqlParameterSource deleteParams = new MapSqlParameterSource();
        deleteParams.addValue("employeeId", employee.getId());
        namedParameterJdbcTemplate.update(deleteSql, deleteParams);

        employee.getLeaveRequests().forEach(leaveRequest -> saveLeaveRequest(leaveRequest, employee.getId()));
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM employees WHERE id = :id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }

    @Override
    public Employee getById(int id) {
        String getByIdSql = "SELECT * FROM employees WHERE id = :id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(getByIdSql, mapSqlParameterSource, new EmployeeRowMapper());
    }

    @Override
    public List<Employee> getAll() {
        String getAllSql = "SELECT * FROM employees";
        return namedParameterJdbcTemplate.query(getAllSql, new EmployeeRowMapper());
    }

    private void saveLeaveRequest(LeaveRequest leaveRequest, int employeeId) {
        log.info("saveEmployee saveLeaveRequest dao impl ---- '{}'", employeeId);
        String sql = "INSERT INTO leave_request (id, employee_id, start_date, end_date, status) "
                + "VALUES (:id, :employeeId, :startDate, :endDate, :status)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", leaveRequest.getId());
        mapSqlParameterSource.addValue("employeeId", employeeId);
        mapSqlParameterSource.addValue("startDate", leaveRequest.getStartDate());
        mapSqlParameterSource.addValue("endDate", leaveRequest.getEndDate());
        mapSqlParameterSource.addValue("status", leaveRequest.getStatus());
        log.info("After saveEmployee saveLeaveRequest dao impl ---- '{}'", mapSqlParameterSource);
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }

}
