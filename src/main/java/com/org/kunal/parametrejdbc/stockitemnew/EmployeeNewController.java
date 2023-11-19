/**
 * kunal
 * SpringBootNamedParametreJdbcTemplate
 * com.org.kunal.parametrejdbc.stockitemnew
 */
package com.org.kunal.parametrejdbc.stockitemnew;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * kunal SpringBootNamedParametreJdbcTemplate 2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/kunal")
public class EmployeeNewController {

	private EmployeeNewService employeeService;

	@Autowired
	public EmployeeNewController(EmployeeNewService employeeService) {
		this.employeeService = employeeService;
	}

	@PostMapping(value = "/employees", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public void saveEmployee(@RequestBody Employee employee) {
		employeeService.saveEmployee(employee);
	}

	@PutMapping("/employees/{id}")
	public void updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
		employee.setId(id);
		employeeService.updateEmployee(employee);
	}

	@DeleteMapping("/employees/{id}")
	public void deleteEmployee(@PathVariable int id) {
		employeeService.deleteEmployee(id);
	}

	@GetMapping("/employees/{id}")
	public Employee getEmployeeById(@PathVariable int id) {
		return employeeService.getEmployeeById(id);
	}

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}

}
