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
public interface EmployeeDao {

	void save(Employee employee);

	void update(Employee employee);

	void delete(int id);

	Employee getById(int id);

	List<Employee> getAll();

}
