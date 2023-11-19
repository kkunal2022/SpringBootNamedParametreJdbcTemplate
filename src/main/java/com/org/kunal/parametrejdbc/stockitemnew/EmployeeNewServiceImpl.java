/**
 * kunal
 * SpringBootNamedParametreJdbcTemplate
 * com.org.kunal.parametrejdbc.stockitemnew
 */
package com.org.kunal.parametrejdbc.stockitemnew;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * kunal SpringBootNamedParametreJdbcTemplate 2023
 */
@Service
public class EmployeeNewServiceImpl implements EmployeeNewService {
	private EmployeeDao employeeDao;

	@Autowired
	public EmployeeNewServiceImpl(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	@Override
	public void saveEmployee(Employee employee) {
		employeeDao.save(employee);
	}

	@Override
	public void updateEmployee(Employee employee) {
		employeeDao.update(employee);
	}

	@Override
	public void deleteEmployee(int id) {
		employeeDao.delete(id);
	}

	@Override
	public Employee getEmployeeById(int id) {
		return employeeDao.getById(id);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeDao.getAll();
	}

}
