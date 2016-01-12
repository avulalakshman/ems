package com.spaneos.ems.service;

import java.util.List;
import com.spaneos.ems.domain.Employee;
import com.spaneos.ems.exception.DuplicateDataException;

public interface EmployeeService  {
	/**
	 * It invokes DAO method to store the element into a database. 
	 * @param employee
	 * @return {@link Employee}
	 * @throws NullPointerException if the specified element is null
     * @throws DuplicateDataException if the specified element's attribute - email id already persisted  
	 */
	public Employee registerEmployee(Employee employee);
	public Employee findByEmail(String email);
	public Employee findByMobile(String mobileNumber);
	public Employee findByEmpno(Long empno);
	public List<Employee> findByBirthDate();
	public Employee updateEmployee(Employee employee);
	public List<Employee> findAll();

}
