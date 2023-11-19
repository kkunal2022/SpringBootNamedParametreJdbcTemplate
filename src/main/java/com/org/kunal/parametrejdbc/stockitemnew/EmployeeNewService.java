/**
 * kunal
 * SpringBootNamedParametreJdbcTemplate
 * com.org.kunal.parametrejdbc.stockitemnew
 */
package com.org.kunal.parametrejdbc.stockitemnew;

import java.util.List;

/**
 * kunal SpringBootNamedParametreJdbcTemplate 2023
 */
public interface EmployeeNewService {

	void saveEmployee(Employee employee);

	void updateEmployee(Employee employee);

	void deleteEmployee(int id);

	Employee getEmployeeById(int id);

	List<Employee> getAllEmployees();

}
