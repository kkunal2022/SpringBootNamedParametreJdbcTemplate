/**
 * kunal
 * parametrejdbc
 * com.org.kunal.parametrejdbc.stockitem
 */
package com.org.kunal.parametrejdbc.stockitem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * kunal
 * parametrejdbc
 * 2023
*/
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/employees")
@Slf4j
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}

	@GetMapping("/{employeeId}")
	public Employee getEmployeeById(@PathVariable Long employeeId) {
		return employeeService.getEmployeeById(employeeId);
	}

	@PostMapping
	public Long saveEmployee(@RequestBody Employee employee) {
		return employeeService.saveEmployee(employee);
	}

}
