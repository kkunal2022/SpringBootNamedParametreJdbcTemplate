/**
 * kunal
 * parametrejdbc
 * com.org.kunal.parametrejdbc.stockitem
 */
package com.org.kunal.parametrejdbc.stockitem;

import java.util.List;

/**
 * kunal parametrejdbc 2023
 */
public interface EmployeeService {
	
	List<Employee> getAllEmployees();

	Employee getEmployeeById(Long id);

	Long saveEmployee(Employee employee);
}
