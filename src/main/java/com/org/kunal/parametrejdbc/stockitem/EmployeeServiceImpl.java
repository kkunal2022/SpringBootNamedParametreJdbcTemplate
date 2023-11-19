/**
 * kunal
 * parametrejdbc
 * com.org.kunal.parametrejdbc.stockitem
 */
package com.org.kunal.parametrejdbc.stockitem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * kunal parametrejdbc 2023
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.getAllEmployees();
	}

	@Override
	public Employee getEmployeeById(Long id) {
		return employeeRepository.getEmployeeById(id);
	}

	@Override
	public Long saveEmployee(Employee employee) {
		return employeeRepository.saveEmployee(employee);
	}

}
