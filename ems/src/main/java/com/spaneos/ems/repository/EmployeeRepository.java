package com.spaneos.ems.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spaneos.ems.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

	Employee findByEmail(String email);

	List<Employee> findByDob(LocalDate dob);

	Employee findByMobile(String mobileNumber);

}
