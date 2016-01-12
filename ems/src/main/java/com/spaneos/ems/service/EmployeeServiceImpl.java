package com.spaneos.ems.service;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spaneos.ems.domain.Employee;
import com.spaneos.ems.exception.DataNotFoundException;
import com.spaneos.ems.exception.DuplicateDataException;
import com.spaneos.ems.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepostiory;
	private final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Override
	@Transactional
	public Employee registerEmployee(Employee employee) {
		Validate.notNull(employee, "The Employee object should not be null");
		Validate.notEmpty(employee.getEmail(), "Email id can't be empty");
		String email = employee.getEmail();
		boolean isDuplicate = checkDuplicateEmail(email);
		if (isDuplicate) {
			LOGGER.info("Email id : {} already given to some other Employee", email);
			throw new DuplicateDataException("Email id already given to some other Employee");
		} else {
			employee = employeeRepostiory.save(employee);
			LOGGER.info("Employee is added  with id :{} and email : {}", employee.getId(), employee.getEmail());
		}
		return employee;
	}

	@Override
	public Employee findByEmail(String email) {
		Validate.notNull(email, "Email id can not be null");
		Validate.notEmpty(email, "Email id can not be null");
		Employee employee = employeeRepostiory.findByEmail(email);
		if (employee == null) {
			LOGGER.info("Employee found with the email :{}", email);
			throw new DataNotFoundException("There is no employee exits with email :" + email);
		} else {
			LOGGER.info("Employee exits with email : {}", email);
			return employee;
		}
	}

	@Override
	public Employee findByMobile(String mobileNumber) {
		Validate.notNull(mobileNumber, "Mobile number  can not be null");
		Validate.notEmpty(mobileNumber, "Mobile number  can not be null");
		Employee employee = employeeRepostiory.findByMobile(mobileNumber);
		if (employee == null) {
			LOGGER.info("Employee not found with the mobile number :{}", mobileNumber);
			throw new DataNotFoundException("There is no employee exits with mobile number :" + mobileNumber);
		} else {
			LOGGER.info("Employee exits with mobile number : {}", mobileNumber);
			return employee;
		}
	}

	@Override
	public Employee findByEmpno(Long empno) {
		Validate.notNull(empno, "Mobile number  can not be null");
		Employee employee = employeeRepostiory.findOne(empno);
		if (employee == null) {
			LOGGER.info("Employee not found with the id :{}", empno);
			throw new DataNotFoundException("There is no employee exits with  id :" + empno);
		} else {
			LOGGER.info("Employee exits with id : {}", empno);
			return employee;
		}
	}

	@Override
	public List<Employee> findByBirthDate() {
		LocalDate date = LocalDate.now();
		List<Employee> birthDayList = employeeRepostiory.findByDob(date);
		if (birthDayList == null || birthDayList.size() <= 0) {
			LOGGER.info("Today there is no birth day celebraction");
			throw new DataNotFoundException("Today there is no birthday celebrations ");
		} else {
			LOGGER.info("Today {} employee celebratiing birthday ", birthDayList.size());
			return birthDayList;
		}

	}

	@Override
	public List<Employee> findAll() {
		List<Employee> empList = employeeRepostiory.findAll();
		if (empList == null || empList.size() < 0) {
			LOGGER.info("There is no employees to show the details");
			throw new DataNotFoundException("There is no employees to show the details");
		} else {
			LOGGER.info("Total number of employee : {}", empList.size());
			return empList;
		}
	
	}

	@Override
	@Transactional(rollbackFor = DataNotFoundException.class)
	public Employee updateEmployee(Employee employee) {
		Employee emp = employeeRepostiory.getOne(employee.getId());
		if (emp == null)
			throw new DataNotFoundException("Employee is not found with the given id " + employee.getId());
		employeeRepostiory.save(employee);
		return employee;
	}

	/**
	 * It search records with the given email,if any data is exists then it
	 * returns true otherwise returns false
	 * 
	 * @param email
	 * @return boolean
	 */
	private boolean checkDuplicateEmail(String email) {
		Employee employee = employeeRepostiory.findByEmail(email);
		if (employee != null) {
			LOGGER.info("Employee found with the email :{}", email);
			return true;
		} else {
			LOGGER.info("There is no employee exits with email :{}", email);
			return false;
		}
	}

	public EmployeeRepository getEmployeeRepostiory() {
		return employeeRepostiory;
	}

	public void setEmployeeRepostiory(EmployeeRepository employeeRepostiory) {
		this.employeeRepostiory = employeeRepostiory;
	}

}
